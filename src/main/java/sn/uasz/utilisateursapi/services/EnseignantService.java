package sn.uasz.utilisateursapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.exceptions.ConflitEtatException;
import sn.uasz.utilisateursapi.exceptions.EnseignantNotFoundException;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class EnseignantService  {

    private final EnseignantRepository enseignantRepository;

    private Enseignant toEntity(EnseignantDTO dto) {
        return new Enseignant(
            dto.id(),
            dto.nom(),
            dto.prenom(),
            dto.email(),
            dto.telephone(),
            dto.matricule(),
            dto.grade(),
            dto.createBy(),
            dto.createAt(),
            Boolean.TRUE.equals(dto.actif())
        );
    }
    private EnseignantDTO toDTO(Enseignant e) {
        return new EnseignantDTO(
            e.getId(),
            e.getNom(),
            e.getPrenom(),
            e.getEmail(),
            e.getTelephone(),
            e.getMatricule(),
            e.getGrade(),
            e.getCreateBy(),
            e.getCreateAt(),
            e.isActif()
        );
    }

    public EnseignantDTO ajouterEnseignant(EnseignantDTO enseignantDTO) {
        log.info("Ajout d'un nouvel enseignant: {}", enseignantDTO);
        Enseignant enseignant = toEntity(enseignantDTO);
        Enseignant savedEnseignant = enseignantRepository.save(enseignant);
        return toDTO(savedEnseignant);
    }

    public List<EnseignantDTO> listerTousEnseignants() {
        log.info("Récupération de tous les enseignants");
        return enseignantRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EnseignantDTO obtenirEnseignantParId(Long id) {
        log.info("Récupération de l'enseignant avec ID: {}", id);
        return enseignantRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));
    }

    public EnseignantDTO modifierEnseignant(Long id, EnseignantDTO enseignantDTO) {
        log.info("Modification de l'enseignant avec ID: {}", id);
        Enseignant existingEnseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        // Mise à jour manuelle des champs
        existingEnseignant.setNom(enseignantDTO.nom());
        existingEnseignant.setPrenom(enseignantDTO.prenom());
        existingEnseignant.setEmail(enseignantDTO.email());
        existingEnseignant.setTelephone(enseignantDTO.telephone());
        existingEnseignant.setMatricule(enseignantDTO.matricule());
        existingEnseignant.setGrade(enseignantDTO.grade());
        existingEnseignant.setCreateBy(enseignantDTO.createBy());
        existingEnseignant.setCreateAt(enseignantDTO.createAt());
        existingEnseignant.setActif(Boolean.TRUE.equals(enseignantDTO.actif()));

        Enseignant updatedEnseignant = enseignantRepository.save(existingEnseignant);
        return toDTO(updatedEnseignant);
    }

    public void supprimerEnseignant(Long id) {
        log.info("Suppression de l'enseignant avec ID: {}", id);
        enseignantRepository.deleteById(id);
    }

    public List<EnseignantDTO> rechercherEnseignantsParNom(String nom) {
        log.info("Recherche d'enseignants avec nom contenant: {}", nom);
        return enseignantRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EnseignantDTO activerEnseignant(Long id) throws EnseignantNotFoundException {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));

        if (enseignant.isActif()) {
            throw new ConflitEtatException("L'enseignant est déjà actif");
        }

        enseignant.setActif(true);
        return toDTO(enseignantRepository.save(enseignant));
    }

    public EnseignantDTO desactiverEnseignant(Long id) throws EnseignantNotFoundException {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));

        if (!enseignant.isActif()) {
            throw new ConflitEtatException("L'enseignant est déjà inactif");
        }

        enseignant.setActif(false);
        return toDTO(enseignantRepository.save(enseignant));
    }

    public List<EnseignantDTO> findAllEnseignants() {
        return enseignantRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EnseignantDTO findEnseignantById(Long id) throws EnseignantNotFoundException {
        return enseignantRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));
    }
}
