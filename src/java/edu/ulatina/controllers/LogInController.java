
package edu.ulatina.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "logInController")
@ViewScoped
public class LogInController implements Serializable{
    private String user;
    private String pasword;
    
    private boolean viewDisabledClient = false;
    private boolean viewDisabledVeterinary = false;
    private boolean viewDisabledUnit = false;
    private boolean viewDisabledUser = false;
    private boolean viewDisabledSite = false;

    public LogInController() {
    }

    public LogInController(String user, String pasword) {
        this.user = user;
        this.pasword = pasword;
    }

    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }
    
     public void redirect(String rute) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + rute);
        } catch (Exception e) {

        }
    }
     
     public void logIn() {
         this.redirect("/faces/landingPage.xhtml");
     }
     
     public void logOut() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + "/faces/index.xhtml?faces-redirect=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public List<demo> crearDemo(){
         List<demo> listaDemo = new ArrayList<>();
         
         for(int i = 0; i < 6; i++){
             demo newDemo = new demo("test " + i, "test " + i, "test " + i, "test " + i, "test " + i, "test " + i, "test " + i, "test " + i, i, i, i);
             listaDemo.add(newDemo);
         }
         return listaDemo;
     }
     
      public List<demo> crearDemo2(){
         List<demo> listaDemo = new ArrayList<>();
         
         for(int i = 6; i < 12; i++){
             demo newDemo = new demo("test " + i, "test " + i, "test " + i, "test " + i, "test " + i, "test " + i, "test " + i, "test " + i, i, i, i);
             listaDemo.add(newDemo);
         }
         return listaDemo;
     }

    public boolean isViewDisabledClient() {
        return viewDisabledClient;
    }

    public void setViewDisabledClient(boolean viewDisabledClient) {
        this.viewDisabledClient = viewDisabledClient;
    }

    public boolean isViewDisabledVeterinary() {
        return viewDisabledVeterinary;
    }

    public void setViewDisabledVeterinary(boolean viewDisabledVeterinary) {
        this.viewDisabledVeterinary = viewDisabledVeterinary;
    }

    public boolean isViewDisabledUnit() {
        return viewDisabledUnit;
    }

    public void setViewDisabledUnit(boolean viewDisabledUnit) {
        this.viewDisabledUnit = viewDisabledUnit;
    }

    public boolean isViewDisabledUser() {
        return viewDisabledUser;
    }

    public void setViewDisabledUser(boolean viewDisabledUser) {
        this.viewDisabledUser = viewDisabledUser;
    }

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
}
