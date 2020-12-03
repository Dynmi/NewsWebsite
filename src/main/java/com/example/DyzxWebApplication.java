package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class DyzxWebApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DyzxWebApplication.class);
        //SpringApplication.run(SystemctlApplication.class, args);
        builder.headless(false)
                // .web(WebApplicationType.NONE)
                // .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
