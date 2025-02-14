package com.planetsystems.api.school.staffTransfer.service;


import com.planetsystems.api.school.staffTransfer.dto.ApproveTransferDTO;
import com.planetsystems.api.school.staffTransfer.dto.NewTransferDTO;
import com.planetsystems.api.school.staffTransfer.dto.ResponseDTO;
import com.planetsystems.api.school.staffTransfer.dto.TransferDTO;

import java.util.List;
import java.util.Map;

public interface TransferService {
  
    ResponseDTO<List<TransferDTO>> searchTransfers(Map<String , String> queryMap);
    ResponseDTO<TransferDTO> searchTransfer(Map<String , String> queryMap);
    ResponseDTO<String> createTransfer(NewTransferDTO data);
    String generateRequestCode();

    ResponseDTO<String> approveTransfer(ApproveTransferDTO approveTransfer);

    ResponseDTO<String> rejectTransfer(ApproveTransferDTO approveTransfer);
}
