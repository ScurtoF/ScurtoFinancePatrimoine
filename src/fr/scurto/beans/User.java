package fr.scurto.beans;

public class User {

    private int    id;
    private String email;
    private int    admin;
    private String name;
    private String firstName;
    private String societe;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin( int admin ) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete( String societe ) {
        this.societe = societe;
    }

    public String getIcon() {
        if ( firstName != null && !firstName.isEmpty() && !Character.isDigit( firstName.charAt( 0 ) )
                && Character.isLetter( firstName.charAt( 0 ) ) ) {
            return firstName.toLowerCase().charAt( 0 ) + "-icon.png";
        } else if ( email != null && !email.isEmpty() && !Character.isDigit( email.charAt( 0 ) )
                && Character.isLetter( email.charAt( 0 ) ) ) {
            return email.toLowerCase().charAt( 0 ) + "-icon.png";
        } else {
            return "account.png";
        }
    }

    public String getCompleteName() {
        return firstName.substring( 0, 1 ).toUpperCase()
                .concat( firstName.substring( 1 ).toLowerCase() ) + " "
                + name.substring( 0, 1 ).toUpperCase()
                        .concat( name.substring( 1 ).toLowerCase() );
    }
}
