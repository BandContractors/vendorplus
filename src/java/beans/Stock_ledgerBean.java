package beans;

import api_tax.efris_bean.StockManage;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Item;
import entities.Item_tax_map;
import entities.Item_unit_other;
import entities.Parameter_list;
import entities.Stock;
import entities.Stock_ledger;
import entities.TransItem;
import entities.TransProduction;
import entities.TransProductionItem;
import entities.Transactor;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import utilities.UtilityBean;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.event.ToggleEvent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "stock_ledgerBean")
@SessionScoped
public class Stock_ledgerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Stock_ledgerBean.class.getName());
    private String ActionMessage = null;
    private List<Stock_ledger> Stock_ledgerList;
    private Stock_ledger Stock_ledgerObj;
    private Date Date1;
    private Date Date2;
    private List<Stock_ledger> Stock_ledgerSummary;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private List<Item_unit_other> Item_unit_otherList;

    public void setStock_ledgerFromResultset(Stock_ledger aStock_ledger, ResultSet aResultSet) {
        try {
            try {
                aStock_ledger.setStock_ledger_id(aResultSet.getLong("stock_ledger_id"));
            } catch (NullPointerException npe) {
                aStock_ledger.setStock_ledger_id(0);
            }
            try {
                aStock_ledger.setStore_id(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aStock_ledger.setStore_id(0);
            }
            try {
                aStock_ledger.setItem_id(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aStock_ledger.setItem_id(0);
            }
            try {
                aStock_ledger.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                aStock_ledger.setBatchno("");
            }
            try {
                aStock_ledger.setCode_specific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                aStock_ledger.setCode_specific("");
            }
            try {
                aStock_ledger.setDesc_specific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                aStock_ledger.setDesc_specific("");
            }
            try {
                aStock_ledger.setSpecific_size(aResultSet.getDouble("specific_size"));
            } catch (NullPointerException npe) {
                aStock_ledger.setSpecific_size(0);
            }
            try {
                aStock_ledger.setQty_added(aResultSet.getDouble("qty_added"));
            } catch (NullPointerException npe) {
                aStock_ledger.setQty_added(0);
            }
            try {
                aStock_ledger.setQty_subtracted(aResultSet.getDouble("qty_subtracted"));
            } catch (NullPointerException npe) {
                aStock_ledger.setQty_subtracted(0);
            }
            try {
                aStock_ledger.setTransaction_type_id(aResultSet.getInt("transaction_type_id"));
            } catch (NullPointerException npe) {
                aStock_ledger.setTransaction_type_id(0);
            }
            try {
                aStock_ledger.setAction_type(aResultSet.getString("action_type"));
            } catch (NullPointerException npe) {
                aStock_ledger.setAction_type("");
            }
            try {
                aStock_ledger.setTransaction_id(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                aStock_ledger.setTransaction_id(0);
            }
            try {
                aStock_ledger.setUser_detail_id(aResultSet.getInt("user_detail_id"));
            } catch (NullPointerException npe) {
                aStock_ledger.setUser_detail_id(0);
            }
            try {
                aStock_ledger.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aStock_ledger.setAdd_date(null);
            }
            try {
                aStock_ledger.setQty_bal(aResultSet.getDouble("qty_bal"));
            } catch (NullPointerException npe) {
                aStock_ledger.setQty_bal(0);
            }
            try {
                aStock_ledger.setTax_update_id(aResultSet.getLong("tax_update_id"));
            } catch (NullPointerException npe) {
                aStock_ledger.setTax_update_id(0);
            }
            try {
                aStock_ledger.setTax_is_updated(aResultSet.getInt("tax_is_updated"));
            } catch (NullPointerException npe) {
                aStock_ledger.setTax_is_updated(0);
            }
            try {
                aStock_ledger.setTax_update_synced(aResultSet.getInt("tax_update_synced"));
            } catch (NullPointerException npe) {
                aStock_ledger.setTax_update_synced(0);
            }
            try {
                aStock_ledger.setTransaction_item_id(aResultSet.getLong("transaction_item_id"));
            } catch (Exception e) {
                aStock_ledger.setTransaction_item_id(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public Stock_ledger getStock_ledger(String aTableName, long aStock_ledger_id) {
        String sql = "SELECT * FROM " + aTableName + " WHERE stock_ledger_id=" + aStock_ledger_id;
        ResultSet rs = null;
        Stock_ledger sl = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                sl = new Stock_ledger();
                this.setStock_ledgerFromResultset(sl, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return sl;
    }

    public void callInsertStock_ledger(String aTableName, String aAddSubtract, Stock aStock, double aQty, String aAction_type, int aTrans_type_id, long aTrans_id, int aUser_detail_id) {
        //to be deleted
    }

    public void callInsertStock_ledger(String aTableName, String aAddSubtract, Stock aStock, double aQty, String aAction_type, int aTrans_type_id, long aTrans_id, int aUser_detail_id, long aTrans_item_id) {
        try {
            Stock_ledger stockledger = new Stock_ledger();
            TransProduction TransProd = null;
            TransProductionItem TransProdItem = null;
            try {
                stockledger.setStore_id(aStock.getStoreId());
            } catch (NullPointerException npe) {
                stockledger.setStore_id(0);
            }
            try {
                stockledger.setItem_id(aStock.getItemId());
            } catch (NullPointerException npe) {
                stockledger.setItem_id(0);
            }
            try {
                stockledger.setBatchno(aStock.getBatchno());
            } catch (NullPointerException npe) {
                stockledger.setBatchno("");
            }
            try {
                stockledger.setCode_specific(aStock.getCodeSpecific());
            } catch (NullPointerException npe) {
                stockledger.setCode_specific("");
            }
            try {
                stockledger.setDesc_specific(aStock.getDescSpecific());
            } catch (NullPointerException npe) {
                stockledger.setDesc_specific("");
            }
            try {
                if (aStock.getSpecific_size() > 0) {
                    stockledger.setSpecific_size(aStock.getSpecific_size());
                } else {
                    stockledger.setSpecific_size(1);
                }
            } catch (NullPointerException npe) {
                stockledger.setSpecific_size(1);
            }
            try {
                if (aAddSubtract.equals("Add")) {
                    stockledger.setQty_added(aQty);
                } else {
                    stockledger.setQty_added(0);
                }
            } catch (NullPointerException npe) {
                stockledger.setQty_added(0);
            }
            try {
                if (aAddSubtract.equals("Subtract")) {
                    stockledger.setQty_subtracted(aQty);
                } else {
                    stockledger.setQty_subtracted(0);
                }
            } catch (NullPointerException npe) {
                stockledger.setQty_subtracted(0);
            }
            try {
                stockledger.setTransaction_type_id(aTrans_type_id);
            } catch (NullPointerException npe) {
                stockledger.setTransaction_type_id(0);
            }
            try {
                stockledger.setAction_type(aAction_type);
            } catch (NullPointerException npe) {
                stockledger.setAction_type("");
            }
            try {
                stockledger.setTransaction_id(aTrans_id);
            } catch (NullPointerException npe) {
                stockledger.setTransaction_id(0);
            }
            try {
                stockledger.setUser_detail_id(aUser_detail_id);
            } catch (NullPointerException npe) {
                stockledger.setUser_detail_id(0);
            }
            try {
                stockledger.setAdd_date(new CompanySetting().getCURRENT_SERVER_DATE());
            } catch (NullPointerException npe) {
                stockledger.setAdd_date(null);
            }
            double qtybal = 0;
            try {
                qtybal = new StockBean().getStock(stockledger.getStore_id(), stockledger.getItem_id(), stockledger.getBatchno(), stockledger.getCode_specific(), stockledger.getDesc_specific()).getCurrentqty();
            } catch (Exception e) {
                qtybal = 0;
            }
            stockledger.setQty_bal(qtybal);
            long update_id = 0;
            try {
                update_id = new UtilityBean().getNewTableColumnSeqNumber(aTableName, "tax_update_id");
            } catch (Exception e) {
                update_id = 0;
            }
            stockledger.setTax_update_id(update_id);
            stockledger.setTax_is_updated(0);
            stockledger.setTax_update_synced(0);
            stockledger.setTransaction_item_id(aTrans_item_id);
            //insert
            this.insertStock_ledger(aTableName, stockledger);
            //check alert-stock status
            new Alert_generalBean().checkStockStatusForAlertThread(stockledger.getItem_id());
            //check alert-expiry status
            new Alert_generalBean().checkExpiryStatusForAlertThread(stockledger.getItem_id(), stockledger.getBatchno(), stockledger.getCode_specific(), stockledger.getDesc_specific());
            //URA-STOCK-API
            if (aTrans_type_id != 2 && aTrans_type_id != 4 && aTrans_type_id != 82 && aTrans_type_id != 83 && new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {
                if (aTrans_type_id == 70) {//PROD
                    TransProd = new TransProductionBean().getTransProductionById(aTrans_id);
                    if (aAddSubtract.equals("Subtract")) {//Raw Material / Input
                        TransProdItem = new TransProductionItemBean().getTransProductionItemById(aTrans_item_id);
                    }
                }
                Item_tax_map im = new Item_tax_mapBean().getItem_tax_map(stockledger.getItem_id());
                if (null == im) {
                    //do nothing
                } else {
                    Item itm = new ItemBean().getItem(stockledger.getItem_id());
                    TransItem ti = null;
                    if (aTrans_type_id == 84) {//STOCK TAKE
                        ti = new TransItem();
                        ti.setUnit_id(itm.getUnitId());
                    } else if (aTrans_type_id == 70) {//PROD
                        if (aAddSubtract.equals("Add") && null != TransProd) {//Product/Output
                            ti = new TransItem();
                            ti.setUnit_id(TransProd.getUnit_id());
                        } else if (aAddSubtract.equals("Subtract") && null != TransProdItem) {//Raw Material / Input
                            ti = new TransItem();
                            ti.setUnit_id(TransProdItem.getUnit_id());
                        }
                    } else {
                        ti = new TransItemBean().getTransItem(aTrans_item_id);
                    }
                    if (aStock.getUnitCost() == 0) {
                        aStock.setUnitCost(new TransItemBean().getItemLatestUnitCostPrice(aStock.getItemId(), "", "", "", ti.getUnit_id(), itm.getCurrencyCode(), 1));
                    }
                    if (aAddSubtract.equals("Add")) {
                        Stock stockadd = new Stock();
                        stockadd.setItemId(aStock.getItemId());
                        stockadd.setCurrentqty(aQty);
                        stockadd.setUnitCost(aStock.getUnitCost());
                        String SupplierTIN = "";
                        String SupplierName = "";
                        long SupplierId = 0;
                        if (aTrans_type_id == 70 && null != TransProd) {//PRODUCTION
                            try {
                                SupplierId = TransProd.getTransactor_id();
                            } catch (Exception e) {
                                SupplierId = 0;
                            }
                        } else {
                            try {
                                SupplierId = new TransBean().getTrans(aTrans_id).getTransactorId();
                            } catch (Exception e) {
                                SupplierId = 0;
                            }
                        }
                        if (SupplierId == 0) {
                            SupplierTIN = CompanySetting.getTaxIdentity();
                            SupplierName = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "PAYEE_NAME").getParameter_value();
                        } else {
                            Transactor tr = new TransactorBean().getTransactor(SupplierId);
                            SupplierTIN = tr.getTaxIdentity();
                            SupplierName = tr.getTransactorNames();
                        }
                        String ItemIdTax = "";
                        try {
                            ItemIdTax = new Item_tax_mapBean().getItem_tax_map(stockadd.getItemId()).getItem_id_tax();
                        } catch (Exception e) {
                            ItemIdTax = "";
                        }
                        String UnitCodeTax = "";
                        try {
                            UnitCodeTax = new UnitBean().getUnit(ti.getUnit_id()).getUnit_symbol_tax();
                        } catch (Exception e) {
                            UnitCodeTax = "";
                        }
                        new StockManage().addStockCallThread(stockadd, ItemIdTax, stockledger.getTax_update_id(), SupplierTIN, SupplierName, UnitCodeTax);
                    } else if (aAddSubtract.equals("Subtract")) {
                        Stock stocksub = new Stock();
                        stocksub.setItemId(aStock.getItemId());
                        stocksub.setCurrentqty(aQty);
                        stocksub.setUnitCost(aStock.getUnitCost());
                        //get AdjustType
                        String AdjustType = "105";//Others
                        String ItemIdTax = "";
                        try {
                            ItemIdTax = new Item_tax_mapBean().getItem_tax_map(stocksub.getItemId()).getItem_id_tax();
                        } catch (Exception e) {
                            ItemIdTax = "";
                        }
                        String UnitCodeTax = "";
                        try {
                            UnitCodeTax = new UnitBean().getUnit(ti.getUnit_id()).getUnit_symbol_tax();
                        } catch (Exception e) {
                            UnitCodeTax = "";
                        }
                        new StockManage().subtractStockCallThread(stocksub, ItemIdTax, stockledger.getTax_update_id(), AdjustType, UnitCodeTax);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void insertStock_ledger(String aTableName, Stock_ledger aStock_ledger) {
        String sql = "INSERT INTO " + aTableName
                + "(store_id,item_id,batchno,code_specific,desc_specific,specific_size,"
                + "qty_added,qty_subtracted,transaction_type_id,action_type,transaction_id,user_detail_id,add_date,qty_bal,"
                + "tax_update_id,tax_is_updated,tax_update_synced,transaction_item_id)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aStock_ledger.getStore_id());
            ps.setLong(2, aStock_ledger.getItem_id());
            ps.setString(3, aStock_ledger.getBatchno());
            ps.setString(4, aStock_ledger.getCode_specific());
            ps.setString(5, aStock_ledger.getDesc_specific());
            ps.setDouble(6, aStock_ledger.getSpecific_size());
            ps.setDouble(7, aStock_ledger.getQty_added());
            ps.setDouble(8, aStock_ledger.getQty_subtracted());
            ps.setInt(9, aStock_ledger.getTransaction_type_id());
            ps.setString(10, aStock_ledger.getAction_type());
            ps.setLong(11, aStock_ledger.getTransaction_id());
            ps.setInt(12, aStock_ledger.getUser_detail_id());
            try {
                ps.setTimestamp(13, new java.sql.Timestamp(aStock_ledger.getAdd_date().getTime()));
            } catch (NullPointerException npe) {
                ps.setTimestamp(13, null);
            }
            ps.setDouble(14, aStock_ledger.getQty_bal());
            ps.setLong(15, aStock_ledger.getTax_update_id());
            ps.setInt(16, aStock_ledger.getTax_is_updated());
            ps.setInt(17, aStock_ledger.getTax_update_synced());
            ps.setLong(18, aStock_ledger.getTransaction_item_id());
            ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int updateTaxStock_ledger(String aTableName, long aTax_update_id, int aTax_is_updated, int aTax_update_synced) {
        int update_flag = 0;
        String sql = "UPDATE " + aTableName + " SET tax_is_updated=" + aTax_is_updated + ",tax_update_synced=" + aTax_update_synced + " WHERE tax_update_id=" + aTax_update_id;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate();
            update_flag = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return update_flag;
    }

    public void reSubmitStockTaxAPIAll(List<Stock_ledger> aStock_ledgerList, Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        try {
            int n = 0;
            for (int i = 0; i < aStock_ledgerList.size() && n <= 100; i++) {
                if (aStock_ledgerList.get(i).getTax_update_synced() == 0) {
                    this.reSubmitStockTaxAPI(aStock_ledgerList.get(i), aStock_ledger, aStock_ledgerBean, aItem);
                    n = n + 1;
                }
            }
            //refresh UI
            this.reportStockTaxAPICall(aStock_ledger, aStock_ledgerBean, aItem);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void reSubmitStockTaxAPIOne(Stock_ledger aStock_ledger4Sync, Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        try {
            this.reSubmitStockTaxAPI(aStock_ledger4Sync, aStock_ledger, aStock_ledgerBean, aItem);
            //refresh UI
            this.reportStockTaxAPICall(aStock_ledger, aStock_ledgerBean, aItem);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void reSubmitStockTaxAPI(Stock_ledger aStock_ledger4Sync, Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        try {
            if (aStock_ledger4Sync.getTransaction_type_id() != 2 && aStock_ledger4Sync.getTransaction_type_id() != 4 && new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {
                Item_tax_map im = new Item_tax_mapBean().getItem_tax_map(aStock_ledger4Sync.getItem_id());
                if (null == im) {
                    //do nothing
                } else {
                    Item itm = new ItemBean().getItem(aStock_ledger4Sync.getItem_id());
                    TransItem ti = null;
                    if (aStock_ledger4Sync.getTransaction_type_id() == 84) {//STOCK TAKE
                        ti = new TransItem();
                        ti.setUnit_id(itm.getUnitId());
                    } else {
                        ti = new TransItemBean().getTransItem(aStock_ledger4Sync.getTransaction_item_id());
                    }
                    String TableName = this.getStockLedgerTableName(aStock_ledgerBean);
                    double LatestUnitCostPrice = new TransItemBean().getItemLatestUnitCostPrice(aStock_ledger4Sync.getItem_id(), "", "", "", itm.getUnitId(), itm.getCurrencyCode(), 1);
                    if (aStock_ledger4Sync.getQty_added() > 0) {
                        Stock stockadd = new Stock();
                        stockadd.setItemId(aStock_ledger4Sync.getItem_id());
                        stockadd.setCurrentqty(aStock_ledger4Sync.getQty_added());
                        stockadd.setUnitCost(LatestUnitCostPrice);
                        String SupplierTIN = "";
                        String SupplierName = "";
                        long SupplierId = 0;
                        if (aStock_ledger4Sync.getTransaction_type_id() == 70) {//PRODUCTION
                            try {
                                SupplierId = new TransProductionBean().getTransProductionById(aStock_ledger4Sync.getTransaction_id()).getTransactor_id();
                            } catch (Exception e) {
                                SupplierId = 0;
                            }
                        } else if (aStock_ledger4Sync.getTransaction_type_id() == 84) {
                            SupplierId = 0;
                        } else {
                            try {
                                SupplierId = new TransBean().getTrans(aStock_ledger4Sync.getTransaction_id()).getTransactorId();
                            } catch (Exception e) {
                                SupplierId = 0;
                            }
                        }
                        if (SupplierId == 0) {
                            SupplierTIN = CompanySetting.getTaxIdentity();
                            SupplierName = new Parameter_listBean().getParameter_listByContextName("COMPANY_SETTING", "PAYEE_NAME").getParameter_value();
                        } else {
                            Transactor tr = new TransactorBean().getTransactor(SupplierId);
                            SupplierTIN = tr.getTaxIdentity();
                            SupplierName = tr.getTransactorNames();
                        }
                        String ItemIdTax = "";
                        try {
                            ItemIdTax = new Item_tax_mapBean().getItem_tax_map(stockadd.getItemId()).getItem_id_tax();
                        } catch (Exception e) {
                            ItemIdTax = "";
                        }
                        String UnitCodeTax = "";
                        try {
                            UnitCodeTax = new UnitBean().getUnit(ti.getUnit_id()).getUnit_symbol_tax();
                        } catch (Exception e) {
                            UnitCodeTax = "";
                        }
                        try {
                            if (null == UnitCodeTax || UnitCodeTax.isEmpty()) {
                                UnitCodeTax = new UnitBean().getUnit(new ItemBean().getItem(aStock_ledger4Sync.getItem_id()).getUnitId()).getUnit_symbol_tax();
                            }
                        } catch (Exception e) {
                            UnitCodeTax = "";
                        }
                        new StockManage().addStockCall(stockadd, ItemIdTax, aStock_ledger4Sync.getTax_update_id(), SupplierTIN, SupplierName, TableName, UnitCodeTax);
                    } else if (aStock_ledger4Sync.getQty_subtracted() > 0) {
                        Stock stocksub = new Stock();
                        stocksub.setItemId(aStock_ledger4Sync.getItem_id());
                        stocksub.setCurrentqty(aStock_ledger4Sync.getQty_subtracted());
                        stocksub.setUnitCost(LatestUnitCostPrice);
                        //get AdjustType
                        String AdjustType = "105";//Others
                        String ItemIdTax = "";
                        try {
                            ItemIdTax = new Item_tax_mapBean().getItem_tax_map(stocksub.getItemId()).getItem_id_tax();
                        } catch (Exception e) {
                            ItemIdTax = "";
                        }
                        String UnitCodeTax = "";
                        try {
                            UnitCodeTax = new UnitBean().getUnit(ti.getUnit_id()).getUnit_symbol_tax();
                        } catch (Exception e) {
                            UnitCodeTax = "";
                        }
                        try {
                            if (null == UnitCodeTax || UnitCodeTax.isEmpty()) {
                                UnitCodeTax = new UnitBean().getUnit(new ItemBean().getItem(aStock_ledger4Sync.getItem_id()).getUnitId()).getUnit_symbol_tax();
                            }
                        } catch (Exception e) {
                            UnitCodeTax = "";
                        }
                        new StockManage().subtractStockCall(stocksub, ItemIdTax, aStock_ledger4Sync.getTax_update_id(), AdjustType, TableName, UnitCodeTax);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearStock_ledger(Stock_ledger aStock_ledger) {
        if (null != aStock_ledger) {
            aStock_ledger.setStock_ledger_id(0);
            aStock_ledger.setStore_id(0);
            aStock_ledger.setItem_id(0);
            aStock_ledger.setBatchno("");
            aStock_ledger.setCode_specific("");
            aStock_ledger.setDesc_specific("");
            aStock_ledger.setSpecific_size(0);
            aStock_ledger.setQty_added(0);
            aStock_ledger.setQty_subtracted(0);
            aStock_ledger.setTransaction_type_id(0);
            aStock_ledger.setAction_type("");
            aStock_ledger.setTransaction_id(0);
            aStock_ledger.setUser_detail_id(0);
            aStock_ledger.setAdd_date(null);
            aStock_ledger.setQty_bal(0);
            aStock_ledger.setDescription("");
            aStock_ledger.setUnit_symbol("");
            aStock_ledger.setTransaction_type_name("");
            aStock_ledger.setUser_name("");
            aStock_ledger.setStore_name("");
            aStock_ledger.setTax_update_id(0);
            aStock_ledger.setTax_is_updated(0);
            aStock_ledger.setTax_update_synced(0);
            aStock_ledger.setTransaction_item_id(0);
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

    public void reportStock_ledger(Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        aStock_ledgerBean.setActionMessage("");
        int Month1 = 0;
        int Month2 = 0;
        int Year1 = 0;
        int Year2 = 0;
        try {
            Month1 = new UtilityBean().getMonthFromDate(aStock_ledgerBean.getDate1());
            Month2 = new UtilityBean().getMonthFromDate(aStock_ledgerBean.getDate2());
            Year1 = new UtilityBean().getYearFromDate(aStock_ledgerBean.getDate1());
            Year2 = new UtilityBean().getYearFromDate(aStock_ledgerBean.getDate2());
        } catch (Exception e) {
        }
        if (Month1 == 0 || Month2 == 0 || Month1 != Month2 || Year1 == 0 || Year2 == 0 || Year1 != Year2) {
            msg = "From Date and To Date Must be Within Same Month";
        }
        if (msg.length() > 0) {
            aStock_ledgerBean.setActionMessage(ub.translateWordsInText(BaseName, msg));
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String TableName = this.getStockLedgerTableName(Year1, Month1);
            this.reportStock_ledgerSummary(TableName, aStock_ledger, aStock_ledgerBean, aItem);
            this.reportStock_ledgerDetail(TableName, aStock_ledger, aStock_ledgerBean, aItem);
        }
    }

    public void reportStock_ledgerDetail(String aTableName, Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        aStock_ledgerBean.setActionMessage("");
        ResultSet rs = null;
        this.Stock_ledgerList = new ArrayList<>();
        String sql1 = "SELECT l.*,i.description,ifnull(un2.unit_symbol,un.unit_symbol) as unit_symbol,un.unit_symbol as base_unit_symbol,"
                + "tt.transaction_type_name,us.user_name,s.store_name,tiu.unit_id "
                + "FROM " + aTableName + " l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "LEFT JOIN transaction_item_unit tiu ON l.transaction_item_id=tiu.transaction_item_id "
                + "LEFT JOIN unit un2 ON tiu.unit_id=un2.unit_id "
                + "WHERE l.transaction_type_id NOT IN(82,83,70)";
        String sql2 = "SELECT l.*,i.description,ifnull(un2.unit_symbol,un.unit_symbol) as unit_symbol,un.unit_symbol as base_unit_symbol,"
                + "tt.transaction_type_name,us.user_name,s.store_name,tiu.unit_id "
                + "FROM " + aTableName + " l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "LEFT JOIN transaction_item_cr_dr_note_unit tiu ON l.transaction_item_id=tiu.transaction_item_id "
                + "LEFT JOIN unit un2 ON tiu.unit_id=un2.unit_id "
                + "WHERE l.transaction_type_id IN(82,83)";
        String sql3 = "SELECT l.*,i.description,ifnull(un2.unit_symbol,un.unit_symbol) as unit_symbol,un.unit_symbol as base_unit_symbol,"
                + "tt.transaction_type_name,us.user_name,s.store_name,tiu.unit_id "
                + "FROM " + aTableName + " l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "INNER JOIN trans_production tiu ON l.transaction_item_id=tiu.transaction_id AND l.item_id=tiu.output_item_id "
                + "INNER JOIN unit un2 ON tiu.unit_id=un2.unit_id "
                + "WHERE l.transaction_type_id IN(70) AND l.qty_added>0";
        String sql4 = "SELECT l.*,i.description,ifnull(un2.unit_symbol,un.unit_symbol) as unit_symbol,un.unit_symbol as base_unit_symbol,"
                + "tt.transaction_type_name,us.user_name,s.store_name,tiu.unit_id "
                + "FROM " + aTableName + " l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "INNER JOIN trans_production_item tiu ON l.transaction_item_id=tiu.trans_production_item_id AND l.item_id=tiu.input_item_id "
                + "INNER JOIN unit un2 ON tiu.unit_id=un2.unit_id "
                + "WHERE l.transaction_type_id IN(70) AND l.qty_subtracted>0";
        String wheresql = "";
        String ordersql = "";
        if (aStock_ledger.getStore_id() > 0) {
            wheresql = wheresql + " AND l.store_id=" + aStock_ledger.getStore_id();
        }
        if (aStock_ledger.getTransaction_type_id() > 0) {
            wheresql = wheresql + " AND l.transaction_type_id=" + aStock_ledger.getTransaction_type_id();
        }
        if (aStock_ledger.getUser_detail_id() > 0) {
            wheresql = wheresql + " AND l.user_detail_id=" + aStock_ledger.getUser_detail_id();
        }
        try {
            if (null != aItem && aItem.getItemId() > 0) {
                wheresql = wheresql + " AND l.item_id=" + aItem.getItemId();
            }
        } catch (NullPointerException npe) {

        }
        if (aStock_ledgerBean.getDate1() != null && aStock_ledgerBean.getDate2() != null) {
            wheresql = wheresql + " AND l.add_date BETWEEN '" + new java.sql.Timestamp(aStock_ledgerBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aStock_ledgerBean.getDate2().getTime()) + "'";
        }
        //ordersql = " ORDER BY l.add_date DESC,l.stock_ledger_id DESC";
        String sql = "(" + sql1 + wheresql + ") UNION (" + sql2 + wheresql + ") UNION (" + sql3 + wheresql + ") UNION (" + sql4 + wheresql + ") ";
        ordersql = " ORDER BY add_date DESC,stock_ledger_id DESC";
        sql = sql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock_ledger sl = null;
            Stock_ledgerBean slb = new Stock_ledgerBean();
            String TransNo = "";
            while (rs.next()) {
                sl = new Stock_ledger();
                slb.setStock_ledgerFromResultset(sl, rs);
                sl.setDescription(rs.getString("description"));
                sl.setUnit_symbol(rs.getString("unit_symbol"));
                sl.setBase_unit_symbol(rs.getString("base_unit_symbol"));
                sl.setTransaction_type_name(rs.getString("transaction_type_name"));
                sl.setUser_name(rs.getString("user_name"));
                sl.setStore_name(rs.getString("store_name"));
                TransNo = "";
                try {
                    if (sl.getTransaction_type_id() == 70) {
                        TransNo = new TransProductionBean().getTransProductionById(sl.getTransaction_id()).getTransaction_number();
                    } else if (sl.getTransaction_type_id() == 82 || sl.getTransaction_type_id() == 83) {
                        TransNo = new CreditDebitNoteBean().getTrans_cr_dr_note(sl.getTransaction_id()).getTransactionNumber();
                    } else {
                        TransNo = new TransBean().getTrans(sl.getTransaction_id()).getTransactionNumber();
                    }
                } catch (Exception e) {
                    //do nothing
                }
                sl.setTransaction_number(TransNo);
                this.Stock_ledgerList.add(sl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void reportStock_ledgerSummary(String aTableName, Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        aStock_ledgerBean.setActionMessage("");
        ResultSet rs = null;
        this.Stock_ledgerSummary = new ArrayList<>();
        String wheresql = "";
        String wheredate = "";
        try {
            if (null != aItem && aItem.getItemId() > 0) {
                wheresql = wheresql + " AND l.item_id=" + aItem.getItemId();
            }
        } catch (NullPointerException npe) {
        }
        if (aStock_ledgerBean.getDate1() != null && aStock_ledgerBean.getDate2() != null) {
            wheresql = wheresql + " AND l.add_date BETWEEN '" + new java.sql.Timestamp(aStock_ledgerBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aStock_ledgerBean.getDate2().getTime()) + "'";
            wheredate = " AND (l.add_date BETWEEN '" + new java.sql.Timestamp(aStock_ledgerBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aStock_ledgerBean.getDate2().getTime()) + "')";
        }
        String sql = "select "
                + "t.*,"
                + "i.description,"
                + "u.unit_symbol,"
                + "(select l2.qty_bal from " + aTableName + " l2 where l2.stock_ledger_id=t.max_id) as qty_close "
                + "from "
                + "("
                + "select l.item_id,l.batchno,l.code_specific,l.desc_specific,"
                + "min(l.stock_ledger_id) as min_id,"
                + "max(l.stock_ledger_id) as max_id "
                + "from " + aTableName + " l  "
                + "where 1=1 " + wheresql + " "
                + "group by l.item_id,l.batchno,l.code_specific,l.desc_specific "
                + ") as t "
                + "inner join item i on t.item_id=i.item_id "
                + "inner join unit u on i.unit_id=u.unit_id "
                + "order by i.description";
        //System.out.println("sql1:" + sql);
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock_ledger sl = null;
            Stock_ledgerBean slb = new Stock_ledgerBean();
            while (rs.next()) {
                sl = new Stock_ledger();
                sl.setItem_id(rs.getLong("item_id"));
                sl.setDescription(rs.getString("description"));
                sl.setUnit_symbol(rs.getString("unit_symbol"));
                sl.setBatchno(rs.getString("batchno"));
                sl.setCode_specific(rs.getString("code_specific"));
                sl.setDesc_specific(rs.getString("desc_specific"));
                long MinId = rs.getLong("min_id");
                Stock_ledger MinSL = this.getStock_ledger(aTableName, MinId);
                String MinSql = "";
                if (MinSL.getTransaction_type_id() == 82 || MinSL.getTransaction_type_id() == 83) {
                    MinSql = "select case when l1.qty_added>0 then l1.qty_bal-ifnull(tiu.base_unit_qty,l1.qty_added) when l1.qty_subtracted>0 then l1.qty_bal+ifnull(tiu.base_unit_qty,l1.qty_subtracted) else 0 end as d from " + aTableName + " l1 LEFT JOIN transaction_item_cr_dr_note_unit tiu ON l1.transaction_item_id=tiu.transaction_item_id where l1.stock_ledger_id=" + MinId;
                } else if (MinSL.getTransaction_type_id() == 70 && MinSL.getQty_added() > 0) {
                    MinSql = "select case when l1.qty_added>0 then l1.qty_bal-ifnull(tiu.base_unit_qty,l1.qty_added) when l1.qty_subtracted>0 then l1.qty_bal+ifnull(tiu.base_unit_qty,l1.qty_subtracted) else 0 end as d from " + aTableName + " l1 LEFT JOIN trans_production tiu ON l1.transaction_item_id=tiu.transaction_id where l1.stock_ledger_id=" + MinId;
                } else if (MinSL.getTransaction_type_id() == 70 && MinSL.getQty_subtracted() > 0) {
                    MinSql = "select case when l1.qty_added>0 then l1.qty_bal-ifnull(tiu.base_unit_qty,l1.qty_added) when l1.qty_subtracted>0 then l1.qty_bal+ifnull(tiu.base_unit_qty,l1.qty_subtracted) else 0 end as d from " + aTableName + " l1 LEFT JOIN trans_production_item tiu ON l1.transaction_item_id=tiu.trans_production_item_id where l1.stock_ledger_id=" + MinId;
                } else {
                    MinSql = "select case when l1.qty_added>0 then l1.qty_bal-ifnull(tiu.base_unit_qty,l1.qty_added) when l1.qty_subtracted>0 then l1.qty_bal+ifnull(tiu.base_unit_qty,l1.qty_subtracted) else 0 end as d from " + aTableName + " l1 LEFT JOIN transaction_item_unit tiu ON l1.transaction_item_id=tiu.transaction_item_id where l1.stock_ledger_id=" + MinId;
                }
                //System.out.println("MinSql:" + MinSql);
                double OpenQty = new UtilityBean().getD(MinSql);
                sl.setQty_open(OpenQty);
                sl.setQty_close(rs.getDouble("qty_close"));
                String whereinner = " AND l.item_id=" + sl.getItem_id() + " AND l.batchno='" + sl.getBatchno() + "' AND l.code_specific='" + sl.getCode_specific() + "' AND l.desc_specific='" + sl.getDesc_specific() + "' " + wheredate;
                sl.setStock_ledgerList(this.getStock_ledgerInner(aTableName, whereinner));
                this.Stock_ledgerSummary.add(sl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void reportStockTaxAPICall(Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        aStock_ledgerBean.setActionMessage("");
        int Month1 = 0;
        int Month2 = 0;
        int Year1 = 0;
        int Year2 = 0;
        try {
            Month1 = new UtilityBean().getMonthFromDate(aStock_ledgerBean.getDate1());
            Month2 = new UtilityBean().getMonthFromDate(aStock_ledgerBean.getDate2());
            Year1 = new UtilityBean().getYearFromDate(aStock_ledgerBean.getDate1());
            Year2 = new UtilityBean().getYearFromDate(aStock_ledgerBean.getDate2());
        } catch (Exception e) {
        }
        if (Month1 == 0 || Month2 == 0 || Month1 != Month2 || Year1 == 0 || Year2 == 0 || Year1 != Year2) {
            msg = "From Date and To Date Must be Within Same Month";
        }
        if (msg.length() > 0) {
            aStock_ledgerBean.setActionMessage(ub.translateWordsInText(BaseName, msg));
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String TableName = this.getStockLedgerTableName(Year1, Month1);
            this.reportStockTaxAPIDetail(TableName, aStock_ledger, aStock_ledgerBean, aItem);
        }
    }

    public void reportStockTaxAPIDetail(String aTableName, Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        aStock_ledgerBean.setActionMessage("");
        ResultSet rs = null;
        this.Stock_ledgerList = new ArrayList<>();
        String sql = "";
        String sql1 = "SELECT l.*,i.description,ifnull(un2.unit_symbol,un.unit_symbol) as unit_symbol,un.unit_symbol as base_unit_symbol,"
                + "tt.transaction_type_name,us.user_name,s.store_name,tiu.unit_id "
                + "FROM " + aTableName + " l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "LEFT JOIN transaction_item_unit tiu ON l.transaction_item_id=tiu.transaction_item_id "
                + "LEFT JOIN unit un2 ON tiu.unit_id=un2.unit_id "
                + "WHERE l.transaction_type_id NOT IN (2,4,70) AND l.tax_is_updated=1";
        String sql2 = "SELECT l.*,i.description,ifnull(un2.unit_symbol,un.unit_symbol) as unit_symbol,un.unit_symbol as base_unit_symbol,"
                + "tt.transaction_type_name,us.user_name,s.store_name,tiu.unit_id "
                + "FROM " + aTableName + " l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "LEFT JOIN trans_production tiu ON l.transaction_item_id=tiu.transaction_id "
                + "LEFT JOIN unit un2 ON tiu.unit_id=un2.unit_id "
                + "WHERE l.transaction_type_id=70 AND l.qty_added>0 AND l.tax_is_updated=1";
        String sql3 = "SELECT l.*,i.description,ifnull(un2.unit_symbol,un.unit_symbol) as unit_symbol,un.unit_symbol as base_unit_symbol,"
                + "tt.transaction_type_name,us.user_name,s.store_name,tiu.unit_id "
                + "FROM " + aTableName + " l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "LEFT JOIN trans_production_item tiu ON l.transaction_item_id=tiu.trans_production_item_id "
                + "LEFT JOIN unit un2 ON tiu.unit_id=un2.unit_id "
                + "WHERE l.transaction_type_id=70 AND l.qty_subtracted>0 AND l.tax_is_updated=1";
        String wheresql = "";
        String ordersql = "";
        if (aStock_ledger.getStore_id() > 0) {
            wheresql = wheresql + " AND l.store_id=" + aStock_ledger.getStore_id();
        }
        if (aStock_ledger.getTransaction_type_id() > 0) {
            wheresql = wheresql + " AND l.transaction_type_id=" + aStock_ledger.getTransaction_type_id();
        }
        if (aStock_ledger.getUser_detail_id() > 0) {
            wheresql = wheresql + " AND l.user_detail_id=" + aStock_ledger.getUser_detail_id();
        }
        try {
            if (null != aItem && aItem.getItemId() > 0) {
                wheresql = wheresql + " AND l.item_id=" + aItem.getItemId();
            }
        } catch (NullPointerException npe) {

        }
        if (aStock_ledgerBean.getDate1() != null && aStock_ledgerBean.getDate2() != null) {
            wheresql = wheresql + " AND l.add_date BETWEEN '" + new java.sql.Timestamp(aStock_ledgerBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aStock_ledgerBean.getDate2().getTime()) + "'";
        }
        //ordersql = " ORDER BY l.add_date DESC,l.stock_ledger_id DESC";
        ordersql = " ORDER BY add_date DESC,stock_ledger_id DESC";
        //sql = sql + wheresql + ordersql;
        sql = "(" + sql1 + wheresql + ") UNION (" + sql2 + wheresql + ") UNION (" + sql3 + wheresql + ") " + ordersql;
        //System.out.println("sql:" + sql);
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock_ledger sl = null;
            Stock_ledgerBean slb = new Stock_ledgerBean();
            String TransNo = "";
            while (rs.next()) {
                sl = new Stock_ledger();
                slb.setStock_ledgerFromResultset(sl, rs);
                sl.setDescription(rs.getString("description"));
                sl.setUnit_symbol(rs.getString("unit_symbol"));
                sl.setBase_unit_symbol(rs.getString("base_unit_symbol"));
                sl.setTransaction_type_name(rs.getString("transaction_type_name"));
                sl.setUser_name(rs.getString("user_name"));
                sl.setStore_name(rs.getString("store_name"));
                TransNo = "";
                try {
                    if (sl.getTransaction_type_id() == 70) {
                        TransNo = new TransProductionBean().getTransProductionById(sl.getTransaction_id()).getTransaction_number();
                    } else {
                        TransNo = new TransBean().getTrans(sl.getTransaction_id()).getTransactionNumber();
                    }
                } catch (Exception e) {
                    //do nothing
                }
                sl.setTransaction_number(TransNo);
                this.Stock_ledgerList.add(sl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Stock_ledger> getStock_ledgerInner(String aTableName, String aWhereSql) {
        ResultSet rs = null;
        List<Stock_ledger> sll = new ArrayList<>();
        String sql1 = "select "
                + "sm.*,tt.transaction_type_name from "
                + "("
                + "select "
                + "l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0) as unit_id,"
                + "sum(l.qty_added) as qty_added,"
                + "sum(l.qty_subtracted) as qty_subtracted  "
                + "from " + aTableName + " l "
                + "LEFT JOIN transaction_item_unit tiu ON l.transaction_item_id=tiu.transaction_item_id "
                + "where l.transaction_type_id NOT IN(82,83,70) " + aWhereSql + " "
                + "group by l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0)"
                + ") as sm "
                + "inner join transaction_type tt on sm.transaction_type_id=tt.transaction_type_id ";
        String sql2 = "select "
                + "sm.*,tt.transaction_type_name from "
                + "("
                + "select "
                + "l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0) as unit_id,"
                + "sum(l.qty_added) as qty_added,"
                + "sum(l.qty_subtracted) as qty_subtracted  "
                + "from " + aTableName + " l "
                + "LEFT JOIN transaction_item_cr_dr_note_unit tiu ON l.transaction_item_id=tiu.transaction_item_id "
                + "where l.transaction_type_id IN(82,83) " + aWhereSql + " "
                + "group by l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0)"
                + ") as sm "
                + "inner join transaction_type tt on sm.transaction_type_id=tt.transaction_type_id ";
        String sql3 = "select "
                + "sm.*,tt.transaction_type_name from "
                + "("
                + "select "
                + "l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0) as unit_id,"
                + "sum(l.qty_added) as qty_added,"
                + "sum(l.qty_subtracted) as qty_subtracted  "
                + "from " + aTableName + " l "
                + "LEFT JOIN trans_production tiu ON l.transaction_item_id=tiu.transaction_id "
                + "where l.transaction_type_id IN(70) AND l.qty_added>0 " + aWhereSql + " "
                + "group by l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0)"
                + ") as sm "
                + "inner join transaction_type tt on sm.transaction_type_id=tt.transaction_type_id ";
        String sql4 = "select "
                + "sm.*,tt.transaction_type_name from "
                + "("
                + "select "
                + "l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0) as unit_id,"
                + "sum(l.qty_added) as qty_added,"
                + "sum(l.qty_subtracted) as qty_subtracted  "
                + "from " + aTableName + " l "
                + "LEFT JOIN trans_production_item tiu ON l.transaction_item_id=tiu.trans_production_item_id "
                + "where l.transaction_type_id IN(70) AND l.qty_subtracted>0 " + aWhereSql + " "
                + "group by l.transaction_type_id,l.item_id,ifnull(tiu.unit_id,0)"
                + ") as sm "
                + "inner join transaction_type tt on sm.transaction_type_id=tt.transaction_type_id ";
        String sql = sql1 + " UNION " + sql2 + " UNION " + sql3 + " UNION " + sql4;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stock_ledger sl = null;
            int UnitId = 0;
            ItemBean ib = new ItemBean();
            UnitBean ub = new UnitBean();
            while (rs.next()) {
                sl = new Stock_ledger();
                sl.setTransaction_type_name(rs.getString("transaction_type_name"));
                sl.setQty_subtracted(rs.getDouble("qty_subtracted"));
                sl.setQty_added(rs.getDouble("qty_added"));
                UnitId = rs.getInt("unit_id");
                if (UnitId == 0) {
                    UnitId = ib.getItem(rs.getLong("item_id")).getUnitId();
                }
                sl.setUnit_symbol(ub.getUnit(UnitId).getUnitSymbol());
                sll.add(sl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return sll;
    }

    public void initResetStock_ledgerDetail(Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetStock_ledgerDetail(aStock_ledger, aStock_ledgerBean, aItem);
        }
    }

    public void resetStock_ledgerDetail(Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        aStock_ledgerBean.setActionMessage("");
        try {
            this.clearStock_ledger(aStock_ledger);
        } catch (NullPointerException npe) {
        }
        try {
            new ItemBean().clearItem(aItem);
        } catch (NullPointerException npe) {
        }
        try {
            //aStock_ledgerBean.setDate1(null);
            //aStock_ledgerBean.setDate2(null);
            this.setDateToToday();
            aStock_ledgerBean.Stock_ledgerList.clear();
            aStock_ledgerBean.Stock_ledgerSummary.clear();
        } catch (NullPointerException npe) {
        }
    }

    public String getTodayMonthlyStockLedgerTable() {
        String sql = "{call sp_get_today_monthly_stock_ledger_table()}";
        String TableName = "";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            rs = cs.executeQuery();
            if (rs.next()) {
                TableName = rs.getString("TableName");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return TableName;
    }

    public int createTodayMonthlyStockLedgerTable(String aTableName) {
        String sql = "{call sp_create_today_monthly_stock_ledger_table(?)}";
        int status = 0;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString("in_TableName", aTableName);
            cs.executeUpdate();
            status = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return status;
    }

    /* check stock ledger table
     1. at login, get today's monthly table
     2. check 
     >if exists, do nothing (means parameter list has current)
     >if not exist, a) create one, b) update parameter list c) refresh
     */
    public void checkTodayMonthlyStockLedgerTable() {
        try {
            Parameter_list pl = new Parameter_listBean().getParameter_listById(75);
            if (pl == null) {
                //do nothing, parameterList not updated
            } else {
                String TodayMonthTableName = this.getTodayMonthlyStockLedgerTable();
                String ExistMonthTableName = "";
                try {
                    ExistMonthTableName = pl.getParameter_value();
                } catch (NullPointerException | ClassCastException npe) {
                }
                if (TodayMonthTableName.equals(ExistMonthTableName)) {
                    //do nothing
                } else {
                    if (new Stock_ledgerBean().createTodayMonthlyStockLedgerTable(TodayMonthTableName) == 1) {
                        pl.setParameter_value(TodayMonthTableName);
                        new Parameter_listBean().saveParameter_list(pl);
                        new Parameter_listBean().saveParameter_listByIdMemory(75, TodayMonthTableName);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public String getStockLedgerTableName(Stock_ledgerBean aStock_ledgerBean) {
        int Month1 = 0;
        int Year1 = 0;
        try {
            Month1 = new UtilityBean().getMonthFromDate(aStock_ledgerBean.getDate1());
            Year1 = new UtilityBean().getYearFromDate(aStock_ledgerBean.getDate1());
        } catch (Exception e) {
        }
        return this.getStockLedgerTableName(Year1, Month1);
    }

    public String getStockLedgerTableName(int aYearNo, int aMonthNo) {
        String TableName = "";
        String monthstr = "";
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("00");
        monthstr = decimalFormat.format(aMonthNo);
        TableName = "stock_ledger_" + aYearNo + "_" + monthstr;
        return TableName;
    }

    public void onColumnToggleDetail(ToggleEvent event) {
        try {
            String ColIndex = event.getData().toString();
            String Visibility = event.getVisibility().toString();
            if (ColIndex.equals("10") && Visibility.equals("VISIBLE")) {
                this.loadMultipleUnitsForEachStockLedger();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    //loop through stock leger records and their multiple units
    public void loadMultipleUnitsForEachStockLedger() {
        if (Stock_ledgerList != null) {
            if (Stock_ledgerList.size() > 0) {
                for (Stock_ledger sl : Stock_ledgerList) {
                    if (sl.getQty_bal() != Math.floor(sl.getQty_bal())) {
                        setItem_unit_otherList(getListOfOtherUnitsForStockLedger(sl.getItem_id()));
                        if (getItem_unit_otherList() != null) {
                            if (getItem_unit_otherList().size() > 0) {
                                sl.setItem_unit_otherList(getItem_unit_otherList());
                                sl.setMultiUnitsString(buildOtherUnitValuesForStockLedger(sl.getQty_bal(), sl));
                            }
                        }
                    }
                }
            }
        }
    }

    public String buildOtherUnitValuesForStockLedger(Double qty_bal, Stock_ledger sl) {
        int otherQty = 0;
        double doubleOtherQty = 0.0;
        StringBuilder strBuilder = new StringBuilder("");
        Double otherUnitValue = 0.0;
        Item_unit_other iuo = null;
        try {
            int baseQty = qty_bal.intValue();
            strBuilder.append(qty_bal.intValue()).append(" ").append(sl.getBase_unit_symbol());
            Double nextValueLessThanFullbase = qty_bal - baseQty;

            List<Item_unit_other> iuoList = sl.getItem_unit_otherList();
            int ouiListSize = iuoList.size();
            for (int i = 0; i < ouiListSize; i++) {
                otherQty = 0;
                iuo = iuoList.get(i);
                // if (i > 0) {
                //otherUnitValue = nextValueLessThanFullbase * (iuo.getOther_qty() / iuoList.get(i - 1).getOther_qty());
                // } else {
                otherUnitValue = nextValueLessThanFullbase * iuo.getOther_qty();
                // }
                //check if its the last unit in the list and round off
                //System.out.println(i + ": " + sl.getDescription() + " unit qty " + iuo.getOther_qty());
                if (i == ouiListSize - 1) {
                    otherQty = (int) Math.round(otherUnitValue);//get whole number
                    double othernumber = otherUnitValue - otherQty;
                    if (othernumber <= 0.0001) {
                        doubleOtherQty = otherUnitValue;
                    }

                } else {
                    otherQty = otherUnitValue.intValue();//get whole number
                }
                if (otherQty > 0) {
                    double othernumber = otherUnitValue - otherQty;
                    if (othernumber <= 0.0001) {
                        otherQty = (int) Math.round(otherUnitValue);
                        //System.out.println("otherQty :" + otherQty);
                    }

                    strBuilder.append(", ").append(otherQty).append(" ").append(iuo.getOther_unit_symbol());
                }
                //System.out.println(strBuilder.toString());
                nextValueLessThanFullbase = (otherUnitValue - otherQty) / iuo.getOther_qty();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return strBuilder.toString();
    }

    public List<Item_unit_other> getListOfOtherUnitsForStockLedger(long item_id) {
        String sql = "SELECT distinct itu.other_unit_id, itu.item_id,itu.base_qty,itu.other_qty,"
                + " (select u.unit_symbol from unit u where unit_id =itu.other_unit_id ) as other_unit_symbol "
                + "FROM item_unit_other itu, item i "
                + "WHERE itu.item_id=" + item_id + "";
        //build a list of item other units for a record ledger
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Item_unit_other iuo = null;
            setItem_unit_otherList(new ArrayList<>());
            while (rs.next()) {
                iuo = new Item_unit_other();
                iuo.setOther_qty(rs.getLong("other_unit_id"));
                iuo.setOther_qty(rs.getDouble("other_qty"));
                iuo.setItem_id(rs.getLong("item_id"));
                iuo.setOther_unit_symbol(rs.getString("other_unit_symbol"));
                getItem_unit_otherList().add(iuo);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getItem_unit_otherList();
    }

    /**
     * @return the Stock_ledgerList
     */
    public List<Stock_ledger> getStock_ledgerList() {
        return Stock_ledgerList;
    }

    /**
     * @param Stock_ledgerList the Stock_ledgerList to set
     */
    public void setStock_ledgerList(List<Stock_ledger> Stock_ledgerList) {
        this.Stock_ledgerList = Stock_ledgerList;
    }

    /**
     * @return the Stock_ledgerObj
     */
    public Stock_ledger getStock_ledgerObj() {
        return Stock_ledgerObj;
    }

    /**
     * @param Stock_ledgerObj the Stock_ledgerObj to set
     */
    public void setStock_ledgerObj(Stock_ledger Stock_ledgerObj) {
        this.Stock_ledgerObj = Stock_ledgerObj;
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
     * @return the Stock_ledgerSummary
     */
    public List<Stock_ledger> getStock_ledgerSummary() {
        return Stock_ledgerSummary;
    }

    /**
     * @param Stock_ledgerSummary the Stock_ledgerSummary to set
     */
    public void setStock_ledgerSummary(List<Stock_ledger> Stock_ledgerSummary) {
        this.Stock_ledgerSummary = Stock_ledgerSummary;
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
     * @return the Item_unit_otherList
     */
    public List<Item_unit_other> getItem_unit_otherList() {
        return Item_unit_otherList;
    }

    /**
     * @param Item_unit_otherList the Item_unit_otherList to set
     */
    public void setItem_unit_otherList(List<Item_unit_other> Item_unit_otherList) {
        this.Item_unit_otherList = Item_unit_otherList;
    }
}
