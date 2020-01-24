package fr.scurto.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.scurto.dao.DAOFactory;
import fr.scurto.dao.imp.ActusDao;

/**
 * Servlet implementation class monEspaceServlet
 */
@WebServlet( "/monEspace" )
public class monEspaceServlet extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String MON_ESPACE       = "/WEB-INF/administration/mon_espace.jsp";
    private static final String TITRE            = "titre";
    private static final String LIST_ACTUS       = "actus";
    private ActusDao            actusDao;

    @Override
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.actusDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getActusDao();
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        System.out.println( "dans la servlet mon espace perso" );
        request.setAttribute( TITRE, "Gestions des actualités" );
        request.setAttribute( LIST_ACTUS, actusDao.getActusAccueil() );
        this.getServletContext().getRequestDispatcher( MON_ESPACE ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet( request, response );
    }

}
