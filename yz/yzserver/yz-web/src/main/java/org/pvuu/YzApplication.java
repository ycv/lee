package org.pvuu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "org.pvuu.mapper")
@EnableScheduling
public class YzApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzApplication.class, args);
    }

}