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
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    private int StoreId;
    private String StoreName;
    private String store_code;
    private int shift_id;

    /**
     * @return the StoreId
     */
    public int getStoreId() {
        return StoreId;
    }

    /**
     * @param StoreId the StoreId to set
     */
    public void setStoreId(int StoreId) {
        this.StoreId = StoreId;
    }

    /**
     * @return the StoreName
     */
    public String getStoreName() {
        return StoreName;
    }

    /**
     * @param StoreName the StoreName to set
     */
    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    /**
     * @return the store_code
     */
    public String getStore_code() {
        return store_code;
    }

    /**
     * @param store_code the store_code to set
     */
    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    /**
     * @return the shift_id
     */
    public int getShift_id() {
        return shift_id;
    }

    /**
     * @param shift_id the shift_id to set
     */
    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    
}
