package beans;

import connections.DBConnection;
import entities.AccPeriod;
import entities.CompanySetting;
import entities.GroupRight;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean(name = "accPeriodBean")
@SessionScoped
public class AccPeriodBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<AccPeriod> AccPeriodObjectList;
    private String ActionMessage;

    public void setAccPeriodFromResultset(AccPeriod aAccPeriod, ResultSet aResultSet) {
        try {
            try {
                aAccPeriod.setAccPeriodId(aResultSet.getInt("acc_period_id"));
            } catch (NullPointerException npe) {
                aAccPeriod.setAccPeriodId(0);
            }
            try {
                aAccPeriod.setAccPeriodName(aResultSet.getString("acc_period_name"));
            } catch (NullPointerException npe) {
                aAccPeriod.setAccPeriodName("");
            }
            try {
                aAccPeriod.setStartDate(new Date(aResultSet.getTimestamp("start_date").getTime()));
            } catch (NullPointerException npe) {
                aAccPeriod.setStartDate(null);
            }
            try {
                aAccPeriod.setEndDate(new Date(aResultSet.getTimestamp("end_date").getTime()));
            } catch (NullPointerException npe) {
                aAccPeriod.setEndDate(null);
            }
            try {
                aAccPeriod.setIsCurrent(aResultSet.getInt("is_current"));
            } catch (NullPointerException npe) {
                aAccPeriod.setIsCurrent(0);
            }
            try {
                aAccPeriod.setIsActive(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                aAccPeriod.setIsActive(0);
            }
            try {
                aAccPeriod.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (NullPointerException npe) {
                aAccPeriod.setIsDeleted(0);
            }
            try {
                aAccPeriod.setIsOpen(aResultSet.getInt("is_open"));
            } catch (NullPointerException npe) {
                aAccPeriod.setIsOpen(0);
            }
            try {
                aAccPeriod.setIs_reopen(aResultSet.getInt("is_reopen"));
            } catch (NullPointerException npe) {
                aAccPeriod.setIs_reopen(0);
            }
            try {
                aAccPeriod.setIsClosed(aResultSet.getInt("is_closed"));
            } catch (NullPointerException npe) {
                aAccPeriod.setIsClosed(0);
            }
            try {
                aAccPeriod.setOrderNo(aResultSet.getInt("order_no"));
            } catch (NullPointerException npe) {
                aAccPeriod.setOrderNo(0);
            }
            try {
                aAccPeriod.setAddBy(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                aAccPeriod.setAddBy(0);
            }
            try {
                aAccPeriod.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (NullPointerException npe) {
                aAccPeriod.setLastEditBy(0);
            }
            try {
                aAccPeriod.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aAccPeriod.setAddDate(null);
            }
            try {
                aAccPeriod.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (NullPointerException npe) {
                aAccPeriod.setLastEditDate(null);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public int saveAccPeriod(AccPeriod aAccPeriod) {
        int saved = 0;
        String sql = "{call sp_save_acc_period(?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aAccPeriod) {
                try {
                    cs.setInt("in_acc_period_id", aAccPeriod.getAccPeriodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_acc_period_id", 0);
                }
                try {
                    cs.setString("in_acc_period_name", aAccPeriod.getAccPeriodName());
                } catch (NullPointerException npe) {
                    cs.setString("in_acc_period_name", "");
                }
                try {
                    cs.setDate("in_start_date", new java.sql.Date(aAccPeriod.getStartDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_start_date", null);
                }
                try {
                    cs.setDate("in_end_date", new java.sql.Date(aAccPeriod.getEndDate().getTime()));
                } catch (NullPointerException npe) {
                    cs.setDate("in_end_date", null);
                }
                try {
                    cs.setInt("in_order_no", aAccPeriod.getOrderNo());
                } catch (NullPointerException npe) {
                    cs.setInt("in_order_no", 0);
                }
                try {
                    cs.setInt("in_is_current", aAccPeriod.getIsCurrent());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_current", 0);
                }
                try {
                    cs.setInt("in_is_open", aAccPeriod.getIsOpen());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_open", 0);
                }
                try {
                    cs.setInt("in_is_closed", aAccPeriod.getIsClosed());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_closed", 0);
                }
                try {
                    cs.setInt("in_is_active", aAccPeriod.getIsActive());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_active", 0);
                }
                try {
                    cs.setInt("in_is_deleted", aAccPeriod.getIsDeleted());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_deleted", 0);
                }
                try {
                    cs.setInt("in_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_user_detail_id", 0);
                }
                try {
                    cs.setInt("in_is_reopen", aAccPeriod.getIs_reopen());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_reopen", 0);
                }
                cs.executeUpdate();
                saved = 1;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return saved;
    }

    public void saveAccPeriod2(AccPeriod aAccPeriod) {
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (aAccPeriod.getAccPeriodId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "53", "Add") == 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR..."));
        } else if (aAccPeriod.getAccPeriodId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "53", "Edit") == 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR..."));
        } else {
            String sql = "{call sp_save_acc_period(?,?,?,?,?,?,?,?,?,?,?,?)}";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (null != aAccPeriod) {
                    try {
                        cs.setInt("in_acc_period_id", aAccPeriod.getAccPeriodId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_period_id", 0);
                    }
                    try {
                        cs.setString("in_acc_period_name", aAccPeriod.getAccPeriodName());
                    } catch (NullPointerException npe) {
                        cs.setString("in_acc_period_name", "");
                    }
                    try {
                        cs.setDate("in_start_date", new java.sql.Date(aAccPeriod.getStartDate().getTime()));
                    } catch (NullPointerException npe) {
                        cs.setDate("in_start_date", null);
                    }
                    try {
                        cs.setDate("in_end_date", new java.sql.Date(aAccPeriod.getEndDate().getTime()));
                    } catch (NullPointerException npe) {
                        cs.setDate("in_end_date", null);
                    }
                    try {
                        cs.setInt("in_order_no", aAccPeriod.getOrderNo());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_order_no", 0);
                    }
                    try {
                        cs.setInt("in_is_current", aAccPeriod.getIsCurrent());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_current", 0);
                    }
                    try {
                        cs.setInt("in_is_open", aAccPeriod.getIsOpen());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_open", 0);
                    }
                    try {
                        cs.setInt("in_is_reopen", aAccPeriod.getIs_reopen());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_reopen", 0);
                    }
                    try {
                        cs.setInt("in_is_closed", aAccPeriod.getIsClosed());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_closed", 0);
                    }
                    try {
                        cs.setInt("in_is_active", aAccPeriod.getIsActive());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_active", 0);
                    }
                    try {
                        cs.setInt("in_is_deleted", aAccPeriod.getIsDeleted());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_is_deleted", 0);
                    }
                    try {
                        cs.setInt("in_user_detail_id", new GeneralUserSetting().getCurrentUser().getUserDetailId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_user_detail_id", 0);
                    }
                    cs.executeUpdate();
                    this.setActionMessage("Saved Successfully!");
                    this.clearAccPeriod(aAccPeriod);
                    this.arrangeAccPeriodsOrder();
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
    }

    public int updateAccPeriodOrder(int aAccPeriodId, int aOrderNo) {
        int saved = 0;
        String sql = "UPDATE acc_period SET order_no=" + aOrderNo + " WHERE acc_period_id=" + aAccPeriodId;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (aAccPeriodId > 0) {
                cs.executeUpdate();
                saved = 1;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return saved;
    }

    public void closeAccPeriod(AccPeriod aAccPeriod) {
        String msg = "";
        int x = 0;
        String sql = "{call sp_close_account_period(?)}";
        this.saveSnapshotXrate(aAccPeriod);
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aAccPeriod) {
                aAccPeriod.setIsOpen(0);
                aAccPeriod.setIsClosed(1);
                aAccPeriod.setIsCurrent(0);
                x = this.saveAccPeriod(aAccPeriod);
                if (x == 1) {
                    try {
                        cs.setInt("in_acc_period_id", aAccPeriod.getAccPeriodId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_period_id", 0);
                    }
                    cs.executeUpdate();
                    msg = "Accounting Period CLOSED Successfully!";
                    this.setActionMessage(msg);
                    FacesContext.getCurrentInstance().addMessage("Close", new FacesMessage(msg));
                } else {
                    msg = "Accounting Period NOT CLOSED!";
                    this.setActionMessage(msg);
                    FacesContext.getCurrentInstance().addMessage("Close", new FacesMessage(msg));
                }
            }
        } catch (Exception e) {
            msg = "An error occured:" + "closeAccPeriod:" + e.getMessage();
            System.err.println(msg);
            this.setActionMessage(msg);
            FacesContext.getCurrentInstance().addMessage("Close", new FacesMessage(msg));
        }
    }

    public void reOpenAccPeriod(AccPeriod aAccPeriod) {
        String msg = "";
        int x = 0;
        String sql = "{call sp_reopen_account_period(?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aAccPeriod) {
                aAccPeriod.setIsOpen(1);
                aAccPeriod.setIsClosed(0);
                aAccPeriod.setIs_reopen(1);
                //aAccPeriod.setIsCurrent(0);
                x = this.saveAccPeriod(aAccPeriod);
                if (x == 1) {
                    try {
                        cs.setInt("in_acc_period_id", aAccPeriod.getAccPeriodId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_period_id", 0);
                    }
                    cs.executeUpdate();
                    msg = "Accounting Period RE-OPENED Successfully!";
                    this.setActionMessage(msg);
                    FacesContext.getCurrentInstance().addMessage("ReOpen", new FacesMessage(msg));
                } else {
                    msg = "Accounting Period NOT RE-OPENED!";
                    this.setActionMessage(msg);
                    FacesContext.getCurrentInstance().addMessage("ReOpen", new FacesMessage(msg));
                }
            }
        } catch (Exception e) {
            msg = "An error occured:" + "reOpenAccPeriod:" + e.getMessage();
            System.err.println(msg);
            this.setActionMessage(msg);
            FacesContext.getCurrentInstance().addMessage("ReOpen", new FacesMessage(msg));
        }
    }

    public void openAccPeriod(AccPeriod aAccPeriodToOpen) {
        String msg = "";
        String sql = "";
        int x = 0;
        AccPeriod PrevAccPeriod = null;
        PrevAccPeriod = this.getAccPeriodByOrder(aAccPeriodToOpen.getOrderNo() - 1);
        if (null == PrevAccPeriod || this.getAccPeriodsCurrentFirst().size() == 1) {
            aAccPeriodToOpen.setIsOpen(1);
            aAccPeriodToOpen.setIsCurrent(1);
            aAccPeriodToOpen.setIsClosed(0);
            x = this.saveAccPeriod(aAccPeriodToOpen);
            if (x == 1) {
                msg = "Accounting Period OPENED Successfully!";
                this.setActionMessage(msg);
                FacesContext.getCurrentInstance().addMessage("Open", new FacesMessage(msg));
            }
        } else if (null != PrevAccPeriod && PrevAccPeriod.getIsClosed() == 0) {
            msg = "Accounting Period CANNOT BE OPENED, when the previous Accounting Period is NOT CLOSED!";
            this.setActionMessage(msg);
            FacesContext.getCurrentInstance().addMessage("Open", new FacesMessage(msg));
        } else {
            aAccPeriodToOpen.setIsOpen(1);
            aAccPeriodToOpen.setIsCurrent(1);
            aAccPeriodToOpen.setIsClosed(0);
            x = this.saveAccPeriod(aAccPeriodToOpen);
            //post open balances
            if (x == 1) {
                sql = "{call sp_post_ledger_open_balances(?,?)}";
                try (
                        Connection conn = DBConnection.getMySQLConnection();
                        CallableStatement cs = conn.prepareCall(sql);) {
                    try {
                        cs.setInt("in_closed_acc_period_id", PrevAccPeriod.getAccPeriodId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_closed_acc_period_id", 0);
                    }
                    try {
                        cs.setInt("in_opened_acc_period_id", aAccPeriodToOpen.getAccPeriodId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_opened_acc_period_id", 0);
                    }
                    cs.executeUpdate();
                    msg = "Accounting Period OPENED Successfully!";
                    this.setActionMessage(msg);
                    FacesContext.getCurrentInstance().addMessage("Open", new FacesMessage(msg));
                } catch (Exception e) {
                    msg = "An error occured:" + "openAccPeriod:" + e.getMessage();
                    System.err.println(msg);
                    this.setActionMessage(msg);
                    FacesContext.getCurrentInstance().addMessage("Close", new FacesMessage(msg));
                }
            }
            //post scheduled depreciation
            if (x == 1) {
                new AccDepScheduleBean().postAccDepSchedules(aAccPeriodToOpen, 0);
            }
        }
    }

    public void reopenAccPeriod(AccPeriod aAccPeriodToOpen) {

    }

    public AccPeriod getAccPeriodById(int aAccPeriodId) {
        String sql = "";
        if (aAccPeriodId > 0) {
            sql = "SELECT * FROM acc_period WHERE acc_period_id=" + aAccPeriodId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccPeriod accperiod = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return accperiod;
    }

    public AccPeriod getAccPeriodByOrder(int aOrderNo) {
        String sql = "";
        if (aOrderNo > 0) {
            sql = "SELECT * FROM acc_period WHERE order_no=" + aOrderNo;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccPeriod accperiod = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return accperiod;
    }

    public AccPeriod getAccPeriodCurrent() {
        String sql = "SELECT * FROM acc_period WHERE is_current=1";
        ResultSet rs = null;
        AccPeriod accperiod = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return accperiod;
    }

    public AccPeriod getAccPeriodLatest() {
        String sql = "SELECT * FROM acc_period ORDER BY end_date DESC LIMIT 1";
        ResultSet rs = null;
        AccPeriod accperiod = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
            }
        } catch (Exception e) {
            System.err.println("getAccPeriodLatest:" + e.getMessage());
        }
        return accperiod;
    }

    public AccPeriod getAccPeriod(Date aDate) {
        String sql = "SELECT * FROM acc_period WHERE '" + new java.sql.Date(aDate.getTime()) + "' BETWEEN start_date AND end_date";
        ResultSet rs = null;
        AccPeriod accperiod = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return accperiod;
    }

    public List<AccPeriod> getAccPeriodsCurrentFirst() {
        String sql;
        sql = "select * from acc_period order by is_current DESC, end_date DESC";
        ResultSet rs = null;
        //List<AccPeriod> aps = new ArrayList<AccPeriod>();
        this.setAccPeriodObjectList(new ArrayList<AccPeriod>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccPeriod accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
                this.getAccPeriodObjectList().add(accperiod);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return getAccPeriodObjectList();
    }

    public List<AccPeriod> returnAccPeriodsCurrentFirst() {
        String sql;
        sql = "select * from acc_period order by is_current DESC, end_date DESC";
        ResultSet rs = null;
        List<AccPeriod> aps = new ArrayList<AccPeriod>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccPeriod accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
                aps.add(accperiod);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return aps;
    }

    public List<AccPeriod> returnAccPeriodObjectListAll() {
        String sql;
        sql = "select * from acc_period order by order_no desc";
        ResultSet rs = null;
        List<AccPeriod> lst = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccPeriod accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
                lst.add(accperiod);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return lst;
    }

    public void arrangeAccPeriodsOrder() {
        String sql;
        sql = "select * from acc_period order by start_date ASC";
        ResultSet rs = null;
        int orderno = 1;
        AccPeriod accperiod = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                accperiod = new AccPeriod();
                this.setAccPeriodFromResultset(accperiod, rs);
                if (orderno == rs.getInt("order_no")) {
                    //do nothing
                } else {
                    int x = this.updateAccPeriodOrder(accperiod.getAccPeriodId(), orderno);
                }
                orderno = orderno + 1;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void setNextAccPeriodStartEndDate(AccPeriod NewAccPeriod) {
        Calendar calLatest = Calendar.getInstance();
        int AccPrdEndDay = 0;
        int AccPrdEndMonth = 0;
        int startYear = 0;
        int endYear = 0;

        String AccPeriodEndDate = "";
        Date aDate = null;
        AccPeriod LatestAccPeriod = this.getAccPeriodLatest();
        if (null == LatestAccPeriod) {
            LatestAccPeriod = new AccPeriod();
        }
        if (LatestAccPeriod.getAccPeriodId() == 0) {
            calLatest.setTime(new CompanySetting().getCURRENT_SERVER_DATE());
        } else {
            calLatest.setTime(LatestAccPeriod.getEndDate());
        }
        //get company setting's end day and month
        try {
            AccPeriodEndDate = new Parameter_listBean().getParameter_listByContextNameMemory("COMPANY_SETTING", "ACCOUNTING_PERIOD_END_DATE").getParameter_value();
            if (AccPeriodEndDate.length() == 5) {
                AccPrdEndDay = Integer.parseInt(AccPeriodEndDate.substring(0, 2));
                AccPrdEndMonth = Integer.parseInt(AccPeriodEndDate.substring(3));
            }
        } catch (Exception e) {
            //
        }

        //get New AccPeriod start date
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        if (AccPrdEndMonth == 12) {
            calStart.set(Calendar.MONTH, 0);//0 is Jan
        } else {
            calStart.set(Calendar.MONTH, AccPrdEndMonth);
        }
        if (AccPrdEndMonth == 12) {
            startYear = calLatest.get(Calendar.YEAR) + 1;
            calStart.set(Calendar.YEAR, startYear);
        } else {
            startYear = calLatest.get(Calendar.YEAR);
            calStart.set(Calendar.YEAR, startYear);
        }
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        NewAccPeriod.setStartDate(calStart.getTime());

        //get New AccPeriod end date
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.DAY_OF_MONTH, AccPrdEndDay);
        if (AccPrdEndMonth == 12) {
            calEnd.set(Calendar.MONTH, AccPrdEndMonth - 1);//0 is Jan
        } else {
            calEnd.set(Calendar.MONTH, AccPrdEndMonth - 1);
        }
        if (AccPrdEndMonth == 12) {
            endYear = calLatest.get(Calendar.YEAR) + 1;
            calEnd.set(Calendar.YEAR, endYear);
        } else {
            endYear = calLatest.get(Calendar.YEAR) + 1;
            calEnd.set(Calendar.YEAR, endYear);
        }
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);
        NewAccPeriod.setEndDate(calEnd.getTime());

        //account period year and order
        if (startYear == endYear) {
            NewAccPeriod.setAccPeriodName(Integer.toString(startYear));
        } else {
            NewAccPeriod.setAccPeriodName(Integer.toString(startYear) + "-" + Integer.toString(endYear));
        }
        NewAccPeriod.setOrderNo(LatestAccPeriod.getOrderNo() + 1);
    }

    public void initAddNewAccPeriod(AccPeriod aAccPeriod) {
        try {
            if (null != aAccPeriod) {
                this.setNextAccPeriodStartEndDate(aAccPeriod);
                aAccPeriod.setAction_mode(1);//0-dont show; 1-add new; 2-edit
                aAccPeriod.setAccPeriodId(0);
                //aAccPeriod.setAccPeriodName("");
                aAccPeriod.setIsCurrent(0);
                //aAccPeriod.setStartDate(null);
                //aAccPeriod.setEndDate(null);
                aAccPeriod.setIsActive(0);
                aAccPeriod.setIsDeleted(0);
                aAccPeriod.setIsOpen(0);
                aAccPeriod.setIsClosed(0);
                //aAccPeriod.setOrderNo(0);
                aAccPeriod.setAddBy(0);
                aAccPeriod.setLastEditBy(0);
                aAccPeriod.setAddDate(null);
                aAccPeriod.setLastEditDate(null);
            }
        } catch (Exception e) {
            System.out.println("initAddNewAccPeriod:" + e.getMessage());
        }
    }

    public void initClearAccPeriod(AccPeriod aAccPeriod) {
        if (null != aAccPeriod) {
            this.clearAccPeriod(aAccPeriod);
        }
    }

    public void clearAccPeriod(AccPeriod aAccPeriod) {
        if (null != aAccPeriod) {
            aAccPeriod.setAccPeriodId(0);
            aAccPeriod.setAccPeriodName("");
            aAccPeriod.setIsCurrent(0);
            aAccPeriod.setStartDate(null);
            aAccPeriod.setEndDate(null);
            aAccPeriod.setIsActive(1);
            aAccPeriod.setIsDeleted(0);
            aAccPeriod.setIsOpen(0);
            aAccPeriod.setIsClosed(0);
            aAccPeriod.setOrderNo(0);
            aAccPeriod.setAddBy(0);
            aAccPeriod.setLastEditBy(0);
            aAccPeriod.setAddDate(null);
            aAccPeriod.setLastEditDate(null);
            aAccPeriod.setAction_mode(0);
        }
    }

    public void copyAccPeriod(AccPeriod aFrom, AccPeriod aTo) {
        aTo.setAction_mode(2);
        aTo.setAccPeriodId(aFrom.getAccPeriodId());
        aTo.setAccPeriodName(aFrom.getAccPeriodName());
        aTo.setStartDate(aFrom.getStartDate());
        aTo.setEndDate(aFrom.getEndDate());
        aTo.setOrderNo(aFrom.getOrderNo());
        aTo.setIsActive(aFrom.getIsActive());
        aTo.setIsCurrent(aFrom.getIsCurrent());
        aTo.setIsOpen(aFrom.getIsOpen());
        aTo.setIsClosed(aFrom.getIsClosed());
        aTo.setIsDeleted(aFrom.getIsDeleted());
        aTo.setAddBy(aFrom.getAddBy());
        aTo.setAddDate(aFrom.getAddDate());
        aTo.setLastEditBy(aFrom.getLastEditBy());
        aTo.setLastEditDate(aFrom.getLastEditDate());
    }

    public void deleteAccPeriod(AccPeriod aAccPeriod) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        String sql2 = "SELECT COUNT(*) AS n from acc_journal WHERE acc_period_id=+" + aAccPeriod.getAccPeriodId();

        if (null != aAccPeriod) {
            aAccPeriod.setAction_mode(0);
        }
        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "53", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new UtilityBean().getN(sql2) > 0) {
            msg = "SELECTED ACCOUNTING PERIOD CANNOT BE DELETED; IT HAS JOURNAL ENTRIES, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            String sql = "DELETE FROM acc_period WHERE acc_period_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aAccPeriod.getAccPeriodId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.clearAccPeriod(aAccPeriod);
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("Account Period not deleted");
            }
        }
    }

    public int saveSnapshotStockValue(AccPeriod aAccPeriod) {
        int saved = 0;
        String sql = "{call sp_take_snapshot_stock_value(?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aAccPeriod) {
                try {
                    cs.setInt("in_acc_period_id", aAccPeriod.getAccPeriodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_acc_period_id", 0);
                }
                cs.executeUpdate();
                saved = 1;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return saved;
    }

    public int saveSnapshotXrate(AccPeriod aAccPeriod) {
        int saved = 0;
        String sql = "{call sp_take_snapshot_xrate(?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aAccPeriod) {
                try {
                    cs.setInt("in_acc_period_id", aAccPeriod.getAccPeriodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_acc_period_id", 0);
                }
                cs.executeUpdate();
                saved = 1;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return saved;
    }

    /**
     * @return the AccPeriodObjectList
     */
    public List<AccPeriod> getAccPeriodObjectList() {
        return AccPeriodObjectList;
    }

    /**
     * @param AccPeriodObjectList the AccPeriodObjectList to set
     */
    public void setAccPeriodObjectList(List<AccPeriod> AccPeriodObjectList) {
        this.AccPeriodObjectList = AccPeriodObjectList;
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
}
