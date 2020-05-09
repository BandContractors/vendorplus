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
public class AccJournal implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long AccJournalId;
    private Date JournalDate;
    private long TransactionId;
    private int TransactionTypeId;
    private int TransactionReasonId;
    private long PayId;
    private int PayTypeId;
    private int PayReasonId;
    private int StoreId;
    private long BillTransactorId;
    private String LedgerFolio;
    private int AccCoaId;
    private String AccountCode;
    private double DebitAmount;
    private double CreditAmount;
    private String Narration;
    private int AccPeriodId;
    private int AccChildAccountId;
    private String CurrencyCode;
    private double Xrate;
    private int IsActive;
    private int IsDeleted;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;
    private long JobId;
    //start-for income statement dialog
    private String transaction_type_name;
    private String transaction_number;
    private String account_name;
    private double amount;
    //end-for income statement dialog

    /**
     * @return the AccJournalId
     */
    public long getAccJournalId() {
        return AccJournalId;
    }

    /**
     * @param AccJournalId the AccJournalId to set
     */
    public void setAccJournalId(long AccJournalId) {
        this.AccJournalId = AccJournalId;
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
     * @return the TransactionTypeId
     */
    public int getTransactionTypeId() {
        return TransactionTypeId;
    }

    /**
     * @param TransactionTypeId the TransactionTypeId to set
     */
    public void setTransactionTypeId(int TransactionTypeId) {
        this.TransactionTypeId = TransactionTypeId;
    }

    /**
     * @return the TransactionReasonId
     */
    public int getTransactionReasonId() {
        return TransactionReasonId;
    }

    /**
     * @param TransactionReasonId the TransactionReasonId to set
     */
    public void setTransactionReasonId(int TransactionReasonId) {
        this.TransactionReasonId = TransactionReasonId;
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
     * @return the LedgerFolio
     */
    public String getLedgerFolio() {
        return LedgerFolio;
    }

    /**
     * @param LedgerFolio the LedgerFolio to set
     */
    public void setLedgerFolio(String LedgerFolio) {
        this.LedgerFolio = LedgerFolio;
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
     * @return the Narration
     */
    public String getNarration() {
        return Narration;
    }

    /**
     * @param Narration the Narration to set
     */
    public void setNarration(String Narration) {
        this.Narration = Narration;
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
     * @return the Xrate
     */
    public double getXrate() {
        return Xrate;
    }

    /**
     * @param Xrate the Xrate to set
     */
    public void setXrate(double Xrate) {
        this.Xrate = Xrate;
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
     * @return the PayTypeId
     */
    public int getPayTypeId() {
        return PayTypeId;
    }

    /**
     * @param PayTypeId the PayTypeId to set
     */
    public void setPayTypeId(int PayTypeId) {
        this.PayTypeId = PayTypeId;
    }

    /**
     * @return the PayReasonId
     */
    public int getPayReasonId() {
        return PayReasonId;
    }

    /**
     * @param PayReasonId the PayReasonId to set
     */
    public void setPayReasonId(int PayReasonId) {
        this.PayReasonId = PayReasonId;
    }

    /**
     * @return the JournalDate
     */
    public Date getJournalDate() {
        return JournalDate;
    }

    /**
     * @param JournalDate the JournalDate to set
     */
    public void setJournalDate(Date JournalDate) {
        this.JournalDate = JournalDate;
    }

    /**
     * @return the JobId
     */
    public long getJobId() {
        return JobId;
    }

    /**
     * @param JobId the JobId to set
     */
    public void setJobId(long JobId) {
        this.JobId = JobId;
    }

    /**
     * @return the transaction_type_name
     */
    public String getTransaction_type_name() {
        return transaction_type_name;
    }

    /**
     * @param transaction_type_name the transaction_type_name to set
     */
    public void setTransaction_type_name(String transaction_type_name) {
        this.transaction_type_name = transaction_type_name;
    }

    /**
     * @return the transaction_number
     */
    public String getTransaction_number() {
        return transaction_number;
    }

    /**
     * @param transaction_number the transaction_number to set
     */
    public void setTransaction_number(String transaction_number) {
        this.transaction_number = transaction_number;
    }

    /**
     * @return the account_name
     */
    public String getAccount_name() {
        return account_name;
    }

    /**
     * @param account_name the account_name to set
     */
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
}
