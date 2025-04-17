package sn.uasz.UtilisateursAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.UtilisateursAPI.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Etudiant findByNom(String nom);
    Etudiant findByMatricule(String matricule);
}
