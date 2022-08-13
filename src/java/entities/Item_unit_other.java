package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Item_unit_other implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    private long item_unit_other_id;
    private long item_id;
    private String description;
    private String base_unit_symbol;
    private double base_qty;
    private int other_unit_id;
    private String other_unit_symbol;
    private double other_qty;
    private double other_unit_retailsale_price;
    private double other_unit_wholesale_price;
    private int other_default_purchase;
    private int other_default_sale;
    private int is_active;
    private String last_edit_by;
    private Date last_edit_date;
    private int base_unit_id;

    /**
     * @return the item_unit_other_id
     */
    public long getItem_unit_other_id() {
        return item_unit_other_id;
    }

    /**
     * @param item_unit_other_id the item_unit_other_id to set
     */
    public void setItem_unit_other_id(long item_unit_other_id) {
        this.item_unit_other_id = item_unit_other_id;
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
     * @return the base_qty
     */
    public double getBase_qty() {
        return base_qty;
    }

    /**
     * @param base_qty the base_qty to set
     */
    public void setBase_qty(double base_qty) {
        this.base_qty = base_qty;
    }

    /**
     * @return the other_unit_id
     */
    public int getOther_unit_id() {
        return other_unit_id;
    }

    /**
     * @param other_unit_id the other_unit_id to set
     */
    public void setOther_unit_id(int other_unit_id) {
        this.other_unit_id = other_unit_id;
    }

    /**
     * @return the other_qty
     */
    public double getOther_qty() {
        return other_qty;
    }

    /**
     * @param other_qty the other_qty to set
     */
    public void setOther_qty(double other_qty) {
        this.other_qty = other_qty;
    }

    /**
     * @return the other_unit_retailsale_price
     */
    public double getOther_unit_retailsale_price() {
        return other_unit_retailsale_price;
    }

    /**
     * @param other_unit_retailsale_price the other_unit_retailsale_price to set
     */
    public void setOther_unit_retailsale_price(double other_unit_retailsale_price) {
        this.other_unit_retailsale_price = other_unit_retailsale_price;
    }

    /**
     * @return the other_unit_wholesale_price
     */
    public double getOther_unit_wholesale_price() {
        return other_unit_wholesale_price;
    }

    /**
     * @param other_unit_wholesale_price the other_unit_wholesale_price to set
     */
    public void setOther_unit_wholesale_price(double other_unit_wholesale_price) {
        this.other_unit_wholesale_price = other_unit_wholesale_price;
    }

    /**
     * @return the other_default_purchase
     */
    public int getOther_default_purchase() {
        return other_default_purchase;
    }

    /**
     * @param other_default_purchase the other_default_purchase to set
     */
    public void setOther_default_purchase(int other_default_purchase) {
        this.other_default_purchase = other_default_purchase;
    }

    /**
     * @return the other_default_sale
     */
    public int getOther_default_sale() {
        return other_default_sale;
    }

    /**
     * @param other_default_sale the other_default_sale to set
     */
    public void setOther_default_sale(int other_default_sale) {
        this.other_default_sale = other_default_sale;
    }

    /**
     * @return the is_active
     */
    public int getIs_active() {
        return is_active;
    }

    /**
     * @param is_active the is_active to set
     */
    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    /**
     * @return the last_edit_by
     */
    public String getLast_edit_by() {
        return last_edit_by;
    }

    /**
     * @param last_edit_by the last_edit_by to set
     */
    public void setLast_edit_by(String last_edit_by) {
        this.last_edit_by = last_edit_by;
    }

    /**
     * @return the last_edit_date
     */
    public Date getLast_edit_date() {
        return last_edit_date;
    }

    /**
     * @param last_edit_date the last_edit_date to set
     */
    public void setLast_edit_date(Date last_edit_date) {
        this.last_edit_date = last_edit_date;
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

    /**
     * @return the other_unit_symbol
     */
    public String getOther_unit_symbol() {
        return other_unit_symbol;
    }

    /**
     * @param other_unit_symbol the other_unit_symbol to set
     */
    public void setOther_unit_symbol(String other_unit_symbol) {
        this.other_unit_symbol = other_unit_symbol;
    }

    /**
     * @return the base_unit_id
     */
    public int getBase_unit_id() {
        return base_unit_id;
    }

    /**
     * @param base_unit_id the base_unit_id to set
     */
    public void setBase_unit_id(int base_unit_id) {
        this.base_unit_id = base_unit_id;
    }

}
