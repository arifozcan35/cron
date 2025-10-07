package tr.cronjob.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HealthController {

    /**
     * Health check endpoint
     * This endpoint is used to keep the application awake by pinging from external services
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        String currentTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        
        log.info("Health check performed: {}", currentTime);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", currentTime);
        response.put("message", "Cron Job application is running");
        
        return response;
    }

    @GetMapping("/")
    public Map<String, String> root() {
        String currentTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", currentTime);
        response.put("message", "Spring Boot Cron Job Application");
        response.put("description", "This application sends requests to the target URL every 13 minutes");
        
        return response;
    }
}

