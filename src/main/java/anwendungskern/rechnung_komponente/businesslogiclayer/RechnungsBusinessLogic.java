package anwendungskern.rechnung_komponente.businesslogiclayer;

import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kurs_komponente.accesslayer.IKursServicesFuerRechnungen;
import anwendungskern.kurs_komponente.data_accesslayer.Kurs;
import anwendungskern.rechnung_komponente.data_accesslayer.Rechnung;
import anwendungskern.rechnung_komponente.data_accesslayer.RechnungsRepo;
import anwendungskern.rechnung_komponente.data_accesslayer.Rechnungsposition;
import anwendungskern.rechnung_komponente.datatypes.AbrechnungsZeitraumTyp;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class RechnungsBusinessLogic {
    private IKursServicesFuerRechnungen ks;
    private RechnungsRepo repo;

    public RechnungsBusinessLogic(RechnungsRepo repo, IKursServicesFuerRechnungen ks)
    {
        this.repo = repo;
        this.ks = ks;
    }

    public List<Rechnung> ErstelleRechnungen(AbrechnungsZeitraumTyp abrechnungsZeitraum)
    {
        int monat = abrechnungsZeitraum.getMonat();
        int year = abrechnungsZeitraum.getJahr();
        List<Rechnung> rechnungen = new ArrayList<Rechnung>();
        Map<Kunde, List<Kurs>> kundenKurse = new HashMap<Kunde, List<Kurs>>();
        List<Kurs> kurse = ks.GetKurseByVeranstaltungszeit(monat, year);
        for(Kurs kurs : kurse)
        {
            for (Kunde teilnehmer : kurs.getTeilnehmer())
            {
                if (!kundenKurse.containsKey(teilnehmer))
                {
                    List<Kurs> listKurse = new ArrayList<Kurs>();
                    listKurse.add(kurs);
                    kundenKurse.put(teilnehmer, listKurse);
                }
                else
                {
                    kundenKurse.get(teilnehmer).add(kurs);
                }
            }
        }

        for (Map.Entry<Kunde, List<Kurs>> pair : kundenKurse.entrySet())
        {
            List<Rechnungsposition> positionen = new ArrayList<Rechnungsposition>();

            for(Kurs kurs : pair.getValue())
            {
                positionen.add(new Rechnungsposition(kurs));
            }

            Rechnung r = new Rechnung(pair.getKey(), new AbrechnungsZeitraumTyp(monat, year), positionen);

            rechnungen.add(r);
        }
        return repo.SaveAll(rechnungen);
    }
}
