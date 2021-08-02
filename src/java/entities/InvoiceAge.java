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
public class InvoiceAge implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_id;
    private String transaction_number;
    private long transactor_id;
    private String transactor_names;
    private String currency_code;
    private Date transaction_date;
    private int age_days;
    private double grand_total;
    private double total_paid;
    private double balance;
    private String days_category;
    private double perc;

    /**
     * @return the transaction_id
     */
    public long getTransaction_id() {
        return transaction_id;
    }

    /**
     * @param transaction_id the transaction_id to set
     */
    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
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
     * @return the transactor_id
     */
    public long getTransactor_id() {
        return transactor_id;
    }

    /**
     * @param transactor_id the transactor_id to set
     */
    public void setTransactor_id(long transactor_id) {
        this.transactor_id = transactor_id;
    }

    /**
     * @return the transactor_names
     */
    public String getTransactor_names() {
        return transactor_names;
    }

    /**
     * @param transactor_names the transactor_names to set
     */
    public void setTransactor_names(String transactor_names) {
        this.transactor_names = transactor_names;
    }

    /**
     * @return the currency_code
     */
    public String getCurrency_code() {
        return currency_code;
    }

    /**
     * @param currency_code the currency_code to set
     */
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    /**
     * @return the transaction_date
     */
    public Date getTransaction_date() {
        return transaction_date;
    }

    /**
     * @param transaction_date the transaction_date to set
     */
    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    /**
     * @return the age_days
     */
    public int getAge_days() {
        return age_days;
    }

    /**
     * @param age_days the age_days to set
     */
    public void setAge_days(int age_days) {
        this.age_days = age_days;
    }

    /**
     * @return the grand_total
     */
    public double getGrand_total() {
        return grand_total;
    }

    /**
     * @param grand_total the grand_total to set
     */
    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    /**
     * @return the total_paid
     */
    public double getTotal_paid() {
        return total_paid;
    }

    /**
     * @param total_paid the total_paid to set
     */
    public void setTotal_paid(double total_paid) {
        this.total_paid = total_paid;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the days_category
     */
    public String getDays_category() {
        return days_category;
    }

    /**
     * @param days_category the days_category to set
     */
    public void setDays_category(String days_category) {
        this.days_category = days_category;
    }

    /**
     * @return the perc
     */
    public double getPerc() {
        return perc;
    }

    /**
     * @param perc the perc to set
     */
    public void setPerc(double perc) {
        this.perc = perc;
    }
}
