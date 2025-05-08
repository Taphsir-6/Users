package sn.uasz.utilisateursapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.entities.Role;
import sn.uasz.utilisateursapi.enums.Grade;
import sn.uasz.utilisateursapi.exceptions.ConflitEtatException;
import sn.uasz.utilisateursapi.exceptions.EnseignantNotFoundException;
import sn.uasz.utilisateursapi.mappers.EnseignantMapper;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour la classe {@link EnseignantService}.
 * Elle utilise JUnit 5 et Mockito pour tester le comportement des différentes méthodes du service.
 */
@ExtendWith(MockitoExtension.class)
class EnseignantServiceTest {

    @Mock
    private EnseignantRepository enseignantRepository;

    @Mock
    private EnseignantMapper enseignantMapper;

    @InjectMocks
    private EnseignantService enseignantService;

    private Enseignant enseignant;
    private EnseignantDTO enseignantDTO;

    /**
     * Initialisation des objets utilisés pour les tests avant chaque méthode.
     */
    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1L);
        role.setLibelle("ROLE_ENSEIGNANT");

        enseignant = Enseignant.builder()
                .id(1L)
                .nom("Diop")
                .prenom("Ibrahima")
                .email("diop.ibrahima@uasz.sn")
                .telephone("+221771234567")
                .matricule("MAT001")
                .grade(Grade.PROFESSEUR)
                .createBy("admin")
                .createAt(LocalDate.now())
                .actif(true)
                .build();

        enseignantDTO = new EnseignantDTO(
                1L,
                "Diop",
                "Ibrahima",
                "diop.ibrahima@uasz.sn",
                "+221771234567",
                "MAT001",
                Grade.PROFESSEUR,
                List.of(role),
                "admin",
                LocalDate.now(),
                true
        );
    }

    /**
     * Test de la méthode {@code ajouterEnseignant} pour vérifier qu'un enseignant est correctement ajouté.
     */
    @Test
    void testAjouterEnseignant() {
        when(enseignantMapper.toEntity(any())).thenReturn(enseignant);
        when(enseignantRepository.save(any())).thenReturn(enseignant);
        when(enseignantMapper.toDTO(any())).thenReturn(enseignantDTO);

        EnseignantDTO result = enseignantService.ajouterEnseignant(enseignantDTO);

        assertNotNull(result);
        assertEquals("Diop", result.nom());
        verify(enseignantRepository).save(any());
    }

    /**
     * Test de la méthode {@code obtenirEnseignantParId} avec un ID valide.
     */
    @Test
    void testObtenirEnseignantParId() {
        when(enseignantRepository.findById(1L)).thenReturn(Optional.of(enseignant));
        when(enseignantMapper.toDTO(any())).thenReturn(enseignantDTO);

        EnseignantDTO result = enseignantService.obtenirEnseignantParId(1L);

        assertNotNull(result);
        assertEquals("Diop", result.nom());
    }

    /**
     * Test de la méthode {@code obtenirEnseignantParId} avec un ID inexistant (doit lever une exception).
     */
    @Test
    void testObtenirEnseignantParIdNonTrouve() {
        when(enseignantRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> enseignantService.obtenirEnseignantParId(1L));
    }

    /**
     * Test de la méthode {@code supprimerEnseignant} pour vérifier que la suppression est effectuée.
     */
    @Test
    void testSupprimerEnseignant() {
        doNothing().when(enseignantRepository).deleteById(1L);
        enseignantService.supprimerEnseignant(1L);
        verify(enseignantRepository).deleteById(1L);
    }

    /**
     * Test de la méthode {@code desactiverEnseignant} lorsque l’enseignant est déjà inactif (doit lever une exception).
     */
    @Test
    void testDesactiverEnseignantDejaInactif() {
        enseignant.setActif(false);
        when(enseignantRepository.findById(1L)).thenReturn(Optional.of(enseignant));
        assertThrows(ConflitEtatException.class, () -> enseignantService.desactiverEnseignant(1L));
    }

    /**
     * Test de la méthode {@code activerEnseignant} avec un enseignant inexistant (doit lever une exception).
     */
    @Test
    void testActiverEnseignantNonExistant() {
        when(enseignantRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EnseignantNotFoundException.class, () -> enseignantService.activerEnseignant(1L));
    }

    /**
     * Test de la méthode {@code rechercherEnseignantsParNom} avec un nom existant.
     */
    @Test
    void testRechercherEnseignantsParNom() {
        when(enseignantRepository.findByNomContainingIgnoreCase("Diop")).thenReturn(List.of(enseignant));
        when(enseignantMapper.toDTO(any())).thenReturn(enseignantDTO);

        List<EnseignantDTO> result = enseignantService.rechercherEnseignantsParNom("Diop");

        assertEquals(1, result.size());
        assertEquals("Diop", result.get(0).nom());
        verify(enseignantRepository).findByNomContainingIgnoreCase("Diop");
    }

    /**
     * Test de la méthode {@code modifierEnseignant} pour vérifier la mise à jour des données.
     */
    @Test
    void testModifierEnseignant() {
        when(enseignantRepository.findById(1L)).thenReturn(Optional.of(enseignant));
        doAnswer(invocation -> {
            EnseignantDTO dto = invocation.getArgument(0);
            Enseignant target = invocation.getArgument(1);
            target.setNom(dto.nom());
            target.setPrenom(dto.prenom());
            return null;
        }).when(enseignantMapper).updateEnseignantFromDTO(any(), any());

        when(enseignantRepository.save(any())).thenReturn(enseignant);
        when(enseignantMapper.toDTO(any())).thenReturn(enseignantDTO);

        EnseignantDTO result = enseignantService.modifierEnseignant(1L, enseignantDTO);

        assertNotNull(result);
        assertEquals("Diop", result.nom());
    }

    /**
     * Test de la méthode {@code modifierEnseignant} avec un ID inexistant (doit lever une exception).
     */
    @Test
    void testModifierEnseignantNonExistant() {
        when(enseignantRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> enseignantService.modifierEnseignant(1L, enseignantDTO));
    }

    /**
     * Test de la méthode {@code activerEnseignant} pour activer un enseignant inactif.
     */
    @Test
    void testActiverEnseignant() throws EnseignantNotFoundException {
        enseignant.setActif(false);
        when(enseignantRepository.findById(1L)).thenReturn(Optional.of(enseignant));
        when(enseignantRepository.save(any())).thenReturn(enseignant);
        when(enseignantMapper.toDTO(any())).thenReturn(enseignantDTO);

        EnseignantDTO result = enseignantService.activerEnseignant(1L);

        assertTrue(result.actif());
        verify(enseignantRepository).save(any());
    }

    /**
     * Test de la méthode {@code desactiverEnseignant} pour désactiver un enseignant actif.
     */
    @Test
    void testDesactiverEnseignant() throws EnseignantNotFoundException {
        enseignant.setActif(true);
        when(enseignantRepository.findById(1L)).thenReturn(Optional.of(enseignant));
        when(enseignantRepository.save(any())).thenReturn(enseignant);

        EnseignantDTO enseignantDesactive = new EnseignantDTO(
                enseignant.getId(),
                enseignant.getNom(),
                enseignant.getPrenom(),
                enseignant.getEmail(),
                enseignant.getTelephone(),
                enseignant.getMatricule(),
                enseignant.getGrade(),
                enseignantDTO.roles(),
                enseignant.getCreateBy(),
                enseignant.getCreateAt(),
                false
        );

        when(enseignantMapper.toDTO(any())).thenReturn(enseignantDesactive);

        EnseignantDTO result = enseignantService.desactiverEnseignant(1L);

        assertFalse(result.actif());
        verify(enseignantRepository).save(any());
    }
}
