package sn.uasz.utilisateursapi.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.enums.Grade;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EnseignantMapperTest {

    private final EnseignantMapper mapper = Mappers.getMapper(EnseignantMapper.class);

    @Test
    void testToEntity() {
        EnseignantDTO dto = new EnseignantDTO(
                1L,
                "Diop",
                "Omar",
                "omar.diop@uasz.sn",
                "+221771234567",
                "123ABC",
                Grade.PROFESSEUR_TITULAIRE,
                "admin",
                LocalDate.of(2024, 12, 1),
                true
        );

        Enseignant entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.nom(), entity.getNom());
        assertEquals(dto.prenom(), entity.getPrenom());
        assertEquals(dto.email(), entity.getEmail());
        assertEquals(dto.telephone(), entity.getTelephone());
        assertEquals(dto.matricule(), entity.getMatricule());
        assertEquals(dto.grade(), entity.getGrade());
        assertEquals(dto.createBy(), entity.getCreateBy());
        assertEquals(dto.createAt(), entity.getCreateAt());
        assertEquals(dto.actif(), entity.isActif());
    }

    @Test
    void testToDTO() {
        Enseignant entity = new Enseignant();
        entity.setId(2L);
        entity.setNom("Sow");
        entity.setPrenom("Fatou");
        entity.setEmail("fatou.sow@uasz.sn");
        entity.setTelephone("+221771112233");
        entity.setMatricule("456DEF");
        entity.setGrade(Grade.MAITRE_DE_CONFERENCES);
        entity.setCreateBy("admin2");
        entity.setCreateAt(LocalDate.of(2025, 1, 1));
        entity.setActif(false);

        EnseignantDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getNom(), dto.nom());
        assertEquals(entity.getPrenom(), dto.prenom());
        assertEquals(entity.getEmail(), dto.email());
        assertEquals(entity.getTelephone(), dto.telephone());
        assertEquals(entity.getMatricule(), dto.matricule());
        assertEquals(entity.getGrade(), dto.grade());
        assertEquals(entity.getCreateBy(), dto.createBy());
        assertEquals(entity.getCreateAt(), dto.createAt());
        assertEquals(entity.isActif(), dto.actif());
    }

    @Test
    void testUpdateEntityFromDTO() {
        Enseignant entity = new Enseignant();
        entity.setNom("Original");
        entity.setPrenom("Data");

        EnseignantDTO dto = new EnseignantDTO(
                null,
                "MisUpdated",
                "MisMapper",
                "updated@uasz.sn",
                "+221770000000",
                "999ZZZ",
                Grade.VACATAIRE,
                "modifieur",
                LocalDate.of(2025, 5, 1),
                false
        );

        mapper.updateEnseignantFromDTO(dto, entity);

        assertEquals("MisUpdated", entity.getNom());
        assertEquals("MisMapper", entity.getPrenom());
        assertEquals("updated@uasz.sn", entity.getEmail());
        assertEquals("+221770000000", entity.getTelephone());
        assertEquals("999ZZZ", entity.getMatricule());
        assertEquals(Grade.VACATAIRE, entity.getGrade());
        assertEquals("modifieur", entity.getCreateBy());
        assertEquals(LocalDate.of(2025, 5, 1), entity.getCreateAt());
        assertFalse(entity.isActif());
    }
}
