package sn.uasz.utilisateursapi.mappers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.entities.Role;

class RoleMapperTest {

    private final RoleMapper roleMapper = new RoleMapper();

    /**
     * Teste la méthode toDTO() du mapper RoleMapper.
     * Vérifie que la conversion d'un objet Role vers un RoleDTO
     * copie correctement tous les champs.
     */
    @Test
    void testToDTO() {
        // Given : Création d'un objet Role avec des données
        Role role = new Role();
        role.setId(1L);
        role.setLibelle("Administrateur");
        role.setDescription("Gère les utilisateurs et les permissions");

        // When : Appel de la méthode toDTO() du mapper
        RoleDTO dto = roleMapper.toDTO(role);

        // Then : Vérification que les champs du DTO correspondent à ceux de l'entité
        assertNotNull(dto);
        assertEquals(role.getId(), dto.getId());
        assertEquals(role.getLibelle(), dto.getLibelle());
        assertEquals(role.getDescription(), dto.getDescription());
    }

    /**
     * Teste la méthode toEntity() du mapper RoleMapper.
     * Vérifie que la conversion d'un objet RoleDTO vers un Role
     * copie correctement tous les champs.
     */
    @Test
    void testToEntity() {
        // Given : Création d'un objet RoleDTO avec des données
        RoleDTO dto = new RoleDTO();
        dto.setId(2L);
        dto.setLibelle("Utilisateur");
        dto.setDescription("Accès standard au système");

        // When : Appel de la méthode toEntity() du mapper
        Role role = roleMapper.toEntity(dto);

        // Then : Vérification que les champs de l'entité correspondent à ceux du DTO
        assertNotNull(role);
        assertEquals(dto.getId(), role.getId());
        assertEquals(dto.getLibelle(), role.getLibelle());
        assertEquals(dto.getDescription(), role.getDescription());
    }
}
