package beans;

import connections.DBConnection;
import entities.Stock_take_session_item;
import entities.CompanySetting;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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
    private List<Stock_take_session_item> Stock_take_session_itemObjectList;
    private Stock_take_session_item Stock_take_session_itemObj;

    public void setStock_take_session_itemFromResultset(Stock_take_session_item aStock_take_session_item, ResultSet aResultSet) {
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
                aStock_take_session_item.setSpecific_size(0);
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

    public Stock_take_session_item getStock_take_session_item(long aStock_take_session_item_id) {
        String sql = "SELECT * FROM stock_take_session_item WHERE stock_take_session_item_id=" + aStock_take_session_item_id;
        ResultSet rs = null;
        Stock_take_session_item obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stock_take_session_item();
                this.setStock_take_session_itemFromResultset(obj, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return obj;
    }

    public long insertStock_take_session_item(Stock_take_session_item aStock_take_session_item) {
        long InsertedId = 0;
        String sql = null;
        sql = "INSERT INTO stock_take_session_item("
                + "stock_take_session_id,add_date,add_by,item_id,batchno,"
                + "code_specific,desc_specific,specific_size,qty_system,qty_physical,"
                + "qty_short,qty_over,unit_cost,qty_diff_adjusted,notes) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
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
    public void clearStock_take_session_item(Stock_take_session_item aStock_take_session_item) {
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
    public List<Stock_take_session_item> getStock_take_session_itemObjectList() {
        return Stock_take_session_itemObjectList;
    }

    /**
     * @param Stock_take_session_itemObjectList the
     * Stock_take_session_itemObjectList to set
     */
    public void setStock_take_session_itemObjectList(List<Stock_take_session_item> Stock_take_session_itemObjectList) {
        this.Stock_take_session_itemObjectList = Stock_take_session_itemObjectList;
    }

    /**
     * @return the Stock_take_session_itemObj
     */
    public Stock_take_session_item getStock_take_session_itemObj() {
        return Stock_take_session_itemObj;
    }

    /**
     * @param Stock_take_session_itemObj the Stock_take_session_itemObj to set
     */
    public void setStock_take_session_itemObj(Stock_take_session_item Stock_take_session_itemObj) {
        this.Stock_take_session_itemObj = Stock_take_session_itemObj;
    }
}
