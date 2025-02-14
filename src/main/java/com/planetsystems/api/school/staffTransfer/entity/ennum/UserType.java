package com.planetsystems.api.school.staffTransfer.entity.ennum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;



@ToString
@Getter
@AllArgsConstructor
public enum UserType {
  TEACHER("Teacher"),HEADTEACHER("Head teacher"),DEPUTY_HEADTEACHER("Deputy Head Teacher"),ADMIN("admin");

 String userType;


    public static Optional<UserType> fromStr(String status){
  return Arrays.asList(UserType.values()).parallelStream().filter(request -> request.getUserType().equalsIgnoreCase(status)).findAny();
 }
}
