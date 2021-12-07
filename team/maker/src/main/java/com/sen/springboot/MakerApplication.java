package com.sen.springboot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
@MapperScan("com.sen.springboot.mapper")
public class MakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakerApplication.class, args);
    }

}
