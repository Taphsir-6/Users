package sn.uasz.UtilisateursAPI.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.uasz.UtilisateursAPI.dtos.EnseignantDTO;
import sn.uasz.UtilisateursAPI.entities.Enseignant;
import sn.uasz.UtilisateursAPI.exceptions.ConflitEtatException;
import sn.uasz.UtilisateursAPI.exceptions.EnseignantNotFoundException;
import sn.uasz.UtilisateursAPI.mappers.EnseignantMapperImpl;
import sn.uasz.UtilisateursAPI.repositories.EnseignantRepository;
import sn.uasz.UtilisateursAPI.services.EnseignantService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final EnseignantMapperImpl mapper;

    @Override
    public EnseignantDTO activerEnseignant(Long id) throws EnseignantNotFoundException {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));

        if(enseignant.isActif()) {
            throw new ConflitEtatException("L'enseignant est déjà actif");
        }

        enseignant.setActif(true);
        return mapper.enseignantToEnseignantDTO(enseignantRepository.save(enseignant));
    }

    @Override
    public EnseignantDTO desactiverEnseignant(Long id) throws EnseignantNotFoundException {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));

        if(!enseignant.isActif()) {
            throw new ConflitEtatException("L'enseignant est déjà inactif");
        }

        enseignant.setActif(false);
        return mapper.enseignantToEnseignantDTO(enseignantRepository.save(enseignant));
    }

    @Override
    public List<EnseignantDTO> findAllEnseignants() {
        return enseignantRepository.findAll()
                .stream()
                .map(mapper::enseignantToEnseignantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnseignantDTO findEnseignantById(Long id) throws EnseignantNotFoundException {
        return enseignantRepository.findById(id)
                .map(mapper::enseignantToEnseignantDTO)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));
    }
}