package com.planetsystems.api.school.staffTransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Pagination {
    private int page;
    private int limit;
    private long total;
    private int totalPages;
}