package beans;

import api_sm_bi.CheckApiBean;
import api_sm_bi.SMbiBean;
import connections.DBConnection;
import entities.CompanySetting;
import entities.Loyalty_transaction;
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
@ManagedBean(name = "loyalty_transactionBean")
@SessionScoped
public class Loyalty_transactionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Loyalty_transactionBean.class.getName());
    private Date Date1;
    private Date Date2;
    private String TransactionNumber;
    private String ActionMessage = null;
    private List<Loyalty_transaction> TransList;
    private List<Loyalty_transaction> TransListSummary;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setLoyalty_transactionFromResultset(Loyalty_transaction aLoyalty_transaction, ResultSet aResultSet) {
        try {
            try {
                aLoyalty_transaction.setLoyalty_transaction_id(aResultSet.getLong("loyalty_transaction_id"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setLoyalty_transaction_id(0);
            }
            try {
                aLoyalty_transaction.setCard_number(aResultSet.getString("card_number"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setCard_number("");
            }
            try {
                aLoyalty_transaction.setInvoice_number(aResultSet.getString("invoice_number"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setInvoice_number("");
            }
            try {
                aLoyalty_transaction.setTransaction_date(new Date(aResultSet.getDate("transaction_date").getTime()));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setTransaction_date(null);
            }
            try {
                aLoyalty_transaction.setPoints_awarded(aResultSet.getDouble("points_awarded"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setPoints_awarded(0);
            }
            try {
                aLoyalty_transaction.setAmount_awarded(aResultSet.getDouble("amount_awarded"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setAmount_awarded(0);
            }
            try {
                aLoyalty_transaction.setPoints_spent(aResultSet.getDouble("points_spent"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setPoints_spent(0);
            }
            try {
                aLoyalty_transaction.setAmount_spent(aResultSet.getDouble("amount_spent"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setAmount_spent(0);
            }
            try {
                aLoyalty_transaction.setCurrency_code(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setCurrency_code("");
            }
            try {
                aLoyalty_transaction.setStaff_code(aResultSet.getString("staff_code"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setStaff_code("");
            }
            try {
                aLoyalty_transaction.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setAdd_date(null);
            }
            try {
                aLoyalty_transaction.setStatus_sync(aResultSet.getInt("status_sync"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setStatus_sync(0);
            }
            try {
                aLoyalty_transaction.setStatus_date(new Date(aResultSet.getTimestamp("status_date").getTime()));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setStatus_date(null);
            }
            try {
                aLoyalty_transaction.setStatus_desc(aResultSet.getString("status_desc"));
            } catch (NullPointerException npe) {
                aLoyalty_transaction.setStatus_desc("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void insertLoyalty_transactionCallThread(long aLoyalty_transaction_id) {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    insertLoyalty_transactionCall(aLoyalty_transaction_id);
                }
            };
            Executor e = Executors.newSingleThreadExecutor();
            e.execute(task);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void insertLoyalty_transactionCall(long aLoyalty_transaction_id) {
        try {
//            if (aTransaction_id > 0 && aTransaction_type_id > 0) {
//                if (aTransaction_type_id == 2) {//SalesInvoice
//                    Trans t = new TransBean().getTrans(aTransaction_id);
//                    if (null != t) {
//                        Loyalty_transaction tsmbi = new Loyalty_transaction();
//                        tsmbi.setTransaction_id(t.getTransactionId());
//                        tsmbi.setTransaction_type_id(t.getTransactionTypeId());
//                        tsmbi.setTransaction_reason_id(t.getTransactionReasonId());
//                        tsmbi.setTransaction_number(t.getTransactionNumber());
//                        Date dt = new CompanySetting().getCURRENT_SERVER_DATE();
//                        tsmbi.setAdd_date(dt);
//                        tsmbi.setStatus_sync(0);
//                        tsmbi.setStatus_date(dt);
//                        tsmbi.setStatus_desc("not synced");
//                        int s = this.insertLoyalty_transaction(tsmbi);
//                    }
//                } else if (aTransaction_type_id == 82 || aTransaction_type_id == 83) {//82-126-CREDIT NOTE, 83-127-DEBIT NOTE
//                    Trans t = new CreditDebitNoteBean().getTrans_cr_dr_note(aTransaction_id);
//                    if (null != t) {
//                        Loyalty_transaction tsmbi = new Loyalty_transaction();
//                        tsmbi.setTransaction_id(t.getTransactionId());
//                        tsmbi.setTransaction_type_id(t.getTransactionTypeId());
//                        tsmbi.setTransaction_reason_id(t.getTransactionReasonId());
//                        tsmbi.setTransaction_number(t.getTransactionNumber());
//                        Date dt = new CompanySetting().getCURRENT_SERVER_DATE();
//                        tsmbi.setAdd_date(dt);
//                        tsmbi.setStatus_sync(0);
//                        tsmbi.setStatus_date(dt);
//                        tsmbi.setStatus_desc("not synced");
//                        int s = this.insertLoyalty_transaction(tsmbi);
//                    }
//                }
//            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertLoyalty_transaction(Loyalty_transaction aLoyalty_transaction) {
        int saved = 0;
        String sql = "INSERT INTO loyalty_transaction"
                + "(card_number,invoice_number,transaction_date,"
                + "points_awarded,amount_awarded,points_spent,amount_spent,"
                + "currency_code,staff_code,"
                + "add_date,status_sync,status_date,status_desc)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (null != aLoyalty_transaction) {
                try {
                    ps.setString(1, aLoyalty_transaction.getCard_number());
                } catch (NullPointerException npe) {
                    ps.setString(1, "");
                }
                try {
                    ps.setString(2, aLoyalty_transaction.getInvoice_number());
                } catch (NullPointerException npe) {
                    ps.setString(2, "");
                }
                try {
                    ps.setDate(3, new java.sql.Date(aLoyalty_transaction.getTransaction_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setDate(3, new java.sql.Date(new Date().getTime()));
                }
                try {
                    ps.setDouble(4, aLoyalty_transaction.getPoints_awarded());
                } catch (NullPointerException npe) {
                    ps.setDouble(4, 0);
                }
                try {
                    ps.setDouble(5, aLoyalty_transaction.getAmount_awarded());
                } catch (NullPointerException npe) {
                    ps.setDouble(5, 0);
                }
                try {
                    ps.setDouble(6, aLoyalty_transaction.getPoints_spent());
                } catch (NullPointerException npe) {
                    ps.setDouble(6, 0);
                }
                try {
                    ps.setDouble(7, aLoyalty_transaction.getAmount_spent());
                } catch (NullPointerException npe) {
                    ps.setDouble(7, 0);
                }
                try {
                    ps.setString(8, aLoyalty_transaction.getCurrency_code());
                } catch (NullPointerException npe) {
                    ps.setString(8, "");
                }
                try {
                    ps.setString(9, aLoyalty_transaction.getStaff_code());
                } catch (NullPointerException npe) {
                    ps.setString(9, "");
                }
                try {
                    ps.setTimestamp(10, new java.sql.Timestamp(aLoyalty_transaction.getAdd_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(10, null);
                }
                try {
                    ps.setInt(11, aLoyalty_transaction.getStatus_sync());
                } catch (NullPointerException npe) {
                    ps.setInt(11, 0);
                }
                try {
                    ps.setTimestamp(12, new java.sql.Timestamp(aLoyalty_transaction.getStatus_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(12, null);
                }
                try {
                    ps.setString(13, aLoyalty_transaction.getStatus_desc());
                } catch (NullPointerException npe) {
                    ps.setString(13, "");
                }
                ps.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public void updateNotSyncedAndCallSyncJob(long aLoyalty_transaction_id, int aCurrent_status_sync) {
        try {
            if (aLoyalty_transaction_id > 0) {
                if (aCurrent_status_sync == 2) {
                    int x = this.updateLoyalty_transaction(0, new CompanySetting().getCURRENT_SERVER_DATE(), "", aLoyalty_transaction_id);
                }
                if (new CheckApiBean().IsSmBiAvailable() && new Parameter_listBean().getParameter_listByContextNameMemory("API", "API_SMBI_URL").getParameter_value().length() > 0) {
                    new SMbiBean().syncSMbiCall();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int updateLoyalty_transaction(int aStatus_sync, Date aStatus_date, String aStatus_desc, long aLoyalty_transaction_id) {
        int saved = 0;
        String sql = "UPDATE loyalty_transaction SET "
                + "status_sync=?,status_date=?,status_desc=? WHERE loyalty_transaction_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (aLoyalty_transaction_id > 0) {
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
                    ps.setLong(4, aLoyalty_transaction_id);
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
            if ((this.getDate1() != null && this.getDate2() != null) || (this.getTransactionNumber().length() > 0)) {
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
            if (this.getTransactionNumber().length() > 0) {
                WhereAppend = WhereAppend + " AND m.transaction_number='" + this.getTransactionNumber() + "'";
            }
            if (this.getDate1() != null && this.getDate2() != null) {
                WhereAppend = WhereAppend + " AND m.add_date BETWEEN '" + new java.sql.Timestamp(this.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(this.getDate2().getTime()) + "'";
            }
            String sql = "select m.*,tt.transaction_type_name from loyalty_transaction m "
                    + "inner join transaction_type tt on m.transaction_type_id=tt.transaction_type_id where 1=1 " + WhereAppend + " ORDER BY add_date DESC";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                Loyalty_transaction trans = null;
                while (rs.next()) {
                    trans = new Loyalty_transaction();
//                    this.setLoyalty_transactionFromResultset(trans, rs);
//                    try {
//                        trans.setTransaction_type_name(rs.getString("transaction_type_name"));
//                    } catch (Exception npe) {
//                        trans.setTransaction_type_name("");
//                    }
                    this.TransList.add(trans);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
            //2. summary
            String sqlsum = "select tt.transaction_type_name,m.status_sync,count(*) as n from loyalty_transaction m "
                    + "inner join transaction_type tt on m.transaction_type_id=tt.transaction_type_id "
                    + "where 1=1 " + WhereAppend
                    + " group by tt.transaction_type_name,m.status_sync "
                    + " order by transaction_type_name ASC";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                Loyalty_transaction transsum = null;
                while (rs.next()) {
                    transsum = new Loyalty_transaction();
//                    try {
//                        transsum.setTransaction_type_name(rs.getString("transaction_type_name"));
//                    } catch (NullPointerException npe) {
//                        transsum.setTransaction_type_name("");
//                    }
//                    try {
//                        transsum.setStatus_sync(rs.getInt("status_sync"));
//                    } catch (NullPointerException npe) {
//                        transsum.setStatus_sync(0);
//                    }
//                    try {
//                        transsum.setItemCount(rs.getLong("n"));
//                    } catch (NullPointerException npe) {
//                        transsum.setItemCount(0);
//                    }
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
            //this.setTransactionTypeId(0);
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
    public List<Loyalty_transaction> getTransList() {
        return TransList;
    }

    /**
     * @param TransList the TransList to set
     */
    public void setTransList(List<Loyalty_transaction> TransList) {
        this.TransList = TransList;
    }

    /**
     * @return the TransListSummary
     */
    public List<Loyalty_transaction> getTransListSummary() {
        return TransListSummary;
    }

    /**
     * @param TransListSummary the TransListSummary to set
     */
    public void setTransListSummary(List<Loyalty_transaction> TransListSummary) {
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
