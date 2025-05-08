package sn.uasz.utilisateursapi.mappers;

import org.springframework.stereotype.Component;
import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.entities.Role;

/**
 * Mapper permettant la conversion entre l'entité {@link Role} et le DTO {@link RoleDTO}.
 * <p>
 * Ce composant facilite la transformation des objets {@code Role} dans l'API,
 * pour exposer uniquement les données utiles et isoler la couche de persistance.
 * </p>
 *
 * Auteur : KOUMBA THIONGANE
 * Date de dernière modification : 07 MAI 2025
 */
@Component
public class RoleMapper {

    /**
     * Convertit une entité {@link Role} en un DTO {@link RoleDTO}.
     * <p>
     * Cette méthode extrait les informations de l'entité {@link Role} et les copie dans le DTO pour une utilisation
     * dans les réponses API ou les couches de présentation.
     * </p>
     *
     * @param role l'entité à convertir
     * @return l'objet DTO correspondant
     */
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }

        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setLibelle(role.getLibelle());
        dto.setDescription(role.getDescription());
        return dto;
    }

    /**
     * Convertit un DTO {@link RoleDTO} en une entité {@link Role}.
     * <p>
     * Cette méthode prend un DTO reçu en entrée, extrait ses informations et les copie dans l'entité pour
     * l'enregistrer dans la base de données.
     * </p>
     *
     * @param dto le DTO à convertir
     * @return l'entité {@link Role} correspondante
     */
    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        Role role = new Role();
        role.setId(dto.getId());
        role.setLibelle(dto.getLibelle());
        role.setDescription(dto.getDescription());
        return role;
    }
}
