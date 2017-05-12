package anwendungskern.mitarbeiter_komponente.accesslayer;

import anwendungskern.mitarbeiter_komponente.data_accesslayer.Rezeptionist;
import anwendungskern.mitarbeiter_komponente.data_accesslayer.Trainer;

import java.math.BigDecimal;

/**
 * Created by Heinrich on 28.11.2015.
 */
public interface IMitarbeiterServicesFuerKunden {
    Rezeptionist FindRezeptionistById(int id);
    Trainer FindTrainerById(int id);
}
