package sn.uasz.utilisateursapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.services.VacataireService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour {@link VacataireDataInitializer}.
 * <p>
 * Vérifie le bon fonctionnement de la création de vacataires de test lors de l'initialisation des données.
 * Utilise un mock du service {@link VacataireService} pour isoler la logique d'initialisation.
 * </p>
 * <p>
 * Auteur : Omar DIOP
 * Date de dernière modification : 24 avril 2025
 * </p>
 */
class VacataireDataInitializerTest {

    private VacataireService vacataireService;
    private VacataireDataInitializer initializer;

    /**
     * Initialisation du test : création d'un mock du service {@link VacataireService} et de l'initialiseur.
     */
    @BeforeEach
    void setUp() {
        vacataireService = mock(VacataireService.class);
        initializer = new VacataireDataInitializer(vacataireService);
    }

    /**
     * Teste la création d'un vacataire de test avec succès.
     * Vérifie que la méthode retourne true et que le service est bien appelé.
     */
    @Test
    void testCreerVacataireTest_success() {
        boolean result = initializer.creerVacataireTest("Jean", "Dupont", "jean.dupont@univ.fr", "0600000000", "Math");
        assertTrue(result);
        verify(vacataireService, times(1)).creerVacataire(any(VacataireDTO.class));
    }

    /**
     * Teste la création d'un vacataire de test en cas d'échec (exception levée).
     * Vérifie que la méthode retourne false lorsque le service lève une exception.
     */
    @Test
    void testCreerVacataireTest_failure() {
        doThrow(new RuntimeException("Erreur simulée")).when(vacataireService).creerVacataire(any());
        boolean result = initializer.creerVacataireTest("Erreur", "Test", "fail@test.com", "0600000000", "Erreur");
        assertFalse(result);
    }

    /**
     * Teste la vérification de la création des vacataires de test avec succès.
     * Vérifie que la méthode retourne true et que le service est appelé le nombre attendu de fois.
     */
    @Test
    void testVerifierCreationVacataires_success() {
        boolean result = initializer.verifierCreationVacataires();
        assertTrue(result);
        verify(vacataireService, times(5)).creerVacataire(any(VacataireDTO.class));
    }

    /**
     * Teste la vérification de la création des vacataires de test en cas d'échec partiel (exception levée pour un vacataire).
     * Vérifie que la méthode retourne false lorsque le service lève une exception pour un vacataire.
     */
    @Test
    void testVerifierCreationVacataires_partialFailure() {
        doThrow(new RuntimeException("Erreur simulée")).when(vacataireService)
                .creerVacataire(argThat(dto -> "Pierre".equals(dto.getPrenom())));
        boolean result = initializer.verifierCreationVacataires();
        assertFalse(result);
    }
}
