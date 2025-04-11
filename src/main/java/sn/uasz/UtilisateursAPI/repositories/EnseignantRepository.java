package sn.uasz.UtilisateursAPI.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sn.uasz.UtilisateursAPI.entities.Enseignant;

@RepositoryRestResource
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}
