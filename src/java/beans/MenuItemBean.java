package beans;

import entities.CompanySetting;
import entities.MenuItem;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.GeneralUserSetting;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "menuItemBean")
@SessionScoped
public class MenuItemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private MenuItem MenuItemObj;

    public void refreshMenuItemObj() {
        try {
            if (null == this.MenuItemObj) {
                this.MenuItemObj = new MenuItem();
            }
            Parameter_listBean pb = new Parameter_listBean();
            GeneralUserSetting gus = new GeneralUserSetting();
            try {
                this.MenuItemObj.setSYSTEM_NAME(pb.getParameter_listByContextNameMemory("SYSTEM", "SYSTEM_NAME").getParameter_value());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setSYSTEM_NAME("");
            }
            try {
                this.MenuItemObj.setLICENSE_CLIENT_NAME(CompanySetting.getLICENSE_CLIENT_NAME());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setLICENSE_CLIENT_NAME("");
            }
            try {
                this.MenuItemObj.setSYSTEM_NAME_CLIENT(pb.getParameter_listByContextNameMemory("SYSTEM", "SYSTEM_NAME_CLIENT").getParameter_value());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setSYSTEM_NAME_CLIENT("");
            }
            try {
                this.MenuItemObj.setSYSTEM_VERSION(pb.getParameter_listByContextNameMemory("SYSTEM", "SYSTEM_VERSION").getParameter_value());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setSYSTEM_VERSION("");
            }
            try {
                this.MenuItemObj.setHIRE_MODULE_ON(gus.getHIRE_MODULE_ON());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setHIRE_MODULE_ON(0);
            }
            try {
                this.MenuItemObj.setLOGIN_TYPE(gus.getLoginType());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setLOGIN_TYPE(0);
            }
            try {
                this.MenuItemObj.setCURRENT_USER(gus.getCurrentUser());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setCURRENT_USER(null);
            }
            try {
                this.MenuItemObj.setCURRENT_STORE(gus.getCurrentStore());
            } catch (NullPointerException npe) {
                this.MenuItemObj.setCURRENT_STORE(null);
            }
            try {
                String ParaValue = pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "CUSTOMER_NAME").getParameter_value();
                if (ParaValue.length() == 0) {
                    ParaValue = "Customer";
                }
                this.MenuItemObj.setCUSTOMER_NAME(ParaValue);
            } catch (Exception e) {
                this.MenuItemObj.setCUSTOMER_NAME("Customer");
            }
            try {
                String ParaValue = pb.getParameter_listByContextNameMemory("COMPANY_SETTING", "SUPPLIER_NAME").getParameter_value();
                if (ParaValue.length() == 0) {
                    ParaValue = "Supplier";
                }
                this.MenuItemObj.setSUPPLIER_NAME(ParaValue);
            } catch (Exception e) {
                this.MenuItemObj.setSUPPLIER_NAME("Supplier");
            }
        } catch (Exception e) {

        }
    }

    /**
     * @return the MenuItemObj
     */
    public MenuItem getMenuItemObj() {
        return MenuItemObj;
    }

    /**
     * @param MenuItemObj the MenuItemObj to set
     */
    public void setMenuItemObj(MenuItem MenuItemObj) {
        this.MenuItemObj = MenuItemObj;
    }
}
