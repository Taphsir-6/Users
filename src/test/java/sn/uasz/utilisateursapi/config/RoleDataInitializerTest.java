package sn.uasz.utilisateursapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.services.RoleService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Classe de test pour RoleDataInitializer.
 *
 * Auteur : Koumba Thiongane
 * Date : 07 mai 2025
 */
class RoleDataInitializerTest {

    private RoleService roleService;
    private RoleDataInitializer initializer;

    @BeforeEach
    void setUp() {
        roleService = mock(RoleService.class);
        initializer = new RoleDataInitializer(roleService);
    }

    /**
     * Teste le succès de l'ajout d'un rôle.
     */
    @Test
    void testAjouterRoleTest_success() {
        boolean result = initializer.ajouterRoleTest("ADMIN", "Administrateur avec tous les droits");
        assertTrue(result, "Le rôle devrait être ajouté avec succès.");
        verify(roleService, times(1)).ajouterRole(any(RoleDTO.class));
    }

    /**
     * Teste un échec simulé lors de l'ajout d'un rôle.
     */
    @Test
    void testAjouterRoleTest_failure() {
        doThrow(new RuntimeException("Erreur simulée")).when(roleService).ajouterRole(any(RoleDTO.class));
        boolean result = initializer.ajouterRoleTest("ETUDIANT", "Erreur simulée");
        assertFalse(result, "L'ajout devrait échouer à cause de l'exception simulée.");
    }

    /**
     * Teste la création de plusieurs rôles avec succès.
     */
    @Test
    void testVerifierCreationRoles_success() {
        boolean result = initializer.verifierCreationRoles();
        assertTrue(result, "Tous les rôles doivent être ajoutés avec succès.");
        verify(roleService, times(5)).ajouterRole(any(RoleDTO.class));
    }

    /**
     * Simule un échec partiel pendant la création de rôles.
     */
    @Test
    void testVerifierCreationRoles_partialFailure() {
        doThrow(new RuntimeException("Erreur simulée")).when(roleService)
                .ajouterRole(argThat(role -> "ENSEIGNANT".equals(role.getLibelle())));
        boolean result = initializer.verifierCreationRoles();
        assertFalse(result, "L'ajout des rôles devrait échouer partiellement.");
    }
}
