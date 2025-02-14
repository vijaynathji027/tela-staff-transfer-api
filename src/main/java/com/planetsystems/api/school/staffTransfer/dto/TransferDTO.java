package com.planetsystems.api.school.staffTransfer.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransferDTO {
 private String id;
 private StaffDTO requestedBy;
 private StaffDTO approvedBy;
 private StaffDTO staff;
 private SchoolDTO fromSchool;
 private SchoolDTO toSchool;
 private String requestStatus;
 private String code;
 private String requestedDate;
 private String requestedTime;
 private String approvedDate;
 private String approvedTime;
 private String comment;
 private String transferCategory;
 private String academicTermId;
 private String academicTermName;
 private String academicYear;


}
