package com.task.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String baseFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(baseFilePath + "**") //이 부분은 윈도우에서 실행 시 변경해줘야함
                .addResourceLocations("file://%s/".formatted(baseFilePath));
    }
}