package com.planetsystems.api.school.staffTransfer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class SystemResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private boolean status = true;
    private int statusCode = 200;
    private T data = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pagination pagination;


    // Default constructor
    public SystemResponseDTO() {
    }

    // Constructor with only data
    public SystemResponseDTO(T data) {
        this.data = data;
        this.message = "success";
        this.status = true;
    }

    // Constructor with data, message, and optional status
    public SystemResponseDTO(T data, String message, Boolean status) {
        this.data = data;
        this.message = message;
        this.status = (status != null) ? status : true;
        this.statusCode = 200;
    }

    // Constructor with data and message
    public SystemResponseDTO(T data, String message) {
        this.message = message;
        this.data = data;
        this.status = true;
    }

    // Constructor with data and count (pagination not included)
    public SystemResponseDTO(T data, long count) {
        this.data = data;
        this.message = "success";
        this.status = true;
    }

    // Constructor with data, message, and pagination (if needed)
    public SystemResponseDTO(T data, String message, Pagination pagination) {
        this.data = data;
        this.message = message;
        this.status = true;
        this.pagination = pagination;
    }

    // Static factory methods
    public static <T> SystemResponseDTO<T> createResponse(T data) {
        return new SystemResponseDTO<>(data);
    }

    public static <T> SystemResponseDTO<T> createResponse(String message) {
        return new SystemResponseDTO<>(null, message);
    }

    public static <T> SystemResponseDTO<T> createResponse(T data, String message) {
        return new SystemResponseDTO<>(data, message);
    }

    public static <T> SystemResponseDTO<T> createResponse(T data, String message, int statusCode) {
        SystemResponseDTO<T> response = createResponse(data, message);
        response.setStatusCode(statusCode);
        return response;
    }

    public static <T> SystemResponseDTO<T> createResponse(T data, Pagination pagination) {
        SystemResponseDTO<T> response = createResponse(data);
        response.setPagination(pagination);
        return response;
    }

    public static <T> SystemResponseDTO<T> createResponse(T data, String message, boolean status, Pagination pagination) {
        SystemResponseDTO<T> response = createResponse(data, message);
        response.setStatus(status);
        response.setPagination(pagination);
        return response;
    }

    public static <T> SystemResponseDTO<T> createResponse(T data, String message, boolean status, int statusCode, Pagination pagination) {
        SystemResponseDTO<T> response = new SystemResponseDTO<>(data, message, status);
        response.setStatusCode(statusCode);
        response.setPagination(pagination);
        return response;
    }

    public static <T> SystemResponseDTO<T> createErrorResponse(String message, int statusCode) {
        SystemResponseDTO<T> response = new SystemResponseDTO<>(null, message, false);
        response.setStatusCode(statusCode);
        return response;
    }

    public SystemResponseDTO<T> setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public SystemResponseDTO<T> setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public SystemResponseDTO<T> setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }
}
