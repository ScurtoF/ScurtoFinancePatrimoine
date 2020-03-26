package fr.scurto.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
    private List<Actus>         listActusAccueil;

    @Override
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.actusDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getActusDao();
        listActusAccueil = actusDao.getAllActus();
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        if ( request.getParameter( "search" ) != null )
            request.setAttribute( LIST_ACTUS,
                    listActusAccueil.stream()
                            .filter( a -> a.getTitre().toLowerCase()
                                    .contains( request.getParameter( "search" ).toString().toLowerCase() ) )
                            .collect( Collectors.toList() ) );
        else
            request.setAttribute( LIST_ACTUS, listActusAccueil );

        request.setAttribute( LIST_ACTUS_ACCUEIL, listActusAccueil );
        this.getServletContext().getRequestDispatcher( TABLEAU_ACTU ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
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
            // PLACEMENT
            boolean otherUpdatePlacement = false;
            Actus actuMovePlacement = null;
            if ( request.getParameter( ATT_PLACEMENT ) != null ) {
                String placement = request.getParameter( ATT_PLACEMENT );
                if ( !placement.isEmpty() && !placement.equals( "0" ) ) {
                    actu.setPlacement( Integer.parseInt( placement ) );
                    List<Actus> listActuAcMmPlacement = listActusAccueil.stream()
                            .filter( a -> a.getPlacement() == actu.getPlacement() ).collect( Collectors.toList() );
                    if ( listActuAcMmPlacement.size() != 0 ) {
                        otherUpdatePlacement = true;
                        actuMovePlacement = listActuAcMmPlacement.get( 0 );
                        actuMovePlacement.setPlacement( null );
                    }
                }
            }
            if ( request.getParameter( ATT_TYPE ).equals( "ajout-actu" ) ) {
                if ( actusDao.createActu( actu ) ) {
                    listActusAccueil.add( actu );
                    if ( !otherUpdatePlacement )
                        response.getWriter().append( "success" );
                } else
                    response.getWriter().append( "error" );
            } else {
                actu.setId( Integer.parseInt( request.getParameter( ATT_ID ) ) );
                Actus actuModified = listActusAccueil.stream().filter( a -> a.getId() == actu.getId() )
                        .collect( Collectors.toList() ).get( 0 );
                actuModified.setDate( actu.getDate() );
                actuModified.setPlacement( actu.getPlacement() );
                actuModified.setText( actu.getText() );
                actuModified.setTitre( actu.getTitre() );
                actuModified.setTitreAccueil( actu.getTitreAccueil() );
                if ( actusDao.modifActu( actuModified ) ) {
                    for ( Actus a : listActusAccueil )
                        if ( a.getId() == actu.getId() )
                            a = actu;
                    if ( !otherUpdatePlacement ) {
                        List<Actus> listActus = new ArrayList<>();
                        listActus.addAll( listActusAccueil.stream()
                                .filter( a -> ( a.getPlacement() != null && a.getPlacement() != 0 ) )
                                .sorted( Comparator.comparing( Actus::getPlacement ) )
                                .collect( Collectors.toList() ) );
                        listActus.addAll( listActusAccueil.stream()
                                .filter( a -> ( a.getPlacement() == null || a.getPlacement() == 0 ) )
                                .collect( Collectors.toList() ) );
                        listActusAccueil = listActus;
                        response.getWriter().append( "success" );
                    }
                } else
                    response.getWriter().append( "error" );
            }
            if ( otherUpdatePlacement ) {
                if ( actusDao.modifActu( actuMovePlacement ) ) {
                    List<Actus> listActus = new ArrayList<>();
                    listActus.addAll( listActusAccueil.stream()
                            .filter( a -> ( a.getPlacement() != null && a.getPlacement() != 0 ) )
                            .sorted( Comparator.comparing( Actus::getPlacement ) )
                            .collect( Collectors.toList() ) );
                    listActus.addAll( listActusAccueil.stream()
                            .filter( a -> ( a.getPlacement() == null || a.getPlacement() == 0 ) )
                            .collect( Collectors.toList() ) );
                    listActusAccueil = listActus;
                    response.getWriter().append( "success" );
                } else
                    response.getWriter().append( "error" );
            }
        }
    }

}
