package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.DownloadStatus;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author kolynz
 */
@ManagedBean
@SessionScoped
public class DownloadStatusBean implements Serializable {

    static Logger LOGGER = Logger.getLogger(DownloadStatusBean.class.getName());

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setDownloadStatusFromResultset(DownloadStatus aDownloadStatus, ResultSet aResultSet) {

        try {
            try {
                aDownloadStatus.setDownload_status_id(aResultSet.getInt("download_status_id"));
            } catch (Exception e) {
                aDownloadStatus.setDownload_status_id(0);
            }
            try {
                aDownloadStatus.setDownload_name(aResultSet.getString("download_name"));
            } catch (Exception e) {
                aDownloadStatus.setDownload_name("");
            }
            try {
                aDownloadStatus.setDownload_status(aResultSet.getInt("download_status"));
            } catch (Exception e) {
                aDownloadStatus.setDownload_status(0);
            }
            try {
                aDownloadStatus.setDownload_status_msg(aResultSet.getString("download_status_msg"));
            } catch (Exception e) {
                aDownloadStatus.setDownload_status_msg("");
            }
            try {
                aDownloadStatus.setTotal_amount(aResultSet.getInt("total_amount"));
            } catch (Exception e) {
                aDownloadStatus.setTotal_amount(0);
            }
            try {
                aDownloadStatus.setTotal_downloaded(aResultSet.getInt("total_downloaded"));
            } catch (Exception e) {
                aDownloadStatus.setTotal_downloaded(0);
            }
            try {
                aDownloadStatus.setAdd_date(new java.sql.Timestamp(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                aDownloadStatus.setAdd_date(null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int saveDownloadStatus(DownloadStatus aDownloadStatus) {
        int saved = 0;
        try {
            if (aDownloadStatus.getDownload_status_id() == 0) {
                saved = this.insertDownloadStatus(aDownloadStatus);
            } else if (aDownloadStatus.getDownload_status_id() > 0) {
                saved = this.updateDownloadStatus(aDownloadStatus);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertDownloadStatus(DownloadStatus aDownloadStatus) {
        int inserted = 0;
        String sql = "INSERT INTO download_status (download_name,download_status,download_status_msg ,total_amount,total_downloaded, add_date) "
                + "VALUES(?,?,?,?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aDownloadStatus.getDownload_name());
            ps.setInt(2, aDownloadStatus.getDownload_status());
            ps.setString(3, aDownloadStatus.getDownload_status_msg());
            ps.setInt(4, aDownloadStatus.getTotal_amount());
            ps.setInt(5, aDownloadStatus.getTotal_downloaded());
            //ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
            ps.setTimestamp(6, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));

            ps.executeUpdate();
            
            inserted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return inserted;
    }

    public int updateDownloadStatus(DownloadStatus aDownloadStatus) {
        int IsUpdated = 0;
        //,, ,,, 
        String sql = "UPDATE download_status SET download_name=?,download_status=?,download_status_msg=?,total_amount=?,total_downloaded=?,add_date=? WHERE download_status_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, aDownloadStatus.getDownload_name());
            ps.setInt(2, aDownloadStatus.getDownload_status());
            ps.setString(3, aDownloadStatus.getDownload_status_msg());
            ps.setInt(4, aDownloadStatus.getTotal_amount());
            ps.setDouble(5, aDownloadStatus.getTotal_downloaded());
            //ps.setTimestamp(6, new java.sql.Timestamp(aDownloadStatus.getAdd_date().getTime()));
            ps.setTimestamp(6, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setInt(7, aDownloadStatus.getDownload_status_id());

            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int updateTotalDownloaded(DownloadStatus aDownloadStatus) {
        int IsUpdated = 0;
        String sql = "UPDATE download_status SET download_status=?,download_status_msg=?,total_amount=?,total_downloaded=?,add_date=? WHERE download_name=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setInt(1, aDownloadStatus.getDownload_status());
            ps.setString(2, aDownloadStatus.getDownload_status_msg());
            ps.setInt(3, aDownloadStatus.getTotal_amount());
            ps.setDouble(4, aDownloadStatus.getTotal_downloaded());
            //ps.setTimestamp(5, new java.sql.Timestamp(aDownloadStatus.getAdd_date().getTime()));
            ps.setTimestamp(5, new java.sql.Timestamp(new CompanySetting().getCURRENT_SERVER_DATE().getTime()));
            ps.setString(6, aDownloadStatus.getDownload_name());

            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteDownloadStatus(DownloadStatus aDownloadStatus) {
        int IsDeleted = 0;
        String sql = "DELETE FROM download_status WHERE download_status_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aDownloadStatus.getDownload_status_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public DownloadStatus getDownloadStatusById(int aDownloadStatusId) {
        String sql = "SELECT * FROM download_status WHERE download_status_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aDownloadStatusId);
            rs = ps.executeQuery();
            if (rs.next()) {
                DownloadStatus obj = new DownloadStatus();
                this.setDownloadStatusFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public DownloadStatus getDownloadStatusByDownloadName(String aDownloadName) {
        String sql = "SELECT * FROM download_status WHERE download_name=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aDownloadName);
            rs = ps.executeQuery();
            if (rs.next()) {
                DownloadStatus obj = new DownloadStatus();
                this.setDownloadStatusFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<DownloadStatus> getDownloadStatusAll() {
        String sql = "SELECT * FROM download_status";
        ResultSet rs;
        List<DownloadStatus> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                DownloadStatus obj = new DownloadStatus();
                this.setDownloadStatusFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

}
