package com.example.tbank_invest.controller;


import com.example.tbank_invest.config.properties.BotProperties;
import com.example.tbank_invest.service.CalculatorBondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TbankBotApi extends TelegramLongPollingBot {

    private final BotProperties properties;
    private final CalculatorBondService calculatorBondService;

    @Override
    public String getBotUsername() {
        return properties.getName();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage()){

            Long chatId = update.getMessage().getChatId();

            SendMessage sendMessage = new SendMessage(String.valueOf(chatId), String.valueOf(calculatorBondService.yearlyYieldBond(update.getMessage().getText())));

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
