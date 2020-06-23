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
    public class GoodsDetails {

    public GoodsDetails() {
        this.categoryId="";
        this.categoryName="";
        this.deemedFlag="";
        this.discountFlag="";
        this.discountTaxRate="";
        this.discountTotal="";
        this.exciseCurrency="";
        this.exciseFlag="";
        this.exciseRate="";
        this.exciseRateName="";
        this.exciseRule="";
        this.exciseTax="";
        this.exciseUnit="";
        this.goodsCategoryId="";
        this.goodsCategoryName="";
        this.item="";
        this.itemCode="";
        this.orderNumber="";
        this.pack="";
        this.qty="";
        this.stick="";
        this.tax="";
        this.taxRate="";
        this.total="";
        this.unitOfMeasure="";
        this.unitPrice="";
    }

    public GoodsDetails(String item, String itemCode, String qty, String unitOfMeasure, String unitPrice, String total, String taxRate, String tax, String discountTotal, String discountTaxRate, String orderNumber, String discountFlag, String deemedFlag, String exciseFlag, String categoryId, String categoryName, String goodsCategoryId, String goodsCategoryName, String exciseRate, String exciseRule, String exciseTax, String pack, String stick, String exciseUnit, String exciseCurrency, String exciseRateName) {
        this.item = item;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitOfMeasure = unitOfMeasure;
        this.unitPrice = unitPrice;
        this.total = total;
        this.taxRate = taxRate;
        this.tax = tax;
        this.discountTotal = discountTotal;
        this.discountTaxRate = discountTaxRate;
        this.orderNumber = orderNumber;
        this.discountFlag = discountFlag;
        this.deemedFlag = deemedFlag;
        this.exciseFlag = exciseFlag;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.goodsCategoryId = goodsCategoryId;
        this.goodsCategoryName = goodsCategoryName;
        this.exciseRate = exciseRate;
        this.exciseRule = exciseRule;
        this.exciseTax = exciseTax;
        this.pack = pack;
        this.stick = stick;
        this.exciseUnit = exciseUnit;
        this.exciseCurrency = exciseCurrency;
        this.exciseRateName = exciseRateName;
    }

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
    }
