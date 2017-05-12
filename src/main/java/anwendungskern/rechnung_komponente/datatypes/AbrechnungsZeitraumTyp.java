package anwendungskern.rechnung_komponente.datatypes;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class AbrechnungsZeitraumTyp {
    private int monat;
    private int jahr;

    public AbrechnungsZeitraumTyp(int monat, int jahr)
    {
        if(IstGueltigerMonat(monat) && IstGueltigesJahr(jahr))
        {
            this.monat = monat;
            this.jahr = jahr;
        }
        else
        {
            throw new IllegalArgumentException(String.format("?/? ist kein gültiger Abrechnungszeitraum", monat, jahr));
        }
    }

    public int getMonat(){
        return monat;
    }

    public int getJahr(){
        return jahr;
    }

    public static boolean IstGueltigerMonat(int monat)
    {
        return monat > 0 && monat <= 12;
    }

    public static boolean IstGueltigesJahr(int jahr)
    {
        return jahr >= LocalDateTime.MIN.getYear() && jahr <= LocalDateTime.MAX.getYear();
    }

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof AbrechnungsZeitraumTyp)) return false;

        AbrechnungsZeitraumTyp az = (AbrechnungsZeitraumTyp) other;

        return getMonat() == az.getMonat() &&
                getJahr() == az.getJahr();
    }
}
