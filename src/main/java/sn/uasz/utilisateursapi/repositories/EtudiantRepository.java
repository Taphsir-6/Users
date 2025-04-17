package sn.uasz.utilisateursapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.utilisateursapi.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Etudiant findByNom(String nom);
    Etudiant findByMatricule(String matricule);
}
