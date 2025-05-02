package sn.uasz.utilisateursapi.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.services.EtudiantService;

@Configuration
@RequiredArgsConstructor
public class EtudiantDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(EtudiantDataInitializer.class);

    private final EtudiantService etudiantService;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            try {
                // Ajouter 5 vacataires de test
                EtudiantDTO etu1 = new EtudiantDTO();
                etu1.setNom("Dupont");
                etu1.setPrenom("Jean");
                etu1.setMatricule("202202898");
                etudiantService.ajouterEtudiant(etu1);

                EtudiantDTO etu2 = new EtudiantDTO();
                etu2.setNom("Martin");
                etu2.setPrenom("Marie");
                etu2.setMatricule("marie.martin@example.com");
                etudiantService.ajouterEtudiant(etu2);

                EtudiantDTO etu3 = new EtudiantDTO();
                etu3.setNom("Martin");
                etu3.setPrenom("Marie");
                etu3.setMatricule("marie.martin@example.com");
                etudiantService.ajouterEtudiant(etu3);



                logger.info("\n=== les etudiants ont été ajoutés avec succès ===\n");
            } catch (Exception e) {
                logger.error("Erreur lors de l'initialisation des données : {}", e.getMessage(), e);
                throw new sn.uasz.utilisateursapi.exceptions.EtudiantDataInitializationException("Échec lors de l'initialisation des étudiants", e);
            }
        };
    }
}
