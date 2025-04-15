package sn.uasz.UtilisateursAPI.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.uasz.UtilisateursAPI.dtos.EnseignantDTO;
import sn.uasz.UtilisateursAPI.exceptions.EnseignantNotFoundException;
import sn.uasz.UtilisateursAPI.services.EnseignantService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/enseignants")
@AllArgsConstructor
public class EnseignantController {

    private final EnseignantService enseignantService;

    @PutMapping("/{id}/activer")
    public ResponseEntity<EnseignantDTO> activerEnseignant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.activerEnseignant(id));
        } catch (EnseignantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<EnseignantDTO> desactiverEnseignant(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.desactiverEnseignant(id));
        } catch (EnseignantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EnseignantDTO>> getAllEnseignants() {
        return ResponseEntity.ok(enseignantService.findAllEnseignants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> getEnseignantById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(enseignantService.findEnseignantById(id));
        } catch (EnseignantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}