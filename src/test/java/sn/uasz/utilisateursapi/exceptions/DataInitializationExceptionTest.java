package sn.uasz.utilisateursapi.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Importations statiques pour les assertions

/**
 * Classe de test unitaire pour {@link DataInitializationException}.
 * <p>
 * Vérifie le bon fonctionnement des constructeurs de l'exception personnalisée utilisée lors de l'initialisation des données.
 * </p>
 * <p>
 * Auteur : Omar DIOP
 * Date de dernière modification : 24 avril 2025
 * </p>
 */
class DataInitializationExceptionTest {

    /**
     * Teste le constructeur avec message seul.
     * Vérifie que le message est bien transmis et que la cause est nulle.
     */
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

    /**
     * Teste le constructeur avec message et cause.
     * Vérifie que le message et la cause sont bien transmis.
     */
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