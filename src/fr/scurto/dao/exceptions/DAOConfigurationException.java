package fr.scurto.dao.exceptions;

/**
 * Classe g�rant les exceptions li�es � la configuration SGBD
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
