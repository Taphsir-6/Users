package sn.uasz.UtilisateursAPI.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantDTO {
    private Long id;
    private String email;
    private String prenom;
    private String nom;
    private String matricule;
    private String grade;
    private boolean actif;
}

