
package edu.ulatina.objects;

import java.io.Serializable;


public class UnitTO  implements Serializable{
    
    private String plate;
    private String name;
    private int state;

    public UnitTO() {
    }

    public UnitTO(String plate, String name, int state) {
        this.plate = plate;
        this.name = name;
        this.state = state;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    
    
    
}
