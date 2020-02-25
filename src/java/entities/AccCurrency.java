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
public class AccCurrency implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccCurrencyId;
    private String CurrencyName;
    private String CurrencyCode;
    private int CurrencyNo;
    private int IsLocalCurrency;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;
    private double Buying;
    private double Selling;
    private int decimal_places;
    private int rounding_mode;
    private String currency_unit;
    private String decimal_unit;

    /**
     * @return the AccCurrencyId
     */
    public int getAccCurrencyId() {
        return AccCurrencyId;
    }

    /**
     * @param AccCurrencyId the AccCurrencyId to set
     */
    public void setAccCurrencyId(int AccCurrencyId) {
        this.AccCurrencyId = AccCurrencyId;
    }

    /**
     * @return the CurrencyName
     */
    public String getCurrencyName() {
        return CurrencyName;
    }

    /**
     * @param CurrencyName the CurrencyName to set
     */
    public void setCurrencyName(String CurrencyName) {
        this.CurrencyName = CurrencyName;
    }

    /**
     * @return the CurrencyCode
     */
    public String getCurrencyCode() {
        return CurrencyCode;
    }

    /**
     * @param CurrencyCode the CurrencyCode to set
     */
    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    /**
     * @return the CurrencyNo
     */
    public int getCurrencyNo() {
        return CurrencyNo;
    }

    /**
     * @param CurrencyNo the CurrencyNo to set
     */
    public void setCurrencyNo(int CurrencyNo) {
        this.CurrencyNo = CurrencyNo;
    }

    /**
     * @return the IsLocalCurrency
     */
    public int getIsLocalCurrency() {
        return IsLocalCurrency;
    }

    /**
     * @param IsLocalCurrency the IsLocalCurrency to set
     */
    public void setIsLocalCurrency(int IsLocalCurrency) {
        this.IsLocalCurrency = IsLocalCurrency;
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
     * @return the decimal_places
     */
    public int getDecimal_places() {
        return decimal_places;
    }

    /**
     * @param decimal_places the decimal_places to set
     */
    public void setDecimal_places(int decimal_places) {
        this.decimal_places = decimal_places;
    }

    /**
     * @return the rounding_mode
     */
    public int getRounding_mode() {
        return rounding_mode;
    }

    /**
     * @param rounding_mode the rounding_mode to set
     */
    public void setRounding_mode(int rounding_mode) {
        this.rounding_mode = rounding_mode;
    }

    /**
     * @return the currency_unit
     */
    public String getCurrency_unit() {
        return currency_unit;
    }

    /**
     * @param currency_unit the currency_unit to set
     */
    public void setCurrency_unit(String currency_unit) {
        this.currency_unit = currency_unit;
    }

    /**
     * @return the decimal_unit
     */
    public String getDecimal_unit() {
        return decimal_unit;
    }

    /**
     * @param decimal_unit the decimal_unit to set
     */
    public void setDecimal_unit(String decimal_unit) {
        this.decimal_unit = decimal_unit;
    }

}
