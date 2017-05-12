package anwendungskern.kurs_komponente.accesslayer;

import anwendungskern.kunden_komponente.accesslayer.IKundenServicesFuerKurse;
import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kurs_komponente.accesslayer.exceptions.KursUeberfuelltException;
import anwendungskern.kurs_komponente.businesslogiclayer.KursBusinessLogic;
import anwendungskern.kurs_komponente.data_accesslayer.Kurs;
import anwendungskern.kurs_komponente.data_accesslayer.KursRepo;
import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServicesFuerKurse;
import common.Check;
import org.javalite.activejdbc.Base;
import persistenceservice.ActiveJDBCHelper;

import java.util.List;

/**
 * Created by Heinrich on 03.01.2016.
 */
public class KurskomponenteFacade implements IKursServices, IKursServicesFuerRechnungen {

    private IMitarbeiterServicesFuerKurse mitarbeiterServices;
    private KursBusinessLogic kursBusinessLogic;
    private KursRepo repo;

    public KurskomponenteFacade(IKundenServicesFuerKurse ks, IMitarbeiterServicesFuerKurse ms) {
        mitarbeiterServices = ms;
        repo = new KursRepo();
        kursBusinessLogic = new KursBusinessLogic(ks, repo);

    }

    public Kurs CreateKurs(Kurs kurs, int idRezeptionist, int idTrainer) {
        Check.Argument(kurs != null, "Kurs darf nicht null sein");
        Check.Argument(kurs.getID() == 0, "KursID muss 0 sein");
        Check.Argument(idRezeptionist != 0, "Rezeptionist darf nicht 0 sein");
        Check.Argument(idTrainer != 0, "Trainer darf nicht 0 sein");

        kurs.setAngelegtVon(mitarbeiterServices.FindRezeptionistById(idRezeptionist));
        kurs.setKursleiter(mitarbeiterServices.FindTrainerById(idTrainer));

        return repo.saveKurs(kurs);
    }

    public Kurs FindKursById(int id) {
        return repo.findKursById(id);
    }

    public List<Kurs> GetAlleKurse() {
        return repo.getAlleKurse();
    }

    public List<Kurs> GetKurseByVeranstaltungszeit(int monat, int jahr) {
        return repo.getKurseByVeranstaltungszeit(monat, jahr);
    }

    public Kurs UpdateKurs(Kurs kurs) {
        return repo.updateKurs(kurs);
    }

    public void DeleteKurs(Kurs kurs) {
        repo.deleteKurs(kurs);
    }

    public void BucheKurs(int idKunde, Kurs kurs) throws KursUeberfuelltException {
        kursBusinessLogic.BucheKurs(idKunde, kurs);
    }

    public void BucheKurs(List<Integer> idKunden, Kurs kurs) throws KursUeberfuelltException {
        kursBusinessLogic.BucheKurs(idKunden, kurs);
    }

    public void BucheKundenAufAnderenKursUm(int idKunde, Kurs kursVon, Kurs kursNach) throws KursUeberfuelltException {
        kursBusinessLogic.BucheKundenAufAnderenKursUm(idKunde, kursVon, kursNach);
    }

    public void BucheKundenAufAnderenKursUm(List<Integer> idKunden, Kurs kursVon, Kurs kursNach) throws KursUeberfuelltException {
        kursBusinessLogic.BucheKundenAufAnderenKursUm(idKunden, kursVon, kursNach);
    }
}
