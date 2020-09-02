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
                aTransaction_tax_map.setVerification_code_tax(aResultSet.getString("verification_code_tax"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setVerification_code_tax("");
            }
            try {
                aTransaction_tax_map.setQr_code_tax(aResultSet.getString("qr_code_tax"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setQr_code_tax("");
            }
            try {
                aTransaction_tax_map.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setAdd_date(null);
            }
            try {
                aTransaction_tax_map.setIs_updated(aResultSet.getInt("is_updated"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setIs_updated(0);
            }
            try {
                aTransaction_tax_map.setUpdate_synced(aResultSet.getInt("update_synced"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setUpdate_synced(0);
            }
            try {
                aTransaction_tax_map.setUpdate_type(aResultSet.getString("update_type"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setUpdate_type("");
            }
            try {
                aTransaction_tax_map.setTransaction_number_tax_update(aResultSet.getString("transaction_number_tax_update"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setTransaction_number_tax_update("");
            }
            try {
                aTransaction_tax_map.setVerification_code_tax_update(aResultSet.getString("verification_code_tax_update"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setVerification_code_tax_update("");
            }
            try {
                aTransaction_tax_map.setQr_code_tax_update(aResultSet.getString("qr_code_tax_update"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setQr_code_tax_update("");
            }
            try {
                aTransaction_tax_map.setIs_updated_more_than_once(aResultSet.getInt("is_updated_more_than_once"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setIs_updated_more_than_once(0);
            }
            try {
                aTransaction_tax_map.setMore_than_once_update_reconsiled(aResultSet.getInt("more_than_once_update_reconsiled"));
            } catch (NullPointerException npe) {
                aTransaction_tax_map.setMore_than_once_update_reconsiled(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public int saveTransaction_tax_map(Transaction_tax_map aTransaction_tax_map) {
        int saved = 0;
        String sql = "{call sp_save_transaction_tax_map(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
                    cs.setInt("in_transaction_type_id", aTransaction_tax_map.getTransaction_type_id());
                } catch (NullPointerException npe) {
                    cs.setInt("in_transaction_type_id", 0);
                }
                try {
                    cs.setInt("in_transaction_reason_id", aTransaction_tax_map.getTransaction_type_id());
                } catch (NullPointerException npe) {
                    cs.setInt("in_transaction_reason_id", 0);
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
                try {
                    cs.setString("in_verification_code_tax", aTransaction_tax_map.getVerification_code_tax());
                } catch (NullPointerException npe) {
                    cs.setString("in_verification_code_tax", "");
                }
                String Qcode = aTransaction_tax_map.getQr_code_tax();
                try {
                    if (aTransaction_tax_map.getQr_code_tax().length() > 1000) {
                        Qcode = aTransaction_tax_map.getQr_code_tax().substring(0, 999);
                    }
                    cs.setString("in_qr_code_tax", Qcode);
                } catch (NullPointerException npe) {
                    cs.setString("in_qr_code_tax", "");
                }
                try {
                    cs.setInt("in_is_updated", aTransaction_tax_map.getIs_updated());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_updated", 0);
                }
                try {
                    cs.setInt("in_update_synced", aTransaction_tax_map.getUpdate_synced());
                } catch (NullPointerException npe) {
                    cs.setInt("in_update_synced", 0);
                }

                try {
                    cs.setString("in_update_type", aTransaction_tax_map.getUpdate_type());
                } catch (NullPointerException npe) {
                    cs.setString("in_update_type", "");
                }
                try {
                    cs.setString("in_transaction_number_tax_update", aTransaction_tax_map.getTransaction_number_tax_update());
                } catch (NullPointerException npe) {
                    cs.setString("in_transaction_number_tax_update", "");
                }
                try {
                    cs.setString("in_verification_code_tax_update", aTransaction_tax_map.getVerification_code_tax_update());
                } catch (NullPointerException npe) {
                    cs.setString("in_verification_code_tax_update", "");
                }
                String Qcode_update = aTransaction_tax_map.getQr_code_tax_update();
                try {
                    if (aTransaction_tax_map.getQr_code_tax_update().length() > 1000) {
                        Qcode_update = aTransaction_tax_map.getQr_code_tax_update().substring(0, 999);
                    }
                    cs.setString("in_qr_code_tax_update", Qcode_update);
                } catch (NullPointerException npe) {
                    cs.setString("in_qr_code_tax_update", "");
                }
                try {
                    cs.setInt("in_is_updated_more_than_once", aTransaction_tax_map.getIs_updated_more_than_once());
                } catch (NullPointerException npe) {
                    cs.setInt("in_is_updated_more_than_once", 0);
                }
                try {
                    cs.setInt("in_more_than_once_update_reconsiled", aTransaction_tax_map.getMore_than_once_update_reconsiled());
                } catch (NullPointerException npe) {
                    cs.setInt("in_more_than_once_update_reconsiled", 0);
                }
                cs.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            System.err.println("saveTransaction_tax_map1:" + e.getMessage());
        }
        return saved;
    }

    public void saveTransaction_tax_map(long aTransId, String aTransNoTax, String aVerCodeTax, String aQrCodeTax) {
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
                transtaxmap.setVerification_code_tax(aVerCodeTax);
                transtaxmap.setQr_code_tax(aQrCodeTax);
                transtaxmap.setIs_updated(0);
                transtaxmap.setUpdate_synced(0);
                transtaxmap.setUpdate_type("");
                transtaxmap.setTransaction_number_tax_update("");
                transtaxmap.setVerification_code_tax_update("");
                transtaxmap.setQr_code_tax_update("");
                transtaxmap.setIs_updated_more_than_once(0);
                transtaxmap.setMore_than_once_update_reconsiled(0);
                int x = this.saveTransaction_tax_map(transtaxmap);
            }
        } catch (Exception e) {
            System.err.println("saveTransaction_tax_map2:" + e.getMessage());
        }
    }

    public void markTransaction_tax_mapUpdated(Transaction_tax_map aTransaction_tax_map) {
        try {
            if (null != aTransaction_tax_map) {
                aTransaction_tax_map.setIs_updated(1);
                aTransaction_tax_map.setUpdate_synced(0);
                int x = this.saveTransaction_tax_map(aTransaction_tax_map);
            }
        } catch (Exception e) {
            System.err.println("markTransaction_tax_mapUpdated:" + e.getMessage());
        }
    }

    public void markTransaction_tax_mapUpdateSynced(Transaction_tax_map aTransaction_tax_map) {
        try {
            if (null != aTransaction_tax_map) {
                aTransaction_tax_map.setUpdate_synced(1);
                int x = this.saveTransaction_tax_map(aTransaction_tax_map);
            }
        } catch (Exception e) {
            System.err.println("markTransaction_tax_mapUpdated:" + e.getMessage());
        }
    }

    public void markTransaction_tax_mapUpdated_more_than_once(Transaction_tax_map aTransaction_tax_map) {
        try {
            if (null != aTransaction_tax_map) {
                aTransaction_tax_map.setIs_updated_more_than_once(1);
                aTransaction_tax_map.setMore_than_once_update_reconsiled(0);
                int x = this.saveTransaction_tax_map(aTransaction_tax_map);
            }
        } catch (Exception e) {
            System.err.println("markTransaction_tax_mapUpdated_more_than_once:" + e.getMessage());
        }
    }

    public void markTransaction_tax_mapMore_than_once_update_reconsiled(Transaction_tax_map aTransaction_tax_map) {
        try {
            if (null != aTransaction_tax_map) {
                aTransaction_tax_map.setMore_than_once_update_reconsiled(1);
                int x = this.saveTransaction_tax_map(aTransaction_tax_map);
            }
        } catch (Exception e) {
            System.err.println("markTransaction_tax_mapMore_than_once_update_reconsiled:" + e.getMessage());
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
