package com.planetsystems.api.school.staffTransfer.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.planetsystems.api.school.staffTransfer.entity.ennum.Gender;
import com.planetsystems.api.school.staffTransfer.entity.ennum.RequestStatus;
import com.planetsystems.api.school.staffTransfer.entity.ennum.TransferCategory;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@ToString
public class Transfer {

 @Id
 @UuidGenerator   
 private String id;
 @Column(nullable = false)
 private String academicTermId;
 @Column(nullable = false)
 private String academicTermName;
 @Column(nullable = false)
 private String academicYear;

 @Column(nullable = false)
 @Embedded
 private Staff staff;

 @Column(nullable = false)
 private TransferCategory transferCategory;

 @Column(nullable = false)
 @Embedded
 private RequestedBy requestedBy;

 @Embedded
 private ApprovedBy approvedBy;

 @Column(nullable = false)
 @Embedded
 private FromSchool fromSchool;

 @Column(nullable = false)
 @Embedded
 private ToSchool toSchool;

 @Column(nullable = false)
 private RequestStatus requestStatus;

 @Column(nullable = false)
 private String code;

 private String comment;


 @Column(updatable = false)
 private LocalDate requestedDate;

 @Column(updatable = false)
 private LocalTime requestedTime;

 @Column(insertable = false)
 private LocalDate approvedDate;

 @Column(insertable = false)
 private LocalTime approvedTime;

}
