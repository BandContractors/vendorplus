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
public class TransactorLedger implements Serializable {

    private static final long serialVersionUID = 1L;

    private long TransactorLedgerId;
    private int StoreId;
    private String StoreName;
    private long TransactionId;
    private long PayId;
    private String TransactionTypeName;
    private String Description;
    private Date TransactionDate;
    private Date TransactionDate2;
    private Date AddDate;
    private Date AddDate2;
    private long TransactorId;
    private String TransactorNames;
    private String LedgerEntryType;
    private double AmountDebit;
    private double AmountCredit;
    private double SumAmountDebit;
    private double SumAmountCredit;
    private double NetDebt;
    private double NetCredit;
    private long BillTransactorId;
    private String BillTransactorNames;
    //just for display
    private double NetDebtCreditAmount;
    private String NetDebtCreditLabel;

    /**
     * @return the TransactorLedgerId
     */
    public long getTransactorLedgerId() {
        return TransactorLedgerId;
    }

    /**
     * @param TransactorLedgerId the TransactorLedgerId to set
     */
    public void setTransactorLedgerId(long TransactorLedgerId) {
        this.TransactorLedgerId = TransactorLedgerId;
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
     * @return the TransactionId
     */
    public long getTransactionId() {
        return TransactionId;
    }

    /**
     * @param TransactionId the TransactionId to set
     */
    public void setTransactionId(long TransactionId) {
        this.TransactionId = TransactionId;
    }

    /**
     * @return the PayId
     */
    public long getPayId() {
        return PayId;
    }

    /**
     * @param PayId the PayId to set
     */
    public void setPayId(long PayId) {
        this.PayId = PayId;
    }

    /**
     * @return the TransactionTypeName
     */
    public String getTransactionTypeName() {
        return TransactionTypeName;
    }

    /**
     * @param TransactionTypeName the TransactionTypeName to set
     */
    public void setTransactionTypeName(String TransactionTypeName) {
        this.TransactionTypeName = TransactionTypeName;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the TransactionDate
     */
    public Date getTransactionDate() {
        return TransactionDate;
    }

    /**
     * @param TransactionDate the TransactionDate to set
     */
    public void setTransactionDate(Date TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    /**
     * @return the TransactionDate2
     */
    public Date getTransactionDate2() {
        return TransactionDate2;
    }

    /**
     * @param TransactionDate2 the TransactionDate2 to set
     */
    public void setTransactionDate2(Date TransactionDate2) {
        this.TransactionDate2 = TransactionDate2;
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
     * @return the AddDate2
     */
    public Date getAddDate2() {
        return AddDate2;
    }

    /**
     * @param AddDate2 the AddDate2 to set
     */
    public void setAddDate2(Date AddDate2) {
        this.AddDate2 = AddDate2;
    }

    /**
     * @return the TransactorId
     */
    public long getTransactorId() {
        return TransactorId;
    }

    /**
     * @param TransactorId the TransactorId to set
     */
    public void setTransactorId(long TransactorId) {
        this.TransactorId = TransactorId;
    }

    /**
     * @return the TransactorNames
     */
    public String getTransactorNames() {
        return TransactorNames;
    }

    /**
     * @param TransactorNames the TransactorNames to set
     */
    public void setTransactorNames(String TransactorNames) {
        this.TransactorNames = TransactorNames;
    }

    /**
     * @return the LedgerEntryType
     */
    public String getLedgerEntryType() {
        return LedgerEntryType;
    }

    /**
     * @param LedgerEntryType the LedgerEntryType to set
     */
    public void setLedgerEntryType(String LedgerEntryType) {
        this.LedgerEntryType = LedgerEntryType;
    }

    /**
     * @return the AmountDebit
     */
    public double getAmountDebit() {
        return AmountDebit;
    }

    /**
     * @param AmountDebit the AmountDebit to set
     */
    public void setAmountDebit(double AmountDebit) {
        this.AmountDebit = AmountDebit;
    }

    /**
     * @return the AmountCredit
     */
    public double getAmountCredit() {
        return AmountCredit;
    }

    /**
     * @param AmountCredit the AmountCredit to set
     */
    public void setAmountCredit(double AmountCredit) {
        this.AmountCredit = AmountCredit;
    }

    /**
     * @return the SumAmountDebit
     */
    public double getSumAmountDebit() {
        return SumAmountDebit;
    }

    /**
     * @param SumAmountDebit the SumAmountDebit to set
     */
    public void setSumAmountDebit(double SumAmountDebit) {
        this.SumAmountDebit = SumAmountDebit;
    }

    /**
     * @return the SumAmountCredit
     */
    public double getSumAmountCredit() {
        return SumAmountCredit;
    }

    /**
     * @param SumAmountCredit the SumAmountCredit to set
     */
    public void setSumAmountCredit(double SumAmountCredit) {
        this.SumAmountCredit = SumAmountCredit;
    }

    /**
     * @return the NetDebt
     */
    public double getNetDebt() {
        return NetDebt;
    }

    /**
     * @param NetDebt the NetDebt to set
     */
    public void setNetDebt(double NetDebt) {
        this.NetDebt = NetDebt;
    }

    /**
     * @return the NetCredit
     */
    public double getNetCredit() {
        return NetCredit;
    }

    /**
     * @param NetCredit the NetCredit to set
     */
    public void setNetCredit(double NetCredit) {
        this.NetCredit = NetCredit;
    }

    /**
     * @return the NetDebtCreditAmount
     */
    public double getNetDebtCreditAmount() {
        return NetDebtCreditAmount;
    }

    /**
     * @param NetDebtCreditAmount the NetDebtCreditAmount to set
     */
    public void setNetDebtCreditAmount(double NetDebtCreditAmount) {
        this.NetDebtCreditAmount = NetDebtCreditAmount;
    }

    /**
     * @return the NetDebtCreditLabel
     */
    public String getNetDebtCreditLabel() {
        return NetDebtCreditLabel;
    }

    /**
     * @param NetDebtCreditLabel the NetDebtCreditLabel to set
     */
    public void setNetDebtCreditLabel(String NetDebtCreditLabel) {
        this.NetDebtCreditLabel = NetDebtCreditLabel;
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
     * @return the BillTransactorNames
     */
    public String getBillTransactorNames() {
        return BillTransactorNames;
    }

    /**
     * @param BillTransactorNames the BillTransactorNames to set
     */
    public void setBillTransactorNames(String BillTransactorNames) {
        this.BillTransactorNames = BillTransactorNames;
    }
    
}
