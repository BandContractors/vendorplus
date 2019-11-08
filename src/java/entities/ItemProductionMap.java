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
public class ItemProductionMap implements Serializable {

    private static final long serialVersionUID = 1L;
    private long ItemProductionMapId;
    private long OutputItemId;
    private long InputItemId;
    private double InputQty;
    private double InputQtyTotal;
    private String OutputItemName;
    private String InputItemName;
    private String OutputItemUnit;
    private String InputItemUnit;
    private String Batchno;
    private String CodeSpecific;
    private String DescSpecific;
    private long StockId;
    private double InputQtyCurrent;
    private double InputQtyBalance;

    /**
     * @return the ItemProductionMapId
     */
    public long getItemProductionMapId() {
        return ItemProductionMapId;
    }

    /**
     * @param ItemProductionMapId the ItemProductionMapId to set
     */
    public void setItemProductionMapId(long ItemProductionMapId) {
        this.ItemProductionMapId = ItemProductionMapId;
    }

    /**
     * @return the OutputItemId
     */
    public long getOutputItemId() {
        return OutputItemId;
    }

    /**
     * @param OutputItemId the OutputItemId to set
     */
    public void setOutputItemId(long OutputItemId) {
        this.OutputItemId = OutputItemId;
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
     * @return the OutputItemName
     */
    public String getOutputItemName() {
        return OutputItemName;
    }

    /**
     * @param OutputItemName the OutputItemName to set
     */
    public void setOutputItemName(String OutputItemName) {
        this.OutputItemName = OutputItemName;
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
     * @return the OutputItemUnit
     */
    public String getOutputItemUnit() {
        return OutputItemUnit;
    }

    /**
     * @param OutputItemUnit the OutputItemUnit to set
     */
    public void setOutputItemUnit(String OutputItemUnit) {
        this.OutputItemUnit = OutputItemUnit;
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
     * @return the StockId
     */
    public long getStockId() {
        return StockId;
    }

    /**
     * @param StockId the StockId to set
     */
    public void setStockId(long StockId) {
        this.StockId = StockId;
    }

    /**
     * @return the InputQtyTotal
     */
    public double getInputQtyTotal() {
        return InputQtyTotal;
    }

    /**
     * @param InputQtyTotal the InputQtyTotal to set
     */
    public void setInputQtyTotal(double InputQtyTotal) {
        this.InputQtyTotal = InputQtyTotal;
    }

    /**
     * @return the InputQtyCurrent
     */
    public double getInputQtyCurrent() {
        return InputQtyCurrent;
    }

    /**
     * @param InputQtyCurrent the InputQtyCurrent to set
     */
    public void setInputQtyCurrent(double InputQtyCurrent) {
        this.InputQtyCurrent = InputQtyCurrent;
    }

    /**
     * @return the InputQtyBalance
     */
    public double getInputQtyBalance() {
        return InputQtyBalance;
    }

    /**
     * @param InputQtyBalance the InputQtyBalance to set
     */
    public void setInputQtyBalance(double InputQtyBalance) {
        this.InputQtyBalance = InputQtyBalance;
    }

}
