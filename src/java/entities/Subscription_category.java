package entities;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Subscription_category implements Serializable {

    private static final long serialVersionUID = 1L;
    private int subscription_category_id;
    private String category_code;
    private String category_name;

    /**
     * @return the subscription_category_id
     */
    public int getSubscription_category_id() {
        return subscription_category_id;
    }

    /**
     * @param subscription_category_id the subscription_category_id to set
     */
    public void setSubscription_category_id(int subscription_category_id) {
        this.subscription_category_id = subscription_category_id;
    }

    /**
     * @return the category_code
     */
    public String getCategory_code() {
        return category_code;
    }

    /**
     * @param category_code the category_code to set
     */
    public void setCategory_code(String category_code) {
        this.category_code = category_code;
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
