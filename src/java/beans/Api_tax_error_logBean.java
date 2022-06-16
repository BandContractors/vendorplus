package beans;

import connections.DBConnection;
import entities.Api_tax_error_log;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@ManagedBean(name = "api_tax_error_logBean")
@SessionScoped
public class Api_tax_error_logBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Api_tax_error_logBean.class.getName());
    private List<Api_tax_error_log> Api_tax_error_logList;

    public void setApi_tax_error_logFromResultset(Api_tax_error_log aApi_tax_error_log, ResultSet aResultSet) {
        try {
            try {
                aApi_tax_error_log.setApi_tax_error_log_id(aResultSet.getLong("api_tax_error_log_id"));
            } catch (NullPointerException npe) {
                aApi_tax_error_log.setApi_tax_error_log_id(0);
            }
            try {
                aApi_tax_error_log.setId_table(aResultSet.getLong("id_table"));
            } catch (NullPointerException npe) {
                aApi_tax_error_log.setId_table(0);
            }
            try {
                aApi_tax_error_log.setName_table(aResultSet.getString("name_table"));
            } catch (NullPointerException npe) {
                aApi_tax_error_log.setName_table("");
            }
            try {
                aApi_tax_error_log.setTransaction_type_id(aResultSet.getInt("transaction_type_id"));
            } catch (NullPointerException npe) {
                aApi_tax_error_log.setTransaction_type_id(0);
            }
            try {
                aApi_tax_error_log.setTransaction_reason_id(aResultSet.getInt("transaction_reason_id"));
            } catch (NullPointerException npe) {
                aApi_tax_error_log.setTransaction_reason_id(0);
            }
            try {
                aApi_tax_error_log.setError_desc(aResultSet.getString("error_desc"));
            } catch (NullPointerException npe) {
                aApi_tax_error_log.setError_desc("");
            }
            try {
                aApi_tax_error_log.setError_date(new Date(aResultSet.getTimestamp("error_date").getTime()));
            } catch (NullPointerException npe) {
                aApi_tax_error_log.setError_date(null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertApi_tax_error_log(Api_tax_error_log aApi_tax_error_log) {
        int inserted = 0;
        String sql = "INSERT api_tax_error_log(id_table,name_table,transaction_type_id,transaction_reason_id,error_desc,error_date) "
                + "VALUES(?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aApi_tax_error_log.getId_table());
            ps.setString(2, aApi_tax_error_log.getName_table());
            ps.setInt(3, aApi_tax_error_log.getTransaction_type_id());
            ps.setInt(4, aApi_tax_error_log.getTransaction_reason_id());
            ps.setString(5, aApi_tax_error_log.getError_desc());
            ps.setTimestamp(6, new java.sql.Timestamp(aApi_tax_error_log.getError_date().getTime()));
            ps.executeUpdate();
            inserted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return inserted;
    }

    public void refreshApi_tax_error_logList(int aTransTypeId, long aTransId) {
        try {
            try {
                this.Api_tax_error_logList.clear();
            } catch (Exception e) {
                this.Api_tax_error_logList = new ArrayList<>();
            }
            String sql;
            sql = "select * from api_tax_error_log where transaction_type_id=" + aTransTypeId + " and id_table=" + aTransId + " order by error_date desc";
            ResultSet rs = null;
            Api_tax_error_log obj = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    obj = new Api_tax_error_log();
                    this.setApi_tax_error_logFromResultset(obj, rs);
                    this.Api_tax_error_logList.add(obj);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void refreshApi_tax_error_logList(long aTaxUpdateId) {
        try {
            try {
                this.Api_tax_error_logList.clear();
            } catch (Exception e) {
                this.Api_tax_error_logList = new ArrayList<>();
            }
            String sql;
            sql = "select * from api_tax_error_log where name_table LIKE 'stock_ledger%' and id_table=" + aTaxUpdateId + " order by error_date desc";
            ResultSet rs = null;
            Api_tax_error_log obj = null;
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    obj = new Api_tax_error_log();
                    this.setApi_tax_error_logFromResultset(obj, rs);
                    this.Api_tax_error_logList.add(obj);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    /**
     * @return the Api_tax_error_logList
     */
    public List<Api_tax_error_log> getApi_tax_error_logList() {
        return Api_tax_error_logList;
    }

    /**
     * @param Api_tax_error_logList the Api_tax_error_logList to set
     */
    public void setApi_tax_error_logList(List<Api_tax_error_log> Api_tax_error_logList) {
        this.Api_tax_error_logList = Api_tax_error_logList;
    }
}
