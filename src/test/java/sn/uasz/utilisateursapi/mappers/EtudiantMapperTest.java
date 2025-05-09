package sn.uasz.utilisateursapi.mappers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.entities.Etudiant;

import java.time.LocalDate;

class EtudiantMapperTest {

    private final EtudiantMapper etudiantMapper = new EtudiantMapper();

    /**
     * Teste la méthode toDTO() du mapper EtudiantMapper.
     * Vérifie que la conversion d'un objet Etudiant vers un EtudiantDTO
     * copie correctement tous les champs.
     */
    @Test
    void testToDTO() {
        // Given : Création d'un objet Etudiant avec des données
        Etudiant etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Diop");
        etudiant.setPrenom("Amina");
        etudiant.setMatricule("MAT123");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 15));
        etudiant.setLieuNaissance("Ziguinchor");


        // When : Appel de la méthode toDTO() du mapper
        EtudiantDTO dto = etudiantMapper.toDTO(etudiant);

        // Then : Vérification que les champs du DTO correspondent à ceux de l'entité
        assertNotNull(dto);
        assertEquals(etudiant.getId(), dto.getId());
        assertEquals(etudiant.getNom(), dto.getNom());
        assertEquals(etudiant.getPrenom(), dto.getPrenom());
        assertEquals(etudiant.getMatricule(), dto.getMatricule());
        assertEquals(etudiant.getEmail(), dto.getEmail());
        assertEquals(etudiant.getDateNaissance(), dto.getDateNaissance());
        assertEquals(etudiant.getLieuNaissance(), dto.getLieuNaissance());
    }

    /**
     * Teste la méthode toEntity() du mapper EtudiantMapper.
     * Vérifie que la conversion d'un objet EtudiantDTO vers un Etudiant
     * copie correctement tous les champs.
     */
    @Test
    void testToEntity() {
        // Given : Création d'un objet EtudiantDTO avec des données
        EtudiantDTO dto = new EtudiantDTO();
        dto.setNom("Fall");
        dto.setPrenom("Ibrahima");
        dto.setMatricule("MAT456");
        dto.setEmail("ibrahima.fall@example.com");
        dto.setDateNaissance(LocalDate.of(2000, 1, 15));
        dto.setLieuNaissance("Ziguinchor");

        // When : Appel de la méthode toEntity() du mapper
        Etudiant etudiant = etudiantMapper.toEntity(dto);

        // Then : Vérification que les champs de l'entité correspondent à ceux du DTO
        assertNotNull(etudiant);
        assertEquals(dto.getNom(), etudiant.getNom());
        assertEquals(dto.getPrenom(), etudiant.getPrenom());
        assertEquals(dto.getMatricule(), etudiant.getMatricule());
        assertEquals(dto.getEmail(), etudiant.getEmail());
        assertEquals(dto.getDateNaissance(), etudiant.getDateNaissance());
        assertEquals(dto.getLieuNaissance(), etudiant.getLieuNaissance());
    }

}
