package fr.scurto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.scurto.dao.exceptions.DAOConfigurationException;

public abstract class DAO<T> {

    protected DAOFactory daoFactory = null;

    // CONSTRUCTEUR ET RECUPERATION DE LOBJET CONNEXION
    public DAO( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    // METHODE DE CREATION D'OBJET
    public abstract T map( ResultSet result ) throws SQLException;

    /*
     * Initialise la requête préparée basée sur la connexion passée en argument,
     * avec la requête SQL et les objets donnés.
     */
    public PreparedStatement initialisationRequetePreparee( Connection connexion, String sql,
            boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }

    /* Fermeture silencieuse du resultset */
    public static void fermetureSilencieuse( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                throw new DAOConfigurationException( "Échec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse du statement */
    public static void fermetureSilencieuse( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                throw new DAOConfigurationException( "Échec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse de la connexion */
    public static void fermetureSilencieuse( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
                // LOGGER.info( "Connexion rendu au pool de connexion" );
            } catch ( SQLException e ) {
                throw new DAOConfigurationException( "Échec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    /* Fermetures silencieuses du statement et de la connexion */
    public static void fermeturesSilencieuses( Statement state, Connection connexion ) {
        fermetureSilencieuse( state );
        fermetureSilencieuse( connexion );
    }

    /* Fermetures silencieuses du resultset, du statement et de la connexion */
    public static void fermeturesSilencieuses( ResultSet resultSet, Statement statement, Connection connexion ) {
        fermetureSilencieuse( resultSet );
        fermetureSilencieuse( statement );
        fermetureSilencieuse( connexion );
    }

    /* Fermetures silencieuses du statement et de la connexion */
    public static void fermeturesSilencieuses( ResultSet result, Statement state ) {
        fermetureSilencieuse( result );
        fermetureSilencieuse( state );
    }

}
