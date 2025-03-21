package com.example.tbank_invest.mapper;

import com.example.tbank_invest.entity.Bond;
import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Coupon;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class BondMapper {

    public Bond mapBond(ru.tinkoff.piapi.contract.v1.Bond bond,
                        List<Coupon> coupons,
                        Double currentPriceNoNKD){

        long daysBetween = getDaysBetween(bond.getMaturityDate());
        double coupon = Double.parseDouble(coupons.getFirst().getPayOneBond().getUnits() + "." + coupons.getFirst().getPayOneBond().getNano());
        double nkd = Double.parseDouble(bond.getAciValue().getUnits() + "." + bond.getAciValue().getNano());
        int countCoupons = coupons.size();
        double nominal = Double.parseDouble(bond.getInitialNominal().getUnits() + "." + bond.getInitialNominal().getNano());
        double priceWithNKD = currentPriceNoNKD + nkd;
        double allPriceCoupons = countCoupons * coupon - nkd;
        double priceEndNominal = nominal - currentPriceNoNKD;
        double onePriceBond = (allPriceCoupons + priceEndNominal) - priceWithNKD * 0.003d;
        double years = (double) daysBetween / 365;
        double onePriceBondYear = onePriceBond / years;

        return Bond.builder()
                .name(bond.getName())
                .ticker(getTicker(bond))
                .daysBetween(daysBetween)
                .coupon(coupon)
                .nkd(nkd)
                .countCoupons(countCoupons)
                .nominal(nominal)
                .priceWithNKD(currentPriceNoNKD + nkd)
                .allPriceCoupons(allPriceCoupons)
                .priceEndNominal(priceEndNominal)
                .onePriceBond(onePriceBond)
                .years(years)
                .onePriceBondYear(onePriceBondYear)
                .annualReturn((onePriceBondYear * 100) / priceWithNKD)
                .build();
    }

    private long getDaysBetween(Timestamp dateMaturity) {
        Instant now = Instant.now();
        Instant dateMat = Instant.ofEpochSecond(dateMaturity.getSeconds(), dateMaturity.getNanos());

        return ChronoUnit.DAYS.between(now, dateMat);
    }

    private String getTicker(ru.tinkoff.piapi.contract.v1.Bond bond) {
        return bond.getTicker().contains("ISSU") ? bond.getIsin() : bond.getTicker();
    }

}
