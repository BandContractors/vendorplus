/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ErrorHandler {

    public String getStatusCode() {
        String val = "N/A";
        try {
            val = String.valueOf((Integer) FacesContext.getCurrentInstance().getExternalContext().
                    getRequestMap().get("javax.servlet.error.status_code"));
        } catch (NullPointerException npe) {
        }
        return val;
    }

    public String getMessage() {
        String val = "N/A";
        try {
            val = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.message");
        } catch (NullPointerException npe) {
        }
        return val;
    }

    public String getExceptionType() {
        String val = "N/A";
        try {
            val = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception_type").toString();
        } catch (NullPointerException npe) {
        }
        return val;
    }

    public String getException() {
        String val = "N/A";
        try {
            val = (String) ((Exception) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception")).toString();
        } catch (NullPointerException npe) {
        }
        return val;
    }

    public String getRequestURI() {
        String val = "N/A";
        try {
            val = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.request_uri");
        } catch (NullPointerException npe) {
        }
        return val;
    }

    public String getServletName() {
        String val = "N/A";
        try {
            val = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.servlet_name");
        } catch (NullPointerException npe) {
        }
        return val;
    }

}
