package com.planetsystems.api.school.staffTransfer.entity;


import com.planetsystems.api.school.staffTransfer.entity.ennum.Gender;
import com.planetsystems.api.school.staffTransfer.entity.ennum.UserType;
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
public class RequestedBy {
    @Column(nullable = false)
    private String requestedById;

    @Column(nullable = false)
    private Gender requestedByGender;

    @Column(nullable = false)
    private String requestedByFirstName;
    
    @Column(nullable = false)
    private String requestedByLastName;

    private String requestedByPhoneNumber;
    
    private String requestedByEmail;

    @Column(nullable = false)
    private UserType requestedByUserType;
}
