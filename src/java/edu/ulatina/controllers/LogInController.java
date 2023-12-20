package edu.ulatina.controllers;

import edu.ulatina.objects.UserTO;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import edu.ulatina.services.ServiceUserTO;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;

@ManagedBean(name = "logInController")
@SessionScoped
public class LogInController  implements Serializable {

    private String email;
    private String pasword;

    public LogInController() {
    }

    public LogInController(String user, String pasword) {
        this.email = user;
        this.pasword = pasword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        FacesContext context = FacesContext.getCurrentInstance();

        
        if (email.isEmpty() || email == null) {
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor Nulo", "El correo esta vacio"));
            return;
        }

        if (pasword.isEmpty() || pasword == null) {
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "Valor Nulo", "La contraseña esta vacia"));
            return;
        }

        String regexPatternEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (!Pattern.compile(regexPatternEmail).matcher(email).matches()) {

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalido", "El correo ingresado no es valido"));
            return;
        }

        try {
            if (!new ServiceUserTO().selectByEmailAndPassword(email, pasword)) {
                context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Usuario o Contraseña incorrecta"));
            } else {
                UserTO user = new UserTO();
                user.setEmail(email);
                
                context.getExternalContext().getSessionMap().put("user", user);
                this.redirect("/faces/landingPage.xhtml");
            }

        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Problemas al conectar a la base de datos"));
        }

    }
}
