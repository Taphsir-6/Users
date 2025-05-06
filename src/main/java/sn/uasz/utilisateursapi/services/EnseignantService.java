package sn.uasz.utilisateursapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.exceptions.ConflitEtatException;
import sn.uasz.utilisateursapi.exceptions.EnseignantException;
import sn.uasz.utilisateursapi.exceptions.EnseignantNotFoundException;
import sn.uasz.utilisateursapi.mappers.EnseignantMapper;
import sn.uasz.utilisateursapi.repositories.EnseignantRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier central pour la gestion des enseignants.

 * Fournit toutes les opérations courantes (CRUD + extra) sur l'entité {@link Enseignant}.
 * Implémente des règles métier telles que l’unicité des emails, l’activation/désactivation
 * et la validation des états.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final EnseignantMapper enseignantMapper;

    /**
     * Ajoute un nouvel enseignant dans le système.
     * Vérifie l’unicité de l’email avant insertion.
     *
     * @param enseignantDTO données du nouvel enseignant
     * @return l'enseignant créé, converti en DTO
     * @throws EnseignantException si l’email est déjà utilisé
     */
    public EnseignantDTO ajouterEnseignant(EnseignantDTO enseignantDTO) {
        log.info("Ajout d'un nouvel enseignant: {}", enseignantDTO);

        if (enseignantRepository.existsByEmail(enseignantDTO.email())) {
            throw new EnseignantException("Email déjà utilisé : " + enseignantDTO.email());
        }

        Enseignant enseignant = enseignantMapper.toEntity(enseignantDTO);
        Enseignant savedEnseignant = enseignantRepository.save(enseignant);
        return enseignantMapper.toDTO(savedEnseignant);
    }

    /**
     * Retourne tous les enseignants enregistrés.
     *
     * @return liste des enseignants au format DTO
     */
    public List<EnseignantDTO> listerTousEnseignants() {
        log.info("Récupération de tous les enseignants");
        return enseignantRepository.findAll()
                .stream()
                .map(enseignantMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retourne un enseignant par son identifiant.
     *
     * @param id identifiant de l’enseignant
     * @return DTO de l’enseignant trouvé
     * @throws RuntimeException si non trouvé (❗améliorable avec une exception dédiée)
     */
    public EnseignantDTO obtenirEnseignantParId(Long id) {
        log.info("Récupération de l'enseignant avec ID: {}", id);
        return enseignantRepository.findById(id)
                .map(enseignantMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));
    }

    /**
     * Met à jour un enseignant existant à partir d’un DTO.
     *
     * @param id identifiant de l’enseignant à modifier
     * @param enseignantDTO données mises à jour
     * @return DTO de l’enseignant modifié
     * @throws RuntimeException si l’enseignant n’existe pas
     */
    public EnseignantDTO modifierEnseignant(Long id, EnseignantDTO enseignantDTO) {
        log.info("Modification de l'enseignant avec ID: {}", id);
        Enseignant existingEnseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        enseignantMapper.updateEnseignantFromDTO(enseignantDTO, existingEnseignant);
        Enseignant updatedEnseignant = enseignantRepository.save(existingEnseignant);
        return enseignantMapper.toDTO(updatedEnseignant);
    }

    /**
     * Supprime un enseignant à partir de son ID.
     *
     * @param id identifiant de l’enseignant à supprimer
     */
    public void supprimerEnseignant(Long id) {
        log.info("Suppression de l'enseignant avec ID: {}", id);
        enseignantRepository.deleteById(id);
    }

    /**
     * Recherche les enseignants dont le nom contient une chaîne donnée.
     *
     * @param nom chaîne à rechercher dans les noms
     * @return liste des enseignants correspondants (DTOs)
     */
    public List<EnseignantDTO> rechercherEnseignantsParNom(String nom) {
        log.info("Recherche d'enseignants avec nom contenant: {}", nom);
        return enseignantRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(enseignantMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Active un enseignant inactif.
     *
     * @param id identifiant de l’enseignant
     * @return enseignant activé (DTO)
     * @throws EnseignantNotFoundException si introuvable
     * @throws ConflitEtatException si déjà actif
     */
    public EnseignantDTO activerEnseignant(Long id) throws EnseignantNotFoundException {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));

        if (enseignant.isActif()) {
            throw new ConflitEtatException("L'enseignant est déjà actif");
        }

        enseignant.setActif(true);
        return enseignantMapper.toDTO(enseignantRepository.save(enseignant));
    }

    /**
     * Désactive un enseignant actif.
     *
     * @param id identifiant de l’enseignant
     * @return enseignant désactivé (DTO)
     * @throws EnseignantNotFoundException si introuvable
     * @throws ConflitEtatException si déjà inactif
     */
    public EnseignantDTO desactiverEnseignant(Long id) throws EnseignantNotFoundException {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));

        if (!enseignant.isActif()) {
            throw new ConflitEtatException("L'enseignant est déjà inactif");
        }

        enseignant.setActif(false);
        return enseignantMapper.toDTO(enseignantRepository.save(enseignant));
    }

    /**
     * ⚠️ Duplication possible — à fusionner avec {@code listerTousEnseignants()}.
     */
    public List<EnseignantDTO> findAllEnseignants() {
        return enseignantRepository.findAll()
                .stream()
                .map(enseignantMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * ⚠️ Duplication possible — à fusionner avec {@code obtenirEnseignantParId()}.
     */
    public EnseignantDTO findEnseignantById(Long id) throws EnseignantNotFoundException {
        return enseignantRepository.findById(id)
                .map(enseignantMapper::toDTO)
                .orElseThrow(() -> new EnseignantNotFoundException("Enseignant non trouvé"));
    }
}
