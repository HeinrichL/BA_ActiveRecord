package test_kundenkomponente;

import anwendungskern.kunden_komponente.accesslayer.IKundenServices;
import anwendungskern.kunden_komponente.accesslayer.IKundenServicesFuerKurse;
import anwendungskern.kunden_komponente.accesslayer.KundenkomponenteFacade;
import anwendungskern.kunden_komponente.data_accesslayer.Kunde;
import anwendungskern.kunden_komponente.data_accesslayer.Kundenstatus;
import anwendungskern.kunden_komponente.datatypes.AdressTyp;
import anwendungskern.kunden_komponente.datatypes.EmailTyp;
import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServices;
import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServicesFuerKunden;
import anwendungskern.mitarbeiter_komponente.accesslayer.MitarbeiterkomponenteFacade;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
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
import java.util.List;

/**
 * Created by Heinrich on 28.11.2015.
 */
public class UnittestKunde {

    static IKundenServices ks;
    static IMitarbeiterServices ms;

    static Kunde k1;
    static Rezeptionist r1;

    @BeforeClass
    public static void Init(){
        ActiveJDBCHelper.OpenDatabaseConnection();
        ms = new MitarbeiterkomponenteFacade();
        ks = new KundenkomponenteFacade((IMitarbeiterServicesFuerKunden) ms);

        r1 = new Rezeptionist("Anleger", "Rezeptionist");
        ms.CreateRezeptionist(r1);
    }

    @AfterClass
    public static void Close(){
        r1.delete();
        ActiveJDBCHelper.CloseDatabaseConnection();
    }

    @Before
    public void before(){
        k1 = new Kunde("Klaus", "M�ller", new AdressTyp("Berliner Tor", "7", "22111", "Hamburg"), new EmailTyp("test@test.de"), "040555", new DateTime(2000,05,04,0,0), Kundenstatus.Basic);
    }

    @Test
    public void TestCreate(){
        k1 = ks.CreateKunde(k1, r1.getID());
        Assert.assertTrue(k1.getID() != 0);
        Assert.assertEquals(k1.getAngelegtVon(), r1);

        k1.delete();
    }

    @Test
    public void TestFindKunde()
    {
        ks.CreateKunde(k1, r1.getID());

        Kunde k2 = ks.FindKundeById(k1.getID());
        Assert.assertEquals(k1, k2);

        k1.delete();
    }

    @Test
    public void TestGetKundenByIds()
    {
        ks.CreateKunde(k1, r1.getID());
        Kunde k2 = new Kunde("Klaus", "M�ller", new AdressTyp("Berliner Tor", "7", "22111", "Hamburg"), new EmailTyp("test@test.de"), "040555", new DateTime(2000,05,04,0,0), Kundenstatus.Basic);
        ks.CreateKunde(k2, r1.getID());
        List<Kunde> expected = new ArrayList<Kunde>(Arrays.asList(k1, k2));
        List<Kunde> kunden = ((IKundenServicesFuerKurse) ks).GetKundenByIds(Arrays.asList(k1.getID(), k2.getID()));
        Assert.assertEquals(expected, kunden);

        k1.delete();
        k2.delete();
    }

    @Test
    public void TestUpdate()
    {
        ks.CreateKunde(k1, r1.getID());

        k1.setNachname("Neuer Nachname");
        ks.UpdateKunde(k1);

        Kunde k2 = ks.FindKundeById(k1.getID());
        Assert.assertEquals(k1, k2);
        k1.delete();
    }

    @Test
    public void TestDelete()
    {
        ks.CreateKunde(k1, r1.getID());
        int id = k1.getID();
        ks.DeleteKunde(k1);
        Assert.assertEquals(null, ks.FindKundeById(id));
    }

    @Test
    public void TestSetzeKundenstatus()
    {
        ks.CreateKunde(k1, r1.getID());
        ks.SetzeKundenStatus(k1, Kundenstatus.Premium);
        Assert.assertTrue(k1.getID() != 0);

        k1.delete();
    }
}
