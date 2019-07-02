package za.co.yakka.cache;
import java.util.*;
import java.util.concurrent.ExecutionException;

import feign.Feign;
import feign.gson.GsonDecoder;
import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import za.co.yakka.model.FeignResponse;
import za.co.yakka.model.Rate;
import za.co.yakka.utilities.ExchangeRateInterface;

public class GuavaCache {

	LoadingCache<String, Map<String, Double>> currencyCodes;
	static Logger logger;
	
	
	public GuavaCache() {
		super();

		logger = Logger.getLogger(GuavaCache.class);

		currencyCodes = CacheBuilder.newBuilder()
				.build(new CacheLoader<String, Map<String, Double>>(){
			
					@Override
					public Map<String, Double> load(String key) throws Exception {

						ExchangeRateInterface exchangeRateAPI = Feign.builder()
								.decoder(new GsonDecoder())
								.target(ExchangeRateInterface.class,"https://api.exchangeratesapi.io/latest");

						FeignResponse map = exchangeRateAPI.currencyCodes(key);
						logger.debug("Got the require output for from the API");
						Map<String, Double> rates = map.getRates();

						return rates;

					}
				});
	}
	
	public Set<String> getCurrencyCodes(String key) {
		logger.debug("Fetching from Guava cache");

		Map<String, Double> rates = null;

		try {
			rates = currencyCodes.get("");
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return rates.keySet();
	}





}
