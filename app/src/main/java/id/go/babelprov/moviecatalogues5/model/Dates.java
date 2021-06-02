package id.go.babelprov.moviecatalogues5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Dates {

    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    @SerializedName("maximum")
    @Expose
    private String maximum;

    @SerializedName("minimum")
    @Expose
    private String minimum;

    //-------------------------------------------------------------------------------------------
    //   GETTER & SETTER
    //-------------------------------------------------------------------------------------------
    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
}
