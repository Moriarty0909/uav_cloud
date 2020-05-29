package com.ccssoft.clouduav;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudUavApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudUavApplication.class, args);
    }

}
