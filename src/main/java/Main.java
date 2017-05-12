import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kunden_komponente.datatypes.EmailTyp;
import anwendungskern.kurs_komponente.data_accesslayer.Kurs;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Trainer;
import com.google.common.base.Stopwatch;
import org.javalite.activejdbc.*;
import org.javalite.activejdbc.cache.CacheManager;
import org.javalite.activejdbc.validation.Validator;
import persistenceservice.ActiveJDBCHelper;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws SQLException {

        ActiveJDBCHelper.OpenDatabaseConnection();
        Kurs.deleteAll();
        Kunde.deleteAll();
        Rezeptionist.deleteAll();
        Trainer.deleteAll();
        boolean mail = true;
        int inserts = 1000;
        for(int c = 0; c < 2; c++){
            ActiveJDBCHelper.openTransaction();
            Stopwatch sw = new Stopwatch();
            sw.start();
            for(int i = 0; i < inserts * c; i++){

                Rezeptionist r = new Rezeptionist();
                r.save();

                Kunde k = new Kunde();
                k.setVorname("Max");
                k.setNachname("Mustermann");
                k.setAngelegtVon(r);
                if(mail) {
                    k.setEmail(new EmailTyp("bla@test.de"));
                    mail = false;
                }
                else{
                    mail = true;
                }
                k.save();
            }
            //kunden
            ActiveJDBCHelper.commitTransaction();
            sw.stop();
            System.out.println(c * inserts + ": " + sw.elapsedMillis());
            sw.reset().start();

            try {
                PreparedStatement ps = Base.startBatch
                        ("insert into kunden (vorname, nachname) values (?,?)");
                for(int i = 0; i < inserts * c; i++){
                    Base.addBatch(ps, "Max","MustermannBatch");
                }
                ActiveJDBCHelper.openTransaction();
                Base.executeBatch(ps);
                ActiveJDBCHelper.commitTransaction();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sw.stop();
            System.out.println(c * inserts + " Batch: " + sw.elapsedMillis());
        }

        int count = Kunde.findAll().size();

        List<Kunde> kunden = Kunde.where("kundennummer > 2000");//.include(Rezeptionist.class);

        List<Rezeptionist> rez = new ArrayList<>();
        kunden.forEach(k -> { k.setVorname("dasfsda"); k.save();});
        Kurs.deleteAll();
        Kunde.deleteAll();
        Rezeptionist.deleteAll();
        ActiveJDBCHelper.CloseDatabaseConnection();
    }
}
