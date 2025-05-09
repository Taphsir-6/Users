package sn.uasz.utilisateursapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.services.EtudiantService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de test pour la configuration d'initialisation des données étudiant.
 * Elle teste l'ajout d'étudiants et la méthode de vérification globale.
 *
 * Auteur : KOUMBA THIONGANE
 * Date : 07 MAI 2025
 */
class EtudiantDataInitializerTest {

    private EtudiantService etudiantService;
    private EtudiantDataInitializer initializer;

    /**
     * Initialisation des mocks et de la classe à tester avant chaque test.
     */
    @BeforeEach
    void setUp() {
        etudiantService = mock(EtudiantService.class);
        initializer = new EtudiantDataInitializer(etudiantService);
    }

    /**
     * Teste le succès de l'ajout d'un étudiant.
     */
    @Test
    void testAjouterEtudiantTest_success() {
        boolean result = initializer.ajouterEtudiantTest(
                "Dupont", "Jean", "202202898", LocalDate.of(2000, 5, 12), "Dakar", "jean.dupont@exemple.com"
        );
        assertTrue(result, "L'étudiant devrait être ajouté avec succès.");
        verify(etudiantService, times(1)).ajouterEtudiant(any(EtudiantDTO.class));
    }

    /**
     * Teste un échec simulé lors de l'ajout d'un étudiant.
     */
    @Test
    void testAjouterEtudiantTest_failure() {
        doThrow(new RuntimeException("Erreur simulée")).when(etudiantService).ajouterEtudiant(any());
        boolean result = initializer.ajouterEtudiantTest(
                "Erreur", "Test", "000000000", LocalDate.of(2000, 1, 1), "ErreurVille", "erreur.test@exemple.com"
        );
        assertFalse(result, "L'ajout devrait échouer à cause de l'exception simulée.");
    }

    /**
     * Vérifie que la création automatique de 5 étudiants réussit.
     */
    @Test
    void testVerifierCreationEtudiant_success() {
        boolean result = initializer.verifierCreationEtudiant();
        assertTrue(result, "Tous les étudiants doivent être ajoutés avec succès.");
        verify(etudiantService, times(5)).ajouterEtudiant(any(EtudiantDTO.class));
    }

    /**
     * Simule un échec partiel pendant la création automatique d'étudiants.
     */
    @Test
    void testVerifierCreationEtudiant_partialFailure() {
        doThrow(new RuntimeException("Erreur simulée")).when(etudiantService)
                .ajouterEtudiant(argThat(dto -> "Pierre".equals(dto.getPrenom())));
        boolean result = initializer.verifierCreationEtudiant();
        assertFalse(result, "La création devrait échouer partiellement à cause de 'Pierre'.");
    }
}
