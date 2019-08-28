package entities;

import java.io.Serializable;
import java.util.Date;
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
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    private long StockId;
    private int StoreId;
    private long ItemId;
    private String Batchno;
    private double Currentqty;
    private Date ItemMnfDate;
    private Date ItemExpDate;

    private double UnitCost;
    private String CodeSpecific;
    private String DescSpecific;
    private String DescMore;
    private String WarrantyDesc;
    private Date WarrantyExpiryDate;
    private Date PurchaseDate;
    private Date DepStartDate;
    private int DepMethodId;
    private double DepRate;
    private int AverageMethodId;
    private int EffectiveLife;
    private String AccountCode;
    private double ResidualValue;
    private int AssetStatusId;
    private String AssetStatusDesc;
    
    //For Report Purposes Only
    private String ItemCode;
    private String Description;
    private String UnitSymbol;
    private String CurrencyCode;
    private int ReorderLevel;
    private double UnitCostPrice;
    private double UnitRetailsalePrice;
    private double UnitWholesalePrice;
    private String StoreName;
    private String CategoryName;
    private String UnitName;
    private String SubCategoryName;
    private double CostValue;
    private double RetailsaleValue;
    private double WholesaleValue;
    private double qty_damage;
    private double qty_out;
    private double specific_size;
    private String stock_type;

    /**
     * @return the StockId
     */
    public long getStockId() {
        return StockId;
    }

    /**
     * @param StockId the StockId to set
     */
    public void setStockId(long StockId) {
        this.StockId = StockId;
    }

    /**
     * @return the StoreId
     */
    public int getStoreId() {
        return StoreId;
    }

    /**
     * @param StoreId the StoreId to set
     */
    public void setStoreId(int StoreId) {
        this.StoreId = StoreId;
    }

    /**
     * @return the ItemId
     */
    public long getItemId() {
        return ItemId;
    }

    /**
     * @param ItemId the ItemId to set
     */
    public void setItemId(long ItemId) {
        this.ItemId = ItemId;
    }

    /**
     * @return the Batchno
     */
    public String getBatchno() {
        return Batchno;
    }

    /**
     * @param Batchno the Batchno to set
     */
    public void setBatchno(String Batchno) {
        this.Batchno = Batchno;
    }

    /**
     * @return the Currentqty
     */
    public double getCurrentqty() {
        return Currentqty;
    }

    /**
     * @param Currentqty the Currentqty to set
     */
    public void setCurrentqty(double Currentqty) {
        this.Currentqty = Currentqty;
    }

    /**
     * @return the ItemMnfDate
     */
    public Date getItemMnfDate() {
        return ItemMnfDate;
    }

    /**
     * @param ItemMnfDate the ItemMnfDate to set
     */
    public void setItemMnfDate(Date ItemMnfDate) {
        this.ItemMnfDate = ItemMnfDate;
    }

    /**
     * @return the ItemExpDate
     */
    public Date getItemExpDate() {
        return ItemExpDate;
    }

    /**
     * @param ItemExpDate the ItemExpDate to set
     */
    public void setItemExpDate(Date ItemExpDate) {
        this.ItemExpDate = ItemExpDate;
    }

    /**
     * @return the UnitCost
     */
    public double getUnitCost() {
        return UnitCost;
    }

    /**
     * @param UnitCost the UnitCost to set
     */
    public void setUnitCost(double UnitCost) {
        this.UnitCost = UnitCost;
    }

    /**
     * @return the CodeSpecific
     */
    public String getCodeSpecific() {
        return CodeSpecific;
    }

    /**
     * @param CodeSpecific the CodeSpecific to set
     */
    public void setCodeSpecific(String CodeSpecific) {
        this.CodeSpecific = CodeSpecific;
    }

    /**
     * @return the DescSpecific
     */
    public String getDescSpecific() {
        return DescSpecific;
    }

    /**
     * @param DescSpecific the DescSpecific to set
     */
    public void setDescSpecific(String DescSpecific) {
        this.DescSpecific = DescSpecific;
    }

    /**
     * @return the DescMore
     */
    public String getDescMore() {
        return DescMore;
    }

    /**
     * @param DescMore the DescMore to set
     */
    public void setDescMore(String DescMore) {
        this.DescMore = DescMore;
    }

    /**
     * @return the WarrantyExpiryDate
     */
    public Date getWarrantyExpiryDate() {
        return WarrantyExpiryDate;
    }

    /**
     * @param WarrantyExpiryDate the WarrantyExpiryDate to set
     */
    public void setWarrantyExpiryDate(Date WarrantyExpiryDate) {
        this.WarrantyExpiryDate = WarrantyExpiryDate;
    }

    /**
     * @return the PurchaseDate
     */
    public Date getPurchaseDate() {
        return PurchaseDate;
    }

    /**
     * @param PurchaseDate the PurchaseDate to set
     */
    public void setPurchaseDate(Date PurchaseDate) {
        this.PurchaseDate = PurchaseDate;
    }

    /**
     * @return the DepStartDate
     */
    public Date getDepStartDate() {
        return DepStartDate;
    }

    /**
     * @param DepStartDate the DepStartDate to set
     */
    public void setDepStartDate(Date DepStartDate) {
        this.DepStartDate = DepStartDate;
    }

    /**
     * @return the DepMethodId
     */
    public int getDepMethodId() {
        return DepMethodId;
    }

    /**
     * @param DepMethodId the DepMethodId to set
     */
    public void setDepMethodId(int DepMethodId) {
        this.DepMethodId = DepMethodId;
    }

    /**
     * @return the DepRate
     */
    public double getDepRate() {
        return DepRate;
    }

    /**
     * @param DepRate the DepRate to set
     */
    public void setDepRate(double DepRate) {
        this.DepRate = DepRate;
    }

    /**
     * @return the AverageMethodId
     */
    public int getAverageMethodId() {
        return AverageMethodId;
    }

    /**
     * @param AverageMethodId the AverageMethodId to set
     */
    public void setAverageMethodId(int AverageMethodId) {
        this.AverageMethodId = AverageMethodId;
    }

    /**
     * @return the EffectiveLife
     */
    public int getEffectiveLife() {
        return EffectiveLife;
    }

    /**
     * @param EffectiveLife the EffectiveLife to set
     */
    public void setEffectiveLife(int EffectiveLife) {
        this.EffectiveLife = EffectiveLife;
    }

    /**
     * @return the AssetStatusId
     */
    public int getAssetStatusId() {
        return AssetStatusId;
    }

    /**
     * @param AssetStatusId the AssetStatusId to set
     */
    public void setAssetStatusId(int AssetStatusId) {
        this.AssetStatusId = AssetStatusId;
    }

    /**
     * @return the AssetStatusDesc
     */
    public String getAssetStatusDesc() {
        return AssetStatusDesc;
    }

    /**
     * @param AssetStatusDesc the AssetStatusDesc to set
     */
    public void setAssetStatusDesc(String AssetStatusDesc) {
        this.AssetStatusDesc = AssetStatusDesc;
    }

    /**
     * @return the WarrantyDesc
     */
    public String getWarrantyDesc() {
        return WarrantyDesc;
    }

    /**
     * @param WarrantyDesc the WarrantyDesc to set
     */
    public void setWarrantyDesc(String WarrantyDesc) {
        this.WarrantyDesc = WarrantyDesc;
    }

    /**
     * @return the AccountCode
     */
    public String getAccountCode() {
        return AccountCode;
    }

    /**
     * @param AccountCode the AccountCode to set
     */
    public void setAccountCode(String AccountCode) {
        this.AccountCode = AccountCode;
    }

    /**
     * @return the ResidualValue
     */
    public double getResidualValue() {
        return ResidualValue;
    }

    /**
     * @param ResidualValue the ResidualValue to set
     */
    public void setResidualValue(double ResidualValue) {
        this.ResidualValue = ResidualValue;
    }

    /**
     * @return the ItemCode
     */
    public String getItemCode() {
        return ItemCode;
    }

    /**
     * @param ItemCode the ItemCode to set
     */
    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the UnitSymbol
     */
    public String getUnitSymbol() {
        return UnitSymbol;
    }

    /**
     * @param UnitSymbol the UnitSymbol to set
     */
    public void setUnitSymbol(String UnitSymbol) {
        this.UnitSymbol = UnitSymbol;
    }

    /**
     * @return the CurrencyCode
     */
    public String getCurrencyCode() {
        return CurrencyCode;
    }

    /**
     * @param CurrencyCode the CurrencyCode to set
     */
    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    /**
     * @return the ReorderLevel
     */
    public int getReorderLevel() {
        return ReorderLevel;
    }

    /**
     * @param ReorderLevel the ReorderLevel to set
     */
    public void setReorderLevel(int ReorderLevel) {
        this.ReorderLevel = ReorderLevel;
    }

    /**
     * @return the UnitCostPrice
     */
    public double getUnitCostPrice() {
        return UnitCostPrice;
    }

    /**
     * @param UnitCostPrice the UnitCostPrice to set
     */
    public void setUnitCostPrice(double UnitCostPrice) {
        this.UnitCostPrice = UnitCostPrice;
    }

    /**
     * @return the UnitRetailsalePrice
     */
    public double getUnitRetailsalePrice() {
        return UnitRetailsalePrice;
    }

    /**
     * @param UnitRetailsalePrice the UnitRetailsalePrice to set
     */
    public void setUnitRetailsalePrice(double UnitRetailsalePrice) {
        this.UnitRetailsalePrice = UnitRetailsalePrice;
    }

    /**
     * @return the UnitWholesalePrice
     */
    public double getUnitWholesalePrice() {
        return UnitWholesalePrice;
    }

    /**
     * @param UnitWholesalePrice the UnitWholesalePrice to set
     */
    public void setUnitWholesalePrice(double UnitWholesalePrice) {
        this.UnitWholesalePrice = UnitWholesalePrice;
    }

    /**
     * @return the StoreName
     */
    public String getStoreName() {
        return StoreName;
    }

    /**
     * @param StoreName the StoreName to set
     */
    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    /**
     * @return the CategoryName
     */
    public String getCategoryName() {
        return CategoryName;
    }

    /**
     * @param CategoryName the CategoryName to set
     */
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    /**
     * @return the UnitName
     */
    public String getUnitName() {
        return UnitName;
    }

    /**
     * @param UnitName the UnitName to set
     */
    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    /**
     * @return the SubCategoryName
     */
    public String getSubCategoryName() {
        return SubCategoryName;
    }

    /**
     * @param SubCategoryName the SubCategoryName to set
     */
    public void setSubCategoryName(String SubCategoryName) {
        this.SubCategoryName = SubCategoryName;
    }

    /**
     * @return the CostValue
     */
    public double getCostValue() {
        return CostValue;
    }

    /**
     * @param CostValue the CostValue to set
     */
    public void setCostValue(double CostValue) {
        this.CostValue = CostValue;
    }

    /**
     * @return the RetailsaleValue
     */
    public double getRetailsaleValue() {
        return RetailsaleValue;
    }

    /**
     * @param RetailsaleValue the RetailsaleValue to set
     */
    public void setRetailsaleValue(double RetailsaleValue) {
        this.RetailsaleValue = RetailsaleValue;
    }

    /**
     * @return the WholesaleValue
     */
    public double getWholesaleValue() {
        return WholesaleValue;
    }

    /**
     * @param WholesaleValue the WholesaleValue to set
     */
    public void setWholesaleValue(double WholesaleValue) {
        this.WholesaleValue = WholesaleValue;
    }

    /**
     * @return the qty_damage
     */
    public double getQty_damage() {
        return qty_damage;
    }

    /**
     * @param qty_damage the qty_damage to set
     */
    public void setQty_damage(double qty_damage) {
        this.qty_damage = qty_damage;
    }

    /**
     * @return the qty_out
     */
    public double getQty_out() {
        return qty_out;
    }

    /**
     * @param qty_out the qty_out to set
     */
    public void setQty_out(double qty_out) {
        this.qty_out = qty_out;
    }

    /**
     * @return the specific_size
     */
    public double getSpecific_size() {
        return specific_size;
    }

    /**
     * @param specific_size the specific_size to set
     */
    public void setSpecific_size(double specific_size) {
        this.specific_size = specific_size;
    }

    /**
     * @return the stock_type
     */
    public String getStock_type() {
        return stock_type;
    }

    /**
     * @param stock_type the stock_type to set
     */
    public void setStock_type(String stock_type) {
        this.stock_type = stock_type;
    }
}
