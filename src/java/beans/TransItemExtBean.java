package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.TransItem;
import entities.Transaction_item_hist_unit;
import entities.Transaction_item_unit;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
@ManagedBean
@SessionScoped
public class TransItemExtBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(TransItemExtBean.class.getName());

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void calVatFromAmountAtPurchase(TransItem aTransItem, String aCurrencyCode) {
        try {
            if (null != aTransItem) {
                double VatPerc = CompanySetting.getVatPerc();
                double VatAmount = 0;
                double AmountExcVat = 0;
                double AmountIncVat = aTransItem.getUnitPrice();
                if (VatPerc > 0 && AmountIncVat > 0) {
                    AmountExcVat = AmountIncVat / (1 + (VatPerc / 100));
                    AmountExcVat = new AccCurrencyBean().roundAmount(aCurrencyCode, AmountExcVat);
                } else {
                    AmountExcVat = AmountIncVat;
                }
                VatAmount = AmountIncVat - AmountExcVat;
                aTransItem.setUnitPrice(AmountExcVat);
                aTransItem.setUnitVat(VatAmount);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setTransaction_item_unitFromResultset(Transaction_item_unit aTransaction_item_unit, ResultSet aResultSet) {
        try {
            try {
                aTransaction_item_unit.setTransaction_item_id(aResultSet.getLong("transaction_item_id"));
            } catch (Exception e) {
                aTransaction_item_unit.setTransaction_item_id(0);
            }
            try {
                aTransaction_item_unit.setUnit_id(aResultSet.getInt("unit_id"));
            } catch (Exception e) {
                aTransaction_item_unit.setUnit_id(0);
            }
            try {
                aTransaction_item_unit.setBase_unit_qty(aResultSet.getDouble("base_unit_qty"));
            } catch (Exception e) {
                aTransaction_item_unit.setBase_unit_qty(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public long getTransItemId(long aTransId, long aItemId, String aBatchno, String aCodeSpecific, String aDescSpecific) {
        long TransItemId = 0;
        String sql = "SELECT * FROM transaction_item WHERE transaction_id=" + aTransId + " AND item_id=" + aItemId + " AND batchno='" + aBatchno + "' AND code_specific='" + aCodeSpecific + "' AND desc_specific='" + aDescSpecific + "'";
        ResultSet rs = null;
        TransItem ti = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                TransItemId = rs.getLong("transaction_item_id");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return TransItemId;
    }

    public long insertTransaction_item_unit(Transaction_item_unit aTransaction_item_unit) {
        long newId = 0;
        String sql = "INSERT INTO transaction_item_unit"
                + "(transaction_item_id,unit_id,base_unit_qty)"
                + " VALUES"
                + "(?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setLong(1, aTransaction_item_unit.getTransaction_item_id());
            ps.setInt(2, aTransaction_item_unit.getUnit_id());
            ps.setDouble(3, aTransaction_item_unit.getBase_unit_qty());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getLong(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return newId;
    }

    public int insertTransaction_item_hist_unit(Transaction_item_hist_unit aTransaction_item_hist_unit) {
        int inserted = 0;
        String sql = "INSERT INTO transaction_item_hist_unit"
                + "(transaction_item_hist_id,unit_id,base_unit_qty)"
                + " VALUES"
                + "(?,?,?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransaction_item_hist_unit.getTransaction_item_hist_id());
            ps.setInt(2, aTransaction_item_hist_unit.getUnit_id());
            ps.setDouble(3, aTransaction_item_hist_unit.getBase_unit_qty());
            ps.executeUpdate();
            inserted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return inserted;
    }

    public int deleteTransItemsUnitByTransId(long aTransId) {
        int deleted = 0;
        String sql = "DELETE FROM transaction_item_unit WHERE transaction_item_id IN("
                + "SELECT ti.transaction_item_id FROM transaction_item ti WHERE ti.transaction_id=?"
                + ")";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aTransId);
            ps.executeUpdate();
            deleted = 1;
        } catch (Exception e) {
            deleted = 0;
            LOGGER.log(Level.ERROR, e);
        }
        return deleted;
    }

    /**
     * @return the menuItemBean
     */
    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    /**
     * @param menuItemBean the menuItemBean to set
     */
    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }

}
