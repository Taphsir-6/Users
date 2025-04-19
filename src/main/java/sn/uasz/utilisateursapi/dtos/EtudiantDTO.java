package sn.uasz.utilisateursapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EtudiantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private String email;
    private String photo;
}