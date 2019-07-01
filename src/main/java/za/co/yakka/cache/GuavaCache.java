package za.co.yakka.cache;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import feign.Feign;
import feign.gson.GsonDecoder;
import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import za.co.yakka.utilities.ExchangeRateApi;

public class GuavaCache {

	LoadingCache<String, Set<String>> currencyCodes;
	ApiUtils utilities;
	static Logger logger;
	
	
	public GuavaCache() {
		super();

		logger = Logger.getLogger(GuavaCache.class);
		utilities = new ApiUtils();
		
		currencyCodes = CacheBuilder.newBuilder()
				.build(new CacheLoader<String, Set<String>>(){
			
					@Override
					public Set<String> load(String key) throws Exception {

						ExchangeRateApi exchangeRateAPI = Feign.builder()
								.decoder(new GsonDecoder())
								.target(ExchangeRateApi.class,"https://api.exchangeratesapi.io/latest");

						Map<String, Object> response  = exchangeRateAPI.currencyCodes(key);
						Map<String, Double> rates =  (Map<String, Double>) response.get("rates");

						return rates.keySet();

					}
				});
	}
	
	public Set<String> getCurrencyCodes(String key){
		logger.debug("Fetching from Guava cache");
		try {
		
			return currencyCodes.get(key);
		
		}
		catch( ExecutionException e) {
		
			e.printStackTrace();
		
		}	
		
		return null;
	}





}
