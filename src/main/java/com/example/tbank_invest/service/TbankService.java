package com.example.tbank_invest.service;

import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.Coupon;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.time.Instant;
import java.util.List;

public interface TbankService {

     InstrumentShort findInstrument(String id);
     Bond findBond(String figi);
     List<Coupon> findCoupon(String figi);
     Quotation findLastPrice(String figi);
}
