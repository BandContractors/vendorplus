package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.Stock;
import entities.Stock_ledger;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
@ManagedBean(name = "stock_ledgerBean")
@SessionScoped
public class Stock_ledgerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Stock_ledger> Stock_ledgerObjectList;
    private Stock_ledger Stock_ledgerObj;

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
            //insert
            this.insertStock_ledger(stockledger);
        } catch (Exception e) {
            System.err.println("callInsertStock_ledger:" + e.getMessage());
        }
    }

    public void insertStock_ledger(Stock_ledger aStock_ledger) {
        String sql = "INSERT INTO stock_ledger"
                + "(store_id,item_id,batchno,code_specific,desc_specific,specific_size,"
                + "qty_added,qty_subtracted,transaction_type_id,action_type,transaction_id,user_detail_id,add_date)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("insertStock_ledger:" + e.getMessage());
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
        }
    }

    /**
     * @return the Stock_ledgerObjectList
     */
    public List<Stock_ledger> getStock_ledgerObjectList() {
        return Stock_ledgerObjectList;
    }

    /**
     * @param Stock_ledgerObjectList the Stock_ledgerObjectList to set
     */
    public void setStock_ledgerObjectList(List<Stock_ledger> Stock_ledgerObjectList) {
        this.Stock_ledgerObjectList = Stock_ledgerObjectList;
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
}
