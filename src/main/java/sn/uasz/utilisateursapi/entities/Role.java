package sn.uasz.utilisateursapi.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité JPA représentant un rôle attribuable à un utilisateur dans le système.
 * Cette classe est utilisée pour définir différents types de rôles tels que "Administrateur", "Utilisateur", etc.
 * Elle utilise les annotations Lombok pour générer automatiquement les constructeurs, getters, setters et le builder.
 */
@Entity // Indique que cette classe est une entité persistante JPA
@NoArgsConstructor // Génère un constructeur sans argument
@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Getter // Génère automatiquement les getters pour tous les champs
@Setter // Génère automatiquement les setters pour tous les champs
@Builder // Permet l'utilisation du design pattern Builder
public class Role {

    /**
     * Identifiant unique du rôle.
     * Généré automatiquement par la base de données (auto-incrémentation).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Libellé du rôle (ex : "ADMIN", "ETUDIANT", etc.).
     */
    private String libelle;

    /**
     * Description textuelle du rôle permettant d’en préciser l’usage ou les permissions.
     */
    private String description;
}
