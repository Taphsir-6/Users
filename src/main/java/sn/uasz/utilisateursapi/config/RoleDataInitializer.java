package sn.uasz.utilisateursapi.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.services.RoleService;

/**
 * Classe de configuration pour l'initialisation des rôles au démarrage de l'application.
 * <p>
 * Cette classe insère des rôles de test dans la base de données à chaque lancement,
 * ce qui facilite le développement, les tests et la démonstration de l'API.
 * </p>
 * <ul>
 *   <li>Crée des rôles via le service métier {@link RoleService}.</li>
 *   <li>Utilise des DTOs pour assurer la cohérence des données.</li>
 *   <li>Consigne les opérations dans les logs.</li>
 * </ul>
 *
 * Auteur : Koumba Thiongane<br>
 * Date de dernière modification : 07 mai 2025
 */
@Configuration
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

    // Logger pour les opérations d’initialisation
    private static final Logger logger = LoggerFactory.getLogger(RoleDataInitializer.class);

    // Service pour la gestion des rôles
    private final RoleService roleService;

    @Override
    public void run(String... args) {
        verifierCreationRoles();
    }

    /**
     * Ajoute un rôle de test dans la base de données.
     *
     * @param libelle     Libellé du rôle (ex. : ADMIN, ETUDIANT)
     * @param description Description du rôle
     * @return true si le rôle est ajouté avec succès, false sinon
     */
    public boolean ajouterRoleTest(String libelle, String description) {
        try {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setLibelle(libelle);
            roleDTO.setDescription(description);

            roleService.ajouterRole(roleDTO);
            return true;
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout du rôle {} : {}", libelle, e.getMessage());
            return false;
        }
    }

    /**
     * Crée plusieurs rôles de test pour initialiser la base.
     *
     * @return true si tous les rôles ont été ajoutés avec succès, false sinon
     */
    public boolean verifierCreationRoles() {
        boolean success = true;

        success &= ajouterRoleTest("ADMIN", "Administrateur ayant tous les droits.");
        success &= ajouterRoleTest("ETUDIANT", "Utilisateur étudiant ayant un accès limité.");
        success &= ajouterRoleTest("ENSEIGNANT", "Membre du personnel enseignant.");
        success &= ajouterRoleTest("RESPONSABLE", "Responsable pédagogique ou administratif.");
        success &= ajouterRoleTest("VISITEUR", "Utilisateur sans compte permanent.");

        if (success) {
            logger.info("\n=== 5 rôles ont été ajoutés avec succès ===\n");
        } else {
            logger.warn("\n=== Certains rôles n'ont pas pu être créés ===\n");
        }

        return success;
    }
}
