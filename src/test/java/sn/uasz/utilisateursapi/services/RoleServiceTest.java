package sn.uasz.utilisateursapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.entities.Role;
import sn.uasz.utilisateursapi.exceptions.RoleNotFoundException;
import sn.uasz.utilisateursapi.mappers.RoleMapper;
import sn.uasz.utilisateursapi.repositories.RoleRepository;

import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour le service RoleService.
 * Utilise JUnit 5 et Mockito pour simuler les dépendances.
 */
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    // Simule le repository pour les opérations en base de données.
    @Mock
    private RoleRepository roleRepository;

    // Simule le mapper entre l'entité Role et le DTO RoleDTO.
    @Mock
    private RoleMapper roleMapper;

    // Simule un gestionnaire d'entités JPA (non utilisé directement ici).
    @Mock
    private EntityManager entityManager;

    // Injecte les mocks dans une instance de RoleService.
    @InjectMocks
    private RoleService roleService;

    private Role role;
    private RoleDTO roleDTO;

    /**
     * Initialise les objets communs aux tests avant chaque exécution.
     */
    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setLibelle("ADMIN");
        role.setDescription("Administrateur du système");

        roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setLibelle("ADMIN");
        roleDTO.setDescription("Administrateur du système");
    }

    /**
     * Teste l'ajout d'un rôle en s'assurant que le DTO retourné correspond à celui attendu.
     */
    @Test
    void ajouterRole_shouldReturnSavedRoleDTO() {
        when(roleMapper.toEntity(any(RoleDTO.class))).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.toDTO(any(Role.class))).thenReturn(roleDTO);

        RoleDTO result = roleService.ajouterRole(roleDTO);

        assertNotNull(result);
        assertEquals(roleDTO.getId(), result.getId());
        assertEquals(roleDTO.getLibelle(), result.getLibelle());
        verify(roleMapper, times(1)).toEntity(any(RoleDTO.class));
        verify(roleRepository, times(1)).save(any(Role.class));
        verify(roleMapper, times(1)).toDTO(any(Role.class));
    }

    /**
     * Teste la modification d'un rôle avec un ID inexistant et attend une exception.
     */
    @Test
    void modifierRole_shouldThrowExceptionWhenRoleNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.modifierRole(1, roleDTO));
    }

    /**
     * Teste la suppression d'un rôle existant.
     */
    @Test
    void supprimerRole_shouldDeleteRole() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).delete(any(Role.class));

        roleService.supprimerRole(1);

        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).delete(any(Role.class));
    }

    /**
     * Teste la suppression d’un rôle inexistant et attend une exception.
     */
    @Test
    void supprimerRole_shouldThrowExceptionWhenRoleNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            roleService.supprimerRole(1);
        });
    }

    /**
     * Teste la récupération d'un rôle existant par son ID.
     */
    @Test
    void obtenirRole_shouldReturnRoleDTO() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toDTO(any(Role.class))).thenReturn(roleDTO);

        RoleDTO result = roleService.obtenirRole(1);

        assertNotNull(result);
        assertEquals(roleDTO.getId(), result.getId());
        verify(roleRepository, times(1)).findById(1L);
        verify(roleMapper, times(1)).toDTO(any(Role.class));
    }

    /**
     * Teste la récupération d’un rôle inexistant et attend une exception.
     */
    @Test
    void obtenirRole_shouldThrowExceptionWhenRoleNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            roleService.obtenirRole(1);
        });
    }

    /**
     * Teste la récupération de tous les rôles en vérifiant que la liste retournée est correcte.
     */
    @Test
    void findAll_shouldReturnListOfRoleDTO() {
        List<Role> roles = Arrays.asList(role);
        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.toDTO(any(Role.class))).thenReturn(roleDTO);

        List<RoleDTO> result = roleService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toDTO(any(Role.class));
    }
}
