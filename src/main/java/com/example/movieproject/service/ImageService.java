package com.example.movieproject.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile file){

        if(file==null && file.isEmpty()){
            throw new RuntimeException();
        }

        String originName = file.getOriginalFilename();

        String fileName = UUID.randomUUID()+originName;
        ObjectMetadata objectMetadata = new ObjectMetadata();

        try {
            objectMetadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket,fileName,file.getInputStream(),objectMetadata);
        }catch (IOException e){
            e.printStackTrace();
        }


        return amazonS3Client.getUrl(bucket,fileName).toString();
    }


}
