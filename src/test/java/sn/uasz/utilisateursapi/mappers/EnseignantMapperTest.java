package sn.uasz.utilisateursapi.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.entities.Role;
import sn.uasz.utilisateursapi.enums.Grade;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour {@link EnseignantMapper}.
 * <p>
 * Cette classe vérifie les opérations de mappage entre les entités {@link Enseignant}
 * et les objets de transfert de données {@link EnseignantDTO}, à l'aide de la bibliothèque MapStruct.
 */
class EnseignantMapperTest {

    /** Instance du mapper généré automatiquement par MapStruct. */
    private final EnseignantMapper mapper = Mappers.getMapper(EnseignantMapper.class);

    /**
     * Teste la méthode {@code toEntity} pour vérifier que les données
     * d’un {@link EnseignantDTO} sont correctement transférées vers un {@link Enseignant}.
     */
    @Test
    void testToEntity() {
        // Création des rôles simulés
        Role role1 = new Role();
        role1.setId(1L);
        role1.setLibelle("ROLE_USER");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setLibelle("ROLE_ADMIN");

        List<Role> roles = List.of(role1, role2);

        // Création du DTO
        EnseignantDTO dto = new EnseignantDTO(
                1L,
                "Diop",
                "Omar",
                "omar.diop@uasz.sn",
                "+221771234567",
                "123ABC",
                Grade.PROFESSEUR_TITULAIRE,
                roles,
                "admin",
                LocalDate.of(2024, 12, 1),
                true
        );

        // Mapping vers entité
        Enseignant entity = mapper.toEntity(dto);

        // Assertions pour vérifier que le mappage est correct
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
        assertEquals(dto.actif(), entity.getActif());
    }

    /**
     * Teste la méthode {@code toDTO} pour vérifier que les données
     * d’un {@link Enseignant} sont correctement transférées vers un {@link EnseignantDTO}.
     */
    @Test
    void testToDTO() {
        // Création de l'entité
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

        // Mapping vers DTO
        EnseignantDTO dto = mapper.toDTO(entity);

        // Vérifications
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
        assertEquals(entity.getActif(), dto.actif());
    }

    /**
     * Teste la méthode {@code updateEnseignantFromDTO} pour vérifier que les champs d’une
     * entité {@link Enseignant} sont correctement mis à jour à partir d’un {@link EnseignantDTO}.
     */
    @Test
    void testUpdateEntityFromDTO() {
        // Entité initiale
        Enseignant entity = new Enseignant();
        entity.setNom("Original");
        entity.setPrenom("Data");

        // Création d'un rôle
        Role role = new Role();
        role.setId(3L);
        role.setLibelle("ROLE_GUEST");

        List<Role> roles = List.of(role);

        // Nouveau DTO avec les données mises à jour
        EnseignantDTO dto = new EnseignantDTO(
                null,
                "MisUpdated",
                "MisMapper",
                "updated@uasz.sn",
                "+221770000000",
                "999ZZZ",
                Grade.VACATAIRE,
                roles,
                "modifieur",
                LocalDate.of(2025, 5, 1),
                false
        );

        // Mise à jour de l'entité
        mapper.updateEnseignantFromDTO(dto, entity);

        // Vérifications
        assertEquals("MisUpdated", entity.getNom());
        assertEquals("MisMapper", entity.getPrenom());
        assertEquals("updated@uasz.sn", entity.getEmail());
        assertEquals("+221770000000", entity.getTelephone());
        assertEquals("999ZZZ", entity.getMatricule());
        assertEquals(Grade.VACATAIRE, entity.getGrade());
        assertEquals("modifieur", entity.getCreateBy());
        assertEquals(LocalDate.of(2025, 5, 1), entity.getCreateAt());
        assertFalse(entity.getActif());
    }
}
