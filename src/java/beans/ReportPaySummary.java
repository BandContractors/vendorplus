package beans;


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
public class ReportPaySummary implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String FieldName;
    private double SumPaidAmount;
    private double SumPointsSpentAmount;
    private int StoreId;
    private int TransactionTypeId;
    private double CashIn;
    private double CashOut;
    private double CashNet;

    /**
     * @return the FieldName
     */
    public String getFieldName() {
        return FieldName;
    }

    /**
     * @param FieldName the FieldName to set
     */
    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    /**
     * @return the SumPaidAmount
     */
    public double getSumPaidAmount() {
        return SumPaidAmount;
    }

    /**
     * @param SumPaidAmount the SumPaidAmount to set
     */
    public void setSumPaidAmount(double SumPaidAmount) {
        this.SumPaidAmount = SumPaidAmount;
    }

    /**
     * @return the SumPointsSpentAmount
     */
    public double getSumPointsSpentAmount() {
        return SumPointsSpentAmount;
    }

    /**
     * @param SumPointsSpentAmount the SumPointsSpentAmount to set
     */
    public void setSumPointsSpentAmount(double SumPointsSpentAmount) {
        this.SumPointsSpentAmount = SumPointsSpentAmount;
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
     * @return the CashIn
     */
    public double getCashIn() {
        return CashIn;
    }

    /**
     * @param CashIn the CashIn to set
     */
    public void setCashIn(double CashIn) {
        this.CashIn = CashIn;
    }

    /**
     * @return the CashOut
     */
    public double getCashOut() {
        return CashOut;
    }

    /**
     * @param CashOut the CashOut to set
     */
    public void setCashOut(double CashOut) {
        this.CashOut = CashOut;
    }

    /**
     * @return the CashNet
     */
    public double getCashNet() {
        return CashNet;
    }

    /**
     * @param CashNet the CashNet to set
     */
    public void setCashNet(double CashNet) {
        this.CashNet = CashNet;
    }
}
