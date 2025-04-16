package sn.uasz.utilisateursapi.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record EnseignantDTO(
        Integer id,

        @NotBlank @Size(min = 2, max = 50)
        String nom,

        @NotBlank @Size(min = 2, max = 50)
        String prenom,

        @NotBlank @Email
        String email,

        @Pattern(regexp = "^\\+?[0-9]{9,15}$")
        String telephone,

        @NotBlank
        String matricule,

        @NotBlank
        String grade,

        String createBy,
        LocalDateTime createAt
) {
    public String nomComplet() {
        return prenom + " " + nom;
    }
}