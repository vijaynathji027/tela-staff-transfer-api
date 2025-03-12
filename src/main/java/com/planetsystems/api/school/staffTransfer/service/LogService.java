package com.planetsystems.api.school.staffTransfer.service;

import com.planetsystems.api.school.staffTransfer.dto.SystemResponseDTO;
import com.planetsystems.api.school.staffTransfer.repository.LogRepository;
import com.planetsystems.api.school.staffTransfer.util.AppMessages;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public SystemResponseDTO getAllLogs() {
        return SystemResponseDTO.createResponse(logRepository.readAllLogs(), AppMessages.LOG_FETCHED_SUCCESSFULLY);
    }

    private List<Map<String, Object>> filterLogs(String key, Object value) {
        return logRepository.readAllLogs().stream()
                .filter(log -> log.get(key) != null && log.get(key).toString().equals(value.toString()))
                .collect(Collectors.toList());
    }

    public SystemResponseDTO getFilteredLogs(String date, String ip, String method, String username, Integer statusCode) throws BadRequestException {
        if (date != null) {
            try {
                LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
                if (parsedDate.isAfter(LocalDate.now())) {
                    throw new BadRequestException(AppMessages.DATE_CANNOT_BE_A_FUTURE_DATE);
                }
            } catch (DateTimeParseException e) {
                throw new BadRequestException(AppMessages.INVALID_DATE_FORMAT_FOR_LOG);
            } catch (BadRequestException e) {
                throw  e;
            }
        }

        List<Map<String, Object>> logs = logRepository.filterLogs(date, ip, method, username, statusCode);
        return SystemResponseDTO.createResponse(logs, AppMessages.LOG_FETCHED_SUCCESSFULLY);
    }
}
