package com.planetsystems.api.school.staffTransfer.entity;


import com.planetsystems.api.school.staffTransfer.entity.ennum.Gender;
import com.planetsystems.api.school.staffTransfer.entity.ennum.UserType;
import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class ApprovedBy {


    private String approvedById;

    private String approvedByFirstName;
    

    private String approvedByLastName;

    private String approvedByPhoneNumber;
    
    private String approvedByEmail;
    private Gender approvedByGender;

    private UserType approvedByUserType;
}
