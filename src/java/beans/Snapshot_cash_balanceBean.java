package beans;

import connections.DBConnection;
import entities.Cdc_general;
import entities.Snapshot_cash_balance;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Snapshot_cash_balanceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Snapshot_cash_balance> Snapshot_cash_balanceList;
    private Snapshot_cash_balance Snapshot_cash_balanceObj;

    public void setSnapshot_cash_balanceFromResultset(Snapshot_cash_balance aSnapshot_cash_balance, ResultSet aResultSet) {
        try {
            try {
                aSnapshot_cash_balance.setSnapshot_cash_balance_id(aResultSet.getLong("snapshot_cash_balance_id"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setSnapshot_cash_balance_id(0);
            }
            try {
                aSnapshot_cash_balance.setSnapshot_no(aResultSet.getLong("snapshot_no"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setSnapshot_no(0);
            }
            try {
                aSnapshot_cash_balance.setSnapshot_date(new Date(aResultSet.getTimestamp("snapshot_date").getTime()));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setSnapshot_date(null);
            }
            try {
                aSnapshot_cash_balance.setAcc_period_id(aResultSet.getInt("acc_period_id"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setAcc_period_id(0);
            }
            try {
                aSnapshot_cash_balance.setCdc_id(aResultSet.getString("cdc_id"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setCdc_id("");
            }
            try {
                aSnapshot_cash_balance.setAccount_code(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setAccount_code("");
            }
            try {
                aSnapshot_cash_balance.setAcc_child_account_id(aResultSet.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setAcc_child_account_id(0);
            }
            try {
                aSnapshot_cash_balance.setCurrency_code(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setCurrency_code("");
            }
            try {
                aSnapshot_cash_balance.setDebit_amount(aResultSet.getDouble("debit_amount"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setDebit_amount(0);
            }
            try {
                aSnapshot_cash_balance.setCredit_amount(aResultSet.getDouble("credit_amount"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setCredit_amount(0);
            }
            try {
                aSnapshot_cash_balance.setDebit_amount_lc(aResultSet.getDouble("debit_amount_lc"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setDebit_amount_lc(0);
            }
            try {
                aSnapshot_cash_balance.setCredit_amount_lc(aResultSet.getDouble("credit_amount_lc"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setCredit_amount_lc(0);
            }
            try {
                aSnapshot_cash_balance.setDebit_balance(aResultSet.getDouble("debit_balance"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setDebit_balance(0);
            }
            try {
                aSnapshot_cash_balance.setDebit_balance_lc(aResultSet.getDouble("debit_balance_lc"));
            } catch (NullPointerException npe) {
                aSnapshot_cash_balance.setDebit_balance_lc(0);
            }
        } catch (Exception e) {
            System.err.println("setSnapshot_cash_balanceFromResultset:" + e.getMessage());
        }
    }

    public Snapshot_cash_balance getSnapshot_cash_balance(Long aSnapshot_cash_balance_id) {
        String sql = "SLECT * FROM snapshot_cash_balance WHERE snapshot_cash_balance_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aSnapshot_cash_balance_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Snapshot_cash_balance ssv = new Snapshot_cash_balance();
                this.setSnapshot_cash_balanceFromResultset(ssv, rs);
                return ssv;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("getSnapshot_cash_balance:" + e.getMessage());
            return null;
        }
    }

    public long getRecordsByCdc_id(String aCdc_id) {
        String sql = "SELECT count(*) as cdc_id FROM snapshot_cash_balance WHERE cdc_id=?";
        ResultSet rs = null;
        long n = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCdc_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    n = rs.getLong("cdc_id");
                } catch (NullPointerException npe) {

                }
            }
        } catch (Exception e) {
            System.err.println("getRecordsByCdc_id:" + e.getMessage());
        }
        return n;
    }

    public int insertSnapshot_cash_balance(Cdc_general aCdc_general) {
        int saved = 0;
        String sql = "{call sp_insert_snapshot_cash_balance(?,?,?,?)}";
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
            System.err.println("insertSnapshot_cash_balance:" + e.getMessage());
        }
        return saved;
    }

    public void clearSnapshot_cash_balance(Snapshot_cash_balance aSnapshot_cash_balance) {
        aSnapshot_cash_balance.setSnapshot_cash_balance_id(0);
        aSnapshot_cash_balance.setSnapshot_no(0);
        aSnapshot_cash_balance.setSnapshot_date(null);
        aSnapshot_cash_balance.setAcc_period_id(0);
        aSnapshot_cash_balance.setCdc_id("");
        aSnapshot_cash_balance.setAccount_code("");
        aSnapshot_cash_balance.setAcc_child_account_id(0);
        aSnapshot_cash_balance.setCurrency_code("");
        aSnapshot_cash_balance.setDebit_amount(0);
        aSnapshot_cash_balance.setCredit_amount(0);
        aSnapshot_cash_balance.setDebit_amount_lc(0);
        aSnapshot_cash_balance.setCredit_amount_lc(0);
        aSnapshot_cash_balance.setDebit_balance(0);
        aSnapshot_cash_balance.setDebit_balance_lc(0);
    }

    /**
     * @return the Snapshot_cash_balanceList
     */
    public List<Snapshot_cash_balance> getSnapshot_cash_balanceList() {
        return Snapshot_cash_balanceList;
    }

    /**
     * @param Snapshot_cash_balanceList the Snapshot_cash_balanceList to set
     */
    public void setSnapshot_cash_balanceList(List<Snapshot_cash_balance> Snapshot_cash_balanceList) {
        this.Snapshot_cash_balanceList = Snapshot_cash_balanceList;
    }

    /**
     * @return the Snapshot_cash_balanceObj
     */
    public Snapshot_cash_balance getSnapshot_cash_balanceObj() {
        return Snapshot_cash_balanceObj;
    }

    /**
     * @param Snapshot_cash_balanceObj the Snapshot_cash_balanceObj to set
     */
    public void setSnapshot_cash_balanceObj(Snapshot_cash_balance Snapshot_cash_balanceObj) {
        this.Snapshot_cash_balanceObj = Snapshot_cash_balanceObj;
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
