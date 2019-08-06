package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Item_combination implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long item_combination_id;
    private long parent_item_id;
    private long child_item_id;
    private double child_qty;
    private String parent_item_name;
    private String parent_item_unit;
    private String child_item_name;
    private String child_item_unit;

    /**
     * @return the item_combination_id
     */
    public long getItem_combination_id() {
        return item_combination_id;
    }

    /**
     * @param item_combination_id the item_combination_id to set
     */
    public void setItem_combination_id(long item_combination_id) {
        this.item_combination_id = item_combination_id;
    }

    /**
     * @return the parent_item_id
     */
    public long getParent_item_id() {
        return parent_item_id;
    }

    /**
     * @param parent_item_id the parent_item_id to set
     */
    public void setParent_item_id(long parent_item_id) {
        this.parent_item_id = parent_item_id;
    }

    /**
     * @return the child_item_id
     */
    public long getChild_item_id() {
        return child_item_id;
    }

    /**
     * @param child_item_id the child_item_id to set
     */
    public void setChild_item_id(long child_item_id) {
        this.child_item_id = child_item_id;
    }

    /**
     * @return the child_qty
     */
    public double getChild_qty() {
        return child_qty;
    }

    /**
     * @param child_qty the child_qty to set
     */
    public void setChild_qty(double child_qty) {
        this.child_qty = child_qty;
    }

    /**
     * @return the parent_item_name
     */
    public String getParent_item_name() {
        return parent_item_name;
    }

    /**
     * @param parent_item_name the parent_item_name to set
     */
    public void setParent_item_name(String parent_item_name) {
        this.parent_item_name = parent_item_name;
    }

    /**
     * @return the parent_item_unit
     */
    public String getParent_item_unit() {
        return parent_item_unit;
    }

    /**
     * @param parent_item_unit the parent_item_unit to set
     */
    public void setParent_item_unit(String parent_item_unit) {
        this.parent_item_unit = parent_item_unit;
    }

    /**
     * @return the child_item_name
     */
    public String getChild_item_name() {
        return child_item_name;
    }

    /**
     * @param child_item_name the child_item_name to set
     */
    public void setChild_item_name(String child_item_name) {
        this.child_item_name = child_item_name;
    }

    /**
     * @return the child_item_unit
     */
    public String getChild_item_unit() {
        return child_item_unit;
    }

    /**
     * @param child_item_unit the child_item_unit to set
     */
    public void setChild_item_unit(String child_item_unit) {
        this.child_item_unit = child_item_unit;
    }
}
