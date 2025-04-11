package sn.uasz.UtilisateursAPI.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.uasz.UtilisateursAPI.dtos.VacataireDTO;
import sn.uasz.UtilisateursAPI.services.VacataireService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class VacataireDataInitializer {
    
    private final VacataireService vacataireService;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            try {
                // Ajouter 5 vacataires de test
                VacataireDTO vacataire1 = new VacataireDTO();
                vacataire1.setNom("Dupont");
                vacataire1.setPrenom("Jean");
                vacataire1.setEmail("jean.dupont@example.com");
                vacataire1.setTelephone("0123456789");
                vacataire1.setSpecialite("Mathématiques");
                vacataire1.setActif(true);
                vacataireService.ajouterVacataire(vacataire1);
                
                VacataireDTO vacataire2 = new VacataireDTO();
                vacataire2.setNom("Martin");
                vacataire2.setPrenom("Marie");
                vacataire2.setEmail("marie.martin@example.com");
                vacataire2.setTelephone("0123456788");
                vacataire2.setSpecialite("Français");
                vacataire2.setActif(true);
                vacataireService.ajouterVacataire(vacataire2);
                
                VacataireDTO vacataire3 = new VacataireDTO();
                vacataire3.setNom("Dubois");
                vacataire3.setPrenom("Pierre");
                vacataire3.setEmail("pierre.dubois@example.com");
                vacataire3.setTelephone("0123456787");
                vacataire3.setSpecialite("Histoire");
                vacataire3.setActif(true);
                vacataireService.ajouterVacataire(vacataire3);
                
                VacataireDTO vacataire4 = new VacataireDTO();
                vacataire4.setNom("Lefebvre");
                vacataire4.setPrenom("Sophie");
                vacataire4.setEmail("sophie.lefebvre@example.com");
                vacataire4.setTelephone("0123456786");
                vacataire4.setSpecialite("Anglais");
                vacataire4.setActif(true);
                vacataireService.ajouterVacataire(vacataire4);
                
                VacataireDTO vacataire5 = new VacataireDTO();
                vacataire5.setNom("Moreau");
                vacataire5.setPrenom("Thomas");
                vacataire5.setEmail("thomas.moreau@example.com");
                vacataire5.setTelephone("0123456785");
                vacataire5.setSpecialite("Physique");
                vacataire5.setActif(true);
                vacataireService.ajouterVacataire(vacataire5);
                
                System.out.println("\n=== 5 vacataires ont été ajoutés avec succès ===\n");
            } catch (Exception e) {
                System.err.println("Erreur lors de l'initialisation des données : " + e.getMessage());
                throw e;
            }
        };
    }
}
