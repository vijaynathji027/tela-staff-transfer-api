package com.planetsystems.api.school.staffTransfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SchoolDTO {
    private String telaNumber;

    private String schoolId;

    @NotBlank(message = "name is required")
    @NotEmpty(message = "name is required")
    private String name;

    @NotBlank(message = "schoolLevel is required")
    @NotEmpty(message = "schoolLevel is required")
    private String schoolLevel;

    @NotBlank(message = "schoolOwnership is required")
    @NotEmpty(message = "schoolOwnership is required")
    private String schoolOwnership;


//    private DistrictDTO district;
}
