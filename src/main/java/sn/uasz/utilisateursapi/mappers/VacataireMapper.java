/*
 * Auteur : Omar DIOP
 * Date de création : 2025
 * Description :
 *  Classe utilitaire pour la conversion entre l'entité Vacataire et le DTO VacataireDTO.
 *  Permet de centraliser la logique de mapping pour garantir la cohérence et la maintenabilité du code.
 */
package sn.uasz.utilisateursapi.mappers;

import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.entities.Vacataire;

/**
 * Mapper pour la conversion entre Vacataire et VacataireDTO.
 *
 * @author Omar DIOP
 */
public class VacataireMapper {
    /**
     * Convertit une entité Vacataire en DTO VacataireDTO.
     *
     * @param vacataire l'entité Vacataire à convertir
     * @return le DTO VacataireDTO correspondant
     */
    public static VacataireDTO toDTO(Vacataire vacataire) {
        if (vacataire == null) return null;
        VacataireDTO dto = new VacataireDTO();
        dto.setId(vacataire.getId());
        dto.setNom(vacataire.getNom());
        dto.setPrenom(vacataire.getPrenom());
        dto.setEmail(vacataire.getEmail());
        dto.setTelephone(vacataire.getTelephone());
        dto.setSpecialite(vacataire.getSpecialite());
        dto.setActif(vacataire.isActif());
        return dto;
    }

    /**
     * Convertit un DTO VacataireDTO en entité Vacataire.
     *
     * @param dto le DTO VacataireDTO à convertir
     * @return l'entité Vacataire correspondante
     */
    public static Vacataire toEntity(VacataireDTO dto) {
        if (dto == null) return null;
        Vacataire vacataire = new Vacataire();
        vacataire.setId(dto.getId());
        vacataire.setNom(dto.getNom());
        vacataire.setPrenom(dto.getPrenom());
        vacataire.setEmail(dto.getEmail());
        vacataire.setTelephone(dto.getTelephone());
        vacataire.setSpecialite(dto.getSpecialite());
        vacataire.setActif(dto.isActif());
        return vacataire;
    }
}
