package anwendungskern.mitarbeiter_komponente.accesslayer;

import anwendungskern.mitarbeiter_komponente.data_accesslayer.*;

import java.math.BigDecimal;

/**
 * Created by Heinrich on 28.11.2015.
 */
public interface IMitarbeiterServices {
    Rezeptionist CreateRezeptionist(Rezeptionist r);
    Trainer CreateTrainer(Trainer t);

    Rezeptionist FindRezeptionistById(int id);
    Trainer FindTrainerById(int id);

    Rezeptionist UpdateRezeptionist(Rezeptionist r);
    Trainer UpdateTrainer(Trainer t);

    void DeleteRezeptionist(Rezeptionist r);
    void DeleteTrainer(Trainer t);
}
