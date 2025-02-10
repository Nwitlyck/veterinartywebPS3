package edu.ulatina.controllers;

import edu.ulatina.objects.UnitTO;
import edu.ulatina.services.ServiceAppointmentTO;
import edu.ulatina.services.ServiceUnitTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import org.primefaces.PrimeFaces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 *
 * @author Dylan
 */
@Named("unitController")
@ViewScoped
public class UnitController implements Serializable {

    private ServiceUnitTO serv;
    private boolean viewDisableUnit = false;
    private UnitTO selectedUnit;

    public boolean isViewDisableUnit() {
        return viewDisableUnit;
    }

    public void setViewDisableUnit(boolean viewDisableUnit) {
        this.viewDisableUnit = viewDisableUnit;
    }

    public UnitTO getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(UnitTO selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    @PostConstruct
    public void initialize() {
        serv = new ServiceUnitTO();
        selectedUnit = new UnitTO();
    }

    public List<UnitTO> getUnitList() {
        List<UnitTO> returnList = new ArrayList<>();
        try {
            returnList = serv.select(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnList;
    }

    public List<UnitTO> getDisableUnitList() {
        List<UnitTO> returnList = new ArrayList<>();
        try {
            returnList = serv.select(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnList;
    }

    public void viewDisabledMessageUnit(AjaxBehaviorEvent event) {
        UIComponent component = event.getComponent();
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            Boolean value = (Boolean) inputComponent.getValue();
            String summary = value ? "Visualizando los deshabilitados" : "Visualizando los habilitados";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
        }
    }

    public void createNewUnit() {
        if (!notNull()) {
            return;
        }

        try {
            this.serv.insert(selectedUnit);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error en alguno de los campos de datos"));
        }
        this.selectedUnit = new UnitTO();
        PrimeFaces.current().executeScript("PF('manageSiteContent').hide()");

    }

    public void disableUnit() {
        System.out.println("Estoy deshabilitando la unidad");
        try {
            serv.delete(selectedUnit);
            new ServiceAppointmentTO().disableUnit(this.selectedUnit.getPlate());
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar la unidad en base de datos"));
        }
        this.selectedUnit = new UnitTO();

    }

    public void enableUnit() {
        System.out.println("Estoy habilitando la unidad");
        try {
            serv.enable(selectedUnit);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar la unidad en base de datos"));
        }
        this.selectedUnit = new UnitTO();
    }

    public void updateUnit() {
        if (!notNull()) {
            return;
        }

        try {
            this.serv.update(selectedUnit);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al actualizar la base de datos"));
        }
        this.selectedUnit = new UnitTO();
        PrimeFaces.current().executeScript("PF('manageSiteContent').hide()");

    }

    public void resetSelectedUnit() {
        this.selectedUnit = new UnitTO();
    }

    private boolean notNull() {

        if (selectedUnit.getPlate() == null || selectedUnit.getPlate().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de placa esta vacio"));
            return false;
        }
        if (selectedUnit.getName() == null || selectedUnit.getName().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de numero esta vacio"));
            return false;
        }
        return true;
    }

}
