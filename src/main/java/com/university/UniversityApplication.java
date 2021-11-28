package com.university;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class UniversityApplication {

	public static void main(String[] args) {
//		try {
//			Thread.sleep(60000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		SpringApplication.run(UniversityApplication.class, args);
	}

}
