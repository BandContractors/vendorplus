package beans;

import entities.AccChildAccount;
import entities.Loyalty_transaction;
import entities.TransItem;
import entities.Pay;
import entities.PayMethod;
import entities.Transactor;
import entities.UserDetail;
import entities.Store;
import entities.Trans;
import entities.TransProduction;
import entities.TransProductionItem;
import entities.TransactionPackage;
import entities.TransactionPackageItem;
import entities.TransactionReason;
import entities.TransactionType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "outputDetail")
@SessionScoped
public class OutputDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Trans trans;
    private Pay pay;
    private UserDetail add_user_detail;
    private UserDetail trans_user_detail;
    private Transactor transactor;
    private Transactor bill_transactor;
    private UserDetail edit_user_detail;
    private List<TransItem> trans_items = new ArrayList<>();
    private int total_items;
    private List<Integer> total_items_list;
    private Store store;
    private Store store2;
    private TransactionType transaction_type;
    private TransactionReason transaction_reason;
    private Transactor scheme_transactor;
    private AccChildAccount acc_child_account;
    private AccChildAccount acc_child_account2;
    private UserDetail authorised_by_user_detail;
    private PayMethod pay_method;
    private String pay_reason;
    private String pay_reason2;
    private TransProduction transProduction;
    private List<TransProductionItem> trans_prod_items = new ArrayList<>();
    private String TransAmountInWords;
    private String PayAmountInWords;
    private UserDetail approve_user_detail;
    private TransactionPackage transactionPackage;
    private List<TransactionPackageItem> transactionPackageItems = new ArrayList<>();

    public OutputDetail() {
    }

    /**
     * @return the trans
     */
    public Trans getTrans() {
        return trans;
    }

    /**
     * @param trans the trans to set
     */
    public void setTrans(Trans trans) {
        this.trans = trans;
    }

    /**
     * @return the pay
     */
    public Pay getPay() {
        return pay;
    }

    /**
     * @param pay the pay to set
     */
    public void setPay(Pay pay) {
        this.pay = pay;
    }

    /**
     * @return the add_user_detail
     */
    public UserDetail getAdd_user_detail() {
        return add_user_detail;
    }

    /**
     * @param add_user_detail the add_user_detail to set
     */
    public void setAdd_user_detail(UserDetail add_user_detail) {
        this.add_user_detail = add_user_detail;
    }

    /**
     * @return the trans_user_detail
     */
    public UserDetail getTrans_user_detail() {
        return trans_user_detail;
    }

    /**
     * @param trans_user_detail the trans_user_detail to set
     */
    public void setTrans_user_detail(UserDetail trans_user_detail) {
        this.trans_user_detail = trans_user_detail;
    }

    /**
     * @return the transactor
     */
    public Transactor getTransactor() {
        return transactor;
    }

    /**
     * @param transactor the transactor to set
     */
    public void setTransactor(Transactor transactor) {
        this.transactor = transactor;
    }

    /**
     * @return the bill_transactor
     */
    public Transactor getBill_transactor() {
        return bill_transactor;
    }

    /**
     * @param bill_transactor the bill_transactor to set
     */
    public void setBill_transactor(Transactor bill_transactor) {
        this.bill_transactor = bill_transactor;
    }

    /**
     * @return the edit_user_detail
     */
    public UserDetail getEdit_user_detail() {
        return edit_user_detail;
    }

    /**
     * @param edit_user_detail the edit_user_detail to set
     */
    public void setEdit_user_detail(UserDetail edit_user_detail) {
        this.edit_user_detail = edit_user_detail;
    }

    /**
     * @return the trans_items
     */
    public List<TransItem> getTrans_items() {
        return trans_items;
    }

    /**
     * @param trans_items the trans_items to set
     */
    public void setTrans_items(List<TransItem> trans_items) {
        this.trans_items = trans_items;
    }

    /**
     * @return the total_items
     */
    public int getTotal_items() {
        return total_items;
    }

    /**
     * @param total_items the total_items to set
     */
    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    /**
     * @return the total_items_list
     */
    public List<Integer> getTotal_items_list() {
        return total_items_list;
    }

    /**
     * @param total_items_list the total_items_list to set
     */
    public void setTotal_items_list(List<Integer> total_items_list) {
        this.total_items_list = total_items_list;
    }

    /**
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * @return the store2
     */
    public Store getStore2() {
        return store2;
    }

    /**
     * @param store2 the store2 to set
     */
    public void setStore2(Store store2) {
        this.store2 = store2;
    }

    /**
     * @return the transaction_type
     */
    public TransactionType getTransaction_type() {
        return transaction_type;
    }

    /**
     * @param transaction_type the transaction_type to set
     */
    public void setTransaction_type(TransactionType transaction_type) {
        this.transaction_type = transaction_type;
    }

    /**
     * @return the transaction_reason
     */
    public TransactionReason getTransaction_reason() {
        return transaction_reason;
    }

    /**
     * @param transaction_reason the transaction_reason to set
     */
    public void setTransaction_reason(TransactionReason transaction_reason) {
        this.transaction_reason = transaction_reason;
    }

    /**
     * @return the scheme_transactor
     */
    public Transactor getScheme_transactor() {
        return scheme_transactor;
    }

    /**
     * @param scheme_transactor the scheme_transactor to set
     */
    public void setScheme_transactor(Transactor scheme_transactor) {
        this.scheme_transactor = scheme_transactor;
    }

    /**
     * @return the acc_child_account
     */
    public AccChildAccount getAcc_child_account() {
        return acc_child_account;
    }

    /**
     * @param acc_child_account the acc_child_account to set
     */
    public void setAcc_child_account(AccChildAccount acc_child_account) {
        this.acc_child_account = acc_child_account;
    }

    /**
     * @return the acc_child_account2
     */
    public AccChildAccount getAcc_child_account2() {
        return acc_child_account2;
    }

    /**
     * @param acc_child_account2 the acc_child_account2 to set
     */
    public void setAcc_child_account2(AccChildAccount acc_child_account2) {
        this.acc_child_account2 = acc_child_account2;
    }

    /**
     * @return the authorised_by_user_detail
     */
    public UserDetail getAuthorised_by_user_detail() {
        return authorised_by_user_detail;
    }

    /**
     * @param authorised_by_user_detail the authorised_by_user_detail to set
     */
    public void setAuthorised_by_user_detail(UserDetail authorised_by_user_detail) {
        this.authorised_by_user_detail = authorised_by_user_detail;
    }

    /**
     * @return the pay_method
     */
    public PayMethod getPay_method() {
        return pay_method;
    }

    /**
     * @param pay_method the pay_method to set
     */
    public void setPay_method(PayMethod pay_method) {
        this.pay_method = pay_method;
    }

    /**
     * @return the pay_reason
     */
    public String getPay_reason() {
        return pay_reason;
    }

    /**
     * @param pay_reason the pay_reason to set
     */
    public void setPay_reason(String pay_reason) {
        this.pay_reason = pay_reason;
    }

    /**
     * @return the pay_reason2
     */
    public String getPay_reason2() {
        return pay_reason2;
    }

    /**
     * @param pay_reason2 the pay_reason2 to set
     */
    public void setPay_reason2(String pay_reason2) {
        this.pay_reason2 = pay_reason2;
    }

    /**
     * @return the transProduction
     */
    public TransProduction getTransProduction() {
        return transProduction;
    }

    /**
     * @param transProduction the transProduction to set
     */
    public void setTransProduction(TransProduction transProduction) {
        this.transProduction = transProduction;
    }

    /**
     * @return the trans_prod_items
     */
    public List<TransProductionItem> getTrans_prod_items() {
        return trans_prod_items;
    }

    /**
     * @param trans_prod_items the trans_prod_items to set
     */
    public void setTrans_prod_items(List<TransProductionItem> trans_prod_items) {
        this.trans_prod_items = trans_prod_items;
    }

    /**
     * @return the TransAmountInWords
     */
    public String getTransAmountInWords() {
        return TransAmountInWords;
    }

    /**
     * @param TransAmountInWords the TransAmountInWords to set
     */
    public void setTransAmountInWords(String TransAmountInWords) {
        this.TransAmountInWords = TransAmountInWords;
    }

    /**
     * @return the PayAmountInWords
     */
    public String getPayAmountInWords() {
        return PayAmountInWords;
    }

    /**
     * @param PayAmountInWords the PayAmountInWords to set
     */
    public void setPayAmountInWords(String PayAmountInWords) {
        this.PayAmountInWords = PayAmountInWords;
    }

    /**
     * @return the approve_user_detail
     */
    public UserDetail getApprove_user_detail() {
        return approve_user_detail;
    }

    /**
     * @param approve_user_detail the approve_user_detail to set
     */
    public void setApprove_user_detail(UserDetail approve_user_detail) {
        this.approve_user_detail = approve_user_detail;
    }

    /**
     * @return the transactionPackageItems
     */
    public List<TransactionPackageItem> getTransactionPackageItems() {
        return transactionPackageItems;
    }

    /**
     * @param transactionPackageItems the TransactionPackageItems to set
     */
    public void setTransactionPackageItems(List<TransactionPackageItem> transactionPackageItems) {
        this.transactionPackageItems = transactionPackageItems;
    }

    /**
     * @return the transactionPackage
     */
    public TransactionPackage getTransactionPackage() {
        return transactionPackage;
    }

    /**
     * @param transactionPackage the transactionPackage to set
     */
    public void setTransactionPackage(TransactionPackage transactionPackage) {
        this.transactionPackage = transactionPackage;
    }
}
