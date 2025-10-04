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

    // Her 13 dakikada bir hedef URL'ye istek atar
    // Cron expression: 0 */13 * * * * -> Her 13 dakikada bir (dakikanin 0. saniyesinde)
    @Scheduled(cron = "0 */13 * * * *")
    public void sendScheduledRequest() {
        String currentTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        
        log.info("======================================================");
        log.info("Cron Job başlatıldı: {}", currentTime);
        log.info("Hedef URL: {}", targetUrl);
        
        try {
            String response = restTemplate.getForObject(targetUrl, String.class);
            log.info("✅ İstek başarılı! Response: {}", 
                    response != null && response.length() > 100 
                    ? response.substring(0, 100) + "..." 
                    : response);
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            // 404 hatası - Endpoint yok ama sunucu ulaşılabilir durumda
            log.info("✅ Sunucuya ulaşıldı! (Endpoint bulunamadı ama sorun değil, sunucu uyanık)");
            log.debug("404 Detay: {}", e.getMessage());
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            // Diğer 4xx hataları - Sunucu ulaşılabilir
            log.info("✅ Sunucuya ulaşıldı! (HTTP {}: {})", e.getStatusCode(), e.getStatusText());
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            // 5xx hataları - Sunucu ulaşılabilir ama hata veriyor
            log.warn("⚠️ Sunucuya ulaşıldı ama sunucu hatası! (HTTP {}: {})", e.getStatusCode(), e.getStatusText());
        } catch (Exception e) {
            // Sunucuya hiç ulaşılamadı veya timeout
            log.error("❌ İstek başarısız! Sunucuya ulaşılamadı: {}", e.getMessage());
        }
        
        log.info("======================================================");
    }

    // Uygulama basladiktan 30 saniye sonra ilk istegi atar
    // Boylece uygulama ayaga kalktiginda hemen calistigini gorebilirsiniz
    @Scheduled(initialDelay = 30000, fixedDelay = Long.MAX_VALUE)
    public void sendInitialRequest() {
        log.info("İlk test isteği gönderiliyor...");
        sendScheduledRequest();
    }
}

