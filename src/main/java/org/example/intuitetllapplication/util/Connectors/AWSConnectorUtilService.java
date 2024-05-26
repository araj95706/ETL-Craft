package org.example.intuitetllapplication.util.Connectors;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AWSConnectorUtilService implements ConnectorUtil{
    AmazonS3 amazonS3Client;
    @Value("${etl.inputFilePath}")
    String inputFilePath;
    @Value("${etl.outputFilePath}")
    String outputFilePath;
    @Value("${etl.filePathOnS3}")
    String filePathOnS3;


    @Autowired
    public AWSConnectorUtilService(AmazonS3 amazonS3Client){
        this.amazonS3Client = amazonS3Client;
    }

    public void createBucket(String bucketName){
        amazonS3Client.createBucket(new CreateBucketRequest(bucketName));
    }

    public void uploadFile(String bucketName, String fileName){
        Path path = Paths.get("prices.csv");
        File file = new File(path.toString());
        String uploadedFile = filePathOnS3 + fileName;
        PutObjectResult putObjectResult = amazonS3Client.putObject(new PutObjectRequest(bucketName, uploadedFile, file));
    }

    public File createTempLocalFile(String bucketName, String fileName) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName,fileName));
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        File createdInputFile = new File(inputFilePath);
        FileOutputStream outputStream = new FileOutputStream(createdInputFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = s3ObjectInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
        }
        return createdInputFile;
    }

}
