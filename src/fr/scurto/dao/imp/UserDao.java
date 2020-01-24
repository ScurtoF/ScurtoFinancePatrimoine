package fr.scurto.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.scurto.beans.User;
import fr.scurto.dao.DAO;
import fr.scurto.dao.DAOFactory;

public class UserDao extends DAO<User> {

    private static final String FIND_USER       = "SELECT * FROM scurto_bdd.users WHERE email = ? AND password = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM scurto_bdd.users WHERE id = ?";

    public UserDao( DAOFactory daoFactory ) {
        super( daoFactory );
    }

    public User login( String login, String password ) {
        User user = null;
        ResultSet result = null;
        PreparedStatement state = null;
        Connection con = null;
        try {
            con = daoFactory.getConnection();
            state = initialisationRequetePreparee( con, FIND_USER, false, login, password );
            result = state.executeQuery();
            if ( result.first() ) {
                user = map( result );
            }
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        } finally {
            fermeturesSilencieuses( result, state, con );
        }
        return user;
    }

    public User getUserById( int id, Connection con ) {
        User user = null;
        ResultSet result = null;
        PreparedStatement state = null;
        try {
            con = daoFactory.getConnection();
            state = initialisationRequetePreparee( con, FIND_USER_BY_ID, false, id );
            result = state.executeQuery();
            if ( result.first() ) {
                user = map( result );
            }
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        } finally {
            fermeturesSilencieuses( result, state );
        }
        return user;
    }

    @Override
    public User map( ResultSet result ) throws SQLException {
        User user = new User();
        user.setId( result.getInt( "id" ) );
        user.setEmail( result.getString( "email" ) );
        user.setName( result.getString( "name" ) );
        user.setFirstName( result.getString( "first_name" ) );
        user.setSociete( result.getString( "societe" ) );
        user.setAdmin( result.getInt( "admin" ) );
        return user;
    }

}
