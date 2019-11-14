package entities;

import java.io.Serializable;
import java.util.Date;
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
public class TransProductionItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private long TransProductionItemId;
    private long TransProductionId;
    private long InputItemId;
    private String InputItemName;
    private String InputItemUnit;
    private double InputQty;
    private double InputUnitCost;
    private String Batchno;
    private String CodeSpecific;
    private String DescSpecific;
    private String DescMore;
    private double input_unit_qty;
    private double input_qty_bfr_prod;
    private double input_qty_afr_prod;
    

    /**
     * @return the TransProductionItemId
     */
    public long getTransProductionItemId() {
        return TransProductionItemId;
    }

    /**
     * @param TransProductionItemId the TransProductionItemId to set
     */
    public void setTransProductionItemId(long TransProductionItemId) {
        this.TransProductionItemId = TransProductionItemId;
    }

    /**
     * @return the TransProductionId
     */
    public long getTransProductionId() {
        return TransProductionId;
    }

    /**
     * @param TransProductionId the TransProductionId to set
     */
    public void setTransProductionId(long TransProductionId) {
        this.TransProductionId = TransProductionId;
    }

    /**
     * @return the Batchno
     */
    public String getBatchno() {
        return Batchno;
    }

    /**
     * @param Batchno the Batchno to set
     */
    public void setBatchno(String Batchno) {
        this.Batchno = Batchno;
    }

    /**
     * @return the InputItemId
     */
    public long getInputItemId() {
        return InputItemId;
    }

    /**
     * @param InputItemId the InputItemId to set
     */
    public void setInputItemId(long InputItemId) {
        this.InputItemId = InputItemId;
    }

    /**
     * @return the InputQty
     */
    public double getInputQty() {
        return InputQty;
    }

    /**
     * @param InputQty the InputQty to set
     */
    public void setInputQty(double InputQty) {
        this.InputQty = InputQty;
    }

    /**
     * @return the InputUnitCost
     */
    public double getInputUnitCost() {
        return InputUnitCost;
    }

    /**
     * @param InputUnitCost the InputUnitCost to set
     */
    public void setInputUnitCost(double InputUnitCost) {
        this.InputUnitCost = InputUnitCost;
    }

    /**
     * @return the CodeSpecific
     */
    public String getCodeSpecific() {
        return CodeSpecific;
    }

    /**
     * @param CodeSpecific the CodeSpecific to set
     */
    public void setCodeSpecific(String CodeSpecific) {
        this.CodeSpecific = CodeSpecific;
    }

    /**
     * @return the DescSpecific
     */
    public String getDescSpecific() {
        return DescSpecific;
    }

    /**
     * @param DescSpecific the DescSpecific to set
     */
    public void setDescSpecific(String DescSpecific) {
        this.DescSpecific = DescSpecific;
    }

    /**
     * @return the DescMore
     */
    public String getDescMore() {
        return DescMore;
    }

    /**
     * @param DescMore the DescMore to set
     */
    public void setDescMore(String DescMore) {
        this.DescMore = DescMore;
    }

    /**
     * @return the InputItemName
     */
    public String getInputItemName() {
        return InputItemName;
    }

    /**
     * @param InputItemName the InputItemName to set
     */
    public void setInputItemName(String InputItemName) {
        this.InputItemName = InputItemName;
    }

    /**
     * @return the InputItemUnit
     */
    public String getInputItemUnit() {
        return InputItemUnit;
    }

    /**
     * @param InputItemUnit the InputItemUnit to set
     */
    public void setInputItemUnit(String InputItemUnit) {
        this.InputItemUnit = InputItemUnit;
    }

    /**
     * @return the input_unit_qty
     */
    public double getInput_unit_qty() {
        return input_unit_qty;
    }

    /**
     * @param input_unit_qty the input_unit_qty to set
     */
    public void setInput_unit_qty(double input_unit_qty) {
        this.input_unit_qty = input_unit_qty;
    }

    /**
     * @return the input_qty_bfr_prod
     */
    public double getInput_qty_bfr_prod() {
        return input_qty_bfr_prod;
    }

    /**
     * @param input_qty_bfr_prod the input_qty_bfr_prod to set
     */
    public void setInput_qty_bfr_prod(double input_qty_bfr_prod) {
        this.input_qty_bfr_prod = input_qty_bfr_prod;
    }

    /**
     * @return the input_qty_afr_prod
     */
    public double getInput_qty_afr_prod() {
        return input_qty_afr_prod;
    }

    /**
     * @param input_qty_afr_prod the input_qty_afr_prod to set
     */
    public void setInput_qty_afr_prod(double input_qty_afr_prod) {
        this.input_qty_afr_prod = input_qty_afr_prod;
    }

 
}
