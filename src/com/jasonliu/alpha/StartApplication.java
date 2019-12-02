package com.jasonliu.alpha;

import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by liujg on 2019-08-06
 */
@SpringBootApplication(scanBasePackages = {
        "com.jasonliu.alpha"}
)
@EnableScheduling
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(StartApplication.class);

        Properties properties = new Properties();
        properties.put("spring.profiles.active", "test");
        application.setDefaultProperties(properties);

        application.run(args);
    }

}