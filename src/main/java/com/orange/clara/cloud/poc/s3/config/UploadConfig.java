package com.orange.clara.cloud.poc.s3.config;

import com.orange.clara.cloud.poc.s3.upload.UploadS3Stream;
import com.orange.clara.cloud.poc.s3.upload.UploadS3StreamImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C) 2015 Orange
 * <p/>
 * This software is distributed under the terms and conditions of the 'MIT'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'http://opensource.org/licenses/MIT'.
 * <p/>
 * Author: Arthur Halet
 * Date: 01/10/2015
 */
@Configuration
public class UploadConfig {

    @Bean
    public UploadS3Stream uploadS3Stream() {
        return new UploadS3StreamImpl();
    }
}
