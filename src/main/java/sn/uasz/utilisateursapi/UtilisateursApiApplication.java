package sn.uasz.utilisateursapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sn.uasz.utilisateursapi.repositories.EnseignantRepository;




@SpringBootApplication
public class UtilisateursApiApplication {

	@Autowired
	private EnseignantRepository enseignantRepository;

	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApiApplication.class, args);
	}

}



