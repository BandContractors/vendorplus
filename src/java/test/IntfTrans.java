package test;

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
public class IntfTrans implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transId;
    private String custName;
    private double totalAmount;

    public IntfTrans() {
        this.transId = 0;
        this.custName = "";
        this.totalAmount = 0;
    }

    /**
     * @return the transId
     */
    public long getTransId() {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId(long transId) {
        this.transId = transId;
    }

    /**
     * @return the custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the totalAmount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
