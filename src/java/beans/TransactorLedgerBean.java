package beans;

import connections.DBConnection;
import entities.TransactorLedger;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "transactorLedgerBean")
@SessionScoped
public class TransactorLedgerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    List<TransactorLedger> ReportTransactorLedger = new ArrayList<TransactorLedger>();
    List<TransactorLedger> ReportTransactorLedgerSummary = new ArrayList<TransactorLedger>();
    List<TransactorLedger> ReportTransactorLedgerSummaryAll = new ArrayList<TransactorLedger>();
    List<TransactorLedger> ReportTransactorLedgerSummaryAllIndividual = new ArrayList<TransactorLedger>();
    private TransactorLedger CurrentTransactorLedger = new TransactorLedger();

    public void saveTransactorLedger(TransactorLedger aTransactorLedger) {
        String sql = null;
        if (aTransactorLedger.getTransactorLedgerId() == 0) {
            sql = "{call sp_insert_transactor_ledger(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else {
            sql = "{call sp_update_transactor_ledger(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt("in_store_id", aTransactorLedger.getStoreId());
            cs.setString("in_store_name", aTransactorLedger.getStoreName());
            cs.setLong("in_transaction_id", aTransactorLedger.getTransactionId());
            cs.setLong("in_pay_id", aTransactorLedger.getPayId());
            cs.setString("in_transaction_type_name", aTransactorLedger.getTransactionTypeName());
            cs.setString("in_description", aTransactorLedger.getDescription());
            cs.setDate("in_transaction_date", new java.sql.Date(aTransactorLedger.getTransactionDate().getTime()));
            cs.setLong("in_transactor_id", aTransactorLedger.getTransactorId());
            cs.setString("in_transactor_names", aTransactorLedger.getTransactorNames());
            cs.setLong("in_bill_transactor_id", aTransactorLedger.getBillTransactorId());
            cs.setString("in_bill_transactor_names", aTransactorLedger.getBillTransactorNames());
            cs.setString("in_ledger_entry_type", aTransactorLedger.getLedgerEntryType());
            cs.setDouble("in_amount_debit", aTransactorLedger.getAmountDebit());
            cs.setDouble("in_amount_credit", aTransactorLedger.getAmountCredit());
            if (aTransactorLedger.getTransactorLedgerId() > 0) {
                cs.setLong("in_transactor_ledger_id", aTransactorLedger.getTransactorLedgerId());
                cs.setTimestamp("in_add_date", new java.sql.Timestamp(aTransactorLedger.getAddDate().getTime()));
            }
            cs.executeUpdate();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public TransactorLedger getTransactorLedger(long aTransactorLedgerId) {
        String sql = "{call sp_search_transactor_ledger_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactorLedgerId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransactorLedgerFromResultset(rs);
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public TransactorLedger getTransactorLedgerByTransIdTransType(long aTransactionId, String aTransactionTypeName) {
        String sql = "{call sp_search_transactor_ledger_by_transid_transtype(?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            ps.setString(2, aTransactionTypeName);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransactorLedgerFromResultset(rs);
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public TransactorLedger getTransactorLedgerByMinTransIdTransTypeDesc(long aTransactionId, String aTransactionTypeName, String aDescription) {
        String sql = "{call sp_search_transactor_ledger_by_min_transid_type_desc(?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransactionId);
            ps.setString(2, aTransactionTypeName);
            ps.setString(3, aDescription);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getTransactorLedgerFromResultset(rs);
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public TransactorLedger getTransactorLedgerFromResultset(ResultSet aResultSet) {
        try {
            TransactorLedger transactorLedger = new TransactorLedger();
            transactorLedger.setTransactorLedgerId(aResultSet.getLong("transactor_ledger_id"));
            try {
                transactorLedger.setStoreId(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                transactorLedger.setStoreId(0);
            }
            try {
                transactorLedger.setStoreName(aResultSet.getString("store_name"));
            } catch (NullPointerException npe) {
                transactorLedger.setStoreName("");
            }
            try {
                transactorLedger.setTransactionId(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                transactorLedger.setTransactionId(0);
            }
            try {
                transactorLedger.setPayId(aResultSet.getLong("pay_id"));
            } catch (NullPointerException npe) {
                transactorLedger.setPayId(0);
            }
            try {
                transactorLedger.setTransactionTypeName(aResultSet.getString("transaction_type_name"));
            } catch (NullPointerException npe) {
                transactorLedger.setTransactionTypeName("");
            }
            try {
                transactorLedger.setDescription(aResultSet.getString("description"));
            } catch (NullPointerException npe) {
                transactorLedger.setDescription("");
            }
            try {
                transactorLedger.setTransactionDate(new Date(aResultSet.getDate("transaction_date").getTime()));
            } catch (NullPointerException npe) {
                transactorLedger.setTransactionDate(null);
            }
            try {
                transactorLedger.setTransactorId(aResultSet.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                transactorLedger.setTransactorId(0);
            }
            try {
                transactorLedger.setTransactorNames(aResultSet.getString("transactor_names"));
            } catch (NullPointerException npe) {
                transactorLedger.setTransactorNames("");
            }
            try {
                transactorLedger.setBillTransactorId(aResultSet.getLong("bill_transactor_id"));
            } catch (NullPointerException npe) {
                transactorLedger.setBillTransactorId(0);
            }
            try {
                transactorLedger.setBillTransactorNames(aResultSet.getString("bill_transactor_names"));
            } catch (NullPointerException npe) {
                transactorLedger.setBillTransactorNames("");
            }
            try {
                transactorLedger.setLedgerEntryType(aResultSet.getString("ledger_entry_type"));
            } catch (NullPointerException npe) {
                transactorLedger.setLedgerEntryType("");
            }
            try {
                transactorLedger.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                transactorLedger.setAddDate(null);
            }
            try {
                transactorLedger.setAmountDebit(aResultSet.getDouble("amount_debit"));
            } catch (NullPointerException npe) {
                transactorLedger.setAmountDebit(0);
            }
            try {
                transactorLedger.setAmountCredit(aResultSet.getDouble("amount_credit"));
            } catch (NullPointerException npe) {
                transactorLedger.setAmountCredit(0);
            }
            return transactorLedger;

        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        }

    }

    public List<TransactorLedger> getReportTransactorLedger(TransactorLedger aTransactorLedger) {
        String sql;
        sql = "{call sp_report_transactor_ledger(?,?,?,?,?,?,?)}";
        ResultSet rs = null;
        this.ReportTransactorLedger.clear();
        if (aTransactorLedger != null) {
            if (aTransactorLedger.getTransactionDate() == null && aTransactorLedger.getTransactionDate2() == null
                    && aTransactorLedger.getAddDate() == null && aTransactorLedger.getAddDate2() == null) {
                this.ActionMessage = (("Atleast one date range(TransactionDate or AddDate) is needed..."));
            } else if (aTransactorLedger.getTransactionDate() == null && aTransactorLedger.getTransactionDate2() != null) {
                this.ActionMessage = (("Transaction Date(From) is needed..."));
            } else if (aTransactorLedger.getTransactionDate() != null && aTransactorLedger.getTransactionDate2() == null) {
                this.ActionMessage = (("Transaction Date(T0) is needed..."));
            } else if (aTransactorLedger.getAddDate() == null && aTransactorLedger.getAddDate2() != null) {
                this.ActionMessage = (("Add Date(From) is needed..."));
            } else if (aTransactorLedger.getAddDate() != null && aTransactorLedger.getAddDate2() == null) {
                this.ActionMessage = (("Add Date(To) is needed..."));
            } else {
                try (
                        Connection conn = DBConnection.getMySQLConnection();
                        PreparedStatement ps = conn.prepareStatement(sql);) {
                    try {
                        ps.setInt(1, aTransactorLedger.getStoreId());
                    } catch (NullPointerException npe) {
                        ps.setInt(1, 0);
                    }
                    try {
                        ps.setDate(2, new java.sql.Date(aTransactorLedger.getTransactionDate().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setDate(2, null);
                    }
                    try {
                        ps.setDate(3, new java.sql.Date(aTransactorLedger.getTransactionDate2().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setDate(3, null);
                    }
                    try {
                        ps.setTimestamp(4, new java.sql.Timestamp(aTransactorLedger.getAddDate().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setTimestamp(4, null);
                    }
                    try {
                        ps.setTimestamp(5, new java.sql.Timestamp(aTransactorLedger.getAddDate2().getTime()));
                    } catch (NullPointerException npe) {
                        ps.setTimestamp(5, null);
                    }
                    try {
                        ps.setLong(6, aTransactorLedger.getBillTransactorId());
                    } catch (NullPointerException npe) {
                        ps.setLong(6, 0);
                    }
                    try {
                        ps.setString(7, aTransactorLedger.getTransactionTypeName());
                    } catch (NullPointerException npe) {
                        ps.setString(7, "");
                    }
                    rs = ps.executeQuery();
                    //System.out.println(rs.getStatement());
                    while (rs.next()) {
                        this.ReportTransactorLedger.add(this.getTransactorLedgerFromResultset(rs));
                    }
                    this.ActionMessage = ((""));
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
        }
        return this.ReportTransactorLedger;
    }

    public long getReportTransactionCount() {
        return this.ReportTransactorLedger.size();
    }

    public List<TransactorLedger> getReportTransactorLedgerSummarySingleIndividual(long aBillTransactorId) {
        String sql;
        sql = "{call sp_report_transactor_ledger_summary_single_individual(?)}";
        ResultSet rs = null;
        this.ReportTransactorLedgerSummary.clear();
        if (aBillTransactorId != 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                try {
                    ps.setLong(1, aBillTransactorId);
                } catch (NullPointerException npe) {
                    ps.setLong(1, 0);
                }
                rs = ps.executeQuery();
                //System.out.println(rs.getStatement());
                TransactorLedger TmpTransactorLedger;
                while (rs.next()) {
                    TmpTransactorLedger = new TransactorLedger();
                    TmpTransactorLedger.setSumAmountDebit(rs.getDouble("sum_amount_debit"));
                    TmpTransactorLedger.setSumAmountCredit(rs.getDouble("sum_amount_credit"));
                    if ((rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit")) < 0) {
                        TmpTransactorLedger.setNetDebt(0);
                    } else {
                        TmpTransactorLedger.setNetDebt(rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit"));
                    }
                    if ((rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit")) < 0) {
                        TmpTransactorLedger.setNetCredit(0);
                    } else {
                        TmpTransactorLedger.setNetCredit(rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit"));
                    }
                    this.ReportTransactorLedgerSummary.add(TmpTransactorLedger);
                }
                //this.ActionMessage=((""));
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
        return this.ReportTransactorLedgerSummary;
    }

    public TransactorLedger getCurrentTransactorLedgerByIndividual(long aBillTransactorId) {
        String sql;
        sql = "{call sp_report_transactor_ledger_summary_single_individual(?)}";
        ResultSet rs = null;
        TransactorLedger tl = new TransactorLedger();
        //initialise
        tl.setSumAmountDebit(0);
        tl.setSumAmountCredit(0);
        tl.setNetDebt(0);
        tl.setNetDebt(0);
        //for output only
        tl.setNetDebtCreditAmount(0);
        tl.setNetDebtCreditLabel("BAL");

        if (aBillTransactorId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                try {
                    ps.setLong(1, aBillTransactorId);
                } catch (NullPointerException npe) {
                    ps.setLong(1, 0);
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    tl.setSumAmountDebit(rs.getDouble("sum_amount_debit"));
                    tl.setSumAmountCredit(rs.getDouble("sum_amount_credit"));
                    if (rs.getDouble("sum_amount_debit") > rs.getDouble("sum_amount_credit")) {
                        tl.setNetDebt(rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit"));
                        //for output only
                        tl.setNetDebtCreditAmount(rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit"));
                        tl.setNetDebtCreditLabel("DEBT");
                    }else if (rs.getDouble("sum_amount_credit") > rs.getDouble("sum_amount_debit")) {
                        tl.setNetCredit(rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit"));
                        //output only
                        tl.setNetDebtCreditAmount(rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit"));
                        tl.setNetDebtCreditLabel("CREDIT");
                    }
                }
            } catch (SQLException se) {
                tl.setSumAmountDebit(0);
                tl.setSumAmountCredit(0);
                tl.setNetDebt(0);
                tl.setNetDebt(0);
                //for output only
                tl.setNetDebtCreditAmount(0);
                tl.setNetDebtCreditLabel("BAL");
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
        return tl;
    }

    public List<TransactorLedger> getReportTransactorLedgerSummaryAllIndividual() {
        String sql;
        sql = "{call sp_report_transactor_ledger_summary_all_individual()}";
        ResultSet rs = null;
        this.ReportTransactorLedgerSummaryAllIndividual.clear();
        if (1 == 1) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                //System.out.println(rs.getStatement());
                TransactorLedger TmpTransactorLedger;
                while (rs.next()) {
                    TmpTransactorLedger = new TransactorLedger();
                    TmpTransactorLedger.setBillTransactorNames(rs.getString("bill_transactor_names"));
                    TmpTransactorLedger.setSumAmountDebit(rs.getDouble("sum_amount_debit"));
                    TmpTransactorLedger.setSumAmountCredit(rs.getDouble("sum_amount_credit"));
                    if ((rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit")) < 0) {
                        TmpTransactorLedger.setNetDebt(0);
                    } else {
                        TmpTransactorLedger.setNetDebt(rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit"));
                    }
                    if ((rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit")) < 0) {
                        TmpTransactorLedger.setNetCredit(0);
                    } else {
                        TmpTransactorLedger.setNetCredit(rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit"));
                    }
                    this.ReportTransactorLedgerSummaryAllIndividual.add(TmpTransactorLedger);
                }
                //this.ActionMessage=((""));
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
        return this.ReportTransactorLedgerSummaryAllIndividual;
    }

    public List<TransactorLedger> getReportTransactorLedgerSummaryAll() {
        String sql;
        sql = "{call sp_report_transactor_ledger_summary_all()}";
        ResultSet rs = null;
        this.ReportTransactorLedgerSummaryAll.clear();
        if (1 == 1) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                //System.out.println(rs.getStatement());
                TransactorLedger TmpTransactorLedger;
                while (rs.next()) {
                    TmpTransactorLedger = new TransactorLedger();
                    TmpTransactorLedger.setSumAmountDebit(rs.getDouble("sum_amount_debit"));
                    TmpTransactorLedger.setSumAmountCredit(rs.getDouble("sum_amount_credit"));
                    if ((rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit")) < 0) {
                        TmpTransactorLedger.setNetDebt(0);
                    } else {
                        TmpTransactorLedger.setNetDebt(rs.getDouble("sum_amount_debit") - rs.getDouble("sum_amount_credit"));
                    }
                    if ((rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit")) < 0) {
                        TmpTransactorLedger.setNetCredit(0);
                    } else {
                        TmpTransactorLedger.setNetCredit(rs.getDouble("sum_amount_credit") - rs.getDouble("sum_amount_debit"));
                    }
                    this.ReportTransactorLedgerSummaryAll.add(TmpTransactorLedger);
                }
                //this.ActionMessage=((""));
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
        return this.ReportTransactorLedgerSummaryAll;
    }

    public boolean IsAmountNegative(double aAmount) {
        if (aAmount >= 0) {
            return false;
        } else {
            return true;
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
     * @return the CurrentTransactorLedger
     */
    public TransactorLedger getCurrentTransactorLedger() {
        return CurrentTransactorLedger;
    }

    /**
     * @param CurrentTransactorLedger the CurrentTransactorLedger to set
     */
    public void setCurrentTransactorLedger(TransactorLedger CurrentTransactorLedger) {
        this.CurrentTransactorLedger = CurrentTransactorLedger;
    }

}
