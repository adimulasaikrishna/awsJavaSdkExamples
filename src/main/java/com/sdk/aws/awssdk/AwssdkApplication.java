package com.sdk.aws.awssdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = { "com.sdk.aws.*"} )
public class AwssdkApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwssdkApplication.class, args);
	}


}
