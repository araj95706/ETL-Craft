package org.example.intuitetllapplication.util;

import org.example.intuitetllapplication.model.EtlJob;
import org.example.intuitetllapplication.model.Source;
import org.example.intuitetllapplication.util.Connectors.AWSConnectorUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class MainJob {
    /*
    1. Get File Connector
    2. Spawn as may loaderJobs as required according to total_batch size
    3. Create queue of LoaderJobs having size as batch_size of loader_job
    4. Will create as many loaderJobs as required and push it to queue
    5. Will have one scheduler which will run loaderJobs from queue
    6. EtlJob Processor()  combine outputs (CSV)
     */

    AWSConnectorUtilService connector;
    LoaderJob loaderJob;
    @Autowired
    public MainJob(AWSConnectorUtilService awsConnectorUtilService, LoaderJob loaderJob){
        this.connector = awsConnectorUtilService;
        this.loaderJob = loaderJob;
    }

    public void processJob(EtlJob etlJob) throws IOException {
        File inputFile = getFileToLocal(etlJob.getSource());
        Long batch_size = etlJob.getBatch_size();
        loaderJob.processFile(etlJob.getMapping(), batch_size);
        upload(etlJob.getBucket_name(), etlJob.getFile_name());
    }

    private File getFileToLocal(Source source) throws IOException {
        String filepath = source.getSource_path() + source.getFileName();
        return connector.createTempLocalFile(source.getBucketName(), filepath);
    }

    private void upload(String bucketName, String file_name){
        connector.uploadFile(bucketName, file_name);
    }

}
