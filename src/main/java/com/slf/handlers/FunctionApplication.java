package com.slf.handlers;

import com.slf.functions.TemplateFunction;
import com.slf.models.TemplateRequest;
import com.slf.models.TemplateResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication(scanBasePackages = {"com.slf"})
public class FunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionApplication.class, args);
    }

    @Bean
    public Function<TemplateRequest, TemplateResponse> handle() {
        return new TemplateFunction();
    }
}
