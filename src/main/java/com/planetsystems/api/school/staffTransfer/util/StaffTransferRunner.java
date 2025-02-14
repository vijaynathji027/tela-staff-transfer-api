package com.planetsystems.api.school.staffTransfer.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaffTransferRunner implements CommandLineRunner {


//    final TransferRepository transferRepository;
//    final TransferService transferService;
//    final ObjectMapper objectMapper;


    @Override
    public void run(String... args) throws Exception {

//        NewTransferDTO dto = NewTransferDTO.builder()
//                .academicYear("wergthtrew")
//                .academicTermId("sdfgrew")
//                .academicTermName("term I")
//                .fromSchool(SchoolDTO.builder()
//                        .schoolId("wqert32ewrt4w32")
//                        .name("from school ps").telaNumber("800020202").schoolLevel(SchoolLevel.PRIMARY.getSchoolLevel()).schoolOwnership(SchoolOwnership.GOV.getOwnership()).build())
//
//                .toSchool(SchoolDTO.builder()
//                        .schoolId("wqerew232")
//                        .name("to school ps").telaNumber("800023456540202").schoolOwnership(SchoolOwnership.GOV.getOwnership()).schoolLevel(SchoolLevel.SECONDARY.getSchoolLevel()).build())
//
//                .requestedBy(StaffDTO.builder().email("h@gmail.com").firstName("h f").lastName("h last").phoneNumber("976543").userType(UserType.HEADTEACHER.getUserType()).build())
//                .staff(StaffDTO.builder().email("s@gmail.com").firstName("s f").lastName("s last").phoneNumber("95435676543").userType(UserType.TEACHER.getUserType()).build())
////                .requestStatus(RequestStatus.PENDING.getStatus())
//                .build();
////
//        System.out.println(dto);
//
//        System.out.println(objectMapper.writeValueAsString(dto));
//
////
////        System.out.println("hello world");
//        boolean transferExits = transferRepository.existsByRequestStatusAndFromSchool_FromTelaNumberAndStaff_FirstNameOrStaff_LastNameOrStaff_PhoneNumberOrStaff_Email
//                (RequestStatus.PENDING, dto.getFromSchool().getTelaNumber(),
//                        dto.getStaff().getFirstName(),
//                        dto.getStaff().getLastName(),
//                        dto.getStaff().getPhoneNumber(), dto.getStaff().getEmail());
//
//        System.out.println("transferExits "+ transferExits);
//
//        if (!transferExits){
//            Transfer transfer = Transfer.builder()
//                    .academicTermId(dto.getAcademicTermId())
//                    .academicTermName(dto.getAcademicTermName())
//                    .academicYear(dto.getAcademicYear())
//                    .staff(Staff.builder().staffId("wqetra342").email(dto.getStaff().getEmail())
//                            .gender(Gender.MALE)
//                            .firstName(dto.getStaff().getFirstName()).lastName(dto.getStaff().getLastName()).phoneNumber(dto.getStaff().getPhoneNumber()).userType(UserType.fromStr(dto.getStaff().getUserType()).get()).build())
//                    .requestedBy(RequestedBy.builder().requestedById("2345tygfd").requestedByEmail(dto.getRequestedBy().getEmail())
//                            .requestedByGender(Gender.MALE)
//                            .requestedByFirstName(dto.getRequestedBy().getFirstName()).requestedByLastName(dto.getRequestedBy().getLastName()).requestedByPhoneNumber(dto.getRequestedBy().getPhoneNumber())
//                            .requestedByUserType(UserType.fromStr(dto.getRequestedBy().getUserType()).get()).build())
//                    .requestStatus(RequestStatus.PENDING)
//                    .toSchool(ToSchool.builder()
//                            .toSchoolId(dto.getToSchool().getSchoolId())
//                            .toSchoolName(dto.getToSchool().getName()).toTelaNumber(dto.getToSchool().getTelaNumber())
//                            .toSchoolLevel(SchoolLevel.fromStr(dto.getToSchool().getSchoolLevel()).get())
//                            .toSchoolOwnership(SchoolOwnership.fromStr(dto.getToSchool().getSchoolOwnership()).get()).build())
//
//                    .fromSchool(FromSchool.builder()
//                            .fromSchoolId(dto.getFromSchool().getSchoolId())
//                            .fromSchoolName(dto.getFromSchool().getName()).fromTelaNumber(dto.getFromSchool().getTelaNumber())
//                            .fromSchoolOwnership(SchoolOwnership.fromStr(dto.getFromSchool().getSchoolOwnership()).get())
//                            .fromSchoolLevel(SchoolLevel.fromStr(dto.getFromSchool().getSchoolLevel()).get()).build())
//                    .code(transferService.generateRequestCode())
//                    .transferCategory(TransferCategory.LOCAL_GOVERNMENT)
//                    .requestedDate(LocalDate.now())
//                    .requestedTime(LocalTime.now())
//                    .build();
//
//
//
//            System.out.println("saved  "+transferRepository.save(transfer));
//
//        }
//
//        QTransfer qTransfer = QTransfer.transfer;
//        BooleanExpression booleanExpression = qTransfer.fromSchool.fromTelaNumber.equalsIgnoreCase(dto.getFromSchool().getTelaNumber());
//        booleanExpression =  booleanExpression.and(qTransfer.staff.email.eq(dto.getStaff().getEmail()+"j"));
//
//
//        List<Transfer> allTransfers = (List<Transfer>) transferRepository.findAll(booleanExpression);
//
//        System.out.println(allTransfers);

    }

//    private String generateRequestCode(){
//        int max = 100000;
//        int min = 1000;
//        int randomNum = new Random().nextInt(max - min + 1) + min;
//        return "ST-"+randomNum;
//    }

}
