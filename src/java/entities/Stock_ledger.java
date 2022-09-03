package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
    private double qty_bal;
    private String description;
    private String unit_symbol;
    private String base_unit_symbol;
    private String transaction_type_name;
    private String user_name;
    private String store_name;
    private String transaction_number;
    private double qty_open;
    private double qty_close;
    private List<Stock_ledger> Stock_ledgerList;
    private long tax_update_id;
    private int tax_is_updated;
    private int tax_update_synced;
    private long transaction_item_id;

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

    /**
     * @return the qty_bal
     */
    public double getQty_bal() {
        return qty_bal;
    }

    /**
     * @param qty_bal the qty_bal to set
     */
    public void setQty_bal(double qty_bal) {
        this.qty_bal = qty_bal;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the unit_symbol
     */
    public String getUnit_symbol() {
        return unit_symbol;
    }

    /**
     * @param unit_symbol the unit_symbol to set
     */
    public void setUnit_symbol(String unit_symbol) {
        this.unit_symbol = unit_symbol;
    }

    /**
     * @return the transaction_type_name
     */
    public String getTransaction_type_name() {
        return transaction_type_name;
    }

    /**
     * @param transaction_type_name the transaction_type_name to set
     */
    public void setTransaction_type_name(String transaction_type_name) {
        this.transaction_type_name = transaction_type_name;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the store_name
     */
    public String getStore_name() {
        return store_name;
    }

    /**
     * @param store_name the store_name to set
     */
    public void setStore_name(String store_name) {
        this.store_name = store_name;
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
     * @return the qty_open
     */
    public double getQty_open() {
        return qty_open;
    }

    /**
     * @param qty_open the qty_open to set
     */
    public void setQty_open(double qty_open) {
        this.qty_open = qty_open;
    }

    /**
     * @return the qty_close
     */
    public double getQty_close() {
        return qty_close;
    }

    /**
     * @param qty_close the qty_close to set
     */
    public void setQty_close(double qty_close) {
        this.qty_close = qty_close;
    }

    /**
     * @return the Stock_ledgerList
     */
    public List<Stock_ledger> getStock_ledgerList() {
        return Stock_ledgerList;
    }

    /**
     * @param Stock_ledgerList the Stock_ledgerList to set
     */
    public void setStock_ledgerList(List<Stock_ledger> Stock_ledgerList) {
        this.Stock_ledgerList = Stock_ledgerList;
    }

    /**
     * @return the tax_is_updated
     */
    public int getTax_is_updated() {
        return tax_is_updated;
    }

    /**
     * @param tax_is_updated the tax_is_updated to set
     */
    public void setTax_is_updated(int tax_is_updated) {
        this.tax_is_updated = tax_is_updated;
    }

    /**
     * @return the tax_update_synced
     */
    public int getTax_update_synced() {
        return tax_update_synced;
    }

    /**
     * @param tax_update_synced the tax_update_synced to set
     */
    public void setTax_update_synced(int tax_update_synced) {
        this.tax_update_synced = tax_update_synced;
    }

    /**
     * @return the tax_update_id
     */
    public long getTax_update_id() {
        return tax_update_id;
    }

    /**
     * @param tax_update_id the tax_update_id to set
     */
    public void setTax_update_id(long tax_update_id) {
        this.tax_update_id = tax_update_id;
    }

    /**
     * @return the transaction_item_id
     */
    public long getTransaction_item_id() {
        return transaction_item_id;
    }

    /**
     * @param transaction_item_id the transaction_item_id to set
     */
    public void setTransaction_item_id(long transaction_item_id) {
        this.transaction_item_id = transaction_item_id;
    }

    /**
     * @return the base_unit_symbol
     */
    public String getBase_unit_symbol() {
        return base_unit_symbol;
    }

    /**
     * @param base_unit_symbol the base_unit_symbol to set
     */
    public void setBase_unit_symbol(String base_unit_symbol) {
        this.base_unit_symbol = base_unit_symbol;
    }
}
