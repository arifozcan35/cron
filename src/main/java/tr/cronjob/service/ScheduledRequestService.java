package tr.cronjob.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ScheduledRequestService {

    private final RestTemplate restTemplate;

    @Value("${cronjob.target.url}")
    private String targetUrl;

    public ScheduledRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Every 13 minutes the target URL is requested
    // Cron expression: 0 */13 * * * * -> Every 13 minutes (at the 0 second of the minute)
    @Scheduled(cron = "0 */13 * * * *")
    public void sendScheduledRequest() {
        String currentTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        
        log.info("======================================================");
        log.info("Cron Job started: {}", currentTime);
        log.info("Target URL: {}", targetUrl);
        
        try {
            String response = restTemplate.getForObject(targetUrl, String.class);
            log.info("✅ Request successful! Response: {}", 
                    response != null && response.length() > 100 
                    ? response.substring(0, 100) + "..." 
                    : response);
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            log.info("✅ Server reached! (Endpoint not found but server is reachable)");
            log.debug("404 Detail: {}", e.getMessage());
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            log.info("✅ Server reached! (HTTP {}: {})", e.getStatusCode(), e.getStatusText());
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            log.warn("⚠️ Server reached but server error! (HTTP {}: {})", e.getStatusCode(), e.getStatusText());
        } catch (Exception e) {
            log.error("❌ Request failed! Server not reachable: {}", e.getMessage());
        }
        
        log.info("======================================================");
    }

    // After the application starts, the first request is sent
    // So you can see that the application is running immediately
    @Scheduled(initialDelay = 30000, fixedDelay = Long.MAX_VALUE)
    public void sendInitialRequest() {
        log.info("First test request is being sent...");
        sendScheduledRequest();
    }
}

