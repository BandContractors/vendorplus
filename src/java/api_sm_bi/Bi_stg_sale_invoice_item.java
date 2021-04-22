package api_sm_bi;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Bi_stg_sale_invoice_item implements Serializable {

    private static final long serialVersionUID = 1L;
    private String section_code;
    private String branch_code;
    private String business_code;
    private String group_code;
    private String invoice_number;
    private Date sales_date;
    private String customer_name;
    private double gross_amount;
    private double trade_discount;
    private double cash_discount;
    private double tax_amount;
    private double profit_margin;
    private double amount_tendered;
    private String staff_code;
    private String currency_code;
    private String country_code;
    private String loc_level2_code;
    private String loc_level3_code;

    public Bi_stg_sale_invoice_item() {
        this.section_code = "";
        this.branch_code = "";
        this.business_code = "";
        this.group_code = "";
        this.invoice_number = "";
        this.sales_date = new Date();
        this.customer_name = "";
        this.gross_amount = 0;
        this.trade_discount = 0;
        this.cash_discount = 0;
        this.tax_amount = 0;
        this.profit_margin = 0;
        this.amount_tendered = 0;
        this.staff_code = "";
        this.currency_code = "";
        this.country_code = "";
        this.loc_level2_code = "";
        this.loc_level3_code = "";
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
     * @return the sales_date
     */
    public Date getSales_date() {
        return sales_date;
    }

    /**
     * @param sales_date the sales_date to set
     */
    public void setSales_date(Date sales_date) {
        this.sales_date = sales_date;
    }

    /**
     * @return the customer_name
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * @param customer_name the customer_name to set
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * @return the gross_amount
     */
    public double getGross_amount() {
        return gross_amount;
    }

    /**
     * @param gross_amount the gross_amount to set
     */
    public void setGross_amount(double gross_amount) {
        this.gross_amount = gross_amount;
    }

    /**
     * @return the trade_discount
     */
    public double getTrade_discount() {
        return trade_discount;
    }

    /**
     * @param trade_discount the trade_discount to set
     */
    public void setTrade_discount(double trade_discount) {
        this.trade_discount = trade_discount;
    }

    /**
     * @return the cash_discount
     */
    public double getCash_discount() {
        return cash_discount;
    }

    /**
     * @param cash_discount the cash_discount to set
     */
    public void setCash_discount(double cash_discount) {
        this.cash_discount = cash_discount;
    }

    /**
     * @return the tax_amount
     */
    public double getTax_amount() {
        return tax_amount;
    }

    /**
     * @param tax_amount the tax_amount to set
     */
    public void setTax_amount(double tax_amount) {
        this.tax_amount = tax_amount;
    }

    /**
     * @return the profit_margin
     */
    public double getProfit_margin() {
        return profit_margin;
    }

    /**
     * @param profit_margin the profit_margin to set
     */
    public void setProfit_margin(double profit_margin) {
        this.profit_margin = profit_margin;
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
     * @return the country_code
     */
    public String getCountry_code() {
        return country_code;
    }

    /**
     * @param country_code the country_code to set
     */
    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    /**
     * @return the loc_level2_code
     */
    public String getLoc_level2_code() {
        return loc_level2_code;
    }

    /**
     * @param loc_level2_code the loc_level2_code to set
     */
    public void setLoc_level2_code(String loc_level2_code) {
        this.loc_level2_code = loc_level2_code;
    }

    /**
     * @return the loc_level3_code
     */
    public String getLoc_level3_code() {
        return loc_level3_code;
    }

    /**
     * @param loc_level3_code the loc_level3_code to set
     */
    public void setLoc_level3_code(String loc_level3_code) {
        this.loc_level3_code = loc_level3_code;
    }

}
