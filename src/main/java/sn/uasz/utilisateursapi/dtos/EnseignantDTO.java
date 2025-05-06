package sn.uasz.utilisateursapi.dtos;

import jakarta.validation.constraints.*;
import sn.uasz.utilisateursapi.enums.Grade;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) représentant un enseignant dans le système.

 * Cette classe est immuable et utilise la syntaxe Java `record`, ce qui la rend concise,
 * sûre pour la sérialisation, et pratique pour l'utilisation dans des contextes RESTful.

 * Les annotations de validation Jakarta (ex-javax) sont utilisées pour garantir l'intégrité
 * des données en entrée via des contrôleurs ou des formulaires.

 * Exemple d'utilisation : création d’un enseignant via un endpoint POST.
 */
public record EnseignantDTO(

        /* *
         * Identifiant unique de l'enseignant (optionnel lors de la création).
         */
        Long id,

        /* *
         * Nom de l'enseignant.
         * - Obligatoire
         * - Minimum 2 caractères, maximum 50
         */
        @NotBlank @Size(min = 2, max = 50)
        String nom,

        /* *
         * Prénom de l'enseignant.
         * - Obligatoire
         * - Minimum 2 caractères, maximum 50
         */
        @NotBlank @Size(min = 2, max = 50)
        String prenom,

        /* *
         * Adresse email professionnelle de l'enseignant.
         * - Obligatoire
         * - Doit être une adresse email valide
         */
        @NotBlank @Email
        String email,

        /* *
         * Numéro de téléphone de l'enseignant.
         * - Doit correspondre à un format international (+221..., etc.)
         * - Entre 9 et 15 chiffres
         */
        @Pattern(regexp = "^\\+?[0-9]{9,15}$")
        String telephone,

        /* *
         * Numéro matricule unique de l'enseignant.
         * - Obligatoire
         */
        @NotBlank
        String matricule,

        /* *
         * Grade académique de l'enseignant.
         * - Ex : PROFESSEUR, MAITRE_CONFERENCES, VACATAIRE, etc.
         * - Doit être non nul
         */
        @NotNull
        Grade grade,

        /* *
         * Utilisateur ayant créé l’enregistrement.
         * - Optionnel
         */
        String createBy,

        /* *
         * Date de création de l’enregistrement.
         * - Optionnelle
         */
        LocalDate createAt,

        /* *
         * Statut actif ou non de l'enseignant.
         * - Peut être null, à définir par défaut dans le service ou l'entité
         */
        Boolean actif
) {
    /**
     * Retourne le nom complet formaté : "Prénom Nom".
     *
     * @return le nom complet de l’enseignant
     */
    public String nomComplet() {
        return prenom + " " + nom;
    }
}
