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
public class TransProduction implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    private long TransactionId;
    private Date TransactionDate;
    private int StoreId;
    private String StoreName;
    private int Store2Id;
    private String Store2Name;
    private int TransactionTypeId;
    private String TransactionTypeName;
    private int TransactionReasonId;
    private String TransactionReasonName;
    private String Batchno;
    private String TransactionRef;
    private long OutputItemId;
    private String OutputItemName;
    private String OutputItemUnit;
    private double OrderedQty;
    private double OutputQty;
    private double OutputUnitCost;
    private double OutputTotalCost;
    private String TransactionComment;
    private int AddUserDetailId;
    private String AddUserDetailName;
    private Date AddDate;
    private int EditUserDetailId;
    private String EditUserDetailName;
    private Date EditDate;
    private Date TransactionDate2;
    private Date AddDate2;
    private Date EditDate2;
    private boolean OtherDates;
    private int TransactionUserDetailId;
    private String TransactionUserDetailName;
    private Date ItemExpiryDate;
    private Date ItemMnfDate;
    private String CodeSpecific;
    private String DescSpecific;
    private String DescMore;
    private int AccChildAccountId;
    private String CurrencyCode;
    private String account_code;
    private long transactor_id;
    private String transaction_number;
    private String transactor_names;
    private double specific_size;
    private double specific_size_qty;
    //for view only
    private Double input_qty;

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
     * @return the Store2Name
     */
    public String getStore2Name() {
        return Store2Name;
    }

    /**
     * @param Store2Name the Store2Name to set
     */
    public void setStore2Name(String Store2Name) {
        this.Store2Name = Store2Name;
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
     * @return the TransactionRef
     */
    public String getTransactionRef() {
        return TransactionRef;
    }

    /**
     * @param TransactionRef the TransactionRef to set
     */
    public void setTransactionRef(String TransactionRef) {
        this.TransactionRef = TransactionRef;
    }

    /**
     * @return the OutputItemId
     */
    public long getOutputItemId() {
        return OutputItemId;
    }

    /**
     * @param OutputItemId the OutputItemId to set
     */
    public void setOutputItemId(long OutputItemId) {
        this.OutputItemId = OutputItemId;
    }

    /**
     * @return the OutputQty
     */
    public double getOutputQty() {
        return OutputQty;
    }

    /**
     * @param OutputQty the OutputQty to set
     */
    public void setOutputQty(double OutputQty) {
        this.OutputQty = OutputQty;
    }

    /**
     * @return the OutputUnitCost
     */
    public double getOutputUnitCost() {
        return OutputUnitCost;
    }

    /**
     * @param OutputUnitCost the OutputUnitCost to set
     */
    public void setOutputUnitCost(double OutputUnitCost) {
        this.OutputUnitCost = OutputUnitCost;
    }

    /**
     * @return the OutputTotalCost
     */
    public double getOutputTotalCost() {
        return OutputTotalCost;
    }

    /**
     * @param OutputTotalCost the OutputTotalCost to set
     */
    public void setOutputTotalCost(double OutputTotalCost) {
        this.OutputTotalCost = OutputTotalCost;
    }

    /**
     * @return the TransactionComment
     */
    public String getTransactionComment() {
        return TransactionComment;
    }

    /**
     * @param TransactionComment the TransactionComment to set
     */
    public void setTransactionComment(String TransactionComment) {
        this.TransactionComment = TransactionComment;
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
     * @return the TransactionDate2
     */
    public Date getTransactionDate2() {
        return TransactionDate2;
    }

    /**
     * @param TransactionDate2 the TransactionDate2 to set
     */
    public void setTransactionDate2(Date TransactionDate2) {
        this.TransactionDate2 = TransactionDate2;
    }

    /**
     * @return the AddDate2
     */
    public Date getAddDate2() {
        return AddDate2;
    }

    /**
     * @param AddDate2 the AddDate2 to set
     */
    public void setAddDate2(Date AddDate2) {
        this.AddDate2 = AddDate2;
    }

    /**
     * @return the EditDate2
     */
    public Date getEditDate2() {
        return EditDate2;
    }

    /**
     * @param EditDate2 the EditDate2 to set
     */
    public void setEditDate2(Date EditDate2) {
        this.EditDate2 = EditDate2;
    }

    /**
     * @return the OtherDates
     */
    public boolean isOtherDates() {
        return OtherDates;
    }

    /**
     * @param OtherDates the OtherDates to set
     */
    public void setOtherDates(boolean OtherDates) {
        this.OtherDates = OtherDates;
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
     * @return the ItemExpiryDate
     */
    public Date getItemExpiryDate() {
        return ItemExpiryDate;
    }

    /**
     * @param ItemExpiryDate the ItemExpiryDate to set
     */
    public void setItemExpiryDate(Date ItemExpiryDate) {
        this.ItemExpiryDate = ItemExpiryDate;
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
     * @return the AccChildAccountId
     */
    public int getAccChildAccountId() {
        return AccChildAccountId;
    }

    /**
     * @param AccChildAccountId the AccChildAccountId to set
     */
    public void setAccChildAccountId(int AccChildAccountId) {
        this.AccChildAccountId = AccChildAccountId;
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
     * @return the account_code
     */
    public String getAccount_code() {
        return account_code;
    }

    /**
     * @param account_code the account_code to set
     */
    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }

    /**
     * @return the OutputItemName
     */
    public String getOutputItemName() {
        return OutputItemName;
    }

    /**
     * @param OutputItemName the OutputItemName to set
     */
    public void setOutputItemName(String OutputItemName) {
        this.OutputItemName = OutputItemName;
    }

    /**
     * @return the transactor_id
     */
    public long getTransactor_id() {
        return transactor_id;
    }

    /**
     * @param transactor_id the transactor_id to set
     */
    public void setTransactor_id(long transactor_id) {
        this.transactor_id = transactor_id;
    }

    /**
     * @return the transaction_number
     */
    public String getTransaction_number() {
        return transaction_number;
    }

    /**
     * @param transaction_number the transaction_number to set
     */
    public void setTransaction_number(String transaction_number) {
        this.transaction_number = transaction_number;
    }

    /**
     * @return the transactor_names
     */
    public String getTransactor_names() {
        return transactor_names;
    }

    /**
     * @param transactor_names the transactor_names to set
     */
    public void setTransactor_names(String transactor_names) {
        this.transactor_names = transactor_names;
    }

    /**
     * @return the OutputItemUnit
     */
    public String getOutputItemUnit() {
        return OutputItemUnit;
    }

    /**
     * @param OutputItemUnit the OutputItemUnit to set
     */
    public void setOutputItemUnit(String OutputItemUnit) {
        this.OutputItemUnit = OutputItemUnit;
    }

    /**
     * @return the OrderedQty
     */
    public double getOrderedQty() {
        return OrderedQty;
    }

    /**
     * @param OrderedQty the OrderedQty to set
     */
    public void setOrderedQty(double OrderedQty) {
        this.OrderedQty = OrderedQty;
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
     * @return the specific_size_qty
     */
    public double getSpecific_size_qty() {
        return specific_size_qty;
    }

    /**
     * @param specific_size_qty the specific_size_qty to set
     */
    public void setSpecific_size_qty(double specific_size_qty) {
        this.specific_size_qty = specific_size_qty;
    }

    /**
     * @return the input_qty
     */
    public Double getInput_qty() {
        return input_qty;
    }

    /**
     * @param input_qty the input_qty to set
     */
    public void setInput_qty(Double input_qty) {
        this.input_qty = input_qty;
    }
    
    
}
