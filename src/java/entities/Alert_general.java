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
public class Alert_general implements Serializable {

    private static final long serialVersionUID = 1L;

    private long alert_general_id;
    private Date alert_date;
    private String alert_type;
    private String subject;
    private String message;
    private String alert_users;
    private String read_by;
    private String alert_items;
    private Date add_date;
    private int add_by;
    private Date last_update_date;
    private int last_update_by;
    private String status_code;
    private String alert_category;

    /**
     * @return the alert_general_id
     */
    public long getAlert_general_id() {
        return alert_general_id;
    }

    /**
     * @param alert_general_id the alert_general_id to set
     */
    public void setAlert_general_id(long alert_general_id) {
        this.alert_general_id = alert_general_id;
    }

    /**
     * @return the alert_type
     */
    public String getAlert_type() {
        return alert_type;
    }

    /**
     * @param alert_type the alert_type to set
     */
    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the alert_users
     */
    public String getAlert_users() {
        return alert_users;
    }

    /**
     * @param alert_users the alert_users to set
     */
    public void setAlert_users(String alert_users) {
        this.alert_users = alert_users;
    }

    /**
     * @return the read_by
     */
    public String getRead_by() {
        return read_by;
    }

    /**
     * @param read_by the read_by to set
     */
    public void setRead_by(String read_by) {
        this.read_by = read_by;
    }

    /**
     * @return the alert_items
     */
    public String getAlert_items() {
        return alert_items;
    }

    /**
     * @param alert_items the alert_items to set
     */
    public void setAlert_items(String alert_items) {
        this.alert_items = alert_items;
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
     * @return the status_code
     */
    public String getStatus_code() {
        return status_code;
    }

    /**
     * @param status_code the status_code to set
     */
    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    /**
     * @return the alert_date
     */
    public Date getAlert_date() {
        return alert_date;
    }

    /**
     * @param alert_date the alert_date to set
     */
    public void setAlert_date(Date alert_date) {
        this.alert_date = alert_date;
    }

    /**
     * @return the alert_category
     */
    public String getAlert_category() {
        return alert_category;
    }

    /**
     * @param alert_category the alert_category to set
     */
    public void setAlert_category(String alert_category) {
        this.alert_category = alert_category;
    }

}
