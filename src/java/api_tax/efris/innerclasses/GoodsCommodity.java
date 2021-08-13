/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

import java.io.Serializable;

/**
 *
 * @author bajuna
 */
public class GoodsCommodity implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public String getCommodityCategoryCode() {
        return commodityCategoryCode;
    }

    public void setCommodityCategoryCode(String commodityCategoryCode) {
        this.commodityCategoryCode = commodityCategoryCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCommodityCategoryName() {
        return commodityCategoryName;
    }

    public void setCommodityCategoryName(String commodityCategoryName) {
        this.commodityCategoryName = commodityCategoryName;
    }

    public String getCommodityCategoryLevel() {
        return commodityCategoryLevel;
    }

    public void setCommodityCategoryLevel(String commodityCategoryLevel) {
        this.commodityCategoryLevel = commodityCategoryLevel;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getIsLeafNode() {
        return isLeafNode;
    }

    public void setIsLeafNode(String isLeafNode) {
        this.isLeafNode = isLeafNode;
    }

    public String getServiceMark() {
        return serviceMark;
    }

    public void setServiceMark(String serviceMark) {
        this.serviceMark = serviceMark;
    }

    public String getIsZeroRate() {
        return isZeroRate;
    }

    public void setIsZeroRate(String isZeroRate) {
        this.isZeroRate = isZeroRate;
    }

    public String getZeroRateStartDate() {
        return zeroRateStartDate;
    }

    public void setZeroRateStartDate(String zeroRateStartDate) {
        this.zeroRateStartDate = zeroRateStartDate;
    }

    public String getZeroRateEndDate() {
        return zeroRateEndDate;
    }

    public void setZeroRateEndDate(String zeroRateEndDate) {
        this.zeroRateEndDate = zeroRateEndDate;
    }

    public String getIsExempt() {
        return isExempt;
    }

    public void setIsExempt(String isExempt) {
        this.isExempt = isExempt;
    }

    public String getExemptRateStartDate() {
        return exemptRateStartDate;
    }

    public void setExemptRateStartDate(String exemptRateStartDate) {
        this.exemptRateStartDate = exemptRateStartDate;
    }

    public String getExemptRateEndDate() {
        return exemptRateEndDate;
    }

    public void setExemptRateEndDate(String exemptRateEndDate) {
        this.exemptRateEndDate = exemptRateEndDate;
    }

    public String getEnableStatusCode() {
        return enableStatusCode;
    }

    public void setEnableStatusCode(String enableStatusCode) {
        this.enableStatusCode = enableStatusCode;
    }

    public String getExclusion() {
        return exclusion;
    }

    public void setExclusion(String exclusion) {
        this.exclusion = exclusion;
    }

}
