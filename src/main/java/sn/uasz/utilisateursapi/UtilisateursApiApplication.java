package sn.uasz.utilisateursapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;
import sn.uasz.utilisateursapi.enums.Grade;

import java.time.LocalDate;


@SpringBootApplication
public class UtilisateursApiApplication {

	@Autowired
	private EnseignantRepository enseignantRepository;

	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApiApplication.class, args);
	}
}



