package sn.uasz.utilisateursapi.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.services.EtudiantService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantControllerTest {

    @Mock
    private EtudiantService etudiantService; // Simulation du service métier

    @InjectMocks
    private EtudiantController etudiantController; // Contrôleur à tester

    private EtudiantDTO etudiantDTO; // Données de test

    /**
     * Initialisation des données avant chaque test.
     */
    @BeforeEach
    void setUp() {
        etudiantDTO = new EtudiantDTO();
        etudiantDTO.setId(1L);
        etudiantDTO.setNom("Dupont");
        etudiantDTO.setPrenom("Jean");
        etudiantDTO.setMatricule("202202898");
        etudiantDTO.setEmail("dupontjj45@zig.univ.sn");
        etudiantDTO.setDateNaissance(LocalDate.of(2000, 1, 15));
        etudiantDTO.setLieuNaissance("Ziguinchor");
    }

    /**
     * Teste la récupération d’un étudiant par son ID.
     * Vérifie que le contrôleur retourne l'étudiant avec un statut HTTP 200 (OK).
     */
    @Test
    void getEtudiantById_shouldReturnEtudiant() {
        when(etudiantService.obtenirEtudiant(1)).thenReturn(etudiantDTO);

        ResponseEntity<EtudiantDTO> response = etudiantController.getEtudiantById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dupont", response.getBody().getNom());
        verify(etudiantService, times(1)).obtenirEtudiant(1);
    }

    /**
     * Teste la récupération de la liste de tous les étudiants.
     * Vérifie que le contrôleur retourne bien tous les étudiants avec un statut HTTP 200 (OK).
     */

    /**
     * Teste la création d’un étudiant via le contrôleur.
     * Vérifie que le DTO retourné correspond aux données fournies.
     */
    @Test
    void createEtudiant_shouldReturnCreatedEtudiant() {
        when(etudiantService.ajouterEtudiant(any(EtudiantDTO.class))).thenReturn(etudiantDTO);

        ResponseEntity<EtudiantDTO> response = etudiantController.createEtudiant(etudiantDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dupont", response.getBody().getNom());
        verify(etudiantService, times(1)).ajouterEtudiant(any(EtudiantDTO.class));
    }

    /**
     * Teste la mise à jour d’un étudiant.
     * Vérifie que les nouvelles informations sont bien prises en compte.
     */
    @Test
    void updateEtudiant_shouldReturnUpdatedEtudiant() {
        EtudiantDTO updatedEtudiant = new EtudiantDTO();
        updatedEtudiant.setNom("Dupont-Updated");
        updatedEtudiant.setPrenom("Jean-Updated");
        updatedEtudiant.setDateNaissance(LocalDate.of(2000, 2, 2));
        updatedEtudiant.setLieuNaissance("Thiès");

        when(etudiantService.modifierEtudiant(anyInt(), any(EtudiantDTO.class))).thenReturn(updatedEtudiant);

        ResponseEntity<EtudiantDTO> response = etudiantController.updateEtudiant(1L, updatedEtudiant);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dupont-Updated", response.getBody().getNom());
        verify(etudiantService, times(1)).modifierEtudiant(anyInt(), any(EtudiantDTO.class));
    }

    /**
     * Teste la suppression d’un étudiant.
     * Vérifie que la méthode du service est bien appelée et retourne un statut HTTP 200.
     */
    @Test
    void deleteEtudiant_shouldReturnOk() {
        doNothing().when(etudiantService).supprimerEtudiant(anyInt());

        ResponseEntity<Void> response = etudiantController.deleteEtudiant(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(etudiantService, times(1)).supprimerEtudiant(anyInt());
    }

    /**
     * Teste la gestion des exceptions dans le contrôleur.
     * Vérifie que le message d’erreur est bien encapsulé dans une réponse avec statut HTTP 400.
     */
    @Test
    void handleException_shouldReturnBadRequest() {
        RuntimeException exception = new RuntimeException("Étudiant non trouvé");

        ResponseEntity<Map<String, String>> response = etudiantController.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Étudiant non trouvé", response.getBody().get("message"));
    }
}
