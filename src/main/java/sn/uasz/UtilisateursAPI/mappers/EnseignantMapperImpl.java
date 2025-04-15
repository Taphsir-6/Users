package sn.uasz.UtilisateursAPI.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import sn.uasz.UtilisateursAPI.dtos.EnseignantDTO;
import sn.uasz.UtilisateursAPI.entities.Enseignant;

@Service
public class EnseignantMapperImpl {
    public EnseignantDTO enseignantToEnseignantDTO(Enseignant enseignant) {
        EnseignantDTO dto = new EnseignantDTO();
        BeanUtils.copyProperties(enseignant, dto);
        return dto;
    }

    public Enseignant enseignantDTOToEnseignant(EnseignantDTO dto) {
        Enseignant enseignant = new Enseignant();
        BeanUtils.copyProperties(dto, enseignant);
        return enseignant;
    }
}