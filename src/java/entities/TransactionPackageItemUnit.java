/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TransactionPackageItemUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transactionItemId;
    private long unitId;
    private double baseUnitQty;

    /**
     * @return the transactionItemId
     */
    public long getTransactionItemId() {
        return transactionItemId;
    }

    /**
     * @param transactionItemId the transactionItemId to set
     */
    public void setTransactionItemId(long transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

    /**
     * @return the unitId
     */
    public long getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    /**
     * @return the baseUnitQty
     */
    public double getBaseUnitQty() {
        return baseUnitQty;
    }

    /**
     * @param baseUnitQty the baseUnitQty to set
     */
    public void setBaseUnitQty(double baseUnitQty) {
        this.baseUnitQty = baseUnitQty;
    }

   
}
