package fr.scurto.beans;

public class Actus {

    private int    id;
    private String titre;
    private String titreAccueil;
    private String date;
    private String text;
    private User   userCreate;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre( String titre ) {
        this.titre = titre;
    }

    public String getTitreAccueil() {
        return titreAccueil;
    }

    public void setTitreAccueil( String titreAccueil ) {
        this.titreAccueil = titreAccueil;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public User getUserCreate() {
        return userCreate;
    }

    public void setUserCreate( User userCreate ) {
        this.userCreate = userCreate;
    }

    @Override
    public String toString() {
        return "Actus [titre=" + titre + ", titreAccueil=" + titreAccueil + ", date=" + date + ", text=" + text
                + ", userCreate=" + userCreate + "]";
    }

}
