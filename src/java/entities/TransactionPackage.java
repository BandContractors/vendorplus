package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

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
public class TransactionPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    private long transactionPackageId;
    private long transactionId;
    private String transactionNumber;
    private Date transactionDate;
    private int storeId;
    private int store2Id;
    private long transactorId;
    private int transactionTypeId;
    private int transactionReasonId;
    private double subTotal;
    private double totalTradeDiscount;
    private double totalTax;
    private double cashDiscount;
    private double grandTotal;
    private String transactionRef;
    private int addUserDetailId;
    private Date addDate;
    private int editUserDetailId;
    private Date editDate;
    private int TransactionUserDetailId;
    private String currencyCode;
    private int locationId;
    private String statusCode;
    private Date statusDate;
    private List<TransactionPackageItem> aTransactionPackageItemsList = new ArrayList<>();

    /**
     * @return the transactionPackageId
     */
    public long getTransactionPackageId() {
        return transactionPackageId;
    }

    /**
     * @param transactionPackageId the transactionPackageId to set
     */
    public void setTransactionPackageId(long transactionPackageId) {
        this.transactionPackageId = transactionPackageId;
    }

    /**
     * @return the transactionNumber
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * @param transactionNumber the transactionNumber to set
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * @return the transactionDate
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * @return the storeId
     */
    public int getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the store2Id
     */
    public int getStore2Id() {
        return store2Id;
    }

    /**
     * @param store2Id the store2Id to set
     */
    public void setStore2Id(int store2Id) {
        this.store2Id = store2Id;
    }

    /**
     * @return the transactorId
     */
    public long getTransactorId() {
        return transactorId;
    }

    /**
     * @param transactorId the transactorId to set
     */
    public void setTransactorId(long transactorId) {
        this.transactorId = transactorId;
    }

    /**
     * @return the transactionTypeId
     */
    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    /**
     * @param transactionTypeId the transactionTypeId to set
     */
    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    /**
     * @return the transactionReasonId
     */
    public int getTransactionReasonId() {
        return transactionReasonId;
    }

    /**
     * @param transactionReasonId the transactionReasonId to set
     */
    public void setTransactionReasonId(int transactionReasonId) {
        this.transactionReasonId = transactionReasonId;
    }

    /**
     * @return the subTotal
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the subTotal to set
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return the totalTradeDiscount
     */
    public double getTotalTradeDiscount() {
        return totalTradeDiscount;
    }

    /**
     * @param totalTradeDiscount the totalTradeDiscount to set
     */
    public void setTotalTradeDiscount(double totalTradeDiscount) {
        this.totalTradeDiscount = totalTradeDiscount;
    }

    /**
     * @return the totalTax
     */
    public double getTotalTax() {
        return totalTax;
    }

    /**
     * @param totalTax the totalTax to set
     */
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    /**
     * @return the cashDiscount
     */
    public double getCashDiscount() {
        return cashDiscount;
    }

    /**
     * @param cashDiscount the cashDiscount to set
     */
    public void setCashDiscount(double cashDiscount) {
        this.cashDiscount = cashDiscount;
    }

    /**
     * @return the grandTotal
     */
    public double getGrandTotal() {
        return grandTotal;
    }

    /**
     * @param grandTotal the grandTotal to set
     */
    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    /**
     * @return the transactionRef
     */
    public String getTransactionRef() {
        return transactionRef;
    }

    /**
     * @param transactionRef the transactionRef to set
     */
    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    /**
     * @return the addUserDetailId
     */
    public int getAddUserDetailId() {
        return addUserDetailId;
    }

    /**
     * @param addUserDetailId the addUserDetailId to set
     */
    public void setAddUserDetailId(int addUserDetailId) {
        this.addUserDetailId = addUserDetailId;
    }

    /**
     * @return the addDate
     */
    public Date getAddDate() {
        return addDate;
    }

    /**
     * @param addDate the addDate to set
     */
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    /**
     * @return the editUserDetailId
     */
    public int getEditUserDetailId() {
        return editUserDetailId;
    }

    /**
     * @param editUserDetailId the editUserDetailId to set
     */
    public void setEditUserDetailId(int editUserDetailId) {
        this.editUserDetailId = editUserDetailId;
    }

    /**
     * @return the editDate
     */
    public Date getEditDate() {
        return editDate;
    }

    /**
     * @param editDate the editDate to set
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    /**
     * @return the TransactionUserDetailId
     */
    public int getTransactionUserDetailId() {
        return TransactionUserDetailId;
    }

    /**
     * @param TransactionUserDetailId the TransactionUserDetailId to set
     */
    public void setTransactionUserDetailId(int TransactionUserDetailId) {
        this.TransactionUserDetailId = TransactionUserDetailId;
    }

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the locationId
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusDate
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the transactionId
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the aTransactionPackageItemsList
     */
    public List<TransactionPackageItem> getaTransactionPackageItemsList() {
        return aTransactionPackageItemsList;
    }

    /**
     * @param aTransactionPackageItemsList the aTransactionPackageItemsList to set
     */
    public void setaTransactionPackageItemsList(List<TransactionPackageItem> aTransactionPackageItemsList) {
        this.aTransactionPackageItemsList = aTransactionPackageItemsList;
    }

}
