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
    
}
