package sn.uasz.UtilisateursAPI.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Enseignant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private String grade;

    private String createby;
    private String createat;


}
