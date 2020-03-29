package fr.scurto.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.scurto.dao.DAOFactory;
import fr.scurto.dao.imp.ActusDao;
import fr.scurto.utils.ActualiteCache;

@WebFilter( filterName = "RestrictionFilter", urlPatterns = "/*" )
public class RestrictionFilter implements Filter {

    public static final String  PAGE            = "page";
    private static final String ATT_CACHE_ACTUS = "cacheActus";
    private static final String SESSION_LOG     = "user_scurto";

    private Map<String, String> mapPagePublic;
    private Map<String, String> mapPagePrivate;

    @Override
    public void init( FilterConfig config ) throws ServletException {
        mapPagePublic = new HashMap<>();
        mapPagePublic.put( "/index.jsp", "index" );
        mapPagePublic.put( "/professionnels.jsp", "professionnels" );
        mapPagePublic.put( "/construction.jsp", "construction" );
        mapPagePublic.put( "/entreprises.jsp", "entreprises" );
        mapPagePublic.put( "/contact.jsp", "contact" );
        mapPagePublic.put( "/presentation.jsp", "presentation" );
        mapPagePublic.put( "/liens-utiles.jsp", "liens-utiles" );
        mapPagePublic.put( "/actualites.jsp", "actualites" );
        mapPagePublic.put( "/cv.html", "cv" );
        mapPagePublic.put( "/mention_legales.jsp", "mention_legales" );
        mapPagePublic.put( "/authenticate", "authenticate" );
        mapPagePublic.put( "/contact", "contact" );
        mapPagePublic.put( "/actualites", "actualites" );

        mapPagePrivate = new HashMap<>();
        mapPagePrivate.put( "/monEspace", "monEspace" );
        mapPagePrivate.put( "/modification-actu", "modification-actu" );
    }

    @Override
    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        /** Récupération de la session depuis la requête */
        // HttpSession session = request.getSession();
        System.out.println( request.getRequestURI().substring(
                request.getContextPath().length() ) );

        /** Non-filtrage des ressources statiques */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        if ( chemin.startsWith( "/assets" ) || chemin.startsWith( "/images" ) || chemin.startsWith( "/icon" ) ) {
            chain.doFilter( request, response );
            return;
        }
        System.out.println( chemin );

        if ( mapPagePublic.containsKey( chemin ) ) {
            if ( chemin.equals( "/index.jsp" ) ) {
                ActusDao actusDao = ( (DAOFactory) request.getServletContext().getAttribute( "daofactory" ) )
                        .getActusDao();
                request.setAttribute( "actus", ActualiteCache.getActusAccueil( actusDao ) );
            }
            request.setAttribute( PAGE, mapPagePublic.get( chemin ) );
            chain.doFilter( request, response );
            return;
        } else if ( mapPagePrivate.containsKey( chemin ) && request.getSession().getAttribute( SESSION_LOG ) != null ) {
            request.setAttribute( PAGE, mapPagePrivate.get( chemin ) );
            chain.doFilter( request, response );
        } else {
            ActusDao actusDao = ( (DAOFactory) request.getServletContext().getAttribute( "daofactory" ) )
                    .getActusDao();
            request.setAttribute( "actus", ActualiteCache.getActusAccueil( actusDao ) );
            req.getRequestDispatcher( "/index.jsp" ).forward( req, resp );
            request.setAttribute( PAGE, mapPagePublic.get( "index" ) );
        }
    }

    @Override
    public void destroy() {
    }

}
