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
public class Stock_ledger implements Serializable {
    private static final long serialVersionUID = 1L;

    private long stock_ledger_id;
    private int store_id; 
    private long item_id;
    private String batchno;
    private String code_specific;
    private String desc_specific;
    private double specific_size;
    private double qty_added;
    private double qty_subtracted;
    private int transaction_type_id;
    private String action_type;
    private long transaction_id;
    private int user_detail_id;
    private Date add_date;

    /**
     * @return the stock_ledger_id
     */
    public long getStock_ledger_id() {
        return stock_ledger_id;
    }

    /**
     * @param stock_ledger_id the stock_ledger_id to set
     */
    public void setStock_ledger_id(long stock_ledger_id) {
        this.stock_ledger_id = stock_ledger_id;
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
     * @return the specific_size
     */
    public double getSpecific_size() {
        return specific_size;
    }

    /**
     * @param specific_size the specific_size to set
     */
    public void setSpecific_size(double specific_size) {
        this.specific_size = specific_size;
    }

    /**
     * @return the qty_added
     */
    public double getQty_added() {
        return qty_added;
    }

    /**
     * @param qty_added the qty_added to set
     */
    public void setQty_added(double qty_added) {
        this.qty_added = qty_added;
    }

    /**
     * @return the qty_subtracted
     */
    public double getQty_subtracted() {
        return qty_subtracted;
    }

    /**
     * @param qty_subtracted the qty_subtracted to set
     */
    public void setQty_subtracted(double qty_subtracted) {
        this.qty_subtracted = qty_subtracted;
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
     * @return the action_type
     */
    public String getAction_type() {
        return action_type;
    }

    /**
     * @param action_type the action_type to set
     */
    public void setAction_type(String action_type) {
        this.action_type = action_type;
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
     * @return the user_detail_id
     */
    public int getUser_detail_id() {
        return user_detail_id;
    }

    /**
     * @param user_detail_id the user_detail_id to set
     */
    public void setUser_detail_id(int user_detail_id) {
        this.user_detail_id = user_detail_id;
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
}
