package fr.scurto.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.scurto.beans.User;
import fr.scurto.dao.DAOFactory;
import fr.scurto.dao.imp.UserDao;
import fr.scurto.security.ReCaptchaService;
import fr.scurto.security.SecurityService;

/**
 * Servlet implementation class AuthenticateServlet
 */
@WebServlet( "/authenticate" )
public class AuthenticateServlet extends HttpServlet {
    private static final long   serialVersionUID = 1L;

    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String DIV_RETOUR       = "/jsp/nav/nav-connexion.jsp";
    private static final String SESSION_LOG      = "user_scurto";
    private UserDao             userDao;

    @Override
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.userDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( DIV_RETOUR ).forward( request, response );
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        try {
            JsonObject data = new Gson().fromJson( request.getReader(), JsonObject.class );
            response.setContentType( "text/plain" );
            response.setCharacterEncoding( "UTF-8" );
            String mail = data.get( "mail" ).getAsString();
            String password = SecurityService.getHashCode( data.get( "password" ).getAsString() );
            if ( mail != null && password != null ) {
                if ( ReCaptchaService.isValid( data.get( "captchatoken" ).getAsString() ) ) {
                    // if ( true ) {
                    User user = userDao.login( mail, password );
                    if ( user != null ) {
                        request.getSession().setAttribute( SESSION_LOG, user );
                        response.getWriter().append( "valide" );
                    } else {
                        response.getWriter().append( "No" );
                    }
                } else {
                    response.getWriter().append( "No" );
                }
            } else {
                response.getWriter().append( "No" );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            response.getWriter().append( "No" );
        }
    }

}
