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
public class Pay implements Serializable {

    private static final long serialVersionUID = 1L;
    private long PayId;
    private Date PayDate;
    private double PaidAmount;
    private int PayMethodId;
    private int AddUserDetailId;
    private int EditUserDetailId;
    private Date AddDate;
    private Date EditDate;
    private double PointsSpent;
    private double PointsSpentAmount;
    private long DeletePayId;
    private String PayRefNo;
    private String PayCategory;
    private long BillTransactorId;
    private int StoreId;
    
    private int AccChildAccountId;
    private String CurrencyCode;
    private double XRate;
    private int Status;
    private String StatusDesc;
    private int PayTypeId;
    private int PayReasonId;
    private int AccChildAccountId2;
    private double PrincipalAmount;
    private double InterestAmount;
    //for report
    private Date PayDate2;
    private Date AddDate2;
    private Date EditDate2;
    
    private String pay_number;
    private String PaidForSummary;

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
     * @return the PayDate
     */
    public Date getPayDate() {
        return PayDate;
    }

    /**
     * @param PayDate the PayDate to set
     */
    public void setPayDate(Date PayDate) {
        this.PayDate = PayDate;
    }

    /**
     * @return the PaidAmount
     */
    public double getPaidAmount() {
        return PaidAmount;
    }

    /**
     * @param PaidAmount the PaidAmount to set
     */
    public void setPaidAmount(double PaidAmount) {
        this.PaidAmount = PaidAmount;
    }

    /**
     * @return the PayMethodId
     */
    public int getPayMethodId() {
        return PayMethodId;
    }

    /**
     * @param PayMethodId the PayMethodId to set
     */
    public void setPayMethodId(int PayMethodId) {
        this.PayMethodId = PayMethodId;
    }

    /**
     * @return the AddUserDetailId
     */
    public int getAddUserDetailId() {
        return AddUserDetailId;
    }

    /**
     * @param AddUserDetailId the AddUserDetailId to set
     */
    public void setAddUserDetailId(int AddUserDetailId) {
        this.AddUserDetailId = AddUserDetailId;
    }

    /**
     * @return the EditUserDetailId
     */
    public int getEditUserDetailId() {
        return EditUserDetailId;
    }

    /**
     * @param EditUserDetailId the EditUserDetailId to set
     */
    public void setEditUserDetailId(int EditUserDetailId) {
        this.EditUserDetailId = EditUserDetailId;
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
     * @return the EditDate
     */
    public Date getEditDate() {
        return EditDate;
    }

    /**
     * @param EditDate the EditDate to set
     */
    public void setEditDate(Date EditDate) {
        this.EditDate = EditDate;
    }

    /**
     * @return the PointsSpentAmount
     */
    public double getPointsSpentAmount() {
        return PointsSpentAmount;
    }

    /**
     * @param PointsSpentAmount the PointsSpentAmount to set
     */
    public void setPointsSpentAmount(double PointsSpentAmount) {
        this.PointsSpentAmount = PointsSpentAmount;
    }

    /**
     * @return the DeletePayId
     */
    public long getDeletePayId() {
        return DeletePayId;
    }

    /**
     * @param DeletePayId the DeletePayId to set
     */
    public void setDeletePayId(long DeletePayId) {
        this.DeletePayId = DeletePayId;
    }

    /**
     * @return the PointsSpent
     */
    public double getPointsSpent() {
        return PointsSpent;
    }

    /**
     * @param PointsSpent the PointsSpent to set
     */
    public void setPointsSpent(double PointsSpent) {
        this.PointsSpent = PointsSpent;
    }

    /**
     * @return the PayRefNo
     */
    public String getPayRefNo() {
        return PayRefNo;
    }

    /**
     * @param PayRefNo the PayRefNo to set
     */
    public void setPayRefNo(String PayRefNo) {
        this.PayRefNo = PayRefNo;
    }

    /**
     * @return the PayCategory
     */
    public String getPayCategory() {
        return PayCategory;
    }

    /**
     * @param PayCategory the PayCategory to set
     */
    public void setPayCategory(String PayCategory) {
        this.PayCategory = PayCategory;
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
     * @return the XRate
     */
    public double getXRate() {
        return XRate;
    }

    /**
     * @param XRate the XRate to set
     */
    public void setXRate(double XRate) {
        this.XRate = XRate;
    }

    /**
     * @return the Status
     */
    public int getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(int Status) {
        this.Status = Status;
    }

    /**
     * @return the StatusDesc
     */
    public String getStatusDesc() {
        return StatusDesc;
    }

    /**
     * @param StatusDesc the StatusDesc to set
     */
    public void setStatusDesc(String StatusDesc) {
        this.StatusDesc = StatusDesc;
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
     * @return the AccChildAccountId2
     */
    public int getAccChildAccountId2() {
        return AccChildAccountId2;
    }

    /**
     * @param AccChildAccountId2 the AccChildAccountId2 to set
     */
    public void setAccChildAccountId2(int AccChildAccountId2) {
        this.AccChildAccountId2 = AccChildAccountId2;
    }

    /**
     * @return the PrincipalAmount
     */
    public double getPrincipalAmount() {
        return PrincipalAmount;
    }

    /**
     * @param PrincipalAmount the PrincipalAmount to set
     */
    public void setPrincipalAmount(double PrincipalAmount) {
        this.PrincipalAmount = PrincipalAmount;
    }

    /**
     * @return the InterestAmount
     */
    public double getInterestAmount() {
        return InterestAmount;
    }

    /**
     * @param InterestAmount the InterestAmount to set
     */
    public void setInterestAmount(double InterestAmount) {
        this.InterestAmount = InterestAmount;
    }

    /**
     * @return the PayDate2
     */
    public Date getPayDate2() {
        return PayDate2;
    }

    /**
     * @param PayDate2 the PayDate2 to set
     */
    public void setPayDate2(Date PayDate2) {
        this.PayDate2 = PayDate2;
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
     * @return the EditDate2
     */
    public Date getEditDate2() {
        return EditDate2;
    }

    /**
     * @param EditDate2 the EditDate2 to set
     */
    public void setEditDate2(Date EditDate2) {
        this.EditDate2 = EditDate2;
    }

    /**
     * @return the pay_number
     */
    public String getPay_number() {
        return pay_number;
    }

    /**
     * @param pay_number the pay_number to set
     */
    public void setPay_number(String pay_number) {
        this.pay_number = pay_number;
    }

    /**
     * @return the PaidForSummary
     */
    public String getPaidForSummary() {
        return PaidForSummary;
    }

    /**
     * @param PaidForSummary the PaidForSummary to set
     */
    public void setPaidForSummary(String PaidForSummary) {
        this.PaidForSummary = PaidForSummary;
    }
}
