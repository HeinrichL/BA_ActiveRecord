package test_rechnungskomponente;

import anwendungskern.kunden_komponente.accesslayer.IKundenServices;
import anwendungskern.kunden_komponente.accesslayer.IKundenServicesFuerKurse;
import anwendungskern.kunden_komponente.accesslayer.KundenkomponenteFacade;
import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kunden_komponente.data_accesslayer.Kundenstatus;
import anwendungskern.kunden_komponente.datatypes.AdressTyp;
import anwendungskern.kunden_komponente.datatypes.EmailTyp;
import anwendungskern.kurs_komponente.accesslayer.IKursServices;
import anwendungskern.kurs_komponente.accesslayer.IKursServicesFuerRechnungen;
import anwendungskern.kurs_komponente.accesslayer.KurskomponenteFacade;
import anwendungskern.kurs_komponente.accesslayer.exceptions.KursUeberfuelltException;
import anwendungskern.kurs_komponente.data_accesslayer.Kurs;
import anwendungskern.kurs_komponente.data_accesslayer.Kursstatus;
import anwendungskern.kurs_komponente.datatypes.VeranstaltungszeitTyp;
import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServices;
import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServicesFuerKunden;
import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServicesFuerKurse;
import anwendungskern.mitarbeiter_komponente.accesslayer.MitarbeiterkomponenteFacade;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Trainer;
import anwendungskern.rechnung_komponente.accesslayer.IRechnungsServices;
import anwendungskern.rechnung_komponente.accesslayer.RechnungskomponenteFacade;
import anwendungskern.rechnung_komponente.data_accesslayer.Rechnung;
import anwendungskern.rechnung_komponente.data_accesslayer.Rechnungsposition;
import anwendungskern.rechnung_komponente.datatypes.AbrechnungsZeitraumTyp;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.*;
import persistenceservice.ActiveJDBCHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class UnittestRechnung {

    private static IKundenServices kundenServices;
    private static IMitarbeiterServices mitarbeiterServices;
    private static IKursServices kursServices;
    private static IRechnungsServices rechnungsServices;

    private static Rezeptionist r;
    private static Trainer t;

    private static Kunde kunde1;
    private static Kunde kunde2;

    private static Kurs kurs1;
    private static Kurs kurs2;

    @BeforeClass
    public static void setUpClass(){
        ActiveJDBCHelper.OpenDatabaseConnection();
        mitarbeiterServices = new MitarbeiterkomponenteFacade();
        kundenServices = new KundenkomponenteFacade((IMitarbeiterServicesFuerKunden) mitarbeiterServices);
        kursServices = new KurskomponenteFacade((IKundenServicesFuerKurse) kundenServices, (IMitarbeiterServicesFuerKurse) mitarbeiterServices);
        rechnungsServices = new RechnungskomponenteFacade((IKursServicesFuerRechnungen) kursServices);

        t = new Trainer("Guter", "Trainer");
        r = new Rezeptionist("Guter", "Rezeptionist");

        mitarbeiterServices.CreateRezeptionist(r);
        mitarbeiterServices.CreateTrainer(t);

        kunde1 = new Kunde("Klaus", "Müller", new AdressTyp("Berliner Tor", "7", "22091", "Hamburg"), new EmailTyp("bla@test.de"), "123456", new DateTime(1995,02,01,0,0), Kundenstatus.Basic);
        kunde2 = new Kunde("Heinz", "Schmidt", new AdressTyp("Berliner Tor", "7", "22091", "Hamburg"), new EmailTyp("bla2@test.de"), "654321", new DateTime(1995, 01, 01,0,0), Kundenstatus.Premium);

        kundenServices.CreateKunde(kunde1, r.getID());
        kundenServices.CreateKunde(kunde2, r.getID());
        DateTime now = DateTime.now();
        kurs1 = new Kurs("Kurs1", "Beschreibung 1", 3, new VeranstaltungszeitTyp(now, now.plusHours(2)), Kursstatus.Vorbei);
        kurs2 = new Kurs("Kurs2", "Beschreibung 2", 3, new VeranstaltungszeitTyp(now, now.plusHours(2)), Kursstatus.Vorbei);

        kursServices.CreateKurs(kurs1, r.getID(), t.getID());
        kursServices.CreateKurs(kurs2, r.getID(), t.getID());
        try {
            kursServices.BucheKurs(kunde1.getID(), kurs1);
            kursServices.BucheKurs(kunde1.getID(), kurs2);
            kursServices.BucheKurs(kunde2.getID(), kurs2);
        }
        catch (KursUeberfuelltException e){
            Assert.fail();
        }
    }
    @AfterClass
    public static void Clean() {
        kurs1.deleteCascadeShallow();
        kurs2.deleteCascadeShallow();
        kunde1.delete();
        kunde2.delete();
        r.delete();
        t.delete();
        ActiveJDBCHelper.CloseDatabaseConnection();
    }

    @After
    public void After(){
        for(Rechnung r : rechnungsServices.GetAlleRechnungen()){
            r.deleteCascadeShallow();
        }
    }

    @Test
    public void TestMethodErstelleRechnungen()
    {
        DateTime now = DateTime.now();
        int monat = now.getMonthOfYear();
        int jahr = now.getYear();
        List<Rechnung> rechnungen = rechnungsServices.ErstelleRechnungen(new AbrechnungsZeitraumTyp(monat, jahr));

        //Rechnung für Kunde1
        Rechnungsposition rpos = new Rechnungsposition(kurs1);
        rpos.setID(rechnungen.get(0).getRechnungspositionen().get(0).getID());
        Rechnungsposition rPos2 = new Rechnungsposition(kurs2);
        rPos2.setID(rechnungen.get(0).getRechnungspositionen().get(1).getID());
        List<Rechnungsposition> rpositions = new ArrayList<Rechnungsposition>();
        rpositions.add(rpos);
        rpositions.add(rPos2);
        Rechnung r1 = new Rechnung(kunde1,new AbrechnungsZeitraumTyp(monat, jahr), rpositions);
        r1.setId(rechnungen.get(0).getID());

        //Rechnung für Kunde2
        Rechnungsposition rPos3 = new Rechnungsposition(kurs2);
        rPos3.setID(rechnungen.get(1).getRechnungspositionen().get(0).getID());

        List<Rechnungsposition> rpositions2 = new ArrayList<Rechnungsposition>();
        rpositions2.add(rPos3);

        Rechnung r2 = new Rechnung(kunde2, new AbrechnungsZeitraumTyp(monat, jahr),rpositions);
        r2.setId(rechnungen.get(1).getID());
        List<Rechnung> expected = new ArrayList<Rechnung>();
        expected.add(r1);
        expected.add(r2);
        assertThat(rechnungen, is(expected));
        //Assert.assertEquals(expected, rechnungen);
        Assert.assertEquals(2, rechnungen.size());
        final int[] rechnungspositionen = {0};
        rechnungen.forEach(r -> rechnungspositionen[0] += r.getRechnungspositionen().size());
        Assert.assertEquals(3, rechnungspositionen[0]);
    }

    @Test
    public void TestFindByIdSuccess()
    {
        DateTime now = DateTime.now();
        int monat = now.getMonthOfYear();
        int jahr = now.getYear();
        List<Rechnung> rechnungen = rechnungsServices.ErstelleRechnungen(new AbrechnungsZeitraumTyp(monat, jahr));

        Rechnung re = rechnungsServices.FindRechnungById(rechnungen.get(0).getID());

        assertThat(re, is(rechnungen.get(0)));
    }

    @Test
    public void TestGetAlleRechnungen()
    {
        DateTime now = DateTime.now();
        int monat = now.getMonthOfYear();
        int jahr = now.getYear();
        List<Rechnung> rechnungen = rechnungsServices.ErstelleRechnungen(new AbrechnungsZeitraumTyp(monat, jahr));

        List<Rechnung> re = rechnungsServices.GetAlleRechnungen();

        assertThat(re, is(rechnungen));
    }

    @Test
    public void TestGetRechnungenByAbrechnungszeitraum()
    {
        DateTime now = DateTime.now();
        int monat = now.getMonthOfYear();
        int jahr = now.getYear();
        kurs2.setVeranstaltungszeitpunkt(new VeranstaltungszeitTyp(now.minusMonths(4), now.minusMonths(4).plusHours(2)));
        kursServices.UpdateKurs(kurs2);

        List<Rechnung> rechnungen = rechnungsServices.ErstelleRechnungen(new AbrechnungsZeitraumTyp(monat, jahr));
        List<Rechnung> res = rechnungsServices.GetRechnungByAbrechnungsZeitraum(new AbrechnungsZeitraumTyp(monat, jahr));

        //Nur 1 Kurs wird abgerechnet
        Assert.assertTrue(rechnungen.size() == 1);
        assertThat(res, is(rechnungen));

        kurs2.setVeranstaltungszeitpunkt(new VeranstaltungszeitTyp(now, now.plusHours(2)));
        kursServices.UpdateKurs(kurs2);
    }
}
