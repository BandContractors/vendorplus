package beans;

import connections.DBConnection;
import entities.Alert_general;
import entities.CompanySetting;
import entities.EmailEntity;
import entities.Item;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@ManagedBean(name = "alert_generalBean")
@SessionScoped
public class Alert_generalBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Alert_general> Alert_generalObjectList;
    private Alert_general Alert_generalObj;

    public void setAlert_generalFromResultset(Alert_general aAlert_general, ResultSet aResultSet) {
        try {
            try {
                aAlert_general.setAlert_general_id(aResultSet.getLong("alert_general_id"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_general_id(0);
            }
            try {
                aAlert_general.setAlert_date(new Date(aResultSet.getDate("alert_date").getTime()));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_date(null);
            }
            try {
                aAlert_general.setAlert_type(aResultSet.getString("alert_type"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_type("");
            }
            try {
                aAlert_general.setMessage(aResultSet.getString("message"));
            } catch (NullPointerException npe) {
                aAlert_general.setMessage("");
            }
            try {
                aAlert_general.setSubject(aResultSet.getString("subject"));
            } catch (NullPointerException npe) {
                aAlert_general.setSubject("");
            }
            try {
                aAlert_general.setAlert_users(aResultSet.getString("alert_users"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_users("");
            }
            try {
                aAlert_general.setRead_by(aResultSet.getString("read_by"));
            } catch (NullPointerException npe) {
                aAlert_general.setRead_by("");
            }
            try {
                aAlert_general.setAlert_items(aResultSet.getString("alert_items"));
            } catch (NullPointerException npe) {
                aAlert_general.setAlert_items("");
            }
            try {
                aAlert_general.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aAlert_general.setAdd_date(null);
            }
            try {
                aAlert_general.setLast_update_date(new Date(aResultSet.getTimestamp("last_update_date").getTime()));
            } catch (NullPointerException npe) {
                aAlert_general.setLast_update_date(null);
            }
            try {
                aAlert_general.setAdd_by(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                aAlert_general.setAdd_by(0);
            }
            try {
                aAlert_general.setLast_update_by(aResultSet.getInt("last_update_by"));
            } catch (NullPointerException npe) {
                aAlert_general.setLast_update_by(0);
            }
            try {
                aAlert_general.setStatus_code(aResultSet.getString("status_code"));
            } catch (NullPointerException npe) {
                aAlert_general.setStatus_code("");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public Alert_general getAlert_general(long aAlert_general_id) {
        String sql = "SELECT * FROM alert_general WHERE alert_general_id=" + aAlert_general_id;
        ResultSet rs = null;
        Alert_general ag = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ag = new Alert_general();
                this.setAlert_generalFromResultset(ag, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return ag;
    }

    public void saveAlert_general(Alert_general aAlert_general) {
        String sql = null;
        if (aAlert_general.getAlert_general_id() == 0) {
            sql = "INSERT INTO alert_general(alert_type,subject,message,alert_users,read_by,alert_items,add_date,add_by,status_code,alert_date) VALUES (?,?,?,?,?,?,?,?,?,?)";
        } else if (aAlert_general.getAlert_general_id() > 0) {
            sql = "UPDATE alert_general SET status_code=?,read_by=?,last_update_date=?,last_update_by=? WHERE alert_general_id=" + aAlert_general.getAlert_general_id();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //Insert
            if (aAlert_general.getAlert_general_id() == 0) {
                ps.setString(1, aAlert_general.getAlert_type());
                ps.setString(2, aAlert_general.getSubject());
                ps.setString(3, aAlert_general.getMessage());
                ps.setString(4, aAlert_general.getAlert_users());
                ps.setString(5, aAlert_general.getRead_by());
                ps.setString(6, aAlert_general.getAlert_items());
                try {
                    ps.setTimestamp(7, new java.sql.Timestamp(aAlert_general.getAdd_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(7, null);
                }
                ps.setInt(8, aAlert_general.getAdd_by());
                ps.setString(9, aAlert_general.getStatus_code());
                try {
                    ps.setDate(10, new java.sql.Date(aAlert_general.getAlert_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setDate(10, null);
                }
            }
            //update
            if (aAlert_general.getAlert_general_id() > 0) {
                ps.setString(1, aAlert_general.getStatus_code());
                ps.setString(2, aAlert_general.getRead_by());
                try {
                    ps.setTimestamp(3, new java.sql.Timestamp(aAlert_general.getLast_update_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(3, null);
                }
                ps.setInt(4, aAlert_general.getLast_update_by());
            }
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("saveAlert_general:" + e.getMessage());
        }
    }

    public void clearAlert_general(Alert_general aAlert_general) {
        if (null != aAlert_general) {
            aAlert_general.setAlert_general_id(0);
            aAlert_general.setAlert_date(null);
            aAlert_general.setAlert_type("");
            aAlert_general.setMessage("");
            aAlert_general.setAlert_users("");
            aAlert_general.setRead_by("");
            aAlert_general.setAlert_items("");
            aAlert_general.setAdd_date(null);
            aAlert_general.setLast_update_date(null);
            aAlert_general.setAdd_by(0);
            aAlert_general.setLast_update_by(0);
            aAlert_general.setStatus_code("");
        }
    }

    /**
     * @return the Alert_generalObjectList
     */
    public List<Alert_general> getAlert_generalObjectList() {
        return Alert_generalObjectList;
    }

    public void checkStockStatusForAlert(long aItem_id) {
        try {
            //get STOCK_ALERTS_MODE 0(None),1(Out of stock),2(Low stock),3(Both Out and Low)
            String STOCK_ALERTS_MODE = "0";
            try {
                STOCK_ALERTS_MODE = new Parameter_listBean().getParameter_listByContextNameMemory("ALERTS", "STOCK_ALERTS_MODE").getParameter_value();
            } catch (Exception e) {
                //do nothing
            }
            if (STOCK_ALERTS_MODE.equals("0")) {
                //do nothing
            } else {
                //get item current stock status
                Item item = new ItemBean().getItemCurrentStockStatus(aItem_id);
                String stockstatus = "";
                if (null != item) {
                    stockstatus = item.getStock_status();
                }
                if (stockstatus.equals("Out of Stock") || stockstatus.equals("Low Stock")) {
                    if (STOCK_ALERTS_MODE.equals("3") || ((stockstatus.equals("Out of Stock") && STOCK_ALERTS_MODE.equals("1")) || (stockstatus.equals("Low Stock") && STOCK_ALERTS_MODE.equals("2")))) {
                        //check if it alert hasnt been taken today
                        if (this.isTodayAlertFound(aItem_id, stockstatus)) {
                            //ignore
                        } else {
                            this.saveNewStockStatusAlert(item);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("checkStockStatusForAlert:" + e.getMessage());
        }
    }

    public void checkStockStatusForEmail(Alert_general aAlert_general) {
        try {
            //get STOCK_ALERTS_EMAIL 0(No),1(Yes-Out of stock),2(Yes-Low stock),3(Yes-Both Out and Low)
            String STOCK_ALERTS_EMAIL = "0";
            try {
                STOCK_ALERTS_EMAIL = new Parameter_listBean().getParameter_listByContextNameMemory("ALERTS", "STOCK_ALERTS_EMAIL").getParameter_value();
            } catch (Exception e) {
                //do nothing
            }
            if (STOCK_ALERTS_EMAIL.equals("0")) {
                //do nothing
            } else {
                //get alert current stock status
                String stockstatus = "";
                if (null != aAlert_general) {
                    stockstatus = aAlert_general.getAlert_type();
                }
                if (stockstatus.equals("Out of Stock") || stockstatus.equals("Low Stock")) {
                    if (STOCK_ALERTS_EMAIL.equals("3") || ((stockstatus.equals("Out of Stock") && STOCK_ALERTS_EMAIL.equals("1")) || (stockstatus.equals("Low Stock") && STOCK_ALERTS_EMAIL.equals("2")))) {
                        EmailEntity emailentity = new EmailEntity();
                        emailentity.setSubject(aAlert_general.getSubject());
                        emailentity.setMessage(aAlert_general.getMessage());
                        String emailids = this.replaceUserIDsWithEmail(aAlert_general.getAlert_users());
                        emailentity.setToEmail(emailids);
                        try {
                            if (emailentity.getToEmail().length() > 0) {
                                int sent = new EmailEntityBean().validateSendEmailBackground(emailentity);
                            }
                        } catch (Exception e) {
                            //do nothing
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("checkStockStatusForEmail:" + e.getMessage());
        }
    }

    public String replaceUserIDsWithEmail(String aUserIDs) {
        String EmailsStr = "";
        List<String> UserIDsList = new ArrayList<>();
        try {
            UserIDsList = Arrays.asList(aUserIDs.split(","));
        } catch (Exception e) {
            //do nothing
        }
        for (int i = 0; i < UserIDsList.size(); i++) {
            String email = "";
            try {
                email = new UserDetailBean().getUserDetail(Integer.parseInt(UserIDsList.get(i))).getEmail_address();
            } catch (Exception e) {
                //do nothing
            }
            if (email.length() > 0) {
                if (EmailsStr.length() > 0) {
                    EmailsStr = EmailsStr + "," + email;
                } else {
                    EmailsStr = email;
                }
            }
        }
        return EmailsStr;
    }

    public void updateReadStockStatusAlert(Alert_general aAlertgeneral) {
        try {
            if (aAlertgeneral.getAlert_general_id() > 0) {
                if (aAlertgeneral.getRead_by().length() > 0) {
                    aAlertgeneral.setRead_by(aAlertgeneral.getRead_by() + "," + new GeneralUserSetting().getCurrentUser().getUserDetailId());
                } else {
                    aAlertgeneral.setRead_by("" + new GeneralUserSetting().getCurrentUser().getUserDetailId() + "");
                }
                aAlertgeneral.setLast_update_date(new CompanySetting().getCURRENT_SERVER_DATE());
                aAlertgeneral.setLast_update_by(new GeneralUserSetting().getCurrentUser().getUserDetailId());
                this.saveAlert_general(aAlertgeneral);
                this.refreshAlerts();
                org.primefaces.PrimeFaces.current().executeScript("doUpdateMenuClick()");
            }
        } catch (Exception e) {
            System.out.println("updateReadStockStatusAlert:" + e.getMessage());
        }
    }

    public void saveNewStockStatusAlert(Item aItem) {
        try {
            Alert_general alertgeneral = new Alert_general();
            alertgeneral.setAlert_general_id(0);
            alertgeneral.setAlert_type(aItem.getStock_status());
            alertgeneral.setAlert_date(new CompanySetting().getCURRENT_SERVER_DATE());
            alertgeneral.setAdd_date(new CompanySetting().getCURRENT_SERVER_DATE());
            alertgeneral.setAdd_by(UserDetailBean.getSystemUserDetailId());
            String subject = aItem.getStock_status() + ", " + aItem.getDescription();
            if (subject.length() > 150) {
                subject = subject.substring(0, (subject.length() - 1));
            }
            alertgeneral.setSubject(subject);
            String message = aItem.getStock_status() + " for item (" + aItem.getDescription() + ")"
                    + " Current Quantity:" + aItem.getQty_total() + " ReOrderLevel:" + aItem.getReorderLevel();
            alertgeneral.setMessage(message);
            alertgeneral.setAlert_users(new UserDetailBean().getUserDetailIDsForFunction("112", "allow_view"));
            alertgeneral.setRead_by("");
            alertgeneral.setAlert_items("" + aItem.getItemId() + "");
            alertgeneral.setStatus_code("");
            this.saveAlert_general(alertgeneral);
            this.checkStockStatusForEmail(alertgeneral);
        } catch (Exception e) {
            System.out.println("saveNewStockStatusAlert:" + e.getMessage());
        }
    }

    public boolean isTodayAlertFound(long aItem_id, String aAlert_type) {
        boolean res = false;
        Date today = new CompanySetting().getCURRENT_SERVER_DATE();
        Alert_general lag = this.getLatestAlert_general(aItem_id, aAlert_type);
        if (null == lag) {
            res = false;
        } else {
            if (new UtilityBean().isDatesEqual(today, lag.getAlert_date()) == 1) {
                res = true;
            } else {
                res = false;
            }
        }
        return res;
    }

    public Alert_general getLatestAlert_general(long aItem_id, String aAlert_type) {
        String sql = "SELECT a1.* FROM alert_general a1 WHERE a1.alert_general_id=(SELECT max(a2.alert_general_id) FROM alert_general a2 WHERE a2.alert_type='" + aAlert_type + "' AND " + aItem_id + " IN(a2.alert_items))";
        ResultSet rs = null;
        Alert_general ag = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                ag = new Alert_general();
                this.setAlert_generalFromResultset(ag, rs);
            }
        } catch (Exception e) {
            System.err.println("getLatestAlert_general:" + e.getMessage());
        }
        return ag;
    }

    public List<Alert_general> retrieveUserUnreadStockAlerts() {
        int userid = new GeneralUserSetting().getCurrentUser().getUserDetailId();
        String sql = "select * from alert_general where " + userid + " IN(alert_users) and " + userid + " NOT IN(read_by) order by add_date desc LIMIT 100";
        ResultSet rs = null;
        List<Alert_general> aList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Alert_general ag = null;
            while (rs.next()) {
                ag = new Alert_general();
                this.setAlert_generalFromResultset(ag, rs);
                aList.add(ag);
            }
        } catch (Exception e) {
            System.err.println("retrieveUserUnreadStockAlerts:" + e.getMessage());
        }
        return aList;
    }

    public void refreshAlerts() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(true);
            List<Alert_general> agL = this.retrieveUserUnreadStockAlerts();
            httpSession.setAttribute("USER_UNREAD_STOCK_ALERTS_LIST", agL);
            httpSession.setAttribute("USER_UNREAD_STOCK_ALERTS_COUNT", agL.size());
        } catch (NullPointerException | ClassCastException npe) {
            //
        }
    }

    public void setUserUnreadStockAlerts(List<Alert_general> aUserUnreadStockAlertsList, int aUserUnreadAlertsCount) {
        int userid = new GeneralUserSetting().getCurrentUser().getUserDetailId();
        String sql = "select * from alert_general where " + userid + " IN(alert_users) and " + userid + " NOT IN(read_by) order by add_date desc LIMIT 100";
        ResultSet rs = null;
        try {
            aUserUnreadStockAlertsList.clear();
        } catch (NullPointerException npe) {
            aUserUnreadStockAlertsList = new ArrayList<>();
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Alert_general ag = null;
            while (rs.next()) {
                ag = new Alert_general();
                this.setAlert_generalFromResultset(ag, rs);
                aUserUnreadStockAlertsList.add(ag);
            }
            aUserUnreadAlertsCount = aUserUnreadStockAlertsList.size();
        } catch (Exception e) {
            System.err.println("refreshUserUnreadStockAlerts:" + e.getMessage());
        }
    }

    /**
     * @param Alert_generalObjectList the Alert_generalObjectList to set
     */
    public void setAlert_generalObjectList(List<Alert_general> Alert_generalObjectList) {
        this.Alert_generalObjectList = Alert_generalObjectList;
    }

    /**
     * @return the Alert_generalObj
     */
    public Alert_general getAlert_generalObj() {
        return Alert_generalObj;
    }

    /**
     * @param Alert_generalObj the Alert_generalObj to set
     */
    public void setAlert_generalObj(Alert_general Alert_generalObj) {
        this.Alert_generalObj = Alert_generalObj;
    }
}
