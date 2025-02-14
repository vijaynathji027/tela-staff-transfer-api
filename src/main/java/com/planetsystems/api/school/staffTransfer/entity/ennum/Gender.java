package com.planetsystems.api.school.staffTransfer.entity.ennum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@ToString
public enum Gender {
  MALE("male"),FEMALE("female");

 String gender;

    public static Optional<Gender> fromStr(String gender){
  return Arrays.asList(Gender.values()).parallelStream().filter(g -> g.getGender().equalsIgnoreCase(gender)).findAny();
 }

}
