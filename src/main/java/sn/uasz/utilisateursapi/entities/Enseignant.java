package sn.uasz.utilisateursapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import sn.uasz.utilisateursapi.enums.Grade;

/**
 * Représente un enseignant dans le système.
 * Cette classe contient toutes les informations personnelles et professionnelles
 * d'un enseignant de l'université.
 */
@Entity @NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Enseignant {
    /** Identifiant unique de l'enseignant */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom de l'enseignant */
    private String nom;

    /** Prénom de l'enseignant */
    private String prenom;

    /** Adresse email professionnelle de l'enseignant */
    @Column(unique = true)
    private String email;
    /** Numéro de téléphone de l'enseignant */
    private String telephone;

    /** Numéro matricule unique de l'enseignant */
    private String matricule;

    /** Grade académique de l'enseignant (ex: Professeur, Maître de conférences, etc.) */
    @Enumerated(EnumType.STRING)
    private Grade grade;

    /** Utilisateur ayant créé l'enregistrement */
    private String createBy;

    /** Date de création de l'enregistrement */
    private LocalDate createAt;
    @Builder.Default
    private boolean actif = true;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "enseignant_roles",
            joinColumns = @JoinColumn(name = "enseignant_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;


    // Getters/Setters
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean getActif() {
        return actif;
    }
}