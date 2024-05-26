package org.example.intuitetllapplication.configurations;

import org.example.intuitetllapplication.model.MarketPricesStore;
import org.example.intuitetllapplication.model.StockPriceDetails;
import org.example.intuitetllapplication.model.Trade;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EventWriter implements ItemWriter<Trade> {

    private MarketPricesStore marketPricesStore;
    @Autowired
    EventWriter(MarketPricesStore marketPricesStore){
        this.marketPricesStore = marketPricesStore;
    }

    @Override
    public void write(Chunk<? extends Trade> trades) throws Exception {
        trades.forEach(t -> {
            if (marketPricesStore.containsKey(t.getStock())) {
                double tradePrice = t.getPrice();
                StockPriceDetails priceDetails = marketPricesStore.get(t.getStock());
                // Set highest price
                if (tradePrice > priceDetails.getHigh()) {
                    priceDetails.setHigh(tradePrice);
                }
                // Set lowest price
                if (tradePrice < priceDetails.getLow()) {
                    priceDetails.setLow(tradePrice);
                }
                // Set close price
                priceDetails.setClose(tradePrice);
            } else {
                marketPricesStore.put(t.getStock(),
                        new StockPriceDetails(t.getStock(), t.getPrice(), t.getPrice(), t.getPrice(), t.getPrice()));
            }
        });
    }
}

