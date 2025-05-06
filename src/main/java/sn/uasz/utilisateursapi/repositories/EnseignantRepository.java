package sn.uasz.utilisateursapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.utilisateursapi.entities.Enseignant;

import java.util.List;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    List<Enseignant> findByNomContainingIgnoreCase(String nom);
    boolean existsByEmail(String email);

}
