package sn.uasz.utilisateursapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.services.EtudiantService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantDataInitializerTest {

    private EtudiantService etudiantService;
    private EtudiantDataInitializer initializer;

    @BeforeEach
    void setUp() {
        etudiantService = mock(EtudiantService.class);
        initializer = new EtudiantDataInitializer(etudiantService);
    }

    @Test
    void testCreerEtudiantTest_success() {
        boolean result = initializer.ajouterEtudiantTest("Dupont", "Jean", "202202898","photo1.jpg");
        assertTrue(result);
        verify(etudiantService, times(1)).ajouterEtudiant(any(EtudiantDTO.class));
    }

    @Test
    void testAjouterEtudiantTest_failure() {
        doThrow(new RuntimeException("Erreur simulée")).when(etudiantService).ajouterEtudiant(any());
        boolean result = initializer.ajouterEtudiantTest("Erreur", "Test", "fail@test.com","Erreur");
        assertFalse(result);
    }

    @Test
    void testVerifierCreationEtudiant_success() {
        boolean result = initializer.verifierCreationEtudiant();
        assertTrue(result);
        verify(etudiantService, times(5)).ajouterEtudiant(any(EtudiantDTO.class));
    }

    @Test
    void testVerifierCreationEtudiant_partialFailure() {
        doThrow(new RuntimeException("Erreur simulée")).when(etudiantService)
                .ajouterEtudiant(argThat(dto -> "Pierre".equals(dto.getPrenom())));
        boolean result = initializer.verifierCreationEtudiant();
        assertFalse(result);
    }
}
