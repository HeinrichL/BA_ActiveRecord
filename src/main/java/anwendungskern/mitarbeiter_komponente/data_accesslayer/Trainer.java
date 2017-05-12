package anwendungskern.mitarbeiter_komponente.data_accesslayer;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by Heinrich on 28.11.2015.
 */
@Table("trainer")
@IdName("id")
public class Trainer extends Mitarbeiter {

    public Trainer(String vorname, String nachname){
        setVorname(vorname);
        setNachname(nachname);
    }

    public Trainer(){}

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof Trainer)) return false;

        Trainer t = (Trainer) other;
        return this.getID() == t.getID() &&
                getVorname().equals(t.getVorname()) &&
                getNachname().equals(t.getNachname());

    }
}
