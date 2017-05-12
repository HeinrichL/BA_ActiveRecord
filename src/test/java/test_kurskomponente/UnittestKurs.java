package test_kurskomponente;

import anwendungskern.kunden_komponente.accesslayer.IKundenServices;
import anwendungskern.kunden_komponente.accesslayer.IKundenServicesFuerKurse;
import anwendungskern.kunden_komponente.accesslayer.KundenkomponenteFacade;
import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kunden_komponente.data_accesslayer.Kundenstatus;
import anwendungskern.kunden_komponente.datatypes.AdressTyp;
import anwendungskern.kunden_komponente.datatypes.EmailTyp;
import anwendungskern.kurs_komponente.accesslayer.IKursServices;
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
import org.joda.time.DateTime;
import persistenceservice.ActiveJDBCHelper;
import junit.framework.Assert;
import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Heinrich on 03.01.2016.
 */
public class UnittestKurs {

    private static IKursServices kursServices;
    private static IMitarbeiterServices ms;
    private static IKundenServices kundenServices;

    private static Kurs k1;
    private static Kurs k2;
    private static Rezeptionist r1;
    private static Trainer t1;

    private static Kunde ku1;
    private static Kunde ku2;

    @BeforeClass
    public static void Init() {
        ActiveJDBCHelper.OpenDatabaseConnection();
        ms = new MitarbeiterkomponenteFacade();
        kundenServices = new KundenkomponenteFacade((IMitarbeiterServicesFuerKunden) ms);
        kursServices = new KurskomponenteFacade((IKundenServicesFuerKurse) kundenServices, (IMitarbeiterServicesFuerKurse) ms);

        t1 = new Trainer("Guter", "Trainer");
        r1 = new Rezeptionist("Guter", "Rezeptionist");

        ms.CreateRezeptionist(r1);
        ms.CreateTrainer(t1);

        ku1 = new Kunde("Klaus", "Müller", new AdressTyp("Berliner Tor", "7", "22091", "Hamburg"), new EmailTyp("bla@test.de"), "123456", new DateTime(1990,05,04,0,0), Kundenstatus.Basic);
        ku2 = new Kunde("Heinz", "Schmidt", new AdressTyp("Berliner Tor", "7", "22091", "Hamburg"), new EmailTyp("bla2@test.de"), "654321", new DateTime(1995, 01, 01,0,0), Kundenstatus.Premium);

        kundenServices.CreateKunde(ku1, r1.getID());
        kundenServices.CreateKunde(ku2, r1.getID());
    }

    @AfterClass
    public static void Clean() {
        ku1.delete();
        ku2.delete();
        r1.delete();
        t1.delete();
        ActiveJDBCHelper.CloseDatabaseConnection();
    }

    @Before
    public void Before() {
        DateTime now = DateTime.now();
        k1 = new Kurs("Cooler Kurs", "Testbeschreibung", 3, new VeranstaltungszeitTyp(now, now.plusHours(2)), Kursstatus.Geplant);
        k2 = new Kurs("Anderer cooler Kurs", "Testbeschreibung", 1, new VeranstaltungszeitTyp(now, now.plusHours(2)), Kursstatus.Geplant);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date dt = now.toDate();
    }

    public void deleteKurs(Kurs k) {
        for (Kunde ku : k.getTeilnehmer()) {
            k.remove(ku);
        }
        k.delete();
    }

    @Test
    public void TestMethodCreateKurs() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());

        Assert.assertTrue(k1.getID() != 0);

        k1.delete();
    }

    @Test
    public void TestFindKurs() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());

        Kurs kurs2 = kursServices.FindKursById(k1.getID());

        Assert.assertEquals(k1, kurs2);

        k1.delete();
    }

    @Test
    public void TestGetAlleKurse() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());
        kursServices.CreateKurs(k2, r1.getID(), t1.getID());

        List<Kurs> kurse = new ArrayList<Kurs>();
        kurse.add(k1);
        kurse.add(k2);

        List<Kurs> alleKurse = kursServices.GetAlleKurse();
        assertThat(kurse, is(alleKurse));

        k1.delete();
        k2.delete();
    }

    @Test
    public void TestUpdateKurs() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());

        k1.setBeschreibung("Neue Beschreibung");
        kursServices.UpdateKurs(k1);

        Kurs kurs2 = kursServices.FindKursById(k1.getID());
        Assert.assertEquals(k1, kurs2);

        k1.delete();
    }

    @Test
    public void TestDeleteKurs() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());

        int id = k1.getID();
        kursServices.DeleteKurs(k1);

        Assert.assertEquals(null, kursServices.FindKursById(id));
    }

    @Test
    public void TestBucheKursFuerEinenKundenSuccess() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());
        try {
            kursServices.BucheKurs(ku1.getID(), k1);
        } catch (KursUeberfuelltException e) {
            Assert.fail();
        }

        Assert.assertTrue(k1.getTeilnehmer().contains(ku1));
        Assert.assertTrue(k1.HatFreiePlaetze());

        deleteKurs(k1);
    }

    @Test
    public void TestBucheKursFuerEinenKundenKursVoll() {
        kursServices.CreateKurs(k2, r1.getID(), t1.getID());

        try {
            kursServices.BucheKurs(ku1.getID(), k2);
        } catch (KursUeberfuelltException e) {
            Assert.fail();
        }

        try {
            kursServices.BucheKurs(ku2.getID(), k2);
            Assert.fail("Kurs sollte hier voll sein");
        } catch (KursUeberfuelltException e) {
            Assert.assertFalse(k2.HatFreiePlaetze());
        } finally {
            deleteKurs(k2);
        }
    }

    @Test
    public void TestBucheKursFuerZweiKundenSuccess() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());
        List<Integer> kunden = new ArrayList<Integer>(Arrays.asList(ku1.getID(), ku2.getID()));
        try {
            kursServices.BucheKurs(kunden, k1);
        } catch (KursUeberfuelltException e) {
            Assert.fail();
        }

        Assert.assertTrue(k1.getTeilnehmer().contains(ku1));
        Assert.assertTrue(k1.getTeilnehmer().contains(ku2));
        Assert.assertTrue(k1.HatFreiePlaetze());

        deleteKurs(k1);
    }

    @Test
    public void TestBucheKursFuerZweiKundenNurEinPlatzFrei() {
        kursServices.CreateKurs(k2, r1.getID(), t1.getID());
        List<Integer> kunden = new ArrayList<Integer>(Arrays.asList(ku1.getID(), ku2.getID()));

        try {
            kursServices.BucheKurs(kunden, k2);
            Assert.fail("Kurs sollte hier nicht genug Plätze haben");
        } catch (KursUeberfuelltException e) {
            Assert.assertTrue(k2.HatFreiePlaetze());
        } finally {
            deleteKurs(k2);
        }
    }

    @Test
    public void TestBucheEinenKundenAufAnderenKursUmSuccess() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());
        kursServices.CreateKurs(k2, r1.getID(), t1.getID());

        try {
            kursServices.BucheKurs(ku1.getID(), k1);
            kursServices.BucheKundenAufAnderenKursUm(ku1.getID(), k1, k2);
        } catch (KursUeberfuelltException e) {
            Assert.fail();
        }

        Assert.assertFalse(k1.getTeilnehmer().contains(ku1));
        Assert.assertTrue(k2.getTeilnehmer().contains(ku1));

        deleteKurs(k1);
        deleteKurs(k2);
    }

    @Test
    public void TestBucheEinenKundenAufAnderenKursUmKursVoll() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());
        kursServices.CreateKurs(k2, r1.getID(), t1.getID());

        try {
            kursServices.BucheKurs(ku2.getID(), k1);
            kursServices.BucheKurs(ku1.getID(), k2);
        } catch (KursUeberfuelltException e) {
            Assert.fail();
        }

        try {
            kursServices.BucheKundenAufAnderenKursUm(ku2.getID(), k1, k2);
            Assert.fail("Zielkurs sollte hier voll sein");
        } catch (KursUeberfuelltException e) {
            Assert.assertFalse(k2.HatFreiePlaetze());
        } finally {
            deleteKurs(k1);
            deleteKurs(k2);
        }

    }

    @Test
    public void TestBucheZweiKundenAufAnderenKursUmSuccess() {
        k2.setMaximaleTeilnehmeranzahl(2);

        kursServices.CreateKurs(k1, r1.getID(), t1.getID());
        kursServices.CreateKurs(k2, r1.getID(), t1.getID());

        List<Integer> kunden = new ArrayList<Integer>(Arrays.asList(ku1.getID(), ku2.getID()));
        try {
            kursServices.BucheKurs(kunden, k1);
            kursServices.BucheKundenAufAnderenKursUm(kunden, k1, k2);
        } catch (KursUeberfuelltException e) {
            Assert.fail();
        }

        Assert.assertFalse(k1.getTeilnehmer().contains(ku1));
        Assert.assertFalse(k1.getTeilnehmer().contains(ku2));
        Assert.assertTrue(k2.getTeilnehmer().contains(ku1));
        Assert.assertTrue(k2.getTeilnehmer().contains(ku1));

        kursServices.DeleteKurs(k1);
        kursServices.DeleteKurs(k2);
    }

    @Test
    public void TestBucheZweiKundenAufAnderenKursUmNurEinPlatzFrei() {
        kursServices.CreateKurs(k1, r1.getID(), t1.getID());
        kursServices.CreateKurs(k2, r1.getID(), t1.getID());

        List<Integer> kunden = new ArrayList<Integer>(Arrays.asList(ku1.getID(), ku2.getID()));

        try {
            kursServices.BucheKurs(kunden, k1);
        } catch (KursUeberfuelltException e) {
            Assert.fail();
        }

        try {
            kursServices.BucheKundenAufAnderenKursUm(kunden, k1, k2);
            Assert.fail("Zielkurs sollte hier nicht genug Platz haben");
        } catch (KursUeberfuelltException e) {
            Assert.assertTrue(k1.HatFreiePlaetze());
            Assert.assertTrue(k2.HatFreiePlaetze());
        } finally {
            deleteKurs(k1);
            deleteKurs(k2);
        }
    }
}
