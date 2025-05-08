package sn.uasz.utilisateursapi.exceptions;


public class RoleException extends RuntimeException {

    /**
     * Crée une exception avec un message d'erreur.
     * @param message Message décrivant l'erreur.
     */
    public RoleException(String message) {
        super(message);
    }

    /**
     * Crée une exception avec un message et une cause sous-jacente.
     * @param message Message décrivant l'erreur.
     * @param cause Exception d'origine.
     */
    public RoleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée une exception uniquement avec une cause sous-jacente.
     * @param cause Exception d'origine.
     */
    public RoleException(Throwable cause) {
        super(cause);
    }
}
