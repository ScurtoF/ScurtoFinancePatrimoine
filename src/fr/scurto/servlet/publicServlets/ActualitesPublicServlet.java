package fr.scurto.servlet.publicServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.scurto.beans.Actus;
import fr.scurto.dao.DAOFactory;
import fr.scurto.dao.imp.ActusDao;
import fr.scurto.security.SecurityService;
import fr.scurto.utils.ActualiteCache;

/**
 * Servlet implementation class AuthenticateServlet
 */
@WebServlet( "/actualites" )
public class ActualitesPublicServlet extends HttpServlet {
    private static final long   serialVersionUID = 1L;

    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String PAGE             = "/actualites.jsp";
    private static final String DIV_RETOUR       = "/jsp/container/actualites-article.jsp";
    private ActusDao            actusDao;

    @Override
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.actusDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getActusDao();
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        List<Actus> list = ActualiteCache.getAllActus( actusDao );
        request.setAttribute( "actus", list );
        if ( list != null && !list.isEmpty() ) {
            if ( request.getParameter( "actu" ) != null ) {
                try {
                    request.setAttribute( "selectedActu",
                            ActualiteCache.getActuById( Integer.parseInt( request.getParameter( "actu" ) ) ) );
                } catch ( Exception e ) {
                    request.setAttribute( "selectedActu", list.get( 0 ) );
                }
            } else {
                request.setAttribute( "selectedActu", list.get( 0 ) );
            }
        }
        this.getServletContext().getRequestDispatcher( PAGE ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        try {
            String actuId = request.getParameter( "choix" );
            System.out.println( actuId );
            if ( !actuId.isEmpty() ) {
                String password = SecurityService.getHashCode( "scurto" );
                System.out.println( "scurto -> " + password );
                request.setAttribute( "selectedActu", ActualiteCache.getActuById( Integer.parseInt( actuId ) ) );
            }
        } catch ( Exception e ) {
            request.setAttribute( "selectedActu", null );
        }
        this.getServletContext().getRequestDispatcher( DIV_RETOUR ).forward( request, response );
    }

    // private void updateActus() {
    // DateTime currentTime = new DateTime();
    // if ( majTime == null ) {
    // long diffMillis = currentTime.getMillis() - majTime.getMillis();
    // if ( diffMillis > 1200 ) {
    // System.out.println( "MISE A JOUR LIST + 1200ms" );
    // listActus = actusDao.getAllActus();
    // majTime = new DateTime();
    // }
    // } else {
    // System.out.println( "MISE A JOUR LIST CLASSIC" );
    // listActus = actusDao.getAllActus();
    // majTime = new DateTime();
    // }
    // }
}