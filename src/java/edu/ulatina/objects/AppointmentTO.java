package edu.ulatina.objects;

import java.sql.Timestamp;

/**
 *
 * @author Nwitlyck
 */
public class AppointmentTO {
    
    private int id;
    private int idUser;
    private int idCustomer;
    private String idUnit;
    private int idSite;
    private int idAsistant;
    private Timestamp date;
    private String adress;
    private int canton;
    private int province;
    private String description;
    private int state;

    public AppointmentTO() {
    }

    public AppointmentTO(int id, int idUser, int idCustomer, String idUnit, int idSite, int idAsistant, Timestamp date, String adress, int canton, int province, String description, int state) {
        this.id = id;
        this.idUser = idUser;
        this.idCustomer = idCustomer;
        this.idUnit = idUnit;
        this.idSite = idSite;
        this.idAsistant = idAsistant;
        this.date = date;
        this.adress = adress;
        this.canton = canton;
        this.province = province;
        this.description = description;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(String idUnit) {
        this.idUnit = idUnit;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public int getIdAsistant() {
        return idAsistant;
    }

    public void setIdAsistant(int idAsistant) {
        this.idAsistant = idAsistant;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getCanton() {
        return canton;
    }

    public void setCanton(int canton) {
        this.canton = canton;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
}
