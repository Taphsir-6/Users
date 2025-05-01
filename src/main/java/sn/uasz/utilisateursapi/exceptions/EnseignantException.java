package sn.uasz.utilisateursapi.exceptions;

/**
 * Exception personnalisée pour les erreurs liées à la gestion des enseignants.
 */
public class EnseignantException extends RuntimeException {
    public EnseignantException(String message) {
        super(message);
    }
}
