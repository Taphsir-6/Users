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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Validated
public class EtudiantService {
    private final EtudiantRepository etudiantRepository;

    private final EtudiantMapper etudiantMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private final Random random = new Random();

    public EtudiantService(EtudiantRepository etudiantRepository, EtudiantMapper etudiantMapper) {
        this.etudiantRepository = etudiantRepository;
        this.etudiantMapper = etudiantMapper;
    }

    @Transactional
    public EtudiantDTO ajouterEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = etudiantMapper.toEntity(etudiantDTO);
        // Générer l'email
        String nom = etudiant.getNom();
        String prenom = etudiant.getPrenom();
        String email = genererEmail(nom, prenom);
        etudiant.setEmail(email);
        etudiant.setCreateat(new Date().toString());
        etudiant.setCreateby("SYSTEM"); // ou récupérer l'utilisateur connecté

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        return etudiantMapper.toDTO(savedEtudiant);
    }

    @Transactional
    public EtudiantDTO modifierEtudiant(int id, EtudiantDTO etudiantDTO) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new EtudiantNotFoundException("Étudiant non trouvé avec l'ID : " + id);
        }

        Etudiant etudiant = optionalEtudiant.get();
        etudiant.setNom(etudiantDTO.getNom());
        etudiant.setPrenom(etudiantDTO.getPrenom());
        etudiant.setMatricule(etudiantDTO.getMatricule());
        // Générer le nouvel email basé sur le nouveau nom/prénom
        String email = genererEmail(etudiantDTO.getNom(), etudiantDTO.getPrenom());
        etudiant.setEmail(email);
        etudiant.setPhoto(etudiantDTO.getPhoto());
        // On ne modifie pas createAt et createby

        // Détacher l'entité pour éviter les problèmes de transaction
        entityManager.detach(etudiant);

        // Sauvegarder avec une nouvelle transaction
        return etudiantMapper.toDTO(etudiantRepository.save(etudiant));
    }

    @Transactional
    public void supprimerEtudiant(int id) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new EtudiantNotFoundException("Étudiant non trouvé avec l'ID : " + id);
        }

        Etudiant etudiant = optionalEtudiant.get();
        etudiantRepository.delete(etudiant);
    }

    /**
     * Obtenir les informations d'un étudiant spécifique
     *
     * @param id l'identifiant de l'étudiant
     * @return les informations de l'étudiant
     */
    public EtudiantDTO obtenirEtudiant(int id) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new EtudiantNotFoundException("Étudiant non trouvé avec l'ID : " + id);
        }

        Etudiant etudiant = optionalEtudiant.get();
        return etudiantMapper.toDTO(etudiant);
    }

    /**
     * Obtenir tous les étudiants
     *
     * @return la liste des étudiants
     */
    public List<EtudiantDTO> findAll() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        return etudiants.stream()
                .map(etudiantMapper::toDTO)
                .toList();
    }

    // Méthode pour générer l'email
    public String genererEmail(String nom, String prenom) {
        String premiereLettrePrenom = prenom.substring(0, 1).toLowerCase();
        String premiereLettreNom = nom.substring(0, 1).toLowerCase();
        //Générer un nombre aléatoire
        int numeroAleatoire = random.nextInt(100);
        return nom.toLowerCase() + premiereLettrePrenom + premiereLettreNom + numeroAleatoire + "@zig.univ.sn";
    }
}
