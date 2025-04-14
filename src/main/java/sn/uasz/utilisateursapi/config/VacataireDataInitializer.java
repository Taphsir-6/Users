package sn.uasz.utilisateursapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.services.VacataireService;

/**
 * Initialiseur de données pour les vacataires.
 * Cette classe charge les données initiales des vacataires dans la base de données.
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 * 
 * @see VacataireService
 */
@Configuration
@RequiredArgsConstructor
public class VacataireDataInitializer implements CommandLineRunner {
    private final VacataireService vacataireService;

    /**
     * Initialise les données des vacataires dans la base de données.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés)
     */
    @Override
    public void run(String... args) {
        try {
            // Création des vacataires de test
            creerVacataireTest("Dupont", "Jean", "jean.dupont@univ-avignon.fr", "+33612345678", "Mathématiques");
            creerVacataireTest("Martin", "Marie", "marie.martin@univ-avignon.fr", "+33623456789", "Français");
            creerVacataireTest("Leroy", "Pierre", "pierre.leroy@univ-avignon.fr", "+33634567890", "Histoire");
            creerVacataireTest("Dubois", "Sophie", "sophie.dubois@univ-avignon.fr", "+33645678901", "Anglais");
            creerVacataireTest("Moreau", "Thomas", "thomas.moreau@univ-avignon.fr", "+33656789012", "Physique");
            
            System.out.println("\n=== 5 vacataires ont été ajoutés avec succès ===\n");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation des données : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Crée un vacataire de test avec les données fournies.
     * 
     * @param nom Le nom du vacataire
     * @param prenom Le prénom du vacataire
     * @param email L'email du vacataire
     * @param telephone Le numéro de téléphone du vacataire
     * @param specialite La spécialité du vacataire
     */
    private void creerVacataireTest(String nom, String prenom, String email, String telephone, String specialite) {
        VacataireDTO vacataireDTO = new VacataireDTO();
        vacataireDTO.setNom(nom);
        vacataireDTO.setPrenom(prenom);
        vacataireDTO.setEmail(email);
        vacataireDTO.setTelephone(telephone);
        vacataireDTO.setSpecialite(specialite);
        vacataireDTO.setActif(true);
        
        vacataireService.creerVacataire(vacataireDTO);
    }
}
