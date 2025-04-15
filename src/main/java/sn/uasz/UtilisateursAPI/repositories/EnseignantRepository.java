package sn.uasz.UtilisateursAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.UtilisateursAPI.entities.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}
