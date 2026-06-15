
package com.mphasis.csp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CspApplication  {

    public static void main(String[] args) {
        SpringApplication.run(CspApplication.class, args);
    }
}