package anwendungskern.kurs_komponente.data_accesslayer;

import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import common.Check;

import java.util.List;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class KursRepo {
    public Kurs saveKurs(Kurs kurs) {
        kurs.saveIt();

        return kurs;
    }

    public Kurs findKursById(int id) {
        return Kurs.findById(id);
    }

    public List<Kurs> getAlleKurse() {
        return Kurs.findAll();
    }

    public List<Kurs> getKurseByVeranstaltungszeit(int monat, int jahr) {
        return Kurs.where("year(veranstaltungszeit_startzeitpunkt) = ? and month(veranstaltungszeit_startzeitpunkt) = ?", jahr, monat);
    }

    public Kurs updateKurs(Kurs kurs) {
        kurs.saveIt();
        return kurs;
    }

    public void deleteKurs(Kurs kurs) {
        kurs.deleteCascadeShallow();
    }
}
