package com.planetsystems.api.school.staffTransfer.entity;

import com.planetsystems.api.school.staffTransfer.entity.ennum.SchoolLevel;
import com.planetsystems.api.school.staffTransfer.entity.ennum.SchoolOwnership;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class FromSchool {

    String fromTelaNumber;


    String fromSchoolId;

    @Column(nullable = false)
    String fromSchoolName;

    @Column(nullable = false)
    SchoolOwnership fromSchoolOwnership;

    @Column(nullable = false)
    SchoolLevel fromSchoolLevel;

    
}
