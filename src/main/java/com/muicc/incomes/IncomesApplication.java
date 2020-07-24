package com.muicc.incomes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class IncomesApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IncomesApplication.class, args);
    }

}
