## Client Exchange Rate Web Service

Generic Service endpoint:

: http://localhost:9080/v2/api/client/\<intended action\>

* actions:
    * addClient(client id, client name)
    * updateClient(client id, client name)
    * getClient(client id, )
    * getCurrencyCodes() 
    * getExchangeRateQuote(client id,
                           Source Currency Code,
                           Target Currency Code,
                           Source Amount)
                            
