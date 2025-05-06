package sn.uasz.utilisateursapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.enums.Grade;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;
import sn.uasz.utilisateursapi.services.EnseignantService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnseignantDataInitializerTest {

    private EnseignantService enseignantService;
    private EnseignantRepository enseignantRepository;
    private EnseignantDataInitializer initializer;

    @BeforeEach
    void setUp() {
        enseignantService = mock(EnseignantService.class);
        enseignantRepository = mock(EnseignantRepository.class);
        initializer = new EnseignantDataInitializer(enseignantService, enseignantRepository);
    }

    @Test
    void testCreerEnseignantTest_success() {
        when(enseignantRepository.existsByEmail("test@uasz.sn")).thenReturn(false);

        boolean result = invokeCreerEnseignant(
                "Sall", "Ousmane", "test@uasz.sn", "+221771112233",
                "123456/A", Grade.PROFESSEUR_ASSIMILE, "testuser"
        );

        assertTrue(result);
        verify(enseignantRepository, times(1)).existsByEmail("test@uasz.sn");
        verify(enseignantService, times(1)).ajouterEnseignant(any(EnseignantDTO.class));
    }

    @Test
    void testCreerEnseignantTest_alreadyExists() {
        when(enseignantRepository.existsByEmail("exist@uasz.sn")).thenReturn(true);

        boolean result = invokeCreerEnseignant(
                "Sow", "Abdou", "exist@uasz.sn", "+221771234567",
                "654321/B", Grade.VACATAIRE, "testuser"
        );

        assertTrue(result);  // La méthode retourne true même si on ne recrée pas
        verify(enseignantService, never()).ajouterEnseignant(any());
    }

    @Test
    void testCreerEnseignantTest_failure() {
        when(enseignantRepository.existsByEmail("fail@uasz.sn")).thenReturn(false);
        doThrow(new RuntimeException("Erreur simulée")).when(enseignantService).ajouterEnseignant(any());

        boolean result = invokeCreerEnseignant(
                "Ndiaye", "Aissatou", "fail@uasz.sn", "+221779998877",
                "999999/Z", Grade.PROFESSEUR, "admin"
        );

        assertFalse(result);
        verify(enseignantService, times(1)).ajouterEnseignant(any());
    }

    // 🔧 Hack pour appeler la méthode protégée (ou privée via réflexion si besoin)
    private boolean invokeCreerEnseignant(String nom, String prenom, String email,
                                          String telephone, String matricule,
                                          Grade grade, String createdBy) {
        try {
            var method = EnseignantDataInitializer.class.getDeclaredMethod(
                    "creerEnseignantTest", String.class, String.class, String.class,
                    String.class, String.class, Grade.class, String.class
            );
            method.setAccessible(true);
            return (boolean) method.invoke(initializer, nom, prenom, email, telephone, matricule, grade, createdBy);
        } catch (Exception e) {
            throw new RuntimeException("Échec d'invocation de creerEnseignantTest", e);
        }
    }
}
