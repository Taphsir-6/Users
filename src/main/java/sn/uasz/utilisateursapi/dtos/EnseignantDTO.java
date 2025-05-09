package sn.uasz.utilisateursapi.dtos;

import jakarta.validation.constraints.*;
import sn.uasz.utilisateursapi.entities.Role;
import sn.uasz.utilisateursapi.enums.Grade;

import java.time.LocalDate;
import java.util.List;

/**
 * Représente un Data Transfer Object (DTO) pour un enseignant.
 * Ce DTO est utilisé pour transférer les données d’un enseignant entre les couches de l’application.
 *
 * <p>Les validations sont appliquées via les annotations Jakarta Bean Validation pour garantir l'intégrité des données.</p>
 *
 * @param id         L'identifiant unique de l'enseignant.
 * @param nom        Le nom de l'enseignant (entre 2 et 50 caractères, non vide).
 * @param prenom     Le prénom de l'enseignant (entre 2 et 50 caractères, non vide).
 * @param email      L’adresse e-mail de l’enseignant (format valide, non vide).
 * @param telephone  Le numéro de téléphone de l'enseignant (doit correspondre à un format international valide).
 * @param matricule  Le matricule de l'enseignant (obligatoire).
 * @param grade      Le grade de l’enseignant (ne peut pas être nul).
 * @param roles      La liste des rôles associés à l'enseignant (optionnelle).
 * @param createBy   Le nom de l'utilisateur ayant créé cet enseignant (optionnel).
 * @param createAt   La date de création de l'enregistrement (optionnelle).
 * @param actif      Indique si l'enseignant est actif (true ou false, optionnel).
 */
public record EnseignantDTO(
        Long id,
        @NotBlank @Size(min = 2, max = 50)
        String nom,
        @NotBlank @Size(min = 2, max = 50)
        String prenom,
        @NotBlank @Email
        String email,
        @Pattern(regexp = "^\\+?\\d{9,15}$")
        String telephone,
        @NotBlank
        String matricule,
        @NotNull
        Grade grade,
        List<Role> roles,
        String createBy,
        LocalDate createAt,
        Boolean actif

) {
    /**
     * Retourne le nom complet de l’enseignant en concaténant le prénom et le nom.
     *
     * @return le nom complet de l’enseignant (ex. : "Jean Dupont").
     */
    public String nomComplet() {
        return prenom + " " + nom;
    }
}