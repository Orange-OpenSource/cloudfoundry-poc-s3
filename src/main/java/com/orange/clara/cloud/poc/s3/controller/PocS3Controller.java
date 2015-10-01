package com.orange.clara.cloud.poc.s3.controller;

import com.orange.clara.cloud.poc.s3.upload.UploadS3Stream;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Copyright (C) 2015 Orange
 * <p/>
 * This software is distributed under the terms and conditions of the 'MIT'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'http://opensource.org/licenses/MIT'.
 * <p/>
 * Author: Arthur Halet
 * Date: 30/09/2015
 */
@RestController
public class PocS3Controller {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    @Qualifier(value = "blobStoreContext")
    private BlobStoreContext blobStoreContext;

    @Autowired
    @Qualifier(value = "bucketName")
    private String bucketName;

    @Autowired
    private UploadS3Stream uploadS3Stream;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String upload(@RequestParam("name") String name,
                  @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return "You failed to upload " + name + " because the file was empty.";
        }

        byte[] bytes = multipartFile.getBytes();
        BlobStore blobStore = blobStoreContext.getBlobStore();
        Blob blob = blobStore.blobBuilder(name)
                .payload(bytes)
                .build();

        blobStore.putBlob(this.bucketName, blob);

        return "We uploaded the file '" + name + "' to a riakcs!";
    }

    @RequestMapping("/show/{fileName:.*}")
    public String show(@PathVariable String fileName) throws IOException {
        BlobStore blobStore = blobStoreContext.getBlobStore();
        Blob blob = blobStore.getBlob(this.bucketName, fileName);
        InputStream inputStream = blob.getPayload().openStream();

        String content = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            content += line;
            content += "<br/>";
        }
        br.close();

        return content;
    }

    @RequestMapping(value = "/download/{fileName:.*}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> download(@PathVariable String fileName)
            throws IOException {
        BlobStore blobStore = blobStoreContext.getBlobStore();
        Blob blob = blobStore.getBlob(this.bucketName, fileName);

        HttpHeaders respHeaders = new HttpHeaders();

        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentLength(blob.getPayload().getContentMetadata().getContentLength());
        respHeaders.setContentDispositionFormData("attachment", fileName);

        InputStream inputStream = blob.getPayload().openStream();

        InputStreamResource isr = new InputStreamResource(inputStream);
        return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/uploadInStream", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadInStream(@RequestParam("name") String name,
                          @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return "You failed to upload " + name + " because the file was empty.";
        }

        InputStream multipartFileStream = multipartFile.getInputStream();
        BlobStore blobStore = blobStoreContext.getBlobStore();
        Blob blob = blobStore.blobBuilder(name).build();
        this.uploadS3Stream.upload(multipartFileStream, blob);

        return "We uploaded the file '" + name + "' to a riakcs!";
    }
}
