package com.sen.springboot;

import com.sen.springboot.common.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
@EnableTransactionManagement
@Slf4j
@MapperScan("com.sen.springboot.mapper")
public class MakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakerApplication.class, args);
    }
    @Bean
    public SnowFlake snowFlake() {
        SnowFlake snowFlake = new SnowFlake(1, 1);
        return snowFlake;
    }
    
}
