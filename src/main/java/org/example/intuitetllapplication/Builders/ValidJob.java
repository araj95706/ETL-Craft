package org.example.intuitetllapplication.Builders;

import lombok.Getter;
import lombok.Setter;
import org.example.intuitetllapplication.Exceptions.*;
import org.example.intuitetllapplication.model.Mapping;
import org.example.intuitetllapplication.model.Source;
import org.example.intuitetllapplication.model.Status;

@Getter
@Setter
public class ValidJob {
    private Long job_id;
    private String job_name;
    private Status jobStatus;
    private org.example.intuitetllapplication.model.Source Source;
    private org.example.intuitetllapplication.model.Mapping Mapping;
    private Long batch_size;
    private String bucket_name;
    private String file_name;

    public static JobBuilder createBuilder(){
        return new JobBuilder();
    }
    private ValidJob(JobBuilder jobBuilder){
        this.setJob_id(jobBuilder.getJob_id());
        this.setBatch_size(jobBuilder.getBatch_size());
        this.setJob_name(jobBuilder.getJob_name());
        this.setJobStatus(jobBuilder.getJobStatus());
        this.Source = jobBuilder.getSource();
        this.setMapping(jobBuilder.getMapping());
        this.setBucket_name(jobBuilder.getBucket_name());
        this.setFile_name(jobBuilder.getFile_name());
    }

    public static class JobBuilder{
        private Long job_id;
        private String job_name;
        private Status jobStatus;
        private Source source;
        private Long batch_size;
        private String bucket_name;

        public String getFile_name() {
            return file_name;
        }

        public JobBuilder setFile_name(String file_name) {
            this.file_name = file_name;
            return this;
        }

        private String file_name;
        private org.example.intuitetllapplication.model.Mapping Mapping;
        private void validate() throws InvalidSourceIDException, invalidBatchSizeException {
            validateSource();
            validatedMapping();
            validateSourceMapping();
            validateBatchSize();
        }
        private void validateSource() throws InvalidSourceIDException {

        }
        private void validatedMapping()  {

        }

        private void validateSourceMapping()  {

        }
        private void validateBatchSize()  throws invalidBatchSizeException{

        }
        public ValidJob build() throws invalidBatchSizeException, InvalidSourceIDException {
            validate();
            return new ValidJob(this);
        }

        public Long getJob_id() {
            return job_id;
        }

        public JobBuilder setJob_id(Long job_id) {
            this.job_id = job_id;
            return this;
        }

        public String getJob_name() {
            return job_name;
        }

        public JobBuilder setJob_name(String job_name) {
            this.job_name = job_name;
            return this;
        }

        public Status getJobStatus() {
            return jobStatus;
        }

        public JobBuilder setJobStatus(Status jobStatus) {
            this.jobStatus = jobStatus;
            return this;
        }

        public Source getSource() {
            return source;
        }

        public JobBuilder setSource(Source source) {
            this.source = source;
            return this;
        }

        public Long getBatch_size() {
            return batch_size;
        }

        public JobBuilder setBatch_size(Long batch_size) {
            this.batch_size = batch_size;
            return this;
        }

        public Mapping getMapping() {
            return Mapping;
        }

        public JobBuilder setMapping(Mapping mapping) {
            Mapping = mapping;
            return this;
        }

        public String getBucket_name(){
            return this.bucket_name;
        }

        public JobBuilder setBucket_name(String bucket_name){
            this.bucket_name = bucket_name;
            return this;
        }
    }

}
