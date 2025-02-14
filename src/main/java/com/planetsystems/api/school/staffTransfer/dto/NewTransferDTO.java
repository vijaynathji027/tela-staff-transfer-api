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
public class NewTransferDTO {
    private String academicTermId;
    private String academicTermName;
    private String academicYear;
    private StaffDTO requestedBy;
//    private StaffDTO approvedBy;
    private StaffDTO staff;
    private SchoolDTO fromSchool;
    private SchoolDTO toSchool;

    @NotBlank(message = "transfer category is required")
    @NotEmpty(message = "transfer category is required")
    private String transferCategory;
//    private String code;

}
