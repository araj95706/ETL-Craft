package org.example.intuitetllapplication.configurations;

import org.apache.http.Header;
import org.example.intuitetllapplication.model.MarketPricesStore;
import org.example.intuitetllapplication.model.StockPriceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class EventListener extends JobExecutionListenerSupport {
    @Value("${etl.outputFilePath}")
    String outputFilePath;
    private static final String LINE_DILM = ",";
    private MarketPricesStore marketPricesStore;
    @Autowired
    public EventListener(MarketPricesStore marketPricesStore){
        this.marketPricesStore = marketPricesStore;
    }
//
//    @Value("#{jobParameters['mapping']}")
//    private String mapping;
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Path path = Paths.get("prices.csv");
            try (BufferedWriter fileWriter = Files.newBufferedWriter(path)) {
                fileWriter.write("stock,open,close,low,high");
                fileWriter.newLine();
                for (StockPriceDetails pd : marketPricesStore.values()) {
                    fileWriter.write(new StringBuilder().append(pd.getStock())
                            .append(LINE_DILM).append(pd.getOpen())
                            .append(LINE_DILM).append(pd.getClose())
                            .append(LINE_DILM).append(pd.getLow())
                            .append(LINE_DILM).append(pd.getHigh()).toString());
                    fileWriter.newLine();
                }
            } catch (Exception e) {

            }
        }
    }
}
