package anwendungskern.kunden_komponente.accesslayer;

import anwendungskern.kunden_komponente.data_accesslayer.Kunde;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Heinrich on 28.11.2015.
 */
public interface IKundenServicesFuerKurse {
    Kunde FindKundeById(int id);

    List<Kunde> GetAlleKunden();
    List<Kunde> GetKundenByIds(List<Integer> ids);
}
