package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.Trans;
import entities.Transaction_smbi_map;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.faces.bean.ManagedBean;
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
@ManagedBean(name = "transaction_smbi_mapBean")
@SessionScoped
public class Transaction_smbi_mapBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Transaction_smbi_mapBean.class.getName());

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
                Trans t = new TransBean().getTrans(aTransaction_id);
                if (null != t && aTransaction_type_id == 2) {//SalesInvoice
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
}
