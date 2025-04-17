package sn.uasz.UtilisateursAPI.exceptions;



public class EtudiantException extends RuntimeException {
    public EtudiantException(String message) {
        super(message);
    }

    public EtudiantException(String message, Throwable cause) {
        super(message, cause);
    }
}

