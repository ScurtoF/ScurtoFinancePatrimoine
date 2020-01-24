package fr.scurto.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.scurto.dao.DAOFactory;

@WebListener
public class InitialisationDaoFactory implements ServletContextListener {

    private static final String ATT_DAO_FACTORY = "daofactory";
    private static final String ATT_CACHE_ACTUS = "cacheActus";
    private DAOFactory          daoFactory;

    @Override
    public void contextInitialized( ServletContextEvent event ) {
        System.out.println( "Init dao factory" );
        // Récupération du ServletContext lors du chargement de l'application
        ServletContext servletContext = event.getServletContext();
        // Instanciation de notre DAOFactory
        this.daoFactory = DAOFactory.getInstance();
        // Enregistrement dans un attribut ayant pour portée toute l'application
        servletContext.setAttribute( ATT_DAO_FACTORY, daoFactory );
    }

    @Override
    public void contextDestroyed( ServletContextEvent event ) {
        daoFactory.closePool();
    }

}
