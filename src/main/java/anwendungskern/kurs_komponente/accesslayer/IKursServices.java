package anwendungskern.kurs_komponente.accesslayer;

import anwendungskern.kurs_komponente.accesslayer.exceptions.KursUeberfuelltException;
import anwendungskern.kurs_komponente.data_accesslayer.Kurs;

import java.util.List;

/**
 * Created by Heinrich on 03.01.2016.
 */
public interface IKursServices {
    Kurs CreateKurs(Kurs kurs, int idRezeptionist, int idTrainer);
    Kurs FindKursById(int id);
    List<Kurs> GetAlleKurse();
    List<Kurs> GetKurseByVeranstaltungszeit(int monat, int jahr);
    Kurs UpdateKurs(Kurs kurs);
    void DeleteKurs(Kurs kurs);

    void BucheKurs(int idKunde, Kurs kurs) throws KursUeberfuelltException;
    void BucheKurs(List<Integer> idKunden, Kurs kurs) throws KursUeberfuelltException;
    void BucheKundenAufAnderenKursUm(int idKunde, Kurs kursVon, Kurs kursNach) throws KursUeberfuelltException;
    void BucheKundenAufAnderenKursUm(List<Integer> idKunden, Kurs kursVon, Kurs kursNach) throws KursUeberfuelltException;
}
