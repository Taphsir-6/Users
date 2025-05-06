package sn.uasz.utilisateursapi.mappers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.entities.Etudiant;

class EtudiantMapperTest {

    private final EtudiantMapper etudiantMapper = new EtudiantMapper();

    @Test
    void testToDTO() {
        // Given
        Etudiant etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Diop");
        etudiant.setPrenom("Amina");
        etudiant.setMatricule("MAT123");
        etudiant.setEmail("amina.diop@example.com");
        etudiant.setPhoto("photo.jpg");

        // When
        EtudiantDTO dto = etudiantMapper.toDTO(etudiant);

        // Then
        assertNotNull(dto);
        assertEquals(etudiant.getId(), dto.getId());
        assertEquals(etudiant.getNom(), dto.getNom());
        assertEquals(etudiant.getPrenom(), dto.getPrenom());
        assertEquals(etudiant.getMatricule(), dto.getMatricule());
        assertEquals(etudiant.getEmail(), dto.getEmail());
        assertEquals(etudiant.getPhoto(), dto.getPhoto());
    }

    @Test
    void testToEntity() {
        // Given
        EtudiantDTO dto = new EtudiantDTO();
        dto.setNom("Fall");
        dto.setPrenom("Ibrahima");
        dto.setMatricule("MAT456");
        dto.setEmail("ibrahima.fall@example.com");
        dto.setPhoto("photo2.jpg");

        // When
        Etudiant etudiant = etudiantMapper.toEntity(dto);

        // Then
        assertNotNull(etudiant);
        assertEquals(dto.getNom(), etudiant.getNom());
        assertEquals(dto.getPrenom(), etudiant.getPrenom());
        assertEquals(dto.getMatricule(), etudiant.getMatricule());
        assertEquals(dto.getEmail(), etudiant.getEmail());
        assertEquals(dto.getPhoto(), etudiant.getPhoto());
    }

    @Test
    void testToEntityWithNullDTO() {
        assertThrows(NullPointerException.class, () -> etudiantMapper.toEntity(null));
    }

    @Test
    void testToDTOWithNullEntity() {
        assertThrows(NullPointerException.class, () -> etudiantMapper.toDTO(null));
    }
}