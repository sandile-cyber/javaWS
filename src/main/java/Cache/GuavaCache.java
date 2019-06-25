package Cache;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import za.co.yakka.APIUtil;

public class GuavaCache {

	LoadingCache<String, List<String>> currencyCodes;
	APIUtil utilities;
	public GuavaCache() {
		super();
		String key = "Currency";
		utilities = new APIUtil();
		
		
		currencyCodes = CacheBuilder.newBuilder()
				.maximumSize(1000)
				.build(new CacheLoader<String, List<String>>(){
			
					@Override
					public List<String> load(String key) throws Exception {
						
						StringBuffer response = null;
						
						List<String> currencyCodes;

						response = utilities.invokeAPI("https://api.exchangeratesapi.io/latest?base=GBP");
						currencyCodes = utilities.parseJSONObject(response);
						
						return currencyCodes;
					}
				});
	}
	
	public List<String> getCurrencyCodes(String key){
		
		try {
		
			return currencyCodes.get(key);
		
		}
		catch( ExecutionException e) {
		
			e.printStackTrace();
		
		}	
		
		return null;
	}
	
	public void addCurrencyCodesList(String key, List<String> value) {
		
		currencyCodes.put(key, value);
	
	}

	

}
