package edu.ulatina.objects;

import java.io.Serializable;

public class SiteTO implements Serializable{
    
    private int id;
    private String name;
    private int province;
    private int canton;
    private String adress;
    private String phone;
    private int state;

    public SiteTO() {
    }

    public SiteTO(int id, String name, int province, int canton, String adress, String phone, int state) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.canton = canton;
        this.adress = adress;
        this.phone = phone;
        this.state = state;
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

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCanton() {
        return canton;
    }

    public void setCanton(int canton) {
        this.canton = canton;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    
}
