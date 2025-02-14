package com.planetsystems.api.school.staffTransfer.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferConfigDTO {

    private List<String> genders = new ArrayList<>();
    private List<String> schoolOwnerships = new ArrayList<>();
    private List<String> schoolLevels = new ArrayList<>();
    private List<String> userTypes = new ArrayList<>();
    private List<String> requestStatuses = new ArrayList<>();
    private List<String> transferCategories = new ArrayList<>();




}
