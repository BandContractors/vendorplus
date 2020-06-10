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
public class AccDepSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int AccDepScheduleId;
    private long StockId;
    private int DepForAccPeriodId;
    private Date DepFromDate;
    private Date DepToDate;
    private int YearNumber;
    private double DepAmount;
    private int post_status;
    //for output
    private String acc_period_name;
    

    /**
     * @return the AccDepScheduleId
     */
    public int getAccDepScheduleId() {
        return AccDepScheduleId;
    }

    /**
     * @param AccDepScheduleId the AccDepScheduleId to set
     */
    public void setAccDepScheduleId(int AccDepScheduleId) {
        this.AccDepScheduleId = AccDepScheduleId;
    }

    /**
     * @return the DepForAccPeriodId
     */
    public int getDepForAccPeriodId() {
        return DepForAccPeriodId;
    }

    /**
     * @param DepForAccPeriodId the DepForAccPeriodId to set
     */
    public void setDepForAccPeriodId(int DepForAccPeriodId) {
        this.DepForAccPeriodId = DepForAccPeriodId;
    }

    /**
     * @return the DepFromDate
     */
    public Date getDepFromDate() {
        return DepFromDate;
    }

    /**
     * @param DepFromDate the DepFromDate to set
     */
    public void setDepFromDate(Date DepFromDate) {
        this.DepFromDate = DepFromDate;
    }

    /**
     * @return the DepToDate
     */
    public Date getDepToDate() {
        return DepToDate;
    }

    /**
     * @param DepToDate the DepToDate to set
     */
    public void setDepToDate(Date DepToDate) {
        this.DepToDate = DepToDate;
    }

    /**
     * @return the YearNumber
     */
    public int getYearNumber() {
        return YearNumber;
    }

    /**
     * @param YearNumber the YearNumber to set
     */
    public void setYearNumber(int YearNumber) {
        this.YearNumber = YearNumber;
    }

    /**
     * @return the DepAmount
     */
    public double getDepAmount() {
        return DepAmount;
    }

    /**
     * @param DepAmount the DepAmount to set
     */
    public void setDepAmount(double DepAmount) {
        this.DepAmount = DepAmount;
    }

    /**
     * @return the StockId
     */
    public long getStockId() {
        return StockId;
    }

    /**
     * @param StockId the StockId to set
     */
    public void setStockId(long StockId) {
        this.StockId = StockId;
    }

    /**
     * @return the post_status
     */
    public int getPost_status() {
        return post_status;
    }

    /**
     * @param post_status the post_status to set
     */
    public void setPost_status(int post_status) {
        this.post_status = post_status;
    }

    /**
     * @return the acc_period_name
     */
    public String getAcc_period_name() {
        return acc_period_name;
    }

    /**
     * @param acc_period_name the acc_period_name to set
     */
    public void setAcc_period_name(String acc_period_name) {
        this.acc_period_name = acc_period_name;
    }
}
