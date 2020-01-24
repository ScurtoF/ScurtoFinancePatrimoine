package fr.scurto.dao.exceptions;

/**
 * Classe gérant les exceptions liées à la configuration SGBD
 * 
 * @author SCURTO FLORIAN
 *
 */
public class DAOConfigurationException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Constructeurs */
    public DAOConfigurationException( String message ) {
        super( message );
    }

    public DAOConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOConfigurationException( Throwable cause ) {
        super( cause );
    }

}
