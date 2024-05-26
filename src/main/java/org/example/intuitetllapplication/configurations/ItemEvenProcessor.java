package org.example.intuitetllapplication.configurations;

import org.example.intuitetllapplication.model.MarketEvent;
import org.example.intuitetllapplication.model.Trade;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemEvenProcessor implements ItemProcessor<MarketEvent, Trade> {
    @Override
    public Trade process(final MarketEvent marketEvent) throws Exception {
        final String stock = marketEvent.getStock();
        final String time = marketEvent.getTime();
        final double price = Double.valueOf(marketEvent.getPrice());
        final long shares = Long.valueOf(marketEvent.getShares());
        final Trade trade = new Trade(stock, time, price, shares);
        return trade;
    }

}
