package sn.uasz.utilisateursapi.exceptions;

/**
 * Exception personnalisée pour les cas où un vacataire n'est pas trouvé.
 */
public class VacataireNotFoundException extends RuntimeException {
    /**
     * Message par défaut quand un vacataire n'est pas trouvé.
     */
    public static final String MESSAGE = "Vacataire non trouvé";

    /**
     * Constructeur par défaut avec le message par défaut.
     */
    public VacataireNotFoundException() {
        super(MESSAGE);
    }

    /**
     * Constructeur avec un message personnalisé.
     *
     * @param message Le message d'erreur personnalisé
     */
    public VacataireNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message et une cause.
     *
     * @param message Le message d'erreur
     * @param cause La cause de l'erreur
     */
    public VacataireNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur avec une cause.
     * Utilise le message par défaut et la cause fournie.
     *
     * @param cause La cause de l'erreur
     */
    public VacataireNotFoundException(Throwable cause) {
        super(MESSAGE, cause); // Ce constructeur nécessitait un test
    }
}