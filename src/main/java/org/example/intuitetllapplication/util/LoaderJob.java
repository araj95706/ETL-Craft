package org.example.intuitetllapplication.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.intuitetllapplication.model.Mapping;
import org.example.intuitetllapplication.util.Readers.CSVReadWriteUtilService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Getter
@Setter
public class LoaderJob {
    @Value("${etl.outputFilePath}")
    private String outputFilePath;
    private CSVReadWriteUtilService csvReaderUtil;
    private JobLauncher jobLauncher;
    private Job job;
    @Autowired
    public LoaderJob(CSVReadWriteUtilService csvReaderUtil, JobLauncher jobLauncher,@Qualifier("csvJob") Job job){
        this.csvReaderUtil = csvReaderUtil;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    /*
    1. Will Get CSV file and Mapping
    2. Using Mappings it'll parse CSV
    3. EtlJob Process (Transform)
    4. Write to output txt (Only one thread can write at a time)
     */

    public void processFile(Mapping mapping, Long batch_size) throws IOException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .addLong("batch_Size", batch_size)
                .addString("mapping", mapping.getColumnsOrder().toString())
                .toJobParameters();

        JobExecution run = null;
        try {
            run = jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
        System.out.println(run.getStatus().toString());
    }

//    public File transformCSVRecord(CSVParser parser, Mapping mapping) throws IOException {
//        File createdOutputFile = new File(outputFilePath.toString());
//        String mappingValues = mapping.getColumnsOrder();
//        for(int i = 0; i < mappingValues.size(); i++){
//            String column = mappingValues.get(i);
//            for(CSVRecord csvRecord: parser){
//                String record = csvRecord.get(column);
//                writeRecordToCSV(createdOutputFile, record);
//            }
//        }
//        return createdOutputFile;
//    }
//
//    public void writeRecordToCSV(File createdOutputFile,String string) throws IOException {
//        csvReaderUtil.writeRecord(createdOutputFile, string);
//    }
}
