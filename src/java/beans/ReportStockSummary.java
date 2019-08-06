package beans;


import java.io.Serializable;
import java.util.Date;
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
public class ReportStockSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String StoreName;
    private String CategoryName;
    private String SubCategoryName;
    private double SumCurrentQty;
    private double SumCostValue;
    private double SumWholesaleValue;
    private double SumRetailsaleValue;

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
     * @return the CategoryName
     */
    public String getCategoryName() {
        return CategoryName;
    }

    /**
     * @param CategoryName the CategoryName to set
     */
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    /**
     * @return the SubCategoryName
     */
    public String getSubCategoryName() {
        return SubCategoryName;
    }

    /**
     * @param SubCategoryName the SubCategoryName to set
     */
    public void setSubCategoryName(String SubCategoryName) {
        this.SubCategoryName = SubCategoryName;
    }

    /**
     * @return the SumCurrentQty
     */
    public double getSumCurrentQty() {
        return SumCurrentQty;
    }

    /**
     * @param SumCurrentQty the SumCurrentQty to set
     */
    public void setSumCurrentQty(double SumCurrentQty) {
        this.SumCurrentQty = SumCurrentQty;
    }

    /**
     * @return the SumCostValue
     */
    public double getSumCostValue() {
        return SumCostValue;
    }

    /**
     * @param SumCostValue the SumCostValue to set
     */
    public void setSumCostValue(double SumCostValue) {
        this.SumCostValue = SumCostValue;
    }

    /**
     * @return the SumWholesaleValue
     */
    public double getSumWholesaleValue() {
        return SumWholesaleValue;
    }

    /**
     * @param SumWholesaleValue the SumWholesaleValue to set
     */
    public void setSumWholesaleValue(double SumWholesaleValue) {
        this.SumWholesaleValue = SumWholesaleValue;
    }

    /**
     * @return the SumRetailsaleValue
     */
    public double getSumRetailsaleValue() {
        return SumRetailsaleValue;
    }

    /**
     * @param SumRetailsaleValue the SumRetailsaleValue to set
     */
    public void setSumRetailsaleValue(double SumRetailsaleValue) {
        this.SumRetailsaleValue = SumRetailsaleValue;
    }

}
