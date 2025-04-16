package sn.uasz.UtilisateursAPI.mappers;

import org.springframework.stereotype.Component;
import sn.uasz.UtilisateursAPI.dtos.EtudiantDTO;
import sn.uasz.UtilisateursAPI.entities.Etudiant;

@Component
public class EtudiantMapper {

    public EtudiantDTO toDTO(Etudiant etudiant) {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(etudiant.getId());
        dto.setNom(etudiant.getNom());
        dto.setPrenom(etudiant.getPrenom());
        dto.setMatricule(etudiant.getMatricule());
        dto.setEmail(etudiant.getEmail());
        dto.setPhoto(etudiant.getPhoto());
        return dto;
    }

    public Etudiant toEntity(EtudiantDTO dto) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setMatricule(dto.getMatricule());
        etudiant.setEmail(dto.getEmail());
        etudiant.setPhoto(dto.getPhoto());
        return etudiant;
    }
}
