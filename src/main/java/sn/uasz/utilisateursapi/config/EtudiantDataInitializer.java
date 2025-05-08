package sn.uasz.utilisateursapi.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.services.EtudiantService;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe de configuration pour l'initialisation automatique des étudiants.
 * Elle est exécutée au démarrage de l'application pour injecter des données de test.
 *
 * Auteur : KOUMBA THIONGANE
 * Date   : 08 Mai 2025
 */
@Configuration
@RequiredArgsConstructor
public class EtudiantDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(EtudiantDataInitializer.class);
    private final EtudiantService etudiantService;

    @Override
    public void run(String... args) {
        verifierCreationEtudiant();
    }

    /**
     * Méthode pour ajouter un étudiant de test à la base de données.
     *
     * @param nom           Nom de l'étudiant
     * @param prenom        Prénom de l'étudiant
     * @param matricule     Matricule de l'étudiant
     * @param dateNaissance Date de naissance de l'étudiant
     * @param lieuNaissance Lieu de naissance de l'étudiant
     * @param email         Email de l'étudiant
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean ajouterEtudiantTest(String nom, String prenom, String matricule,
                                       LocalDate dateNaissance, String lieuNaissance, String email) {
        try {
            EtudiantDTO etudiantDTO = new EtudiantDTO();
            etudiantDTO.setNom(nom);
            etudiantDTO.setPrenom(prenom);
            etudiantDTO.setMatricule(matricule);
            etudiantDTO.setDateNaissance(dateNaissance);
            etudiantDTO.setLieuNaissance(lieuNaissance);
            etudiantDTO.setEmail(email); // Email requis
            etudiantDTO.setRoles(List.of()); // Aucun rôle par défaut

            etudiantService.ajouterEtudiant(etudiantDTO);
            logger.info("✅ Étudiant ajouté : {} {}", prenom, nom);
            return true;
        } catch (Exception e) {
            logger.error("❌ Erreur lors de l'ajout de l'étudiant {} {} : {}", prenom, nom, e.getMessage());
            return false;
        }
    }

    /**
     * Initialise plusieurs étudiants dans la base pour les tests.
     *
     * @return true si tous les étudiants ont été ajoutés, false sinon
     */
    public boolean verifierCreationEtudiant() {
        boolean success = true;

        success &= ajouterEtudiantTest("Dupont", "Jean", "202202898", LocalDate.of(2000, 5, 12), "Dakar", "jean.dupont@exemple.com");
        success &= ajouterEtudiantTest("Martin", "Marie", "202202899", LocalDate.of(2001, 3, 22), "Thiès", "marie.martin@exemple.com");
        success &= ajouterEtudiantTest("Leroy", "Pierre", "202202900", LocalDate.of(1999, 11, 30), "Ziguinchor", "pierre.leroy@exemple.com");
        success &= ajouterEtudiantTest("Dubois", "Sophie", "202202901", LocalDate.of(2002, 1, 8), "Saint-Louis", "sophie.dubois@exemple.com");
        success &= ajouterEtudiantTest("Moreau", "Thomas", "202202902", LocalDate.of(2000, 7, 15), "Kaolack", "thomas.moreau@exemple.com");

        if (success) {
            logger.info("\n=== ✅ 5 étudiants ont été ajoutés avec succès ===\n");
        } else {
            logger.warn("\n=== ⚠️ Certains étudiants n'ont pas pu être créés ===\n");
        }

        return success;
    }
}
