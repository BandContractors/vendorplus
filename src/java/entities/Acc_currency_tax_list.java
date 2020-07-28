package entities;

import java.io.Serializable;
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
public class Acc_currency_tax_list implements Serializable {

    private static final long serialVersionUID = 1L;
    private int acc_currency_list_id;
    private String currency_code_tax;
    private String currency_name_tax;

    /**
     * @return the acc_currency_list_id
     */
    public int getAcc_currency_list_id() {
        return acc_currency_list_id;
    }

    /**
     * @param acc_currency_list_id the acc_currency_list_id to set
     */
    public void setAcc_currency_list_id(int acc_currency_list_id) {
        this.acc_currency_list_id = acc_currency_list_id;
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
     * @return the currency_name_tax
     */
    public String getCurrency_name_tax() {
        return currency_name_tax;
    }

    /**
     * @param currency_name_tax the currency_name_tax to set
     */
    public void setCurrency_name_tax(String currency_name_tax) {
        this.currency_name_tax = currency_name_tax;
    }

}
