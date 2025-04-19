package sn.uasz.utilisateursapi.mappers;

import org.mapstruct.*;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;

public interface EnseignantMapper {

    Enseignant toEntity(EnseignantDTO enseignantDTO);

    EnseignantDTO toDTO(Enseignant enseignant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEnseignantFromDTO(EnseignantDTO enseignantDTO, @MappingTarget Enseignant enseignant);
}