package sn.uasz.utilisateursapi.dtos;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) représentant un vacataire.
 * Ce DTO est utilisé pour la communication entre le contrôleur et le service,
 * ainsi que pour la réponse aux requêtes REST.
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 * 
 * @see Vacataire
 * @see VacataireService
 * @see VacataireController
 * 
 * <h3>Exemple d'utilisation</h3>
 * <pre>
 * {@code
 * // Création d'un DTO de vacataire
 * VacataireDTO vacataireDTO = new VacataireDTO();
 * vacataireDTO.setNom("Doe");
 * vacataireDTO.setPrenom("John");
 * vacataireDTO.setEmail("john.doe@example.com");
 * 
 * // Conversion vers l'entité
 * Vacataire vacataire = new Vacataire();
 * vacataire.setNom(vacataireDTO.getNom());
 * vacataire.setPrenom(vacataireDTO.getPrenom());
 * vacataire.setEmail(vacataireDTO.getEmail());
 * }
 * </pre>
 */
@Data
public class VacataireDTO {
    /**
     * Identifiant unique du vacataire.
     * Peut être null lors de la création d'un nouveau vacataire.
     */
    private Long id;
    
    /**
     * Nom du vacataire.
     * Obligatoire, ne doit pas être vide.
     */
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    /**
     * Prénom du vacataire.
     * Obligatoire, ne doit pas être vide.
     */
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    
    /**
     * Adresse email du vacataire.
     * Doit être unique et valide.
     */
    @Email(message = "L'email doit être valide")
    private String email;
    
    /**
     * Numéro de téléphone du vacataire.
     * Format : +33612345678
     */
    private String telephone;
    
    /**
     * Spécialité principale du vacataire (par exemple: Mathématiques, Français, etc.).
     */
    private String specialite;
    
    /**
     * Indicateur de l'activité du vacataire.
     * true si le vacataire est actif, false s'il est inactif.
     */
    private boolean actif;
}
