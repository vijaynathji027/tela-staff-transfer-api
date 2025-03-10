package com.planetsystems.api.school.staffTransfer.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetsystems.api.school.staffTransfer.dto.SystemResponseDTO;
import com.planetsystems.api.school.staffTransfer.exceptions.InvalidTokenException;
import com.planetsystems.api.school.staffTransfer.service.RedisService;
import com.planetsystems.api.school.staffTransfer.util.AppMessages;
import com.planetsystems.api.school.staffTransfer.util.IpAddressUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public RequestFilter(JwtUtil jwtUtil, RedisService redisService) {
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
    }

    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private void validateHeader(String header, String errorMessage) {
        if (header == null || header.trim().isEmpty()) {
            throw new InvalidTokenException(errorMessage);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        String endpoint = request.getRequestURI();
        String method = request.getMethod().toUpperCase();
        String username = "UNKNOWN";
        int statusCode = 200;

        // Wrap request and response for caching
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            requestWrapper.getParameterMap();
            final String authorizationHeader = request.getHeader("Authorization");
            validateHeader(authorizationHeader, AppMessages.INVALID_TOKEN);

            if (!(authorizationHeader.startsWith("Bearer ") && authorizationHeader.length() > 7)) {
                throw new InvalidTokenException(AppMessages.INVALID_TOKEN);
            }

            String jwt = authorizationHeader.substring(7);

            Claims claims = jwtUtil.extractAllClaims(jwt);
            String deviceId = claims.get("deviceId") != null ? claims.get("deviceId").toString() : null;
            username = claims.get("sub") != null ? claims.get("sub").toString() : "UNKNOWN";

            if (deviceId == null || username == null) {
                throw new InvalidTokenException(AppMessages.INVALID_TOKEN);
            }

            String redisTokenValue = redisService.getAuthToken(username, deviceId);
            boolean userStatus = redisService.getAuthStatus(username, deviceId);

            if (redisTokenValue.isEmpty() || !jwt.equals(redisTokenValue)) {
                throw new InvalidTokenException(AppMessages.INVALID_TOKEN);
            } else if (!userStatus) {
                throw new InvalidTokenException(AppMessages.USER_ACCOUNT_IS_INACTIVE);
            }

            redisService.refreshTokenExpiry(username, deviceId);
            filterChain.doFilter(requestWrapper, responseWrapper);
            statusCode = responseWrapper.getStatus();
            long duration = System.currentTimeMillis() - startTime;
            logJsonRequest(username, endpoint, method, statusCode, duration, requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        } catch (InvalidTokenException e) {
            SystemResponseDTO<Object> responseDTO = new SystemResponseDTO<>();
            responseDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            responseDTO.setMessage(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(responseDTO));
        } catch (Exception e) {
            SystemResponseDTO<Object> responseDTO = new SystemResponseDTO<>();
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(responseDTO));
        }
    }


    private void logJsonRequest(String username, String endpoint, String method, int statusCode,
                                long responseTime, ContentCachingRequestWrapper request,
                                ContentCachingResponseWrapper response) {
        try {
            // 🔹 Read request body AFTER filter execution
            byte[] requestBytes = request.getContentAsByteArray();
            String requestBody = requestBytes.length > 0 ? new String(requestBytes, StandardCharsets.UTF_8) : null;

            // 🔹 Read response body AFTER filter execution
            byte[] responseBytes = response.getContentAsByteArray();
            String responseBody = responseBytes.length > 0 ? new String(responseBytes, StandardCharsets.UTF_8) : null;

            Map<String, Object> logData = new HashMap<>();
            logData.put("timestamp", LocalDateTime.now().toString());
            logData.put("method", method);
            logData.put("statusCode", statusCode);
            logData.put("username", username);
            logData.put("resource", endpoint);
            logData.put("queryString", request.getQueryString());
            logData.put("requestBody", endpoint.startsWith("/logs") ? null : requestBody);
            logData.put("response", endpoint.startsWith("/logs") ? null : responseBody);
            logData.put("responseTime", responseTime + " ms");
            logData.put("clientIp", IpAddressUtil.getClientIp(request));
            logData.put("userAgent", request.getHeader("User-Agent"));

            logger.info(objectMapper.writeValueAsString(logData));

        } catch (Exception e) {
            logger.error("Failed to log request/response body", e);
        }
    }

    private void handleErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        SystemResponseDTO<Object> responseDTO = new SystemResponseDTO<>();
        responseDTO.setStatusCode(status);
        responseDTO.setMessage(message);

        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(responseDTO));
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        return object == null ? null : new ObjectMapper().writeValueAsString(object);
    }
}
