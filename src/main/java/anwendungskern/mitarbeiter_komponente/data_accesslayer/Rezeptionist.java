package anwendungskern.mitarbeiter_komponente.data_accesslayer;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.util.Map;

/**
 * Created by Heinrich on 28.11.2015.
 */
@Table("rezeptionisten")
@IdName("id")
public class Rezeptionist extends Mitarbeiter {

    public Rezeptionist(String vorname, String nachname){
        setVorname(vorname);
        setNachname(nachname);
    }

    public Rezeptionist(){}

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof Rezeptionist)) return false;

        Rezeptionist r = (Rezeptionist) other;
        return this.getID() == r.getID() &&
                getVorname().equals(r.getVorname()) &&
                getNachname().equals(r.getNachname());
    }
}
