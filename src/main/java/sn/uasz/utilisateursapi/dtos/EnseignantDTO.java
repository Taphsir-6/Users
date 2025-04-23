package sn.uasz.utilisateursapi.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EnseignantDTO(
        Long id,

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
        LocalDate createAt,
         Boolean actif
) {
    public String nomComplet() {
        return prenom + " " + nom;
    }
}