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
public class Cdc_general implements Serializable {

    private static final long serialVersionUID = 1L;

    private long cdc_general_id;
    private String cdc_function;
    private String cdc_id;
    private Date cdc_date;
    private Date cdc_start_time;
    private Date cdc_end_time;
    private int is_passed;
    private double records_affected;
    private Date add_date;
    private int add_by;
    private Date last_update_date;
    private int last_update_by;
    private long snapshot_no;
    private int acc_period_id;

    /**
     * @return the cdc_general_id
     */
    public long getCdc_general_id() {
        return cdc_general_id;
    }

    /**
     * @param cdc_general_id the cdc_general_id to set
     */
    public void setCdc_general_id(long cdc_general_id) {
        this.cdc_general_id = cdc_general_id;
    }

    /**
     * @return the cdc_function
     */
    public String getCdc_function() {
        return cdc_function;
    }

    /**
     * @param cdc_function the cdc_function to set
     */
    public void setCdc_function(String cdc_function) {
        this.cdc_function = cdc_function;
    }

    /**
     * @return the cdc_id
     */
    public String getCdc_id() {
        return cdc_id;
    }

    /**
     * @param cdc_id the cdc_id to set
     */
    public void setCdc_id(String cdc_id) {
        this.cdc_id = cdc_id;
    }

    /**
     * @return the cdc_date
     */
    public Date getCdc_date() {
        return cdc_date;
    }

    /**
     * @param cdc_date the cdc_date to set
     */
    public void setCdc_date(Date cdc_date) {
        this.cdc_date = cdc_date;
    }

    /**
     * @return the cdc_start_time
     */
    public Date getCdc_start_time() {
        return cdc_start_time;
    }

    /**
     * @param cdc_start_time the cdc_start_time to set
     */
    public void setCdc_start_time(Date cdc_start_time) {
        this.cdc_start_time = cdc_start_time;
    }

    /**
     * @return the cdc_end_time
     */
    public Date getCdc_end_time() {
        return cdc_end_time;
    }

    /**
     * @param cdc_end_time the cdc_end_time to set
     */
    public void setCdc_end_time(Date cdc_end_time) {
        this.cdc_end_time = cdc_end_time;
    }

    /**
     * @return the is_passed
     */
    public int getIs_passed() {
        return is_passed;
    }

    /**
     * @param is_passed the is_passed to set
     */
    public void setIs_passed(int is_passed) {
        this.is_passed = is_passed;
    }

    /**
     * @return the records_affected
     */
    public double getRecords_affected() {
        return records_affected;
    }

    /**
     * @param records_affected the records_affected to set
     */
    public void setRecords_affected(double records_affected) {
        this.records_affected = records_affected;
    }

    /**
     * @return the add_date
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * @param add_date the add_date to set
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * @return the add_by
     */
    public int getAdd_by() {
        return add_by;
    }

    /**
     * @param add_by the add_by to set
     */
    public void setAdd_by(int add_by) {
        this.add_by = add_by;
    }

    /**
     * @return the last_update_date
     */
    public Date getLast_update_date() {
        return last_update_date;
    }

    /**
     * @param last_update_date the last_update_date to set
     */
    public void setLast_update_date(Date last_update_date) {
        this.last_update_date = last_update_date;
    }

    /**
     * @return the last_update_by
     */
    public int getLast_update_by() {
        return last_update_by;
    }

    /**
     * @param last_update_by the last_update_by to set
     */
    public void setLast_update_by(int last_update_by) {
        this.last_update_by = last_update_by;
    }

    /**
     * @return the snapshot_no
     */
    public long getSnapshot_no() {
        return snapshot_no;
    }

    /**
     * @param snapshot_no the snapshot_no to set
     */
    public void setSnapshot_no(long snapshot_no) {
        this.snapshot_no = snapshot_no;
    }

    /**
     * @return the acc_period_id
     */
    public int getAcc_period_id() {
        return acc_period_id;
    }

    /**
     * @param acc_period_id the acc_period_id to set
     */
    public void setAcc_period_id(int acc_period_id) {
        this.acc_period_id = acc_period_id;
    }

}
