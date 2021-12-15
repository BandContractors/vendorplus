package beans;

import connections.DBConnection;
import entities.Item;
import entities.Stock;
import entities.Stocktake_session_item;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "stock_take_session_itemBean")
@SessionScoped
public class Stock_take_session_itemBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Stock_take_session_itemBean.class.getName());

    private String ActionMessage = null;
    private List<Stocktake_session_item> Stock_take_session_itemObjectList = new ArrayList<>();
    private Stocktake_session_item Stock_take_session_itemObj;

    public void setStock_take_session_itemFromResultset(Stocktake_session_item aStock_take_session_item, ResultSet aResultSet) {
        try {
            try {
                aStock_take_session_item.setStock_take_session_item_id(aResultSet.getLong("stock_take_session_item_id"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setStock_take_session_item_id(0);
            }
            try {
                aStock_take_session_item.setStock_take_session_id(aResultSet.getLong("stock_take_session_id"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setStock_take_session_id(0);
            }
            try {
                aStock_take_session_item.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setAdd_date(null);
            }
            try {
                aStock_take_session_item.setAdd_by(aResultSet.getString("add_by"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setAdd_by("");
            }
            try {
                aStock_take_session_item.setItem_id(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setItem_id(0);
            }
            try {
                aStock_take_session_item.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setBatchno("");
            }
            try {
                aStock_take_session_item.setCode_specific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setCode_specific("");
            }
            try {
                aStock_take_session_item.setDesc_specific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setDesc_specific("");
            }
            try {
                aStock_take_session_item.setSpecific_size(aResultSet.getDouble("specific_size"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setSpecific_size(1);
            }
            try {
                aStock_take_session_item.setQty_system(aResultSet.getDouble("qty_system"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setQty_system(0);
            }
            try {
                aStock_take_session_item.setQty_physical(aResultSet.getDouble("qty_physical"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setQty_physical(0);
            }
            try {
                aStock_take_session_item.setQty_short(aResultSet.getDouble("qty_short"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setQty_short(0);
            }
            try {
                aStock_take_session_item.setQty_over(aResultSet.getDouble("qty_over"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setQty_over(0);
            }
            try {
                aStock_take_session_item.setUnit_cost(aResultSet.getDouble("unit_cost"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setUnit_cost(0);
            }
            try {
                aStock_take_session_item.setQty_diff_adjusted(aResultSet.getInt("qty_diff_adjusted"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setQty_diff_adjusted(0);
            }
            try {
                aStock_take_session_item.setNotes(aResultSet.getString("notes"));
            } catch (NullPointerException npe) {
                aStock_take_session_item.setNotes("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public Stocktake_session_item getStock_take_session_item(long aStock_take_session_item_id) {
        String sql = "SELECT * FROM stock_take_session_item WHERE stock_take_session_item_id=" + aStock_take_session_item_id;
        ResultSet rs = null;
        Stocktake_session_item obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stocktake_session_item();
                this.setStock_take_session_itemFromResultset(obj, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return obj;
    }

    public Stocktake_session_item getStock_take_session_item(long aStock_take_session_id, long aItem_id, String aBatchno, String aCode_specific, String aDesc_specific, double aSpecific_size) {
        String sql = "SELECT * FROM stock_take_session_item WHERE stock_take_session_id=? AND item_id=? AND batchno=? AND code_specific=? AND desc_specific=? AND specific_size=?";
        ResultSet rs = null;
        Stocktake_session_item obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aStock_take_session_id);
            ps.setLong(2, aItem_id);
            ps.setString(3, aBatchno);
            ps.setString(4, aCode_specific);
            ps.setString(5, aDesc_specific);
            ps.setDouble(6, aSpecific_size);
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stocktake_session_item();
                this.setStock_take_session_itemFromResultset(obj, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return obj;
    }

    public long insertStock_take_session_item(Stocktake_session_item aStock_take_session_item) {
        long InsertedId = 0;
        String sql = null;
        sql = "INSERT INTO stock_take_session_item("
                + "stock_take_session_id,add_date,add_by,item_id,batchno,"
                + "code_specific,desc_specific,specific_size,qty_system,qty_physical,"
                + "qty_short,qty_over,unit_cost,qty_diff_adjusted,notes) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            if (aStock_take_session_item.getStock_take_session_item_id() == 0) {
                ps.setLong(1, aStock_take_session_item.getStock_take_session_id());
                try {
                    ps.setTimestamp(2, new java.sql.Timestamp(aStock_take_session_item.getAdd_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(2, null);
                }
                ps.setString(3, aStock_take_session_item.getAdd_by());
                ps.setLong(4, aStock_take_session_item.getItem_id());
                ps.setString(5, aStock_take_session_item.getBatchno());
                ps.setString(6, aStock_take_session_item.getCode_specific());
                ps.setString(7, aStock_take_session_item.getDesc_specific());
                ps.setDouble(8, aStock_take_session_item.getSpecific_size());
                ps.setDouble(9, aStock_take_session_item.getQty_system());
                ps.setDouble(10, aStock_take_session_item.getQty_physical());
                ps.setDouble(11, aStock_take_session_item.getQty_short());
                ps.setDouble(12, aStock_take_session_item.getQty_over());
                ps.setDouble(13, aStock_take_session_item.getUnit_cost());
                ps.setInt(14, aStock_take_session_item.getQty_diff_adjusted());
                ps.setString(15, aStock_take_session_item.getNotes());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    InsertedId = rs.getLong(1);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return InsertedId;
    }

//    public int updateStock_take_session(long aStock_take_session_id, double aStock_items_counted, String aNotes, Date aLast_update_date, String aLast_update_by) {
//        int updated = 0;
//        String sql = null;
//        sql = "UPDATE stock_take_session_item SET stock_items_counted=?,notes=?,last_update_date=?,last_update_by=? WHERE stock_take_session_item_id=?";
//        try (
//                Connection conn = DBConnection.getMySQLConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);) {
//            if (aStock_take_session_id > 0) {
//                ps.setDouble(1, aStock_items_counted);
//                ps.setString(2, aNotes);
//                try {
//                    ps.setTimestamp(3, new java.sql.Timestamp(aLast_update_date.getTime()));
//                } catch (NullPointerException npe) {
//                    ps.setTimestamp(3, null);
//                }
//                ps.setString(4, aLast_update_by);
//                ps.setLong(5, aStock_take_session_id);
//                ps.executeUpdate();
//                updated = 1;
//            }
//        } catch (Exception e) {
//            LOGGER.log(Level.ERROR, e);
//        }
//        return updated;
//    }
    public int updateIsAdjusted(long aStock_take_session_item_id, int aQty_diff_adjusted) {
        int updated = 0;
        String sql = null;
        sql = "UPDATE stock_take_session_item SET qty_diff_adjusted=? WHERE stock_take_session_item_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aStock_take_session_item_id > 0) {
                ps.setInt(1, aQty_diff_adjusted);
                ps.executeUpdate();
                updated = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return updated;
    }

    public int stockAdjust(Stocktake_session_item aStocktake_session_item, int aStore_id, int aTransTypeId, int aTransReasonId) {
        int adjusted = 0;
        try {
            if (null != aStocktake_session_item) {
                StockBean stockbean = new StockBean();
                if (aStocktake_session_item.getQty_over() > 0 || aStocktake_session_item.getQty_short() > 0) {
                    double UnitCostPrice = 0;
                    if (stockbean.getStock(aStore_id, aStocktake_session_item.getItem_id(), aStocktake_session_item.getBatchno(), aStocktake_session_item.getCode_specific(), aStocktake_session_item.getDesc_specific()) != null) {
                        //update
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(aStore_id);
                        stock.setItemId(aStocktake_session_item.getItem_id());
                        stock.setBatchno(aStocktake_session_item.getBatchno());
                        stock.setCodeSpecific(aStocktake_session_item.getCode_specific());
                        stock.setDescSpecific(aStocktake_session_item.getDesc_specific());
                        UnitCostPrice = aStocktake_session_item.getUnit_cost();
                        stock.setUnitCost(UnitCostPrice);
                        stock.setSpecific_size(aStocktake_session_item.getSpecific_size());
                        String TableName = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                        if (aStocktake_session_item.getQty_over() > 0) {
                            i = stockbean.addStock(stock, aStocktake_session_item.getQty_over());
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Add", stock, aStocktake_session_item.getQty_over(), "Add", aTransTypeId, aTransReasonId, new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        } else if (aStocktake_session_item.getQty_short() > 0) {
                            i = new StockBean().subtractStock(stock, aStocktake_session_item.getQty_short());
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Subtract", stock, aStocktake_session_item.getQty_short(), "Add", aTransTypeId, aTransReasonId, new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        }
                        adjusted = 1;
                    } else {
                        //insert
                        Stock stock = new Stock();
                        int i = 0;
                        stock.setStoreId(aStore_id);
                        stock.setItemId(aStocktake_session_item.getItem_id());
                        stock.setBatchno(aStocktake_session_item.getBatchno());
                        stock.setCodeSpecific(aStocktake_session_item.getCode_specific());
                        stock.setDescSpecific(aStocktake_session_item.getDesc_specific());
                        stock.setDescMore("");
                        stock.setItemMnfDate(null);
                        stock.setItemExpDate(null);
                        UnitCostPrice = aStocktake_session_item.getUnit_cost();
                        stock.setUnitCost(UnitCostPrice);
                        stock.setWarrantyDesc("");
                        stock.setWarrantyExpiryDate(null);
                        stock.setPurchaseDate(null);
                        stock.setDepStartDate(null);
                        stock.setDepMethodId(0);
                        stock.setDepRate(0);
                        stock.setAverageMethodId(0);
                        stock.setEffectiveLife(0);
                        Item itm = new ItemBean().getItem(aStocktake_session_item.getItem_id());
                        if (itm.getAssetAccountCode().length() > 0) {
                            stock.setAccountCode(itm.getAssetAccountCode());
                        } else {
                            stock.setAccountCode(itm.getExpenseAccountCode());
                        }
                        stock.setResidualValue(0);
                        stock.setAssetStatusId(1);
                        stock.setAssetStatusDesc("");
                        stock.setSpecific_size(1);
                        String TableName = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "CURRENT_TABLE_NAME_STOCK_LEDGER").getParameter_value();
                        if (aStocktake_session_item.getQty_over() > 0) {
                            stock.setCurrentqty(aStocktake_session_item.getQty_over());
                            i = new StockBean().saveStock(stock);
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Add", stock, aStocktake_session_item.getQty_over(), "Add", aTransTypeId, aTransReasonId, new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        } else if (aStocktake_session_item.getQty_short() > 0) {
                            stock.setCurrentqty(0 - aStocktake_session_item.getQty_short());
                            i = new StockBean().saveStock(stock);
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Subtract", stock, (0 - aStocktake_session_item.getQty_short()), "Add", aTransTypeId, aTransReasonId, new GeneralUserSetting().getCurrentUser().getUserDetailId());
                        }
                        adjusted = 1;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return adjusted;
    }

    public void clearStock_take_session_item(Stocktake_session_item aStock_take_session_item) {
        if (null != aStock_take_session_item) {
            aStock_take_session_item.setStock_take_session_item_id(0);
            aStock_take_session_item.setStock_take_session_id(0);
            aStock_take_session_item.setAdd_date(null);
            aStock_take_session_item.setAdd_by("");
            aStock_take_session_item.setItem_id(0);
            aStock_take_session_item.setBatchno("");
            aStock_take_session_item.setCode_specific("");
            aStock_take_session_item.setDesc_specific("");
            aStock_take_session_item.setSpecific_size(0);
            aStock_take_session_item.setQty_system(0);
            aStock_take_session_item.setQty_physical(0);
            aStock_take_session_item.setQty_short(0);
            aStock_take_session_item.setQty_over(0);
            aStock_take_session_item.setUnit_cost(0);
            aStock_take_session_item.setQty_diff_adjusted(0);
            aStock_take_session_item.setNotes("");
        }
    }

    public void changeQtyCounted(Stocktake_session_item aStocktake_session_item) {
        try {
            aStocktake_session_item.setQty_short(0);
            aStocktake_session_item.setQty_over(0);
            if (aStocktake_session_item.getQty_system() > aStocktake_session_item.getQty_physical()) {
                aStocktake_session_item.setQty_short(aStocktake_session_item.getQty_system() - aStocktake_session_item.getQty_physical());
            } else if (aStocktake_session_item.getQty_physical() > aStocktake_session_item.getQty_system()) {
                aStocktake_session_item.setQty_over(aStocktake_session_item.getQty_physical() - aStocktake_session_item.getQty_system());
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
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
     * @return the Stock_take_session_itemObjectList
     */
    public List<Stocktake_session_item> getStock_take_session_itemObjectList() {
        return Stock_take_session_itemObjectList;
    }

    /**
     * @param Stock_take_session_itemObjectList the
     * Stock_take_session_itemObjectList to set
     */
    public void setStock_take_session_itemObjectList(List<Stocktake_session_item> Stock_take_session_itemObjectList) {
        this.Stock_take_session_itemObjectList = Stock_take_session_itemObjectList;
    }

    /**
     * @return the Stock_take_session_itemObj
     */
    public Stocktake_session_item getStock_take_session_itemObj() {
        return Stock_take_session_itemObj;
    }

    /**
     * @param Stock_take_session_itemObj the Stock_take_session_itemObj to set
     */
    public void setStock_take_session_itemObj(Stocktake_session_item Stock_take_session_itemObj) {
        this.Stock_take_session_itemObj = Stock_take_session_itemObj;
    }
}
