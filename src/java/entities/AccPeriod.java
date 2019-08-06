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
public class AccPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    private int AccPeriodId;
    private String AccPeriodName;
    private Date StartDate;
    private Date EndDate;
    private int IsCurrent;
    private int IsActive;
    private int IsDeleted;
    private int IsOpen;
    private int IsClosed;
    private int OrderNo;
    private Date AddDate;
    private int AddBy;
    private Date LastEditDate;
    private int LastEditBy;

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
     * @return the AccPeriodName
     */
    public String getAccPeriodName() {
        return AccPeriodName;
    }

    /**
     * @param AccPeriodName the AccPeriodName to set
     */
    public void setAccPeriodName(String AccPeriodName) {
        this.AccPeriodName = AccPeriodName;
    }

    /**
     * @return the StartDate
     */
    public Date getStartDate() {
        return StartDate;
    }

    /**
     * @param StartDate the StartDate to set
     */
    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    /**
     * @return the EndDate
     */
    public Date getEndDate() {
        return EndDate;
    }

    /**
     * @param EndDate the EndDate to set
     */
    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    /**
     * @return the IsCurrent
     */
    public int getIsCurrent() {
        return IsCurrent;
    }

    /**
     * @param IsCurrent the IsCurrent to set
     */
    public void setIsCurrent(int IsCurrent) {
        this.IsCurrent = IsCurrent;
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
     * @return the IsOpen
     */
    public int getIsOpen() {
        return IsOpen;
    }

    /**
     * @param IsOpen the IsOpen to set
     */
    public void setIsOpen(int IsOpen) {
        this.IsOpen = IsOpen;
    }

    /**
     * @return the IsClosed
     */
    public int getIsClosed() {
        return IsClosed;
    }

    /**
     * @param IsClosed the IsClosed to set
     */
    public void setIsClosed(int IsClosed) {
        this.IsClosed = IsClosed;
    }

    /**
     * @return the OrderNo
     */
    public int getOrderNo() {
        return OrderNo;
    }

    /**
     * @param OrderNo the OrderNo to set
     */
    public void setOrderNo(int OrderNo) {
        this.OrderNo = OrderNo;
    }

}
