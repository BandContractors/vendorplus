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
public class Unit_tax_list implements Serializable {

    private static final long serialVersionUID = 1L;
    private int unit_tax_list_id;
    private String unit_symbol_tax;
    private String unit_name_tax;

    /**
     * @return the unit_tax_list_id
     */
    public int getUnit_tax_list_id() {
        return unit_tax_list_id;
    }

    /**
     * @param unit_tax_list_id the unit_tax_list_id to set
     */
    public void setUnit_tax_list_id(int unit_tax_list_id) {
        this.unit_tax_list_id = unit_tax_list_id;
    }

    /**
     * @return the unit_symbol_tax
     */
    public String getUnit_symbol_tax() {
        return unit_symbol_tax;
    }

    /**
     * @param unit_symbol_tax the unit_symbol_tax to set
     */
    public void setUnit_symbol_tax(String unit_symbol_tax) {
        this.unit_symbol_tax = unit_symbol_tax;
    }

    /**
     * @return the unit_name_tax
     */
    public String getUnit_name_tax() {
        return unit_name_tax;
    }

    /**
     * @param unit_name_tax the unit_name_tax to set
     */
    public void setUnit_name_tax(String unit_name_tax) {
        this.unit_name_tax = unit_name_tax;
    }

}
