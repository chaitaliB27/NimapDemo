package com.nimap.curdOperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.nimap.curdOperation.Entity")
public class CurdOperationApplication {

	public static void main(String[] args) {
		System.out.println("Hello");

		SpringApplication.run(CurdOperationApplication.class, args);
	}

}
