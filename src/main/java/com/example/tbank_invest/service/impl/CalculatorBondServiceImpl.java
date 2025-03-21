package com.example.tbank_invest.service.impl;

import com.example.tbank_invest.mapper.BondMapper;
import com.example.tbank_invest.service.CalculatorBondService;
import com.example.tbank_invest.service.TbankService;
import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.Coupon;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorBondServiceImpl implements CalculatorBondService {

    private final TbankService tbankService;
    private final BondMapper bondMapper;

    @Override
    public com.example.tbank_invest.entity.Bond yearlyYieldBond(String ticker, Double currentPriceNoNKD) {

        InstrumentShort instrument = tbankService.findInstrument(ticker);
        Bond bond = tbankService.findBond(instrument.getFigi());
        List<Coupon> coupons = tbankService.findCoupon(instrument.getFigi());
        if (Objects.isNull(currentPriceNoNKD)) {
            Quotation lastPrice = tbankService.findLastPrice(instrument.getFigi());
            currentPriceNoNKD = Double.parseDouble(lastPrice.getUnits() + "." + lastPrice.getNano()) * 10;
        }

        com.example.tbank_invest.entity.Bond mapBond = bondMapper.mapBond(bond, coupons, currentPriceNoNKD);

        log.info("Bond: {}", mapBond);

        return mapBond;
    }

}
