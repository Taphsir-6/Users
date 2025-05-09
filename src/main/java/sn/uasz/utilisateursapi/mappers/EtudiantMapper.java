package sn.uasz.utilisateursapi.mappers;

import org.springframework.stereotype.Component;
import sn.uasz.utilisateursapi.dtos.EtudiantDTO;
import sn.uasz.utilisateursapi.entities.Etudiant;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper permettant la conversion entre l'entité {@link Etudiant} et le DTO {@link EtudiantDTO}.
 *
 * Auteur : Koumba Thiongane
 * Date de dernière modification : 07 mai 2025
 */
@Component
public class EtudiantMapper {

    /**
     * Convertit une entité {@link Etudiant} en un DTO {@link EtudiantDTO}.
     *
     * @param etudiant l'entité à convertir
     * @return l'objet DTO correspondant
     */
    public EtudiantDTO toDTO(Etudiant etudiant) {
        if (etudiant == null) {
            return null;
        }

        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(etudiant.getId());
        dto.setNom(etudiant.getNom());
        dto.setPrenom(etudiant.getPrenom());
        dto.setMatricule(etudiant.getMatricule());
        dto.setEmail(etudiant.getEmail());
        dto.setDateNaissance(etudiant.getDateNaissance());
        dto.setLieuNaissance(etudiant.getLieuNaissance());

        // Copie des rôles s'ils existent
        if (etudiant.getRoles() != null) {
            dto.setRoles(new ArrayList<>(etudiant.getRoles()));
        }

        return dto;
    }

    /**
     * Convertit un DTO {@link EtudiantDTO} en une entité {@link Etudiant}.
     *
     * @param dto le DTO à convertir
     * @return l'entité {@link Etudiant} correspondante
     */
    public Etudiant toEntity(EtudiantDTO dto) {
        if (dto == null) {
            return null;
        }

        Etudiant etudiant = new Etudiant();
        etudiant.setId(dto.getId());
        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setMatricule(dto.getMatricule());
        etudiant.setEmail(dto.getEmail());
        etudiant.setDateNaissance(dto.getDateNaissance());
        etudiant.setLieuNaissance(dto.getLieuNaissance());

        // Copie des rôles s'ils existent
        if (dto.getRoles() != null) {
            etudiant.setRoles(new ArrayList<>(dto.getRoles()));
        }

        return etudiant;
    }
}
