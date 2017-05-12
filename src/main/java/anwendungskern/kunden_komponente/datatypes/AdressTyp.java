package anwendungskern.kunden_komponente.datatypes;

import java.io.Serializable;

/**
 * Created by Heinrich on 28.11.2015.
 */

public class AdressTyp implements Serializable {
    public final String strasse;
    public final String hausnummer;
    public final String plz;
    public final String ort;

    public AdressTyp(String strasse, String hausnummer, String plz, String ort)
    {
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.ort = ort;
    }

    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof AdressTyp)) return false;

        AdressTyp a = (AdressTyp) o;
        return this.strasse.equals(a.strasse)&&
                this.hausnummer.equals(a.hausnummer)&&
                this.plz.equals(a.plz)&&
                this.ort.equals(a.ort);
    }
}
