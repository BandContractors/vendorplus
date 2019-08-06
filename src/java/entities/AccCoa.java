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
public class AccCoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccCoaId;
    private String AccountCode;
    private String AccountName;
    private String AccountDesc;
    private int AccClassId;
    private int AccTypeId;
    private int AccGroupId;
    private int AccCategoryId;
    private int OrderCoa;
    private int IsActive;
    private int IsDeleted;
    private int IsChild;
    private int IsTransactorMandatory;
    private int IsSystemAccount;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;

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
     * @return the AccountCode
     */
    public String getAccountCode() {
        return AccountCode;
    }

    /**
     * @param AccountCode the AccountCode to set
     */
    public void setAccountCode(String AccountCode) {
        this.AccountCode = AccountCode;
    }

    /**
     * @return the AccountName
     */
    public String getAccountName() {
        return AccountName;
    }

    /**
     * @param AccountName the AccountName to set
     */
    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    /**
     * @return the AccountDesc
     */
    public String getAccountDesc() {
        return AccountDesc;
    }

    /**
     * @param AccountDesc the AccountDesc to set
     */
    public void setAccountDesc(String AccountDesc) {
        this.AccountDesc = AccountDesc;
    }

    /**
     * @return the AccClassId
     */
    public int getAccClassId() {
        return AccClassId;
    }

    /**
     * @param AccClassId the AccClassId to set
     */
    public void setAccClassId(int AccClassId) {
        this.AccClassId = AccClassId;
    }

    /**
     * @return the AccTypeId
     */
    public int getAccTypeId() {
        return AccTypeId;
    }

    /**
     * @param AccTypeId the AccTypeId to set
     */
    public void setAccTypeId(int AccTypeId) {
        this.AccTypeId = AccTypeId;
    }

    /**
     * @return the AccGroupId
     */
    public int getAccGroupId() {
        return AccGroupId;
    }

    /**
     * @param AccGroupId the AccGroupId to set
     */
    public void setAccGroupId(int AccGroupId) {
        this.AccGroupId = AccGroupId;
    }

    /**
     * @return the AccCategoryId
     */
    public int getAccCategoryId() {
        return AccCategoryId;
    }

    /**
     * @param AccCategoryId the AccCategoryId to set
     */
    public void setAccCategoryId(int AccCategoryId) {
        this.AccCategoryId = AccCategoryId;
    }

    /**
     * @return the OrderCoa
     */
    public int getOrderCoa() {
        return OrderCoa;
    }

    /**
     * @param OrderCoa the OrderCoa to set
     */
    public void setOrderCoa(int OrderCoa) {
        this.OrderCoa = OrderCoa;
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
     * @return the IsChild
     */
    public int getIsChild() {
        return IsChild;
    }

    /**
     * @param IsChild the IsChild to set
     */
    public void setIsChild(int IsChild) {
        this.IsChild = IsChild;
    }

    /**
     * @return the IsTransactorMandatory
     */
    public int getIsTransactorMandatory() {
        return IsTransactorMandatory;
    }

    /**
     * @param IsTransactorMandatory the IsTransactorMandatory to set
     */
    public void setIsTransactorMandatory(int IsTransactorMandatory) {
        this.IsTransactorMandatory = IsTransactorMandatory;
    }

    /**
     * @return the IsSystemAccount
     */
    public int getIsSystemAccount() {
        return IsSystemAccount;
    }

    /**
     * @param IsSystemAccount the IsSystemAccount to set
     */
    public void setIsSystemAccount(int IsSystemAccount) {
        this.IsSystemAccount = IsSystemAccount;
    }

}
