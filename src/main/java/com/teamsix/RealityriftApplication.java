package com.teamsix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class RealityriftApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealityriftApplication.class, args);
	}

}
