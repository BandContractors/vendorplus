package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Stock_out implements Serializable {
   private static final long serialVersionUID = 1L;
   private long stock_out_id;
   private long transactor_id;
   private long site_id;
   private long item_id;
   private double qty_out;
   private int store_id;
   private String batchno;
   private String code_specific;
   private String desc_specific;
   private long transaction_id;
   //report only
   private String item_description;
   private String site_name;
   private String transactor_names;
   private Date transaction_date;
   private Date from_date;
   private Date to_date;
   private double duration_passed;
   private String transaction_number;

    /**
     * @return the stock_out_id
     */
    public long getStock_out_id() {
        return stock_out_id;
    }

    /**
     * @param stock_out_id the stock_out_id to set
     */
    public void setStock_out_id(long stock_out_id) {
        this.stock_out_id = stock_out_id;
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
     * @return the item_id
     */
    public long getItem_id() {
        return item_id;
    }

    /**
     * @param item_id the item_id to set
     */
    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    /**
     * @return the qty_out
     */
    public double getQty_out() {
        return qty_out;
    }

    /**
     * @param qty_out the qty_out to set
     */
    public void setQty_out(double qty_out) {
        this.qty_out = qty_out;
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
     * @return the batchno
     */
    public String getBatchno() {
        return batchno;
    }

    /**
     * @param batchno the batchno to set
     */
    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    /**
     * @return the code_specific
     */
    public String getCode_specific() {
        return code_specific;
    }

    /**
     * @param code_specific the code_specific to set
     */
    public void setCode_specific(String code_specific) {
        this.code_specific = code_specific;
    }

    /**
     * @return the desc_specific
     */
    public String getDesc_specific() {
        return desc_specific;
    }

    /**
     * @param desc_specific the desc_specific to set
     */
    public void setDesc_specific(String desc_specific) {
        this.desc_specific = desc_specific;
    }

    /**
     * @return the site_id
     */
    public long getSite_id() {
        return site_id;
    }

    /**
     * @param site_id the site_id to set
     */
    public void setSite_id(long site_id) {
        this.site_id = site_id;
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
     * @return the item_description
     */
    public String getItem_description() {
        return item_description;
    }

    /**
     * @param item_description the item_description to set
     */
    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    /**
     * @return the site_name
     */
    public String getSite_name() {
        return site_name;
    }

    /**
     * @param site_name the site_name to set
     */
    public void setSite_name(String site_name) {
        this.site_name = site_name;
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
     * @return the from_date
     */
    public Date getFrom_date() {
        return from_date;
    }

    /**
     * @param from_date the from_date to set
     */
    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    /**
     * @return the to_date
     */
    public Date getTo_date() {
        return to_date;
    }

    /**
     * @param to_date the to_date to set
     */
    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    /**
     * @return the duration_passed
     */
    public double getDuration_passed() {
        return duration_passed;
    }

    /**
     * @param duration_passed the duration_passed to set
     */
    public void setDuration_passed(double duration_passed) {
        this.duration_passed = duration_passed;
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
}
