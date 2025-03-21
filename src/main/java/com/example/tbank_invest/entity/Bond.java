package com.example.tbank_invest.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Bond {

    String name;
    String ticker;
    Long daysBetween;
    Double coupon;
    Double nkd;
    Integer countCoupons;
    Double nominal;
    Double priceWithNKD;
    Double allPriceCoupons;
    Double priceEndNominal;
    Double onePriceBond;
    Double years;
    Double onePriceBondYear;
    Double annualReturn;

}
