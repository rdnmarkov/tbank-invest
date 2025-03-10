package com.example.tbank_invest.config;

import com.example.tbank_invest.controller.TbankBotApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TbankBotApi tbankBotApi) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot((LongPollingBot) tbankBotApi);
        }
        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }

        return telegramBotsApi;
    }


}
