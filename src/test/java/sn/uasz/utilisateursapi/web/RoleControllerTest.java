package sn.uasz.utilisateursapi.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.services.RoleService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private RoleService roleService; // Simulation du service métier

    @InjectMocks
    private RoleController roleController; // Contrôleur à tester

    private RoleDTO roleDTO; // Données de test

    /**
     * Initialisation des données avant chaque test.
     */
    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setLibelle("ADMIN");
        roleDTO.setDescription("Administrateur du système");
    }

    /**
     * Teste la récupération d’un rôle par son ID.
     * Vérifie que le contrôleur retourne le rôle avec un statut HTTP 200 (OK).
     */
    @Test
    void getRoleById_shouldReturnRole() {
        when(roleService.obtenirRole(1)).thenReturn(roleDTO);

        ResponseEntity<RoleDTO> response = roleController.getRoleById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ADMIN", response.getBody().getLibelle());
        verify(roleService, times(1)).obtenirRole(1);
    }

    /**
     * Teste la récupération de la liste de tous les rôles.
     * Vérifie que le contrôleur retourne bien tous les rôles avec un statut HTTP 200 (OK).
     */
    @Test
    void getAllRoles_shouldReturnListOfRoles() {
        List<RoleDTO> roles = Arrays.asList(
                roleDTO,
                new RoleDTO(2L, "USER", "Utilisateur du système")
        );

        when(roleService.findAll()).thenReturn(roles);

        ResponseEntity<List<RoleDTO>> response = roleController.getAllRole();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(roleService, times(1)).findAll();
    }

    /**
     * Teste la création d’un rôle via le contrôleur.
     * Vérifie que le DTO retourné correspond aux données fournies.
     */
    @Test
    void createRole_shouldReturnCreatedRole() {
        when(roleService.ajouterRole(any(RoleDTO.class))).thenReturn(roleDTO);

        ResponseEntity<RoleDTO> response = roleController.createRole(roleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ADMIN", response.getBody().getLibelle());
        verify(roleService, times(1)).ajouterRole(any(RoleDTO.class));
    }

    /**
     * Teste la mise à jour d’un rôle.
     * Vérifie que les nouvelles informations sont bien prises en compte.
     */
    @Test
    void updateRole_shouldReturnUpdatedRole() {
        RoleDTO updatedRole = new RoleDTO();
        updatedRole.setLibelle("ADMIN-UPDATED");
        updatedRole.setDescription("Administrateur mis à jour");

        when(roleService.modifierRole(anyInt(), any(RoleDTO.class))).thenReturn(updatedRole);

        ResponseEntity<RoleDTO> response = roleController.updateRole(1L, updatedRole);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ADMIN-UPDATED", response.getBody().getLibelle());
        verify(roleService, times(1)).modifierRole(anyInt(), any(RoleDTO.class));
    }

    /**
     * Teste la suppression d’un rôle.
     * Vérifie que la méthode du service est bien appelée et retourne un statut HTTP 200.
     */
    @Test
    void deleteRole_shouldReturnOk() {
        doNothing().when(roleService).supprimerRole(anyInt());

        ResponseEntity<Void> response = roleController.deleteRole(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(roleService, times(1)).supprimerRole(anyInt());
    }

    /**
     * Teste la gestion des exceptions dans le contrôleur.
     * Vérifie que le message d’erreur est bien encapsulé dans une réponse avec statut HTTP 400.
     */
    @Test
    void handleException_shouldReturnBadRequest() {
        RuntimeException exception = new RuntimeException("Rôle non trouvé");

        ResponseEntity<Map<String, String>> response = roleController.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Rôle non trouvé", response.getBody().get("message"));
    }
}
