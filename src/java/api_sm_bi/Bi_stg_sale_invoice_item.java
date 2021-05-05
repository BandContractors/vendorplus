package api_sm_bi;

import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Bi_stg_sale_invoice_item implements Serializable {

    private static final long serialVersionUID = 1L;
    private String bi_item_code;
    private String src_item_description;
    private double qty;
    private double unit_price;
    private double unit_trade_discount;
    private double unit_vat;
    private double amount;
    private String vat_rated;
    private double unit_cost_price;
    private double unit_profit_margin;

    public Bi_stg_sale_invoice_item() {
        this.bi_item_code = "";
        this.src_item_description = "";
        this.qty = 0;
        this.unit_price = 0;
        this.unit_trade_discount = 0;
        this.unit_vat = 0;
        this.amount = 0;
        this.vat_rated = "";
        this.unit_cost_price = 0;
        this.unit_profit_margin = 0;
    }

    /**
     * @return the bi_item_code
     */
    public String getBi_item_code() {
        return bi_item_code;
    }

    /**
     * @param bi_item_code the bi_item_code to set
     */
    public void setBi_item_code(String bi_item_code) {
        this.bi_item_code = bi_item_code;
    }

    /**
     * @return the src_item_description
     */
    public String getSrc_item_description() {
        return src_item_description;
    }

    /**
     * @param src_item_description the src_item_description to set
     */
    public void setSrc_item_description(String src_item_description) {
        this.src_item_description = src_item_description;
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    /**
     * @return the unit_price
     */
    public double getUnit_price() {
        return unit_price;
    }

    /**
     * @param unit_price the unit_price to set
     */
    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    /**
     * @return the unit_trade_discount
     */
    public double getUnit_trade_discount() {
        return unit_trade_discount;
    }

    /**
     * @param unit_trade_discount the unit_trade_discount to set
     */
    public void setUnit_trade_discount(double unit_trade_discount) {
        this.unit_trade_discount = unit_trade_discount;
    }

    /**
     * @return the unit_vat
     */
    public double getUnit_vat() {
        return unit_vat;
    }

    /**
     * @param unit_vat the unit_vat to set
     */
    public void setUnit_vat(double unit_vat) {
        this.unit_vat = unit_vat;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the vat_rated
     */
    public String getVat_rated() {
        return vat_rated;
    }

    /**
     * @param vat_rated the vat_rated to set
     */
    public void setVat_rated(String vat_rated) {
        this.vat_rated = vat_rated;
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
     * @return the unit_profit_margin
     */
    public double getUnit_profit_margin() {
        return unit_profit_margin;
    }

    /**
     * @param unit_profit_margin the unit_profit_margin to set
     */
    public void setUnit_profit_margin(double unit_profit_margin) {
        this.unit_profit_margin = unit_profit_margin;
    }

}
