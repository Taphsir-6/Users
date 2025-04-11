package sn.uasz.UtilisateursAPI.dtos;

import lombok.Data;

@Data
public class VacataireDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;
    private boolean actif;
}
