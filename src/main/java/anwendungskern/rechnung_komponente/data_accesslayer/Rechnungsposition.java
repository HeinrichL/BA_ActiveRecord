package anwendungskern.rechnung_komponente.data_accesslayer;

import anwendungskern.kurs_komponente.data_accesslayer.Kurs;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by Heinrich on 02.02.2016.
 */
@Table("rechnungspositionen")
@IdName("rp_id")
@BelongsToParents({
        @BelongsTo(parent = Kurs.class, foreignKeyName = "kurs_id"),
        @BelongsTo(parent = Rechnung.class, foreignKeyName = "rechnung_id")
})
@BelongsTo(parent = Kurs.class, foreignKeyName = "kurs_id")
public class Rechnungsposition extends Model {

    private final String kosten = "kosten";

    public Rechnungsposition(Kurs kurs){
        setKosten(0.0);
        setKurs(kurs);
    }

    public Rechnungsposition(){}

    public int getID() {
        return getId() == null ? 0 : getLongId().intValue();
    }
    public void setID(int id){
        setId(id);
    }

    public double getKosten(){
        return getDouble(kosten);
    }

    public void setKosten(double kosten){
        setDouble(this.kosten, kosten);
    }

    public Kurs getKurs(){
        return parent(Kurs.class);
    }
    public void setKurs(Kurs k){
        setParent(k);
    }

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof Rechnungsposition)) return false;

        Rechnungsposition r = (Rechnungsposition) other;

        return this.getID() == (r.getID()) &&
                getKosten() == r.getKosten() &&
                getKurs().equals(r.getKurs());
    }
}
