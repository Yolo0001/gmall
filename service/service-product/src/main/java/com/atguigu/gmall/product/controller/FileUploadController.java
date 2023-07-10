package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("admin/product")
public class FileUploadController {

    @Value("${minio.endpointUrl}")
    public String endpointUrl;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.secreKey}")
    public String secreKey;

    @Value("${minio.bucketName}")
    public String bucketName;

    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception {
        String url = "";

        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpointUrl)
                .credentials(accessKey, secreKey)
                .build();
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (bucketExists) {
            System.out.println("Bucket gmall already exists.");
        }else  {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

        }

        //  定义一个文件的名称 : 文件上传的时候，名称不能重复！
        String fileName = System.currentTimeMillis()+ UUID.randomUUID().toString();
        // 使用putObject上传一个文件到存储桶中。
        //  minioClient.putObject("asiatrip","asiaphotos.zip", "/home/user/Photos/asiaphotos.zip");
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
        //  System.out.println("/home/user/Photos/asiaphotos.zip is successfully uploaded as asiaphotos.zip to `asiatrip` bucket.");
        //  文件上传之后的路径： http://39.99.159.121:9000/gmall/xxxxxx
        url = endpointUrl+"/"+bucketName+"/"+fileName;

        System.out.println("url:\t"+url);
        //  将文件上传之后的路径返回给页面！
        return Result.ok(url);

    }
}
