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

@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
@Tag(name = "Enseignants", description = "Gestion des enseignants de l'université")
public class EnseignantController {

    private final EnseignantService enseignantService;

    /**
     * Ajouter un nouvel enseignant
     */
    @Operation(summary = "Ajouter un nouvel enseignant")
    @PostMapping
    public ResponseEntity<EnseignantDTO> ajouterEnseignant(@RequestBody EnseignantDTO enseignantDTO) {
        EnseignantDTO nouveauEnseignant = enseignantService.ajouterEnseignant(enseignantDTO);
        return new ResponseEntity<>(nouveauEnseignant, HttpStatus.CREATED);
    }

    /**
     * Récupérer tous les enseignants
     */
    @Operation(summary = "Lister tous les enseignants")
    @GetMapping
    public ResponseEntity<List<EnseignantDTO>> listerEnseignants() {
        List<EnseignantDTO> enseignants = enseignantService.listerTousEnseignants();
        return ResponseEntity.ok(enseignants);
    }

    /**
     * Récupérer un enseignant par son ID
     */
    @Operation(summary = "Récupérer un enseignant par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> obtenirEnseignant(@PathVariable Long id) {
        EnseignantDTO enseignant = enseignantService.obtenirEnseignantParId(id);
        return ResponseEntity.ok(enseignant);
    }

    /**
     * Modifier un enseignant existant
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
     * Supprimer un enseignant
     */
    @Operation(summary = "Supprimer un enseignant")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long id) {
        enseignantService.supprimerEnseignant(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Rechercher des enseignants par nom
     */
    @Operation(summary = "Rechercher des enseignants par nom")
    @GetMapping("/recherche")
    public ResponseEntity<List<EnseignantDTO>> rechercherParNom(@RequestParam String nom) {
        List<EnseignantDTO> resultats = enseignantService.rechercherEnseignantsParNom(nom);
        return ResponseEntity.ok(resultats);
    }
    @PutMapping("/{id}/activer")
    public ResponseEntity<EnseignantDTO> activerEnseignant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.activerEnseignant(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<EnseignantDTO> desactiverEnseignant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.desactiverEnseignant(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}