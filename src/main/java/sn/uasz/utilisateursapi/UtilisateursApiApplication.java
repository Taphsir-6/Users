package sn.uasz.utilisateursapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;

import java.util.List;

@SpringBootApplication
public class UtilisateursApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApiApplication.class, args);
	}

	// Méthode exécutée au démarrage de l'application
	@Bean
	public CommandLineRunner initData(EnseignantRepository enseignantRepository) {
		return args -> {
			System.out.println("\n=========== INSERTION DES DONNÉES ===========\n");

			List<Enseignant> enseignants = List.of(
					new Enseignant(null, "Diop", "Ibrahima", "diop@example.com", "77-101-22-33", "181185/D", "Professeur Assimilé", "admin", "2024-01-01"),
					new Enseignant(null, "Fall", "Modou", "fall@example.com", "77-202-33-44", "181186/A", "Vacataire", "admin", "2024-01-01"),
					new Enseignant(null, "Ndiaye", "Ibrahima", "ndiaye@example.com", "77-303-44-55", "181187/B", "Vacataire", "admin", "2024-01-01")
			);


			enseignantRepository.saveAll(enseignants);

			System.out.println("✅ Enseignants insérés avec succès !");
		};
	}
}
