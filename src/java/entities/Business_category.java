package entities;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Business_category implements Serializable {

    private static final long serialVersionUID = 1L;
    private int business_category_id;
    private String category_name;

    /**
     * @return the business_category_id
     */
    public int getBusiness_category_id() {
        return business_category_id;
    }

    /**
     * @param business_category_id the business_category_id to set
     */
    public void setBusiness_category_id(int business_category_id) {
        this.business_category_id = business_category_id;
    }

    /**
     * @return the category_name
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
     * @param category_name the category_name to set
     */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
