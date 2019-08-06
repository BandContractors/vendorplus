package entities;


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
public class AccXrate implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccXrateId;
    private int LocalCurrencyId;
    private int ForeignCurrencyId;
    private String LocalCurrencyCode;
    private String ForeignCurrencyCode;
    private double Buying;
    private double Selling;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;

    /**
     * @return the LocalCurrencyId
     */
    public int getLocalCurrencyId() {
        return LocalCurrencyId;
    }

    /**
     * @param LocalCurrencyId the LocalCurrencyId to set
     */
    public void setLocalCurrencyId(int LocalCurrencyId) {
        this.LocalCurrencyId = LocalCurrencyId;
    }

    /**
     * @return the ForeignCurrencyId
     */
    public int getForeignCurrencyId() {
        return ForeignCurrencyId;
    }

    /**
     * @param ForeignCurrencyId the ForeignCurrencyId to set
     */
    public void setForeignCurrencyId(int ForeignCurrencyId) {
        this.ForeignCurrencyId = ForeignCurrencyId;
    }

    /**
     * @return the LocalCurrencyCode
     */
    public String getLocalCurrencyCode() {
        return LocalCurrencyCode;
    }

    /**
     * @param LocalCurrencyCode the LocalCurrencyCode to set
     */
    public void setLocalCurrencyCode(String LocalCurrencyCode) {
        this.LocalCurrencyCode = LocalCurrencyCode;
    }

    /**
     * @return the ForeignCurrencyCode
     */
    public String getForeignCurrencyCode() {
        return ForeignCurrencyCode;
    }

    /**
     * @param ForeignCurrencyCode the ForeignCurrencyCode to set
     */
    public void setForeignCurrencyCode(String ForeignCurrencyCode) {
        this.ForeignCurrencyCode = ForeignCurrencyCode;
    }

    /**
     * @return the IsActive
     */
    public int getIsActive() {
        return IsActive;
    }

    /**
     * @param IsActive the IsActive to set
     */
    public void setIsActive(int IsActive) {
        this.IsActive = IsActive;
    }

    /**
     * @return the IsDeleted
     */
    public int getIsDeleted() {
        return IsDeleted;
    }

    /**
     * @param IsDeleted the IsDeleted to set
     */
    public void setIsDeleted(int IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    /**
     * @return the AddDate
     */
    public Date getAddDate() {
        return AddDate;
    }

    /**
     * @param AddDate the AddDate to set
     */
    public void setAddDate(Date AddDate) {
        this.AddDate = AddDate;
    }

    /**
     * @return the AddBy
     */
    public int getAddBy() {
        return AddBy;
    }

    /**
     * @param AddBy the AddBy to set
     */
    public void setAddBy(int AddBy) {
        this.AddBy = AddBy;
    }

    /**
     * @return the LastEditDate
     */
    public Date getLastEditDate() {
        return LastEditDate;
    }

    /**
     * @param LastEditDate the LastEditDate to set
     */
    public void setLastEditDate(Date LastEditDate) {
        this.LastEditDate = LastEditDate;
    }

    /**
     * @return the LastEditBy
     */
    public int getLastEditBy() {
        return LastEditBy;
    }

    /**
     * @param LastEditBy the LastEditBy to set
     */
    public void setLastEditBy(int LastEditBy) {
        this.LastEditBy = LastEditBy;
    }

    /**
     * @return the Buying
     */
    public double getBuying() {
        return Buying;
    }

    /**
     * @param Buying the Buying to set
     */
    public void setBuying(double Buying) {
        this.Buying = Buying;
    }

    /**
     * @return the Selling
     */
    public double getSelling() {
        return Selling;
    }

    /**
     * @param Selling the Selling to set
     */
    public void setSelling(double Selling) {
        this.Selling = Selling;
    }

    /**
     * @return the AccXrateId
     */
    public int getAccXrateId() {
        return AccXrateId;
    }

    /**
     * @param AccXrateId the AccXrateId to set
     */
    public void setAccXrateId(int AccXrateId) {
        this.AccXrateId = AccXrateId;
    }

}
