package sn.uasz.utilisateursapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) représentant un rôle.
 *
 * Ce DTO permet de transférer les données liées à un rôle entre les différentes couches de l'application
 * sans exposer directement l'entité persistante {@code Role}.
 *
 * <p><strong>Champs validés :</strong></p>
 * <ul>
 *     <li><code>libelle</code> : ne doit pas être vide</li>
 *     <li><code>description</code> : optionnelle mais limitée en taille</li>
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
public class RoleDTO {

    /**
     * Identifiant unique du rôle (généré automatiquement).
     */
    private Long id;

    /**
     * Libellé du rôle (ex : ADMIN, ETUDIANT, etc.). Ce champ est requis.
     */
    @NotBlank(message = "Le libellé est obligatoire.")
    private String libelle;

    /**
     * Description du rôle. Ce champ est optionnel mais peut être limité en taille.
     */
    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères.")
    private String description;
}
