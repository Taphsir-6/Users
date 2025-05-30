package sn.uasz.utilisateursapi.exceptions;

/**
 * Exception levée lorsqu'un enseignant est introuvable dans le système.
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Crée une exception avec un message d'erreur.
     * @param message Message décrivant l'erreur.
     */
    public RoleNotFoundException(String message) {
        super(message);
    }

    /**
     * Crée une exception avec un message et une cause sous-jacente.
     * @param message Message décrivant l'erreur.
     * @param cause Exception d'origine.
     */
    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée une exception uniquement avec une cause sous-jacente.
     * @param cause Exception d'origine.
     */
    public RoleNotFoundException(Throwable cause) {
        super(cause);
    }
}
