package com.alura.challenges.literalura;

import com.alura.challenges.literalura.model.Menu;
import com.alura.challenges.literalura.repository.BookRepository;
import com.alura.challenges.literalura.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private Main main;
	@Override
	public void run(String... args) throws Exception {
		main.menuChoices();
	}
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

}
