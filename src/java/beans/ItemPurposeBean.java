package beans;

import connections.DBConnection;
import entities.ItemPurpose;
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
@ManagedBean(name = "itemPurposeBean")
@SessionScoped
public class ItemPurposeBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public void setItemPurposeFromResultset(ItemPurpose aItemPurpose, ResultSet aResultSet) {
        try {
            try {
                aItemPurpose.setItemPurposeId(aResultSet.getInt("item_purpose_id"));
            } catch (NullPointerException npe) {
                aItemPurpose.setItemPurposeId(0);
            }
            try {
                aItemPurpose.setItemPurposeName(aResultSet.getString("item_purpose_name"));
            } catch (NullPointerException npe) {
                aItemPurpose.setItemPurposeName("");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public List<ItemPurpose> getItemPurposes() {
        String sql = "";
        sql = "SELECT * FROM item_purpose order by item_purpose_id ASC";
        ResultSet rs = null;
        List<ItemPurpose> recs = new ArrayList<ItemPurpose>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemPurpose rec = new ItemPurpose();
                this.setItemPurposeFromResultset(rec, rs);
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

    public ItemPurpose getItemPurposeById(int aItemPurposeId) {
        String sql = "";
        sql = "SELECT * FROM item_purpose WHERE item_purpose_id=" + aItemPurposeId;
        ResultSet rs = null;
        ItemPurpose rec = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                rec = new ItemPurpose();
                this.setItemPurposeFromResultset(rec, rs);
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
