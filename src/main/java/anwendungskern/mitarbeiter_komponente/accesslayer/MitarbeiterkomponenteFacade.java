package anwendungskern.mitarbeiter_komponente.accesslayer;

import anwendungskern.mitarbeiter_komponente.data_accesslayer.MitarbeiterRepo;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Trainer;
import common.Check;

import java.math.BigDecimal;

/**
 * Created by Heinrich on 28.11.2015.
 */
public class MitarbeiterkomponenteFacade implements IMitarbeiterServices, IMitarbeiterServicesFuerKunden, IMitarbeiterServicesFuerKurse {
    private final MitarbeiterRepo repo;

    public MitarbeiterkomponenteFacade(){
        repo = new MitarbeiterRepo();
    }


    public Rezeptionist CreateRezeptionist(Rezeptionist r) {
        Check.Argument(r != null, "Rezeptionist darf nicht null sein");
        Check.Argument(r.getID() == 0, "ID muss 0 sein");

        return repo.saveRezeptionist(r);
    }

    public Trainer CreateTrainer(Trainer t) {
        Check.Argument(t != null, "Trainer darf nicht null sein");
        Check.Argument(t.getID() == 0, "ID muss 0 sein");

        return repo.saveTrainer(t);
    }

    public Rezeptionist FindRezeptionistById(int id) {
        Check.Argument(id > 0, "ID muss groesser als 0 sein, ist aber " + id);

        return repo.findRezeptionistById(id);
    }

    public Trainer FindTrainerById(int id) {
        Check.Argument(id > 0, "ID muss groesser als 0 sein, ist aber " + id);

        return repo.findTrainerById(id);
    }

    public Rezeptionist UpdateRezeptionist(Rezeptionist r) {
        Check.Argument(r != null, "Rezeptionist darf nicht null sein");
        Check.Argument(r.getID() > 0, "ID muss groesser als 0 sein, ist aber " + r.getId());

        return repo.updateRezeptionist(r);
    }

    public Trainer UpdateTrainer(Trainer t) {
        Check.Argument(t != null, "Trainer darf nicht null sein");
        Check.Argument(t.getID() > 0, "ID muss groesser als 0 sein, ist aber " + t.getId());

        return repo.updateTrainer(t);
    }

    public void DeleteRezeptionist(Rezeptionist r) {
        Check.Argument(r != null, "Rezeptionist darf nicht null sein");
        Check.Argument(r.getID() > 0, "ID muss groesser als 0 sein, ist aber " + r.getId());

        repo.deleteRezeptionist(r);
    }

    public void DeleteTrainer(Trainer t) {
        Check.Argument(t != null, "Trainer darf nicht null sein");
        Check.Argument(t.getID() > 0, "ID muss groesser als 0 sein, ist aber " + t.getId());

        repo.deleteTrainer(t);
    }
}
