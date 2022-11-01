package com.quochuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyShopApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyShopApplication.class, args);

        System.out.println("======================================================================================================>");
        System.out.println("============= Application is running... ==============================================================>");
        System.out.println("======================================================================================================>");
    }

}
