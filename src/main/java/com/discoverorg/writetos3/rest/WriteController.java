package com.discoverorg.writetos3.rest;

import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@RestController
public class WriteController {
    private ResourceLoader resourceLoader;

    public WriteController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/write")
    public String writeToS3() {
        String s3Url = "s3://dorg-dev-dex/testfile";
        WritableResource writableResource = (WritableResource) getResourceLoader().getResource(s3Url);
        try (PrintWriter writer = new PrintWriter(writableResource.getOutputStream())) {
            writer.write("This is being written to s3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "File written to : " + s3Url;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
