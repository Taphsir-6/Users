package sn.uasz.utilisateursapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.utilisateursapi.entities.Enseignant;

import java.util.List;

/**
 * Repository Spring Data JPA pour l'entité {@link Enseignant}.

 * Fournit des méthodes standards pour :
 * - la persistance (enregistrement, mise à jour, suppression)
 * - la recherche personnalisée
 * - la pagination/sorting

 * Hérite de {@link JpaRepository}, ce qui offre un grand nombre
 * de fonctionnalités prêtes à l’emploi.
 */
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    /**
     * Recherche tous les enseignants dont le nom contient
     * la chaîne spécifiée (sans sensibilité à la casse).

     * Requête SQL équivalente :
     * <pre>
     * SELECT * FROM enseignant
     * WHERE LOWER(nom) LIKE LOWER('%:nom%')
     * </pre>
     *
     * @param nom chaîne partielle du nom à rechercher
     * @return liste des enseignants correspondants
     */
    List<Enseignant> findByNomContainingIgnoreCase(String nom);

    /**
     * Vérifie si un enseignant existe déjà avec l’adresse email donnée.
     * Utilisé notamment pour éviter les doublons à la création.
     *
     * @param email l’adresse email à tester
     * @return true si un enseignant existe avec cet email, false sinon
     */
    boolean existsByEmail(String email);
}
