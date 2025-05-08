package sn.uasz.utilisateursapi.exceptions;


public class EtudiantException extends RuntimeException {

    /**
     * Crée une exception avec un message d'erreur.
     * @param message Message décrivant l'erreur.
     */
    public EtudiantException(String message) {
        super(message);
    }

    /**
     * Crée une exception avec un message et une cause sous-jacente.
     * @param message Message décrivant l'erreur.
     * @param cause Exception d'origine.
     */
    public EtudiantException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée une exception uniquement avec une cause sous-jacente.
     * @param cause Exception d'origine.
     */
    public EtudiantException(Throwable cause) {
        super(cause);
    }
}
