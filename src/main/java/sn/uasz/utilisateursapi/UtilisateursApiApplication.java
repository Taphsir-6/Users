package sn.uasz.utilisateursapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;

import java.time.LocalDate;

/**
 * @author Ousmane Mane on 11/12/2021.
 * @project utilisateurs-api
 */
@SpringBootApplication
public class UtilisateursApiApplication {

	@Autowired
	private EnseignantRepository enseignantRepository;

	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApiApplication.class, args);
	}

	@Bean
	@Profile("!test")
	CommandLineRunner initDatabase() {
		return args -> {
			// Insertion d'enseignants au démarrage, idempotente
			if (!enseignantRepository.existsByEmail("mane.ousmane@uasz.sn")) {
				enseignantRepository.save(new Enseignant(
					null,
					"Mané", // nom
					"Ousmane", // prénom
					"mane.ousmane@uasz.sn", // email
					"+221770000001", // téléphone
					"MAT123", // matricule
					"Assistant", // grade
					"admin", // createBy
					LocalDate.now(), // createAt
					false // actif
				));
			}

			if (!enseignantRepository.existsByEmail("diop.ibrahima@uasz.sn")) {
				enseignantRepository.save(new Enseignant(
					null,
					"Diop",
					"Ibrahima",
					"diop.ibrahima@uasz.sn",
					"+221770000001",
					"INF247",
					"Professeur",
					"admin", // createBy
					LocalDate.now(), // createAt
					true
				));
			}

			if (!enseignantRepository.existsByEmail("diouf.moussa@uasz.sn")) {
				enseignantRepository.save(new Enseignant(
					null,
					"Diouf",
					"Moussa",
					"diouf.moussa@uasz.sn",
					"+221770000001",
					"INF247",
					"Professeur",
					"admin", // createBy
					LocalDate.now(), // createAt
					true
				));
			}
		};
	}
}
