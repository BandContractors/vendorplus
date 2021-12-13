package beans;

import connections.DBConnection;
import entities.Stocktake_session;
import entities.CompanySetting;
import entities.Stocktake_session_item;
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
                    msg = "There is an Open Session Running for Store ##: " + store.getStoreName();
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
                    //this.refreshStock_take_sessionList(store.getStoreId());
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

    public int updateStock_take_session(long aStock_take_session_id, double aStock_items_counted, Date aLast_update_date, String aLast_update_by) {
        int updated = 0;
        String sql = null;
        sql = "UPDATE stock_take_session SET stock_items_counted=?,last_update_date=?,last_update_by=? WHERE stock_take_session_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aStock_take_session_id > 0) {
                ps.setDouble(1, aStock_items_counted);
                try {
                    ps.setTimestamp(2, new java.sql.Timestamp(aLast_update_date.getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(2, null);
                }
                ps.setString(3, aLast_update_by);
                ps.setLong(4, aStock_take_session_id);
                ps.executeUpdate();
                updated = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return updated;
    }

    public void closeStock_take_sessionCall(Stocktake_session aStocktake_session) {
        String msg = "";
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            if (null != aStocktake_session) {
                if (aStocktake_session.getStock_take_session_id() > 0 && aStocktake_session.getIs_closed() == 0) {
                    Date dt = new CompanySetting().getCURRENT_SERVER_DATE();
                    UserDetail userdetail = new GeneralUserSetting().getCurrentUser();
                    long counted = new UtilityBean().getN("select count(*) as n from stock_take_session_item where stock_take_session_id=" + aStocktake_session.getStock_take_session_id());
                    int x = this.closeStock_take_session(aStocktake_session.getStock_take_session_id(), dt, 1, dt, userdetail.getUserName(), counted);
                    if (x == 1) {
                        msg = "Closed Successfully";
                    } else {
                        msg = "An Error has Occured During the Closing Process";
                    }
                    if (msg.length() > 0) {
                        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int closeStock_take_session(long aStock_take_session_id, Date aEnd_time, int aIs_closed, Date aLast_update_date, String aLast_update_by, long aCounted) {
        int updated = 0;
        String sql = null;
        sql = "UPDATE stock_take_session SET end_time=?,is_closed=?,last_update_date=?,last_update_by=?,stock_items_counted=? WHERE stock_take_session_id=?";
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
                try {
                    ps.setTimestamp(3, new java.sql.Timestamp(aLast_update_date.getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(3, null);
                }
                ps.setString(4, aLast_update_by);
                ps.setLong(5, aCounted);
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

    public List<Stocktake_session> getStock_take_sessionList(int aStoreId, int aLimit) {
        List<Stocktake_session> ssList = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM stock_take_session WHERE store_id=" + aStoreId + " ORDER BY add_date DESC LIMIT " + aLimit;
        ResultSet rs = null;
        Stocktake_session ss = null;
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

    public void getStock_take_session_itemList(Stock_take_sessionBean aStock_take_sessionBean, List<Stocktake_session_item> aStocktake_session_itemList, int aFlag) {//aFlag:0 All,1:Uncounted,2:Counted
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        try {
            if (aStock_take_sessionBean.getStock_take_session_id() == 0) {
                msg = "Select Stock Take Session";
            } else if (aStock_take_sessionBean.getCategory_id() == 0 && aStock_take_sessionBean.getItem_id() == 0) {
                msg = "Select Category or Item";
            }
        } catch (Exception e) {
            //do nothing
        }
        ResultSet rs = null;
        aStocktake_session_itemList.clear();
        Stock_take_session_itemBean stB = new Stock_take_session_itemBean();
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "select i.*,ifnull(s.batchno,'') batchno,ifnull(s.code_specific,'') code_specific,ifnull(s.desc_specific,'') desc_specific,ifnull(s.specific_size,1) specific_size,ifnull(s.currentqty,0) currentqty,ifnull(s.unit_cost,i.unit_cost_price) unit_cost from item i left join stock s on i.item_id=s.item_id AND s.store_id=" + new GeneralUserSetting().getCurrentStore().getStoreId() + " WHERE i.is_track=1 AND i.is_suspended='No' ";
            String wheresql = "";
            String ordersql = "";
            if (aStock_take_sessionBean.getItem_id() > 0) {
                wheresql = wheresql + " AND i.item_id=" + aStock_take_sessionBean.getItem_id();
            }
            if (aStock_take_sessionBean.getCategory_id() > 0) {
                wheresql = wheresql + " AND i.category_id=" + aStock_take_sessionBean.getCategory_id();
            }
            if (aStock_take_sessionBean.getSubcategory_id() > 0) {
                wheresql = wheresql + " AND i.sub_category_id=" + aStock_take_sessionBean.getSubcategory_id();
            }
            ordersql = " ORDER BY i.description,batchno,code_specific,desc_specific";
            sql = sql + wheresql + ordersql;
            //System.out.println("sql:" + sql);
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                Stocktake_session_item obj = null;
                while (rs.next()) {
                    obj = stB.getStock_take_session_item(aStock_take_sessionBean.getStock_take_session_id(), rs.getLong("item_id"), rs.getString("batchno"), rs.getString("code_specific"), rs.getString("desc_specific"), rs.getDouble("specific_size"));
                    if (obj == null && (aFlag == 0 || aFlag == 1)) {//not stock taken.
                        obj = new Stocktake_session_item();
                        obj.setStock_take_session_item_id(0);
                        obj.setStock_take_session_id(aStock_take_sessionBean.getStock_take_session_id());
                        obj.setAdd_date(null);
                        obj.setAdd_by("");
                        try {
                            obj.setItem_id(rs.getLong("item_id"));
                        } catch (NullPointerException npe) {
                            obj.setItem_id(0);
                        }
                        try {
                            obj.setBatchno(rs.getString("batchno"));
                        } catch (NullPointerException npe) {
                            obj.setBatchno("");
                        }
                        try {
                            obj.setCode_specific(rs.getString("code_specific"));
                        } catch (NullPointerException npe) {
                            obj.setCode_specific("");
                        }
                        try {
                            obj.setDesc_specific(rs.getString("desc_specific"));
                        } catch (NullPointerException npe) {
                            obj.setDesc_specific("");
                        }
                        try {
                            obj.setSpecific_size(rs.getDouble("specific_size"));
                        } catch (NullPointerException npe) {
                            obj.setSpecific_size(1);
                        }
                        try {
                            obj.setQty_system(rs.getDouble("currentqty"));
                        } catch (NullPointerException npe) {
                            obj.setQty_system(0);
                        }
                        obj.setQty_physical(0);
                        if (obj.getQty_system() > 0) {
                            obj.setQty_short(obj.getQty_system() - obj.getQty_physical());
                        } else {
                            obj.setQty_over(0);
                        }
                        try {
                            obj.setUnit_cost(rs.getDouble("unit_cost"));
                        } catch (NullPointerException npe) {
                            obj.setUnit_cost(0);
                        }
                        obj.setQty_diff_adjusted(0);
                        obj.setNotes("");
                        obj.setDescription(rs.getString("description"));
                    } else if (null != obj && (aFlag == 0 || aFlag == 2)) {//stock taken
                        obj.setDescription(rs.getString("description"));
                    }
                    if (null != obj) {
                        aStocktake_session_itemList.add(obj);
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
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
