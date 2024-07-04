package com.JournalOne.JournalOne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalOneApplication.class, args);


	}

}
