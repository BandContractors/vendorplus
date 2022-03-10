package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Item_store_reorder implements Serializable {

    private static final long serialVersionUID = 1L;
    private long item_store_reorder_id;
    private long item_id;
    private int store_id;
    private double reorder_level;
    private String description;
    private String store_name;

    /**
     * @return the item_store_reorder_id
     */
    public long getItem_store_reorder_id() {
        return item_store_reorder_id;
    }

    /**
     * @param item_store_reorder_id the item_store_reorder_id to set
     */
    public void setItem_store_reorder_id(long item_store_reorder_id) {
        this.item_store_reorder_id = item_store_reorder_id;
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
     * @return the reorder_level
     */
    public double getReorder_level() {
        return reorder_level;
    }

    /**
     * @param reorder_level the reorder_level to set
     */
    public void setReorder_level(double reorder_level) {
        this.reorder_level = reorder_level;
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
     * @return the store_name
     */
    public String getStore_name() {
        return store_name;
    }

    /**
     * @param store_name the store_name to set
     */
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

}
