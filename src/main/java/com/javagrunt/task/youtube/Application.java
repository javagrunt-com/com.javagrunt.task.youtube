package com.javagrunt.task.youtube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableTask
@EnableJdbcRepositories
@SpringBootApplication
@ImportRuntimeHints(RunTimeHints.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}