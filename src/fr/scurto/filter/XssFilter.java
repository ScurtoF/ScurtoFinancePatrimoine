package fr.scurto.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@WebFilter( filterName = "XssFilter", urlPatterns = { "/*" } )
public class XssFilter implements Filter {

    @Override
    public void init( FilterConfig config ) throws ServletException {
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
            throws IOException, ServletException {
        chain.doFilter( new XssRequestWrapper( (HttpServletRequest) request ), response );
    }

    @Override
    public void destroy() {
    }
}

class XssRequestWrapper extends HttpServletRequestWrapper {

    // Expression r�guli�re de controle
    private static final Pattern[] XSS_PATTERNS = {
            Pattern.compile( "<.*>" ),
            Pattern.compile( "&.*?;" ),
            Pattern.compile( "%[0-9a-fA-F]*" )
    };

    // Constructeur
    public XssRequestWrapper( HttpServletRequest request ) {
        super( request );
    }

    @Override
    public String[] getParameterValues( String parameterName ) {
        String[] values = super.getParameterValues( parameterName );

        if ( values == null )
            return null;

        int count = values.length;
        for ( int i = 0; i < count; i++ )
            // On remplace chaque chaine de caract�re
            values[i] = removeTags( values[i] );

        return null;
    }

    @Override
    public String getParameter( String parameter ) {
        return removeTags( super.getParameter( parameter ) );
    }

    // S�curiser l'entete
    @Override
    public String getHeader( String name ) {
        return removeTags( super.getHeader( name ) );
    }

    // Code de s�curit�
    private String removeTags( String value ) {
        if ( value != null ) {
            // On retire le code ASCII 0, au cas ou
            value = value.replaceAll( "\0", "" );

            // Supprime l'ensemble de tags et des entit�s existants
            for ( Pattern pattern : XSS_PATTERNS )
                value = pattern.matcher( value ).replaceAll( "" );

            // Au cas ou les caract�re < et > ne seraient pas en nombre pairs
            value = value.replaceAll( "<", "" );
            value = value.replaceAll( ">", "" );
        }
        return value;
    }

}
