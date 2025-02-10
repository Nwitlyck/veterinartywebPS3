/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.controllers;

import edu.ulatina.objects.UserTO;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 *
 * @author Nwitlyck
 */ 
@Named("logOutController")
@ViewScoped
public class LogOutController implements Serializable{

    public void verifySession() {

        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            UserTO user = (UserTO) context.getExternalContext().getSessionMap().get("user");
            if(user == null){
                redirect("/faces/index.xhtml?faces-redirect=true");
            }
        } catch (Exception e) {
        }
    }

    public void logOut() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            redirect("/faces/index.xhtml?faces-redirect=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void redirect(String rute) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + rute);
        } catch (Exception e) {

        }
    }
}
