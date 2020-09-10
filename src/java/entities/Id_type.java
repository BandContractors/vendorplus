package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Id_type implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id_type_id;
    private String id_type_code;
    private String id_type_name;
    private int applies_to_individual;
    private int applies_to_company;
    private int is_active;

    /**
     * @return the id_type_id
     */
    public int getId_type_id() {
        return id_type_id;
    }

    /**
     * @param id_type_id the id_type_id to set
     */
    public void setId_type_id(int id_type_id) {
        this.id_type_id = id_type_id;
    }

    /**
     * @return the id_type_code
     */
    public String getId_type_code() {
        return id_type_code;
    }

    /**
     * @param id_type_code the id_type_code to set
     */
    public void setId_type_code(String id_type_code) {
        this.id_type_code = id_type_code;
    }

    /**
     * @return the id_type_name
     */
    public String getId_type_name() {
        return id_type_name;
    }

    /**
     * @param id_type_name the id_type_name to set
     */
    public void setId_type_name(String id_type_name) {
        this.id_type_name = id_type_name;
    }

    /**
     * @return the applies_to_individual
     */
    public int getApplies_to_individual() {
        return applies_to_individual;
    }

    /**
     * @param applies_to_individual the applies_to_individual to set
     */
    public void setApplies_to_individual(int applies_to_individual) {
        this.applies_to_individual = applies_to_individual;
    }

    /**
     * @return the applies_to_company
     */
    public int getApplies_to_company() {
        return applies_to_company;
    }

    /**
     * @param applies_to_company the applies_to_company to set
     */
    public void setApplies_to_company(int applies_to_company) {
        this.applies_to_company = applies_to_company;
    }

    /**
     * @return the is_active
     */
    public int getIs_active() {
        return is_active;
    }

    /**
     * @param is_active the is_active to set
     */
    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }
}
