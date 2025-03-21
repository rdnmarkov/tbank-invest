package com.example.tbank_invest.service;

import com.example.tbank_invest.entity.Bond;

public interface CalculatorBondService {

    Bond yearlyYieldBond(String ticker, Double currentPriceNoNKD );
}
