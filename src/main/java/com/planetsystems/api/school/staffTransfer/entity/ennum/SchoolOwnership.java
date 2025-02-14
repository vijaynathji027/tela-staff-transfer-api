package com.planetsystems.api.school.staffTransfer.entity.ennum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@ToString
public enum SchoolOwnership {
  GOV("government"),PRIVATE("private");

 String ownership;

    public static Optional<SchoolOwnership> fromStr(String ownership){
  return Arrays.asList(SchoolOwnership.values()).parallelStream().filter(o -> o.getOwnership().equalsIgnoreCase(ownership)).findAny();
 }

}
