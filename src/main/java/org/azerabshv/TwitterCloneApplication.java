package org.azerabshv;

import org.azerabshv.services.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class TwitterCloneApplication  {


	public static void main(String[] args) {
		SpringApplication.run(TwitterCloneApplication.class, args);
	}

}
