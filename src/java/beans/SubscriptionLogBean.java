package beans;

import static beans.SubscriptionBean.LOGGER;
import connections.DBConnection;
import entities.Subscription;
import entities.Subscription_log;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "subscriptionLogBean")
@SessionScoped
public class SubscriptionLogBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(SubscriptionLogBean.class.getName());

    public void setSubscription_logFromResultset(Subscription_log aSubscription_log, ResultSet aResultSet) {
        try {
            try {
                aSubscription_log.setSubscription_log_id(aResultSet.getLong("subscription_log_id"));
            } catch (Exception e) {
                aSubscription_log.setSubscription_log_id(0);
            }
            try {
                aSubscription_log.setSubscription_log_id(aResultSet.getInt("subscription_id"));
            } catch (Exception e) {
                aSubscription_log.setSubscription_log_id(0);
            }
            try {
                aSubscription_log.setTransactor_id(aResultSet.getLong("transactor_id"));
            } catch (Exception e) {
                aSubscription_log.setTransactor_id(0);
            }
            try {
                aSubscription_log.setSubscription_category_id(aResultSet.getInt("subscription_category_id"));
            } catch (Exception e) {
                aSubscription_log.setSubscription_category_id(0);
            }
            try {
                aSubscription_log.setBusiness_category_id(aResultSet.getInt("business_category_id"));
            } catch (Exception e) {
                aSubscription_log.setBusiness_category_id(0);
            }
            try {
                aSubscription_log.setItem_id(aResultSet.getLong("item_id"));
            } catch (Exception e) {
                aSubscription_log.setItem_id(0);
            }
            try {
                aSubscription_log.setDescription(aResultSet.getString("description"));
            } catch (Exception e) {
                aSubscription_log.setDescription("");
            }
            try {
                aSubscription_log.setAmount(aResultSet.getDouble("amount"));
            } catch (Exception e) {
                aSubscription_log.setAmount(0);
            }
            try {
                aSubscription_log.setIs_recurring(aResultSet.getString("is_recurring"));
            } catch (Exception e) {
                aSubscription_log.setIs_recurring("");
            }
            try {
                aSubscription_log.setCurrent_status(aResultSet.getString("current_status"));
            } catch (Exception e) {
                aSubscription_log.setCurrent_status("");
            }
            try {
                aSubscription_log.setFrequency(aResultSet.getString("frequency"));
            } catch (Exception e) {
                aSubscription_log.setFrequency("");
            }
            try {
                aSubscription_log.setUnit_price(aResultSet.getDouble("unit_price"));
            } catch (Exception e) {
                aSubscription_log.setUnit_price(0);
            }
            try {
                aSubscription_log.setQty(aResultSet.getDouble("qty"));
            } catch (Exception e) {
                aSubscription_log.setQty(1);
            }
            try {
                aSubscription_log.setAgent(aResultSet.getString("agent"));
            } catch (Exception e) {
                aSubscription_log.setAgent("");
            }
            try {
                aSubscription_log.setAccount_manager(aResultSet.getString("account_manager"));
            } catch (Exception e) {
                aSubscription_log.setAccount_manager("");
            }
            try {
                aSubscription_log.setSubscription_date(new Date(aResultSet.getTimestamp("subscription_date").getTime()));
            } catch (Exception e) {
                aSubscription_log.setSubscription_date(null);
            }
            try {
                aSubscription_log.setRenewal_date(new Date(aResultSet.getTimestamp("renewal_date").getTime()));
            } catch (Exception e) {
                aSubscription_log.setRenewal_date(null);
            }
            try {
                aSubscription_log.setExpiry_date(new Date(aResultSet.getTimestamp("expiry_date").getTime()));
            } catch (Exception e) {
                aSubscription_log.setExpiry_date(null);
            }
            try {
                aSubscription_log.setFree_at_reg(aResultSet.getInt("free_at_reg"));
            } catch (Exception e) {
                aSubscription_log.setFree_at_reg(0);
            }
            try {
                aSubscription_log.setCommission_amount(aResultSet.getDouble("commission_amount"));
            } catch (Exception e) {
                aSubscription_log.setCommission_amount(0);
            }
            try {
                aSubscription_log.setAction(aResultSet.getString("action"));
            } catch (Exception e) {
                aSubscription_log.setAction("");
            }
            try {
                aSubscription_log.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aSubscription_log.setAdd_date(null);
            }
            try {
                aSubscription_log.setAdded_by(aResultSet.getString("added_by"));
            } catch (Exception e) {
                aSubscription_log.setAdded_by("");
            }
            try {
                aSubscription_log.setConverted_by(aResultSet.getString("converted_by"));
            } catch (Exception e) {
                aSubscription_log.setConverted_by("");
            }
            try {
                aSubscription_log.setReferred_by(aResultSet.getString("referred_by"));
            } catch (Exception e) {
                aSubscription_log.setReferred_by("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setSubscription_logFromResultset_old(Subscription_log aSubscription_log, ResultSet aResultSet) {
        try {
            try {
                aSubscription_log.setSubscription_log_id(aResultSet.getLong("subscription_log_id"));
            } catch (Exception e) {
                aSubscription_log.setSubscription_log_id(0);
            }
            try {
                //aSubscription_log.setAdd_date(aResultSet.getDate("add_date"));
                aSubscription_log.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aSubscription_log.setAdd_date(null);
            }
            try {
                aSubscription_log.setAdded_by(aResultSet.getString("added_by"));
            } catch (Exception e) {
                aSubscription_log.setAdded_by("");
            }
            try {
                aSubscription_log.setSubscription_id(aResultSet.getInt("subscription_id"));
            } catch (Exception e) {
                aSubscription_log.setSubscription_id(0);
            }
            try {
                aSubscription_log.setAction(aResultSet.getString("action"));
            } catch (Exception e) {
                aSubscription_log.setAction("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertSubscription_log(Subscription_log aSubscription_log) {
        int saved = 0;
        String sql = "{call sp_insert_subscription_log(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt(1, aSubscription_log.getSubscription_id());
            cs.setLong(2, aSubscription_log.getTransactor_id());
            cs.setInt(3, aSubscription_log.getSubscription_category_id());
            cs.setInt(4, aSubscription_log.getBusiness_category_id());
            cs.setLong(5, aSubscription_log.getItem_id());
            cs.setString(6, aSubscription_log.getDescription());
            cs.setDouble(7, aSubscription_log.getAmount());
            cs.setString(8, aSubscription_log.getIs_recurring());
            cs.setString(9, aSubscription_log.getCurrent_status());
            cs.setString(10, aSubscription_log.getFrequency());
            cs.setDouble(11, aSubscription_log.getUnit_price());
            cs.setDouble(12, aSubscription_log.getQty());
            cs.setString(13, aSubscription_log.getAgent());
            cs.setString(14, aSubscription_log.getAccount_manager());
            cs.setTimestamp(15, new java.sql.Timestamp(aSubscription_log.getSubscription_date().getTime()));
            if (aSubscription_log.getRenewal_date() != null) {
                cs.setTimestamp(16, new java.sql.Timestamp(aSubscription_log.getRenewal_date().getTime()));
            } else {
                cs.setTimestamp(16, null);
            }
            if ("No".equals(aSubscription_log.getIs_recurring())) {
                cs.setTimestamp(17, null);
            } else {
                cs.setTimestamp(17, new java.sql.Timestamp(aSubscription_log.getExpiry_date().getTime()));
            }
            cs.setInt(18, aSubscription_log.getFree_at_reg());
            cs.setDouble(19, aSubscription_log.getCommission_amount());
            //cs.setTimestamp(16, new java.sql.Timestamp(aSubscription_log.getRenewal_date().getTime()));
            //cs.setTimestamp(17, new java.sql.Timestamp(aSubscription_log.getExpiry_date().getTime()));
            cs.setString(20, aSubscription_log.getAction());
            cs.setTimestamp(21, new java.sql.Timestamp(new java.util.Date().getTime()));
            cs.setString(22, new GeneralUserSetting().getCurrentUser().getUserName());
            cs.setString(23, aSubscription_log.getConverted_by());
            cs.setString(24, aSubscription_log.getReferred_by());

            cs.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            saved = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertSubscription_log_old(Subscription_log aSubscription_log) {
        int saved = 0;
        String sql = "INSERT INTO subscription_log"
                + "(add_date,added_by,subscription_id,action)"
                + "VALUES"
                + "(?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //add_date,added_by,subscription_id,action
            ps.setTimestamp(1, new java.sql.Timestamp(new java.util.Date().getTime()));
            //ps.setString(2, aSubscription_log.getAdded_by());
            ps.setString(2, new GeneralUserSetting().getCurrentUser().getUserName());
            ps.setInt(3, aSubscription_log.getSubscription_id());
            ps.setString(4, aSubscription_log.getAction());
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public List<Subscription_log> getSubscription_logList() {
        String sql;
        sql = "{call sp_search_subscription_log_by_none()}";
        ResultSet rs;
        List<Subscription_log> subscription_logs = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription_log subscription_log = new Subscription_log();
                this.setSubscription_logFromResultset(subscription_log, rs);
                subscription_logs.add(subscription_log);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return subscription_logs;
    }

    public List<Subscription_log> getSubscription_log() {
        String sql = "SELECT * FROM subscription_log";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            List<Subscription_log> subscriptionLog = new ArrayList<>();
            while (rs.next()) {
                Subscription_log obj = new Subscription_log();
                this.setSubscription_logFromResultset_old(obj, rs);
                subscriptionLog.add(obj);
            }
            conn.close();
            return subscriptionLog;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Subscription_log> getSubscriptionlogBySubscriptionId(int aSubscription_id) {
        String sql = "{call sp_search_subscription_log_by_subscription_id(?)}";
        ResultSet rs;
        List<Subscription_log> subscription_logs = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aSubscription_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription_log subscription_log = new Subscription_log();
                this.setSubscription_logFromResultset(subscription_log, rs);
                subscription_logs.add(subscription_log);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return subscription_logs;
    }

    public List<Subscription_log> getSubscriptionlogBySubscriptionId_old(int aSubscription_id) {
        String sql = "SELECT * FROM subscription_log WHERE subscription_id = ?";
        ResultSet rs;
        List<Subscription_log> subscriptionLogList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aSubscription_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Subscription_log obj = new Subscription_log();
                this.setSubscription_logFromResultset_old(obj, rs);
                subscriptionLogList.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return subscriptionLogList;
    }

    public Subscription_log getSubscription_logById(long aSubscription_log_id) {
        String sql;
        if (aSubscription_log_id > 0) {
            sql = "{call sp_search_subscription_log_by_id(?)}";
        } else {
            return null;
        }
        ResultSet rs;
        Subscription_log subscription_log = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aSubscription_log_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                subscription_log = new Subscription_log();
                this.setSubscription_logFromResultset(subscription_log, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return subscription_log;
    }

    public Subscription_log getSubscription_logById_old(long aSubscription_log_id) {
        String sql = "SELECT * FROM subscription_log WHERE subscription_log_id = ?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aSubscription_log_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Subscription_log obj = new Subscription_log();
                this.setSubscription_logFromResultset_old(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public int deleteSubscription_log(long subscriptionLogId) {
        int deleted = 0;
        String sql = "DELETE FROM subscription_log WHERE subscription_log_id = ?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, subscriptionLogId);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
    }

    public int updateSubscription_log_old(Subscription_log aSubscription_log) {
        int updated = 0;
        String sql = "UPDATE subscription SET add_date = ?, added_by = ?, subscription_id = ?, action = ? WHERE subscription_log_id = ?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //add_date,added_by,subscription_id,action,subscription_log_id
            ps.setTimestamp(1, new java.sql.Timestamp(new java.util.Date().getTime()));
            ps.setString(2, aSubscription_log.getAdded_by());
            ps.setInt(3, aSubscription_log.getSubscription_id());
            ps.setString(4, aSubscription_log.getAction());
            ps.setLong(5, aSubscription_log.getSubscription_log_id());
            ps.executeUpdate();
            updated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return updated;
    }

    public int updateSubscription_log(Subscription_log aSubscription_log) {
        int status = 0;
        String sql = "{call sp_update_subscription_log(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong(1, aSubscription_log.getSubscription_log_id());
            cs.setInt(2, aSubscription_log.getSubscription_id());
            cs.setLong(3, aSubscription_log.getTransactor_id());
            cs.setInt(4, aSubscription_log.getSubscription_category_id());
            cs.setInt(5, aSubscription_log.getBusiness_category_id());
            cs.setLong(6, aSubscription_log.getItem_id());
            cs.setString(7, aSubscription_log.getDescription());
            cs.setDouble(8, aSubscription_log.getAmount());
            cs.setString(9, aSubscription_log.getIs_recurring());
            cs.setString(10, aSubscription_log.getCurrent_status());
            cs.setString(11, aSubscription_log.getFrequency());
            cs.setDouble(12, aSubscription_log.getUnit_price());
            cs.setDouble(13, aSubscription_log.getQty());
            cs.setString(14, aSubscription_log.getAgent());
            cs.setString(15, aSubscription_log.getAccount_manager());
            cs.setTimestamp(16, new java.sql.Timestamp(aSubscription_log.getSubscription_date().getTime()));
            cs.setTimestamp(17, new java.sql.Timestamp(aSubscription_log.getRenewal_date().getTime()));
            cs.setTimestamp(18, new java.sql.Timestamp(aSubscription_log.getExpiry_date().getTime()));
            cs.setInt(19, aSubscription_log.getFree_at_reg());
            cs.setDouble(20, aSubscription_log.getCommission_amount());
            cs.setString(21, aSubscription_log.getAction());
            cs.setTimestamp(22, new java.sql.Timestamp(new java.util.Date().getTime()));
            cs.setString(23, new GeneralUserSetting().getCurrentUser().getUserName());
            cs.setString(24, aSubscription_log.getConverted_by());
            cs.setString(25, aSubscription_log.getReferred_by());

            cs.executeUpdate();
            status = 1;
        } catch (Exception e) {
            status = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return status;
    }
}
