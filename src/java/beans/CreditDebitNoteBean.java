package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.GroupRight;
import entities.Pay;
import entities.Stock;
import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.TransactionReason;
import entities.TransactionType;
import entities.Transaction_item_cr_dr_note_unit;
import entities.Transaction_item_unit;
import entities.Transactor;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sessions.GeneralUserSetting;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;

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
public class CreditDebitNoteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(CreditDebitNoteBean.class.getName());
    private String DateType;
    private Date Date1;
    private Date Date2;
    private String FieldName;
    private List<Trans> TransList;
    private List<Trans> TransListSummary;
    private String ActionMessage = null;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private Trans TransObj;
    private List<TransItem> TransItemList;
    private Pay PayObj;
    private String ActionType;

    public Trans getTrans_cr_dr_note(long aTransactionId) {
        String sql = "{call sp_search_transaction_by_id_cr_dr_note(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new TransBean().getTransFromResultset(rs);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Trans> getTrans_cr_dr_notes(String aTransNumberRef, int aIs_cancel) {
        //aIs_cancel: 0 No 1 Yes 2 All
        List<Trans> aList = new ArrayList<>();
        String sql = "";
        if (aIs_cancel == 0) {
            sql = "SELECT * FROM transaction_cr_dr_note WHERE transaction_ref=? AND is_cancel=0";
        } else if (aIs_cancel == 1) {
            sql = "SELECT * FROM transaction_cr_dr_note WHERE transaction_ref=? AND is_cancel=1";
        } else {
            sql = "SELECT * FROM transaction_cr_dr_note WHERE transaction_ref=?";
        }
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransNumberRef);
            rs = ps.executeQuery();
            while (rs.next()) {
                aList.add(new TransBean().getTransFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return aList;
    }

    public void refreshTrans_cr_dr_notes(String aTransNumberRef, List<Trans> aList) {
        String sql = "SELECT * FROM transaction_cr_dr_note WHERE transaction_ref=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransNumberRef);
            rs = ps.executeQuery();
            aList.clear();
            while (rs.next()) {
                aList.add(new TransBean().getTransFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public TransItem getTransItemFromList(String aItemCode, List<TransItem> aTransItems) {
        TransItem ti = new TransItem();
        for (int i = 0; i < aTransItems.size(); i++) {
            if (aItemCode.equals(Long.toString(aTransItems.get(i).getItemId())) && aTransItems.get(i).getItem_no() == 0) {
                ti.setItemQty(aTransItems.get(i).getItemQty());
                ti.setAmountIncVat(aTransItems.get(i).getAmountIncVat());
                ti.setUnitVat(aTransItems.get(i).getUnitVat());
                ti.setVatPerc(aTransItems.get(i).getVatPerc());
                aTransItems.get(i).setItem_no(1);
                break;
            }
        }
        return ti;
    }

    public long saveCreditDebitNote(Trans aOldTrans, Trans aNewTrans, List<TransItem> aOldTransItems, List<TransItem> aNewTransItems, int aMode_code) {
        long SavedNoteId = 0;
        String CreditOrDebitNote = "";
        int TransTypeId = 0;
        int TransReasId = 0;
        List<TransItem> CreditDebitTransItems = new ArrayList<>();
        Trans CreditDebitTrans = new Trans();
        try {
            if (aNewTrans.getGrandTotal() > aOldTrans.getGrandTotal() || aNewTrans.getTotalVat() > aOldTrans.getTotalVat()) {//Debit note
                //127, 'DEBIT NOTE', 83
                CreditOrDebitNote = "Debit Note";
                TransTypeId = 83;
                TransReasId = 127;
            } else if (aNewTrans.getGrandTotal() < aOldTrans.getGrandTotal() || aNewTrans.getTotalVat() < aOldTrans.getTotalVat()) {//Credit note
                //126, 'CREDIT NOTE', 82
                CreditOrDebitNote = "Credit Note";
                TransTypeId = 82;
                TransReasId = 126;
            }
            TransItemBean tib = new TransItemBean();
            TransBean tb = new TransBean();
            //get credit/debit transItems
            for (int i = 0; i < aNewTransItems.size(); i++) {
                //check if item has changed
                TransItem NewTransItem = aNewTransItems.get(i);
                TransItem OldTransItem = tib.itemExistsObj(aOldTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific(), NewTransItem.getUnit_id());
                Double ChangedQty = NewTransItem.getItemQty() - OldTransItem.getItemQty();//can be + or -
                Double ChangedQtyBase = NewTransItem.getBase_unit_qty() - OldTransItem.getBase_unit_qty();
                if (ChangedQty != 0) {
                    TransItem CreditDebitTransItem = new TransItem();
                    tib.copyObjectTransItem(NewTransItem, CreditDebitTransItem);//From,To
                    CreditDebitTransItem.setTransactionItemId(0);
                    CreditDebitTransItem.setItemQty(ChangedQty);
                    CreditDebitTransItem.setBase_unit_qty(ChangedQtyBase);
                    //These remain unchanged, values in the new object will be taken
                    //UnitPrice,UnitTradeDiscount,UnitVat,UnitPriceIncVat,UnitPriceExcVat,UnitCostPrice,UnitProfitMargin
                    //EarnPerc,Qty_balance,Qty_damage,Duration_value,Duration_passed,earn_amount
                    CreditDebitTransItem.setAmount(NewTransItem.getAmount() - OldTransItem.getAmount());
                    CreditDebitTransItem.setAmountIncVat(NewTransItem.getAmountIncVat() - OldTransItem.getAmountIncVat());
                    CreditDebitTransItem.setAmountExcVat(NewTransItem.getAmountExcVat() - OldTransItem.getAmountExcVat());
                    CreditDebitTransItems.add(CreditDebitTransItem);
                }
            }
            //get credit/debit trans
            //Remain Unchanged: New trans to take precendance
            //CashDiscount,edit_user_detail_id,CardNumber,AmountTendered,ChangeAmount,TotalProfitMargin
            tb.copyTransObject(aNewTrans, CreditDebitTrans);//From,To
            CreditDebitTrans.setTransactionDate(new CompanySetting().getCURRENT_SERVER_DATE());
            CreditDebitTrans.setTransactionId(0);
            CreditDebitTrans.setTransactionNumber("");
            CreditDebitTrans.setTransactionTypeId(TransTypeId);
            CreditDebitTrans.setTransactionReasonId(TransReasId);
            CreditDebitTrans.setTotalVat(aNewTrans.getTotalVat() - aOldTrans.getTotalVat());
            CreditDebitTrans.setSubTotal(aNewTrans.getSubTotal() - aOldTrans.getSubTotal());
            CreditDebitTrans.setGrandTotal(aNewTrans.getGrandTotal() - aOldTrans.getGrandTotal());
            CreditDebitTrans.setTotalTradeDiscount(aNewTrans.getTotalTradeDiscount() - aOldTrans.getTotalTradeDiscount());
            CreditDebitTrans.setCashDiscount(aNewTrans.getCashDiscount() - aOldTrans.getCashDiscount());
            CreditDebitTrans.setPointsAwarded(aNewTrans.getPointsAwarded() - aOldTrans.getPointsAwarded());
            CreditDebitTrans.setSpendPointsAmount(aNewTrans.getSpendPointsAmount() - aOldTrans.getSpendPointsAmount());
            CreditDebitTrans.setTotalStdVatableAmount(aNewTrans.getTotalStdVatableAmount() - aOldTrans.getTotalStdVatableAmount());
            CreditDebitTrans.setTotalZeroVatableAmount(aNewTrans.getTotalZeroVatableAmount() - aOldTrans.getTotalZeroVatableAmount());
            CreditDebitTrans.setTotalExemptVatableAmount(aNewTrans.getTotalExemptVatableAmount() - aOldTrans.getTotalExemptVatableAmount());
            CreditDebitTrans.setTotalProfitMargin(aNewTrans.getTotalProfitMargin() - aOldTrans.getTotalProfitMargin());
            CreditDebitTrans.setAmountTendered(aNewTrans.getAmountTendered() - aOldTrans.getAmountTendered());
            CreditDebitTrans.setTotalPaid(0);
            CreditDebitTrans.setChangeAmount(aNewTrans.getChangeAmount() - aOldTrans.getChangeAmount());
            CreditDebitTrans.setTransactionRef(aNewTrans.getTransactionNumber());
            CreditDebitTrans.setTransactionComment(CreditOrDebitNote);
            CreditDebitTrans.setMode_code(aMode_code);
            CreditDebitTrans.setTransactionUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            //save trans credit/debit note
            SavedNoteId = this.insertTrans_cr_dr_note(aNewTrans.getStoreId(), TransTypeId, TransReasId, CreditDebitTrans);
            if (SavedNoteId > 0) {
                this.saveTransItems_cr_dr_note(aNewTrans.getStoreId(), TransTypeId, TransReasId, CreditDebitTrans, CreditDebitTransItems, SavedNoteId);
            }
            //save
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return SavedNoteId;
    }

    public long insertTrans_cr_dr_note(int aStoreId, int aTransTypeId, int aTransReasonId, Trans trans) {
        long InsertedTransId = 0;
        String sql = "{call sp_insert_transaction_cr_dr_note(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);
            cs.setDate("in_transaction_date", new java.sql.Date(trans.getTransactionDate().getTime()));
            cs.setInt("in_store_id", store.getStoreId());
            trans.setStoreId(store.getStoreId());
            cs.setInt("in_store2_id", trans.getStore2Id());
            cs.setLong("in_transactor_id", trans.getTransactorId());
            trans.setTransactionTypeId(transtype.getTransactionTypeId());
            cs.setInt("in_transaction_type_id", trans.getTransactionTypeId());
            trans.setTransactionReasonId(transreason.getTransactionReasonId());
            cs.setInt("in_transaction_reason_id", trans.getTransactionReasonId());
            cs.setDouble("in_cash_discount", trans.getCashDiscount());
            cs.setDouble("in_total_vat", trans.getTotalVat());
            cs.setString("in_transaction_comment", trans.getTransactionComment());
            cs.setInt("in_add_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
            trans.setAddUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            cs.setTimestamp("in_add_date", new java.sql.Timestamp(new java.util.Date().getTime()));
            cs.setInt("in_edit_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());//will be made null by the SP
            cs.setTimestamp("in_edit_date", new java.sql.Timestamp(new java.util.Date().getTime()));//will be made null by the SP
            cs.setString("in_transaction_ref", trans.getTransactionRef());
            cs.registerOutParameter("out_transaction_id", VARCHAR);
            cs.setDouble("in_sub_total", trans.getSubTotal());
            cs.setDouble("in_grand_total", trans.getGrandTotal());
            cs.setDouble("in_total_trade_discount", trans.getTotalTradeDiscount());
            cs.setDouble("in_points_awarded", trans.getPointsAwarded());
            cs.setString("in_card_number", trans.getCardNumber());
            cs.setDouble("in_total_std_vatable_amount", trans.getTotalStdVatableAmount());
            cs.setDouble("in_total_zero_vatable_amount", trans.getTotalZeroVatableAmount());
            cs.setDouble("in_total_exempt_vatable_amount", trans.getTotalExemptVatableAmount());
            cs.setDouble("in_vat_perc", CompanySetting.getVatPerc());
            cs.setDouble("in_amount_tendered", trans.getAmountTendered());
            cs.setDouble("in_change_amount", trans.getChangeAmount());
            cs.setString("in_is_cash_discount_vat_liable", CompanySetting.getIsCashDiscountVatLiable());
            cs.setDouble("in_total_profit_margin", trans.getTotalProfitMargin());
            try {
                if (trans.getTransactionUserDetailId() == 0) {
                    trans.setTransactionUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                }
            } catch (NullPointerException npe) {
                trans.setTransactionUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            }
            cs.setInt("in_transaction_user_detail_id", trans.getTransactionUserDetailId());
            try {
                if (trans.getBillTransactorId() == 0) {
                    trans.setBillTransactorId(trans.getTransactorId());
                }
            } catch (NullPointerException npe) {
                trans.setBillTransactorId(trans.getTransactorId());
            }
            cs.setLong("in_bill_transactor_id", trans.getBillTransactorId());
            try {
                cs.setLong("in_scheme_transactor_id", trans.getSchemeTransactorId());
            } catch (NullPointerException npe) {
                cs.setLong("in_scheme_transactor_id", 0);
            }
            try {
                cs.setString("in_princ_scheme_member", trans.getPrincSchemeMember());
            } catch (NullPointerException npe) {
                cs.setString("in_princ_scheme_member", "");
            }
            try {
                cs.setString("in_scheme_card_number", trans.getSchemeCardNumber());
            } catch (NullPointerException npe) {
                cs.setString("in_scheme_card_number", "");
            }
            try {
                if (trans.getTransactionNumber().length() == 0) {
                    String NewTransNo = new Trans_number_controlBean().getNewTransNumber(transtype);
                    int IsNewTransNoUsed = new Trans_number_controlBean().getIsTrans_number_used(transtype.getTransactionTypeId(), NewTransNo);
                    if (IsNewTransNoUsed == 0) {
                        trans.setTransactionNumber(NewTransNo);
                        cs.setString("in_transaction_number", trans.getTransactionNumber());
                        new Trans_number_controlBean().updateTrans_number_control(transtype);
                    } else {
                        trans.setTransactionNumber("");
                        cs.setString("in_transaction_number", trans.getTransactionNumber());
                    }
                } else {
                    cs.setString("in_transaction_number", trans.getTransactionNumber());
                }
            } catch (NullPointerException npe) {
                cs.setString("in_transaction_number", "");
            }
            try {
                cs.setDate("in_delivery_date", new java.sql.Date(trans.getDeliveryDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_delivery_date", null);
            }
            try {
                cs.setString("in_delivery_address", trans.getDeliveryAddress());
            } catch (NullPointerException npe) {
                cs.setString("in_delivery_address", "");
            }
            try {
                cs.setString("in_pay_terms", trans.getPayTerms());
            } catch (NullPointerException npe) {
                cs.setString("in_pay_terms", "");
            }
            try {
                cs.setString("in_terms_conditions", trans.getTermsConditions());
            } catch (NullPointerException npe) {
                cs.setString("in_terms_conditions", "");
            }
            try {
                cs.setInt("in_authorised_by_user_detail_id", trans.getAuthorisedByUserDetailId());
            } catch (NullPointerException npe) {
                cs.setInt("in_authorised_by_user_detail_id", 0);
            }
            try {
                cs.setDate("in_authorise_date", new java.sql.Date(trans.getAuthoriseDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_authorise_date", null);
            }
            try {
                cs.setDate("in_pay_due_date", new java.sql.Date(trans.getPayDueDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_pay_due_date", null);
            }
            try {
                cs.setDate("in_expiry_date", new java.sql.Date(trans.getExpiryDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_expiry_date", null);
            }
            try {
                cs.setInt("in_acc_child_account_id", trans.getAccChildAccountId());
            } catch (NullPointerException npe) {
                cs.setInt("in_acc_child_account_id", 0);
            }
            try {
                cs.setString("in_currency_code", trans.getCurrencyCode());
            } catch (NullPointerException npe) {
                cs.setString("in_currency_code", "");
            }
            try {
                trans.setXrate(trans.getXrate());
            } catch (NullPointerException npe) {
                trans.setXrate(1);
            }
            cs.setDouble("in_xrate", trans.getXrate());
            try {
                cs.setDate("in_from_date", new java.sql.Date(trans.getFrom_date().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_from_date", null);
            }
            try {
                cs.setDate("in_to_date", new java.sql.Date(trans.getTo_date().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_to_date", null);
            }
            try {
                cs.setString("in_duration_type", trans.getDuration_type());
            } catch (NullPointerException npe) {
                cs.setString("in_duration_type", "");
            }
            try {
                cs.setLong("in_site_id", trans.getSite_id());
            } catch (NullPointerException npe) {
                cs.setLong("in_site_id", 0);
            }
            try {
                cs.setString("in_transactor_rep", trans.getTransactor_rep());
            } catch (NullPointerException npe) {
                cs.setString("in_transactor_rep", "");
            }
            try {
                cs.setString("in_transactor_vehicle", trans.getTransactor_vehicle());
            } catch (NullPointerException npe) {
                cs.setString("in_transactor_vehicle", "");
            }
            try {
                cs.setString("in_transactor_driver", trans.getTransactor_driver());
            } catch (NullPointerException npe) {
                cs.setString("in_transactor_driver", "");
            }
            try {
                cs.setDouble("in_duration_value", trans.getDuration_value());
            } catch (NullPointerException npe) {
                cs.setDouble("in_duration_value", 0);
            }
            //bought in after order module
            try {
                cs.setLong("in_location_id", trans.getLocation_id());
            } catch (NullPointerException npe) {
                cs.setLong("in_location_id", 0);
            }
            if (null == trans.getStatus_code()) {
                cs.setString("in_status_code", "");
            } else {
                cs.setString("in_status_code", trans.getStatus_code());
            }
            if (null == trans.getStatus_date()) {
                cs.setTimestamp("in_status_date", null);
            } else {
                cs.setTimestamp("in_status_date", new java.sql.Timestamp(trans.getStatus_date().getTime()));
            }
            if (null == trans.getDelivery_mode()) {
                cs.setString("in_delivery_mode", "");
            } else {
                cs.setString("in_delivery_mode", trans.getDelivery_mode());
            }
            try {
                cs.setInt("in_is_processed", trans.getIs_processed());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_processed", 0);
            }
            try {
                cs.setInt("in_is_paid", trans.getIs_paid());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_paid", 0);
            }
            try {
                cs.setInt("in_is_cancel", trans.getIs_cancel());
            } catch (NullPointerException npe) {
                cs.setInt("in_is_cancel", 0);
            }
            try {
                cs.setDouble("in_spent_points_amount", trans.getSpendPointsAmount());
            } catch (Exception e) {
                cs.setDouble("in_spent_points_amount", 0);
            }
            try {
                cs.setInt("in_mode_code", trans.getMode_code());
            } catch (NullPointerException npe) {
                cs.setInt("in_mode_code", 0);
            }
            //save
            cs.executeUpdate();
            InsertedTransId = cs.getLong("out_transaction_id");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return InsertedTransId;
    }

    public void saveTransItems_cr_dr_note(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, List<TransItem> aActiveTransItems, long TransactionId) {
        try {
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            while (ListItemIndex < ListItemNo) {
                ati.get(ListItemIndex).setTransactionId(TransactionId);
                this.saveTransItem_cr_dr_note(aStoreId, aTransTypeId, aTransReasonId, aTrans, ati.get(ListItemIndex));
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveTransItem_cr_dr_note(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, TransItem transitem) {
        String sql = null;
        if (transitem.getTransactionItemId() == 0) {
            sql = "{call sp_insert_transaction_item_cr_dr_note(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (transitem.getTransactionItemId() > 0) {
            sql = "{call sp_update_transaction_item_cr_dr_note(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (transitem.getTransactionItemId() == 0) {
                //clean batch
                if (transitem.getBatchno() == null) {
                    transitem.setBatchno("");
                }
                cs.setString("in_is_trade_discount_vat_liable", transitem.getIsTradeDiscountVatLiable());
                cs.setLong("in_transaction_id", transitem.getTransactionId());
                cs.setLong("in_item_id", transitem.getItemId());
                cs.setString("in_batchno", transitem.getBatchno());
                try {
                    cs.setDate("in_item_mnf_date", new java.sql.Date(transitem.getItemMnfDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_item_mnf_date", null);
                }
                try {
                    cs.setDate("in_item_expiry_date", new java.sql.Date(transitem.getItemExpryDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_item_expiry_date", null);
                }
                cs.setDouble("in_item_qty", transitem.getItemQty());
                cs.setDouble("in_unit_price", transitem.getUnitPrice());
                cs.setDouble("in_unit_trade_discount", transitem.getUnitTradeDiscount());
                cs.setDouble("in_unit_vat", transitem.getUnitVat());
                cs.setDouble("in_amount", transitem.getAmount());
                cs.setString("in_vat_rated", transitem.getVatRated());
                cs.setDouble("in_vat_perc", transitem.getVatPerc());
                cs.setDouble("in_unit_price_inc_vat", transitem.getUnitPriceIncVat());
                cs.setDouble("in_unit_price_exc_vat", transitem.getUnitPriceExcVat());
                cs.setDouble("in_amount_inc_vat", transitem.getAmountIncVat());
                cs.setDouble("in_amount_exc_vat", transitem.getAmountExcVat());
                cs.setString("in_stock_effect", "");
                cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
                cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
                cs.setDouble("in_earn_perc", 0);
                cs.setDouble("in_earn_amount", 0);
                try {
                    cs.setString("in_code_specific", transitem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    cs.setString("in_code_specific", "");
                }
                try {
                    cs.setString("in_desc_specific", transitem.getDescSpecific());
                } catch (NullPointerException npe) {
                    cs.setString("in_desc_specific", "");
                }
                try {
                    cs.setString("in_desc_more", transitem.getDescMore());
                } catch (NullPointerException npe) {
                    cs.setString("in_desc_more", "");
                }
                try {
                    cs.setString("in_warranty_desc", transitem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    cs.setString("in_warranty_desc", "");
                }
                try {
                    cs.setDate("in_warranty_expiry_date", new java.sql.Date(transitem.getWarrantyExpiryDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_warranty_expiry_date", null);
                }
                try {
                    cs.setString("in_account_code", transitem.getAccountCode());
                } catch (NullPointerException npe) {
                    cs.setString("in_account_code", "");
                }
                try {
                    cs.setDate("in_purchase_date", new java.sql.Date(transitem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_purchase_date", null);
                }
                try {
                    cs.setDate("in_dep_start_date", new java.sql.Date(transitem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_dep_start_date", null);
                }
                try {
                    cs.setInt("in_dep_method_id", transitem.getDepMethodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_dep_method_id", 0);
                }
                try {
                    cs.setDouble("in_dep_rate", transitem.getDepRate());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_dep_rate", 0);
                }
                try {
                    cs.setInt("in_average_method_id", transitem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_average_method_id", 0);
                }
                try {
                    cs.setInt("in_effective_life", transitem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    cs.setInt("in_effective_life", 0);
                }
                try {
                    cs.setDouble("in_residual_value", transitem.getResidualValue());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_residual_value", 0);
                }
                try {
                    cs.setString("in_narration", transitem.getNarration());
                } catch (NullPointerException npe) {
                    cs.setString("in_narration", "");
                }
                try {
                    cs.setDouble("in_qty_balance", transitem.getQty_balance());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_qty_balance", 0);
                }
                try {
                    cs.setDouble("in_duration_value", transitem.getDuration_value());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_duration_value", 0);
                }
                try {
                    cs.setDouble("in_qty_damage", transitem.getQty_damage());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_qty_damage", 0);
                }
                try {
                    cs.setDouble("in_duration_passed", transitem.getDuration_passed());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_duration_passed", 0);
                }
                try {
                    if (transitem.getSpecific_size() > 0) {
                        cs.setDouble("in_specific_size", transitem.getSpecific_size());
                    } else {
                        cs.setDouble("in_specific_size", 1);
                    }
                } catch (NullPointerException npe) {
                    cs.setDouble("in_specific_size", 1);
                }
                cs.registerOutParameter("out_transaction_item_id", VARCHAR);
                //save
                cs.executeUpdate();
                long InsertedId1 = cs.getLong("out_transaction_item_id");
                try {
                    if (InsertedId1 > 0) {
                        Transaction_item_cr_dr_note_unit tiu = new Transaction_item_cr_dr_note_unit();
                        tiu.setTransaction_item_id(InsertedId1);
                        tiu.setUnit_id(transitem.getUnit_id());
                        tiu.setBase_unit_qty(transitem.getBase_unit_qty());
                        new TransItemExtBean().insertTransaction_item_cr_dr_note_unit(tiu);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                }
            } else if (transitem.getTransactionItemId() > 0) {
                //do nothing; this is for edit
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<TransItem> getTransItemsByTransactionId_cr_dr_note(long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id_cr_dr_note(?)}";
        ResultSet rs = null;
        List<TransItem> tis = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            TransItem ti = null;
            while (rs.next()) {
                ti = new TransItemBean().getTransItemFromResultSet(rs);
                new TransItemBean().updateLookUpsUI(ti);
                tis.add(ti);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return tis;
    }

    public TransItem getTransItem_cr_dr_note(long aTransactionItemId) {
        TransItem ti = null;
        String sql;
        sql = "{call sp_search_transaction_item_cr_dr_note(?)}";
        ResultSet rs = null;
        List<TransItem> tis = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionItemId);
            rs = ps.executeQuery();

            if (rs.next()) {
                ti = new TransItemBean().getTransItemFromResultSet(rs);
                new TransItemBean().updateLookUpsUI(ti);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ti;
    }

    public int getCountDebitAndCreditNotes(String aTransactionNumber) {
        int n = 0;
        String sql;
        sql = "SELECT COUNT(*) as ncount FROM transaction_cr_dr_note WHERE transaction_ref=? AND is_cancel=0";
        ResultSet rs = null;
        List<TransItem> tis = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransactionNumber);
            rs = ps.executeQuery();
            TransItem ti = null;
            if (rs.next()) {
                n = rs.getInt("ncount");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return n;
    }

    public void initResetSalesCrDrNoteDetail(Trans aTrans, CreditDebitNoteBean aCreditDebitNoteBean, Transactor aBillTransactor, Transactor aTransactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetSalesCrDrNoteDetail(aTrans, aCreditDebitNoteBean, aBillTransactor, aTransactor);
        }
    }

    public void resetSalesCrDrNoteDetail(Trans aTrans, CreditDebitNoteBean aCreditDebitNoteBean, Transactor aBillTransactor, Transactor aTransactor) {
        aCreditDebitNoteBean.setActionMessage("");
        try {
            new TransBean().clearTrans(aTrans);
            aTrans.setTransactionTypeId(0);
            aTrans.setTransactionReasonId(0);
        } catch (NullPointerException npe) {
        }
        try {
            new TransactorBean().clearTransactor(aBillTransactor);
        } catch (NullPointerException npe) {
        }
        try {
            new TransactorBean().clearTransactor(aTransactor);
        } catch (NullPointerException npe) {
        }
        try {
            aCreditDebitNoteBean.setDateType("");
            aCreditDebitNoteBean.setDate1(null);
            aCreditDebitNoteBean.setDate2(null);
            aCreditDebitNoteBean.setFieldName("");
            aCreditDebitNoteBean.TransList.clear();
            aCreditDebitNoteBean.TransListSummary.clear();
        } catch (NullPointerException npe) {
        }
    }

    public void setDateToToday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();
        this.setDate1(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getDate1());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate1(cal.getTime());

        this.setDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getDate2());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate2(cal2.getTime());
    }

    public void setDateToYesturday() {
        Date CurrentServerDate = new CompanySetting().getCURRENT_SERVER_DATE();

        this.setDate1(CurrentServerDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getDate1());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate1(cal.getTime());

        this.setDate2(CurrentServerDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(this.getDate2());
        cal2.add(Calendar.DATE, -1);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        // Put it back in the Date object  
        this.setDate2(cal2.getTime());
    }

    public void reportSalesCrDrNoteDetail(Trans aTrans, CreditDebitNoteBean aCreditDebitNoteBean) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        aCreditDebitNoteBean.setActionMessage("");
        try {
            if ((aCreditDebitNoteBean.getDate1() != null && aCreditDebitNoteBean.getDate2() != null) || aTrans.getTransactionNumber().length() > 0 || aTrans.getTransactionRef().length() > 0) {
                //okay no problem
            } else {
                msg = "Either Select Date Range or Specify Note Number or Specify Invoice Reference Number";
            }
        } catch (Exception e) {
            //do nothing
        }
        if (aCreditDebitNoteBean.getDateType().length() == 0) {
            aCreditDebitNoteBean.setDateType("Add Date");
        }
        ResultSet rs = null;
        this.TransList = new ArrayList<>();
        this.TransListSummary = new ArrayList<>();
        if (msg.length() > 0) {
            aCreditDebitNoteBean.setActionMessage(ub.translateWordsInText(BaseName, msg));
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "SELECT * FROM transaction_cr_dr_note WHERE 1=1";
            String sqlsum = "";
            if (aCreditDebitNoteBean.getFieldName().length() > 0) {
                sqlsum = "SELECT " + aCreditDebitNoteBean.getFieldName() + ",currency_code,sum(grand_total) as grand_total,sum(total_profit_margin) as total_profit_margin,sum(total_vat) as total_vat,sum(cash_discount) as cash_discount,sum(spent_points_amount) as spent_points_amount FROM transaction_cr_dr_note WHERE 1=1";
            } else {
                sqlsum = "SELECT currency_code,sum(grand_total) as grand_total,sum(total_profit_margin) as total_profit_margin,sum(total_vat) as total_vat,sum(cash_discount) as cash_discount,sum(spent_points_amount) as spent_points_amount FROM transaction_cr_dr_note WHERE 1=1";
            }
            String wheresql = "";
            String ordersql = "";
            String ordersqlsum = "";
            String groupbysql = "";
            if (aCreditDebitNoteBean.getFieldName().length() > 0) {
                groupbysql = " GROUP BY " + aCreditDebitNoteBean.getFieldName() + ",currency_code";
            } else {
                groupbysql = " GROUP BY currency_code";
            }
            if (aTrans.getStoreId() > 0) {
                wheresql = wheresql + " AND store_id=" + aTrans.getStoreId();
            }
            if (aTrans.getTransactionNumber().length() > 0) {
                wheresql = wheresql + " AND transaction_number='" + aTrans.getTransactionNumber() + "'";
            }
            if (aTrans.getTransactionRef().length() > 0) {
                wheresql = wheresql + " AND transaction_ref='" + aTrans.getTransactionRef() + "'";
            }
            if (aTrans.getAddUserDetailId() > 0) {
                wheresql = wheresql + " AND add_user_detail_id=" + aTrans.getAddUserDetailId();
            }
            if (aTrans.getTransactionUserDetailId() > 0) {
                wheresql = wheresql + " AND transaction_user_detail_id=" + aTrans.getTransactionUserDetailId();
            }
            if (aTrans.getBillTransactorId() > 0) {
                wheresql = wheresql + " AND bill_transactor_id=" + aTrans.getBillTransactorId();
            }
            if (aTrans.getTransactorId() > 0) {
                wheresql = wheresql + " AND transactor_id=" + aTrans.getTransactorId();
            }
            if (aCreditDebitNoteBean.getDateType().length() > 0 && aCreditDebitNoteBean.getDate1() != null && aCreditDebitNoteBean.getDate2() != null) {
                switch (aCreditDebitNoteBean.getDateType()) {
                    case "Note Date":
                        wheresql = wheresql + " AND transaction_date BETWEEN '" + new java.sql.Date(aCreditDebitNoteBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aCreditDebitNoteBean.getDate2().getTime()) + "'";
                        break;
                    case "Add Date":
                        wheresql = wheresql + " AND add_date BETWEEN '" + new java.sql.Timestamp(aCreditDebitNoteBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aCreditDebitNoteBean.getDate2().getTime()) + "'";
                        break;
                }
            }
            ordersql = " ORDER BY add_date DESC,transaction_id DESC";
            if (aCreditDebitNoteBean.getFieldName().length() > 0) {
                ordersqlsum = " ORDER BY " + aCreditDebitNoteBean.getFieldName() + ",currency_code";
            } else {
                ordersqlsum = " ORDER BY currency_code";
            }
            sql = sql + wheresql + ordersql;
            sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                Trans trans = null;
                TransBean tb = new TransBean();
                while (rs.next()) {
                    trans = new Trans();
                    tb.setTransFromResultset(trans, rs);
                    this.TransList.add(trans);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                Trans transsum = null;
                while (rs.next()) {
                    transsum = new Trans();
                    if (aCreditDebitNoteBean.getFieldName().length() > 0) {
                        switch (aCreditDebitNoteBean.getFieldName()) {
                            case "add_user_detail_id":
                                try {
                                    transsum.setAddUserDetailId(rs.getInt("add_user_detail_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setAddUserDetailId(0);
                                }
                                break;
                            case "transaction_user_detail_id":
                                try {
                                    transsum.setTransactionUserDetailId(rs.getInt("transaction_user_detail_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setTransactionUserDetailId(0);
                                }
                                break;
                            case "bill_transactor_id":
                                try {
                                    transsum.setBillTransactorId(rs.getLong("bill_transactor_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setBillTransactorId(0);
                                }
                                break;
                            case "transactor_id":
                                try {
                                    transsum.setTransactorId(rs.getLong("transactor_id"));
                                } catch (NullPointerException npe) {
                                    transsum.setTransactorId(0);
                                }
                                break;
                            case "transaction_date":
                                try {
                                    transsum.setTransactionDate(new Date(rs.getDate("transaction_date").getTime()));
                                } catch (NullPointerException | SQLException npe) {
                                    transsum.setTransactionDate(null);
                                }
                                break;
                            case "store_id":
                                try {
                                    transsum.setStoreId(rs.getInt("store_id"));
                                    Store st = new StoreBean().getStore(transsum.getStoreId());
                                    transsum.setStoreName(st.getStoreName());
                                } catch (NullPointerException npe) {
                                    transsum.setStoreName("");
                                }
                                break;
                        }
                    }
                    try {
                        transsum.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        transsum.setCurrencyCode("");
                    }
                    try {
                        transsum.setGrandTotal(rs.getDouble("grand_total"));
                    } catch (NullPointerException npe) {
                        transsum.setGrandTotal(0);
                    }
                    try {
                        transsum.setTotalProfitMargin(rs.getDouble("total_profit_margin"));
                    } catch (NullPointerException npe) {
                        transsum.setTotalProfitMargin(0);
                    }
                    try {
                        transsum.setTotalVat(rs.getDouble("total_vat"));
                    } catch (NullPointerException npe) {
                        transsum.setTotalVat(0);
                    }
                    try {
                        transsum.setCashDiscount(rs.getDouble("cash_discount"));
                    } catch (NullPointerException npe) {
                        transsum.setCashDiscount(0);
                    }
                    try {
                        transsum.setSpendPointsAmount(rs.getDouble("spent_points_amount"));
                    } catch (NullPointerException npe) {
                        transsum.setSpendPointsAmount(0);
                    }
                    this.TransListSummary.add(transsum);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public void initCreditDebitNoteSession(long aTransId, String aAction) {
        try {
            long TransId = aTransId;
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("CURRENT_TRANSACTION_ID", TransId);
            httpSession.setAttribute("CURRENT_TRANSACTION_ACTION", aAction);
            httpSession.setAttribute("CURRENT_PAY_ID", 0);
            this.setActionType(aAction);
            //this.setTransObj(new CreditDebitNoteBean().getTrans_cr_dr_note(TransId));
            //this.setTransItemList(new CreditDebitNoteBean().getTransItemsByTransactionId_cr_dr_note(TransId));
            //this.setPayObj(null);
            //refresh output
            new OutputDetailBean().refreshOutputCrDr("PARENT", "");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String validateCreditNote(int aStoreId, Trans trans, List<TransItem> aActiveTransItems) {
        //CREDIT NOTE; CreditOrDebitNote = "Credit Note"; TransTypeId = 82; TransReasId = 126;
        String msg = "";
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(82);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(126);
            Store store = new StoreBean().getStore(aStoreId);

            String ItemMessage = "";
            try {
                //ItemMessage = new TransItemBean().getAnyItemTotalQtyGreaterThanCurrentQty(new TransItemBean().getTransItemListCurLessPrevQty(aActiveTransItems, trans), store.getStoreId(), transtype.getTransactionTypeName());
            } catch (NullPointerException npe) {
            }
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            if (null == transtype) {
                msg = "Invalid Transaction";
            } else if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(transreason.getTransactionReasonId()), "Add") == 0) {
                msg = "Access Denied";
            } else if (trans.getTransactionDate() == null) {
                msg = "Select " + transtype.getTransactionDateLabel();
            } else if ((new GeneralUserSetting().getDaysFromDateToLicenseExpiryDate(trans.getTransactionDate()) <= 0 || new GeneralUserSetting().getDaysFromDateToLicenseExpiryDate(new CompanySetting().getCURRENT_SERVER_DATE()) <= 0) && CompanySetting.getLicenseType() != 9) {
                msg = "Server Date is Wrong or Lincese is Expired";
            } else if (aActiveTransItems.size() < 1) {
                msg = "Item not Found for " + transtype.getTransactionOutputLabel();
            } else if (trans.getGrandTotal() < 0) {
                msg = "Invalid Credit Note Amount";
            } else if (null == new AccPeriodBean().getAccPeriod(new CompanySetting().getCURRENT_SERVER_DATE())) {
                msg = "Selected Date does not Match Accounting Period";
            } else if (new AccPeriodBean().getAccPeriod(new CompanySetting().getCURRENT_SERVER_DATE()).getIsClosed() == 1) {
                msg = "Selected Date is for a Closed Accounting Period";
            } else if (this.countItemsWithQtyChange("Adds", new TransItemBean().getTransItemListCurLessPrevQty(aActiveTransItems, trans)) > 0) {
                msg = "You Cannot Add Quantity for Credit Note";
            } else if (this.countItemsWithQtyChange("Subs", new TransItemBean().getTransItemListCurLessPrevQty(aActiveTransItems, trans)) == 0) {
                msg = "Atleast One Item Quantity has to Decrease";
            }
        } catch (Exception e) {
            msg = "An Error has Occured During the Validation Process";
            //System.err.println("--:validateTransCEC:--" + e.getMessage());
            LOGGER.log(Level.ERROR, e);
        }
        return msg;
    }

    public String validateDebitNote(int aStoreId, Trans trans, List<TransItem> aActiveTransItems) {
        String msg = "";
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(83);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(127);
            Store store = new StoreBean().getStore(aStoreId);

            String ItemMessage = "";
            try {
                ItemMessage = new TransItemBean().getAnyItemTotalQtyGreaterThanCurrentQty(new TransItemBean().getTransItemListCurLessPrevQty(aActiveTransItems, trans), store.getStoreId(), transtype.getTransactionTypeName());
            } catch (NullPointerException npe) {
            }
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            if (null == transtype) {
                msg = "Invalid Transaction";
            } else if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, Integer.toString(transreason.getTransactionReasonId()), "Add") == 0) {
                msg = "Access Denied";
            } else if (trans.getTransactionDate() == null) {
                msg = "Select " + transtype.getTransactionDateLabel();
            } else if ((new GeneralUserSetting().getDaysFromDateToLicenseExpiryDate(trans.getTransactionDate()) <= 0 || new GeneralUserSetting().getDaysFromDateToLicenseExpiryDate(new CompanySetting().getCURRENT_SERVER_DATE()) <= 0) && CompanySetting.getLicenseType() != 9) {
                msg = "Server Date is Wrong or Lincese is Expired";
            } else if (aActiveTransItems.size() < 1) {
                msg = "Item not Found for " + transtype.getTransactionOutputLabel();
            } else if (trans.getGrandTotal() <= 0) {
                msg = "Invalid Debit Note Amount";
            } else if (null == new AccPeriodBean().getAccPeriod(new CompanySetting().getCURRENT_SERVER_DATE())) {
                msg = "Selected Date does not Match Accounting Period";
            } else if (new AccPeriodBean().getAccPeriod(new CompanySetting().getCURRENT_SERVER_DATE()).getIsClosed() == 1) {
                msg = "Selected Date is for a Closed Accounting Period";
            } else if (this.countItemsWithQtyChange("Adds", new TransItemBean().getTransItemListCurLessPrevQty(aActiveTransItems, trans)) == 0) {
                msg = "Atleast One Item Quantity has to Increase";
            } else if (this.countItemsWithQtyChange("Subs", new TransItemBean().getTransItemListCurLessPrevQty(aActiveTransItems, trans)) > 0) {
                msg = "You Cannot Subtract Quantity for Debit Note";
            } else if (trans.getBillTransactorId() == 0) {
                msg = "You Cannot Debit Transaction With No Customer";
            } else if (!ItemMessage.equals("")) {
                msg = "Insufficient Stock for Item ##" + ItemMessage;
            }
        } catch (Exception e) {
            msg = "An Error has Occured During the Validation Process";
            LOGGER.log(Level.ERROR, e);
        }
        return msg;
    }

    public int countItemsWithQtyChange(String aChange, List<TransItem> aTransItemsListCurLessPrevQty) {
        int Found = 0;
        try {
            List<TransItem> ati = aTransItemsListCurLessPrevQty;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            while (ListItemIndex < ListItemNo) {
                if (aChange.equals("Adds") && ati.get(ListItemIndex).getItemQty() > 0) {
                    Found = Found + 1;
                } else if (aChange.equals("Subs") && ati.get(ListItemIndex).getItemQty() < 0) {
                    Found = Found + 1;
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Found;
    }

    public boolean stockAdjustCrDrNote(long aCrDrNoteTransId) {
        try {
            Trans trans = this.getTrans_cr_dr_note(aCrDrNoteTransId);
            List<TransItem> transItemList = this.getTransItemsByTransactionId_cr_dr_note(aCrDrNoteTransId);
            int i = 0;
            int n = 0;
            int nSuccess = 0;
            try {
                n = transItemList.size();
            } catch (Exception e) {
                //
            }
            while (i < n) {
                int success = this.stockAdjustCrDrNote(trans, transItemList.get(i));
                nSuccess = nSuccess + success;
                i = i + 1;
            }
            if (nSuccess == n) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return false;
        }
    }

    public int stockAdjustCrDrNote(Trans aTransCrDr, TransItem aTransItemCrDr) {
        int success = 0;
        try {
            if (new ItemBean().getItem(aTransItemCrDr.getItemId()).getIsTrack() == 0) {
                success = 1;
            } else {
                String sql = null;
                String sql2 = null;
                StockBean StkBean = new StockBean();
                Stock Stk = new Stock();
                Stk = StkBean.getStock(aTransCrDr.getStoreId(), aTransItemCrDr.getItemId(), aTransItemCrDr.getBatchno(), aTransItemCrDr.getCodeSpecific(), aTransItemCrDr.getDescSpecific());
                double UnitCostPrice = 0;
                //stock change (previous-current) is positive (+):add(+)
                if (aTransItemCrDr.getItemQty() > 0) {
                    if (Stk != null) {
                        //update
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(aTransCrDr.getStoreId());
                        stock.setItemId(aTransItemCrDr.getItemId());
                        stock.setBatchno(aTransItemCrDr.getBatchno());
                        stock.setCodeSpecific(aTransItemCrDr.getCodeSpecific());
                        stock.setDescSpecific(aTransItemCrDr.getDescSpecific());
                        UnitCostPrice = aTransItemCrDr.getUnitCostPrice();
                        stock.setUnitCost(UnitCostPrice);
                        i = new StockBean().addStock(stock, aTransItemCrDr.getBase_unit_qty());
                        stock.setSpecific_size(aTransItemCrDr.getSpecific_size());
                        String TableName = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                        new Stock_ledgerBean().callInsertStock_ledger(TableName, "Add", stock, aTransItemCrDr.getItemQty(), "Add", aTransCrDr.getTransactionTypeId(), aTransCrDr.getTransactionId(), aTransCrDr.getAddUserDetailId(), aTransItemCrDr.getTransactionItemId());
                    } else {
                        //insert
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(aTransCrDr.getStoreId());
                        stock.setItemId(aTransItemCrDr.getItemId());
                        stock.setBatchno(aTransItemCrDr.getBatchno());
                        stock.setCodeSpecific(aTransItemCrDr.getCodeSpecific());
                        stock.setDescSpecific(aTransItemCrDr.getDescSpecific());
                        stock.setDescMore(aTransItemCrDr.getDescMore());
                        stock.setCurrentqty(aTransItemCrDr.getBase_unit_qty());
                        stock.setItemMnfDate(aTransItemCrDr.getItemMnfDate());
                        stock.setItemExpDate(aTransItemCrDr.getItemExpryDate());
                        UnitCostPrice = aTransItemCrDr.getUnitCostPrice();
                        stock.setUnitCost(UnitCostPrice);
                        stock.setWarrantyDesc(aTransItemCrDr.getWarrantyDesc());
                        stock.setWarrantyExpiryDate(aTransItemCrDr.getWarrantyExpiryDate());
                        stock.setPurchaseDate(aTransItemCrDr.getPurchaseDate());
                        stock.setDepStartDate(aTransItemCrDr.getDepStartDate());
                        stock.setDepMethodId(aTransItemCrDr.getDepMethodId());
                        stock.setDepRate(aTransItemCrDr.getDepRate());
                        stock.setAverageMethodId(aTransItemCrDr.getAverageMethodId());
                        stock.setEffectiveLife(aTransItemCrDr.getEffectiveLife());
                        stock.setAccountCode(aTransItemCrDr.getAccountCode());
                        stock.setResidualValue(aTransItemCrDr.getResidualValue());
                        stock.setAssetStatusId(1);
                        stock.setAssetStatusDesc("");
                        stock.setSpecific_size(aTransItemCrDr.getSpecific_size());
                        i = new StockBean().saveStock(stock);
                        String TableName = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                        new Stock_ledgerBean().callInsertStock_ledger(TableName, "Add", stock, aTransItemCrDr.getItemQty(), "Add", aTransCrDr.getTransactionTypeId(), aTransCrDr.getTransactionId(), aTransCrDr.getAddUserDetailId(), aTransItemCrDr.getTransactionItemId());
                    }
                    //stock change (previous-current) is negative (-):subtract(-)
                } else if (aTransItemCrDr.getItemQty() < 0) {
                    if (Stk != null) {
                        //update
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(aTransCrDr.getStoreId());
                        stock.setItemId(aTransItemCrDr.getItemId());
                        stock.setBatchno(aTransItemCrDr.getBatchno());
                        stock.setCodeSpecific(aTransItemCrDr.getCodeSpecific());
                        stock.setDescSpecific(aTransItemCrDr.getDescSpecific());
                        UnitCostPrice = aTransItemCrDr.getUnitCostPrice();
                        stock.setUnitCost(UnitCostPrice);
                        i = new StockBean().subtractStock(stock, (-1) * aTransItemCrDr.getBase_unit_qty());
                        stock.setSpecific_size(aTransItemCrDr.getSpecific_size());
                        String TableName = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                        new Stock_ledgerBean().callInsertStock_ledger(TableName, "Subtract", stock, (-1) * aTransItemCrDr.getItemQty(), "Add", aTransCrDr.getTransactionTypeId(), aTransCrDr.getTransactionId(), aTransCrDr.getAddUserDetailId(), aTransItemCrDr.getTransactionItemId());
                    } else {
                        //insert
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(aTransCrDr.getStoreId());
                        stock.setItemId(aTransItemCrDr.getItemId());
                        stock.setBatchno(aTransItemCrDr.getBatchno());
                        stock.setCodeSpecific(aTransItemCrDr.getCodeSpecific());
                        stock.setDescSpecific(aTransItemCrDr.getDescSpecific());
                        stock.setDescMore(aTransItemCrDr.getDescMore());
                        stock.setCurrentqty(aTransItemCrDr.getBase_unit_qty());
                        stock.setItemMnfDate(aTransItemCrDr.getItemMnfDate());
                        stock.setItemExpDate(aTransItemCrDr.getItemExpryDate());
                        UnitCostPrice = aTransItemCrDr.getUnitCostPrice();
                        stock.setUnitCost(UnitCostPrice);
                        stock.setWarrantyDesc(aTransItemCrDr.getWarrantyDesc());
                        stock.setWarrantyExpiryDate(aTransItemCrDr.getWarrantyExpiryDate());
                        stock.setPurchaseDate(aTransItemCrDr.getPurchaseDate());
                        stock.setDepStartDate(aTransItemCrDr.getDepStartDate());
                        stock.setDepMethodId(aTransItemCrDr.getDepMethodId());
                        stock.setDepRate(aTransItemCrDr.getDepRate());
                        stock.setAverageMethodId(aTransItemCrDr.getAverageMethodId());
                        stock.setEffectiveLife(aTransItemCrDr.getEffectiveLife());
                        stock.setAccountCode(aTransItemCrDr.getAccountCode());
                        stock.setResidualValue(aTransItemCrDr.getResidualValue());
                        stock.setAssetStatusId(1);
                        stock.setAssetStatusDesc("");
                        stock.setSpecific_size(aTransItemCrDr.getSpecific_size());
                        i = new StockBean().saveStock(stock);
                        String TableName = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                        new Stock_ledgerBean().callInsertStock_ledger(TableName, "Add", stock, aTransItemCrDr.getItemQty(), "Add", aTransCrDr.getTransactionTypeId(), aTransCrDr.getTransactionId(), aTransCrDr.getAddUserDetailId(), aTransItemCrDr.getTransactionItemId());
                    }
                }
                StkBean = null;
                Stk = null;
                success = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return success;
    }

    /**
     * @return the DateType
     */
    public String getDateType() {
        return DateType;
    }

    /**
     * @param DateType the DateType to set
     */
    public void setDateType(String DateType) {
        this.DateType = DateType;
    }

    /**
     * @return the Date1
     */
    public Date getDate1() {
        return Date1;
    }

    /**
     * @param Date1 the Date1 to set
     */
    public void setDate1(Date Date1) {
        this.Date1 = Date1;
    }

    /**
     * @return the Date2
     */
    public Date getDate2() {
        return Date2;
    }

    /**
     * @param Date2 the Date2 to set
     */
    public void setDate2(Date Date2) {
        this.Date2 = Date2;
    }

    /**
     * @return the FieldName
     */
    public String getFieldName() {
        return FieldName;
    }

    /**
     * @param FieldName the FieldName to set
     */
    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    /**
     * @return the TransList
     */
    public List<Trans> getTransList() {
        return TransList;
    }

    /**
     * @param TransList the TransList to set
     */
    public void setTransList(List<Trans> TransList) {
        this.TransList = TransList;
    }

    /**
     * @return the TransListSummary
     */
    public List<Trans> getTransListSummary() {
        return TransListSummary;
    }

    /**
     * @param TransListSummary the TransListSummary to set
     */
    public void setTransListSummary(List<Trans> TransListSummary) {
        this.TransListSummary = TransListSummary;
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param ActionMessage the ActionMessage to set
     */
    public void setActionMessage(String ActionMessage) {
        this.ActionMessage = ActionMessage;
    }

    /**
     * @return the menuItemBean
     */
    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    /**
     * @param menuItemBean the menuItemBean to set
     */
    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }

    /**
     * @return the TransObj
     */
    public Trans getTransObj() {
        return TransObj;
    }

    /**
     * @param TransObj the TransObj to set
     */
    public void setTransObj(Trans TransObj) {
        this.TransObj = TransObj;
    }

    /**
     * @return the TransItemList
     */
    public List<TransItem> getTransItemList() {
        return TransItemList;
    }

    /**
     * @param TransItemList the TransItemList to set
     */
    public void setTransItemList(List<TransItem> TransItemList) {
        this.TransItemList = TransItemList;
    }

    /**
     * @return the PayObj
     */
    public Pay getPayObj() {
        return PayObj;
    }

    /**
     * @param PayObj the PayObj to set
     */
    public void setPayObj(Pay PayObj) {
        this.PayObj = PayObj;
    }

    /**
     * @return the ActionType
     */
    public String getActionType() {
        return ActionType;
    }

    /**
     * @param ActionType the ActionType to set
     */
    public void setActionType(String ActionType) {
        this.ActionType = ActionType;
    }

}
