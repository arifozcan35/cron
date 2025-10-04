# Spring Boot Cron Job Uygulaması

Bu uygulama, belirtilen bir URL'ye her 13 dakikada bir otomatik HTTP isteği gönderen basit bir Spring Boot projesidir.

## 🎯 Amaç

Backend'de çalışan bir projeyi uyanık tutmak için her 13 dakikada bir ping atmak.

## ⚙️ Özellikler

- ✅ Her 13 dakikada bir otomatik istek gönderir
- ✅ Hedef URL kolayca değiştirilebilir
- ✅ Uygulama başladıktan 30 saniye sonra ilk test isteği atar
- ✅ Detaylı loglama ile her istek takip edilebilir
- ✅ Production ortamına deploy edilebilir

## 🚀 Kurulum ve Çalıştırma

### Yerel Ortamda Çalıştırma

```bash
# Maven ile çalıştırma
./mvnw spring-boot:run

# Veya Windows'ta
mvnw.cmd spring-boot:run
```

### JAR Dosyası Oluşturma

```bash
# Maven ile build
./mvnw clean package

# JAR dosyasını çalıştırma
java -jar target/cronjob-0.0.1-SNAPSHOT.jar
```

## 🔧 Konfigürasyon

Hedef URL'yi değiştirmek için `src/main/resources/application.properties` dosyasını düzenleyin:

```properties
# Hedef URL
cronjob.target.url=https://birdakikadinle.onrender.com

# Port (opsiyonel, default: 8080)
server.port=8080
```

### Environment Variable ile Değiştirme

Production ortamında environment variable kullanarak da değiştirebilirsiniz:

```bash
# Linux/Mac
export CRONJOB_TARGET_URL=https://yeni-url.com
export PORT=8080
java -jar cronjob-0.0.1-SNAPSHOT.jar

# Windows PowerShell
$env:CRONJOB_TARGET_URL="https://yeni-url.com"
$env:PORT="8080"
java -jar cronjob-0.0.1-SNAPSHOT.jar
```

## ☁️ Deploy (Render.com için)

1. GitHub'a projeyi pushlayın
2. Render.com'da yeni bir "Web Service" oluşturun
3. GitHub reponuzu bağlayın
4. Aşağıdaki ayarları yapın:
   - **Build Command:** `./mvnw clean package`
   - **Start Command:** `java -jar target/cronjob-0.0.1-SNAPSHOT.jar`
   - **Environment Variables:**
     - `CRONJOB_TARGET_URL` = `https://birdakikadinle.onrender.com`

## 📋 Cron Schedule Ayarı

Varsayılan olarak her 13 dakikada bir istek atar. Bunu değiştirmek için `ScheduledRequestService.java` dosyasındaki cron expression'ı değiştirebilirsiniz:

```java
@Scheduled(cron = "0 */13 * * * *")  // Her 13 dakikada bir
```

### Cron Expression Örnekleri:
- `0 */5 * * * *` - Her 5 dakikada bir
- `0 */10 * * * *` - Her 10 dakikada bir
- `0 */15 * * * *` - Her 15 dakikada bir
- `0 0 * * * *` - Her saat başı

## 📝 Loglar

Uygulama her istek attığında konsola log yazdırır:

```
======================================================
Cron Job başlatıldı: 04-10-2025 15:30:00
Hedef URL: https://birdakikadinle.onrender.com
İstek başarılı! Response: {...}
======================================================
```

## 🛠️ Teknolojiler

- Spring Boot 3.5.6
- Java 21
- Maven
- Lombok
- Spring Scheduling

## 📄 Lisans

Bu proje açık kaynak kodludur.

