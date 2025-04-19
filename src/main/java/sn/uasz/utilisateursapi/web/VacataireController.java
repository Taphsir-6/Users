package sn.uasz.utilisateursapi.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sn.uasz.utilisateursapi.dtos.VacataireDTO;
import sn.uasz.utilisateursapi.exceptions.VacataireNotFoundException;
import sn.uasz.utilisateursapi.services.VacataireService;
import java.util.List;

@Slf4j

/**
 * Contrôleur REST pour la gestion des vacataires.
 * Ce contrôleur expose les endpoints REST pour interagir avec les vacataires.
 * 
 * @author Omar Diop
 * @version 1.0
 * @since 2025-04-12
 * 
 * @see VacataireService
 */
@RestController
@RequestMapping("/api/vacataires")
@Tag(name = "Vacataires", description = "API pour la gestion des vacataires")
public class VacataireController {
    private final VacataireService vacataireService;

    public VacataireController(VacataireService vacataireService) {
        this.vacataireService = vacataireService;
    }

    @ExceptionHandler(VacataireNotFoundException.class)
    public ResponseEntity<String> handleVacataireNotFoundException(VacataireNotFoundException ex) {
        log.error("Erreur lors de la gestion du vacataire : {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Erreur inattendue : {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Une erreur inattendue est survenue", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Crée un nouveau vacataire.
     * 
     * @param vacataireDTO Les données du vacataire à créer
     * @return Le DTO du vacataire créé
     */
    @Operation(summary = "Créer un nouveau vacataire")
    @ApiResponse(responseCode = "201", description = "Vacataire créé avec succès",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VacataireDTO.class)))
    @PostMapping
    public ResponseEntity<VacataireDTO> creerVacataire(
            @Parameter(description = "Données du vacataire à créer")
            @RequestBody @Validated VacataireDTO vacataireDTO) {
        VacataireDTO createdVacataire = vacataireService.creerVacataire(vacataireDTO);
        return new ResponseEntity<>(createdVacataire, HttpStatus.CREATED);
    }

    /**
     * Récupère un vacataire par son ID.
     * 
     * @param id L'identifiant du vacataire
     * @return Le DTO du vacataire trouvé ou 404 si non trouvé
     */
    @Operation(summary = "Récupérer un vacataire par ID")
    @ApiResponse(responseCode = "200", description = "Vacataire trouvé",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VacataireDTO.class)))
    @ApiResponse(responseCode = "404", description = "Vacataire non trouvé")
    @GetMapping("/{id}")
    public ResponseEntity<VacataireDTO> getVacataire(
            @Parameter(description = "ID du vacataire à récupérer")
            @PathVariable Long id) {
        try {
            VacataireDTO vacataire = vacataireService.getVacataire(id);
            if (vacataire == null) {
                log.warn("Vacataire non trouvé avec l'ID : {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("Vacataire récupéré avec succès : {}", id);
            return new ResponseEntity<>(vacataire, HttpStatus.OK);
        } catch (VacataireNotFoundException e) {
            log.error("Erreur lors de la récupération du vacataire : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Met à jour un vacataire existant.
     * 
     * @param id L'identifiant du vacataire à mettre à jour
     * @param vacataireDTO Les nouvelles données du vacataire
     * @return Le DTO du vacataire mis à jour
     */
    @Operation(summary = "Mettre à jour un vacataire")
    @ApiResponse(responseCode = "200", description = "Vacataire mis à jour avec succès",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VacataireDTO.class)))
    @ApiResponse(responseCode = "404", description = "Vacataire non trouvé")
    @PutMapping("/{id}")
    public ResponseEntity<VacataireDTO> mettreAJourVacataire(
            @Parameter(description = "ID du vacataire à mettre à jour")
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données du vacataire")
            @RequestBody @Validated VacataireDTO vacataireDTO) {
        VacataireDTO updatedVacataire = vacataireService.mettreAJourVacataire(id, vacataireDTO);
        return new ResponseEntity<>(updatedVacataire, HttpStatus.OK);
    }

    /**
     * Supprime un vacataire du système.
     * 
     * @param id L'identifiant du vacataire à supprimer
     * @return 204 si la suppression a réussi, 404 si le vacataire n'existe pas
     */
    @Operation(summary = "Supprimer un vacataire")
    @ApiResponse(responseCode = "204", description = "Vacataire supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Vacataire non trouvé")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVacataire(
            @Parameter(description = "ID du vacataire à supprimer")
            @PathVariable Long id) {
        boolean deleted = vacataireService.supprimerVacataire(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Récupère tous les vacataires actifs.
     * 
     * @return La liste des vacataires actifs
     */
    @Operation(summary = "Lister tous les vacataires actifs")
    @ApiResponse(responseCode = "200", description = "Liste des vacataires actifs",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VacataireDTO.class)))
    @GetMapping
    public ResponseEntity<List<VacataireDTO>> getAllVacatairesActifs() {
        List<VacataireDTO> vacataires = vacataireService.getAllVacatairesActifs();
        return new ResponseEntity<>(vacataires, HttpStatus.OK);
    }

    /**
     * Désactive un vacataire.
     * 
     * @param id L'identifiant du vacataire à désactiver
     * @return Le DTO du vacataire désactivé
     */
    @Operation(summary = "Désactiver un vacataire")
    @ApiResponse(responseCode = "200", description = "Vacataire désactivé avec succès",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VacataireDTO.class)))
    @ApiResponse(responseCode = "404", description = "Vacataire non trouvé")
    @PostMapping("/{id}/desactiver")
    public ResponseEntity<VacataireDTO> desactiverVacataire(
            @Parameter(description = "ID du vacataire à désactiver")
            @PathVariable Long id) {
        VacataireDTO vacataire = vacataireService.desactiverVacataire(id);
        return new ResponseEntity<>(vacataire, HttpStatus.OK);
    }

    /**
     * Réactive un vacataire.
     * 
     * @param id L'identifiant du vacataire à réactiver
     * @return Le DTO du vacataire réactivé
     */
    @Operation(summary = "Réactiver un vacataire")
    @ApiResponse(responseCode = "200", description = "Vacataire réactivé avec succès",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VacataireDTO.class)))
    @ApiResponse(responseCode = "404", description = "Vacataire non trouvé")
    @PostMapping("/{id}/reactiver")
    public ResponseEntity<VacataireDTO> reactivierVacataire(
            @Parameter(description = "ID du vacataire à réactiver")
            @PathVariable Long id) {
        VacataireDTO vacataire = vacataireService.reactivierVacataire(id);
        return new ResponseEntity<>(vacataire, HttpStatus.OK);
    }
}
