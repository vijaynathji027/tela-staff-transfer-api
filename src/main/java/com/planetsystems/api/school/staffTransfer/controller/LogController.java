package com.planetsystems.api.school.staffTransfer.controller;

import com.planetsystems.api.school.staffTransfer.dto.SystemResponseDTO;
import com.planetsystems.api.school.staffTransfer.service.LogService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public SystemResponseDTO getAllLogs(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String ip,
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer statusCode
    ) throws BadRequestException {
        return logService.getFilteredLogs(date, ip, method, username, statusCode);
    }
}
