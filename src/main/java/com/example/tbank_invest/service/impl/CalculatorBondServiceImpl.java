package com.example.tbank_invest.service.impl;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorBondServiceImpl implements CalculatorBondService {

    private final TbankService tbankService;

    @Override
    public double yearlyYieldBond(String ticker) {

        InstrumentShort instrument = tbankService.findInstrument(ticker);
        Bond bond = tbankService.findBond(instrument.getFigi());
        List<Coupon> coupons = tbankService.findCoupon(instrument.getFigi());
        Quotation lastPrice = tbankService.findLastPrice(instrument.getFigi());

        long daysBetween = getDaysBetween(bond.getMaturityDate());
        double coupon = Double.parseDouble(coupons.getFirst().getPayOneBond().getUnits() + "." + coupons.getFirst().getPayOneBond().getNano());
        double nkd = Double.parseDouble(bond.getAciValue().getUnits() + "." + bond.getAciValue().getNano());
        double currentPriceNoNKD = Double.parseDouble(lastPrice.getUnits()+ "." + lastPrice.getNano()) * 10;
        int countCoupons = coupons.size();
        double nominal = Double.parseDouble(bond.getInitialNominal().getUnits() + "." + bond.getInitialNominal().getNano());
        double priceWithNKD = currentPriceNoNKD + nkd;
        double allPriceCoupons = countCoupons * coupon - nkd;
        double priceEndNominal = nominal - currentPriceNoNKD;
        double onePriceBond = (allPriceCoupons + priceEndNominal) - priceWithNKD * 0.003d;
        double years = (double) daysBetween / 365;
        double onePriceBondYear = onePriceBond/years;

        return (onePriceBondYear*100)/priceWithNKD;
    }

    private long getDaysBetween(Timestamp dateMaturity) {
        Instant now = Instant.now();
        Instant dateMat = Instant.ofEpochSecond(dateMaturity.getSeconds(), dateMaturity.getNanos());

        return ChronoUnit.DAYS.between(now, dateMat);
    }
}
