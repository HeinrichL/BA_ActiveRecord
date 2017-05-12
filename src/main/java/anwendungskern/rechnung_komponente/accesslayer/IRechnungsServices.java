package anwendungskern.rechnung_komponente.accesslayer;

import anwendungskern.rechnung_komponente.data_accesslayer.Rechnung;
import anwendungskern.rechnung_komponente.datatypes.AbrechnungsZeitraumTyp;

import java.util.List;

/**
 * Created by Heinrich on 02.02.2016.
 */
public interface IRechnungsServices {
    List<Rechnung> ErstelleRechnungen(AbrechnungsZeitraumTyp abrechnungsZeitraum);
    Rechnung FindRechnungById(int id);
    List<Rechnung> GetAlleRechnungen();
    List<Rechnung> GetRechnungByAbrechnungsZeitraum(AbrechnungsZeitraumTyp abrechnungsZeitraum);
}
