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
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;
    private int UnitId;
    private String UnitName;
    private String UnitSymbol;
    private String unit_symbol_tax;

    /**
     * @return the UnitId
     */
    public int getUnitId() {
        return UnitId;
    }

    /**
     * @param UnitId the UnitId to set
     */
    public void setUnitId(int UnitId) {
        this.UnitId = UnitId;
    }

    /**
     * @return the UnitName
     */
    public String getUnitName() {
        return UnitName;
    }

    /**
     * @param UnitName the UnitName to set
     */
    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    /**
     * @return the UnitSymbol
     */
    public String getUnitSymbol() {
        return UnitSymbol;
    }

    /**
     * @param UnitSymbol the UnitSymbol to set
     */
    public void setUnitSymbol(String UnitSymbol) {
        this.UnitSymbol = UnitSymbol;
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

    
}
