package sn.uasz.UtilisateursAPI.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import sn.uasz.UtilisateursAPI.dtos.VacataireDTO;
import sn.uasz.UtilisateursAPI.entities.Vacataire;
import sn.uasz.UtilisateursAPI.services.VacataireService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des vacataires
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-11
 */
@Tag(name = "Vacataires", description = "API pour la gestion des vacataires")
@RestController
@RequestMapping("/api/vacataires")
@Validated
public class VacataireController {
    
    /**
     * Service pour la gestion des vacataires
     */
    private final VacataireService vacataireService;

    /**
     * Constructeur avec injection de dépendance
     * 
     * @param vacataireService le service pour la gestion des vacataires
     */
    public VacataireController(VacataireService vacataireService) {
        this.vacataireService = vacataireService;
    }

    /**
     * Récupérer les informations d'un vacataire spécifique
     * 
     * @param id l'identifiant du vacataire
     * @return les informations du vacataire
     */
    @Operation(
        summary = "Récupérer un vacataire",
        description = "Récupère les informations d'un vacataire spécifique",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vacataire trouvé",
                content = @Content(schema = @Schema(implementation = VacataireDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Vacataire non trouvé"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VacataireDTO> obtenirVacataire(@Parameter(description = "Identifiant du vacataire") @PathVariable Long id) {
        return ResponseEntity.ok(vacataireService.obtenirVacataire(id));
    }

    /**
     * Lister tous les vacataires
     * 
     * @return la liste de tous les vacataires
     */
    @Operation(
        summary = "Lister tous les vacataires",
        description = "Récupère la liste de tous les vacataires",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Liste des vacataires",
                content = @Content(schema = @Schema(implementation = List.class))
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<VacataireDTO>> listerVacataires() {
        List<Vacataire> listeVacataires = vacataireService.findAll();
        List<VacataireDTO> listeDTOs = listeVacataires.stream()
            .map(vacataire -> {
                VacataireDTO dto = new VacataireDTO();
                dto.setId(vacataire.getId());
                dto.setNom(vacataire.getNom());
                dto.setPrenom(vacataire.getPrenom());
                dto.setEmail(vacataire.getEmail());
                dto.setTelephone(vacataire.getTelephone());
                dto.setSpecialite(vacataire.getSpecialite());
                dto.setActif(vacataire.isActif());
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(listeDTOs);
    }

    /**
     * Ajouter un nouveau vacataire
     * 
     * @param vacataireDTO les informations du nouveau vacataire
     * @return le vacataire ajouté avec son ID
     */
    @Operation(
        summary = "Ajouter un nouveau vacataire",
        description = "Crée un nouveau vacataire avec les informations fournies",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vacataire ajouté avec succès",
                content = @Content(schema = @Schema(implementation = VacataireDTO.class))
            )
        }
    )
    @PostMapping
    public ResponseEntity<VacataireDTO> ajouterVacataire(@Valid @RequestBody VacataireDTO vacataireDTO) {
        return ResponseEntity.ok(vacataireService.ajouterVacataire(vacataireDTO));
    }

    /**
     * Modifier les informations d'un vacataire existant
     * 
     * @param id l'identifiant du vacataire à modifier
     * @param vacataireDTO les nouvelles informations du vacataire
     * @return le vacataire modifié
     */
    @Operation(
        summary = "Modifier un vacataire existant",
        description = "Met à jour les informations d'un vacataire existant",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vacataire modifié avec succès",
                content = @Content(schema = @Schema(implementation = VacataireDTO.class))
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VacataireDTO> modifierVacataire(
            @Parameter(description = "Identifiant du vacataire") @PathVariable Long id,
            @Valid @RequestBody VacataireDTO vacataireDTO) {
        return ResponseEntity.ok(vacataireService.modifierVacataire(id, vacataireDTO));
    }

    /**
     * Supprimer un vacataire
     * 
     * @param id l'identifiant du vacataire à supprimer
     * @return une réponse vide
     */
    @Operation(
        summary = "Supprimer un vacataire",
        description = "Supprime un vacataire existant",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vacataire supprimé avec succès"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVacataire(@Parameter(description = "Identifiant du vacataire") @PathVariable Long id) {
        vacataireService.supprimerVacataire(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Désactiver un vacataire (rendre inactif)
     * 
     * @param id l'identifiant du vacataire à désactiver
     * @return une réponse vide
     */
    @Operation(
        summary = "Désactiver un vacataire",
        description = "Rend un vacataire inactif",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vacataire désactivé avec succès"
            )
        }
    )
    @PostMapping("/{id}/desactiver")
    public ResponseEntity<Void> desactiverVacataire(@Parameter(description = "Identifiant du vacataire") @PathVariable Long id) {
        vacataireService.desactiverVacataire(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Activer un vacataire (rendre actif)
     * 
     * @param id l'identifiant du vacataire à activer
     * @return une réponse vide
     */
    @Operation(
        summary = "Activer un vacataire",
        description = "Rend un vacataire actif",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vacataire activé avec succès"
            )
        }
    )
    @PostMapping("/{id}/activer")
    public ResponseEntity<Void> activerVacataire(@Parameter(description = "Identifiant du vacataire") @PathVariable Long id) {
        vacataireService.activerVacataire(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Gérer les exceptions
     * 
     * @param ex l'exception à gérer
     * @return une réponse d'erreur avec le message
     */
    @Operation(
        summary = "Gérer les erreurs",
        description = "Gère les erreurs runtime et retourne un message d'erreur approprié"
    )
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}
