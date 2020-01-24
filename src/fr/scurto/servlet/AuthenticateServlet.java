package fr.scurto.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;

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
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( DIV_RETOUR ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        String mail = request.getParameter( "mail" );
        String password = SecurityService.getHashCode( request.getParameter( "password" ) );

        try {
            if ( ReCaptchaService.isValid( request.getParameter( "captchatoken" ) ) ) {
                User user = userDao.login( mail, password );
                if ( user != null ) {
                    request.getSession().setAttribute( SESSION_LOG, user );
                    response.getWriter().append( "valide" );
                } else {
                    response.getWriter().append( "No" );
                }
            }
        } catch ( ParseException e ) {
            e.printStackTrace();
        }
    }

}
