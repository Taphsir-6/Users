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
    private EtudiantService etudiantService;

    @InjectMocks
    private EtudiantController etudiantController;

    private EtudiantDTO etudiantDTO;

    @BeforeEach
    void setUp() {
        etudiantDTO = new EtudiantDTO();
        etudiantDTO.setId(1L);
        etudiantDTO.setNom("Dupont");
        etudiantDTO.setPrenom("Jean");
        etudiantDTO.setMatricule("202202898");
        etudiantDTO.setPhoto("photo1.jpg");
        etudiantDTO.setEmail("dupontjj45@zig.univ.sn");
    }

    @Test
    void getEtudiantById_shouldReturnEtudiant() {
        // Arrange
        when(etudiantService.obtenirEtudiant(1)).thenReturn(etudiantDTO);

        // Act
        ResponseEntity<EtudiantDTO> response = etudiantController.getEtudiantById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dupont", response.getBody().getNom());
        verify(etudiantService, times(1)).obtenirEtudiant(1);
    }

    @Test
    void getAllEtudiants_shouldReturnListOfEtudiants() {
        // Arrange
        List<EtudiantDTO> etudiants = Arrays.asList(
                etudiantDTO,
                new EtudiantDTO(2L, "Martin", "Marie", "202202899", "photo2.jpg", "martinmm78@zig.univ.sn")
        );
        when(etudiantService.findAll()).thenReturn(etudiants);

        // Act
        ResponseEntity<List<EtudiantDTO>> response = etudiantController.getAllEtudiants();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(etudiantService, times(1)).findAll();
    }

    @Test
    void createEtudiant_shouldReturnCreatedEtudiant() {
        // Arrange
        when(etudiantService.ajouterEtudiant(any(EtudiantDTO.class))).thenReturn(etudiantDTO);

        // Act
        ResponseEntity<EtudiantDTO> response = etudiantController.createEtudiant(etudiantDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dupont", response.getBody().getNom());
        verify(etudiantService, times(1)).ajouterEtudiant(any(EtudiantDTO.class));
    }

    @Test
    void updateEtudiant_shouldReturnUpdatedEtudiant() {
        // Arrange
        EtudiantDTO updatedEtudiant = new EtudiantDTO();
        updatedEtudiant.setNom("Dupont-Updated");
        updatedEtudiant.setPrenom("Jean-Updated");

        when(etudiantService.modifierEtudiant(anyInt(), any(EtudiantDTO.class))).thenReturn(updatedEtudiant);

        // Act
        ResponseEntity<EtudiantDTO> response = etudiantController.updateEtudiant(1L, updatedEtudiant);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dupont-Updated", response.getBody().getNom());
        verify(etudiantService, times(1)).modifierEtudiant(anyInt(), any(EtudiantDTO.class));
    }

    @Test
    void deleteEtudiant_shouldReturnOk() {
        // Arrange
        doNothing().when(etudiantService).supprimerEtudiant(anyInt());

        // Act
        ResponseEntity<Void> response = etudiantController.deleteEtudiant(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(etudiantService, times(1)).supprimerEtudiant(anyInt());
    }

    @Test
    void handleException_shouldReturnBadRequest() {
        // Arrange
        RuntimeException exception = new RuntimeException("Étudiant non trouvé");

        // Act
        ResponseEntity<Map<String, String>> response = etudiantController.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Étudiant non trouvé", response.getBody().get("message"));
    }
}