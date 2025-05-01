/*
 * Auteur : Omar DIOP
 * Date de création : 2025
 * Description :
 *  Classe de test unitaire pour VacataireMapper.
 *  Vérifie la fiabilité de la conversion entre Vacataire et VacataireDTO.
 */
package sn.uasz.utilisateursapi.mappers;

import org.junit.jupiter.api.Test;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.entities.Vacataire;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe VacataireMapper.
 *
 * @author Omar DIOP
 */
class VacataireMapperTest {
    /**
     * Teste la conversion d'une entité Vacataire vers un DTO VacataireDTO.
     */
    @Test
    void testToDTO() {
        Vacataire vacataire = new Vacataire();
        vacataire.setId(1L);
        vacataire.setNom("Sow");
        vacataire.setPrenom("Omar");
        vacataire.setEmail("omar.sow@univ.sn");
        vacataire.setTelephone("771234567");
        vacataire.setSpecialite("Maths");
        vacataire.setActif(true);

        VacataireDTO dto = VacataireMapper.toDTO(vacataire);
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Sow", dto.getNom());
        assertEquals("Omar", dto.getPrenom());
        assertEquals("omar.sow@univ.sn", dto.getEmail());
        assertEquals("771234567", dto.getTelephone());
        assertEquals("Maths", dto.getSpecialite());
        assertTrue(dto.isActif());
    }

    /**
     * Teste la conversion d'un DTO VacataireDTO vers une entité Vacataire.
     */
    @Test
    void testToEntity() {
        VacataireDTO dto = new VacataireDTO();
        dto.setId(2L);
        dto.setNom("Diop");
        dto.setPrenom("Fatou");
        dto.setEmail("fatou.diop@univ.sn");
        dto.setTelephone("781234567");
        dto.setSpecialite("Physique");
        dto.setActif(false);

        Vacataire vacataire = VacataireMapper.toEntity(dto);
        assertNotNull(vacataire);
        assertEquals(2L, vacataire.getId());
        assertEquals("Diop", vacataire.getNom());
        assertEquals("Fatou", vacataire.getPrenom());
        assertEquals("fatou.diop@univ.sn", vacataire.getEmail());
        assertEquals("781234567", vacataire.getTelephone());
        assertEquals("Physique", vacataire.getSpecialite());
        assertFalse(vacataire.isActif());
    }

    /**
     * Teste la conversion d'une entité nulle vers un DTO (doit retourner null).
     */
    @Test
    void testToDTO_Null() {
        assertNull(VacataireMapper.toDTO(null));
    }

    /**
     * Teste la conversion d'un DTO nul vers une entité (doit retourner null).
     */
    @Test
    void testToEntity_Null() {
        assertNull(VacataireMapper.toEntity(null));
    }
}
