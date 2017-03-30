package mordbad.master.dss;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by haakon on 02/11/16.
 */

@Table(name="PersonModel")
public class PersonModel extends Model {


    @Column(name = "formal")
    public int formal;

    @Column(name = "land")
    public int land;


    @Column(name = "kjønn")
    public int kjønn;

    @Column(name = "alder")
    public int alder;
    @Column(name = "utdanning")
    public int utdanning;
    @Column(name = "status")
    public int status;



    @Column(name = "spise_lokal")
    public int spise_lokal;
    @Column(name = "shoppe")
    public int shoppe;
    @Column(name = "historisk")
    public int historisk;
    @Column(name = "slappe_av")
    public int slappe_av;
    @Column(name = "teater")
    public int teater;
    @Column(name = "kunst")
    public int kunst;
    @Column(name = "moro")
    public int moro;
    @Column(name = "lokal_historie")
    public int lokal_historie;
    @Column(name = "sightseeing")
    public int sightseeing;
    @Column(name = "lokal_kultur")
    public int lokal_kultur;
    @Column(name = "restaurant")
    public int restaurant;

    public PersonModel(String[] data) {



    }


    public enum FORMAL {
        BESØKE_VENNER(1);
        //,2,0
        private final int value;

        FORMAL(final int newValue){
            value = newValue;

        }

        public int getValue(){return value;}
    }

        //TODO DODO: I want an enumMap instead for human-readable stuff. But it needs to come later.
    public PersonModel(int formal, int land, int kjønn, int alder, int utdanning, int status, int spise_lokal, int shoppe, int historisk, int slappe_av, int teater, int kunst, int moro, int lokal_historie, int sightseeing, int lokal_kultur, int restaurant) {
        this.formal = formal;
        this.land = land;
        this.kjønn = kjønn;
        this.alder = alder;
        this.utdanning = utdanning;
        this.status = status;
        this.spise_lokal = spise_lokal;
        this.shoppe = shoppe;
        this.historisk = historisk;
        this.slappe_av = slappe_av;
        this.teater = teater;
        this.kunst = kunst;
        this.moro = moro;
        this.lokal_historie = lokal_historie;
        this.sightseeing = sightseeing;
        this.lokal_kultur = lokal_kultur;
        this.restaurant = restaurant;
    }
}
