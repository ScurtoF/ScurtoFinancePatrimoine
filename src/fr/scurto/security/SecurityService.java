package fr.scurto.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityService {

    public static String getHashCode( String str ) {
        MessageDigest md;
        if ( str != null ) {
            try {
                md = MessageDigest.getInstance( "SHA-256" );
                md.update( str.getBytes() );

                byte byteData[] = md.digest();

                // convertir le tableau de bits en une format hexadécimal -
                // méthode
                // 1
                StringBuffer sb = new StringBuffer();
                for ( int i = 0; i < byteData.length; i++ ) {
                    sb.append( Integer.toString( ( byteData[i] & 0xff ) + 0x100, 16 ).substring( 1 ) );
                }

                return sb.toString();
            } catch ( NoSuchAlgorithmException e ) {
                e.printStackTrace();
                return null;
            }
        }
        System.out.println( "str is null" );
        return "";
    }

}
