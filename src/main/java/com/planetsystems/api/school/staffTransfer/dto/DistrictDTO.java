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
@Deprecated
public class DistrictDTO {
    @NotBlank(message = "districtId is required")
    @NotEmpty(message = "districtId is required")
    private String id ;

    @NotBlank(message = "name is required")
    @NotEmpty(message = "name is required")
    private String name;

    private IdNameDTO subRegion;
}
