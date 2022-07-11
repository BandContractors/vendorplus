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
public class EFRIS_good_detail implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long EFRIS_good_detail_id;
    private String invoiceNo;
    private String referenceNo;
    private String item;
    private String itemCode;
    private String qty;
    private String unitOfMeasure;
    private String unitPrice;
    private String total;
    private String taxRate;
    private String tax;
    private String discountTotal;
    private String discountTaxRate;
    private String orderNumber;
    private String discountFlag;
    private String deemedFlag;
    private String exciseFlag;
    private String categoryId;
    private String categoryName;
    private String goodsCategoryId;
    private String goodsCategoryName;
    private String exciseRate;
    private String exciseRule;
    private String exciseTax;
    private String pack;
    private String stick;
    private String exciseUnit;
    private String exciseCurrency;
    private String exciseRateName;
    
    //Additionals
    private int process_flag;
    private Date add_date;
    private Date process_date;

    // Getter Methods 
    public String getItem() {
        return item;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getQty() {
        return qty;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getTotal() {
        return total;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public String getTax() {
        return tax;
    }

    public String getDiscountTotal() {
        return discountTotal;
    }

    public String getDiscountTaxRate() {
        return discountTaxRate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDiscountFlag() {
        return discountFlag;
    }

    public String getDeemedFlag() {
        return deemedFlag;
    }

    public String getExciseFlag() {
        return exciseFlag;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public String getGoodsCategoryName() {
        return goodsCategoryName;
    }

    public String getExciseRate() {
        return exciseRate;
    }

    public String getExciseRule() {
        return exciseRule;
    }

    public String getExciseTax() {
        return exciseTax;
    }

    public String getPack() {
        return pack;
    }

    public String getStick() {
        return stick;
    }

    public String getExciseUnit() {
        return exciseUnit;
    }

    public String getExciseCurrency() {
        return exciseCurrency;
    }

    public String getExciseRateName() {
        return exciseRateName;
    }

    // Setter Methods 
    public void setItem(String item) {
        this.item = item;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setDiscountTotal(String discountTotal) {
        this.discountTotal = discountTotal;
    }

    public void setDiscountTaxRate(String discountTaxRate) {
        this.discountTaxRate = discountTaxRate;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setDiscountFlag(String discountFlag) {
        this.discountFlag = discountFlag;
    }

    public void setDeemedFlag(String deemedFlag) {
        this.deemedFlag = deemedFlag;
    }

    public void setExciseFlag(String exciseFlag) {
        this.exciseFlag = exciseFlag;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public void setGoodsCategoryName(String goodsCategoryName) {
        this.goodsCategoryName = goodsCategoryName;
    }

    public void setExciseRate(String exciseRate) {
        this.exciseRate = exciseRate;
    }

    public void setExciseRule(String exciseRule) {
        this.exciseRule = exciseRule;
    }

    public void setExciseTax(String exciseTax) {
        this.exciseTax = exciseTax;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }

    public void setExciseUnit(String exciseUnit) {
        this.exciseUnit = exciseUnit;
    }

    public void setExciseCurrency(String exciseCurrency) {
        this.exciseCurrency = exciseCurrency;
    }

    public void setExciseRateName(String exciseRateName) {
        this.exciseRateName = exciseRateName;
    }

    /**
     * @return the process_flag
     */
    public int getProcess_flag() {
        return process_flag;
    }

    /**
     * @param process_flag the process_flag to set
     */
    public void setProcess_flag(int process_flag) {
        this.process_flag = process_flag;
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
     * @return the process_date
     */
    public Date getProcess_date() {
        return process_date;
    }

    /**
     * @param process_date the process_date to set
     */
    public void setProcess_date(Date process_date) {
        this.process_date = process_date;
    }

    /**
     * @return the EFRIS_good_detail_id
     */
    public long getEFRIS_good_detail_id() {
        return EFRIS_good_detail_id;
    }

    /**
     * @param EFRIS_good_detail_id the EFRIS_good_detail_id to set
     */
    public void setEFRIS_good_detail_id(long EFRIS_good_detail_id) {
        this.EFRIS_good_detail_id = EFRIS_good_detail_id;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the referenceNo
     */
    public String getReferenceNo() {
        return referenceNo;
    }

    /**
     * @param referenceNo the referenceNo to set
     */
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
}
