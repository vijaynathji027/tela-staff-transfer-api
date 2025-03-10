package com.planetsystems.api.school.staffTransfer.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class LogRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String LOG_DIRECTORY = "logs/";


    public List<Map<String, Object>> filterLogs(String date, String ip, String method, String username, Integer statusCode) {
        return readAllLogs().stream()
                .filter(log -> date == null || (log.get("timestamp") != null && log.get("timestamp").toString().startsWith(date)))
                .filter(log -> ip == null || (log.get("ip") != null && log.get("ip").toString().equals(ip)))
                .filter(log -> method == null || (log.get("method") != null && log.get("method").toString().equalsIgnoreCase(method)))
                .filter(log -> username == null || (log.get("username") != null && log.get("username").toString().equalsIgnoreCase(username)))
                .filter(log -> statusCode == null || (log.get("statusCode") != null && log.get("statusCode").toString().equals(statusCode.toString())))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> readAllLogs() {
        File[] logFiles = new File(LOG_DIRECTORY).listFiles((dir, name) -> name.endsWith(".json"));
        
        List<Map<String, Object>> allLogs = new ArrayList<>();

        for (File file : logFiles) {
            

            allLogs.addAll(readLogsFromFile(file));
        }

        // Sort logs by timestamp in descending order, handling nulls
        allLogs.sort((log1, log2) -> {
            String timestamp1 = (String) log1.get("timestamp");
            String timestamp2 = (String) log2.get("timestamp");

            if (timestamp1 == null && timestamp2 == null) return 0; // Both are null
            if (timestamp1 == null) return 1; // Null comes last
            if (timestamp2 == null) return -1; // Null comes last

            return timestamp2.compareTo(timestamp1); // Descending order
        });

        return allLogs;
    }

    public List<Map<String, Object>> readLogsByDate(String date) {
        File logFile = new File(LOG_DIRECTORY + "application.json");

        // File logFile = new File(LOG_DIRECTORY + "application-" + date + ".json");
        
        return logFile.exists() ? readLogsFromFile(logFile) : new ArrayList<>();
    }

    public List<Map<String, Object>> readLogsFromFile(File file) {
        List<Map<String, Object>> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Map<String, Object> logEntry = objectMapper.readValue(line, Map.class);
                // Extract additional information from the message
                extractAdditionalInfo(logEntry);
                logs.add(logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }

    private void extractAdditionalInfo(Map<String, Object> logEntry) {
        String message = (String) logEntry.get("message");
        if (message != null) {
            try {
                JsonNode jsonMessage = objectMapper.readTree(message);
                if (jsonMessage.has("method")) {
                    logEntry.put("method", jsonMessage.get("method").asText());
                }
                if (jsonMessage.has("resource")) {
                    logEntry.put("resource", jsonMessage.get("resource").asText());
                }
                if (jsonMessage.has("queryString")) {
                    logEntry.put("queryString", jsonMessage.get("queryString").asText());
                }
                if (jsonMessage.has("requestBody")) {
                    String requestBody = jsonMessage.get("requestBody").asText();
                    try {
                        JsonNode parsedRequestBody = objectMapper.readTree(requestBody);
                        logEntry.put("request", parsedRequestBody);
                    } catch (IOException e) {
                        logEntry.put("request", requestBody); // Keep as string if parsing fails
                    }
                }
                if (jsonMessage.has("response")) {
                    String response = jsonMessage.get("response").asText();
                    try {
                        JsonNode parsedResponse = objectMapper.readTree(response);
                        logEntry.put("response", parsedResponse);
                    } catch (IOException e) {
                        logEntry.put("response", response); // Keep as string if parsing fails
                    }
                }
                if (jsonMessage.has("clientIp")) {
                    logEntry.put("ip", jsonMessage.get("clientIp").asText());
                }
                if (jsonMessage.has("userAgent")) {
                    logEntry.put("userAgent", jsonMessage.get("userAgent").asText());
                }
                if (jsonMessage.has("responseTime")) {
                    logEntry.put("responseTime", jsonMessage.get("responseTime").asText());
                }
                if (jsonMessage.has("username")) {
                    logEntry.put("username", jsonMessage.get("username").asText());
                }
                if (jsonMessage.has("statusCode")) {
                    logEntry.put("statusCode", jsonMessage.get("statusCode").asInt());
                }
                if (jsonMessage.has("timestamp")) {
                    logEntry.put("timestamp", jsonMessage.get("timestamp").asText());
                }
                // Remove the original message field
                logEntry.remove("message");
            } catch (IOException e) {
                // Handle the case where message is not JSON
                // Use regex patterns as before
                Pattern resourcePattern = Pattern.compile("endpoint: ([^ ]+)");
                Pattern ipPattern = Pattern.compile("clientIp\":\"([^\"]+)\"");
                Pattern requestPattern = Pattern.compile("requestBody\":\"([^\"]+)\"");
                Pattern responsePattern = Pattern.compile("response\":\"([^\"]+)\"");
                Pattern methodPattern = Pattern.compile("method\":\"([^\"]+)\"");
                Pattern statusCodePattern = Pattern.compile("statusCode\":(\\d+)");
                Pattern userAgentPattern = Pattern.compile("userAgent\":\"([^\"]+)\"");
                Pattern responseTimePattern = Pattern.compile("responseTime\":\"([^\"]+)\"");
                Pattern timestampPattern = Pattern.compile("timestamp\":\"([^\"]+)\"");

                Matcher resourceMatcher = resourcePattern.matcher(message);
                Matcher ipMatcher = ipPattern.matcher(message);
                Matcher requestMatcher = requestPattern.matcher(message);
                Matcher responseMatcher = responsePattern.matcher(message);
                Matcher methodMatcher = methodPattern.matcher(message);
                Matcher statusCodeMatcher = statusCodePattern.matcher(message);
                Matcher userAgentMatcher = userAgentPattern.matcher(message);
                Matcher responseTimeMatcher = responseTimePattern.matcher(message);
                Matcher timestampMatcher = timestampPattern.matcher(message);

                if (resourceMatcher.find()) {
                    logEntry.put("resource", resourceMatcher.group(1));
                }
                if (ipMatcher.find()) {
                    logEntry.put("ip", ipMatcher.group(1));
                }
                if (requestMatcher.find()) {
                    logEntry.put("request", requestMatcher.group(1));
                }
                if (responseMatcher.find()) {
                    logEntry.put("response", responseMatcher.group(1));
                }
                if (methodMatcher.find()) {
                    logEntry.put("method", methodMatcher.group(1));
                }
                if (statusCodeMatcher.find()) {
                    logEntry.put("statusCode", Integer.parseInt(statusCodeMatcher.group(1)));
                }
                if (userAgentMatcher.find()) {
                    logEntry.put("userAgent", userAgentMatcher.group(1));
                }
                if (responseTimeMatcher.find()) {
                    logEntry.put("responseTime", responseTimeMatcher.group(1));
                }
                if (timestampMatcher.find()) {
                    logEntry.put("timestamp", timestampMatcher.group(1));
                }
                // Remove the original message field
                logEntry.remove("message");
            }
        }
    }
}
