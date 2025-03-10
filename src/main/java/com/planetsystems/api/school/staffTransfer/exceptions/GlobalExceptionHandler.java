package com.planetsystems.api.school.staffTransfer.exceptions;


import com.planetsystems.api.school.staffTransfer.dto.ErrorResponseDTO;
import com.planetsystems.api.school.staffTransfer.dto.SystemResponseDTO;
import com.planetsystems.api.school.staffTransfer.util.AppMessages;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;


import java.rmi.ServerException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TelaNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(TelaNotFoundException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.NOT_FOUND.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleUsernameNotFoundException(TelaNotFoundException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.NOT_FOUND.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.FORBIDDEN.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public final ResponseEntity<Object> handleMissingTokenException(InsufficientAuthenticationException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.UNAUTHORIZED.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<Object> handleExpiredTokenException(ExpiredJwtException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.FORBIDDEN.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AlreadyExitsException.class)
    public final ResponseEntity<Object> handleUserNameAlreadyUsedException(AlreadyExitsException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.CONFLICT.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InValidCredentialsException.class)
    public final ResponseEntity<Object> handleInValidCredentialsException(InValidCredentialsException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.UNAUTHORIZED.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public final ResponseEntity<Object> handleInBadRequestException(HttpClientErrorException.BadRequest ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingDataException.class)
    public final ResponseEntity<Object> handleMissingDataException(MissingDataException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public final ResponseEntity<Object> handleNotFoundException(MissingDataException ex, WebRequest request) throws Exception {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, ex.getMessage(), false, HttpStatus.NOT_FOUND.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public final ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException ex, WebRequest request) {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(
                null,
                AppMessages.ERROR_MULTIPLE_RESULT_FOUND,
                false,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MultipleResultsFoundException.class)
    public final ResponseEntity<Object> handleMultipleResultsFoundException(MultipleResultsFoundException ex, WebRequest request) {
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(
                null,
                ex.getMessage(),
                false,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({InvalidException.class})
    public ResponseEntity handleInvalidException(InvalidException exception) {
        exception.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, exception.getMessage(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception) {
        exception.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, exception.getMessage(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity handleDateTimeParseException(DateTimeParseException exception) {
        exception.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, exception.getMessage(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity handleForbiddenException(HttpMessageNotReadableException exception) {
        exception.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, exception.getMessage(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ex.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, errors.toString(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, errors.toString(), false, HttpStatus.BAD_REQUEST.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class, ServerException.class})
    public ResponseEntity handleRuntimeException(RuntimeException exception) {
        exception.printStackTrace();
        SystemResponseDTO<Void> responseDTO = SystemResponseDTO.createResponse(null, exception.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
