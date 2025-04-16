package sn.uasz.utilisateursapi.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.mappers.EnseignantMapper;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final EnseignantMapper enseignantMapper;

    public EnseignantDTO ajouterEnseignant(EnseignantDTO enseignantDTO) {
        log.info("Ajout d'un nouvel enseignant: {}", enseignantDTO);
        Enseignant enseignant = enseignantMapper.toEntity(enseignantDTO);
        Enseignant savedEnseignant = enseignantRepository.save(enseignant);
        return enseignantMapper.toDTO(savedEnseignant);
    }

    public List<EnseignantDTO> listerTousEnseignants() {
        log.info("Récupération de tous les enseignants");
        return enseignantRepository.findAll()
                .stream()
                .map(enseignantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnseignantDTO obtenirEnseignantParId(int id) {
        log.info("Récupération de l'enseignant avec ID: {}", id);
        return enseignantRepository.findById(id)
                .map(enseignantMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));
    }

    public EnseignantDTO modifierEnseignant(int id, EnseignantDTO enseignantDTO) {
        log.info("Modification de l'enseignant avec ID: {}", id);
        Enseignant existingEnseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        enseignantMapper.updateEnseignantFromDTO(enseignantDTO, existingEnseignant);
        Enseignant updatedEnseignant = enseignantRepository.save(existingEnseignant);
        return enseignantMapper.toDTO(updatedEnseignant);
    }

    public void supprimerEnseignant(int id) {
        log.info("Suppression de l'enseignant avec ID: {}", id);
        enseignantRepository.deleteById(id);
    }

    public List<EnseignantDTO> rechercherEnseignantsParNom(String nom) {
        log.info("Recherche d'enseignants avec nom contenant: {}", nom);
        return enseignantRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(enseignantMapper::toDTO)
                .collect(Collectors.toList());
    }
}