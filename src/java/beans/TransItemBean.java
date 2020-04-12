package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.AccCoa;
import entities.AccCurrency;
import entities.Category;
import entities.Stock;
import entities.CompanySetting;
import entities.TransItem;
import entities.Item;
import entities.UserItemEarn;
import entities.TransactionType;
import entities.UserDetail;
import entities.ItemMap;
import entities.DiscountPackageItem;
import entities.Stock_out;
import entities.Store;
import entities.SubCategory;
import entities.Trans;
import entities.TransactionReason;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
public class TransItemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TransItem> TransItems;
    private String ActionMessage = null;
    private TransItem SelectedTransItem = null;
    private int SelectedTransactionItemId;
    private String SearchTransItem = "";
    private List<TransItem> ActiveTransItems = new ArrayList<TransItem>();
    List<TransItem> ReportTransItem = new ArrayList<TransItem>();
    private String ItemString = "";
    private AccCoa SelectedAccCoa = null;
    private int total_items;
    private List<Integer> total_items_list;
    private List<TransItem> ActiveTransItemsChild = new ArrayList<TransItem>();
    private List<Category> CategoryList;
    private List<SubCategory> SubCategoryList;
    private List<Item> ItemList;
    private String CategoryName;
    private String SubCategoryName;

    public void specifySize(TransItem aTransItem) {
        if (null != aTransItem) {
            Item item = new ItemBean().getItem(aTransItem.getItemId());
            int SizeToSpecificName = item.getSize_to_specific_name();
            String UnitSym = new UnitBean().getUnit(item.getUnitId()).getUnitSymbol();
            aTransItem.setItemQty(aTransItem.getSpecific_size() * aTransItem.getSpecific_size_qty());
            aTransItem.setAmount(aTransItem.getItemQty() * aTransItem.getUnitPrice());
            if (SizeToSpecificName == 1 && item.getIsGeneral() == 1) {
                //aTransItem.setDescSpecific(new UtilityBean().formatDoubleToString(aTransItem.getSpecific_size_qty()) + " PCs of " + new UtilityBean().formatDoubleToString(aTransItem.getSpecific_size()) + " " + UnitSym);
                aTransItem.setDescSpecific(new UtilityBean().formatDoubleToString(aTransItem.getSpecific_size()) + "" + UnitSym + " per PC");
            }
        }
    }

    public String getSpecificSizeQtyFromQtyAndSizeStr(TransItem aTransItem) {
        String outpstr = "";
        double outp = 1.0;
        double qty = 0;
        double sze = 1;
        if (null != aTransItem) {
            qty = aTransItem.getItemQty();
            sze = aTransItem.getSpecific_size();
            if (sze > 0) {
                outp = qty / sze;
            }
        }
        if (sze == 1) {
            //do nothng
        } else {
            outpstr = " (" + new UtilityBean().formatDoubleToString(outp) + "PCs)";
        }
        return outpstr;
    }

    public double getSpecificSizeQtyFromQtyAndSize(TransItem aTransItem) {
        double outp = 1.0;
        if (null != aTransItem) {
            double qty = aTransItem.getItemQty();
            double sze = aTransItem.getSpecific_size();
            if (sze > 0) {
                outp = qty / sze;
            }
        }
        return outp;
    }

    public void setSpecificSizeQtyFromQtyAndSize(TransItem aTransItem) {
        double outp = 1.0;
        if (null != aTransItem) {
            double qty = aTransItem.getItemQty();
            double sze = aTransItem.getSpecific_size();
            if (sze > 0) {
                outp = qty / sze;
            }
            aTransItem.setSpecific_size_qty(outp);
        }
    }

    public void saveTransItems(Trans aTrans, List<TransItem> aActiveTransItems, long TransactionId) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            ati.get(ListItemIndex).setTransactionId(TransactionId);
            this.saveTransItem(aTrans, ati.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void saveTransItemsCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, List<TransItem> aActiveTransItems, long TransactionId) {
        try {
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            while (ListItemIndex < ListItemNo) {
                ati.get(ListItemIndex).setTransactionId(TransactionId);
                if (aTransTypeId == 67 && ati.get(ListItemIndex).getItemQty() <= 0 && ati.get(ListItemIndex).getQty_damage() <= 0) {
                    //do nothing
                } else {
                    this.saveTransItemCEC(aStoreId, aTransTypeId, aTransReasonId, aSaleType, aTrans, ati.get(ListItemIndex));
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void saveTransItemsJournalEntry(Trans aTrans, List<TransItem> aActiveTransItems, long TransactionId) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            ati.get(ListItemIndex).setTransactionId(TransactionId);
            this.saveTransItemJournalEntry(aTrans, ati.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void saveTransItemsCashTransfer(Trans aTrans, List<TransItem> aActiveTransItems, long TransactionId) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            ati.get(ListItemIndex).setTransactionId(TransactionId);
            this.saveTransItemCashTransfer(aTrans, ati.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void saveTransItemsCashAdjustment(Trans aTrans, List<TransItem> aActiveTransItems, long TransactionId) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            ati.get(ListItemIndex).setTransactionId(TransactionId);
            this.saveTransItemCashAdjustment(aTrans, ati.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void saveTransItem(Trans aTrans, TransItem transitem) {
        String sql = null;
        String sql2 = null;
        String msg = "";

        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        TransactionType TransType = new TransactionType();
        StockBean StkBean = new StockBean();

        if (1 == 2) {
        } else {

            if (transitem.getTransactionItemId() == 0) {
                sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            } else if (transitem.getTransactionItemId() > 0) {
                sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (transitem.getTransactionItemId() == 0) {
                    //clean batch
                    if (transitem.getBatchno() == null) {
                        transitem.setBatchno("");
                    }

                    cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
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
                        cs.setDate("in_item_mnf_date", null);
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
                    if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "HIRE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setString("in_stock_effect", "D");
                    } else if ("PURCHASE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setString("in_stock_effect", "C");
                    } else if ("EXPENSE ENTRY".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setString("in_stock_effect", "C");
                    } else if ("TRANSFER".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setString("in_stock_effect", "B");
                    } else if ("DISPOSE STOCK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setString("in_stock_effect", "D");
                    } else if ("UNPACK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setString("in_stock_effect", "D");
                    } else if ("ITEM RECEIVED".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "HIRE RETURN NOTE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setString("in_stock_effect", "C");
                    } else {
                        cs.setString("in_stock_effect", "");
                    }
                    //for profit margin
                    cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
                    cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
                    //for user earning
                    if ("HIRE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) && ("RETAIL SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType()) || "WHOLE SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())))) {
                        UserItemEarn bUserItemEarn = new UserItemEarn();
                        Item bItem = new Item();
                        UserDetail bUserDetail = new UserDetail();
                        int bTransTypeId, bTransReasId, bItemCatId, bItemSubCatId, bUserCatId;
                        bTransTypeId = new GeneralUserSetting().getCurrentTransactionTypeId();
                        bTransReasId = new GeneralUserSetting().getCurrentTransactionReasonId();
                        bItem = new ItemBean().getItem(transitem.getItemId());
                        bItemCatId = bItem.getCategoryId();
                        bUserDetail = new UserDetailBean().getUserDetail(aTrans.getTransactionUserDetailId());
                        bUserCatId = bUserDetail.getUserCategoryId();
                        try {
                            bItemSubCatId = bItem.getSubCategoryId();
                        } catch (NullPointerException npe) {
                            bItemSubCatId = 0;
                        }
                        try {
                            //System.out.println(bTransTypeId + "," + bTransReasId + "," + bItemCatId + "," + bItemSubCatId + "," + bUserCatId);
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
                    if ("UNPACK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        cs.setLong("in_item_id", transitem.getItemId2());
                        cs.setDouble("in_item_qty", transitem.getItemQty2());
                        cs.setString("in_stock_effect", "C");
                        cs.executeUpdate();
                    }

                    //update stock
                    TransType = TransTypeBean.getTransactionType(new GeneralUserSetting().getCurrentTransactionTypeId());
                    if (new ItemBean().getItem(transitem.getItemId()).getIsTrack() == 1) {
                        if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "DISPOSE STOCK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                            Stock stock = new Stock();
                            int i = 0;
                            stock.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                            stock.setItemId(transitem.getItemId());
                            stock.setBatchno(transitem.getBatchno());
                            stock.setCodeSpecific(transitem.getCodeSpecific());
                            stock.setDescSpecific(transitem.getDescSpecific());
                            i = new StockBean().subtractStock(stock, transitem.getItemQty());
                            stock.setSpecific_size(transitem.getSpecific_size());
                            new Stock_ledgerBean().callInsertStock_ledger("Subtract", stock, transitem.getItemQty(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        }
                        if ("HIRE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                            Stock_out sout = new Stock_out();
                            int i = 0;
                            sout.setStore_id(new GeneralUserSetting().getCurrentStore().getStoreId());
                            sout.setItem_id(transitem.getItemId());
                            sout.setBatchno(transitem.getBatchno());
                            sout.setCode_specific(transitem.getCodeSpecific());
                            sout.setDesc_specific(transitem.getDescSpecific());
                            sout.setQty_out(transitem.getItemQty());
                            sout.setTransactor_id(aTrans.getTransactorId());
                            sout.setSite_id(aTrans.getSite_id());
                            sout.setTransaction_id(aTrans.getTransactionId());
                            i = new Stock_outBean().InsertOrUpdateStock_out(sout, "Add");
                        }
                        if ("HIRE RETURN NOTE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                            Stock_out sout = new Stock_out();
                            int i = 0;
                            sout.setStore_id(new GeneralUserSetting().getCurrentStore().getStoreId());
                            sout.setItem_id(transitem.getItemId());
                            sout.setBatchno(transitem.getBatchno());
                            sout.setCode_specific(transitem.getCodeSpecific());
                            sout.setDesc_specific(transitem.getDescSpecific());
                            sout.setQty_out(transitem.getItemQty() + transitem.getQty_damage());
                            sout.setTransactor_id(aTrans.getTransactorId());
                            sout.setSite_id(aTrans.getSite_id());
                            Trans aRefTrans = null;
                            try {
                                aRefTrans = new TransBean().getTransByTransNumber(aTrans.getTransactionRef());
                                sout.setTransaction_id(aRefTrans.getTransactionId());
                            } catch (NullPointerException npe) {
                                sout.setTransaction_id(0);
                            }
                            if (sout.getTransaction_id() > 0) {
                                i = new Stock_outBean().UpdateNoInsertStock_out(sout, "Subtract");
                            }
                            //update if any damage/lost item has been returned
                            if (i == 1 && transitem.getQty_damage() > 0) {
                                Stock stock2 = new Stock();
                                int i2 = 0;
                                stock2.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                                stock2.setItemId(transitem.getItemId());
                                stock2.setBatchno(transitem.getBatchno());
                                stock2.setCodeSpecific(transitem.getCodeSpecific());
                                stock2.setDescSpecific(transitem.getDescSpecific());
                                i2 = new StockBean().updateStockDamage(stock2, transitem.getQty_damage(), "Add");
                            }
                        }
                        if ("ITEM RECEIVED".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || (new GeneralUserSetting().getCurrentTransactionTypeId() == 1 && new GeneralUserSetting().getCurrentTransactionReasonId() == 29)) {
                            double UnitCostPrice = 0;
                            if (StkBean.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), transitem.getItemId(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific()) != null) {
                                //update/add
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                                stock.setItemId(transitem.getItemId());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                UnitCostPrice = 0;
                                //the asset interface uses unit_price for cost price
                                if (new GeneralUserSetting().getCurrentTransactionReasonId() == 29) {
                                    UnitCostPrice = transitem.getUnitPrice();
                                } else {
                                    UnitCostPrice = transitem.getUnitCostPrice();
                                }
                                stock.setUnitCost(UnitCostPrice);
                                i = new StockBean().addStock(stock, transitem.getItemQty());
                                stock.setSpecific_size(transitem.getSpecific_size());
                                new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            } else {
                                //insert
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                                stock.setItemId(transitem.getItemId());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                stock.setDescMore(transitem.getDescMore());
                                stock.setCurrentqty(transitem.getItemQty());
                                stock.setItemMnfDate(transitem.getItemMnfDate());
                                stock.setItemExpDate(transitem.getItemExpryDate());
                                UnitCostPrice = 0;
                                if (new GeneralUserSetting().getCurrentTransactionReasonId() == 29) {
                                    UnitCostPrice = transitem.getUnitPrice();
                                } else {
                                    UnitCostPrice = transitem.getUnitCostPrice();
                                }
                                stock.setUnitCost(UnitCostPrice);
                                stock.setWarrantyDesc(transitem.getWarrantyDesc());
                                stock.setWarrantyExpiryDate(transitem.getWarrantyExpiryDate());
                                stock.setPurchaseDate(transitem.getPurchaseDate());
                                stock.setDepStartDate(transitem.getDepStartDate());
                                stock.setDepMethodId(transitem.getDepMethodId());
                                stock.setDepRate(transitem.getDepRate());
                                stock.setAverageMethodId(transitem.getAverageMethodId());
                                stock.setEffectiveLife(transitem.getEffectiveLife());
                                stock.setAccountCode(transitem.getAccountCode());
                                stock.setResidualValue(transitem.getResidualValue());
                                stock.setAssetStatusId(1);
                                stock.setAssetStatusDesc("");
                                stock.setSpecific_size(transitem.getSpecific_size());
                                i = new StockBean().saveStock(stock);
                                new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            }
                        }

                        //TRANSFER - 1. Subtract stock from the source store
                        double FromUnitCost = 0;
                        if ("TRANSFER".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                            Stock stock = new Stock();
                            int i = 0;
                            stock.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                            stock.setItemId(transitem.getItemId());
                            stock.setBatchno(transitem.getBatchno());
                            stock.setCodeSpecific(transitem.getCodeSpecific());
                            stock.setDescSpecific(transitem.getDescSpecific());
                            i = new StockBean().subtractStock(stock, transitem.getItemQty());
                            try {
                                FromUnitCost = new StockBean().getStock(stock.getStoreId(), stock.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific()).getUnitCost();
                            } catch (NullPointerException npe) {

                            }
                            stock.setSpecific_size(transitem.getSpecific_size());
                            new Stock_ledgerBean().callInsertStock_ledger("Subtract", stock, transitem.getItemQty(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        }
                        //TRANSFER - 2. Add/Insert stock to the destination store
                        if ("TRANSFER".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                            if (StkBean.getStock(new GeneralUserSetting().getCurrentStore2Id(), transitem.getItemId(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific()) != null) {
                                //update/add
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(new GeneralUserSetting().getCurrentStore2Id());
                                stock.setItemId(transitem.getItemId());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                i = new StockBean().addStock(stock, transitem.getItemQty());
                                stock.setSpecific_size(transitem.getSpecific_size());
                                new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            } else {
                                //insert
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(new GeneralUserSetting().getCurrentStore2Id());
                                stock.setItemId(transitem.getItemId());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                stock.setCurrentqty(transitem.getItemQty());
                                stock.setDescMore(transitem.getDescMore());
                                stock.setCurrentqty(transitem.getItemQty());
                                stock.setItemMnfDate(transitem.getItemMnfDate());
                                stock.setItemExpDate(transitem.getItemExpryDate());
                                //get cost price from the mother store
                                stock.setUnitCost(FromUnitCost);

                                stock.setWarrantyDesc(transitem.getWarrantyDesc());
                                stock.setWarrantyExpiryDate(transitem.getWarrantyExpiryDate());
                                stock.setPurchaseDate(transitem.getPurchaseDate());
                                stock.setDepStartDate(transitem.getDepStartDate());
                                stock.setDepMethodId(transitem.getDepMethodId());
                                stock.setDepRate(transitem.getDepRate());
                                stock.setAverageMethodId(transitem.getAverageMethodId());
                                stock.setEffectiveLife(transitem.getEffectiveLife());
                                stock.setAccountCode(transitem.getAccountCode());
                                stock.setResidualValue(transitem.getResidualValue());
                                stock.setAssetStatusId(1);
                                stock.setAssetStatusDesc("");
                                stock.setSpecific_size(transitem.getSpecific_size());
                                i = new StockBean().saveStock(stock);
                                new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            }
                        }
                        //UNPACK - 1. Subtract stock from the source BigItem
                        if ("UNPACK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                            Stock stock = new Stock();
                            int i = 0;
                            stock.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                            stock.setItemId(transitem.getItemId());
                            stock.setBatchno(transitem.getBatchno());
                            stock.setCodeSpecific(transitem.getCodeSpecific());
                            stock.setDescSpecific(transitem.getDescSpecific());
                            i = new StockBean().subtractStock(stock, transitem.getItemQty());
                            stock.setSpecific_size(transitem.getSpecific_size());
                            new Stock_ledgerBean().callInsertStock_ledger("Subtract", stock, transitem.getItemQty(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        }
                        //UNPACK - 2. Add/Insert stock to the destination small item
                        if ("UNPACK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                            if (StkBean.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), transitem.getItemId2(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific()) != null) {
                                //update/add
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                                stock.setItemId(transitem.getItemId2());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                i = new StockBean().addStock(stock, transitem.getItemQty2());
                                stock.setSpecific_size(transitem.getSpecific_size());
                                new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty2(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            } else {
                                //insert
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                                stock.setItemId(transitem.getItemId2());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                stock.setCurrentqty(transitem.getItemQty2());
                                //temp fix -- start
                                Item aItem = new ItemBean().getItem(transitem.getItemId2());
                                stock.setDescMore("");
                                if (aItem.getItemType().equals("PRODUCT")) {
                                    stock.setAccountCode("5-10-000-010");
                                } else if (aItem.getItemType().equals("SERVICE")) {
                                    stock.setAccountCode("5-10-000-020");
                                }
                                stock.setAssetStatusId(1);
                                stock.setAssetStatusDesc("");
                                //temp fix -- end
                                //get the last unit cost price
                                long LatestTransItemId = 0;
                                double LatestTransItemUnitCostPrice = 0;
                                LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(9, 13, stock.getStoreId(), stock.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific());
                                if (LatestTransItemId > 0) {
                                    try {
                                        LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                                    } catch (NullPointerException npe) {
                                        LatestTransItemUnitCostPrice = 0;
                                    }
                                }
                                //incase the small item has never been supplied; try to get the the latest unit cost for the bigger item and divide
                                if (LatestTransItemUnitCostPrice <= 0) {
                                    long LatestTransItemIdBig = 0;
                                    double LatestTransItemUnitCostPriceBig = 0;
                                    double FractionQty = 0;
                                    LatestTransItemIdBig = this.getItemUnitCostPriceLatestTransItemId(9, 13, stock.getStoreId(), transitem.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific());
                                    if (LatestTransItemIdBig > 0) {
                                        //get fraction qty
                                        try {
                                            FractionQty = new ItemMapBean().getItemMapByBigItemId(transitem.getItemId()).getFractionQty();
                                        } catch (NullPointerException npe) {
                                            FractionQty = 0;
                                        }
                                        try {
                                            if (FractionQty > 0) {
                                                LatestTransItemUnitCostPriceBig = this.getTransItem(LatestTransItemIdBig).getUnitCostPrice() / FractionQty;
                                            }
                                        } catch (NullPointerException npe) {
                                            LatestTransItemUnitCostPriceBig = 0;
                                        }
                                    }
                                    if (LatestTransItemUnitCostPriceBig > 0) {
                                        LatestTransItemUnitCostPrice = LatestTransItemUnitCostPriceBig;
                                    }
                                }

                                if (LatestTransItemUnitCostPrice > 0) {
                                    stock.setUnitCost(LatestTransItemUnitCostPrice);
                                } else {
                                    stock.setUnitCost(transitem.getUnitCostPrice());
                                }

                                try {
                                    stock.setItemMnfDate(transitem.getItemMnfDate());
                                } catch (NullPointerException npe) {
                                    stock.setItemMnfDate(null);
                                }
                                try {
                                    stock.setItemExpDate(transitem.getItemExpryDate());
                                } catch (NullPointerException npe) {
                                    stock.setItemExpDate(null);
                                }
                                stock.setSpecific_size(transitem.getSpecific_size());
                                i = new StockBean().saveStock(stock);
                                new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty2(), "Add", new GeneralUserSetting().getCurrentTransactionTypeId(), transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            }
                        }
                    }

                    TransType = null;
                    TransTypeBean = null;
                    StkBean = null;

                } else if (transitem.getTransactionItemId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("TransItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransItem NOT saved!"));
            }
        }
    }

    public void saveTransItemCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, TransItem transitem) {
        String sql = null;
        String msg = "";

        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        StockBean StkBean = new StockBean();

        if (1 == 2) {
        } else {
            if (transitem.getTransactionItemId() == 0) {
                sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            } else if (transitem.getTransactionItemId() > 0) {
                sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {

                TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
                TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
                Store store = new StoreBean().getStore(aStoreId);
                Store store2 = new StoreBean().getStore(aTrans.getStore2Id());

                if (transitem.getTransactionItemId() == 0) {
                    //clean batch
                    if (transitem.getBatchno() == null) {
                        transitem.setBatchno("");
                    }

                    cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
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
                    } else if ("PURCHASE INVOICE".equals(transtype.getTransactionTypeName())) {
                        cs.setString("in_stock_effect", "C");
                    } else if ("EXPENSE ENTRY".equals(transtype.getTransactionTypeName())) {
                        cs.setString("in_stock_effect", "C");
                    } else if ("TRANSFER".equals(transtype.getTransactionTypeName())) {
                        cs.setString("in_stock_effect", "B");
                    } else if ("DISPOSE STOCK".equals(transtype.getTransactionTypeName())) {
                        cs.setString("in_stock_effect", "D");
                    } else if ("UNPACK".equals(transtype.getTransactionTypeName())) {
                        cs.setString("in_stock_effect", "D");
                    } else if ("ITEM RECEIVED".equals(transtype.getTransactionTypeName()) || "HIRE RETURN NOTE".equals(transtype.getTransactionTypeName())) {
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
                        bUserCatId = bUserDetail.getUserCategoryId();
                        try {
                            bItemSubCatId = bItem.getSubCategoryId();
                        } catch (NullPointerException npe) {
                            bItemSubCatId = 0;
                        }
                        try {
                            //System.out.println(bTransTypeId + "," + bTransReasId + "," + bItemCatId + "," + bItemSubCatId + "," + bUserCatId);
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

                    //update stock
                    if (("SALE INVOICE".equals(transtype.getTransactionTypeName()) && !new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("0")) || ("GOODS DELIVERY".equals(transtype.getTransactionTypeName()) && !new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("1"))) {
                        //ingore stock update
                    } else {
                        if (new ItemBean().getItem(transitem.getItemId()).getIsTrack() == 1) {
                            if ("SALE INVOICE".equals(transtype.getTransactionTypeName()) || "DISPOSE STOCK".equals(transtype.getTransactionTypeName()) || ("STOCK ADJUSTMENT".equals(transtype.getTransactionTypeName()) && transitem.getNarration().equals("Subtract")) || "STOCK CONSUMPTION".equals(transtype.getTransactionTypeName()) || "GOODS DELIVERY".equals(transtype.getTransactionTypeName())) {
                                Stock stock = new Stock();
                                int i = 0;
                                //this for purpose of orders made to store2 from store 1; 
                                //inventory to be deducted from store 2 instead
                                int Store2Id = 0;
                                try {
                                    Store2Id = store2.getStoreId();
                                } catch (Exception e) {
                                    Store2Id = 0;
                                }
                                if (Store2Id > 0) {
                                    stock.setStoreId(store2.getStoreId());
                                } else {
                                    stock.setStoreId(store.getStoreId());
                                }
                                stock.setItemId(transitem.getItemId());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                i = new StockBean().subtractStock(stock, transitem.getItemQty());
                                stock.setSpecific_size(transitem.getSpecific_size());
                                new Stock_ledgerBean().callInsertStock_ledger("Subtract", stock, transitem.getItemQty(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            }
                            if ("HIRE INVOICE".equals(transtype.getTransactionTypeName())) {
                                Stock_out sout = new Stock_out();
                                int i = 0;
                                sout.setStore_id(store.getStoreId());
                                sout.setItem_id(transitem.getItemId());
                                sout.setBatchno(transitem.getBatchno());
                                sout.setCode_specific(transitem.getCodeSpecific());
                                sout.setDesc_specific(transitem.getDescSpecific());
                                sout.setQty_out(transitem.getItemQty());
                                sout.setTransactor_id(aTrans.getTransactorId());
                                sout.setSite_id(aTrans.getSite_id());
                                sout.setTransaction_id(aTrans.getTransactionId());
                                i = new Stock_outBean().InsertOrUpdateStock_out(sout, "Add");
                            }
                            if ("HIRE RETURN NOTE".equals(transtype.getTransactionTypeName())) {
                                Stock_out sout = new Stock_out();
                                int i = 0;
                                sout.setStore_id(store.getStoreId());
                                sout.setItem_id(transitem.getItemId());
                                sout.setBatchno(transitem.getBatchno());
                                sout.setCode_specific(transitem.getCodeSpecific());
                                sout.setDesc_specific(transitem.getDescSpecific());
                                sout.setQty_out(transitem.getItemQty() + transitem.getQty_damage());
                                sout.setTransactor_id(aTrans.getTransactorId());
                                sout.setSite_id(aTrans.getSite_id());
                                Trans aRefTrans = null;
                                try {
                                    aRefTrans = new TransBean().getTransByTransNumber(aTrans.getTransactionRef());
                                    sout.setTransaction_id(aRefTrans.getTransactionId());
                                } catch (NullPointerException npe) {
                                    sout.setTransaction_id(0);
                                }
                                if (sout.getTransaction_id() > 0) {
                                    i = new Stock_outBean().UpdateNoInsertStock_out(sout, "Subtract");
                                }
                                //update if any damage/lost item has been returned
                                if (i == 1 && transitem.getQty_damage() > 0) {
                                    Stock stock2 = new Stock();
                                    int i2 = 0;
                                    stock2.setStoreId(store.getStoreId());
                                    stock2.setItemId(transitem.getItemId());
                                    stock2.setBatchno(transitem.getBatchno());
                                    stock2.setCodeSpecific(transitem.getCodeSpecific());
                                    stock2.setDescSpecific(transitem.getDescSpecific());
                                    i2 = new StockBean().updateStockDamage(stock2, transitem.getQty_damage(), "Add");
                                }
                            }
                            if ("ITEM RECEIVED".equals(transtype.getTransactionTypeName()) || (transtype.getTransactionTypeId() == 1 && transreason.getTransactionReasonId() == 29) || ("STOCK ADJUSTMENT".equals(transtype.getTransactionTypeName()) && transitem.getNarration().equals("Add"))) {
                                double UnitCostPrice = 0;
                                if (StkBean.getStock(store.getStoreId(), transitem.getItemId(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific()) != null) {
                                    //update/add
                                    Stock stock = new Stock();
                                    int i = 0;
                                    stock.setStoreId(store.getStoreId());
                                    stock.setItemId(transitem.getItemId());
                                    stock.setBatchno(transitem.getBatchno());
                                    stock.setCodeSpecific(transitem.getCodeSpecific());
                                    stock.setDescSpecific(transitem.getDescSpecific());
                                    UnitCostPrice = 0;
                                    //the asset interface uses unit_price for cost price
                                    if (transreason.getTransactionReasonId() == 29) {
                                        UnitCostPrice = transitem.getUnitPrice();
                                    } else {
                                        UnitCostPrice = transitem.getUnitCostPrice();
                                    }
                                    stock.setUnitCost(UnitCostPrice);
                                    i = new StockBean().addStock(stock, transitem.getItemQty());
                                    stock.setSpecific_size(transitem.getSpecific_size());
                                    new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                                } else {
                                    //insert
                                    Stock stock = new Stock();
                                    int i = 0;
                                    stock.setStoreId(store.getStoreId());
                                    stock.setItemId(transitem.getItemId());
                                    stock.setBatchno(transitem.getBatchno());
                                    stock.setCodeSpecific(transitem.getCodeSpecific());
                                    stock.setDescSpecific(transitem.getDescSpecific());
                                    stock.setDescMore(transitem.getDescMore());
                                    stock.setCurrentqty(transitem.getItemQty());
                                    stock.setItemMnfDate(transitem.getItemMnfDate());
                                    stock.setItemExpDate(transitem.getItemExpryDate());
                                    UnitCostPrice = 0;
                                    if (transreason.getTransactionReasonId() == 29) {
                                        UnitCostPrice = transitem.getUnitPrice();
                                    } else {
                                        UnitCostPrice = transitem.getUnitCostPrice();
                                    }
                                    stock.setUnitCost(UnitCostPrice);
                                    stock.setWarrantyDesc(transitem.getWarrantyDesc());
                                    stock.setWarrantyExpiryDate(transitem.getWarrantyExpiryDate());
                                    stock.setPurchaseDate(transitem.getPurchaseDate());
                                    stock.setDepStartDate(transitem.getDepStartDate());
                                    stock.setDepMethodId(transitem.getDepMethodId());
                                    stock.setDepRate(transitem.getDepRate());
                                    stock.setAverageMethodId(transitem.getAverageMethodId());
                                    stock.setEffectiveLife(transitem.getEffectiveLife());
                                    stock.setAccountCode(transitem.getAccountCode());
                                    stock.setResidualValue(transitem.getResidualValue());
                                    stock.setAssetStatusId(1);
                                    stock.setAssetStatusDesc("");
                                    stock.setSpecific_size(transitem.getSpecific_size());
                                    i = new StockBean().saveStock(stock);
                                    new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                                }
                            }

                            //TRANSFER - 1. Subtract stock from the source store
                            double FromUnitCost = 0;
                            if ("TRANSFER".equals(transtype.getTransactionTypeName())) {
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(store.getStoreId());
                                stock.setItemId(transitem.getItemId());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                i = new StockBean().subtractStock(stock, transitem.getItemQty());
                                try {
                                    FromUnitCost = new StockBean().getStock(stock.getStoreId(), stock.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific()).getUnitCost();
                                } catch (NullPointerException npe) {

                                }
                                stock.setSpecific_size(transitem.getSpecific_size());
                                new Stock_ledgerBean().callInsertStock_ledger("Subtract", stock, transitem.getItemQty(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            }
                            //TRANSFER - 2. Add/Insert stock to the destination store
                            if ("TRANSFER".equals(transtype.getTransactionTypeName())) {
                                if (StkBean.getStock(store2.getStoreId(), transitem.getItemId(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific()) != null) {
                                    //update/add
                                    Stock stock = new Stock();
                                    int i = 0;
                                    stock.setStoreId(store2.getStoreId());
                                    stock.setItemId(transitem.getItemId());
                                    stock.setBatchno(transitem.getBatchno());
                                    stock.setCodeSpecific(transitem.getCodeSpecific());
                                    stock.setDescSpecific(transitem.getDescSpecific());
                                    i = new StockBean().addStock(stock, transitem.getItemQty());
                                    stock.setSpecific_size(transitem.getSpecific_size());
                                    new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                                } else {
                                    //insert
                                    Stock stock = new Stock();
                                    int i = 0;
                                    stock.setStoreId(store2.getStoreId());
                                    stock.setItemId(transitem.getItemId());
                                    stock.setBatchno(transitem.getBatchno());
                                    stock.setCodeSpecific(transitem.getCodeSpecific());
                                    stock.setDescSpecific(transitem.getDescSpecific());
                                    stock.setCurrentqty(transitem.getItemQty());
                                    stock.setDescMore(transitem.getDescMore());
                                    stock.setCurrentqty(transitem.getItemQty());
                                    stock.setItemMnfDate(transitem.getItemMnfDate());
                                    stock.setItemExpDate(transitem.getItemExpryDate());
                                    //get cost price from the mother store
                                    stock.setUnitCost(FromUnitCost);
                                    stock.setWarrantyDesc(transitem.getWarrantyDesc());
                                    stock.setWarrantyExpiryDate(transitem.getWarrantyExpiryDate());
                                    stock.setPurchaseDate(transitem.getPurchaseDate());
                                    stock.setDepStartDate(transitem.getDepStartDate());
                                    stock.setDepMethodId(transitem.getDepMethodId());
                                    stock.setDepRate(transitem.getDepRate());
                                    stock.setAverageMethodId(transitem.getAverageMethodId());
                                    stock.setEffectiveLife(transitem.getEffectiveLife());
                                    stock.setAccountCode(transitem.getAccountCode());
                                    stock.setResidualValue(transitem.getResidualValue());
                                    stock.setAssetStatusId(1);
                                    stock.setAssetStatusDesc("");
                                    stock.setSpecific_size(transitem.getSpecific_size());
                                    i = new StockBean().saveStock(stock);
                                    new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                                }
                            }
                            //UNPACK - 1. Subtract stock from the source BigItem
                            if ("UNPACK".equals(transtype.getTransactionTypeName())) {
                                Stock stock = new Stock();
                                int i = 0;
                                stock.setStoreId(store.getStoreId());
                                stock.setItemId(transitem.getItemId());
                                stock.setBatchno(transitem.getBatchno());
                                stock.setCodeSpecific(transitem.getCodeSpecific());
                                stock.setDescSpecific(transitem.getDescSpecific());
                                i = new StockBean().subtractStock(stock, transitem.getItemQty());
                                stock.setSpecific_size(transitem.getSpecific_size());
                                new Stock_ledgerBean().callInsertStock_ledger("Subtract", stock, transitem.getItemQty(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                            }
                            //UNPACK - 2. Add/Insert stock to the destination small item
                            if ("UNPACK".equals(transtype.getTransactionTypeName())) {
                                if (StkBean.getStock(store.getStoreId(), transitem.getItemId2(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific()) != null) {
                                    //update/add
                                    Stock stock = new Stock();
                                    int i = 0;
                                    stock.setStoreId(store.getStoreId());
                                    stock.setItemId(transitem.getItemId2());
                                    stock.setBatchno(transitem.getBatchno());
                                    stock.setCodeSpecific(transitem.getCodeSpecific());
                                    stock.setDescSpecific(transitem.getDescSpecific());
                                    i = new StockBean().addStock(stock, transitem.getItemQty2());
                                    stock.setSpecific_size(transitem.getSpecific_size());
                                    new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty2(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                                } else {
                                    //insert
                                    Stock stock = new Stock();
                                    int i = 0;
                                    stock.setStoreId(store.getStoreId());
                                    stock.setItemId(transitem.getItemId2());
                                    stock.setBatchno(transitem.getBatchno());
                                    stock.setCodeSpecific(transitem.getCodeSpecific());
                                    stock.setDescSpecific(transitem.getDescSpecific());
                                    stock.setCurrentqty(transitem.getItemQty2());
                                    //get the last unit cost price
                                    long LatestTransItemId = 0;
                                    double LatestTransItemUnitCostPrice = 0;
                                    LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(9, 13, stock.getStoreId(), stock.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific());
                                    if (LatestTransItemId > 0) {
                                        try {
                                            LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                                        } catch (NullPointerException npe) {
                                            LatestTransItemUnitCostPrice = 0;
                                        }
                                    }
                                    //incase the small item has never been supplied; try to get the the latest unit cost for the bigger item and divide
                                    if (LatestTransItemUnitCostPrice <= 0) {
                                        long LatestTransItemIdBig = 0;
                                        double LatestTransItemUnitCostPriceBig = 0;
                                        double FractionQty = 0;
                                        LatestTransItemIdBig = this.getItemUnitCostPriceLatestTransItemId(9, 13, stock.getStoreId(), transitem.getItemId(), stock.getBatchno(), stock.getCodeSpecific(), stock.getDescSpecific());
                                        if (LatestTransItemIdBig > 0) {
                                            //get fraction qty
                                            try {
                                                FractionQty = new ItemMapBean().getItemMapByBigItemId(transitem.getItemId()).getFractionQty();
                                            } catch (NullPointerException npe) {
                                                FractionQty = 0;
                                            }
                                            try {
                                                if (FractionQty > 0) {
                                                    LatestTransItemUnitCostPriceBig = this.getTransItem(LatestTransItemIdBig).getUnitCostPrice() / FractionQty;
                                                }
                                            } catch (NullPointerException npe) {
                                                LatestTransItemUnitCostPriceBig = 0;
                                            }
                                        }
                                        if (LatestTransItemUnitCostPriceBig > 0) {
                                            LatestTransItemUnitCostPrice = LatestTransItemUnitCostPriceBig;
                                        }
                                    }

                                    if (LatestTransItemUnitCostPrice > 0) {
                                        stock.setUnitCost(LatestTransItemUnitCostPrice);
                                    } else {
                                        stock.setUnitCost(transitem.getUnitCostPrice());
                                    }

                                    try {
                                        stock.setItemMnfDate(transitem.getItemMnfDate());
                                    } catch (NullPointerException npe) {
                                        stock.setItemMnfDate(null);
                                    }
                                    try {
                                        stock.setItemExpDate(transitem.getItemExpryDate());
                                    } catch (NullPointerException npe) {
                                        stock.setItemExpDate(null);
                                    }
                                    stock.setSpecific_size(transitem.getSpecific_size());
                                    i = new StockBean().saveStock(stock);
                                    new Stock_ledgerBean().callInsertStock_ledger("Add", stock, transitem.getItemQty2(), "Add", aTransTypeId, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                                }
                            }
                        }
                    }
                    TransTypeBean = null;
                    StkBean = null;

                } else if (transitem.getTransactionItemId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("TransItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransItem NOT saved!"));
            }
        }
    }

    public void saveDraftTransItems(Trans aTrans, List<TransItem> aActiveTransItems, long TransactionHistId) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            ati.get(ListItemIndex).setTransactionId(TransactionHistId);
            this.saveDraftTransItem(aTrans, ati.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void saveDraftTransItem(Trans aTrans, TransItem transitem) {
        String sql = null;
        String sql2 = null;
        String msg = "";
        if (1 == 2) {
        } else {
            sql = "{call sp_insert_transaction_item_hist(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (transitem.getBatchno() == null) {
                    transitem.setBatchno("");
                }
                cs.setLong("in_transaction_hist_id", transitem.getTransactionId());
                cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
                cs.setLong("in_transaction_id", 0);
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
                if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    cs.setString("in_stock_effect", "D");
                } else if ("PURCHASE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    cs.setString("in_stock_effect", "C");
                } else if ("EXPENSE ENTRY".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    cs.setString("in_stock_effect", "C");
                } else if ("TRANSFER".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    cs.setString("in_stock_effect", "B");
                } else if ("DISPOSE STOCK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    cs.setString("in_stock_effect", "D");
                } else if ("UNPACK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    cs.setString("in_stock_effect", "D");
                } else if ("ITEM RECEIVED".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    cs.setString("in_stock_effect", "C");
                } else {
                    cs.setString("in_stock_effect", "");
                }
                //for profit margin
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
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("DRAFT not saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Draft not saved!"));
            }
        }
    }

    public void saveTransItemJournalEntry(Trans aTrans, TransItem transitem) {
        String sql = null;
        String sql2 = null;
        String msg = "";
        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        TransactionType TransType = new TransactionType();
        StockBean StkBean = new StockBean();

        if (1 == 2) {
        } else {

            if (transitem.getTransactionItemId() == 0) {
                sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            } else if (transitem.getTransactionItemId() > 0) {
                sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (transitem.getTransactionItemId() == 0) {
                    cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
                    cs.setLong("in_transaction_id", transitem.getTransactionId());
                    cs.setLong("in_item_id", 0);
                    cs.setString("in_batchno", "");
                    cs.setDate("in_item_expiry_date", null);
                    cs.setDate("in_item_mnf_date", null);
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
                    //for profit margin
                    cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
                    cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
                    //for user earning
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
                    cs.setDouble("in_specific_size", 1);
                    //save
                    cs.executeUpdate();

                    TransType = null;
                    TransTypeBean = null;
                    StkBean = null;

                } else if (transitem.getTransactionItemId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("TransItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransItem NOT saved!"));
            }
        }
    }

    public void saveTransItemCashTransfer(Trans aTrans, TransItem transitem) {
        String sql = null;
        String sql2 = null;
        String msg = "";
        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        TransactionType TransType = new TransactionType();
        StockBean StkBean = new StockBean();

        if (1 == 2) {
        } else {

            if (transitem.getTransactionItemId() == 0) {
                sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            } else if (transitem.getTransactionItemId() > 0) {
                sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (transitem.getTransactionItemId() == 0) {
                    cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
                    cs.setLong("in_transaction_id", transitem.getTransactionId());
                    cs.setLong("in_item_id", 0);
                    cs.setString("in_batchno", transitem.getBatchno());
                    cs.setDate("in_item_expiry_date", null);
                    cs.setDate("in_item_mnf_date", null);
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
                    //for profit margin
                    cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
                    cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
                    //for user earning
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
                    cs.setDouble("in_specific_size", 1);
                    //save
                    cs.executeUpdate();

                    TransType = null;
                    TransTypeBean = null;
                    StkBean = null;

                } else if (transitem.getTransactionItemId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("TransItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransItem NOT saved!"));
            }
        }
    }

    public void saveTransItemCashAdjustment(Trans aTrans, TransItem transitem) {
        String sql = null;
        String sql2 = null;
        String msg = "";
        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        TransactionType TransType = new TransactionType();
        StockBean StkBean = new StockBean();

        if (1 == 2) {
        } else {

            if (transitem.getTransactionItemId() == 0) {
                sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            } else if (transitem.getTransactionItemId() > 0) {
                sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (transitem.getTransactionItemId() == 0) {
                    cs.setString("in_is_trade_discount_vat_liable", CompanySetting.getIsTradeDiscountVatLiable());
                    cs.setLong("in_transaction_id", transitem.getTransactionId());
                    cs.setLong("in_item_id", 0);
                    cs.setString("in_batchno", transitem.getBatchno());
                    cs.setDate("in_item_expiry_date", null);
                    cs.setDate("in_item_mnf_date", null);
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
                    //for profit margin
                    cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
                    cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
                    //for user earning
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
                    cs.setDouble("in_specific_size", 1);
                    //save
                    cs.executeUpdate();

                    TransType = null;
                    TransTypeBean = null;
                    StkBean = null;

                } else if (transitem.getTransactionItemId() > 0) {
                    //do nothing; this is for edit
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("TransItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("TransItem NOT saved!"));
            }
        }
    }

    public void updateTransItem(TransItem transitem) {
        int success = 0;
        String sql = "{call sp_update_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_transaction_item_id", transitem.getTransactionItemId());
            cs.setDouble("in_item_qty", transitem.getItemQty());
            cs.setDouble("in_unit_price", transitem.getUnitPrice());
            cs.setDouble("in_unit_trade_discount", transitem.getUnitTradeDiscount());
            cs.setDouble("in_unit_vat", transitem.getUnitVat());
            cs.setDouble("in_amount", transitem.getAmount());
            cs.setDouble("in_unit_price_inc_vat", transitem.getUnitPriceIncVat());
            cs.setDouble("in_unit_price_exc_vat", transitem.getUnitPriceExcVat());
            cs.setDouble("in_amount_inc_vat", transitem.getAmountIncVat());
            cs.setDouble("in_amount_exc_vat", transitem.getAmountExcVat());
            //for profit margin
            cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
            cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
            //for user earning
            //get the previously used earn rate/perc and update for the new qty
            cs.setDouble("in_earn_perc", transitem.getEarnPerc());
            cs.setDouble("in_earn_amount", (double) (transitem.getEarnPerc() * transitem.getAmountIncVat() * 0.01));
            //save
            cs.executeUpdate();
            success = 1;
        } catch (SQLException se) {
            success = 0;
            System.err.println("UpdateTransaction:" + se.getMessage());

        }
    }

    public void updateTransItemCEC(TransItem transitem) {
        int success = 0;
        String sql = "{call sp_update_transaction_itemCEC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_transaction_item_id", transitem.getTransactionItemId());
            cs.setDouble("in_item_qty", transitem.getItemQty());
            cs.setDouble("in_unit_price", transitem.getUnitPrice());
            cs.setDouble("in_unit_trade_discount", transitem.getUnitTradeDiscount());
            cs.setDouble("in_unit_vat", transitem.getUnitVat());
            cs.setDouble("in_amount", transitem.getAmount());
            cs.setDouble("in_unit_price_inc_vat", transitem.getUnitPriceIncVat());
            cs.setDouble("in_unit_price_exc_vat", transitem.getUnitPriceExcVat());
            cs.setDouble("in_amount_inc_vat", transitem.getAmountIncVat());
            cs.setDouble("in_amount_exc_vat", transitem.getAmountExcVat());
            //for profit margin
            cs.setDouble("in_unit_cost_price", transitem.getUnitCostPrice());
            cs.setDouble("in_unit_profit_margin", transitem.getUnitProfitMargin());
            //for user earning
            //get the previously used earn rate/perc and update for the new qty
            cs.setDouble("in_earn_perc", transitem.getEarnPerc());
            cs.setDouble("in_earn_amount", (double) (transitem.getEarnPerc() * transitem.getAmountIncVat() * 0.01));
            //in_earn_perc", transitem.getEarnPerc());
            cs.setDouble("in_qty_balance", transitem.getQty_balance());
            cs.setDouble("in_duration_value", transitem.getDuration_value());
            cs.setDouble("in_qty_damage", transitem.getQty_damage());
            cs.setDouble("in_duration_passed", transitem.getDuration_passed());
            //save
            cs.executeUpdate();
            success = 1;
        } catch (SQLException se) {
            success = 0;
            System.err.println("UpdateTransaction:" + se.getMessage());

        }
    }

    public int updateTransItemCECOpenBalance(TransItem aTransitem) {
        int success = 0;
        String sql = "UPDATE transaction_item SET amount_inc_vat=?,amount_exc_vat=?,amount=? WHERE transaction_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setDouble(1, aTransitem.getAmountIncVat());
            ps.setDouble(2, aTransitem.getAmountExcVat());
            ps.setDouble(3, aTransitem.getAmount());
            ps.setLong(4, aTransitem.getTransactionItemId());
            //save
            ps.executeUpdate();
            success = 1;
        } catch (Exception e) {
            success = 0;
            System.err.println("updateTransItemCECOpenBalance:" + e.getMessage());
        }
        return success;
    }

    public void saveTransItemAutoUnpack(TransItem transitem) {
        String sql = null;
        String sql2 = null;
        StockBean StkBean = new StockBean();
        sql = "{call sp_insert_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {

            //clean batch
            if (transitem.getBatchno() == null) {
                transitem.setBatchno("");
            }

            cs.setString("in_is_trade_discount_vat_liable", "");
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
            cs.setDouble("in_unit_price", 0);
            cs.setDouble("in_unit_trade_discount", 0);
            cs.setDouble("in_unit_vat", 0);
            cs.setDouble("in_amount", 0);
            try {
                cs.setDate("in_item_expiry_date", new java.sql.Date(transitem.getItemExpryDate().getTime()));
                cs.setDate("in_item_mnf_date", new java.sql.Date(transitem.getItemMnfDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_item_expiry_date", null);
                cs.setDate("in_item_mnf_date", null);
            }
            cs.setString("in_vat_rated", "");
            cs.setDouble("in_vat_perc", 0);
            cs.setDouble("in_unit_price_inc_vat", 0);
            cs.setDouble("in_unit_price_exc_vat", 0);
            cs.setDouble("in_amount_inc_vat", 0);
            cs.setDouble("in_amount_exc_vat", 0);
            cs.setString("in_stock_effect", "D");
            //for profit margin
            cs.setDouble("in_unit_cost_price", 0);
            cs.setDouble("in_unit_profit_margin", 0);
            //for user earning
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

            //repeat for the unpacked ones
            cs.setLong("in_item_id", transitem.getItemId2());
            cs.setDouble("in_item_qty", transitem.getItemQty2());
            cs.setString("in_stock_effect", "C");
            cs.executeUpdate();

            //update stock
            int SubtractStatus = 0;
            int AddStatus = 0;
            Stock SubtractObj = new Stock();
            Stock AddObj = new Stock();
            //UNPACK - 1. Subtract stock from the source BigItem
            SubtractObj.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
            SubtractObj.setItemId(transitem.getItemId());
            SubtractObj.setBatchno(transitem.getBatchno());
            SubtractObj.setCodeSpecific(transitem.getCodeSpecific());
            SubtractObj.setDescSpecific(transitem.getDescSpecific());
            SubtractStatus = new StockBean().subtractStock(SubtractObj, transitem.getItemQty());
            SubtractObj.setSpecific_size(transitem.getSpecific_size());
            new Stock_ledgerBean().callInsertStock_ledger("Subtract", SubtractObj, transitem.getItemQty(), "Add", 7, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());

            //UNPACK - 2. Add/Insert stock to the destination small item
            Stock ExistingStockObj = StkBean.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), transitem.getItemId2(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific());
            if (null != ExistingStockObj) {
                //update/add
                if (SubtractStatus == 1) {
                    AddObj.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                    AddObj.setItemId(transitem.getItemId2());
                    AddObj.setBatchno(transitem.getBatchno());
                    AddObj.setCodeSpecific(transitem.getCodeSpecific());
                    AddObj.setDescSpecific(transitem.getDescSpecific());
                    AddObj.setUnitCost(ExistingStockObj.getUnitCost());
                    AddStatus = new StockBean().addStock(AddObj, transitem.getItemQty2());
                    AddObj.setSpecific_size(transitem.getSpecific_size());
                    new Stock_ledgerBean().callInsertStock_ledger("Add", AddObj, transitem.getItemQty2(), "Add", 7, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                }
            } else {
                //insert
                if (SubtractStatus == 1) {
                    AddObj.setStockId(0);
                    AddObj.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                    AddObj.setItemId(transitem.getItemId2());
                    AddObj.setBatchno(transitem.getBatchno());
                    AddObj.setCodeSpecific(transitem.getCodeSpecific());
                    AddObj.setDescSpecific(transitem.getDescSpecific());
                    AddObj.setCurrentqty(transitem.getItemQty2());
                    try {
                        AddObj.setItemExpDate(new java.sql.Date(transitem.getItemExpryDate().getTime()));
                        AddObj.setItemMnfDate(new java.sql.Date(transitem.getItemMnfDate().getTime()));
                    } catch (NullPointerException npe) {
                        AddObj.setItemExpDate(null);
                        AddObj.setItemMnfDate(null);
                    }
                    AddObj.setUnitCost(this.findItemLatestUnitCostPrice(new GeneralUserSetting().getCurrentStore().getStoreId(), transitem.getItemId2(), 9, 13));
                    //temp fix -- start
                    Item aItem = new ItemBean().getItem(transitem.getItemId2());
                    AddObj.setDescMore("");
                    if (aItem.getItemType().equals("PRODUCT")) {
                        AddObj.setAccountCode("5-10-000-010");
                    } else if (aItem.getItemType().equals("SERVICE")) {
                        AddObj.setAccountCode("5-10-000-020");
                    }
                    AddObj.setAssetStatusId(1);
                    AddObj.setAssetStatusDesc("");
                    //temp fix -- end
                    try {
                        if (transitem.getSpecific_size() > 0) {
                            cs.setDouble("in_specific_size", transitem.getSpecific_size());
                        } else {
                            cs.setDouble("in_specific_size", 1);
                        }
                    } catch (NullPointerException npe) {
                        cs.setDouble("in_specific_size", 1);
                    }
                    AddStatus = new StockBean().saveStock(AddObj);
                    new Stock_ledgerBean().callInsertStock_ledger("Add", AddObj, transitem.getItemQty2(), "Add", 7, transitem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                }
            }
            StkBean = null;
        } catch (SQLException se) {
            System.err.println("saveTransItemAutoUnpack:" + se.getMessage());
        }
    }

    public boolean updateTransItems(long aTransactionId, long aTransactionHistId, List<TransItem> aNewTransItems) {
        try {
            //get trans items that was moved to the history table
            List<TransItem> aHistTransItems = new ArrayList<TransItem>();
            this.setTransItemsHistoryByIDs(aTransactionId, aTransactionHistId, aHistTransItems);

            //1. Reverse and update all trans items whoose qty has changed
            int NewListItemIndex = 0;
            int HistListItemIndex = 0;
            int NewListItemNo = aNewTransItems.size();
            int HistListItemNo = aHistTransItems.size();
            double aDiffHistNewQty = 0;
            TransItem nti = new TransItem();
            TransItem hti = new TransItem();
            while (NewListItemIndex < NewListItemNo) {
                HistListItemIndex = 0;
                hti = aHistTransItems.get(HistListItemIndex);
                while (HistListItemIndex < HistListItemNo) {
                    nti = aNewTransItems.get(NewListItemIndex);
                    if (nti.getItemId() == hti.getItemId() && nti.getBatchno().equals(hti.getBatchno()) && (nti.getCodeSpecific() == null ? hti.getCodeSpecific() == null : nti.getCodeSpecific().equals(hti.getCodeSpecific())) && (nti.getDescSpecific() == null ? hti.getDescSpecific() == null : nti.getDescSpecific().equals(hti.getDescSpecific()))) {
                        aDiffHistNewQty = hti.getItemQty() - nti.getItemQty();
                        break;
                    }
                    HistListItemIndex = HistListItemIndex + 1;
                }
                //2. Reverse and update individual trans item whoose qty has changed
                if (aDiffHistNewQty > 0 || aDiffHistNewQty < 0) {
                    this.reverseTransItem(nti, aDiffHistNewQty);
                    this.updateTransItem(nti);
                }
                NewListItemIndex = NewListItemIndex + 1;
            }
            return true;
        } catch (Exception e) {
            System.err.println("UpdateTransItems:" + e.getMessage());
            return false;
        }
    }

    public boolean updateTransItemsV2(long aTransactionId, long aTransactionHistId, List<TransItem> aNewTransItems) {
        try {
            //get trans items that was moved to the history table
            List<TransItem> aHistTransItems = new ArrayList<TransItem>();
            this.setTransItemsHistoryByIDs(aTransactionId, aTransactionHistId, aHistTransItems);

            //1. Reverse and update all trans items whoose qty has changed
            int NewListItemIndex = 0;
            int HistListItemIndex = 0;
            int NewListItemNo = aNewTransItems.size();
            int HistListItemNo = aHistTransItems.size();
            double aDiffHistNewQty = 0;
            TransItem nti = new TransItem();
            TransItem hti = new TransItem();
            while (NewListItemIndex < NewListItemNo) {
                HistListItemIndex = 0;
                aDiffHistNewQty = 0;
                //hti = aHistTransItems.get(HistListItemIndex);
                nti = aNewTransItems.get(NewListItemIndex);
                while (HistListItemIndex < HistListItemNo) {
                    //nti = aNewTransItems.get(NewListItemIndex);
                    hti = aHistTransItems.get(HistListItemIndex);
                    if (nti.getItemId() == hti.getItemId() && nti.getBatchno().equals(hti.getBatchno()) && (nti.getCodeSpecific() == null ? hti.getCodeSpecific() == null : nti.getCodeSpecific().equals(hti.getCodeSpecific())) && (nti.getDescSpecific() == null ? hti.getDescSpecific() == null : nti.getDescSpecific().equals(hti.getDescSpecific()))) {
                        aDiffHistNewQty = hti.getItemQty() - nti.getItemQty();
                        break;
                    }
                    HistListItemIndex = HistListItemIndex + 1;
                }
                //2. Reverse and update individual trans item whoose qty has changed
                if (aDiffHistNewQty > 0 || aDiffHistNewQty < 0) {
                    this.reverseTransItemV2(new TransactionTypeBean().getTransactionType(new TransBean().getTrans(aTransactionId).getTransactionTypeId()), new TransactionReasonBean().getTransactionReason(new TransBean().getTrans(aTransactionId).getTransactionReasonId()), nti, aDiffHistNewQty);
                    this.updateTransItem(nti);
                }
                NewListItemIndex = NewListItemIndex + 1;
            }
            return true;
        } catch (Exception e) {
            System.err.println("UpdateTransItems:" + e.getMessage());
            return false;
        }
    }

    public boolean updateTransItemsCEC(long aTransactionId, long aTransactionHistId, List<TransItem> aNewTransItems) {
        try {
            //get trans items that was moved to the history table
            List<TransItem> aHistTransItems = new ArrayList<TransItem>();
            this.setTransItemsHistoryByIDs(aTransactionId, aTransactionHistId, aHistTransItems);

            //1. Reverse and update all trans items whoose qty has changed
            int NewListItemIndex = 0;
            int HistListItemIndex = 0;
            int NewListItemNo = aNewTransItems.size();
            int HistListItemNo = aHistTransItems.size();
            double aDiffHistNewQty = 0;
            double aDiffHistNewQty_damage = 0;
            TransItem nti = new TransItem();
            TransItem hti = new TransItem();
            while (NewListItemIndex < NewListItemNo) {
                HistListItemIndex = 0;
                aDiffHistNewQty = 0;
                aDiffHistNewQty_damage = 0;
                //hti = aHistTransItems.get(HistListItemIndex);
                nti = aNewTransItems.get(NewListItemIndex);
                while (HistListItemIndex < HistListItemNo) {
                    //nti = aNewTransItems.get(NewListItemIndex);
                    hti = aHistTransItems.get(HistListItemIndex);
                    if (nti.getItemId() == hti.getItemId() && nti.getBatchno().equals(hti.getBatchno()) && (nti.getCodeSpecific() == null ? hti.getCodeSpecific() == null : nti.getCodeSpecific().equals(hti.getCodeSpecific())) && (nti.getDescSpecific() == null ? hti.getDescSpecific() == null : nti.getDescSpecific().equals(hti.getDescSpecific()))) {
                        aDiffHistNewQty = hti.getItemQty() - nti.getItemQty();
                        aDiffHistNewQty_damage = hti.getQty_damage() - nti.getQty_damage();
                        break;
                    }
                    HistListItemIndex = HistListItemIndex + 1;
                }
                //2. Reverse and update individual trans item whoose qty has changed
                if (aDiffHistNewQty > 0 || aDiffHistNewQty < 0 || aDiffHistNewQty_damage > 0 || aDiffHistNewQty_damage < 0) {
                    Trans t = new TransBean().getTrans(aTransactionId);
                    if (t.getTransactionTypeId() == 2 && t.getStore2Id() > 0) {//Invoice with Store2Id -- for order sent to
                        this.reverseTransItemCEC(t.getStore2Id(), t.getTransactionTypeId(), t.getTransactionReasonId(), "", nti, aDiffHistNewQty, aDiffHistNewQty_damage);
                    } else {
                        this.reverseTransItemCEC(t.getStoreId(), t.getTransactionTypeId(), t.getTransactionReasonId(), "", nti, aDiffHistNewQty, aDiffHistNewQty_damage);
                    }
                    this.updateTransItemCEC(nti);
                }
                NewListItemIndex = NewListItemIndex + 1;
            }
            return true;
        } catch (Exception e) {
            System.err.println("UpdateTransItemsCEC:" + e.getMessage());
            return false;
        }
    }

    public void reverseTransItem(TransItem transitem, double aDiffHistNewQty) {
        String sql = null;
        String sql2 = null;
        StockBean StkBean = new StockBean();
        Stock Stk = new Stock();

        if (transitem.getTransactionItemId() == 0 || new ItemBean().getItem(transitem.getItemId()).getItemType().equals("SERVICE")) {
            //do nothing
        } else {
            //1. reverse stock
            Stk = StkBean.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), transitem.getItemId(), transitem.getBatchno(), transitem.getCodeSpecific(), transitem.getDescSpecific());
            //for additive transactions, if diff is +ve, subtract; if diff is -ve Add
            //originally "PURCHASE INVOICE" but changed to "ITEM RECEIVED"
            if ("ITEM RECEIVED".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                if (aDiffHistNewQty > 0) {
                    //subtract stock
                    if (Stk != null) {
                        //update/subtract
                        sql2 = "{call sp_subtract_stock_by_store_item_batch(?,?,?,?)}";
                        try (
                                Connection conn2 = DBConnection.getMySQLConnection();
                                CallableStatement cs2 = conn2.prepareCall(sql2);) {
                            cs2.setInt("in_store_id", new GeneralUserSetting().getCurrentStore().getStoreId());
                            cs2.setLong("in_item_id", transitem.getItemId());
                            cs2.setString("in_batchno", transitem.getBatchno());
                            cs2.setDouble("in_qty", aDiffHistNewQty);
                            cs2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(TransItemBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //insert
                        Stock stk = new Stock();
                        stk.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        stk.setItemId(transitem.getItemId());
                        stk.setBatchno(transitem.getBatchno());
                        stk.setCurrentqty(-1 * aDiffHistNewQty);
                        stk.setItemExpDate(transitem.getItemExpryDate());
                        stk.setItemMnfDate(transitem.getItemMnfDate());

                    }

                } else if (aDiffHistNewQty < 0) {
                    //add stock
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity
                    if (Stk != null) {
                        //update/add
                        sql2 = "{call sp_add_stock_by_store_item_batch(?,?,?,?)}";
                        try (
                                Connection conn2 = DBConnection.getMySQLConnection();
                                CallableStatement cs2 = conn2.prepareCall(sql2);) {
                            cs2.setInt("in_store_id", new GeneralUserSetting().getCurrentStore().getStoreId());
                            cs2.setLong("in_item_id", transitem.getItemId());
                            cs2.setString("in_batchno", transitem.getBatchno());
                            cs2.setDouble("in_qty", aDiffHistNewQty);
                            cs2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(TransItemBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //insert
                        sql2 = "{call sp_insert_stock(?,?,?,?,?,?)}";
                        try (
                                Connection conn2 = DBConnection.getMySQLConnection();
                                CallableStatement cs2 = conn2.prepareCall(sql2);) {
                            cs2.setInt("in_store_id", new GeneralUserSetting().getCurrentStore().getStoreId());
                            cs2.setLong("in_item_id", transitem.getItemId());
                            cs2.setString("in_batchno", transitem.getBatchno());
                            cs2.setDouble("in_currentqty", aDiffHistNewQty);
                            try {
                                cs2.setDate("in_item_exp_date", new java.sql.Date(transitem.getItemExpryDate().getTime()));
                                cs2.setDate("in_item_mnf_date", new java.sql.Date(transitem.getItemMnfDate().getTime()));
                            } catch (NullPointerException npe) {
                                cs2.setDate("in_item_exp_date", null);
                                cs2.setDate("in_item_mnf_date", null);
                            }
                            cs2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(TransItemBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }

            if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "DISPOSE STOCK".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                if (aDiffHistNewQty > 0) {
                    //add stock
                    if (Stk != null) {
                        //update/add
                        sql2 = "{call sp_add_stock_by_store_item_batch(?,?,?,?)}";
                        try (
                                Connection conn2 = DBConnection.getMySQLConnection();
                                CallableStatement cs2 = conn2.prepareCall(sql2);) {
                            cs2.setInt("in_store_id", new GeneralUserSetting().getCurrentStore().getStoreId());
                            cs2.setLong("in_item_id", transitem.getItemId());
                            cs2.setString("in_batchno", transitem.getBatchno());
                            cs2.setDouble("in_qty", aDiffHistNewQty);
                            cs2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(TransItemBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //insert
                        sql2 = "{call sp_insert_stock(?,?,?,?,?,?)}";
                        try (
                                Connection conn2 = DBConnection.getMySQLConnection();
                                CallableStatement cs2 = conn2.prepareCall(sql2);) {
                            cs2.setInt("in_store_id", new GeneralUserSetting().getCurrentStore().getStoreId());
                            cs2.setLong("in_item_id", transitem.getItemId());
                            cs2.setString("in_batchno", transitem.getBatchno());
                            cs2.setDouble("in_currentqty", aDiffHistNewQty);
                            try {
                                cs2.setDate("in_item_exp_date", new java.sql.Date(transitem.getItemExpryDate().getTime()));
                                cs2.setDate("in_item_mnf_date", new java.sql.Date(transitem.getItemMnfDate().getTime()));
                            } catch (NullPointerException npe) {
                                cs2.setDate("in_item_exp_date", null);
                                cs2.setDate("in_item_mnf_date", null);
                            }
                            cs2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(TransItemBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (aDiffHistNewQty < 0) {
                    //subtract stock
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity

                    if (Stk != null) {
                        //update/subtract
                        sql2 = "{call sp_subtract_stock_by_store_item_batch(?,?,?,?)}";
                        try (
                                Connection conn2 = DBConnection.getMySQLConnection();
                                CallableStatement cs2 = conn2.prepareCall(sql2);) {
                            cs2.setInt("in_store_id", new GeneralUserSetting().getCurrentStore().getStoreId());
                            cs2.setLong("in_item_id", transitem.getItemId());
                            cs2.setString("in_batchno", transitem.getBatchno());
                            cs2.setDouble("in_qty", aDiffHistNewQty);
                            cs2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(TransItemBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //insert
                        sql2 = "{call sp_insert_stock(?,?,?,?,?,?)}";
                        try (
                                Connection conn2 = DBConnection.getMySQLConnection();
                                CallableStatement cs2 = conn2.prepareCall(sql2);) {
                            cs2.setInt("in_store_id", new GeneralUserSetting().getCurrentStore().getStoreId());
                            cs2.setLong("in_item_id", transitem.getItemId());
                            cs2.setString("in_batchno", transitem.getBatchno());
                            cs2.setDouble("in_currentqty", (-1 * aDiffHistNewQty));
                            try {
                                cs2.setDate("in_item_exp_date", new java.sql.Date(transitem.getItemExpryDate().getTime()));
                                cs2.setDate("in_item_mnf_date", new java.sql.Date(transitem.getItemMnfDate().getTime()));
                            } catch (NullPointerException npe) {
                                cs2.setDate("in_item_exp_date", null);
                                cs2.setDate("in_item_mnf_date", null);
                            }
                            cs2.executeUpdate();
                        } catch (SQLException ex) {
                            Logger.getLogger(TransItemBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
            StkBean = null;
            Stk = null;
        }
    }

    public void reverseTransItemV2(TransactionType aTransType, TransactionReason aTransReason, TransItem aTransItem, double aDiffHistNewQty) {
        String sql = null;
        String sql2 = null;
        StockBean StkBean = new StockBean();
        Stock Stk = new Stock();

        if (aTransItem.getTransactionItemId() == 0 || new ItemBean().getItem(aTransItem.getItemId()).getIsTrack() == 0) {
            //do nothing
        } else {
            //1. reverse stock
            Stk = StkBean.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aTransItem.getItemId(), aTransItem.getBatchno(), aTransItem.getCodeSpecific(), aTransItem.getDescSpecific());
            //for additive transactions, if diff is +ve, subtract; if diff is -ve Add
            if ("ITEM RECEIVED".equals(aTransType.getTransactionTypeName()) || (aTransType.getTransactionTypeId() == 1 && aTransReason.getTransactionReasonId() == 29)) {
                if (aDiffHistNewQty > 0) {
                    //subtract stock
                    if (Stk != null) {
                        //update/subtract
                        Stock s = new Stock();
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().subtractStock(s, aDiffHistNewQty);
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(-1 * aDiffHistNewQty);

                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(aTransType.getTransactionTypeId(), aTransReason.getTransactionReasonId(), s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                    }

                } else if (aDiffHistNewQty < 0) {
                    //add stock
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity
                    if (Stk != null) {
                        //update/add
                        Stock s = new Stock();
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().addStock(s, aDiffHistNewQty);
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(aDiffHistNewQty);

                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(aTransType.getTransactionTypeId(), aTransReason.getTransactionReasonId(), s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                    }
                }

            }

            if ("SALE INVOICE".equals(aTransType.getTransactionTypeName()) || "DISPOSE STOCK".equals(aTransType.getTransactionTypeName())) {
                if (aDiffHistNewQty > 0) {
                    //add stock
                    if (Stk != null) {
                        //update/add
                        Stock s = new Stock();
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().addStock(s, aDiffHistNewQty);
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(aDiffHistNewQty);

                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(9, 13, s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        try {
                            if (aTransItem.getSpecific_size() > 0) {
                                s.setSpecific_size(aTransItem.getSpecific_size());
                            } else {
                                s.setSpecific_size(1);
                            }
                        } catch (NullPointerException npe) {
                            s.setSpecific_size(1);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                    }
                } else if (aDiffHistNewQty < 0) {
                    //subtract stock
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity
                    if (Stk != null) {
                        //update/subtract
                        Stock s = new Stock();
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().subtractStock(s, aDiffHistNewQty);
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(new GeneralUserSetting().getCurrentStore().getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(-1 * aDiffHistNewQty);

                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(9, 13, s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        try {
                            if (aTransItem.getSpecific_size() > 0) {
                                s.setSpecific_size(aTransItem.getSpecific_size());
                            } else {
                                s.setSpecific_size(1);
                            }
                        } catch (NullPointerException npe) {
                            s.setSpecific_size(1);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                    }
                }

            }
            StkBean = null;
            Stk = null;
        }
    }

    public void reverseTransItemCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, TransItem aTransItem, double aDiffHistNewQty, double aDiffHistNewQty_damage) {
        String sql = null;
        String sql2 = null;
        StockBean StkBean = new StockBean();
        Stock Stk = new Stock();

        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);

        if (aTransItem.getTransactionItemId() == 0 || new ItemBean().getItem(aTransItem.getItemId()).getIsTrack() == 0 || ("SALE INVOICE".equals(transtype.getTransactionTypeName()) && !new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("0")) || ("GOODS DELIVERY".equals(transtype.getTransactionTypeName()) && !new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("1"))) {
            //do nothing
        } else {
            //1. reverse stock
            Stk = StkBean.getStock(store.getStoreId(), aTransItem.getItemId(), aTransItem.getBatchno(), aTransItem.getCodeSpecific(), aTransItem.getDescSpecific());
            //for additive transactions, if diff is +ve, subtract; if diff is -ve Add
            if ("ITEM RECEIVED".equals(transtype.getTransactionTypeName()) || (transtype.getTransactionTypeId() == 1 && transreason.getTransactionReasonId() == 29)) {
                if (aDiffHistNewQty > 0) {
                    //subtract stock
                    if (Stk != null) {
                        //update/subtract
                        Stock s = new Stock();
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().subtractStock(s, aDiffHistNewQty);
                        s.setSpecific_size(aTransItem.getSpecific_size());
                        new Stock_ledgerBean().callInsertStock_ledger("Subtract", s, aDiffHistNewQty, "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(-1 * aDiffHistNewQty);
                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(transtype.getTransactionTypeId(), transreason.getTransactionReasonId(), s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        try {
                            if (aTransItem.getSpecific_size() > 0) {
                                s.setSpecific_size(aTransItem.getSpecific_size());
                            } else {
                                s.setSpecific_size(1);
                            }
                        } catch (NullPointerException npe) {
                            s.setSpecific_size(1);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                        new Stock_ledgerBean().callInsertStock_ledger("Add", s, (-1 * aDiffHistNewQty), "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    }

                } else if (aDiffHistNewQty < 0) {
                    //add stock
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity
                    if (Stk != null) {
                        //update/add
                        Stock s = new Stock();
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().addStock(s, aDiffHistNewQty);
                        s.setSpecific_size(aTransItem.getSpecific_size());
                        new Stock_ledgerBean().callInsertStock_ledger("Add", s, aDiffHistNewQty, "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(aDiffHistNewQty);
                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(transtype.getTransactionTypeId(), transreason.getTransactionReasonId(), s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        try {
                            if (aTransItem.getSpecific_size() > 0) {
                                s.setSpecific_size(aTransItem.getSpecific_size());
                            } else {
                                s.setSpecific_size(1);
                            }
                        } catch (NullPointerException npe) {
                            s.setSpecific_size(1);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                        new Stock_ledgerBean().callInsertStock_ledger("Add", s, aDiffHistNewQty, "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    }
                }

            }

            if ("SALE INVOICE".equals(transtype.getTransactionTypeName()) || "DISPOSE STOCK".equals(transtype.getTransactionTypeName()) || "GOODS DELIVERY".equals(transtype.getTransactionTypeName()) || "STOCK CONSUMPTION".equals(transtype.getTransactionTypeName())) {
                if (aDiffHistNewQty > 0) {
                    //add stock
                    if (Stk != null) {
                        //update/add
                        Stock s = new Stock();
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().addStock(s, aDiffHistNewQty);
                        s.setSpecific_size(aTransItem.getSpecific_size());
                        new Stock_ledgerBean().callInsertStock_ledger("Add", s, aDiffHistNewQty, "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(aDiffHistNewQty);
                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(9, 13, s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        try {
                            if (aTransItem.getSpecific_size() > 0) {
                                s.setSpecific_size(aTransItem.getSpecific_size());
                            } else {
                                s.setSpecific_size(1);
                            }
                        } catch (NullPointerException npe) {
                            s.setSpecific_size(1);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                        s.setSpecific_size(aTransItem.getSpecific_size());
                        new Stock_ledgerBean().callInsertStock_ledger("Add", s, aDiffHistNewQty, "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    }
                } else if (aDiffHistNewQty < 0) {
                    //subtract stock
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity
                    if (Stk != null) {
                        //update/subtract
                        Stock s = new Stock();
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        int x = 0;
                        x = new StockBean().subtractStock(s, aDiffHistNewQty);
                        s.setSpecific_size(aTransItem.getSpecific_size());
                        new Stock_ledgerBean().callInsertStock_ledger("Subtract", s, aDiffHistNewQty, "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } else {
                        //insert
                        Stock s = new Stock();
                        s.setStockId(0);
                        s.setStoreId(store.getStoreId());
                        s.setItemId(aTransItem.getItemId());
                        s.setBatchno(aTransItem.getBatchno());
                        s.setCodeSpecific(aTransItem.getCodeSpecific());
                        s.setDescSpecific(aTransItem.getDescSpecific());
                        s.setCurrentqty(-1 * aDiffHistNewQty);
                        //get the last unit cost price
                        long LatestTransItemId = 0;
                        double LatestTransItemUnitCostPrice = 0;
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId(9, 13, s.getStoreId(), s.getItemId(), s.getBatchno(), s.getCodeSpecific(), s.getDescSpecific());
                        if (LatestTransItemId > 0) {
                            try {
                                LatestTransItemUnitCostPrice = this.getTransItem(LatestTransItemId).getUnitCostPrice();
                            } catch (NullPointerException npe) {
                                LatestTransItemUnitCostPrice = 0;
                            }
                        }
                        if (LatestTransItemUnitCostPrice > 0) {
                            s.setUnitCost(LatestTransItemUnitCostPrice);
                        } else {
                            s.setUnitCost(aTransItem.getUnitCostPrice());
                        }

                        try {
                            s.setItemExpDate(aTransItem.getItemExpryDate());
                            s.setItemMnfDate(aTransItem.getItemMnfDate());
                        } catch (NullPointerException npe) {
                            s.setItemExpDate(null);
                            s.setItemMnfDate(null);
                        }
                        try {
                            if (aTransItem.getSpecific_size() > 0) {
                                s.setSpecific_size(aTransItem.getSpecific_size());
                            } else {
                                s.setSpecific_size(1);
                            }
                        } catch (NullPointerException npe) {
                            s.setSpecific_size(1);
                        }
                        int x = 0;
                        x = new StockBean().saveStock(s);
                        new Stock_ledgerBean().callInsertStock_ledger("Add", s, (-1 * aDiffHistNewQty), "Edit", aTransTypeId, aTransItem.getTransactionId(), new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    }
                }
            }
            if ("HIRE INVOICE".equals(transtype.getTransactionTypeName())) {
                Trans t = new TransBean().getTrans(aTransItem.getTransactionId());
                if (aDiffHistNewQty > 0) {
                    //Add/insert stock_out
                    Stock_out sout = new Stock_out();
                    int x = 0;
                    sout.setStore_id(store.getStoreId());
                    sout.setItem_id(aTransItem.getItemId());
                    sout.setBatchno(aTransItem.getBatchno());
                    sout.setCode_specific(aTransItem.getCodeSpecific());
                    sout.setDesc_specific(aTransItem.getDescSpecific());
                    sout.setQty_out(aDiffHistNewQty);
                    sout.setTransactor_id(t.getTransactorId());
                    sout.setSite_id(t.getSite_id());
                    sout.setTransaction_id(aTransItem.getTransactionId());
                    x = new Stock_outBean().InsertOrUpdateStock_out(sout, "Add");
                } else if (aDiffHistNewQty < 0) {
                    //subtract/insert stock_out
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity
                    Stock_out sout = new Stock_out();
                    int x = 0;
                    sout.setStore_id(store.getStoreId());
                    sout.setItem_id(aTransItem.getItemId());
                    sout.setBatchno(aTransItem.getBatchno());
                    sout.setCode_specific(aTransItem.getCodeSpecific());
                    sout.setDesc_specific(aTransItem.getDescSpecific());
                    sout.setQty_out(aDiffHistNewQty);
                    sout.setTransactor_id(t.getTransactorId());
                    sout.setSite_id(t.getSite_id());
                    sout.setTransaction_id(aTransItem.getTransactionId());
                    x = new Stock_outBean().InsertOrUpdateStock_out(sout, "Subtract");
                }
            }
            if ("HIRE RETURN NOTE".equals(transtype.getTransactionTypeName())) {
                Trans t = new TransBean().getTrans(aTransItem.getTransactionId());
                Trans aRefTrans = new TransBean().getTransByTransNumber(t.getTransactionRef());
                //for returned good
                if (aDiffHistNewQty > 0) {
                    //Add/insert stock_out
                    Stock_out sout = new Stock_out();
                    int x = 0;
                    sout.setStore_id(store.getStoreId());
                    sout.setItem_id(aTransItem.getItemId());
                    sout.setBatchno(aTransItem.getBatchno());
                    sout.setCode_specific(aTransItem.getCodeSpecific());
                    sout.setDesc_specific(aTransItem.getDescSpecific());
                    sout.setQty_out(aDiffHistNewQty);
                    sout.setTransactor_id(aRefTrans.getTransactorId());
                    sout.setSite_id(aRefTrans.getSite_id());
                    sout.setTransaction_id(aRefTrans.getTransactionId());
                    x = new Stock_outBean().InsertOrUpdateStock_out(sout, "Add");
                } else if (aDiffHistNewQty < 0) {
                    //subtract/insert stock_out
                    aDiffHistNewQty = (-1) * aDiffHistNewQty;//remove the -ve from the quantity
                    Stock_out sout = new Stock_out();
                    int x = 0;
                    sout.setStore_id(store.getStoreId());
                    sout.setItem_id(aTransItem.getItemId());
                    sout.setBatchno(aTransItem.getBatchno());
                    sout.setCode_specific(aTransItem.getCodeSpecific());
                    sout.setDesc_specific(aTransItem.getDescSpecific());
                    sout.setQty_out(aDiffHistNewQty);
                    sout.setTransactor_id(aRefTrans.getTransactorId());
                    sout.setSite_id(aRefTrans.getSite_id());
                    sout.setTransaction_id(aRefTrans.getTransactionId());
                    x = new Stock_outBean().InsertOrUpdateStock_out(sout, "Subtract");
                }
                //for returned damage/lost
                if (aDiffHistNewQty_damage > 0) {
                    //+ update stock_out for damage
                    Stock_out sout = new Stock_out();
                    int x = 0;
                    sout.setStore_id(store.getStoreId());
                    sout.setItem_id(aTransItem.getItemId());
                    sout.setBatchno(aTransItem.getBatchno());
                    sout.setCode_specific(aTransItem.getCodeSpecific());
                    sout.setDesc_specific(aTransItem.getDescSpecific());
                    sout.setQty_out(aDiffHistNewQty_damage);
                    sout.setTransactor_id(aRefTrans.getTransactorId());
                    sout.setSite_id(aRefTrans.getSite_id());
                    sout.setTransaction_id(aRefTrans.getTransactionId());
                    x = new Stock_outBean().InsertOrUpdateStock_out(sout, "Add");
                    //- update if any damage/lost item has been returned
                    if (x == 1) {
                        Stock stock2 = new Stock();
                        int x2 = 0;
                        stock2.setStoreId(store.getStoreId());
                        stock2.setItemId(aTransItem.getItemId());
                        stock2.setBatchno(aTransItem.getBatchno());
                        stock2.setCodeSpecific(aTransItem.getCodeSpecific());
                        stock2.setDescSpecific(aTransItem.getDescSpecific());
                        x2 = new StockBean().updateStockDamage(stock2, aDiffHistNewQty_damage, "Subtract");
                    }
                } else if (aDiffHistNewQty_damage < 0) {
                    aDiffHistNewQty_damage = (-1) * aDiffHistNewQty_damage;//remove the -ve from the quantity
                    //- update stock_out for damage
                    Stock_out sout = new Stock_out();
                    int x = 0;
                    sout.setStore_id(store.getStoreId());
                    sout.setItem_id(aTransItem.getItemId());
                    sout.setBatchno(aTransItem.getBatchno());
                    sout.setCode_specific(aTransItem.getCodeSpecific());
                    sout.setDesc_specific(aTransItem.getDescSpecific());
                    sout.setQty_out(aDiffHistNewQty_damage);
                    sout.setTransactor_id(aRefTrans.getTransactorId());
                    sout.setSite_id(aRefTrans.getSite_id());
                    sout.setTransaction_id(aRefTrans.getTransactionId());
                    x = new Stock_outBean().InsertOrUpdateStock_out(sout, "Subtract");
                    //- update if any damage/lost item has been returned
                    if (x == 1) {
                        Stock stock2 = new Stock();
                        int x2 = 0;
                        stock2.setStoreId(store.getStoreId());
                        stock2.setItemId(aTransItem.getItemId());
                        stock2.setBatchno(aTransItem.getBatchno());
                        stock2.setCodeSpecific(aTransItem.getCodeSpecific());
                        stock2.setDescSpecific(aTransItem.getDescSpecific());
                        x2 = new StockBean().updateStockDamage(stock2, aDiffHistNewQty_damage, "Add");
                    }
                }
            }
            StkBean = null;
            Stk = null;
        }
    }

    public TransItem getTransItem(long aTransactionItemId) {
        String sql = "{call sp_search_transaction_item_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransItemFromResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public TransItem getTransItemByTransAndItem(long aTransactionItemId, long aItemId) {
        String sql = "SELECT * FROM transaction_item WHERE transaction_id=" + aTransactionItemId + " AND item_id=" + aItemId;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransItemFromResultSet(rs);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("getTransItemByTransAndItem:" + e.getMessage());
            return null;
        }
    }

    public TransItem getTransItemFromResultSet(ResultSet aResultSet) {
        try {
            TransItem transitem = new TransItem();
            try {
                transitem.setTransactionItemId(aResultSet.getLong("transaction_item_id"));
            } catch (NullPointerException npe) {
                transitem.setTransactionItemId(0);
            }
            try {
                transitem.setTransactionId(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                transitem.setTransactionId(0);
            }
            try {
                transitem.setItemId(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                transitem.setItemId(0);
            }
            try {
                transitem.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                transitem.setBatchno("");
            }
            try {
                transitem.setItemQty(aResultSet.getDouble("item_qty"));
            } catch (NullPointerException npe) {
                transitem.setItemQty(0);
            }

            try {
                transitem.setUnitPrice(aResultSet.getDouble("unit_price"));
            } catch (NullPointerException npe) {
                transitem.setUnitPrice(0);
            }

            try {
                transitem.setItemMnfDate(new Date(aResultSet.getDate("item_mnf_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setItemMnfDate(null);
            }
            try {
                transitem.setItemExpryDate(new Date(aResultSet.getDate("item_expiry_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setItemExpryDate(null);
            }

            try {
                transitem.setUnitTradeDiscount(aResultSet.getDouble("unit_trade_discount"));
            } catch (NullPointerException npe) {
                transitem.setUnitTradeDiscount(0);
            }

            try {
                transitem.setUnitVat(aResultSet.getDouble("unit_vat"));
            } catch (NullPointerException npe) {
                transitem.setUnitVat(0);
            }

            try {
                transitem.setAmount(aResultSet.getDouble("amount"));
            } catch (NullPointerException npe) {
                transitem.setAmount(0);
            }

            try {
                transitem.setVatRated(aResultSet.getString("vat_rated"));
            } catch (NullPointerException npe) {
                transitem.setVatRated("");
            }

            try {
                transitem.setVatPerc(aResultSet.getDouble("vat_perc"));
            } catch (NullPointerException npe) {
                transitem.setVatPerc(0);
            }

            try {
                transitem.setUnitPriceIncVat(aResultSet.getDouble("unit_price_inc_vat"));
            } catch (NullPointerException npe) {
                transitem.setUnitPriceIncVat(0);
            }

            try {
                transitem.setUnitPriceExcVat(aResultSet.getDouble("unit_price_exc_vat"));
            } catch (NullPointerException npe) {
                transitem.setUnitPriceExcVat(0);
            }

            try {
                transitem.setAmountIncVat(aResultSet.getDouble("amount_inc_vat"));
            } catch (NullPointerException npe) {
                transitem.setAmountIncVat(0);
            }

            try {
                transitem.setAmountExcVat(aResultSet.getDouble("amount_exc_vat"));
            } catch (NullPointerException npe) {
                transitem.setAmountExcVat(0);
            }

            try {
                transitem.setStockEffect(aResultSet.getString("stock_effect"));
            } catch (NullPointerException npe) {
                transitem.setStockEffect("");
            }

            try {
                transitem.setIsTradeDiscountVatLiable(aResultSet.getString("is_trade_discount_vat_liable"));
            } catch (NullPointerException npe) {
                transitem.setIsTradeDiscountVatLiable("");
            }

            //for report only
            try {
                transitem.setDescription(aResultSet.getString("description"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setDescription("");
            }
            try {
                transitem.setUnitSymbol(aResultSet.getString("unit_symbol"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setUnitSymbol("");
            }
            try {
                transitem.setTransactionDate(new Date(aResultSet.getDate("transaction_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionDate(null);
            }
            try {
                transitem.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAddDate(null);
            }
            try {
                transitem.setEditDate(new Date(aResultSet.getTimestamp("edit_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEditDate(null);
            }
            try {
                transitem.setStoreName(aResultSet.getString("store_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setStoreName("");
            }
            try {
                transitem.setStoreName2(aResultSet.getString("store_name2"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setStoreName2("");
            }
            try {
                transitem.setTransactorNames(aResultSet.getString("transactor_names"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactorNames("");
            }
            try {
                transitem.setBillTransactorName(aResultSet.getString("bill_transactor_names"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setBillTransactorName("");
            }
            try {
                transitem.setTransactionTypeName(aResultSet.getString("transaction_type_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionTypeName("");
            }
            try {
                transitem.setTransactionReasonName(aResultSet.getString("transaction_reason_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionReasonName("");
            }
            try {
                transitem.setAddUserDetailName(aResultSet.getString("add_user_detail_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAddUserDetailName("");
            }
            try {
                transitem.setEditUserDetailName(aResultSet.getString("edit_user_detail_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEditUserDetailName("");
            }
            try {
                transitem.setTransactionUserDetailName(aResultSet.getString("transaction_user_detail_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionUserDetailName("");
            }
            try {
                transitem.setUnitCostPrice(aResultSet.getDouble("unit_cost_price"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setUnitCostPrice(0);
            }
            try {
                transitem.setUnitProfitMargin(aResultSet.getDouble("unit_profit_margin"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setUnitProfitMargin(0);
            }
            try {
                transitem.setEarnPerc(aResultSet.getDouble("earn_perc"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEarnPerc(0);
            }
            try {
                transitem.setEarnAmount(aResultSet.getDouble("earn_amount"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEarnAmount(0);
            }
            try {
                transitem.setTransactionUserDetailId(aResultSet.getInt("transaction_user_detail_id"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionUserDetailId(0);
            }
            try {
                transitem.setBillTransactorId(aResultSet.getLong("bill_transactor_id"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setBillTransactorId(0);
            }
            try {
                transitem.setCodeSpecific(aResultSet.getString("code_specific"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setCodeSpecific("");
            }
            try {
                transitem.setDescSpecific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setDescSpecific("");
            }
            try {
                transitem.setDescMore(aResultSet.getString("desc_more"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setDescMore("");
            }
            try {
                transitem.setWarrantyDesc(aResultSet.getString("warranty_desc"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setWarrantyDesc("");
            }
            try {
                transitem.setAddDate(new Date(aResultSet.getDate("warranty_expiry_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAddDate(null);
            }
            try {
                transitem.setAccountCode(aResultSet.getString("account_code"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAccountCode("");
            }
            try {
                transitem.setPurchaseDate(new Date(aResultSet.getDate("purchase_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setPurchaseDate(null);
            }
            try {
                transitem.setDepStartDate(new Date(aResultSet.getDate("dep_start_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setDepStartDate(null);
            }
            try {
                transitem.setDepMethodId(aResultSet.getInt("dep_method_id"));
            } catch (NullPointerException npe) {
                transitem.setDepMethodId(0);
            }
            try {
                transitem.setDepRate(aResultSet.getDouble("dep_rate"));
            } catch (NullPointerException npe) {
                transitem.setDepRate(0);
            }
            try {
                transitem.setAverageMethodId(aResultSet.getInt("average_method_id"));
            } catch (NullPointerException npe) {
                transitem.setAverageMethodId(0);
            }
            try {
                transitem.setEffectiveLife(aResultSet.getInt("effective_life"));
            } catch (NullPointerException npe) {
                transitem.setEffectiveLife(0);
            }
            try {
                transitem.setResidualValue(aResultSet.getDouble("residual_value"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setResidualValue(0);
            }
            try {
                transitem.setNarration(aResultSet.getString("narration"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setNarration("");
            }
            try {
                transitem.setQty_balance(aResultSet.getDouble("qty_balance"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setQty_balance(0);
            }
            try {
                transitem.setDuration_value(aResultSet.getDouble("duration_value"));
            } catch (NullPointerException npe) {
                transitem.setDuration_value(0);
            }

            try {
                transitem.setQty_damage(aResultSet.getDouble("qty_damage"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setQty_damage(0);
            }
            try {
                transitem.setDuration_passed(aResultSet.getDouble("duration_passed"));
            } catch (NullPointerException npe) {
                transitem.setDuration_passed(0);
            }
            try {
                if (aResultSet.getDouble("specific_size") > 0) {
                    transitem.setSpecific_size(aResultSet.getDouble("specific_size"));
                } else {
                    transitem.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                transitem.setSpecific_size(1);
            }
            return transitem;
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        }

    }

    public void setTransItemFromResultSet(TransItem transitem, ResultSet aResultSet) {
        try {
            //TransItem transitem = new TransItem();
            try {
                transitem.setTransactionItemId(aResultSet.getLong("transaction_item_id"));
            } catch (NullPointerException npe) {
                transitem.setTransactionItemId(0);
            }
            try {
                transitem.setTransactionId(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                transitem.setTransactionId(0);
            }
            try {
                transitem.setItemId(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                transitem.setItemId(0);
            }
            try {
                transitem.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                transitem.setBatchno("");
            }
            try {
                transitem.setItemQty(aResultSet.getDouble("item_qty"));
            } catch (NullPointerException npe) {
                transitem.setItemQty(0);
            }

            try {
                transitem.setUnitPrice(aResultSet.getDouble("unit_price"));
            } catch (NullPointerException npe) {
                transitem.setUnitPrice(0);
            }

            try {
                transitem.setItemMnfDate(new Date(aResultSet.getDate("item_mnf_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setItemMnfDate(null);
            }

            try {
                transitem.setItemExpryDate(new Date(aResultSet.getDate("item_expiry_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setItemExpryDate(null);
            }

            try {
                transitem.setUnitTradeDiscount(aResultSet.getDouble("unit_trade_discount"));
            } catch (NullPointerException npe) {
                transitem.setUnitTradeDiscount(0);
            }

            try {
                transitem.setUnitVat(aResultSet.getDouble("unit_vat"));
            } catch (NullPointerException npe) {
                transitem.setUnitVat(0);
            }

            try {
                transitem.setAmount(aResultSet.getDouble("amount"));
            } catch (NullPointerException npe) {
                transitem.setAmount(0);
            }

            try {
                transitem.setVatRated(aResultSet.getString("vat_rated"));
            } catch (NullPointerException npe) {
                transitem.setVatRated("");
            }

            try {
                transitem.setVatPerc(aResultSet.getDouble("vat_perc"));
            } catch (NullPointerException npe) {
                transitem.setVatPerc(0);
            }

            try {
                transitem.setUnitPriceIncVat(aResultSet.getDouble("unit_price_inc_vat"));
            } catch (NullPointerException npe) {
                transitem.setUnitPriceIncVat(0);
            }

            try {
                transitem.setUnitPriceExcVat(aResultSet.getDouble("unit_price_exc_vat"));
            } catch (NullPointerException npe) {
                transitem.setUnitPriceExcVat(0);
            }

            try {
                transitem.setAmountIncVat(aResultSet.getDouble("amount_inc_vat"));
            } catch (NullPointerException npe) {
                transitem.setAmountIncVat(0);
            }

            try {
                transitem.setAmountExcVat(aResultSet.getDouble("amount_exc_vat"));
            } catch (NullPointerException npe) {
                transitem.setAmountExcVat(0);
            }

            try {
                transitem.setStockEffect(aResultSet.getString("stock_effect"));
            } catch (NullPointerException npe) {
                transitem.setStockEffect("");
            }

            try {
                transitem.setIsTradeDiscountVatLiable(aResultSet.getString("is_trade_discount_vat_liable"));
            } catch (NullPointerException npe) {
                transitem.setIsTradeDiscountVatLiable("");
            }

            //for report only
            try {
                transitem.setDescription(aResultSet.getString("description"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setDescription("");
            }
            try {
                transitem.setUnitSymbol(aResultSet.getString("unit_symbol"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setUnitSymbol("");
            }
            try {
                transitem.setTransactionDate(new Date(aResultSet.getDate("transaction_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionDate(null);
            }
            try {
                transitem.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAddDate(null);
            }
            try {
                transitem.setEditDate(new Date(aResultSet.getTimestamp("edit_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEditDate(null);
            }
            try {
                transitem.setStoreName(aResultSet.getString("store_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setStoreName("");
            }
            try {
                transitem.setStoreName2(aResultSet.getString("store_name2"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setStoreName2("");
            }
            try {
                transitem.setTransactorNames(aResultSet.getString("transactor_names"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactorNames("");
            }
            try {
                transitem.setBillTransactorName(aResultSet.getString("bill_transactor_names"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setBillTransactorName("");
            }
            try {
                transitem.setTransactionTypeName(aResultSet.getString("transaction_type_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionTypeName("");
            }
            try {
                transitem.setTransactionReasonName(aResultSet.getString("transaction_reason_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionReasonName("");
            }
            try {
                transitem.setAddUserDetailName(aResultSet.getString("add_user_detail_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAddUserDetailName("");
            }
            try {
                transitem.setEditUserDetailName(aResultSet.getString("edit_user_detail_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEditUserDetailName("");
            }
            try {
                transitem.setTransactionUserDetailName(aResultSet.getString("transaction_user_detail_name"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionUserDetailName("");
            }
            try {
                transitem.setUnitCostPrice(aResultSet.getDouble("unit_cost_price"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setUnitCostPrice(0);
            }
            try {
                transitem.setUnitProfitMargin(aResultSet.getDouble("unit_profit_margin"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setUnitProfitMargin(0);
            }
            try {
                transitem.setEarnPerc(aResultSet.getDouble("earn_perc"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEarnPerc(0);
            }
            try {
                transitem.setEarnAmount(aResultSet.getDouble("earn_amount"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setEarnAmount(0);
            }
            try {
                transitem.setTransactionUserDetailId(aResultSet.getInt("transaction_user_detail_id"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setTransactionUserDetailId(0);
            }
            try {
                transitem.setBillTransactorId(aResultSet.getLong("bill_transactor_id"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setBillTransactorId(0);
            }
            try {
                transitem.setCodeSpecific(aResultSet.getString("code_specific"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setCodeSpecific("");
            }
            try {
                transitem.setDescSpecific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setDescSpecific("");
            }
            try {
                transitem.setDescMore(aResultSet.getString("desc_more "));
            } catch (NullPointerException | SQLException npe) {
                transitem.setDescMore("");
            }
            try {
                transitem.setWarrantyDesc(aResultSet.getString("warranty_desc"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setWarrantyDesc("");
            }
            try {
                transitem.setAddDate(new Date(aResultSet.getDate("warranty_expiry_date").getTime()));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAddDate(null);
            }
            try {
                transitem.setAccountCode(aResultSet.getString("account_code"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setAccountCode("");
            }
            try {
                transitem.setPurchaseDate(new Date(aResultSet.getDate("purchase_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setPurchaseDate(null);
            }
            try {
                transitem.setDepStartDate(new Date(aResultSet.getDate("dep_start_date").getTime()));
            } catch (NullPointerException npe) {
                transitem.setDepStartDate(null);
            }
            try {
                transitem.setDepMethodId(aResultSet.getInt("dep_method_id"));
            } catch (NullPointerException npe) {
                transitem.setDepMethodId(0);
            }
            try {
                transitem.setDepRate(aResultSet.getDouble("dep_rate"));
            } catch (NullPointerException npe) {
                transitem.setDepRate(0);
            }
            try {
                transitem.setAverageMethodId(aResultSet.getInt("average_method_id"));
            } catch (NullPointerException npe) {
                transitem.setAverageMethodId(0);
            }
            try {
                transitem.setEffectiveLife(aResultSet.getInt("effective_life"));
            } catch (NullPointerException npe) {
                transitem.setEffectiveLife(0);
            }
            try {
                transitem.setResidualValue(aResultSet.getDouble("residual_value"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setResidualValue(0);
            }
            try {
                transitem.setNarration(aResultSet.getString("narration"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setNarration("");
            }
            try {
                transitem.setQty_balance(aResultSet.getDouble("qty_balance"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setQty_balance(0);
            }
            try {
                transitem.setDuration_value(aResultSet.getDouble("duration_value"));
            } catch (NullPointerException npe) {
                transitem.setDuration_value(0);
            }
            try {
                transitem.setQty_damage(aResultSet.getDouble("qty_damage"));
            } catch (NullPointerException | SQLException npe) {
                transitem.setQty_damage(0);
            }
            try {
                transitem.setDuration_passed(aResultSet.getDouble("duration_passed"));
            } catch (NullPointerException npe) {
                transitem.setDuration_passed(0);
            }
            try {
                if (aResultSet.getDouble("specific_size") > 0) {
                    transitem.setSpecific_size(aResultSet.getDouble("specific_size"));
                } else {
                    transitem.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                transitem.setSpecific_size(1);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public TransItem getTransItemFromResultSetBillReport(ResultSet aResultSet) {
        TransItem transitem = new TransItem();
        try {
            transitem.setCategoryName(aResultSet.getString("category_name"));
        } catch (NullPointerException | SQLException npe) {
            transitem.setCategoryName("");
        }
        try {
            transitem.setSumAmountExcVat(aResultSet.getDouble("sum_amount_exc_vat"));
        } catch (NullPointerException | SQLException npe) {
            transitem.setSumAmountExcVat(0);
        }
        try {
            transitem.setSumAmountIncVat(aResultSet.getDouble("sum_amount_inc_vat"));
        } catch (NullPointerException | SQLException npe) {
            transitem.setSumAmountIncVat(0);
        }
        return transitem;
    }

    public List<TransItem> getReportTransItem(Trans aTrans, TransItem aTransItem) {
        String sql;
        sql = "{call sp_report_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        ResultSet rs = null;
        this.ReportTransItem.clear();
        if (aTrans != null && aTransItem != null) {
            if (aTrans.getTransactionDate() == null && aTrans.getTransactionDate2() == null
                    && aTrans.getAddDate() == null && aTrans.getAddDate2() == null
                    && aTrans.getEditDate() == null && aTrans.getEditDate2() == null && aTransItem.getTransactionId() == 0) {
                this.ActionMessage = (("Atleast one date range(TransactionDate,AddDate,EditDate) is needed..."));
            } else if (aTrans.getTransactionDate() == null && aTrans.getTransactionDate2() != null) {
                this.ActionMessage = (("Transaction Date(From) is needed..."));
            } else if (aTrans.getTransactionDate() != null && aTrans.getTransactionDate2() == null) {
                this.ActionMessage = (("Transaction Date(T0) is needed..."));
            } else if (aTrans.getAddDate() == null && aTrans.getAddDate2() != null) {
                this.ActionMessage = (("Add Date(From) is needed..."));
            } else if (aTrans.getAddDate() != null && aTrans.getAddDate2() == null) {
                this.ActionMessage = (("Add Date(To) is needed..."));
            } else if (aTrans.getEditDate() == null && aTrans.getEditDate2() != null) {
                this.ActionMessage = (("Edit Date(From) is needed..."));
            } else if (aTrans.getEditDate() != null && aTrans.getEditDate2() == null) {
                this.ActionMessage = (("Edit Date(To) is needed..."));
            } else {
                try (
                        Connection conn = DBConnection.getMySQLConnection();
                        PreparedStatement ps = conn.prepareStatement(sql);) {
                    try {
                        ps.setDate(1, new java.sql.Date(aTrans.getTransactionDate().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setDate(1, null);
                    }
                    try {
                        ps.setDate(2, new java.sql.Date(aTrans.getTransactionDate2().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setDate(2, null);
                    }
                    try {
                        ps.setInt(3, aTrans.getStoreId());
                    } catch (NullPointerException npe) {
                        ps.setInt(3, 0);
                    }
                    try {
                        ps.setInt(4, aTrans.getStore2Id());
                    } catch (NullPointerException npe) {
                        ps.setInt(4, 0);
                    }
                    try {
                        ps.setLong(5, aTrans.getTransactorId());
                    } catch (NullPointerException npe) {
                        ps.setLong(5, 0);
                    }
                    try {
                        ps.setInt(6, aTrans.getTransactionTypeId());
                    } catch (NullPointerException npe) {
                        ps.setInt(6, 0);
                    }
                    try {
                        ps.setInt(7, aTrans.getTransactionReasonId());
                    } catch (NullPointerException npe) {
                        ps.setInt(7, 0);
                    }
                    try {
                        ps.setInt(8, aTrans.getAddUserDetailId());
                    } catch (NullPointerException npe) {
                        ps.setInt(8, 0);
                    }
                    try {
                        ps.setTimestamp(9, new java.sql.Timestamp(aTrans.getAddDate().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setTimestamp(9, null);
                    }
                    try {
                        ps.setTimestamp(10, new java.sql.Timestamp(aTrans.getAddDate2().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setTimestamp(10, null);
                    }
                    try {
                        ps.setInt(11, aTrans.getEditUserDetailId());
                    } catch (NullPointerException npe) {
                        ps.setInt(11, 0);
                    }
                    try {
                        ps.setTimestamp(12, new java.sql.Timestamp(aTrans.getEditDate().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setTimestamp(12, null);
                    }
                    try {
                        ps.setTimestamp(13, new java.sql.Timestamp(aTrans.getEditDate2().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setTimestamp(13, null);
                    }
                    try {
                        ps.setLong(14, aTransItem.getTransactionId());
                    } catch (NullPointerException npe) {
                        ps.setLong(14, 0);
                    }
                    try {
                        ps.setLong(15, aTransItem.getItemId());
                    } catch (NullPointerException npe) {
                        ps.setLong(15, 0);
                    }
                    try {
                        ps.setInt(16, aTrans.getTransactionUserDetailId());
                    } catch (NullPointerException npe) {
                        ps.setInt(16, 0);
                    }
                    try {
                        ps.setLong(17, aTrans.getBillTransactorId());
                    } catch (NullPointerException npe) {
                        ps.setLong(17, 0);
                    }

                    rs = ps.executeQuery();
                    //System.out.println(rs.getStatement());
                    while (rs.next()) {
                        this.ReportTransItem.add(this.getTransItemFromResultSet(rs));
                    }
                    this.ActionMessage = ((""));
                } catch (SQLException se) {
                    System.err.println(se.getMessage());
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                }
            }
        }
        return this.ReportTransItem;
    }

    public List<TransItem> getReportTransItemUserEarn(Trans aTrans) {
        String sql;
        sql = "{call sp_report_transaction_item_user_earn(?,?,?,?,?,?)}";
        ResultSet rs = null;
        this.ReportTransItem.clear();
        if (aTrans != null) {
            if (aTrans.getTransactionDate() == null || aTrans.getTransactionDate2() == null) {
                this.ActionMessage = (("Select date range(Transaction Date)..."));
            } else if (aTrans.getTransactionDate() == null && aTrans.getTransactionDate2() != null) {
                this.ActionMessage = (("Transaction Date(From) is needed..."));
            } else if (aTrans.getTransactionDate() != null && aTrans.getTransactionDate2() == null) {
                this.ActionMessage = (("Transaction Date(T0) is needed..."));
            } else {
                try (
                        Connection conn = DBConnection.getMySQLConnection();
                        PreparedStatement ps = conn.prepareStatement(sql);) {
                    try {
                        ps.setDate(1, new java.sql.Date(aTrans.getTransactionDate().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setDate(1, null);
                    }
                    try {
                        ps.setDate(2, new java.sql.Date(aTrans.getTransactionDate2().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setDate(2, null);
                    }
                    try {
                        ps.setInt(3, aTrans.getStoreId());
                    } catch (NullPointerException npe) {
                        ps.setInt(3, 0);
                    }
                    try {
                        ps.setInt(4, aTrans.getTransactionTypeId());
                    } catch (NullPointerException npe) {
                        ps.setInt(4, 0);
                    }
                    try {
                        ps.setInt(5, aTrans.getTransactionReasonId());
                    } catch (NullPointerException npe) {
                        ps.setInt(5, 0);
                    }
                    try {
                        ps.setInt(6, aTrans.getTransactionUserDetailId());
                    } catch (NullPointerException npe) {
                        ps.setInt(6, 0);
                    }

                    rs = ps.executeQuery();
                    //System.out.println(rs.getStatement());
                    while (rs.next()) {
                        this.ReportTransItem.add(this.getTransItemFromResultSet(rs));
                    }
                    this.ActionMessage = ((""));
                } catch (SQLException se) {
                    System.err.println(se.getMessage());
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                }
            }
        }
        return this.ReportTransItem;
    }

    public List<TransItem> getReportBillTransItemsSummary(long aTransId) {
        String sql;
        sql = "{call sp_report_bill_items_summary(?)}";
        ResultSet rs = null;
        this.ReportTransItem.clear();
        if (aTransId > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                try {
                    ps.setLong(1, aTransId);
                } catch (NullPointerException npe) {
                    ps.setLong(1, 0);
                }

                rs = ps.executeQuery();
                //System.out.println(rs.getStatement());
                while (rs.next()) {
                    this.ReportTransItem.add(this.getTransItemFromResultSetBillReport(rs));
                }
                this.ActionMessage = ((""));
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        }
        return this.ReportTransItem;
    }

    public void ViewTransItemPriceHistory(long aItemId, int aTransTypeId, int aTransReasonId, int aLimit) {
        try {
            if (aItemId > 0 && aTransTypeId > 0 && aTransReasonId > 0 & aLimit > 0) {
                this.refreshPriceHistory(aItemId, aTransTypeId, aTransReasonId, aLimit);
                //open the view in a dialog
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("modal", true);
                options.put("draggable", true);
                options.put("resizable", true);
                options.put("contentWidth", 600);
                options.put("contentHeight", 300);
                options.put("scrollable", true);
                if (aTransTypeId == 9) {
                    org.primefaces.PrimeFaces.current().dialog().openDynamic("ReportPurchasePriceHistory.xhtml", options, null);
                } else if (aTransTypeId == 1) {
                    org.primefaces.PrimeFaces.current().dialog().openDynamic("ReportPurchasePriceHistoryPI.xhtml", options, null);
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    public void refreshPriceHistory(long aItemId, int aTransTypeId, int aTransReasonId, int aLimit) {
        this.TransItems = this.getTransItemsByItemTtTr(aItemId, aTransTypeId, aTransReasonId, aLimit);
    }

    public List<TransItem> getTransItemsByItemTtTr(long aItemId, int aTransTypeId, int aTransReasonId, int aLimit) {
        String sql;
        sql = "{call sp_search_transaction_item_by_tt_tr_ii(?,?,?,?)}";
        ResultSet rs = null;
        List<TransItem> tis = new ArrayList<>();
        if (aItemId > 0 && aTransTypeId > 0 && aTransReasonId > 0 & aLimit > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, aItemId);
                ps.setInt(2, aTransTypeId);
                ps.setInt(3, aTransReasonId);
                ps.setInt(4, aLimit);
                rs = ps.executeQuery();
                while (rs.next()) {
                    tis.add(this.getTransItemFromResultSet(rs));
                }
                this.ActionMessage = ((""));
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        }
        return tis;
    }

    public List<TransItem> getReportTransItemPriceHistory(long aItemId, int aTransTypeId, int aTransReasonId, int aLimit) {
        String sql;
        sql = "{call sp_search_transaction_item_by_tt_tr_ii(?,?,?,?)}";
        ResultSet rs = null;
        this.ReportTransItem.clear();
        if (aItemId > 0 && aTransTypeId > 0 && aTransReasonId > 0 & aLimit > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, aItemId);
                ps.setInt(2, aTransTypeId);
                ps.setInt(3, aTransReasonId);
                ps.setInt(4, aLimit);
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.ReportTransItem.add(this.getTransItemFromResultSet(rs));
                }
                this.ActionMessage = ((""));
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        }
        return this.ReportTransItem;
    }

    public List<TransItem> getReportTransItemPriceHistory_OLD() {
        String sql;
        sql = "{call sp_report_transaction_item(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        ResultSet rs = null;
        this.ReportTransItem.clear();
        long aItemId = 0;
        try {
            aItemId = new GeneralUserSetting().getCurrentItemId();
        } catch (NullPointerException npe) {
            aItemId = 0;
        }
        if (aItemId != 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setDate(1, null);
                ps.setDate(2, null);
                ps.setInt(3, 0);
                ps.setInt(4, 0);
                ps.setLong(5, 0);
                ps.setInt(6, 1);//1 for purchase
                ps.setInt(7, 0);
                ps.setInt(8, 0);
                ps.setTimestamp(9, null);
                ps.setTimestamp(10, null);
                ps.setInt(11, 0);
                ps.setTimestamp(12, null);
                ps.setTimestamp(13, null);
                ps.setLong(14, 0);
                ps.setLong(15, aItemId);
                ps.setInt(16, 0);
                ps.setLong(17, 0);
                rs = ps.executeQuery();
                while (rs.next()) {
                    this.ReportTransItem.add(this.getTransItemFromResultSet(rs));
                }
                this.ActionMessage = ((""));
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        }
        return this.ReportTransItem;
    }

    public long getReportTransItemCount() {
        return this.ReportTransItem.size();
    }

    public void deleteTransItem(TransItem transitem) {
        String sql = "DELETE FROM transaction_item WHERE transaction_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, transitem.getTransactionItemId());
            ps.executeUpdate();
            this.setActionMessage("Deleted Successfully!");
            //this.clearTransItem(transitem);
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            this.setActionMessage("TransItem NOT deleted");
        }
    }

    public int deleteTransItemsCEC(long aTransId) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_item WHERE transaction_item_id>0 && transaction_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransId);
            ps.executeUpdate();
            deleted = 1;
        } catch (SQLException se) {
            deleted = 0;
            System.err.println("deleteTransItemsCEC:" + se.getMessage());
        }
        return deleted;
    }

    public void displayTransItem(TransItem TransItemFrom, TransItem TransItemTo) {
        TransItemTo.setTransactionItemId(TransItemFrom.getTransactionItemId());
        TransItemTo.setTransactionId(TransItemFrom.getTransactionId());
        TransItemTo.setItemId(TransItemFrom.getItemId());
        TransItemTo.setBatchno(TransItemFrom.getBatchno());
        TransItemTo.setItemQty(TransItemFrom.getItemQty());
        TransItemTo.setUnitPrice(TransItemFrom.getUnitPrice());
        TransItemTo.setItemExpryDate(TransItemFrom.getItemExpryDate());
        TransItemTo.setItemMnfDate(TransItemFrom.getItemMnfDate());
        //add for unit vat, etc
    }

    public List<TransItem> getTransItemsByTransactionId(long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id(?)}";
        ResultSet rs = null;
        setTransItems(new ArrayList<TransItem>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            TransItem ti = null;
            while (rs.next()) {
                ti = this.getTransItemFromResultSet(rs);
                this.updateLookUpsUI(ti);
                getTransItems().add(ti);
            }
        } catch (SQLException se) {
            System.err.println("getTransItemsByTransactionId:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return getTransItems();
    }

    public List<TransItem> getTransItemsFromHist(long aTransactionHistId) {
        String sql;
        sql = "{call sp_search_transaction_item_hist_by_hist_id(?)}";
        ResultSet rs = null;
        setTransItems(new ArrayList<TransItem>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionHistId);
            rs = ps.executeQuery();
            while (rs.next()) {
                getTransItems().add(this.getTransItemFromResultSet(rs));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return getTransItems();
    }

    public void setTransItemsFromHist(List<TransItem> aTransItems, long aTransactionHistId) {
        String sql;
        sql = "{call sp_search_transaction_item_hist_by_hist_id(?)}";
        ResultSet rs = null;
        aTransItems.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionHistId);
            rs = ps.executeQuery();
            TransItem ti;
            while (rs.next()) {
                ti = this.getTransItemFromResultSet(rs);
                this.updateLookUpsUI(ti);
                aTransItems.add(ti);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public List<TransItem> getTransItemsSummaryByAccount(long aTransactionId) {
        String sql;
        sql = "{call sp_search_trans_item_summary_by_account(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setAccountCode(rs.getString("account_code"));
                transitem.setAmountExcVat(rs.getDouble("amount_exc_vat"));
                transitem.setAmountIncVat(rs.getDouble("amount_inc_vat"));
                transitems.add(transitem);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return transitems;
    }

    public List<TransItem> getTransItemsSummaryByItemType(long aTransactionId) {
        String sql;
        sql = "{call sp_search_trans_item_summary_by_item_type(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setItem_type(rs.getString("item_type"));
                transitem.setAmountExcVat(rs.getDouble("amount_exc_vat"));
                transitem.setAmountIncVat(rs.getDouble("amount_inc_vat"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getTransItemsSummaryByItemType:" + e.getMessage());
        }
        return transitems;
    }

    public List<TransItem> getInventoryCostByTrans(long aTransactionId) {
        String sql;
        sql = "{call sp_search_inventory_cost_by_trans(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setAccountCode(rs.getString("account_code"));
                transitem.setUnitCostPrice(rs.getDouble("unit_cost_price"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getInventoryCostByTrans:" + e.getMessage());
        }
        return transitems;
    }

    public List<TransItem> getInventoryCostByTransDispose(long aTransactionId) {
        String sql;
        sql = "{call sp_search_inventory_cost_by_trans_dispose(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setAccountCode(rs.getString("account_code"));
                transitem.setAmountExcVat(rs.getDouble("amount_exc_vat"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getInventoryCostByTransDispose:" + e.getMessage());
        }
        return transitems;
    }

    public List<TransItem> getInventoryCostByTransConsume(long aTransactionId) {
        String sql;
        sql = "{call sp_search_inventory_cost_by_trans_consume(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setAccountCode(rs.getString("account_code"));
                transitem.setAmountExcVat(rs.getDouble("amount_exc_vat"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getInventoryCostByTransConsume:" + e.getMessage());
        }
        return transitems;
    }

    public List<TransItem> getInventoryCostByTransInput(long aTransactionId) {
        String sql;
        sql = "{call sp_search_inventory_cost_by_trans_input(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setAccountCode(rs.getString("account_code"));
                transitem.setAmount(rs.getDouble("input_total_cost"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getInventoryCostByTransInput:" + e.getMessage());
        }
        return transitems;
    }
    
    public List<TransItem> getInventoryCostByTransOutput(long aTransactionId) {
        String sql;
        sql = "{call sp_search_inventory_cost_by_trans_output(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setAccountCode(rs.getString("account_code"));
                transitem.setAmount(rs.getDouble("output_total_cost"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getInventoryCostByTransOutput:" + e.getMessage());
        }
        return transitems;
    }

    public List<TransItem> getInventoryItemTypeCostByTrans(long aTransactionId) {
        String sql;
        sql = "{call sp_search_inventory_item_type_cost_by_trans(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setItem_type(rs.getString("item_type"));
                transitem.setUnitCostPrice(rs.getDouble("unit_cost_price"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getInventoryItemTypeCostByTrans:" + e.getMessage());
        }
        return transitems;
    }

    public List<TransItem> getInventoryItemTypeCostByTransConsume(long aTransactionId) {
        String sql;
        sql = "{call sp_search_inventory_item_type_cost_by_trans_consume(?)}";
        ResultSet rs = null;
        List<TransItem> transitems = new ArrayList<>();
        TransItem transitem = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                transitem = new TransItem();
                transitem.setItem_type(rs.getString("item_type"));
                transitem.setAmountExcVat(rs.getDouble("amount_exc_vat"));
                transitems.add(transitem);
            }
        } catch (Exception e) {
            System.err.println("getInventoryItemTypeCostByTransConsume:" + e.getMessage());
        }
        return transitems;
    }

    public void resetTransactionItem(int aResetType, List<TransItem> aTransItems) {//1-TransId only;2-TransId & all amounts EXC qty
        List<TransItem> ati = aTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            if (aResetType == 1 || aResetType == 2 || aResetType == 3) {
                ati.get(ListItemIndex).setTransactionItemId(0);
            }
            if (aResetType == 2) {
                ati.get(ListItemIndex).setUnitPrice(0);
                ati.get(ListItemIndex).setUnitPriceIncVat(0);
                ati.get(ListItemIndex).setUnitPriceExcVat(0);
                ati.get(ListItemIndex).setAmount(0);
                ati.get(ListItemIndex).setAmountIncVat(0);
                ati.get(ListItemIndex).setAmountExcVat(0);
            }
            if (aResetType == 3) {//set unit cost price for item receive 3
                ati.get(ListItemIndex).setUnitCostPrice(ati.get(ListItemIndex).getUnitPriceIncVat());
            }
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void assignTransItemsByTransactionId(long aTransactionId, List<TransItem> aTransItems) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id(?)}";
        ResultSet rs = null;
        //TransItems = new ArrayList<TransItem>();
        aTransItems.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                TransItem ti = this.getTransItemFromResultSet(rs);
                this.updateLookUpsUI(ti);
                aTransItems.add(ti);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void assignTransItemsByTransactionId2(long aTransactionId, List<TransItem> aTransItems) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id(?)}";
        ResultSet rs = null;
        //TransItems = new ArrayList<TransItem>();
        aTransItems.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                aTransItems.add(this.getTransItemFromResultSet(rs));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public List<TransItem> getTransItemsByTransactionId2(long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id2(?)}";
        ResultSet rs = null;
        setTransItems(new ArrayList<>());
        total_items_list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                i = i + 1;
                TransItem ti = this.getTransItemFromResultSet(rs);
                ti.setItem_no(i);
                getTransItems().add(ti);
                //TransItems.add(this.getTransItemFromResultSet(rs));
            }
            this.total_items = i;
            //get for total_items_list
            for (int x = 1; x <= (15 - this.total_items); x++) {
                total_items_list.add(x);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return getTransItems();
    }

    public List<TransItem> getTransItemsByTransactionIdCEC(long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id2(?)}";
        ResultSet rs = null;
        List<TransItem> tis = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                i = i + 1;
                TransItem ti = this.getTransItemFromResultSet(rs);
                ti.setItem_no(i);
                this.updateLookUpsUI(ti);
                tis.add(ti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tis;
    }

    public List<TransItem> getTransItemsByTransactionId3(long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id3(?)}";
        ResultSet rs = null;
        setTransItems(new ArrayList<TransItem>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                getTransItems().add(this.getTransItemFromResultSet(rs));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return getTransItems();
    }

    public void setTransItemsHistoryByIDs(long aTransactionId, long aTransactionHistId, List<TransItem> hTransItems) {
        String sql;
        sql = "{call sp_search_transaction_item_hist_by_ids(?,?)}";
        ResultSet rs = null;
        hTransItems.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            ps.setLong(2, aTransactionHistId);
            rs = ps.executeQuery();
            while (rs.next()) {
                hTransItems.add(this.getTransItemFromResultSet(rs));
            }
        } catch (SQLException se) {
            System.err.println("setTransItemsHistoryByIDs:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void setTransItemsByTransactionId(List<TransItem> aTransItems, long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_id(?)}";
        ResultSet rs = null;
        aTransItems.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            while (rs.next()) {
                aTransItems.add(this.getTransItemFromResultSet(rs));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void setTransItemsByTransactionNumber(List<TransItem> aTransItems, String aTransactionNumber) {
        String sql;
        sql = "{call sp_search_transaction_item_by_transaction_number(?)}";
        ResultSet rs = null;
        aTransItems.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransactionNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                aTransItems.add(this.getTransItemFromResultSet(rs));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void updateTransItemBatchDates(TransItem aTransItemToUpdate) {
        //get item's stock details
        try {
            StockBean sb = new StockBean();
            Stock st = new Stock();
            st = sb.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aTransItemToUpdate.getItemId(), aTransItemToUpdate.getBatchno(), aTransItemToUpdate.getCodeSpecific(), aTransItemToUpdate.getDescSpecific());
            aTransItemToUpdate.setItemExpryDate(st.getItemExpDate());
            aTransItemToUpdate.setItemMnfDate(st.getItemMnfDate());
        } catch (NullPointerException npe) {
            aTransItemToUpdate.setItemExpryDate(null);
            aTransItemToUpdate.setItemMnfDate(null);
        }
    }

    public void addTransItem(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem) {
        double IncludedVat;
        double ExcludedVat;
        double VatPercent;
        double xrate = 1;
        double XrateMultiply = 1;

        try {
            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            try {
                if ("ITEM RECEIVED".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    xrate = 1;
                } else {
                    xrate = new AccXrateBean().getXrate(aSelectedItem.getCurrencyCode(), aTrans.getCurrencyCode());
                }
            } catch (NullPointerException npe) {
                xrate = 1;
            }
            try {
                if (aSelectedItem.getCurrencyCode().equals(LocalCurrency.getCurrencyCode()) && !aTrans.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                    XrateMultiply = 1 / xrate;
                } else {
                    XrateMultiply = xrate;
                }
            } catch (NullPointerException npe) {
                XrateMultiply = 1;
            }

            if (null == NewTransItem.getBatchno()) {
                NewTransItem.setBatchno("");
            }
            if (null == NewTransItem.getCodeSpecific()) {
                NewTransItem.setCodeSpecific("");
            }
            if (null == NewTransItem.getDescSpecific()) {
                NewTransItem.setDescSpecific("");
            }
            if (null == NewTransItem.getNarration()) {
                NewTransItem.setNarration("");
            }
            //update vat perc to be used
            if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) && ("COST-PRICE SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType()) || "EXEMPT SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType()))) {
                VatPercent = 0;
            } else {
                VatPercent = CompanySetting.getVatPerc();
            }

            //Update Override prices
            if (("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "HIRE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "HIRE QUOTATION".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) && NewTransItem.isOverridePrices()) {
                NewTransItem.setUnitPrice(NewTransItem.getUnitPrice2());
                NewTransItem.setVatRated(NewTransItem.getVatRated2());
                NewTransItem.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount2());
            }

            //get item's stock details
            //get and check number of batches
            StockBean sb = new StockBean();
            Stock st = new Stock();
            st = sb.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());

            //unpacking - AutoUnPackModule
            //process items that might need to be unpacked
            if (aSelectedItem.getIsTrack() == 1 && "SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) & "Yes".equals(CompanySetting.getIsAllowAutoUnpack())) {
//                double TotalQtyFromList = 0;
//                try {
//                    TotalQtyFromList = this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());
//                } catch (NullPointerException npe) {
//                    TotalQtyFromList = 0;
//                }
//                double TotalQtyFromListAndNew = TotalQtyFromList + NewTransItem.getItemQty();
//
//                double SmallItemQtyNeeded = 0;
//                try {
//                    SmallItemQtyNeeded = TotalQtyFromListAndNew - st.getCurrentqty();
//                } catch (NullPointerException npe) {
//                    SmallItemQtyNeeded = TotalQtyFromListAndNew;
//                }
//                //Unpackaging if needed
//                if (st == null || st.getCurrentqty() == 0) {//this item and batch does not exist in this store OR its quatity is 0 (out of stock)
//                    if (this.autoUnpackItem(NewTransItem, SmallItemQtyNeeded) == 1) {
//                    } else {
//                    }
//                } else if ((NewTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific())) > st.getCurrentqty()) {
//                    //double TotalQtyFromList=this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno());
//                    if (this.autoUnpackItem(NewTransItem, SmallItemQtyNeeded) == 1) {
//                    } else {
//                    }
//                }
//                //re-calculate stock for small item after unpackaing
//                st = sb.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());
            }

            //reset messages
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(0);
            int StockFail1 = 0, StockFail2 = 0, StockFail3 = 0;
            if (aSelectedItem.getIsTrack() == 1) {
                if (st == null || st.getCurrentqty() == 0) {//this item and batch does not exist in this store OR its quatity is 0 (out of stock)
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("SELECTED ITEM and BATCH - DOES NOT EXIST or IS OUT OF STOCK !");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                    StockFail1 = 1;
                }
            }
            if (aSelectedItem.getIsTrack() == 1 && null != st) {
                if ((NewTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific())) > st.getCurrentqty()) {
                    //check if supplied qty + existing qty is more than total current stock qty
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("SELECTED ITEM and BATCH - INSUFFICIENT STOCK !");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                    StockFail2 = 1;
                }
            }
            if (NewTransItem.getItemQty() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("ENTER ITEM QUANTITY !");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                StockFail3 = 1;
            }

            if (StockFail1 == 0 && StockFail2 == 0 && StockFail3 == 0) {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setUnitCostPrice(NewTransItem.getUnitCostPrice());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());
                //for UNPACK
                ti.setItemId2(NewTransItem.getItemId2());
                ti.setItemQty2(NewTransItem.getItemQty2());
                //for HIRE
                ti.setDuration_value(NewTransItem.getDuration_value());

                //Check if this is a vatable item
                if ("STANDARD".equals(NewTransItem.getVatRated()) && ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "PURCHASE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "HIRE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()))) {
                    //this is a vatable item
                    ti.setVatPerc(VatPercent);
                    //Check if VAT is Inclusive or Excuksive
                    if ("Yes".equals(CompanySetting.getIsVatInclusive())) {
                        //VAT - Inclusive
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) - (100 * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);

                        } else {
                            //do nothing; IncVat=IncVat
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = NewTransItem.getUnitPrice() - (100 * NewTransItem.getUnitPrice() / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);
                        }
                    } else {
                        //VAT - Exclusive
                        ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ExcludedVat = (VatPercent / 100) * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount());
                        } else {
                            ExcludedVat = (VatPercent / 100) * NewTransItem.getUnitPrice();
                        }
                        ti.setUnitVat(ExcludedVat);
                        ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + ExcludedVat);
                    }

                } else {
                    //this ISNT a vatable item
                    ti.setVatPerc(0);
                    ti.setUnitVat(0);
                    ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                    ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                }
                if (aSelectedItem.getIsTrack() == 1) {
                    ti.setItemExpryDate(st.getItemExpDate());
                    ti.setItemMnfDate(st.getItemMnfDate());
                } else {
                    ti.setItemExpryDate(null);
                    ti.setItemMnfDate(null);
                }
                if ("HIRE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                    ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                } else {
                    ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                }
                //for profit margin
                if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    ti.setUnitCostPrice(XrateMultiply * new StockBean().getItemUnitCostPrice(new GeneralUserSetting().getCurrentStore().getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific()));
                    ti.setUnitProfitMargin((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) - ti.getUnitCostPrice());
                } else {
                    ti.setUnitCostPrice(0);
                    ti.setUnitProfitMargin(0);
                }

                //bms veriabales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setStockId(NewTransItem.getStockId());
                } catch (NullPointerException npe) {
                    ti.setStockId(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    if ("HIRE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "HIRE QUOTATION".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                        ti.setAmount(ti.getUnitPrice() * ti.getItemQty() * ti.getDuration_value());
                        ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                        ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                    } else {
                        ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
                        ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                        ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    }
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, null);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            System.err.println("addTransItem:" + npe.getMessage());
        }
    }

    public String addTransItemCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem) {
        String status = "";
        double IncludedVat;
        double ExcludedVat;
        double VatPercent;
        double xrate = 1;
        double XrateMultiply = 1;

        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);

        try {
            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            try {
                if ("ITEM RECEIVED".equals(transtype.getTransactionTypeName())) {
                    xrate = 1;
                } else {
                    xrate = new AccXrateBean().getXrate(aSelectedItem.getCurrencyCode(), aTrans.getCurrencyCode());
                }
            } catch (NullPointerException npe) {
                xrate = 1;
            }
            try {
                if (aSelectedItem.getCurrencyCode().equals(LocalCurrency.getCurrencyCode()) && !aTrans.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                    XrateMultiply = 1 / xrate;
                } else {
                    XrateMultiply = xrate;
                }
            } catch (NullPointerException npe) {
                XrateMultiply = 1;
            }
            //check for selected specific stock item
            try {
                if (NewTransItem.getStockId() > 0) {
                    this.updateTransItemBatchSpecific(NewTransItem, NewTransItem.getStockId());
                }
            } catch (Exception e) {
            }

            if (null == NewTransItem.getBatchno()) {
                NewTransItem.setBatchno("");
            }
            if (null == NewTransItem.getCodeSpecific()) {
                NewTransItem.setCodeSpecific("");
            }
            if (null == NewTransItem.getDescSpecific()) {
                NewTransItem.setDescSpecific("");
            }
            if (null == NewTransItem.getNarration()) {
                NewTransItem.setNarration("");
            }
            //update vat perc to be used
            if ("SALE INVOICE".equals(transtype.getTransactionTypeName()) && ("COST-PRICE SALE INVOICE".equals(aSaleType) || "EXEMPT SALE INVOICE".equals(aSaleType))) {
                VatPercent = 0;
            } else {
                VatPercent = CompanySetting.getVatPerc();
            }

            //Update Override prices
            if (("SALE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE QUOTATION".equals(transtype.getTransactionTypeName())) && NewTransItem.isOverridePrices() || "HIRE RETURN INVOICE".equals(transtype.getTransactionTypeName())) {
                NewTransItem.setUnitPrice(NewTransItem.getUnitPrice2());
                NewTransItem.setVatRated(NewTransItem.getVatRated2());
                NewTransItem.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount2());
            }

            //get item's stock details
            //get and check number of batches
            StockBean sb = new StockBean();
            Stock st = new Stock();
            st = sb.getStock(store.getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());

            //unpacking - AutoUnPackModule
            //process items that might need to be unpacked
            if (aSelectedItem.getIsTrack() == 1 && "SALE INVOICE".equals(transtype.getTransactionTypeName()) & "Yes".equals(CompanySetting.getIsAllowAutoUnpack()) && new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("0")) {
                double TotalQtyFromList = 0;
                try {
                    TotalQtyFromList = this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    TotalQtyFromList = 0;
                }
                double TotalQtyFromListAndNew = TotalQtyFromList + NewTransItem.getItemQty();

                double SmallItemQtyNeeded = 0;
                try {
                    SmallItemQtyNeeded = TotalQtyFromListAndNew - st.getCurrentqty();
                } catch (NullPointerException npe) {
                    SmallItemQtyNeeded = TotalQtyFromListAndNew;
                }
                //Unpackaging if needed
                if (st == null || st.getCurrentqty() == 0) {//this item and batch does not exist in this store OR its quatity is 0 (out of stock)
                    if (this.autoUnpackItem(NewTransItem, SmallItemQtyNeeded) == 1) {
                    } else {
                    }
                } else if ((NewTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific())) > st.getCurrentqty()) {
                    //double TotalQtyFromList=this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno());
                    if (this.autoUnpackItem(NewTransItem, SmallItemQtyNeeded) == 1) {
                    } else {
                    }
                }
                //re-calculate stock for small item after unpackaing
                st = sb.getStock(store.getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());
            }
            int StockFail1 = 0, StockFail2 = 0, StockFail3 = 0;
            if (aSelectedItem.getIsTrack() == 1 && !"HIRE RETURN INVOICE".equals(transtype.getTransactionTypeName())) {
                if (transtype.getTransactionTypeName().equals("STOCK ADJUSTMENT")) {
                    if (NewTransItem.getNarration().equals("Subtract") && (st == null || st.getCurrentqty() == 0)) {//this item and batch does not exist in this store OR its quatity is 0 (out of stock)
                        status = "SELECTED ITEM and BATCH - DOES NOT EXIST or IS OUT OF STOCK !";
                        StockFail1 = 1;
                    }
                } else {
                    if (transtype.getTransactionTypeName().equals("SALE INVOICE") && !new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("0")) {
                        //ignore check
                    } else {
                        if (st == null || st.getCurrentqty() == 0) {//this item and batch does not exist in this store OR its quatity is 0 (out of stock)
                            status = "SELECTED ITEM and BATCH - DOES NOT EXIST or IS OUT OF STOCK !";
                            StockFail1 = 1;
                        }
                    }
                }
            }
            if (aSelectedItem.getIsTrack() == 1 && null != st && !"HIRE RETURN INVOICE".equals(transtype.getTransactionTypeName())) {
                if (transtype.getTransactionTypeName().equals("STOCK ADJUSTMENT")) {
                    if (NewTransItem.getNarration().equals("Subtract") && ((NewTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific())) > st.getCurrentqty())) {
                        //check if supplied qty + existing qty is more than total current stock qty
                        status = "SELECTED ITEM and BATCH - INSUFFICIENT STOCK !";
                        StockFail2 = 1;
                    }
                } else {
                    if (transtype.getTransactionTypeName().equals("SALE INVOICE") && !new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("0")) {
                        //ignore check
                    } else {
                        if ((NewTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific())) > st.getCurrentqty()) {
                            //check if supplied qty + existing qty is more than total current stock qty
                            status = "SELECTED ITEM and BATCH - INSUFFICIENT STOCK !";
                            StockFail2 = 1;
                        }
                    }
                }
            }

            //for dispose,consume; apply unit cost price of the stock/batch
            if (aSelectedItem.getIsTrack() == 1 && null != st && ("DISPOSE STOCK".equals(transtype.getTransactionTypeName()) || "STOCK CONSUMPTION".equals(transtype.getTransactionTypeName()))) {
                NewTransItem.setUnitCostPrice(xrate * st.getUnitCost());
                NewTransItem.setUnitPrice(NewTransItem.getUnitCostPrice());
                NewTransItem.setUnitPriceExcVat(NewTransItem.getUnitCostPrice());
                NewTransItem.setAmountExcVat(NewTransItem.getItemQty() * NewTransItem.getUnitPriceExcVat());
            }

            if (NewTransItem.getItemQty() <= 0) {
                status = "ENTER ITEM QUANTITY !";
                StockFail3 = 1;
            }
            if (StockFail1 == 0 && StockFail2 == 0 && StockFail3 == 0) {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setUnitCostPrice(NewTransItem.getUnitCostPrice());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());
                //for UNPACK
                ti.setItemId2(NewTransItem.getItemId2());
                ti.setItemQty2(NewTransItem.getItemQty2());
                //for HIRE
                ti.setDuration_value(NewTransItem.getDuration_value());

                //Check if this is a vatable item
                if ("STANDARD".equals(NewTransItem.getVatRated()) && ("SALE INVOICE".equals(transtype.getTransactionTypeName()) || "PURCHASE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE RETURN INVOICE".equals(transtype.getTransactionTypeName()))) {
                    //this is a vatable item
                    ti.setVatPerc(VatPercent);
                    //Check if VAT is Inclusive or Excuksive
                    if ("Yes".equals(CompanySetting.getIsVatInclusive())) {
                        //VAT - Inclusive
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) - (100 * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);
                        } else {
                            //do nothing; IncVat=IncVat
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = NewTransItem.getUnitPrice() - (100 * NewTransItem.getUnitPrice() / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);
                        }
                    } else {
                        //VAT - Exclusive
                        ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ExcludedVat = (VatPercent / 100) * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount());
                        } else {
                            ExcludedVat = (VatPercent / 100) * NewTransItem.getUnitPrice();
                        }
                        ti.setUnitVat(ExcludedVat);
                        ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + ExcludedVat);
                    }

                } else {
                    //this ISNT a vatable item
                    ti.setVatPerc(0);
                    ti.setUnitVat(0);
                    ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                    ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                }
                if (aSelectedItem.getIsTrack() == 1) {
                    try {
                        ti.setItemExpryDate(st.getItemExpDate());
                    } catch (NullPointerException npe) {
                        ti.setItemExpryDate(null);
                    }
                    try {
                        ti.setItemMnfDate(st.getItemMnfDate());
                    } catch (NullPointerException npe) {
                        ti.setItemMnfDate(null);
                    }
                } else {
                    ti.setItemExpryDate(null);
                    ti.setItemMnfDate(null);
                }
                if ("HIRE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE QUOTATION".equals(transtype.getTransactionTypeName())) {
                    ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                    ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                } else if ("HIRE RETURN INVOICE".equals(transtype.getTransactionTypeName())) {
                    if (NewTransItem.getNarration().equals("DAMAGED/LOST")) {
                        ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                        ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    } else {
                        ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                        ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                    }
                } else {
                    ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                }
                //for profit margin
                if ("SALE INVOICE".equals(transtype.getTransactionTypeName())) {
                    if (aSelectedItem.getIsTrack() == 1) {
                        ti.setUnitCostPrice(XrateMultiply * new StockBean().getItemUnitCostPrice(store.getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific()));
                    } else {
                        ti.setUnitCostPrice(XrateMultiply * aSelectedItem.getUnitCostPrice());
                    }
                    ti.setUnitProfitMargin((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) - ti.getUnitCostPrice());
                } else if ("STOCK ADJUSTMENT".equals(transtype.getTransactionTypeName())) {
                    //ti.setUnitCostPrice(0);//(this has been already set from UI)
                    ti.setUnitProfitMargin(0);
                } else {
                    ti.setUnitCostPrice(0);
                    ti.setUnitProfitMargin(0);
                }

                //bms veriabales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setStockId(NewTransItem.getStockId());
                } catch (NullPointerException npe) {
                    ti.setStockId(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_total(NewTransItem.getQty_total());
                } catch (NullPointerException npe) {
                    ti.setQty_total(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = 1 - 2;//==-1
                if (!"HIRE RETURN INVOICE".equals(transtype.getTransactionTypeName())) {
                    ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                }
                if (ItemFoundAtIndex == -1) {
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    if ("HIRE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE QUOTATION".equals(transtype.getTransactionTypeName())) {
                        ti.setAmount(ti.getUnitPrice() * ti.getItemQty() * ti.getDuration_value());
                        ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                        ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
                    } else {
                        ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
                        ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                        ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    }
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }
            }
        } catch (NullPointerException npe) {
            status = "ERROR OCCURED - ITEM NOT ADDED";
            System.err.println("addTransItemCEC:" + npe.getMessage());
            npe.printStackTrace();
        }
        return status;
    }

    public void updateLookUpsUI(TransItem aTransItem) {
        try {
            Item item = null;
            if (null == aTransItem) {
                //do nothing
            } else {
                item = new ItemBean().getItem(aTransItem.getItemId());
                aTransItem.setDescription(item.getDescription());
                aTransItem.setAlias_name(item.getAlias_name());
                aTransItem.setDisplay_alias_name(item.getDisplay_alias_name());
                try {
                    aTransItem.setUnitSymbol(new UnitBean().getUnit(item.getUnitId()).getUnitSymbol());
                } catch (NullPointerException npe) {
                    aTransItem.setUnitSymbol("");
                }
                try {
                    aTransItem.setAccountName(new AccCoaBean().getAccCoaByCodeOrId(aTransItem.getAccountCode(), 0).getAccountName());
                } catch (NullPointerException npe) {
                    aTransItem.setAccountName("");
                }
                aTransItem.setItem_currency_code(item.getCurrencyCode());
                aTransItem.setIs_general(item.getIsGeneral());
                aTransItem.setOverride_gen_name(item.getOverride_gen_name());
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUI:" + e.getMessage());
        }
    }

    public void roundTransItemsAmount(Trans aTrans, TransItem aTransItem) {
        aTransItem.setUnitVat(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransItem.getUnitVat()));
        aTransItem.setUnitPriceExcVat(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransItem.getUnitPriceExcVat()));
        aTransItem.setUnitPriceIncVat(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransItem.getUnitPriceIncVat()));
        aTransItem.setUnitProfitMargin(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransItem.getUnitProfitMargin()));
        aTransItem.setAmount(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransItem.getAmount()));
        aTransItem.setAmountIncVat(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransItem.getAmountIncVat()));
        aTransItem.setAmountExcVat(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransItem.getAmountExcVat()));
    }

    public void addTransItemCallCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem) {
        String status = "";
        aStatusBean.setItemAddedStatus("");
        aStatusBean.setItemNotAddedStatus("");
        aStatusBean.setShowItemAddedStatus(0);
        aStatusBean.setShowItemNotAddedStatus(0);
        try {
            //check
            if (aTransTypeId == 71 && NewTransItem.getNarration().length() == 0) {//STOCK ADJUSTMENT
                status = "please select Adjustment Type...";
            } else if (aTransTypeId == 71 && NewTransItem.getUnitCostPrice() == 0) {//STOCK ADJUSTMENT
                status = "please enter Unit Cost...";
            } else if (NewTransItem.getCodeSpecific().length() > 250) {
                status = "Code cannot be more than 250 characters...";
            } else if (NewTransItem.getDescSpecific().length() > 250) {
                status = "Name cannot be more than 250 characters...";
            } else if (NewTransItem.getDescMore().length() > 250) {
                status = "More Description cannot be more than 250 characters...";
            } else {
                status = this.addTransItemCEC(aStoreId, aTransTypeId, aTransReasonId, aSaleType, aTrans, aActiveTransItems, NewTransItem, aSelectedItem);
            }
            if (status.length() > 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus(status);
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                FacesContext.getCurrentInstance().addMessage("Add Item", new FacesMessage(status));
            } else {
                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, null);
                //update totals
                transB.setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);
            }
            //get current stock
            if (aTransTypeId == 4) {
                new TransItemBean().refreshCurrentStock(aActiveTransItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("addTransItemCallCEC:" + e.getMessage());
        }
    }

    public void clearTransItems(Trans aTrans, List<TransItem> aActiveTransItems) {
        try {
            aActiveTransItems.clear();
            //update totals
            new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
        } catch (Exception e) {
            System.err.println("clearTransItems:" + e.getMessage());
        }
    }

    public void addTransItemSaleQuotation(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem) {
        double IncludedVat;
        double ExcludedVat;
        double VatPercent;
        try {
            //update vat perc to be used
            VatPercent = CompanySetting.getVatPerc();
            //set Batch no to 
            NewTransItem.setBatchno("");
            NewTransItem.setCodeSpecific("");
            NewTransItem.setDescSpecific("");

            //Update Override prices
            if (NewTransItem.isOverridePrices()) {
                NewTransItem.setUnitPrice(NewTransItem.getUnitPrice2());
                NewTransItem.setVatRated(NewTransItem.getVatRated2());
                NewTransItem.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount2());
            }
            //reset messages
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(0);
            int StockFail1 = 0, StockFail2 = 0, StockFail3 = 0;
            if (NewTransItem.getItemQty() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("ENTER ITEM QUANTITY !");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                StockFail3 = 1;
            }

            if (StockFail1 == 0 && StockFail2 == 0 && StockFail3 == 0) {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());
                //for UNPACK
                //ti.setItemId2(NewTransItem.getItemId2());
                //ti.setItemQty2(NewTransItem.getItemQty2());

                //Check if this is a vatable item
                if ("STANDARD".equals(NewTransItem.getVatRated())) {
                    //this is a vatable item
                    ti.setVatPerc(VatPercent);
                    //Check if VAT is Inclusive or Excuksive
                    if ("Yes".equals(CompanySetting.getIsVatInclusive())) {
                        //VAT - Inclusive
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) - (100 * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);

                        } else {
                            //do nothing; IncVat=IncVat
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = NewTransItem.getUnitPrice() - (100 * NewTransItem.getUnitPrice() / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);
                        }
                    } else {
                        //VAT - Exclusive
                        ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ExcludedVat = (VatPercent / 100) * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount());
                        } else {
                            ExcludedVat = (VatPercent / 100) * NewTransItem.getUnitPrice();
                        }
                        ti.setUnitVat(ExcludedVat);
                        ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + ExcludedVat);
                    }

                } else {
                    //this ISNT a vatable item
                    ti.setVatPerc(0);
                    ti.setUnitVat(0);
                    ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                    ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                }

                //nullfy expiry and man dates
                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
                //calculate Amt Inc/Exc Vat
                ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //for profit margin set to 0
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
                    ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, null);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
            }
        } catch (NullPointerException npe) {
            System.err.println("addTransItemSalesQuotation:" + npe.getMessage());
        }
    }

    public void initCategoriesQuickOrder() {
        try {
            try {
                this.CategoryList.clear();
            } catch (NullPointerException npe) {
                this.CategoryList = new ArrayList<>();
            }
            this.CategoryList = new CategoryBean().getCategoriesQuickOrder();
        } catch (Exception e) {
        }
    }

    public void initResetDefaultQuickOrder() {
        this.CategoryName = "";
        this.SubCategoryName = "";
        //clear sub categories
        try {
            this.SubCategoryList.clear();
        } catch (NullPointerException npe) {

        }
        //clear item list
        try {
            this.ItemList.clear();
        } catch (NullPointerException npe) {

        }
    }

    public void refreshItemListByCategory(int aCategoryId) {
        try {
            try {
                this.ItemList.clear();
            } catch (NullPointerException npe) {
                this.ItemList = new ArrayList<>();
            }
            this.ItemList = new ItemBean().getItemObjectListForQuickOrder(aCategoryId, 0);
        } catch (Exception e) {
        }
    }

    public void refreshItemListByCategoryNoSubCategory(int aCategoryId) {
        try {
            try {
                this.ItemList.clear();
            } catch (NullPointerException npe) {
                this.ItemList = new ArrayList<>();
            }
            this.ItemList = new ItemBean().getItemObjectListForQuickOrder(aCategoryId, -1);
        } catch (Exception e) {
        }
    }

    public void refreshItemListBySubCategory(SubCategory aSubCategory) {
        if (this.getSubCategoryName().equals(aSubCategory.getSubCategoryName())) {
            this.setSubCategoryName("");
            aSubCategory = null;
            this.ItemList.clear();
        } else {
            this.setSubCategoryName(aSubCategory.getSubCategoryName());
            try {
                try {
                    this.ItemList.clear();
                } catch (NullPointerException npe) {
                    this.ItemList = new ArrayList<>();
                }
                this.ItemList = new ItemBean().getItemObjectListForQuickOrder(0, aSubCategory.getSubCategoryId());
            } catch (Exception e) {
            }
        }
    }

    public void refreshSubCategoryListByCategory(int aCategoryId) {
        try {
            try {
                this.SubCategoryList.clear();
            } catch (NullPointerException npe) {
                this.SubCategoryList = new ArrayList<>();
            }
            this.SubCategoryList = new SubCategoryBean().getSubCategoriesByCategoryId(aCategoryId);
        } catch (Exception e) {
        }
    }

    public void refreshSubCategoryOrItemList(Category aCategory) {
        if (this.getCategoryName().equals(aCategory.getCategoryName())) {
            this.setCategoryName("");
            aCategory = null;
            this.SubCategoryList.clear();
            this.ItemList.clear();
        } else {
            this.setCategoryName(aCategory.getCategoryName());
            this.refreshSubCategoryListByCategory(aCategory.getCategoryId());
            this.refreshItemListByCategoryNoSubCategory(aCategory.getCategoryId());
        }
    }

    public void addTransItemSaleQuotationCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem) {
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);

        double IncludedVat;
        double ExcludedVat;
        double VatPercent;
        try {
            //update vat perc to be used
            VatPercent = CompanySetting.getVatPerc();
            //set Batch no to 
            /*
             NewTransItem.setBatchno("");
             NewTransItem.setCodeSpecific("");
             NewTransItem.setDescSpecific("");
             */

            //Update Override prices
            if (NewTransItem.isOverridePrices()) {
                NewTransItem.setUnitPrice(NewTransItem.getUnitPrice2());
                NewTransItem.setVatRated(NewTransItem.getVatRated2());
                NewTransItem.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount2());
            }
            //reset messages
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(0);
            int StockFail1 = 0, StockFail2 = 0, StockFail3 = 0;
            if (NewTransItem.getItemQty() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("ENTER ITEM QUANTITY !");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                StockFail3 = 1;
            }

            if (NewTransItem.getCodeSpecific().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Code cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                StockFail3 = 1;
            }

            if (NewTransItem.getDescSpecific().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Name cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                StockFail3 = 1;
            }

            if (NewTransItem.getDescMore().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("More Description cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                StockFail3 = 1;
            }

            if (StockFail1 == 0 && StockFail2 == 0 && StockFail3 == 0) {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());
                //for UNPACK
                //ti.setItemId2(NewTransItem.getItemId2());
                //ti.setItemQty2(NewTransItem.getItemQty2());

                //Check if this is a vatable item
                if ("STANDARD".equals(NewTransItem.getVatRated())) {
                    //this is a vatable item
                    ti.setVatPerc(VatPercent);
                    //Check if VAT is Inclusive or Excuksive
                    if ("Yes".equals(CompanySetting.getIsVatInclusive())) {
                        //VAT - Inclusive
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) - (100 * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);

                        } else {
                            //do nothing; IncVat=IncVat
                            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                            IncludedVat = NewTransItem.getUnitPrice() - (100 * NewTransItem.getUnitPrice() / (100 + VatPercent));
                            ti.setUnitVat(IncludedVat);
                            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);
                        }
                    } else {
                        //VAT - Exclusive
                        ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                        if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                            ExcludedVat = (VatPercent / 100) * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount());
                        } else {
                            ExcludedVat = (VatPercent / 100) * NewTransItem.getUnitPrice();
                        }
                        ti.setUnitVat(ExcludedVat);
                        ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + ExcludedVat);
                    }

                } else {
                    //this ISNT a vatable item
                    ti.setVatPerc(0);
                    ti.setUnitVat(0);
                    ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                    ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                }

                //nullfy expiry and man dates
                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
                //calculate Amt Inc/Exc Vat
                ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //for profit margin set to 0
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
                    ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, null);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
            }
        } catch (NullPointerException npe) {
            System.err.println("addTransItemSalesQuotation:" + npe.getMessage());
        }
    }

    public void addTransItemTransferRequest(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem) {
        try {
            //reset messages
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(0);
            int StockFail1 = 0, StockFail2 = 0, StockFail3 = 0;
            if (NewTransItem.getItemQty() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("ENTER ITEM QUANTITY !");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                StockFail1 = 1;
            }
            if (StockFail1 == 0 && StockFail2 == 0 && StockFail3 == 0) {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno("");
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(0);
                ti.setUnitTradeDiscount(0);
                ti.setAmount(0);
                ti.setVatRated(NewTransItem.getVatRated());
                ti.setVatPerc(0);
                ti.setUnitVat(0);
                ti.setUnitPriceIncVat(0);
                ti.setUnitPriceExcVat(0);
                //nullfy expiry and man dates
                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
                //calculate Amt Inc/Exc Vat
                ti.setAmountIncVat(0);
                ti.setAmountExcVat(0);
                //for profit margin set to 0
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);
                //for bms
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
                    ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }
                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, null);
                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                //new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
            }
        } catch (NullPointerException npe) {
            System.err.println("addTransItemTransferRequest:" + npe.getMessage());
        }
    }

    public void editTransItem(int aTransTypeNameId, Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        if (ti.getItemQty() < 0) {
            ti.setItemQty(0);
        }
        if (aTransTypeNameId == 2) {//SALE INVOICE
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 10) {//SALE QUOTATION
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 11) {//SALE ORDER
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 12) {//GOODS DELIVERY
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }

        if (aTransTypeNameId == 1) {//PURCHASE INVOICE
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 8) {//PURCHASE ORDER
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 9) {//GOODS RECEIVED
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }
        if (aTransTypeNameId == 3) {//DISPOSE STOCK
            aTrans.setCashDiscount(0);
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }

        //for profit margin
        if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
            ti.setUnitCostPrice(ti.getUnitCostPrice());
            ti.setUnitProfitMargin((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) - ti.getUnitCostPrice());
        } else {
            ti.setUnitCostPrice(0);
            ti.setUnitProfitMargin(0);
        }
        //round off amounts basing on currency rules
        this.roundTransItemsAmount(aTrans, ti);

        //update totals
        new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
    }

    public void editTransItemCEC(int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        if (ti.getItemQty() < 0) {
            ti.setItemQty(0);
        }
        if (aTransTypeId == 2) {//SALE INVOICE
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 10) {//SALE QUOTATION
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 11) {//SALE ORDER
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 12) {//GOODS DELIVERY
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }
        if (aTransTypeId == 1) {//PURCHASE INVOICE
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 8) {//PURCHASE ORDER
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 9) {//GOODS RECEIVED
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }
        if (aTransTypeId == 3 || aTransTypeId == 72) {//DISPOSE STOCK or STOCK CONSUMPTION
            aTrans.setCashDiscount(0);
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }

        if ("HIRE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE QUOTATION".equals(transtype.getTransactionTypeName())) {
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty() * ti.getDuration_value());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
        } else {
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        //for profit margin
        if ("SALE INVOICE".equals(transtype.getTransactionTypeName())) {
            ti.setUnitCostPrice(ti.getUnitCostPrice());
            ti.setUnitProfitMargin((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) - ti.getUnitCostPrice());
        } else {
            ti.setUnitCostPrice(0);
            ti.setUnitProfitMargin(0);
        }
        //round off amounts basing on currency rules
        this.roundTransItemsAmount(aTrans, ti);
        //update totals
        new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
    }

    public void editTransItemQuickOrder(int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        //first update unit prices
        double IncludedVat;
        double ExcludedVat;
        double VatPercent = ti.getVatPerc();
        //this is a vatable item
        if ("STANDARD".equals(ti.getVatRated())) {
            //Check if VAT is Inclusive or Excuksive
            if ("Yes".equals(CompanySetting.getIsVatInclusive())) {
                //VAT - Inclusive
                if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                    ti.setUnitPriceIncVat(ti.getUnitPrice());
                    IncludedVat = (ti.getUnitPrice() - ti.getUnitTradeDiscount()) - (100 * (ti.getUnitPrice() - ti.getUnitTradeDiscount()) / (100 + VatPercent));
                    ti.setUnitVat(IncludedVat);
                    ti.setUnitPriceExcVat(ti.getUnitPrice() - IncludedVat);
                } else {
                    //do nothing; IncVat=IncVat
                    ti.setUnitPriceIncVat(ti.getUnitPrice());
                    IncludedVat = ti.getUnitPrice() - (100 * ti.getUnitPrice() / (100 + VatPercent));
                    ti.setUnitVat(IncludedVat);
                    ti.setUnitPriceExcVat(ti.getUnitPrice() - IncludedVat);
                }
            } else {
                //VAT - Exclusive
                ti.setUnitPriceExcVat(ti.getUnitPrice());
                if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                    ExcludedVat = (VatPercent / 100) * (ti.getUnitPrice() - ti.getUnitTradeDiscount());
                } else {
                    ExcludedVat = (VatPercent / 100) * ti.getUnitPrice();
                }
                ti.setUnitVat(ExcludedVat);
                ti.setUnitPriceIncVat(ti.getUnitPrice() + ExcludedVat);
            }
        } else {
            //this ISNT a vatable item
            ti.setVatPerc(0);
            ti.setUnitVat(0);
            ti.setUnitPriceIncVat(ti.getUnitPrice());
            ti.setUnitPriceExcVat(ti.getUnitPrice());
        }
        ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

        //calculate prices
        if (ti.getItemQty() < 0) {
            ti.setItemQty(0);
        }
        if (aTransTypeId == 2) {//SALE INVOICE
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 10) {//SALE QUOTATION
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 11) {//SALE ORDER
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 12) {//GOODS DELIVERY
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }
        if (aTransTypeId == 1) {//PURCHASE INVOICE
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 8) {//PURCHASE ORDER
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeId == 9) {//GOODS RECEIVED
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }
        if (aTransTypeId == 3) {//DISPOSE STOCK
            aTrans.setCashDiscount(0);
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }

        if ("HIRE INVOICE".equals(transtype.getTransactionTypeName()) || "HIRE QUOTATION".equals(transtype.getTransactionTypeName())) {
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty() * ti.getDuration_value());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty() * ti.getDuration_value());
        } else {
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        //for profit margin
        if ("SALE INVOICE".equals(transtype.getTransactionTypeName())) {
            ti.setUnitCostPrice(ti.getUnitCostPrice());
            ti.setUnitProfitMargin((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) - ti.getUnitCostPrice());
        } else {
            ti.setUnitCostPrice(0);
            ti.setUnitProfitMargin(0);
        }
        //round off amounts basing on currency rules
        this.roundTransItemsAmount(aTrans, ti);
        //update totals
        new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
    }

    public void editTransItemV2(int aTransTypeNameId, Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        if (ti.getItemQty() < 0) {
            ti.setItemQty(0);
        }
        if (aTransTypeNameId == 2) {//SALE INVOICE
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 10) {//SALE QUOTATION
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 11) {//SALE ORDER
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 12) {//GOODS DELIVERY
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }

        if (aTransTypeNameId == 1) {//PURCHASE INVOICE
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 8) {//PURCHASE ORDER
            ti.setAmount(ti.getItemQty() * (ti.getUnitPrice() + ti.getUnitVat() - ti.getUnitTradeDiscount()));
            ti.setAmountIncVat(ti.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }
        if (aTransTypeNameId == 9) {//GOODS RECEIVED
            ti.setAmount(0);
            ti.setAmountIncVat(0);
            ti.setAmountExcVat(0);
        }
        if (aTransTypeNameId == 3) {//DISPOSE STOCK
            aTrans.setCashDiscount(0);
            ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
        }

        //for profit margin
        if (aTransTypeNameId == 2) {//SALE INVOICE
            ti.setUnitCostPrice(ti.getUnitCostPrice());
            ti.setUnitProfitMargin((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) - ti.getUnitCostPrice());
        } else {
            ti.setUnitCostPrice(0);
            ti.setUnitProfitMargin(0);
        }
        //update totals
        new TransBean().setTransTotalsAndUpdateV2(aTrans, aActiveTransItems);
    }

    public void addTransItemAutoAdd(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, String aEntryMode) {
        double IncludedVat;
        double ExcludedVat;
        double VatPercent;
        try {

            //update vat perc to be used
            if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) && "COST-PRICE SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())) {
                VatPercent = 0;
            } else {
                VatPercent = CompanySetting.getVatPerc();
            }

            //Update Override prices
            if (("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "SALE ORDER".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) && NewTransItem.isOverridePrices()) {
                NewTransItem.setUnitPrice(NewTransItem.getUnitPrice2());
                NewTransItem.setVatRated(NewTransItem.getVatRated2());
                NewTransItem.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount2());
            }

            //get item's stock details
            //get and check number of batches
            StockBean sb = new StockBean();
            Stock st = new Stock();
            st = sb.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());

            //reset messages
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(0);

            TransItem ti = new TransItem();
            ti.setTransactionItemId(NewTransItem.getTransactionItemId());
            ti.setTransactionId(NewTransItem.getTransactionId());
            ti.setItemId(NewTransItem.getItemId());
            ti.setBatchno(NewTransItem.getBatchno());
            ti.setItemQty(NewTransItem.getItemQty());
            ti.setUnitPrice(NewTransItem.getUnitPrice());
            ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
            ti.setAmount(NewTransItem.getAmount());
            ti.setVatRated(NewTransItem.getVatRated());
            //for UNPACK
            ti.setItemId2(NewTransItem.getItemId2());
            ti.setItemQty2(NewTransItem.getItemQty2());

            //Check if this is a vatable item
            if ("STANDARD".equals(NewTransItem.getVatRated()) && ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "SALE ORDER".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "PURCHASE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()))) {
                //this is a vatable item
                ti.setVatPerc(VatPercent);
                //Check if VAT is Inclusive or Excuksive
                if ("Yes".equals(CompanySetting.getIsVatInclusive())) {
                    //VAT - Inclusive
                    if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                        ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                        IncludedVat = (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) - (100 * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount()) / (100 + VatPercent));
                        ti.setUnitVat(IncludedVat);
                        ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);

                    } else {
                        //do nothing; IncVat=IncVat
                        ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                        IncludedVat = NewTransItem.getUnitPrice() - (100 * NewTransItem.getUnitPrice() / (100 + VatPercent));
                        ti.setUnitVat(IncludedVat);
                        ti.setUnitPriceExcVat(NewTransItem.getUnitPrice() - IncludedVat);
                    }
                } else {
                    //VAT - Exclusive
                    ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                    if ("No".equals(CompanySetting.getIsTradeDiscountVatLiable())) {
                        ExcludedVat = (VatPercent / 100) * (NewTransItem.getUnitPrice() - NewTransItem.getUnitTradeDiscount());
                    } else {
                        ExcludedVat = (VatPercent / 100) * NewTransItem.getUnitPrice();
                    }
                    ti.setUnitVat(ExcludedVat);
                    ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + ExcludedVat);
                }

            } else {
                //this ISNT a vatable item
                ti.setVatPerc(0);
                ti.setUnitVat(0);
                ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());
                ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
            }
            try {
                ti.setItemExpryDate(st.getItemExpDate());
                ti.setItemMnfDate(st.getItemMnfDate());
            } catch (NullPointerException npe) {
                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
            }
            ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

            //for profit margin
            if ("SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) || "SALE ORDER".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                ti.setUnitCostPrice(st.getUnitCost());
                ti.setUnitProfitMargin((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) - st.getUnitCost());
            } else {
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);
            }

            //bms veraibales
            try {
                if (null == NewTransItem.getBatchno()) {
                    ti.setBatchno("");
                } else {
                    ti.setBatchno(NewTransItem.getBatchno());
                }
            } catch (NullPointerException npe) {
                ti.setBatchno("");
            }
            try {
                if (null == NewTransItem.getCodeSpecific()) {
                    ti.setCodeSpecific("");
                } else {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                }
            } catch (NullPointerException npe) {
                ti.setCodeSpecific("");
            }
            try {
                if (null == NewTransItem.getDescSpecific()) {
                    ti.setDescSpecific("");
                } else {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                }
            } catch (NullPointerException npe) {
                ti.setDescSpecific("");
            }
            try {
                ti.setDescMore(NewTransItem.getDescMore());
            } catch (NullPointerException npe) {
                ti.setDescMore("");
            }
            try {
                ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
            } catch (NullPointerException npe) {
                ti.setWarrantyDesc("");
            }
            try {
                ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
            } catch (NullPointerException npe) {
                ti.setWarrantyExpiryDate(null);
            }
            try {
                ti.setAccountCode(NewTransItem.getAccountCode());
            } catch (NullPointerException npe) {
                ti.setAccountCode("");
            }
            try {
                ti.setNarration(NewTransItem.getNarration());
            } catch (NullPointerException npe) {
                ti.setNarration("");
            }
            try {
                ti.setQty_balance(NewTransItem.getQty_balance());
            } catch (NullPointerException npe) {
                ti.setQty_balance(0);
            }
            try {
                ti.setDuration_value(NewTransItem.getDuration_value());
            } catch (NullPointerException npe) {
                ti.setDuration_value(0);
            }
            try {
                ti.setQty_damage(NewTransItem.getQty_damage());
            } catch (NullPointerException npe) {
                ti.setQty_damage(0);
            }
            try {
                ti.setDuration_passed(NewTransItem.getDuration_passed());
            } catch (NullPointerException npe) {
                ti.setDuration_passed(0);
            }
            try {
                if (NewTransItem.getSpecific_size() > 0) {
                    ti.setSpecific_size(NewTransItem.getSpecific_size());
                } else {
                    ti.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                ti.setSpecific_size(1);
            }
            //check if itme+batchno already exists
            int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
            if (ItemFoundAtIndex == -1) {
                //round off amounts basing on currency rules
                this.roundTransItemsAmount(aTrans, ti);
                //add
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(0, ti);
                } else {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ti);
                }
            } else {
                ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
                ti.setAmountIncVat((ti.getUnitPriceIncVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());
                //round off amounts basing on currency rules
                this.roundTransItemsAmount(aTrans, ti);
                //add
                this.updateLookUpsUI(ti);
                aActiveTransItems.add(ItemFoundAtIndex, ti);
                aActiveTransItems.remove(ItemFoundAtIndex + 1);
            }

            TransBean transB = new TransBean();
            if (aEntryMode.equals("BarCode")) {
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, null);
            }// for ItemClick - Do not clear

            aStatusBean.setItemAddedStatus("ITEM ADDED");
            aStatusBean.setItemNotAddedStatus("");
            aStatusBean.setShowItemAddedStatus(1);
            aStatusBean.setShowItemNotAddedStatus(0);

            //update totals
            new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            System.err.println("addTransItemAutoAdd:" + npe.getMessage());
        }
    }

    public void addTransItemUNPACK(StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Trans NewTrans, Item aItem) {
        if (aItem.getIsTrack() == 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("A non-trackable item cannot be be UNPACKED... !");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            //check for selected specific stock item
            try {
                if (NewTransItem.getStockId() > 0) {
                    this.updateTransItemBatchSpecific(NewTransItem, NewTransItem.getStockId());
                }
            } catch (Exception e) {
            }

            //get item's stock details
            //get and check number of batches
            StockBean sb = new StockBean();
            Stock st = new Stock();
            st = sb.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific());
            if (st == null || st.getCurrentqty() == 0) {//this item and batch does not exist in this store OR its quatity is 0 (out of stock)
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECTED BIG ITEM and BATCH - DOES NOT EXIST or IS OUT OF STOCK !");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getItemQty() < 0) {
                //BACK
                //check if supplied qty + existing qty is more than total current stock qty
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("QUANTITY CANNOT BE NEGATIVE...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if ((NewTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno(), NewTransItem.getCodeSpecific(), NewTransItem.getDescSpecific())) > st.getCurrentqty()) {
                //BACK
                //check if supplied qty + existing qty is more than total current stock qty
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECTED BIG ITEM and BATCH - INSUFFICIENT STOCK !");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if ("UNPACK".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) && (NewTransItem.getItemId2() == 0 || NewTransItem.getItemQty2() == 0)) {
                //BACK
                //check if supplied qty + existing qty is more than total current stock qty
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECTED ITEM/QUANTITY TO UNPACK TO IS INVALID !");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }

                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());
                //for UNPACK
                ti.setItemId2(NewTransItem.getItemId2());
                ti.setItemQty2(NewTransItem.getItemQty2());
                try {
                    ti.setItemExpryDate(st.getItemExpDate());
                } catch (NullPointerException npe) {
                    ti.setItemExpryDate(null);
                }
                try {
                    ti.setItemMnfDate(st.getItemMnfDate());
                } catch (NullPointerException npe) {
                    ti.setItemMnfDate(null);
                }
                //for profit margin
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);

                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(0, ti);
                } else {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ti);
                }
                TransBean transB = new TransBean();
                transB.saveTrans(NewTrans, aActiveTransItems, null, null, null, null, null, null);
                transB.clearAll(NewTrans, null, NewTransItem, aItem, null, 1, null);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);
            }
        }
    }

    public int autoUnpackItem(TransItem aSmallTransItem, double aSmallItemQtyNeeded) {
        long aBigItemQtyToUnpack = 0;
        try {
            //1. check if Small Item has big item
            ItemMap aItemMap;
            try {
                aItemMap = new ItemMapBean().getItemMapBySmallItemId(aSmallTransItem.getItemId());
                aBigItemQtyToUnpack = Math.round(Math.ceil(aSmallItemQtyNeeded / aItemMap.getFractionQty()));
            } catch (NullPointerException npe) {
                aBigItemQtyToUnpack = 0;
                aItemMap = null;
            }

            //2. check if needed Qty can be unpacked from the Big Item
            if (aItemMap != null & aSmallItemQtyNeeded > 0 & aBigItemQtyToUnpack > 0) {
                //check if at-least 1 qty is available in stock for the BigItem
                Stock aStock;
                aStock = new StockBean().getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aItemMap.getBigItemId(), aSmallTransItem.getBatchno(), aSmallTransItem.getCodeSpecific(), aSmallTransItem.getDescSpecific());
                if (aStock != null && aBigItemQtyToUnpack <= aStock.getCurrentqty()) {
                    //now that Qty is available, unpack it
                    TransItem ti = new TransItem();
                    ti.setItemId(aItemMap.getBigItemId());
                    ti.setBatchno(aSmallTransItem.getBatchno());
                    ti.setItemQty(aBigItemQtyToUnpack);
                    //for UNPACK
                    ti.setItemId2(aSmallTransItem.getItemId());
                    ti.setItemQty2(aBigItemQtyToUnpack * aItemMap.getFractionQty());
                    try {
                        ti.setBatchno(aSmallTransItem.getBatchno());
                    } catch (NullPointerException npe) {
                        ti.setBatchno("");
                    }
                    try {
                        ti.setCodeSpecific(aSmallTransItem.getCodeSpecific());
                    } catch (NullPointerException npe) {
                        ti.setCodeSpecific("");
                    }
                    try {
                        ti.setDescSpecific(aSmallTransItem.getDescSpecific());
                    } catch (NullPointerException npe) {
                        ti.setDescSpecific("");
                    }
                    ti.setItemExpryDate(aStock.getItemExpDate());
                    ti.setItemMnfDate(aStock.getItemMnfDate());

                    //for profit margin
                    ti.setUnitCostPrice(0);
                    ti.setUnitProfitMargin(0);

                    TransBean transB = new TransBean();
                    transB.saveTransAutoUnpack(ti);
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.err.println("autoUnpackItem:" + e.getMessage());
            return 0;
        }
    }

    public void addTransItemPURCHASECallCEC(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        String status = "";
        aStatusBean.setItemAddedStatus("");
        aStatusBean.setItemNotAddedStatus("");
        aStatusBean.setShowItemAddedStatus(0);
        aStatusBean.setShowItemNotAddedStatus(0);
        try {
            if (null != aSelectedItem && aSelectedItem.getIsBuy() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("This item is set up not to be supplied/purchased!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getCodeSpecific().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Code cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getDescSpecific().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Name cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getDescMore().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("More Description cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                if (NewTransItem.getItemQty() <= 0 || NewTransItem.getUnitPrice() <= 0) {
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID ITEM-QUANTITY OR UNIT-PRICE...!");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                } else if (NewTransItem.getItemId() == 0) {
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM...!");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                } else if (NewTransItem.getAccountCode().length() == 0) {
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM ACCOUNT...!");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                } else {
                    status = this.addTransItemPURCHASECEC(aStoreId, aTransTypeId, aTransReasonId, aTrans, aStatusBean, aActiveTransItems, NewTransItem, aSelectedItem, aSelectedAccCoa);
                    if (status.length() > 0) {
                        aStatusBean.setItemAddedStatus("");
                        aStatusBean.setItemNotAddedStatus(status);
                        aStatusBean.setShowItemAddedStatus(0);
                        aStatusBean.setShowItemNotAddedStatus(1);
                        FacesContext.getCurrentInstance().addMessage("Add Item", new FacesMessage(status));
                    } else {
                        TransBean transB = new TransBean();
                        transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);
                        //update totals
                        transB.setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
                        aStatusBean.setItemAddedStatus("ITEM ADDED");
                        aStatusBean.setItemNotAddedStatus("");
                        aStatusBean.setShowItemAddedStatus(1);
                        aStatusBean.setShowItemNotAddedStatus(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("addTransItemPURCHASECallCEC:" + e.getMessage());
        }
    }

    public String addTransItemPURCHASECEC(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        String status = "";
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);
        try {
            TransItem ti = new TransItem();
            ti.setTransactionItemId(NewTransItem.getTransactionItemId());
            ti.setTransactionId(NewTransItem.getTransactionId());
            ti.setItemId(NewTransItem.getItemId());
            ti.setBatchno(NewTransItem.getBatchno());
            ti.setItemQty(NewTransItem.getItemQty());
            ti.setUnitPrice(NewTransItem.getUnitPrice());
            ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
            ti.setAmount(NewTransItem.getAmount());
            ti.setVatRated(NewTransItem.getVatRated());

            ti.setVatPerc(CompanySetting.getVatPerc());
            ti.setUnitVat(NewTransItem.getUnitVat());
            ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
            ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + NewTransItem.getUnitVat());

            ti.setItemExpryDate(NewTransItem.getItemExpryDate());
            ti.setItemMnfDate(NewTransItem.getItemMnfDate());
            ti.setAmountIncVat(NewTransItem.getAmount());
            ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

            //bms veraibales
            try {
                ti.setCodeSpecific(NewTransItem.getCodeSpecific());
            } catch (NullPointerException npe) {
                ti.setCodeSpecific("");
            }
            try {
                ti.setDescSpecific(NewTransItem.getDescSpecific());
            } catch (NullPointerException npe) {
                ti.setDescSpecific("");
            }
            try {
                ti.setDescMore(NewTransItem.getDescMore());
            } catch (NullPointerException npe) {
                ti.setDescMore("");
            }
            try {
                ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
            } catch (NullPointerException npe) {
                ti.setWarrantyDesc("");
            }
            try {
                ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
            } catch (NullPointerException npe) {
                ti.setWarrantyExpiryDate(null);
            }
            try {
                ti.setAccountCode(NewTransItem.getAccountCode());
            } catch (NullPointerException npe) {
                ti.setAccountCode("");
            }
            try {
                ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
            } catch (NullPointerException npe) {
                ti.setPurchaseDate(null);
            }
            try {
                ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
            } catch (NullPointerException npe) {
                ti.setDepStartDate(null);
            }
            try {
                ti.setDepMethodId(NewTransItem.getDepMethodId());
            } catch (NullPointerException npe) {
                ti.setDepMethodId(0);
            }
            try {
                ti.setDepRate(NewTransItem.getDepRate());
            } catch (NullPointerException npe) {
                ti.setDepRate(0);
            }
            try {
                ti.setAverageMethodId(NewTransItem.getAverageMethodId());
            } catch (NullPointerException npe) {
                ti.setAverageMethodId(0);
            }
            try {
                ti.setEffectiveLife(NewTransItem.getEffectiveLife());
            } catch (NullPointerException npe) {
                ti.setEffectiveLife(0);
            }
            try {
                ti.setResidualValue(NewTransItem.getResidualValue());
            } catch (NullPointerException npe) {
                ti.setResidualValue(0);
            }
            try {
                ti.setNarration(NewTransItem.getNarration());
            } catch (NullPointerException npe) {
                ti.setNarration("");
            }
            try {
                if (NewTransItem.getSpecific_size() > 0) {
                    ti.setSpecific_size(NewTransItem.getSpecific_size());
                } else {
                    ti.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                ti.setSpecific_size(1);
            }
            //check if itme+batchno already exists
            int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
            if (ItemFoundAtIndex == -1) {
                //round off amounts basing on currency rules
                this.roundTransItemsAmount(aTrans, ti);
                //add
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(0, ti);
                } else {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ti);
                }
            } else {
                ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                ti.setAmount(ti.getAmount() + aActiveTransItems.get(ItemFoundAtIndex).getAmount());
                ti.setAmountIncVat(ti.getAmountIncVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountIncVat());
                ti.setAmountExcVat(ti.getAmountExcVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountExcVat());
                //round off amounts basing on currency rules
                this.roundTransItemsAmount(aTrans, ti);
                //add
                this.updateLookUpsUI(ti);
                aActiveTransItems.add(ItemFoundAtIndex, ti);
                aActiveTransItems.remove(ItemFoundAtIndex + 1);
            }

            //for profit margin
            ti.setUnitCostPrice(0);
            ti.setUnitProfitMargin(0);
            //TransBean transB = new TransBean();
            //transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);
            //update totals
            //new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
        } catch (Exception e) {
            status = "ERROR OCCURED - ITEM NOT ADDED";
            e.printStackTrace();
            System.err.println("addTransItemPURCHASECEC:" + e.getMessage());
        }
        return status;
    }

    public void addTransItemPURCHASE(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        if (null != aSelectedItem && aSelectedItem.getIsBuy() == 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("This item is set up not to be supplied/purchased!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            if (NewTransItem.getItemQty() <= 0 || NewTransItem.getUnitPrice() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID ITEM-QUANTITY OR UNIT-PRICE...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getItemId() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getAccountCode().length() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM ACCOUNT...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());

                ti.setVatPerc(CompanySetting.getVatPerc());
                ti.setUnitVat(NewTransItem.getUnitVat());
                ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + NewTransItem.getUnitVat());

                ti.setItemExpryDate(NewTransItem.getItemExpryDate());
                ti.setItemMnfDate(NewTransItem.getItemMnfDate());
                ti.setAmountIncVat(NewTransItem.getAmount());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getAmount() + aActiveTransItems.get(ItemFoundAtIndex).getAmount());
                    ti.setAmountIncVat(ti.getAmountIncVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountIncVat());
                    ti.setAmountExcVat(ti.getAmountExcVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountExcVat());
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                //for profit margin
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
            }
        }
    }

    public void addTransItemJournalEntry(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        if (null == aSelectedAccCoa || NewTransItem.getAccountCode().length() <= 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID ACCOUNT...!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else if (aTrans.getTransactorId() <= 0 && aSelectedAccCoa.getIsTransactorMandatory() == 1) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID NAME(CUSTOMER/SUPPLIER/PROVIDER/EMPLOYEE)...!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else if (aSelectedAccCoa.getIsChild() == 1 && NewTransItem.getCodeSpecific().length() < 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("PLEASE SPECIFY CHILD ACCOUNT...!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else if (NewTransItem.getAmountExcVat() <= 0 && NewTransItem.getAmountIncVat() <= 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("PLEASE ENTER CREDIT OR DEBIT AMOUNT...!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            TransItem ti = new TransItem();
            ti.setTransactionItemId(0);
            ti.setTransactionId(0);
            ti.setItemId(0);
            ti.setBatchno("");
            ti.setItemQty(1);
            ti.setUnitPrice(0);
            ti.setUnitTradeDiscount(0);
            ti.setAmount(0);
            ti.setVatRated("");
            ti.setVatPerc(CompanySetting.getVatPerc());
            ti.setUnitVat(0);
            ti.setUnitPriceExcVat(0);
            ti.setUnitPriceIncVat(0);
            ti.setAmountExcVat(NewTransItem.getAmountExcVat());
            ti.setAmountIncVat(NewTransItem.getAmountIncVat());
            ti.setItemExpryDate(null);
            ti.setItemMnfDate(null);
            //bms veraibales
            try {
                ti.setCodeSpecific(NewTransItem.getCodeSpecific());
            } catch (NullPointerException npe) {
                ti.setCodeSpecific("");
            }
            try {
                ti.setDescSpecific(NewTransItem.getDescSpecific());
            } catch (NullPointerException npe) {
                ti.setDescSpecific("");
            }
            try {
                ti.setDescMore(NewTransItem.getDescMore());
            } catch (NullPointerException npe) {
                ti.setDescMore("");
            }
            try {
                ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
            } catch (NullPointerException npe) {
                ti.setWarrantyDesc("");
            }
            try {
                ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
            } catch (NullPointerException npe) {
                ti.setWarrantyExpiryDate(null);
            }
            try {
                ti.setAccountCode(NewTransItem.getAccountCode());
            } catch (NullPointerException npe) {
                ti.setAccountCode("");
            }
            try {
                ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
            } catch (NullPointerException npe) {
                ti.setPurchaseDate(null);
            }
            try {
                ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
            } catch (NullPointerException npe) {
                ti.setDepStartDate(null);
            }
            try {
                ti.setDepMethodId(NewTransItem.getDepMethodId());
            } catch (NullPointerException npe) {
                ti.setDepMethodId(0);
            }
            try {
                ti.setDepRate(NewTransItem.getDepRate());
            } catch (NullPointerException npe) {
                ti.setDepRate(0);
            }
            try {
                ti.setAverageMethodId(NewTransItem.getAverageMethodId());
            } catch (NullPointerException npe) {
                ti.setAverageMethodId(0);
            }
            try {
                ti.setEffectiveLife(NewTransItem.getEffectiveLife());
            } catch (NullPointerException npe) {
                ti.setEffectiveLife(0);
            }
            try {
                ti.setResidualValue(NewTransItem.getResidualValue());
            } catch (NullPointerException npe) {
                ti.setResidualValue(0);
            }
            try {
                ti.setNarration(NewTransItem.getNarration());
            } catch (NullPointerException npe) {
                ti.setNarration("");
            }
            try {
                ti.setQty_balance(NewTransItem.getQty_balance());
            } catch (NullPointerException npe) {
                ti.setQty_balance(0);
            }
            try {
                ti.setDuration_value(NewTransItem.getDuration_value());
            } catch (NullPointerException npe) {
                ti.setDuration_value(0);
            }
            try {
                ti.setQty_damage(NewTransItem.getQty_damage());
            } catch (NullPointerException npe) {
                ti.setQty_damage(0);
            }
            try {
                ti.setDuration_passed(NewTransItem.getDuration_passed());
            } catch (NullPointerException npe) {
                ti.setDuration_passed(0);
            }
            try {
                if (NewTransItem.getSpecific_size() > 0) {
                    ti.setSpecific_size(NewTransItem.getSpecific_size());
                } else {
                    ti.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                ti.setSpecific_size(1);
            }
            //check if itme+batchno already exists
            int ItemFoundAtIndex = itemExistsJournalEntry(aActiveTransItems, ti.getAccountCode(), ti.getCodeSpecific());
            if (ItemFoundAtIndex == -1) {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(0, ti);
                } else {
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ti);
                }
            } else {
                this.updateLookUpsUI(ti);
                aActiveTransItems.add(ItemFoundAtIndex, ti);
                aActiveTransItems.remove(ItemFoundAtIndex + 1);
            }
            //for profit margin
            ti.setUnitCostPrice(0);
            ti.setUnitProfitMargin(0);
            TransBean transB = new TransBean();
            transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);
            aStatusBean.setItemAddedStatus("ITEM ADDED");
            aStatusBean.setItemNotAddedStatus("");
            aStatusBean.setShowItemAddedStatus(1);
            aStatusBean.setShowItemNotAddedStatus(0);

            //update totals
            new TransBean().setTransTotalsAndUpdateJournalEntry(aTrans, aActiveTransItems);
        }
    }

    public int validateOpenBalance(Trans aTrans, List<TransItem> aActiveTransItems, TransItem aTransItem, AccCoa aSelectedAccCoa) {
        int added = 0;
        String msg = "";
        try {
            TransactionType TransType = new TransactionTypeBean().getTransactionType(new GeneralUserSetting().getCurrentTransactionTypeId());
            TransactionReason TransReason = new TransactionReasonBean().getTransactionReason(new GeneralUserSetting().getCurrentTransactionReasonId());
            //customer
            if (TransReason.getTransactionReasonId() == 117) {
                if (aTrans.getTransactorId() <= 0) {
                    msg = "Select Customer";
                } else if (null == aTrans.getTransactionDate()) {
                    msg = "Select Opening Date";
                } else if (aTransItem.getAccountCode().length() == 0) {
                    msg = "Specify Receivable Account";
                } else if (aTransItem.getAmount() <= 0) {
                    msg = "Specify Outstanding Customer Balance";
                } else {
                    aTrans.setGrandTotal(aTransItem.getAmount());
                    msg = "";
                }
            }
            //supplier
            if (TransReason.getTransactionReasonId() == 118) {
                if (aTrans.getTransactorId() <= 0) {
                    msg = "Select Supplier";
                } else if (null == aTrans.getTransactionDate()) {
                    msg = "Select Opening Date";
                } else if (aTransItem.getAccountCode().length() == 0) {
                    msg = "Specify Payable Account";
                } else if (aTransItem.getAmount() <= 0) {
                    msg = "Specify Outstanding Supplier Balance";
                } else {
                    aTrans.setGrandTotal(aTransItem.getAmount());
                    msg = "";
                }
            }
            //cash
            if (TransReason.getTransactionReasonId() == 119) {
                if (null == aTrans.getTransactionDate()) {
                    msg = "Select Opening Date";
                } else if (aTransItem.getAccountCode().length() <= 0) {
                    msg = "Specify Cash Account";
                } else if (aTransItem.getCodeSpecific().length() <= 0) {
                    msg = "Specify Cash Child Account";
                } else if (aTransItem.getAmount() <= 0) {
                    msg = "Specify Cash Account Opening Balance";
                } else {
                    aTrans.setGrandTotal(aTransItem.getAmount());
                    msg = "";
                }
            }
            //other account
            if (TransReason.getTransactionReasonId() == 120) {
                if (null == aTrans.getTransactionDate()) {
                    msg = "Select Opening Date";
                } else if (null == aSelectedAccCoa || aTransItem.getAccountCode().length() <= 0) {
                    msg = "Specify Account";
                } else if (aSelectedAccCoa.getIsChild() == 1 && aTransItem.getCodeSpecific().length() <= 0) {
                    msg = "Specify Child Account";
                } else if (aTransItem.getAmountIncVat() <= 0 && aTransItem.getAmountExcVat() <= 0) {
                    msg = "Specify Account Opening Balance";
                } else if (aTransItem.getAmountIncVat() > 0 && aTransItem.getAmountExcVat() > 0) {
                    msg = "Specify Debit or Credit Amount only but not both...";
                } else {
                    if (aTransItem.getAmountIncVat() > 0) {
                        aTransItem.setAmount(aTransItem.getAmountIncVat());
                        aTrans.setGrandTotal(aTransItem.getAmountIncVat());
                    } else {
                        aTransItem.setAmount(aTransItem.getAmountExcVat());
                        aTrans.setGrandTotal(aTransItem.getAmountExcVat());
                    }
                    msg = "";
                }
            }
            if (msg.length() > 0) {
                FacesContext.getCurrentInstance().addMessage("Opening Balance", new FacesMessage(msg));
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(0);
                ti.setTransactionId(0);
                ti.setItemId(0);
                ti.setBatchno("");
                ti.setItemQty(1);
                ti.setUnitPrice(0);
                ti.setUnitTradeDiscount(0);
                ti.setAmount(0);
                ti.setVatRated("");
                ti.setVatPerc(CompanySetting.getVatPerc());
                ti.setUnitVat(0);
                ti.setUnitPriceExcVat(0);
                ti.setUnitPriceIncVat(0);
                ti.setAmountExcVat(aTransItem.getAmountExcVat());
                ti.setAmountIncVat(aTransItem.getAmountIncVat());
                ti.setAmount(aTransItem.getAmount());
                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
                if (null == aTransItem.getCodeSpecific()) {
                    ti.setCodeSpecific("");
                } else {
                    ti.setCodeSpecific(aTransItem.getCodeSpecific());
                }
                ti.setDescSpecific("");
                ti.setDescMore("");
                ti.setWarrantyDesc("");
                ti.setWarrantyExpiryDate(null);
                if (null == aTransItem.getAccountCode()) {
                    ti.setAccountCode("");
                } else {
                    ti.setAccountCode(aTransItem.getAccountCode());
                }
                ti.setPurchaseDate(null);
                ti.setDepStartDate(null);
                ti.setDepMethodId(0);
                ti.setDepRate(0);
                ti.setAverageMethodId(0);
                ti.setEffectiveLife(0);
                ti.setResidualValue(0);
                ti.setNarration("");
                ti.setQty_balance(0);
                ti.setDuration_value(0);
                ti.setQty_damage(0);
                ti.setDuration_passed(0);
                ti.setSpecific_size(1);
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);
                //add item
                aActiveTransItems.add(ti);
                added = 1;
            }
        } catch (Exception e) {
            System.out.println("validateOpenBalance:" + e.getMessage());
        }
        return added;
    }

    public void addTransItemCashTransfer(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        String FromCurCode = "";
        String ToCurCode = "";
        String FromAccCode = "";
        String ToAccCode = "";
        double FromAmount = 0;
        double ToAmount = 0;
        double FromAccBalance = 0;
        int BalCheckerOn = 0;
        String LocalCurrencyCode = "";
        try {
            LocalCurrencyCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (Exception e) {

        }
        if (null != NewTransItem) {
            FromCurCode = NewTransItem.getBatchno();
            ToCurCode = NewTransItem.getDescSpecific();
            FromAccCode = NewTransItem.getAccountCode();
            ToAccCode = NewTransItem.getCodeSpecific();
            FromAmount = NewTransItem.getAmountIncVat();
            ToAmount = NewTransItem.getAmountExcVat();
            FromAccBalance = NewTransItem.getUnitPrice();
            try {
                BalCheckerOn = new AccChildAccountBean().getAccChildAccByCode(FromAccCode).getBalance_checker_on();
            } catch (Exception e) {

            }
            if (FromAccCode.length() <= 0 || ToAccCode.length() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID FROM/TO ACCOUNTS...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (FromAccCode.equals(ToAccCode) && FromCurCode.equals(ToCurCode)) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("FROM/TO ACCOUNTS AND CURRENCIES CANNOT BE THE SAME...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (!FromCurCode.equals(LocalCurrencyCode) && !ToCurCode.equals(LocalCurrencyCode) && !FromCurCode.equals(ToCurCode)) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("ONE OF THE ACCOUNTS BETWEEN FROM AND TO MUST BE A LOCAL CURRENCY...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (FromAmount <= 0 || ToAmount <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER TRANSFER AMOUNT...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (BalCheckerOn == 1 && (FromAccBalance <= 0 || FromAmount > FromAccBalance)) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("AVAILABLE ACC BALANCE IS LESS THAN AMOUNT FOR TRANSFER...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(0);
                ti.setTransactionId(0);
                ti.setItemId(0);
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(1);
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitPrice2(NewTransItem.getUnitPrice2());
                ti.setUnitTradeDiscount(0);
                ti.setAmount(0);
                ti.setVatRated("");
                ti.setVatPerc(NewTransItem.getVatPerc());
                ti.setUnitVat(0);
                ti.setUnitPriceExcVat(0);
                ti.setUnitPriceIncVat(0);
                ti.setAmountExcVat(NewTransItem.getAmountExcVat());
                ti.setAmountIncVat(NewTransItem.getAmountIncVat());
                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExistsCashTransfer(aActiveTransItems, ti);
                if (ItemFoundAtIndex == -1) {
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        //this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        //this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    //this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }
                //for profit margin
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);
                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);
                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);
            }
        }
    }

    public void addTransItemCashAdjustment(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        String FromCurCode = "";
        String FromAccCode = "";
        double AdjustAmount = 0;
        double NewBalance = 0;
        double CurrentBalance = 0;
        int BalCheckerOn = 0;
        if (null != NewTransItem) {
            FromCurCode = NewTransItem.getBatchno();
            FromAccCode = NewTransItem.getAccountCode();
            AdjustAmount = NewTransItem.getAmountIncVat();
            NewBalance = NewTransItem.getAmountExcVat();
            CurrentBalance = NewTransItem.getUnitPrice();
            try {
                BalCheckerOn = new AccChildAccountBean().getAccChildAccByCode(FromAccCode).getBalance_checker_on();
            } catch (Exception e) {

            }
            if (FromAccCode.length() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID CASH ACCOUNT...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (AdjustAmount <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER AMOUNT TO ADJUST...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (BalCheckerOn == 1 && NewBalance < 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("NEW BALANCE ON THE ACCOUNT CANNOT BE NEGATIVE...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (AdjustAmount < 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("AMOUNT TO ADJUST CANNOT BE NEGATIVE...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(0);
                ti.setTransactionId(0);
                ti.setItemId(0);
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(1);
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitPrice2(0);
                ti.setUnitTradeDiscount(0);
                ti.setAmount(0);
                ti.setVatRated("");
                ti.setVatPerc(0);
                ti.setUnitVat(0);
                ti.setUnitPriceExcVat(0);
                ti.setUnitPriceIncVat(0);
                ti.setAmountExcVat(NewTransItem.getAmountExcVat());
                ti.setAmountIncVat(NewTransItem.getAmountIncVat());
                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExistsCashAdjustment(aActiveTransItems, ti);
                if (ItemFoundAtIndex == -1) {
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        //this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        //this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    //this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }
                //for profit margin
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);
                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);
                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);
            }
        }
    }

    public void addTransItemDelivery(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        if (null != aSelectedItem && aSelectedItem.getIsSale() == 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("This item is set up not to be Sold!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            if (NewTransItem.getItemQty() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID ITEM-QUANTITY...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getItemId() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());

                ti.setVatPerc(CompanySetting.getVatPerc());
                ti.setUnitVat(NewTransItem.getUnitVat());
                ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + NewTransItem.getUnitVat());

                ti.setItemExpryDate(NewTransItem.getItemExpryDate());
                ti.setItemMnfDate(NewTransItem.getItemMnfDate());
                ti.setAmountIncVat(NewTransItem.getAmount());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getAmount() + aActiveTransItems.get(ItemFoundAtIndex).getAmount());
                    ti.setAmountIncVat(ti.getAmountIncVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountIncVat());
                    ti.setAmountExcVat(ti.getAmountExcVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountExcVat());
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                //for profit margin
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
            }
        }
    }

    public void addTransItemDeliveryCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);

        if (null != aSelectedItem && aSelectedItem.getIsSale() == 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("This item is set up not to be Sold!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            if (NewTransItem.getItemQty() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID ITEM-QUANTITY...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getItemId() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());

                ti.setVatPerc(CompanySetting.getVatPerc());
                ti.setUnitVat(NewTransItem.getUnitVat());
                ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + NewTransItem.getUnitVat());

                ti.setItemExpryDate(NewTransItem.getItemExpryDate());
                ti.setItemMnfDate(NewTransItem.getItemMnfDate());
                ti.setAmountIncVat(NewTransItem.getAmount());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getAmount() + aActiveTransItems.get(ItemFoundAtIndex).getAmount());
                    ti.setAmountIncVat(ti.getAmountIncVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountIncVat());
                    ti.setAmountExcVat(ti.getAmountExcVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountExcVat());
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                //for profit margin
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
            }
        }
    }

    public void addTransItemReceive(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        if (null != aSelectedItem && aSelectedItem.getIsBuy() == 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("This item is set up not to be supplied/purchased!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            if (NewTransItem.getItemQty() <= 0 || NewTransItem.getUnitCostPrice() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID ITEM-QUANTITY or UNIT-COST-PRICE...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getItemId() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());

                ti.setVatPerc(CompanySetting.getVatPerc());
                ti.setUnitVat(NewTransItem.getUnitVat());
                ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + NewTransItem.getUnitVat());

                ti.setItemExpryDate(NewTransItem.getItemExpryDate());
                ti.setItemMnfDate(NewTransItem.getItemMnfDate());
                ti.setAmountIncVat(NewTransItem.getAmount());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getAmount() + aActiveTransItems.get(ItemFoundAtIndex).getAmount());
                    ti.setAmountIncVat(ti.getAmountIncVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountIncVat());
                    ti.setAmountExcVat(ti.getAmountExcVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountExcVat());
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                //for profit margin
                //ti.setUnitCostPrice(0);
                ti.setUnitCostPrice(NewTransItem.getUnitCostPrice());//this left for updating stock's unit cost
                ti.setUnitProfitMargin(0);

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
            }
        }
    }

    public void addTransItemReceiveCEC(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        if (null != aSelectedItem && aSelectedItem.getIsBuy() == 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("This item is set up not to be supplied/purchased!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            if (NewTransItem.getItemQty() <= 0 || NewTransItem.getUnitCostPrice() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("PLEASE ENTER VALID ITEM-QUANTITY or UNIT-COST-PRICE...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getItemId() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getCodeSpecific().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Code cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getDescSpecific().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Name cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getDescMore().length() > 250) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("More Description cannot be more than 250 characters...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno(NewTransItem.getBatchno());
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(NewTransItem.getUnitTradeDiscount());
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());

                ti.setVatPerc(CompanySetting.getVatPerc());
                ti.setUnitVat(NewTransItem.getUnitVat());
                ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                ti.setUnitPriceIncVat(NewTransItem.getUnitPrice() + NewTransItem.getUnitVat());

                ti.setItemExpryDate(NewTransItem.getItemExpryDate());
                ti.setItemMnfDate(NewTransItem.getItemMnfDate());
                ti.setAmountIncVat(NewTransItem.getAmount());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getAmount() + aActiveTransItems.get(ItemFoundAtIndex).getAmount());
                    ti.setAmountIncVat(ti.getAmountIncVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountIncVat());
                    ti.setAmountExcVat(ti.getAmountExcVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountExcVat());
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                //for profit margin
                //ti.setUnitCostPrice(0);
                ti.setUnitCostPrice(NewTransItem.getUnitCostPrice());//this left for updating stock's unit cost
                ti.setUnitProfitMargin(0);

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
            }
        }
    }

    public void addTransItemAssetReceived(Trans aTrans, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem NewTransItem, Item aSelectedItem, AccCoa aSelectedAccCoa) {
        if (null == aSelectedItem) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("Please select a valid Fixed Asset item!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else if (aSelectedItem.getIsAsset() == 0) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("Selected item is not a Fixed Asset!");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
        } else {
            if (NewTransItem.getItemQty() <= 0 || NewTransItem.getUnitPrice() <= 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Please specify PURCHASE PRICE...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (NewTransItem.getItemId() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("SELECT A VALID ITEM...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (aTrans.getTransactionReasonId() == 28 && NewTransItem.getAccountCode().length() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Select a valid ASSET ACCOUNT...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (aTrans.getTransactionReasonId() == 28 && NewTransItem.getPurchaseDate() == null) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Select a valid PURCHASE DATE...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else if (aTrans.getTransactionReasonId() == 28 && NewTransItem.getDescSpecific().length() == 0) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("Please specify the ASSET NAME...!");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
            } else {
                TransItem ti = new TransItem();
                ti.setTransactionItemId(NewTransItem.getTransactionItemId());
                ti.setTransactionId(NewTransItem.getTransactionId());
                ti.setItemId(NewTransItem.getItemId());
                ti.setBatchno("");
                ti.setItemQty(NewTransItem.getItemQty());
                ti.setUnitPrice(NewTransItem.getUnitPrice());
                ti.setUnitTradeDiscount(0);
                ti.setAmount(NewTransItem.getAmount());
                ti.setVatRated(NewTransItem.getVatRated());

                ti.setVatPerc(CompanySetting.getVatPerc());
                ti.setUnitVat(0);
                ti.setUnitPriceExcVat(NewTransItem.getUnitPrice());
                ti.setUnitPriceIncVat(NewTransItem.getUnitPrice());

                ti.setItemExpryDate(null);
                ti.setItemMnfDate(null);
                ti.setAmountIncVat(NewTransItem.getAmount());
                ti.setAmountExcVat((ti.getUnitPriceExcVat() - ti.getUnitTradeDiscount()) * ti.getItemQty());

                //bms veraibales
                try {
                    ti.setCodeSpecific(NewTransItem.getCodeSpecific());
                } catch (NullPointerException npe) {
                    ti.setCodeSpecific("");
                }
                try {
                    ti.setDescSpecific(NewTransItem.getDescSpecific());
                } catch (NullPointerException npe) {
                    ti.setDescSpecific("");
                }
                try {
                    ti.setDescMore(NewTransItem.getDescMore());
                } catch (NullPointerException npe) {
                    ti.setDescMore("");
                }
                try {
                    ti.setWarrantyDesc(NewTransItem.getWarrantyDesc());
                } catch (NullPointerException npe) {
                    ti.setWarrantyDesc("");
                }
                try {
                    ti.setWarrantyExpiryDate(NewTransItem.getWarrantyExpiryDate());
                } catch (NullPointerException npe) {
                    ti.setWarrantyExpiryDate(null);
                }
                try {
                    ti.setAccountCode(NewTransItem.getAccountCode());
                } catch (NullPointerException npe) {
                    ti.setAccountCode("");
                }
                try {
                    ti.setPurchaseDate(new java.sql.Date(NewTransItem.getPurchaseDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setPurchaseDate(null);
                }
                try {
                    ti.setDepStartDate(new java.sql.Date(NewTransItem.getDepStartDate().getTime()));
                } catch (NullPointerException npe) {
                    ti.setDepStartDate(null);
                }
                try {
                    ti.setDepMethodId(NewTransItem.getDepMethodId());
                } catch (NullPointerException npe) {
                    ti.setDepMethodId(0);
                }
                try {
                    ti.setDepRate(NewTransItem.getDepRate());
                } catch (NullPointerException npe) {
                    ti.setDepRate(0);
                }
                try {
                    ti.setAverageMethodId(NewTransItem.getAverageMethodId());
                } catch (NullPointerException npe) {
                    ti.setAverageMethodId(0);
                }
                try {
                    ti.setEffectiveLife(NewTransItem.getEffectiveLife());
                } catch (NullPointerException npe) {
                    ti.setEffectiveLife(0);
                }
                try {
                    ti.setResidualValue(NewTransItem.getResidualValue());
                } catch (NullPointerException npe) {
                    ti.setResidualValue(0);
                }
                try {
                    ti.setNarration(NewTransItem.getNarration());
                } catch (NullPointerException npe) {
                    ti.setNarration("");
                }
                try {
                    ti.setQty_balance(NewTransItem.getQty_balance());
                } catch (NullPointerException npe) {
                    ti.setQty_balance(0);
                }
                try {
                    ti.setDuration_value(NewTransItem.getDuration_value());
                } catch (NullPointerException npe) {
                    ti.setDuration_value(0);
                }
                try {
                    ti.setQty_damage(NewTransItem.getQty_damage());
                } catch (NullPointerException npe) {
                    ti.setQty_damage(0);
                }
                try {
                    ti.setDuration_passed(NewTransItem.getDuration_passed());
                } catch (NullPointerException npe) {
                    ti.setDuration_passed(0);
                }
                try {
                    if (NewTransItem.getSpecific_size() > 0) {
                        ti.setSpecific_size(NewTransItem.getSpecific_size());
                    } else {
                        ti.setSpecific_size(1);
                    }
                } catch (NullPointerException npe) {
                    ti.setSpecific_size(1);
                }
                //check if itme+batchno already exists
                int ItemFoundAtIndex = itemExists(aActiveTransItems, ti.getItemId(), ti.getBatchno(), ti.getCodeSpecific(), ti.getDescSpecific());
                if (ItemFoundAtIndex == -1) {
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(0, ti);
                    } else {
                        this.updateLookUpsUI(ti);
                        aActiveTransItems.add(ti);
                    }
                } else {
                    ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    ti.setAmount(ti.getAmount() + aActiveTransItems.get(ItemFoundAtIndex).getAmount());
                    ti.setAmountIncVat(ti.getAmountIncVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountIncVat());
                    ti.setAmountExcVat(ti.getAmountExcVat() + aActiveTransItems.get(ItemFoundAtIndex).getAmountExcVat());
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, ti);
                    //add
                    this.updateLookUpsUI(ti);
                    aActiveTransItems.add(ItemFoundAtIndex, ti);
                    aActiveTransItems.remove(ItemFoundAtIndex + 1);
                }

                //for profit margin
                ti.setUnitCostPrice(0);
                ti.setUnitProfitMargin(0);

                TransBean transB = new TransBean();
                transB.clearAll(null, aActiveTransItems, NewTransItem, aSelectedItem, null, 1, aSelectedAccCoa);

                aStatusBean.setItemAddedStatus("ITEM ADDED");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(1);
                aStatusBean.setShowItemNotAddedStatus(0);

                //update totals
                new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
            }
        }
    }

    public int itemExists(List<TransItem> aActiveTransItems, Long ItemIdent, String BatchNumb, String aCodeSpec, String aDescSpec) {
        List<TransItem> ati = aActiveTransItems;
        int ItemFoundAtIndex = -1;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double SubT = 0;
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getItemId() == ItemIdent && BatchNumb.equals(ati.get(ListItemIndex).getBatchno()) && aCodeSpec.equals(ati.get(ListItemIndex).getCodeSpecific()) && aDescSpec.equals(ati.get(ListItemIndex).getDescSpecific())) {
                ItemFoundAtIndex = ListItemIndex;
                break;
            } else {
                ItemFoundAtIndex = -1;
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return ItemFoundAtIndex;
    }

    public int itemExistsJournalEntry(List<TransItem> aActiveTransItems, String aAccountCode, String aChildAccountCode) {
        List<TransItem> ati = aActiveTransItems;
        int ItemFoundAtIndex = -1;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getAccountCode().equals(aAccountCode) && aChildAccountCode.equals(ati.get(ListItemIndex).getCodeSpecific())) {
                ItemFoundAtIndex = ListItemIndex;
                break;
            } else {
                ItemFoundAtIndex = -1;
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return ItemFoundAtIndex;
    }

    public int itemExistsCashTransfer(List<TransItem> aActiveTransItems, TransItem aTransItem) {
        List<TransItem> ati = aActiveTransItems;
        int ItemFoundAtIndex = -1;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        String FromCurCode = "";
        String ToCurCode = "";
        String FromAccCode = "";
        String ToAccCode = "";
        double FromAmount = 0;
        double ToAmount = 0;
        if (null != aTransItem) {
            FromCurCode = aTransItem.getBatchno();
            ToCurCode = aTransItem.getDescSpecific();
            FromAccCode = aTransItem.getAccountCode();
            ToAccCode = aTransItem.getCodeSpecific();
            FromAmount = aTransItem.getAmountIncVat();
            ToAmount = aTransItem.getAmountExcVat();
        }
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getAccountCode().equals(FromAccCode) && ToAccCode.equals(ati.get(ListItemIndex).getCodeSpecific()) && ati.get(ListItemIndex).getBatchno().equals(FromCurCode) && ToCurCode.equals(ati.get(ListItemIndex).getDescSpecific())) {
                ItemFoundAtIndex = ListItemIndex;
                break;
            } else {
                ItemFoundAtIndex = -1;
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return ItemFoundAtIndex;
    }

    public int itemExistsCashAdjustment(List<TransItem> aActiveTransItems, TransItem aTransItem) {
        List<TransItem> ati = aActiveTransItems;
        int ItemFoundAtIndex = -1;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        String FromCurCode = "";
        String FromAccCode = "";
        if (null != aTransItem) {
            FromCurCode = aTransItem.getBatchno();
            FromAccCode = aTransItem.getAccountCode();
        }
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getAccountCode().equals(FromAccCode) && ati.get(ListItemIndex).getBatchno().equals(FromCurCode)) {
                ItemFoundAtIndex = ListItemIndex;
                break;
            } else {
                ItemFoundAtIndex = -1;
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return ItemFoundAtIndex;
    }

    public void removeTransItem(Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        aActiveTransItems.remove(ti);

        //update totals
        new TransBean().setTransTotalsAndUpdate(aTrans, aActiveTransItems);
    }

    public void removeTransItemCEC(int aTransTypeId, int aTransReasonId, Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        aActiveTransItems.remove(ti);
        //update totals
        new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
    }

    public void overridePrice(TransItem aTransItem) {
        aTransItem.setIs_override_price(1);
    }

    public void addQtyTransItemCEC(int aTransTypeId, int aTransReasonId, Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        try {
            ti.setItemQty(ti.getItemQty() + 1);
            if (ti.getItemQty() < 0) {
                ti.setItemQty(0);
            }
            //update totals
            //new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
            this.editTransItemCEC(aTransTypeId, aTransReasonId, "", aTrans, aActiveTransItems, ti);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subtractQtyTransItemCEC(int aTransTypeId, int aTransReasonId, Trans aTrans, List<TransItem> aActiveTransItems, TransItem ti) {
        try {
            ti.setItemQty(ti.getItemQty() - 1);
            if (ti.getItemQty() < 0) {
                ti.setItemQty(0);
            }
            //update totals
            //new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
            this.editTransItemCEC(aTransTypeId, aTransReasonId, "", aTrans, aActiveTransItems, ti);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getListTotalItemBatchQty(List<TransItem> aActiveTransItems, Long aItemI, String aBatchN, String aCodeSpec, String aDescSpec) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TQty = 0;
        while (ListItemIndex < ListItemNo) {
            String CodeSp = "";
            String DescSp = "";
            String BatcNo = "";
            try {
                BatcNo = ati.get(ListItemIndex).getBatchno();
            } catch (NullPointerException npe) {
                BatcNo = "";
            }
            try {
                CodeSp = ati.get(ListItemIndex).getCodeSpecific();
            } catch (NullPointerException npe) {
                CodeSp = "";
            }
            try {
                DescSp = ati.get(ListItemIndex).getDescSpecific();
            } catch (NullPointerException npe) {
                DescSp = "";
            }
            if (ati.get(ListItemIndex).getItemId() == aItemI && aBatchN.equals(BatcNo) && aCodeSpec.equals(CodeSp) && aDescSpec.equals(DescSp)) {
                TQty = TQty + ati.get(ListItemIndex).getItemQty();
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return TQty;
    }

    public String getAnyItemTotalQtyGreaterThanCurrentQty(List<TransItem> aActiveTransItems, int aStoreId, String aTransactionType) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TQty = 0;
        this.setItemString("");
        while (ListItemIndex < ListItemNo && ((aTransactionType.equals("SALE INVOICE") && new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("0")) || aTransactionType.equals("TRANSFER") || aTransactionType.equals("DISPOSE STOCK") || aTransactionType.equals("STOCK ADJUSTMENT") || (aTransactionType.equals("GOODS DELIVERY") && new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "DEPLETE_SOLD_STOCK_UPON").getParameter_value().equals("1")))) {
            this.setItemString(ati.get(ListItemIndex).getDescription() + " " + ati.get(ListItemIndex).getBatchno());
            if (aTransactionType.equals("STOCK ADJUSTMENT") && ati.get(ListItemIndex).getNarration().equals("Add")) {
                //skip for adding
                this.setItemString("");
            } else {
                if (this.isItemTotalQtyGreaterThanCurrentQty(aStoreId, ati.get(ListItemIndex).getItemId(), ati.get(ListItemIndex).getBatchno(), ati.get(ListItemIndex).getItemQty(), ati.get(ListItemIndex).getCodeSpecific(), ati.get(ListItemIndex).getDescSpecific())) {
                    this.setItemString(new ItemBean().findItem(ati.get(ListItemIndex).getItemId()).getDescription() + " " + ati.get(ListItemIndex).getBatchno() + ", " + UtilityBean.combineTwoStr(ati.get(ListItemIndex).getCodeSpecific(), ati.get(ListItemIndex).getDescSpecific(), 2));
                    break;
                } else {
                    this.setItemString("");
                }
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return this.getItemString();
    }

    public String getAnyItemReturnTotalGreaterThanBalance(List<TransItem> aActiveTransItems, String aTransactionType) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TQty = 0;
        this.setItemString("");
        while (ListItemIndex < ListItemNo && aTransactionType.equals("HIRE RETURN NOTE")) {
            this.setItemString(ati.get(ListItemIndex).getDescription() + " " + ati.get(ListItemIndex).getBatchno());
            if (ati.get(ListItemIndex).getQty_balance() < (ati.get(ListItemIndex).getItemQty() + ati.get(ListItemIndex).getQty_damage())) {
                this.setItemString(new ItemBean().findItem(ati.get(ListItemIndex).getItemId()).getDescription() + " " + ati.get(ListItemIndex).getBatchno() + ", " + UtilityBean.combineTwoStr(ati.get(ListItemIndex).getCodeSpecific(), ati.get(ListItemIndex).getDescSpecific(), 2));
                break;
            } else {
                this.setItemString("");
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return this.getItemString();
    }

    public String getAnyItemDeliveryTotalGreaterThanBalance(List<TransItem> aActiveTransItems, String aTransactionType) {
        List<TransItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TQty = 0;
        this.setItemString("");
        while (ListItemIndex < ListItemNo && aTransactionType.equals("HIRE DELIVERY NOTE")) {
            this.setItemString(ati.get(ListItemIndex).getDescription() + " " + ati.get(ListItemIndex).getBatchno());
            if (ati.get(ListItemIndex).getQty_balance() < ati.get(ListItemIndex).getItemQty()) {
                this.setItemString(new ItemBean().findItem(ati.get(ListItemIndex).getItemId()).getDescription() + " " + ati.get(ListItemIndex).getBatchno() + ", " + UtilityBean.combineTwoStr(ati.get(ListItemIndex).getCodeSpecific(), ati.get(ListItemIndex).getDescSpecific(), 2));
                break;
            } else {
                this.setItemString("");
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return this.getItemString();
    }

    public boolean isItemTotalQtyGreaterThanCurrentQty(int aStoreId, Long ItemI, String BatchN, double aItemQty, String aCodeSpec, String aDescSpec) {
        //check item type
        Item i = new ItemBean().getItem(ItemI);
        if (i.getIsTrack() == 0) {
            return false;
        } else {
            //get item's stock details
            //get and check number of batches
            StockBean sb = new StockBean();
            Stock st = new Stock();
            double aCurrentQty = 0;
            st = sb.getStock(aStoreId, ItemI, BatchN, aCodeSpec, aDescSpec);
            if (st != null) {
                aCurrentQty = st.getCurrentqty();
            }

            if (aItemQty <= aCurrentQty) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void clearTransItem(TransItem tri) {
        if (tri != null) {
            tri.setTransactionItemId(0);
            tri.setTransactionId(0);
            tri.setItemId(0);
            tri.setItemId2(0);
            tri.setBatchno("");
            tri.setItemQty(1);
            tri.setItemQty2(0);
            tri.setFractionQty(0);
            tri.setUnitPrice(0);
            tri.setAmount(0);
            tri.setItemExpryDate(null);
            tri.setItemMnfDate(null);
            tri.setUnitVat(0);
            tri.setUnitTradeDiscount(0);
            tri.setItemCode("");
            tri.setUnitPriceIncVat(0);
            tri.setUnitPriceExcVat(0);
            tri.setAmountIncVat(0);
            tri.setAmountExcVat(0);
            tri.setStockEffect("");
            tri.setUnitPrice2(0);
            tri.setUnitTradeDiscount2(0);
            tri.setVatRated2("");
            tri.setOverridePrices(false);
            tri.setSpecifyWarranty(false);
            tri.setUnitCostPrice(0);
            tri.setUnitProfitMargin(0);
            tri.setEarnPerc(0);
            tri.setEarnAmount(0);
            tri.setCodeSpecific("");
            tri.setDescSpecific("");
            tri.setDescMore("");
            tri.setWarrantyDesc("");
            tri.setWarrantyExpiryDate(null);
            tri.setStockId(0);
            tri.setAccountCode("");
            tri.setPurchaseDate(null);
            tri.setDepStartDate(null);
            tri.setDepMethodId(0);
            tri.setDepRate(0);
            tri.setAverageMethodId(0);
            tri.setEffectiveLife(0);
            tri.setResidualValue(0);
            tri.setNarration("");
            tri.setQty_balance(0);
            tri.setDuration_value(0);
            tri.setQty_damage(0);
            tri.setDuration_passed(0);
            tri.setIs_general(0);
            tri.setSpecific_size_qty(1);
            tri.setSpecific_size(1);
            tri.setIs_override_price(0);
        }
    }

    public void copyObjectTransItem(TransItem FromObj, TransItem ToObj) {
        if (ToObj != null) {
            ToObj.setTransactionItemId(FromObj.getTransactionItemId());
            ToObj.setTransactionId(FromObj.getTransactionId());
            ToObj.setItemId(FromObj.getItemId());
            ToObj.setItemId2(FromObj.getItemId2());
            ToObj.setBatchno(FromObj.getBatchno());
            ToObj.setItemQty(FromObj.getItemQty());
            ToObj.setItemQty2(FromObj.getItemQty2());
            ToObj.setFractionQty(FromObj.getFractionQty());
            ToObj.setUnitPrice(FromObj.getUnitPrice());
            ToObj.setAmount(FromObj.getAmount());
            ToObj.setItemExpryDate(FromObj.getItemExpryDate());
            ToObj.setItemMnfDate(FromObj.getItemMnfDate());
            ToObj.setUnitVat(FromObj.getUnitVat());
            ToObj.setUnitTradeDiscount(FromObj.getUnitTradeDiscount());
            ToObj.setItemCode(FromObj.getItemCode());
            ToObj.setUnitPriceIncVat(FromObj.getUnitPriceIncVat());
            ToObj.setUnitPriceExcVat(FromObj.getUnitPriceExcVat());
            ToObj.setAmountIncVat(FromObj.getAmountIncVat());
            ToObj.setAmountExcVat(FromObj.getAmountExcVat());
            ToObj.setStockEffect(FromObj.getStockEffect());
            ToObj.setUnitPrice2(FromObj.getUnitPrice2());
            ToObj.setUnitTradeDiscount2(FromObj.getUnitTradeDiscount2());
            ToObj.setVatRated2(FromObj.getVatRated2());
            ToObj.setOverridePrices(FromObj.isOverridePrices());
            ToObj.setSpecifyWarranty(FromObj.isSpecifyWarranty());
            ToObj.setUnitCostPrice(FromObj.getUnitCostPrice());
            ToObj.setUnitProfitMargin(FromObj.getUnitProfitMargin());
            ToObj.setEarnPerc(FromObj.getEarnPerc());
            ToObj.setEarnAmount(FromObj.getEarnAmount());
            ToObj.setCodeSpecific(FromObj.getCodeSpecific());
            ToObj.setDescSpecific(FromObj.getDescSpecific());
            ToObj.setDescMore(FromObj.getDescMore());
            ToObj.setWarrantyDesc(FromObj.getWarrantyDesc());
            ToObj.setWarrantyExpiryDate(FromObj.getWarrantyExpiryDate());
            ToObj.setStockId(FromObj.getStockId());
            ToObj.setAccountCode(FromObj.getAccountCode());
            ToObj.setPurchaseDate(FromObj.getPurchaseDate());
            ToObj.setDepStartDate(FromObj.getDepStartDate());
            ToObj.setDepMethodId(FromObj.getDepMethodId());
            ToObj.setDepRate(FromObj.getDepRate());
            ToObj.setAverageMethodId(FromObj.getAverageMethodId());
            ToObj.setEffectiveLife(FromObj.getEffectiveLife());
            ToObj.setResidualValue(FromObj.getResidualValue());
            ToObj.setNarration(FromObj.getNarration());
            ToObj.setQty_balance(FromObj.getQty_balance());
            ToObj.setDuration_value(FromObj.getDuration_value());
            ToObj.setQty_damage(FromObj.getQty_damage());
            ToObj.setDuration_passed(FromObj.getDuration_passed());
            ToObj.setIs_general(0);
            ToObj.setSpecific_size_qty(FromObj.getSpecific_size_qty());
            ToObj.setSpecific_size(FromObj.getSpecific_size_qty());
        }
    }

    public void clearTransItem2(TransItem tri) {
        if (tri != null) {
            tri.setTransactionItemId(0);
            tri.setTransactionId(0);
            tri.setItemId(0);
            tri.setItemId2(0);
            tri.setBatchno("");
            tri.setItemQty(0);
            tri.setItemQty2(0);
            tri.setFractionQty(0);
            tri.setUnitPrice(0);
            tri.setAmount(0);
            tri.setItemExpryDate(null);
            tri.setItemMnfDate(null);
            tri.setUnitVat(0);
            tri.setUnitTradeDiscount(0);
            tri.setItemCode("");
            tri.setUnitPriceIncVat(0);
            tri.setUnitPriceExcVat(0);
            tri.setAmountIncVat(0);
            tri.setAmountExcVat(0);
            tri.setStockEffect("");
            tri.setUnitPrice2(0);
            tri.setUnitTradeDiscount2(0);
            tri.setVatRated2("");
            tri.setOverridePrices(false);
            tri.setSpecifyWarranty(false);
            tri.setUnitCostPrice(0);
            tri.setUnitProfitMargin(0);
            tri.setEarnPerc(0);
            tri.setEarnAmount(0);
            tri.setCodeSpecific("");
            tri.setDescSpecific("");
            tri.setDescMore("");
            tri.setWarrantyDesc("");
            tri.setWarrantyExpiryDate(null);
            tri.setStockId(0);
            tri.setAccountCode("");
            tri.setPurchaseDate(null);
            tri.setDepStartDate(null);
            tri.setDepMethodId(0);
            tri.setDepRate(0);
            tri.setAverageMethodId(0);
            tri.setEffectiveLife(0);
            tri.setResidualValue(0);
            tri.setNarration("");
            tri.setQty_balance(0);
            tri.setDuration_value(0);
            tri.setQty_damage(0);
            tri.setDuration_passed(0);
            tri.setIs_general(0);
            tri.setSpecific_size_qty(0);
            tri.setSpecific_size(0);
            tri.setIs_override_price(0);
        }
    }

    /**
     * @return the ItemString
     */
    public String getItemString() {
        return ItemString;
    }

    /**
     * @param ItemString the ItemString to set
     */
    public void setItemString(String ItemString) {
        this.ItemString = ItemString;
    }

    /**
     * @return the ActiveTransItems
     */
    public List<TransItem> getActiveTransItems() {
        return ActiveTransItems;
    }

    /**
     * @param ActiveTransItems the ActiveTransItems to set
     */
    public void setActiveTransItems(List<TransItem> ActiveTransItems) {
        this.ActiveTransItems = ActiveTransItems;
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
     * @return the SelectedTransItem
     */
    public TransItem getSelectedTransItem() {
        return SelectedTransItem;
    }

    /**
     * @param SelectedTransItem the SelectedTransItem to set
     */
    public void setSelectedTransItem(TransItem SelectedTransItem) {
        this.SelectedTransItem = SelectedTransItem;
    }

    /**
     * @return the SelectedTransactionItemId
     */
    public int getSelectedTransactionItemId() {
        return SelectedTransactionItemId;
    }

    /**
     * @param SelectedTransactionItemId the SelectedTransactionItemId to set
     */
    public void setSelectedTransactionItemId(int SelectedTransactionItemId) {
        this.SelectedTransactionItemId = SelectedTransactionItemId;
    }

    /**
     * @return the SearchTransItem
     */
    public String getSearchTransItem() {
        return SearchTransItem;
    }

    /**
     * @param SearchTransItem the SearchTransItem to set
     */
    public void setSearchTransItem(String SearchTransItem) {
        this.SearchTransItem = SearchTransItem;
    }

    public void updateModelTransItem(Trans aTrans, TransItem aTransItemToUpdate, StatusBean aStatusBean, List<TransItem> aActiveTransItems, Item i, int auto) {//auto=1 for itemCode, auto=0 is for desc/code    ,2 is for other
        aStatusBean.setItemAddedStatus("");
        aStatusBean.setItemNotAddedStatus("");
        aStatusBean.setShowItemAddedStatus(0);
        aStatusBean.setShowItemNotAddedStatus(0);

        StockBean sb = new StockBean();
        List<Stock> batches = new ArrayList<Stock>();
        DiscountPackageItem dpi = null;

        if (i == null) {
            aTransItemToUpdate.setItemId(0);
            aTransItemToUpdate.setUnitPrice(0);
            aTransItemToUpdate.setVatRated("");
            aTransItemToUpdate.setItemQty(0);
            aTransItemToUpdate.setAmount(0);
            aTransItemToUpdate.setItemCode("");
            new ItemBean().clearItem(i);
            aTransItemToUpdate.setUnitPrice2(0);
            aTransItemToUpdate.setUnitCostPrice(0);
            aTransItemToUpdate.setUnitTradeDiscount(0);
            aTransItemToUpdate.setUnitTradeDiscount2(0);
            aTransItemToUpdate.setVatRated2("");
            aTransItemToUpdate.setDuration_value(0);
        } else {
            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            try {
                if ("ITEM RECEIVED".equals(new GeneralUserSetting().getCurrentTransactionTypeName())) {
                    xrate = 1;
                } else {
                    xrate = new AccXrateBean().getXrate(i.getCurrencyCode(), aTrans.getCurrencyCode());
                }
            } catch (NullPointerException npe) {
                xrate = 1;
            }
            try {
                if (i.getCurrencyCode().equals(LocalCurrency.getCurrencyCode()) && !aTrans.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                    XrateMultiply = 1 / xrate;
                } else {
                    XrateMultiply = xrate;
                }
            } catch (NullPointerException npe) {
                XrateMultiply = 1;
            }
            i.setUnit_hire_price(i.getUnit_hire_price() * XrateMultiply);
            i.setUnitRetailsalePrice(i.getUnitRetailsalePrice() * XrateMultiply);
            i.setUnitWholesalePrice(i.getUnitWholesalePrice() * XrateMultiply);

            aTransItemToUpdate.setItemId(i.getItemId());
            aTransItemToUpdate.setIsTradeDiscountVatLiable(CompanySetting.getIsTradeDiscountVatLiable());
            if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE ORDER") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE QUOTATION") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE QUOTATION")) {
                dpi = new DiscountPackageItemBean().getActiveDiscountPackageItem(new GeneralUserSetting().getCurrentStore().getStoreId(), i.getItemId(), 1, aTrans.getTransactorId(), i.getCategoryId(), i.getSubCategoryId());
            } else {
                dpi = null;
            }
            if (dpi != null) {
                //dpi.setRetailsaleDiscountAmt(dpi.getRetailsaleDiscountAmt() * XrateMultiply);
                //dpi.setWholesaleDiscountAmt(dpi.getWholesaleDiscountAmt() * XrateMultiply);
            }
            //get account code for the item
            try {
                if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE INVOICE")) {
                    if (i.getItemType().equals("PRODUCT")) {//4-10-000-010 - SALES Products
                        aTransItemToUpdate.setAccountCode("4-10-000-010");
                    } else if (i.getItemType().equals("SERVICE")) {//4-10-000-020 - SALES Services	
                        aTransItemToUpdate.setAccountCode("4-10-000-020");
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("PURCHASE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("EXPENSE ENTRY")) {
                    if (new GeneralUserSetting().getCurrentTransactionReasonId() == 1) {//GOOD AND SERVICE
                        if (i.getItemType().equals("PRODUCT")) {
                            aTransItemToUpdate.setAccountCode("5-10-000-010");
                        } else if (i.getItemType().equals("SERVICE")) {
                            aTransItemToUpdate.setAccountCode("5-10-000-020");
                        }
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 27 || new GeneralUserSetting().getCurrentTransactionReasonId() == 43) {//EXPENSE,EXPENSE ENTRY
                        aTransItemToUpdate.setAccountCode(i.getExpenseAccountCode());
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 29) {//ASSET
                        aTransItemToUpdate.setAccountCode(i.getAssetAccountCode());
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("PURCHASE ORDER")) {
                    if (new GeneralUserSetting().getCurrentTransactionReasonId() == 12) {//GOOD AND SERVICE
                        if (i.getItemType().equals("PRODUCT")) {
                            aTransItemToUpdate.setAccountCode("5-10-000-010");
                        } else if (i.getItemType().equals("SERVICE")) {
                            aTransItemToUpdate.setAccountCode("5-10-000-020");
                        }
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 30) {//EXPENSE
                        aTransItemToUpdate.setAccountCode(i.getExpenseAccountCode());
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 31) {//ASSET
                        aTransItemToUpdate.setAccountCode(i.getAssetAccountCode());
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("ITEM RECEIVED")) {
                    //aTransItemToUpdate.setAccountCode(i.getAssetAccountCode());
                    if (new GeneralUserSetting().getCurrentTransactionReasonId() == 13) {//GOOD/PRODUCT
                        if (i.getItemType().equals("PRODUCT")) {
                            aTransItemToUpdate.setAccountCode("5-10-000-010");
                        } else if (i.getItemType().equals("SERVICE")) {
                            aTransItemToUpdate.setAccountCode("5-10-000-020");
                        }
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 32) {//EXPENSE
                        aTransItemToUpdate.setAccountCode(i.getExpenseAccountCode());
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 28) {//ASSET
                        aTransItemToUpdate.setAccountCode(i.getAssetAccountCode());
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE INVOICE")) {
                    aTransItemToUpdate.setAccountCode("4-10-000-050");
                } else {
                    aTransItemToUpdate.setAccountCode("");
                }
            } catch (NullPointerException npe) {
                aTransItemToUpdate.setAccountCode("");
            }

            if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE QUOTATION")) {
                aTransItemToUpdate.setDuration_value(aTrans.getDuration_value());
            } else {
                aTransItemToUpdate.setDuration_value(0);
            }

            //apply recent unit cost
            if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("ITEM RECEIVED")) {
                long LatestTransItemId = 0;
                if (new GeneralUserSetting().getCurrentTransactionReasonId() == 13) {//GOOD/PRODUCT
                    try {
                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId2(9, 13, new GeneralUserSetting().getCurrentStore().getStoreId(), i.getItemId(), "", "", "");
                    } catch (NullPointerException npe) {
                        LatestTransItemId = 0;
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 32) {//EXPENSE

                } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 28) {//ASSET

                }
                if (LatestTransItemId > 0) {
                    try {
                        aTransItemToUpdate.setUnitCostPrice(new TransItemBean().getTransItem(LatestTransItemId).getUnitCostPrice());
                    } catch (NullPointerException npe) {
                    }
                }
            }

            if ("EXEMPT SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())) {
                aTransItemToUpdate.setUnitPrice(0);
                aTransItemToUpdate.setUnitTradeDiscount(0);
            } else if ("COST-PRICE SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())) {
                //aTransItemToUpdate.setUnitPrice(i.getUnitCostPrice());
                aTransItemToUpdate.setUnitTradeDiscount(0);
            } else if ("WHOLE SALE QUOTATION".equals(new GeneralUserSetting().getCurrentSaleType()) || "WHOLE SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())) {
                aTransItemToUpdate.setUnitPrice(i.getUnitWholesalePrice());
                if (dpi != null) {
                    //aTransItemToUpdate.setUnitTradeDiscount(dpi.getWholesaleDiscountAmt());
                    aTransItemToUpdate.setUnitTradeDiscount(i.getUnitWholesalePrice() * dpi.getWholesaleDiscountAmt() / 100);
                }
            } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE QUOTATION")) {
                aTransItemToUpdate.setUnitPrice(i.getUnit_hire_price());
                if (dpi != null) {
                    aTransItemToUpdate.setUnitTradeDiscount(i.getUnit_hire_price() * dpi.getHire_price_discount_amt() / 100);
                }
            } else {
                if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE ORDER") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE QUOTATION") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("EXPENSE ENTRY") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("PURCHASE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("DISPOSE STOCK")) {
                    aTransItemToUpdate.setUnitPrice(i.getUnitRetailsalePrice());
                } else {
                    aTransItemToUpdate.setUnitPrice(0);
                }
                if (dpi != null) {
                    aTransItemToUpdate.setUnitTradeDiscount(i.getUnitRetailsalePrice() * dpi.getRetailsaleDiscountAmt() / 100);
                }
            }
            aTransItemToUpdate.setVatRated(i.getVatRated());
            aTransItemToUpdate.setItemCode(i.getItemCode());

            //??override/change UnitPrice  for all transactions
            //??override/change Trade.Discount for all transactions
            //??override/change Vat.Rated for all transactions
            //NOTE: all overide can be done when addition mode is notne automatic; see them below
            if (auto == 0) {
                aTransItemToUpdate.setUnitPrice2(aTransItemToUpdate.getUnitPrice());
                aTransItemToUpdate.setUnitTradeDiscount2(aTransItemToUpdate.getUnitTradeDiscount());
                aTransItemToUpdate.setVatRated2(aTransItemToUpdate.getVatRated());
                //just to help by autofilling
                aTransItemToUpdate.setItemQty(1);
                if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("HIRE QUOTATION")) {
                    aTransItemToUpdate.setAmount(aTransItemToUpdate.getItemQty() * aTransItemToUpdate.getUnitPrice() * aTransItemToUpdate.getDuration_value());
                } else {
                    aTransItemToUpdate.setAmount(aTransItemToUpdate.getItemQty() * aTransItemToUpdate.getUnitPrice());
                }
            } else if (auto == 1) {//item entered through barcode
                batches = sb.getStocks(new GeneralUserSetting().getCurrentStore().getStoreId(), i.getItemId());
                if (batches.size() == 1) {
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(0);
                    //update model
                    aTransItemToUpdate.setItemQty(1);
                    aTransItemToUpdate.setAmount(aTransItemToUpdate.getItemQty() * aTransItemToUpdate.getUnitPrice());
                    aTransItemToUpdate.setBatchno(batches.get(0).getBatchno());
                    //Add Item to the list
                    this.addTransItem(aTrans, aStatusBean, aActiveTransItems, aTransItemToUpdate, i);
                } else {//if batches are many OR item has zero current quantity
                    aTransItemToUpdate.setItemQty(0);
                    aTransItemToUpdate.setAmount(0);
                    if (batches.size() > 1) {
                        aStatusBean.setItemAddedStatus("");
                        aStatusBean.setItemNotAddedStatus("PLEASE SELECT BATCH No");
                        aStatusBean.setShowItemAddedStatus(0);
                        aStatusBean.setShowItemNotAddedStatus(1);
                    } else if (batches.size() <= 0) {
                        aStatusBean.setItemAddedStatus("");
                        aStatusBean.setItemNotAddedStatus("ITEM IS OUT OF STOCK");
                        aStatusBean.setShowItemAddedStatus(0);
                        aStatusBean.setShowItemNotAddedStatus(1);
                    }

                }
            }
        }
    }

    public void updateModelTransItemCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, TransItem aTransItemToUpdate, Item aItem, double aDefaultQty) {
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);
        DiscountPackageItem dpi = null;
        if (aItem == null || transtype == null) {
            aTransItemToUpdate.setItemId(0);
            aTransItemToUpdate.setUnitPrice(0);
            aTransItemToUpdate.setVatRated("");
            aTransItemToUpdate.setItemQty(0);
            aTransItemToUpdate.setAmount(0);
            aTransItemToUpdate.setItemCode("");
            new ItemBean().clearItem(aItem);
            aTransItemToUpdate.setUnitPrice2(0);
            aTransItemToUpdate.setUnitCostPrice(0);
            aTransItemToUpdate.setUnitTradeDiscount(0);
            aTransItemToUpdate.setUnitTradeDiscount2(0);
            aTransItemToUpdate.setVatRated2("");
            aTransItemToUpdate.setDuration_value(0);
        } else {
            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            try {
                if ("ITEM RECEIVED".equals(transtype.getTransactionTypeName())) {
                    xrate = 1;
                } else {
                    xrate = new AccXrateBean().getXrate(aItem.getCurrencyCode(), aTrans.getCurrencyCode());
                }
            } catch (NullPointerException npe) {
                xrate = 1;
            }
            try {
                if (aItem.getCurrencyCode().equals(LocalCurrency.getCurrencyCode()) && !aTrans.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                    XrateMultiply = 1 / xrate;
                } else {
                    XrateMultiply = xrate;
                }
            } catch (NullPointerException npe) {
                XrateMultiply = 1;
            }
            aItem.setUnit_hire_price(aItem.getUnit_hire_price() * XrateMultiply);
            aItem.setUnitRetailsalePrice(aItem.getUnitRetailsalePrice() * XrateMultiply);
            aItem.setUnitWholesalePrice(aItem.getUnitWholesalePrice() * XrateMultiply);
            aItem.setUnit_special_price(aItem.getUnit_special_price() * XrateMultiply);

            aTransItemToUpdate.setItemId(aItem.getItemId());
            aTransItemToUpdate.setIsTradeDiscountVatLiable(CompanySetting.getIsTradeDiscountVatLiable());
            if (transtype.getTransactionTypeName().equals("SALE ORDER") || transtype.getTransactionTypeName().equals("SALE QUOTATION") || transtype.getTransactionTypeName().equals("SALE INVOICE") || transtype.getTransactionTypeName().equals("HIRE INVOICE") || transtype.getTransactionTypeName().equals("HIRE QUOTATION") || transtype.getTransactionTypeName().equals("HIRE RETURN INVOICE")) {
                dpi = new DiscountPackageItemBean().getActiveDiscountPackageItem(store.getStoreId(), aItem.getItemId(), 1, aTrans.getTransactorId(), aItem.getCategoryId(), aItem.getSubCategoryId());
            } else {
                dpi = null;
            }
            if (dpi != null) {
                //dpaItem.setRetailsaleDiscountAmt(dpaItem.getRetailsaleDiscountAmt() * XrateMultiply);
                //dpaItem.setWholesaleDiscountAmt(dpaItem.getWholesaleDiscountAmt() * XrateMultiply);
            }

            //get account code for the cost of inventory
            aTransItemToUpdate.setAccountCode(this.getTransItemInventCostAccount(transtype, transreason, aItem));

            if (transtype.getTransactionTypeName().equals("HIRE INVOICE") || transtype.getTransactionTypeName().equals("HIRE QUOTATION") || transtype.getTransactionTypeName().equals("HIRE RETURN INVOICE")) {
                aTransItemToUpdate.setDuration_value(aTrans.getDuration_value());
            } else {
                aTransItemToUpdate.setDuration_value(0);
            }
            //apply recent unit cost
            if (transtype.getTransactionTypeName().equals("ITEM RECEIVED") || transtype.getTransactionTypeName().equals("PRODUCTION") || transtype.getTransactionTypeName().equals("STOCK ADJUSTMENT")) {
                aTransItemToUpdate.setUnitCostPrice(this.getItemLatestUnitCostPrice(aItem.getItemId(), "", "", ""));
            }
//            if (transtype.getTransactionTypeName().equals("ITEM RECEIVED") || transtype.getTransactionTypeName().equals("PRODUCTION")) {
//                long LatestTransItemId = 0;
//                if (transreason.getTransactionReasonId() == 13) {//GOOD/PRODUCT
//                    try {
//                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId2(9, 13, store.getStoreId(), aItem.getItemId(), "", "", "");
//                    } catch (NullPointerException npe) {
//                        LatestTransItemId = 0;
//                    }
//                } else if (transreason.getTransactionReasonId() == 32) {//EXPENSE
//                    try {
//                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId2(9, 32, store.getStoreId(), aItem.getItemId(), "", "", "");
//                    } catch (NullPointerException npe) {
//                        LatestTransItemId = 0;
//                    }
//                } else if (transreason.getTransactionReasonId() == 28) {//ASSET
//                    try {
//                        LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId2(9, 28, store.getStoreId(), aItem.getItemId(), "", "", "");
//                    } catch (NullPointerException npe) {
//                        LatestTransItemId = 0;
//                    }
//                }
//                if (LatestTransItemId > 0) {
//                    try {
//                        aTransItemToUpdate.setUnitCostPrice(new TransItemBean().getTransItem(LatestTransItemId).getUnitCostPrice());
//                    } catch (NullPointerException npe) {
//                    }
//                }
//            }

            if ("EXEMPT SALE INVOICE".equals(aSaleType)) {
                aTransItemToUpdate.setUnitPrice(0);
                aTransItemToUpdate.setUnitTradeDiscount(0);
            } else if ("COST-PRICE SALE INVOICE".equals(aSaleType)) {
                //aTransItemToUpdate.setUnitPrice(aItem.getUnitCostPrice());
                aTransItemToUpdate.setUnitTradeDiscount(0);
            } else if ("WHOLE SALE QUOTATION".equals(aSaleType) || "WHOLE SALE INVOICE".equals(aSaleType) || "WHOLE SALE ORDER".equals(aSaleType)) {
                aTransItemToUpdate.setUnitPrice(aItem.getUnitWholesalePrice());
                if (dpi != null) {
                    //aTransItemToUpdate.setUnitTradeDiscount(dpaItem.getWholesaleDiscountAmt());
                    aTransItemToUpdate.setUnitTradeDiscount(aItem.getUnitWholesalePrice() * dpi.getWholesaleDiscountAmt() / 100);
                }
            } else if ("SPECIAL SALE INVOICE".equals(aSaleType) || "SPECIAL SALE QUOTATION".equals(aSaleType) || "SPECIAL SALE ORDER".equals(aSaleType)) {
                aTransItemToUpdate.setUnitPrice(aItem.getUnit_special_price());
                if (dpi != null) {
                    aTransItemToUpdate.setUnitTradeDiscount(0);
                }
            } else if (transtype.getTransactionTypeName().equals("HIRE INVOICE") || transtype.getTransactionTypeName().equals("HIRE QUOTATION") || transtype.getTransactionTypeName().equals("HIRE RETURN INVOICE")) {
                aTransItemToUpdate.setUnitPrice(aItem.getUnit_hire_price());
                if (dpi != null) {
                    aTransItemToUpdate.setUnitTradeDiscount(aItem.getUnit_hire_price() * dpi.getHire_price_discount_amt() / 100);
                }
            } else {
                if (transtype.getTransactionTypeName().equals("SALE ORDER") || transtype.getTransactionTypeName().equals("SALE QUOTATION") || transtype.getTransactionTypeName().equals("SALE INVOICE") || transtype.getTransactionTypeName().equals("EXPENSE ENTRY") || transtype.getTransactionTypeName().equals("PURCHASE INVOICE") || transtype.getTransactionTypeName().equals("DISPOSE STOCK")) {
                    aTransItemToUpdate.setUnitPrice(aItem.getUnitRetailsalePrice());
                } else {
                    aTransItemToUpdate.setUnitPrice(0);
                }
                if (dpi != null) {
                    aTransItemToUpdate.setUnitTradeDiscount(aItem.getUnitRetailsalePrice() * dpi.getRetailsaleDiscountAmt() / 100);
                }
            }
            aTransItemToUpdate.setVatRated(aItem.getVatRated());
            aTransItemToUpdate.setItemCode(aItem.getItemCode());

            //Default override prices
            aTransItemToUpdate.setUnitPrice2(aTransItemToUpdate.getUnitPrice());
            aTransItemToUpdate.setUnitTradeDiscount2(aTransItemToUpdate.getUnitTradeDiscount());
            aTransItemToUpdate.setVatRated2(aTransItemToUpdate.getVatRated());
            //Default qty
            aTransItemToUpdate.setItemQty(aDefaultQty);
            if (transtype.getTransactionTypeName().equals("HIRE INVOICE") || transtype.getTransactionTypeName().equals("HIRE QUOTATION") || transtype.getTransactionTypeName().equals("HIRE RETURN INVOICE")) {
                aTransItemToUpdate.setAmount(aTransItemToUpdate.getItemQty() * aTransItemToUpdate.getUnitPrice() * aTransItemToUpdate.getDuration_value());
            } else {
                aTransItemToUpdate.setAmount(aTransItemToUpdate.getItemQty() * aTransItemToUpdate.getUnitPrice());
            }
        }
    }

    public double getItemLatestUnitCostPrice(long aItemId, String aBatchno, String aCodeSpec, String aDescSpec) {
        double LatestUnitCostPrice = 0;
        long LatestItemReceivedTransItemId = 0;
        //1. check if item has been received before
        try {
            LatestItemReceivedTransItemId = this.getItemReceivedLatestTransItemId(aItemId, aBatchno, aCodeSpec, aDescSpec);
        } catch (Exception e) {
            LatestItemReceivedTransItemId = 0;
        }
        if (LatestItemReceivedTransItemId > 0) {
            try {
                LatestUnitCostPrice = new TransItemBean().getTransItem(LatestItemReceivedTransItemId).getUnitCostPrice();
            } catch (Exception e) {
                LatestUnitCostPrice = 0;
            }
        }

        //2. if not recently received, check from stock table for any store
        if (LatestUnitCostPrice <= 0) {
            try {
                LatestUnitCostPrice = new StockBean().getStockAnyStore(aItemId, aBatchno, aCodeSpec, aDescSpec).getUnitCost();
            } catch (Exception e) {
                LatestUnitCostPrice = 0;
            }
        }

        //3. if nothing in stock table, check from item settings
        if (LatestUnitCostPrice <= 0) {
            try {
                LatestUnitCostPrice = new ItemBean().getItem(aItemId).getUnitCostPrice();
            } catch (Exception e) {
                LatestUnitCostPrice = 0;
            }
        }

        return LatestUnitCostPrice;
    }

    public void refreshAdjustUponItemChange(TransItem aTransItem) {
        try {
            if (null != aTransItem) {
                aTransItem.setStockId(0);
                aTransItem.setBatchno("");
                aTransItem.setCodeSpecific("");
                aTransItem.setDescSpecific("");
                aTransItem.setUnitCostPrice(0);
                aTransItem.setQty_total(0);
            }
            aTransItem.setStockId(0);
            aTransItem.setBatchno("");
            aTransItem.setCodeSpecific("");
            aTransItem.setDescSpecific("");
            aTransItem.setUnitCostPrice(this.getItemLatestUnitCostPrice(aTransItem.getItemId(), aTransItem.getBatchno(), aTransItem.getCodeSpecific(), aTransItem.getDescSpecific()));
            Stock st = new StockBean().getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aTransItem.getItemId(), aTransItem.getBatchno(), aTransItem.getCodeSpecific(), aTransItem.getDescSpecific());
            if (null != st) {
                aTransItem.setStockId(st.getStockId());
                aTransItem.setQty_total(st.getCurrentqty());
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    public void refreshAdjustUponStockChange(TransItem aTransItem) {
        try {
            if (null != aTransItem) {
                aTransItem.setBatchno("");
                aTransItem.setCodeSpecific("");
                aTransItem.setDescSpecific("");
                aTransItem.setUnitCostPrice(0);
                aTransItem.setQty_total(0);
            }
            Stock st = new StockBean().getStock(aTransItem.getStockId());
            if (null != st) {
                aTransItem.setBatchno(st.getBatchno());
                aTransItem.setCodeSpecific(st.getCodeSpecific());
                aTransItem.setDescSpecific(st.getDescSpecific());
                aTransItem.setUnitCostPrice(this.getItemLatestUnitCostPrice(aTransItem.getItemId(), aTransItem.getBatchno(), aTransItem.getCodeSpecific(), aTransItem.getDescSpecific()));
                aTransItem.setQty_total(st.getCurrentqty());
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    public void refreshAdjustUponSpecificChange(TransItem aTransItem) {
        try {
            if (null != aTransItem) {
                //aTransItem.setUnitCostPrice(0);
                aTransItem.setQty_total(0);
            }
            Stock st = null;
            try {
                st = new StockBean().getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aTransItem.getItemId(), aTransItem.getBatchno(), aTransItem.getCodeSpecific(), aTransItem.getDescSpecific());
            } catch (Exception e) {
                //do nothing
            }
            if (null != st) {
                aTransItem.setQty_total(st.getCurrentqty());
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    public String getTransItemInventCostAccount(TransactionType aTransType, TransactionReason aTransReason, Item aItem) {
        String AccountCode = "";
        try {
            if (aTransType.getTransactionTypeName().equals("SALE INVOICE") || aTransType.getTransactionTypeName().equals("SALE ORDER")) {
                if (aItem.getExpenseAccountCode() != null && aItem.getExpenseAccountCode().length() > 0) {
                    AccountCode = aItem.getExpenseAccountCode();
                } else {
                    if (aItem.getItemType().equals("PRODUCT")) {//5-10-000-010 - COS Products
                        AccountCode = "5-10-000-010";
                    } else if (aItem.getItemType().equals("SERVICE")) {//5-10-000-020 - COS Services	
                        AccountCode = "5-10-000-020";
                    }
                }
            } else if (aTransType.getTransactionTypeName().equals("PURCHASE INVOICE") || aTransType.getTransactionTypeName().equals("EXPENSE ENTRY")) {
                if (aTransReason.getTransactionReasonId() == 1) {//GOOD AND SERVICE
                    if (aItem.getExpenseAccountCode() != null && aItem.getExpenseAccountCode().length() > 0) {
                        AccountCode = aItem.getExpenseAccountCode();
                    } else {
                        if (aItem.getItemType().equals("PRODUCT")) {
                            AccountCode = "5-10-000-010";
                        } else if (aItem.getItemType().equals("SERVICE")) {
                            AccountCode = "5-10-000-020";
                        }
                    }
                } else if (aTransReason.getTransactionReasonId() == 27 || aTransReason.getTransactionReasonId() == 43) {//EXPENSE,EXPENSE ENTRY
                    AccountCode = aItem.getExpenseAccountCode();
                } else if (aTransReason.getTransactionReasonId() == 29) {//ASSET
                    AccountCode = aItem.getAssetAccountCode();
                }
            } else if (aTransType.getTransactionTypeName().equals("PURCHASE ORDER")) {
                if (aTransReason.getTransactionReasonId() == 12) {//GOOD AND SERVICE
                    if (aItem.getExpenseAccountCode() != null && aItem.getExpenseAccountCode().length() > 0) {
                        AccountCode = aItem.getExpenseAccountCode();
                    } else {
                        if (aItem.getItemType().equals("PRODUCT")) {
                            AccountCode = "5-10-000-010";
                        } else if (aItem.getItemType().equals("SERVICE")) {
                            AccountCode = "5-10-000-020";
                        }
                    }
                } else if (aTransReason.getTransactionReasonId() == 30) {//EXPENSE
                    AccountCode = aItem.getExpenseAccountCode();
                } else if (aTransReason.getTransactionReasonId() == 31) {//ASSET
                    AccountCode = aItem.getAssetAccountCode();
                }
            } else if (aTransType.getTransactionTypeName().equals("ITEM RECEIVED")) {
                //aTransItemToUpdate.setAccountCode(aItem.getAssetAccountCode());
                if (aTransReason.getTransactionReasonId() == 13) {//GOOD/PRODUCT
                    if (aItem.getExpenseAccountCode() != null && aItem.getExpenseAccountCode().length() > 0) {
                        AccountCode = aItem.getExpenseAccountCode();
                    } else {
                        if (aItem.getItemType().equals("PRODUCT")) {
                            AccountCode = "5-10-000-010";
                        } else if (aItem.getItemType().equals("SERVICE")) {
                            AccountCode = "5-10-000-020";
                        }
                    }
                } else if (aTransReason.getTransactionReasonId() == 32) {//EXPENSE
                    AccountCode = aItem.getExpenseAccountCode();
                } else if (aTransReason.getTransactionReasonId() == 28) {//ASSET
                    AccountCode = aItem.getAssetAccountCode();
                }
            } else if (aTransType.getTransactionTypeName().equals("HIRE INVOICE") || aTransType.getTransactionTypeName().equals("HIRE RETURN INVOICE") || aTransType.getTransactionTypeName().equals("HIRE QUOTATION")) {
                AccountCode = "4-10-000-050";
            } else if (aTransType.getTransactionTypeName().equals("DISPOSE STOCK") || aTransType.getTransactionTypeName().equals("STOCK CONSUMPTION")) {
                if (aItem.getExpenseAccountCode() != null && aItem.getExpenseAccountCode().length() > 0) {
                    AccountCode = aItem.getExpenseAccountCode();
                } else {
                    if (aItem.getItemType().equals("PRODUCT")) {//5-10-000-010 - COS Products
                        AccountCode = "5-10-000-010";
                    } else if (aItem.getItemType().equals("SERVICE")) {//5-10-000-020 - COS Services	
                        AccountCode = "5-10-000-020";
                    }
                }
            } else {
                AccountCode = "";
            }
        } catch (Exception e) {
            AccountCode = "";
        }
        return AccountCode;
    }

    public void updateAccCodeTransItem(TransItem aTransItemToUpdate, AccCoa aAccCoa) {
        try {
            if (null != aAccCoa) {
                aTransItemToUpdate.setAccountCode(aAccCoa.getAccountCode());
            } else {
                aTransItemToUpdate.setAccountCode("");
            }
        } catch (NullPointerException npe) {
            aTransItemToUpdate.setAccountCode("");
        }
    }

    public void updateModelTransItemBarCode(Trans aTrans, TransItem aTransItemToUpdate, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem aSelectedTransItem, Item aSelectedItem) {//auto=1 for itemCode, auto=0 is for desc/code    ,2 is for other
        aStatusBean.setItemAddedStatus("");
        aStatusBean.setItemNotAddedStatus("");
        aStatusBean.setShowItemAddedStatus(0);
        aStatusBean.setShowItemNotAddedStatus(0);

        StockBean sb = new StockBean();
        List<Stock> batches = new ArrayList<Stock>();
        //TransItemBean tib = this;
        DiscountPackageItem dpi = null;

        try {
            aSelectedItem = new ItemBean().findItemByCodeActive(aSelectedTransItem.getItemCode());
        } catch (NullPointerException npe) {
            aSelectedItem = null;
        }

        if (aSelectedItem == null) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("ENTERED BARCODE NUMBER DOES NOT MATCH WITH ANY ITEM REGISTERED");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
            new ItemBean().clearItem(aSelectedItem);
            this.clearTransItem2(aSelectedTransItem);
        } else if (null != aSelectedItem && aSelectedItem.getIsGeneral() == 1) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("A GENERAL ITEM CANNOT BE TRANSACTED BY BAR-CODE");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
            new ItemBean().clearItem(aSelectedItem);
            this.clearTransItem2(aSelectedTransItem);
        } else if (aSelectedItem != null) {
            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            xrate = new AccXrateBean().getXrate(aSelectedItem.getCurrencyCode(), aTrans.getCurrencyCode());
            if (aSelectedItem.getCurrencyCode().equals(LocalCurrency.getCurrencyCode()) && !aTrans.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                XrateMultiply = 1 / xrate;
            } else {
                XrateMultiply = xrate;
            }
            //aSelectedItem.setUnitCostPrice(aSelectedItem.getUnitCostPrice() * XrateMultiply);
            aSelectedItem.setUnitRetailsalePrice(aSelectedItem.getUnitRetailsalePrice() * XrateMultiply);
            aSelectedItem.setUnitWholesalePrice(aSelectedItem.getUnitWholesalePrice() * XrateMultiply);

            aSelectedTransItem.setItemId(aSelectedItem.getItemId());
            aSelectedTransItem.setItemQty(1);
            aSelectedTransItem.setIsTradeDiscountVatLiable(CompanySetting.getIsTradeDiscountVatLiable());
            if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE INVOICE")) {
                dpi = new DiscountPackageItemBean().getActiveDiscountPackageItem(new GeneralUserSetting().getCurrentStore().getStoreId(), aSelectedItem.getItemId(), aSelectedTransItem.getItemQty(), aTrans.getTransactorId(), aSelectedItem.getCategoryId(), aSelectedItem.getSubCategoryId());
            } else {
                dpi = null;
            }

            if (dpi != null) {
                //dpi.setRetailsaleDiscountAmt(dpi.getRetailsaleDiscountAmt() * XrateMultiply);
                //dpi.setWholesaleDiscountAmt(dpi.getWholesaleDiscountAmt() * XrateMultiply);
            }
            //get account code for the item
            try {
                if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE INVOICE")) {
                    if (aSelectedItem.getItemType().equals("PRODUCT")) {//4-10-000-010 - SALES Products
                        aSelectedTransItem.setAccountCode("4-10-000-010");
                    } else if (aSelectedItem.getItemType().equals("SERVICE")) {//4-10-000-020 - SALES Services	
                        aSelectedTransItem.setAccountCode("4-10-000-020");
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("PURCHASE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("EXPENSE ENTRY")) {
                    if (new GeneralUserSetting().getCurrentTransactionReasonId() == 1) {//GOOD AND SERVICE
                        if (aSelectedItem.getItemType().equals("PRODUCT")) {
                            aSelectedTransItem.setAccountCode("5-10-000-010");
                        } else if (aSelectedItem.getItemType().equals("SERVICE")) {
                            aSelectedTransItem.setAccountCode("5-10-000-020");
                        }
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 27 || new GeneralUserSetting().getCurrentTransactionReasonId() == 43) {//EXPENSE,EXPENSE ENTRY
                        aSelectedTransItem.setAccountCode(aSelectedItem.getExpenseAccountCode());
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 29) {//ASSET
                        aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("PURCHASE ORDER")) {
                    if (new GeneralUserSetting().getCurrentTransactionReasonId() == 12) {//GOOD AND SERVICE
                        if (aSelectedItem.getItemType().equals("PRODUCT")) {
                            aSelectedTransItem.setAccountCode("5-10-000-010");
                        } else if (aSelectedItem.getItemType().equals("SERVICE")) {
                            aSelectedTransItem.setAccountCode("5-10-000-020");
                        }
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 30) {//EXPENSE
                        aSelectedTransItem.setAccountCode(aSelectedItem.getExpenseAccountCode());
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 31) {//ASSET
                        aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
                    }
                } else if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("ITEM RECEIVED")) {
                    //aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
                    if (new GeneralUserSetting().getCurrentTransactionReasonId() == 13) {//GOOD/PRODUCT
                        if (aSelectedItem.getItemType().equals("PRODUCT")) {
                            aSelectedTransItem.setAccountCode("5-10-000-010");
                        } else if (aSelectedItem.getItemType().equals("SERVICE")) {
                            aSelectedTransItem.setAccountCode("5-10-000-020");
                        }
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 32) {//EXPENSE
                        aSelectedTransItem.setAccountCode(aSelectedItem.getExpenseAccountCode());
                    } else if (new GeneralUserSetting().getCurrentTransactionReasonId() == 28) {//ASSET
                        aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
                    }
                } else {
                    aSelectedTransItem.setAccountCode("");
                }
            } catch (NullPointerException npe) {
                aSelectedTransItem.setAccountCode("");
            }

            if ("EXEMPT SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())) {
                aSelectedTransItem.setUnitPrice(0);
                aSelectedTransItem.setUnitTradeDiscount(0);
            } else if ("COST-PRICE SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())) {
                //aSelectedTransItem.setUnitPrice(aSelectedItem.getUnitCostPrice());
                aSelectedTransItem.setUnitTradeDiscount(0);
            } else if ("WHOLE SALE INVOICE".equals(new GeneralUserSetting().getCurrentSaleType())) {
                aSelectedTransItem.setUnitPrice(aSelectedItem.getUnitWholesalePrice());
                if (dpi != null) {
                    //aSelectedTransItem.setUnitTradeDiscount(dpi.getWholesaleDiscountAmt());
                    aSelectedTransItem.setUnitTradeDiscount(aSelectedItem.getUnitWholesalePrice() * dpi.getWholesaleDiscountAmt() / 100);
                }
            } else {
                if (new GeneralUserSetting().getCurrentTransactionTypeName().equals("SALE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("PURCHASE INVOICE") || new GeneralUserSetting().getCurrentTransactionTypeName().equals("DISPOSE STOCK")) {
                    aSelectedTransItem.setUnitPrice(aSelectedItem.getUnitRetailsalePrice());
                } else {
                    aSelectedTransItem.setUnitPrice(0);
                }
                if (dpi != null) {
                    //aSelectedTransItem.setUnitTradeDiscount(dpi.getRetailsaleDiscountAmt());
                    aSelectedTransItem.setUnitTradeDiscount(aSelectedItem.getUnitRetailsalePrice() * dpi.getRetailsaleDiscountAmt() / 100);
                }
            }
            aSelectedTransItem.setVatRated(aSelectedItem.getVatRated());
            aSelectedTransItem.setItemCode(aSelectedItem.getItemCode());

            batches = sb.getStocks(new GeneralUserSetting().getCurrentStore().getStoreId(), aSelectedItem.getItemId());
            if (batches.size() == 1) {
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(0);
                //update model
                aSelectedTransItem.setItemQty(1);
                aSelectedTransItem.setAmount(aSelectedTransItem.getItemQty() * aSelectedTransItem.getUnitPrice());
                aSelectedTransItem.setBatchno(batches.get(0).getBatchno());
                //Add Item to the list

                //get item's stock details
                Stock st = new Stock();
                st = sb.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aSelectedTransItem.getItemId(), aSelectedTransItem.getBatchno(), "", "");

                //unpacking - AutoUnPackModule
                //process items that might need to be unpacked
                if (aSelectedItem.getIsTrack() == 1 && "SALE INVOICE".equals(new GeneralUserSetting().getCurrentTransactionTypeName()) & "Yes".equals(CompanySetting.getIsAllowAutoUnpack())) {
                    TransItemBean autoTIB = this;
                    double TotalQtyFromList = 0;
                    try {
                        TotalQtyFromList = autoTIB.getListTotalItemBatchQty(aActiveTransItems, aSelectedTransItem.getItemId(), aSelectedTransItem.getBatchno(), aSelectedTransItem.getCodeSpecific(), aSelectedTransItem.getDescSpecific());
                    } catch (NullPointerException npe) {
                        TotalQtyFromList = 0;
                    }
                    double TotalQtyFromListAndNew = TotalQtyFromList + aSelectedTransItem.getItemQty();

                    double SmallItemQtyNeeded = 0;
                    try {
                        SmallItemQtyNeeded = TotalQtyFromListAndNew - st.getCurrentqty();
                    } catch (NullPointerException npe) {
                        SmallItemQtyNeeded = TotalQtyFromListAndNew;
                    }
                    //Unpackaging if needed
                    if (st == null || st.getCurrentqty() == 0) {//this item and batch does not exist in this store OR its quatity is 0 (out of stock)
                        if (autoTIB.autoUnpackItem(aSelectedTransItem, SmallItemQtyNeeded) == 1) {
                        } else {
                        }
                    } else if ((aSelectedTransItem.getItemQty() + autoTIB.getListTotalItemBatchQty(aActiveTransItems, aSelectedTransItem.getItemId(), aSelectedTransItem.getBatchno(), aSelectedTransItem.getCodeSpecific(), aSelectedTransItem.getDescSpecific())) > st.getCurrentqty()) {
                        //double TotalQtyFromList=this.getListTotalItemBatchQty(aActiveTransItems, NewTransItem.getItemId(), NewTransItem.getBatchno());
                        if (autoTIB.autoUnpackItem(aSelectedTransItem, SmallItemQtyNeeded) == 1) {
                        } else {
                        }
                    }
                    //re-calculate stock for small item after unpackaing
                    st = sb.getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aSelectedTransItem.getItemId(), aSelectedTransItem.getBatchno(), "", "");
                }
                if (st == null || st.getCurrentqty() == 0) {//aSelectedTransItem item and batch does not exist in aSelectedTransItem store OR its quatity is 0 (out of stock)
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("(" + aSelectedItem.getDescription() + ") " + "SELECTED ITEM - DOES NOT EXIST or IS OUT OF STOCK !");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                    new ItemBean().clearItem(aSelectedItem);
                    this.clearTransItem2(aSelectedTransItem);
                } else if ((aSelectedTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, aSelectedTransItem.getItemId(), aSelectedTransItem.getBatchno(), aSelectedTransItem.getCodeSpecific(), aSelectedTransItem.getDescSpecific())) > st.getCurrentqty()) {
                    //BACK
                    //check if supplied qty + existing qty is more than total current stock qty
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("(" + aSelectedItem.getDescription() + ") " + "SELECTED ITEM and BATCH - INSUFFICIENT STOCK !");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                    new ItemBean().clearItem(aSelectedItem);
                    this.clearTransItem2(aSelectedTransItem);
                } else {
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(0);
                    this.addTransItemAutoAdd(aTrans, aStatusBean, aActiveTransItems, aSelectedTransItem, aSelectedItem, "BarCode");
                }
            } else {//if batches are many OR item has zero current quantity
                aSelectedTransItem.setItemQty(0);
                aSelectedTransItem.setAmount(0);
                if (batches.size() > 1) {
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("(" + aSelectedItem.getDescription() + ") " + "ITEM HAS BATCHES, ENTER ITEM MANUALLY AND SELECT BATCH No");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                    new ItemBean().clearItem(aSelectedItem);
                    this.clearTransItem2(aSelectedTransItem);
                } else if (batches.size() <= 0) {
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("(" + aSelectedItem.getDescription() + ") " + "ITEM IS OUT OF STOCK");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                    new ItemBean().clearItem(aSelectedItem);
                    this.clearTransItem2(aSelectedTransItem);
                }
            }
        }
    }

    public void updateModelTransItemAutoAddCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, TransItem aTransItemToUpdate, StatusBean aStatusBean, List<TransItem> aActiveTransItems, TransItem aSelectedTransItem, Item aSelectedItem, String aEntryMode) {//auto=1 for itemCode, auto=0 is for desc/code    ,2 is for other
        //Capture Modes can be: BarCode,ItemClick
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);

        aStatusBean.setItemAddedStatus("");
        aStatusBean.setItemNotAddedStatus("");
        aStatusBean.setShowItemAddedStatus(0);
        aStatusBean.setShowItemNotAddedStatus(0);

        StockBean sb = new StockBean();
        List<Stock> batches = new ArrayList<Stock>();
        //TransItemBean tib = this;
        DiscountPackageItem dpi = null;
        if (aEntryMode.equals("BarCode")) {
            try {
                aSelectedItem = new ItemBean().findItemByCodeActive(aSelectedTransItem.getItemCode());
            } catch (NullPointerException npe) {
                aSelectedItem = null;
            }
        }//For ItemClick -- aSelectedItem is already Item Object

        if (aSelectedItem == null) {
            aStatusBean.setItemAddedStatus("");
            aStatusBean.setItemNotAddedStatus("UNABLE TO FIND ITEM WITH GIVEN DETAILS");
            aStatusBean.setShowItemAddedStatus(0);
            aStatusBean.setShowItemNotAddedStatus(1);
            new ItemBean().clearItem(aSelectedItem);
            this.clearTransItem2(aSelectedTransItem);
        } else if (aSelectedItem != null) {
            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            xrate = new AccXrateBean().getXrate(aSelectedItem.getCurrencyCode(), aTrans.getCurrencyCode());
            if (aSelectedItem.getCurrencyCode().equals(LocalCurrency.getCurrencyCode()) && !aTrans.getCurrencyCode().equals(LocalCurrency.getCurrencyCode())) {
                XrateMultiply = 1 / xrate;
            } else {
                XrateMultiply = xrate;
            }
            //aSelectedItem.setUnitCostPrice(aSelectedItem.getUnitCostPrice() * XrateMultiply);
            aSelectedItem.setUnitRetailsalePrice(aSelectedItem.getUnitRetailsalePrice() * XrateMultiply);
            aSelectedItem.setUnitWholesalePrice(aSelectedItem.getUnitWholesalePrice() * XrateMultiply);
            aSelectedItem.setUnit_special_price(aSelectedItem.getUnit_special_price() * XrateMultiply);

            aSelectedTransItem.setItemId(aSelectedItem.getItemId());
            aSelectedTransItem.setItemQty(1);
            aSelectedTransItem.setIsTradeDiscountVatLiable(CompanySetting.getIsTradeDiscountVatLiable());
            if (transtype.getTransactionTypeName().equals("SALE INVOICE") || transtype.getTransactionTypeName().equals("SALE ORDER")) {
                dpi = new DiscountPackageItemBean().getActiveDiscountPackageItem(store.getStoreId(), aSelectedItem.getItemId(), aSelectedTransItem.getItemQty(), aTrans.getTransactorId(), aSelectedItem.getCategoryId(), aSelectedItem.getSubCategoryId());
            } else {
                dpi = null;
            }

            if (dpi != null) {
                //dpi.setRetailsaleDiscountAmt(dpi.getRetailsaleDiscountAmt() * XrateMultiply);
                //dpi.setWholesaleDiscountAmt(dpi.getWholesaleDiscountAmt() * XrateMultiply);
            }
            //get account code for the item
            aSelectedTransItem.setAccountCode(this.getTransItemInventCostAccount(transtype, transreason, aSelectedItem));
//            try {
//                if (transtype.getTransactionTypeName().equals("SALE INVOICE") || transtype.getTransactionTypeName().equals("SALE ORDER")) {
//                    if (aSelectedItem.getItemType().equals("PRODUCT")) {//4-10-000-010 - SALES Products
//                        aSelectedTransItem.setAccountCode("4-10-000-010");
//                    } else if (aSelectedItem.getItemType().equals("SERVICE")) {//4-10-000-020 - SALES Services	
//                        aSelectedTransItem.setAccountCode("4-10-000-020");
//                    }
//                } else if (transtype.getTransactionTypeName().equals("PURCHASE INVOICE") || transtype.getTransactionTypeName().equals("EXPENSE ENTRY")) {
//                    if (transreason.getTransactionReasonId() == 1) {//GOOD AND SERVICE
//                        if (aSelectedItem.getItemType().equals("PRODUCT")) {
//                            aSelectedTransItem.setAccountCode("5-10-000-010");
//                        } else if (aSelectedItem.getItemType().equals("SERVICE")) {
//                            aSelectedTransItem.setAccountCode("5-10-000-020");
//                        }
//                    } else if (transreason.getTransactionReasonId() == 27 || transreason.getTransactionReasonId() == 43) {//EXPENSE,EXPENSE ENTRY
//                        aSelectedTransItem.setAccountCode(aSelectedItem.getExpenseAccountCode());
//                    } else if (transreason.getTransactionReasonId() == 29) {//ASSET
//                        aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
//                    }
//                } else if (transtype.getTransactionTypeName().equals("PURCHASE ORDER")) {
//                    if (transreason.getTransactionReasonId() == 12) {//GOOD AND SERVICE
//                        if (aSelectedItem.getItemType().equals("PRODUCT")) {
//                            aSelectedTransItem.setAccountCode("5-10-000-010");
//                        } else if (aSelectedItem.getItemType().equals("SERVICE")) {
//                            aSelectedTransItem.setAccountCode("5-10-000-020");
//                        }
//                    } else if (transreason.getTransactionReasonId() == 30) {//EXPENSE
//                        aSelectedTransItem.setAccountCode(aSelectedItem.getExpenseAccountCode());
//                    } else if (transreason.getTransactionReasonId() == 31) {//ASSET
//                        aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
//                    }
//                } else if (transtype.getTransactionTypeName().equals("ITEM RECEIVED")) {
//                    //aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
//                    if (transreason.getTransactionReasonId() == 13) {//GOOD/PRODUCT
//                        if (aSelectedItem.getItemType().equals("PRODUCT")) {
//                            aSelectedTransItem.setAccountCode("5-10-000-010");
//                        } else if (aSelectedItem.getItemType().equals("SERVICE")) {
//                            aSelectedTransItem.setAccountCode("5-10-000-020");
//                        }
//                    } else if (transreason.getTransactionReasonId() == 32) {//EXPENSE
//                        aSelectedTransItem.setAccountCode(aSelectedItem.getExpenseAccountCode());
//                    } else if (transreason.getTransactionReasonId() == 28) {//ASSET
//                        aSelectedTransItem.setAccountCode(aSelectedItem.getAssetAccountCode());
//                    }
//                } else {
//                    aSelectedTransItem.setAccountCode("");
//                }
//            } catch (NullPointerException npe) {
//                aSelectedTransItem.setAccountCode("");
//            }

            if ("EXEMPT SALE INVOICE".equals(aSaleType)) {
                aSelectedTransItem.setUnitPrice(0);
                aSelectedTransItem.setUnitTradeDiscount(0);
            } else if ("COST-PRICE SALE INVOICE".equals(aSaleType)) {
                //aSelectedTransItem.setUnitPrice(aSelectedItem.getUnitCostPrice());
                aSelectedTransItem.setUnitTradeDiscount(0);
            } else if ("WHOLE SALE INVOICE".equals(aSaleType)) {
                aSelectedTransItem.setUnitPrice(aSelectedItem.getUnitWholesalePrice());
                if (dpi != null) {
                    //aSelectedTransItem.setUnitTradeDiscount(dpi.getWholesaleDiscountAmt());
                    aSelectedTransItem.setUnitTradeDiscount(aSelectedItem.getUnitWholesalePrice() * dpi.getWholesaleDiscountAmt() / 100);
                }
            } else if ("SPECIAL SALE INVOICE".equals(aSaleType)) {
                aSelectedTransItem.setUnitPrice(aSelectedItem.getUnit_special_price());
                if (dpi != null) {
                    aSelectedTransItem.setUnitTradeDiscount(0);
                }
            } else {
                if (transtype.getTransactionTypeName().equals("SALE INVOICE") || transtype.getTransactionTypeName().equals("PURCHASE INVOICE") || transtype.getTransactionTypeName().equals("DISPOSE STOCK") || transtype.getTransactionTypeName().equals("SALE ORDER")) {
                    aSelectedTransItem.setUnitPrice(aSelectedItem.getUnitRetailsalePrice());
                } else {
                    aSelectedTransItem.setUnitPrice(0);
                }
                if (dpi != null) {
                    //aSelectedTransItem.setUnitTradeDiscount(dpi.getRetailsaleDiscountAmt());
                    aSelectedTransItem.setUnitTradeDiscount(aSelectedItem.getUnitRetailsalePrice() * dpi.getRetailsaleDiscountAmt() / 100);
                }
            }
            aSelectedTransItem.setVatRated(aSelectedItem.getVatRated());
            aSelectedTransItem.setItemCode(aSelectedItem.getItemCode());

            batches = sb.getStocks(store.getStoreId(), aSelectedItem.getItemId());
            if (batches.size() > 1) {
                aSelectedTransItem.setItemQty(0);
                aSelectedTransItem.setAmount(0);
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("(" + aSelectedItem.getDescription() + ") " + "ITEM HAS BATCHES, ENTER ITEM DESCRIPTION MANUALLY...");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(1);
                new ItemBean().clearItem(aSelectedItem);
                this.clearTransItem2(aSelectedTransItem);
            } else {//batches either 1 or not there
                aStatusBean.setItemAddedStatus("");
                aStatusBean.setItemNotAddedStatus("");
                aStatusBean.setShowItemAddedStatus(0);
                aStatusBean.setShowItemNotAddedStatus(0);
                int unpack_needed = 0;
                double SmallItemQtyNeeded = 0;
                Stock st = new Stock();
                if (batches.size() == 1) {
                    st = sb.getStock(store.getStoreId(), aSelectedTransItem.getItemId(), batches.get(0).getBatchno(), batches.get(0).getCodeSpecific(), batches.get(0).getDescSpecific());
                }
                if (aSelectedItem.getIsTrack() == 1 && ("SALE INVOICE".equals(transtype.getTransactionTypeName()) || "SALE ORDER".equals(transtype.getTransactionTypeName())) & "Yes".equals(CompanySetting.getIsAllowAutoUnpack())) {
                    double TotalQtyFromList = 0;
                    try {
                        TotalQtyFromList = this.getListTotalItemBatchQty(aActiveTransItems, aSelectedTransItem.getItemId(), aSelectedTransItem.getBatchno(), aSelectedTransItem.getCodeSpecific(), aSelectedTransItem.getDescSpecific());
                    } catch (NullPointerException npe) {
                        TotalQtyFromList = 0;
                    }
                    double TotalQtyFromListAndNew = TotalQtyFromList + aSelectedTransItem.getItemQty();
                    try {
                        SmallItemQtyNeeded = TotalQtyFromListAndNew - st.getCurrentqty();
                    } catch (NullPointerException npe) {
                        SmallItemQtyNeeded = TotalQtyFromListAndNew;
                    }
                    if (SmallItemQtyNeeded > 0) {
                        unpack_needed = 1;
                    }
                }
                if (unpack_needed == 1) {
                    if (this.autoUnpackItem(aSelectedTransItem, SmallItemQtyNeeded) == 1) {
                        //refresh/recalculate stock
                        st = new Stock();
                        try {
                            st = sb.getStocks(store.getStoreId(), aSelectedTransItem.getItemId()).get(0);
                        } catch (Exception e) {
                        }
                    } else {
                    }
                }
                //update model
                aSelectedTransItem.setItemQty(1);
                try {
                    if (null == st.getBatchno()) {
                        aSelectedTransItem.setBatchno("");
                    } else {
                        aSelectedTransItem.setBatchno(st.getBatchno());
                    }
                } catch (NullPointerException npe) {
                    aSelectedTransItem.setBatchno("");
                }
                try {
                    if (null == st.getCodeSpecific()) {
                        aSelectedTransItem.setCodeSpecific("");
                    } else {
                        aSelectedTransItem.setCodeSpecific(st.getCodeSpecific());
                    }
                } catch (NullPointerException npe) {
                    aSelectedTransItem.setCodeSpecific("");
                }
                try {
                    if (null == st.getDescSpecific()) {
                        aSelectedTransItem.setDescSpecific("");
                    } else {
                        aSelectedTransItem.setDescSpecific(st.getDescSpecific());
                    }
                } catch (NullPointerException npe) {
                    aSelectedTransItem.setDescSpecific("");
                }
                if ((aSelectedTransItem.getItemQty() + this.getListTotalItemBatchQty(aActiveTransItems, aSelectedTransItem.getItemId(), aSelectedTransItem.getBatchno(), aSelectedTransItem.getCodeSpecific(), aSelectedTransItem.getDescSpecific())) > st.getCurrentqty() && "SALE INVOICE".equals(transtype.getTransactionTypeName())) {
                    //BACK
                    //check if supplied qty + existing qty is more than total current stock qty
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("(" + aSelectedItem.getDescription() + ") " + "SELECTED ITEM and BATCH - INSUFFICIENT STOCK !");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(1);
                    new ItemBean().clearItem(aSelectedItem);
                    this.clearTransItem2(aSelectedTransItem);
                } else {//CABBAGE
                    aStatusBean.setItemAddedStatus("");
                    aStatusBean.setItemNotAddedStatus("");
                    aStatusBean.setShowItemAddedStatus(0);
                    aStatusBean.setShowItemNotAddedStatus(0);
                    aSelectedTransItem.setAmount(aSelectedTransItem.getItemQty() * aSelectedTransItem.getUnitPrice());
                    aSelectedTransItem.setBatchno(st.getBatchno());
                    this.addTransItemAutoAdd(aTrans, aStatusBean, aActiveTransItems, aSelectedTransItem, aSelectedItem, aEntryMode);
                }
            }
        }
    }

    public void calUnpackedQty(TransItem aTransItem) {
        try {
            if (aTransItem.getItemId() != 0 && aTransItem.getItemId2() != 0) {
                aTransItem.setItemQty2((new ItemMapBean().getItemMapByBigItemId(aTransItem.getItemId()).getFractionQty()) * aTransItem.getItemQty());
            } else {
                aTransItem.setItemQty2(0);
            }
        } catch (Exception e) {
            aTransItem.setItemQty2(0);
        }
    }

    public String displayItemDesc(String aSpecificCode, String aSpecificDesc, String aMoreDesc, String aWarrantyDesc) {
        String itemStr = "";
        try {
            if (aSpecificCode.length() > 0) {
                itemStr = aSpecificCode;
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aSpecificDesc.length() > 0) {
                if (itemStr.length() > 0) {
                    itemStr = itemStr + ", " + aSpecificDesc;
                } else {
                    itemStr = aSpecificDesc;
                }
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aMoreDesc.length() > 0) {
                if (itemStr.length() > 0) {
                    itemStr = itemStr + ", " + aMoreDesc;
                } else {
                    itemStr = aMoreDesc;
                }
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aWarrantyDesc.length() > 0) {
                if (itemStr.length() > 0) {
                    itemStr = itemStr + ", " + aWarrantyDesc;
                } else {
                    itemStr = aWarrantyDesc;
                }
            }
        } catch (NullPointerException npe) {
        }

        return itemStr;
    }

    public void updateTransItemSpecific(TransItem aTransItem, long aStockId) {
        try {
            aTransItem.setCodeSpecific("");
            aTransItem.setDescSpecific("");
            Stock stock = null;
            stock = new StockBean().getStock(aStockId);
            if (null != stock && null != aTransItem) {
                aTransItem.setCodeSpecific(stock.getCodeSpecific());
                aTransItem.setDescSpecific(stock.getDescSpecific());
            } else {
                aTransItem.setCodeSpecific("");
                aTransItem.setDescSpecific("");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateTransItemBatchSpecific(TransItem aTransItem, long aStockId) {
        try {
            aTransItem.setBatchno("");
            aTransItem.setCodeSpecific("");
            aTransItem.setDescSpecific("");
            aTransItem.setItemExpryDate(null);
            aTransItem.setItemMnfDate(null);
            Stock stock = null;
            stock = new StockBean().getStock(aStockId);
            if (null == stock || null == aTransItem) {
                aTransItem.setBatchno("");
                aTransItem.setCodeSpecific("");
                aTransItem.setDescSpecific("");
                aTransItem.setItemExpryDate(null);
                aTransItem.setItemMnfDate(null);
            } else {
                aTransItem.setBatchno(stock.getBatchno());
                aTransItem.setCodeSpecific(stock.getCodeSpecific());
                aTransItem.setDescSpecific(stock.getDescSpecific());
                try {
                    aTransItem.setItemExpryDate(stock.getItemExpDate());
                } catch (NullPointerException npe) {
                    aTransItem.setItemExpryDate(null);
                }
                try {
                    aTransItem.setItemMnfDate(stock.getItemMnfDate());
                } catch (NullPointerException npe) {
                    aTransItem.setItemMnfDate(null);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateTransItemTransferSpecific(TransItem aTransItem, long aStockId) {
        try {
            aTransItem.setQty_damage_total(0);//storing available stock qty
            Stock stock = null;
            stock = new StockBean().getStock(aStockId);
            if (null == stock || null == aTransItem) {
                //do nothing
            } else {
                aTransItem.setQty_damage_total(stock.getCurrentqty());
            }
        } catch (Exception e) {
            System.err.println("updateTransItemTransferSpecific:" + e.getMessage());
        }
    }

    public void refreshCurrentStock(List<TransItem> aTransItems) {
        try {
            Stock stock;
            for (int i = 0; i < aTransItems.size(); i++) {
                String BatNo = "", CodSpe = "", DesSpe = "";
                try {
                    BatNo = aTransItems.get(i).getBatchno();
                } catch (NullPointerException npe) {
                }
                try {
                    CodSpe = aTransItems.get(i).getCodeSpecific();
                } catch (NullPointerException npe) {
                }
                try {
                    DesSpe = aTransItems.get(i).getDescSpecific();
                } catch (NullPointerException npe) {
                }
                try {
                    stock = new StockBean().getStock(new GeneralUserSetting().getCurrentStore().getStoreId(), aTransItems.get(i).getItemId(), BatNo, CodSpe, DesSpe);
                    aTransItems.get(i).setQty_damage_total(stock.getCurrentqty());
                } catch (NullPointerException npe) {
                    aTransItems.get(i).setQty_damage_total(0);
                }
            }
        } catch (Exception e) {
            System.err.println("refreshCurrentStock:" + e.getMessage());
        }
    }

    public void updateTransItemAccountCode(TransItem aTransItem, AccCoa aAccountCoa) {
        try {
            if (null != aAccountCoa) {
                aTransItem.setAccountCode(aAccountCoa.getAccountCode());
            } else {
                aTransItem.setAccountCode("");
            }
        } catch (Exception e) {

        }
    }

    public List<TransItem> getTransItemListCurLessPrevQty(List<TransItem> aCurTransItemList, Trans aCurTrans) {
        List<TransItem> CurLessPrevTransItemList = new ArrayList<>();
        List<TransItem> PrevTransItemList = new ArrayList<>();
        PrevTransItemList = new TransItemBean().getTransItemsByTransactionId(aCurTrans.getTransactionId());
        int ListItemIndexCur = 0;
        int ListItemNoCur = aCurTransItemList.size();
        int ListItemIndexPrev = 0;
        int ListItemNoPrev = PrevTransItemList.size();
        double PrevQty = 0;
        double CurQty = 0;
        TransItem CurTransItem = null;
        TransItem PrevTransItem = null;
        //TransItem CurLessPrevTransItem = null;
        while (ListItemIndexCur < ListItemNoCur) {
            //CurLessPrevTransItem = new TransItem();
            CurQty = 0;
            CurTransItem = new TransItem();
            this.copyObjectTransItem(aCurTransItemList.get(ListItemIndexCur), CurTransItem);
            CurQty = CurTransItem.getItemQty();
            ListItemIndexPrev = 0;
            while (ListItemIndexPrev < ListItemNoPrev) {
                PrevQty = 0;
                PrevTransItem = new TransItem();
                //PrevTransItem = PrevTransItemList.get(ListItemIndexPrev);
                this.copyObjectTransItem(PrevTransItemList.get(ListItemIndexPrev), PrevTransItem);
                if (CurTransItem.getItemId() == PrevTransItem.getItemId() && CurTransItem.getBatchno().equals(PrevTransItem.getBatchno()) && CurTransItem.getCodeSpecific() == null ? PrevTransItem.getCodeSpecific() == null : CurTransItem.getCodeSpecific().equals(PrevTransItem.getCodeSpecific()) && CurTransItem.getDescSpecific() == null ? PrevTransItem.getDescSpecific() == null : CurTransItem.getDescSpecific().equals(PrevTransItem.getDescSpecific())) {
                    PrevQty = PrevTransItem.getItemQty();
                    break;
                }
                ListItemIndexPrev = ListItemIndexPrev + 1;
            }
            if ((CurQty - PrevQty) > 0) {
                CurTransItem.setItemQty(CurQty - PrevQty);
                CurLessPrevTransItemList.add(CurTransItem);
            }
            ListItemIndexCur = ListItemIndexCur + 1;
        }
        return CurLessPrevTransItemList;
    }

    public long getItemUnitCostPriceLatestTransItemId(int aTransTypeId, int aTransReasId, int aStoreId, long aItemId, String aBatchno, String aCodeSpec, String aDescSpec) {
        String sql = "{call sp_item_unit_cost_price_latest_trans_item_id(?,?,?,?,?,?,?)}";
        long transitemid = 0;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt("in_store_id", aStoreId);
            cs.setLong("in_item_id", aItemId);
            cs.setString("in_batchno", aBatchno);
            cs.setString("in_code_specific", aCodeSpec);
            cs.setString("in_desc_specific", aDescSpec);
            cs.setDouble("in_transaction_type_id", aTransTypeId);
            cs.setDouble("in_transaction_reason_id", aTransReasId);
            rs = cs.executeQuery();
            if (rs.next()) {
                try {
                    transitemid = rs.getLong("transaction_item_id");
                } catch (NullPointerException | SQLException npe) {
                    transitemid = 0;
                }
            }
        } catch (SQLException ex) {
            transitemid = 0;
            System.out.println("getItemUnitCostPriceLatestTransItemId:" + ex.getMessage());
        }
        return transitemid;
    }

    public long getItemUnitCostPriceLatestTransItemId2(int aTransTypeId, int aTransReasId, int aStoreId, long aItemId, String aBatchno, String aCodeSpec, String aDescSpec) {
        String sql = "{call sp_item_unit_cost_price_latest_trans_item_id2(?,?,?,?,?,?,?)}";
        long transitemid = 0;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt("in_store_id", aStoreId);
            cs.setLong("in_item_id", aItemId);
            cs.setString("in_batchno", aBatchno);
            cs.setString("in_code_specific", aCodeSpec);
            cs.setString("in_desc_specific", aDescSpec);
            cs.setDouble("in_transaction_type_id", aTransTypeId);
            cs.setDouble("in_transaction_reason_id", aTransReasId);
            rs = cs.executeQuery();
            if (rs.next()) {
                try {
                    transitemid = rs.getLong("transaction_item_id");
                } catch (NullPointerException | SQLException npe) {
                    transitemid = 0;
                }
            }
        } catch (SQLException ex) {
            transitemid = 0;
            System.out.println("getItemUnitCostPriceLatestTransItemId2:" + ex.getMessage());
        }
        return transitemid;
    }

    public long getItemReceivedLatestTransItemId(long aItemId, String aBatchno, String aCodeSpec, String aDescSpec) {
        String sql = "{call sp_get_item_received_latest_trans_item_id(?,?,?,?)}";
        long transitemid = 0;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_item_id", aItemId);
            cs.setString("in_batchno", aBatchno);
            cs.setString("in_code_specific", aCodeSpec);
            cs.setString("in_desc_specific", aDescSpec);
            rs = cs.executeQuery();
            if (rs.next()) {
                try {
                    transitemid = rs.getLong("transaction_item_id");
                } catch (NullPointerException | SQLException npe) {
                    transitemid = 0;
                }
            }
        } catch (Exception e) {
            transitemid = 0;
            System.out.println("getItemReceivedLatestTransItemId:" + e.getMessage());
        }
        return transitemid;
    }

    public TransItem getTransItem(Long aTransItemId) {
        String sql = "{SELECT * FROM transaction_item WHERE transaction_item_id=?}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransItemFromResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void refreshTransTotalWeight(Trans aTrans, List<TransItem> aActiveTransItems) {
        try {
            aTrans.setTotal_weight(this.calcTransTotalWeight(aActiveTransItems));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double calcTransTotalWeight(List<TransItem> aActiveTransItems) {
        double totalweight = 0;
        try {
            List<TransItem> ati = aActiveTransItems;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            double ItemUnitWeight = 0;
            while (ListItemIndex < ListItemNo) {
                try {
                    ItemUnitWeight = new ItemBean().getItem(ati.get(ListItemIndex).getItemId()).getUnit_weight();
                } catch (NullPointerException npe) {
                    ItemUnitWeight = 0;
                }
                totalweight = totalweight + (ati.get(ListItemIndex).getItemQty() * ItemUnitWeight);
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        return totalweight;
    }

    public String showItemDescription(long aItemId, String aCode, String aName, String aDescMore) {
        String FullItemDesc = "";
        try {
            Item item = new ItemBean().getItem(aItemId);
            if (item.getIsGeneral() == 0) {
                FullItemDesc = item.getDescription();
            } else {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "SHOW_GEN_ITEM_NAME").getParameter_value().equals("1")) {
                    FullItemDesc = item.getDescription();
                }
                if (item.getOverride_gen_name() == 1) {
                    FullItemDesc = item.getDescription();
                } else if (item.getOverride_gen_name() == 2) {
                    FullItemDesc = "";
                }
            }
            //ovveride item desc with alias if set and allowable
            if (item.getAlias_name().length() > 0 && item.getDisplay_alias_name() == 1) {
                FullItemDesc = item.getAlias_name();
            } else {
                FullItemDesc = item.getDescription();
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aName.length() > 0) {
                FullItemDesc = FullItemDesc + " " + aName;
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aCode.length() > 0) {
                FullItemDesc = FullItemDesc + " " + aCode;
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aDescMore.length() > 0) {
                FullItemDesc = FullItemDesc + " " + aDescMore;
            }
        } catch (NullPointerException npe) {
        }
        return FullItemDesc;
    }

    public String showItemDescriptionCEC(String aItemDesc, String aCode, String aName, String aDescMore) {
        String FullItemDesc = "";
        try {
            if (aName.length() <= 0 && aCode.length() <= 0) { //indicator for non-general item
                FullItemDesc = aItemDesc;
            } else {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "SHOW_GEN_ITEM_NAME").getParameter_value().equals("1")) {
                    FullItemDesc = aItemDesc;
                }
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aName.length() > 0) {
                FullItemDesc = FullItemDesc + " " + aName;
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aCode.length() > 0) {
                FullItemDesc = FullItemDesc + " " + aCode;
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aDescMore.length() > 0) {
                FullItemDesc = FullItemDesc + " " + aDescMore;
            }
        } catch (NullPointerException npe) {
        }
        return FullItemDesc;
    }

    public String showItemDescriptionCEC(TransItem aTransItem) {
        int NewLineEachName = 0;//NEW_LINE_EACH_NAME
        int LinesBtnNames = 1;//LINES_BTN_NAMES
        NewLineEachName = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("GENERAL_NAME", "NEW_LINE_EACH_NAME").getParameter_value());
        LinesBtnNames = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("GENERAL_NAME", "LINES_BTN_NAMES").getParameter_value());
        String NewLineStr = " ";
        if (NewLineEachName == 1 && LinesBtnNames > 0) {
            for (int i = 1; i <= LinesBtnNames; i++) {
                NewLineStr = NewLineStr + "<br /> ";
            }
        }
        String FullItemDesc = "";
        try {
            if (aTransItem.getDescSpecific().length() <= 0 && aTransItem.getCodeSpecific().length() <= 0) { //indicator for non-general item
                if (aTransItem.getAlias_name().length() > 0 && aTransItem.getDisplay_alias_name() == 1) {
                    FullItemDesc = aTransItem.getAlias_name();
                } else {
                    FullItemDesc = aTransItem.getDescription();
                }
            } else {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "SHOW_GEN_ITEM_NAME").getParameter_value().equals("1")) {
                    if (aTransItem.getAlias_name().length() > 0 && aTransItem.getDisplay_alias_name() == 1) {
                        FullItemDesc = aTransItem.getAlias_name();
                    } else {
                        FullItemDesc = aTransItem.getDescription();
                    }
                }
                if (aTransItem.getOverride_gen_name() == 1) {
                    if (aTransItem.getAlias_name().length() > 0 && aTransItem.getDisplay_alias_name() == 1) {
                        FullItemDesc = aTransItem.getAlias_name();
                    } else {
                        FullItemDesc = aTransItem.getDescription();
                    }
                } else if (aTransItem.getOverride_gen_name() == 2) {
                    FullItemDesc = "";
                }
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aTransItem.getDescSpecific().length() > 0) {
                if (FullItemDesc.length() > 0) {
                    FullItemDesc = FullItemDesc + NewLineStr + aTransItem.getDescSpecific();
                } else {
                    FullItemDesc = aTransItem.getDescSpecific();
                }
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aTransItem.getCodeSpecific().length() > 0) {
                if (FullItemDesc.length() > 0) {
                    FullItemDesc = FullItemDesc + NewLineStr + aTransItem.getCodeSpecific();
                } else {
                    FullItemDesc = aTransItem.getCodeSpecific();
                }
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aTransItem.getDescMore().length() > 0) {
                if (FullItemDesc.length() > 0) {
                    FullItemDesc = FullItemDesc + NewLineStr + aTransItem.getDescMore();
                } else {
                    FullItemDesc = aTransItem.getDescMore();
                }
            }
        } catch (NullPointerException npe) {
        }
        return FullItemDesc;
    }

    public String showItemDescriptionCECexcSpecific(TransItem aTransItem) {
        int NewLineEachName = 0;//NEW_LINE_EACH_NAME
        int LinesBtnNames = 1;//LINES_BTN_NAMES
        NewLineEachName = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("GENERAL_NAME", "NEW_LINE_EACH_NAME").getParameter_value());
        LinesBtnNames = Integer.parseInt(new Parameter_listBean().getParameter_listByContextNameMemory("GENERAL_NAME", "LINES_BTN_NAMES").getParameter_value());
        String NewLineStr = " ";
        if (NewLineEachName == 1 && LinesBtnNames > 0) {
            for (int i = 1; i <= LinesBtnNames; i++) {
                NewLineStr = NewLineStr + "<br /> ";
            }
        }
        String FullItemDesc = "";
        try {
            if (aTransItem.getDescSpecific().length() <= 0 && aTransItem.getCodeSpecific().length() <= 0) { //indicator for non-general item
                if (aTransItem.getAlias_name().length() > 0 && aTransItem.getDisplay_alias_name() == 1) {
                    FullItemDesc = aTransItem.getAlias_name();
                } else {
                    FullItemDesc = aTransItem.getDescription();
                }
            } else {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "SHOW_GEN_ITEM_NAME").getParameter_value().equals("1")) {
                    if (aTransItem.getAlias_name().length() > 0 && aTransItem.getDisplay_alias_name() == 1) {
                        FullItemDesc = aTransItem.getAlias_name();
                    } else {
                        FullItemDesc = aTransItem.getDescription();
                    }
                }
            }
        } catch (NullPointerException npe) {
        }
        /*
         try {
         if (aTransItem.getDescSpecific().length() > 0) {
         if (FullItemDesc.length() > 0) {
         FullItemDesc = FullItemDesc + NewLineStr + aTransItem.getDescSpecific();
         } else {
         FullItemDesc = aTransItem.getDescSpecific();
         }
         }
         } catch (NullPointerException npe) {
         }
         try {
         if (aTransItem.getCodeSpecific().length() > 0) {
         if (FullItemDesc.length() > 0) {
         FullItemDesc = FullItemDesc + NewLineStr + aTransItem.getCodeSpecific();
         } else {
         FullItemDesc = aTransItem.getCodeSpecific();
         }
         }
         } catch (NullPointerException npe) {
         }
         try {
         if (aTransItem.getDescMore().length() > 0) {
         if (FullItemDesc.length() > 0) {
         FullItemDesc = FullItemDesc + NewLineStr + aTransItem.getDescMore();
         } else {
         FullItemDesc = aTransItem.getDescMore();
         }
         }
         } catch (NullPointerException npe) {
         }
         */
        return FullItemDesc;
    }

    public String showTransItemsSummary(long aTransId) {
        String TransItemsSummary = "";
        List<TransItem> tis = this.getTransItemsByTransactionId(aTransId);
        TransItem ti = null;
        for (int i = 0; i < tis.size(); i++) {
            ti = tis.get(i);
            this.updateLookUpsUI(ti);
            if (TransItemsSummary.length() > 0) {
                TransItemsSummary = TransItemsSummary + ", " + this.showItemDescriptionCEC(ti);
            } else {
                TransItemsSummary = this.showItemDescriptionCEC(ti);
            }
        }
        return TransItemsSummary;
    }

    public String showItemDescriptionCEC_old(TransItem aTransItem) {
        //String aItemDesc, String aCode, String aName, String aDescMore
        String FullItemDesc = "";
        try {
            if (aTransItem.getDescSpecific().length() <= 0 && aTransItem.getCodeSpecific().length() <= 0) { //indicator for non-general item
                if (aTransItem.getAlias_name().length() > 0 && aTransItem.getDisplay_alias_name() == 1) {
                    FullItemDesc = aTransItem.getAlias_name();
                } else {
                    FullItemDesc = aTransItem.getDescription();
                }
            } else {
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "SHOW_GEN_ITEM_NAME").getParameter_value().equals("1")) {
                    if (aTransItem.getAlias_name().length() > 0 && aTransItem.getDisplay_alias_name() == 1) {
                        FullItemDesc = aTransItem.getAlias_name();
                    } else {
                        FullItemDesc = aTransItem.getDescription();
                    }
                }
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aTransItem.getDescSpecific().length() > 0) {
                FullItemDesc = FullItemDesc + " " + aTransItem.getDescSpecific();
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aTransItem.getCodeSpecific().length() > 0) {
                FullItemDesc = FullItemDesc + " " + aTransItem.getCodeSpecific();
            }
        } catch (NullPointerException npe) {
        }
        try {
            if (aTransItem.getDescMore().length() > 0) {
                FullItemDesc = FullItemDesc + " " + aTransItem.getDescMore();
            }
        } catch (NullPointerException npe) {
        }
        return FullItemDesc;
    }

    public void reverseItems(List<TransItem> aTransItems) {
        Collections.reverse(aTransItems);
    }

    public double findItemLatestUnitCostPrice(int aStoreId, long aItemId, int aTransTypeId, int aTransReasId) {//used when not available in Stock
        double unitcostprice = 0;
        long LatestTransItemId = 0;
        try {
            LatestTransItemId = this.getItemUnitCostPriceLatestTransItemId2(aTransTypeId, aTransReasId, aStoreId, aItemId, "", "", "");
        } catch (NullPointerException npe) {
            LatestTransItemId = 0;
        }
        if (LatestTransItemId > 0) {
            try {
                unitcostprice = new TransItemBean().getTransItem(LatestTransItemId).getUnitCostPrice();
            } catch (NullPointerException npe) {
            }
        } else {//use mapping logic
            ItemMap MappedItem = new ItemMapBean().getItemMapBySmallItemId(aItemId);
            long LatestTransItemIdBig = 0;
            try {
                LatestTransItemIdBig = this.getItemUnitCostPriceLatestTransItemId2(aTransTypeId, aTransReasId, aStoreId, MappedItem.getBigItemId(), "", "", "");
            } catch (NullPointerException npe) {
                LatestTransItemIdBig = 0;
            }
            if (LatestTransItemIdBig > 0) {
                try {
                    if (MappedItem.getFractionQty() > 0) {
                        unitcostprice = new TransItemBean().getTransItem(LatestTransItemIdBig).getUnitCostPrice() / MappedItem.getFractionQty();
                    }
                } catch (NullPointerException npe) {
                }
            } else {
                unitcostprice = 0;
            }
        }

        return unitcostprice;
    }

    public int overrideItemsByTransItemsId(int aTransTypeId, int aTransReasonId, long aNewTransId, String aAffectedTransItemssIDs) {
        int passed = 0;
        //String sql = "UPDATE transaction_item SET transaction_id=" + aNewTransId + " WHERE transaction_type_id=" + aTransTypeId + " AND transaction_reason_id=" + aTransReasonId + " AND transaction_id IN(" + aAffectedTranssIDs + ")";
        String sql = "UPDATE transaction_item SET transaction_id=" + aNewTransId + " WHERE transaction_item_id IN(" + aAffectedTransItemssIDs + ")";
        //System.out.println("SQL:" + sql);
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate();
            passed = 1;
        } catch (Exception e) {
            passed = 0;
            System.err.println("overrideItemsByTransItemsId:" + e.getMessage());
        }
        return passed;
    }

    public String getTransItemssIDsByTransIDs(String aTranssIDs) {
        String sql = "SELECT * FROM transaction_item WHERE transaction_id IN(" + aTranssIDs + ")";
        ResultSet rs;
        String TransItemsIDsExcFirst = "";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                if (TransItemsIDsExcFirst.length() == 0) {
                    TransItemsIDsExcFirst = "" + rs.getLong("transaction_item_id");
                } else {
                    TransItemsIDsExcFirst = TransItemsIDsExcFirst + "," + rs.getLong("transaction_item_id");
                }
            }
        } catch (Exception e) {
            System.err.println("getSelectedTranssIDsExcFirst:" + e.getMessage());
        }
        return TransItemsIDsExcFirst;
    }

    public String getTransItemsString(long aTransId, int aLimitCharacters) {
        String TransItemsString = "";
        String sql = "SELECT * FROM transaction_item WHERE transaction_id=" + aTransId;
        ResultSet rs = null;
        if (aTransId > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                Item item;
                UtilityBean ub = new UtilityBean();
                String itemdesc = "";
                while (rs.next()) {
                    item = new ItemBean().getItem(rs.getLong("item_id"));
                    itemdesc = item.getDescription() + "(" + ub.formatDoubleToString(rs.getDouble("item_qty")) + ")";
                    if (TransItemsString.length() > 0) {
                        TransItemsString = TransItemsString + ", " + itemdesc;
                    } else {
                        TransItemsString = itemdesc;
                    }
                    if (aLimitCharacters > 0 && TransItemsString.length() >= aLimitCharacters) {
                        TransItemsString = TransItemsString + " ...";
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("getTransItemsString:" + e.getMessage());
            }
        }
        return TransItemsString;
    }

    /**
     * @return the SelectedAccCoa
     */
    public AccCoa getSelectedAccCoa() {
        return SelectedAccCoa;
    }

    /**
     * @param SelectedAccCoa the SelectedAccCoa to set
     */
    public void setSelectedAccCoa(AccCoa SelectedAccCoa) {
        this.SelectedAccCoa = SelectedAccCoa;
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
     * @return the ActiveTransItemsChild
     */
    public List<TransItem> getActiveTransItemsChild() {
        return ActiveTransItemsChild;
    }

    /**
     * @param ActiveTransItemsChild the ActiveTransItemsChild to set
     */
    public void setActiveTransItemsChild(List<TransItem> ActiveTransItemsChild) {
        this.ActiveTransItemsChild = ActiveTransItemsChild;
    }

    /**
     * @return the TransItems
     */
    public List<TransItem> getTransItems() {
        return TransItems;
    }

    /**
     * @param TransItems the TransItems to set
     */
    public void setTransItems(List<TransItem> TransItems) {
        this.TransItems = TransItems;
    }

    /**
     * @return the CategoryList
     */
    public List<Category> getCategoryList() {
        return CategoryList;
    }

    /**
     * @param CategoryList the CategoryList to set
     */
    public void setCategoryList(List<Category> CategoryList) {
        this.CategoryList = CategoryList;
    }

    /**
     * @return the ItemList
     */
    public List<Item> getItemList() {
        return ItemList;
    }

    /**
     * @param ItemList the ItemList to set
     */
    public void setItemList(List<Item> ItemList) {
        this.ItemList = ItemList;
    }

    /**
     * @return the SubCategoryList
     */
    public List<SubCategory> getSubCategoryList() {
        return SubCategoryList;
    }

    /**
     * @param SubCategoryList the SubCategoryList to set
     */
    public void setSubCategoryList(List<SubCategory> SubCategoryList) {
        this.SubCategoryList = SubCategoryList;
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
}
