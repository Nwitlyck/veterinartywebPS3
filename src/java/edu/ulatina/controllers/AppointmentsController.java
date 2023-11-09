package edu.ulatina.controllers;

import edu.ulatina.objects.*;
import edu.ulatina.services.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nwitlyck
 */
@ManagedBean(name = "appointmentsController")
@ViewScoped
public class AppointmentsController implements Serializable {

    private ServiceAppointmentTO serviceAppointment;
    private ServiceDetails serviceDetails;
    private FacesContext context;
    private Map<String, Integer> mapDetail;
    private Map<String, Integer> mapUsers;

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
    private boolean flag;

    public AppointmentTO getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(AppointmentTO selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
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
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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

    public String getUserName(int id) {
        String name = "Id " + id +" no encontrada";
        for (Map.Entry<String, Integer> entry : this.mapUsers.entrySet()) {
            if (entry.getValue() == id) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getSiteName(int id) {
        String name = "Id " + id +" no encontrada";
        for (Map.Entry<String, Integer> entry : this.mapSite.entrySet()) {
            if (entry.getValue() == id) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getCustomerName(int cedula) {
        String name = "Cedula " + cedula +" no encontrada";
        for (Map.Entry<String, Integer> entry : this.mapCustomer.entrySet()) {
            if (entry.getValue() == cedula) {
                name = entry.getKey();
            }
        }
        return name;
    }

    public String getDetailName(int id) {
        String name = "Not Found";
        for (Map.Entry<String, Integer> entry : this.mapDetail.entrySet()) {
            if (entry.getValue() == id) {
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

    private void filStuff() {
        fillListAppointments();
        fillMapUser();
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

    public void newAppointment() {
        this.selectedAppointment = new AppointmentTO();
        this.flag = true;
    }

    public void fillListAppointments() {

        if (this.selectedState == 0) {
            final int detailOfEspera = 94;
            this.selectedState = detailOfEspera;
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
        final int masterOfProvince = 11;

        try {
            this.mapProvince = this.serviceDetails.select(masterOfProvince);
        } catch (Exception e) {
            this.listAppointments = new ArrayList<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }
    
    private void fillMapUser() {

        try {
            this.mapUsers = new ServiceUserTO().selectMapAll();
        } catch (Exception e) {
            this.mapUsers = new HashMap<>();
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
            this.mapVet = new ServiceUserTO().selectMap(masterOfAsistan);
        } catch (Exception e) {
            this.mapProvince = new HashMap<>();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Fatal", "No se puede connectar a la base de datos"));
        }
    }

    private void fillMapCanton() {
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
