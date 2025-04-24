package sn.uasz.utilisateursapi.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;
import sn.uasz.utilisateursapi.services.EnseignantService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class EnseignantDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(EnseignantDataInitializer.class);

    private final EnseignantService enseignantService;
    private final EnseignantRepository enseignantRepository;

    @Override
    public void run(String... args) {
        insererEnseignantsTest();
    }

    private boolean creerEnseignantTest(String nom, String prenom, String email, String telephone, String matricule, String grade, String createdBy) {
        try {
            if (enseignantRepository.existsByEmail(email)) {
                logger.info("🔁 Enseignant avec l'email {} existe déjà. Insertion ignorée.", email);
                return true;
            }

            EnseignantDTO dto = new EnseignantDTO(
                    null,
                    nom,
                    prenom,
                    email,
                    telephone,
                    matricule,
                    grade,
                    createdBy,
                    LocalDate.now(),
                    true
            );

            enseignantService.ajouterEnseignant(dto);
            logger.info("✅ Enseignant {} {} inséré avec succès.", prenom, nom);
            return true;

        } catch (Exception e) {
            logger.error("❌ Échec d'insertion pour {} {} : {}", prenom, nom, e.getMessage(), e);
            return false;
        }
    }

    private void insererEnseignantsTest() {
        boolean success = true;

        success &= creerEnseignantTest("Diop", "Ibrahima", "diop.ibrahima@uasz.sn", "+221771012233", "181185/D", "Professeur Assimilé", "admin");
        success &= creerEnseignantTest("Fall", "Modou", "fall.modou@uasz.sn", "+221772023344", "181186/A", "Vacataire", "admin");
        success &= creerEnseignantTest("Ndiaye", "Ibrahima", "ndiaye.ibrahima@uasz.sn", "+221773034455", "181187/B", "Vacataire", "admin");

        if (success) {
            logger.info("\n🎉 Tous les enseignants ont été insérés avec succès !");
        } else {
            logger.warn("\n⚠️ Certains enseignants n'ont pas pu être insérés !");
        }
    }
}
