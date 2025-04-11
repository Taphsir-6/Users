package sn.uasz.UtilisateursAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.uasz.UtilisateursAPI.entities.Vacataire;

public interface VacataireRepository extends JpaRepository<Vacataire, Long> {
    Vacataire findByEmail(String email);
    Vacataire findByIdAndActif(Long id, boolean actif);
}
