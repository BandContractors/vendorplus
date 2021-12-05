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
public class stock_take_session implements Serializable {

    private static final long serialVersionUID = 1L;

    private long stock_take_session_id;
    private int store_id;
    private int acc_period_id;
    private String notes;
    private Date start_time;
    private Date end_time;
    private int is_closed;
    private double stock_items_available;
    private double stock_items_counted;
    private Date add_date;
    private String add_by;
    private Date last_update_date;
    private String last_update_by;

    /**
     * @return the stock_take_session_id
     */
    public long getStock_take_session_id() {
        return stock_take_session_id;
    }

    /**
     * @param stock_take_session_id the stock_take_session_id to set
     */
    public void setStock_take_session_id(long stock_take_session_id) {
        this.stock_take_session_id = stock_take_session_id;
    }

    /**
     * @return the store_id
     */
    public int getStore_id() {
        return store_id;
    }

    /**
     * @param store_id the store_id to set
     */
    public void setStore_id(int store_id) {
        this.store_id = store_id;
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

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the start_time
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * @param start_time the start_time to set
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * @return the end_time
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * @param end_time the end_time to set
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * @return the is_closed
     */
    public int getIs_closed() {
        return is_closed;
    }

    /**
     * @param is_closed the is_closed to set
     */
    public void setIs_closed(int is_closed) {
        this.is_closed = is_closed;
    }

    /**
     * @return the stock_items_available
     */
    public double getStock_items_available() {
        return stock_items_available;
    }

    /**
     * @param stock_items_available the stock_items_available to set
     */
    public void setStock_items_available(double stock_items_available) {
        this.stock_items_available = stock_items_available;
    }

    /**
     * @return the stock_items_counted
     */
    public double getStock_items_counted() {
        return stock_items_counted;
    }

    /**
     * @param stock_items_counted the stock_items_counted to set
     */
    public void setStock_items_counted(double stock_items_counted) {
        this.stock_items_counted = stock_items_counted;
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
    public String getAdd_by() {
        return add_by;
    }

    /**
     * @param add_by the add_by to set
     */
    public void setAdd_by(String add_by) {
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
    public String getLast_update_by() {
        return last_update_by;
    }

    /**
     * @param last_update_by the last_update_by to set
     */
    public void setLast_update_by(String last_update_by) {
        this.last_update_by = last_update_by;
    }
}
