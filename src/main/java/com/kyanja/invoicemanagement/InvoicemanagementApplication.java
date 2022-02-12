package com.kyanja.invoicemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class InvoicemanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoicemanagementApplication.class, args);
    }

}
