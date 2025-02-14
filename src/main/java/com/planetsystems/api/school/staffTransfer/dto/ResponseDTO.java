package com.planetsystems.api.school.staffTransfer.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ResponseDTO<T> {

    boolean status;
    String message;
    T data;


    public ResponseDTO(T data) {
        this.data = data;
        this.status = true;
        this.message = "success";
    }

    public ResponseDTO(T data , String message) {
        this.message = message;
        this.data = data;
    }
}
