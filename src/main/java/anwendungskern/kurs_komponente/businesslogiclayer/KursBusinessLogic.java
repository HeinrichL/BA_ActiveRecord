package anwendungskern.kurs_komponente.businesslogiclayer;

import anwendungskern.kunden_komponente.accesslayer.IKundenServicesFuerKurse;
import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kurs_komponente.accesslayer.exceptions.KursUeberfuelltException;
import anwendungskern.kurs_komponente.data_accesslayer.Kurs;
import anwendungskern.kurs_komponente.data_accesslayer.KursRepo;
import persistenceservice.ActiveJDBCHelper;
import org.javalite.activejdbc.Base;

import java.util.List;


// Buche Kunde um

/**
 * Created by Heinrich on 03.01.2016.
 */
public class KursBusinessLogic {
    private IKundenServicesFuerKurse kundenServices;
    private KursRepo repo;

    public KursBusinessLogic(IKundenServicesFuerKurse ks, KursRepo repo) {

        kundenServices = ks;
        this.repo = repo;
    }

    public void BucheKurs(int idKunde, Kurs kurs) throws KursUeberfuelltException {
        ActiveJDBCHelper.openTransaction();
        if (kurs.HatFreiePlaetze()) {
            kurs.addTeilnehmer(kundenServices.FindKundeById(idKunde));
            kurs.saveIt();
            ActiveJDBCHelper.commitTransaction();
        } else {
            ActiveJDBCHelper.rollbackTransaction();
            throw new KursUeberfuelltException("Kurs ist bereits ausgebucht");
        }
    }

    public void BucheKurs(List<Integer> idKunden, Kurs kurs) throws KursUeberfuelltException {
        ActiveJDBCHelper.openTransaction();
        if (kurs.HatFreiePlaetze(idKunden.size())) {
            List<Kunde> kunden = kundenServices.GetKundenByIds(idKunden);
            for (Kunde k : kunden) {
                kurs.addTeilnehmer(k);
            }
            kurs.saveIt();
            ActiveJDBCHelper.commitTransaction();
        } else {
            ActiveJDBCHelper.rollbackTransaction();
            throw new KursUeberfuelltException("Kurs ist bereits ausgebucht");
        }
    }

    public void BucheKundenAufAnderenKursUm(int idKunde, Kurs kursVon, Kurs kursNach) throws KursUeberfuelltException {
        ActiveJDBCHelper.openTransaction();
        if (kursNach.HatFreiePlaetze()) {
            Kunde k = kundenServices.FindKundeById(idKunde);

            kursVon.removeTeilnehmer(k);
            kursNach.addTeilnehmer(k);

            kursVon.saveIt();
            kursNach.saveIt();
            ActiveJDBCHelper.commitTransaction();
        } else {
            ActiveJDBCHelper.rollbackTransaction();
            throw new KursUeberfuelltException("Zielkurs ist bereits ausgebucht");
        }
    }

    public void BucheKundenAufAnderenKursUm(List<Integer> idKunden, Kurs kursVon, Kurs kursNach) throws KursUeberfuelltException {
        ActiveJDBCHelper.openTransaction();
        if (kursNach.HatFreiePlaetze(idKunden.size())) {
            List<Kunde> kunden = kundenServices.GetKundenByIds(idKunden);

            for (Kunde k : kunden) {
                kursVon.removeTeilnehmer(k);
                kursNach.addTeilnehmer(k);
            }

            kursVon.saveIt();
            kursNach.saveIt();
            ActiveJDBCHelper.commitTransaction();
        } else {
            ActiveJDBCHelper.rollbackTransaction();
            throw new KursUeberfuelltException("Zielkurs ist bereits ausgebucht");
        }
    }
}
