package sn.uasz.utilisateursapi.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.entities.Etudiant;
import sn.uasz.utilisateursapi.mappers.EtudiantMapper;
import sn.uasz.utilisateursapi.repositories.EtudiantRepository;

import jakarta.persistence.EntityManager;
import sn.uasz.utilisateursapi.services.EtudiantService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private EtudiantMapper etudiantMapper;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant etudiant;
    private EtudiantDTO etudiantDTO;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Dupont");
        etudiant.setPrenom("Jean");
        etudiant.setMatricule("202202898");
        etudiant.setEmail("dupontjj45@zig.univ.sn");
        etudiant.setPhoto("photo1.jpg");

        etudiantDTO = new EtudiantDTO();
        etudiantDTO.setId(1L);
        etudiantDTO.setNom("Dupont");
        etudiantDTO.setPrenom("Jean");
        etudiantDTO.setMatricule("202202898");
        etudiantDTO.setEmail("dupontjj45@zig.univ.sn");
        etudiantDTO.setPhoto("photo1.jpg");
    }

    @Test
    void ajouterEtudiant_shouldReturnSavedEtudiantDTO() {
        // Arrange
        when(etudiantMapper.toEntity(any(EtudiantDTO.class))).thenReturn(etudiant);
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(etudiantMapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        // Act
        EtudiantDTO result = etudiantService.ajouterEtudiant(etudiantDTO);

        // Assert
        assertNotNull(result);
        assertEquals(etudiantDTO.getId(), result.getId());
        assertEquals(etudiantDTO.getNom(), result.getNom());
        verify(etudiantMapper, times(1)).toEntity(any(EtudiantDTO.class));
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
        verify(etudiantMapper, times(1)).toDTO(any(Etudiant.class));
    }

    @Test
    void modifierEtudiant_shouldReturnUpdatedEtudiantDTO() {
        // Arrange
        EtudiantDTO updatedDTO = new EtudiantDTO();
        updatedDTO.setNom("Dupont-Updated");
        updatedDTO.setPrenom("Jean-Updated");
        updatedDTO.setMatricule("202202898");

        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(etudiantMapper.toDTO(any(Etudiant.class))).thenReturn(updatedDTO);

        // Act
        EtudiantDTO result = etudiantService.modifierEtudiant(1, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Dupont-Updated", result.getNom());
        assertEquals("Jean-Updated", result.getPrenom());
        verify(etudiantRepository, times(1)).findById(1L);
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
        verify(entityManager, times(1)).detach(any(Etudiant.class));
    }

    @Test
    void modifierEtudiant_shouldThrowExceptionWhenEtudiantNotFound() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            etudiantService.modifierEtudiant(1, etudiantDTO);
        });
    }

    @Test
    void supprimerEtudiant_shouldDeleteEtudiant() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        doNothing().when(etudiantRepository).delete(any(Etudiant.class));

        // Act
        etudiantService.supprimerEtudiant(1);

        // Assert
        verify(etudiantRepository, times(1)).findById(1L);
        verify(etudiantRepository, times(1)).delete(any(Etudiant.class));
    }

    @Test
    void supprimerEtudiant_shouldThrowExceptionWhenEtudiantNotFound() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            etudiantService.supprimerEtudiant(1);
        });
    }

    @Test
    void obtenirEtudiant_shouldReturnEtudiantDTO() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(etudiantMapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        // Act
        EtudiantDTO result = etudiantService.obtenirEtudiant(1);

        // Assert
        assertNotNull(result);
        assertEquals(etudiantDTO.getId(), result.getId());
        verify(etudiantRepository, times(1)).findById(1L);
        verify(etudiantMapper, times(1)).toDTO(any(Etudiant.class));
    }

    @Test
    void obtenirEtudiant_shouldThrowExceptionWhenEtudiantNotFound() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            etudiantService.obtenirEtudiant(1);
        });
    }

    @Test
    void findAll_shouldReturnListOfEtudiantDTO() {
        // Arrange
        List<Etudiant> etudiants = Arrays.asList(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiants);
        when(etudiantMapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        // Act
        List<EtudiantDTO> result = etudiantService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(etudiantRepository, times(1)).findAll();
        verify(etudiantMapper, times(1)).toDTO(any(Etudiant.class));
    }

    @Test
    void genererEmail_shouldGenerateValidEmail() {
        // Act
        String email = etudiantService.genererEmail("Dupont", "Jean");

        // Assert
        assertNotNull(email);
        assertTrue(email.matches("^dupontj[a-z]\\d+@zig\\.univ\\.sn$"));
    }
}