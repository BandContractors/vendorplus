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
    private String rate_name;
    private double rate_perc;
    private double rate_value;
    private double excise_tax;
    private String currency_code_tax;
    private String unit_code_tax;

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
     * @return the rate_perc
     */
    public double getRate_perc() {
        return rate_perc;
    }

    /**
     * @param rate_perc the rate_perc to set
     */
    public void setRate_perc(double rate_perc) {
        this.rate_perc = rate_perc;
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
     * @return the excise_tax
     */
    public double getExcise_tax() {
        return excise_tax;
    }

    /**
     * @param excise_tax the excise_tax to set
     */
    public void setExcise_tax(double excise_tax) {
        this.excise_tax = excise_tax;
    }

    /**
     * @return the currency_code_tax
     */
    public String getCurrency_code_tax() {
        return currency_code_tax;
    }

    /**
     * @param currency_code_tax the currency_code_tax to set
     */
    public void setCurrency_code_tax(String currency_code_tax) {
        this.currency_code_tax = currency_code_tax;
    }

    /**
     * @return the unit_code_tax
     */
    public String getUnit_code_tax() {
        return unit_code_tax;
    }

    /**
     * @param unit_code_tax the unit_code_tax to set
     */
    public void setUnit_code_tax(String unit_code_tax) {
        this.unit_code_tax = unit_code_tax;
    }
}
