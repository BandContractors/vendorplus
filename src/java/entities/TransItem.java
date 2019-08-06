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
public class TransItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private long TransactionItemId;
    private long TransactionId;
    private long ItemId;
    private String Batchno;
    private double ItemQty;
    private double UnitPrice;
    private double Amount;
    private Date ItemExpryDate;
    private Date ItemMnfDate;
    private double UnitVat;
    private double UnitTradeDiscount;
    private String VatRated;
    private double VatPerc;
    private String ItemCode;
    private int keyCode;
    private double UnitPriceIncVat;
    private double UnitPriceExcVat;
    private double AmountIncVat;
    private double AmountExcVat;
    private long ItemId2;
    private double ItemQty2;
    private double FractionQty;
    private String StockEffect;
    private String IsTradeDiscountVatLiable;
    private boolean OverridePrices;
    private boolean SpecifyWarranty;
    private double UnitPrice2;
    private double UnitTradeDiscount2;
    private String VatRated2;
    //variables for report ONLY
    private String Description;
    private String alias_name;
    private int display_alias_name;
    private String UnitSymbol;
    private int StoreId;
    private int Store2Id;
    private Date TransactionDate;
    private Date AddDate;
    private Date EditDate;
    private long TransactorId;
    private int TransactionTypeId;
    private int TransactionReasonId;
    private int AddUserDetailId;
    private int EditUserDetailId;
    private String StoreName;
    private String StoreName2;
    private String AddUserDetailName;
    private String EditUserDetailName;
    private String TransactorNames;
    private String TransactionTypeName;
    private String TransactionReasonName;
    private int TransactionUserDetailId;
    private String TransactionUserDetailName;
    private long BillTransactorId;
    private String BillTransactorName;
    //for profit margin
    private double UnitCostPrice;
    private double UnitProfitMargin;
    //for user transaction earning
    private double EarnPerc;
    private double EarnAmount;
    //for bill repprt summary
    private String CategoryName;
    private double SumAmountIncVat;
    private double SumAmountExcVat;
    //more for bms version
    private String CodeSpecific;
    private String DescSpecific;
    private String DescMore;
    private String WarrantyDesc;
    private Date WarrantyExpiryDate;
    //for code specific's stock
    private long StockId;
    //specific account code
    private String AccountCode;
    private String AccountName;
    private Date PurchaseDate;
    private Date DepStartDate;
    private int DepMethodId;
    private double DepRate;
    private int AverageMethodId;
    private int EffectiveLife;
    private double ResidualValue;
    private String Narration;
    private double qty_balance;
    private double duration_value;
    private double qty_damage;
    private double duration_passed;
    private double qty_taken;
    private double qty_total;
    private double qty_damage_total;
    private int item_no;
    private String item_currency_code;
    

    /**
     * @return the TransactionItemId
     */
    public long getTransactionItemId() {
        return TransactionItemId;
    }

    /**
     * @param TransactionItemId the TransactionItemId to set
     */
    public void setTransactionItemId(long TransactionItemId) {
        this.TransactionItemId = TransactionItemId;
    }

    /**
     * @return the TransactionId
     */
    public long getTransactionId() {
        return TransactionId;
    }

    /**
     * @param TransactionId the TransactionId to set
     */
    public void setTransactionId(long TransactionId) {
        this.TransactionId = TransactionId;
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
     * @return the ItemQty
     */
    public double getItemQty() {
        return ItemQty;
    }

    /**
     * @param ItemQty the ItemQty to set
     */
    public void setItemQty(double ItemQty) {
        this.ItemQty = ItemQty;
    }

    /**
     * @return the UnitPrice
     */
    public double getUnitPrice() {
        return UnitPrice;
    }

    /**
     * @param UnitPrice the UnitPrice to set
     */
    public void setUnitPrice(double UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    /**
     * @return the Amount
     */
    public double getAmount() {
        return Amount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the ItemExpryDate
     */
    public Date getItemExpryDate() {
        return ItemExpryDate;
    }

    /**
     * @param ItemExpryDate the ItemExpryDate to set
     */
    public void setItemExpryDate(Date ItemExpryDate) {
        this.ItemExpryDate = ItemExpryDate;
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
     * @return the UnitVat
     */
    public double getUnitVat() {
        return UnitVat;
    }

    /**
     * @param UnitVat the UnitVat to set
     */
    public void setUnitVat(double UnitVat) {
        this.UnitVat = UnitVat;
    }

    /**
     * @return the UnitTradeDiscount
     */
    public double getUnitTradeDiscount() {
        return UnitTradeDiscount;
    }

    /**
     * @param UnitTradeDiscount the UnitTradeDiscount to set
     */
    public void setUnitTradeDiscount(double UnitTradeDiscount) {
        this.UnitTradeDiscount = UnitTradeDiscount;
    }

    /**
     * @return the VatRated
     */
    public String getVatRated() {
        return VatRated;
    }

    /**
     * @param VatRated the VatRated to set
     */
    public void setVatRated(String VatRated) {
        this.VatRated = VatRated;
    }

    /**
     * @return the VatPerc
     */
    public double getVatPerc() {
        return VatPerc;
    }

    /**
     * @param VatPerc the VatPerc to set
     */
    public void setVatPerc(double VatPerc) {
        this.VatPerc = VatPerc;
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
     * @return the keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * @param keyCode the keyCode to set
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * @return the UnitPriceIncVat
     */
    public double getUnitPriceIncVat() {
        return UnitPriceIncVat;
    }

    /**
     * @param UnitPriceIncVat the UnitPriceIncVat to set
     */
    public void setUnitPriceIncVat(double UnitPriceIncVat) {
        this.UnitPriceIncVat = UnitPriceIncVat;
    }

    /**
     * @return the UnitPriceExcVat
     */
    public double getUnitPriceExcVat() {
        return UnitPriceExcVat;
    }

    /**
     * @param UnitPriceExcVat the UnitPriceExcVat to set
     */
    public void setUnitPriceExcVat(double UnitPriceExcVat) {
        this.UnitPriceExcVat = UnitPriceExcVat;
    }

    /**
     * @return the AmountIncVat
     */
    public double getAmountIncVat() {
        return AmountIncVat;
    }

    /**
     * @param AmountIncVat the AmountIncVat to set
     */
    public void setAmountIncVat(double AmountIncVat) {
        this.AmountIncVat = AmountIncVat;
    }

    /**
     * @return the AmountExcVat
     */
    public double getAmountExcVat() {
        return AmountExcVat;
    }

    /**
     * @param AmountExcVat the AmountExcVat to set
     */
    public void setAmountExcVat(double AmountExcVat) {
        this.AmountExcVat = AmountExcVat;
    }

    /**
     * @return the ItemId2
     */
    public long getItemId2() {
        return ItemId2;
    }

    /**
     * @param ItemId2 the ItemId2 to set
     */
    public void setItemId2(long ItemId2) {
        this.ItemId2 = ItemId2;
    }

    /**
     * @return the ItemQty2
     */
    public double getItemQty2() {
        return ItemQty2;
    }

    /**
     * @param ItemQty2 the ItemQty2 to set
     */
    public void setItemQty2(double ItemQty2) {
        this.ItemQty2 = ItemQty2;
    }

    /**
     * @return the FractionQty
     */
    public double getFractionQty() {
        return FractionQty;
    }

    /**
     * @param FractionQty the FractionQty to set
     */
    public void setFractionQty(double FractionQty) {
        this.FractionQty = FractionQty;
    }

    /**
     * @return the StockEffect
     */
    public String getStockEffect() {
        return StockEffect;
    }

    /**
     * @param StockEffect the StockEffect to set
     */
    public void setStockEffect(String StockEffect) {
        this.StockEffect = StockEffect;
    }

    /**
     * @return the IsTradeDiscountVatLiable
     */
    public String getIsTradeDiscountVatLiable() {
        return IsTradeDiscountVatLiable;
    }

    /**
     * @param IsTradeDiscountVatLiable the IsTradeDiscountVatLiable to set
     */
    public void setIsTradeDiscountVatLiable(String IsTradeDiscountVatLiable) {
        this.IsTradeDiscountVatLiable = IsTradeDiscountVatLiable;
    }

    /**
     * @return the OverridePrices
     */
    public boolean isOverridePrices() {
        return OverridePrices;
    }

    /**
     * @param OverridePrices the OverridePrices to set
     */
    public void setOverridePrices(boolean OverridePrices) {
        this.OverridePrices = OverridePrices;
    }

    /**
     * @return the UnitPrice2
     */
    public double getUnitPrice2() {
        return UnitPrice2;
    }

    /**
     * @param UnitPrice2 the UnitPrice2 to set
     */
    public void setUnitPrice2(double UnitPrice2) {
        this.UnitPrice2 = UnitPrice2;
    }

    /**
     * @return the UnitTradeDiscount2
     */
    public double getUnitTradeDiscount2() {
        return UnitTradeDiscount2;
    }

    /**
     * @param UnitTradeDiscount2 the UnitTradeDiscount2 to set
     */
    public void setUnitTradeDiscount2(double UnitTradeDiscount2) {
        this.UnitTradeDiscount2 = UnitTradeDiscount2;
    }

    /**
     * @return the VatRated2
     */
    public String getVatRated2() {
        return VatRated2;
    }

    /**
     * @param VatRated2 the VatRated2 to set
     */
    public void setVatRated2(String VatRated2) {
        this.VatRated2 = VatRated2;
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
     * @return the Store2Id
     */
    public int getStore2Id() {
        return Store2Id;
    }

    /**
     * @param Store2Id the Store2Id to set
     */
    public void setStore2Id(int Store2Id) {
        this.Store2Id = Store2Id;
    }

    /**
     * @return the TransactionDate
     */
    public Date getTransactionDate() {
        return TransactionDate;
    }

    /**
     * @param TransactionDate the TransactionDate to set
     */
    public void setTransactionDate(Date TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    /**
     * @return the AddDate
     */
    public Date getAddDate() {
        return AddDate;
    }

    /**
     * @param AddDate the AddDate to set
     */
    public void setAddDate(Date AddDate) {
        this.AddDate = AddDate;
    }

    /**
     * @return the EditDate
     */
    public Date getEditDate() {
        return EditDate;
    }

    /**
     * @param EditDate the EditDate to set
     */
    public void setEditDate(Date EditDate) {
        this.EditDate = EditDate;
    }

    /**
     * @return the TransactorId
     */
    public long getTransactorId() {
        return TransactorId;
    }

    /**
     * @param TransactorId the TransactorId to set
     */
    public void setTransactorId(long TransactorId) {
        this.TransactorId = TransactorId;
    }

    /**
     * @return the TransactionTypeId
     */
    public int getTransactionTypeId() {
        return TransactionTypeId;
    }

    /**
     * @param TransactionTypeId the TransactionTypeId to set
     */
    public void setTransactionTypeId(int TransactionTypeId) {
        this.TransactionTypeId = TransactionTypeId;
    }

    /**
     * @return the TransactionReasonId
     */
    public int getTransactionReasonId() {
        return TransactionReasonId;
    }

    /**
     * @param TransactionReasonId the TransactionReasonId to set
     */
    public void setTransactionReasonId(int TransactionReasonId) {
        this.TransactionReasonId = TransactionReasonId;
    }

    /**
     * @return the AddUserDetailId
     */
    public int getAddUserDetailId() {
        return AddUserDetailId;
    }

    /**
     * @param AddUserDetailId the AddUserDetailId to set
     */
    public void setAddUserDetailId(int AddUserDetailId) {
        this.AddUserDetailId = AddUserDetailId;
    }

    /**
     * @return the EditUserDetailId
     */
    public int getEditUserDetailId() {
        return EditUserDetailId;
    }

    /**
     * @param EditUserDetailId the EditUserDetailId to set
     */
    public void setEditUserDetailId(int EditUserDetailId) {
        this.EditUserDetailId = EditUserDetailId;
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
     * @return the StoreName2
     */
    public String getStoreName2() {
        return StoreName2;
    }

    /**
     * @param StoreName2 the StoreName2 to set
     */
    public void setStoreName2(String StoreName2) {
        this.StoreName2 = StoreName2;
    }

    /**
     * @return the AddUserDetailName
     */
    public String getAddUserDetailName() {
        return AddUserDetailName;
    }

    /**
     * @param AddUserDetailName the AddUserDetailName to set
     */
    public void setAddUserDetailName(String AddUserDetailName) {
        this.AddUserDetailName = AddUserDetailName;
    }

    /**
     * @return the EditUserDetailName
     */
    public String getEditUserDetailName() {
        return EditUserDetailName;
    }

    /**
     * @param EditUserDetailName the EditUserDetailName to set
     */
    public void setEditUserDetailName(String EditUserDetailName) {
        this.EditUserDetailName = EditUserDetailName;
    }

    /**
     * @return the TransactorNames
     */
    public String getTransactorNames() {
        return TransactorNames;
    }

    /**
     * @param TransactorNames the TransactorNames to set
     */
    public void setTransactorNames(String TransactorNames) {
        this.TransactorNames = TransactorNames;
    }

    /**
     * @return the TransactionTypeName
     */
    public String getTransactionTypeName() {
        return TransactionTypeName;
    }

    /**
     * @param TransactionTypeName the TransactionTypeName to set
     */
    public void setTransactionTypeName(String TransactionTypeName) {
        this.TransactionTypeName = TransactionTypeName;
    }

    /**
     * @return the TransactionReasonName
     */
    public String getTransactionReasonName() {
        return TransactionReasonName;
    }

    /**
     * @param TransactionReasonName the TransactionReasonName to set
     */
    public void setTransactionReasonName(String TransactionReasonName) {
        this.TransactionReasonName = TransactionReasonName;
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
     * @return the UnitProfitMargin
     */
    public double getUnitProfitMargin() {
        return UnitProfitMargin;
    }

    /**
     * @param UnitProfitMargin the UnitProfitMargin to set
     */
    public void setUnitProfitMargin(double UnitProfitMargin) {
        this.UnitProfitMargin = UnitProfitMargin;
    }

    /**
     * @return the TransactionUserDetailId
     */
    public int getTransactionUserDetailId() {
        return TransactionUserDetailId;
    }

    /**
     * @param TransactionUserDetailId the TransactionUserDetailId to set
     */
    public void setTransactionUserDetailId(int TransactionUserDetailId) {
        this.TransactionUserDetailId = TransactionUserDetailId;
    }

    /**
     * @return the TransactionUserDetailName
     */
    public String getTransactionUserDetailName() {
        return TransactionUserDetailName;
    }

    /**
     * @param TransactionUserDetailName the TransactionUserDetailName to set
     */
    public void setTransactionUserDetailName(String TransactionUserDetailName) {
        this.TransactionUserDetailName = TransactionUserDetailName;
    }

    /**
     * @return the BillTransactorId
     */
    public long getBillTransactorId() {
        return BillTransactorId;
    }

    /**
     * @param BillTransactorId the BillTransactorId to set
     */
    public void setBillTransactorId(long BillTransactorId) {
        this.BillTransactorId = BillTransactorId;
    }

    /**
     * @return the BillTransactorName
     */
    public String getBillTransactorName() {
        return BillTransactorName;
    }

    /**
     * @param BillTransactorName the BillTransactorName to set
     */
    public void setBillTransactorName(String BillTransactorName) {
        this.BillTransactorName = BillTransactorName;
    }

    /**
     * @return the EarnPerc
     */
    public double getEarnPerc() {
        return EarnPerc;
    }

    /**
     * @param EarnPerc the EarnPerc to set
     */
    public void setEarnPerc(double EarnPerc) {
        this.EarnPerc = EarnPerc;
    }

    /**
     * @return the EarnAmount
     */
    public double getEarnAmount() {
        return EarnAmount;
    }

    /**
     * @param EarnAmount the EarnAmount to set
     */
    public void setEarnAmount(double EarnAmount) {
        this.EarnAmount = EarnAmount;
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
     * @return the SumAmountIncVat
     */
    public double getSumAmountIncVat() {
        return SumAmountIncVat;
    }

    /**
     * @param SumAmountIncVat the SumAmountIncVat to set
     */
    public void setSumAmountIncVat(double SumAmountIncVat) {
        this.SumAmountIncVat = SumAmountIncVat;
    }

    /**
     * @return the SumAmountExcVat
     */
    public double getSumAmountExcVat() {
        return SumAmountExcVat;
    }

    /**
     * @param SumAmountExcVat the SumAmountExcVat to set
     */
    public void setSumAmountExcVat(double SumAmountExcVat) {
        this.SumAmountExcVat = SumAmountExcVat;
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
     * @return the SpecifyWarranty
     */
    public boolean isSpecifyWarranty() {
        return SpecifyWarranty;
    }

    /**
     * @param SpecifyWarranty the SpecifyWarranty to set
     */
    public void setSpecifyWarranty(boolean SpecifyWarranty) {
        this.SpecifyWarranty = SpecifyWarranty;
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
     * @return the Narration
     */
    public String getNarration() {
        return Narration;
    }

    /**
     * @param Narration the Narration to set
     */
    public void setNarration(String Narration) {
        this.Narration = Narration;
    }

    /**
     * @return the qty_balance
     */
    public double getQty_balance() {
        return qty_balance;
    }

    /**
     * @param qty_balance the qty_balance to set
     */
    public void setQty_balance(double qty_balance) {
        this.qty_balance = qty_balance;
    }

    /**
     * @return the duration_value
     */
    public double getDuration_value() {
        return duration_value;
    }

    /**
     * @param duration_value the duration_value to set
     */
    public void setDuration_value(double duration_value) {
        this.duration_value = duration_value;
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
     * @return the duration_passed
     */
    public double getDuration_passed() {
        return duration_passed;
    }

    /**
     * @param duration_passed the duration_passed to set
     */
    public void setDuration_passed(double duration_passed) {
        this.duration_passed = duration_passed;
    }

    /**
     * @return the qty_taken
     */
    public double getQty_taken() {
        return qty_taken;
    }

    /**
     * @param qty_taken the qty_taken to set
     */
    public void setQty_taken(double qty_taken) {
        this.qty_taken = qty_taken;
    }

    /**
     * @return the qty_total
     */
    public double getQty_total() {
        return qty_total;
    }

    /**
     * @param qty_total the qty_total to set
     */
    public void setQty_total(double qty_total) {
        this.qty_total = qty_total;
    }

    /**
     * @return the qty_damage_total
     */
    public double getQty_damage_total() {
        return qty_damage_total;
    }

    /**
     * @param qty_damage_total the qty_damage_total to set
     */
    public void setQty_damage_total(double qty_damage_total) {
        this.qty_damage_total = qty_damage_total;
    }

    /**
     * @return the item_no
     */
    public int getItem_no() {
        return item_no;
    }

    /**
     * @param item_no the item_no to set
     */
    public void setItem_no(int item_no) {
        this.item_no = item_no;
    }

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
     * @return the AccountName
     */
    public String getAccountName() {
        return AccountName;
    }

    /**
     * @param AccountName the AccountName to set
     */
    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    /**
     * @return the item_currency_code
     */
    public String getItem_currency_code() {
        return item_currency_code;
    }

    /**
     * @param item_currency_code the item_currency_code to set
     */
    public void setItem_currency_code(String item_currency_code) {
        this.item_currency_code = item_currency_code;
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

}
