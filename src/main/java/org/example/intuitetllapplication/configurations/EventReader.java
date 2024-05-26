package org.example.intuitetllapplication.configurations;

import org.example.intuitetllapplication.model.MarketEvent;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class EventReader extends FlatFileItemReader<MarketEvent>{

//    @Lazy
//    @Value("#{jobParameters['mapping']}")
//    private String mapping;

    @Value("${etl.inputFilePath}")
    String inputFilePath;
    public EventReader() {
        this.setResource(new FileSystemResource("src/main/resources/trades.csv"));
        this.setName("csv-reader");
        this.setLinesToSkip(1);
        this.setLineMapper(new DefaultLineMapper<MarketEvent>() {
            {
                DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
                tokenizer.setDelimiter(",");
                tokenizer.setNames("stock", "time", "price", "shares");
                tokenizer.setStrict(false);
                BeanWrapperFieldSetMapper mapper = new BeanWrapperFieldSetMapper<>();
                mapper.setTargetType(MarketEvent.class);
                this.setFieldSetMapper(mapper);
                this.setLineTokenizer(tokenizer);
            }
        });
    }
}
