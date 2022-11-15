/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static beans.ItemBean.LOGGER;
import static beans.TransItemBean.LOGGER;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import connections.DBConnection;
import entities.AccCoa;
import entities.AccCurrency;
import entities.CompanySetting;
import entities.DiscountPackageItem;
import entities.Item;
import entities.Stock;
import entities.Store;
import entities.Trans;
import entities.TransItem;
import entities.TransactionPackage;
import entities.TransactionPackageItem;
import entities.TransactionPackageItemUnit;
import entities.TransactionReason;
import entities.TransactionType;
import entities.Transactor;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/**
 *
 * @author HP
 */
@ManagedBean
@SessionScoped
public class TransactionPackageItemBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransactionPackageItemBean.class.getName());
    private List<Trans> TransListHist = new ArrayList<>();
    private List<TransactionPackageItem> TransPackageItemList = new ArrayList<>();

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setTransactionPackageItemFromResultset(TransactionPackageItem transPackageItem, ResultSet aResultSet) {
        try {
            try {
                transPackageItem.setTransactionPackageItemId(aResultSet.getLong("transaction_package_item_id"));
            } catch (Exception npe) {
                transPackageItem.setTransactionPackageItemId(0);
            }
            try {
                transPackageItem.setTransactionPackageId(aResultSet.getLong("transaction_package_id"));
            } catch (Exception npe) {
                transPackageItem.setTransactionPackageId(0);
            }
            try {
                transPackageItem.setAmount(aResultSet.getDouble("amount"));
            } catch (Exception npe) {
                transPackageItem.setAmount(0);
            }
            try {
                transPackageItem.setItemQty(aResultSet.getDouble("item_qty"));
            } catch (Exception npe) {
                transPackageItem.setItemQty(0);
            }
            try {
                transPackageItem.setUnitPrice(aResultSet.getInt("unit_price"));
            } catch (Exception npe) {
                transPackageItem.setUnitPrice(0);
            }
            try {
                transPackageItem.setExciseTax(aResultSet.getDouble("excise_tax"));
            } catch (Exception npe) {
                transPackageItem.setExciseTax(0);
            }
            try {
                transPackageItem.setVatPerc(aResultSet.getInt("vat_perc"));
            } catch (Exception npe) {
                transPackageItem.setVatPerc(0);
            }
            try {
                transPackageItem.setItemDescription(aResultSet.getString("item_description"));
            } catch (Exception e) {
                transPackageItem.setItemDescription("");
            }
            try {
                transPackageItem.setTransactionPackageId(aResultSet.getLong("transaction_package_id"));
            } catch (Exception e) {
                transPackageItem.setTransactionPackageId(0);
            }
            try {
                transPackageItem.setTransactionPackageItemId(aResultSet.getLong("transaction_package_item_id"));
            } catch (Exception e) {
                transPackageItem.setTransactionPackageItemId(0);
            }
            try {
                transPackageItem.setUnitVat(aResultSet.getDouble("unit_vat"));
            } catch (Exception e) {
                transPackageItem.setUnitVat(0);
            }
            try {
                transPackageItem.setBatchNo(aResultSet.getString("batchno"));
            } catch (Exception e) {
                transPackageItem.setBatchNo("");
            }
            try {
                transPackageItem.setUnitSymbol(aResultSet.getString("unit_symbol"));
            } catch (Exception e) {
                transPackageItem.setUnitSymbol("");
            }
            try {
                transPackageItem.setItemId(aResultSet.getLong("item_id"));
            } catch (Exception e) {
                transPackageItem.setItemId(0);
            }
            try {
                transPackageItem.setUnitTradeDiscount(aResultSet.getDouble("unit_trade_discount"));
            } catch (Exception e) {
                transPackageItem.setUnitTradeDiscount(0);
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<TransactionPackageItem> getTransactionPackageItemOutput(long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_package_item_by_transaction_id(?)}";
        ResultSet rs = null;
        List<TransactionPackageItem> tis = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                i = i + 1;
                TransactionPackageItem tpi = new TransactionPackageItem();
                this.setTransactionPackageItemFromResultset(tpi, rs);
                tis.add(tpi);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return tis;
    }

    public void saveTransactionPackageItem(int aStoreId, int aTransTypeId, int aTransReasonId, Trans trans, TransactionPackageItem transPackageItem) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }

        String sql = null;
        String msg = "";

        TransactionTypeBean TransTypeBean = new TransactionTypeBean();
        StockBean StkBean = new StockBean();

        if (1 == 2) {
        } else {
            if (transPackageItem.getTransactionPackageItemId() == 0) {
                sql = "{call sp_insert_transaction_package_item(?,?,?,?,?,"
                        + "?,?,?,?,?,"
                        + "?,?,?,?,?,"
                        + "?,?,?,?,?,"
                        + "?,?,?)}";
            } else if (transPackageItem.getTransactionPackageItemId() > 0) {
                sql = "{call sp_update_transaction_item(?,?,?,?,?,"
                        + "?,?,?,?,?,"
                        + "?,?,?,?,?,"
                        + "?,?,?,?,?,"
                        + "?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {

                TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
                TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
                Store store = new StoreBean().getStore(aStoreId);
                //Store store2 = new StoreBean().getStore(aTrans.getStore2Id());

                if (transPackageItem.getTransactionPackageItemId() == 0) {
                    //clean batch
                    if (transPackageItem.getBatchNo() == null) {
                        transPackageItem.setBatchNo("");
                    }
                    cs.setLong("in_transaction_package_id", transPackageItem.getTransactionPackageId());
                    cs.setLong("in_item_id", transPackageItem.getItemId());
                    cs.setString("in_batchno", transPackageItem.getBatchNo());
                    cs.setInt("in_unit_id", transPackageItem.getUnitId());
                    cs.setDouble("in_base_unit_qty", transPackageItem.getBaseUnitQty());
                    cs.setDouble("in_item_qty", transPackageItem.getItemQty());
                    cs.setDouble("in_unit_price", transPackageItem.getUnitPrice());
                    cs.setDouble("in_unit_trade_discount", transPackageItem.getUnitTradeDiscount());
                    cs.setDouble("in_unit_vat", transPackageItem.getUnitVat());
                    cs.registerOutParameter("out_transaction_package_item_id", VARCHAR);
                    cs.setString("in_excise_rate_name", transPackageItem.getExciseRateName());
                    cs.setDouble("in_excise_rate_perc", transPackageItem.getExciseRatePerc());
                    cs.setDouble("in_excise_rate_value", transPackageItem.getExciseRatePerc());
                    cs.setDouble("in_excise_tax", transPackageItem.getExciseTax());
                    cs.setDouble("in_excise_currency_code_tax", transPackageItem.getExciseCurrencyCodeTax());
                    cs.setDouble("in_excise_unit_code_tax", transPackageItem.getExciseUnitCodeTax());
                    cs.setDouble("in_amount", transPackageItem.getAmount());
                    cs.setString("in_vat_rated", transPackageItem.getVatRated());
                    cs.setDouble("in_vat_perc", transPackageItem.getVatPerc());
                    try {
                        cs.setString("in_code_specific", transPackageItem.getCodeSpecific());
                    } catch (NullPointerException npe) {
                        cs.setString("in_code_specific", "");
                    }
                    try {
                        cs.setString("in_desc_specific", transPackageItem.getDescSpecific());
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_specific", "");
                    }
                    try {
                        cs.setString("in_desc_more", transPackageItem.getDescMore());
                    } catch (NullPointerException npe) {
                        cs.setString("in_desc_more", "");
                    }
                    try {
                        cs.setString("in_narration", transPackageItem.getNarration());
                    } catch (NullPointerException npe) {
                        cs.setString("in_narration", "");
                    }

                    cs.registerOutParameter("out_transaction_package_item_id", VARCHAR);
                    //save
                    cs.executeUpdate();
                    long InsertedId1 = cs.getLong("out_transaction_package_item_id");
                    long InsertedId2 = 0;
                    try {
                        if (InsertedId1 > 0) {
                            TransactionPackageItemUnit tiu = new TransactionPackageItemUnit();
                            tiu.setTransactionItemId(InsertedId1);
                            tiu.setUnitId(transPackageItem.getUnitId());
                            tiu.setBaseUnitQty(transPackageItem.getBaseUnitQty());
                            this.insertTransaction_item_unit(tiu);//transaction package item unit
                        }
                    } catch (Exception e) {
                        LOGGER.log(Level.ERROR, e);
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                //this.setActionMessage(ub.translateWordsInText(BaseName, "Transaction Item Not Saved"));
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, "Transaction Item Not Saved")));
            }
        }
    }

    public int saveTransPackageItemsCEC(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, List<TransactionPackageItem> aTransPackageItemList, long transactionPackageId) {
        int insertedItems = 0;
        try {
            List<TransactionPackageItem> ati = aTransPackageItemList;
            int ListItemIndex = 0;
            int ListItemNo = ati.size();
            int inserted = 0;
            while (ListItemIndex < ListItemNo) {
                ati.get(ListItemIndex).setTransactionPackageId(transactionPackageId);

                this.saveTransactionPackageItem(aStoreId, aTransTypeId, aTransReasonId, aTrans, ati.get(ListItemIndex));
                //insertedItems = insertedItems + inserted;
                ListItemIndex = ListItemIndex + 1;
                insertedItems = ListItemIndex;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return insertedItems;
    }

    public long insertTransaction_item_unit(TransactionPackageItemUnit aTransactionPackageItemUnit) {
        long newId = 0;
        String sql = "INSERT INTO transaction_package_item_unit"
                + "(transaction_package_item_unit_id ,unit_id,base_unit_qty)"
                + " VALUES"
                + "(?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setLong(1, aTransactionPackageItemUnit.getTransactionItemId());
            ps.setLong(2, aTransactionPackageItemUnit.getUnitId());
            ps.setDouble(3, aTransactionPackageItemUnit.getBaseUnitQty());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return newId;
    }

    public int updateTransaction_item_unit(long transaction_package_item_unit_id, double aBase_unit_qty) {
        int success = 0;
        String sql = "UPDATE transaction_package_item_unit SET base_unit_qty=? WHERE transaction_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setLong(1, transaction_package_item_unit_id);
            ps.setDouble(2, aBase_unit_qty);
            ps.executeUpdate();
            success = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return success;
    }

    public void addTransPackageItemToList(int aTransTypeId, int aTransReasonId, TransactionPackage aTransPackage, List<TransactionPackageItem> aTransactionPackageItemList, TransItem NewTransItem, Item aSelectedItem) {
        UtilityBean ub = new UtilityBean();
        Gson gson = new Gson();
        String BaseName = "language_en";
        BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        double BaseQty = new ItemBean().getBaseUnitQty(NewTransItem.getItemId(), NewTransItem.getUnit_id(), NewTransItem.getItemQty());
        if (BaseQty > 0) {
            NewTransItem.setBase_unit_qty(BaseQty);
        } else {
            NewTransItem.setUnit_id(aSelectedItem.getUnitId());
            NewTransItem.setBase_unit_qty(NewTransItem.getItemQty());
        }
        TransactionPackage ti = new TransactionPackage();
        TransactionPackageItem tPackageItem = gson.fromJson(gson.toJson(ti), TransactionPackageItem.class);

        //bms veraibales
        try {
            tPackageItem.setCodeSpecific(NewTransItem.getCodeSpecific());
        } catch (NullPointerException npe) {
            tPackageItem.setCodeSpecific("");
        }
        try {
            tPackageItem.setDescSpecific(NewTransItem.getDescSpecific());
        } catch (NullPointerException npe) {
            tPackageItem.setDescSpecific("");
        }
        try {
            tPackageItem.setDescMore(NewTransItem.getDescMore());
        } catch (NullPointerException npe) {
            tPackageItem.setDescMore("");
        }
        aTransactionPackageItemList.add(tPackageItem);

        //update totals
        //new TransBean().setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, aActiveTransItems);
    }

    public int itemExists(List<TransactionPackageItem> aTransactionPackageItemList, Long aItemId, String aBatchNo, String aCodeSpec, String aDescSpec, int aUnitId) {
        List<TransactionPackageItem> ati = aTransactionPackageItemList;
        int ItemFoundAtIndex = -1;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getItemId() == aItemId && aBatchNo.equals(ati.get(ListItemIndex).getBatchNo()) && aCodeSpec.equals(ati.get(ListItemIndex).getCodeSpecific()) && aDescSpec.equals(ati.get(ListItemIndex).getDescSpecific()) && ati.get(ListItemIndex).getUnitId() == aUnitId) {
                ItemFoundAtIndex = ListItemIndex;
                break;
            } else {
                ItemFoundAtIndex = -1;
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return ItemFoundAtIndex;
    }

    public void addTransactionPackageItem(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, TransactionPackage transactionPackage, TransactionPackageItem NewTransPackageItem, StatusBean aStatusBean, List<TransactionPackageItem> aTransactionPackageItemList, Item aSelectedItem) {
        UtilityBean ub = new UtilityBean();
        Gson gson = new Gson();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String status = "";
        aStatusBean.setItemAddedStatus("");
        aStatusBean.setItemNotAddedStatus("");
        aStatusBean.setShowItemAddedStatus(0);
        aStatusBean.setShowItemNotAddedStatus(0);
        try {
            //check
            if (NewTransPackageItem.getCodeSpecific().length() > 250) {
                status = "Code cannot be more than 250 Characters";
            } else if (NewTransPackageItem.getDescSpecific().length() > 250) {
                status = "Name cannot be more than 250 Characters";
            } else if (NewTransPackageItem.getDescMore().length() > 250) {
                status = "More Description cannot be more than 250 Characters";
            } else if (NewTransPackageItem.getUnitPrice() < 0 || NewTransPackageItem.getAmount() < 0 || NewTransPackageItem.getUnitTradeDiscount() < 0) {
                status = "Prices cannot be Negative";
            } else {
                double BaseQty = new ItemBean().getBaseUnitQty(NewTransPackageItem.getItemId(), NewTransPackageItem.getUnitId(), NewTransPackageItem.getItemQty());
                if (BaseQty > 0) {
                    NewTransPackageItem.setBaseUnitQty(BaseQty);
                } else {

                }
                // TransactionPackageItem transPackageItem = 
                // NewTransPackageItem.setUnitId(aSelectedItem.getUnitId());
                NewTransPackageItem.setItemId(aSelectedItem.getItemId());
                NewTransPackageItem.setItemDescription(aSelectedItem.getDescription());

                try {
                    if (null == aSelectedItem.getItemCode()) {
                        NewTransPackageItem.setItemCode("");
                    } else {
                        NewTransPackageItem.setItemCode(aSelectedItem.getItemCode());
                    }
                } catch (Exception e) {
                    NewTransPackageItem.setItemCode("");
                }
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {
                    if (null == new Item_tax_mapBean().getItem_tax_mapSynced(NewTransPackageItem.getItemId())) {
                        NewTransPackageItem.setIsTaxSynced(0);
                    } else {
                        NewTransPackageItem.setIsTaxSynced(1);
                    }
                } else {
                    NewTransPackageItem.setIsTaxSynced(1);
                }

                //aTransactionPackageItemList.add(transIt);
                // this.addTransItemCEC(aStoreId, aTransTypeId, aTransReasonId, aTrans, aTransactionPackageItemList, NewTransPackageItem, aSelectedItem);
                int ItemFoundAtIndex = 1 - 2;//==-1
                ItemFoundAtIndex = itemExists(aTransactionPackageItemList, NewTransPackageItem.getItemId(), NewTransPackageItem.getBatchNo(), NewTransPackageItem.getCodeSpecific(), NewTransPackageItem.getDescSpecific(), NewTransPackageItem.getUnitId());
                if (ItemFoundAtIndex == -1) {
                    JsonObject trans = (JsonObject) new JsonParser().parse(gson.toJson(NewTransPackageItem));
                    TransactionPackageItem transIt = new TransactionPackageItem();
                    transIt = gson.fromJson(trans, TransactionPackageItem.class);
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        this.updateLookUpsUI(transIt);
                        aTransactionPackageItemList.add(0, transIt);
                    } else {
                        this.updateLookUpsUI(transIt);
                        aTransactionPackageItemList.add(transIt);
                    }
                } else {
                    NewTransPackageItem.setItemQty(NewTransPackageItem.getItemQty() + aTransactionPackageItemList.get(ItemFoundAtIndex).getItemQty());
                    NewTransPackageItem.setAmount(NewTransPackageItem.getUnitPrice() * NewTransPackageItem.getItemQty());
                    NewTransPackageItem.setAmount((NewTransPackageItem.getUnitPrice() - NewTransPackageItem.getUnitTradeDiscount()) * NewTransPackageItem.getItemQty());
                    JsonObject trans = (JsonObject) new JsonParser().parse(gson.toJson(NewTransPackageItem));
                    TransactionPackageItem transIt = new TransactionPackageItem();
                    transIt = gson.fromJson(trans, TransactionPackageItem.class);
                    this.updateLookUpsUI(transIt);
                    aTransactionPackageItemList.add(ItemFoundAtIndex, transIt);
                    aTransactionPackageItemList.remove(ItemFoundAtIndex + 1);
                }
                this.clearAll(aTrans, aTransactionPackageItemList, NewTransPackageItem, null, aSelectedItem, null, 1, null);
                //this.TransPackageItemList = aTransactionPackageItemList;
                //}
                transactionPackage.setSubTotal(new TransactionPackageBean().getSubTotal(aTransactionPackageItemList));
                transactionPackage.setGrandTotal(new TransactionPackageBean().getGrandTotal(aTransactionPackageItemList));
                this.setTransTotalsAndUpdateCEC(aTransTypeId, aTransReasonId, aTrans, transactionPackage, aTransactionPackageItemList);
                this.clearTransactionPackageItem(NewTransPackageItem);
                new ItemBean().clearSelectedItem();

            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void editTransItemUponUnitChange(int aTransTypeId, int aTransReasonId, TransactionPackageItem ti) {
        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        //first update unit prices
        double IncludedVat;
        double ExcludedVat;
        double VatPercent = ti.getVatPerc();
        //this is a vatable item

        //calculate prices
        if (ti.getItemQty() < 0) {
            ti.setItemQty(0);
        }
        ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
    }

    public void updateLookUpsUI(TransactionPackageItem aTransItem) {
        try {
            Item item = null;
            if (null == aTransItem) {
                //do nothing
            } else {
                item = new ItemBean().getItem(aTransItem.getItemId());
                try {
                    aTransItem.setItemDescription(item.getDescription());
                } catch (NullPointerException npe) {
                    aTransItem.setItemDescription("");
                }

                try {
                    if (aTransItem.getUnitId() > 0) {
                        aTransItem.setUnitSymbol(new UnitBean().getUnit(aTransItem.getUnitId()).getUnitSymbol());
                    } else {
                        aTransItem.setUnitSymbol(new UnitBean().getUnit(item.getUnitId()).getUnitSymbol());
                    }
                } catch (NullPointerException npe) {
                    aTransItem.setUnitSymbol("");
                }

                try {
                    if (null == item.getItemCode()) {
                        aTransItem.setItemCode("");
                    } else {
                        aTransItem.setItemCode(item.getItemCode());
                    }
                } catch (Exception e) {
                    aTransItem.setItemCode("");
                }
                if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {
                    if (null == new Item_tax_mapBean().getItem_tax_mapSynced(aTransItem.getItemId())) {
                        aTransItem.setIsTaxSynced(0);
                    } else {
                        aTransItem.setIsTaxSynced(1);
                    }
                } else {
                    aTransItem.setIsTaxSynced(1);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public double getListTotalItemBatchQty(List<TransactionPackageItem> aActiveTransItems, Long aItemI, String aBatchN, String aCodeSpec, String aDescSpec) {
        List<TransactionPackageItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TQty = 0;
        while (ListItemIndex < ListItemNo) {
            String CodeSp = "";
            String DescSp = "";
            String BatcNo = "";
            try {
                BatcNo = ati.get(ListItemIndex).getBatchNo();
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
                //TQty = TQty + ati.get(ListItemIndex).getItemQty();
                TQty = TQty + ati.get(ListItemIndex).getBaseUnitQty();
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return TQty;
    }

    public String addTransItemCEC(int aStoreId, int aTransTypeId, int aTransReasonId, Trans aTrans, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem NewTransPackageItem, Item aSelectedItem) {
        String status = "";
        double IncludedVat;
        double ExcludedVat;
        double VatPercent;
        double xrate = 1;
        double XrateMultiply = 1;
        Gson gson = new Gson();

        TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
        TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
        Store store = new StoreBean().getStore(aStoreId);

        try {
            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            try {
                xrate = new AccXrateBean().getXrate(aSelectedItem.getCurrencyCode(), aTrans.getCurrencyCode());
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
                if (NewTransPackageItem.getStockId() > 0) {
                    //this.updateTransItemBatchSpecific(NewTransPackageItem, NewTransPackageItem.getStockId());
                }
            } catch (Exception e) {
            }

            if (null == NewTransPackageItem.getBatchNo()) {
                NewTransPackageItem.setBatchNo("");
            }
            if (null == NewTransPackageItem.getCodeSpecific()) {
                NewTransPackageItem.setCodeSpecific("");
            }
            if (null == NewTransPackageItem.getDescSpecific()) {
                NewTransPackageItem.setDescSpecific("");
            }
            if (null == NewTransPackageItem.getNarration()) {
                NewTransPackageItem.setNarration("");
            }

            //check if itme+batchno already exists
            int ItemFoundAtIndex = 1 - 2;//==-1
            ItemFoundAtIndex = itemExists(aActiveTransItems, NewTransPackageItem.getItemId(), NewTransPackageItem.getBatchNo(), NewTransPackageItem.getCodeSpecific(), NewTransPackageItem.getDescSpecific(), NewTransPackageItem.getUnitId());

            if (NewTransPackageItem.getAmount() < 0) {
                status = "Discount cannot exceed Unit Price";
            } else {
                if (ItemFoundAtIndex == -1) {
                    //round off amounts basing on currency rules
                    this.roundTransItemsAmount(aTrans, NewTransPackageItem);
                    //add
                    if (new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "LIST_ITEMS_APPEND").getParameter_value().equals("0")) {
                        aActiveTransItems.add(0, NewTransPackageItem);
                    } else {
                        aActiveTransItems.add(NewTransPackageItem);
                    }

                    NewTransPackageItem.setItemQty(NewTransPackageItem.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                    NewTransPackageItem.setAmount(NewTransPackageItem.getUnitPrice() * NewTransPackageItem.getItemQty());
                }
                //round off amounts basing on currency rules
                this.roundTransItemsAmount(aTrans, NewTransPackageItem);
                //add
                aActiveTransItems.add(ItemFoundAtIndex, NewTransPackageItem);
                aActiveTransItems.remove(ItemFoundAtIndex + 1);
            }

            //get item's stock details
            //get and check number of batches
            StockBean sb = new StockBean();
            Stock st = new Stock();
            st = sb.getStock(store.getStoreId(), NewTransPackageItem.getItemId(), NewTransPackageItem.getBatchNo(), NewTransPackageItem.getCodeSpecific(), NewTransPackageItem.getDescSpecific());

            //unpacking - AutoUnPackModule
            //process items that might need to be unpacked
            int StockFail1 = 0, StockFail2 = 0, StockFail3 = 0;

            //for dispose,consume; apply unit cost price of the stock/batch
            if (aSelectedItem.getIsTrack() == 1 && null != st && ("DISPOSE STOCK".equals(transtype.getTransactionTypeName()) || "STOCK CONSUMPTION".equals(transtype.getTransactionTypeName()))) {
                NewTransPackageItem.setUnitPrice(NewTransPackageItem.getUnitPrice());
                NewTransPackageItem.setAmount(NewTransPackageItem.getItemQty() * NewTransPackageItem.getUnitPrice());
            }
            if (NewTransPackageItem.getItemQty() <= 0) {
                status = "Enter Item Quantity";
                StockFail3 = 1;
            }
            if (StockFail1 == 0 && StockFail2 == 0 && StockFail3 == 0) {

                TransactionPackageItem ti = new TransactionPackageItem();
                ti = gson.fromJson(gson.toJson(NewTransPackageItem), TransactionPackageItem.class);

                this.roundTransItemsAmount(aTrans, ti);
                ti.setItemQty(ti.getItemQty() + aActiveTransItems.get(ItemFoundAtIndex).getItemQty());
                ti.setAmount(ti.getUnitPrice() * ti.getItemQty());
                aActiveTransItems.add(ItemFoundAtIndex, ti);
                aActiveTransItems.remove(ItemFoundAtIndex + 1);
            }
        } catch (Exception e) {
            status = "Item Not Saved due to Error";
            LOGGER.log(Level.ERROR, e);
        }
        return status;
    }

    public void updateBaseUnityQty(TransactionPackageItem aTransPackageItem) {
        try {
            double BaseQty = new ItemBean().getBaseUnitQty(aTransPackageItem.getItemId(), aTransPackageItem.getUnitId(), aTransPackageItem.getItemQty());
            if (BaseQty >= 0) {
                aTransPackageItem.setBaseUnitQty(BaseQty);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
    
        public void addQtyTransItemCEC(TransactionPackageItem ti) {
        try {
            ti.setItemQty(ti.getItemQty() + 1);
            if (ti.getItemQty() < 0) {
                ti.setItemQty(0);
            }
            //update amount
            ti.setAmount(ti.getItemQty() * ti.getUnitPrice());
        } catch (Exception e) {
        }
    }

    public void subtractQtyTransItemCEC(TransactionPackageItem ti) {
        try {
            ti.setItemQty(ti.getItemQty() - 1);
            if (ti.getItemQty() < 0) {
                ti.setItemQty(0);
            }
            //update amount
            ti.setAmount(ti.getItemQty() * ti.getUnitPrice());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void addQtyTransPackageItemCEC(int aTransTypeId, Trans aTrans, TransactionPackage transactionPackage, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem ti) {
        try {
            ti.setItemQty(ti.getItemQty() + 1);
            if (ti.getItemQty() < 0) {
                ti.setItemQty(0);
            }
            //update totals
            this.editTransPackageItemCEC(aTransTypeId, aTrans, transactionPackage, aActiveTransItems, ti);
        } catch (Exception e) {
        }
    }

    public void subtractQtyTransPackageItemCEC(int aTransTypeId, Trans aTrans, TransactionPackage transactionPackage, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem ti) {
        try {
            ti.setItemQty(ti.getItemQty() - 1);
            if (ti.getItemQty() < 0) {
                ti.setItemQty(0);
            }
            //update totals
            this.editTransPackageItemCEC(aTransTypeId, aTrans, transactionPackage, aActiveTransItems, ti);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void editTransPackageItemCEC(int aTransTypeId, Trans aTrans, TransactionPackage transactionPackage, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem ti) {
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            this.updateBaseUnityQty(ti);
            if (ti.getItemQty() < 0) {
                ti.setItemQty(0);
            }
            if (aTransTypeId == 88) {//PACKAGING
                ti.setAmount((ti.getUnitPrice() - ti.getUnitTradeDiscount()) * ti.getItemQty());
            }
            //round off amounts basing on currency rules
            this.roundTransItemsAmount(aTrans, ti);
            //update totals
            this.setTransTotalsAndUpdateCEC(aTransTypeId, 0, aTrans, transactionPackage, aActiveTransItems);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public double getTotalTradeDiscountCEC(Trans aTrans, List<TransactionPackageItem> aActiveTransItems) {
        List<TransactionPackageItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TotTradeDisc = 0;
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getUnitTradeDiscount() > 0) {
                TotTradeDisc = TotTradeDisc + (ati.get(ListItemIndex).getUnitTradeDiscount() * ati.get(ListItemIndex).getItemQty());
            } else {
                TotTradeDisc = TotTradeDisc + ati.get(ListItemIndex).getItemQty();
            }
            ListItemIndex = ListItemIndex + 1;
        }
        TotTradeDisc = (double) new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), TotTradeDisc, "TOTAL_OTHER");
        return TotTradeDisc;
    }

    public double getTotalVat(List<TransactionPackageItem> aActiveTransItems) {
        List<TransactionPackageItem> ati = aActiveTransItems;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        double TVat = 0;
        while (ListItemIndex < ListItemNo) {
            if (ati.get(ListItemIndex).getUnitVat() > 0) {
                TVat = TVat + (ati.get(ListItemIndex).getUnitVat() * ati.get(ListItemIndex).getItemQty());
            } else {
                TVat = TVat + ati.get(ListItemIndex).getItemQty();
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return TVat;
    }

    public void setTransTotalsAndUpdateCEC(int aTransTypeId, int aTransReasonId, Trans aTrans, TransactionPackage transactionPackage, List<TransactionPackageItem> aTransactionPackageItemList) {
        aTrans.setGrandTotal(new TransactionPackageBean().getSubTotal(aTransactionPackageItemList));

        aTrans.setSubTotal(new TransactionPackageBean().getSubTotal(aTransactionPackageItemList));
        aTrans.setTotalTradeDiscount(this.getTotalTradeDiscountCEC(aTrans, aTransactionPackageItemList));
        aTrans.setTotalVat(this.getTotalVat(aTransactionPackageItemList));

        transactionPackage.setSubTotal((aTrans.getSubTotal() + transactionPackage.getTotalTax()) - (transactionPackage.getTotalTradeDiscount() + transactionPackage.getCashDiscount()));
        transactionPackage.setTotalTradeDiscount(this.getTotalTradeDiscountCEC(aTrans, aTransactionPackageItemList));
        transactionPackage.setTotalTax(this.getTotalVat(aTransactionPackageItemList));
        transactionPackage.setGrandTotal(this.getGrandTotal(transactionPackage));

        //Customer Display
        String PortName = new Parameter_listBean().getParameter_listByContextName("CUSTOMER_DISPLAY", "COM_PORT_NAME").getParameter_value();
        String ClientPcName = new GeneralUserSetting().getClientComputerName();
        String SizeStr = new Parameter_listBean().getParameter_listByContextName("CUSTOMER_DISPLAY", "MAX_CHARACTERS_PER_LINE").getParameter_value();
        int Size = 0;
        if (SizeStr.length() > 0) {
            Size = Integer.parseInt(SizeStr);
        }
        if (PortName.length() > 0 && ClientPcName.length() > 0 && Size > 0 && (aTransTypeId == 2 || aTransTypeId == 11)) {
            UtilityBean ub = new UtilityBean();
            ub.invokeLocalCustomerDisplay(ClientPcName, PortName, Size, ub.formatDoubleToString(aTrans.getGrandTotal()), "");
        }
    }

    public boolean updateTransPackageItems(long aTransactionId, List<TransactionPackageItem> aNewTransPackageItems) {
        try {
            //get trans items that was moved to the history table
            //1. Reverse and update all trans items whoose qty has changed
            int NewListItemIndex = 0;
            int NewListItemNo = aNewTransPackageItems.size();
            TransactionPackageItem nti = null;
            while (NewListItemIndex < NewListItemNo) {
                nti = aNewTransPackageItems.get(NewListItemIndex);//new TransactionPackageItem();
                if (nti.getItemQty() > 0) {
                    this.updateTransPackageItemCEC(nti);
                } else {
                    int delete = this.deleteTransPackageItemsById(nti.getTransactionPackageItemId());
                    if (delete == 1) {
                        deleteTransPackageItemsUnitByTransPackageItemId(nti.getTransactionPackageItemId());
                    }
                }
                NewListItemIndex = NewListItemIndex + 1;
            }
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return false;
        }
    }

    public void updateTransPackageItemCEC(TransactionPackageItem transPackageItem) {
        int success = 0;
        String sql = "{call sp_update_transaction_package_item(?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_transaction_package_item_id", transPackageItem.getTransactionPackageItemId());
            cs.setDouble("in_item_qty", transPackageItem.getItemQty());
            cs.setDouble("in_unit_price", transPackageItem.getUnitPrice());
            cs.setDouble("in_unit_trade_discount", transPackageItem.getUnitTradeDiscount());
            cs.setDouble("in_unit_vat", transPackageItem.getUnitVat());
            cs.setDouble("in_amount", transPackageItem.getAmount());
            cs.executeUpdate();
            success = new TransItemExtBean().updateTransaction_item_unit(transPackageItem.getTransactionPackageItemId(), transPackageItem.getBaseUnitQty());
        } catch (Exception e) {
            success = 0;
            LOGGER.log(Level.ERROR, e);
        }
    }

    public double getGrandTotal(TransactionPackage aTransPackage) {
        double GTotal = 0;
        GTotal = (aTransPackage.getSubTotal() + aTransPackage.getTotalTax()) - (aTransPackage.getTotalTradeDiscount() + aTransPackage.getCashDiscount());
        return GTotal;
    }

    public void roundTransItemsAmount(Trans aTrans, TransactionPackageItem aTransPackageItem) {
        aTransPackageItem.setUnitVat(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransPackageItem.getUnitVat(), "ITEM"));
        aTransPackageItem.setAmount(new AccCurrencyBean().roundAmount(aTrans.getCurrencyCode(), aTransPackageItem.getAmount(), "ITEM"));
    }

    public void removeTransItemCEC(int aTransTypeId, Trans aTrans, TransactionPackage transactionPackage, List<TransactionPackageItem> aActiveTransItems, TransactionPackageItem ti) {
        try {
            aActiveTransItems.remove(ti);
            //update totals
            this.setTransTotalsAndUpdateCEC(aTransTypeId, 0, aTrans, transactionPackage, aActiveTransItems);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearTransactionPackageItem(TransactionPackageItem tri) {
        if (tri != null) {
            tri.setTransactionPackageId(0);
            tri.setItemId(0);
            tri.setBatchNo("");
            tri.setItemQty(0);
            tri.setUnitSymbol("");
            tri.setUnitPrice(0);
            tri.setAmount(0);
            tri.setUnitVat(0);
            tri.setUnitTradeDiscount(0);
            tri.setCodeSpecific("");
            tri.setDescSpecific("");
            tri.setDescMore("");
            tri.setExciseUnitCodeTax(0);
            tri.setExciseCurrencyCodeTax(0);
            tri.setExciseTax(0);
            tri.setExciseRatePerc(0);
            tri.setExciseRateName("");
            tri.setNarration("");
            tri.setUnitId(0);
            tri.setBaseUnitQty(0);
            tri.setItemDescription("");
        }
    }

    public void setStatus(StatusBean aStatusBean, int showNotAdded, int showAdded, String status) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        aStatusBean.setItemAddedStatus(ub.translateWordsInText(BaseName, status));
        aStatusBean.setItemNotAddedStatus("");
        aStatusBean.setShowItemAddedStatus(showAdded);
        aStatusBean.setShowItemNotAddedStatus(showNotAdded);
    }

    public void clearAll(Trans t, List<TransactionPackageItem> aTransactionPackageItemList, TransactionPackageItem ti, TransactionPackage transactionPackage, Item aSelectedItem, Transactor aSelectedTransactor, int ClearNo, AccCoa aSelectedAccCoa) {
//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all  
        TransactionPackageItemBean tib = new TransactionPackageItemBean();
        ItemBean itmB = new ItemBean();
        TransactorBean trB = new TransactorBean();
        AccCoaBean acBean = new AccCoaBean();

        if (ClearNo == 1 || ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            //clear autoCompletetd item
            itmB.clearSelectedItem();
            itmB.clearItem(aSelectedItem);
            //clear the selcted trans item
            tib.clearTransactionPackageItem(ti);
            //clear selected AccCoa
            acBean.clearAccCoa(aSelectedAccCoa);
        }
        if (ClearNo == 2) {//Clear No: 0-do not clear, 1 - clear trans item only, 2 - clear all
            //put code for clearing customer/supplier/transactor
            trB.clearSelectedTransactor();
            trB.clearTransactor(aSelectedTransactor);
            //clear all the item LIST
            //--//tib.getActiveTransItems().clear();
            aTransactionPackageItemList.clear();

            //clear Trans inc. payments
            //this.clearTrans(t);
        }
    }

    public void updateModelTransItemCEC(int aStoreId, int aTransTypeId, int aTransReasonId, String aSaleType, Trans aTrans, TransactionPackageItem aTransPackageItemToUpdate, Item aItem, double aDefaultQty) {
        try {
            TransactionType transtype = new TransactionTypeBean().getTransactionType(aTransTypeId);
            TransactionReason transreason = new TransactionReasonBean().getTransactionReason(aTransReasonId);
            Store store = new StoreBean().getStore(aStoreId);
            DiscountPackageItem dpi = null;
            if (aItem == null) {
                aTransPackageItemToUpdate.setItemId(0);
                aTransPackageItemToUpdate.setUnitPrice(0);
                aTransPackageItemToUpdate.setVatRated("");
                aTransPackageItemToUpdate.setItemQty(0);
                aTransPackageItemToUpdate.setAmount(0);
                aTransPackageItemToUpdate.setUnitTradeDiscount(0);
                new ItemBean().clearItem(aItem);
            } else {
                aTransPackageItemToUpdate.setItemId(aItem.getItemId());
                aTransPackageItemToUpdate.setUnitPrice(aItem.getUnitRetailsalePrice());
                aTransPackageItemToUpdate.setVatRated(aItem.getVatRated());
                aTransPackageItemToUpdate.setItemQty(1);
                aTransPackageItemToUpdate.setUnitId(aItem.getUnitId());
                aTransPackageItemToUpdate.setItemDescription(aItem.getDescription());
                aTransPackageItemToUpdate.setAmount(aItem.getUnitRetailsalePrice());
                //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            }

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public Double getTransPackageItemsTotalQty(List<TransactionPackageItem> aTransactionPackageItemList) {
        Double nQty = 0.0;
        try {
            for (int i = 0; i < aTransactionPackageItemList.size(); i++) {
                nQty = nQty + aTransactionPackageItemList.get(i).getItemQty();
            }
        } catch (Exception e) {
            //
        }
        return nQty;
    }

    public List<TransactionPackageItem> getTransPackageItemsByTransactionPackageId(long aTransactionPackageId) {
        String sql;
        sql = "{call sp_search_transaction_package_item_by_transaction_package_id(?)}";
        ResultSet rs = null;
        this.setTransPackageItemList(new ArrayList<TransactionPackageItem>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionPackageId);
            rs = ps.executeQuery();
            TransactionPackageItem ti = null;
            while (rs.next()) {
                ti = new TransactionPackageItem();
                this.setTransactionPackageItemFromResultset(ti, rs);
                getTransPackageItemList().add(ti);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getTransPackageItemList();
    }

    public void setTransPackageItemsByTransactionId(List<TransactionPackageItem> transactionPackageItem, long aTransactionId) {
        String sql;
        sql = "{call sp_search_transaction_package_item_by_transaction_id(?)}";
        ResultSet rs = null;
        if (transactionPackageItem != null) {
            if (transactionPackageItem.size() > 0) {
                transactionPackageItem.clear();
            }
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            rs = ps.executeQuery();
            TransactionPackageItem tp = null;
            while (rs.next()) {
                tp = new TransactionPackageItem();
                this.setTransactionPackageItemFromResultset(tp, rs);
                transactionPackageItem.add(tp);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String getSpecificSizeQtyFromQtyAndSizeStr(TransactionPackageItem transactionPackageItem) {
        String outpstr = "";
        double outp = 1.0;
        double qty = 0;
        double sze = 1;
        if (null != transactionPackageItem) {
            qty = transactionPackageItem.getItemQty();
            //sze = transactionPackageItem.getgetSpecific_size();
            sze = 1;
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

    public int deleteTransPackageItemsUnitByTransPackageItemId(long aTransPackageItemId) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_package_item_unit WHERE transaction_package_item_unit_id IN("
                + "SELECT ti.transaction_package_item_id FROM transaction_package_item ti WHERE ti.transaction_package_id=?"
                + ")";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransPackageItemId);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            deleted = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
    }

    public int deleteTransPackageItemsCEC(long aTransPackageId) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_package_item WHERE transaction_package_item_id>0 && transaction_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransPackageId);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            deleted = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
    }

    public int deleteTransPackageItemsById(long aTransPackageItemId) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_package_item WHERE transaction_package_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransPackageItemId);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            deleted = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
    }

    public int deleteTransactionPackageByTransId(long transactionId) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_package WHERE transaction_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, transactionId);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            deleted = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
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
     * @return the aTransPackageItemList
     */
    public List<TransactionPackageItem> getTransPackageItemList() {
        if (TransPackageItemList == null) {
            return TransPackageItemList = new ArrayList<>();
        }
        return TransPackageItemList;
    }

    /**
     * @param TransPackageItemList the aTransPackageItemList to set
     */
    public void setTransPackageItemList(List<TransactionPackageItem> TransPackageItemList) {
        this.TransPackageItemList = TransPackageItemList;
    }

    /**
     * @return the TransListHist
     */
    public List<Trans> getTransListHist() {
        return TransListHist;
    }

    /**
     * @param TransListHist the TransListHist to set
     */
    public void setTransListHist(List<Trans> TransListHist) {
        this.TransListHist = TransListHist;
    }
}
