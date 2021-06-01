/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_tax.efris.innerclasses;

/**
 *
 * @author bajuna
 */
public class TaxDetails {

    public TaxDetails() {
        this.exciseCurrency = "";
        this.exciseUnit = "";
        this.grossAmount = "";
        this.netAmount = "";
        this.taxAmount = "";
        this.taxCategory = "";
        this.taxRate = "";
        this.taxRateName = "";
        this.taxCategoryCode="";
    }

    public TaxDetails(String taxCategory, String netAmount, String taxRate, String taxAmount, String grossAmount, String exciseUnit, String exciseCurrency, String taxRateName) {
        this.taxCategory = taxCategory;
        this.netAmount = netAmount;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.grossAmount = grossAmount;
        this.exciseUnit = exciseUnit;
        this.exciseCurrency = exciseCurrency;
        this.taxRateName = taxRateName;
    }

    private String taxCategory;
    private String netAmount;
    private String taxRate;
    private String taxAmount;
    private String grossAmount;
    private String exciseUnit;
    private String exciseCurrency;
    private String taxRateName;
    private String taxCategoryCode;

    // Getter Methods 
    public String getTaxCategory() {
        return taxCategory;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public String getExciseUnit() {
        return exciseUnit;
    }

    public String getExciseCurrency() {
        return exciseCurrency;
    }

    public String getTaxRateName() {
        return taxRateName;
    }

    // Setter Methods 
    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public void setExciseUnit(String exciseUnit) {
        this.exciseUnit = exciseUnit;
    }

    public void setExciseCurrency(String exciseCurrency) {
        this.exciseCurrency = exciseCurrency;
    }

    public void setTaxRateName(String taxRateName) {
        this.taxRateName = taxRateName;
    }

    /**
     * @return the taxCategoryCode
     */
    public String getTaxCategoryCode() {
        return taxCategoryCode;
    }

    /**
     * @param taxCategoryCode the taxCategoryCode to set
     */
    public void setTaxCategoryCode(String taxCategoryCode) {
        this.taxCategoryCode = taxCategoryCode;
    }
}
