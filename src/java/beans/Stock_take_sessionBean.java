package beans;

import connections.DBConnection;
import entities.Stocktake_session;
import entities.CompanySetting;
import entities.Store;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean(name = "stock_take_sessionBean")
@SessionScoped
public class Stock_take_sessionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Stock_take_sessionBean.class.getName());

    private String ActionMessage = null;
    private List<Stocktake_session> Stock_take_sessionObjectList;
    private Stocktake_session Stock_take_sessionObj;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private long stock_take_session_id;
    private int category_id;
    private int subcategory_id;
    private long item_id;

    public void setStock_take_sessionFromResultset(Stocktake_session aStock_take_session, ResultSet aResultSet) {
        try {
            try {
                aStock_take_session.setStock_take_session_id(aResultSet.getLong("stock_take_session_id"));
            } catch (NullPointerException npe) {
                aStock_take_session.setStock_take_session_id(0);
            }
            try {
                aStock_take_session.setStore_id(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aStock_take_session.setStore_id(0);
            }
            try {
                aStock_take_session.setAcc_period_id(aResultSet.getInt("acc_period_id"));
            } catch (NullPointerException npe) {
                aStock_take_session.setAcc_period_id(0);
            }
            try {
                aStock_take_session.setNotes(aResultSet.getString("notes"));
            } catch (NullPointerException npe) {
                aStock_take_session.setNotes("");
            }
            try {
                aStock_take_session.setStart_time(new Date(aResultSet.getTimestamp("start_time").getTime()));
            } catch (NullPointerException npe) {
                aStock_take_session.setStart_time(null);
            }
            try {
                aStock_take_session.setEnd_time(new Date(aResultSet.getTimestamp("end_time").getTime()));
            } catch (NullPointerException npe) {
                aStock_take_session.setEnd_time(null);
            }
            try {
                aStock_take_session.setIs_closed(aResultSet.getInt("is_closed"));
            } catch (NullPointerException npe) {
                aStock_take_session.setIs_closed(0);
            }
            try {
                aStock_take_session.setStock_items_available(aResultSet.getDouble("stock_items_available"));
            } catch (NullPointerException npe) {
                aStock_take_session.setStock_items_available(0);
            }
            try {
                aStock_take_session.setStock_items_counted(aResultSet.getDouble("stock_items_counted"));
            } catch (NullPointerException npe) {
                aStock_take_session.setStock_items_counted(0);
            }
            try {
                aStock_take_session.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aStock_take_session.setAdd_date(null);
            }
            try {
                aStock_take_session.setLast_update_date(new Date(aResultSet.getTimestamp("last_update_date").getTime()));
            } catch (NullPointerException npe) {
                aStock_take_session.setLast_update_date(null);
            }
            try {
                aStock_take_session.setAdd_by(aResultSet.getString("add_by"));
            } catch (NullPointerException npe) {
                aStock_take_session.setAdd_by("");
            }
            try {
                aStock_take_session.setLast_update_by(aResultSet.getString("last_update_by"));
            } catch (NullPointerException npe) {
                aStock_take_session.setLast_update_by("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public Date getStartTime(int aHourOfDay, int aMinute) {
        try {
            Date CurrentTime = new CompanySetting().getCURRENT_SERVER_DATE();
            Calendar cal = Calendar.getInstance();
            cal.setTime(CurrentTime);
            cal.set(Calendar.HOUR_OF_DAY, aHourOfDay);
            cal.set(Calendar.MINUTE, aMinute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date StartTime = cal.getTime();
            if (CurrentTime.compareTo(StartTime) > 0) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                StartTime = cal.getTime();
            }
            return StartTime;
        } catch (Exception e) {
            return null;
        }
    }

    public Stocktake_session getStock_take_session(long aStock_take_session_id) {
        String sql = "SELECT * FROM stock_take_session WHERE stock_take_session_id=" + aStock_take_session_id;
        ResultSet rs = null;
        Stocktake_session coa = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                coa = new Stocktake_session();
                this.setStock_take_sessionFromResultset(coa, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return coa;
    }

    public Stocktake_session getOpenStockSession(int aStore_id) {
        String sql = "SELECT * FROM stock_take_session WHERE is_closed=0 AND store_id=" + aStore_id;
        ResultSet rs = null;
        Stocktake_session obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Stocktake_session();
                this.setStock_take_sessionFromResultset(obj, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return obj;
    }

    public boolean isOpenSessionFound(int aStore_id) {
        boolean res = false;
        Date today = new CompanySetting().getCURRENT_SERVER_DATE();
        Stocktake_session obj = this.getOpenStockSession(aStore_id);
        if (null == obj) {
            res = false;
        } else {
            res = true;
        }
        return res;
    }

    public void insertStock_take_sessionCall(Stocktake_session aStock_take_session) {
        String msg = "";
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            Store store = new GeneralUserSetting().getCurrentStore();
            UserDetail userdetail = new GeneralUserSetting().getCurrentUser();
            if (null != aStock_take_session) {
                if (this.isOpenSessionFound(store.getStoreId())) {
                    msg = "There is an Open Session Running for Store ##" + store.getStoreName();
                } else {
                    Date serverdate = new CompanySetting().getCURRENT_SERVER_DATE();
                    aStock_take_session.setStart_time(serverdate);
                    aStock_take_session.setAdd_date(serverdate);
                    aStock_take_session.setStore_id(store.getStoreId());
                    aStock_take_session.setAdd_by(userdetail.getUserName());
                    aStock_take_session.setAcc_period_id(new AccPeriodBean().getAccPeriod(serverdate).getAccPeriodId());
                    aStock_take_session.setStock_items_available(ub.getN("select count(*) as n from stock where store_id=" + store.getStoreId()));
                    long savedid = this.insertStock_take_session(aStock_take_session);
                    if (savedid > 0) {
                        msg = "Saved Successfully";
                    } else {
                        msg = "An Error has Occured During the Saving Process";
                    }
                    this.refreshStock_take_sessionList(store.getStoreId());
                }
            }
            if (msg.length() > 0) {
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public long insertStock_take_session(Stocktake_session aStock_take_session) {
        long InsertedId = 0;
        String sql = null;
        sql = "INSERT INTO stock_take_session(store_id,acc_period_id,notes,start_time,"
                + "end_time,is_closed,stock_items_available,stock_items_counted,"
                + "add_date,add_by,last_update_date,last_update_by) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            if (aStock_take_session.getStock_take_session_id() == 0) {
                ps.setInt(1, aStock_take_session.getStore_id());
                ps.setInt(2, aStock_take_session.getAcc_period_id());
                ps.setString(3, aStock_take_session.getNotes());
                try {
                    ps.setTimestamp(4, new java.sql.Timestamp(aStock_take_session.getStart_time().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(4, null);
                }
                try {
                    ps.setTimestamp(5, null);
                } catch (NullPointerException npe) {
                    ps.setTimestamp(5, null);
                }
                try {
                    ps.setInt(6, 0);
                } catch (NullPointerException npe) {
                    ps.setInt(6, 0);
                }
                ps.setDouble(7, aStock_take_session.getStock_items_available());
                ps.setDouble(8, 0);
                try {
                    ps.setTimestamp(9, new java.sql.Timestamp(aStock_take_session.getAdd_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(9, null);
                }
                ps.setString(10, aStock_take_session.getAdd_by());
                ps.setTimestamp(11, null);
                ps.setString(12, null);
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

    public int updateStock_take_session(long aStock_take_session_id, double aStock_items_counted, String aNotes, Date aLast_update_date, String aLast_update_by) {
        int updated = 0;
        String sql = null;
        sql = "UPDATE stock_take_session SET stock_items_counted=?,notes=?,last_update_date=?,last_update_by=? WHERE stock_take_session_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aStock_take_session_id > 0) {
                ps.setDouble(1, aStock_items_counted);
                ps.setString(2, aNotes);
                try {
                    ps.setTimestamp(3, new java.sql.Timestamp(aLast_update_date.getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(3, null);
                }
                ps.setString(4, aLast_update_by);
                ps.setLong(5, aStock_take_session_id);
                ps.executeUpdate();
                updated = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return updated;
    }

    public int closeStock_take_session(long aStock_take_session_id, Date aEnd_time, int aIs_closed, String aNotes, Date aLast_update_date, String aLast_update_by) {
        int updated = 0;
        String sql = null;
        sql = "UPDATE stock_take_session SET end_time=?,is_closed=?,notes=?,last_update_date=?,last_update_by=? WHERE stock_take_session_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aStock_take_session_id > 0) {
                try {
                    ps.setTimestamp(1, new java.sql.Timestamp(aEnd_time.getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(1, null);
                }
                ps.setInt(2, aIs_closed);
                ps.setString(3, aNotes);
                try {
                    ps.setTimestamp(4, new java.sql.Timestamp(aLast_update_date.getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(4, null);
                }
                ps.setString(5, aLast_update_by);
                ps.setLong(6, aStock_take_session_id);
                ps.executeUpdate();
                updated = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return updated;
    }

    public void clearStock_take_session(Stocktake_session aStock_take_session) {
        if (null != aStock_take_session) {
            aStock_take_session.setStock_take_session_id(0);
            aStock_take_session.setStore_id(0);
            aStock_take_session.setAcc_period_id(0);
            aStock_take_session.setNotes("");
            aStock_take_session.setStart_time(null);
            aStock_take_session.setEnd_time(null);
            aStock_take_session.setIs_closed(0);
            aStock_take_session.setStock_items_available(0);
            aStock_take_session.setStock_items_counted(0);
            aStock_take_session.setAdd_date(null);
            aStock_take_session.setLast_update_date(null);
            aStock_take_session.setAdd_by("");
            aStock_take_session.setLast_update_by("");
        }
    }

    public void refreshStock_take_sessionList(int aStoreId) {
        String sql;
        sql = "SELECT * FROM stock_take_session WHERE store_id=" + aStoreId + " ORDER BY add_date DESC LIMIT 20";
        ResultSet rs = null;
        Stocktake_session ss = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                ss = new Stocktake_session();
                this.setStock_take_sessionFromResultset(ss, rs);
                this.Stock_take_sessionObjectList.add(ss);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Stocktake_session> getOpenStock_take_sessionList() {
        String sql;
        Store store = new GeneralUserSetting().getCurrentStore();
        sql = "SELECT * FROM stock_take_session WHERE is_closed=0 AND store_id=" + store.getStoreId() + " ORDER BY add_date DESC";
        ResultSet rs = null;
        Stocktake_session ss = null;
        List<Stocktake_session> ssList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                ss = new Stocktake_session();
                this.setStock_take_sessionFromResultset(ss, rs);
                ssList.add(ss);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ssList;
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
     * @return the Stock_take_sessionObjectList
     */
    public List<Stocktake_session> getStock_take_sessionObjectList() {
        return Stock_take_sessionObjectList;
    }

    /**
     * @param Stock_take_sessionObjectList the Stock_take_sessionObjectList to
     * set
     */
    public void setStock_take_sessionObjectList(List<Stocktake_session> Stock_take_sessionObjectList) {
        this.Stock_take_sessionObjectList = Stock_take_sessionObjectList;
    }

    /**
     * @return the Stock_take_sessionObj
     */
    public Stocktake_session getStock_take_sessionObj() {
        return Stock_take_sessionObj;
    }

    /**
     * @param Stock_take_sessionObj the Stock_take_sessionObj to set
     */
    public void setStock_take_sessionObj(Stocktake_session Stock_take_sessionObj) {
        this.Stock_take_sessionObj = Stock_take_sessionObj;
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
     * @return the stock_take_session_id
     */
    public long getStock_take_session_id() {
        return stock_take_session_id;
    }

    /**
     * @param stock_take_session_id the stock_take_session_id to set
     */
    public void setStock_take_session_id(long stock_take_session_id) {
        this.stock_take_session_id = stock_take_session_id;
    }

    /**
     * @return the category_id
     */
    public int getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id the category_id to set
     */
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    /**
     * @return the subcategory_id
     */
    public int getSubcategory_id() {
        return subcategory_id;
    }

    /**
     * @param subcategory_id the subcategory_id to set
     */
    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    /**
     * @return the item_id
     */
    public long getItem_id() {
        return item_id;
    }

    /**
     * @param item_id the item_id to set
     */
    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }
}
