package anwendungskern.kunden_komponente.data_accesslayer;

import anwendungskern.kunden_komponente.datatypes.AdressTyp;
import anwendungskern.kunden_komponente.datatypes.EmailTyp;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.*;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.sql.Date;

@Table("kunden")
@IdName("kundennummer")
@BelongsTo(parent = Rezeptionist.class, foreignKeyName = "angelegtVon_id")
@VersionColumn("version")
public class Kunde extends Model{
    private final String vorname = "vorname";
    private final String nachname = "nachname";
    private final String strasse = "adresse_strasse";
    private final String hausnummer = "adresse_hausnummer";
    private final String plz = "adresse_plz";
    private final String ort = "adresse_ort";
    private final String email = "emailadresse_email";
    private final String telefonnummer = "telefonnummer";
    private final String geburtsdatum = "geburtsdatum";
    private final String status = "kundenstatus";

    public Kunde(String vorname, String nachname, AdressTyp adresse, EmailTyp email, String telefon, DateTime geburtsdatum, Kundenstatus status){
        setVorname(vorname) ;
        setNachname(nachname);
        setAdresse(adresse);
        setEmail(email);
        setTelefonnummer(telefon);
        setGeburtsdatum(geburtsdatum);
        setKundenstatus(status);
    }

    public Kunde(){}

    public int getID(){
        return getId() == null ? 0 : getLongId().intValue();
    }

    public String getVorname(){ return getString(vorname); }
    public void setVorname(String vorname){
        setString(this.vorname, vorname);
    }

    public String getNachname() { return getString(nachname); }
    public void setNachname(String nachname) {
        setString(this.nachname, nachname);
    }

    public AdressTyp getAdresse(){
        return new AdressTyp(getString(strasse), getString(hausnummer), getString(plz), getString(ort));
    }
    public void setAdresse(AdressTyp adresse) {
        set(strasse, adresse.strasse, hausnummer, adresse.hausnummer, plz, adresse.plz, ort, adresse.ort);
    }

    public EmailTyp getEmail(){
        return new EmailTyp(getString(email));
    }
    public void setEmail(EmailTyp email) {
        setString(this.email, email.mail);
    }

    public String getTelefonnummer(){ return getString(telefonnummer); }
    public void setTelefonnummer(String telefonnummer) {
        set(this.telefonnummer, telefonnummer);
    }

    public DateTime getGeburtsdatum(){ return new DateTime(getDate(geburtsdatum)); }
    public void setGeburtsdatum(DateTime geburtsdatum) {
        set(this.geburtsdatum, geburtsdatum.toDate());
    }

    public Kundenstatus getKundenstatus(){ return Kundenstatus.valueOf(getString(status)); }
    public void setKundenstatus(Kundenstatus kundenstatus) {
        setString(this.status, kundenstatus.name());
    }

    public Rezeptionist getAngelegtVon() { return parent(Rezeptionist.class); }
    public void setAngelegtVon(Rezeptionist angelegtVon) {
        //getAttributes();
        setParent(angelegtVon);
    }

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof Kunde)) return false;

        Kunde k = (Kunde) other;

        return this.getID() == (k.getID()) &&
                getString(vorname).equals(k.getString(vorname)) &&
                getString(nachname).equals(k.getString(nachname)) &&
                getAdresse().equals(k.getAdresse()) &&
                getEmail().equals(k.getEmail()) &&
                getString(telefonnummer).equals(k.getString(telefonnummer)) &&
                getGeburtsdatum().equals(k.getGeburtsdatum()) &&
                getString(status).equals(k.getString(status)) &&
                parent(Rezeptionist.class).equals(k.parent(Rezeptionist.class));
    }

    @Override
    public int hashCode(){
        int res = 1;
        res = res * getVorname().hashCode() % 7;
        res = res * getNachname().hashCode() % 7;
        return res;
    }
}
