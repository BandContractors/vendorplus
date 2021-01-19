package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.TransactionReason;
import entities.TransactionType;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.GeneralUserSetting;

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
            System.err.println("getTrans_cr_dr_note:" + e.getMessage());
            return null;
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

    public long saveCreditDebitNote(Trans aOldTrans, Trans aNewTrans, List<TransItem> aOldTransItems, List<TransItem> aNewTransItems) {
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
                TransItem OldTransItem = tib.itemExistsObj(aOldTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());
                Double ChangedQty = NewTransItem.getItemQty() - OldTransItem.getItemQty();//can be + or -
                if (ChangedQty != 0) {
                    TransItem CreditDebitTransItem = new TransItem();
                    tib.copyObjectTransItem(NewTransItem, CreditDebitTransItem);//From,To
                    CreditDebitTransItem.setTransactionItemId(0);
                    CreditDebitTransItem.setItemQty(ChangedQty);
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
            CreditDebitTrans.setTransactionId(0);
            CreditDebitTrans.setTransactionNumber("");
            CreditDebitTrans.setTransactionTypeId(TransTypeId);
            CreditDebitTrans.setTransactionReasonId(TransReasId);
            CreditDebitTrans.setTotalVat(aNewTrans.getTotalVat() - aOldTrans.getTotalVat());
            CreditDebitTrans.setSubTotal(aNewTrans.getSubTotal() - aOldTrans.getSubTotal());
            CreditDebitTrans.setGrandTotal(aNewTrans.getGrandTotal() - aOldTrans.getGrandTotal());
            CreditDebitTrans.setTotalTradeDiscount(aNewTrans.getTotalTradeDiscount() - aOldTrans.getTotalTradeDiscount());
            CreditDebitTrans.setPointsAwarded(aNewTrans.getPointsAwarded() - aOldTrans.getPointsAwarded());
            CreditDebitTrans.setTotalStdVatableAmount(aNewTrans.getTotalStdVatableAmount() - aOldTrans.getTotalStdVatableAmount());
            CreditDebitTrans.setTotalZeroVatableAmount(aNewTrans.getTotalZeroVatableAmount() - aOldTrans.getTotalZeroVatableAmount());
            CreditDebitTrans.setTotalExemptVatableAmount(aNewTrans.getTotalExemptVatableAmount() - aOldTrans.getTotalExemptVatableAmount());
            CreditDebitTrans.setTransactionRef(aNewTrans.getTransactionNumber());
            CreditDebitTrans.setTransactionComment(CreditOrDebitNote);
            //save trans credit/debit note
            SavedNoteId = this.insertTrans_cr_dr_note(aNewTrans.getStoreId(), TransTypeId, TransReasId, CreditDebitTrans);
            if (SavedNoteId > 0) {
                this.saveTransItems_cr_dr_note(aNewTrans.getStoreId(), TransTypeId, TransReasId, CreditDebitTrans, CreditDebitTransItems, SavedNoteId);
            }
            //save
        } catch (Exception e) {
            System.err.println("saveCreditDebitNote:" + e.getMessage());
        }
        return SavedNoteId;
    }

    public long insertTrans_cr_dr_note(int aStoreId, int aTransTypeId, int aTransReasonId, Trans trans) {
        long InsertedTransId = 0;
        String sql = "{call sp_insert_transaction_cr_dr_note(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
            //save
            cs.executeUpdate();
            InsertedTransId = cs.getLong("out_transaction_id");
        } catch (Exception e) {
            System.err.println("insertTrans_cr_dr_note:" + e.getMessage());
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
            System.err.println("saveTransItems_cr_dr_note:" + e.getMessage());
            //npe.printStackTrace();
        }
    }

    public void saveTransItem_cr_dr_note(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, TransItem transitem) {
        String sql = null;
        if (transitem.getTransactionItemId() == 0) {
            sql = "{call sp_insert_transaction_item_cr_dr_note(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
                //save
                cs.executeUpdate();
            } else if (transitem.getTransactionItemId() > 0) {
                //do nothing; this is for edit
            }
        } catch (Exception e) {
            System.err.println("saveTransItem_cr_dr_note:" + e.getMessage());
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
            System.err.println("getTransItemsByTransactionId_cr_dr_note:" + e.getMessage());
        }
        return tis;
    }

    public int getCountDebitAndCreditNotes(String aTransactionNumber) {
        int n = 0;
        String sql;
        sql = "SELECT COUNT(*) as ncount FROM transaction_cr_dr_note WHERE transaction_ref=?";
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
            System.err.println("getCountDebitAndCreditNotes:" + e.getMessage());
        }
        return n;
    }

}
