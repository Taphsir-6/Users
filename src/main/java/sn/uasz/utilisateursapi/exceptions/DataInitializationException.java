package sn.uasz.utilisateursapi.exceptions;

/**
 * Exception personnalisée pour les erreurs d'initialisation des données.
 */
public class DataInitializationException extends RuntimeException {
    /**
     * Constructeur avec un message d'erreur.
     * 
     * @param message Le message d'erreur
     */
    public DataInitializationException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message et une cause.
     * 
     * @param message Le message d'erreur
     * @param cause La cause de l'erreur
     */
    public DataInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
