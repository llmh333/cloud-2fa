package org.example.cloud2fa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
@Slf4j
public class Cloud2faApplication {

    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(Cloud2faApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");

        String baseUrl = "http://localhost:" + port + contextPath;

        log.info("");
        log.info("╔════════════════════════════════════════════════════════════════╗");
        log.info("║                      APPLICATION STARTED                       ║");
        log.info("╠════════════════════════════════════════════════════════════════╣");
        log.info("║    Swagger UI:     {}{}", padRight(baseUrl + "/swagger-ui.html", 35), "║");
        log.info("║    API Docs:       {}{}", padRight(baseUrl + "/v3/api-docs", 35), "║");
        log.info("╚════════════════════════════════════════════════════════════════╝");
        log.info("");
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
