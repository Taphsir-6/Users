package sn.uasz.utilisateursapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.utilisateursapi.entities.Role;

/**
 * Interface pour la gestion des opérations de persistance des rôles.
 * Étend JpaRepository pour faciliter les opérations CRUD (Create, Read, Update, Delete) sur les rôles.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Recherche un rôle par son nom.
     *
     * @param libelle Le libelle du rôle à rechercher
     * @return Le rôle correspondant au nom, ou null si aucun rôle n'est trouvé
     */
    Role findByLibelle(String libelle);
}
