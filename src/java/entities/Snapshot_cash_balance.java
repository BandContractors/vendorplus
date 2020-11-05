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
public class Snapshot_cash_balance implements Serializable {

    private static final long serialVersionUID = 1L;

    private long snapshot_cash_balance_id;
    private long snapshot_no;
    private Date snapshot_date;
    private int acc_period_id;
    private String cdc_id;
    private String account_code;
    private int acc_child_account_id;
    private String currency_code;
    private double debit_amount;
    private double credit_amount;
    private double debit_amount_lc;
    private double credit_amount_lc;
    private double debit_balance;
    private double debit_balance_lc;

    /**
     * @return the snapshot_cash_balance_id
     */
    public long getSnapshot_cash_balance_id() {
        return snapshot_cash_balance_id;
    }

    /**
     * @param snapshot_cash_balance_id the snapshot_cash_balance_id to set
     */
    public void setSnapshot_cash_balance_id(long snapshot_cash_balance_id) {
        this.snapshot_cash_balance_id = snapshot_cash_balance_id;
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
     * @return the snapshot_date
     */
    public Date getSnapshot_date() {
        return snapshot_date;
    }

    /**
     * @param snapshot_date the snapshot_date to set
     */
    public void setSnapshot_date(Date snapshot_date) {
        this.snapshot_date = snapshot_date;
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
     * @return the debit_amount
     */
    public double getDebit_amount() {
        return debit_amount;
    }

    /**
     * @param debit_amount the debit_amount to set
     */
    public void setDebit_amount(double debit_amount) {
        this.debit_amount = debit_amount;
    }

    /**
     * @return the credit_amount
     */
    public double getCredit_amount() {
        return credit_amount;
    }

    /**
     * @param credit_amount the credit_amount to set
     */
    public void setCredit_amount(double credit_amount) {
        this.credit_amount = credit_amount;
    }

    /**
     * @return the debit_amount_lc
     */
    public double getDebit_amount_lc() {
        return debit_amount_lc;
    }

    /**
     * @param debit_amount_lc the debit_amount_lc to set
     */
    public void setDebit_amount_lc(double debit_amount_lc) {
        this.debit_amount_lc = debit_amount_lc;
    }

    /**
     * @return the credit_amount_lc
     */
    public double getCredit_amount_lc() {
        return credit_amount_lc;
    }

    /**
     * @param credit_amount_lc the credit_amount_lc to set
     */
    public void setCredit_amount_lc(double credit_amount_lc) {
        this.credit_amount_lc = credit_amount_lc;
    }

    /**
     * @return the debit_balance
     */
    public double getDebit_balance() {
        return debit_balance;
    }

    /**
     * @param debit_balance the debit_balance to set
     */
    public void setDebit_balance(double debit_balance) {
        this.debit_balance = debit_balance;
    }

    /**
     * @return the debit_balance_lc
     */
    public double getDebit_balance_lc() {
        return debit_balance_lc;
    }

    /**
     * @param debit_balance_lc the debit_balance_lc to set
     */
    public void setDebit_balance_lc(double debit_balance_lc) {
        this.debit_balance_lc = debit_balance_lc;
    }
}
