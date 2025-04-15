package sn.uasz.UtilisateursAPI.services;

import sn.uasz.UtilisateursAPI.dtos.EnseignantDTO;
import sn.uasz.UtilisateursAPI.exceptions.EnseignantNotFoundException;

import java.util.List;

public interface EnseignantService {
    EnseignantDTO activerEnseignant(Long id) throws EnseignantNotFoundException;
    EnseignantDTO desactiverEnseignant(Long id) throws EnseignantNotFoundException;
    List<EnseignantDTO> findAllEnseignants();
    EnseignantDTO findEnseignantById(Long id) throws EnseignantNotFoundException;
}