/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris;

import api_tax.efris.innerclasses.ExciseDutyDetailsList;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author emuwonge
 */
public class EFRIS_excise_duty_list implements Serializable {

    private static final long serialVersionUID = 1L;

    private long EFRIS_excise_duty_list_id;
    private String id;
    private String exciseDutyCode;
    private String goodService;
    private String parentCode;
    private String rateText;
    private String isLeafNode;
    private String effectiveDate;
    private List<ExciseDutyDetailsList> exciseDutyDetailsList;
    private String unit; //unit_code_tax
    private String currency; //currency_code_tax
    private String rate_perc;
    private String rate_qty;
    private String rateText_perc;
    private String rateText_qty;

    //Additionals
    //private String added_by;
    private Date add_date;
    private String parentName;

    /**
     * @return the EFRIS_excise_duty_list_id
     */
    public long getEFRIS_excise_duty_list_id() {
        return EFRIS_excise_duty_list_id;
    }

    /**
     * @param EFRIS_excise_duty_list_id the EFRIS_excise_duty_list_id to set
     */
    public void setEFRIS_excise_duty_list_id(long EFRIS_excise_duty_list_id) {
        this.EFRIS_excise_duty_list_id = EFRIS_excise_duty_list_id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the exciseDutyCode
     */
    public String getExciseDutyCode() {
        return exciseDutyCode;
    }

    /**
     * @param exciseDutyCode the exciseDutyCode to set
     */
    public void setExciseDutyCode(String exciseDutyCode) {
        this.exciseDutyCode = exciseDutyCode;
    }

    /**
     * @return the goodService
     */
    public String getGoodService() {
        return goodService;
    }

    /**
     * @param goodService the goodService to set
     */
    public void setGoodService(String goodService) {
        this.goodService = goodService;
    }

    /**
     * @return the parentCode
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * @param parentCode the parentCode to set
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * @return the rateText
     */
    public String getRateText() {
        return rateText;
    }

    /**
     * @param rateText the rateText to set
     */
    public void setRateText(String rateText) {
        this.rateText = rateText;
    }

    /**
     * @return the isLeafNode
     */
    public String getIsLeafNode() {
        return isLeafNode;
    }

    /**
     * @param isLeafNode the isLeafNode to set
     */
    public void setIsLeafNode(String isLeafNode) {
        this.isLeafNode = isLeafNode;
    }

    /**
     * @return the effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
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

    /**
     * @return the rate_perc
     */
    public String getRate_perc() {
        return rate_perc;
    }

    /**
     * @param rate_perc the rate_perc to set
     */
    public void setRate_perc(String rate_perc) {
        this.rate_perc = rate_perc;
    }

    /**
     * @return the rate_qty
     */
    public String getRate_qty() {
        return rate_qty;
    }

    /**
     * @param rate_qty the rate_qty to set
     */
    public void setRate_qty(String rate_qty) {
        this.rate_qty = rate_qty;
    }

    /**
     * @return the add_date
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * @param add_date the add_date to set
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * @return the exciseDutyDetailsList
     */
    public List<ExciseDutyDetailsList> getExciseDutyDetailsList() {
        return exciseDutyDetailsList;
    }

    /**
     * @param exciseDutyDetailsList the exciseDutyDetailsList to set
     */
    public void setExciseDutyDetailsList(List<ExciseDutyDetailsList> exciseDutyDetailsList) {
        this.exciseDutyDetailsList = exciseDutyDetailsList;
    }

    /**
     * @return the parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName the parentName to set
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * @return the rateText_perc
     */
    public String getRateText_perc() {
        return rateText_perc;
    }

    /**
     * @param rateText_perc the rateText_perc to set
     */
    public void setRateText_perc(String rateText_perc) {
        this.rateText_perc = rateText_perc;
    }

    /**
     * @return the rateText_qty
     */
    public String getRateText_qty() {
        return rateText_qty;
    }

    /**
     * @param rateText_qty the rateText_qty to set
     */
    public void setRateText_qty(String rateText_qty) {
        this.rateText_qty = rateText_qty;
    }
}
