package anwendungskern.kunden_komponente.accesslayer;

import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kunden_komponente.data_accesslayer.KundenRepo;
import anwendungskern.kunden_komponente.data_accesslayer.Kundenstatus;
import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServicesFuerKunden;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import org.javalite.common.Util;

import java.util.List;

/**
 * Created by Heinrich on 28.11.2015.
 */
public class KundenkomponenteFacade implements IKundenServices, IKundenServicesFuerKurse, IKundenServicesFuerRechnungen {
    private final IMitarbeiterServicesFuerKunden ms;
    private final KundenRepo repo;

    public KundenkomponenteFacade(IMitarbeiterServicesFuerKunden ms){

        this.ms = ms;
        repo = new KundenRepo();
    }

    public Kunde CreateKunde(Kunde k, int mitarbeiterId) {
        Rezeptionist r = ms.FindRezeptionistById(mitarbeiterId);
        k.setAngelegtVon(r);
        return repo.saveKunde(k);
    }

    public Kunde FindKundeById(int id) {
        return repo.findKundeById(id);
    }

    public List<Kunde> GetAlleKunden() {
        return repo.findAlleKunden();
    }

    public List<Kunde> GetKundenByIds(List<Integer> ids) {
        return repo.getKundenByIds(ids);
    }

    public Kunde UpdateKunde(Kunde k) {
        return repo.updateKunde(k);
    }

    public void DeleteKunde(Kunde k) {
        repo.deleteKunde(k);
    }

    public Kunde SetzeKundenStatus(Kunde k, Kundenstatus status) {
        k.setKundenstatus(status);
        repo.saveKunde(k);
        return k;
    }
}
