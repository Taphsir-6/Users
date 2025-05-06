package sn.uasz.utilisateursapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.services.EtudiantService;

@Configuration
@RequiredArgsConstructor
public class EtudiantDataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(EtudiantDataInitializer.class);
    private final EtudiantService etudiantService;

    @Override
    public void run(String... args) {
        verifierCreationEtudiant();
    }

    public boolean ajouterEtudiantTest(String nom, String prenom, String matricule, String photo) {
        try {
            EtudiantDTO etudiantDTO = new EtudiantDTO();
            etudiantDTO.setNom(nom);
            etudiantDTO.setPrenom(prenom);
            etudiantDTO.setMatricule(matricule);
            etudiantDTO.setPhoto(photo);

            etudiantService.ajouterEtudiant(etudiantDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifierCreationEtudiant() {
        boolean success = true;
        success &= ajouterEtudiantTest("Dupont", "Jean", "202202898", "photo1.jpg");
        success &= ajouterEtudiantTest("Martin", "Marie", "202202899", "photo2.jpg");
        success &= ajouterEtudiantTest("Leroy", "Pierre", "202202900", "photo3.jpg");
        success &= ajouterEtudiantTest("Dubois", "Sophie", "202202901", "photo4.jpg");
        success &= ajouterEtudiantTest("Moreau", "Thomas", "202202902", "photo5.jpg");

        if (success) {
            logger.info("\n=== 5 étudiants ont été ajoutés avec succès ===\n");
        } else {
            logger.warn("\n=== Certains étudiants n'ont pas pu être créés ===\n");
        }
        return success;
    }
}
