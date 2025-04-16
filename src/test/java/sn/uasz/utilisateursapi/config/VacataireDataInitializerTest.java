package sn.uasz.utilisateursapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.services.VacataireService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VacataireDataInitializerTest {

    private VacataireService vacataireService;
    private VacataireDataInitializer initializer;

    @BeforeEach
    void setUp() {
        vacataireService = mock(VacataireService.class);
        initializer = new VacataireDataInitializer(vacataireService);
    }

    @Test
    void testCreerVacataireTest_success() {
        boolean result = initializer.creerVacataireTest("Diop", "Omar", "diop.omar@univ.fr", "0600000000", "Math");
        assertTrue(result);
        verify(vacataireService, times(1)).creerVacataire(any(VacataireDTO.class));
    }

    @Test
    void testCreerVacataireTest_failure() {
        doThrow(new RuntimeException("Erreur simulée")).when(vacataireService).creerVacataire(any());
        boolean result = initializer.creerVacataireTest("Erreur", "Test", "fail@test.com", "0600000000", "Erreur");
        assertFalse(result);
    }

    @Test
    void testVerifierCreationVacataires_success() {
        boolean result = initializer.verifierCreationVacataires();
        assertTrue(result);
        verify(vacataireService, times(5)).creerVacataire(any(VacataireDTO.class));
    }

    @Test
    void testVerifierCreationVacataires_partialFailure() {
        doThrow(new RuntimeException("Erreur simulée")).when(vacataireService)
                .creerVacataire(argThat(dto -> "Babacar".equals(dto.getPrenom())));
        boolean result = initializer.verifierCreationVacataires();
        assertFalse(result);
    }
}
