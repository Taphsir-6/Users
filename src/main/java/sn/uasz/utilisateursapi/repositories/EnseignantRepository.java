package sn.uasz.utilisateursapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.utilisateursapi.entities.Enseignant;
import sn.uasz.utilisateursapi.entities.Vacataire;

import java.util.List;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    List<Enseignant> findByNomContainingIgnoreCase(String nom);
    boolean existsByEmail(String email);

}
