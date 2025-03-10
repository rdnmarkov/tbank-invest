package com.example.tbank_invest.config;

import com.example.tbank_invest.config.properties.TbankProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

@Configuration
public class TbankConfig {

    @Bean
    public InvestApi investApi(@Autowired TbankProperties tbankProperties){
        return InvestApi.create(tbankProperties.getToken());
    }
}
