package com.example.tbank_invest.service.impl;

import com.example.tbank_invest.service.TbankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.Coupon;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TbankServiceImpl implements TbankService {

    private final InvestApi api;

    public InstrumentShort findInstrument(String id){
        return api.getInstrumentsService().findInstrumentSync(id).getFirst();
    }

    public Bond findBond(String figi){
        return api.getInstrumentsService().getBondByFigiSync(figi);
    }

    public List<Coupon> findCoupon(String figi){
        return api.getInstrumentsService().getBondCouponsSync(figi, Instant.now(),  Instant.now().plusSeconds(100L * 365 * 24 * 60 * 60));
    }

    public Quotation findLastPrice(String figi){
       return  api.getMarketDataService().getLastPricesSync(List.of(figi)).getFirst().getPrice();
    }

}
