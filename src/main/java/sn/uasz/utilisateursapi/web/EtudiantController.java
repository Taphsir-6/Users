package sn.uasz.UtilisateursAPI.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sn.uasz.UtilisateursAPI.dtos.EtudiantDTO;
import sn.uasz.UtilisateursAPI.services.EtudiantService;

import java.util.List;
import java.util.Map;

@Tag(name = "Étudiants", description = "API pour la gestion des étudiants")
@RestController
@RequestMapping("/api/etudiants")
@Validated
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @Operation(
            summary = "Obtenir un étudiant par ID",
            description = "Récupère les détails d'un étudiant spécifique",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Étudiant trouvé",
                            content = @Content(schema = @Schema(implementation = EtudiantDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Étudiant non trouvé"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> getEtudiantById(
            @Parameter(description = "ID de l'étudiant") @PathVariable long id) {
        return ResponseEntity.ok(etudiantService.obtenirEtudiant((int) id));
    }

    @Operation(
            summary = "Lister tous les étudiants",
            description = "Récupère la liste complète des étudiants",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des étudiants",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<EtudiantDTO>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.findAll());
    }

    @Operation(
            summary = "Créer un nouvel étudiant",
            description = "Ajoute un nouvel étudiant dans le système",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Étudiant créé",
                            content = @Content(schema = @Schema(implementation = EtudiantDTO.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<EtudiantDTO> createEtudiant(
            @Valid @RequestBody EtudiantDTO etudiantDTO) {
        return ResponseEntity.ok(etudiantService.ajouterEtudiant(etudiantDTO));
    }

    @Operation(
            summary = "Mettre à jour un étudiant",
            description = "Modifie les informations d'un étudiant existant",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Étudiant mis à jour",
                            content = @Content(schema = @Schema(implementation = EtudiantDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Étudiant non trouvé"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EtudiantDTO> updateEtudiant(
            @Parameter(description = "ID de l'étudiant") @PathVariable long id,
            @Valid @RequestBody EtudiantDTO etudiantDTO) {
        return ResponseEntity.ok(etudiantService.modifierEtudiant((int) id, etudiantDTO));
    }

    @Operation(
            summary = "Supprimer un étudiant",
            description = "Supprime un étudiant du système",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Étudiant supprimé"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Étudiant non trouvé"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(
            @Parameter(description = "ID de l'étudiant") @PathVariable long id) {
        etudiantService.supprimerEtudiant((int) id);
        return ResponseEntity.ok().build();
    }
//revue de code a faire
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}
