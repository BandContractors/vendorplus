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
public class AccLedger implements Serializable {

    private static final long serialVersionUID = 1L;

    private long AccLedgerId;
    private int AccPeriodId;
    private long BillTransactorId;
    private String AccountCode;
    private int AccChildAccountId;
    private String CurrencyCode;
    private double DebitAmount;
    private double CreditAmount;
    private double DebitAmountLc;
    private double CreditAmountLc;
    private String TransactorName;
    private String TransactorRef;
    //start-for balance sheet dialog
    private String Category;
    private String Subcategory;
    private double Amount;
    //end-for balance sheet dialog
    private String child_account_name;
    private String parent_account_name;

    /**
     * @return the AccLedgerId
     */
    public long getAccLedgerId() {
        return AccLedgerId;
    }

    /**
     * @param AccLedgerId the AccLedgerId to set
     */
    public void setAccLedgerId(long AccLedgerId) {
        this.AccLedgerId = AccLedgerId;
    }

    /**
     * @return the AccPeriodId
     */
    public int getAccPeriodId() {
        return AccPeriodId;
    }

    /**
     * @param AccPeriodId the AccPeriodId to set
     */
    public void setAccPeriodId(int AccPeriodId) {
        this.AccPeriodId = AccPeriodId;
    }

    /**
     * @return the BillTransactorId
     */
    public long getBillTransactorId() {
        return BillTransactorId;
    }

    /**
     * @param BillTransactorId the BillTransactorId to set
     */
    public void setBillTransactorId(long BillTransactorId) {
        this.BillTransactorId = BillTransactorId;
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
     * @return the DebitAmount
     */
    public double getDebitAmount() {
        return DebitAmount;
    }

    /**
     * @param DebitAmount the DebitAmount to set
     */
    public void setDebitAmount(double DebitAmount) {
        this.DebitAmount = DebitAmount;
    }

    /**
     * @return the CreditAmount
     */
    public double getCreditAmount() {
        return CreditAmount;
    }

    /**
     * @param CreditAmount the CreditAmount to set
     */
    public void setCreditAmount(double CreditAmount) {
        this.CreditAmount = CreditAmount;
    }

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
     * @return the DebitAmountLc
     */
    public double getDebitAmountLc() {
        return DebitAmountLc;
    }

    /**
     * @param DebitAmountLc the DebitAmountLc to set
     */
    public void setDebitAmountLc(double DebitAmountLc) {
        this.DebitAmountLc = DebitAmountLc;
    }

    /**
     * @return the CreditAmountLc
     */
    public double getCreditAmountLc() {
        return CreditAmountLc;
    }

    /**
     * @param CreditAmountLc the CreditAmountLc to set
     */
    public void setCreditAmountLc(double CreditAmountLc) {
        this.CreditAmountLc = CreditAmountLc;
    }

    /**
     * @return the TransactorName
     */
    public String getTransactorName() {
        return TransactorName;
    }

    /**
     * @param TransactorName the TransactorName to set
     */
    public void setTransactorName(String TransactorName) {
        this.TransactorName = TransactorName;
    }

    /**
     * @return the TransactorRef
     */
    public String getTransactorRef() {
        return TransactorRef;
    }

    /**
     * @param TransactorRef the TransactorRef to set
     */
    public void setTransactorRef(String TransactorRef) {
        this.TransactorRef = TransactorRef;
    }

    /**
     * @return the Category
     */
    public String getCategory() {
        return Category;
    }

    /**
     * @param Category the Category to set
     */
    public void setCategory(String Category) {
        this.Category = Category;
    }

    /**
     * @return the Subcategory
     */
    public String getSubcategory() {
        return Subcategory;
    }

    /**
     * @param Subcategory the Subcategory to set
     */
    public void setSubcategory(String Subcategory) {
        this.Subcategory = Subcategory;
    }

    /**
     * @return the Amount
     */
    public double getAmount() {
        return Amount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the child_account_name
     */
    public String getChild_account_name() {
        return child_account_name;
    }

    /**
     * @param child_account_name the child_account_name to set
     */
    public void setChild_account_name(String child_account_name) {
        this.child_account_name = child_account_name;
    }

    /**
     * @return the parent_account_name
     */
    public String getParent_account_name() {
        return parent_account_name;
    }

    /**
     * @param parent_account_name the parent_account_name to set
     */
    public void setParent_account_name(String parent_account_name) {
        this.parent_account_name = parent_account_name;
    }

}
