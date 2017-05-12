package anwendungskern.mitarbeiter_komponente.data_accesslayer;

/**
 * Created by Heinrich on 02.02.2016.
 */
public class MitarbeiterRepo {
    public Rezeptionist saveRezeptionist(Rezeptionist r) {
        r.saveIt();
        return r;
    }

    public Trainer saveTrainer(Trainer t) {
        t.saveIt();
        return t;
    }

    public Rezeptionist findRezeptionistById(int id) {
        return Rezeptionist.findById(id);
    }

    public Trainer findTrainerById(int id) {
        return Trainer.findById(id);
    }

    public Rezeptionist updateRezeptionist(Rezeptionist r) {
        r.saveIt();
        return r;
    }

    public Trainer updateTrainer(Trainer t) {
        t.saveIt();
        return t;
    }

    public void deleteRezeptionist(Rezeptionist r) {
        r.delete();
    }

    public void deleteTrainer(Trainer t) {
        t.delete();
    }
}
