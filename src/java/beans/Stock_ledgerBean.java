package beans;

import api_tax.efris_bean.StockManage;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Item;
import entities.Item_tax_map;
import entities.Stock;
import entities.Stock_ledger;
import entities.Transactor;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
@ManagedBean(name = "stock_ledgerBean")
@SessionScoped
public class Stock_ledgerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Stock_ledger> Stock_ledgerList;
    private Stock_ledger Stock_ledgerObj;
    private Date Date1;
    private Date Date2;
    private List<Stock_ledger> Stock_ledgerSummary;

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
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public Stock_ledger getStock_ledger(long aStock_ledger_id) {
        String sql = "SELECT * FROM stock_ledger WHERE stock_ledger_id=" + aStock_ledger_id;
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
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return sl;
    }

    public void callInsertStock_ledger(String aAddSubtract, Stock aStock, double aQty, String aAction_type, int aTrans_type_id, long aTrans_id, int aUser_detail_id) {
        try {
            Stock_ledger stockledger = new Stock_ledger();
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
                update_id = new UtilityBean().getNewTableColumnSeqNumber("stock_ledger", "tax_update_id");
            } catch (Exception e) {
                update_id = 0;
            }
            stockledger.setTax_update_id(update_id);
            stockledger.setTax_is_updated(0);
            stockledger.setTax_update_synced(0);
            //insert
            this.insertStock_ledger(stockledger);
            //check alert-stock status
            new Alert_generalBean().checkStockStatusForAlert(stockledger.getItem_id());
            //check alert-expiry status
            new Alert_generalBean().checkExpiryStatusForAlert(stockledger.getItem_id(), stockledger.getBatchno(), stockledger.getCode_specific(), stockledger.getDesc_specific());
            //URA-STOCK-API
            if (aTrans_type_id != 2 && new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "TAX_BRANCH_NO").getParameter_value().length() > 0) {
                Item_tax_map im = new Item_tax_mapBean().getItem_tax_map(stockledger.getItem_id());
                if (null == im) {
                    //do nothing
                } else {
                    if (aAddSubtract.equals("Add")) {
                        Stock stockadd = new Stock();
                        stockadd.setItemId(aStock.getItemId());
                        stockadd.setCurrentqty(aQty);
                        stockadd.setUnitCost(aStock.getUnitCost());
                        String SupplierTIN = "";
                        String SupplierName = "";
                        long SupplierId = 0;
                        if (aTrans_type_id == 70) {//PRODUCTION
                            try {
                                SupplierId = new TransProductionBean().getTransProductionById(aTrans_id).getTransactor_id();
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
                            SupplierName = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "PAYEE_NAME").getParameter_value();
                        } else {
                            Transactor tr = new TransactorBean().getTransactor(SupplierId);
                            SupplierTIN = tr.getTaxIdentity();
                            SupplierName = tr.getTransactorNames();
                        }
                        new StockManage().addStockCallThread(stockadd, stockledger.getTax_update_id(), SupplierTIN, SupplierName);
                    } else if (aAddSubtract.equals("Subtract")) {
                        Stock stocksub = new Stock();
                        stocksub.setItemId(aStock.getItemId());
                        stocksub.setCurrentqty(aQty);
                        stocksub.setUnitCost(aStock.getUnitCost());
                        //get AdjustType
                        String AdjustType = "105";//Others
                        new StockManage().subtractStockCallThread(stocksub, stockledger.getTax_update_id(), AdjustType);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("callInsertStock_ledger:" + e.getMessage());
        }
    }

    public void insertStock_ledger(Stock_ledger aStock_ledger) {
        String sql = "INSERT INTO stock_ledger"
                + "(store_id,item_id,batchno,code_specific,desc_specific,specific_size,"
                + "qty_added,qty_subtracted,transaction_type_id,action_type,transaction_id,user_detail_id,add_date,qty_bal,"
                + "tax_update_id,tax_is_updated,tax_update_synced)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("insertStock_ledger:" + e.getMessage());
        }
    }

    public int updateTaxStock_ledger(long aTax_update_id, int aTax_is_updated, int aTax_update_synced) {
        int update_flag = 0;
        String sql = "UPDATE stock_ledger SET tax_is_updated=" + aTax_is_updated + ",tax_update_synced=" + aTax_update_synced + " WHERE tax_update_id=" + aTax_update_id;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate();
            update_flag = 1;
        } catch (Exception e) {
            System.err.println("updateTaxStock_ledger:" + e.getMessage());
        }
        return update_flag;
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
        this.reportStock_ledgerSummary(aStock_ledger, aStock_ledgerBean, aItem);
        this.reportStock_ledgerDetail(aStock_ledger, aStock_ledgerBean, aItem);
    }

    public void reportStock_ledgerDetail(Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
        aStock_ledgerBean.setActionMessage("");
        ResultSet rs = null;
        this.Stock_ledgerList = new ArrayList<>();
        String sql = "SELECT l.*,i.description,un.unit_symbol,tt.transaction_type_name,us.user_name,s.store_name "
                + "FROM stock_ledger l "
                + "INNER JOIN item i ON l.item_id=i.item_id "
                + "INNER JOIN unit un ON i.unit_id=un.unit_id "
                + "INNER JOIN transaction_type tt ON l.transaction_type_id=tt.transaction_type_id "
                + "INNER JOIN user_detail us ON l.user_detail_id=us.user_detail_id "
                + "INNER JOIN store s ON l.store_id=s.store_id "
                + "WHERE 1=1";
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
        ordersql = " ORDER BY l.add_date DESC,l.stock_ledger_id DESC";
        sql = sql + wheresql + ordersql;
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
            System.err.println("reportStock_ledgerDetail:" + e.getMessage());
        }
    }

    public void reportStock_ledgerSummary(Stock_ledger aStock_ledger, Stock_ledgerBean aStock_ledgerBean, Item aItem) {
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
                + "(select case when l1.qty_added>0 then l1.qty_bal-l1.qty_added when l1.qty_subtracted>0 then l1.qty_bal+l1.qty_subtracted else 0 end from stock_ledger l1 where l1.stock_ledger_id=t.min_id) as qty_open,"
                + "(select l2.qty_bal from stock_ledger l2 where l2.stock_ledger_id=t.max_id) as qty_close "
                + "from "
                + "("
                + "select l.item_id,l.batchno,l.code_specific,l.desc_specific,"
                + "min(l.stock_ledger_id) as min_id,"
                + "max(l.stock_ledger_id) as max_id "
                + "from stock_ledger l  "
                + "where 1=1 " + wheresql + " "
                + "group by l.item_id,l.batchno,l.code_specific,l.desc_specific "
                + ") as t "
                + "inner join item i on t.item_id=i.item_id "
                + "inner join unit u on i.unit_id=u.unit_id "
                + "order by i.description;";
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
                sl.setQty_open(rs.getDouble("qty_open"));
                sl.setQty_close(rs.getDouble("qty_close"));
                String whereinner = " AND l.item_id=" + sl.getItem_id() + " AND l.batchno='" + sl.getBatchno() + "' AND l.code_specific='" + sl.getCode_specific() + "' AND l.desc_specific='" + sl.getDesc_specific() + "' " + wheredate;
                sl.setStock_ledgerList(this.getStock_ledgerInner(whereinner));
                this.Stock_ledgerSummary.add(sl);
            }
        } catch (Exception e) {
            System.err.println("reportStock_ledgerSummary:" + e.getMessage());
        }
    }

    public List<Stock_ledger> getStock_ledgerInner(String aWhereSql) {
        ResultSet rs = null;
        List<Stock_ledger> sll = new ArrayList<>();
        String wheresql = "select "
                + "sm.*,tt.transaction_type_name from "
                + "("
                + "select "
                + "l.transaction_type_id,"
                + "sum(l.qty_added) as qty_added,"
                + "sum(l.qty_subtracted) as qty_subtracted  "
                + "from stock_ledger l "
                + "where 1=1 " + aWhereSql + " "
                + "group by l.transaction_type_id"
                + ") as sm "
                + "inner join transaction_type tt on sm.transaction_type_id=tt.transaction_type_id "
                + "order by tt.transaction_type_name";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(wheresql);) {
            rs = ps.executeQuery();
            Stock_ledger sl = null;
            while (rs.next()) {
                sl = new Stock_ledger();
                sl.setTransaction_type_name(rs.getString("transaction_type_name"));
                sl.setQty_subtracted(rs.getDouble("qty_subtracted"));
                sl.setQty_added(rs.getDouble("qty_added"));
                sll.add(sl);
            }
        } catch (Exception e) {
            System.err.println("getStock_ledgerInner:" + e.getMessage());
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
}
