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
public class Staff {
    @Column(nullable = false)
    private String staffId;


    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;

    private String phoneNumber;
    
    private String email;

    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false)
    private Gender gender;
}
