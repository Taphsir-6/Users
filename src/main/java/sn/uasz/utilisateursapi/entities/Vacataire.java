package sn.uasz.utilisateursapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;
import lombok.Data;

/**
 * Entité représentant un vacataire dans le système.
 * Un vacataire est un enseignant ou un intervenant qui travaille sur une base temporaire.
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 * 
 * @see VacataireDTO
 * @see VacataireRepository
 * @see VacataireService
 * 
 * <h3>Exemple d'utilisation</h3>
 * <pre>
 * {@code
 * // Création d'un nouveau vacataire
 * Vacataire vacataire = new Vacataire();
 * vacataire.setNom("Doe");
 * vacataire.setPrenom("John");
 * vacataire.setEmail("john.doe@example.com");
 * vacataire.setActif(true);
 * 
 * // Enregistrement dans la base de données
 * vacataireRepository.save(vacataire);
 * }
 * </pre>
 */
@Data
@Entity
@Table(name = "vacataires")
public class Vacataire {
    /**
     * Identifiant unique du vacataire.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(unique = true)
    private String email;

    /**
     * Numéro de téléphone du vacataire.
     * Format : +33612345678
     */
    @Pattern(regexp = "^(\\+33\\s?\\d{9}|0\\s?\\d{9})$", message = "Format de téléphone invalide. Formats acceptés : +336123456, +221778776932, +33 612 34 56 , +221 77 877 69 32")
    private String telephone;

    /**
     * Spécialité principale du vacataire (par exemple: Mathématiques, Français, etc.).
     */
    private String specialite;

    /**
     * Statut d'activité du vacataire.
     * Définit si le vacataire est actuellement actif dans le système.
     */
    private boolean actif = true;

    /**
     * Date de création du vacataire dans le système.
     * Générée automatiquement lors de la création.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date dateCreation;

    /**
     * Date de dernière modification du vacataire.
     * Générée automatiquement lors de chaque mise à jour.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification;

    /**
     * Méthode appelée automatiquement par JPA lors de la création d'un nouvel enregistrement.
     */
    @PrePersist
    protected void onCreate() {
        dateCreation = new Date();
    }

    /**
     * Méthode appelée automatiquement par JPA lors de la mise à jour d'un enregistrement.
     */
    @PreUpdate
    protected void onUpdate() {
        dateModification = new Date();
    }
}
