
package edu.ulatina.controllers;

public class demo {
    private String cedula;
    private String email;
    private String adress;
    private String province;
    private String state;
    private String lastName;
    private String name;
    private String password;
    private int unitNumber;
    private int plate;

    public demo() {
    }

    public demo(String cedula, String email, String adress, String province, String state, String lastName, String name, String password, int unitNumber, int plate) {
        this.cedula = cedula;
        this.email = email;
        this.adress = adress;
        this.province = province;
        this.state = state;
        this.lastName = lastName;
        this.name = name;
        this.password = password;
        this.unitNumber = unitNumber;
        this.plate = plate;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public int getPlate() {
        return plate;
    }

    public void setPlate(int plate) {
        this.plate = plate;
    }
    
    
}
