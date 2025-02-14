package com.planetsystems.api.school.staffTransfer.entity;

import com.planetsystems.api.school.staffTransfer.entity.ennum.SchoolLevel;
import com.planetsystems.api.school.staffTransfer.entity.ennum.SchoolOwnership;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@ToString
public class ToSchool {
    String toTelaNumber;

    String toSchoolId;



    @Column(nullable = false)
    String toSchoolName;

    @Column(nullable = false)
    SchoolOwnership toSchoolOwnership;

    @Column(nullable = false)
    SchoolLevel toSchoolLevel;



//    @Column(nullable = false)
//    private ToDistrict toDistrict;
}
