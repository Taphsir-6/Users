package sn.uasz.utilisateursapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.utilisateursapi.entities.Etudiant;

/**
 * Interface pour la gestion des opérations de persistance des étudiants.
 * Étend JpaRepository pour faciliter les opérations CRUD (Create, Read, Update, Delete) sur les étudiants.
 * Auteur : KOUMBA THIONGANE
 * Date de dernière modification : 07 MAI 2025
 */
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    /**
     * Recherche un étudiant par son nom.
     *
     * @param nom Le nom de l'étudiant à rechercher
     * @return L'étudiant correspondant au nom, ou null si aucun étudiant n'est trouvé
     */
    Etudiant findByNom(String nom);

    /**
     * Recherche un étudiant par son matricule.
     *
     * @param matricule Le matricule de l'étudiant à rechercher
     * @return L'étudiant correspondant au matricule, ou null si aucun étudiant n'est trouvé
     */
    Etudiant findByMatricule(String matricule);
}
