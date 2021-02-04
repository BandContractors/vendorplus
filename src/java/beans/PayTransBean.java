package beans;

import connections.DBConnection;
import entities.Pay;
import entities.PayTrans;
import entities.Transactor;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
public class PayTransBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(PayTransBean.class.getName());

    private List<PayTrans> PayTranss;
    private String ActionMessage = null;
    private PayTrans SelectedPayTrans = null;

    public void savePayTranss(Pay aPay, List<PayTrans> aPayTranss) {
        int ListItemIndex = 0;
        int ListItemNo = 0;
        try {
            ListItemNo = aPayTranss.size();
        } catch (NullPointerException npe) {
            ListItemNo = 0;
        }
        while (ListItemIndex < ListItemNo) {
            aPayTranss.get(ListItemIndex).setPayId(aPay.getPayId());
            if (aPayTranss.get(ListItemIndex).getTransPaidAmount() > 0) {
                this.savePayTrans(aPayTranss.get(ListItemIndex));
            }
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void savePayTrans(PayTrans aPayTrans) {
        String sql = "";
        if (aPayTrans != null) {
            if (aPayTrans.getPayTransId() > 0) {
                sql = "{call sp_update_pay_trans(?,?,?,?,?,?,?,?)}";
            } else {
                sql = "{call sp_insert_pay_trans(?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (aPayTrans.getPayTransId() > 0) {
                    cs.setLong("in_pay_trans_id", aPayTrans.getPayTransId());
                }
                cs.setLong("in_pay_id", aPayTrans.getPayId());
                cs.setLong("in_transaction_id", aPayTrans.getTransactionId());
                cs.setString("in_transaction_number", aPayTrans.getTransactionNumber());
                cs.setDouble("in_trans_paid_amount", aPayTrans.getTransPaidAmount());
                cs.setInt("in_transaction_type_id", aPayTrans.getTransactionTypeId());
                cs.setInt("in_transaction_reason_id", aPayTrans.getTransactionReasonId());
                try {
                    cs.setString("in_account_code", aPayTrans.getAccount_code());
                } catch (NullPointerException npe) {
                    cs.setString("in_account_code", "");
                }
                cs.executeUpdate();
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public PayTrans getPayTrans(long PayTransId) {
        String sql = "{call sp_search_pay_trans_by_pay_trans_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, PayTransId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getPayTransFromResultset(rs);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public PayTrans getPayTransFromResultset(ResultSet rs) {
        PayTrans paytrans = new PayTrans();
        try {
            try {
                paytrans.setPayTransId(rs.getLong("pay_trans_id"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setPayTransId(0);
            }
            try {
                paytrans.setPayId(rs.getLong("pay_id"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setPayId(0);
            }
            try {
                paytrans.setTransactionId(rs.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                paytrans.setTransactionId(0);
            }
            try {
                paytrans.setTransactionNumber(rs.getString("transaction_number"));
            } catch (NullPointerException npe) {
                paytrans.setTransactionNumber("");
            }
            try {
                paytrans.setTransactionRef(rs.getString("transaction_ref"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setTransactionRef("");
            }
            try {
                paytrans.setTransPaidAmount(rs.getDouble("trans_paid_amount"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setTransPaidAmount(0);
            }
            try {
                paytrans.setTransactionTypeId(rs.getInt("transaction_type_id"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setTransactionTypeId(0);
            }
            try {
                paytrans.setTransactionReasonId(rs.getInt("transaction_reason_id"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setTransactionReasonId(0);
            }
            try {
                paytrans.setSumTransPaidAmount(rs.getDouble("sum_trans_paid_amount"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setSumTransPaidAmount(0);
            }
            try {
                paytrans.setGrandTotal(rs.getDouble("grand_total"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setGrandTotal(0);
            }
            try {
                paytrans.setAccount_code(rs.getString("account_code"));
            } catch (NullPointerException | SQLException npe) {
                paytrans.setAccount_code("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return paytrans;
    }

    public void clearPayTrans(PayTrans paytrans) {
        paytrans.setPayTransId(0);
        paytrans.setPayId(0);
        paytrans.setTransactionId(0);
        paytrans.setTransactionNumber("");
        paytrans.setTransPaidAmount(0);
        paytrans.setTransactionTypeId(0);
        paytrans.setTransactionReasonId(0);
        paytrans.setAccount_code("");
    }

    public void initClearPayTrans(PayTrans paytrans, Transactor transactor) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                paytrans.setPayTransId(0);
                paytrans.setPayId(0);
                paytrans.setTransactionId(0);
                paytrans.setTransactionNumber("");
                paytrans.setTransPaidAmount(0);
                paytrans.setTransactionTypeId(0);
                paytrans.setTransactionReasonId(0);
                paytrans.setAccount_code("");
                new TransactorBean().clearTransactor(transactor);
            } catch (Exception e) {
            }
        }
    }

    public List<PayTrans> getPayTranssByTransaction(long TransId) {
        String sql = "{call sp_search_pay_trans_by_transaction_id(?)}";
        ResultSet rs = null;
        setPayTranss(new ArrayList<PayTrans>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, TransId);
            rs = ps.executeQuery();
            while (rs.next()) {
                getPayTranss().add(this.getPayTransFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getPayTranss();
    }

    public List<PayTrans> getPayTranssByPayId(long aPayId) {
        String sql = "{call sp_search_pay_trans_by_pay_id(?)}";
        ResultSet rs = null;
        setPayTranss(new ArrayList<PayTrans>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aPayId);
            rs = ps.executeQuery();
            while (rs.next()) {
                getPayTranss().add(this.getPayTransFromResultset(rs));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return getPayTranss();
    }

    public double getTotalPaidByOrderRef(String aOrderNumber) {
        String sql = "select ifnull(sum(pt.trans_paid_amount),0) as total_paid from pay_trans pt inner join transaction t on pt.transaction_id=t.transaction_id and t.transaction_ref='" + aOrderNumber + "'";
        ResultSet rs = null;
        double totalpay = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                totalpay = rs.getDouble("total_paid");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return totalpay;
    }

    public double getTotalPaidByTransId(long aTransId) {
        String sql = "select ifnull(sum(pt.trans_paid_amount),0) as total_paid from pay_trans pt where pt.transaction_id=" + aTransId;
        ResultSet rs = null;
        double totalpay = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                totalpay = rs.getDouble("total_paid");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return totalpay;
    }

    public double getTotalBalByTransId(long aTransId) {
        String sql = "select t.transaction_id,t.grand_total,ifnull((select sum(pt.trans_paid_amount) from pay_trans pt where pt.transaction_id=t.transaction_id),0) as total_paid_calc from transaction t where t.transaction_id=" + aTransId;
        ResultSet rs = null;
        double totalbal = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                totalbal = rs.getDouble("grand_total") - rs.getDouble("total_paid_calc");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return totalbal;
    }

    public void updateTransTotalPaid(long aTransId) {
        String sql = "UPDATE transaction SET total_paid=(select ifnull(sum(pt.trans_paid_amount),0) from pay_trans pt where pt.transaction_id=" + aTransId + ") WHERE transaction_id>0 AND transaction_id=" + aTransId;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void updateTranssTotalPaid(List<PayTrans> aPayTranss) {
        try {
            if (null == aPayTranss) {
                //do nothing
            } else {
                if (aPayTranss.size() > 0) {
                    for (int i = 0; i < aPayTranss.size(); i++) {
                        this.updateTransTotalPaid(aPayTranss.get(i).getTransactionId());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshSalePayTranssSummaryByTransactor(long aTransactorId, String aCurrencyCode) {
        String sql = "{call sp_search_sum_sale_pay_trans_by_transactor(?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorId);
            ps.setString(2, aCurrencyCode);
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshSalePayTranssSummaryByTransactor2(long aTransactorId, String aCurrencyCode, Date aDate1, Date aDate2) {
        String sql = "{call sp_search_sum_sale_pay_trans_by_transactor2(?,?,?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorId);
            ps.setString(2, aCurrencyCode);
            try {
                ps.setDate(3, new java.sql.Date(aDate1.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(3, null);
            }
            try {
                ps.setDate(4, new java.sql.Date(aDate2.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(4, null);
            }
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshSalePayTranssSummaryByTransactor3(long aTransactorId, String aCurrencyCode, Date aDate1, Date aDate2) {
        String sql = "{call sp_search_sum_sale_pay_trans_by_transactor3(?,?,?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorId);
            ps.setString(2, aCurrencyCode);
            try {
                ps.setDate(3, new java.sql.Date(aDate1.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(3, null);
            }
            try {
                ps.setDate(4, new java.sql.Date(aDate2.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(4, null);
            }
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                PayTranss.add(pt);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshSalePayTranssSummaryByTransNo(String aTransactionNo, String aCurrencyCode) {
        String sql = "{call sp_search_sum_sale_pay_trans_by_transno(?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransactionNo);
            ps.setString(2, aCurrencyCode);
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshSalePayTranssSummaryByTransNo2(String aTransactionNo, String aCurrencyCode, Date aDate1, Date aDate2) {
        String sql = "{call sp_search_sum_sale_pay_trans_by_transno2(?,?,?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransactionNo);
            ps.setString(2, aCurrencyCode);
            try {
                ps.setDate(3, new java.sql.Date(aDate1.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(3, null);
            }
            try {
                ps.setDate(4, new java.sql.Date(aDate2.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(4, null);
            }
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshPurchasePayTranssSummaryByTransactor(long aTransactorId, String aCurrencyCode) {
        String sql = "{call sp_search_sum_purchase_pay_trans_by_transactor(?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorId);
            ps.setString(2, aCurrencyCode);
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshPurchasePayTranssSummaryByTransactor2(long aTransactorId, String aCurrencyCode, Date aDate1, Date aDate2) {
        String sql = "{call sp_search_sum_purchase_pay_trans_by_transactor2(?,?,?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorId);
            ps.setString(2, aCurrencyCode);
            try {
                ps.setDate(3, new java.sql.Date(aDate1.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(3, null);
            }
            try {
                ps.setDate(4, new java.sql.Date(aDate2.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(4, null);
            }
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshPurchasePayTranssSummaryByTransNo(String aTransactionNo, String aCurrencyCode) {
        String sql = "{call sp_search_sum_purchase_pay_trans_by_transno(?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransactionNo);
            ps.setString(2, aCurrencyCode);
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshPurchasePayTranssSummaryByTransNo2(String aTransactionNo, String aCurrencyCode, Date aDate1, Date aDate2) {
        String sql = "{call sp_search_sum_purchase_pay_trans_by_transno2(?,?,?,?)}";
        ResultSet rs = null;
        PayTranss = new ArrayList<PayTrans>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aTransactionNo);
            ps.setString(2, aCurrencyCode);
            try {
                ps.setDate(3, new java.sql.Date(aDate1.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(3, null);
            }
            try {
                ps.setDate(4, new java.sql.Date(aDate2.getTime()));
            } catch (NullPointerException npe) {
                ps.setDate(4, null);
            }
            rs = ps.executeQuery();
            PayTrans pt = new PayTrans();
            while (rs.next()) {
                pt = this.getPayTransFromResultset(rs);
                if (pt.getGrandTotal() > pt.getSumTransPaidAmount()) {
                    PayTranss.add(pt);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshPayTranssByBillTrPayCatTransNo(String aTransactionNumber, long aBillTransactorId, String aPayCategory, String aCurrencyCode) {
        if (aBillTransactorId != 0) {
            if (aPayCategory.equals("IN")) {
                this.refreshSalePayTranssSummaryByTransactor(aBillTransactorId, aCurrencyCode);
            } else if (aPayCategory.equals("OUT")) {
                this.refreshPurchasePayTranssSummaryByTransactor(aBillTransactorId, aCurrencyCode);
            } else {

            }
        } else if (aTransactionNumber.length() > 0) {
            try {
                if (aPayCategory.equals("IN")) {
                    this.refreshSalePayTranssSummaryByTransNo(aTransactionNumber, aCurrencyCode);
                } else if (aPayCategory.equals("OUT")) {
                    this.refreshPurchasePayTranssSummaryByTransNo(aTransactionNumber, aCurrencyCode);
                }
            } catch (NullPointerException npe) {
                PayTranss = null;
            }
        } else {
            PayTranss = null;
        }
    }

    public void refreshPayTranssByBillTrPayCatTransNo2(String aTransactionNumber, long aBillTransactorId, String aPayCategory, String aCurrencyCode, Date aDate1, Date aDate2) {
        if (aBillTransactorId != 0) {
            if (aPayCategory.equals("IN")) {
                this.refreshSalePayTranssSummaryByTransactor2(aBillTransactorId, aCurrencyCode, aDate1, aDate2);
            } else if (aPayCategory.equals("OUT")) {
                this.refreshPurchasePayTranssSummaryByTransactor2(aBillTransactorId, aCurrencyCode, aDate1, aDate2);
            } else {

            }
        } else if (aTransactionNumber.length() > 0) {
            try {
                if (aPayCategory.equals("IN")) {
                    this.refreshSalePayTranssSummaryByTransNo2(aTransactionNumber, aCurrencyCode, aDate1, aDate2);
                } else if (aPayCategory.equals("OUT")) {
                    this.refreshPurchasePayTranssSummaryByTransNo2(aTransactionNumber, aCurrencyCode, aDate1, aDate2);
                }
            } catch (NullPointerException npe) {
                PayTranss = null;
            }
        } else {
            PayTranss = null;
        }
    }

    public void refreshPayTranssByBillTrPayCatTransNo3(String aTransactionNumber, long aBillTransactorId, String aPayCategory, String aCurrencyCode, Date aDate1, Date aDate2) {
        if (aBillTransactorId != 0) {
            if (aPayCategory.equals("IN")) {
                this.refreshSalePayTranssSummaryByTransactor3(aBillTransactorId, aCurrencyCode, aDate1, aDate2);
            } else if (aPayCategory.equals("OUT")) {
                this.refreshPurchasePayTranssSummaryByTransactor2(aBillTransactorId, aCurrencyCode, aDate1, aDate2);
            } else {

            }
        } else if (aTransactionNumber.length() > 0) {
            try {
                if (aPayCategory.equals("IN")) {
                    this.refreshSalePayTranssSummaryByTransNo2(aTransactionNumber, aCurrencyCode, aDate1, aDate2);
                } else if (aPayCategory.equals("OUT")) {
                    this.refreshPurchasePayTranssSummaryByTransNo2(aTransactionNumber, aCurrencyCode, aDate1, aDate2);
                }
            } catch (NullPointerException npe) {
                PayTranss = null;
            }
        } else {
            PayTranss = null;
        }
    }

    public void clearPayTranss(List<PayTrans> aPayTranss, Pay aPay) {
        try {
            if (null != aPayTranss) {
                aPayTranss.clear();
            }
            if (null != aPay) {
                aPay.setPaidAmount(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void editPayTrans(List<PayTrans> aPayTranss, PayTrans aPayTrans, Pay aPay) {
        int ListItemIndex = 0;
        int ListItemNo = aPayTranss.size();
        double GTotal = 0;
        while (ListItemIndex < ListItemNo) {
            GTotal = GTotal + (aPayTranss.get(ListItemIndex).getTransPaidAmount());
            ListItemIndex = ListItemIndex + 1;
        }
        aPay.setPaidAmount(GTotal);
    }

    public String getRecGreaterBalStr(List<PayTrans> aPayTranss) {
        String RecGreaterBal = "";
        int ListItemIndex = 0;
        int ListItemNo = aPayTranss.size();
        while (ListItemIndex < ListItemNo) {
            if (aPayTranss.get(ListItemIndex).getTransPaidAmount() > (aPayTranss.get(ListItemIndex).getGrandTotal() - aPayTranss.get(ListItemIndex).getSumTransPaidAmount())) {
                RecGreaterBal = aPayTranss.get(ListItemIndex).getTransactionNumber();
                break;
            }
            if (aPayTranss.get(ListItemIndex).getTransPaidAmount() > this.getTotalBalByTransId(aPayTranss.get(ListItemIndex).getTransactionId())) {
                RecGreaterBal = aPayTranss.get(ListItemIndex).getTransactionNumber();
                break;
            }
            ListItemIndex = ListItemIndex + 1;
        }
        return RecGreaterBal;
    }

    public String getPaidForSummary(long aPayId) {
        String PaidForSummary = "";
        List<PayTrans> pts = this.getPayTranssByPayId(aPayId);
        PayTrans pt = null;
        for (int i = 0; i < pts.size(); i++) {
            pt = pts.get(i);
            String AccountName = "";
            String TransNo = "";
            try {
                if (pt.getAccount_code().length() > 0) {
                    AccountName = new AccCoaBean().getAccCoaByCodeOrId(pt.getAccount_code(), 0).getAccountName();
                }
            } catch (Exception e) {
                //do nothing 
            }
            try {
                if (pt.getTransactionNumber().length() > 0) {
                    TransNo = pt.getTransactionNumber();
                }
            } catch (Exception e) {
                //do nothing 
            }
            if (AccountName.length() > 0) {
                if (PaidForSummary.length() > 0) {
                    PaidForSummary = PaidForSummary + ", " + AccountName;
                } else {
                    PaidForSummary = AccountName;
                }
            }
            if (TransNo.length() > 0) {
                if (PaidForSummary.length() > 0) {
                    PaidForSummary = PaidForSummary + ", " + TransNo;
                } else {
                    PaidForSummary = TransNo;
                }
            }
        }
        return PaidForSummary;
    }

    /**
     * @return the PayTranss
     */
    public List<PayTrans> getPayTranss() {
        return PayTranss;
    }

    /**
     * @param PayTranss the PayTranss to set
     */
    public void setPayTranss(List<PayTrans> PayTranss) {
        this.PayTranss = PayTranss;
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
     * @return the SelectedPayTrans
     */
    public PayTrans getSelectedPayTrans() {
        return SelectedPayTrans;
    }

    /**
     * @param SelectedPayTrans the SelectedPayTrans to set
     */
    public void setSelectedPayTrans(PayTrans SelectedPayTrans) {
        this.SelectedPayTrans = SelectedPayTrans;
    }

}
