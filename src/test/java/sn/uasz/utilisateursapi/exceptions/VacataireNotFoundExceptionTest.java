package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Importe toutes les assertions statiques

/**
 * Classe de test unitaire pour {@link VacataireNotFoundException}.
 * <p>
 * Vérifie le bon fonctionnement des constructeurs de l'exception personnalisée utilisée pour signaler l'absence d'un vacataire.
 * </p>
 * <p>
 * Auteur : Omar DIOP
 * Date de dernière modification : 24 avril 2025
 * </p>
 */
class VacataireNotFoundExceptionTest {

    private static final String TEST_MESSAGE = "Test message personnalisé";
    private static final String TEST_CAUSE_MESSAGE = "Cause racine de l'erreur";

    /**
     * Teste le constructeur par défaut.
     * Vérifie que le message par défaut est utilisé et que la cause est nulle.
     */
    @Test
    void testDefaultConstructor() {
        // Act
        VacataireNotFoundException exception = new VacataireNotFoundException();

        // Assert
        assertEquals(VacataireNotFoundException.MESSAGE, exception.getMessage(), "Le message par défaut devrait être utilisé.");
        assertNull(exception.getCause(), "La cause devrait être nulle pour le constructeur par défaut.");
    }

    /**
     * Teste le constructeur avec message seul.
     * Vérifie que le message personnalisé est bien transmis et que la cause est nulle.
     */
    @Test
    void testMessageConstructor() {
        // Act
        VacataireNotFoundException exception = new VacataireNotFoundException(TEST_MESSAGE);

        // Assert
        assertEquals(TEST_MESSAGE, exception.getMessage(), "Le message personnalisé devrait être utilisé.");
        assertNull(exception.getCause(), "La cause devrait être nulle pour le constructeur avec message seul.");
    }

    /**
     * Teste le constructeur avec message et cause.
     * Vérifie que le message personnalisé et la cause sont bien transmis.
     */
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

    /**
     * Teste le constructeur avec cause seule.
     * Vérifie que le message par défaut est utilisé et que la cause est bien transmise.
     */
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