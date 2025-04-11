package sn.uasz.UtilisateursAPI.mappers;

import sn.uasz.UtilisateursAPI.dto.EnseignantDTO;
import sn.uasz.UtilisateursAPI.entities.Enseignant;
import org.springframework.stereotype.Component;

/**
 * Classe utilitaire pour la conversion entre l'entité Enseignant et son DTO
 */
@Component
public class EnseignantMapper {
    
    /**
     * Convertit une entité Enseignant en DTO
     * @param enseignant L'entité à convertir
     * @return Le DTO correspondant
     */
    public EnseignantDTO toDTO(Enseignant enseignant) {
        if (enseignant == null) {
            return null;
        }
        
        return EnseignantDTO.builder()
                .id(enseignant.getId())
                .nom(enseignant.getNom())
                .prenom(enseignant.getPrenom())
                .email(enseignant.getEmail())
                .telephone(enseignant.getTelephone())
                .matricule(enseignant.getMatricule())
                .grade(enseignant.getGrade())
                .build();
    }

    /**
     * Convertit un DTO en entité Enseignant
     * @param dto Le DTO à convertir
     * @return L'entité correspondante
     */
    public Enseignant toEntity(EnseignantDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Enseignant.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .matricule(dto.getMatricule())
                .grade(dto.getGrade())
                .build();
    }
}
