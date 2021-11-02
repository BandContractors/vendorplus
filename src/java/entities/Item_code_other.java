package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Item_code_other implements Serializable {

    private static final long serialVersionUID = 1L;
    private long item_code_other_id;
    private long item_id;
    private String item_code;
    private String description;

    /**
     * @return the item_code_other_id
     */
    public long getItem_code_other_id() {
        return item_code_other_id;
    }

    /**
     * @param item_code_other_id the item_code_other_id to set
     */
    public void setItem_code_other_id(long item_code_other_id) {
        this.item_code_other_id = item_code_other_id;
    }

    /**
     * @return the item_id
     */
    public long getItem_id() {
        return item_id;
    }

    /**
     * @param item_id the item_id to set
     */
    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    /**
     * @return the item_code
     */
    public String getItem_code() {
        return item_code;
    }

    /**
     * @param item_code the item_code to set
     */
    public void setItem_code(String item_code) {
        this.item_code = item_code;
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

    

}
