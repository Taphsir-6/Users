package sn.uasz.utilisateursapi.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.entities.Etudiant;
import sn.uasz.utilisateursapi.exceptions.EtudiantNotFoundException;
import sn.uasz.utilisateursapi.mappers.EtudiantMapper;
import sn.uasz.utilisateursapi.repositories.EtudiantRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Service de gestion des opérations liées aux étudiants :
 * ajout, modification, suppression, récupération, etc.
 */
@Service
@Validated
public class EtudiantService {

    // Dépendance vers le repository d'accès aux données des étudiants
    private final EtudiantRepository etudiantRepository;

    // Dépendance vers le mapper pour convertir entre Entité et DTO
    private final EtudiantMapper etudiantMapper;

    // EntityManager utilisé pour certaines opérations JPA
    @PersistenceContext
    private EntityManager entityManager;

    // Générateur de nombre aléatoire pour la création des emails
    private final Random random = new Random();

    /**
     * Constructeur avec injection de dépendances.
     */
    public EtudiantService(EtudiantRepository etudiantRepository, EtudiantMapper etudiantMapper) {
        this.etudiantRepository = etudiantRepository;
        this.etudiantMapper = etudiantMapper;
    }

    /**
     * Ajoute un nouvel étudiant dans la base de données.
     * Génère un email à partir du nom et prénom, et ajoute des métadonnées.
     */
    @Transactional
    public EtudiantDTO ajouterEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = etudiantMapper.toEntity(etudiantDTO);

        // Générer un email unique pour l'étudiant
        String nom = etudiant.getNom();
        String prenom = etudiant.getPrenom();
        String email = genererEmail(nom, prenom);
        etudiant.setEmail(email);

        // Ajouter les métadonnées
        etudiant.setCreatedAt(LocalDate.now());
        etudiant.setCreatedBy("SYSTEM");

        // Conserver les autres informations du DTO
        etudiant.setDateNaissance(etudiantDTO.getDateNaissance());
        etudiant.setLieuNaissance(etudiantDTO.getLieuNaissance());
        etudiant.setRoles(etudiantDTO.getRoles());

        // Sauvegarder dans la base et retourner le DTO correspondant
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        return etudiantMapper.toDTO(savedEtudiant);
    }

    /**
     * Modifie un étudiant existant.
     * Si l'étudiant n'existe pas, une exception est levée.
     */
    @Transactional
    public EtudiantDTO modifierEtudiant(int id, EtudiantDTO etudiantDTO) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new EtudiantNotFoundException("Étudiant non trouvé avec l'ID : " + id);
        }

        Etudiant etudiant = optionalEtudiant.get();

        // Mise à jour des champs
        etudiant.setNom(etudiantDTO.getNom());
        etudiant.setPrenom(etudiantDTO.getPrenom());
        etudiant.setMatricule(etudiantDTO.getMatricule());

        // Générer un nouvel email à partir des nouveaux nom/prénom
        String email = genererEmail(etudiantDTO.getNom(), etudiantDTO.getPrenom());
        etudiant.setEmail(email);

        // Mise à jour des autres informations
        etudiant.setDateNaissance(etudiantDTO.getDateNaissance());
        etudiant.setLieuNaissance(etudiantDTO.getLieuNaissance());
        etudiant.setRoles(etudiantDTO.getRoles());

        // Détacher l'objet de la session JPA pour forcer une mise à jour complète
        entityManager.detach(etudiant);

        // Sauvegarder et retourner le nouveau DTO
        return etudiantMapper.toDTO(etudiantRepository.save(etudiant));
    }

    /**
     * Supprime un étudiant à partir de son identifiant.
     * Lève une exception si l'étudiant n'existe pas.
     */
    @Transactional
    public void supprimerEtudiant(int id) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new EtudiantNotFoundException("Étudiant non trouvé avec l'ID : " + id);
        }

        // Suppression de l'étudiant
        etudiantRepository.delete(optionalEtudiant.get());
    }

    /**
     * Retourne un étudiant sous forme de DTO à partir de son identifiant.
     * Lève une exception si l'étudiant est introuvable.
     */
    public EtudiantDTO obtenirEtudiant(int id) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new EtudiantNotFoundException("Étudiant non trouvé avec l'ID : " + id);
        }

        return etudiantMapper.toDTO(optionalEtudiant.get());
    }

    /**
     * Retourne la liste de tous les étudiants sous forme de DTOs.
     */
    public List<EtudiantDTO> findAll() {
        return etudiantRepository.findAll()
                .stream()
                .map(etudiantMapper::toDTO)
                .toList();
    }

    /**
     * Génère un email à partir du nom et prénom de l’étudiant.
     * Exemple : dupontjj42@zig.univ.sn
     */
    public String genererEmail(String nom, String prenom) {
        String premiereLettrePrenom = prenom.substring(0, 1).toLowerCase();
        String premiereLettreNom = nom.substring(0, 1).toLowerCase();
        int numeroAleatoire = random.nextInt(100);
        return nom.toLowerCase() + premiereLettrePrenom + premiereLettreNom + numeroAleatoire + "@zig.univ.sn";
    }
}
