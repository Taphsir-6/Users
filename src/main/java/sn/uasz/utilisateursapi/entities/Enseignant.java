package sn.uasz.utilisateursapi.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import sn.uasz.utilisateursapi.enums.Grade;

/**
 * Représente un enseignant dans le système universitaire.
 *
 * Cette entité est persistée dans une base de données relationnelle à l'aide de JPA.
 * Elle contient des informations personnelles et professionnelles telles que
 * le nom, prénom, email, téléphone, matricule, grade et les métadonnées
 * liées à la création.
 *
 * L'objet est automatiquement géré avec Lombok pour générer les constructeurs, getters,
 * setters, et builder.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Enseignant {

    /**
     * Identifiant unique de l'enseignant.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de famille de l'enseignant.
     */
    private String nom;

    /**
     * Prénom de l'enseignant.
     */
    private String prenom;

    /**
     * Adresse e-mail professionnelle.
     * Doit être unique dans la base de données.
     */
    @Column(unique = true)
    private String email;

    /**
     * Numéro de téléphone de l'enseignant.
     */
    private String telephone;

    /**
     * Numéro matricule unique attribué à l'enseignant.
     * Ce champ doit être unique par enseignant dans le système.
     */
    private String matricule;

    /**
     * Grade académique de l'enseignant.
     * Ce champ est basé sur une énumération {@link Grade}.
     */
    @Enumerated(EnumType.STRING)
    private Grade grade;

    /**
     * Nom d'utilisateur ayant créé l'enregistrement.
     */
    private String createBy;

    /**
     * Date de création de l'enregistrement dans le système.
     */
    private LocalDate createAt;

    /**
     * Indique si l'enseignant est actif dans le système.
     * Par défaut, un enseignant est actif à la création.
     */
    @Builder.Default
    private boolean actif = true;

    /**
     * Getter explicite de l'état actif.
     * (Note : présence de deux méthodes `isActif()` et `getActif()`)
     *
     * @return true si actif, false sinon
     */
    public boolean isActif() {
        return actif;
    }

    /**
     * Setter de l'état actif.
     *
     * @param actif valeur booléenne indiquant si l'enseignant est actif
     */
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    /**
     * Une autre méthode de lecture de l’état actif (toujours retourne false ici).
     * À corriger : cette méthode est incohérente.
     *
     * @return false (erreur probable)
     */
    public boolean getActif() {
        return false; // PROBLEME : ceci retourne toujours false, ce qui est une erreur.
    }
}
