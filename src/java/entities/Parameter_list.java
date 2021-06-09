package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Parameter_list implements Serializable {

    private static final long serialVersionUID = 1L;
    private long parameter_list_id;
    private String context;
    private String parameter_name;
    private String parameter_value;
    private String description;
    private int store_id;

    public Parameter_list() {
        parameter_list_id = 0;
        context = "";
        parameter_name = "";
        parameter_value = "";
        description = "";
        store_id = 0;
    }

    /**
     * @return the parameter_list_id
     */
    public long getParameter_list_id() {
        return parameter_list_id;
    }

    /**
     * @param parameter_list_id the parameter_list_id to set
     */
    public void setParameter_list_id(long parameter_list_id) {
        this.parameter_list_id = parameter_list_id;
    }

    /**
     * @return the parameter_name
     */
    public String getParameter_name() {
        return parameter_name;
    }

    /**
     * @param parameter_name the parameter_name to set
     */
    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    /**
     * @return the parameter_value
     */
    public String getParameter_value() {
        return parameter_value;
    }

    /**
     * @param parameter_value the parameter_value to set
     */
    public void setParameter_value(String parameter_value) {
        this.parameter_value = parameter_value;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the store_id
     */
    public int getStore_id() {
        return store_id;
    }

    /**
     * @param store_id the store_id to set
     */
    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }
}
