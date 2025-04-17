package sn.uasz.utilisateursapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnseignantMapper {

    EnseignantDTO toDTO(Enseignant enseignant);

    Enseignant toEntity(EnseignantDTO dto);

    List<EnseignantDTO> toDTOList(List<Enseignant> enseignants);

    List<Enseignant> toEntityList(List<EnseignantDTO> dtos);

    void updateEnseignantFromDTO(EnseignantDTO dto, @MappingTarget Enseignant enseignant);
}
