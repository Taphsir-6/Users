package sn.uasz.utilisateursapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;

import java.time.LocalDate;


@SpringBootApplication
public class UtilisateursApiApplication {

	@Autowired
	private EnseignantRepository enseignantRepository;

	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApiApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			// Insertion d'enseignants au démarrage
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



		};
	}
}



