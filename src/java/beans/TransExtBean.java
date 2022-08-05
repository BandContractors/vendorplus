package beans;

import api_tax.efris_bean.InvoiceBean;
import static beans.TransBean.LOGGER;
import static beans.TransItemBean.LOGGER;
import connections.DBConnection;
import entities.AccCoa;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.Item;
import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.TransactionReason;
import entities.TransactionType;
import entities.Transaction_tax_map;
import entities.Transactor;
import entities.UserDetail;
import entities.UserItemEarn;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import static java.sql.Types.VARCHAR;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "transExtBean")
@SessionScoped
public class TransExtBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransExtBean.class.getName());
    private boolean ShowDetail;

    public void saveSalesInvoiceImported(String aLevel, int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans trans, List<TransItem> aActiveTransItems, Transactor aSelectedTransactor, Transactor aSelectedBillTransactor, UserDetail aTransUserDetail, Transactor aSelectedSchemeTransactor, UserDetail aAuthorisedByUserDetail, AccCoa aSelectedAccCoa, Transaction_tax_map aTransaction_tax_map) {
        UtilityBean ub = new UtilityBean();
        int InsertedTransItems = 0;
        //CheckValueBfr = this.checkTrans(0, trans, aActiveTransItems);
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        //Store store = new StoreBean().getStore(aStoreId);
        String ValidationMessage = "";
        //ValidationMessage = this.validateTransCEC(aStoreId, aTransTypeId, aTransReasonId, aSaleType, trans, aActiveTransItems, aSelectedTransactor, aSelectedBillTransactor);
        long payid = 0;
        //-------
        String sql = null;
        String sql2 = null;

        TransItemBean TransItemBean = new TransItemBean();
        if (ValidationMessage.length() > 0) {
            //choose what to do to the message
        } else {
            try {
                this.insertTransCEC(aStoreId, aTransTypeId, aTransReasonId, aSaleType, trans, aActiveTransItems);
                if (trans.getTransactionId() == 0) {
                    //error happened in inserting
                } else {
                    //save trans items
                    //TransItemBean tib = new TransItemBean();
                    InsertedTransItems = this.insertTransItems(aStoreId, aTransTypeId, aTransReasonId, aSaleType, trans, aActiveTransItems, trans.getTransactionId());
                    if (InsertedTransItems == aActiveTransItems.size()) {
                        //pay, stock, journal
                        new TransBean().saveTransOthersThread(trans.getTransactionId(), trans.getPayMethod());
                    }
                    //Update TAX Map
                    if (null != aTransaction_tax_map) {
                        String InvoiceNo = aTransaction_tax_map.getTransaction_number_tax();
                        String VerificationCode = aTransaction_tax_map.getVerification_code_tax();
                        String QrCode = aTransaction_tax_map.getQr_code_tax();
                        if (InvoiceNo.length() > 0) {
                            new Transaction_tax_mapBean().saveTransaction_tax_map(trans.getTransactionId(), InvoiceNo, VerificationCode, QrCode);
                        }
                    }
                    //SMbi API Transactions
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_SMBI_URL").getParameter_value().length() > 0) {
                        new Transaction_smbi_mapBean().insertTransaction_smbi_mapCallThread(trans.getTransactionId(), trans.getTransactionTypeId());
                    }
                    //Insert Work Shift
                    if (new GeneralUserSetting().getCurrentStore().getShift_mode() > 0 && trans.getTransactionTypeId() == 2) {
                        //1. define shift
                        //2. invoke save
                    }
                    TransItemBean = null;

                    //Refresh stock alerts
                    new UtilityBean().refreshAlertsThread();
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public void insertTransCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans trans, List<TransItem> aActiveTransItems) {
        long InsertedTransId = 0;
        String sql = "{call sp_insert_transaction(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
            if (trans.getTransactionTypeId() == 3 || trans.getTransactionTypeId() == 4 || trans.getTransactionTypeId() == 13 || trans.getTransactionTypeId() == 7 || trans.getTransactionTypeId() == 16 || trans.getTransactionTypeId() == 19 || trans.getTransactionTypeId() == 71 || trans.getTransactionTypeId() == 72) {//DISPOSE STOCK, TRANFER & TRANS REQ & UNPACK & JOURNAL ENTRY & EXPENSE ENTRY,STOCK ADJUSTMENT,STOCK CONSUMPTION
                trans.setTransactionReasonId(transreason.getTransactionReasonId());
            }
            cs.setInt("in_transaction_reason_id", trans.getTransactionReasonId());
            cs.setDouble("in_cash_discount", trans.getCashDiscount());
            cs.setDouble("in_total_vat", trans.getTotalVat());
            cs.setString("in_transaction_comment", trans.getTransactionComment());
            cs.setInt("in_add_user_detail_id", trans.getAddUserDetailId());
            trans.setAddUserDetailId(trans.getAddUserDetailId());
            cs.setTimestamp("in_add_date", new java.sql.Timestamp(new java.util.Date().getTime()));
            cs.setInt("in_edit_user_detail_id", trans.getAddUserDetailId());//will be made null by the SP
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
            cs.setDouble("in_vat_perc", trans.getVatPerc());
            cs.setDouble("in_amount_tendered", trans.getAmountTendered());
            cs.setDouble("in_change_amount", trans.getChangeAmount());
            cs.setString("in_is_cash_discount_vat_liable", "No");
            cs.setDouble("in_total_profit_margin", trans.getTotalProfitMargin());
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
                AccCurrency LocalCurrency = null;
                LocalCurrency = new AccCurrencyBean().getLocalCurrency();
                trans.setXrate(new AccXrateBean().getXrate(trans.getCurrencyCode(), LocalCurrency.getCurrencyCode()));
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
            //save
            cs.executeUpdate();
            InsertedTransId = cs.getLong("out_transaction_id");
            trans.setTransactionId(InsertedTransId);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertTransItems(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, List<TransItem> aActiveTransItems, long TransactionId) {
        int insertedItems = 0;
        try {
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            int inserted = 0;
            while (ListItemIndex < ListItemNo) {
                inserted = 0;
                ati.get(ListItemIndex).setTransactionId(TransactionId);
                if (aTransTypeId == 67 && ati.get(ListItemIndex).getItemQty() <= 0 && ati.get(ListItemIndex).getQty_damage() <= 0) {
                    //do nothing
                } else {
                    inserted = this.insertTransItem(aStoreId, aTransTypeId, aTransReasonId, aSaleType, aTrans, ati.get(ListItemIndex));
                }
                insertedItems = insertedItems + inserted;
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return insertedItems;
    }

    public int insertTransItem(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, TransItem transitem) {
        int inserted = 0;
        String sql = null;
        String msg = "";
        sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {

            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            //clean batch
            if (transitem.getBatchno() == null) {
                transitem.setBatchno("");
            }

            cs.setString("in_is_trade_discount_vat_liable", "No");
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
            if ("SALE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE INVOICE".equals(transtype.getTransactionTypeName())) {
                cs.setString("in_stock_effect", "D");
            } else if ("HIRE RETURN NOTE".equals(transtype.getTransactionTypeName())) {
                cs.setString("in_stock_effect", "C");
            } else {
                cs.setString("in_stock_effect", "");
            }
            //for profit margin
            cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
            cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
            //for user earning
            if ("HIRE INVOICE".equals(transtype.getTransactionTypeName()) || ("SALE INVOICE".equals(transtype.getTransactionTypeName()) && ("RETAIL SALE INVOICE".equals(aSaleType) || "WHOLE SALE INVOICE".equals(aSaleType)))) {
                UserItemEarn bUserItemEarn = new UserItemEarn();
                Item bItem = new Item();
                UserDetail bUserDetail = new UserDetail();
                int bTransTypeId, bTransReasId, bItemCatId, bItemSubCatId, bUserCatId;
                bTransTypeId = transtype.getTransactionTypeId();
                bTransReasId = transreason.getTransactionReasonId();
                bItem = new ItemBean().getItem(transitem.getItemId());
                bItemCatId = bItem.getCategoryId();
                bUserDetail = new UserDetailBean().getUserDetail(aTrans.getTransactionUserDetailId());
                //bUserCatId = bUserDetail.getUserCategoryId();
                try {
                    bUserCatId = bUserDetail.getUserCategoryId();
                } catch (NullPointerException npe) {
                    bUserCatId = 0;
                }
                try {
                    bItemSubCatId = bItem.getSubCategoryId();
                } catch (NullPointerException npe) {
                    bItemSubCatId = 0;
                }
                try {
                    bUserItemEarn = new UserItemEarnBean().getUserItemEarnByTtypeTreasIcatIsubcatUcat(bTransTypeId, bTransReasId, bItemCatId, bItemSubCatId, bUserCatId);
                } catch (NullPointerException npe) {
                    bUserItemEarn = null;
                }
                if (null != bUserItemEarn) {
                    cs.setDouble("in_earn_perc", bUserItemEarn.getEarnPerc());
                    cs.setDouble("in_earn_amount", (double) (bUserItemEarn.getEarnPerc() * transitem.getAmountIncVat() * 0.01));
                } else {
                    cs.setDouble("in_earn_perc", 0);
                    cs.setDouble("in_earn_amount", 0);
                }
            } else {
                cs.setDouble("in_earn_perc", 0);
                cs.setDouble("in_earn_amount", 0);
            }
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
            //repeat for the unpacked ones
            if ("UNPACK".equals(transtype.getTransactionTypeName())) {
                cs.setLong("in_item_id", transitem.getItemId2());
                cs.setDouble("in_item_qty", transitem.getItemQty2());
                cs.setString("in_stock_effect", "C");
                cs.executeUpdate();
            }
            inserted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.INFO, Arrays.toString(e.getStackTrace()));
            LOGGER.log(Level.ERROR, e);
        }
        return inserted;
    }

    public void reverseShowDetail() {
        if (this.ShowDetail) {
            this.ShowDetail = false;
        } else {
            this.ShowDetail = true;
        }
    }

    public void resetShowDetail(int aValue) {
        if (aValue == 1) {
            this.ShowDetail = true;
        } else {
            this.ShowDetail = false;
        }
    }

    public void reverseOverridePrices(TransItem aTransItem) {
        if (aTransItem.isOverridePrices()) {
            aTransItem.setOverridePrices(false);
        } else {
            aTransItem.setOverridePrices(true);
        }
    }

    /**
     * @return the ShowDetail
     */
    public boolean isShowDetail() {
        return ShowDetail;
    }

    /**
     * @param ShowDetail the ShowDetail to set
     */
    public void setShowDetail(boolean ShowDetail) {
        this.ShowDetail = ShowDetail;
    }

}
