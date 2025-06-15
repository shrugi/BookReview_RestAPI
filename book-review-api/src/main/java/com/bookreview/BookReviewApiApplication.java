package com.bookreview;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class BookReviewApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookReviewApiApplication.class, args);
	}

}
