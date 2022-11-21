package entities;

import beans.MenuItemBean;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class TransactionPackageItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private long transactionPackageItemId;
    private long transactionPackageId;
    private long itemId;
    private int unitId;
    private String batchNo;
    private String codeSpecific;
    private String descSpecific;
    private String descMore;
    private double itemQty;
    private double baseUnitQty;
    private double unitPrice;
    private double unitTradeDiscount;
    private double unitVat;
    private String exciseRateName;
    private double exciseRatePerc;
    private double exciseRateValue;
    private double exciseTax;
    private double exciseCurrencyCodeTax;
    private double exciseUnitCodeTax;
    private double amount;
    private String vatRated;
    private double vatPerc;
    private String narration;
    private long stockId;
    //variables for report ONLY
    private String itemDescription;
    private String unitSymbol;
    private String itemCode;
    private int isTaxSynced;
    private String alias_name;
    private int display_alias_name;
    private int is_override_price;
    private int override_gen_name;
    private int is_general;

    /**
     * @return the transactionPackageItemId
     */
    public long getTransactionPackageItemId() {
        return transactionPackageItemId;
    }

    /**
     * @param transactionPackageItemId the transactionPackageItemId to set
     */
    public void setTransactionPackageItemId(long transactionPackageItemId) {
        this.transactionPackageItemId = transactionPackageItemId;
    }

    /**
     * @return the transactionPackageId
     */
    public long getTransactionPackageId() {
        return transactionPackageId;
    }

    /**
     * @param transactionPackageId the transactionPackageId to set
     */
    public void setTransactionPackageId(long transactionPackageId) {
        this.transactionPackageId = transactionPackageId;
    }

    /**
     * @return the itemId
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the codeSpecific
     */
    public String getCodeSpecific() {
        return codeSpecific;
    }

    /**
     * @param codeSpecific the codeSpecific to set
     */
    public void setCodeSpecific(String codeSpecific) {
        this.codeSpecific = codeSpecific;
    }

    /**
     * @return the descSpecific
     */
    public String getDescSpecific() {
        return descSpecific;
    }

    /**
     * @param descSpecific the descSpecific to set
     */
    public void setDescSpecific(String descSpecific) {
        this.descSpecific = descSpecific;
    }

    /**
     * @return the descMore
     */
    public String getDescMore() {
        return descMore;
    }

    /**
     * @param descMore the descMore to set
     */
    public void setDescMore(String descMore) {
        this.descMore = descMore;
    }

    /**
     * @return the itemQty
     */
    public double getItemQty() {
        return itemQty;
    }

    /**
     * @param itemQty the itemQty to set
     */
    public void setItemQty(double itemQty) {
        this.itemQty = itemQty;
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

    /**
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the unitVat
     */
    public double getUnitVat() {
        return unitVat;
    }

    /**
     * @param unitVat the unitVat to set
     */
    public void setUnitVat(double unitVat) {
        this.unitVat = unitVat;
    }

    /**
     * @return the exciseRateName
     */
    public String getExciseRateName() {
        return exciseRateName;
    }

    /**
     * @param exciseRateName the exciseRateName to set
     */
    public void setExciseRateName(String exciseRateName) {
        this.exciseRateName = exciseRateName;
    }

    /**
     * @return the exciseRatePerc
     */
    public double getExciseRatePerc() {
        return exciseRatePerc;
    }

    /**
     * @param exciseRatePerc the exciseRatePerc to set
     */
    public void setExciseRatePerc(double exciseRatePerc) {
        this.exciseRatePerc = exciseRatePerc;
    }

    /**
     * @return the exciseRateValue
     */
    public double getExciseRateValue() {
        return exciseRateValue;
    }

    /**
     * @param exciseRateValue the exciseRateValue to set
     */
    public void setExciseRateValue(double exciseRateValue) {
        this.exciseRateValue = exciseRateValue;
    }

    /**
     * @return the exciseTax
     */
    public double getExciseTax() {
        return exciseTax;
    }

    /**
     * @param exciseTax the exciseTax to set
     */
    public void setExciseTax(double exciseTax) {
        this.exciseTax = exciseTax;
    }

    /**
     * @return the exciseCurrencyCodeTax
     */
    public double getExciseCurrencyCodeTax() {
        return exciseCurrencyCodeTax;
    }

    /**
     * @param exciseCurrencyCodeTax the exciseCurrencyCodeTax to set
     */
    public void setExciseCurrencyCodeTax(double exciseCurrencyCodeTax) {
        this.exciseCurrencyCodeTax = exciseCurrencyCodeTax;
    }

    /**
     * @return the exciseUnitCodeTax
     */
    public double getExciseUnitCodeTax() {
        return exciseUnitCodeTax;
    }

    /**
     * @param exciseUnitCodeTax the exciseUnitCodeTax to set
     */
    public void setExciseUnitCodeTax(double exciseUnitCodeTax) {
        this.exciseUnitCodeTax = exciseUnitCodeTax;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the vatRated
     */
    public String getVatRated() {
        return vatRated;
    }

    /**
     * @param vatRated the vatRated to set
     */
    public void setVatRated(String vatRated) {
        this.vatRated = vatRated;
    }

    /**
     * @return the vatPerc
     */
    public double getVatPerc() {
        return vatPerc;
    }

    /**
     * @param vatPerc the vatPerc to set
     */
    public void setVatPerc(double vatPerc) {
        this.vatPerc = vatPerc;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * @return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * @param itemDescription the itemDescription to set
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * @return the unitTradeDiscount
     */
    public double getUnitTradeDiscount() {
        return unitTradeDiscount;
    }

    /**
     * @param unitTradeDiscount the unitTradeDiscount to set
     */
    public void setUnitTradeDiscount(double unitTradeDiscount) {
        this.unitTradeDiscount = unitTradeDiscount;
    }

    /**
     * @return the unitId
     */
    public int getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    /**
     * @return the unitSymbol
     */
    public String getUnitSymbol() {
        return unitSymbol;
    }

    /**
     * @param unitSymbol the unitSymbol to set
     */
    public void setUnitSymbol(String unitSymbol) {
        this.unitSymbol = unitSymbol;
    }

    /**
     * @return the itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode the itemCode to set
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * @return the isTaxSynced
     */
    public int getIsTaxSynced() {
        return isTaxSynced;
    }

    /**
     * @param isTaxSynced the isTaxSynced to set
     */
    public void setIsTaxSynced(int isTaxSynced) {
        this.isTaxSynced = isTaxSynced;
    }

    /**
     * @return the stockId
     */
    public long getStockId() {
        return stockId;
    }

    /**
     * @param stockId the stockId to set
     */
    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    /**
     * @return the alias_name
     */
    public String getAlias_name() {
        return alias_name;
    }

    /**
     * @param alias_name the alias_name to set
     */
    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    /**
     * @return the display_alias_name
     */
    public int getDisplay_alias_name() {
        return display_alias_name;
    }

    /**
     * @param display_alias_name the display_alias_name to set
     */
    public void setDisplay_alias_name(int display_alias_name) {
        this.display_alias_name = display_alias_name;
    }

    /**
     * @return the is_override_price
     */
    public int getIs_override_price() {
        return is_override_price;
    }

    /**
     * @param is_override_price the is_override_price to set
     */
    public void setIs_override_price(int is_override_price) {
        this.is_override_price = is_override_price;
    }

    /**
     * @return the override_gen_name
     */
    public int getOverride_gen_name() {
        return override_gen_name;
    }

    /**
     * @param override_gen_name the override_gen_name to set
     */
    public void setOverride_gen_name(int override_gen_name) {
        this.override_gen_name = override_gen_name;
    }

    /**
     * @return the is_general
     */
    public int getIs_general() {
        return is_general;
    }

    /**
     * @param is_general the is_general to set
     */
    public void setIs_general(int is_general) {
        this.is_general = is_general;
    }
}
