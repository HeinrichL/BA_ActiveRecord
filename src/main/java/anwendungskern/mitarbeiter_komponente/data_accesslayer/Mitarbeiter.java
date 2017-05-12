package anwendungskern.mitarbeiter_komponente.data_accesslayer;

import org.javalite.activejdbc.Model;

import java.math.BigDecimal;

/**
 * Created by Heinrich on 28.11.2015.
 */
public abstract class Mitarbeiter extends Model{

    protected final String vorname = "vorname";
    protected final String nachname = "nachname";

    public int getID(){
        return getId() == null ? 0 : getLongId().intValue();
    }

    public String getVorname(){ return getString(vorname); }
    public void setVorname(String vorname){
        setString(this.vorname, vorname);
    }

    public String getNachname(){ return getString(nachname); }
    public void setNachname(String nachname){
        setString(this.nachname, nachname);
    }
}
