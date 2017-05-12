package anwendungskern.kurs_komponente.datatypes;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Heinrich on 03.01.2016.
 */
public class VeranstaltungszeitTyp implements Serializable{
    final DateTime StartZeitpunkt;
    final DateTime EndZeitpunkt;

    public VeranstaltungszeitTyp(DateTime start, DateTime end) {
        StartZeitpunkt = start;
        EndZeitpunkt = end;
    }

    public DateTime getStartZeitpunkt(){
        return StartZeitpunkt;
    }

    public DateTime getEndZeitpunkt(){
        return EndZeitpunkt;
    }

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof VeranstaltungszeitTyp)) return false;

        VeranstaltungszeitTyp k = (VeranstaltungszeitTyp) other;

        boolean eq = getStartZeitpunkt().equals(k.getStartZeitpunkt());//.getMillis() == k.getStartZeitpunkt().getMillis() &&
                //getEndZeitpunkt().getMillis() == k.getEndZeitpunkt().getMillis();

        return eq;
    }
}
