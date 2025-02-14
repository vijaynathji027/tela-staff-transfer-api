package com.planetsystems.api.school.staffTransfer.repository;


import com.planetsystems.api.school.staffTransfer.entity.Transfer;
import com.planetsystems.api.school.staffTransfer.entity.ennum.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, String>, QuerydslPredicateExecutor<Transfer> {

     boolean existsByRequestStatusAndFromSchool_FromTelaNumberAndStaff_FirstNameOrStaff_LastNameOrStaff_PhoneNumberOrStaff_Email
            (RequestStatus requestStatus , String telaNo , String firstName , String lastName , String phoneNumber , String email);

     boolean existsByRequestStatusAndStaff_StaffId(RequestStatus requestStatus , String staffId);

     List<Transfer> findAllByRequestStatusAndFromSchool_FromTelaNumberAndStaff_FirstNameAndStaff_LastNameAndStaff_PhoneNumberAndStaff_Email
             (RequestStatus requestStatus , String telaNo , String firstName , String lastName , String phoneNumber , String email);

     boolean existsByCode(String code);
}
