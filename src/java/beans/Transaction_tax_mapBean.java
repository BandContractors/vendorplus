package beans;

import connections.DBConnection;
import entities.Trans;
import entities.Transaction_tax_map;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
@ManagedBean(name = "transaction_tax_mapBean")
@SessionScoped
public class Transaction_tax_mapBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setTransaction_tax_mapFromResultset(Transaction_tax_map aTransaction_tax_map, ResultSet aResultSet) {
        try {
            try {
                aTransaction_tax_map.setTransaction_tax_map_id(aResultSet.getLong("transaction_tax_map_id"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setTransaction_tax_map_id(0);
            }
            try {
                aTransaction_tax_map.setTransaction_id(aResultSet.getLong("transaction_id"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setTransaction_id(0);
            }
            try {
                aTransaction_tax_map.setTransaction_type_id(aResultSet.getInt("transaction_type_id"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setTransaction_type_id(0);
            }
            try {
                aTransaction_tax_map.setTransaction_reason_id(aResultSet.getInt("transaction_reason_id"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setTransaction_reason_id(0);
            }
            try {
                aTransaction_tax_map.setTransaction_number(aResultSet.getString("transaction_number"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setTransaction_number("");
            }
            try {
                aTransaction_tax_map.setTransaction_number_tax(aResultSet.getString("transaction_number_tax"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setTransaction_number_tax("");
            }
            try {
                aTransaction_tax_map.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setAdd_date(null);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public int saveTransaction_tax_map(Transaction_tax_map aTransaction_tax_map) {
        int saved = 0;
        String sql = "{call sp_save_transaction_tax_map(?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aTransaction_tax_map) {
                try {
                    cs.setLong("in_transaction_tax_map_id", aTransaction_tax_map.getTransaction_tax_map_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_transaction_tax_map_id", 0);
                }
                try {
                    cs.setLong("in_transaction_id", aTransaction_tax_map.getTransaction_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_transaction_id", 0);
                }
                try {
                    cs.setLong("in_transaction_type_id", aTransaction_tax_map.getTransaction_type_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_transaction_type_id", 0);
                }
                try {
                    cs.setLong("in_transaction_reason_id", aTransaction_tax_map.getTransaction_type_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_transaction_reason_id", 0);
                }
                try {
                    cs.setString("in_transaction_number", aTransaction_tax_map.getTransaction_number());
                } catch (NullPointerException npe) {
                    cs.setString("in_transaction_number", "");
                }
                try {
                    cs.setString("in_transaction_number_tax", aTransaction_tax_map.getTransaction_number_tax());
                } catch (NullPointerException npe) {
                    cs.setString("in_transaction_number_tax", "");
                }
                cs.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            System.err.println("saveTransaction_tax_map1:" + e.getMessage());
        }
        return saved;
    }

    public void saveTransaction_tax_map(long aTransId, String aTransNoTax) {
        try {
            Trans trans = new Trans();
            Transaction_tax_map transtaxmap = new Transaction_tax_map();
            try {
                trans = new TransBean().getTrans(aTransId);
            } catch (Exception e) {
                //
            }
            if (null != trans) {
                transtaxmap.setTransaction_id(aTransId);
                transtaxmap.setTransaction_number(trans.getTransactionNumber());
                transtaxmap.setTransaction_type_id(trans.getTransactionTypeId());
                transtaxmap.setTransaction_reason_id(trans.getTransactionReasonId());
                transtaxmap.setTransaction_number_tax(aTransNoTax);
                int x = this.saveTransaction_tax_map(transtaxmap);
            }
        } catch (Exception e) {
            System.err.println("saveTransaction_tax_map2:" + e.getMessage());
        }
    }

    public Transaction_tax_map getTransaction_tax_map(long aTransId, int aTransTypeId) {
        String sql = "SELECT * FROM transaction_tax_map WHERE transaction_id=? AND transaction_type_id=?";
        ResultSet rs = null;
        Transaction_tax_map ttm = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransId);
            ps.setInt(2, aTransTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ttm = new Transaction_tax_map();
                this.setTransaction_tax_mapFromResultset(ttm, rs);
            }
        } catch (Exception e) {
            System.err.println("getTransaction_tax_map:" + e.getMessage());
        }
        return ttm;
    }

    public String getTaxInvoiceNo(long aTransId, int aTransTypeId) {
        String TaxIN = "";
        String sql = "SELECT * FROM transaction_tax_map WHERE transaction_id=? AND transaction_type_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransId);
            ps.setInt(2, aTransTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                TaxIN = rs.getString("transaction_number_tax");
            }
        } catch (Exception e) {
            System.err.println("getTransaction_tax_map:" + e.getMessage());
        }
        return TaxIN;
    }

}
