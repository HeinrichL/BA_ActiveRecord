package anwendungskern.rechnung_komponente.data_accesslayer;

import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kurs_komponente.data_accesslayer.Kurs;
import anwendungskern.rechnung_komponente.datatypes.AbrechnungsZeitraumTyp;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import java.util.List;

/**
 * Created by Heinrich on 02.02.2016.
 */
@Table("rechnungen")
@IdName("rechnungsnummer")
@BelongsTo(parent = Kunde.class, foreignKeyName = "kunde_id")
public class Rechnung extends Model {
    private final String monat = "abrechnungszeitraum_monat";
    private final String jahr = "abrechnungszeitraum_jahr";
    private final String bezahlt = "bezahlt";
    private List<Rechnungsposition> rechnungspositionen;

    public Rechnung(Kunde k, AbrechnungsZeitraumTyp zeitraum, List<Rechnungsposition> positionen){
        setKunde(k);
        setAbrechnungszeitraum(zeitraum);
        //setRechnungspositionen(positionen);
        rechnungspositionen = positionen;
        setBezahlt(false);
    }

    @Override
    public boolean save(){
        super.save();
        setRechnungspositionen(rechnungspositionen);
        return super.save();
    }

    public Rechnung(){}

    public int getID(){
        return getId() == null ? 0 : getLongId().intValue();
    }

    public Kunde getKunde(){
        return parent(Kunde.class);
    }

    public void setKunde(Kunde k){
        setParent(k);
    }

    public AbrechnungsZeitraumTyp getAbrechnungszeitraum(){
        return new AbrechnungsZeitraumTyp(getInteger(monat), getInteger(jahr));
    }

    public void setAbrechnungszeitraum(AbrechnungsZeitraumTyp zeitraum){
        set(monat, zeitraum.getMonat(), jahr, zeitraum.getJahr());
    }

    public boolean getBezahlt(){
        return getBoolean(bezahlt);
    }

    public void setBezahlt(boolean bezahlt){
        setBoolean(this.bezahlt, bezahlt);
    }

    public List<Rechnungsposition> getRechnungspositionen(){
        return getAll(Rechnungsposition.class);
    }

    public void setRechnungspositionen(List<Rechnungsposition> positionen){
        for(Rechnungsposition r : positionen){
            add(r);
        }
    }

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof Rechnung)) return false;

        Rechnung r = (Rechnung) other;

        return this.getID() == (r.getID()) &&
                getKunde().equals(r.getKunde()) &&
                getAbrechnungszeitraum().equals(r.getAbrechnungszeitraum()) &&
                getBezahlt() == r.getBezahlt() &&
                getRechnungspositionen().equals(r.getRechnungspositionen());
    }
}
