package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Item_unit implements Serializable {

    private static final long serialVersionUID = 1L;

    private int unit_id;
    private String unit_symbol;
    private String unit_name;
    private int is_base;
    private int default_purchase;
    private int default_sale;
    private double base_qty;
    private double other_qty;
    private double unit_retailsale_price;
    private double unit_wholesale_price;

    /**
     * @return the unit_id
     */
    public int getUnit_id() {
        return unit_id;
    }

    /**
     * @param unit_id the unit_id to set
     */
    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
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
     * @return the unit_name
     */
    public String getUnit_name() {
        return unit_name;
    }

    /**
     * @param unit_name the unit_name to set
     */
    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    /**
     * @return the is_base
     */
    public int getIs_base() {
        return is_base;
    }

    /**
     * @param is_base the is_base to set
     */
    public void setIs_base(int is_base) {
        this.is_base = is_base;
    }

    /**
     * @return the default_purchase
     */
    public int getDefault_purchase() {
        return default_purchase;
    }

    /**
     * @param default_purchase the default_purchase to set
     */
    public void setDefault_purchase(int default_purchase) {
        this.default_purchase = default_purchase;
    }

    /**
     * @return the default_sale
     */
    public int getDefault_sale() {
        return default_sale;
    }

    /**
     * @param default_sale the default_sale to set
     */
    public void setDefault_sale(int default_sale) {
        this.default_sale = default_sale;
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
     * @return the unit_retailsale_price
     */
    public double getUnit_retailsale_price() {
        return unit_retailsale_price;
    }

    /**
     * @param unit_retailsale_price the unit_retailsale_price to set
     */
    public void setUnit_retailsale_price(double unit_retailsale_price) {
        this.unit_retailsale_price = unit_retailsale_price;
    }

    /**
     * @return the unit_wholesale_price
     */
    public double getUnit_wholesale_price() {
        return unit_wholesale_price;
    }

    /**
     * @param unit_wholesale_price the unit_wholesale_price to set
     */
    public void setUnit_wholesale_price(double unit_wholesale_price) {
        this.unit_wholesale_price = unit_wholesale_price;
    }

}
