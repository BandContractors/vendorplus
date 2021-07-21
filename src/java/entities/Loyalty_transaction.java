package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Loyalty_transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    private long loyalty_transaction_id;
    private int store_id;
    private String section_code;
    private String branch_code;
    private String business_code;
    private String group_code;
    private String card_number;
    private String invoice_number;
    private Date transaction_date;
    private double points_awarded;
    private double amount_awarded;
    private double points_spent;
    private double amount_spent;
    private String currency_code;
    private String staff_code;
    private Date add_date;
    private int status_sync;
    private Date status_date;
    private String status_desc;

    public Loyalty_transaction() {
        this.section_code = "";
        this.branch_code = "";
        this.business_code = "";
        this.group_code = "";
        this.card_number = "";
        this.invoice_number = "";
        this.transaction_date = new Date();
        this.currency_code = "";
        this.points_awarded = 0;
        this.amount_awarded = 0;
        this.points_spent = 0;
        this.amount_spent = 0;
        this.currency_code = "";
        this.staff_code = "";
        this.add_date = new Date();
        this.status_sync = 0;
        this.status_date = new Date();
        this.status_desc = "";
    }

    /**
     * @return the section_code
     */
    public String getSection_code() {
        return section_code;
    }

    /**
     * @param section_code the section_code to set
     */
    public void setSection_code(String section_code) {
        this.section_code = section_code;
    }

    /**
     * @return the branch_code
     */
    public String getBranch_code() {
        return branch_code;
    }

    /**
     * @param branch_code the branch_code to set
     */
    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    /**
     * @return the business_code
     */
    public String getBusiness_code() {
        return business_code;
    }

    /**
     * @param business_code the business_code to set
     */
    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    /**
     * @return the group_code
     */
    public String getGroup_code() {
        return group_code;
    }

    /**
     * @param group_code the group_code to set
     */
    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    /**
     * @return the card_number
     */
    public String getCard_number() {
        return card_number;
    }

    /**
     * @param card_number the card_number to set
     */
    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    /**
     * @return the invoice_number
     */
    public String getInvoice_number() {
        return invoice_number;
    }

    /**
     * @param invoice_number the invoice_number to set
     */
    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
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
     * @return the points_awarded
     */
    public double getPoints_awarded() {
        return points_awarded;
    }

    /**
     * @param points_awarded the points_awarded to set
     */
    public void setPoints_awarded(double points_awarded) {
        this.points_awarded = points_awarded;
    }

    /**
     * @return the amount_awarded
     */
    public double getAmount_awarded() {
        return amount_awarded;
    }

    /**
     * @param amount_awarded the amount_awarded to set
     */
    public void setAmount_awarded(double amount_awarded) {
        this.amount_awarded = amount_awarded;
    }

    /**
     * @return the points_spent
     */
    public double getPoints_spent() {
        return points_spent;
    }

    /**
     * @param points_spent the points_spent to set
     */
    public void setPoints_spent(double points_spent) {
        this.points_spent = points_spent;
    }

    /**
     * @return the amount_spent
     */
    public double getAmount_spent() {
        return amount_spent;
    }

    /**
     * @param amount_spent the amount_spent to set
     */
    public void setAmount_spent(double amount_spent) {
        this.amount_spent = amount_spent;
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
     * @return the staff_code
     */
    public String getStaff_code() {
        return staff_code;
    }

    /**
     * @param staff_code the staff_code to set
     */
    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
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
     * @return the status_sync
     */
    public int getStatus_sync() {
        return status_sync;
    }

    /**
     * @param status_sync the status_sync to set
     */
    public void setStatus_sync(int status_sync) {
        this.status_sync = status_sync;
    }

    /**
     * @return the status_date
     */
    public Date getStatus_date() {
        return status_date;
    }

    /**
     * @param status_date the status_date to set
     */
    public void setStatus_date(Date status_date) {
        this.status_date = status_date;
    }

    /**
     * @return the status_desc
     */
    public String getStatus_desc() {
        return status_desc;
    }

    /**
     * @param status_desc the status_desc to set
     */
    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    /**
     * @return the loyalty_transaction_id
     */
    public long getLoyalty_transaction_id() {
        return loyalty_transaction_id;
    }

    /**
     * @param loyalty_transaction_id the loyalty_transaction_id to set
     */
    public void setLoyalty_transaction_id(long loyalty_transaction_id) {
        this.loyalty_transaction_id = loyalty_transaction_id;
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

}
