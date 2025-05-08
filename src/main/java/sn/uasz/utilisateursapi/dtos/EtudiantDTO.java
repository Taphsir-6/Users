package sn.uasz.utilisateursapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;
import sn.uasz.utilisateursapi.entities.Role;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) représentant un étudiant.
 *
 * Ce DTO permet d'encapsuler et de transférer les données d'un étudiant à travers les différentes couches
 * de l'application (contrôleurs, services, etc.) sans exposer directement l'entité persistante {@link sn.uasz.utilisateursapi.entities.Etudiant}.
 *
 * Les contraintes de validation sont appliquées via les annotations de {@code jakarta.validation.constraints}
 * afin de garantir l'intégrité des données en entrée (par exemple lors de la création ou de la mise à jour).
 *
 * <p><strong>Validation des champs :</strong></p>
 * <ul>
 *     <li><code>nom</code>, <code>prenom</code>, <code>matricule</code>, <code>email</code>, <code>lieuNaissance</code> : ne doivent pas être vides</li>
 *     <li><code>email</code> : doit avoir un format valide</li>
 *     <li><code>dateNaissance</code> : peut être annotée avec {@code @Past} si nécessaire</li>
 * </ul>
 *
 * @author Koumba Thiongane
 * @version 1.0
 * @since 07 mai 2025
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EtudiantDTO {

    /**
     * Identifiant unique de l'étudiant (généré automatiquement).
     */
    private Long id;

    /**
     * Nom de famille de l'étudiant.
     * Ce champ ne doit pas être vide ni composé uniquement d'espaces.
     */
    @NotBlank(message = "Le nom ne doit pas être vide.")
    private String nom;

    /**
     * Prénom de l'étudiant.
     * Ce champ ne doit pas être vide ni composé uniquement d'espaces.
     */
    @NotBlank(message = "Le prénom ne doit pas être vide.")
    private String prenom;

    /**
     * Matricule unique de l'étudiant.
     * Ce champ est obligatoire.
     */
    @NotBlank(message = "Le matricule est obligatoire.")
    private String matricule;

    /**
     * Adresse e-mail de l'étudiant.
     * Ce champ est obligatoire et doit respecter un format d'email valide.
     */
    @NotBlank(message = "L'adresse e-mail est obligatoire.")
    @Email(message = "L'adresse e-mail n'est pas valide.")
    private String email;

    /**
     * Date de naissance de l'étudiant.
     * Ce champ peut être validé avec {@code @Past} si l'on souhaite restreindre aux dates passées.
     */
    private LocalDate dateNaissance;

    /**
     * Lieu de naissance de l'étudiant.
     * Ce champ est obligatoire.
     */
    @NotBlank(message = "Le lieu de naissance est requis.")
    private String lieuNaissance;

    /**
     * Liste des rôles associés à l'étudiant.
     * Ce champ peut contenir un ou plusieurs rôles selon les droits accordés.
     */
    private List<Role> roles;
}
