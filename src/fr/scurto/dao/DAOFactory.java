package fr.scurto.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import fr.scurto.dao.exceptions.DAOConfigurationException;
import fr.scurto.dao.imp.ActusDao;
import fr.scurto.dao.imp.UserDao;

public final class DAOFactory {

    private static final String FICHIER_PROPERTIES       = "/fr/scurto/config/scurto.properties";
    private static final String PROPERTY_URL             = "jdbc.url";
    private static final String PROPERTY_DRIVER          = "jdbc.driver.class";
    private static final String PROPERTY_NOM_UTILISATEUR = "jdbc.login";
    private static final String PROPERTY_MOT_DE_PASSE    = "jdbc.passwd";

    private static BoneCP       connectionPool           = null;

    DAOFactory( BoneCP connectionPool ) {
        DAOFactory.connectionPool = connectionPool;
    }

    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url = "";
        String driver = "";
        String nomUtilisateur = "";
        String motDePasse = "";

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties;

        fichierProperties = classLoader.getResourceAsStream( "/" +
                FICHIER_PROPERTIES );

        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Le fichier properties " +
                    FICHIER_PROPERTIES + " est introuvable." );
        } else {

            try {
                properties.load( fichierProperties );
                url = properties.getProperty( PROPERTY_URL );
                driver = properties.getProperty( PROPERTY_DRIVER );
                nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
                motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
                Class.forName( driver );

            } catch ( IOException e ) {
                throw new DAOConfigurationException(
                        "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
            } catch ( ClassNotFoundException e ) {
                throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
            }

            try {
                /*
                 * Création d'une configuration de pool de connexions via
                 * l'objet BoneCPConfig et les différents setters associés.
                 */
                BoneCPConfig config = new BoneCPConfig();
                /* Mise en place de l'URL, du nom et du mot de passe */
                config.setJdbcUrl( url );
                config.setUsername( nomUtilisateur );
                config.setPassword( motDePasse );
                /* Paramétrage de la taille du pool */
                config.setMinConnectionsPerPartition( 1 );
                config.setMaxConnectionsPerPartition( 10 );
                config.setPartitionCount( 2 );
                /*
                 * Création du pool à partir de la configuration, via l'objet
                 * BoneCP
                 */
                connectionPool = new BoneCP( config );
            } catch ( SQLException e ) {
                throw new DAOConfigurationException( "Erreur de configuration du pool de connexions.", e );
            }

            /*
             * Enregistrement du pool créé dans une variable d'instance via un
             * appel au constructeur de DAOFactory
             */
            DAOFactory instance = new DAOFactory( connectionPool );
            return instance;
        }
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    public Connection getConnection() throws SQLException {
        // LOGGER.info( "Connexion au pool de connexion demandé" );
        return connectionPool.getConnection();
    }

    public void closePool() {
        connectionPool.close();
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO
     */
    public UserDao getUserDao() {
        return new UserDao( this );
    }

    public ActusDao getActusDao() {
        return new ActusDao( this );
    }
}
