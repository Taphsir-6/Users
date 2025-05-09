package sn.uasz.utilisateursapi.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.entities.Role;
import sn.uasz.utilisateursapi.exceptions.RoleNotFoundException;
import sn.uasz.utilisateursapi.mappers.RoleMapper;
import sn.uasz.utilisateursapi.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Service de gestion des rôles.
 *
 * Cette classe contient des méthodes pour ajouter, modifier, supprimer
 * et récupérer des rôles dans la base de données.
 *
 * Auteur : KOUMBA THIONGANE
 * Date de dernière modification : 07 MAI 2025
 */
@Service
@Validated
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    // Gestion des entités de la base de données
    @PersistenceContext
    private EntityManager entityManager;

    private final Random random = new Random();

    /**
     * Constructeur du service de rôles.
     *
     * @param roleRepository Le repository des rôles.
     * @param roleMapper Le mapper pour transformer entre DTO et entités.
     */
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * Méthode pour ajouter un nouveau rôle à la base de données.
     *
     * @param roleDTO Les informations du rôle à ajouter.
     * @return Le DTO représentant le rôle ajouté.
     */
    @Transactional
    public RoleDTO ajouterRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);

        // Générer l'email de l'étudiant
        String libelle = role.getLibelle();
        String description = role.getDescription();

        // Sauvegarder le rôle
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDTO(savedRole);
    }

    /**
     * Méthode pour modifier les informations d’un rôle existant.
     *
     * @param id L'ID du rôle à modifier.
     * @param roleDTO Les nouvelles informations du rôle.
     * @return Le DTO représentant le rôle modifié.
     * @throws RoleNotFoundException Si le rôle avec cet ID n'existe pas.
     */
    @Transactional
    public RoleDTO modifierRole(int id, RoleDTO roleDTO) {
        Optional<Role> optionalRole = roleRepository.findById((long) id);
        if (!optionalRole.isPresent()) {
            throw new RoleNotFoundException("Rôle non trouvé avec l'ID : " + id);
        }

        Role role = optionalRole.get();
        role.setLibelle(roleDTO.getLibelle());
        role.setDescription(roleDTO.getDescription());
        // Détacher l'entité pour éviter les problèmes de transaction
        entityManager.detach(role);

        // Sauvegarder les changements
        return roleMapper.toDTO(roleRepository.save(role));
    }

    /**
     * Méthode pour supprimer un rôle existant.
     *
     * @param id L'ID du rôle à supprimer.
     * @throws RoleNotFoundException Si le rôle avec cet ID n'existe pas.
     */
    @Transactional
    public void supprimerRole(int id) {
        Optional<Role> optionalRole = roleRepository.findById((long) id);
        if (!optionalRole.isPresent()) {
            throw new RoleNotFoundException("Rôle non trouvé avec l'ID : " + id);
        }

        Role role = optionalRole.get();
        roleRepository.delete(role);
    }

    /**
     * Méthode pour récupérer un rôle par son ID.
     *
     * @param id L'ID du rôle à récupérer.
     * @return Le DTO représentant le rôle récupéré.
     * @throws RoleNotFoundException Si le rôle avec cet ID n'existe pas.
     */
    public RoleDTO obtenirRole(int id) {
        Optional<Role> optionalRole = roleRepository.findById((long) id);
        if (!optionalRole.isPresent()) {
            throw new RoleNotFoundException("Rôle non trouvé avec l'ID : " + id);
        }

        Role role = optionalRole.get();
        return roleMapper.toDTO(role);
    }

    /**
     * Méthode pour récupérer tous les rôles.
     *
     * @return La liste des rôles en DTO.
     */
    public List<RoleDTO> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toDTO)
                .toList();
    }

}
