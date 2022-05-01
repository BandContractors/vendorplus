package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Transaction_approval implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_approval_id;
    private long transaction_hist_id;
    private int transaction_type_id;
    private int transaction_reason_id;
    private Date request_date;
    private int request_by_id;
    private int approval_status;
    private Date status_date;
    private String status_desc;
    private String status_comment;
    private int status_by_id;
    private double grand_total;
    private String transactor_names;
    private String currency_code;
    private long transactor_id;
    private int store_id;
    private double amount_tendered;
    private long transaction_id;
    private int hide_from_view;

    /**
     * @return the transaction_approval_id
     */
    public long getTransaction_approval_id() {
        return transaction_approval_id;
    }

    /**
     * @param transaction_approval_id the transaction_approval_id to set
     */
    public void setTransaction_approval_id(long transaction_approval_id) {
        this.transaction_approval_id = transaction_approval_id;
    }

    /**
     * @return the transaction_hist_id
     */
    public long getTransaction_hist_id() {
        return transaction_hist_id;
    }

    /**
     * @param transaction_hist_id the transaction_hist_id to set
     */
    public void setTransaction_hist_id(long transaction_hist_id) {
        this.transaction_hist_id = transaction_hist_id;
    }

    /**
     * @return the transaction_type_id
     */
    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    /**
     * @param transaction_type_id the transaction_type_id to set
     */
    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    /**
     * @return the transaction_reason_id
     */
    public int getTransaction_reason_id() {
        return transaction_reason_id;
    }

    /**
     * @param transaction_reason_id the transaction_reason_id to set
     */
    public void setTransaction_reason_id(int transaction_reason_id) {
        this.transaction_reason_id = transaction_reason_id;
    }

    /**
     * @return the request_date
     */
    public Date getRequest_date() {
        return request_date;
    }

    /**
     * @param request_date the request_date to set
     */
    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    /**
     * @return the request_by_id
     */
    public int getRequest_by_id() {
        return request_by_id;
    }

    /**
     * @param request_by_id the request_by_id to set
     */
    public void setRequest_by_id(int request_by_id) {
        this.request_by_id = request_by_id;
    }

    /**
     * @return the approval_status
     */
    public int getApproval_status() {
        return approval_status;
    }

    /**
     * @param approval_status the approval_status to set
     */
    public void setApproval_status(int approval_status) {
        this.approval_status = approval_status;
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
     * @return the status_by_id
     */
    public int getStatus_by_id() {
        return status_by_id;
    }

    /**
     * @param status_by_id the status_by_id to set
     */
    public void setStatus_by_id(int status_by_id) {
        this.status_by_id = status_by_id;
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
     * @return the amount_tendered
     */
    public double getAmount_tendered() {
        return amount_tendered;
    }

    /**
     * @param amount_tendered the amount_tendered to set
     */
    public void setAmount_tendered(double amount_tendered) {
        this.amount_tendered = amount_tendered;
    }

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
     * @return the status_comment
     */
    public String getStatus_comment() {
        return status_comment;
    }

    /**
     * @param status_comment the status_comment to set
     */
    public void setStatus_comment(String status_comment) {
        this.status_comment = status_comment;
    }

    /**
     * @return the hide_from_view
     */
    public int getHide_from_view() {
        return hide_from_view;
    }

    /**
     * @param hide_from_view the hide_from_view to set
     */
    public void setHide_from_view(int hide_from_view) {
        this.hide_from_view = hide_from_view;
    }
}
