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
public class Stocktake_session_item implements Serializable {

    private static final long serialVersionUID = 1L;
    private long stock_take_session_item_id;
    private long stock_take_session_id;
    private Date add_date;
    private String add_by;
    private long item_id;
    private String batchno;
    private String code_specific;
    private String desc_specific;
    private double specific_size;
    private double qty_system;
    private double qty_physical;
    private double qty_short;
    private double qty_over;
    private double unit_cost;
    private int qty_diff_adjusted;
    private String notes;
    private String description;
    private String unit_symbol;

    /**
     * @return the stock_take_session_item_id
     */
    public long getStock_take_session_item_id() {
        return stock_take_session_item_id;
    }

    /**
     * @param stock_take_session_item_id the stock_take_session_item_id to set
     */
    public void setStock_take_session_item_id(long stock_take_session_item_id) {
        this.stock_take_session_item_id = stock_take_session_item_id;
    }

    /**
     * @return the stock_take_session_id
     */
    public long getStock_take_session_id() {
        return stock_take_session_id;
    }

    /**
     * @param stock_take_session_id the stock_take_session_id to set
     */
    public void setStock_take_session_id(long stock_take_session_id) {
        this.stock_take_session_id = stock_take_session_id;
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
    public String getAdd_by() {
        return add_by;
    }

    /**
     * @param add_by the add_by to set
     */
    public void setAdd_by(String add_by) {
        this.add_by = add_by;
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
     * @return the qty_system
     */
    public double getQty_system() {
        return qty_system;
    }

    /**
     * @param qty_system the qty_system to set
     */
    public void setQty_system(double qty_system) {
        this.qty_system = qty_system;
    }

    /**
     * @return the qty_physical
     */
    public double getQty_physical() {
        return qty_physical;
    }

    /**
     * @param qty_physical the qty_physical to set
     */
    public void setQty_physical(double qty_physical) {
        this.qty_physical = qty_physical;
    }

    /**
     * @return the qty_short
     */
    public double getQty_short() {
        return qty_short;
    }

    /**
     * @param qty_short the qty_short to set
     */
    public void setQty_short(double qty_short) {
        this.qty_short = qty_short;
    }

    /**
     * @return the qty_over
     */
    public double getQty_over() {
        return qty_over;
    }

    /**
     * @param qty_over the qty_over to set
     */
    public void setQty_over(double qty_over) {
        this.qty_over = qty_over;
    }

    /**
     * @return the unit_cost
     */
    public double getUnit_cost() {
        return unit_cost;
    }

    /**
     * @param unit_cost the unit_cost to set
     */
    public void setUnit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }

    /**
     * @return the qty_diff_adjusted
     */
    public int getQty_diff_adjusted() {
        return qty_diff_adjusted;
    }

    /**
     * @param qty_diff_adjusted the qty_diff_adjusted to set
     */
    public void setQty_diff_adjusted(int qty_diff_adjusted) {
        this.qty_diff_adjusted = qty_diff_adjusted;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
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
}
