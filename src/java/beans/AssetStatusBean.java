package beans;

import connections.DBConnection;
import entities.AssetStatus;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
@ManagedBean(name = "assetStatusBean")
@SessionScoped
public class AssetStatusBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public void setAssetStatusFromResultset(AssetStatus aAssetStatus, ResultSet aResultSet) {
        try {
            try {
                aAssetStatus.setAssetStatusId(aResultSet.getInt("asset_status_id"));
            } catch (NullPointerException npe) {
                aAssetStatus.setAssetStatusId(0);
            }
            try {
                aAssetStatus.setAssetStatusName(aResultSet.getString("asset_status_name"));
            } catch (NullPointerException npe) {
                aAssetStatus.setAssetStatusName("");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public List<AssetStatus> getAssetStatuss() {
        String sql = "";
        sql = "SELECT * FROM asset_status order by asset_status_name ASC";
        ResultSet rs = null;
        List<AssetStatus> recs = new ArrayList<AssetStatus>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AssetStatus rec = new AssetStatus();
                this.setAssetStatusFromResultset(rec, rs);
                recs.add(rec);
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
        return recs;
    }

    public AssetStatus getAssetStatusById(int aAssetStatusId) {
        String sql = "";
        sql = "SELECT * FROM asset_status WHERE asset_status_id=" + aAssetStatusId;
        ResultSet rs = null;
        AssetStatus rec = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                rec = new AssetStatus();
                this.setAssetStatusFromResultset(rec, rs);
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
        return rec;
    }

}
