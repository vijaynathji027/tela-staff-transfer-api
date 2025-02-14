package com.planetsystems.api.school.staffTransfer.controller;

import com.planetsystems.api.school.staffTransfer.dto.*;
import com.planetsystems.api.school.staffTransfer.entity.ennum.*;
import com.planetsystems.api.school.staffTransfer.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
@CrossOrigin
public class TransferController {
    final TransferService transferService;


    @PostMapping
    public ResponseDTO<String> createTransfer(@RequestBody @Valid NewTransferDTO transferDTO) {
        return transferService.createTransfer(transferDTO);
    }

    @PostMapping("/approve")
    public ResponseDTO<String> approveTransfer(@RequestBody @Valid ApproveTransferDTO approveTransfer) {
        return transferService.approveTransfer(approveTransfer);
    }

    @PostMapping("/reject")
    public ResponseDTO<String> rejectTransfer(@RequestBody @Valid ApproveTransferDTO approveTransfer) {
        return transferService.rejectTransfer(approveTransfer);
    }

    @GetMapping
    public ResponseDTO<List<TransferDTO>> searchTransfer(@RequestParam Map<String , String> queryMap) {
        return transferService.searchTransfers(queryMap);
    }

    @GetMapping("/configs")
    public ResponseDTO<TransferConfigDTO> transferConfigs() {
        TransferConfigDTO configDTO = TransferConfigDTO.builder()
                .requestStatuses(Arrays.asList(RequestStatus.values()).stream().map(RequestStatus::getStatus).toList())
                .transferCategories(Arrays.asList(TransferCategory.values()).stream().map(TransferCategory::getTransferCategory).toList())
                .genders(Arrays.asList(Gender.values()).stream().map(Gender::getGender).toList())
                .schoolOwnerships(Arrays.asList(SchoolOwnership.values()).stream().map(SchoolOwnership::getOwnership).toList())
                .schoolLevels(Arrays.asList(SchoolLevel.values()).stream().map(SchoolLevel::getSchoolLevel).toList())
                .userTypes(Arrays.stream(UserType.values()).map(UserType::getUserType).toList())
                .build();
        return new ResponseDTO<>(configDTO);
    }


}
