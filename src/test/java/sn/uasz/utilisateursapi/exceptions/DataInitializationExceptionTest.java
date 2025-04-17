package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Importations statiques pour les assertions

class DataInitializationExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Arrange: Préparer les données du test
        String expectedMessage = "Erreur lors de l'initialisation";

        // Act: Exécuter le code à tester
        DataInitializationException exception = new DataInitializationException(expectedMessage);

        // Assert: Vérifier les résultats
        assertNotNull(exception, "L'exception ne devrait pas être nulle.");
        assertEquals(expectedMessage, exception.getMessage(), "Le message de l'exception ne correspond pas.");
        assertNull(exception.getCause(), "La cause devrait être nulle pour ce constructeur.");
    }

    @Test
    void testConstructorWithMessageAndCause() {
        // Arrange
        String expectedMessage = "Erreur d'initialisation avec cause";
        Throwable expectedCause = new RuntimeException("Cause racine du problème"); // Une cause exemple

        // Act
        DataInitializationException exception = new DataInitializationException(expectedMessage, expectedCause);

        // Assert
        assertNotNull(exception, "L'exception ne devrait pas être nulle.");
        assertEquals(expectedMessage, exception.getMessage(), "Le message de l'exception ne correspond pas.");
        assertNotNull(exception.getCause(), "La cause ne devrait pas être nulle.");
        assertSame(expectedCause, exception.getCause(), "La cause de l'exception ne correspond pas à celle fournie."); // Vérifie que c'est le même objet
    }
}