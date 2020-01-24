package fr.scurto.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.scurto.beans.Actus;
import fr.scurto.dao.DAOFactory;
import fr.scurto.dao.imp.ActusDao;

/**
 * Servlet implementation class ModifActusServlet
 */
@WebServlet( "/modification-actu" )
public class ModifActusServlet extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String CONF_DAO_FACTORY = "daofactory";

    private static final String TABLEAU_ACTU     = "/WEB-INF/administration/tableau-actualite.jsp";
    private static final String LIST_ACTUS       = "actus";

    private static final String ATT_ID           = "idActu";
    private static final String ATT_TITRE        = "titre";
    private static final String ATT_SOUS_TITRE   = "sousTitre";
    private static final String ATT_TEXT         = "textActu";
    private static final String ATT_TYPE         = "type";

    private ActusDao            actusDao;
    private List<Actus>         listActusAccueil;

    @Override
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.actusDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getActusDao();
        listActusAccueil = actusDao.getActusAccueil();
        System.out.println( "INIT SERVLET ACTUS" );
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        System.out.println( "dans la methode get" );
        if ( request.getParameter( "search" ) != null )
            request.setAttribute( LIST_ACTUS,
                    listActusAccueil.stream()
                            .filter( a -> a.getTitre().toLowerCase()
                                    .contains( request.getParameter( "search" ).toString().toLowerCase() ) )
                            .collect( Collectors.toList() ) );
        else
            request.setAttribute( LIST_ACTUS, listActusAccueil );

        this.getServletContext().getRequestDispatcher( TABLEAU_ACTU ).forward( request, response );

    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        System.out.println( "dans la methode post" );
        SimpleDateFormat sf = new SimpleDateFormat( "dd-MM-yyyy" );
        Actus actu = new Actus();
        if ( request.getParameter( ATT_TYPE ).equals( "delete" ) ) {
            actu.setId( Integer.parseInt( request.getParameter( ATT_ID ) ) );
            if ( actusDao.delete( actu ) ) {
                listActusAccueil = listActusAccueil.stream().filter( a -> a.getId() != actu.getId() )
                        .collect( Collectors.toList() );
                response.getWriter().append( "success" );
            } else
                response.getWriter().append( "error" );
        } else {
            actu.setTitre( request.getParameter( ATT_TITRE ) );
            actu.setTitreAccueil( request.getParameter( ATT_SOUS_TITRE ) );
            actu.setText( request.getParameter( ATT_TEXT ) );
            actu.setDate( sf.format( new Date() ) );
            System.out.println( request.getParameter( ATT_TYPE ) );
            if ( request.getParameter( ATT_TYPE ).equals( "ajout-actu" ) ) {
                if ( actusDao.createActu( actu ) ) {
                    listActusAccueil.add( actu );
                    response.getWriter().append( "success" );
                } else
                    response.getWriter().append( "error" );
            } else {
                actu.setId( Integer.parseInt( request.getParameter( ATT_ID ) ) );
                if ( actusDao.modifActu( actu ) ) {
                    for ( Actus a : listActusAccueil )
                        if ( a.getId() == actu.getId() )
                            a = actu;
                    response.getWriter().append( "success" );
                } else
                    response.getWriter().append( "error" );
            }
        }
    }

    public List<Actus> getListActusAccueil() {
        return listActusAccueil;
    }
}
