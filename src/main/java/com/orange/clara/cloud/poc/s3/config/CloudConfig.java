package com.orange.clara.cloud.poc.s3.config;

import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStore;
import com.orange.spring.cloud.connector.s3.core.jcloudswrappers.SpringCloudBlobStoreContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
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
 * Date: 30/09/2015
 */
@Configuration
@ServiceScan
public class CloudConfig extends AbstractCloudConfig {

    @Autowired
    private SpringCloudBlobStoreContext springCloudBlobStoreContext;

    @Bean
    public SpringCloudBlobStoreContext blobStoreContext() {
        System.out.println("marker " + this.springCloudBlobStoreContext);
        return this.springCloudBlobStoreContext;
    }

    @Bean
    public SpringCloudBlobStore blobStore() {
        return this.blobStoreContext().getSpringCloudBlobStore();
    }
}