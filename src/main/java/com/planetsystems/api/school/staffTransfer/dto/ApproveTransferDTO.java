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
public class ApproveTransferDTO {
    @NotBlank(message = "transfer id is required")
    @NotEmpty(message = "transfer id is required")
    private String id;

    @NotBlank(message = "transfer comment is required")
    @NotEmpty(message = "transfer comment is required")
    private String comment;

    private StaffDTO approvedBy;
}
