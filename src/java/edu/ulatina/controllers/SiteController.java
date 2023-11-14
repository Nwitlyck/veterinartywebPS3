package edu.ulatina.controllers;

import edu.ulatina.objects.SiteTO;
import edu.ulatina.services.ServiceAppointmentTO;
import edu.ulatina.services.ServiceSite;
import edu.ulatina.services.ServiceDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "siteController")
@ViewScoped
public class SiteController implements Serializable {

    private boolean viewDisabledSite = false;
    private ServiceSite serv = new ServiceSite();
    private SiteTO selectedSite = new SiteTO();
    private Map<String, Integer> mapProvince;
    private Map<String, Integer> mapCanton;

    public SiteController() {
    }

    public ServiceSite getServ() {
        return serv;
    }

    public void setServ(ServiceSite serv) {
        this.serv = serv;
    }

    public SiteTO getSelectedSite() {
        return selectedSite;
    }

    public void setSelectedSite(SiteTO selectedSite) {
        this.selectedSite = selectedSite;
        fillMapCanton();
    }

    public Map<String, Integer> getMapProvince() {
        return this.mapProvince;
    }

    public void setMapProvince(Map<String, Integer> mapProvince) {
        this.mapProvince = mapProvince;
    }

    public Map<String, Integer> getMapCanton() {
        return this.mapCanton;
    }

    public void setMapCanton(Map<String, Integer> mapCanton) {
        this.mapCanton = mapCanton;
    }

    //isViewDisabledSite setViewDisabledSite y viewDisabledMessage es para los toggleSwitch
    public boolean isViewDisabledSite() {
        return viewDisabledSite;
    }

    public void setViewDisabledSite(boolean viewDisabledSite) {
        this.viewDisabledSite = viewDisabledSite;
    }

    public void viewDisabledMessage(AjaxBehaviorEvent event) {
        UIComponent component = event.getComponent();
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            Boolean value = (Boolean) inputComponent.getValue();
            String summary = value ? "Visualizando los deshabilitados" : "Visualizando los habilitados";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
        }
    }

    public List<SiteTO> getSiteList() {
        List<SiteTO> returnList = new ArrayList<>();
        try {
            returnList = serv.select(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnList;
    }

    public List<SiteTO> getDisableSiteList() {
        List<SiteTO> returnList = new ArrayList<>();
        try {
            returnList = serv.select(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnList;
    }

    public void createNewSite() {
        boolean flag = true;
        if (selectedSite.getName() == null || selectedSite.getName().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de nombre esta vacio"));
            flag = false;
        }
        if (selectedSite.getAdress() == null || selectedSite.getAdress().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de direccion esta vacio"));
            flag = false;
        }
        if (selectedSite.getAdress().length() >= 9 && selectedSite.getAdress().length() <= 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de telefono no es valido"));
            flag = false;
        }
        if (flag) {
            System.out.println("Estoy salvando al sitio nuevo");

            try {
                this.serv.insert(selectedSite);
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error en alguno de los campos de datos"));
            }
            this.selectedSite = new SiteTO();
            PrimeFaces.current().executeScript("PF('manageSiteContent').hide()");
        }

    }

    public void disableSite() {
        System.out.println("Estoy deshabilitando al sitio");
        try {
            this.serv.delete(selectedSite);
            new ServiceAppointmentTO().disableSite(this.selectedSite.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar el sitio en base de datos"));
        }
        this.selectedSite = new SiteTO();

    }

    public void enableSite() {
        System.out.println("Estoy habilitando al sitio");
        try {
            this.serv.enable(selectedSite);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar el sitio en base de datos"));
        }
        this.selectedSite = new SiteTO();
    }

    public void updateSite() {
        boolean flag = true;
        if (selectedSite.getName() == null || selectedSite.getName().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de nombre esta vacio"));
            flag = false;
        }
        if (selectedSite.getAdress() == null || selectedSite.getAdress().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de direccion esta vacio"));
            flag = false;
        }

        if (flag) {
            System.out.println("Estoy salvando al sitio");

            try {
                this.serv.update(selectedSite);
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al hacer update en base de datos"));
            }
            this.selectedSite = new SiteTO();
            PrimeFaces.current().executeScript("PF('manageSiteContent').hide()");
        }
    }

    public void resetSelectedSite() {
        this.selectedSite = new SiteTO();
        fillMapCanton();
    }

    @PostConstruct
    public void initialize() {
        fillMapProvince();
        fillMapCanton();
    }

    private void fillMapProvince() {
        try {
            mapProvince = new ServiceDetails().select(2);
        } catch (Exception e) {
            mapProvince = new HashMap<>();
            e.printStackTrace();
        }
    }

    public void fillMapCanton() {
        try {
            mapCanton = new ServiceDetails().select(selectedSite.getProvince());
        } catch (Exception e) {
            mapCanton = new HashMap<>();
            e.printStackTrace();
        }
    }
}
