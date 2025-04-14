package sn.uasz.utilisateursapi.dtos;

import lombok.*;

/**
 * DTO (Data Transfer Object) pour l'entité Enseignant
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnseignantDTO {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private String grade;
}