package anwendungskern.kurs_komponente.accesslayer;

import anwendungskern.kurs_komponente.data_accesslayer.Kurs;

import java.util.List;

/**
 * Created by Heinrich on 03.01.2016.
 */
public interface IKursServicesFuerRechnungen {
    Kurs FindKursById(int id);
    List<Kurs> GetKurseByVeranstaltungszeit(int monat, int jahr);
}
