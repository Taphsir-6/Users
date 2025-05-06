package sn.uasz.utilisateursapi.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;
import sn.uasz.utilisateursapi.services.EnseignantService;
import sn.uasz.utilisateursapi.enums.Grade;

import java.time.LocalDate;

/**
 * Composant Spring responsable de l'initialisation des données d'enseignants lors du démarrage de l'application.
 *
 * Cette classe implémente {@link CommandLineRunner}, ce qui permet d'exécuter du code au démarrage de l'application.
 * Elle insère des enseignants de test si leurs adresses email ne sont pas déjà présentes dans la base de données.
 */
@Configuration
@RequiredArgsConstructor
public class EnseignantDataInitializer implements CommandLineRunner {

    /** Logger pour journaliser les opérations d'initialisation. */
    private static final Logger logger = LoggerFactory.getLogger(EnseignantDataInitializer.class);

    /** Service métier pour la gestion des enseignants. */
    private final EnseignantService enseignantService;

    /** Repository direct d'accès aux enseignants. */
    private final EnseignantRepository enseignantRepository;

    /**
     * Méthode exécutée automatiquement au démarrage de l'application.
     * Elle appelle la méthode interne pour insérer les enseignants de test.
     *
     * @param args arguments de la ligne de commande (non utilisés ici)
     */
    @Override
    public void run(String... args) {
        insererEnseignantsTest();
    }

    /**
     * Crée et insère un enseignant de test si son email n'existe pas déjà en base.
     *
     * @param nom nom de l'enseignant
     * @param prenom prénom de l'enseignant
     * @param email adresse email professionnelle
     * @param telephone numéro de téléphone
     * @param matricule matricule unique
     * @param grade grade académique
     * @param createdBy utilisateur ayant créé la donnée
     * @return true si l'insertion a réussi ou était déjà présente, false sinon
     */
    private boolean creerEnseignantTest(String nom, String prenom, String email, String telephone, String matricule, Grade grade, String createdBy) {
        try {
            if (enseignantRepository.existsByEmail(email)) {
                logger.info("🔁 Enseignant avec l'email {} existe déjà. Insertion ignorée.", email);
                return true;
            }

            EnseignantDTO dto = new EnseignantDTO(
                    null,              // ID auto-généré
                    nom,
                    prenom,
                    email,
                    telephone,
                    matricule,
                    grade,
                    createdBy,
                    LocalDate.now(),
                    true               // Actif par défaut
            );

            enseignantService.ajouterEnseignant(dto);
            logger.info("✅ Enseignant {} {} inséré avec succès.", prenom, nom);
            return true;

        } catch (Exception e) {
            logger.error("❌ Échec d'insertion pour {} {} : {}", prenom, nom, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Insère une liste d'enseignants prédéfinis à des fins de test.
     * Utilise la méthode {@link #creerEnseignantTest} pour chaque enseignant.
     */
    private void insererEnseignantsTest() {
        boolean success = true;

        success &= creerEnseignantTest("Diop", "Ibrahima", "diop.ibrahima@uasz.sn", "+221771012233", "181185/D", Grade.PROFESSEUR_ASSIMILE, "admin");
        success &= creerEnseignantTest("Fall", "Modou", "fall.modou@uasz.sn", "+221772023344", "181186/A", Grade.VACATAIRE, "admin");
        success &= creerEnseignantTest("Ndiaye", "Ibrahima", "ndiaye.ibrahima@uasz.sn", "+221773034455", "181187/B", Grade.VACATAIRE, "admin");

        if (success) {
            logger.info("\n Tous les enseignants ont été insérés avec succès !");
        } else {
            logger.warn("\n Certains enseignants n'ont pas pu être insérés !");
        }
    }
}
