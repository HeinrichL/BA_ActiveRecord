package test_mitarbeiterkomponente;

import anwendungskern.mitarbeiter_komponente.accesslayer.IMitarbeiterServices;
import anwendungskern.mitarbeiter_komponente.accesslayer.MitarbeiterkomponenteFacade;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Trainer;
import persistenceservice.ActiveJDBCHelper;
import junit.framework.Assert;
import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Heinrich on 28.11.2015.
 */
public class UnittestMitarbeiter {

    static IMitarbeiterServices ms;
    Rezeptionist r1;
    Trainer t1;

    @BeforeClass
    public static void setUpClass(){
        ActiveJDBCHelper.OpenDatabaseConnection();
        ms = new MitarbeiterkomponenteFacade();
    }

    @AfterClass
    public static void Close(){
        ActiveJDBCHelper.CloseDatabaseConnection();
    }

    @Before
    public void Init(){
        r1 = new Rezeptionist("Hans", "Klaus");
        t1 = new Trainer("Guter", "Trainer");
    }

    @Test
    public void testCreateRezeptionist(){
        r1 = ms.CreateRezeptionist(r1);

        Assert.assertTrue(r1.getID() != 0);

        r1.delete();
    }

    @Test
    public void testCreateTrainer(){
        t1 = ms.CreateTrainer(t1);

        Assert.assertTrue(t1.getID() != 0);

        t1.delete();
    }

    @Test
    public void TestFindRezeptionist()
    {
        ms.CreateRezeptionist(r1);

        Rezeptionist r2 = ms.FindRezeptionistById(r1.getID());
        Assert.assertEquals(r1, r2);
        r1.delete();
    }

    @Test
    public void TestFindTrainer()
    {
        ms.CreateTrainer(t1);

        Trainer t2 = ms.FindTrainerById(t1.getID());
        Assert.assertEquals(t1, t2);

        t1.delete();
    }

    @Test
    public void TestUpdateRezeptionist()
    {
        ms.CreateRezeptionist(r1);

        r1.setNachname("Neuu");
        ms.UpdateRezeptionist(r1);

        Rezeptionist r2 = ms.FindRezeptionistById(r1.getID());
        Assert.assertEquals(r1, r2);
        r1.delete();
    }

    @Test
    public void TestUpdateTrainer()
    {
        ms.CreateTrainer(t1);

        t1.setNachname("Neuu");
        ms.UpdateTrainer(t1);

        Trainer t2 = ms.FindTrainerById(t1.getID());
        Assert.assertEquals(t1, t2);
        t1.delete();

    }

    @Test
    public void TestDeleteRezeptionist()
    {
        ms.CreateRezeptionist(r1);
        int id = r1.getID();
        ms.DeleteRezeptionist(r1);
        Assert.assertEquals(null, ms.FindRezeptionistById(id));
    }

    @Test
    public void TestDeleteTrainer()
    {
        ms.CreateTrainer(t1);
        int id = t1.getID();
        ms.DeleteTrainer(t1);
        Assert.assertEquals(null, ms.FindTrainerById(id));
    }


}
