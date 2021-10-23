package beans;

import api_sm_bi.CheckApiBean;
import api_sm_bi.SMbiBean;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Trans;
import entities.Transaction_smbi_map;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "transaction_smbi_mapBean")
@SessionScoped
public class Transaction_smbi_mapBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Transaction_smbi_mapBean.class.getName());
    private Date Date1;
    private Date Date2;
    private int TransactionTypeId;
    private String TransactionNumber;
    private String ActionMessage = null;
    private List<Transaction_smbi_map> TransList;
    private List<Transaction_smbi_map> TransListSummary;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setTransaction_smbi_mapFromResultset(Transaction_smbi_map aTransaction_smbi_map, ResultSet aResultSet) {
        try {
            try {
                aTransaction_smbi_map.setTransaction_smbi_map_id(aResultSet.getLong("transaction_smbi_map_id"));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setTransaction_smbi_map_id(0);
            }
            try {
                aTransaction_smbi_map.setTransaction_id(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setTransaction_id(0);
            }
            try {
                aTransaction_smbi_map.setTransaction_type_id(aResultSet.getInt("transaction_type_id"));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setTransaction_type_id(0);
            }
            try {
                aTransaction_smbi_map.setTransaction_reason_id(aResultSet.getInt("transaction_reason_id"));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setTransaction_reason_id(0);
            }
            try {
                aTransaction_smbi_map.setTransaction_number(aResultSet.getString("transaction_number"));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setTransaction_number("");
            }
            try {
                aTransaction_smbi_map.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setAdd_date(null);
            }
            try {
                aTransaction_smbi_map.setStatus_sync(aResultSet.getInt("status_sync"));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setStatus_sync(0);
            }
            try {
                aTransaction_smbi_map.setStatus_date(new Date(aResultSet.getTimestamp("status_date").getTime()));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setStatus_date(null);
            }
            try {
                aTransaction_smbi_map.setStatus_desc(aResultSet.getString("status_desc"));
            } catch (NullPointerException npe) {
                aTransaction_smbi_map.setStatus_desc("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void insertTransaction_smbi_mapCallThread(long aTransaction_id, int aTransaction_type_id) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    insertTransaction_smbi_mapCall(aTransaction_id, aTransaction_type_id);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void insertTransaction_smbi_mapCall(long aTransaction_id, int aTransaction_type_id) {
        try {
            if (aTransaction_id > 0 && aTransaction_type_id > 0) {
                String scope = new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_SMBI_SCOPE").getParameter_value();
                if (aTransaction_type_id == 2 && (scope.isEmpty() || scope.contains("SALES"))) {//SalesInvoice
                    Trans t = new TransBean().getTrans(aTransaction_id);
                    if (null != t) {
                        Transaction_smbi_map tsmbi = new Transaction_smbi_map();
                        tsmbi.setTransaction_id(t.getTransactionId());
                        tsmbi.setTransaction_type_id(t.getTransactionTypeId());
                        tsmbi.setTransaction_reason_id(t.getTransactionReasonId());
                        tsmbi.setTransaction_number(t.getTransactionNumber());
                        Date dt = new CompanySetting().getCURRENT_SERVER_DATE();
                        tsmbi.setAdd_date(dt);
                        tsmbi.setStatus_sync(0);
                        tsmbi.setStatus_date(dt);
                        tsmbi.setStatus_desc("not synced");
                        int s = this.insertTransaction_smbi_map(tsmbi);
                    }
                } else if ((aTransaction_type_id == 82 || aTransaction_type_id == 83) && (scope.isEmpty() || scope.contains("SALES"))) {//82-126-CREDIT NOTE, 83-127-DEBIT NOTE
                    Trans t = new CreditDebitNoteBean().getTrans_cr_dr_note(aTransaction_id);
                    if (null != t) {
                        Transaction_smbi_map tsmbi = new Transaction_smbi_map();
                        tsmbi.setTransaction_id(t.getTransactionId());
                        tsmbi.setTransaction_type_id(t.getTransactionTypeId());
                        tsmbi.setTransaction_reason_id(t.getTransactionReasonId());
                        tsmbi.setTransaction_number(t.getTransactionNumber());
                        Date dt = new CompanySetting().getCURRENT_SERVER_DATE();
                        tsmbi.setAdd_date(dt);
                        tsmbi.setStatus_sync(0);
                        tsmbi.setStatus_date(dt);
                        tsmbi.setStatus_desc("not synced");
                        int s = this.insertTransaction_smbi_map(tsmbi);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertTransaction_smbi_map(Transaction_smbi_map aTransaction_smbi_map) {
        int saved = 0;
        String sql = "INSERT INTO transaction_smbi_map"
                + "(transaction_id,transaction_type_id,transaction_reason_id,transaction_number,add_date,status_sync,status_date,status_desc)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (null != aTransaction_smbi_map) {
                try {
                    ps.setLong(1, aTransaction_smbi_map.getTransaction_id());
                } catch (NullPointerException npe) {
                    ps.setLong(1, 0);
                }
                try {
                    ps.setInt(2, aTransaction_smbi_map.getTransaction_type_id());
                } catch (NullPointerException npe) {
                    ps.setInt(2, 0);
                }
                try {
                    ps.setInt(3, aTransaction_smbi_map.getTransaction_reason_id());
                } catch (NullPointerException npe) {
                    ps.setInt(3, 0);
                }
                try {
                    ps.setString(4, aTransaction_smbi_map.getTransaction_number());
                } catch (NullPointerException npe) {
                    ps.setString(4, "");
                }
                try {
                    ps.setTimestamp(5, new java.sql.Timestamp(aTransaction_smbi_map.getAdd_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(5, null);
                }
                try {
                    ps.setInt(6, aTransaction_smbi_map.getStatus_sync());
                } catch (NullPointerException npe) {
                    ps.setInt(6, 0);
                }
                try {
                    ps.setTimestamp(7, new java.sql.Timestamp(aTransaction_smbi_map.getStatus_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(7, null);
                }
                try {
                    ps.setString(8, aTransaction_smbi_map.getStatus_desc());
                } catch (NullPointerException npe) {
                    ps.setString(8, "");
                }
                ps.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public void updateNotSyncedAndCallSyncJob(int aTransTypeId, long aTransaction_smbi_map_id, int aCurrent_status_sync) {
        try {
            if (aTransaction_smbi_map_id > 0) {
                if (aTransTypeId == 1010) {
                    new Loyalty_transactionBean().updateNotSyncedAndCallSyncJob(aTransaction_smbi_map_id, aCurrent_status_sync);
                } else {
                    if (aCurrent_status_sync == 2) {
                        int x = this.updateTransaction_smbi_map(0, new CompanySetting().getCURRENT_SERVER_DATE(), "", aTransaction_smbi_map_id);
                    }
                    if (new CheckApiBean().IsSmBiAvailable() && new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_SMBI_URL").getParameter_value().length() > 0) {
                        new SMbiBean().syncSMbiCall();
                    }
                }
                this.reportSMbiAPI();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int updateTransaction_smbi_map(int aStatus_sync, Date aStatus_date, String aStatus_desc, long aTransaction_smbi_map_id) {
        int saved = 0;
        String sql = "UPDATE transaction_smbi_map SET "
                + "status_sync=?,status_date=?,status_desc=? WHERE transaction_smbi_map_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aTransaction_smbi_map_id > 0) {
                try {
                    ps.setInt(1, aStatus_sync);
                } catch (NullPointerException npe) {
                    ps.setInt(1, 0);
                }
                try {
                    ps.setTimestamp(2, new java.sql.Timestamp(aStatus_date.getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(2, null);
                }
                try {
                    ps.setString(3, aStatus_desc);
                } catch (NullPointerException npe) {
                    ps.setString(3, "");
                }
                try {
                    ps.setLong(4, aTransaction_smbi_map_id);
                } catch (NullPointerException npe) {
                    ps.setLong(4, 0);
                }
                ps.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int updateTransaction_smbi_map(int aStatus_sync, Date aStatus_date, String aStatus_desc, long aTransaction_id, int aTransaction_type_id) {
        int saved = 0;
        String sql = "UPDATE transaction_smbi_map SET "
                + "status_sync=?,status_date=?,status_desc=? WHERE transaction_id=? AND transaction_type_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aTransaction_id > 0 && aTransaction_type_id > 0) {
                try {
                    ps.setInt(1, aStatus_sync);
                } catch (NullPointerException npe) {
                    ps.setInt(1, 0);
                }
                try {
                    ps.setTimestamp(2, new java.sql.Timestamp(aStatus_date.getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(2, null);
                }
                try {
                    ps.setString(3, aStatus_desc);
                } catch (NullPointerException npe) {
                    ps.setString(3, "");
                }
                try {
                    ps.setLong(4, aTransaction_id);
                } catch (NullPointerException npe) {
                    ps.setLong(4, 0);
                }
                try {
                    ps.setInt(5, aTransaction_type_id);
                } catch (NullPointerException npe) {
                    ps.setInt(5, 0);
                }
                ps.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
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

    public void reportSMbiAPI() {
        UtilityBean ub = new UtilityBean();
        String BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        this.setActionMessage("");
        try {
            if ((this.getDate1() != null && this.getDate2() != null) || (this.getTransactionNumber().length() > 0 && this.getTransactionTypeId() > 0)) {
                //okay no problem
            } else {
                msg = "Either Select Date Range or Specify Transaction Number and Type";
            }
        } catch (Exception e) {
            //do nothing
        }
        ResultSet rs = null;
        this.TransList = new ArrayList<>();
        this.TransListSummary = new ArrayList<>();
        if (msg.length() > 0) {
            this.setActionMessage(ub.translateWordsInText(BaseName, msg));
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            //1. detail
            String WhereAppend = "";
            if (this.getTransactionTypeId() > 0) {
                WhereAppend = WhereAppend + " AND transaction_type_id=" + this.getTransactionTypeId();
            }
            if (this.getTransactionNumber().length() > 0) {
                WhereAppend = WhereAppend + " AND transaction_number='" + this.getTransactionNumber() + "'";
            }
            if (this.getDate1() != null && this.getDate2() != null) {
                WhereAppend = WhereAppend + " AND add_date BETWEEN '" + new java.sql.Timestamp(this.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(this.getDate2().getTime()) + "'";
            }
            String sql = "select * from view_api_smbi "
                    + "where 1=1 " + WhereAppend + " ORDER BY add_date DESC";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                Transaction_smbi_map trans = null;
                while (rs.next()) {
                    trans = new Transaction_smbi_map();
                    this.setTransaction_smbi_mapFromResultset(trans, rs);
                    try {
                        trans.setTransaction_type_name(rs.getString("transaction_type_name"));
                    } catch (Exception npe) {
                        trans.setTransaction_type_name("");
                    }
                    this.TransList.add(trans);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            //2. summary
            String sqlsum = "select transaction_type_name,status_sync,count(*) as n from view_api_smbi "
                    + "where 1=1 " + WhereAppend
                    + " group by transaction_type_name,status_sync "
                    + " order by transaction_type_name ASC";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                Transaction_smbi_map transsum = null;
                while (rs.next()) {
                    transsum = new Transaction_smbi_map();
                    try {
                        transsum.setTransaction_type_name(rs.getString("transaction_type_name"));
                    } catch (NullPointerException npe) {
                        transsum.setTransaction_type_name("");
                    }
                    try {
                        transsum.setStatus_sync(rs.getInt("status_sync"));
                    } catch (NullPointerException npe) {
                        transsum.setStatus_sync(0);
                    }
                    try {
                        transsum.setItemCount(rs.getLong("n"));
                    } catch (NullPointerException npe) {
                        transsum.setItemCount(0);
                    }
                    this.TransListSummary.add(transsum);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public void reportSMbiAPI_old() {
        UtilityBean ub = new UtilityBean();
        String BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        this.setActionMessage("");
        try {
            if ((this.getDate1() != null && this.getDate2() != null) || (this.getTransactionNumber().length() > 0 && this.getTransactionTypeId() > 0)) {
                //okay no problem
            } else {
                msg = "Either Select Date Range or Specify Transaction Number and Type";
            }
        } catch (Exception e) {
            //do nothing
        }
        ResultSet rs = null;
        this.TransList = new ArrayList<>();
        this.TransListSummary = new ArrayList<>();
        if (msg.length() > 0) {
            this.setActionMessage(ub.translateWordsInText(BaseName, msg));
            FacesContext.getCurrentInstance().addMessage("Report", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            //1. detail
            String WhereAppend = "";
            if (this.getTransactionTypeId() > 0) {
                WhereAppend = WhereAppend + " AND m.transaction_type_id=" + this.getTransactionTypeId();
            }
            if (this.getTransactionNumber().length() > 0) {
                WhereAppend = WhereAppend + " AND m.transaction_number='" + this.getTransactionNumber() + "'";
            }
            if (this.getDate1() != null && this.getDate2() != null) {
                WhereAppend = WhereAppend + " AND m.add_date BETWEEN '" + new java.sql.Timestamp(this.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(this.getDate2().getTime()) + "'";
            }
            String sql = "select m.*,tt.transaction_type_name from transaction_smbi_map m "
                    + "inner join transaction_type tt on m.transaction_type_id=tt.transaction_type_id where 1=1 " + WhereAppend + " ORDER BY add_date DESC";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                Transaction_smbi_map trans = null;
                while (rs.next()) {
                    trans = new Transaction_smbi_map();
                    this.setTransaction_smbi_mapFromResultset(trans, rs);
                    try {
                        trans.setTransaction_type_name(rs.getString("transaction_type_name"));
                    } catch (Exception npe) {
                        trans.setTransaction_type_name("");
                    }
                    this.TransList.add(trans);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            //2. summary
            String sqlsum = "select tt.transaction_type_name,m.status_sync,count(*) as n from transaction_smbi_map m "
                    + "inner join transaction_type tt on m.transaction_type_id=tt.transaction_type_id "
                    + "where 1=1 " + WhereAppend
                    + " group by tt.transaction_type_name,m.status_sync "
                    + " order by transaction_type_name ASC";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                Transaction_smbi_map transsum = null;
                while (rs.next()) {
                    transsum = new Transaction_smbi_map();
                    try {
                        transsum.setTransaction_type_name(rs.getString("transaction_type_name"));
                    } catch (NullPointerException npe) {
                        transsum.setTransaction_type_name("");
                    }
                    try {
                        transsum.setStatus_sync(rs.getInt("status_sync"));
                    } catch (NullPointerException npe) {
                        transsum.setStatus_sync(0);
                    }
                    try {
                        transsum.setItemCount(rs.getLong("n"));
                    } catch (NullPointerException npe) {
                        transsum.setItemCount(0);
                    }
                    this.TransListSummary.add(transsum);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public void resetSMbiAPI() {
        try {
            this.setActionMessage("");
            this.setTransactionTypeId(0);
            this.setDate1(null);
            this.setDate2(null);
            this.TransList.clear();
            this.TransListSummary.clear();
        } catch (NullPointerException npe) {
        }
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
     * @return the TransactionTypeId
     */
    public int getTransactionTypeId() {
        return TransactionTypeId;
    }

    /**
     * @param TransactionTypeId the TransactionTypeId to set
     */
    public void setTransactionTypeId(int TransactionTypeId) {
        this.TransactionTypeId = TransactionTypeId;
    }

    /**
     * @return the TransactionNumber
     */
    public String getTransactionNumber() {
        return TransactionNumber;
    }

    /**
     * @param TransactionNumber the TransactionNumber to set
     */
    public void setTransactionNumber(String TransactionNumber) {
        this.TransactionNumber = TransactionNumber;
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
     * @return the TransList
     */
    public List<Transaction_smbi_map> getTransList() {
        return TransList;
    }

    /**
     * @param TransList the TransList to set
     */
    public void setTransList(List<Transaction_smbi_map> TransList) {
        this.TransList = TransList;
    }

    /**
     * @return the TransListSummary
     */
    public List<Transaction_smbi_map> getTransListSummary() {
        return TransListSummary;
    }

    /**
     * @param TransListSummary the TransListSummary to set
     */
    public void setTransListSummary(List<Transaction_smbi_map> TransListSummary) {
        this.TransListSummary = TransListSummary;
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
}
