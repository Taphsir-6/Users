/**
 * Classe de test pour le service VacataireService.
 * Cette classe contient les tests unitaires pour toutes les opérations de gestion des vacataires.
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 */
package sn.uasz.utilisateursapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.entities.Vacataire;
import sn.uasz.utilisateursapi.repositories.VacataireRepository;
import sn.uasz.utilisateursapi.exceptions.VacataireNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacataireServiceTest {

    @Mock
    private VacataireRepository vacataireRepository;

    @InjectMocks
    private VacataireService vacataireService;

    private Vacataire vacataireTest;
    private VacataireDTO vacataireDTO;

    @BeforeEach
    void setUp() {
        vacataireTest = new Vacataire();
        vacataireTest.setId(1L);
        vacataireTest.setNom("Test");
        vacataireTest.setPrenom("User");
        vacataireTest.setEmail("test@example.com");
        vacataireTest.setActif(true);

        vacataireDTO = new VacataireDTO();
        vacataireDTO.setId(1L);
        vacataireDTO.setNom("Test");
        vacataireDTO.setPrenom("User");
        vacataireDTO.setEmail("test@example.com");
    }

    @Test
    void testCreerVacataire() {
        // Given
        Vacataire vacataireToSave = new Vacataire();
        vacataireToSave.setNom(vacataireDTO.getNom());
        vacataireToSave.setPrenom(vacataireDTO.getPrenom());
        vacataireToSave.setEmail(vacataireDTO.getEmail());
        vacataireToSave.setActif(true);

        when(vacataireRepository.save(any(Vacataire.class))).thenAnswer(invocation -> {
            Vacataire saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // When
        VacataireDTO result = vacataireService.creerVacataire(vacataireDTO);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(vacataireDTO.getNom(), result.getNom());
        assertEquals(vacataireDTO.getPrenom(), result.getPrenom());
        assertEquals(vacataireDTO.getEmail(), result.getEmail());
        verify(vacataireRepository, times(1)).save(any(Vacataire.class));
    }

    @Test
    void testGetVacataireExiste() {
        // Given
        when(vacataireRepository.findByIdAndActif(1L, true)).thenReturn(Optional.of(vacataireTest));

        // When
        VacataireDTO result = vacataireService.getVacataire(1L);

        // Then
        assertNotNull(result);
        assertEquals(vacataireTest.getNom(), result.getNom());
        verify(vacataireRepository, times(1)).findByIdAndActif(1L, true);
    }

    @Test
    void testGetVacataireNExistePas() {
        // Given
        when(vacataireRepository.findByIdAndActif(1L, true)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(VacataireNotFoundException.class, () -> {
            vacataireService.getVacataire(1L);
        });
        verify(vacataireRepository, times(1)).findByIdAndActif(1L, true);
    }

    @Test
    void testSupprimerVacataireExiste() {
        // Given
        Vacataire vacataireToDelete = new Vacataire();
        vacataireToDelete.setId(1L);
        vacataireToDelete.setNom("Test");
        vacataireToDelete.setPrenom("User");
        vacataireToDelete.setEmail("test@example.com");
        vacataireToDelete.setActif(true);

        when(vacataireRepository.findByIdAndActif(1L, true)).thenReturn(Optional.of(vacataireToDelete));
        doNothing().when(vacataireRepository).delete(any(Vacataire.class));

        // When
        boolean result = vacataireService.supprimerVacataire(1L);

        // Then
        assertTrue(result);
        verify(vacataireRepository, times(1)).findByIdAndActif(1L, true);
        verify(vacataireRepository, times(1)).delete(any(Vacataire.class));
    }

    @Test
    void testSupprimerVacataireNExistePas() {
        // Given
        when(vacataireRepository.findByIdAndActif(1L, true)).thenReturn(Optional.empty());

        // When
        boolean result = vacataireService.supprimerVacataire(1L);

        // Then
        assertFalse(result);
        verify(vacataireRepository, times(1)).findByIdAndActif(1L, true);
        verify(vacataireRepository, never()).delete(any(Vacataire.class));
    }

    @Test
    void testGetAllVacatairesActifs() {
        // Given
        Vacataire vacataire1 = new Vacataire();
        vacataire1.setId(1L);
        vacataire1.setNom("Test1");
        vacataire1.setPrenom("User1");
        vacataire1.setEmail("test1@example.com");
        vacataire1.setActif(true);

        Vacataire vacataire2 = new Vacataire();
        vacataire2.setId(2L);
        vacataire2.setNom("Test2");
        vacataire2.setPrenom("User2");
        vacataire2.setEmail("test2@example.com");
        vacataire2.setActif(true);

        when(vacataireRepository.findByActif(true)).thenReturn(List.of(vacataire1, vacataire2));

        // When
        List<VacataireDTO> result = vacataireService.getAllVacatairesActifs();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(v -> v.isActif()));
        verify(vacataireRepository, times(1)).findByActif(true);
    }

    @Test
    void testDesactiverVacataire() {
        // Given
        when(vacataireRepository.findByIdAndActif(1L, true)).thenReturn(Optional.of(vacataireTest));
        doAnswer(invocation -> {
            Vacataire saved = invocation.getArgument(0);
            saved.setActif(false);
            return saved;
        }).when(vacataireRepository).save(any(Vacataire.class));

        // When
        VacataireDTO result = vacataireService.desactiverVacataire(1L);

        // Then
        assertNotNull(result);
        assertFalse(result.isActif());
        verify(vacataireRepository, times(1)).findByIdAndActif(1L, true);
        verify(vacataireRepository, times(1)).save(any(Vacataire.class));
    }

    @Test
    void testDesactiverVacataireNonExistant() {
        // Given
        when(vacataireRepository.findByIdAndActif(1L, true)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(VacataireNotFoundException.class, () -> vacataireService.desactiverVacataire(1L));
        verify(vacataireRepository, times(1)).findByIdAndActif(1L, true);
        verify(vacataireRepository, never()).save(any(Vacataire.class));
    }

    @Test
    void testMettreAJourVacataire() {
        // Given
        when(vacataireRepository.findByIdAndActif(1L, true)).thenReturn(Optional.of(vacataireTest));
        doAnswer(invocation -> {
            Vacataire saved = invocation.getArgument(0);
            saved.setNom(vacataireDTO.getNom());
            saved.setPrenom(vacataireDTO.getPrenom());
            saved.setEmail(vacataireDTO.getEmail());
            saved.setTelephone(vacataireDTO.getTelephone());
            saved.setSpecialite(vacataireDTO.getSpecialite());
            saved.setActif(vacataireDTO.isActif());
            saved.setDateModification(new Date());
            return saved;
        }).when(vacataireRepository).save(any(Vacataire.class));

        // When
        VacataireDTO result = vacataireService.mettreAJourVacataire(1L, vacataireDTO);

        // Then
        assertNotNull(result);
        assertEquals(vacataireDTO.getNom(), result.getNom());
        assertEquals(vacataireDTO.getPrenom(), result.getPrenom());
        assertEquals(vacataireDTO.getEmail(), result.getEmail());
        assertEquals(vacataireDTO.getTelephone(), result.getTelephone());
        assertEquals(vacataireDTO.getSpecialite(), result.getSpecialite());
        assertEquals(vacataireDTO.isActif(), result.isActif());
        verify(vacataireRepository, times(1)).findByIdAndActif(1L, true);
        verify(vacataireRepository, times(1)).save(any(Vacataire.class));
    }

    @Test
    void testGetVacataireByEmailExiste() {
        // Given
        when(vacataireRepository.findByEmail("test@example.com")).thenReturn(vacataireTest);

        // When
        VacataireDTO result = vacataireService.getVacataireByEmail("test@example.com");

        // Then
        assertNotNull(result);
        assertEquals(vacataireTest.getNom(), result.getNom());
        assertEquals(vacataireTest.getPrenom(), result.getPrenom());
        assertEquals(vacataireTest.getEmail(), result.getEmail());
        verify(vacataireRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetVacataireByEmailNExistePas() {
        // Given
        when(vacataireRepository.findByEmail("test@example.com")).thenReturn(null);

        // When
        VacataireDTO result = vacataireService.getVacataireByEmail("test@example.com");

        // Then
        assertNull(result);
        verify(vacataireRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testActiverVacataireExiste() {
        // Given
        when(vacataireRepository.findById(1L)).thenReturn(Optional.of(vacataireTest));
        when(vacataireRepository.save(any(Vacataire.class))).thenAnswer(invocation -> {
            Vacataire saved = invocation.getArgument(0);
            saved.setActif(true);
            saved.setDateModification(new Date());
            return saved;
        });

        // When
        vacataireService.activerVacataire(1L);

        // Then
        verify(vacataireRepository, times(1)).findById(1L);
        verify(vacataireRepository, times(1)).save(any(Vacataire.class));
    }

    @Test
    void testActiverVacataireNonExistant() {
        // Given
        when(vacataireRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(VacataireNotFoundException.class, () -> vacataireService.activerVacataire(1L));
        verify(vacataireRepository, times(1)).findById(1L);
        verify(vacataireRepository, never()).save(any(Vacataire.class));
    }


    @Test
    void testReactivierVacataire() {
        // Given
        when(vacataireRepository.findById(1L)).thenReturn(Optional.of(vacataireTest));
        doAnswer(invocation -> {
            Vacataire saved = invocation.getArgument(0);
            saved.setActif(true);
            saved.setDateModification(new Date());
            return saved;
        }).when(vacataireRepository).save(any(Vacataire.class));

        // When
        VacataireDTO result = vacataireService.reactivierVacataire(1L);

        // Then
        assertNotNull(result);
        assertTrue(result.isActif());
        verify(vacataireRepository, times(1)).findById(1L);
        verify(vacataireRepository, times(1)).save(any(Vacataire.class));
        verify(vacataireRepository, never()).findByIdAndActif(anyLong(), anyBoolean());
    }

    @Test
    void testReactivierVacataireNonExistant() {
        // Given
        when(vacataireRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(VacataireNotFoundException.class, () -> vacataireService.reactivierVacataire(1L));
        verify(vacataireRepository, times(1)).findById(1L);
        verify(vacataireRepository, never()).save(any(Vacataire.class));
    }
}
