package fr.scurto.dao.exceptions;

/**
 * Classe gérant les exceptions SQL
 * 
 * @author Scurto Florian
 *
 */
public class DAOException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Constructeurs */

    public DAOException( String message ) {
        super( message );
    }

    public DAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOException( Throwable cause ) {
        super( cause );
    }
}
