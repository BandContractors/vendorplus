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
public class AccChildAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccChildAccountId;
    private int AccCoaId;
    private String AccCoaAccountCode;
    private String ChildAccountCode;
    private String ChildAccountName;
    private String ChildAccountDesc;
    private int UserDetailId;
    private int StoreId;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;
    private int CurrencyId;
    private String CurrencyCode;
    private int balance_checker_on;


    /**
     * @return the AccChildAccountId
     */
    public int getAccChildAccountId() {
        return AccChildAccountId;
    }

    /**
     * @param AccChildAccountId the AccChildAccountId to set
     */
    public void setAccChildAccountId(int AccChildAccountId) {
        this.AccChildAccountId = AccChildAccountId;
    }

    /**
     * @return the AccCoaId
     */
    public int getAccCoaId() {
        return AccCoaId;
    }

    /**
     * @param AccCoaId the AccCoaId to set
     */
    public void setAccCoaId(int AccCoaId) {
        this.AccCoaId = AccCoaId;
    }

    /**
     * @return the AccCoaAccountCode
     */
    public String getAccCoaAccountCode() {
        return AccCoaAccountCode;
    }

    /**
     * @param AccCoaAccountCode the AccCoaAccountCode to set
     */
    public void setAccCoaAccountCode(String AccCoaAccountCode) {
        this.AccCoaAccountCode = AccCoaAccountCode;
    }

    /**
     * @return the ChildAccountCode
     */
    public String getChildAccountCode() {
        return ChildAccountCode;
    }

    /**
     * @param ChildAccountCode the ChildAccountCode to set
     */
    public void setChildAccountCode(String ChildAccountCode) {
        this.ChildAccountCode = ChildAccountCode;
    }

    /**
     * @return the ChildAccountName
     */
    public String getChildAccountName() {
        return ChildAccountName;
    }

    /**
     * @param ChildAccountName the ChildAccountName to set
     */
    public void setChildAccountName(String ChildAccountName) {
        this.ChildAccountName = ChildAccountName;
    }

    /**
     * @return the ChildAccountDesc
     */
    public String getChildAccountDesc() {
        return ChildAccountDesc;
    }

    /**
     * @param ChildAccountDesc the ChildAccountDesc to set
     */
    public void setChildAccountDesc(String ChildAccountDesc) {
        this.ChildAccountDesc = ChildAccountDesc;
    }

    /**
     * @return the UserDetailId
     */
    public int getUserDetailId() {
        return UserDetailId;
    }

    /**
     * @param UserDetailId the UserDetailId to set
     */
    public void setUserDetailId(int UserDetailId) {
        this.UserDetailId = UserDetailId;
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
     * @return the CurrencyId
     */
    public int getCurrencyId() {
        return CurrencyId;
    }

    /**
     * @param CurrencyId the CurrencyId to set
     */
    public void setCurrencyId(int CurrencyId) {
        this.CurrencyId = CurrencyId;
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
     * @return the balance_checker_on
     */
    public int getBalance_checker_on() {
        return balance_checker_on;
    }

    /**
     * @param balance_checker_on the balance_checker_on to set
     */
    public void setBalance_checker_on(int balance_checker_on) {
        this.balance_checker_on = balance_checker_on;
    }
    
}
