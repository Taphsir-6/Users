package sn.uasz.utilisateursapi.exceptions;

/**
 * Exception personnalisée pour les erreurs liées à la gestion des enseignants.
 * Permet de capturer des messages explicites ainsi que les causes sous-jacentes.
 */
public class EnseignantException extends RuntimeException {

    /**
     * Crée une exception avec un message explicite.
     * @param message Message décrivant l'erreur rencontrée.
     */
    public EnseignantException(String message) {
        super(message);
    }

    /**
     * Crée une exception avec un message et la cause originale.
     * Utile pour le chaînage d'exceptions.
     * @param message Message décrivant l'erreur rencontrée.
     * @param cause Exception d'origine (ex: SQLException, NullPointerException, etc.)
     */
    public EnseignantException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée une exception uniquement avec une cause sous-jacente.
     * @param cause Exception d'origine.
     */
    public EnseignantException(Throwable cause) {
        super(cause);
    }
}
