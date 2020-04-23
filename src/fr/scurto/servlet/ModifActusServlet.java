package fr.scurto.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.scurto.beans.Actus;
import fr.scurto.dao.DAOFactory;
import fr.scurto.dao.imp.ActusDao;
import fr.scurto.utils.ActualiteCache;

/**
 * Servlet implementation class ModifActusServlet
 */
@WebServlet( "/modification-actu" )
public class ModifActusServlet extends HttpServlet {
    private static final long   serialVersionUID   = 1L;
    private static final String CONF_DAO_FACTORY   = "daofactory";

    private static final String TABLEAU_ACTU       = "/WEB-INF/administration/tableau-actualite.jsp";
    private static final String LIST_ACTUS         = "actus";
    private static final String LIST_ACTUS_ACCUEIL = "actus_accueil";

    private static final String ATT_ID             = "idActu";
    private static final String ATT_TITRE          = "titre";
    private static final String ATT_SOUS_TITRE     = "sousTitre";
    private static final String ATT_TEXT           = "textActu";
    private static final String ATT_TYPE           = "type";
    private static final String ATT_PLACEMENT      = "placement";

    private ActusDao            actusDao;

    @Override
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.actusDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getActusDao();
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        if ( request.getParameter( "search" ) != null )
            request.setAttribute( LIST_ACTUS, ActualiteCache.searchActus( request.getParameter( "search" ) ) );
        else
            request.setAttribute( LIST_ACTUS, ActualiteCache.getAllActus( actusDao ) );

        request.setAttribute( LIST_ACTUS_ACCUEIL, ActualiteCache.getAllActus( actusDao ) );
        this.getServletContext().getRequestDispatcher( TABLEAU_ACTU ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        SimpleDateFormat sf = new SimpleDateFormat( "dd-MM-yyyy" );
        Actus actu = new Actus();
        String requestType = "";
        if ( request.getParameter( ATT_TYPE ) != null )
            requestType = request.getParameter( ATT_TYPE );
        if ( requestType.equals( "delete" ) ) {
            actu.setId( Integer.parseInt( request.getParameter( ATT_ID ) ) );
            if ( ActualiteCache.deleteActu( actu, actusDao ) ) {
                response.getWriter().append( "success" );
            } else
                response.getWriter().append( "error" );
        } else {
            if ( request.getParameter( ATT_TITRE ) != null )
                actu.setTitre( request.getParameter( ATT_TITRE ) );
            if ( request.getParameter( ATT_SOUS_TITRE ) != null )
                actu.setTitreAccueil( request.getParameter( ATT_SOUS_TITRE ) );
            if ( request.getParameter( ATT_TEXT ) != null )
                actu.setText( request.getParameter( ATT_TEXT ) );
            if ( request.getParameter( ATT_PLACEMENT ) != null )
                actu.setPlacement( Integer.parseInt( request.getParameter( ATT_PLACEMENT ) ) );
            actu.setDate( sf.format( new Date() ) );
            // PLACEMENT
            if ( requestType.equals( "ajout-actu" ) ) {
                if ( ActualiteCache.createActu( actu, actusDao ) ) {
                    response.getWriter().append( "success" );
                } else
                    response.getWriter().append( "error" );
            } else {
                actu.setId( Integer.parseInt( request.getParameter( ATT_ID ) ) );
                if ( ActualiteCache.modifieActu( actu, actusDao ) ) {
                    response.getWriter().append( "success" );
                } else
                    response.getWriter().append( "error" );
            }
        }
    }

}
