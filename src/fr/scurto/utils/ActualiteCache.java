package fr.scurto.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.scurto.beans.Actus;
import fr.scurto.dao.imp.ActusDao;

public class ActualiteCache {

    private static List<Actus> listActu;

    // GET
    public static List<Actus> getAllActus( ActusDao actuDao ) {
        if ( listActu == null ) {
            updateAllActuList( actuDao );
        }
        return new ArrayList<>( listActu );
    }

    public static Actus getActuById( int id ) {
        synchronized ( listActu ) {
            return listActu.stream().filter( a -> a.getId() == id ).collect( Collectors.toList() ).get( 0 );
        }
    }

    public static List<Actus> getActusAccueil( ActusDao actuDao ) {
        if ( listActu == null ) {
            updateAllActuList( actuDao );
        }
        List<Actus> listAccueil = new ArrayList<>();
        for ( Actus actu : listActu ) {
            if ( actu.getPlacement() != null && actu.getPlacement() != 0 ) {
                listAccueil.add( actu );
            }
        }
        return listAccueil;
    }

    // SEARCH ACTU
    public static List<Actus> searchActus( String str ) {
        if ( str != null ) {
            return listActu.stream()
                    .filter( a -> a.getTitre().toLowerCase()
                            .contains( str.toString().toLowerCase() ) )
                    .collect( Collectors.toList() );
        } else {
            return listActu;
        }
    }

    // UPDATE
    public static void updateAllActuList( ActusDao actuDao ) {
        if ( listActu != null ) {
            synchronized ( listActu ) {
                listActu = actuDao.getAllActus();
            }
        } else {
            listActu = actuDao.getAllActus();
        }
    }

    // DELETE
    public static boolean deleteActu( Actus actu, ActusDao actuDao ) {
        if ( actuDao.delete( actu ) ) {
            listActu = listActu.stream().filter( a -> a.getId() != actu.getId() )
                    .collect( Collectors.toList() );
            return true;
        } else {
            return false;
        }
    }

    // CREATE
    public static boolean createActu( Actus actu, ActusDao actuDao ) {
        if ( actuDao.createActu( actu ) ) {
            listActu.add( actu );
            Actus actuMovePlacement = null;
            if ( actu.getPlacement() != null && actu.getPlacement() != 0 ) {
                List<Actus> listActuAcMmPlacement = listActu.stream()
                        .filter( a -> a.getPlacement() == actu.getPlacement() ).collect( Collectors.toList() );
                if ( listActuAcMmPlacement.size() != 0 ) {
                    actuMovePlacement = listActuAcMmPlacement.get( 0 );
                    actuMovePlacement.setPlacement( 0 );
                    return modifieActu( actuMovePlacement, actuDao );

                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    // MODIFIE
    public static boolean modifieActu( Actus actu, ActusDao actuDao ) {
        Actus actuModified = listActu.stream().filter( a -> a.getId() == actu.getId() )
                .collect( Collectors.toList() ).get( 0 );
        actuModified.setDate( actu.getDate() );
        if ( actu.getPlacement() == null )
            actuModified.setPlacement( 0 );
        else
            actuModified.setPlacement( actu.getPlacement() );
        actuModified.setText( actu.getText() );
        actuModified.setTitre( actu.getTitre() );
        actuModified.setTitreAccueil( actu.getTitreAccueil() );
        if ( actuDao.modifActu( actuModified ) ) {
            if ( actuModified.getPlacement() != null && actuModified.getPlacement() != 0 ) {
                List<Actus> listActuAcMmPlacement = listActu.stream()
                        .filter( a -> a.getPlacement() == actuModified.getPlacement()
                                && a.getId() != actuModified.getId() )
                        .collect( Collectors.toList() );
                if ( listActuAcMmPlacement.size() != 0 ) {
                    Actus actuMovePlacement = listActuAcMmPlacement.get( 0 );
                    actuMovePlacement.setPlacement( 0 );
                    return modifieActu( actuMovePlacement, actuDao );
                } else {
                    List<Actus> listActus = new ArrayList<>();
                    listActus.addAll( listActu.stream()
                            .filter( a -> ( a.getPlacement() != null && a.getPlacement() != 0 ) )
                            .sorted( Comparator.comparing( Actus::getPlacement ) )
                            .collect( Collectors.toList() ) );
                    listActus.addAll( listActu.stream()
                            .filter( a -> ( a.getPlacement() == null || a.getPlacement() == 0 ) )
                            .collect( Collectors.toList() ) );
                    listActu = listActus;
                    return true;
                }
            } else {
                List<Actus> listActus = new ArrayList<>();
                listActus.addAll( listActu.stream()
                        .filter( a -> ( a.getPlacement() != null && a.getPlacement() != 0 ) )
                        .sorted( Comparator.comparing( Actus::getPlacement ) )
                        .collect( Collectors.toList() ) );
                listActus.addAll( listActu.stream()
                        .filter( a -> ( a.getPlacement() == null || a.getPlacement() == 0 ) )
                        .collect( Collectors.toList() ) );
                listActu = listActus;
                return true;
            }
        } else {
            return false;
        }
    }
}
