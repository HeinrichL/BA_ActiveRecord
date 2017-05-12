package anwendungskern.rechnung_komponente.data_accesslayer;

import anwendungskern.rechnung_komponente.datatypes.AbrechnungsZeitraumTyp;

import java.util.List;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class RechnungsRepo {
    public List<Rechnung> SaveAll(List<Rechnung> rechnungen)
    {
        for(Rechnung r : rechnungen){
            r.save();
        }
        return rechnungen;
    }

    public Rechnung FindRechnungById(int id)
    {
        return Rechnung.findById(id);
    }

    public List<Rechnung> GetAlleRechnungen()
    {
        return Rechnung.findAll().load();
    }

    public List<Rechnung> GetRechnungenByAbrechnungszeitraum(AbrechnungsZeitraumTyp zeitraum)
    {
        return Rechnung.where("abrechnungszeitraum_monat = ? and abrechnungszeitraum_jahr = ?", zeitraum.getMonat(), zeitraum.getJahr());
    }
}
