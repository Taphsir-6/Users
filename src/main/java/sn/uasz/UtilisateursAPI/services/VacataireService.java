package sn.uasz.UtilisateursAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sn.uasz.UtilisateursAPI.dtos.VacataireDTO;
import sn.uasz.UtilisateursAPI.entities.Vacataire;
import sn.uasz.UtilisateursAPI.repositories.VacataireRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class VacataireService {
    @Autowired
    private VacataireRepository vacataireRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public VacataireDTO ajouterVacataire(@Validated VacataireDTO vacataireDTO) {
        Vacataire vacataire = new Vacataire();
        vacataire.setNom(vacataireDTO.getNom());
        vacataire.setPrenom(vacataireDTO.getPrenom());
        vacataire.setEmail(vacataireDTO.getEmail());
        vacataire.setTelephone(vacataireDTO.getTelephone());
        vacataire.setSpecialite(vacataireDTO.getSpecialite());
        vacataire.setActif(true);
        vacataire.setDateCreation(new Date());
        vacataire.setDateModification(new Date());
        
        Vacataire savedVacataire = vacataireRepository.save(vacataire);
        return convertToDTO(savedVacataire);
    }

    @Transactional
    public VacataireDTO modifierVacataire(Long id, @Validated VacataireDTO vacataireDTO) {
        Optional<Vacataire> optionalVacataire = vacataireRepository.findById(id);
        if (!optionalVacataire.isPresent()) {
            throw new RuntimeException("Vacataire non trouvé avec l'ID : " + id);
        }
        
        Vacataire vacataire = optionalVacataire.get();
        vacataire.setNom(vacataireDTO.getNom());
        vacataire.setPrenom(vacataireDTO.getPrenom());
        vacataire.setEmail(vacataireDTO.getEmail());
        vacataire.setTelephone(vacataireDTO.getTelephone());
        vacataire.setSpecialite(vacataireDTO.getSpecialite());
        vacataire.setActif(vacataireDTO.isActif());
        vacataire.setDateModification(new Date());
        
        // Détacher l'entité pour éviter les problèmes de transaction
        entityManager.detach(vacataire);
        
        // Sauvegarder avec une nouvelle transaction
        return convertToDTO(vacataireRepository.save(vacataire));
    }

    @Transactional
    public void supprimerVacataire(Long id) {
        Optional<Vacataire> optionalVacataire = vacataireRepository.findById(id);
        if (!optionalVacataire.isPresent()) {
            throw new RuntimeException("Vacataire non trouvé avec l'ID : " + id);
        }
        
        Vacataire vacataire = optionalVacataire.get();
        vacataireRepository.delete(vacataire);
    }

    @Transactional
    public void desactiverVacataire(Long id) {
        Optional<Vacataire> optionalVacataire = vacataireRepository.findById(id);
        if (!optionalVacataire.isPresent()) {
            throw new RuntimeException("Vacataire non trouvé avec l'ID : " + id);
        }
        
        Vacataire vacataire = optionalVacataire.get();
        vacataire.setActif(false);
        vacataire.setDateModification(new Date());
        
        // Détacher l'entité
        entityManager.detach(vacataire);
        
        // Sauvegarder avec une nouvelle transaction
        vacataireRepository.save(vacataire);
    }

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
        entityManager.detach(vacataire);
        
        // Sauvegarder avec une nouvelle transaction
        vacataireRepository.save(vacataire);
    }

    /**
     * Obtenir les informations d'un vacataire spécifique
     * 
     * @param id l'identifiant du vacataire
     * @return les informations du vacataire
     */
    public VacataireDTO obtenirVacataire(Long id) {
        Optional<Vacataire> optionalVacataire = vacataireRepository.findById(id);
        if (!optionalVacataire.isPresent()) {
            throw new RuntimeException("Vacataire non trouvé avec l'ID : " + id);
        }
        
        Vacataire vacataire = optionalVacataire.get();
        return convertToDTO(vacataire);
    }

    /**
     * Obtenir tous les vacataires
     * 
     * @return la liste des vacataires
     */
    public List<Vacataire> findAll() {
        return vacataireRepository.findAll();
    }

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
}
