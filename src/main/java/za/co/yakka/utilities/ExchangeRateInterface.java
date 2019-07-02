package za.co.yakka.utilities;

import feign.Param;
import feign.RequestLine;
import za.co.yakka.model.FeignResponse;
import za.co.yakka.model.Rate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ExchangeRateInterface {

    @RequestLine("GET ?base={BASE}")
    FeignResponse currencyCodes(@Param("BASE") String base);

    @RequestLine("GET /latest?symbols={sourceCurrency},{targetCurrency}")
    FeignResponse exchangeRate(@Param("sourceCurrency") String sourceCurrency, @Param("targetCurrency") String targetCurrency);


}



