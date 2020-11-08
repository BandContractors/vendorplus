package beans;

import connections.DBConnection;
import entities.Cdc_general;
import entities.Cash_balancing_daily;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
public class Cash_balancing_dailyBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Cash_balancing_daily> Cash_balancing_dailyList;
    private Cash_balancing_daily Cash_balancing_dailyObj;

    public void setCash_balancing_dailyFromResultset(Cash_balancing_daily aCash_balancing_daily, ResultSet aResultSet) {
        try {
            try {
                aCash_balancing_daily.setCash_balancing_daily_id(aResultSet.getLong("cash_balancing_daily_id"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_balancing_daily_id(0);
            }
            try {
                aCash_balancing_daily.setBalancing_date(new Date(aResultSet.getDate("balancing_date").getTime()));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setBalancing_date(null);
            }
            try {
                aCash_balancing_daily.setBalancing_user(aResultSet.getInt("balancing_user"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setBalancing_user(0);
            }
            try {
                aCash_balancing_daily.setAccount_code(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setAccount_code("");
            }
            try {
                aCash_balancing_daily.setAcc_child_account_id(aResultSet.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setAcc_child_account_id(0);
            }
            try {
                aCash_balancing_daily.setCurrency_code(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCurrency_code("");
            }
            try {
                aCash_balancing_daily.setCash_at_begin(aResultSet.getDouble("cash_at_begin"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_at_begin(0);
            }
            try {
                aCash_balancing_daily.setCash_transfer_in(aResultSet.getDouble("cash_transfer_in"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_transfer_in(0);
            }
            try {
                aCash_balancing_daily.setCash_adjustment_pos(aResultSet.getDouble("cash_adjustment_pos"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_adjustment_pos(0);
            }
            try {
                aCash_balancing_daily.setCash_receipts(aResultSet.getDouble("cash_receipts"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_receipts(0);
            }
            try {
                aCash_balancing_daily.setCash_transfer_out(aResultSet.getDouble("cash_transfer_out"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_transfer_out(0);
            }
            try {
                aCash_balancing_daily.setCash_adjustment_neg(aResultSet.getDouble("cash_adjustment_neg"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_adjustment_neg(0);
            }
            try {
                aCash_balancing_daily.setCash_payments(aResultSet.getDouble("cash_payments"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_payments(0);
            }
            try {
                aCash_balancing_daily.setCash_balance(aResultSet.getDouble("cash_balance"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_balance(0);
            }
            try {
                aCash_balancing_daily.setActual_cash_count(aResultSet.getDouble("actual_cash_count"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setActual_cash_count(0);
            }
            try {
                aCash_balancing_daily.setCash_over(aResultSet.getDouble("cash_over"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_over(0);
            }
            try {
                aCash_balancing_daily.setCash_at_begin(aResultSet.getDouble("cash_short"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setCash_short(0);
            }
            try {
                aCash_balancing_daily.setAdd_user_detail_id(aResultSet.getInt("add_user_detail_id"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setAdd_user_detail_id(0);
            }
            try {
                aCash_balancing_daily.setEdit_user_detail_id(aResultSet.getInt("edit_user_detail_id"));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setEdit_user_detail_id(0);
            }
            try {
                aCash_balancing_daily.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setAdd_date(null);
            }
            try {
                aCash_balancing_daily.setEdit_date(new Date(aResultSet.getTimestamp("edit_date").getTime()));
            } catch (NullPointerException npe) {
                aCash_balancing_daily.setEdit_date(null);
            }
        } catch (Exception e) {
            System.err.println("setCash_balancing_dailyFromResultset:" + e.getMessage());
        }
    }

    public Cash_balancing_daily getCash_balancing_daily(Long aCash_balancing_daily_id) {
        String sql = "SLECT * FROM cash_balancing_daily WHERE cash_balancing_daily_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aCash_balancing_daily_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Cash_balancing_daily ssv = new Cash_balancing_daily();
                this.setCash_balancing_dailyFromResultset(ssv, rs);
                return ssv;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("getCash_balancing_daily:" + e.getMessage());
            return null;
        }
    }

    public int insertCash_balancing_daily(Cdc_general aCdc_general) {
        int saved = 0;
        String sql = "{call sp_insert_cash_balancing_daily(?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aCdc_general) {
                cs.setInt("in_acc_period_id", aCdc_general.getAcc_period_id());
                cs.setLong("in_snapshot_no", aCdc_general.getSnapshot_no());
                cs.setTimestamp("in_snapshot_date", new java.sql.Timestamp(aCdc_general.getCdc_date().getTime()));
                cs.setString("in_cdc_id", aCdc_general.getCdc_id());
                cs.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            System.err.println("insertCash_balancing_daily:" + e.getMessage());
        }
        return saved;
    }

    public void clearCash_balancing_daily(Cash_balancing_daily aCash_balancing_daily) {
        aCash_balancing_daily.setCash_balancing_daily_id(0);
        aCash_balancing_daily.setBalancing_date(null);
        aCash_balancing_daily.setBalancing_user(0);
        aCash_balancing_daily.setAccount_code("");
        aCash_balancing_daily.setAcc_child_account_id(0);
        aCash_balancing_daily.setCurrency_code("");
        aCash_balancing_daily.setCash_at_begin(0);
        aCash_balancing_daily.setCash_transfer_in(0);
        aCash_balancing_daily.setCash_adjustment_pos(0);
        aCash_balancing_daily.setCash_receipts(0);
        aCash_balancing_daily.setCash_transfer_out(0);
        aCash_balancing_daily.setCash_adjustment_neg(0);
        aCash_balancing_daily.setCash_payments(0);
        aCash_balancing_daily.setCash_balance(0);
        aCash_balancing_daily.setActual_cash_count(0);
        aCash_balancing_daily.setCash_over(0);
        aCash_balancing_daily.setCash_short(0);
        aCash_balancing_daily.setAdd_user_detail_id(0);
        aCash_balancing_daily.setEdit_user_detail_id(0);
        aCash_balancing_daily.setAdd_date(null);
        aCash_balancing_daily.setEdit_date(null);
    }

    /**
     * @return the Cash_balancing_dailyList
     */
    public List<Cash_balancing_daily> getCash_balancing_dailyList() {
        return Cash_balancing_dailyList;
    }

    /**
     * @param Cash_balancing_dailyList the Cash_balancing_dailyList to set
     */
    public void setCash_balancing_dailyList(List<Cash_balancing_daily> Cash_balancing_dailyList) {
        this.Cash_balancing_dailyList = Cash_balancing_dailyList;
    }

    /**
     * @return the Cash_balancing_dailyObj
     */
    public Cash_balancing_daily getCash_balancing_dailyObj() {
        return Cash_balancing_dailyObj;
    }

    /**
     * @param Cash_balancing_dailyObj the Cash_balancing_dailyObj to set
     */
    public void setCash_balancing_dailyObj(Cash_balancing_daily Cash_balancing_dailyObj) {
        this.Cash_balancing_dailyObj = Cash_balancing_dailyObj;
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
