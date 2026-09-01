package org.fakechitor.apexmaprotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApexMapRotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApexMapRotationApplication.class, args);
    }

}
