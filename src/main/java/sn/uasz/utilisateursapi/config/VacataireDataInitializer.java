package sn.uasz.utilisateursapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.services.VacataireService;

@Configuration
@RequiredArgsConstructor
public class VacataireDataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(VacataireDataInitializer.class);
    private final VacataireService vacataireService;

    @Override
    public void run(String... args) {
        verifierCreationVacataires();
    }

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
