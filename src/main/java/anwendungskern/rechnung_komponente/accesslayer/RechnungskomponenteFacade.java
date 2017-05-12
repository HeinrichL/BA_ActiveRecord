package anwendungskern.rechnung_komponente.accesslayer;

import anwendungskern.kurs_komponente.accesslayer.IKursServicesFuerRechnungen;
import anwendungskern.rechnung_komponente.businesslogiclayer.RechnungsBusinessLogic;
import anwendungskern.rechnung_komponente.data_accesslayer.Rechnung;
import anwendungskern.rechnung_komponente.data_accesslayer.RechnungsRepo;
import anwendungskern.rechnung_komponente.datatypes.AbrechnungsZeitraumTyp;

import java.util.List;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class RechnungskomponenteFacade implements IRechnungsServices {

    private RechnungsRepo repo;
    private RechnungsBusinessLogic businessLogic;

    public RechnungskomponenteFacade(IKursServicesFuerRechnungen ks)
    {
        repo = new RechnungsRepo();
        businessLogic = new RechnungsBusinessLogic(repo, ks);
    }

    @Override
    public List<Rechnung> ErstelleRechnungen(AbrechnungsZeitraumTyp abrechnungsZeitraum) {
        return businessLogic.ErstelleRechnungen(abrechnungsZeitraum);
    }

    @Override
    public Rechnung FindRechnungById(int id) {
        return repo.FindRechnungById(id);
    }

    @Override
    public List<Rechnung> GetAlleRechnungen() {
        return repo.GetAlleRechnungen();
    }

    @Override
    public List<Rechnung> GetRechnungByAbrechnungsZeitraum(AbrechnungsZeitraumTyp abrechnungsZeitraum) {
        return repo.GetRechnungenByAbrechnungszeitraum(abrechnungsZeitraum);
    }
}
