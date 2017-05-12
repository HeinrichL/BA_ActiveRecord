package anwendungskern.kunden_komponente.accesslayer;

import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kunden_komponente.data_accesslayer.Kundenstatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Heinrich on 28.11.2015.
 */
public interface IKundenServices {
    Kunde CreateKunde(Kunde k, int mitarbeiterId);

    Kunde FindKundeById(int id);

    List<Kunde> GetAlleKunden();

    Kunde UpdateKunde(Kunde k);

    void DeleteKunde(Kunde k);

    Kunde SetzeKundenStatus(Kunde k, Kundenstatus status);
}
