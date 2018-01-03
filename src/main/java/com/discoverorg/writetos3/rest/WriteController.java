package com.discoverorg.writetos3.rest;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@RestController
public class WriteController {
    private static final String S3_PREFIX = "s3://";
    private static final String S3_BUCKET = "dorg-dev-dex";
    private static final String S3_KEY = "testfile";
    private ResourceLoader resourceLoader;
    private AmazonS3 amazonS3;

    public WriteController(ResourceLoader resourceLoader, AmazonS3 amazonS3) {
        this.resourceLoader = resourceLoader;
        this.amazonS3 = amazonS3;
    }

    @GetMapping("/write")
    public String writeToS3() {
        String s3Url = S3_PREFIX + S3_BUCKET + "/" + S3_KEY;
        WritableResource writableResource = (WritableResource) getResourceLoader().getResource(s3Url);
        try (PrintWriter writer = new PrintWriter(writableResource.getOutputStream())) {
            writer.write("This is being written to s3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        getAmazonS3().setObjectAcl(S3_BUCKET, S3_KEY, CannedAccessControlList.BucketOwnerFullControl);
        return "File written to : " + s3Url;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public AmazonS3 getAmazonS3() {
        return amazonS3;
    }

    public void setAmazonS3(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
}
