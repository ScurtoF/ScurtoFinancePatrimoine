package fr.scurto.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.scurto.beans.Actus;
import fr.scurto.dao.DAO;
import fr.scurto.dao.DAOFactory;

public class ActusDao extends DAO<Actus> {

    private static final String FIND_FIRST  = "SELECT * FROM scurto_bdd.news ORDER BY date";
    private static final String UPDATE_ACTU = "UPDATE scurto_bdd.news SET date = ? , titre = ? , titre_accueil = ?, text = ?  WHERE id = ?";
    private static final String INSERT_ACTU = "INSERT INTO scurto_bdd.news ( date , titre , titre_accueil, text, id_user ) VALUES ( ? , ? , ? , ? , ?  )";
    private static final String REMOVE_ACTU = "DELETE FROM scurto_bdd.news WHERE id=?";

    public ActusDao( DAOFactory daoFactory ) {
        super( daoFactory );
    }

    public List<Actus> getActusAccueil() {
        List<Actus> list = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement state = null;
        Connection con = null;
        try {
            con = daoFactory.getConnection();
            state = initialisationRequetePreparee( con, FIND_FIRST, false );
            result = state.executeQuery();
            while ( result.next() ) {
                Actus actus = map( result );
                // actus.setUserCreate( daoFactory.getUserDao().getUserById(
                // result.getInt( "id_user" ), con ) );
                list.add( actus );
            }
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        } finally {
            fermeturesSilencieuses( result, state, con );
        }
        return list;
    }

    public boolean createActu( Actus actu ) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKey = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, INSERT_ACTU, true, actu.getDate(),
                    actu.getTitre(), actu.getTitreAccueil(), actu.getText(), 1 );
            preparedStatement.executeUpdate();
            generatedKey = preparedStatement.getGeneratedKeys();
            if ( generatedKey.next() )
                actu.setId( generatedKey.getInt( 1 ) );
            else
                throw new SQLException( "CREATING USER FAILED" );

            return true;
        } catch ( SQLException ex ) {
            ex.printStackTrace();
            return false;
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
            fermetureSilencieuse( generatedKey );
        }
    }

    public boolean modifActu( Actus actu ) {
        Connection connexion = null;
        PreparedStatement state = null;
        try {
            connexion = daoFactory.getConnection();
            state = initialisationRequetePreparee( connexion, UPDATE_ACTU, false, actu.getDate(),
                    actu.getTitre(), actu.getTitreAccueil(), actu.getText(), actu.getId() );
            state.executeUpdate();
            return true;
        } catch ( SQLException e ) {
            e.printStackTrace();
            return false;
        } finally {
            fermeturesSilencieuses( state, connexion );
        }
    }

    public boolean delete( Actus actu ) {
        Connection connexion = null;
        PreparedStatement state = null;
        try {
            connexion = daoFactory.getConnection();
            state = initialisationRequetePreparee( connexion, REMOVE_ACTU, false, actu.getId() );
            state.executeUpdate();
            return true;
        } catch ( SQLException e ) {
            e.printStackTrace();
            return false;
        } finally {
            fermeturesSilencieuses( state, connexion );
        }
    }

    @Override
    public Actus map( ResultSet result ) throws SQLException {
        Actus actus = new Actus();
        actus.setId( result.getInt( "id" ) );
        actus.setDate( result.getString( "date" ) );
        actus.setTitre( result.getString( "titre" ) );
        actus.setTitreAccueil( result.getString( "titre_accueil" ) );
        actus.setText( result.getString( "text" ) );
        return actus;
    }

}
