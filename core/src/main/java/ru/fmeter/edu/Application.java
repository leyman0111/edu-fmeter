package ru.fmeter.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ru.fmeter.edu", "ru.fmeter.dao", "ru.fmeter.post"})
@EnableJpaRepositories(basePackages = {"ru.fmeter.dao"})
@EntityScan(basePackages = {"ru.fmeter.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
