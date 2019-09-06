package com.lucifaer.expTest.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JavaWebApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.setProperty("file.encoding", "UTF-8");
        super.onStartup(servletContext);
    }
}
