/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import beans.MenuItemBean;
import beans.Parameter_listBean;
import connections.DBConnection;
import entities.CompanySetting;
import entities.MenuItem;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import sessions.GeneralUserSetting;

/**
 * REST Web Service
 *
 * @author Wence Benda
 */
@Path("service")
public class ServiceResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ServiceResource
     */
    public ServiceResource() {
    }

    /**
     * Retrieves representation of an instance of api.ServiceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        String x="-";
        String y="-";
        try {
            //TODO return proper representation object
            DBConnection.readConnectionConfigurations("configurations.ConfigFile");
            new Parameter_listBean().refreshSavedParameterLists();
            x=new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "MOBILE_NUMBER").getParameter_value();
            y=CompanySetting.getCompanyName();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServiceResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        MenuItem mi=new MenuItemBean().getMenuItemObj();
        return "<h1>" + y + "</h1> </br>" + x;
    }

    /**
     * PUT method for updating or creating an instance of ServiceResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
