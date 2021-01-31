package com.destiny.kaiheila.destinybot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author qilong
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableScheduling
public class DestinybotApplication {

    public static void main(String[] args)   {
        SpringApplication.run(DestinybotApplication.class, args);

    }

}
