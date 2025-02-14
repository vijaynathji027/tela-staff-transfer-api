package com.planetsystems.api.school.staffTransfer.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponseDTO<T> {

    boolean status;
    String message;
    T data;


    public ErrorResponseDTO(String message) {
        this.data = null;
        this.status = false;
        this.message = message;
    }

    public ErrorResponseDTO(T data , String message) {
        this.message = message;
        this.data = data;
    }
}
