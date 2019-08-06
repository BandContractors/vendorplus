package beans;

import connections.DBConnection;
import entities.ItemWarranty;
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
@ManagedBean(name = "itemWarrantyBean")
@SessionScoped
public class ItemWarrantyBean implements Serializable {
    private static final long serialVersionUID = 1L;

    public void setItemWarrantyFromResultset(ItemWarranty aItemWarranty, ResultSet aResultSet) {
        try {
            try {
                aItemWarranty.setItemWarrantyId(aResultSet.getInt("item_warranty_id"));
            } catch (NullPointerException npe) {
                aItemWarranty.setItemWarrantyId(0);
            }
            try {
                aItemWarranty.setItemWarrantyName(aResultSet.getString("item_warranty_name"));
            } catch (NullPointerException npe) {
                aItemWarranty.setItemWarrantyName("");
            }
            try {
                aItemWarranty.setItemWarrantyDesc(aResultSet.getString("item_warranty_desc"));
            } catch (NullPointerException npe) {
                aItemWarranty.setItemWarrantyDesc("");
            }
            try {
                aItemWarranty.setDurationMonths(aResultSet.getInt("duration_months"));
            } catch (NullPointerException npe) {
                aItemWarranty.setDurationMonths(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public List<ItemWarranty> getItemWarrantys() {
        String sql = "";
        sql = "SELECT * FROM item_warranty order by item_warranty_name ASC";
        ResultSet rs = null;
        List<ItemWarranty> recs = new ArrayList<ItemWarranty>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemWarranty rec = new ItemWarranty();
                this.setItemWarrantyFromResultset(rec, rs);
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

    public ItemWarranty getItemWarrantyById(int aItemWarrantyId) {
        String sql = "";
        sql = "SELECT * FROM item_warranty WHERE item_warranty_id=" + aItemWarrantyId;
        ResultSet rs = null;
        ItemWarranty rec = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                rec = new ItemWarranty();
                this.setItemWarrantyFromResultset(rec, rs);
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
