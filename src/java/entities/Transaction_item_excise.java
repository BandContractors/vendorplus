package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Transaction_item_excise implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_item_excise_id;
    private long transaction_item_id;
    private String excise_duty_code;
    private String rate_text;
    private String rate_name;
    private String rate_name_type;
    private double rate_value;
    private double calc_excise_tax_amount;
    private String rate_currency_code_tax;
    private String rate_unit_code_tax;

    public Transaction_item_excise() {
        this.transaction_item_excise_id = 0;
        this.transaction_item_id = 0;
        this.excise_duty_code = "";
        this.rate_text = "";
        this.rate_name = "";
        this.rate_name_type = "";
        this.rate_value = 0.0;
        this.calc_excise_tax_amount = 0.0;
        this.rate_currency_code_tax = "";
        this.rate_unit_code_tax = "";
    }

    /**
     * @return the transaction_item_excise_id
     */
    public long getTransaction_item_excise_id() {
        return transaction_item_excise_id;
    }

    /**
     * @param transaction_item_excise_id the transaction_item_excise_id to set
     */
    public void setTransaction_item_excise_id(long transaction_item_excise_id) {
        this.transaction_item_excise_id = transaction_item_excise_id;
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
     * @return the excise_duty_code
     */
    public String getExcise_duty_code() {
        return excise_duty_code;
    }

    /**
     * @param excise_duty_code the excise_duty_code to set
     */
    public void setExcise_duty_code(String excise_duty_code) {
        this.excise_duty_code = excise_duty_code;
    }

    /**
     * @return the rate_name
     */
    public String getRate_name() {
        return rate_name;
    }

    /**
     * @param rate_name the rate_name to set
     */
    public void setRate_name(String rate_name) {
        this.rate_name = rate_name;
    }

    /**
     * @return the rate_value
     */
    public double getRate_value() {
        return rate_value;
    }

    /**
     * @param rate_value the rate_value to set
     */
    public void setRate_value(double rate_value) {
        this.rate_value = rate_value;
    }

    /**
     * @return the calc_excise_tax_amount
     */
    public double getCalc_excise_tax_amount() {
        return calc_excise_tax_amount;
    }

    /**
     * @param calc_excise_tax_amount the calc_excise_tax_amount to set
     */
    public void setCalc_excise_tax_amount(double calc_excise_tax_amount) {
        this.calc_excise_tax_amount = calc_excise_tax_amount;
    }

    /**
     * @return the rate_currency_code_tax
     */
    public String getRate_currency_code_tax() {
        return rate_currency_code_tax;
    }

    /**
     * @param rate_currency_code_tax the rate_currency_code_tax to set
     */
    public void setRate_currency_code_tax(String rate_currency_code_tax) {
        this.rate_currency_code_tax = rate_currency_code_tax;
    }

    /**
     * @return the rate_unit_code_tax
     */
    public String getRate_unit_code_tax() {
        return rate_unit_code_tax;
    }

    /**
     * @param rate_unit_code_tax the rate_unit_code_tax to set
     */
    public void setRate_unit_code_tax(String rate_unit_code_tax) {
        this.rate_unit_code_tax = rate_unit_code_tax;
    }

    /**
     * @return the rate_text
     */
    public String getRate_text() {
        return rate_text;
    }

    /**
     * @param rate_text the rate_text to set
     */
    public void setRate_text(String rate_text) {
        this.rate_text = rate_text;
    }

    /**
     * @return the rate_name_type
     */
    public String getRate_name_type() {
        return rate_name_type;
    }

    /**
     * @param rate_name_type the rate_name_type to set
     */
    public void setRate_name_type(String rate_name_type) {
        this.rate_name_type = rate_name_type;
    }
}
