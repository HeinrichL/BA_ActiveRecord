package anwendungskern.kunden_komponente.data_accesslayer;

import org.javalite.common.Util;

import java.util.List;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class KundenRepo {

    public Kunde saveKunde(Kunde k){
        k.save();
        return k;
    }

    public Kunde findKundeById(int id){
        return Kunde.findById(id);
    }

    public Kunde updateKunde(Kunde k)
    {
        k.save();
        return k;
    }

    public void deleteKunde(Kunde k)
    {
        k.delete();
    }

    public List<Kunde> findAlleKunden()
    {
        return Kunde.findAll().load();
    }

    public List<Kunde> getKundenByIds(List<Integer> ids)
    {
        return Kunde.where("kundennummer in (" + Util.join(ids, ",") +")");

    }
}
