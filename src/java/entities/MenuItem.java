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
    private String CUSTOMER_NAME;
    private String SUPPLIER_NAME;
    private String LOC_LEVEL_2_LABEL;
    private String LOC_LEVEL_3_LABEL;
    private String DEFAULT_FOCUS_CONTROL_ID;
    private int ENABLE_AUTO_COMPLETE_ITEM_SEARCH;
    private int PURCHASE_INVOICE_MODE;
    private String LANG_VAR_SYS;
    private String LANG_VAR_OUT;
    private String LANG_BASE_NAME_SYS;
    private String LANG_BASE_NAME_OUT;

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

    /**
     * @return the CUSTOMER_NAME
     */
    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    /**
     * @param CUSTOMER_NAME the CUSTOMER_NAME to set
     */
    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    /**
     * @return the SUPPLIER_NAME
     */
    public String getSUPPLIER_NAME() {
        return SUPPLIER_NAME;
    }

    /**
     * @param SUPPLIER_NAME the SUPPLIER_NAME to set
     */
    public void setSUPPLIER_NAME(String SUPPLIER_NAME) {
        this.SUPPLIER_NAME = SUPPLIER_NAME;
    }

    /**
     * @return the LOC_LEVEL_2_LABEL
     */
    public String getLOC_LEVEL_2_LABEL() {
        return LOC_LEVEL_2_LABEL;
    }

    /**
     * @param LOC_LEVEL_2_LABEL the LOC_LEVEL_2_LABEL to set
     */
    public void setLOC_LEVEL_2_LABEL(String LOC_LEVEL_2_LABEL) {
        this.LOC_LEVEL_2_LABEL = LOC_LEVEL_2_LABEL;
    }

    /**
     * @return the LOC_LEVEL_3_LABEL
     */
    public String getLOC_LEVEL_3_LABEL() {
        return LOC_LEVEL_3_LABEL;
    }

    /**
     * @param LOC_LEVEL_3_LABEL the LOC_LEVEL_3_LABEL to set
     */
    public void setLOC_LEVEL_3_LABEL(String LOC_LEVEL_3_LABEL) {
        this.LOC_LEVEL_3_LABEL = LOC_LEVEL_3_LABEL;
    }

    /**
     * @return the DEFAULT_FOCUS_CONTROL_ID
     */
    public String getDEFAULT_FOCUS_CONTROL_ID() {
        return DEFAULT_FOCUS_CONTROL_ID;
    }

    /**
     * @param DEFAULT_FOCUS_CONTROL_ID the DEFAULT_FOCUS_CONTROL_ID to set
     */
    public void setDEFAULT_FOCUS_CONTROL_ID(String DEFAULT_FOCUS_CONTROL_ID) {
        this.DEFAULT_FOCUS_CONTROL_ID = DEFAULT_FOCUS_CONTROL_ID;
    }

    /**
     * @return the ENABLE_AUTO_COMPLETE_ITEM_SEARCH
     */
    public int getENABLE_AUTO_COMPLETE_ITEM_SEARCH() {
        return ENABLE_AUTO_COMPLETE_ITEM_SEARCH;
    }

    /**
     * @param ENABLE_AUTO_COMPLETE_ITEM_SEARCH the
     * ENABLE_AUTO_COMPLETE_ITEM_SEARCH to set
     */
    public void setENABLE_AUTO_COMPLETE_ITEM_SEARCH(int ENABLE_AUTO_COMPLETE_ITEM_SEARCH) {
        this.ENABLE_AUTO_COMPLETE_ITEM_SEARCH = ENABLE_AUTO_COMPLETE_ITEM_SEARCH;
    }

    /**
     * @return the PURCHASE_INVOICE_MODE
     */
    public int getPURCHASE_INVOICE_MODE() {
        return PURCHASE_INVOICE_MODE;
    }

    /**
     * @param PURCHASE_INVOICE_MODE the PURCHASE_INVOICE_MODE to set
     */
    public void setPURCHASE_INVOICE_MODE(int PURCHASE_INVOICE_MODE) {
        this.PURCHASE_INVOICE_MODE = PURCHASE_INVOICE_MODE;
    }

    /**
     * @return the LANG_VAR_SYS
     */
    public String getLANG_VAR_SYS() {
        return LANG_VAR_SYS;
    }

    /**
     * @param LANG_VAR_SYS the LANG_VAR_SYS to set
     */
    public void setLANG_VAR_SYS(String LANG_VAR_SYS) {
        this.LANG_VAR_SYS = LANG_VAR_SYS;
    }

    /**
     * @return the LANG_VAR_OUT
     */
    public String getLANG_VAR_OUT() {
        return LANG_VAR_OUT;
    }

    /**
     * @param LANG_VAR_OUT the LANG_VAR_OUT to set
     */
    public void setLANG_VAR_OUT(String LANG_VAR_OUT) {
        this.LANG_VAR_OUT = LANG_VAR_OUT;
    }

    /**
     * @return the LANG_BASE_NAME_SYS
     */
    public String getLANG_BASE_NAME_SYS() {
        return LANG_BASE_NAME_SYS;
    }

    /**
     * @param LANG_BASE_NAME_SYS the LANG_BASE_NAME_SYS to set
     */
    public void setLANG_BASE_NAME_SYS(String LANG_BASE_NAME_SYS) {
        this.LANG_BASE_NAME_SYS = LANG_BASE_NAME_SYS;
    }

    /**
     * @return the LANG_BASE_NAME_OUT
     */
    public String getLANG_BASE_NAME_OUT() {
        return LANG_BASE_NAME_OUT;
    }

    /**
     * @param LANG_BASE_NAME_OUT the LANG_BASE_NAME_OUT to set
     */
    public void setLANG_BASE_NAME_OUT(String LANG_BASE_NAME_OUT) {
        this.LANG_BASE_NAME_OUT = LANG_BASE_NAME_OUT;
    }
}
