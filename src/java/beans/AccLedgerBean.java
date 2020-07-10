package beans;

import connections.DBConnection;
import entities.AccBalanceSheet;
import entities.AccCurrency;
import entities.AccIncomeStatement;
import entities.AccJournal;
import entities.AccLedger;
import entities.AccPeriod;
import entities.Pay;
import entities.TransItem;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "accLedgerBean")
@SessionScoped
public class AccLedgerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<AccLedger> AccLedgerList;
    private List<AccLedger> AccLedgerSummary;
    private AccLedger AccLedgerObj;
    private List<AccLedger> AccLedgerListOpenBal;
    private List<AccLedger> AccLedgerCashAccBal;
    private List<AccLedger> AccLedgerReceivablesAccBal;
    private List<AccLedger> AccLedgerPayablesAccBal;
    private double AccountBalance;
    private List<AccLedger> CategoryList;
    private String CategoryHeader;
    private List<AccJournal> JournalList;
    private long n;
    private long i;
    private String OpenBalanceHeader = "";

    public void setAccLedgerFromResultset(AccLedger accledger, ResultSet aResultSet) {
        try {
            try {
                accledger.setAccLedgerId(aResultSet.getLong("acc_ledger_id"));
            } catch (NullPointerException npe) {
                accledger.setAccLedgerId(0);
            }
            try {
                accledger.setAccPeriodId(aResultSet.getInt("acc_period_id"));
            } catch (NullPointerException npe) {
                accledger.setAccPeriodId(0);
            }
            try {
                accledger.setBillTransactorId(aResultSet.getLong("bill_transactor_id"));
            } catch (NullPointerException npe) {
                accledger.setBillTransactorId(0);
            }

            try {
                accledger.setAccountCode(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                accledger.setAccountCode("");
            }
            try {
                accledger.setAccChildAccountId(aResultSet.getInt("acc_child_account_id"));
            } catch (NullPointerException npe) {
                accledger.setAccChildAccountId(0);
            }
            try {
                accledger.setCurrencyCode(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                accledger.setCurrencyCode("");
            }
            try {
                accledger.setDebitAmount(aResultSet.getDouble("debit_amount"));
            } catch (NullPointerException npe) {
                accledger.setDebitAmount(0);
            }
            try {
                accledger.setCreditAmount(aResultSet.getDouble("credit_amount"));
            } catch (NullPointerException npe) {
                accledger.setCreditAmount(0);
            }
            try {
                accledger.setDebitAmountLc(aResultSet.getDouble("debit_amount_lc"));
            } catch (NullPointerException npe) {
                accledger.setDebitAmountLc(0);
            }
            try {
                accledger.setCreditAmountLc(aResultSet.getDouble("credit_amount_lc"));
            } catch (NullPointerException npe) {
                accledger.setCreditAmountLc(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void postJounalToLedger(AccJournal aAccJournal) {
        String sql = "";
        AccLedger al = this.searchAccLedger(aAccJournal);
        if (al.getAccLedgerId() == 0) {//Insert
            sql = "{call sp_insert_acc_ledger(?,?,?,?,?,?,?,?,?)}";
        } else {//Update
            sql = "{call sp_update_acc_ledger(?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (al.getAccLedgerId() == 0) {//Insert
                try {
                    cs.setInt("in_acc_period_id", aAccJournal.getAccPeriodId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_acc_period_id", 0);
                }
                try {
                    cs.setLong("in_bill_transactor_id", aAccJournal.getBillTransactorId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_bill_transactor_id", 0);
                }
                try {
                    cs.setString("in_account_code", aAccJournal.getAccountCode());
                } catch (NullPointerException npe) {
                    cs.setString("in_account_code", "");
                }
                try {
                    cs.setInt("in_acc_child_account_id", aAccJournal.getAccChildAccountId());
                } catch (NullPointerException npe) {
                    cs.setInt("in_acc_child_account_id", 0);
                }
                cs.setString("in_currency_code", aAccJournal.getCurrencyCode());
                cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                cs.setDouble("in_debit_amount_lc", aAccJournal.getDebitAmount() * aAccJournal.getXrate());
                cs.setDouble("in_credit_amount_lc", aAccJournal.getCreditAmount() * aAccJournal.getXrate());
            } else {//Update
                try {
                    cs.setLong("in_acc_ledger_id", al.getAccLedgerId());
                } catch (NullPointerException npe) {
                    cs.setLong("in_acc_ledger_id", 0);
                }
                cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                cs.setDouble("in_debit_amount_lc", aAccJournal.getDebitAmount() * aAccJournal.getXrate());
                cs.setDouble("in_credit_amount_lc", aAccJournal.getCreditAmount() * aAccJournal.getXrate());
            }
            cs.executeUpdate();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void postJounalToLedgerSpecify(AccJournal aAccJournal) {
        String sql = "";
        AccLedger al = this.searchAccLedgerSpecify(aAccJournal);
        if (al.getAccLedgerId() == 0) {//Insert
            sql = "{call sp_insert_acc_ledger_specify(?,?,?,?,?,?,?,?,?,?)}";
        } else {//Update
            sql = "{call sp_update_acc_ledger_specify(?,?,?,?,?,?)}";
        }
        String TableName = new AccJournalBean().getSpecificTableName(aAccJournal.getAccountCode(), "LEDGER");
        if (TableName.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                cs.setString("in_table_name", TableName);
                if (al.getAccLedgerId() == 0) {//Insert
                    try {
                        cs.setInt("in_acc_period_id", aAccJournal.getAccPeriodId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_period_id", 0);
                    }
                    try {
                        cs.setLong("in_bill_transactor_id", aAccJournal.getBillTransactorId());
                    } catch (NullPointerException npe) {
                        cs.setLong("in_bill_transactor_id", 0);
                    }
                    try {
                        cs.setString("in_account_code", aAccJournal.getAccountCode());
                    } catch (NullPointerException npe) {
                        cs.setString("in_account_code", "");
                    }
                    try {
                        cs.setInt("in_acc_child_account_id", aAccJournal.getAccChildAccountId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_child_account_id", 0);
                    }
                    cs.setString("in_currency_code", aAccJournal.getCurrencyCode());
                    cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                    cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                    cs.setDouble("in_debit_amount_lc", aAccJournal.getDebitAmount() * aAccJournal.getXrate());
                    cs.setDouble("in_credit_amount_lc", aAccJournal.getCreditAmount() * aAccJournal.getXrate());
                } else {//Update
                    try {
                        cs.setLong("in_acc_ledger_id", al.getAccLedgerId());
                    } catch (NullPointerException npe) {
                        cs.setLong("in_acc_ledger_id", 0);
                    }
                    cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                    cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                    cs.setDouble("in_debit_amount_lc", aAccJournal.getDebitAmount() * aAccJournal.getXrate());
                    cs.setDouble("in_credit_amount_lc", aAccJournal.getCreditAmount() * aAccJournal.getXrate());
                }
                cs.executeUpdate();
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
    }

    public void postJounalToLedgerSpecific(AccJournal aAccJournal) {
        String sql = "";
        AccLedger al = this.searchAccLedgerSpecific(aAccJournal);
        String TableName = new AccJournalBean().getSpecificTableName(aAccJournal.getAccountCode(), "LEDGER");
        if (al.getAccLedgerId() == 0) {//Insert
            sql = "{call sp_insert_" + TableName + "(?,?,?,?,?,?,?,?,?)}";
        } else {//Update
            sql = "{call sp_update_" + TableName + "(?,?,?,?,?)}";
        }
        if (TableName.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (al.getAccLedgerId() == 0) {//Insert
                    try {
                        cs.setInt("in_acc_period_id", aAccJournal.getAccPeriodId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_period_id", 0);
                    }
                    try {
                        cs.setLong("in_bill_transactor_id", aAccJournal.getBillTransactorId());
                    } catch (NullPointerException npe) {
                        cs.setLong("in_bill_transactor_id", 0);
                    }
                    try {
                        cs.setString("in_account_code", aAccJournal.getAccountCode());
                    } catch (NullPointerException npe) {
                        cs.setString("in_account_code", "");
                    }
                    try {
                        cs.setInt("in_acc_child_account_id", aAccJournal.getAccChildAccountId());
                    } catch (NullPointerException npe) {
                        cs.setInt("in_acc_child_account_id", 0);
                    }
                    cs.setString("in_currency_code", aAccJournal.getCurrencyCode());
                    cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                    cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                    cs.setDouble("in_debit_amount_lc", aAccJournal.getDebitAmount() * aAccJournal.getXrate());
                    cs.setDouble("in_credit_amount_lc", aAccJournal.getCreditAmount() * aAccJournal.getXrate());
                } else {//Update
                    try {
                        cs.setLong("in_acc_ledger_id", al.getAccLedgerId());
                    } catch (NullPointerException npe) {
                        cs.setLong("in_acc_ledger_id", 0);
                    }
                    cs.setDouble("in_debit_amount", aAccJournal.getDebitAmount());
                    cs.setDouble("in_credit_amount", aAccJournal.getCreditAmount());
                    cs.setDouble("in_debit_amount_lc", aAccJournal.getDebitAmount() * aAccJournal.getXrate());
                    cs.setDouble("in_credit_amount_lc", aAccJournal.getCreditAmount() * aAccJournal.getXrate());
                }
                cs.executeUpdate();
            } catch (Exception e) {
                System.err.println("postJounalToLedgerSpecific:" + e.getMessage());
            }
        }
    }

    public AccLedger searchAccLedger(AccJournal aAccJournal) {
        String sql;
        sql = "{call sp_search_acc_ledger(?,?,?,?,?)}";
        ResultSet rs = null;
        AccLedger accledger = new AccLedger();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            try {
                ps.setInt(1, aAccJournal.getAccPeriodId());
            } catch (NullPointerException npe) {
                ps.setInt(1, 0);
            }
            try {
                ps.setLong(2, aAccJournal.getBillTransactorId());
            } catch (NullPointerException npe) {
                ps.setLong(2, 0);
            }
            try {
                ps.setString(3, aAccJournal.getAccountCode());
            } catch (NullPointerException npe) {
                ps.setString(3, "");
            }
            try {
                ps.setInt(4, aAccJournal.getAccChildAccountId());
            } catch (NullPointerException npe) {
                ps.setInt(4, 0);
            }
            try {
                ps.setString(5, aAccJournal.getCurrencyCode());
            } catch (NullPointerException npe) {
                ps.setString(5, "");
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                //accledger = new AccLedger();
                this.setAccLedgerFromResultset(accledger, rs);
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
        return accledger;
    }

    public AccLedger searchAccLedgerSpecify(AccJournal aAccJournal) {
        String sql;
        sql = "{call sp_search_acc_ledger_specify(?,?,?,?,?,?)}";
        ResultSet rs = null;
        AccLedger accledger = new AccLedger();
        String TableName = new AccJournalBean().getSpecificTableName(aAccJournal.getAccountCode(), "LEDGER");
        if (TableName.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setString(6, TableName);
                try {
                    ps.setInt(1, aAccJournal.getAccPeriodId());
                } catch (NullPointerException npe) {
                    ps.setInt(1, 0);
                }
                try {
                    ps.setLong(2, aAccJournal.getBillTransactorId());
                } catch (NullPointerException npe) {
                    ps.setLong(2, 0);
                }
                try {
                    ps.setString(3, aAccJournal.getAccountCode());
                } catch (NullPointerException npe) {
                    ps.setString(3, "");
                }
                try {
                    ps.setInt(4, aAccJournal.getAccChildAccountId());
                } catch (NullPointerException npe) {
                    ps.setInt(4, 0);
                }
                try {
                    ps.setString(5, aAccJournal.getCurrencyCode());
                } catch (NullPointerException npe) {
                    ps.setString(5, "");
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    //accledger = new AccLedger();
                    this.setAccLedgerFromResultset(accledger, rs);
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
        return accledger;
    }

    public AccLedger searchAccLedgerSpecific(AccJournal aAccJournal) {
        String sql;
        String TableName = new AccJournalBean().getSpecificTableName(aAccJournal.getAccountCode(), "LEDGER");
        sql = "{call sp_search_" + TableName + "(?,?,?,?,?)}";
        ResultSet rs = null;
        AccLedger accledger = new AccLedger();
        if (TableName.length() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                try {
                    ps.setInt(1, aAccJournal.getAccPeriodId());
                } catch (NullPointerException npe) {
                    ps.setInt(1, 0);
                }
                try {
                    ps.setLong(2, aAccJournal.getBillTransactorId());
                } catch (NullPointerException npe) {
                    ps.setLong(2, 0);
                }
                try {
                    ps.setString(3, aAccJournal.getAccountCode());
                } catch (NullPointerException npe) {
                    ps.setString(3, "");
                }
                try {
                    ps.setInt(4, aAccJournal.getAccChildAccountId());
                } catch (NullPointerException npe) {
                    ps.setInt(4, 0);
                }
                try {
                    ps.setString(5, aAccJournal.getCurrencyCode());
                } catch (NullPointerException npe) {
                    ps.setString(5, "");
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    //accledger = new AccLedger();
                    this.setAccLedgerFromResultset(accledger, rs);
                }
            } catch (Exception e) {
                System.err.println("searchAccLedgerSpecific:" + e.getMessage());
            }
        }
        return accledger;
    }

    public void reportAccLedger(int aAccPeriodId, int aAccTypeId) {
        String sql = "SELECT * FROM view_ledger_general al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE 1=1";

        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        if (aAccPeriodId > 0) {
            this.AccLedgerList = new ArrayList<>();
            wheresql = " AND al.acc_period_id=" + aAccPeriodId;
            if (aAccTypeId > 0) {
                wheresql = wheresql + " AND ac.acc_type_id=" + aAccTypeId;
            }
            ordersql = " ORDER BY ac.order_coa";
            sql = sql + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    this.setAccLedgerFromResultset(accledger, rs);
                    this.AccLedgerList.add(accledger);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
    }

    public void reportAccLedgerUnionOpenBal(int aAccPeriodId, int aAccTypeId) {
        String sql = "SELECT 0 as acc_ledger_id,0 AS bill_transactor_id, 0 AS acc_child_account_id,"
                + "al.acc_period_id,al.account_code,al.currency_code,"
                + "sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE 1=1";
        String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.AccLedgerList = new ArrayList<>();
        this.AccLedgerSummary = new ArrayList<>();
        if (aAccPeriodId > 0) {
            wheresql = " AND al.acc_period_id=" + aAccPeriodId;
            if (aAccTypeId > 0) {
                wheresql = wheresql + " AND ac.acc_type_id=" + aAccTypeId;
            }
            ordersql = " ORDER BY al.account_code ASC";
            sql = sql + wheresql + " GROUP BY al.acc_period_id,al.account_code,al.currency_code " + ordersql;
            sqlsum = sqlsum + wheresql + " GROUP BY al.currency_code ";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    this.setAccLedgerFromResultset(accledger, rs);
                    this.AccLedgerList.add(accledger);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccLedger accledgersum = null;
            while (rs.next()) {
                accledgersum = new AccLedger();
                try {
                    accledgersum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accledgersum.setCurrencyCode("");
                }
                try {
                    accledgersum.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accledgersum.setDebitAmount(0);
                }
                try {
                    accledgersum.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accledgersum.setCreditAmount(0);
                }
                this.getAccLedgerSummary().add(accledgersum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportCashAccBalances() {
        String sqlsum = "SELECT al.account_code,al.acc_child_account_id,al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE al.account_code LIKE '1-00-000%'";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.AccLedgerCashAccBal = new ArrayList<>();
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        wheresql = " AND 1=1";
        ordersql = " ORDER BY al.account_code ASC";
        sqlsum = sqlsum + wheresql + " GROUP BY al.account_code,al.acc_child_account_id,al.currency_code " + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccLedger accledger = null;
            while (rs.next()) {
                accledger = new AccLedger();
                try {
                    accledger.setAccountCode(rs.getString("account_code"));
                } catch (NullPointerException npe) {
                    accledger.setAccountCode("");
                }
                try {
                    accledger.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accledger.setCurrencyCode("");
                }
                try {
                    accledger.setAccChildAccountId(rs.getInt("acc_child_account_id"));
                } catch (NullPointerException npe) {
                    accledger.setAccChildAccountId(0);
                }
                try {
                    accledger.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmount(0);
                }
                try {
                    accledger.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmount(0);
                }
                try {
                    //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                    accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmountLc(0);
                }
                try {
                    //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                    accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmountLc(0);
                }
                this.getAccLedgerCashAccBal().add(accledger);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportCashAccBalances_Old() {
        String sqlsum = "SELECT al.account_code,al.acc_child_account_id,al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE al.account_code LIKE '1-00-000%'";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.AccLedgerCashAccBal = new ArrayList<>();
        int accperiodid = 0;
        try {
            accperiodid = new AccPeriodBean().getAccPeriodCurrent().getAccPeriodId();
        } catch (NullPointerException npe) {
            accperiodid = 0;
        }
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        if (accperiodid > 0) {
            wheresql = " AND al.acc_period_id=" + accperiodid;
            ordersql = " ORDER BY al.account_code ASC";
            sqlsum = sqlsum + wheresql + " GROUP BY al.account_code,al.acc_child_account_id,al.currency_code " + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setAccountCode(rs.getString("account_code"));
                    } catch (NullPointerException npe) {
                        accledger.setAccountCode("");
                    }
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setAccChildAccountId(rs.getInt("acc_child_account_id"));
                    } catch (NullPointerException npe) {
                        accledger.setAccChildAccountId(0);
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    this.getAccLedgerCashAccBal().add(accledger);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
    }

    public double getCashAccBalance(int aChildAccId, String aCurrencyCode) {
        double bal = 0;
        String sqlsum = "SELECT al.account_code,al.acc_child_account_id,al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE al.account_code LIKE '1-00-000%' and al.acc_child_account_id=" + aChildAccId + " and al.currency_code='" + aCurrencyCode + "'";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        int accperiodid = 0;
        try {
            accperiodid = new AccPeriodBean().getAccPeriodCurrent().getAccPeriodId();
        } catch (NullPointerException npe) {
            accperiodid = 0;
        }
        if (accperiodid > 0) {
            wheresql = " AND al.acc_period_id=" + accperiodid;
            ordersql = " ORDER BY al.account_code ASC";
            sqlsum = sqlsum + wheresql + " GROUP BY al.account_code,al.acc_child_account_id,al.currency_code " + ordersql;
            //System.out.println("sqlsum:" + sqlsum);
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                if (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setAccountCode(rs.getString("account_code"));
                    } catch (NullPointerException npe) {
                        accledger.setAccountCode("");
                    }
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setAccChildAccountId(rs.getInt("acc_child_account_id"));
                    } catch (NullPointerException npe) {
                        accledger.setAccChildAccountId(0);
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    bal = accledger.getDebitAmount() - accledger.getCreditAmount();
                }
            } catch (SQLException se) {
                System.err.println("getCashAccBalance:" + se.getMessage());
            }
        }
        return bal;
    }

    public double getCashAccBalanceByAccCode(String aChildAccCode, String aCurrencyCode) {
        double bal = 0;
        int childaccid = 0;
        try {
            childaccid = new AccChildAccountBean().getAccChildAccByCode(aChildAccCode).getAccChildAccountId();
        } catch (Exception e) {

        }
        if (childaccid > 0) {
            bal = this.getCashAccBalance(childaccid, aCurrencyCode);
        }
        return bal;
    }

    public void refreshBalancesXrate(TransItem aTransItem) {
        String FromCurCode = "";
        String ToCurCode = "";
        String FromAccCode = "";
        String ToAccCode = "";
        if (null != aTransItem) {
            FromCurCode = aTransItem.getBatchno();
            ToCurCode = aTransItem.getDescSpecific();
            FromAccCode = aTransItem.getAccountCode();
            ToAccCode = aTransItem.getCodeSpecific();
            try {
                aTransItem.setUnitPrice(this.getCashAccBalanceByAccCode(FromAccCode, FromCurCode));
            } catch (Exception e) {
                aTransItem.setUnitPrice(0);
            }
            try {
                aTransItem.setUnitPrice2(this.getCashAccBalanceByAccCode(ToAccCode, ToCurCode));
            } catch (Exception e) {
                aTransItem.setUnitPrice2(0);
            }

            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            try {
                xrate = new AccXrateBean().getXrate(FromCurCode, ToCurCode);
            } catch (NullPointerException npe) {
                xrate = 1;
            }
            try {
                if (FromCurCode.equals(LocalCurrency.getCurrencyCode()) && !ToCurCode.equals(LocalCurrency.getCurrencyCode())) {
                    XrateMultiply = 1 / xrate;
                } else {
                    XrateMultiply = xrate;
                }
            } catch (NullPointerException npe) {
                XrateMultiply = 1;
            }
            aTransItem.setVatPerc(xrate);//to xrate:vatPerc
            aTransItem.setDepRate(XrateMultiply);//multi-factor:depRate
            aTransItem.setAmountExcVat(aTransItem.getAmountIncVat() * XrateMultiply);
        }
    }

    public void refreshNewBalance(TransItem aTransItem) {
        if (null != aTransItem) {
            if (aTransItem.getNarration().equals("Add")) {
                aTransItem.setAmountExcVat(aTransItem.getUnitPrice() + aTransItem.getAmountIncVat());
            } else if (aTransItem.getNarration().equals("Subtract")) {
                aTransItem.setAmountExcVat(aTransItem.getUnitPrice() - aTransItem.getAmountIncVat());
            } else {
                aTransItem.setAmountExcVat(0);
            }
        }
    }

    public void refreshBalance(TransItem aTransItem) {
        String FromCurCode = "";
        String FromAccCode = "";
        if (null != aTransItem) {
            FromCurCode = aTransItem.getBatchno();
            FromAccCode = aTransItem.getAccountCode();
            try {
                aTransItem.setUnitPrice(this.getCashAccBalanceByAccCode(FromAccCode, FromCurCode));
            } catch (Exception e) {
                aTransItem.setUnitPrice(0);
            }
        }
    }

    public void refreshXrate(TransItem aTransItem) {
        String FromCurCode = "";
        String ToCurCode = "";
        String FromAccCode = "";
        String ToAccCode = "";
        if (null != aTransItem) {
            FromCurCode = aTransItem.getBatchno();
            ToCurCode = aTransItem.getDescSpecific();
            FromAccCode = aTransItem.getAccountCode();
            ToAccCode = aTransItem.getCodeSpecific();
            try {
                aTransItem.setUnitPrice(this.getCashAccBalanceByAccCode(FromAccCode, FromCurCode));
            } catch (Exception e) {
                aTransItem.setUnitPrice(0);
            }
            try {
                aTransItem.setUnitPrice2(this.getCashAccBalanceByAccCode(ToAccCode, ToCurCode));
            } catch (Exception e) {
                aTransItem.setUnitPrice2(0);
            }

            //for where item currency is different from trans currency, we first get the factor to convert to trans currency
            double xrate = 1;
            double XrateMultiply = 1;
            AccCurrency LocalCurrency = null;
            LocalCurrency = new AccCurrencyBean().getLocalCurrency();
            try {
                if (aTransItem.getVatPerc() > 0) {
                    xrate = aTransItem.getVatPerc();
                }
            } catch (NullPointerException npe) {
                xrate = 1;
            }
            try {
                if (FromCurCode.equals(LocalCurrency.getCurrencyCode()) && !ToCurCode.equals(LocalCurrency.getCurrencyCode())) {
                    XrateMultiply = 1 / xrate;
                } else {
                    XrateMultiply = xrate;
                }
            } catch (NullPointerException npe) {
                XrateMultiply = 1;
            }
            aTransItem.setVatPerc(xrate);//to xrate:vatPerc
            aTransItem.setDepRate(XrateMultiply);//multi-factor:depRate
            aTransItem.setAmountExcVat(aTransItem.getAmountIncVat() * XrateMultiply);
        }
    }

    public boolean checkerBalancePass(int aPayMethodId, int aChildAccId, String aCurrencyCode, double aPayAmount, int aTransTypeId, int aTransReasId, int aPayTypeId, int aPayReasId) {
        boolean pass = true;
        int BalanceCheckerOn = 0;
        if (aPayAmount > 0 && aPayMethodId != 6 && aPayMethodId != 7 && (aTransTypeId == 1 || aTransTypeId == 15 || aTransTypeId == 19)) {
            try {
                BalanceCheckerOn = new AccChildAccountBean().getAccChildAccById(aChildAccId).getBalance_checker_on();
            } catch (Exception e) {
            }
            if (BalanceCheckerOn == 0) {
                pass = true;
            } else {
                double BalOnAcc = this.getCashAccBalance(aChildAccId, aCurrencyCode);
                if (aPayAmount > BalOnAcc) {
                    pass = false;
                }
            }
        }
        return pass;
    }

    public void reportReceivableAccBalances() {
        String sqlsum = "SELECT v.*,t.transactor_names,t.transactor_ref FROM view_ledger_acc_rec_balances v "
                + "INNER JOIN transactor t ON v.bill_transactor_id=t.transactor_id "
                + "ORDER BY t.transactor_names ASC";
        ResultSet rs = null;
        this.AccLedgerReceivablesAccBal = new ArrayList<>();
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccLedger accledger = null;
            while (rs.next()) {
                accledger = new AccLedger();
                try {
                    accledger.setAccountCode(rs.getString("account_code"));
                } catch (NullPointerException npe) {
                    accledger.setAccountCode("");
                }
                try {
                    accledger.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accledger.setCurrencyCode("");
                }
                try {
                    accledger.setBillTransactorId(rs.getInt("bill_transactor_id"));
                } catch (NullPointerException npe) {
                    accledger.setBillTransactorId(0);
                }
                try {
                    accledger.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmount(0);
                }
                try {
                    accledger.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmount(0);
                }
                try {
                    accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmountLc(0);
                }
                try {
                    accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmountLc(0);
                }
                try {
                    accledger.setTransactorName(rs.getString("transactor_names"));
                } catch (Exception e) {
                    accledger.setTransactorName("");
                }
                try {
                    accledger.setTransactorRef(rs.getString("transactor_ref"));
                } catch (Exception e) {
                    accledger.setTransactorRef("");
                }
                this.getAccLedgerReceivablesAccBal().add(accledger);
            }
        } catch (SQLException se) {
            System.err.println("reportReceivableAccBalances:" + se.getMessage());
        }
    }

    public void reportReceivableAccBalances_old() {
        String sqlsum = "SELECT al.account_code,al.bill_transactor_id,al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE al.account_code LIKE '1-00-010%'";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.AccLedgerReceivablesAccBal = new ArrayList<>();
        int accperiodid = 0;
        try {
            accperiodid = new AccPeriodBean().getAccPeriodCurrent().getAccPeriodId();
        } catch (NullPointerException npe) {
            accperiodid = 0;
        }
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        if (accperiodid > 0) {
            wheresql = " AND al.acc_period_id=" + accperiodid;
            ordersql = " ORDER BY al.bill_transactor_id DESC";
            sqlsum = sqlsum + wheresql + " GROUP BY al.account_code,al.bill_transactor_id,al.currency_code " + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setAccountCode(rs.getString("account_code"));
                    } catch (NullPointerException npe) {
                        accledger.setAccountCode("");
                    }
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setBillTransactorId(rs.getInt("bill_transactor_id"));
                    } catch (NullPointerException npe) {
                        accledger.setBillTransactorId(0);
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    if ((accledger.getDebitAmount() - accledger.getCreditAmount()) != 0) {
                        this.getAccLedgerReceivablesAccBal().add(accledger);
                    }
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
    }

    public double getReceivableAccBalance(long aBillTransactorId) {
        double DebitBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM view_ledger_acc_rec_balances al "
                    + "WHERE bill_transactor_id=" + aBillTransactorId + " GROUP BY al.currency_code";
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
            String LocCurCode = "";
            try {
                LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
            } catch (NullPointerException npe) {
                LocCurCode = "";
            }
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    DebitBalance = DebitBalance + (accledger.getDebitAmountLc() - accledger.getCreditAmountLc());
                }
            } catch (SQLException se) {
                System.err.println("getReceivableAccBalance:" + se.getMessage());
            }
        }
        return DebitBalance;
    }

    public double getReceivableAccBalanceTrade(long aBillTransactorId) {
        double DebitBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM acc_ledger_receivable al "
                    + "WHERE al.account_code='1-00-010-010' AND bill_transactor_id=" + aBillTransactorId + " GROUP BY al.currency_code";
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
            String LocCurCode = "";
            try {
                LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
            } catch (NullPointerException npe) {
                LocCurCode = "";
            }
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    DebitBalance = DebitBalance + (accledger.getDebitAmountLc() - accledger.getCreditAmountLc());
                }
            } catch (SQLException se) {
                System.err.println("getReceivableAccTradeBalance:" + se.getMessage());
            }
        }
        return DebitBalance;
    }

    public double getReceivableAccBalance_old(long aBillTransactorId) {
        double DebitBalance = 0;
        String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code "
                + "WHERE al.account_code LIKE '1-00-010%' AND bill_transactor_id=" + aBillTransactorId;
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        int accperiodid = 0;
        try {
            accperiodid = new AccPeriodBean().getAccPeriodCurrent().getAccPeriodId();
        } catch (NullPointerException npe) {
            accperiodid = 0;
        }
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        if (accperiodid > 0) {
            wheresql = " AND al.acc_period_id=" + accperiodid;
            ordersql = "";//ordersql = " ORDER BY al.bill_transactor_id DESC";
            sqlsum = sqlsum + wheresql + " GROUP BY al.currency_code " + ordersql;//sqlsum = sqlsum + wheresql + " GROUP BY al.account_code,al.bill_transactor_id,al.currency_code " + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    DebitBalance = DebitBalance + (accledger.getDebitAmountLc() - accledger.getCreditAmountLc());
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
        return DebitBalance;
    }

    public double getPrepaidIncomeAccBalance(long aBillTransactorId, String aCurrencyCode) {
        double CreditBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM view_ledger_acc_prepaid_income_balances al "
                    + "WHERE currency_code='" + aCurrencyCode + "' AND bill_transactor_id=" + aBillTransactorId;
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    CreditBalance = CreditBalance + (accledger.getCreditAmount() - accledger.getDebitAmount());
                }
            } catch (SQLException se) {
                System.err.println("getPrepaidIncomeAccBalance:" + se.getMessage());
            }
        }
        return CreditBalance;
    }

    public double getPrepaidIncomeAccBalanceTrade(long aBillTransactorId, String aCurrencyCode) {
        double CreditBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM acc_ledger_prepaid al "
                    + "WHERE account_code='2-00-000-070' AND currency_code='" + aCurrencyCode + "' AND bill_transactor_id=" + aBillTransactorId;
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            //System.out.println("sqlsum:" + sqlsum);
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    CreditBalance = CreditBalance + (accledger.getCreditAmount() - accledger.getDebitAmount());
                }
            } catch (SQLException se) {
                System.err.println("getPrepaidIncomeAccBalance:" + se.getMessage());
            }
        }
        return CreditBalance;
    }

    public double getPrepaidIncomeAccBalanceToCur(long aBillTransactorId, String aToCurrencyCode) {
        double CreditBalance = 0;
        String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_acc_prepaid_income_balances al "
                + "WHERE bill_transactor_id=" + aBillTransactorId + " GROUP BY al.currency_code";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        wheresql = "";
        ordersql = "";
        sqlsum = sqlsum + wheresql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccLedger accledger = null;
            while (rs.next()) {
                accledger = new AccLedger();
                try {
                    accledger.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accledger.setCurrencyCode("");
                }
                try {
                    accledger.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmount(0);
                }
                try {
                    accledger.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmount(0);
                }
                try {
                    accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), aToCurrencyCode));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmountLc(0);
                }
                try {
                    accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), aToCurrencyCode));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmountLc(0);
                }
                CreditBalance = CreditBalance + (accledger.getCreditAmountLc() - accledger.getDebitAmountLc());
            }
        } catch (SQLException se) {
            System.err.println("getPrepaidIncomeAccBalanceToCur:" + se.getMessage());
        }
        return CreditBalance;
    }

    public double getPrepaidExpenseAccBalance(long aBillTransactorId, String aCurrencyCode) {
        double DebitBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM view_ledger_acc_prepaid_expense_balances al "
                    + "WHERE currency_code='" + aCurrencyCode + "' AND bill_transactor_id=" + aBillTransactorId;
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
//        String LocCurCode = "";
//        try {
//            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
//        } catch (NullPointerException npe) {
//            LocCurCode = "";
//        }
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
//                try {
//                    //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
//                    accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
//                } catch (NullPointerException npe) {
//                    accledger.setDebitAmountLc(0);
//                }
//                try {
//                    //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
//                    accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
//                } catch (NullPointerException npe) {
//                    accledger.setCreditAmountLc(0);
//                }
                    DebitBalance = DebitBalance + (accledger.getDebitAmount() - accledger.getCreditAmount());
                }
            } catch (SQLException se) {
                System.err.println("getPrepaidExpenseAccBalance:" + se.getMessage());
            }
        }
        return DebitBalance;
    }

    public double getPrepaidExpenseAccBalanceTrade(long aBillTransactorId, String aCurrencyCode) {
        double DebitBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM acc_ledger_prepaid al "
                    + "WHERE account_code='1-00-030-050' AND currency_code='" + aCurrencyCode + "' AND bill_transactor_id=" + aBillTransactorId;
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
//        String LocCurCode = "";
//        try {
//            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
//        } catch (NullPointerException npe) {
//            LocCurCode = "";
//        }
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
//                try {
//                    //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
//                    accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
//                } catch (NullPointerException npe) {
//                    accledger.setDebitAmountLc(0);
//                }
//                try {
//                    //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
//                    accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
//                } catch (NullPointerException npe) {
//                    accledger.setCreditAmountLc(0);
//                }
                    DebitBalance = DebitBalance + (accledger.getDebitAmount() - accledger.getCreditAmount());
                }
            } catch (SQLException se) {
                System.err.println("getPrepaidExpenseAccBalance:" + se.getMessage());
            }
        }
        return DebitBalance;
    }

    public double getPrepaidExpenseAccBalanceToCur(long aBillTransactorId, String aToCurrencyCode) {
        double DebitBalance = 0;
        String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_acc_prepaid_expense_balances al "
                + "WHERE bill_transactor_id=" + aBillTransactorId + " GROUP BY al.currency_code";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        wheresql = "";
        ordersql = "";
        sqlsum = sqlsum + wheresql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccLedger accledger = null;
            while (rs.next()) {
                accledger = new AccLedger();
                try {
                    accledger.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accledger.setCurrencyCode("");
                }
                try {
                    accledger.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmount(0);
                }
                try {
                    accledger.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmount(0);
                }
                try {
                    accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), aToCurrencyCode));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmountLc(0);
                }
                try {
                    accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), aToCurrencyCode));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmountLc(0);
                }
                DebitBalance = DebitBalance + (accledger.getDebitAmountLc() - accledger.getCreditAmountLc());
            }
        } catch (SQLException se) {
            System.err.println("getPrepaidExpenseAccBalanceToCur:" + se.getMessage());
        }
        return DebitBalance;
    }

    public void reportPayableAccBalances() {
        String sqlsum = "SELECT v.*,t.transactor_names,t.transactor_ref FROM view_ledger_acc_pay_balances v "
                + "INNER JOIN transactor t ON v.bill_transactor_id=t.transactor_id "
                + "ORDER BY t.transactor_names ASC";
        ResultSet rs = null;
        this.AccLedgerPayablesAccBal = new ArrayList<>();
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccLedger accledger = null;
            while (rs.next()) {
                accledger = new AccLedger();
                try {
                    accledger.setAccountCode(rs.getString("account_code"));
                } catch (NullPointerException npe) {
                    accledger.setAccountCode("");
                }
                try {
                    accledger.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accledger.setCurrencyCode("");
                }
                try {
                    accledger.setBillTransactorId(rs.getInt("bill_transactor_id"));
                } catch (NullPointerException npe) {
                    accledger.setBillTransactorId(0);
                }
                try {
                    accledger.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmount(0);
                }
                try {
                    accledger.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmount(0);
                }
                try {
                    accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                } catch (NullPointerException npe) {
                    accledger.setDebitAmountLc(0);
                }
                try {
                    accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                } catch (NullPointerException npe) {
                    accledger.setCreditAmountLc(0);
                }
                try {
                    accledger.setTransactorName(rs.getString("transactor_names"));
                } catch (Exception e) {
                    accledger.setTransactorName("");
                }
                try {
                    accledger.setTransactorRef(rs.getString("transactor_ref"));
                } catch (Exception e) {
                    accledger.setTransactorRef("");
                }
                this.getAccLedgerPayablesAccBal().add(accledger);
            }
        } catch (SQLException se) {
            System.err.println("reportPayableAccBalances:" + se.getMessage());
        }
    }

    public void reportPayableAccBalances_old() {
        String sqlsum = "SELECT al.account_code,al.bill_transactor_id,al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE al.account_code LIKE '2-00-000%'";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.AccLedgerPayablesAccBal = new ArrayList<>();
        int accperiodid = 0;
        try {
            accperiodid = new AccPeriodBean().getAccPeriodCurrent().getAccPeriodId();
        } catch (NullPointerException npe) {
            accperiodid = 0;
        }
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        if (accperiodid > 0) {
            wheresql = " AND al.acc_period_id=" + accperiodid;
            ordersql = " ORDER BY al.bill_transactor_id DESC";
            sqlsum = sqlsum + wheresql + " GROUP BY al.account_code,al.bill_transactor_id,al.currency_code " + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setAccountCode(rs.getString("account_code"));
                    } catch (NullPointerException npe) {
                        accledger.setAccountCode("");
                    }
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setBillTransactorId(rs.getInt("bill_transactor_id"));
                    } catch (NullPointerException npe) {
                        accledger.setBillTransactorId(0);
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    if ((accledger.getCreditAmount() - accledger.getDebitAmount()) != 0) {
                        this.getAccLedgerPayablesAccBal().add(accledger);
                    }
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
    }

    public double getPayableAccBalance(long aBillTransactorId) {
        double CreditBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM view_ledger_acc_pay_balances al "
                    + "WHERE bill_transactor_id=" + aBillTransactorId + " group by al.currency_code";
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
            String LocCurCode = "";
            try {
                LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
            } catch (NullPointerException npe) {
                LocCurCode = "";
            }
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    CreditBalance = CreditBalance + (accledger.getCreditAmountLc() - accledger.getDebitAmountLc());
                }
            } catch (SQLException se) {
                System.err.println("getPayableAccBalance:" + se.getMessage());
            }
        }
        return CreditBalance;
    }

    public double getPayableAccBalanceTrade(long aBillTransactorId) {
        double CreditBalance = 0;
        if (aBillTransactorId > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM acc_ledger_payable al "
                    + "WHERE al.account_code='2-00-000-010' AND bill_transactor_id=" + aBillTransactorId + " group by al.currency_code";
            String wheresql = "";
            String ordersql = "";
            ResultSet rs = null;
            String LocCurCode = "";
            try {
                LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
            } catch (NullPointerException npe) {
                LocCurCode = "";
            }
            wheresql = "";
            ordersql = "";
            sqlsum = sqlsum + wheresql + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    CreditBalance = CreditBalance + (accledger.getCreditAmountLc() - accledger.getDebitAmountLc());
                }
            } catch (SQLException se) {
                System.err.println("getPayableAccBalanceTrade:" + se.getMessage());
            }
        }
        return CreditBalance;
    }

    public void refreshPayableAccBalance(String aAccountCode, long aBillTransactorId, Pay aPay) {
        if (null == aPay) {
            //do nothing
        } else {
            aPay.setAccountBalance(this.getPayableAccBalance(aAccountCode, aBillTransactorId));
        }
    }

    public double getPayableAccBalance(String aAccountCode, long aBillTransactorId) {
        double CreditBalance = 0;
        String WhereBillTra = "";
        String WhereAccCode = "";
        String WhereSql = "";
        if (aBillTransactorId > 0) {
            WhereBillTra = "bill_transactor_id=" + aBillTransactorId;
        }
        if (aAccountCode.length() > 0) {
            WhereAccCode = "al.account_code='" + aAccountCode + "'";
        }
        if (WhereAccCode.length() > 0 && aBillTransactorId > 0) {
            WhereSql = WhereAccCode + " AND " + WhereBillTra;
        } else if (WhereAccCode.length() > 0) {
            WhereSql = WhereAccCode;
        } else if (aBillTransactorId > 0) {
            WhereSql = WhereBillTra;
        }
        if (WhereSql.length() > 0) {
            String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                    + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                    + "FROM acc_ledger al "
                    + "WHERE " + WhereSql + " group by al.currency_code";
            ResultSet rs = null;
            String LocCurCode = "";
            try {
                LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
            } catch (NullPointerException npe) {
                LocCurCode = "";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    CreditBalance = CreditBalance + (accledger.getCreditAmountLc() - accledger.getDebitAmountLc());
                }
            } catch (SQLException se) {
                System.err.println("getPayableAccBalance:" + se.getMessage());
            }
        }
        return CreditBalance;
    }

    public double getPayableAccBalance_old(long aBillTransactorId) {
        double CreditBalance = 0;
        String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,"
                + "sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON al.account_code=ac.account_code "
                + "WHERE al.account_code LIKE '2-00-000%' AND bill_transactor_id=" + aBillTransactorId;
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        int accperiodid = 0;
        try {
            accperiodid = new AccPeriodBean().getAccPeriodCurrent().getAccPeriodId();
        } catch (NullPointerException npe) {
            accperiodid = 0;
        }
        String LocCurCode = "";
        try {
            LocCurCode = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            LocCurCode = "";
        }
        if (accperiodid > 0) {
            wheresql = " AND al.acc_period_id=" + accperiodid;
            ordersql = "";
            sqlsum = sqlsum + wheresql + " GROUP BY al.currency_code " + ordersql;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sqlsum);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    try {
                        accledger.setCurrencyCode(rs.getString("currency_code"));
                    } catch (NullPointerException npe) {
                        accledger.setCurrencyCode("");
                    }
                    try {
                        accledger.setDebitAmount(rs.getDouble("debit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmount(0);
                    }
                    try {
                        accledger.setCreditAmount(rs.getDouble("credit_amount"));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmount(0);
                    }
                    try {
                        //accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setDebitAmountLc(accledger.getDebitAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setDebitAmountLc(0);
                    }
                    try {
                        //accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(LocCurCode, accledger.getCurrencyCode()));
                        accledger.setCreditAmountLc(accledger.getCreditAmount() * new AccXrateBean().getXrateMultiply(accledger.getCurrencyCode(), LocCurCode));
                    } catch (NullPointerException npe) {
                        accledger.setCreditAmountLc(0);
                    }
                    CreditBalance = CreditBalance + (accledger.getCreditAmountLc() - accledger.getDebitAmountLc());
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }
        return CreditBalance;
    }

    public String calTotalCashAccBalLc(List<AccLedger> aAccLedgers) {
        List<AccLedger> ati = aAccLedgers;
        int ListItemIndex = 0;
        int ListItemNo = 0;
        try {
            ListItemNo = ati.size();
        } catch (NullPointerException npe) {
            ListItemNo = 0;
        }
        double TotAmountLc = 0;
        while (ListItemIndex < ListItemNo) {
            TotAmountLc = TotAmountLc + (ati.get(ListItemIndex).getDebitAmountLc() - ati.get(ListItemIndex).getCreditAmountLc());
            ListItemIndex = ListItemIndex + 1;
        }
        String CurCodeLc = "";
        try {
            CurCodeLc = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            CurCodeLc = "";
        }
        String aNetString = "";
        DecimalFormat myFormatter = new DecimalFormat("###,###.###;(###,###.###)");
        aNetString = myFormatter.format(TotAmountLc) + " " + CurCodeLc;
        return aNetString;
    }

    public String calTotalReceivableAccBalLc(List<AccLedger> aAccLedgers) {
        List<AccLedger> ati = aAccLedgers;
        int ListItemIndex = 0;
        int ListItemNo = 0;
        try {
            ListItemNo = ati.size();
        } catch (NullPointerException npe) {
            ListItemNo = 0;
        }
        double TotAmountLc = 0;
        while (ListItemIndex < ListItemNo) {
            TotAmountLc = TotAmountLc + (ati.get(ListItemIndex).getDebitAmountLc() - ati.get(ListItemIndex).getCreditAmountLc());
            ListItemIndex = ListItemIndex + 1;
        }
        String CurCodeLc = "";
        try {
            CurCodeLc = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            CurCodeLc = "";
        }
        String aNetString = "";
        DecimalFormat myFormatter = new DecimalFormat("###,###.###;(###,###.###)");
        aNetString = myFormatter.format(TotAmountLc) + " " + CurCodeLc;
        return aNetString;
    }

    public String calTotalPayableAccBalLc(List<AccLedger> aAccLedgers) {
        List<AccLedger> ati = aAccLedgers;
        int ListItemIndex = 0;
        int ListItemNo = 0;
        try {
            ListItemNo = ati.size();
        } catch (NullPointerException npe) {
            ListItemNo = 0;
        }
        double TotAmountLc = 0;
        while (ListItemIndex < ListItemNo) {
            TotAmountLc = TotAmountLc + (ati.get(ListItemIndex).getCreditAmountLc() - ati.get(ListItemIndex).getDebitAmountLc());
            ListItemIndex = ListItemIndex + 1;
        }
        String CurCodeLc = "";
        try {
            CurCodeLc = new AccCurrencyBean().getLocalCurrency().getCurrencyCode();
        } catch (NullPointerException npe) {
            CurCodeLc = "";
        }
        String aNetString = "";
        DecimalFormat myFormatter = new DecimalFormat("###,###.###;(###,###.###)");
        aNetString = myFormatter.format(TotAmountLc) + " " + CurCodeLc;
        return aNetString;
    }

    public void reportAccLedgerUnionCloseBal(int aAccPeriodId) {
        String sql = "SELECT 0 as acc_ledger_id,0 AS bill_transactor_id, 0 AS acc_child_account_id,"
                + "al.acc_period_id,al.account_code,al.currency_code,"
                + "sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_close_detail al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE 1=1";
        String sqlsum = "SELECT al.currency_code,sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_close_detail al INNER JOIN acc_coa ac ON al.account_code=ac.account_code WHERE 1=1";
        String wheresql = "";
        String ordersql = "";
        ResultSet rs = null;
        this.AccLedgerList = new ArrayList<>();
        this.AccLedgerSummary = new ArrayList<>();
        if (aAccPeriodId > 0) {
            wheresql = " AND al.acc_period_id=" + aAccPeriodId;
            ordersql = " ORDER BY al.account_code ASC";
            sql = sql + wheresql + " GROUP BY al.acc_period_id,al.account_code,al.currency_code " + ordersql;
            sqlsum = sqlsum + wheresql + " GROUP BY al.currency_code ";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                AccLedger accledger = null;
                while (rs.next()) {
                    accledger = new AccLedger();
                    this.setAccLedgerFromResultset(accledger, rs);
                    this.AccLedgerList.add(accledger);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
            }
        }

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sqlsum);) {
            rs = ps.executeQuery();
            AccLedger accledgersum = null;
            while (rs.next()) {
                accledgersum = new AccLedger();
                try {
                    accledgersum.setCurrencyCode(rs.getString("currency_code"));
                } catch (NullPointerException npe) {
                    accledgersum.setCurrencyCode("");
                }
                try {
                    accledgersum.setDebitAmount(rs.getDouble("debit_amount"));
                } catch (NullPointerException npe) {
                    accledgersum.setDebitAmount(0);
                }
                try {
                    accledgersum.setCreditAmount(rs.getDouble("credit_amount"));
                } catch (NullPointerException npe) {
                    accledgersum.setCreditAmount(0);
                }
                this.getAccLedgerSummary().add(accledgersum);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public void reportAccLedgerOpenBal(int aAccPeriodId) {
        this.OpenBalanceHeader = "";
        String sql = "SELECT * FROM view_ledger_open_bal al WHERE al.acc_period_id=" + aAccPeriodId + " ORDER BY  al.account_code ASC";
        ResultSet rs = null;
        this.AccLedgerListOpenBal = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccLedger accledger = null;
            while (rs.next()) {
                accledger = new AccLedger();
                this.setAccLedgerFromResultset(accledger, rs);
                this.AccLedgerListOpenBal.add(accledger);
            }
            this.OpenBalanceHeader = this.AccLedgerListOpenBal.size() + " Opening Balance Records For " + new AccPeriodBean().getAccPeriodById(aAccPeriodId).getAccPeriodName();
        } catch (Exception e) {
            System.err.println("reportAccLedgerOpenBal:" + e.getMessage());
        }
    }

    public void refreshViewCategoryBalanceSheet(int aAccPeriodId, String aAccCodeStart, String aDrCrBalance) {
        this.CategoryList = new ArrayList<>();
        double balance = 0;
        double totalDr = 0;
        double totalCr = 0;
        ResultSet rs = null;
        String sql = "SELECT account_code,acc_child_account_id,sum(debit_amount_lc) as debit_amount_lc,sum(credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances WHERE acc_period_id=" + aAccPeriodId + " AND account_code LIKE '" + aAccCodeStart + "%' "
                + "GROUP BY account_code,acc_child_account_id";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccLedger al = null;
            while (rs.next()) {
                al = new AccLedger();
                //account
                try {
                    al.setAccountCode(rs.getString("account_code"));
                } catch (Exception e) {
                    al.setAccountCode("");
                }
                try {
                    if (al.getAccountCode().length() > 0) {
                        al.setCategory(new AccCoaBean().getAccCoaByCodeOrId(al.getAccountCode(), 0).getAccountName());
                    } else {
                        al.setCategory("");
                    }
                } catch (Exception e) {
                    al.setCategory("");
                }
                //child account
                int childaccid = 0;
                try {
                    childaccid = rs.getInt("acc_child_account_id");
                } catch (Exception e) {
                    childaccid = 0;
                }
                try {
                    if (childaccid > 0) {
                        al.setSubcategory(new AccChildAccountBean().getAccChildAccById(childaccid).getChildAccountName());
                    } else {
                        al.setSubcategory("");
                    }
                } catch (Exception e) {
                    al.setSubcategory("");
                }
                //amount
                try {
                    totalDr = rs.getDouble("debit_amount_lc");
                } catch (NullPointerException npe) {
                    totalDr = 0;
                }
                try {
                    totalCr = rs.getDouble("credit_amount_lc");
                } catch (NullPointerException npe) {
                    totalCr = 0;
                }
                if (aDrCrBalance.equals("Dr")) {
                    balance = totalDr - totalCr;
                } else if (aDrCrBalance.equals("Cr")) {
                    balance = totalCr - totalDr;
                }
                al.setAmount(balance);
                //add obj
                this.CategoryList.add(al);
            }
        } catch (Exception e) {
            System.err.println("refreshViewCategoryBalanceSheet:" + e.getMessage());
        }
    }

    public void refreshViewCategoryIncomeStatement(String aSQL, String aDrCr) {
        this.JournalList = new ArrayList<>();
        ResultSet rs = null;
        String sql = aSQL;
        long recs = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccJournal aj = null;
            while (rs.next()) {
                recs = recs + 1;
                aj = new AccJournal();
                try {
                    aj.setTransaction_type_name(rs.getString("transaction_type_name"));
                } catch (Exception e) {
                    aj.setTransaction_type_name("");
                }
                try {
                    aj.setTransactionId(rs.getLong("transaction_id"));
                } catch (Exception e) {
                    aj.setTransactionId(0);
                }
                try {
                    aj.setTransaction_number(rs.getString("transaction_number"));
                } catch (Exception e) {
                    aj.setTransaction_number("");
                }
                try {
                    aj.setAccount_name(rs.getString("account_name"));
                } catch (Exception e) {
                    aj.setAccount_name("");
                }
                try {
                    aj.setCurrencyCode(rs.getString("currency_code"));
                } catch (Exception e) {
                    aj.setCurrencyCode("");
                }
                try {
                    aj.setXrate(rs.getDouble("xrate"));
                } catch (Exception e) {
                    aj.setXrate(0);
                }
                try {
                    aj.setJournalDate(new Date(rs.getDate("journal_date").getTime()));
                } catch (Exception e) {
                    aj.setJournalDate(null);
                }
                try {
                    if (aDrCr.equals("Dr")) {
                        aj.setAmount(rs.getDouble("debit_amount") - rs.getDouble("credit_amount"));
                    } else if (aDrCr.equals("Cr")) {
                        aj.setAmount(rs.getDouble("credit_amount") - rs.getDouble("debit_amount"));
                    } else {
                        aj.setAmount(0);
                    }
                } catch (Exception e) {
                    aj.setAmount(0);
                }
                //add obj
                this.JournalList.add(aj);
            }
            this.i = recs;
        } catch (Exception e) {
            System.err.println("refreshViewCategoryIncomeStatement:" + e.getMessage());
        }
    }

    public double balanceAccountsStartWith(int aAccPeriodId, String aAccCodeStart, String aDrCrBalance) {
        double balance = 0;
        double totalDr = 0;
        double totalCr = 0;
        ResultSet rs = null;
        String sql = "SELECT acc_period_id,sum(debit_amount_lc) as debit_amount_lc,sum(credit_amount_lc) as credit_amount_lc "
                + "FROM view_ledger_union_open_balances WHERE acc_period_id=" + aAccPeriodId + " AND account_code LIKE '" + aAccCodeStart + "%' "
                + "GROUP BY acc_period_id";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    totalDr = rs.getDouble("debit_amount_lc");
                } catch (NullPointerException npe) {
                    totalDr = 0;
                }
                try {
                    totalCr = rs.getDouble("credit_amount_lc");
                } catch (NullPointerException npe) {
                    totalCr = 0;
                }
            }
            if (aDrCrBalance.equals("Dr")) {
                balance = totalDr - totalCr;
            } else if (aDrCrBalance.equals("Cr")) {
                balance = totalCr - totalDr;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return balance;
    }

    public double balanceAccountsStartWith(int aAccPeriodId, String aAccCodeStart, String aDrCrBalance, Date aDate1, Date aDate2) {
        double balance = 0;
        double totalDr = 0;
        double totalCr = 0;
        ResultSet rs = null;
        String sql = "";
        if (null != aDate1 && null != aDate2) {
            sql = "SELECT lg.acc_period_id,sum(lg.debit_amount_lc) as debit_amount_lc,sum(lg.credit_amount_lc) as credit_amount_lc "
                    + "FROM ("
                    + "select ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code,"
                    + "sum(ac.debit_amount) as debit_amount,sum(ac.credit_amount) as credit_amount,"
                    + "sum(ac.debit_amount*ac.xrate) as debit_amount_lc,sum(ac.credit_amount*ac.xrate) as credit_amount_lc from acc_journal ac "
                    + "where  ac.acc_period_id=" + aAccPeriodId + " and ac.journal_date between '" + new java.sql.Date(aDate1.getTime()) + "' and '" + new java.sql.Date(aDate2.getTime()) + "' and account_code LIKE '" + aAccCodeStart + "%' "
                    + "group by ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code"
                    + ") AS lg "
                    + "GROUP BY lg.acc_period_id";
        } else {
            sql = "SELECT acc_period_id,sum(debit_amount_lc) as debit_amount_lc,sum(credit_amount_lc) as credit_amount_lc "
                    + "FROM view_ledger_union_open_balances WHERE acc_period_id=" + aAccPeriodId + " AND account_code LIKE '" + aAccCodeStart + "%' "
                    + "GROUP BY acc_period_id";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    totalDr = rs.getDouble("debit_amount_lc");
                } catch (NullPointerException npe) {
                    totalDr = 0;
                }
                try {
                    totalCr = rs.getDouble("credit_amount_lc");
                } catch (NullPointerException npe) {
                    totalCr = 0;
                }
            }
            if (aDrCrBalance.equals("Dr")) {
                balance = totalDr - totalCr;
            } else if (aDrCrBalance.equals("Cr")) {
                balance = totalCr - totalDr;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return balance;
    }

    public void initViewCategoryBalanceSheet(int aAccPeriodId, String aAccCodeStart, String aDrCrBalance, String aCategoryHeader) {
        this.CategoryHeader = aCategoryHeader;
        this.refreshViewCategoryBalanceSheet(aAccPeriodId, aAccCodeStart, aDrCrBalance);
    }

    public void initViewCategoryIncomeStatement(AccIncomeStatement aAccIncomeStatement, String aCategoryHeader, String aCategory) {
        String sql = "SELECT y.transaction_type_name,t.transaction_id,t.transaction_number,a.account_name,j.currency_code,j.journal_date,j.debit_amount,j.credit_amount,j.xrate "
                + "FROM acc_journal j "
                + "INNER JOIN transaction t ON j.transaction_id=t.transaction_id "
                + "INNER JOIN transaction_type y ON j.transaction_type_id=y.transaction_type_id "
                + "INNER JOIN acc_coa a ON j.acc_coa_id=a.acc_coa_id  "
                + "WHERE j.acc_period_id=" + aAccIncomeStatement.getAccPeriodId();
        String sql2 = "SELECT count(*) as n "
                + "FROM acc_journal j "
                + "INNER JOIN transaction t ON j.transaction_id=t.transaction_id "
                + "INNER JOIN transaction_type y ON j.transaction_type_id=y.transaction_type_id "
                + "INNER JOIN acc_coa a ON j.acc_coa_id=a.acc_coa_id  "
                + "WHERE j.acc_period_id=" + aAccIncomeStatement.getAccPeriodId();
        String aWhere = "";
        String AccStartWith = "";
        String DrCr = "";
        String aOrder = " ORDER BY j.journal_date DESC,y.transaction_type_name ASC";
        this.CategoryHeader = aCategoryHeader;
        //Upper case 1st Character for the category
        aCategory = aCategory.substring(0, 1).toUpperCase() + aCategory.substring(1);
        //compile sql
        if (null != aAccIncomeStatement.getDate1() && null != aAccIncomeStatement.getDate2()) {
            aWhere = aWhere + " AND j.journal_date between '" + new java.sql.Date(aAccIncomeStatement.getDate1().getTime()) + "' and '" + new java.sql.Date(aAccIncomeStatement.getDate2().getTime()) + "'";
        }
        switch (aCategory) {
            case "RevORSaleProduct":
                AccStartWith = "4-10-000-010";
                DrCr = "Cr";
                break;
            case "RevORSaleService":
                AccStartWith = "4-10-000-020";
                DrCr = "Cr";
                break;
            case "RevORSaleHire":
                AccStartWith = "4-10-000-050";
                DrCr = "Cr";
                break;
            case "RevORSaleDisc":
                AccStartWith = "4-10-000-030";
                DrCr = "Dr";
                break;
            case "RevORSaleReturn":
                AccStartWith = "4-10-000-040";
                DrCr = "Dr";
                break;
            case "RevNORInterest":
                AccStartWith = "4-20-000-010";
                DrCr = "Cr";
                break;
            case "RevNORDividend":
                AccStartWith = "4-20-000-020";
                DrCr = "Cr";
                break;
            case "RevNORCommission":
                AccStartWith = "4-20-000-030";
                DrCr = "Cr";
                break;
            case "RevNORRental":
                AccStartWith = "4-20-000-040";
                DrCr = "Cr";
                break;
            case "RevNORGainSaleAsset":
                AccStartWith = "4-20-000-050";
                DrCr = "Cr";
                break;
            case "RevNORGainGift":
                AccStartWith = "4-20-000-060";
                DrCr = "Cr";
                break;
            case "RevNORGainExchange":
                AccStartWith = "4-20-000-070";
                DrCr = "Cr";
                break;
            case "RevNOROther":
                AccStartWith = "4-20-000-080";
                DrCr = "Cr";
                break;
            case "ExpCOGSProduct":
                AccStartWith = "5-10-000-010";
                DrCr = "Dr";
                break;
            case "ExpCOGSService":
                AccStartWith = "5-10-000-020";
                DrCr = "Dr";
                break;
            case "ExpCOGSFreight":
                AccStartWith = "5-10-000-030";
                DrCr = "Dr";
                break;
            case "ExpCOGSInvAdj":
                AccStartWith = "5-10-000-040";
                DrCr = "Dr";
                break;
            case "ExpCOGSReturn":
                AccStartWith = "5-10-000-050";
                DrCr = "Cr";
                break;
            case "ExpCOGSDisc":
                AccStartWith = "5-10-000-060";
                DrCr = "Cr";
                break;
            case "ExpCOGSManfSold":
                AccStartWith = "5-10-000-070";
                DrCr = "Dr";
                break;
            case "ExpCOGSLoyalty":
                AccStartWith = "5-10-000-080";
                DrCr = "Dr";
                break;
            case "ExpCOGSInvWriteOff":
                AccStartWith = "5-10-000-090";
                DrCr = "Dr";
                break;
            case "ExpOEAdvertise":
                AccStartWith = "5-20-000";
                DrCr = "Dr";
                break;
            case "ExpOEAudit":
                AccStartWith = "5-20-010";
                DrCr = "Dr";
                break;
            case "ExpOEBadDebts":
                AccStartWith = "5-20-020";
                DrCr = "Dr";
                break;
            case "ExpOECommission":
                AccStartWith = "5-20-030";
                DrCr = "Dr";
                break;
            case "ExpOEComputer":
                AccStartWith = "5-20-040";
                DrCr = "Dr";
                break;
            case "ExpOEDonations":
                AccStartWith = "5-20-050";
                DrCr = "Dr";
                break;
            case "ExpOEEntertainment":
                AccStartWith = "5-20-060";
                DrCr = "Dr";
                break;
            case "ExpOEFreightTransport":
                AccStartWith = "5-20-070";
                DrCr = "Dr";
                break;
            case "ExpOEGift":
                AccStartWith = "5-20-080";
                DrCr = "Dr";
                break;
            case "ExpOEHotelLodging":
                AccStartWith = "5-20-090";
                DrCr = "Dr";
                break;
            case "ExpOELegal":
                AccStartWith = "5-20-100";
                DrCr = "Dr";
                break;
            case "ExpOEUtility":
                AccStartWith = "5-20-110";
                DrCr = "Dr";
                break;
            case "ExpOERent":
                AccStartWith = "5-20-120";
                DrCr = "Dr";
                break;
            case "ExpOERates":
                AccStartWith = "5-20-130";
                DrCr = "Dr";
                break;
            case "ExpOERepairMaint":
                AccStartWith = "5-20-140";
                DrCr = "Dr";
                break;
            case "ExpOESalesPromotion":
                AccStartWith = "5-20-150";
                DrCr = "Dr";
                break;
            case "ExpOEStaffWelfare":
                AccStartWith = "5-20-160";
                DrCr = "Dr";
                break;
            case "ExpOEStartupPreOperate":
                AccStartWith = "5-20-170";
                DrCr = "Dr";
                break;
            case "ExpOEStationeryPrint":
                AccStartWith = "5-20-180";
                DrCr = "Dr";
                break;
            case "ExpOESubsAllowance":
                AccStartWith = "5-20-190";
                DrCr = "Dr";
                break;
            case "ExpOETelephone":
                AccStartWith = "5-20-200";
                DrCr = "Dr";
                break;
            case "ExpOETraining":
                AccStartWith = "5-20-210";
                DrCr = "Dr";
                break;
            case "ExpOETravel":
                AccStartWith = "5-20-220";
                DrCr = "Dr";
                break;
            case "ExpOEWorkshopConf":
                AccStartWith = "5-20-230";
                DrCr = "Dr";
                break;
            case "ExpOEInternet":
                AccStartWith = "5-20-240";
                DrCr = "Dr";
                break;
            case "ExpOEDepriciation":
                AccStartWith = "5-20-250";
                DrCr = "Dr";
                break;
            case "ExpOELossDisposalAsset":
                AccStartWith = "5-20-260";
                DrCr = "Dr";
                break;
            case "ExpOEManagementFees":
                AccStartWith = "5-20-270";
                DrCr = "Dr";
                break;
            case "ExpOEScientificResearch":
                AccStartWith = "5-20-280";
                DrCr = "Dr";
                break;
            case "ExpOEEmployment":
                AccStartWith = "5-20-290";
                DrCr = "Dr";
                break;
            case "ExpOEFinancial":
                AccStartWith = "5-20-300";
                DrCr = "Dr";
                break;
            case "ExpOEShortInsurance":
                AccStartWith = "5-20-400";
                DrCr = "Dr";
                break;
            case "ExpOEIncomeTax":
                AccStartWith = "5-20-410";
                DrCr = "Dr";
                break;
            case "ExpOEProposedDividend":
                AccStartWith = "5-20-420";
                DrCr = "Dr";
                break;
            case "ExpOEOther":
                AccStartWith = "5-20-430";
                DrCr = "Dr";
                break;
            case "ExpNOE":
                AccStartWith = "5-30-000";
                DrCr = "Dr";
                break;
        }
        aWhere = aWhere + " AND j.account_code LIKE '" + AccStartWith + "%'";
        sql = sql + aWhere + aOrder + " LIMIT 1000";
        sql2 = sql2 + aWhere;
        this.n = new UtilityBean().getN(sql2);
        this.refreshViewCategoryIncomeStatement(sql, DrCr);
    }

    public void refreshAccBalanceSheet(AccBalanceSheet aAccBalanceSheet) {
        AccPeriod accperiod = null;
        accperiod = new AccPeriodBean().getAccPeriodById(aAccBalanceSheet.getAccPeriodId());

        double TempTotal = 0;
        //Assets - Fixed
        aAccBalanceSheet.setAssetFixedPPE(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-20-000", "Dr"));
        TempTotal = TempTotal + aAccBalanceSheet.getAssetFixedPPE();
        aAccBalanceSheet.setAssetFixedAccumDep(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-20-010", "Cr"));
        if (aAccBalanceSheet.getAssetFixedAccumDep() >= 0) {
            TempTotal = TempTotal - aAccBalanceSheet.getAssetFixedAccumDep();
        } else {
            TempTotal = TempTotal + aAccBalanceSheet.getAssetFixedAccumDep();
        }
        aAccBalanceSheet.setAssetFixedOtherNonCur(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-20-020", "Dr"));
        TempTotal = TempTotal + aAccBalanceSheet.getAssetFixedOtherNonCur();
        //Assets - Current
        aAccBalanceSheet.setAssetCurCash(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-00-000", "Dr"));
        TempTotal = TempTotal + aAccBalanceSheet.getAssetCurCash();
        aAccBalanceSheet.setAssetCurRec(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-00-010", "Dr"));
        TempTotal = TempTotal + aAccBalanceSheet.getAssetCurRec();
        aAccBalanceSheet.setAssetCurInv(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-00-020", "Dr"));
        TempTotal = TempTotal + aAccBalanceSheet.getAssetCurInv();
        aAccBalanceSheet.setAssetCurPreExp(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-00-030", "Dr"));
        TempTotal = TempTotal + aAccBalanceSheet.getAssetCurPreExp();
        aAccBalanceSheet.setAssetCurOtherCur(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "1-00-040", "Dr"));
        TempTotal = TempTotal + aAccBalanceSheet.getAssetCurOtherCur();
        //Assets - Total
        aAccBalanceSheet.setAssetTotal(TempTotal);
        TempTotal = 0;

        //Liabilities - Long Term
        aAccBalanceSheet.setLiabLongDebt(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "2-10-000", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getLiabLongDebt();
        aAccBalanceSheet.setLiabLongDefTax(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "2-10-010", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getLiabLongDefTax();
        aAccBalanceSheet.setLiabLongOtherNonCur(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "2-10-020", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getLiabLongOtherNonCur();
        //Liabilities - Current
        aAccBalanceSheet.setLiabCurPay(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "2-00-000", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getLiabCurPay();
        aAccBalanceSheet.setLiabCurAccrComp(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "2-00-010", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getLiabCurAccrComp();
        aAccBalanceSheet.setLiabCurAccrTax(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "2-00-020", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getLiabCurAccrTax();
        aAccBalanceSheet.setLiabCurOtherCur(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "2-00-030", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getLiabCurOtherCur();
        //Liabilities - Total
        aAccBalanceSheet.setLiabTotal(TempTotal);
        TempTotal = 0;

        //Net Assets
        if (aAccBalanceSheet.getLiabTotal() >= 0) {
            aAccBalanceSheet.setNetAssets(aAccBalanceSheet.getAssetTotal() - aAccBalanceSheet.getLiabTotal());
        } else {
            aAccBalanceSheet.setNetAssets(aAccBalanceSheet.getAssetTotal() + aAccBalanceSheet.getLiabTotal());
        }

        //Equity
        aAccBalanceSheet.setEquityPaidCap(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-010", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getEquityPaidCap();
        aAccBalanceSheet.setEquityPartCap(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-020", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getEquityPartCap();
        aAccBalanceSheet.setEquityCommStock(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-030", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getEquityCommStock();
        aAccBalanceSheet.setEquityPrefStock(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-040", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getEquityPrefStock();
        aAccBalanceSheet.setEquityMembCont(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-050", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getEquityMembCont();
        aAccBalanceSheet.setEquityRetEarn(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-060", "Cr"));
        TempTotal = TempTotal + aAccBalanceSheet.getEquityRetEarn();
        aAccBalanceSheet.setEquityDrawing(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-070", "Dr"));
        if (aAccBalanceSheet.getEquityDrawing() >= 0) {
            TempTotal = TempTotal - aAccBalanceSheet.getEquityDrawing();
        } else {
            TempTotal = TempTotal + aAccBalanceSheet.getEquityDrawing();
        }
        aAccBalanceSheet.setEquityDividend(this.balanceAccountsStartWith(aAccBalanceSheet.getAccPeriodId(), "3-10-000-080", "Dr"));
        if (aAccBalanceSheet.getEquityDividend() >= 0) {
            TempTotal = TempTotal - aAccBalanceSheet.getEquityDividend();
        } else {
            TempTotal = TempTotal + aAccBalanceSheet.getEquityDividend();
        }
        //Equitys - Total
        aAccBalanceSheet.setEquityTotal(TempTotal);
        TempTotal = 0;
    }

    public void refreshAccIncomeStatement(AccIncomeStatement aAccIncomeStatement, Date aDate1, Date aDate2) {
        double TempTotal = 0;
        //Revenue - OR(Sales)
        aAccIncomeStatement.setRevORSaleProduct(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-010", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevORSaleProduct();
        aAccIncomeStatement.setRevORSaleService(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-020", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevORSaleService();
        aAccIncomeStatement.setRevORSaleHire(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-050", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevORSaleHire();
        aAccIncomeStatement.setRevORSaleDisc(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-030", "Dr", aDate1, aDate2));
        TempTotal = TempTotal - aAccIncomeStatement.getRevORSaleDisc();
        aAccIncomeStatement.setRevORSaleReturn(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-040", "Dr", aDate1, aDate2));
        TempTotal = TempTotal - aAccIncomeStatement.getRevORSaleReturn();
        //Revenue - OR(Sales) - Total
        aAccIncomeStatement.setRevORSaleTotal(TempTotal);
        TempTotal = 0;
        //Revenue - NOR
        aAccIncomeStatement.setRevNORInterest(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-010", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORInterest();
        aAccIncomeStatement.setRevNORDividend(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-020", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORDividend();
        aAccIncomeStatement.setRevNORCommission(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-030", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORCommission();
        aAccIncomeStatement.setRevNORRental(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-040", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORRental();
        aAccIncomeStatement.setRevNORGainSaleAsset(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-050", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORGainSaleAsset();
        aAccIncomeStatement.setRevNORGainGift(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-060", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORGainGift();
        aAccIncomeStatement.setRevNORGainExchange(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-070", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORGainExchange();
        aAccIncomeStatement.setRevNOROther(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-080", "Cr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNOROther();
        //Revenue - NOR - Total
        aAccIncomeStatement.setRevNORTotal(TempTotal);
        TempTotal = 0;
        aAccIncomeStatement.setRevTotal(aAccIncomeStatement.getRevORSaleTotal() + aAccIncomeStatement.getRevNORTotal());

        //Expenses - COGS
        aAccIncomeStatement.setExpCOGSProduct(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-010", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSProduct();
        aAccIncomeStatement.setExpCOGSService(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-020", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSService();
        aAccIncomeStatement.setExpCOGSFreight(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-030", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSFreight();
        aAccIncomeStatement.setExpCOGSInvAdj(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-040", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSInvAdj();
        aAccIncomeStatement.setExpCOGSReturn(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-050", "Cr", aDate1, aDate2));
        TempTotal = TempTotal - aAccIncomeStatement.getExpCOGSReturn();
        aAccIncomeStatement.setExpCOGSDisc(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-060", "Cr", aDate1, aDate2));
        TempTotal = TempTotal - aAccIncomeStatement.getExpCOGSDisc();
        aAccIncomeStatement.setExpCOGSManfSold(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-070", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSManfSold();
        aAccIncomeStatement.setExpCOGSLoyalty(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-080", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSLoyalty();
        aAccIncomeStatement.setExpCOGSInvWriteOff(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-090", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSInvWriteOff();
        //Expense COGS - Total
        aAccIncomeStatement.setExpCOGSTotal(TempTotal);
        TempTotal = 0;
        if (aAccIncomeStatement.getExpCOGSTotal() >= 0) {
            aAccIncomeStatement.setGrossProfit(aAccIncomeStatement.getRevTotal() - aAccIncomeStatement.getExpCOGSTotal());
        } else {
            aAccIncomeStatement.setGrossProfit(aAccIncomeStatement.getRevTotal() + aAccIncomeStatement.getExpCOGSTotal());
        }

        //Expenses - OE
        aAccIncomeStatement.setExpOEAdvertise(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-000", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEAdvertise();
        aAccIncomeStatement.setExpOEAudit(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-010", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEAudit();
        aAccIncomeStatement.setExpOEBadDebts(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-020", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEBadDebts();
        aAccIncomeStatement.setExpOECommission(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-030", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOECommission();
        aAccIncomeStatement.setExpOEComputer(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-040", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEComputer();
        aAccIncomeStatement.setExpOEDonations(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-050", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEDonations();
        aAccIncomeStatement.setExpOEEntertainment(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-060", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEEntertainment();
        aAccIncomeStatement.setExpOEFreightTransport(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-070", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEFreightTransport();
        aAccIncomeStatement.setExpOEGift(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-080", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEGift();
        aAccIncomeStatement.setExpOEHotelLodging(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-090", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEHotelLodging();
        aAccIncomeStatement.setExpOELegal(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-100", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOELegal();
        aAccIncomeStatement.setExpOEUtility(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-110", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEUtility();
        aAccIncomeStatement.setExpOERent(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-120", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOERent();
        aAccIncomeStatement.setExpOERates(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-130", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOERates();
        aAccIncomeStatement.setExpOERepairMaint(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-140", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOERepairMaint();
        aAccIncomeStatement.setExpOESalesPromotion(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-150", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOESalesPromotion();
        aAccIncomeStatement.setExpOEStaffWelfare(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-160", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEStaffWelfare();
        aAccIncomeStatement.setExpOEStartupPreOperate(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-170", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEStartupPreOperate();
        aAccIncomeStatement.setExpOEStationeryPrint(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-180", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEStationeryPrint();
        aAccIncomeStatement.setExpOESubsAllowance(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-190", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOESubsAllowance();
        aAccIncomeStatement.setExpOETelephone(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-200", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOETelephone();
        aAccIncomeStatement.setExpOETraining(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-210", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOETraining();
        aAccIncomeStatement.setExpOETravel(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-220", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOETravel();
        aAccIncomeStatement.setExpOEWorkshopConf(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-230", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEWorkshopConf();
        aAccIncomeStatement.setExpOEInternet(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-240", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEInternet();
        aAccIncomeStatement.setExpOEDepriciation(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-250", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEDepriciation();
        aAccIncomeStatement.setExpOELossDisposalAsset(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-260", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOELossDisposalAsset();
        aAccIncomeStatement.setExpOEManagementFees(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-270", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEManagementFees();
        aAccIncomeStatement.setExpOEScientificResearch(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-280", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEScientificResearch();
        aAccIncomeStatement.setExpOEEmployment(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-290", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEEmployment();
        aAccIncomeStatement.setExpOEFinancial(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-300", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEFinancial();
        aAccIncomeStatement.setExpOEShortInsurance(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-400", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEShortInsurance();
        aAccIncomeStatement.setExpOEIncomeTax(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-410", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEIncomeTax();
        aAccIncomeStatement.setExpOEProposedDividend(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-420", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEProposedDividend();
        aAccIncomeStatement.setExpOEOther(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-430", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEOther();
        //Expense OE - Total
        aAccIncomeStatement.setExpOETotal(TempTotal);
        TempTotal = 0;

        //Expense NOE
        aAccIncomeStatement.setExpNOE(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-30-000", "Dr", aDate1, aDate2));
        TempTotal = TempTotal + aAccIncomeStatement.getExpNOE();
        //Expense OE - Total
        aAccIncomeStatement.setExpNOETotal(TempTotal);
        TempTotal = 0;

        //Expenses Total
        aAccIncomeStatement.setExpOENOETotal(aAccIncomeStatement.getExpOETotal() + aAccIncomeStatement.getExpNOETotal());
        //Net Profit
        //aAccIncomeStatement.setNetProfit(aAccIncomeStatement.getRevTotal() - aAccIncomeStatement.getExpCOGSTotal() - aAccIncomeStatement.getExpOENOETotal());
        double LessExpCOGSTotal = 0;
        if (aAccIncomeStatement.getExpCOGSTotal() >= 0) {
            LessExpCOGSTotal = -1 * aAccIncomeStatement.getExpCOGSTotal();
        } else {
            LessExpCOGSTotal = aAccIncomeStatement.getExpCOGSTotal();
        }
        double LessExpOENOETotal = 0;
        if (aAccIncomeStatement.getExpOENOETotal() >= 0) {
            LessExpOENOETotal = -1 * aAccIncomeStatement.getExpOENOETotal();
        } else {
            LessExpOENOETotal = aAccIncomeStatement.getExpOENOETotal();
        }
        aAccIncomeStatement.setNetProfit((aAccIncomeStatement.getRevTotal() + LessExpCOGSTotal) + LessExpOENOETotal);
    }

    public void refreshAccIncomeStatement(AccIncomeStatement aAccIncomeStatement) {
        double TempTotal = 0;
        //Revenue - OR(Sales)
        aAccIncomeStatement.setRevORSaleProduct(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-010", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevORSaleProduct();
        aAccIncomeStatement.setRevORSaleService(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-020", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevORSaleService();
        aAccIncomeStatement.setRevORSaleHire(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-050", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevORSaleHire();
        aAccIncomeStatement.setRevORSaleDisc(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-030", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal - aAccIncomeStatement.getRevORSaleDisc();
        aAccIncomeStatement.setRevORSaleReturn(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-10-000-040", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal - aAccIncomeStatement.getRevORSaleReturn();
        //Revenue - OR(Sales) - Total
        aAccIncomeStatement.setRevORSaleTotal(TempTotal);
        TempTotal = 0;
        //Revenue - NOR
        aAccIncomeStatement.setRevNORInterest(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-010", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORInterest();
        aAccIncomeStatement.setRevNORDividend(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-020", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORDividend();
        aAccIncomeStatement.setRevNORCommission(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-030", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORCommission();
        aAccIncomeStatement.setRevNORRental(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-040", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORRental();
        aAccIncomeStatement.setRevNORGainSaleAsset(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-050", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORGainSaleAsset();
        aAccIncomeStatement.setRevNORGainGift(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-060", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORGainGift();
        aAccIncomeStatement.setRevNORGainExchange(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-070", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNORGainExchange();
        aAccIncomeStatement.setRevNOROther(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "4-20-000-080", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getRevNOROther();
        //Revenue - NOR - Total
        aAccIncomeStatement.setRevNORTotal(TempTotal);
        TempTotal = 0;
        aAccIncomeStatement.setRevTotal(aAccIncomeStatement.getRevORSaleTotal() + aAccIncomeStatement.getRevNORTotal());

        //Expenses - COGS
        aAccIncomeStatement.setExpCOGSProduct(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-010", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSProduct();
        aAccIncomeStatement.setExpCOGSService(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-020", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSService();
        aAccIncomeStatement.setExpCOGSFreight(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-030", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSFreight();
        aAccIncomeStatement.setExpCOGSInvAdj(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-040", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSInvAdj();
        aAccIncomeStatement.setExpCOGSReturn(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-050", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal - aAccIncomeStatement.getExpCOGSReturn();
        aAccIncomeStatement.setExpCOGSDisc(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-060", "Cr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal - aAccIncomeStatement.getExpCOGSDisc();
        aAccIncomeStatement.setExpCOGSManfSold(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-070", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSManfSold();
        aAccIncomeStatement.setExpCOGSLoyalty(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-080", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSLoyalty();
        aAccIncomeStatement.setExpCOGSInvWriteOff(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-10-000-090", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpCOGSInvWriteOff();
        //Expense COGS - Total
        aAccIncomeStatement.setExpCOGSTotal(TempTotal);
        TempTotal = 0;
        if (aAccIncomeStatement.getExpCOGSTotal() >= 0) {
            aAccIncomeStatement.setGrossProfit(aAccIncomeStatement.getRevTotal() - aAccIncomeStatement.getExpCOGSTotal());
        } else {
            aAccIncomeStatement.setGrossProfit(aAccIncomeStatement.getRevTotal() + aAccIncomeStatement.getExpCOGSTotal());
        }

        //Expenses - OE
        aAccIncomeStatement.setExpOEAdvertise(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-000", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEAdvertise();
        aAccIncomeStatement.setExpOEAudit(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-010", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEAudit();
        aAccIncomeStatement.setExpOEBadDebts(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-020", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEBadDebts();
        aAccIncomeStatement.setExpOECommission(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-030", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOECommission();
        aAccIncomeStatement.setExpOEComputer(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-040", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEComputer();
        aAccIncomeStatement.setExpOEDonations(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-050", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEDonations();
        aAccIncomeStatement.setExpOEEntertainment(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-060", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEEntertainment();
        aAccIncomeStatement.setExpOEFreightTransport(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-070", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEFreightTransport();
        aAccIncomeStatement.setExpOEGift(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-080", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEGift();
        aAccIncomeStatement.setExpOEHotelLodging(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-090", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEHotelLodging();
        aAccIncomeStatement.setExpOELegal(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-100", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOELegal();
        aAccIncomeStatement.setExpOEUtility(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-110", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEUtility();
        aAccIncomeStatement.setExpOERent(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-120", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOERent();
        aAccIncomeStatement.setExpOERates(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-130", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOERates();
        aAccIncomeStatement.setExpOERepairMaint(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-140", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOERepairMaint();
        aAccIncomeStatement.setExpOESalesPromotion(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-150", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOESalesPromotion();
        aAccIncomeStatement.setExpOEStaffWelfare(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-160", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEStaffWelfare();
        aAccIncomeStatement.setExpOEStartupPreOperate(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-170", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEStartupPreOperate();
        aAccIncomeStatement.setExpOEStationeryPrint(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-180", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEStationeryPrint();
        aAccIncomeStatement.setExpOESubsAllowance(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-190", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOESubsAllowance();
        aAccIncomeStatement.setExpOETelephone(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-200", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOETelephone();
        aAccIncomeStatement.setExpOETraining(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-210", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOETraining();
        aAccIncomeStatement.setExpOETravel(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-220", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOETravel();
        aAccIncomeStatement.setExpOEWorkshopConf(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-230", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEWorkshopConf();
        aAccIncomeStatement.setExpOEInternet(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-240", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEInternet();
        aAccIncomeStatement.setExpOEDepriciation(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-250", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEDepriciation();
        aAccIncomeStatement.setExpOELossDisposalAsset(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-260", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOELossDisposalAsset();
        aAccIncomeStatement.setExpOEManagementFees(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-270", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEManagementFees();
        aAccIncomeStatement.setExpOEScientificResearch(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-280", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEScientificResearch();
        aAccIncomeStatement.setExpOEEmployment(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-290", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEEmployment();
        aAccIncomeStatement.setExpOEFinancial(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-300", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEFinancial();
        aAccIncomeStatement.setExpOEShortInsurance(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-400", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEShortInsurance();
        aAccIncomeStatement.setExpOEIncomeTax(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-410", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEIncomeTax();
        aAccIncomeStatement.setExpOEProposedDividend(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-420", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEProposedDividend();
        aAccIncomeStatement.setExpOEOther(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-20-430", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpOEOther();
        //Expense OE - Total
        aAccIncomeStatement.setExpOETotal(TempTotal);
        TempTotal = 0;

        //Expense NOE
        aAccIncomeStatement.setExpNOE(this.balanceAccountsStartWith(aAccIncomeStatement.getAccPeriodId(), "5-30-000", "Dr", aAccIncomeStatement.getDate1(), aAccIncomeStatement.getDate2()));
        TempTotal = TempTotal + aAccIncomeStatement.getExpNOE();
        //Expense OE - Total
        aAccIncomeStatement.setExpNOETotal(TempTotal);
        TempTotal = 0;

        //Expenses Total
        aAccIncomeStatement.setExpOENOETotal(aAccIncomeStatement.getExpOETotal() + aAccIncomeStatement.getExpNOETotal());
        //Net Profit
        //aAccIncomeStatement.setNetProfit(aAccIncomeStatement.getRevTotal() - aAccIncomeStatement.getExpCOGSTotal() - aAccIncomeStatement.getExpOENOETotal());
        double LessExpCOGSTotal = 0;
        if (aAccIncomeStatement.getExpCOGSTotal() >= 0) {
            LessExpCOGSTotal = -1 * aAccIncomeStatement.getExpCOGSTotal();
        } else {
            LessExpCOGSTotal = aAccIncomeStatement.getExpCOGSTotal();
        }
        double LessExpOENOETotal = 0;
        if (aAccIncomeStatement.getExpOENOETotal() >= 0) {
            LessExpOENOETotal = -1 * aAccIncomeStatement.getExpOENOETotal();
        } else {
            LessExpOENOETotal = aAccIncomeStatement.getExpOENOETotal();
        }
        aAccIncomeStatement.setNetProfit((aAccIncomeStatement.getRevTotal() + LessExpCOGSTotal) + LessExpOENOETotal);
    }

    public void initResetAccLedgerReport(AccLedger aAccLedger, AccLedgerBean aAccLedgerBean) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetAccLedgerReport(aAccLedger, aAccLedgerBean);
        }
    }

    public void resetAccLedgerReport(AccLedger aAccLedger, AccLedgerBean aAccLedgerBean) {
        aAccLedgerBean.setActionMessage("");
        try {
            this.clearAccLedger(aAccLedger);
        } catch (NullPointerException npe) {
        }
        try {
            aAccLedgerBean.AccLedgerList.clear();
            aAccLedgerBean.AccLedgerSummary.clear();
        } catch (NullPointerException npe) {
        }
    }

    public void initResetAccBalanceSheet(AccBalanceSheet aAccBalanceSheet) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            aAccBalanceSheet = new AccBalanceSheet();
        }
    }

    public void initResetAccIncomeStatement(AccIncomeStatement aAccIncomeStatement) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            aAccIncomeStatement = new AccIncomeStatement();
            aAccIncomeStatement.setDate1(null);
            aAccIncomeStatement.setDate2(null);
        }
    }

    public void initResetAccIncomeStatement(AccIncomeStatement aAccIncomeStatement, AccJournalBean aAccJournalBean) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            aAccIncomeStatement = new AccIncomeStatement();
            //aAccJournalBean.setDate1(null);
            //aAccJournalBean.setDate2(null);
            aAccJournalBean = new AccJournalBean();
        }
    }

    public void clearAccLedger(AccLedger accledger) {
        try {
            accledger.setAccLedgerId(0);
        } catch (NullPointerException npe) {
            accledger.setAccLedgerId(0);
        }
        try {
            accledger.setAccPeriodId(0);
        } catch (NullPointerException npe) {
            accledger.setAccPeriodId(0);
        }
        try {
            accledger.setBillTransactorId(0);
        } catch (NullPointerException npe) {
            accledger.setBillTransactorId(0);
        }

        try {
            accledger.setAccountCode("");
        } catch (NullPointerException npe) {
            accledger.setAccountCode("");
        }
        try {
            accledger.setAccChildAccountId(0);
        } catch (NullPointerException npe) {
            accledger.setAccChildAccountId(0);
        }
        try {
            accledger.setCurrencyCode("");
        } catch (NullPointerException npe) {
            accledger.setCurrencyCode("");
        }
        try {
            accledger.setDebitAmount(0);
        } catch (NullPointerException npe) {
            accledger.setDebitAmount(0);
        }
        try {
            accledger.setCreditAmount(0);
        } catch (NullPointerException npe) {
            accledger.setCreditAmount(0);
        }
        try {
            accledger.setDebitAmountLc(0);
        } catch (NullPointerException npe) {
            accledger.setDebitAmountLc(0);
        }
        try {
            accledger.setCreditAmountLc(0);
        } catch (NullPointerException npe) {
            accledger.setCreditAmountLc(0);
        }
    }

    public List<AccLedger> getAccLedgerBean(AccLedger aAccLedger) {
        List<AccLedger> als = new ArrayList<>();
        return als;
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
     * @return the AccLedgerList
     */
    public List<AccLedger> getAccLedgerList() {
        return AccLedgerList;
    }

    /**
     * @param AccLedgerList the AccLedgerList to set
     */
    public void setAccLedgerList(List<AccLedger> AccLedgerList) {
        this.AccLedgerList = AccLedgerList;
    }

    /**
     * @return the AccLedgerObj
     */
    public AccLedger getAccLedgerObj() {
        return AccLedgerObj;
    }

    /**
     * @param AccLedgerObj the AccLedgerObj to set
     */
    public void setAccLedgerObj(AccLedger AccLedgerObj) {
        this.AccLedgerObj = AccLedgerObj;
    }

    /**
     * @return the AccLedgerListOpenBal
     */
    public List<AccLedger> getAccLedgerListOpenBal() {
        return AccLedgerListOpenBal;
    }

    /**
     * @param AccLedgerListOpenBal the AccLedgerListOpenBal to set
     */
    public void setAccLedgerListOpenBal(List<AccLedger> AccLedgerListOpenBal) {
        this.AccLedgerListOpenBal = AccLedgerListOpenBal;
    }

    /**
     * @return the AccLedgerSummary
     */
    public List<AccLedger> getAccLedgerSummary() {
        return AccLedgerSummary;
    }

    /**
     * @param AccLedgerSummary the AccLedgerSummary to set
     */
    public void setAccLedgerSummary(List<AccLedger> AccLedgerSummary) {
        this.AccLedgerSummary = AccLedgerSummary;
    }

    /**
     * @return the AccLedgerCashAccBal
     */
    public List<AccLedger> getAccLedgerCashAccBal() {
        return AccLedgerCashAccBal;
    }

    /**
     * @param AccLedgerCashAccBal the AccLedgerCashAccBal to set
     */
    public void setAccLedgerCashAccBal(List<AccLedger> AccLedgerCashAccBal) {
        this.AccLedgerCashAccBal = AccLedgerCashAccBal;
    }

    /**
     * @return the AccLedgerReceivablesAccBal
     */
    public List<AccLedger> getAccLedgerReceivablesAccBal() {
        return AccLedgerReceivablesAccBal;
    }

    /**
     * @param AccLedgerReceivablesAccBal the AccLedgerReceivablesAccBal to set
     */
    public void setAccLedgerReceivablesAccBal(List<AccLedger> AccLedgerReceivablesAccBal) {
        this.AccLedgerReceivablesAccBal = AccLedgerReceivablesAccBal;
    }

    /**
     * @return the AccLedgerPayablesAccBal
     */
    public List<AccLedger> getAccLedgerPayablesAccBal() {
        return AccLedgerPayablesAccBal;
    }

    /**
     * @param AccLedgerPayablesAccBal the AccLedgerPayablesAccBal to set
     */
    public void setAccLedgerPayablesAccBal(List<AccLedger> AccLedgerPayablesAccBal) {
        this.AccLedgerPayablesAccBal = AccLedgerPayablesAccBal;
    }

    /**
     * @return the AccountBalance
     */
    public double getAccountBalance() {
        return AccountBalance;
    }

    /**
     * @param AccountBalance the AccountBalance to set
     */
    public void setAccountBalance(double AccountBalance) {
        this.AccountBalance = AccountBalance;
    }

    /**
     * @return the CategoryList
     */
    public List<AccLedger> getCategoryList() {
        return CategoryList;
    }

    /**
     * @param CategoryList the CategoryList to set
     */
    public void setCategoryList(List<AccLedger> CategoryList) {
        this.CategoryList = CategoryList;
    }

    /**
     * @return the CategoryHeader
     */
    public String getCategoryHeader() {
        return CategoryHeader;
    }

    /**
     * @param CategoryHeader the CategoryHeader to set
     */
    public void setCategoryHeader(String CategoryHeader) {
        this.CategoryHeader = CategoryHeader;
    }

    /**
     * @return the JournalList
     */
    public List<AccJournal> getJournalList() {
        return JournalList;
    }

    /**
     * @param JournalList the JournalList to set
     */
    public void setJournalList(List<AccJournal> JournalList) {
        this.JournalList = JournalList;
    }

    /**
     * @return the n
     */
    public long getN() {
        return n;
    }

    /**
     * @param n the n to set
     */
    public void setN(long n) {
        this.n = n;
    }

    /**
     * @return the i
     */
    public long getI() {
        return i;
    }

    /**
     * @param i the i to set
     */
    public void setI(long i) {
        this.i = i;
    }

    /**
     * @return the OpenBalanceHeader
     */
    public String getOpenBalanceHeader() {
        return OpenBalanceHeader;
    }

    /**
     * @param OpenBalanceHeader the OpenBalanceHeader to set
     */
    public void setOpenBalanceHeader(String OpenBalanceHeader) {
        this.OpenBalanceHeader = OpenBalanceHeader;
    }
}
