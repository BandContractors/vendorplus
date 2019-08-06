package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean
@SessionScoped
public class MenuItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String SYSTEM_NAME;
    private String LICENSE_CLIENT_NAME;
    private String SYSTEM_NAME_CLIENT;
    private String SYSTEM_VERSION;
    private int HIRE_MODULE_ON;
    private int LOGIN_TYPE;
    private UserDetail CURRENT_USER;
    private Store CURRENT_STORE;

    /**
     * @return the SYSTEM_NAME
     */
    public String getSYSTEM_NAME() {
        return SYSTEM_NAME;
    }

    /**
     * @param SYSTEM_NAME the SYSTEM_NAME to set
     */
    public void setSYSTEM_NAME(String SYSTEM_NAME) {
        this.SYSTEM_NAME = SYSTEM_NAME;
    }

    /**
     * @return the LICENSE_CLIENT_NAME
     */
    public String getLICENSE_CLIENT_NAME() {
        return LICENSE_CLIENT_NAME;
    }

    /**
     * @param LICENSE_CLIENT_NAME the LICENSE_CLIENT_NAME to set
     */
    public void setLICENSE_CLIENT_NAME(String LICENSE_CLIENT_NAME) {
        this.LICENSE_CLIENT_NAME = LICENSE_CLIENT_NAME;
    }

    /**
     * @return the SYSTEM_NAME_CLIENT
     */
    public String getSYSTEM_NAME_CLIENT() {
        return SYSTEM_NAME_CLIENT;
    }

    /**
     * @param SYSTEM_NAME_CLIENT the SYSTEM_NAME_CLIENT to set
     */
    public void setSYSTEM_NAME_CLIENT(String SYSTEM_NAME_CLIENT) {
        this.SYSTEM_NAME_CLIENT = SYSTEM_NAME_CLIENT;
    }

    /**
     * @return the SYSTEM_VERSION
     */
    public String getSYSTEM_VERSION() {
        return SYSTEM_VERSION;
    }

    /**
     * @param SYSTEM_VERSION the SYSTEM_VERSION to set
     */
    public void setSYSTEM_VERSION(String SYSTEM_VERSION) {
        this.SYSTEM_VERSION = SYSTEM_VERSION;
    }

    /**
     * @return the HIRE_MODULE_ON
     */
    public int getHIRE_MODULE_ON() {
        return HIRE_MODULE_ON;
    }

    /**
     * @param HIRE_MODULE_ON the HIRE_MODULE_ON to set
     */
    public void setHIRE_MODULE_ON(int HIRE_MODULE_ON) {
        this.HIRE_MODULE_ON = HIRE_MODULE_ON;
    }

    /**
     * @return the LOGIN_TYPE
     */
    public int getLOGIN_TYPE() {
        return LOGIN_TYPE;
    }

    /**
     * @param LOGIN_TYPE the LOGIN_TYPE to set
     */
    public void setLOGIN_TYPE(int LOGIN_TYPE) {
        this.LOGIN_TYPE = LOGIN_TYPE;
    }

    /**
     * @return the CURRENT_USER
     */
    public UserDetail getCURRENT_USER() {
        return CURRENT_USER;
    }

    /**
     * @param CURRENT_USER the CURRENT_USER to set
     */
    public void setCURRENT_USER(UserDetail CURRENT_USER) {
        this.CURRENT_USER = CURRENT_USER;
    }

    /**
     * @return the CURRENT_STORE
     */
    public Store getCURRENT_STORE() {
        return CURRENT_STORE;
    }

    /**
     * @param CURRENT_STORE the CURRENT_STORE to set
     */
    public void setCURRENT_STORE(Store CURRENT_STORE) {
        this.CURRENT_STORE = CURRENT_STORE;
    }
}
