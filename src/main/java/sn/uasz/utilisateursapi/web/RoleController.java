package sn.uasz.utilisateursapi.web;

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
import sn.uasz.utilisateursapi.dtos.RoleDTO;
import sn.uasz.utilisateursapi.services.RoleService;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des rôles des utilisateurs.
 * Fournit des endpoints pour créer, modifier, supprimer et consulter des rôles.
 *
 * Auteur : KOUMBA THIONGANE
 * Date de dernière modification : 07 MAI 2025
 */
@Tag(name = "Roles", description = "API pour la gestion des rôles des utilisateurs")
@RestController
@RequestMapping("/api/roles")
@Validated
public class RoleController {

    private final RoleService roleService;

    /**
     * Constructeur avec injection du service de rôles.
     *
     * @param roleService Service de gestion des rôles
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Récupère les détails d’un rôle par son ID.
     *
     * @param id ID du rôle à récupérer
     * @return Détails du rôle trouvé
     */
    @Operation(
            summary = "Obtenir un rôle par ID",
            description = "Récupère les détails d’un rôle spécifique à partir de son identifiant",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle trouvé",
                            content = @Content(schema = @Schema(implementation = RoleDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rôle non trouvé"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(
            @Parameter(description = "ID du rôle") @PathVariable long id) {
        return ResponseEntity.ok(roleService.obtenirRole((int) id));
    }

    /**
     * Récupère la liste complète des rôles.
     *
     * @return Liste des rôles
     */
    @Operation(
            summary = "Lister tous les rôles",
            description = "Récupère l’ensemble des rôles disponibles dans le système",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des rôles",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRole() {
        return ResponseEntity.ok(roleService.findAll());
    }

    /**
     * Crée un nouveau rôle dans le système.
     *
     * @param roleDTO Informations du rôle à créer
     * @return Détails du rôle créé
     */
    @Operation(
            summary = "Créer un nouveau rôle",
            description = "Ajoute un nouveau rôle avec les informations fournies",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle créé avec succès",
                            content = @Content(schema = @Schema(implementation = RoleDTO.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(
            @Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.ajouterRole(roleDTO));
    }

    /**
     * Met à jour les informations d’un rôle existant.
     *
     * @param id ID du rôle à modifier
     * @param roleDTO Nouvelles informations du rôle
     * @return Détails du rôle mis à jour
     */
    @Operation(
            summary = "Mettre à jour un rôle",
            description = "Met à jour les informations d’un rôle identifié par son ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle mis à jour avec succès",
                            content = @Content(schema = @Schema(implementation = RoleDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rôle non trouvé"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(
            @Parameter(description = "ID du rôle") @PathVariable long id,
            @Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.modifierRole((int) id, roleDTO));
    }

    /**
     * Supprime un rôle du système.
     *
     * @param id ID du rôle à supprimer
     * @return Réponse vide si la suppression est réussie
     */
    @Operation(
            summary = "Supprimer un rôle",
            description = "Supprime un rôle du système à partir de son identifiant",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle supprimé avec succès"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rôle non trouvé"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "ID du rôle") @PathVariable long id) {
        roleService.supprimerRole((int) id);
        return ResponseEntity.ok().build();
    }

    /**
     * Gère les exceptions de type RuntimeException en retournant un message d’erreur personnalisé.
     *
     * @param ex L’exception levée
     * @return Une réponse contenant le message de l’exception
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}
