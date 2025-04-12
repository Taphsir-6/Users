/**
 * Interface Spring Data JPA pour la gestion des vacataires.
 * Cette interface étend JpaRepository pour fournir des opérations CRUD
 * standard sur les vacataires et définit des méthodes de requête personnalisées.
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 */
package sn.uasz.UtilisateursAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.uasz.UtilisateursAPI.entities.Vacataire;
import java.util.List;
import java.util.Optional;

/**
 * Interface repository pour la gestion des vacataires.
 * Fournit des méthodes pour interagir avec la base de données des vacataires.
 * 
 * @see Vacataire
 */
@Repository
public interface VacataireRepository extends JpaRepository<Vacataire, Long> {
    /**
     * Recherche un vacataire par son adresse email.
     * 
     * @param email L'adresse email du vacataire à rechercher
     * @return Le vacataire correspondant ou null si non trouvé
     */
    Vacataire findByEmail(String email);

    /**
     * Recherche un vacataire actif par son ID.
     * 
     * @param id L'identifiant du vacataire à rechercher
     * @param actif Le statut d'activité du vacataire (true pour actif)
     * @return Le vacataire correspondant ou null si non trouvé
     */
    Optional<Vacataire> findByIdAndActif(Long id, boolean actif);

    /**
     * Trouve tous les vacataires actifs.
     * 
     * @param actif Le statut actif (true pour actif, false pour inactif)
     * @return La liste des vacataires correspondants
     */
    List<Vacataire> findByActif(boolean actif);
}
