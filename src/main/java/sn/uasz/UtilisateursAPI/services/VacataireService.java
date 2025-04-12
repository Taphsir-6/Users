/**
 * Service Spring pour la gestion des vacataires.
 * Cette classe fournit les méthodes métier pour gérer les vacataires dans le système.
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 */
package sn.uasz.UtilisateursAPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.uasz.UtilisateursAPI.dtos.VacataireDTO;
import sn.uasz.UtilisateursAPI.entities.Vacataire;
import sn.uasz.UtilisateursAPI.repositories.VacataireRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des vacataires.
 * Fournit les opérations CRUD et les méthodes métier liées aux vacataires.
 * 
 * @see Vacataire
 * @see VacataireDTO
 * @see VacataireRepository
 */
@Service
@RequiredArgsConstructor
public class VacataireService {
    private final VacataireRepository vacataireRepository;

    /**
     * Convertit un DTO en entité Vacataire.
     * 
     * @param dto Le DTO à convertir
     * @return L'entité Vacataire correspondante
     */
    private Vacataire convertToEntity(VacataireDTO dto) {
        Vacataire vacataire = new Vacataire();
        vacataire.setId(dto.getId());
        vacataire.setNom(dto.getNom());
        vacataire.setPrenom(dto.getPrenom());
        vacataire.setEmail(dto.getEmail());
        vacataire.setTelephone(dto.getTelephone());
        vacataire.setSpecialite(dto.getSpecialite());
        vacataire.setActif(dto.isActif());
        return vacataire;
    }

    /**
     * Convertit une entité Vacataire en DTO.
     * 
     * @param vacataire L'entité à convertir
     * @return Le DTO correspondant
     */
    private VacataireDTO convertToDTO(Vacataire vacataire) {
        VacataireDTO dto = new VacataireDTO();
        dto.setId(vacataire.getId());
        dto.setNom(vacataire.getNom());
        dto.setPrenom(vacataire.getPrenom());
        dto.setEmail(vacataire.getEmail());
        dto.setTelephone(vacataire.getTelephone());
        dto.setSpecialite(vacataire.getSpecialite());
        dto.setActif(vacataire.isActif());
        return dto;
    }

    /**
     * Crée un nouveau vacataire dans le système.
     * 
     * @param vacataireDTO Les données du vacataire à créer
     * @return Le DTO du vacataire créé
     */
    @Transactional
    public VacataireDTO creerVacataire(VacataireDTO vacataireDTO) {
        Vacataire vacataire = convertToEntity(vacataireDTO);
        vacataire.setDateCreation(new Date());
        vacataire.setDateModification(new Date());
        vacataire.setActif(true); // Assure que le vacataire est actif par défaut
        return convertToDTO(vacataireRepository.save(vacataire));
    }

    /**
     * Récupère un vacataire par son ID.
     * 
     * @param id L'identifiant du vacataire
     * @return Le DTO du vacataire trouvé ou null si non trouvé
     */
    public VacataireDTO getVacataire(Long id) {
        return vacataireRepository.findByIdAndActif(id, true)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * Met à jour un vacataire existant.
     * 
     * @param id L'identifiant du vacataire à mettre à jour
     * @param vacataireDTO Les nouvelles données du vacataire
     * @return Le DTO du vacataire mis à jour
     * @throws RuntimeException Si le vacataire n'existe pas
     */
    @Transactional
    public VacataireDTO mettreAJourVacataire(Long id, VacataireDTO vacataireDTO) {
        return vacataireRepository.findByIdAndActif(id, true)
                .map(vacataire -> {
                    vacataire.setNom(vacataireDTO.getNom());
                    vacataire.setPrenom(vacataireDTO.getPrenom());
                    vacataire.setEmail(vacataireDTO.getEmail());
                    vacataire.setTelephone(vacataireDTO.getTelephone());
                    vacataire.setSpecialite(vacataireDTO.getSpecialite());
                    vacataire.setActif(vacataireDTO.isActif());
                    vacataire.setDateModification(new Date());
                    return convertToDTO(vacataireRepository.save(vacataire));
                })
                .orElseThrow(() -> new RuntimeException("Vacataire non trouvé"));
    }

    /**
     * Récupère la liste de tous les vacataires actifs.
     * 
     * @return La liste des vacataires actifs
     */
    public List<VacataireDTO> getAllVacatairesActifs() {
        List<Vacataire> vacataires = vacataireRepository.findByActif(true);
        return vacataires.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Désactive un vacataire du système.
     * 
     * @param id L'identifiant du vacataire à désactiver
     * @return Le DTO du vacataire désactivé
     * @throws RuntimeException Si le vacataire n'existe pas
     */
    @Transactional
    public VacataireDTO desactiverVacataire(Long id) {
        return vacataireRepository.findByIdAndActif(id, true)
                .map(vacataire -> {
                    vacataire.setActif(false);
                    vacataire.setDateModification(new Date());
                    return convertToDTO(vacataireRepository.save(vacataire));
                })
                .orElseThrow(() -> new RuntimeException("Vacataire non trouvé"));
    }

    /**
     * Réactive un vacataire dans le système.
     * 
     * @param id L'identifiant du vacataire à réactiver
     * @return Le DTO du vacataire réactivé
     * @throws RuntimeException Si le vacataire n'existe pas
     */
    @Transactional
    public VacataireDTO reactivierVacataire(Long id) {
        return vacataireRepository.findById(id)
                .map(vacataire -> {
                    vacataire.setActif(true);
                    vacataire.setDateModification(new Date());
                    return convertToDTO(vacataireRepository.save(vacataire));
                })
                .orElseThrow(() -> new RuntimeException("Vacataire non trouvé"));
    }

    /**
     * Supprime un vacataire du système.
     * 
     * @param id L'identifiant du vacataire à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    @Transactional
    public boolean supprimerVacataire(Long id) {
        return vacataireRepository.findByIdAndActif(id, true)
                .map(vacataire -> {
                    vacataireRepository.delete(vacataire);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Récupère un vacataire par son email.
     * 
     * @param email L'email du vacataire à rechercher
     * @return Le DTO du vacataire trouvé ou null si non trouvé
     */
    public VacataireDTO getVacataireByEmail(String email) {
        Vacataire vacataire = vacataireRepository.findByEmail(email);
        return vacataire != null ? convertToDTO(vacataire) : null;
    }

    /**
     * Active un vacataire du système.
     * 
     * @param id L'identifiant du vacataire à activer
     */
    @Transactional
    public void activerVacataire(Long id) {
        Optional<Vacataire> optionalVacataire = vacataireRepository.findById(id);
        if (!optionalVacataire.isPresent()) {
            throw new RuntimeException("Vacataire non trouvé avec l'ID : " + id);
        }
        
        Vacataire vacataire = optionalVacataire.get();
        vacataire.setActif(true);
        vacataire.setDateModification(new Date());
        
        // Détacher l'entité
        //entityManager.detach(vacataire);
        
        // Sauvegarder avec une nouvelle transaction
        vacataireRepository.save(vacataire);
    }

    /**
     * Récupère la liste de tous les vacataires.
     * 
     * @return La liste des vacataires
     */
    public List<Vacataire> findAll() {
        return vacataireRepository.findAll();
    }
}
