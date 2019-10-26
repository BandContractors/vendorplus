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
public class Trans implements Serializable {

    private static final long serialVersionUID = 1L;

    private long TransactionId;
    private long TransactionId2;
    private long TransactionId3;
    private long TransactionHistId;
    private Date TransactionDate;
    private int StoreId;
    private String StoreName;
    private int Store2Id;
    private String Store2Name;
    private long TransactorId;
    private String TransactorName;
    private int TransactionTypeId;
    private String TransactionTypeName;
    private int TransactionReasonId;
    private String TransactionReasonName;
    private double SubTotal;
    private double TotalTradeDiscount;
    private double TotalVat;
    private double GrandTotal;
    private String TransactionComment;
    private int AddUserDetailId;
    private String AddUserDetailName;
    private Date AddDate;
    private int EditUserDetailId;
    private String EditUserDetailName;
    private Date EditDate;
    private String TransactionRef;
    private double AmountTendered;
    private double ChangeAmount;
    private int PayMethod;
    private double CashDiscount;
    private double PointsAwarded;
    private double BalancePoints;
    private double SpendPoints;
    private double BalancePointsAmount;
    private double SpendPointsAmount;
    private String CardNumber;
    private String CardHolder;
    private long PointsCardId;

    private String ApproveUserName;
    private String ApproveUserPassword;

    private double TotalStdVatableAmount;
    private double TotalZeroVatableAmount;
    private double TotalExemptVatableAmount;
    private double VatPerc;
    private String IsCashDiscountVatLiable;
    //for report
    private Date TransactionDate2;
    private Date AddDate2;
    private Date EditDate2;

    private boolean OtherDates;
    //for profit margin
    private double TotalProfitMargin;
    
    private int TransactionUserDetailId;
    private String TransactionUserDetailName;
    private long BillTransactorId;
    private String BillTransactorName;
    private boolean BillOther;
    private boolean DisplayLoyalty;
    
    private long SchemeTransactorId;
    private String SchemeTransactorName;
    private String PrincSchemeMember;
    private String SchemeCardNumber;
    
    private String TransactionNumber;
    private String TransactionNumber2;
    private String TransactionNumber3;
    private Date DeliveryDate;
    private String DeliveryAddress;
    private String PayTerms;
    private String TermsConditions;
    private int AuthorisedByUserDetailId;
    private Date AuthoriseDate;
    private Date PayDueDate;
    private Date ExpiryDate;
    private int AccChildAccountId;
    private String CurrencyCode;
    private double Xrate;
    private double TotalDebit;
    private double TotalCredit;
    private double TotalPaid;
    private Date from_date;
    private Date to_date;
    private String duration_type;
    private long site_id;
    private String transactor_rep;
    private String transactor_vehicle;
    private String transactor_driver;
    private double duration_value;
    private double cash_dicsount_perc;
    private double total_weight;
    private double balance_receivable;
    private double balance_payable;
    private double deposit_customer;
    private double deposit_supplier;
    private double balance_receivable2;
    private double balance_payable2;
    private double deposit_customer2;
    private double deposit_supplier2;
    private long location_id;
    private String status_code;
    private Date status_date;
    private String delivery_mode;
    private int is_processed;
    private int is_paid;
    private int is_cancel;
    private String user_code;
    private String location_name;
    private int is_selected;
    private int is_invoiced;
    private String TransItemsString;
    private Category category;
    private int is_delivered;
    private String source_code;
    

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
     * @return the TransactorName
     */
    public String getTransactorName() {
        return TransactorName;
    }

    /**
     * @param TransactorName the TransactorName to set
     */
    public void setTransactorName(String TransactorName) {
        this.TransactorName = TransactorName;
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
     * @return the SubTotal
     */
    public double getSubTotal() {
        return SubTotal;
    }

    /**
     * @param SubTotal the SubTotal to set
     */
    public void setSubTotal(double SubTotal) {
        this.SubTotal = SubTotal;
    }

    /**
     * @return the TotalTradeDiscount
     */
    public double getTotalTradeDiscount() {
        return TotalTradeDiscount;
    }

    /**
     * @param TotalTradeDiscount the TotalTradeDiscount to set
     */
    public void setTotalTradeDiscount(double TotalTradeDiscount) {
        this.TotalTradeDiscount = TotalTradeDiscount;
    }

    /**
     * @return the TotalVat
     */
    public double getTotalVat() {
        return TotalVat;
    }

    /**
     * @param TotalVat the TotalVat to set
     */
    public void setTotalVat(double TotalVat) {
        this.TotalVat = TotalVat;
    }

    /**
     * @return the GrandTotal
     */
    public double getGrandTotal() {
        return GrandTotal;
    }

    /**
     * @param GrandTotal the GrandTotal to set
     */
    public void setGrandTotal(double GrandTotal) {
        this.GrandTotal = GrandTotal;
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
     * @return the AmountTendered
     */
    public double getAmountTendered() {
        return AmountTendered;
    }

    /**
     * @param AmountTendered the AmountTendered to set
     */
    public void setAmountTendered(double AmountTendered) {
        this.AmountTendered = AmountTendered;
    }

    /**
     * @return the ChangeAmount
     */
    public double getChangeAmount() {
        return ChangeAmount;
    }

    /**
     * @param ChangeAmount the ChangeAmount to set
     */
    public void setChangeAmount(double ChangeAmount) {
        this.ChangeAmount = ChangeAmount;
    }

    /**
     * @return the PayMethod
     */
    public int getPayMethod() {
        return PayMethod;
    }

    /**
     * @param PayMethod the PayMethod to set
     */
    public void setPayMethod(int PayMethod) {
        this.PayMethod = PayMethod;
    }

    /**
     * @return the CashDiscount
     */
    public double getCashDiscount() {
        return CashDiscount;
    }

    /**
     * @param CashDiscount the CashDiscount to set
     */
    public void setCashDiscount(double CashDiscount) {
        this.CashDiscount = CashDiscount;
    }

    /**
     * @return the PointsAwarded
     */
    public double getPointsAwarded() {
        return PointsAwarded;
    }

    /**
     * @param PointsAwarded the PointsAwarded to set
     */
    public void setPointsAwarded(double PointsAwarded) {
        this.PointsAwarded = PointsAwarded;
    }

    /**
     * @return the BalancePoints
     */
    public double getBalancePoints() {
        return BalancePoints;
    }

    /**
     * @param BalancePoints the BalancePoints to set
     */
    public void setBalancePoints(double BalancePoints) {
        this.BalancePoints = BalancePoints;
    }

    /**
     * @return the SpendPoints
     */
    public double getSpendPoints() {
        return SpendPoints;
    }

    /**
     * @param SpendPoints the SpendPoints to set
     */
    public void setSpendPoints(double SpendPoints) {
        this.SpendPoints = SpendPoints;
    }

    /**
     * @return the BalancePointsAmount
     */
    public double getBalancePointsAmount() {
        return BalancePointsAmount;
    }

    /**
     * @param BalancePointsAmount the BalancePointsAmount to set
     */
    public void setBalancePointsAmount(double BalancePointsAmount) {
        this.BalancePointsAmount = BalancePointsAmount;
    }

    /**
     * @return the SpendPointsAmount
     */
    public double getSpendPointsAmount() {
        return SpendPointsAmount;
    }

    /**
     * @param SpendPointsAmount the SpendPointsAmount to set
     */
    public void setSpendPointsAmount(double SpendPointsAmount) {
        this.SpendPointsAmount = SpendPointsAmount;
    }

    /**
     * @return the CardNumber
     */
    public String getCardNumber() {
        return CardNumber;
    }

    /**
     * @param CardNumber the CardNumber to set
     */
    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    /**
     * @return the CardHolder
     */
    public String getCardHolder() {
        return CardHolder;
    }

    /**
     * @param CardHolder the CardHolder to set
     */
    public void setCardHolder(String CardHolder) {
        this.CardHolder = CardHolder;
    }

    /**
     * @return the PointsCardId
     */
    public long getPointsCardId() {
        return PointsCardId;
    }

    /**
     * @param PointsCardId the PointsCardId to set
     */
    public void setPointsCardId(long PointsCardId) {
        this.PointsCardId = PointsCardId;
    }

    /**
     * @return the ApproveUserName
     */
    public String getApproveUserName() {
        return ApproveUserName;
    }

    /**
     * @param ApproveUserName the ApproveUserName to set
     */
    public void setApproveUserName(String ApproveUserName) {
        this.ApproveUserName = ApproveUserName;
    }

    /**
     * @return the ApproveUserPassword
     */
    public String getApproveUserPassword() {
        return ApproveUserPassword;
    }

    /**
     * @param ApproveUserPassword the ApproveUserPassword to set
     */
    public void setApproveUserPassword(String ApproveUserPassword) {
        this.ApproveUserPassword = ApproveUserPassword;
    }

    /**
     * @return the TotalStdVatableAmount
     */
    public double getTotalStdVatableAmount() {
        return TotalStdVatableAmount;
    }

    /**
     * @param TotalStdVatableAmount the TotalStdVatableAmount to set
     */
    public void setTotalStdVatableAmount(double TotalStdVatableAmount) {
        this.TotalStdVatableAmount = TotalStdVatableAmount;
    }

    /**
     * @return the TotalZeroVatableAmount
     */
    public double getTotalZeroVatableAmount() {
        return TotalZeroVatableAmount;
    }

    /**
     * @param TotalZeroVatableAmount the TotalZeroVatableAmount to set
     */
    public void setTotalZeroVatableAmount(double TotalZeroVatableAmount) {
        this.TotalZeroVatableAmount = TotalZeroVatableAmount;
    }

    /**
     * @return the TotalExemptVatableAmount
     */
    public double getTotalExemptVatableAmount() {
        return TotalExemptVatableAmount;
    }

    /**
     * @param TotalExemptVatableAmount the TotalExemptVatableAmount to set
     */
    public void setTotalExemptVatableAmount(double TotalExemptVatableAmount) {
        this.TotalExemptVatableAmount = TotalExemptVatableAmount;
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
     * @return the IsCashDiscountVatLiable
     */
    public String getIsCashDiscountVatLiable() {
        return IsCashDiscountVatLiable;
    }

    /**
     * @param IsCashDiscountVatLiable the IsCashDiscountVatLiable to set
     */
    public void setIsCashDiscountVatLiable(String IsCashDiscountVatLiable) {
        this.IsCashDiscountVatLiable = IsCashDiscountVatLiable;
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
     * @return the TransactionId2
     */
    public long getTransactionId2() {
        return TransactionId2;
    }

    /**
     * @param TransactionId2 the TransactionId2 to set
     */
    public void setTransactionId2(long TransactionId2) {
        this.TransactionId2 = TransactionId2;
    }

    /**
     * @return the TransactionId3
     */
    public long getTransactionId3() {
        return TransactionId3;
    }

    /**
     * @param TransactionId3 the TransactionId3 to set
     */
    public void setTransactionId3(long TransactionId3) {
        this.TransactionId3 = TransactionId3;
    }

    /**
     * @return the TotalProfitMargin
     */
    public double getTotalProfitMargin() {
        return TotalProfitMargin;
    }

    /**
     * @param TotalProfitMargin the TotalProfitMargin to set
     */
    public void setTotalProfitMargin(double TotalProfitMargin) {
        this.TotalProfitMargin = TotalProfitMargin;
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
     * @return the BillOther
     */
    public boolean isBillOther() {
        return BillOther;
    }

    /**
     * @param BillOther the BillOther to set
     */
    public void setBillOther(boolean BillOther) {
        this.BillOther = BillOther;
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
     * @return the SchemeTransactorId
     */
    public long getSchemeTransactorId() {
        return SchemeTransactorId;
    }

    /**
     * @param SchemeTransactorId the SchemeTransactorId to set
     */
    public void setSchemeTransactorId(long SchemeTransactorId) {
        this.SchemeTransactorId = SchemeTransactorId;
    }

    /**
     * @return the SchemeTransactorName
     */
    public String getSchemeTransactorName() {
        return SchemeTransactorName;
    }

    /**
     * @param SchemeTransactorName the SchemeTransactorName to set
     */
    public void setSchemeTransactorName(String SchemeTransactorName) {
        this.SchemeTransactorName = SchemeTransactorName;
    }

    /**
     * @return the PrincSchemeMember
     */
    public String getPrincSchemeMember() {
        return PrincSchemeMember;
    }

    /**
     * @param PrincSchemeMember the PrincSchemeMember to set
     */
    public void setPrincSchemeMember(String PrincSchemeMember) {
        this.PrincSchemeMember = PrincSchemeMember;
    }

    /**
     * @return the SchemeCardNumber
     */
    public String getSchemeCardNumber() {
        return SchemeCardNumber;
    }

    /**
     * @param SchemeCardNumber the SchemeCardNumber to set
     */
    public void setSchemeCardNumber(String SchemeCardNumber) {
        this.SchemeCardNumber = SchemeCardNumber;
    }

    /**
     * @return the TransactionNumber
     */
    public String getTransactionNumber() {
        return TransactionNumber;
    }

    /**
     * @param TransactionNumber the TransactionNumber to set
     */
    public void setTransactionNumber(String TransactionNumber) {
        this.TransactionNumber = TransactionNumber;
    }

    /**
     * @return the DeliveryDate
     */
    public Date getDeliveryDate() {
        return DeliveryDate;
    }

    /**
     * @param DeliveryDate the DeliveryDate to set
     */
    public void setDeliveryDate(Date DeliveryDate) {
        this.DeliveryDate = DeliveryDate;
    }

    /**
     * @return the DeliveryAddress
     */
    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    /**
     * @param DeliveryAddress the DeliveryAddress to set
     */
    public void setDeliveryAddress(String DeliveryAddress) {
        this.DeliveryAddress = DeliveryAddress;
    }

    /**
     * @return the PayTerms
     */
    public String getPayTerms() {
        return PayTerms;
    }

    /**
     * @param PayTerms the PayTerms to set
     */
    public void setPayTerms(String PayTerms) {
        this.PayTerms = PayTerms;
    }

    /**
     * @return the TermsConditions
     */
    public String getTermsConditions() {
        return TermsConditions;
    }

    /**
     * @param TermsConditions the TermsConditions to set
     */
    public void setTermsConditions(String TermsConditions) {
        this.TermsConditions = TermsConditions;
    }

    /**
     * @return the AuthorisedByUserDetailId
     */
    public int getAuthorisedByUserDetailId() {
        return AuthorisedByUserDetailId;
    }

    /**
     * @param AuthorisedByUserDetailId the AuthorisedByUserDetailId to set
     */
    public void setAuthorisedByUserDetailId(int AuthorisedByUserDetailId) {
        this.AuthorisedByUserDetailId = AuthorisedByUserDetailId;
    }

    /**
     * @return the AuthoriseDate
     */
    public Date getAuthoriseDate() {
        return AuthoriseDate;
    }

    /**
     * @param AuthoriseDate the AuthoriseDate to set
     */
    public void setAuthoriseDate(Date AuthoriseDate) {
        this.AuthoriseDate = AuthoriseDate;
    }

    /**
     * @return the PayDueDate
     */
    public Date getPayDueDate() {
        return PayDueDate;
    }

    /**
     * @param PayDueDate the PayDueDate to set
     */
    public void setPayDueDate(Date PayDueDate) {
        this.PayDueDate = PayDueDate;
    }

    /**
     * @return the ExpiryDate
     */
    public Date getExpiryDate() {
        return ExpiryDate;
    }

    /**
     * @param ExpiryDate the ExpiryDate to set
     */
    public void setExpiryDate(Date ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

    /**
     * @return the TransactionNumber2
     */
    public String getTransactionNumber2() {
        return TransactionNumber2;
    }

    /**
     * @param TransactionNumber2 the TransactionNumber2 to set
     */
    public void setTransactionNumber2(String TransactionNumber2) {
        this.TransactionNumber2 = TransactionNumber2;
    }

    /**
     * @return the TransactionNumber3
     */
    public String getTransactionNumber3() {
        return TransactionNumber3;
    }

    /**
     * @param TransactionNumber3 the TransactionNumber3 to set
     */
    public void setTransactionNumber3(String TransactionNumber3) {
        this.TransactionNumber3 = TransactionNumber3;
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
     * @return the Xrate
     */
    public double getXrate() {
        return Xrate;
    }

    /**
     * @param Xrate the Xrate to set
     */
    public void setXrate(double Xrate) {
        this.Xrate = Xrate;
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
     * @return the DisplayLoyalty
     */
    public boolean isDisplayLoyalty() {
        return DisplayLoyalty;
    }

    /**
     * @param DisplayLoyalty the DisplayLoyalty to set
     */
    public void setDisplayLoyalty(boolean DisplayLoyalty) {
        this.DisplayLoyalty = DisplayLoyalty;
    }

    /**
     * @return the TotalDebit
     */
    public double getTotalDebit() {
        return TotalDebit;
    }

    /**
     * @param TotalDebit the TotalDebit to set
     */
    public void setTotalDebit(double TotalDebit) {
        this.TotalDebit = TotalDebit;
    }

    /**
     * @return the TotalCredit
     */
    public double getTotalCredit() {
        return TotalCredit;
    }

    /**
     * @param TotalCredit the TotalCredit to set
     */
    public void setTotalCredit(double TotalCredit) {
        this.TotalCredit = TotalCredit;
    }

    /**
     * @return the TransactionHistId
     */
    public long getTransactionHistId() {
        return TransactionHistId;
    }

    /**
     * @param TransactionHistId the TransactionHistId to set
     */
    public void setTransactionHistId(long TransactionHistId) {
        this.TransactionHistId = TransactionHistId;
    }

    /**
     * @return the TotalPaid
     */
    public double getTotalPaid() {
        return TotalPaid;
    }

    /**
     * @param TotalPaid the TotalPaid to set
     */
    public void setTotalPaid(double TotalPaid) {
        this.TotalPaid = TotalPaid;
    }

    /**
     * @return the from_date
     */
    public Date getFrom_date() {
        return from_date;
    }

    /**
     * @param from_date the from_date to set
     */
    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    /**
     * @return the to_date
     */
    public Date getTo_date() {
        return to_date;
    }

    /**
     * @param to_date the to_date to set
     */
    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    /**
     * @return the duration_type
     */
    public String getDuration_type() {
        return duration_type;
    }

    /**
     * @param duration_type the duration_type to set
     */
    public void setDuration_type(String duration_type) {
        this.duration_type = duration_type;
    }

    /**
     * @return the site_id
     */
    public long getSite_id() {
        return site_id;
    }

    /**
     * @param site_id the site_id to set
     */
    public void setSite_id(long site_id) {
        this.site_id = site_id;
    }

    /**
     * @return the transactor_rep
     */
    public String getTransactor_rep() {
        return transactor_rep;
    }

    /**
     * @param transactor_rep the transactor_rep to set
     */
    public void setTransactor_rep(String transactor_rep) {
        this.transactor_rep = transactor_rep;
    }

    /**
     * @return the transactor_vehicle
     */
    public String getTransactor_vehicle() {
        return transactor_vehicle;
    }

    /**
     * @param transactor_vehicle the transactor_vehicle to set
     */
    public void setTransactor_vehicle(String transactor_vehicle) {
        this.transactor_vehicle = transactor_vehicle;
    }

    /**
     * @return the transactor_driver
     */
    public String getTransactor_driver() {
        return transactor_driver;
    }

    /**
     * @param transactor_driver the transactor_driver to set
     */
    public void setTransactor_driver(String transactor_driver) {
        this.transactor_driver = transactor_driver;
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
     * @return the cash_dicsount_perc
     */
    public double getCash_dicsount_perc() {
        return cash_dicsount_perc;
    }

    /**
     * @param cash_dicsount_perc the cash_dicsount_perc to set
     */
    public void setCash_dicsount_perc(double cash_dicsount_perc) {
        this.cash_dicsount_perc = cash_dicsount_perc;
    }

    /**
     * @return the total_weight
     */
    public double getTotal_weight() {
        return total_weight;
    }

    /**
     * @param total_weight the total_weight to set
     */
    public void setTotal_weight(double total_weight) {
        this.total_weight = total_weight;
    }

    /**
     * @return the balance_receivable
     */
    public double getBalance_receivable() {
        return balance_receivable;
    }

    /**
     * @param balance_receivable the balance_receivable to set
     */
    public void setBalance_receivable(double balance_receivable) {
        this.balance_receivable = balance_receivable;
    }

    /**
     * @return the balance_payable
     */
    public double getBalance_payable() {
        return balance_payable;
    }

    /**
     * @param balance_payable the balance_payable to set
     */
    public void setBalance_payable(double balance_payable) {
        this.balance_payable = balance_payable;
    }

    /**
     * @return the deposit_customer
     */
    public double getDeposit_customer() {
        return deposit_customer;
    }

    /**
     * @param deposit_customer the deposit_customer to set
     */
    public void setDeposit_customer(double deposit_customer) {
        this.deposit_customer = deposit_customer;
    }

    /**
     * @return the deposit_supplier
     */
    public double getDeposit_supplier() {
        return deposit_supplier;
    }

    /**
     * @param deposit_supplier the deposit_supplier to set
     */
    public void setDeposit_supplier(double deposit_supplier) {
        this.deposit_supplier = deposit_supplier;
    }

    /**
     * @return the balance_receivable2
     */
    public double getBalance_receivable2() {
        return balance_receivable2;
    }

    /**
     * @param balance_receivable2 the balance_receivable2 to set
     */
    public void setBalance_receivable2(double balance_receivable2) {
        this.balance_receivable2 = balance_receivable2;
    }

    /**
     * @return the balance_payable2
     */
    public double getBalance_payable2() {
        return balance_payable2;
    }

    /**
     * @param balance_payable2 the balance_payable2 to set
     */
    public void setBalance_payable2(double balance_payable2) {
        this.balance_payable2 = balance_payable2;
    }

    /**
     * @return the deposit_customer2
     */
    public double getDeposit_customer2() {
        return deposit_customer2;
    }

    /**
     * @param deposit_customer2 the deposit_customer2 to set
     */
    public void setDeposit_customer2(double deposit_customer2) {
        this.deposit_customer2 = deposit_customer2;
    }

    /**
     * @return the deposit_supplier2
     */
    public double getDeposit_supplier2() {
        return deposit_supplier2;
    }

    /**
     * @param deposit_supplier2 the deposit_supplier2 to set
     */
    public void setDeposit_supplier2(double deposit_supplier2) {
        this.deposit_supplier2 = deposit_supplier2;
    }

    /**
     * @return the location_id
     */
    public long getLocation_id() {
        return location_id;
    }

    /**
     * @param location_id the location_id to set
     */
    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }

    /**
     * @return the status_code
     */
    public String getStatus_code() {
        return status_code;
    }

    /**
     * @param status_code the status_code to set
     */
    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    /**
     * @return the status_date
     */
    public Date getStatus_date() {
        return status_date;
    }

    /**
     * @param status_date the status_date to set
     */
    public void setStatus_date(Date status_date) {
        this.status_date = status_date;
    }

    /**
     * @return the delivery_mode
     */
    public String getDelivery_mode() {
        return delivery_mode;
    }

    /**
     * @param delivery_mode the delivery_mode to set
     */
    public void setDelivery_mode(String delivery_mode) {
        this.delivery_mode = delivery_mode;
    }

    /**
     * @return the is_processed
     */
    public int getIs_processed() {
        return is_processed;
    }

    /**
     * @param is_processed the is_processed to set
     */
    public void setIs_processed(int is_processed) {
        this.is_processed = is_processed;
    }

    /**
     * @return the is_paid
     */
    public int getIs_paid() {
        return is_paid;
    }

    /**
     * @param is_paid the is_paid to set
     */
    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    /**
     * @return the is_cancel
     */
    public int getIs_cancel() {
        return is_cancel;
    }

    /**
     * @param is_cancel the is_cancel to set
     */
    public void setIs_cancel(int is_cancel) {
        this.is_cancel = is_cancel;
    }

    /**
     * @return the user_code
     */
    public String getUser_code() {
        return user_code;
    }

    /**
     * @param user_code the user_code to set
     */
    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    /**
     * @return the location_name
     */
    public String getLocation_name() {
        return location_name;
    }

    /**
     * @param location_name the location_name to set
     */
    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    /**
     * @return the is_selected
     */
    public int getIs_selected() {
        return is_selected;
    }

    /**
     * @param is_selected the is_selected to set
     */
    public void setIs_selected(int is_selected) {
        this.is_selected = is_selected;
    }

    /**
     * @return the is_invoiced
     */
    public int getIs_invoiced() {
        return is_invoiced;
    }

    /**
     * @param is_invoiced the is_invoiced to set
     */
    public void setIs_invoiced(int is_invoiced) {
        this.is_invoiced = is_invoiced;
    }

    /**
     * @return the TransItemsString
     */
    public String getTransItemsString() {
        return TransItemsString;
    }

    /**
     * @param TransItemsString the TransItemsString to set
     */
    public void setTransItemsString(String TransItemsString) {
        this.TransItemsString = TransItemsString;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the is_delivered
     */
    public int getIs_delivered() {
        return is_delivered;
    }

    /**
     * @param is_delivered the is_delivered to set
     */
    public void setIs_delivered(int is_delivered) {
        this.is_delivered = is_delivered;
    }

    /**
     * @return the source_code
     */
    public String getSource_code() {
        return source_code;
    }

    /**
     * @param source_code the source_code to set
     */
    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

}
