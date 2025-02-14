package com.planetsystems.api.school.staffTransfer.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder()
@Getter
@Setter
@ToString
public class StaffDTO {
    @NotBlank(message = "staff id comment is required")
    @NotEmpty(message = "staff id comment is required")
private String id;
    @NotBlank(message = "firstname is required")
    @NotEmpty(message = "firstname is required")
private String firstName;
        @NotBlank(message = "lastName is required")
    @NotEmpty(message = "lastName is required")
private String lastName;
    @NotBlank(message = "phoneNumber is required")
    @NotEmpty(message = "phoneNumber is required")
private String phoneNumber;
    @NotBlank(message = "email is required")
    @NotEmpty(message = "email is required")
private String email;

    @NotBlank(message = "gender is required")
    @NotEmpty(message = "gender is required")
    private String gender;

private String userType;
// private SchoolDTO school;
}
