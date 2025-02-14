package com.planetsystems.api.school.staffTransfer.entity.ennum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@ToString
public enum SchoolLevel {
  PRIMARY("primary"),SECONDARY("secondary");

 String schoolLevel;

    public static Optional<SchoolLevel> fromStr(String level){
  return Arrays.asList(SchoolLevel.values()).parallelStream().filter(l -> l.getSchoolLevel().equalsIgnoreCase(level)).findAny();
 }

}
