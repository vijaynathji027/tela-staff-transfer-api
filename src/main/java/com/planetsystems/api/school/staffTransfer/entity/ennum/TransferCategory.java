package com.planetsystems.api.school.staffTransfer.entity.ennum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@ToString
public enum TransferCategory {
  LOCAL_GOVERNMENT("Local government"),CENTRAL_GOVERNMENT("Central Government"),EMERGENCY_TRANSFER("Emergency Transfer");

 String transferCategory;

    public static Optional<TransferCategory> fromStr(String transferCategory){
  return Arrays.asList(TransferCategory.values()).parallelStream().filter(transferCat-> transferCat.getTransferCategory().equalsIgnoreCase(transferCategory)).findAny();
 }

}
