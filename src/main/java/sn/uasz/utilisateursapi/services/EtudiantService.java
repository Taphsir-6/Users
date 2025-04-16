package sn.uasz.UtilisateursAPI.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import sn.uasz.UtilisateursAPI.dtos.EtudiantDTO;
import sn.uasz.UtilisateursAPI.entities.Etudiant;
import sn.uasz.UtilisateursAPI.mappers.EtudiantMapper;
import sn.uasz.UtilisateursAPI.repositories.EtudiantRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EtudiantMapper etudiantMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public EtudiantDTO ajouterEtudiant(@Validated EtudiantDTO etudiantDTO) {
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
    public EtudiantDTO modifierEtudiant(int id, @Validated EtudiantDTO etudiantDTO) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new RuntimeException("Étudiant non trouvé avec l'ID : " + id);
        }

        Etudiant etudiant = optionalEtudiant.get();
        etudiant.setNom(etudiantDTO.getNom());
        etudiant.setPrenom(etudiantDTO.getPrenom());
        etudiant.setMatricule(etudiantDTO.getMatricule());
        etudiant.setEmail(etudiantDTO.getEmail());
        etudiant.setPhoto(etudiantDTO.getPhoto());
        // On ne modifie pas createat et createby

        // Détacher l'entité pour éviter les problèmes de transaction
        entityManager.detach(etudiant);

        // Sauvegarder avec une nouvelle transaction
        return etudiantMapper.toDTO(etudiantRepository.save(etudiant));
    }

    @Transactional
    public void supprimerEtudiant(int id) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById((long) id);
        if (!optionalEtudiant.isPresent()) {
            throw new RuntimeException("Étudiant non trouvé avec l'ID : " + id);
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
            throw new RuntimeException("Étudiant non trouvé avec l'ID : " + id);
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
                .collect(Collectors.toList());
    }

    // Méthode pour générer l'email
    private String genererEmail(String nom, String prenom) {
        String premiereLettrePrenom = prenom.substring(0, 1).toLowerCase();
        String premiereLettreNom = nom.substring(0, 1).toLowerCase();
        //Générer un nombre aléatoire
        int numeroAleatoire = (int) (Math.random() * 100);
        return nom.toLowerCase() + premiereLettrePrenom + premiereLettreNom + numeroAleatoire + "@zig.univ.sn";
    }
}
