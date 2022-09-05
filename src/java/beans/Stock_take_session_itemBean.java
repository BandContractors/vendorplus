package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.GroupRight;
import entities.Item;
import entities.Stock;
import entities.Stocktake_session;
import entities.Stocktake_session_item;
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
@ManagedBean(name = "stock_take_session_itemBean")
@SessionScoped
public class Stock_take_session_itemBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Stock_take_session_itemBean.class.getName());

    private String ActionMessage = null;
    private List<Stocktake_session_item> Stock_take_session_itemObjectList = new ArrayList<>();
    private Stocktake_session_item Stock_take_session_itemObj;
    private Date Date1;
    private Date Date2;
    private int store_id;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;
    private int ShowFilter;

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
            try {
                aStock_take_session_item.setUnit_symbol(aResultSet.getString("unit_symbol"));
            } catch (Exception e) {
                aStock_take_session_item.setUnit_symbol("");
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
                ps.setLong(2, aStock_take_session_item_id);
                ps.executeUpdate();
                updated = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return updated;
    }

    public void stockAdjustCallFromReport(Stocktake_session_item aStocktake_session_item) {
        String msg = "";
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        try {
            if (null != aStocktake_session_item) {
                UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
                List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
                GroupRightBean grb = new GroupRightBean();
                if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "128", "Add") == 0) {
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, "Not Allowed to Access this Function")));
                } else {
                    Stocktake_session ss = new Stock_take_sessionBean().getStock_take_session(aStocktake_session_item.getStock_take_session_id());
                    if (null != ss) {
                        Date dt = new CompanySetting().getCURRENT_SERVER_DATE();
                        UserDetail userdetail = new GeneralUserSetting().getCurrentUser();
                        aStocktake_session_item.setAdd_date(dt);
                        aStocktake_session_item.setAdd_by(userdetail.getUserName());
                        long saveflag = this.stockAdjust(aStocktake_session_item, ss.getStore_id(), 84, aStocktake_session_item.getStock_take_session_item_id());
                        if (saveflag > 0) {
                            //update is adjusted
                            int adjustflag = this.updateIsAdjusted(aStocktake_session_item.getStock_take_session_item_id(), 1);
                            //refresh report
                            if (adjustflag == 1) {
                                aStocktake_session_item.setQty_diff_adjusted(1);
                            }
                            msg = "Stock Adjusted Successfully";
                        } else {
                            msg = "An Error has Occured During the Saving Process";
                        }
                        if (msg.length() > 0) {
                            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int stockAdjust(Stocktake_session_item aStocktake_session_item, int aStore_id, int aTransTypeId, long aTransItemId) {
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
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Add", stock, aStocktake_session_item.getQty_over(), "Add", aTransTypeId, aStocktake_session_item.getStock_take_session_id(), new GeneralUserSetting().getCurrentUser().getUserDetailId(), aTransItemId);
                        } else if (aStocktake_session_item.getQty_short() > 0) {
                            i = new StockBean().subtractStock(stock, aStocktake_session_item.getQty_short());
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Subtract", stock, aStocktake_session_item.getQty_short(), "Add", aTransTypeId, aStocktake_session_item.getStock_take_session_id(), new GeneralUserSetting().getCurrentUser().getUserDetailId(), aTransItemId);
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
                        if (null != itm.getAssetAccountCode() && itm.getAssetAccountCode().length() > 0) {
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
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Add", stock, aStocktake_session_item.getQty_over(), "Add", aTransTypeId, aStocktake_session_item.getStock_take_session_id(), new GeneralUserSetting().getCurrentUser().getUserDetailId(), aTransItemId);
                        } else if (aStocktake_session_item.getQty_short() > 0) {
                            stock.setCurrentqty(0 - aStocktake_session_item.getQty_short());
                            i = new StockBean().saveStock(stock);
                            new Stock_ledgerBean().callInsertStock_ledger(TableName, "Subtract", stock, (0 - aStocktake_session_item.getQty_short()), "Add", aTransTypeId, aStocktake_session_item.getStock_take_session_id(), new GeneralUserSetting().getCurrentUser().getUserDetailId(), aTransItemId);
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

    public void initResetStockTakeReportDetail(Stocktake_session_item aStocktake_session_item, Stock_take_session_itemBean aSStocktake_session_itemBean, Item aItem) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetStockTakeReportDetail(aStocktake_session_item, aSStocktake_session_itemBean, aItem);
        }
    }

    public void resetStockTakeReportDetail(Stocktake_session_item aStocktake_session_item, Stock_take_session_itemBean aStocktake_session_itemBean, Item aItem) {
        try {
            this.clearStock_take_session_item(aStocktake_session_item);
        } catch (NullPointerException npe) {
        }
        try {
            new ItemBean().clearItem(aItem);
        } catch (NullPointerException npe) {
        }
        try {
            aStocktake_session_itemBean.setDate1(null);
            aStocktake_session_itemBean.setDate2(null);
            //this.setDateToToday();
            aStocktake_session_itemBean.Stock_take_session_itemObjectList.clear();
        } catch (NullPointerException npe) {
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

    public void reportStocktake_session_itemCall(Stocktake_session_item aStocktake_session_item, Stock_take_session_itemBean aStock_take_session_itemBean, Item aItem) {
        try {
            UtilityBean ub = new UtilityBean();
            String BaseName = "language_en";
            try {
                BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
            } catch (Exception e) {
            }
            String msg = "";
            if (aStocktake_session_item.getStock_take_session_id() == 0) {
                msg = "Select Session";
                FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                this.reportStocktake_session_item(aStocktake_session_item, aStock_take_session_itemBean, aItem);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void reportStocktake_session_item(Stocktake_session_item aStocktake_session_item, Stock_take_session_itemBean aStock_take_session_itemBean, Item aItem) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        ResultSet rs = null;
        this.Stock_take_session_itemObjectList = new ArrayList<>();
        String sql = "";
        String wheresql = "";
        String ordersql = "";
        sql = "SELECT i.*,m.description,u.unit_symbol "
                + "FROM stock_take_session_item i "
                + "INNER JOIN stock_take_session s ON i.stock_take_session_id=s.stock_take_session_id "
                + "INNER JOIN item m ON i.item_id=m.item_id "
                + "INNER JOIN unit u ON m.unit_id=u.unit_id "
                + "WHERE 1=1";
        if (aStocktake_session_item.getStock_take_session_id() > 0) {
            wheresql = wheresql + " AND i.stock_take_session_id=" + aStocktake_session_item.getStock_take_session_id();
        }
        if (aStock_take_session_itemBean.getStore_id() > 0) {
            wheresql = wheresql + " AND s.store_id=" + aStock_take_session_itemBean.getStore_id();
        }
        if (aStocktake_session_item.getAdd_by().length() > 0) {
            wheresql = wheresql + " AND i.add_by='" + aStocktake_session_item.getAdd_by() + "'";
        }
        if (aStock_take_session_itemBean.getShowFilter() == 1) {
            wheresql = wheresql + " AND i.qty_diff_adjusted=1";
        }
        if (aStock_take_session_itemBean.getShowFilter() == 2) {
            wheresql = wheresql + " AND i.qty_diff_adjusted=0";
        }
        if (aStock_take_session_itemBean.getDate1() != null && aStock_take_session_itemBean.getDate2() != null) {
            wheresql = wheresql + " AND i.add_date BETWEEN '" + new java.sql.Timestamp(aStock_take_session_itemBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aStock_take_session_itemBean.getDate2().getTime()) + "'";
        }
        ordersql = " ORDER BY i.add_date DESC,i.stock_take_session_id DESC";
        sql = sql + wheresql + ordersql;
        //System.out.println("SQL:" + sql);
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Stocktake_session_item ssi = null;
            while (rs.next()) {
                ssi = new Stocktake_session_item();
                this.setStock_take_session_itemFromResultset(ssi, rs);
                ssi.setDescription(rs.getString("description"));
                this.Stock_take_session_itemObjectList.add(ssi);
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
     * @return the store_id
     */
    public int getStore_id() {
        return store_id;
    }

    /**
     * @param store_id the store_id to set
     */
    public void setStore_id(int store_id) {
        this.store_id = store_id;
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
     * @return the ShowFilter
     */
    public int getShowFilter() {
        return ShowFilter;
    }

    /**
     * @param ShowFilter the ShowFilter to set
     */
    public void setShowFilter(int ShowFilter) {
        this.ShowFilter = ShowFilter;
    }
}
