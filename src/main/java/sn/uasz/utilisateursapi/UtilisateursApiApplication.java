package sn.uasz.UtilisateursAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sn.uasz.UtilisateursAPI.entities.Enseignant;
import sn.uasz.UtilisateursAPI.repositories.EnseignantRepository;


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
					"mane.ousmane@uasz.sn",
					"Ousmane",
					"Mané",
					"MAT123",
					"Assistant",
					false
			));

			enseignantRepository.save(new Enseignant(
					null,
					"diop.ibrahima@uasz.sn",
					"Ibrahima",
					"Diop",
					"INF247",
					"Professeur",
					true
			));
			enseignantRepository.save(new Enseignant(
					null,
					"diouf.moussa@uasz.sn",
					"Moussa",
					"Diouf",
					"MAT123",
					"Assistant",
					true
			));



		};
	}
}



