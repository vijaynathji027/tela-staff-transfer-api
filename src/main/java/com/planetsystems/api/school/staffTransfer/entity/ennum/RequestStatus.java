package com.planetsystems.api.school.staffTransfer.entity.ennum;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum RequestStatus {
  PENDING("pending"),APPROVED("approved"),REJECTED("rejected");

 String status;

    public static Optional<RequestStatus> fromStr(String status){
  return Arrays.asList(RequestStatus.values()).parallelStream().filter(request -> request.getStatus().equalsIgnoreCase(status)).findAny();
 }

}
