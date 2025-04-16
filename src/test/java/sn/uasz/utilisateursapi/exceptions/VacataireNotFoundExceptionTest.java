package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Importe toutes les assertions statiques

/**
 * Tests unitaires pour la classe VacataireNotFoundException.
 * Couvre tous les constructeurs définis.
 */
class VacataireNotFoundExceptionTest {

    private static final String TEST_MESSAGE = "Test message personnalisé";
    private static final String TEST_CAUSE_MESSAGE = "Cause racine de l'erreur";

    @Test
    void testDefaultConstructor() {
        // Act
        VacataireNotFoundException exception = new VacataireNotFoundException();

        // Assert
        assertEquals(VacataireNotFoundException.MESSAGE, exception.getMessage(), "Le message par défaut devrait être utilisé.");
        assertNull(exception.getCause(), "La cause devrait être nulle pour le constructeur par défaut.");
    }

    @Test
    void testMessageConstructor() {
        // Act
        VacataireNotFoundException exception = new VacataireNotFoundException(TEST_MESSAGE);

        // Assert
        assertEquals(TEST_MESSAGE, exception.getMessage(), "Le message personnalisé devrait être utilisé.");
        assertNull(exception.getCause(), "La cause devrait être nulle pour le constructeur avec message seul.");
    }

    @Test
    void testMessageAndCauseConstructor() {
        // Arrange
        Throwable cause = new RuntimeException(TEST_CAUSE_MESSAGE); // Utiliser Throwable ou une sous-classe

        // Act
        VacataireNotFoundException exception = new VacataireNotFoundException(TEST_MESSAGE, cause);

        // Assert
        assertEquals(TEST_MESSAGE, exception.getMessage(), "Le message personnalisé devrait être utilisé.");
        assertNotNull(exception.getCause(), "La cause ne devrait pas être nulle.");
        assertSame(cause, exception.getCause(), "La cause fournie devrait être conservée.");
    }

    // --- Test ajouté pour couvrir le dernier constructeur ---
    @Test
    void testCauseConstructor() {
        // Arrange
        Throwable cause = new IllegalArgumentException(TEST_CAUSE_MESSAGE); // Utiliser Throwable ou une sous-classe

        // Act
        VacataireNotFoundException exception = new VacataireNotFoundException(cause);

        // Assert
        assertEquals(VacataireNotFoundException.MESSAGE, exception.getMessage(), "Le message par défaut devrait être utilisé.");
        assertNotNull(exception.getCause(), "La cause ne devrait pas être nulle.");
        assertSame(cause, exception.getCause(), "La cause fournie devrait être conservée.");
    }
}