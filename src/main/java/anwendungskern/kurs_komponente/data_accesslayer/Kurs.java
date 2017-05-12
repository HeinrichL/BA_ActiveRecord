package anwendungskern.kurs_komponente.data_accesslayer;

import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kurs_komponente.datatypes.VeranstaltungszeitTyp;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Trainer;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.*;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Heinrich on 03.01.2016.
 */

@Table("kurse")
@IdName("id")
@BelongsToParents({
        @BelongsTo(parent = Rezeptionist.class, foreignKeyName = "angelegtVon_id"),
        @BelongsTo(parent = Trainer.class, foreignKeyName = "kursleiter_id")
})
@Many2Many(other = Kunde.class, join = "kunden_kurse", sourceFKName = "kurs_id", targetFKName = "kundennummer")
@Cached
public class Kurs extends Model {
    private final String titel = "titel";
    private final String beschreibung = "beschreibung";
    private final String maxTeilnehmer = "maximaleTeilnehmeranzahl";
    private final String startZeitpunkt = "veranstaltungszeit_startzeitpunkt";
    private final String endZeitpunkt = "veranstaltungszeit_endzeitpunkt";
    private final String kursstatus = "kursstatus";
    private final String angelegtVon = "angelegtVon_id";
    private final String kursleiter = "kursleiter_id";

    public Kurs(String titel, String beschreibung, int maximaleTeilnehmerzahl, VeranstaltungszeitTyp veranstaltungszeit, Kursstatus status){
        setTitel(titel);
        setBeschreibung(beschreibung);
        setMaximaleTeilnehmeranzahl(maximaleTeilnehmerzahl);
        setVeranstaltungszeitpunkt(veranstaltungszeit);
        setKursstatus(status);
    }

    public Kurs(){}

    public int getID(){
        return getId() == null ? 0 : getLongId().intValue();
    }

    public String getTitel() { return getString(titel); }
    public void setTitel(String titel){ setString(this.titel, titel); }

    public String getBeschreibung(){ return getString(beschreibung); }
    public void setBeschreibung(String beschreibung) { setString(this.beschreibung, beschreibung); }

    public int getMaximaleTeilnehmeranzahl() { return getInteger(maxTeilnehmer); }
    public void setMaximaleTeilnehmeranzahl(int anzahl) { setInteger(maxTeilnehmer, anzahl); }

    public VeranstaltungszeitTyp getVeranstaltungszeitpunkt() {
        return new VeranstaltungszeitTyp(new DateTime(getDate(startZeitpunkt)), new DateTime(getDate(endZeitpunkt)));
    }
    public void setVeranstaltungszeitpunkt(VeranstaltungszeitTyp vaz){
        setTimestamp(startZeitpunkt, vaz.getStartZeitpunkt().toDate());
        setTimestamp(endZeitpunkt, vaz.getEndZeitpunkt().toDate());
        //set(startZeitpunkt, vaz.getStartZeitpunkt().toDate(), endZeitpunkt, vaz.getEndZeitpunkt().toDate());
    }

    public Kursstatus getKursstatus() { return Kursstatus.valueOf(getString(kursstatus)); }
    public void setKursstatus(Kursstatus status) { setString(kursstatus, status.name()); }

    public Rezeptionist getAngelegtVon() { return parent(Rezeptionist.class); }
    public void setAngelegtVon(Rezeptionist angelegtVon) {
        //getAttributes();
        setParent(angelegtVon);
    }

    public Trainer getKursleiter() { return parent(Trainer.class); }
    public void setKursleiter(Trainer trainer) {
        //getAttributes();
        setParent(trainer);
    }

    public List<Kunde> getTeilnehmer(){
        return getAll(Kunde.class);
    }
    public void addTeilnehmer(Kunde teilnehmer){
        add(teilnehmer);
    }
    public void removeTeilnehmer(Kunde teilnehmer){
        remove(teilnehmer);
    }

    public boolean HatFreiePlaetze(int anzahl){
        return getMaximaleTeilnehmeranzahl() >= getTeilnehmer().size() + anzahl;
    }

    public boolean HatFreiePlaetze(){
        return HatFreiePlaetze(1);
    }

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof Kurs)) return false;

        Kurs k = (Kurs) other;

        VeranstaltungszeitTyp v1 = getVeranstaltungszeitpunkt();
        VeranstaltungszeitTyp v2 = getVeranstaltungszeitpunkt();

        return this.getID() == (k.getID()) &&
                getTitel().equals(k.getTitel()) &&
                getBeschreibung().equals(k.getBeschreibung()) &&
                getMaximaleTeilnehmeranzahl() == k.getMaximaleTeilnehmeranzahl() &&
                getVeranstaltungszeitpunkt().equals(k.getVeranstaltungszeitpunkt()) &&
                getKursstatus().equals(k.getKursstatus()) &&
                getAngelegtVon().equals(k.getAngelegtVon()) &&
                getKursleiter().equals(k.getKursleiter()) &&
                getTeilnehmer().equals(k.getTeilnehmer());
    }
}
