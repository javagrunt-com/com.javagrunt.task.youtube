package com.javagrunt.task.youtube;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

@EnableTask
@SpringBootApplication
public class Application {

	@Bean
	public ApplicationRunner applicationRunner() {
		return new HelloWorldApplicationRunner();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public static class HelloWorldApplicationRunner implements ApplicationRunner {

		@Override
		public void run(ApplicationArguments args) throws Exception {
			System.out.println("Hello, World!");
		}
	}
}
