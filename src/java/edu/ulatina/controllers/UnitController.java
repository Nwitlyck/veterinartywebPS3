
package edu.ulatina.controllers;


import edu.ulatina.objects.UnitTO;
import edu.ulatina.services.ServiceUnitTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Dylan
 */

@ManagedBean (name = "unitController")
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
        boolean flag = true;
        System.out.println("Estoy creando la unidad");
        if (selectedUnit.getPlate()== null || selectedUnit.getPlate().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de placa esta vacio"));
            flag = false;
        }
        if (selectedUnit.getName()== null || selectedUnit.getName().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de numero de unidad esta vacio"));
            flag = false;
        }
        if (flag) {
            System.out.println("Estoy salvando al sitio nuevo");

            try {
                this.serv.insert(selectedUnit);
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error en alguno de los campos de datos"));
            }
            this.selectedUnit = new UnitTO();
            PrimeFaces.current().executeScript("PF('manageSiteContent').hide()");
        }

    }
    
    public void disableUnit(){
           System.out.println("Estoy deshabilitando la unidad");
        try {
           serv.delete(selectedUnit);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar la unidad en base de datos"));
         }
         this.selectedUnit = new UnitTO();
        
    }
    
    public void enableUnit(){
        System.out.println("Estoy habilitando la unidad");
        try {
            serv.enable(selectedUnit);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar la unidad en base de datos"));
         }
         this.selectedUnit = new UnitTO();
    }
    
    public void updateUnit(){
        boolean flag = true;
        if (selectedUnit.getPlate()== null || selectedUnit.getPlate().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de placa esta vacio"));
            flag = false;
        }
        if (selectedUnit.getName()== null || selectedUnit.getName().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El campo de numero esta vacio"));
            flag = false;
        }

        if (flag) {
            System.out.println("Estoy salvando la unidad");

            try {
                this.serv.update(selectedUnit);
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al actualizar la base de datos"));
            }
            this.selectedUnit = new UnitTO();
            PrimeFaces.current().executeScript("PF('manageSiteContent').hide()");
        }
    }
    
     public void resetSelectedUnit(){
        this.selectedUnit = new UnitTO();
    }
    
    
}
