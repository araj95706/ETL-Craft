package org.example.intuitetllapplication.controllers;

import org.example.intuitetllapplication.util.Connectors.AWSConnectorUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    AWSConnectorUtilService awsConnectorUtilService;
    @PostMapping("/createBucket/{bucketName}")
    public ResponseEntity createBucket(@PathVariable String bucketName){

        awsConnectorUtilService.createBucket(bucketName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadfile/{BucketName}")
    public ResponseEntity uploadfile(@PathVariable String BucketName, @PathVariable String fileName){
        awsConnectorUtilService.uploadFile(BucketName, fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{bucketName}/{filePath}")
    public ResponseEntity getFile(@PathVariable String bucketName, @PathVariable String filePath) throws IOException {
        filePath = "ETL/JOB/"+filePath;
        awsConnectorUtilService.createTempLocalFile(bucketName,filePath);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
