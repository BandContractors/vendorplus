package beans;

import connections.DBConnection;
import entities.Item_tax_map;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
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
@ManagedBean(name = "item_tax_mapBean")
@SessionScoped
public class Item_tax_mapBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setItem_tax_mapFromResultset(Item_tax_map aItem_tax_map, ResultSet aResultSet) {
        try {
            try {
                aItem_tax_map.setItem_tax_map_id(aResultSet.getLong("item_tax_map_id"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_tax_map_id(0);
            }
            try {
                aItem_tax_map.setItem_id(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_id(0);
            }
            try {
                aItem_tax_map.setItem_id_tax(aResultSet.getLong("item_id_tax"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_id_tax(0);
            }
            try {
                aItem_tax_map.setItem_code_tax(aResultSet.getString("item_code_tax"));
            } catch (NullPointerException npe) {
                aItem_tax_map.setItem_code_tax("");
            }
        } catch (SQLException se) {
            System.err.println("setItem_tax_mapFromResultset:" + se.getMessage());
        }
    }

    public int saveItem_tax_map(Item_tax_map aItem_tax_map) {
        int saved = 0;
        String sql = "{call sp_save_item_tax_map(?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aItem_tax_map) {
                try {
                    cs.setLong("in_item_tax_map_id", aItem_tax_map.getItem_tax_map_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_item_tax_map_id", 0);
                }
                try {
                    cs.setLong("in_item_id", aItem_tax_map.getItem_id());
                } catch (NullPointerException npe) {
                    cs.setLong("in_item_id", 0);
                }
                try {
                    cs.setLong("in_item_id_tax", aItem_tax_map.getItem_id_tax());
                } catch (NullPointerException npe) {
                    cs.setLong("in_item_id_tax", 0);
                }
                try {
                    cs.setString("in_item_code_tax", aItem_tax_map.getItem_code_tax());
                } catch (NullPointerException npe) {
                    cs.setString("in_item_code_tax", "");
                }
                cs.executeUpdate();
                saved = 1;
            }
        } catch (SQLException se) {
            System.err.println("saveItem_tax_map:" + se.getMessage());
        }
        return saved;
    }
}
