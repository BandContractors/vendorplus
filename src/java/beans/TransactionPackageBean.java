package beans;

import static beans.ItemBean.LOGGER;
import static beans.TransBean.LOGGER;
import static beans.TransItemBean.LOGGER;
import static beans.TransactionPackageItemBean.LOGGER;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Item;
import entities.Item_code_other;

import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.TransactionPackage;
import entities.TransactionPackageItem;
import entities.TransactionReason;
import entities.TransactionType;
import entities.Transaction_item_unit;
import entities.Transactor;
import entities.UserDetail;
import entities.UserItemEarn;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
 * @author Hp
 */
@ManagedBean
@SessionScoped
public class TransactionPackageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransactionPackageBean.class.getName());

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private TransactionPackage transactionPackage;
    private List<TransactionPackageItem> aSalePackageItemList;
    private String ActionMessage;
    private String ActionMessageChild;
    private List<TransactionPackage> transactionPackageList;
    private String transactionNumber;

    //create a sale package
    //save/update sale packege
    //get sale packege by id
    //get sale packege by status
    //get sale packege by date
    public TransactionPackage createTransactionPackage(int aStoreId, int aTransTypeId, Trans aTrans, int aTransReasonId, TransactionPackage tPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        tPackage.setAddUserDetailId(aStoreId);
        tPackage.setCurrencyCode(aTrans.getCurrencyCode());
        //tPackage.setGrandTotal(aTrans.getGrandTotal());
        tPackage.setStatusCode(aTrans.getStatus_code());
        tPackage.setTotalTradeDiscount(aTransReasonId);
        tPackage.setTransactionDate(aTrans.getTransactionDate());
        TransactionType transtype = new TransactionTypeBean().getTransactionType(1);
        String NewTransNo = new Trans_number_controlBean().getNewTransNumber(transtype);
        tPackage.setTransactionNumber(NewTransNo);
        //tPackage.setTransactionId();
        tPackage.setTotalTax(aStoreId);
        tPackage.setStatusCode("1");
        tPackage.setStoreId(aStoreId);
        return tPackage;
    }
    
        
    public void addTransItemCallCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, StatusBean aStatusBean, TransactionPackage aTransPackage, List<TransItem> aActiveTransItems, TransItem aTransItem) {

        //get transaction package by transaction number
        aTransItem.setAmount(aTransPackage.getGrandTotal());
        aTransItem.setItemQty(aTransPackage.getaTransactionPackageItemsList().size());
        aTransItem.setDiscount_amount(aTransReasonId);
        aTransItem.setStoreId(aStoreId);
        aTransItem.setTransaction_number(aTransPackage.getTransactionNumber());
        aTransItem.setAddUserDetailName(ActionMessage);
        aTransItem.setTransactionPackageId(aTransPackage.getTransactionPackageId());
        aTransItem.setTransactionPackageNumber(aTransPackage.getTransactionNumber());
        aTransItem.setDescription(aTransPackage.getTransactionNumber());
        aActiveTransItems.add(aTransItem);
    }

    
    public void covertTransactionPackageIntoTransItem(int aStoreId, int aTransTypeId, Trans aTrans, int aTransReasonId, TransItem aTransItem, TransactionPackage tPackage) {

        String sql = "insert"; //insert into transaction item table
        //after, create a transaction

        if (aTransItem.getTransactionItemId() == 0) {
            //sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            sql = "{call sp_insert_transaction_item_out(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (aTransItem.getTransactionItemId() > 0) {
            sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {

            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);
            Store store2 = new StoreBean().getStore(aTrans.getStore2Id());

            if (aTransItem.getTransactionItemId() == 0) {
                //clean batch
                if (aTransItem.getBatchno() == null) {
                    aTransItem.setBatchno("");
                }

                cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
                cs.setLong("in_transaction_id", aTransItem.getTransactionId());
                cs.setLong("in_item_id", aTransItem.getItemId());
                cs.setString("in_batchno", aTransItem.getBatchno());
                try {
                    cs.setDate("in_item_mnf_date", new java.sql.Date(aTransItem.getItemMnfDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_item_mnf_date", null);
                }
                try {
                    cs.setDate("in_item_expiry_date", new java.sql.Date(aTransItem.getItemExpryDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_item_expiry_date", null);
                }
                cs.setDouble("in_item_qty", aTransItem.getItemQty());
                cs.setDouble("in_unit_price", aTransItem.getUnitPrice());
                cs.setDouble("in_unit_trade_discount", aTransItem.getUnitTradeDiscount());
                cs.setDouble("in_unit_vat", aTransItem.getUnitVat());
                cs.setDouble("in_amount", aTransItem.getAmount());
                cs.setString("in_vat_rated", aTransItem.getVatRated());
                cs.setDouble("in_vat_perc", aTransItem.getVatPerc());
                cs.setDouble("in_unit_price_inc_vat", aTransItem.getUnitPriceIncVat());
                cs.setDouble("in_unit_price_exc_vat", aTransItem.getUnitPriceExcVat());
                cs.setDouble("in_amount_inc_vat", aTransItem.getAmountIncVat());
                cs.setDouble("in_amount_exc_vat", aTransItem.getAmountExcVat());

                try {
                    cs.setString("in_code_specific", aTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    cs.setString("in_code_specific", "");
                }
                try {
                    cs.setString("in_desc_specific", aTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    cs.setString("in_desc_specific", "");
                }
                try {
                    cs.setString("in_desc_more", aTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    cs.setString("in_desc_more", "");
                }
                try {
                    cs.setString("in_warranty_desc", aTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    cs.setString("in_warranty_desc", "");
                }
                try {
                    cs.setDate("in_warranty_expiry_date", new java.sql.Date(aTransItem.getWarrantyExpiryDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_warranty_expiry_date", null);
                }
                try {
                    cs.setString("in_account_code", aTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    cs.setString("in_account_code", "");
                }
                try {
                    cs.setDate("in_purchase_date", new java.sql.Date(aTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_purchase_date", null);
                }
                try {
                    cs.setDate("in_dep_start_date", new java.sql.Date(aTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_dep_start_date", null);
                }
                try {
                    cs.setInt("in_dep_method_id", aTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_dep_method_id", 0);
                }
                try {
                    cs.setDouble("in_dep_rate", aTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_dep_rate", 0);
                }
                try {
                    cs.setInt("in_average_method_id", aTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_average_method_id", 0);
                }
                try {
                    cs.setInt("in_effective_life", aTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    cs.setInt("in_effective_life", 0);
                }
                try {
                    cs.setDouble("in_residual_value", aTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_residual_value", 0);
                }
                try {
                    cs.setString("in_narration", aTransItem.getNarration());
                } catch (NullPointerException npe) {
                    cs.setString("in_narration", "");
                }
                try {
                    cs.setDouble("in_qty_balance", aTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_qty_balance", 0);
                }
                try {
                    cs.setDouble("in_duration_value", aTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_duration_value", 0);
                }
                try {
                    cs.setDouble("in_qty_damage", aTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_qty_damage", 0);
                }
                try {
                    cs.setDouble("in_duration_passed", aTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    cs.setDouble("in_duration_passed", 0);
                }
                try {
                    if (aTransItem.getSpecific_size() > 0) {
                        cs.setDouble("in_specific_size", aTransItem.getSpecific_size());
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
                        Transaction_item_unit tiu = new Transaction_item_unit();
                        tiu.setTransaction_item_id(InsertedId1);
                        tiu.setUnit_id(aTransItem.getUnit_id());
                        tiu.setBase_unit_qty(aTransItem.getBase_unit_qty());
                        new TransItemExtBean().insertTransaction_item_unit(tiu);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, e);
                }

            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    //package auto complete
    public List<TransactionPackage> getSalePackageTransNumberForAutoComplete(String transNumber) {
        String sql = "";
        ResultSet rs = null;
        String queryLowerCase = transNumber.toLowerCase();
        this.transactionPackageList = new ArrayList<>();
        sql = "select transaction_number from transaction_package where store_id=1 and transaction_number like'%" + transNumber + "%'";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                // this.transactionPackageList.add(rs.getString("transaction_number"));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        //return transactionNumbersList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
        return transactionPackageList;
    }

    public void setTransactionPackageFromResultset(TransactionPackage transPackage, ResultSet aResultSet) {
        try {
            try {
                transPackage.setTransactionPackageId(aResultSet.getLong("transaction_package_id"));
            } catch (Exception npe) {
                transPackage.setTransactionPackageId(0);
            }
            try {
                transPackage.setTransactionNumber(aResultSet.getString("transaction_number"));
            } catch (Exception npe) {
                transPackage.setTransactionNumber("");
            }
         
            try {
                transPackage.setGrandTotal(aResultSet.getDouble("grand_total"));
            } catch (Exception npe) {
                transPackage.setGrandTotal(0);
            }
            try {
                transPackage.setStoreId(aResultSet.getInt("store_id"));
            } catch (Exception npe) {
                transPackage.setStoreId(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransTotalsAndUpdateCEC(int aTransTypeId, int aTransReasonId, Trans trans, List<TransactionPackageItem> aTransactionPackageItemList) {

    }

    public void saveTransCECcallFromSINew(int aStoreId, int aTransTypeId, int aTransReasonId, Trans trans, TransactionPackage transactionPackage, List<TransactionPackageItem> aTransactionPackageItemList) {

        transactionPackage = createTransactionPackage(aStoreId, aTransTypeId, trans, aTransReasonId, transactionPackage, aTransactionPackageItemList);
        insertTransactionPackage(aStoreId, aTransTypeId, aTransReasonId, transactionPackage, aTransactionPackageItemList);
    }

    public void setTransTotalsAndUpdateCEC(int aTransTypeId, int aTransReasonId, TransactionPackage aTransPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        aTransPackage.setSubTotal(this.getSubTotal(aTransactionPackageItemList));
        aTransPackage.setTotalTradeDiscount(this.getTotalTradeDiscount(aTransPackage, aTransactionPackageItemList));
        aTransPackage.setTotalTax(this.getTotalTax(aTransactionPackageItemList));
        //aTransPackage.setGrandTotal(this.getGrandTotalCEC(aTransTypeId, aTransReasonId, aTransPackage, aTransactionPackageItemList));

        //Customer Display
        String PortName = new Parameter_listBean().getParameter_listByContextNameMemory("CUSTOMER_DISPLAY", "COM_PORT_NAME").getParameter_value();
        String ClientPcName = new GeneralUserSetting().getClientComputerName();
        String SizeStr = new Parameter_listBean().getParameter_listByContextNameMemory("CUSTOMER_DISPLAY", "MAX_CHARACTERS_PER_LINE").getParameter_value();
        int Size = 0;
        if (SizeStr.length() > 0) {
            Size = Integer.parseInt(SizeStr);
        }
        if (PortName.length() > 0 && ClientPcName.length() > 0 && Size > 0 && (aTransTypeId == 2 || aTransTypeId == 11)) {
            UtilityBean ub = new UtilityBean();
            ub.invokeLocalCustomerDisplay(ClientPcName, PortName, Size, ub.formatDoubleToString(aTransPackage.getGrandTotal()), "");
        }
    }

    public double getTotalTradeDiscount(TransactionPackage aTransPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TotTradeDisc = 0;
        while (ListItemIndex < ListItemNo) {
            TotTradeDisc = TotTradeDisc + (ati.get(ListItemIndex).getUnitTradeDiscount() * ati.get(ListItemIndex).getItemQty());
            ListItemIndex = ListItemIndex + 1;
        }
        // TotTradeDisc = (double) new AccCurrencyBean().roundAmount(aTransPackage.getCurrencyCode(), TotTradeDisc);
        return TotTradeDisc;
    }

    public double getTotalTax(List<TransactionPackageItem> aTransactionPackageItemList) {
        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TVat = 0;
        while (ListItemIndex < ListItemNo) {
            TVat = TVat + (ati.get(ListItemIndex).getUnitVat() * ati.get(ListItemIndex).getItemQty());
            //we shall excise tax here on items applicable
            ListItemIndex = ListItemIndex + 1;
        }
        return TVat;
    }

    public double getSubTotal(List<TransactionPackageItem> aTransactionPackageItemList) {
        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double SubT = 0;
        while (ListItemIndex < ListItemNo) {
            SubT = SubT + (ati.get(ListItemIndex).getUnitPrice() * ati.get(ListItemIndex).getItemQty());
            ListItemIndex = ListItemIndex + 1;
        }
        return SubT;
    }

    public double getGrandTotal(List<TransactionPackageItem> aTransactionPackageItemList) {
        double GTotal = 0;

        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        GTotal = 0;
        while (ListItemIndex < ListItemNo) {
            GTotal = GTotal + (ati.get(ListItemIndex).getItemQty() * ati.get(ListItemIndex).getUnitPrice());
            ListItemIndex = ListItemIndex + 1;
        }
        return GTotal;
    }

    public void insertTransactionPackage(int aStoreId, int aTransTypeId, int aTransReasonId, TransactionPackage tPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        long InsertedTransId = 0;
        String sql = "{call sp_insert_transaction_package(?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);

            cs.setLong("in_transactor_id", 0);
            cs.setInt("in_transaction_type_id", aTransTypeId);

            cs.setInt("in_transaction_reason_id", aTransReasonId);
            //cs.setDate("in_transaction_date", new java.sql.Date(tPackage.getTransactionDate().getTime()));
            cs.setInt("in_store_id", store.getStoreId());

            tPackage.setStoreId(store.getStoreId());
            cs.setInt("in_store2_id", tPackage.getStore2Id());
            //cs.setDouble("in_transaction_id", tPackage.getTransactorId());
            cs.setDouble("in_total_tax", tPackage.getTotalTax());
            cs.setInt("in_add_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
            tPackage.setAddUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            cs.setTimestamp("in_add_date", new java.sql.Timestamp(new java.util.Date().getTime()));
            cs.setInt("in_edit_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());//will be made null by the SP
            cs.setTimestamp("in_edit_date", new java.sql.Timestamp(new java.util.Date().getTime()));//will be made null by the SP
            cs.setString("in_transaction_ref", tPackage.getTransactionRef());
            cs.registerOutParameter("out_transaction_package_id", VARCHAR);
            cs.setDouble("in_sub_total", tPackage.getSubTotal());
            cs.setDouble("in_grand_total", tPackage.getGrandTotal());
            cs.setDouble("in_cash_discount", tPackage.getTotalTradeDiscount());
            cs.setDouble("in_total_trade_discount", tPackage.getTotalTradeDiscount());
            cs.setDouble("in_vat_perc", CompanySetting.getVatPerc());
            cs.setString("in_transaction_comment", "");

            //for profit margin
            try {
                if (tPackage.getTransactionUserDetailId() == 0) {
                    tPackage.setTransactionUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                }
            } catch (NullPointerException npe) {
                tPackage.setTransactionUserDetailId(new GeneralUserSetting().getCurrentUser().getUserDetailId());
            }
            cs.setInt("in_transaction_user_detail_id", tPackage.getTransactionUserDetailId());

            try {
                if (tPackage.getTransactionNumber().length() == 0) {
                    String NewTransNo = new Trans_number_controlBean().getNewTransNumber(transtype);
                    int IsNewTransNoUsed = new Trans_number_controlBean().getIsTrans_number_used(transtype.getTransactionTypeId(), NewTransNo);
                    if (IsNewTransNoUsed == 0) {
                        tPackage.setTransactionNumber(NewTransNo);
                        cs.setString("in_transaction_number", tPackage.getTransactionNumber());
                        new Trans_number_controlBean().updateTrans_number_control(transtype);
                    } else {
                        tPackage.setTransactionNumber("");
                        cs.setString("in_transaction_number", tPackage.getTransactionNumber());
                    }
                } else {
                    cs.setString("in_transaction_number", tPackage.getTransactionNumber());
                }
            } catch (NullPointerException npe) {
                cs.setString("in_transaction_number", "");
            }

            try {
                cs.setString("in_currency_code", tPackage.getCurrencyCode());
            } catch (NullPointerException npe) {
                cs.setString("in_currency_code", "");
            }

            //bought in after order module
            try {
                cs.setLong("in_location_id", tPackage.getLocationId());
            } catch (NullPointerException npe) {
                cs.setLong("in_location_id", 0);
            }
            if (null == tPackage.getStatusCode()) {
                cs.setString("in_status_code", "");
            } else {
                cs.setString("in_status_code", tPackage.getStatusCode());
            }
            if (null == tPackage.getStatusDate()) {
                cs.setTimestamp("in_status_date", null);
            } else {
                cs.setTimestamp("in_status_date", new java.sql.Timestamp(tPackage.getStatusDate().getTime()));
            }
            if (null == tPackage.getTransactionDate()) {
                cs.setTimestamp("in_transaction_date", null);
            } else {
                cs.setTimestamp("in_transaction_date", new java.sql.Timestamp(tPackage.getStatusDate().getTime()));
            }

            //save
            cs.executeUpdate();
            InsertedTransId = cs.getLong("out_transaction_package_id");
            tPackage.setTransactionPackageId(InsertedTransId);
            if (InsertedTransId > 1) {
                for (TransactionPackageItem t : aTransactionPackageItemList) {
                    t.setTransactionPackageId(InsertedTransId);
                    new TransactionPackageItemBean().saveTransactionPackageItem(aStoreId, aTransTypeId, aTransReasonId, t);
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            e.printStackTrace();
        }
    }

    /**
     * @return the aSalePackageItemList
     */
    public List<TransactionPackageItem> getaSalePackageItemList() {
        return aSalePackageItemList;
    }

    /**
     * @param aSalePackageItemList the aSalePackageItemList to set
     */
    public void setaSalePackageItemList(List<TransactionPackageItem> aSalePackageItemList) {
        this.aSalePackageItemList = aSalePackageItemList;
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

    public void initClearAllCEC(String aLevel, Trans t, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem ti, Item aSelectedItem, Transactor aSelectedTransactor, int ClearNo, Transactor aSelectedBillTransactor, UserDetail aTransUserDetail, Transactor aSelectedSchemeTransactor) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all  
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            TransactionPackageItemBean tib = new TransactionPackageItemBean();
            ItemBean itmB = new ItemBean();
            TransactorBean trB = new TransactorBean();

            if (ClearNo == 1 || ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
                //clear autoCompletetd item
                itmB.clearSelectedItem();
                itmB.clearItem(aSelectedItem);
                //clear the selcted trans item
                tib.clearTransactionPackageItem(ti);
            }
            if (ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
                //code for clearing customer/supplier/transactor
                trB.clearSelectedTransactor();
                trB.clearTransactor(aSelectedTransactor);
                //code for clearing customer/supplier/transactor
                //trB.clearSelectedBillTransactor();
                trB.clearTransactor(aSelectedBillTransactor);
                trB.clearTransactor(aSelectedSchemeTransactor);
                //clear all the item LIST
                //--//tib.getActiveTransItems().clear();
                aActiveTransItems.clear();

                //clear Trans
                //this.clearTrans(t);
                //clear transaction user / service offered by
                new UserDetailBean().clearUserDetail(aTransUserDetail);

                //clear current trans and pay ids in session
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSession = request.getSession(false);
                if (aLevel.equals("PARENT")) {
                    httpSession.setAttribute("CURRENT_TRANSACTION_ID", 0);
                    httpSession.setAttribute("CURRENT_PAY_ID", 0);
                    //clear action message
                    this.setActionMessage("");
                } else if (aLevel.equals("CHILD")) {
                    httpSession.setAttribute("CURRENT_TRANSACTION_ID_CHILD", 0);
                    httpSession.setAttribute("CURRENT_PAY_ID_CHILD", 0);
                    //clear action message
                    this.setActionMessageChild("");
                }
            }
            //new OutputDetailBean().refreshOutput(aLevel, "");
            new OutputDetailBean().clearOutput(aLevel, "");
        }
    }

    public List<TransactionPackage> getTransactionPackageObjectListForSale(String Query) {
        String sql, sqlDesc = "", sqlCode = "", sqlCodeOther = "";
        //desc
        String[] ArrayDesc = new UtilityBean().getStringArrayFromXSeperatedStr(Query, " ");
        if (menuItemBean.getMenuItemObj().getITEM_FULL_SEARCH_ON() == 1 && ArrayDesc.length > 1) {
            for (String ArrayDesc1 : ArrayDesc) {
                if (sqlDesc.length() == 0) {
                    sqlDesc = " tp.transaction_number LIKE '%" + ArrayDesc1 + "%' ";
                } else {
                    sqlDesc = sqlDesc + " AND tp.transaction_number LIKE '%" + ArrayDesc1 + "%' ";
                }
            }
        } else {
            sqlDesc = " tp.transaction_number LIKE '%" + Query + "%' ";
        }
        sql = "SELECT * FROM transaction_package tp WHERE tp.status_code=1 AND("
                + "(" + sqlDesc + ") "
                + ") ORDER BY tp.transaction_number ASC LIMIT " + menuItemBean.getMenuItemObj().getSEARCH_ITEMS_LIST_LIMIT();
        //System.out.println("SQL:" + sql);
        ResultSet rs = null;
        this.transactionPackageList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                TransactionPackage t = new TransactionPackage();
                this.setTransactionPackageFromResultset(t, rs);
                this.getTransactionPackageList().add(t);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getTransactionPackageList();
    }

    
    public TransactionPackage findTransactionPackage(long packageId) {
        String sql = "{call sp_search_transaction_package_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, packageId);
            rs = ps.executeQuery();
            if (rs.next()) {
                TransactionPackage t = new TransactionPackage();
                this.setTransactionPackageFromResultset(t, rs);
                return t;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
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
     * @return the ActionMessageChild
     */
    public String getActionMessageChild() {
        return ActionMessageChild;
    }

    /**
     * @param ActionMessageChild the ActionMessageChild to set
     */
    public void setActionMessageChild(String ActionMessageChild) {
        this.ActionMessageChild = ActionMessageChild;
    }

    /**
     * @return the transactionNumber
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * @param transactionNumber the transactionNumber to set
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * @return the transactionPackageList
     */
    public List<TransactionPackage> getTransactionPackageList() {
        return transactionPackageList;
    }

    /**
     * @param transactioPackageList the transactionPackageList to set
     */
    public void setTransactionPackageList(List<TransactionPackage> transactioPackageList) {
        this.transactionPackageList = transactioPackageList;
    }
}
