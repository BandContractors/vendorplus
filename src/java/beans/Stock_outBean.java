package beans;

import connections.DBConnection;
import entities.Stock_out;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "stock_outBean")
@SessionScoped
public class Stock_outBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Stock_out> Stock_outs = new ArrayList<>();
    private Stock_out Stock_outObject = new Stock_out();

    public void setStock_outFromResultset(Stock_out aStock_out, ResultSet aResultSet) {
        try {
            try {
                aStock_out.setStock_out_id(aResultSet.getLong("stock_out_id"));
            } catch (NullPointerException npe) {
                aStock_out.setStock_out_id(0);
            }
            try {
                aStock_out.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                aStock_out.setTransactor_id(0);
            }
            try {
                aStock_out.setItem_id(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aStock_out.setItem_id(0);
            }
            try {
                aStock_out.setSite_id(aResultSet.getLong("site_id"));
            } catch (NullPointerException npe) {
                aStock_out.setSite_id(0);
            }
            try {
                aStock_out.setQty_out(aResultSet.getDouble("qty_out"));
            } catch (NullPointerException npe) {
                aStock_out.setQty_out(0);
            }
            try {
                aStock_out.setStore_id(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aStock_out.setStore_id(0);
            }
            try {
                aStock_out.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                aStock_out.setBatchno("");
            }
            try {
                aStock_out.setCode_specific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                aStock_out.setCode_specific("");
            }
            try {
                aStock_out.setDesc_specific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                aStock_out.setDesc_specific("");
            }
            try {
                aStock_out.setTransaction_id(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                aStock_out.setTransaction_id(0);
            }
        } catch (SQLException se) {
            System.err.println("setStock_outFromResultset:" + se.getMessage());
        }
    }

    public int InsertOrUpdateStock_out(Stock_out aStock_out, String aUpdateType) {
        int status = 0;
        String sqlinsert = "";
        String sqlupdate = "";
        String sql = "";
        Stock_out stockout = this.getStock_outUniqueRecord(aStock_out);
        if (null == stockout || stockout.getStock_out_id() == 0) {
            //Insert
            sqlinsert = "INSERT INTO stock_out(store_id,item_id,batchno,code_specific,desc_specific,transactor_id,site_id,qty_out,transaction_id) VALUES(?,?,?,?,?,?,?,?,?)";
        } else {
            switch (aUpdateType) {
                case "Add":
                    sqlupdate = "UPDATE stock_out SET qty_out=qty_out+? WHERE stock_out_id=?";
                    break;
                case "Subtract":
                    sqlupdate = "UPDATE stock_out SET qty_out=qty_out-? WHERE stock_out_id=?";
                    break;
            }
        }
        if (sqlinsert.length() > 0) {
            sql = sqlinsert;
        } else if (sqlupdate.length() > 0) {
            sql = sqlupdate;
        } else {
            sql = "";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (sqlinsert.length() > 0) {
                //parameters for insert
                ps.setInt(1, aStock_out.getStore_id());
                ps.setLong(2, aStock_out.getItem_id());
                ps.setString(3, aStock_out.getBatchno());
                ps.setString(4, aStock_out.getCode_specific());
                ps.setString(5, aStock_out.getDesc_specific());
                ps.setLong(6, aStock_out.getTransactor_id());
                ps.setLong(7, aStock_out.getSite_id());
                ps.setDouble(8, aStock_out.getQty_out());
                ps.setLong(9, aStock_out.getTransaction_id());
                ps.executeUpdate();
                status = 1;
                Stock_outBean.deleteZeroQtyStock_out();
            } else if (sqlupdate.length() > 0) {
                //parameters for update
                ps.setDouble(1, aStock_out.getQty_out());
                ps.setLong(2, stockout.getStock_out_id());
                ps.executeUpdate();
                status = 1;
                Stock_outBean.deleteZeroQtyStock_out();
            } else {
                //do nothing
            }
        } catch (SQLException se) {
            System.err.println("InsertOrUpdateStock_out:" + se.getMessage());
            status = 0;
        }
        return status;
    }

    public int UpdateNoInsertStock_out(Stock_out aStock_out, String aUpdateType) {
        int status = 0;
        String sqlinsert = "";
        String sqlupdate = "";
        String sql = "";
        Stock_out stockout = this.getStock_outUniqueRecord(aStock_out);
        if (null == stockout || stockout.getStock_out_id() == 0) {
            //Insert
            sqlinsert = "";//do nothing
        } else {
            switch (aUpdateType) {
                case "Add":
                    sqlupdate = "UPDATE stock_out SET qty_out=qty_out+? WHERE stock_out_id=?";
                    break;
                case "Subtract":
                    sqlupdate = "UPDATE stock_out SET qty_out=qty_out-? WHERE stock_out_id=?";
                    break;
            }
        }
        if (sqlinsert.length() > 0) {
            sql = sqlinsert;
        } else if (sqlupdate.length() > 0) {
            sql = sqlupdate;
        } else {
            sql = "";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (sqlinsert.length() > 0) {
                //parameters for insert
                ps.setInt(1, aStock_out.getStore_id());
                ps.setLong(2, aStock_out.getItem_id());
                ps.setString(3, aStock_out.getBatchno());
                ps.setString(4, aStock_out.getCode_specific());
                ps.setString(5, aStock_out.getDesc_specific());
                ps.setLong(6, aStock_out.getTransactor_id());
                ps.setLong(7, aStock_out.getSite_id());
                ps.setDouble(8, aStock_out.getQty_out());
                ps.setLong(9, aStock_out.getTransaction_id());
                ps.executeUpdate();
                status = 1;
                Stock_outBean.deleteZeroQtyStock_out();
            } else if (sqlupdate.length() > 0) {
                //parameters for update
                ps.setDouble(1, aStock_out.getQty_out());
                ps.setLong(2, stockout.getStock_out_id());
                ps.executeUpdate();
                status = 1;
                Stock_outBean.deleteZeroQtyStock_out();
            } else {
                //do nothing
            }
        } catch (SQLException se) {
            System.err.println("UpdateNoInsertStock_out:" + se.getMessage());
            status = 0;
        }
        return status;
    }

    public Stock_out getStock_outById(long aStock_out_id) {
        String sql;
        sql = "SELECT * FROM stock_out WHERE stock_out_id=" + aStock_out_id;
        ResultSet rs = null;
        Stock_out obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stock_out();
                this.setStock_outFromResultset(obj, rs);
            }
        } catch (SQLException se) {
            System.err.println("getStock_outById:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock_outById:" + ex.getMessage());
                }
            }
        }
        return obj;
    }

    public static void deleteZeroQtyStock_out() {
        String sql = "DELETE FROM stock_out WHERE stock_out_id>0 AND qty_out=0";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate();
        } catch (SQLException se) {
            System.err.println("deleteZeroQtyStock_out:" + se.getMessage());
        }
    }

    public Stock_out getStock_outByStockSite(long aStock_out_id, long aSite_id) {
        String sql;
        sql = "SELECT * FROM stock_out WHERE stock_out_id=" + aStock_out_id + " and site_id=" + aSite_id;
        ResultSet rs = null;
        Stock_out obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stock_out();
                this.setStock_outFromResultset(obj, rs);
            }
        } catch (SQLException se) {
            System.err.println("getStock_outByStockSite:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock_outByStockSite:" + ex.getMessage());
                }
            }
        }
        return obj;
    }

    public Stock_out getStock_outUniqueRecord(Stock_out aStock_out) {
        String sql;
        sql = "SELECT * FROM stock_out WHERE store_id=" + aStock_out.getStore_id() + " and item_id=" + aStock_out.getItem_id()
                + " and batchno='" + aStock_out.getBatchno() + "' and code_specific='" + aStock_out.getCode_specific() + "' and desc_specific='" + aStock_out.getDesc_specific()
                + "' and site_id=" + aStock_out.getSite_id() + " and transactor_id=" + aStock_out.getTransactor_id() + " and transaction_id=" + aStock_out.getTransaction_id();
        ResultSet rs = null;
        Stock_out obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stock_out();
                this.setStock_outFromResultset(obj, rs);
            }
        } catch (SQLException se) {
            System.err.println("getStock_outUniqueRecord:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock_outUniqueRecord:" + ex.getMessage());
                }
            }
        }
        return obj;
    }

    public Stock_out getStock_outByItemSpecifics(long aItem_id, String aBatchno, String aCode_specific, String aDesc_specific, long aSite_id) {
        String sql;
        sql = "SELECT * FROM stock_out so "
                + "INNER JOIN stock s ON so.item_id=s.item_id "
                + "WHERE s.item_id=" + aItem_id + " and batchno='" + aBatchno + "' and code_specific='" + aCode_specific + "' and desc_specific='" + aDesc_specific + "' and site_id=" + aSite_id;
        ResultSet rs = null;
        Stock_out obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stock_out();
                this.setStock_outFromResultset(obj, rs);
            }
        } catch (SQLException se) {
            System.err.println("getStock_outByItemSpecifics:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock_outByItemSpecifics:" + ex.getMessage());
                }
            }
        }
        return obj;
    }

    public double getStock_outTotal(int aStoreId, long aItem_id, String aBatchno, String aCode_specific, String aDesc_specific) {
        String sql;
        double stocko = 0;
        sql = "SELECT sum(qty_out) as qty_out FROM stock_out "
                + "WHERE store_id=" + aStoreId + " and item_id=" + aItem_id + " and batchno='" + aBatchno + "' and code_specific='" + aCode_specific + "' and desc_specific='" + aDesc_specific + "'";
        ResultSet rs = null;
        Stock_out obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    stocko = rs.getDouble("qty_out");
                } catch (NullPointerException npe) {
                    stocko = 0;
                }
            }
        } catch (SQLException se) {
            System.err.println("getStock_outTotal:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock_outTotal:" + ex.getMessage());
                }
            }
        }
        return stocko;
    }

    public List<Stock_out> getStock_outsByTransactor(long aTransactor_id) {
        String sql;
        sql = "SELECT * FROM stock_out WHERE transactor_id=" + aTransactor_id;
        ResultSet rs = null;
        List<Stock_out> objs = new ArrayList<Stock_out>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Stock_out obj = new Stock_out();
                this.setStock_outFromResultset(obj, rs);
                objs.add(obj);
            }
        } catch (SQLException se) {
            System.err.println("getStock_outsByTransactor:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock_outsByTransactor:" + ex.getMessage());
                }
            }
        }
        return objs;
    }

    public List<Stock_out> getStock_outsByItem(long aItem_id) {
        String sql;
        sql = "SELECT * FROM stock_out WHERE item_id=" + aItem_id;
        ResultSet rs = null;
        List<Stock_out> objs = new ArrayList<Stock_out>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Stock_out obj = new Stock_out();
                this.setStock_outFromResultset(obj, rs);
                objs.add(obj);
            }
        } catch (SQLException se) {
            System.err.println("getStock_outsByItem:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getStock_outsByItem:" + ex.getMessage());
                }
            }
        }
        return objs;
    }

    public void initClearStock_out(Stock_out aStock_out) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            if (aStock_out != null) {
                this.clearStock_out(aStock_out);
            }
        }
    }

    public void clearStock_out(Stock_out aStock_out) {
        if (null != aStock_out) {

        }
    }

    public void copyStock_out(Stock_out aFrom, Stock_out aTo) {

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
     * @return the Stock_outs
     */
    public List<Stock_out> getStock_outs() {
        return Stock_outs;
    }

    /**
     * @param Stock_outs the Stock_outs to set
     */
    public void setStock_outs(List<Stock_out> Stock_outs) {
        this.Stock_outs = Stock_outs;
    }

    /**
     * @return the Stock_outObject
     */
    public Stock_out getStock_outObject() {
        return Stock_outObject;
    }

    /**
     * @param Stock_outObject the Stock_outObject to set
     */
    public void setStock_outObject(Stock_out Stock_outObject) {
        this.Stock_outObject = Stock_outObject;
    }
}
