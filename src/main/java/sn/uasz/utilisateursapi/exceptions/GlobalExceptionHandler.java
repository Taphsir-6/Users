package sn.uasz.utilisateursapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestionnaire global des exceptions pour l'API Utilisateurs.
 * <p>
 * Cette classe intercepte et gère toutes les exceptions levées par les contrôleurs REST,
 * afin de fournir des réponses HTTP cohérentes et informatives aux clients de l'API.
 * </p>
 * <ul>
 *   <li>Gère les exceptions métiers spécifiques (ex : {@link VacataireNotFoundException}, {@link DataInitializationException}).</li>
 *   <li>Gère les exceptions génériques pour éviter de divulguer des détails sensibles.</li>
 *   <li>Retourne des statuts HTTP appropriés et des messages clairs.</li>
 * </ul>
 * <p>
 * Auteur : Omar DIOP
 * Date de dernière modification : 24 avril 2025
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère l'exception {@link VacataireNotFoundException} lorsqu'un vacataire n'est pas trouvé.
     * Retourne une réponse HTTP 404 (Not Found) avec le message d'erreur.
     *
     * @param ex L'exception levée
     * @return Réponse HTTP 404 avec le message d'erreur
     */
    @ExceptionHandler(VacataireNotFoundException.class)
    public ResponseEntity<String> handleVacataireNotFoundException(VacataireNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Gère l'exception {@link DataInitializationException} lors d'une erreur d'initialisation des données.
     * Retourne une réponse HTTP 500 (Internal Server Error) avec le message d'erreur.
     *
     * @param ex L'exception levée
     * @return Réponse HTTP 500 avec le message d'erreur
     */
    @ExceptionHandler(DataInitializationException.class)
    public ResponseEntity<String> handleDataInitializationException(DataInitializationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gère toutes les autres exceptions non spécifiquement traitées.
     * Retourne une réponse HTTP 500 (Internal Server Error) avec un message générique.
     *
     * @param ex L'exception levée
     * @return Réponse HTTP 500 avec un message d'erreur générique
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("Une erreur inattendue est survenue : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
