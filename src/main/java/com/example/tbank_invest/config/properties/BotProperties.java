package com.example.tbank_invest.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("bot")
public class BotProperties {
    private String name;
    private String token;
}
