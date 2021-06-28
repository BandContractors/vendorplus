package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.TransItem;
import entities.Transactor;
import entities.Store;
import entities.Trans;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
@ManagedBean
@SessionScoped
public class ReportBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(ReportBean.class.getName());
    private String ActionMessage = null;
    private String DateType;
    private Date Date1;
    private Date Date2;
    private String FieldName;
    private List<Trans> TransList;
    private List<Trans> TransListSummary;
    private List<TransItem> TransItemList;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

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

    public void reportOverpaidTrans(Trans aTrans, ReportBean aReportBean) {
        if (aReportBean.getDateType().length() == 0) {
            aReportBean.setDateType("Add Date");
        }
        aReportBean.setActionMessage("");
        ResultSet rs = null;
        this.setTransList(new ArrayList<>());
        this.setTransListSummary(new ArrayList<>());
        PayTransBean ptb = new PayTransBean();
        String sql = "SELECT t.*,pt.trans_paid_amount,(pt.trans_paid_amount-t.grand_total) as total_debit FROM pay_trans pt INNER JOIN transaction t ON pt.transaction_id=t.transaction_id AND pt.trans_paid_amount>t.grand_total ";
        String sqlsum = "";
        if (aReportBean.getFieldName().length() > 0) {
            sqlsum = "SELECT " + aReportBean.getFieldName() + ",t.transaction_type_id,t.currency_code,sum(t.grand_total) as grand_total,sum(pt.trans_paid_amount) as total_paid,sum(pt.trans_paid_amount-t.grand_total) as total_debit,count(t.transaction_id) as count_n FROM pay_trans pt INNER JOIN transaction t ON pt.transaction_id=t.transaction_id AND pt.trans_paid_amount>t.grand_total";
        } else {
            sqlsum = "SELECT t.transaction_type_id,t.currency_code,sum(t.grand_total) as grand_total,sum(pt.trans_paid_amount) as total_paid,sum(pt.trans_paid_amount-t.grand_total) as total_debit,count(t.transaction_id) as count_n FROM pay_trans pt INNER JOIN transaction t ON pt.transaction_id=t.transaction_id AND pt.trans_paid_amount>t.grand_total";
        }
        String wheresql = "";
        String ordersql = "";
        String ordersqlsum = "";
        String groupbysql = "";
        if (aReportBean.getFieldName().length() > 0) {
            groupbysql = " GROUP BY " + aReportBean.getFieldName() + ",t.transaction_type_id,t.currency_code";
        } else {
            groupbysql = " GROUP BY t.transaction_type_id,t.currency_code";
        }
        if (aTrans.getStoreId() > 0) {
            wheresql = wheresql + " AND t.store_id=" + aTrans.getStoreId();
        }
        if (aTrans.getTransactionNumber().length() > 0) {
            wheresql = wheresql + " AND t.transaction_number='" + aTrans.getTransactionNumber() + "'";
        }
        if (aTrans.getTransactionRef().length() > 0) {
            wheresql = wheresql + " AND t.transaction_ref='" + aTrans.getTransactionRef() + "'";
        }
        if (aTrans.getAddUserDetailId() > 0) {
            wheresql = wheresql + " AND t.add_user_detail_id=" + aTrans.getAddUserDetailId();
        }
        if (aTrans.getTransactionUserDetailId() > 0) {
            wheresql = wheresql + " AND t.transaction_user_detail_id=" + aTrans.getTransactionUserDetailId();
        }
        if (aTrans.getBillTransactorId() > 0) {
            wheresql = wheresql + " AND t.bill_transactor_id=" + aTrans.getBillTransactorId();
        }
        if (aTrans.getTransactorId() > 0) {
            wheresql = wheresql + " AND t.transactor_id=" + aTrans.getTransactorId();
        }
        if (aReportBean.getDateType().length() > 0 && aReportBean.getDate1() != null && aReportBean.getDate2() != null) {
            switch (aReportBean.getDateType()) {
                case "Transaction Date":
                    wheresql = wheresql + " AND t.transaction_date BETWEEN '" + new java.sql.Date(aReportBean.getDate1().getTime()) + "' AND '" + new java.sql.Date(aReportBean.getDate2().getTime()) + "'";
                    break;
                case "Add Date":
                    wheresql = wheresql + " AND t.add_date BETWEEN '" + new java.sql.Timestamp(aReportBean.getDate1().getTime()) + "' AND '" + new java.sql.Timestamp(aReportBean.getDate2().getTime()) + "'";
                    break;
            }
        }
        ordersql = " ORDER BY t.transaction_type_id,t.add_date DESC,t.transaction_id DESC";
        if (aReportBean.getFieldName().length() > 0) {
            ordersqlsum = " ORDER BY " + aReportBean.getFieldName() + ",t.transaction_type_id,t.currency_code";
        } else {
            ordersqlsum = " ORDER BY t.transaction_type_id,t.currency_code";
        }
        sql = sql + wheresql + ordersql;
        sqlsum = sqlsum + wheresql + groupbysql + ordersqlsum;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            Trans trans = null;
            while (rs.next()) {
                trans = new Trans();
                new TransBean().setTransFromResultset(trans, rs);
                trans.setTotalPaid(rs.getDouble("trans_paid_amount"));
                trans.setTotalDebit(rs.getDouble("total_debit"));
                try {
                    trans.setTransactionTypeName(new TransactionTypeBean().getTransactionType(trans.getTransactionTypeId()).getTransactionTypeName());
                } catch (NullPointerException npe) {
                    trans.setTransactionTypeName("");
                }
                this.getTransList().add(trans);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            Trans transsum = null;
            while (rs.next()) {
                transsum = new Trans();
                if (aReportBean.getFieldName().length() > 0) {
                    switch (aReportBean.getFieldName()) {
                        case "add_user_detail_id":
                            try {
                                transsum.setAddUserDetailId(rs.getInt("add_user_detail_id"));
                            } catch (NullPointerException npe) {
                                transsum.setAddUserDetailId(0);
                            }
                            break;
                        case "transaction_user_detail_id":
                            try {
                                transsum.setTransactionUserDetailId(rs.getInt("transaction_user_detail_id"));
                            } catch (NullPointerException npe) {
                                transsum.setTransactionUserDetailId(0);
                            }
                            break;
                        case "bill_transactor_id":
                            try {
                                transsum.setBillTransactorId(rs.getLong("bill_transactor_id"));
                            } catch (NullPointerException npe) {
                                transsum.setBillTransactorId(0);
                            }
                            break;
                        case "transactor_id":
                            try {
                                transsum.setTransactorId(rs.getLong("transactor_id"));
                            } catch (NullPointerException npe) {
                                transsum.setTransactorId(0);
                            }
                            break;
                        case "transaction_date":
                            try {
                                transsum.setTransactionDate(new Date(rs.getDate("transaction_date").getTime()));
                            } catch (NullPointerException | SQLException npe) {
                                transsum.setTransactionDate(null);
                            }
                            break;
                        case "store_id":
                            try {
                                transsum.setStoreId(rs.getInt("store_id"));
                                Store st = new StoreBean().getStore(transsum.getStoreId());
                                transsum.setStoreName(st.getStoreName());
                            } catch (NullPointerException npe) {
                                transsum.setStoreName("");
                            }
                            break;
                    }
                }
                //
                try {
                    transsum.setTransactionTypeId(rs.getInt("transaction_type_id"));
                    transsum.setTransactionTypeName(new TransactionTypeBean().getTransactionType(transsum.getTransactionTypeId()).getTransactionTypeName());
                } catch (NullPointerException npe) {
                    transsum.setTransactionTypeId(0);
                    transsum.setTransactionTypeName("");
                }
                try {
                    transsum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    transsum.setCurrencyCode("");
                }
                try {
                    transsum.setGrandTotal(rs.getDouble("grand_total"));
                } catch (NullPointerException npe) {
                    transsum.setGrandTotal(0);
                }
                try {
                    transsum.setTotalPaid(rs.getDouble("total_paid"));
                } catch (NullPointerException npe) {
                    transsum.setTotalPaid(0);
                }
                try {
                    transsum.setTotalDebit(rs.getDouble("total_debit"));
                } catch (NullPointerException npe) {
                    transsum.setTotalDebit(0);
                }
                try {
                    transsum.setSite_id(rs.getLong("count_n"));
                } catch (NullPointerException npe) {
                    transsum.setSite_id(0);
                }
                this.getTransListSummary().add(transsum);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void resetReport(Trans aTrans, ReportBean aReportBean, Transactor aBillTransactor, Transactor aTransactor) {
        aReportBean.setActionMessage("");
        try {
            new TransBean().clearTrans(aTrans);
            aTrans.setTransactionTypeId(0);
            aTrans.setTransactionReasonId(0);
        } catch (NullPointerException npe) {
        }
        try {
            new TransactorBean().clearTransactor(aBillTransactor);
        } catch (NullPointerException npe) {
        }
        try {
            new TransactorBean().clearTransactor(aTransactor);
        } catch (NullPointerException npe) {
        }
        try {
            aReportBean.setDateType("");
            aReportBean.setDate1(null);
            aReportBean.setDate2(null);
            aReportBean.setFieldName("");
            aReportBean.getTransList().clear();
            aReportBean.getTransListSummary().clear();
        } catch (NullPointerException npe) {
        }
    }

    public List<Trans> getTransHistory(long aTransId) {
        List<Trans> aList = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM transaction_hist WHERE transaction_id=" + aTransId + " ORDER BY hist_add_date DESC";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                aList.add(new TransBean().getTransHistFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return aList;
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
     * @return the DateType
     */
    public String getDateType() {
        return DateType;
    }

    /**
     * @param DateType the DateType to set
     */
    public void setDateType(String DateType) {
        this.DateType = DateType;
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
     * @return the FieldName
     */
    public String getFieldName() {
        return FieldName;
    }

    /**
     * @param FieldName the FieldName to set
     */
    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    /**
     * @return the TransList
     */
    public List<Trans> getTransList() {
        return TransList;
    }

    /**
     * @param TransList the TransList to set
     */
    public void setTransList(List<Trans> TransList) {
        this.TransList = TransList;
    }

    /**
     * @return the TransListSummary
     */
    public List<Trans> getTransListSummary() {
        return TransListSummary;
    }

    /**
     * @param TransListSummary the TransListSummary to set
     */
    public void setTransListSummary(List<Trans> TransListSummary) {
        this.TransListSummary = TransListSummary;
    }

    /**
     * @return the TransItemList
     */
    public List<TransItem> getTransItemList() {
        return TransItemList;
    }

    /**
     * @param TransItemList the TransItemList to set
     */
    public void setTransItemList(List<TransItem> TransItemList) {
        this.TransItemList = TransItemList;
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
