/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author emuwonge
 */
public class EFRIS_goods_commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    private long EFRIS_goods_commodity_id;
    private String commodityCategoryCode;
    private String parentCode;
    private String commodityCategoryName;
    private String commodityCategoryLevel;
    private String rate;
    private String isLeafNode;
    private String serviceMark;
    private String isZeroRate;
    private String zeroRateStartDate;
    private String zeroRateEndDate;
    private String isExempt;
    private String exemptRateStartDate;
    private String exemptRateEndDate;
    private String enableStatusCode;
    private String exclusion;

    //Additionals
    //private String added_by;
    private Date add_date;

    /**
     * @return the EFRIS_goods_commodity_id
     */
    public long getEFRIS_goods_commodity_id() {
        return EFRIS_goods_commodity_id;
    }

    /**
     * @param EFRIS_goods_commodity_id the EFRIS_goods_commodity_id to set
     */
    public void setEFRIS_goods_commodity_id(long EFRIS_goods_commodity_id) {
        this.EFRIS_goods_commodity_id = EFRIS_goods_commodity_id;
    }

    /**
     * @return the commodityCategoryCode
     */
    public String getCommodityCategoryCode() {
        return commodityCategoryCode;
    }

    /**
     * @param commodityCategoryCode the commodityCategoryCode to set
     */
    public void setCommodityCategoryCode(String commodityCategoryCode) {
        this.commodityCategoryCode = commodityCategoryCode;
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
     * @return the commodityCategoryName
     */
    public String getCommodityCategoryName() {
        return commodityCategoryName;
    }

    /**
     * @param commodityCategoryName the commodityCategoryName to set
     */
    public void setCommodityCategoryName(String commodityCategoryName) {
        this.commodityCategoryName = commodityCategoryName;
    }

    /**
     * @return the commodityCategoryLevel
     */
    public String getCommodityCategoryLevel() {
        return commodityCategoryLevel;
    }

    /**
     * @param commodityCategoryLevel the commodityCategoryLevel to set
     */
    public void setCommodityCategoryLevel(String commodityCategoryLevel) {
        this.commodityCategoryLevel = commodityCategoryLevel;
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
     * @return the serviceMark
     */
    public String getServiceMark() {
        return serviceMark;
    }

    /**
     * @param serviceMark the serviceMark to set
     */
    public void setServiceMark(String serviceMark) {
        this.serviceMark = serviceMark;
    }

    /**
     * @return the isZeroRate
     */
    public String getIsZeroRate() {
        return isZeroRate;
    }

    /**
     * @param isZeroRate the isZeroRate to set
     */
    public void setIsZeroRate(String isZeroRate) {
        this.isZeroRate = isZeroRate;
    }

    /**
     * @return the zeroRateStartDate
     */
    public String getZeroRateStartDate() {
        return zeroRateStartDate;
    }

    /**
     * @param zeroRateStartDate the zeroRateStartDate to set
     */
    public void setZeroRateStartDate(String zeroRateStartDate) {
        this.zeroRateStartDate = zeroRateStartDate;
    }

    /**
     * @return the zeroRateEndDate
     */
    public String getZeroRateEndDate() {
        return zeroRateEndDate;
    }

    /**
     * @param zeroRateEndDate the zeroRateEndDate to set
     */
    public void setZeroRateEndDate(String zeroRateEndDate) {
        this.zeroRateEndDate = zeroRateEndDate;
    }

    /**
     * @return the isExempt
     */
    public String getIsExempt() {
        return isExempt;
    }

    /**
     * @param isExempt the isExempt to set
     */
    public void setIsExempt(String isExempt) {
        this.isExempt = isExempt;
    }

    /**
     * @return the exemptRateStartDate
     */
    public String getExemptRateStartDate() {
        return exemptRateStartDate;
    }

    /**
     * @param exemptRateStartDate the exemptRateStartDate to set
     */
    public void setExemptRateStartDate(String exemptRateStartDate) {
        this.exemptRateStartDate = exemptRateStartDate;
    }

    /**
     * @return the exemptRateEndDate
     */
    public String getExemptRateEndDate() {
        return exemptRateEndDate;
    }

    /**
     * @param exemptRateEndDate the exemptRateEndDate to set
     */
    public void setExemptRateEndDate(String exemptRateEndDate) {
        this.exemptRateEndDate = exemptRateEndDate;
    }

    /**
     * @return the enableStatusCode
     */
    public String getEnableStatusCode() {
        return enableStatusCode;
    }

    /**
     * @param enableStatusCode the enableStatusCode to set
     */
    public void setEnableStatusCode(String enableStatusCode) {
        this.enableStatusCode = enableStatusCode;
    }

    /**
     * @return the exclusion
     */
    public String getExclusion() {
        return exclusion;
    }

    /**
     * @param exclusion the exclusion to set
     */
    public void setExclusion(String exclusion) {
        this.exclusion = exclusion;
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
}
