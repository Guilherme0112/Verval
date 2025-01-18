package com.br.verval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class VervalApplication {

	public static void main(String[] args) {
		// Variaveis de ambiente

		Dotenv dotenv = Dotenv.load();

		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_PASS", dotenv.get("DB_PASS"));
		
		System.setProperty("KEY_JWT", dotenv.get("KEY_JWT"));

		SpringApplication.run(VervalApplication.class, args);
	}

}
