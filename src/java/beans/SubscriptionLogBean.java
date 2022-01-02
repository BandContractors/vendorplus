package beans;

import connections.DBConnection;
import entities.Subscription_log;
import java.io.Serializable;
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
                this.setSubscription_logFromResultset(obj, rs);
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
                this.setSubscription_logFromResultset(obj, rs);
                subscriptionLogList.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return subscriptionLogList;
    }

    public Subscription_log getSubscription_logById(long aSubscription_log_id) {
        String sql = "SELECT * FROM subscription_log WHERE subscription_log_id = ?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aSubscription_log_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Subscription_log obj = new Subscription_log();
                this.setSubscription_logFromResultset(obj, rs);
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

    public int updateSubscription_log(Subscription_log aSubscription_log) {
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
}
