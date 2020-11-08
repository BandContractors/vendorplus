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
public class Cash_balancing_daily implements Serializable {

    private static final long serialVersionUID = 1L;
    private long cash_balancing_daily_id;
    private Date balancing_date;
    private int balancing_user;
    private String account_code;
    private int acc_child_account_id;
    private String currency_code;
    private double cash_at_begin;
    private double cash_transfer_in;
    private double cash_adjustment_pos;
    private double cash_receipts;
    private double cash_transfer_out;
    private double cash_adjustment_neg;
    private double cash_payments;
    private double cash_balance;
    private double actual_cash_count;
    private double cash_over;
    private double cash_short;
    private int add_user_detail_id;
    private int edit_user_detail_id;
    private Date add_date;
    private Date edit_date;

    /**
     * @return the cash_balancing_daily_id
     */
    public long getCash_balancing_daily_id() {
        return cash_balancing_daily_id;
    }

    /**
     * @param cash_balancing_daily_id the cash_balancing_daily_id to set
     */
    public void setCash_balancing_daily_id(long cash_balancing_daily_id) {
        this.cash_balancing_daily_id = cash_balancing_daily_id;
    }

    /**
     * @return the balancing_date
     */
    public Date getBalancing_date() {
        return balancing_date;
    }

    /**
     * @param balancing_date the balancing_date to set
     */
    public void setBalancing_date(Date balancing_date) {
        this.balancing_date = balancing_date;
    }

    /**
     * @return the balancing_user
     */
    public int getBalancing_user() {
        return balancing_user;
    }

    /**
     * @param balancing_user the balancing_user to set
     */
    public void setBalancing_user(int balancing_user) {
        this.balancing_user = balancing_user;
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
     * @return the acc_child_account_id
     */
    public int getAcc_child_account_id() {
        return acc_child_account_id;
    }

    /**
     * @param acc_child_account_id the acc_child_account_id to set
     */
    public void setAcc_child_account_id(int acc_child_account_id) {
        this.acc_child_account_id = acc_child_account_id;
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
     * @return the cash_at_begin
     */
    public double getCash_at_begin() {
        return cash_at_begin;
    }

    /**
     * @param cash_at_begin the cash_at_begin to set
     */
    public void setCash_at_begin(double cash_at_begin) {
        this.cash_at_begin = cash_at_begin;
    }

    /**
     * @return the cash_transfer_in
     */
    public double getCash_transfer_in() {
        return cash_transfer_in;
    }

    /**
     * @param cash_transfer_in the cash_transfer_in to set
     */
    public void setCash_transfer_in(double cash_transfer_in) {
        this.cash_transfer_in = cash_transfer_in;
    }

    /**
     * @return the cash_adjustment_pos
     */
    public double getCash_adjustment_pos() {
        return cash_adjustment_pos;
    }

    /**
     * @param cash_adjustment_pos the cash_adjustment_pos to set
     */
    public void setCash_adjustment_pos(double cash_adjustment_pos) {
        this.cash_adjustment_pos = cash_adjustment_pos;
    }

    /**
     * @return the cash_receipts
     */
    public double getCash_receipts() {
        return cash_receipts;
    }

    /**
     * @param cash_receipts the cash_receipts to set
     */
    public void setCash_receipts(double cash_receipts) {
        this.cash_receipts = cash_receipts;
    }

    /**
     * @return the cash_transfer_out
     */
    public double getCash_transfer_out() {
        return cash_transfer_out;
    }

    /**
     * @param cash_transfer_out the cash_transfer_out to set
     */
    public void setCash_transfer_out(double cash_transfer_out) {
        this.cash_transfer_out = cash_transfer_out;
    }

    /**
     * @return the cash_adjustment_neg
     */
    public double getCash_adjustment_neg() {
        return cash_adjustment_neg;
    }

    /**
     * @param cash_adjustment_neg the cash_adjustment_neg to set
     */
    public void setCash_adjustment_neg(double cash_adjustment_neg) {
        this.cash_adjustment_neg = cash_adjustment_neg;
    }

    /**
     * @return the cash_payments
     */
    public double getCash_payments() {
        return cash_payments;
    }

    /**
     * @param cash_payments the cash_payments to set
     */
    public void setCash_payments(double cash_payments) {
        this.cash_payments = cash_payments;
    }

    /**
     * @return the cash_balance
     */
    public double getCash_balance() {
        return cash_balance;
    }

    /**
     * @param cash_balance the cash_balance to set
     */
    public void setCash_balance(double cash_balance) {
        this.cash_balance = cash_balance;
    }

    /**
     * @return the actual_cash_count
     */
    public double getActual_cash_count() {
        return actual_cash_count;
    }

    /**
     * @param actual_cash_count the actual_cash_count to set
     */
    public void setActual_cash_count(double actual_cash_count) {
        this.actual_cash_count = actual_cash_count;
    }

    /**
     * @return the cash_over
     */
    public double getCash_over() {
        return cash_over;
    }

    /**
     * @param cash_over the cash_over to set
     */
    public void setCash_over(double cash_over) {
        this.cash_over = cash_over;
    }

    /**
     * @return the cash_short
     */
    public double getCash_short() {
        return cash_short;
    }

    /**
     * @param cash_short the cash_short to set
     */
    public void setCash_short(double cash_short) {
        this.cash_short = cash_short;
    }

    /**
     * @return the add_user_detail_id
     */
    public int getAdd_user_detail_id() {
        return add_user_detail_id;
    }

    /**
     * @param add_user_detail_id the add_user_detail_id to set
     */
    public void setAdd_user_detail_id(int add_user_detail_id) {
        this.add_user_detail_id = add_user_detail_id;
    }

    /**
     * @return the edit_user_detail_id
     */
    public int getEdit_user_detail_id() {
        return edit_user_detail_id;
    }

    /**
     * @param edit_user_detail_id the edit_user_detail_id to set
     */
    public void setEdit_user_detail_id(int edit_user_detail_id) {
        this.edit_user_detail_id = edit_user_detail_id;
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
     * @return the edit_date
     */
    public Date getEdit_date() {
        return edit_date;
    }

    /**
     * @param edit_date the edit_date to set
     */
    public void setEdit_date(Date edit_date) {
        this.edit_date = edit_date;
    }
}
