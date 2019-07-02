## Client Exchange Rate Web Service

Generic Service endpoint:

> http://localhost:9080/v2/clientApi/<intended action\>

* Actions:
    * GET  /client/{id}
    * GET  /clientList/
    * POST /client?id={id}&name={name}
    * PUT  /client?id={id}&name={name}
    * GET  /currencyCodes
    * POST /exchangeRateQuote?id={id}&sourceCurrency={source currency code}&targetCurrency{target Currency code}&sourceAmount={source Amount}
    * POST /adjustedExchangeRate
                          
