package edu.ulatina.controllers;

import edu.ulatina.objects.*;
import edu.ulatina.services.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;

/**
 *
 * @author Nwitlyck
 */
@Named("appointmentsController")
@ViewScoped
public class AppointmentsController implements Serializable {

    private ServiceAppointmentTO serviceAppointment;
    private ServiceDetails serviceDetails;
    private FacesContext context;
    private Map<String, Integer> mapDetail;

    private AppointmentTO selectedAppointment;
    private List<AppointmentTO> listAppointments;
    private Map<String, Integer> mapVet;
    private Map<String, Integer> mapCustomer;
    private Map<String, String> mapUnit;
    private Map<String, Integer> mapSite;
    private Map<String, Integer> mapAssistan;
    private Map<String, Integer> mapCanton;
    private Map<String, Integer> mapProvince;
    private Map<String, Integer> mapState;
    private Date selectedLowerDate;
    private Date selectedUpperDate;
    private int selectedState;
    private boolean flagInsert;
    private boolean flagUpdate;

    public AppointmentTO getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(AppointmentTO selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
        fillMapCanton();
    }

    public List<AppointmentTO> getListAppointments() {
        return listAppointments;
    }

    public void setListAppointments(List<AppointmentTO> listAppointments) {
        this.listAppointments = listAppointments;
    }

    public Map<String, Integer> getMapVet() {
        return mapVet;
    }

    public void setMapVet(Map<String, Integer> mapVet) {
        this.mapVet = mapVet;
    }

    public Map<String, Integer> getMapCustomer() {
        return mapCustomer;
    }

    public void setMapCustomer(Map<String, Integer> mapCustomer) {
        this.mapCustomer = mapCustomer;
    }

    public Map<String, String> getMapUnit() {
        return mapUnit;
    }

    public void setMapUnit(Map<String, String> mapUnit) {
        this.mapUnit = mapUnit;
    }

    public Map<String, Integer> getMapSite() {
        return mapSite;
    }

    public void setMapSite(Map<String, Integer> mapSite) {
        this.mapSite = mapSite;
    }

    public Map<String, Integer> getMapAssistan() {
        return mapAssistan;
    }

    public void setMapAssistan(Map<String, Integer> mapAssistan) {
        this.mapAssistan = mapAssistan;
    }

    public Map<String, Integer> getMapCanton() {
        return mapCanton;
    }

    public void setMapCanton(Map<String, Integer> mapCanton) {
        this.mapCanton = mapCanton;
    }

    public Map<String, Integer> getMapProvince() {
        return mapProvince;
    }

    public void setMapProvince(Map<String, Integer> mapProvince) {
        this.mapProvince = mapProvince;
    }

    public Map<String, Integer> getMapState() {
        return mapState;
    }

    public void setMapState(Map<String, Integer> mapState) {
        this.mapState = mapState;
    }

    public int getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(int selectedState) {
        this.selectedState = selectedState;
        switch (this.selectedState) {
            case 93:
                this.flagInsert = true;
                this.flagUpdate = false;
                break;

            case 94:
                this.flagInsert = false;
                this.flagUpdate = true;
                break;

            default:
                this.flagInsert = false;
                this.flagUpdate = false;
                break;
        }
    }

    public boolean isFlagInsert() {
        return flagInsert;
    }

    public void setFlagInsert(boolean flagInsert) {
        this.flagInsert = flagInsert;
    }

    public boolean isFlagUpdate() {
        return flagUpdate;
    }

    public void setFlagUpdate(boolean flagUpdate) {
        this.flagUpdate = flagUpdate;
    }

    //special gets / sets
    public java.util.Date getSelectedLowerDate() {
        return new Date(selectedLowerDate.getTime());
    }

    public void setSelectedLowerDate(java.util.Date selectedLowerDate) {
        this.selectedLowerDate = new Date(selectedLowerDate.getTime());
    }

    public java.util.Date getSelectedUpperDate() {
        return new Date(selectedUpperDate.getTime());
    }

    public void setSelectedUpperDate(java.util.Date selectedUpperDate) {
        this.selectedUpperDate = new Date(selectedUpperDate.getTime());
    }

    public java.util.Date getSelectedAppointmentDate() {
        if (selectedAppointment.getDate() != null) {
            return new Date(selectedAppointment.getDate().getTime());
        } else {
            return new Date(System.currentTimeMillis());
        }
    }

    public void setSelectedAppointmentDate(java.util.Date selectedUpperDate) {
        this.selectedAppointment.setDate(new Timestamp(selectedUpperDate.getTime()));
    }

    public String getUserName(int id) {
        String name = "Id " + id + " no encontrada";
        for (Map.Entry<String, Integer> entry : this.mapVet.entrySet()) {
            if (entry.getValue() == id) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getSiteName(int id) {
        String name = "Id " + id + " no encontrada";
        for (Map.Entry<String, Integer> entry : this.mapSite.entrySet()) {
            if (entry.getValue() == id) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getCustomerName(int cedula) {
        String name = "Cedula " + cedula + " no encontrada";
        for (Map.Entry<String, Integer> entry : this.mapCustomer.entrySet()) {
            if (entry.getValue() == cedula) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getDetailName(int id) {
        String name = "No encontrado";
        for (Map.Entry<String, Integer> entry : this.mapDetail.entrySet()) {
            if (entry.getValue() == id) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getVetNameSelected() {
        String name = "No encontrado";
        for (Map.Entry<String, Integer> entry : this.mapVet.entrySet()) {
            if (entry.getValue() == this.selectedAppointment.getIdUser()) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getCustomerNameSelected() {
        String name = "No encontrado";
        for (Map.Entry<String, Integer> entry : this.mapCustomer.entrySet()) {
            if (entry.getValue() == this.selectedAppointment.getIdCustomer()) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getUnitNameSelected() {
        String name = "No encontrado";
        for (Map.Entry<String, String> entry : this.mapUnit.entrySet()) {
            if (entry.getValue().equals(this.selectedAppointment.getIdUnit())) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getSiteNameSelected() {
        String name = "No encontrado";
        try {
            for (Map.Entry<String, Integer> entry : this.mapSite.entrySet()) {
                if (entry.getValue() == this.selectedAppointment.getIdSite()) {
                    name = entry.getKey();
                }
            }

        } catch (Exception e) {
        }
        return name;
    }

    public String getAsistantNameSelected() {
        String name = "No encontrado";
        try {
            for (Map.Entry<String, Integer> entry : this.mapAssistan.entrySet()) {
                if (entry.getValue() == this.selectedAppointment.getIdAsistant()) {
                    name = entry.getKey();
                }
            }
        } catch (Exception e) {
        }
        return name;

    }

    public String getProvinceNameSelected() {
        String name = "No encontrado";
        for (Map.Entry<String, Integer> entry : this.mapProvince.entrySet()) {
            if (entry.getValue() == this.selectedAppointment.getProvince()) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getCantonNameSelected() {
        String name = "No encontrado";
        for (Map.Entry<String, Integer> entry : this.mapCanton.entrySet()) {
            if (entry.getValue() == this.selectedAppointment.getCanton()) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getStateNameSelected() {
        String name = "No encontrado";
        for (Map.Entry<String, Integer> entry : this.mapState.entrySet()) {
            if (entry.getValue() == this.selectedAppointment.getState()) {
                name = entry.getKey();
            }
        }
        return name;
    }

    //metods
    @PostConstruct
    public void initialize() {
        this.serviceAppointment = new ServiceAppointmentTO();
        this.serviceDetails = new ServiceDetails();
        this.selectedAppointment = new AppointmentTO();
        this.context = FacesContext.getCurrentInstance();
        filStuff();
    }
    
    public void insertAppointment(){
        
        if(!nullVerification()){
            return;
        }
        
        if (this.selectedAppointment.getDate().compareTo(new Timestamp(System.currentTimeMillis())) <= 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Fecha Pasada", "La fecha ingresada esta en el pasado o es la del dia de hoy"));
            return;
        }
        
        this.selectedAppointment.setDescription("");
        final int detailOfEspera = 93;
        this.selectedAppointment.setState(detailOfEspera);
        
        try {
            serviceAppointment.insert(this.selectedAppointment);
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
            e.printStackTrace();
        }
    }

    public void reAgendAppointment() {

        if (this.selectedAppointment.getDate().compareTo(new Timestamp(System.currentTimeMillis())) <= 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Fecha Pasada", "La fecha ingresada esta en el pasado o es la del dia de hoy"));
            return;
        }

        final int detailOfCancel = 93;

        this.selectedAppointment.setState(detailOfCancel);

        try {
            serviceAppointment.update(this.selectedAppointment);
        } catch (Exception e) {
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
            e.printStackTrace();
        }
        fillListAppointments();
    }

    public void cancelAppointment() {
        final int detailOfCancel = 92;

        this.selectedAppointment.setState(detailOfCancel);

        try {
            serviceAppointment.update(this.selectedAppointment);
        } catch (Exception e) {
        }
        fillListAppointments();
    }
    
    public void search(){
        if (this.selectedUpperDate.compareTo(this.selectedLowerDate) <= 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Problemas", "Por favor verifique las fechas"));
            return;
        }
        
        fillListAppointments();
    }

    public void newAppointment() {
        this.selectedAppointment = new AppointmentTO();
        this.flagInsert = true;
    }

    private boolean nullVerification() {
        
        if(this.selectedAppointment.getIdUser()== 0){
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nullo", "Escoja un veterinario"));
            return false;
        }
        
        if(this.selectedAppointment.getIdCustomer()== 0){
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nullo", "Escoja un cliente"));
            return false;
        }
        
        if(this.selectedAppointment.getIdUnit().isEmpty() || this.selectedAppointment.getIdUnit() == null){
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nullo", "Escoja una unidad"));
            return false;
        }
        
        if(this.selectedAppointment.getDate() == null){
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nullo", "Escoja una fecha"));
            return false;
        }
        
        if(this.selectedAppointment.getProvince()== 0){
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nullo", "Escoja una provincia"));
            return false;
        }
        
        if(this.selectedAppointment.getCanton()== 0){
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nullo", "Escoja un canton"));
            return false;
        }
        
        return true;
    }

    private void filStuff() {
        fillListAppointments();
        fillMapVet();
        fillMapCustomer();
        fillMapUnit();
        fillMapSite();
        fillMapAsistan();
        fillMapProvince();
        fillMapCanton();
        fillMapState();
        fillMapDetail();
    }
    
    private void fillListAppointments() {

        if (this.selectedState == 0) {
            final int detailOfAgended = 93;
            this.setSelectedState(detailOfAgended);
        }
        if (this.selectedLowerDate == null) {
            this.selectedLowerDate = new Date(System.currentTimeMillis());
        }
        if (this.selectedUpperDate == null) {
            this.selectedUpperDate = new Date(selectedLowerDate.getTime() + (1000 * 60 * 60 * 24));
        }

        try {
            this.listAppointments = this.serviceAppointment.select(this.selectedState, this.selectedLowerDate, this.selectedUpperDate);
        } catch (Exception e) {
            e.printStackTrace();
            this.listAppointments = new ArrayList<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapProvince() {
        final int masterOfProvince = 2;

        try {
            this.mapProvince = this.serviceDetails.select(masterOfProvince);
        } catch (Exception e) {
            this.listAppointments = new ArrayList<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapVet() {
        final int detailOfVet = 2;
        
        try {
            this.mapVet = new ServiceUserTO().selectMap(detailOfVet);
        } catch (Exception e) {
            this.mapVet = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapCustomer() {

        try {
            this.mapCustomer = new ServiceCustomerTO().selectMap();
        } catch (Exception e) {
            this.mapCustomer = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapUnit() {
        try {
            this.mapUnit = new ServiceUnitTO().selectMap();
        } catch (Exception e) {
            this.mapUnit = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapSite() {
        try {
            this.mapSite = new ServiceSite().selectMap();
        } catch (Exception e) {
            this.mapSite = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapAsistan() {
        final int masterOfAsistan = 3;
        
        try {
            this.mapAssistan = new ServiceUserTO().selectMap(masterOfAsistan);
        } catch (Exception e) {
            e.printStackTrace();
            this.mapAssistan = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    public void fillMapCanton() {
        try {
            this.mapCanton = this.serviceDetails.select(this.selectedAppointment.getProvince());
        } catch (Exception e) {
            this.mapCanton = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapState() {
        final int masterOfState = 11;

        try {
            this.mapState = this.serviceDetails.select(masterOfState);
        } catch (Exception e) {
            this.mapState = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapDetail() {
        try {
            this.mapDetail = this.serviceDetails.selectAll();
        } catch (Exception e) {
            this.mapDetail = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }
}
