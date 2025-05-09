package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la classe EtudiantException.
 * Elle vérifie que les constructeurs de l'exception fonctionnent correctement
 * en termes de message et de cause.
 */
class EtudiantExceptionTest {

    /**
     * Teste le constructeur de EtudiantException avec uniquement un message.
     * Vérifie que le message est bien stocké et que la cause est nulle.
     */
    @Test
    void testMessageConstructor() {
        String message = "Erreur générique liée à l'etudiant";
        EtudiantException exception = new EtudiantException(message);

        // Vérifie que le message correspond à celui fourni
        assertEquals(message, exception.getMessage());

        // Vérifie que la cause est nulle car non fournie
        assertNull(exception.getCause());
    }

    /**
     * Teste le constructeur de EtudiantException avec un message et une cause.
     * Vérifie que les deux sont correctement assignés.
     */
    @Test
    void testMessageAndCauseConstructor() {
        String message = "Erreur avec cause spécifique";
        Throwable cause = new IllegalArgumentException("Cause d'origine");
        EtudiantException exception = new EtudiantException(message, cause);

        // Vérifie que le message est bien celui passé
        assertEquals(message, exception.getMessage());

        // Vérifie que la cause est correctement enregistrée
        assertEquals(cause, exception.getCause());
    }

    /**
     * Teste le constructeur de EtudiantException avec uniquement une cause.
     * Vérifie que la cause est assignée et que le message dérivé de la cause est bien utilisé.
     */
    @Test
    void testCauseConstructor() {
        Throwable cause = new NullPointerException("Null value encountered");
        EtudiantException exception = new EtudiantException(cause);

        // Vérifie que la cause correspond bien à celle fournie
        assertEquals(cause, exception.getCause());

        // Vérifie que le message est dérivé de la cause (comportement de Throwable)
        assertEquals("java.lang.NullPointerException: Null value encountered", exception.getMessage());
    }

}
