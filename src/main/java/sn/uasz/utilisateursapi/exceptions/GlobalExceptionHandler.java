package sn.uasz.utilisateursapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions pour l'API Utilisateurs.
 * <p>Fournit des réponses HTTP structurées pour les différentes exceptions levées.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestion de l'exception lorsqu'un enseignant n'est pas trouvé.
     */
    @ExceptionHandler(EnseignantNotFoundException.class)
    public ResponseEntity<Object> handleEnseignantNotFound(EnseignantNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Enseignant non trouvé", ex.getMessage());
    }

    /**
     * Gestion de l'exception lorsqu'un vacataire n'est pas trouvé.
     */
    @ExceptionHandler(VacataireNotFoundException.class)
    public ResponseEntity<Object> handleVacataireNotFoundException(VacataireNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Vacataire non trouvé", ex.getMessage());
    }

    /**
     * Gestion des erreurs d'initialisation de données.
     */
    @ExceptionHandler(DataInitializationException.class)
    public ResponseEntity<Object> handleDataInitializationException(DataInitializationException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur d'initialisation des données", ex.getMessage());
    }

    /**
     * Gestion des erreurs personnalisées Enseignant.
     */
    @ExceptionHandler(EnseignantException.class)
    public ResponseEntity<Object> handleEnseignantException(EnseignantException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Erreur liée à un enseignant", ex.getMessage());
    }

    /**
     * Gestion générique des exceptions inattendues.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne", "Une erreur inattendue est survenue : " + ex.getMessage());
    }

    /**
     * Méthode utilitaire pour construire les réponses JSON uniformisées.
     */
    private ResponseEntity<Object> buildResponse(HttpStatus status, String errorTitle, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", errorTitle);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
