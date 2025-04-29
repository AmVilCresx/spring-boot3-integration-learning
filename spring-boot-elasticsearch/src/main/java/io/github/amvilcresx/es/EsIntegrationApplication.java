package io.github.amvilcresx.es;

import org.dromara.easyes.spring.annotation.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EsMapperScan("io.github.amvilcresx.es.mapper")
public class EsIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsIntegrationApplication.class, args);
    }
}
