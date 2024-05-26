package org.example.intuitetllapplication.configurations;

import org.example.intuitetllapplication.model.MarketEvent;
import org.example.intuitetllapplication.model.Trade;
import org.example.intuitetllapplication.repositories.marketEventRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    private final marketEventRepository marketEventRepo;
    EventReader itemReader;
    ItemEvenProcessor itemProcessor;
    EventWriter itemWriter;
    EventListener listener;

    @Value("${etl.inputFilePath}")
    String inputFilePath;
    @Autowired
    public BatchConfig(EventReader itemReader, EventWriter itemWriter, ItemEvenProcessor itemProcessor, EventListener listener, marketEventRepository marketEventRepo){
        this.itemReader = itemReader;
        this.itemWriter = itemWriter;
        this.itemProcessor = itemProcessor;
        this.listener = listener;
        this.marketEventRepo = marketEventRepo;
    }

    @Bean
    public Step etlStep(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csv-step",repository).<MarketEvent, Trade>chunk(10,transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean(name="csvJob")
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("csv-job",jobRepository).listener(listener)
                .flow(etlStep(jobRepository,transactionManager)).end().build();
    }

//    @Bean
//    public RepositoryItemWriter<MarketEvent> itemWriter2(){
//        RepositoryItemWriter<MarketEvent> writer = new RepositoryItemWriter<>();
//        writer.setRepository(marketEventRepo);
//        writer.setMethodName("save");
//        return writer;
//    }

}