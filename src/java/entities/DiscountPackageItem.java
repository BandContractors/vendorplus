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
public class DiscountPackageItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long DiscountPackageItemId;
    private int DiscountPackageId;
    private int StoreId;
    private long ItemId;
    private double ItemQty=1;
    private double WholesaleDiscountAmt;
    private double RetailsaleDiscountAmt;
    private double hire_price_discount_amt;

    /**
     * @return the DiscountPackageItemId
     */
    public long getDiscountPackageItemId() {
        return DiscountPackageItemId;
    }

    /**
     * @param DiscountPackageItemId the DiscountPackageItemId to set
     */
    public void setDiscountPackageItemId(long DiscountPackageItemId) {
        this.DiscountPackageItemId = DiscountPackageItemId;
    }

    /**
     * @return the DiscountPackageId
     */
    public int getDiscountPackageId() {
        return DiscountPackageId;
    }

    /**
     * @param DiscountPackageId the DiscountPackageId to set
     */
    public void setDiscountPackageId(int DiscountPackageId) {
        this.DiscountPackageId = DiscountPackageId;
    }

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
     * @return the ItemId
     */
    public long getItemId() {
        return ItemId;
    }

    /**
     * @param ItemId the ItemId to set
     */
    public void setItemId(long ItemId) {
        this.ItemId = ItemId;
    }

    /**
     * @return the ItemQty
     */
    public double getItemQty() {
        return ItemQty;
    }

    /**
     * @param ItemQty the ItemQty to set
     */
    public void setItemQty(double ItemQty) {
        this.ItemQty = ItemQty;
    }

    /**
     * @return the WholesaleDiscountAmt
     */
    public double getWholesaleDiscountAmt() {
        return WholesaleDiscountAmt;
    }

    /**
     * @param WholesaleDiscountAmt the WholesaleDiscountAmt to set
     */
    public void setWholesaleDiscountAmt(double WholesaleDiscountAmt) {
        this.WholesaleDiscountAmt = WholesaleDiscountAmt;
    }

    /**
     * @return the RetailsaleDiscountAmt
     */
    public double getRetailsaleDiscountAmt() {
        return RetailsaleDiscountAmt;
    }

    /**
     * @param RetailsaleDiscountAmt the RetailsaleDiscountAmt to set
     */
    public void setRetailsaleDiscountAmt(double RetailsaleDiscountAmt) {
        this.RetailsaleDiscountAmt = RetailsaleDiscountAmt;
    }

    /**
     * @return the hire_price_discount_amt
     */
    public double getHire_price_discount_amt() {
        return hire_price_discount_amt;
    }

    /**
     * @param hire_price_discount_amt the hire_price_discount_amt to set
     */
    public void setHire_price_discount_amt(double hire_price_discount_amt) {
        this.hire_price_discount_amt = hire_price_discount_amt;
    }
    
}
