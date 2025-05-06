package sn.uasz.utilisateursapi.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.uasz.utilisateursapi.dtos.EnseignantDTO;
import sn.uasz.utilisateursapi.services.EnseignantService;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des enseignants.
 * Expose des endpoints permettant d'ajouter, modifier, supprimer, rechercher,
 * activer/désactiver des enseignants.
 */
@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
@Tag(name = "Enseignants", description = "Gestion des enseignants de l'université")
public class EnseignantController {

    private final EnseignantService enseignantService;

    /**
     * Ajouter un nouvel enseignant.
     * @param enseignantDTO données de l'enseignant à ajouter.
     * @return l'enseignant ajouté avec son ID.
     */
    @Operation(summary = "Ajouter un nouvel enseignant")
    @PostMapping
    public ResponseEntity<EnseignantDTO> ajouterEnseignant(@RequestBody EnseignantDTO enseignantDTO) {
        EnseignantDTO nouveauEnseignant = enseignantService.ajouterEnseignant(enseignantDTO);
        return new ResponseEntity<>(nouveauEnseignant, HttpStatus.CREATED);
    }

    /**
     * Récupérer la liste de tous les enseignants.
     * @return liste des enseignants.
     */
    @Operation(summary = "Lister tous les enseignants")
    @GetMapping
    public ResponseEntity<List<EnseignantDTO>> listerEnseignants() {
        List<EnseignantDTO> enseignants = enseignantService.listerTousEnseignants();
        return ResponseEntity.ok(enseignants);
    }

    /**
     * Récupérer un enseignant par son identifiant.
     * @param id identifiant de l'enseignant.
     * @return l'enseignant correspondant.
     */
    @Operation(summary = "Récupérer un enseignant par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> obtenirEnseignant(@PathVariable Long id) {
        EnseignantDTO enseignant = enseignantService.obtenirEnseignantParId(id);
        return ResponseEntity.ok(enseignant);
    }

    /**
     * Modifier les données d'un enseignant.
     * @param id identifiant de l'enseignant à modifier.
     * @param enseignantDTO nouvelles données.
     * @return enseignant modifié.
     */
    @Operation(summary = "Modifier un enseignant existant")
    @PutMapping("/{id}")
    public ResponseEntity<EnseignantDTO> modifierEnseignant(
            @PathVariable Long id,
            @RequestBody EnseignantDTO enseignantDTO) {
        EnseignantDTO enseignantModifie = enseignantService.modifierEnseignant(id, enseignantDTO);
        return ResponseEntity.ok(enseignantModifie);
    }

    /**
     * Supprimer un enseignant par son ID.
     * @param id identifiant de l'enseignant.
     * @return réponse HTTP sans contenu.
     */
    @Operation(summary = "Supprimer un enseignant")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long id) {
        enseignantService.supprimerEnseignant(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Rechercher des enseignants dont le nom contient une chaîne donnée.
     * @param nom chaîne de caractères à rechercher.
     * @return liste filtrée d'enseignants.
     */
    @Operation(summary = "Rechercher des enseignants par nom")
    @GetMapping("/recherche")
    public ResponseEntity<List<EnseignantDTO>> rechercherParNom(@RequestParam String nom) {
        List<EnseignantDTO> resultats = enseignantService.rechercherEnseignantsParNom(nom);
        return ResponseEntity.ok(resultats);
    }

    /**
     * Activer un enseignant (changement d'état actif=true).
     * @param id identifiant de l'enseignant.
     * @return enseignant activé.
     */
    @Operation(summary = "Activer un enseignant")
    @PutMapping("/{id}/activer")
    public ResponseEntity<EnseignantDTO> activerEnseignant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.activerEnseignant(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Désactiver un enseignant (changement d'état actif=false).
     * @param id identifiant de l'enseignant.
     * @return enseignant désactivé.
     */
    @Operation(summary = "Désactiver un enseignant")
    @PutMapping("/{id}/desactiver")
    public ResponseEntity<EnseignantDTO> desactiverEnseignant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.desactiverEnseignant(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
