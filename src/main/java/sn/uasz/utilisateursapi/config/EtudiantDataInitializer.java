package sn.uasz.utilisateursapi.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.services.EtudiantService;


@Configuration
@RequiredArgsConstructor
public class EtudiantDataInitializer {

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



                System.out.println("\n=== les etudiants ont été ajoutés avec succès ===\n");
            } catch (Exception e) {
                System.err.println("Erreur lors de l'initialisation des données : " + e.getMessage());
                throw e;
            }
        };
    }
}
