package fr.scurto.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;

import fr.scurto.security.ReCaptchaService;

/**
 * Servlet implementation class ProfessionnelServlet
 */
@WebServlet( "/contact" )
public class ContactServlet extends HttpServlet {
    private static final long   serialVersionUID = 1L;

    private static final String VUE_ACCES_PRO    = "/jsp/container/container-professionnels.jsp";

    private static final String EMAIL_FROM       = "contact@scurto.fr";
    private static final String EMAIL_TO         = "f.scurto@live.fr, contact@scurto.fr";

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // Direction de la requete
        this.getServletContext().getRequestDispatcher( VUE_ACCES_PRO ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        String username = "contact@scurto.fr";
        String password = "0Q~.w@@3)d_1";
        // Etape 1 : Création de la session
        Properties props = new Properties();
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.host", "mail.scurto.fr" );
        props.put( "mail.smtp.socketFactory.port", "465" );
        props.put( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
        props.put( "mail.smtp.port", "465" );
        boolean b = false;
        try {
            b = ReCaptchaService.isValid( request.getParameter( "captchatoken" ) );
            if ( b ) {
                Session session = Session.getInstance( props,
                        new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication( username, password );
                            }
                        } );
                // Etape 2 : Création de l'objet Message
                Message message = new MimeMessage( session );
                message.setFrom( new InternetAddress( EMAIL_FROM ) );
                message.setRecipients( Message.RecipientType.TO,
                        InternetAddress.parse( EMAIL_TO ) );
                message.setSubject( "Contact site : " + request.getParameter( "mail" ) );
                message.setText( request.getParameter( "message" ) + "\nInformation : " + request.getParameter( "nom" )
                        + "-" + request.getParameter( "prenom" ) + "\nTelephone : " + request.getParameter( "tel" ) );
                // Etape 3 : Envoyer le message
                response.getWriter().write( "Message envoyé" );
                Transport.send( message );
            }
        } catch ( ParseException e1 ) {
            e1.printStackTrace();
        } catch ( MessagingException e ) {
            throw new RuntimeException( e );
        }
    }

}
