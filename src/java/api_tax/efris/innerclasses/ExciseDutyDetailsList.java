/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

import java.io.Serializable;

/**
 *
 * @author emuwonge
 */
public class ExciseDutyDetailsList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String exciseDutyId;
    private String type;
    private String rate;
    private String unit;
    private String currency;

    /**
     * @return the exciseDutyId
     */
    public String getExciseDutyId() {
        return exciseDutyId;
    }

    /**
     * @param exciseDutyId the exciseDutyId to set
     */
    public void setExciseDutyId(String exciseDutyId) {
        this.exciseDutyId = exciseDutyId;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
