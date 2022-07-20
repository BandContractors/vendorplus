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
public class PayTrans implements Serializable {

    private static final long serialVersionUID = 1L;
    private long PayTransId;
    private long PayId;
    private long TransactionId;
    private String TransactionNumber;
    private double TransPaidAmount;

    private double SumTransPaidAmount;
    private double GrandTotal;
    private String TransactionRef;
    private int TransactionTypeId;
    private int TransactionReasonId;
    private String account_code;
    private double cr_dr_amount;
    private String cr_dr_type;

    /**
     * @return the PayTransId
     */
    public long getPayTransId() {
        return PayTransId;
    }

    /**
     * @param PayTransId the PayTransId to set
     */
    public void setPayTransId(long PayTransId) {
        this.PayTransId = PayTransId;
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
     * @return the TransPaidAmount
     */
    public double getTransPaidAmount() {
        return TransPaidAmount;
    }

    /**
     * @param TransPaidAmount the TransPaidAmount to set
     */
    public void setTransPaidAmount(double TransPaidAmount) {
        this.TransPaidAmount = TransPaidAmount;
    }

    /**
     * @return the SumTransPaidAmount
     */
    public double getSumTransPaidAmount() {
        return SumTransPaidAmount;
    }

    /**
     * @param SumTransPaidAmount the SumTransPaidAmount to set
     */
    public void setSumTransPaidAmount(double SumTransPaidAmount) {
        this.SumTransPaidAmount = SumTransPaidAmount;
    }

    /**
     * @return the TransactionNumber
     */
    public String getTransactionNumber() {
        return TransactionNumber;
    }

    /**
     * @param TransactionNumber the TransactionNumber to set
     */
    public void setTransactionNumber(String TransactionNumber) {
        this.TransactionNumber = TransactionNumber;
    }

    /**
     * @return the GrandTotal
     */
    public double getGrandTotal() {
        return GrandTotal;
    }

    /**
     * @param GrandTotal the GrandTotal to set
     */
    public void setGrandTotal(double GrandTotal) {
        this.GrandTotal = GrandTotal;
    }

    /**
     * @return the TransactionRef
     */
    public String getTransactionRef() {
        return TransactionRef;
    }

    /**
     * @param TransactionRef the TransactionRef to set
     */
    public void setTransactionRef(String TransactionRef) {
        this.TransactionRef = TransactionRef;
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
     * @return the account_code
     */
    public String getAccount_code() {
        return account_code;
    }

    /**
     * @param account_code the account_code to set
     */
    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }

    /**
     * @return the cr_dr_amount
     */
    public double getCr_dr_amount() {
        return cr_dr_amount;
    }

    /**
     * @param cr_dr_amount the cr_dr_amount to set
     */
    public void setCr_dr_amount(double cr_dr_amount) {
        this.cr_dr_amount = cr_dr_amount;
    }

    /**
     * @return the cr_dr_type
     */
    public String getCr_dr_type() {
        return cr_dr_type;
    }

    /**
     * @param cr_dr_type the cr_dr_type to set
     */
    public void setCr_dr_type(String cr_dr_type) {
        this.cr_dr_type = cr_dr_type;
    }

}
