package com.planetsystems.api.school.staffTransfer.entity;

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
//@Embeddable
@Deprecated
public class ToRegion {
//    @Column(nullable = false)
    String toRegionId;

//    @Column(nullable = false)
    String toRegionName;
}
