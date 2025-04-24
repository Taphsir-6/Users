package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour {@link GlobalExceptionHandler}.
 * <p>
 * Vérifie que le gestionnaire global des exceptions retourne les bonnes réponses HTTP
 * pour chaque type d'exception personnalisée ou générique.
 * </p>
 * <p>
 * Auteur : Omar DIOP
 * Date de dernière modification : 24 avril 2025
 * </p>
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    /**
     * Teste la gestion de l'exception {@link VacataireNotFoundException}.
     * Vérifie que le statut HTTP et le message sont corrects.
     */
    @Test
    void testHandleVacataireNotFoundException() {
        // Given
        String errorMessage = "Vacataire non trouvé";
        VacataireNotFoundException exception = new VacataireNotFoundException(errorMessage);

        // When
        ResponseEntity<String> response = handler.handleVacataireNotFoundException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    /**
     * Teste la gestion de l'exception {@link DataInitializationException}.
     * Vérifie que le statut HTTP et le message sont corrects.
     */
    @Test
    void testHandleDataInitializationException() {
        // Given
        String errorMessage = "Erreur d'initialisation";
        DataInitializationException exception = new DataInitializationException(errorMessage);

        // When
        ResponseEntity<String> response = handler.handleDataInitializationException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    /**
     * Teste la gestion d'une exception générique.
     * Vérifie que le statut HTTP est correct et que le message contient l'erreur originale.
     */
    @Test
    void testHandleException() {
        // Given
        String errorMessage = "Erreur inattendue";
        Exception exception = new Exception(errorMessage);

        // When
        ResponseEntity<String> response = handler.handleException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains(errorMessage));
        assertTrue(response.getBody().startsWith("Une erreur inattendue est survenue : "));
    }
}
