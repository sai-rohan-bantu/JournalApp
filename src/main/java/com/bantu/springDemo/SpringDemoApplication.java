package com.bantu.springDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class SpringDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringDemoApplication.class, args);
		System.out.println("Hello");
	}

	@Bean
	public PlatformTransactionManager manager(MongoDatabaseFactory factory){
		return new MongoTransactionManager(factory);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}




}
