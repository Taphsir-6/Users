/**
 * Point d'entrée de l'application Spring Boot pour la gestion des utilisateurs.
 * Cette classe configure et lance l'application Spring Boot.
 *
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 */
package sn.uasz.utilisateursapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application Spring Boot pour la gestion des utilisateurs.
 * Cette application fournit une API REST pour gérer les vacataires.
 *
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 */
@SpringBootApplication
public class UtilisateursApiApplication {
	/**
	 * Point d'entrée principal de l'application.
	 * Lance l'application Spring Boot avec les configurations par défaut.
	 *
	 * @param args Arguments de la ligne de commande (non utilisés)
	 */
	public static void main(String[] args) {
		SpringApplication.run(UtilisateursApiApplication.class, args);
	}
}