package com.ccssoft.cloudmessagemachine;

import com.ccssoft.cloudcommon.common.utils.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class CloudMessagemachineApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CloudMessagemachineApplication.class, args);
        SpringBeanUtils.setApplicationContext(applicationContext);
    }

}
