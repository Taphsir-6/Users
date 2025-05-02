package sn.uasz.utilisateursapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;

@Mapper(componentModel = "spring")
public interface EnseignantMapper {
    Enseignant toEntity(EnseignantDTO dto);
    EnseignantDTO toDTO(Enseignant entity);
    void updateEnseignantFromDTO(EnseignantDTO dto, @MappingTarget Enseignant entity);
}
