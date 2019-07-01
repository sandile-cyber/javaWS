package za.co.yakka.utilities;

import feign.Param;
import feign.RequestLine;
import za.co.yakka.model.Rate;

import java.util.Map;

public interface ExchangeRateApi {

    @RequestLine("GET ?base={BASE}")
    Map<String, Object> currencyCodes(@Param("BASE") String base);


    //    @RequestLine("GET /latest?symbols={sourceCurrency},{targetCurrency}")
//    void exchangeRate(@Param("sourceCurrency") String sourceCurrency, @Param("targetCurrency") String targetCurrency);



}



