package sn.uasz.utilisateursapi.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Role;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;
import sn.uasz.utilisateursapi.services.EnseignantService;
import sn.uasz.utilisateursapi.enums.Grade;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe de configuration utilisée pour initialiser les données des enseignants
 * dans la base de données à l'exécution de l'application.
 */
@Configuration
@RequiredArgsConstructor
public class EnseignantDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(EnseignantDataInitializer.class);
    private static final String ADMIN_USER = "admin";

    private final EnseignantService enseignantService;
    private final EnseignantRepository enseignantRepository;

    /**
     * Méthode appelée automatiquement au démarrage de l'application.
     * Elle déclenche l'insertion des enseignants de test.
     */
    @Override
    public void run(String... args) {
        insererEnseignantsTest();
    }

    /**
     * Crée et insère un enseignant de test s'il n'existe pas déjà dans la base.
     *
     * @param nom       Nom de l'enseignant
     * @param prenom    Prénom de l'enseignant
     * @param email     Adresse email (doit être unique)
     * @param telephone Numéro de téléphone
     * @param matricule Matricule de l'enseignant
     * @param grade     Grade de l'enseignant
     * @param roles     Liste des rôles assignés à l'enseignant
     * @param createdBy Créateur de l'utilisateur (ici 'admin')
     * @return true si l'insertion est réussie ou ignorée (déjà existant), false en cas d'erreur
     */
    private boolean creerEnseignantTest(String nom, String prenom, String email, String telephone,
                                        String matricule, Grade grade, List<Role> roles, String createdBy) {
        try {
            // Vérification d'existence par email
            if (enseignantRepository.existsByEmail(email)) {
                logger.info("Enseignant avec l'email {} existe déjà. Insertion ignorée.", email);
                return true;
            }

            // Construction du DTO enseignant
            EnseignantDTO dto = new EnseignantDTO(
                    null,
                    nom,
                    prenom,
                    email,
                    telephone,
                    matricule,
                    grade,
                    roles,
                    createdBy,
                    LocalDate.now(),
                    true
            );

            // Insertion via le service
            enseignantService.ajouterEnseignant(dto);
            logger.info("Enseignant {} {} inséré avec succès.", prenom, nom);
            return true;

        } catch (Exception e) {
            logger.error("Échec d'insertion pour {} {} : {}", prenom, nom, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Insère plusieurs enseignants de test à l'initialisation.
     * Les doublons sont ignorés.
     */
    private void insererEnseignantsTest() {
        boolean success = true;

        success &= creerEnseignantTest("Diop", "Ibrahima", "diop.ibrahima@uasz.sn", "+221771012233", "181185/D", Grade.PROFESSEUR_ASSIMILE, null, ADMIN_USER);
        success &= creerEnseignantTest("Fall", "Modou", "fall.modou@uasz.sn", "+221772023344", "181186/A", Grade.VACATAIRE, null, ADMIN_USER);
        success &= creerEnseignantTest("Ndiaye", "Ibrahima", "ndiaye.ibrahima@uasz.sn", "+221773034455", "181187/B", Grade.VACATAIRE, null, ADMIN_USER);

        if (success) {
            logger.info("\n=== Tous les enseignants ont été insérés avec succès !");
        } else {
            logger.warn("\n=== Certains enseignants n'ont pas pu être insérés !");
        }
    }
}