package org.example.intuitetllapplication.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class FxMarketPricesStore.
 *
 * @author ashraf
 */
@Configuration
public class MarketPricesStore {
	private Map<String, StockPriceDetails> stockPrices = new HashMap<String, StockPriceDetails>();
	public boolean containsKey(Object key) {
		return stockPrices.containsKey(key);
	}
	public StockPriceDetails put(String key, StockPriceDetails value) {
		return stockPrices.put(key, value);
	}
	public Collection<StockPriceDetails> values() {
		return stockPrices.values();
	}
	public StockPriceDetails get(Object key) {
		return stockPrices.get(key);
	}
}
