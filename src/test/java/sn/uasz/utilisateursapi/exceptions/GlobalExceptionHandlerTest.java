package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

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
