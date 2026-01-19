package org.example.cloud2fa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Cloud2faApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cloud2faApplication.class, args);
    }

}
