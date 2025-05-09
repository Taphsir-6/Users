package sn.uasz.utilisateursapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Entité représentant un étudiant dans la base de données.
 *
 * Cette classe contient les informations personnelles d'un étudiant,
 * y compris ses rôles dans l'application, la personne ayant créé la fiche,
 * et la date de création.
 *
 * Champs :
 * - id : identifiant unique de l'étudiant (clé primaire auto-générée)
 * - nom : nom de l'étudiant
 * - prenom : prénom de l'étudiant
 * - matricule : identifiant unique de l'étudiant
 * - email : adresse email de l'étudiant (doit être unique)
 * - dateNaissance : date de naissance
 * - lieuNaissance : lieu de naissance
 * - roles : liste des rôles associés à l'étudiant
 * - createdBy : nom ou identifiant de l'utilisateur ayant créé la fiche
 * - createdAt : date de création de la fiche
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Etudiant {

    /**
     * Identifiant unique de l'étudiant (généré automatiquement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de l'étudiant.
     * Ce champ est obligatoire.
     */
    @Column(nullable = false)
    private String nom;

    /**
     * Prénom de l'étudiant.
     * Ce champ est obligatoire.
     */
    @Column(nullable = false)
    private String prenom;

    /**
     * Matricule unique de l'étudiant.
     * Ce champ est obligatoire et doit être unique.
     */
    @Column(nullable = false, unique = true)
    private String matricule;

    /**
     * Adresse email unique de l'étudiant.
     * Ce champ est obligatoire et doit être unique.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Date de naissance de l'étudiant.
     * Ce champ est obligatoire.
     */
    @Column(nullable = false)
    private LocalDate dateNaissance;

    /**
     * Lieu de naissance de l'étudiant.
     * Ce champ est obligatoire.
     */
    @Column(nullable = false)
    private String lieuNaissance;

    /**
     * Liste des rôles attribués à l'étudiant.
     * Association Many-to-Many avec l'entité Role.
     * Les rôles sont chargés en mode paresseux (lazy loading).
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "etudiant_roles",
            joinColumns = @JoinColumn(name = "etudiant_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    /**
     * Nom ou identifiant de l'utilisateur ayant créé la fiche.
     */
    private String createdBy;

    /**
     * Date de création de la fiche (au format ISO yyyy-MM-dd).
     */
    @Column(name = "created_at")
    private LocalDate createdAt;
}
