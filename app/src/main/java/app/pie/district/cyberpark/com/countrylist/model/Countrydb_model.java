package app.pie.district.cyberpark.com.countrylist.model;

/**
 * Created by libin on 22/12/2017.
 */
public class Countrydb_model {

    private int id;
    private String name;


    public Countrydb_model() {
    }

    public Countrydb_model(String name) {
        this.name = name;

    }

    public Countrydb_model(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
