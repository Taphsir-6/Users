package sn.uasz.utilisateursapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;

/**
 * Mapper MapStruct pour convertir entre les entités {@link Enseignant}
 * et les DTO {@link EnseignantDTO}.

 * Ce mapper permet :
 * - La transformation DTO → Entité (persistable)
 * - La transformation Entité → DTO (exposable via API)
 * - La mise à jour partielle d’une entité à partir d’un DTO

 * MapStruct génère automatiquement l’implémentation à la compilation.
 */
@Mapper(componentModel = "spring")
public interface EnseignantMapper {

    /**
     * Convertit un DTO {@link EnseignantDTO} vers une entité {@link Enseignant}.
     * Utile pour la création d'un nouvel enseignant à partir des données d'une requête REST.
     *
     * @param dto l'objet DTO représentant l'enseignant
     * @return l'entité `Enseignant` correspondante
     */
    Enseignant toEntity(EnseignantDTO dto);

    /**
     * Convertit une entité {@link Enseignant} vers un DTO {@link EnseignantDTO}.
     * Utile pour l'exposition d'une entité en réponse API ou vue.
     *
     * @param entity l'entité représentant un enseignant
     * @return un DTO correspondant à l'entité
     */
    EnseignantDTO toDTO(Enseignant entity);

    /**
     * Met à jour les champs d’une entité existante à partir d’un DTO.
     * Les valeurs du DTO écrasent celles de l'entité cible si elles sont non nulles.
     *
     * @param dto     données source
     * @param entity  entité cible à modifier (annotée avec @MappingTarget)
     */
    void updateEnseignantFromDTO(EnseignantDTO dto, @MappingTarget Enseignant entity);
}
