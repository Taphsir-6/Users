package sn.uasz.utilisateursapi.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private String email;

    /** Numéro de téléphone de l'enseignant */
    private String telephone;

    /** Numéro matricule unique de l'enseignant */
    private String matricule;

    /** Grade académique de l'enseignant (ex: Professeur, Maître de conférences, etc.) */
    private String grade;

    /** Utilisateur ayant créé l'enregistrement */
    private String createby;

    /** Date de création de l'enregistrement */
    private String createat;
}
