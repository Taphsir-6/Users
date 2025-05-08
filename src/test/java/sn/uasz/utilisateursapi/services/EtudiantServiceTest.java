package sn.uasz.utilisateursapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.entities.Etudiant;
import sn.uasz.utilisateursapi.exceptions.EtudiantNotFoundException;
import sn.uasz.utilisateursapi.mappers.EtudiantMapper;
import sn.uasz.utilisateursapi.repositories.EtudiantRepository;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour les méthodes du service EtudiantService.
 */
@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository; // Mock du repository

    @Mock
    private EtudiantMapper etudiantMapper; // Mock du mapper

    @Mock
    private EntityManager entityManager; // Mock de l'EntityManager (non utilisé dans les tests)

    @InjectMocks
    private EtudiantService etudiantService; // Service à tester

    private Etudiant etudiant; // Entité Etudiant utilisée pour les tests
    private EtudiantDTO etudiantDTO; // DTO correspondant

    /**
     * Initialisation commune avant chaque test.
     */
    @BeforeEach
    void setUp() {
        // Initialisation de l'entité Etudiant
        etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Dupont");
        etudiant.setPrenom("Jean");
        etudiant.setMatricule("202202898");
        etudiant.setEmail("dupontjj45@zig.univ.sn");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 15));
        etudiant.setLieuNaissance("Ziguinchor");

        // Initialisation du DTO correspondant
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
     * Test de la méthode ajouterEtudiant : doit retourner le DTO sauvegardé.
     */
    @Test
    void ajouterEtudiant_shouldReturnSavedEtudiantDTO() {
        // Simulation du comportement des dépendances
        when(etudiantMapper.toEntity(any(EtudiantDTO.class))).thenReturn(etudiant);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(etudiantMapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        // Appel de la méthode à tester
        EtudiantDTO result = etudiantService.ajouterEtudiant(etudiantDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals(etudiantDTO.getId(), result.getId());
        assertEquals(etudiantDTO.getNom(), result.getNom());
        verify(etudiantMapper, times(1)).toEntity(any(EtudiantDTO.class));
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
        verify(etudiantMapper, times(1)).toDTO(any(Etudiant.class));
    }

    /**
     * Test de la méthode modifierEtudiant : doit lever une exception si l'étudiant n'existe pas.
     */
    @Test
    void modifierEtudiant_shouldThrowExceptionWhenEtudiantNotFound() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EtudiantNotFoundException.class, () -> etudiantService.modifierEtudiant(1, etudiantDTO));
    }

    /**
     * Test de la méthode supprimerEtudiant : doit supprimer l'étudiant si trouvé.
     */
    @Test
    void supprimerEtudiant_shouldDeleteEtudiant() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        doNothing().when(etudiantRepository).delete(any(Etudiant.class));

        etudiantService.supprimerEtudiant(1);

        verify(etudiantRepository, times(1)).findById(1L);
        verify(etudiantRepository, times(1)).delete(any(Etudiant.class));
    }

    /**
     * Test de la méthode supprimerEtudiant : doit lever une exception si l'étudiant n'est pas trouvé.
     */
    @Test
    void supprimerEtudiant_shouldThrowExceptionWhenEtudiantNotFound() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            etudiantService.supprimerEtudiant(1);
        });
    }

    /**
     * Test de la méthode obtenirEtudiant : doit retourner le DTO de l'étudiant trouvé.
     */
    @Test
    void obtenirEtudiant_shouldReturnEtudiantDTO() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(etudiantMapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        EtudiantDTO result = etudiantService.obtenirEtudiant(1);

        assertNotNull(result);
        assertEquals(etudiantDTO.getId(), result.getId());
        verify(etudiantRepository, times(1)).findById(1L);
        verify(etudiantMapper, times(1)).toDTO(any(Etudiant.class));
    }

    /**
     * Test de la méthode obtenirEtudiant : doit lever une exception si l'étudiant n'est pas trouvé.
     */
    @Test
    void obtenirEtudiant_shouldThrowExceptionWhenEtudiantNotFound() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            etudiantService.obtenirEtudiant(1);
        });
    }

    /**
     * Test de la méthode findAll : doit retourner une liste de DTOs.
     */
    @Test
    void findAll_shouldReturnListOfEtudiantDTO() {
        List<Etudiant> etudiants = Arrays.asList(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiants);
        when(etudiantMapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        List<EtudiantDTO> result = etudiantService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(etudiantRepository, times(1)).findAll();
        verify(etudiantMapper, times(1)).toDTO(any(Etudiant.class));
    }

    /**
     * Test de la méthode genererEmail : doit générer un email valide.
     */
    @Test
    void genererEmail_shouldGenerateValidEmail() {
        String email = etudiantService.genererEmail("Dupont", "Jean");

        assertNotNull(email);
        assertTrue(email.matches("^dupontj[a-z]\\d+@zig\\.univ\\.sn$"));
    }
}
