package sn.uasz.utilisateursapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.services.VacataireService;

/**
 * Classe de configuration pour l'initialisation des données de vacataires au démarrage de l'application.
 * <p>
 * Cette classe permet d'insérer des vacataires de test dans la base de données à chaque lancement,
 * facilitant ainsi le développement, les tests et la démonstration de l'API.
 * </p>
 * <ul>
 *   <li>Crée des vacataires via le service métier {@link VacataireService}.</li>
 *   <li>Utilise des DTOs pour garantir la cohérence des données.</li>
 *   <li>Consigne les opérations dans les logs pour le suivi.</li>
 * </ul>
 * <p>
 * Auteur : Omar DIOP
 * Date de dernière modification : 24 avril 2025
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class VacataireDataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(VacataireDataInitializer.class);
    private final VacataireService vacataireService;

    /**
     * Méthode appelée automatiquement au démarrage de l'application.
     * <p>
     * Elle vérifie et crée des vacataires de test si nécessaire.
     * </p>
     *
     * @param args Arguments de la ligne de commande (ignorés ici)
     */
    @Override
    public void run(String... args) {
        verifierCreationVacataires();
    }

    /**
     * Crée un vacataire de test et l'ajoute à la base de données via le service métier.
     * <p>
     * Cette méthode construit un objet {@link VacataireDTO} avec les informations fournies
     * et l'enregistre via {@link VacataireService#creerVacataire(sn.uasz.utilisateursapi.dtos.VacataireDTO)}.
     * </p>
     *
     * @param nom        Nom du vacataire
     * @param prenom     Prénom du vacataire
     * @param email      Adresse email du vacataire
     * @param telephone  Numéro de téléphone du vacataire
     * @param specialite Spécialité principale du vacataire
     * @return true si la création a réussi, false sinon
     */
    public boolean creerVacataireTest(String nom, String prenom, String email, String telephone, String specialite) {
        try {
            VacataireDTO vacataireDTO = new VacataireDTO();
            vacataireDTO.setNom(nom);
            vacataireDTO.setPrenom(prenom);
            vacataireDTO.setEmail(email);
            vacataireDTO.setTelephone(telephone);
            vacataireDTO.setSpecialite(specialite);
            vacataireDTO.setActif(true);

            vacataireService.creerVacataire(vacataireDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifierCreationVacataires() {
        boolean success = true;
        success &= creerVacataireTest("Dupont", "Jean", "jean.dupont@univ-avignon.fr", "+33612345678", "Mathématiques");
        success &= creerVacataireTest("Martin", "Marie", "marie.martin@univ-avignon.fr", "+33623456789", "Français");
        success &= creerVacataireTest("Leroy", "Pierre", "pierre.leroy@univ-avignon.fr", "+33634567890", "Histoire");
        success &= creerVacataireTest("Dubois", "Sophie", "sophie.dubois@univ-avignon.fr", "+33645678901", "Anglais");
        success &= creerVacataireTest("Moreau", "Thomas", "thomas.moreau@univ-avignon.fr", "+33656789012", "Physique");

        if (success) {
            logger.info("\n=== 5 vacataires ont été ajoutés avec succès ===\n");
        } else {
            logger.warn("\n=== Certains vacataires n'ont pas pu être créés ===\n");
        }
        return success;
    }
}
