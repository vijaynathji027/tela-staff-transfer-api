package com.planetsystems.api.school.staffTransfer.service;

import com.planetsystems.api.school.staffTransfer.dto.*;
import com.planetsystems.api.school.staffTransfer.entity.*;
import com.planetsystems.api.school.staffTransfer.entity.ennum.*;
import com.planetsystems.api.school.staffTransfer.exceptions.AlreadyExistsException;
import com.planetsystems.api.school.staffTransfer.exceptions.InvalidException;
import com.planetsystems.api.school.staffTransfer.exceptions.NotFoundException;
import com.planetsystems.api.school.staffTransfer.repository.TransferRepository;
import com.planetsystems.api.school.staffTransfer.util.TelaDatePattern;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements  TransferService {

    public final TransferRepository transferRepository;
    @Value("${tela.transfer.base.url}")
    public   String telaTransferBaseUrl;



    @Override
    @Transactional
    public ResponseDTO<List<TransferDTO>> searchTransfers(Map<String, String> queryMap) {
        System.out.println(queryMap);
        Optional<String> toSchoolIdOptional = Optional.ofNullable(queryMap.get("toSchoolId"));
        Optional<String> fromSchoolIdOptional = Optional.ofNullable(queryMap.get("fromSchoolId"));
        Optional<String> academicYearOptional = Optional.ofNullable(queryMap.get("academicYear"));
        Optional<String> academicTermNameOptional = Optional.ofNullable(queryMap.get("academicTermName"));
        Optional<String> academicTermIdOptional = Optional.ofNullable(queryMap.get("academicTermId"));

        Optional<String> toTelaNumberOptional = Optional.ofNullable(queryMap.get("toTelaNumber"));
        Optional<String> fromTelaNumberOptional = Optional.ofNullable(queryMap.get("fromTelaNumber"));

        Optional<String> codeOptional = Optional.ofNullable(queryMap.get("code"));
        Optional<String> requestStatusOptional = Optional.ofNullable(queryMap.get("requestStatus"));
        Optional<String> requestedByOptional = Optional.ofNullable(queryMap.get("requestedBy"));
        Optional<String> approvedByOptional = Optional.ofNullable(queryMap.get("approvedBy"));
        Optional<String> requestedDateOptional = Optional.ofNullable(queryMap.get("requestedDate"));
        Optional<String> approvedDateOptional = Optional.ofNullable(queryMap.get("approvedDate"));

        Optional<String> staffOptional = Optional.ofNullable(queryMap.get("staff"));

        QTransfer transferTable = QTransfer.transfer;

        BooleanExpression booleanExpression = null;
        if (toTelaNumberOptional.isPresent()) {
            booleanExpression = transferTable.toSchool.toTelaNumber.eq(toTelaNumberOptional.get());
        }

        if (fromSchoolIdOptional.isPresent()) {
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.fromSchool.fromSchoolId.eq(fromSchoolIdOptional.get()) :
                    booleanExpression.and(transferTable.fromSchool.fromSchoolId.eq(fromSchoolIdOptional.get()));
        }

        if (toSchoolIdOptional.isPresent()) {
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.toSchool.toSchoolId.eq(toSchoolIdOptional.get()) :
                    booleanExpression.and(transferTable.toSchool.toSchoolId.eq(toSchoolIdOptional.get()));
        }

        if (requestStatusOptional.isPresent()) {
            Optional<RequestStatus> requestStatus = RequestStatus.fromStr(requestStatusOptional.get());
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.requestStatus.eq(requestStatus.get()) :
                    booleanExpression.and(transferTable.requestStatus.eq(requestStatus.get()));
        }

        if (fromTelaNumberOptional.isPresent()) {
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.fromSchool.fromTelaNumber.eq(fromTelaNumberOptional.get()) :
                    booleanExpression.and(transferTable.fromSchool.fromTelaNumber.eq(fromTelaNumberOptional.get()));
        }

        if (toTelaNumberOptional.isPresent()) {
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.toSchool.toTelaNumber.eq(toTelaNumberOptional.get()) :
                    booleanExpression.and(transferTable.toSchool.toTelaNumber.eq(toTelaNumberOptional.get()));
        }

        if (academicYearOptional.isPresent()) {
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.academicYear.eq(academicYearOptional.get()) :
                    booleanExpression.and(transferTable.academicYear.eq(academicYearOptional.get()));
        }

        if (academicTermIdOptional.isPresent()) {
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.academicTermId.eq(academicTermIdOptional.get()) :
                    booleanExpression.and(transferTable.academicTermId.eq(academicTermIdOptional.get()));
        }

        if (academicTermNameOptional.isPresent()) {
            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.academicTermName.eq(academicTermNameOptional.get()) :
                    booleanExpression.and(transferTable.academicTermName.eq(academicTermNameOptional.get()));
        }

        if (requestedDateOptional.isPresent() && approvedDateOptional.isPresent()) {
            LocalDate requestedDate = LocalDate.parse(requestedDateOptional.get(), TelaDatePattern.datePattern);
            LocalDate approvedDate = LocalDate.parse(approvedDateOptional.get(), TelaDatePattern.datePattern);

            booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.requestedDate.between(requestedDate , approvedDate) :
                    booleanExpression.and(transferTable.requestedDate.between(requestedDate , approvedDate));
        }else{

            if (requestedDateOptional.isPresent()) {
                LocalDate requestedDate = LocalDate.parse(requestedDateOptional.get() , TelaDatePattern.datePattern);
                booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.requestedDate.eq(requestedDate) :
                        booleanExpression.and(transferTable.requestedDate.eq(requestedDate));
            }

            if (approvedDateOptional.isPresent()) {
                LocalDate approvedDate = LocalDate.parse(approvedDateOptional.get(), TelaDatePattern.datePattern);
                booleanExpression = Objects.isNull(booleanExpression) ?  transferTable.approvedDate.eq(approvedDate) :
                        booleanExpression.and(transferTable.approvedDate.eq(approvedDate));
            }

        }





        // TODO Auto-generated method stube
        List<TransferDTO> transferDTOS = (Objects.isNull(booleanExpression) ? transferRepository.findAll() : (List<Transfer>) transferRepository.findAll(booleanExpression))
                .parallelStream()
                .map(transfer -> {
                    TransferDTO transferDTO = TransferDTO.builder()
                            .id(transfer.getId())
                            .academicYear(transfer.getAcademicYear())
                            .academicTermName(transfer.getAcademicTermName())
                            .academicTermId(transfer.getAcademicTermId())
                            .comment(transfer.getComment())
                            .staff(StaffDTO.builder().id(transfer.getStaff().getStaffId()).email(transfer.getStaff().getEmail())
                                    .gender(transfer.getStaff().getGender().getGender())
                                    .firstName(transfer.getStaff().getFirstName()).lastName(transfer.getStaff().getLastName()).phoneNumber(transfer.getStaff().getPhoneNumber()).userType(transfer.getStaff().getUserType().getUserType()).build())
                            .transferCategory(transfer.getTransferCategory().getTransferCategory())
                            .requestStatus(transfer.getRequestStatus().getStatus())
                            .code(transfer.getCode())
                            .requestedBy(StaffDTO.builder().id(transfer.getRequestedBy().getRequestedById()).email(transfer.getRequestedBy().getRequestedByEmail()).phoneNumber(transfer.getRequestedBy()
                                    .getRequestedByPhoneNumber()).firstName(transfer.getRequestedBy().getRequestedByFirstName()).lastName(transfer.getRequestedBy().getRequestedByLastName())
                                    .gender(transfer.getRequestedBy().getRequestedByGender().getGender())
                                    .userType(transfer.getRequestedBy()
                                    .getRequestedByUserType().getUserType()).build())

                            .toSchool(SchoolDTO.builder().name(transfer.getToSchool().getToSchoolName())
                                    .telaNumber(transfer.getToSchool().getToTelaNumber())
                                    .schoolId(transfer.getToSchool().getToSchoolId())
                                    .schoolLevel(transfer.getToSchool().getToSchoolLevel().getSchoolLevel())
                                    .schoolOwnership(transfer.getToSchool().getToSchoolOwnership().getOwnership()).build())

                            .fromSchool(SchoolDTO.builder().name(transfer.getFromSchool().getFromSchoolName()).telaNumber(transfer.getFromSchool().getFromTelaNumber())
                                    .schoolOwnership(transfer.getFromSchool().getFromSchoolOwnership().getOwnership())
                                    .schoolLevel(transfer.getFromSchool().getFromSchoolLevel().getSchoolLevel()).build())
                            .requestedDate(transfer.getRequestedDate().format(TelaDatePattern.datePattern))
                            .requestedTime(transfer.getRequestedTime().format(TelaDatePattern.timePattern24))
                            .build();

                    if (!Objects.isNull(transfer.getApprovedBy())) {
                        transferDTO.setApprovedBy(StaffDTO.builder().id(transfer.getApprovedBy().getApprovedById()).email(transfer.getApprovedBy().getApprovedByEmail())
                                .phoneNumber(transfer.getApprovedBy().getApprovedByPhoneNumber()).firstName(transfer.getApprovedBy().getApprovedByFirstName())
                                .lastName(transfer.getApprovedBy().getApprovedByLastName())
                                .userType(transfer.getApprovedBy()
                                .getApprovedByUserType().getUserType()).build());
                        transferDTO.setApprovedTime(transfer.getApprovedTime().format(TelaDatePattern.timePattern24));
                        transferDTO.setApprovedDate(transfer.getApprovedDate().format(TelaDatePattern.datePattern));
                    }


                    return transferDTO;
                })
                .toList();

//        System.out.println("transferDTOS "+transferDTOS.size());

        return new ResponseDTO<>(transferDTOS);
    }

    @Override
    public ResponseDTO<TransferDTO> searchTransfer(Map<String, String> queryMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchTransfer'");
    }

    @Override
    @Transactional
    public ResponseDTO<String> createTransfer(NewTransferDTO dto) {
        boolean transferExists =  transferRepository.existsByRequestStatusAndStaff_StaffId(RequestStatus.PENDING , dto.getStaff().getId());

//                transferRepository.existsByRequestStatusAndFromSchool_FromTelaNumberAndStaff_FirstNameOrStaff_LastNameOrStaff_PhoneNumberOrStaff_Email(RequestStatus.PENDING,
//                dto.getFromSchool().getTelaNumber(), dto.getStaff().getFirstName(), dto.getStaff().getLastName(), dto.getStaff().getPhoneNumber(), dto.getStaff().getEmail()
//        );


        if (transferExists) {
            throw new AlreadyExistsException("staff has a pending request from " + dto.getFromSchool().getName());
        }

        System.out.println(dto);

        CompletableFuture<Transfer> saveTransferCompletableFuture = CompletableFuture.supplyAsync(() -> {
            String requestCode = generateRequestCode();
            while (transferRepository.existsByCode(requestCode)) {
                requestCode = generateRequestCode();
            }
            return requestCode;
        }).thenApply(requestCode -> {
            Transfer transfer = Transfer.builder()
                    .academicYear(dto.getAcademicYear())
                    .academicTermName(dto.getAcademicTermName())
                    .academicTermId(dto.getAcademicTermId())
                    .staff(Staff.builder()
                            .staffId(dto.getStaff().getId())
                            .gender(Gender.fromStr(dto.getStaff().getGender()).get())
                            .email(dto.getStaff().getEmail()).firstName(dto.getStaff().getFirstName()).lastName(dto.getStaff().getLastName()).phoneNumber(dto.getStaff().getPhoneNumber())
                            .userType(UserType.fromStr(dto.getStaff().getUserType()).get()).build())
                    .requestedBy(RequestedBy.builder().requestedById(dto.getRequestedBy().getId()).requestedByEmail(dto.getRequestedBy().getEmail())
                            .requestedByGender(Gender.fromStr(dto.getRequestedBy().getGender()).get())
                            .requestedByFirstName(dto.getRequestedBy().getFirstName()).requestedByLastName(dto.getRequestedBy().getLastName()).requestedByPhoneNumber(dto.getRequestedBy().getPhoneNumber())
                            .requestedByUserType(UserType.fromStr(dto.getRequestedBy().getUserType()).get()).build())
                    .requestStatus(RequestStatus.PENDING)
                    .requestedTime(LocalTime.now())
                    .requestedDate(LocalDate.now())
                    .toSchool(ToSchool.builder()
                            .toTelaNumber(dto.getToSchool().getTelaNumber())
                            .toSchoolId(dto.getToSchool().getSchoolId())
                            .toSchoolOwnership(SchoolOwnership.fromStr(dto.getToSchool().getSchoolOwnership()).get())
                            .toSchoolLevel(SchoolLevel.fromStr(dto.getToSchool().getSchoolLevel()).get())
                            .toSchoolName(dto.getToSchool().getName()).toTelaNumber(dto.getToSchool().getTelaNumber()).build())

                    .fromSchool(FromSchool.builder()
                            .fromSchoolId(dto.getFromSchool().getSchoolId())
                            .fromSchoolOwnership(SchoolOwnership.fromStr(dto.getFromSchool().getSchoolOwnership()).get())
                            .fromSchoolLevel(SchoolLevel.fromStr(dto.getFromSchool().getSchoolLevel()).get())
                            .fromSchoolName(dto.getFromSchool().getName())
                            .fromTelaNumber(dto.getFromSchool().getTelaNumber()).build())
                    .code(requestCode)
                    .transferCategory(TransferCategory.fromStr(dto.getTransferCategory()).get())
                    .build();
            System.out.println(transfer);

            return transferRepository.save(transfer);
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });


        Transfer save = saveTransferCompletableFuture.join();
        if (Objects.isNull(save)){
            throw new InvalidException("Something wrong happened !!");
        }else{
            return new ResponseDTO<>(save.getId());
        }
    }

    @Override
    public String generateRequestCode(){
        int max = 100000;
        int min = 1000;
        int randomNum = new Random().nextInt(max - min + 1) + min;
        return "ST-"+randomNum;
    }

    @Override
    @Transactional
    public ResponseDTO<String> approveTransfer(ApproveTransferDTO approveTransfer) {
        Transfer transfer = transferRepository.findById(approveTransfer.getId()).orElseThrow(() -> new NotFoundException("transfer not found"));

        if (Objects.isNull(transfer.getRequestedBy())) {
            throw new InvalidException("Transfer missing requested by information");
        }

        if (transfer.getRequestStatus().equals(RequestStatus.REJECTED)) {
            throw new InvalidException("Transfer was rejected already by "+transfer.getApprovedBy().getApprovedByEmail());
        }

        if (!transfer.getRequestStatus().equals(RequestStatus.PENDING)) {
            throw new InvalidException("Transfer is not pending");
        }

        transfer.setApprovedBy(ApprovedBy.builder()
                        .approvedById(approveTransfer.getApprovedBy().getId())
                        .approvedByEmail(approveTransfer.getApprovedBy().getEmail())
                        .approvedByFirstName(approveTransfer.getApprovedBy().getFirstName())
                        .approvedByLastName(approveTransfer.getApprovedBy().getLastName())
                        .approvedByUserType(UserType.ADMIN)
                        .approvedByGender(Gender.fromStr(approveTransfer.getApprovedBy().getGender()).get())
                .build());
        transfer.setRequestStatus(RequestStatus.APPROVED);
        transfer.setApprovedTime(LocalTime.now());
        transfer.setApprovedDate(LocalDate.now());
        transfer.setComment(approveTransfer.getComment());

        CompletableFuture<String> approveTransferFuture = CompletableFuture.supplyAsync(() -> transferRepository.save(transfer))
                .thenApply(savedTransfer -> {
                    String url = "/tela/webapi/attendance/school/"+transfer.getToSchool().getToSchoolId()+"/transfer";
                    ResponseDTO responseDTO = RestClient.create(telaTransferBaseUrl).put().uri(url)
                            .contentType(APPLICATION_JSON)
                            .body(transfer.getStaff().getStaffId())
                            .retrieve()
                            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                                System.out.println("APPROVAL");
                                System.out.println(response);
                                throw new InvalidException(response.getStatusCode().toString());
                            })
                            .body(ResponseDTO.class);

                    if (!responseDTO.isStatus()) {
                        throw new InvalidException("transfer from tela failed");
                    }
                    return savedTransfer.getId();
                }).exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });

        String join = approveTransferFuture.join();
        if (Objects.isNull(join)) {
            throw new InvalidException("Something wrong happened !!");
        }else {
            String message = "successfully approved " + transfer.getStaff().getLastName()+" "+transfer.getStaff().getFirstName()+" transfer from "
                    +transfer.getFromSchool().getFromSchoolName()+" to "+transfer.getToSchool().getToSchoolName();
            return new ResponseDTO<>(transfer.getId() , message);
        }

    }

    @Override
    public ResponseDTO<String> rejectTransfer(ApproveTransferDTO approveTransfer) {
        Transfer transfer = transferRepository.findById(approveTransfer.getId()).orElseThrow(() -> new NotFoundException("transfer not found"));

        if (Objects.isNull(transfer.getRequestedBy())) {
            throw new InvalidException("Transfer missing requested by information");
        }

        if (transfer.getRequestStatus().equals(RequestStatus.APPROVED)) {
            throw new InvalidException("Transfer is approved, cannot be rejected");
        }


        transfer.setApprovedBy(ApprovedBy.builder()
                .approvedById(approveTransfer.getApprovedBy().getId())
                .approvedByEmail(approveTransfer.getApprovedBy().getEmail())
                .approvedByFirstName(approveTransfer.getApprovedBy().getFirstName())
                .approvedByLastName(approveTransfer.getApprovedBy().getLastName())
                .approvedByUserType(UserType.ADMIN)
                .build());
        transfer.setRequestStatus(RequestStatus.REJECTED);
        transfer.setApprovedTime(LocalTime.now());
        transfer.setApprovedDate(LocalDate.now());

        transferRepository.save(transfer);
        String message = "successfully rejected " + transfer.getStaff().getLastName()+" "+transfer.getStaff().getFirstName()+" transfer from "
                +transfer.getFromSchool().getFromSchoolName()+" to "+transfer.getToSchool().getToSchoolName();
        return new ResponseDTO<>(transfer.getId() , message);
    }

}
