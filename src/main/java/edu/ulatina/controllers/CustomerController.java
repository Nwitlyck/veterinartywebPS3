/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.controllers;

import edu.ulatina.objects.CustomersTO;
import edu.ulatina.services.ServiceAppointmentTO;
import edu.ulatina.services.ServiceCustomerTO;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Named;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;

/**
 *
 * @author Vivi
 */
@Named("customerController")
@ViewScoped
public class CustomerController implements Serializable {

    private ServiceCustomerTO serviceCustomerTO;
    private CustomersTO selectedCustomerTO;
    private boolean viewDisabledClient;
    private List<CustomersTO> listCustomersTOsEnable;
    private List<CustomersTO> listCustomersTOsDisble;

    public CustomersTO getSelectedCustomerTO() {
        return selectedCustomerTO;
    }

    public void setSelectedCustomerTO(CustomersTO selectedCustomerTO) {
        this.selectedCustomerTO = selectedCustomerTO;
    }

    public boolean isViewDisabledClient() {
        return viewDisabledClient;
    }

    public void setViewDisabledClient(boolean viewDisabledClient) {
        this.viewDisabledClient = viewDisabledClient;
    }

    public List<CustomersTO> getListCustomersTOsEnable() {
        return listCustomersTOsEnable;
    }

    public void setListCustomersTOsEnable(List<CustomersTO> listCustomersTOsEnable) {
        this.listCustomersTOsEnable = listCustomersTOsEnable;
    }

    public List<CustomersTO> getListCustomersTOsDisble() {
        return listCustomersTOsDisble;
    }

    public void setListCustomersTOsDisble(List<CustomersTO> listCustomersTOsDisble) {
        this.listCustomersTOsDisble = listCustomersTOsDisble;
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

    @PostConstruct
    public void inicializate() {
        serviceCustomerTO = new ServiceCustomerTO();
        selectedCustomerTO = new CustomersTO();
        getCustomerList();
        getDisableCustomerList();

    }

    public void getCustomerList() {
        try {
            listCustomersTOsEnable = serviceCustomerTO.select(1);
        } catch (Exception ex) {
            listCustomersTOsEnable = new ArrayList<>();
            ex.printStackTrace();
        }
    }

    public void getDisableCustomerList() {
        try {
            listCustomersTOsDisble = serviceCustomerTO.select(0);
        } catch (Exception ex) {
            listCustomersTOsDisble = new ArrayList<>();
            ex.printStackTrace();
        }
    }

    private boolean notNull() {

        if (selectedCustomerTO.getCedula() == 0) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor Nulo", "La cédula está vacía"));
            return false;
        }

        if (selectedCustomerTO.getEmail().isEmpty() || selectedCustomerTO.getEmail() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor Nulo", "El correo esta vacio"));
            return false;
        }

        if (selectedCustomerTO.getName().isEmpty() || selectedCustomerTO.getName() == null) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor Nulo", "El nombre esta vacio"));
            return false;
        }

        return true;
    }

    private boolean followMetrics(boolean isUpdate) {

        if (!isUpdate) {
            if (!(selectedCustomerTO.getCedula() >= 100000000 && selectedCustomerTO.getCedula() <= 999999999)) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalido", "La cédula no esta en el rango debido"));
                return false;
            }

            for (CustomersTO customer : listCustomersTOsEnable) {
                if (selectedCustomerTO.getCedula() == customer.getCedula()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalido", "La cedula ya existe"));
                    return false;
                }
            }

            for (CustomersTO customer : listCustomersTOsDisble) {
                if (selectedCustomerTO.getCedula() == customer.getCedula()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalido", "La cedula ya existe"));
                    return false;
                }
            }
        }

        String regexPatternEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (!Pattern.compile(regexPatternEmail).matcher(this.selectedCustomerTO.getEmail()).matches()) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalido", "El correo ingresado no es valido"));
            return false;
        }

        for (CustomersTO customer : listCustomersTOsEnable) {
            if (selectedCustomerTO.getEmail() == customer.getEmail()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalido", "El correo ya existe"));
                return false;
            }
        }

        for (CustomersTO customer : listCustomersTOsDisble) {
            if (selectedCustomerTO.getEmail() == customer.getEmail()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalido", "El correo ya existe"));
                return false;
            }
        }
        return true;
    }

    public void insertCustomer() {

        if (!notNull()) {
            return;
        }

        if (!followMetrics(false)) {
            return;
        }

        try {
            this.serviceCustomerTO.insert(selectedCustomerTO);
            getCustomerList();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al hacer insert en base de datos"));
        } finally {
            selectedCustomerTO = new CustomersTO();
        }
    }

    public void updateCustomer() {

        if (!notNull()) {
            selectedCustomerTO = new CustomersTO();
            return;
        }

        if (!followMetrics(true)) {
            selectedCustomerTO = new CustomersTO();
            return;
        }

        try {
            this.serviceCustomerTO.update(selectedCustomerTO);
            getCustomerList();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al hacer update en base de datos"));
        } finally {
            selectedCustomerTO = new CustomersTO();
        }
    }

    public void disableCustomer() {
        try {
            serviceCustomerTO.delete(selectedCustomerTO);
            new ServiceAppointmentTO().disableCustomer(this.selectedCustomerTO.getCedula());
            getCustomerList();
            getDisableCustomerList();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar el usuario en base de datos"));
        }
        this.selectedCustomerTO = new CustomersTO();

    }

    public void enableCustomer() {
        try {
            serviceCustomerTO.enable(selectedCustomerTO);
            getCustomerList();
            getDisableCustomerList();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al desabilitar el usuario en base de datos"));
        }
        this.selectedCustomerTO = new CustomersTO();
    }
}
