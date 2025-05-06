package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link GlobalExceptionHandler}.
 * Vérifie les réponses HTTP structurées pour chaque exception personnalisée.
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleEnseignantNotFound() {
        String msg = "Aucun enseignant avec cet ID";
        EnseignantNotFoundException ex = new EnseignantNotFoundException(msg);

        ResponseEntity<Object> response = handler.handleEnseignantNotFound(ex);

        assertResponseStructure(response, HttpStatus.NOT_FOUND, "Enseignant non trouvé", msg);
    }

    @Test
    void testHandleVacataireNotFound() {
        String msg = "Vacataire ID introuvable";
        VacataireNotFoundException ex = new VacataireNotFoundException(msg);

        ResponseEntity<Object> response = handler.handleVacataireNotFoundException(ex);

        assertResponseStructure(response, HttpStatus.NOT_FOUND, "Vacataire non trouvé", msg);
    }

    @Test
    void testHandleDataInitializationException() {
        String msg = "Erreur au démarrage de la BD";
        DataInitializationException ex = new DataInitializationException(msg);

        ResponseEntity<Object> response = handler.handleDataInitializationException(ex);

        assertResponseStructure(response, HttpStatus.INTERNAL_SERVER_ERROR, "Erreur d'initialisation des données", msg);
    }

    @Test
    void testHandleEnseignantException() {
        String msg = "Erreur dans les données de l'enseignant";
        EnseignantException ex = new EnseignantException(msg);

        ResponseEntity<Object> response = handler.handleEnseignantException(ex);

        assertResponseStructure(response, HttpStatus.BAD_REQUEST, "Erreur liée à un enseignant", msg);
    }

    @Test
    void testHandleGenericException() {
        String msg = "Problème interne";
        Exception ex = new Exception(msg);

        ResponseEntity<Object> response = handler.handleException(ex);

        assertResponseStructure(response, HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne",
                "Une erreur inattendue est survenue : " + msg);
    }

    private void assertResponseStructure(ResponseEntity<Object> response, HttpStatus expectedStatus, String expectedError, String expectedMessage) {
        assertNotNull(response);
        assertEquals(expectedStatus, response.getStatusCode());

        assertInstanceOf(Map.class, response.getBody());
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertNotNull(body.get("timestamp"));
        assertEquals(expectedStatus.value(), body.get("status"));
        assertEquals(expectedError, body.get("error"));
        assertEquals(expectedMessage, body.get("message"));
    }
}
