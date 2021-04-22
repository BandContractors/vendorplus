package test;

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
public class IntfTransItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transItemId;
    private long transId;
    private String itemName;
    private double itemAmount;

    public IntfTransItem() {
        this.transItemId = 0;
        this.transId = 0;
        this.itemName = "";
        this.itemAmount = 0;
    }

    /**
     * @return the transItemId
     */
    public long getTransItemId() {
        return transItemId;
    }

    /**
     * @param transItemId the transItemId to set
     */
    public void setTransItemId(long transItemId) {
        this.transItemId = transItemId;
    }

    /**
     * @return the transId
     */
    public long getTransId() {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId(long transId) {
        this.transId = transId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemAmount
     */
    public double getItemAmount() {
        return itemAmount;
    }

    /**
     * @param itemAmount the itemAmount to set
     */
    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

}
