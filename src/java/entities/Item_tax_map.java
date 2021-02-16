package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Item_tax_map implements Serializable {

    private static final long serialVersionUID = 1L;
    private long item_tax_map_id;
    private long item_id;
    private String item_id_tax;
    private String item_code_tax;
    private Date add_date;
    private int is_synced;
    private String description;

    /**
     * @return the item_tax_map_id
     */
    public long getItem_tax_map_id() {
        return item_tax_map_id;
    }

    /**
     * @param item_tax_map_id the item_tax_map_id to set
     */
    public void setItem_tax_map_id(long item_tax_map_id) {
        this.item_tax_map_id = item_tax_map_id;
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
     * @return the item_code_tax
     */
    public String getItem_code_tax() {
        return item_code_tax;
    }

    /**
     * @param item_code_tax the item_code_tax to set
     */
    public void setItem_code_tax(String item_code_tax) {
        this.item_code_tax = item_code_tax;
    }

    /**
     * @return the add_date
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * @param add_date the add_date to set
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * @return the is_synced
     */
    public int getIs_synced() {
        return is_synced;
    }

    /**
     * @param is_synced the is_synced to set
     */
    public void setIs_synced(int is_synced) {
        this.is_synced = is_synced;
    }

    /**
     * @return the item_id_tax
     */
    public String getItem_id_tax() {
        return item_id_tax;
    }

    /**
     * @param item_id_tax the item_id_tax to set
     */
    public void setItem_id_tax(String item_id_tax) {
        this.item_id_tax = item_id_tax;
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
