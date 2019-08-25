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
public class Snapshot_stock_value implements Serializable {

    private static final long serialVersionUID = 1L;

    private long snapshot_stock_value_id;
    private int snapshot_no;
    private Date snapshot_date;
    private int acc_period_id;
    private long item_id;
    private String batchno;
    private String code_specific;
    private String desc_specific;
    private String currency_code;
    private double currentqty;
    private double unit_cost_price;
    private double cp_value;
    private double wp_value;
    private double rp_value;
    private double specific_size;
    private double qty_damage;
    private String cdc_id;
    private int store_id;

    /**
     * @return the snapshot_stock_value_id
     */
    public long getSnapshot_stock_value_id() {
        return snapshot_stock_value_id;
    }

    /**
     * @param snapshot_stock_value_id the snapshot_stock_value_id to set
     */
    public void setSnapshot_stock_value_id(long snapshot_stock_value_id) {
        this.snapshot_stock_value_id = snapshot_stock_value_id;
    }

    /**
     * @return the snapshot_no
     */
    public int getSnapshot_no() {
        return snapshot_no;
    }

    /**
     * @param snapshot_no the snapshot_no to set
     */
    public void setSnapshot_no(int snapshot_no) {
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
     * @return the currentqty
     */
    public double getCurrentqty() {
        return currentqty;
    }

    /**
     * @param currentqty the currentqty to set
     */
    public void setCurrentqty(double currentqty) {
        this.currentqty = currentqty;
    }

    /**
     * @return the unit_cost_price
     */
    public double getUnit_cost_price() {
        return unit_cost_price;
    }

    /**
     * @param unit_cost_price the unit_cost_price to set
     */
    public void setUnit_cost_price(double unit_cost_price) {
        this.unit_cost_price = unit_cost_price;
    }

    /**
     * @return the cp_value
     */
    public double getCp_value() {
        return cp_value;
    }

    /**
     * @param cp_value the cp_value to set
     */
    public void setCp_value(double cp_value) {
        this.cp_value = cp_value;
    }

    /**
     * @return the wp_value
     */
    public double getWp_value() {
        return wp_value;
    }

    /**
     * @param wp_value the wp_value to set
     */
    public void setWp_value(double wp_value) {
        this.wp_value = wp_value;
    }

    /**
     * @return the rp_value
     */
    public double getRp_value() {
        return rp_value;
    }

    /**
     * @param rp_value the rp_value to set
     */
    public void setRp_value(double rp_value) {
        this.rp_value = rp_value;
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
     * @return the qty_damage
     */
    public double getQty_damage() {
        return qty_damage;
    }

    /**
     * @param qty_damage the qty_damage to set
     */
    public void setQty_damage(double qty_damage) {
        this.qty_damage = qty_damage;
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
