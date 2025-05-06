package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnseignantExceptionTest {

    @Test
    void testMessageConstructor() {
        String message = "Erreur générique liée à l'enseignant";
        EnseignantException exception = new EnseignantException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        String message = "Erreur avec cause spécifique";
        Throwable cause = new IllegalArgumentException("Cause d'origine");
        EnseignantException exception = new EnseignantException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new NullPointerException("Null value encountered");
        EnseignantException exception = new EnseignantException(cause);

        assertEquals(cause, exception.getCause());
        assertEquals("java.lang.NullPointerException: Null value encountered", exception.getMessage());
    }
}
