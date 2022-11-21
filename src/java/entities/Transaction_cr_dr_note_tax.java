package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Transaction_cr_dr_note_tax implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_cr_dr_note_tax_id;
    private long transaction_id;
    private String tax_category;
    private String tax_rate_name;
    private double tax_rate;
    private double taxable_amount;
    private double tax_amount;

    /**
     * @return the transaction_cr_dr_note_tax_id
     */
    public long getTransaction_cr_dr_note_tax_id() {
        return transaction_cr_dr_note_tax_id;
    }

    /**
     * @param transaction_cr_dr_note_tax_id the transaction_cr_dr_note_tax_id to set
     */
    public void setTransaction_cr_dr_note_tax_id(long transaction_cr_dr_note_tax_id) {
        this.transaction_cr_dr_note_tax_id = transaction_cr_dr_note_tax_id;
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
     * @return the tax_category
     */
    public String getTax_category() {
        return tax_category;
    }

    /**
     * @param tax_category the tax_category to set
     */
    public void setTax_category(String tax_category) {
        this.tax_category = tax_category;
    }

    /**
     * @return the tax_rate_name
     */
    public String getTax_rate_name() {
        return tax_rate_name;
    }

    /**
     * @param tax_rate_name the tax_rate_name to set
     */
    public void setTax_rate_name(String tax_rate_name) {
        this.tax_rate_name = tax_rate_name;
    }

    /**
     * @return the tax_rate
     */
    public double getTax_rate() {
        return tax_rate;
    }

    /**
     * @param tax_rate the tax_rate to set
     */
    public void setTax_rate(double tax_rate) {
        this.tax_rate = tax_rate;
    }

    /**
     * @return the taxable_amount
     */
    public double getTaxable_amount() {
        return taxable_amount;
    }

    /**
     * @param taxable_amount the taxable_amount to set
     */
    public void setTaxable_amount(double taxable_amount) {
        this.taxable_amount = taxable_amount;
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
}
