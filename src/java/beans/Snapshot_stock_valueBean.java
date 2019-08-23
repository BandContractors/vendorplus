package beans;

import connections.DBConnection;
import entities.AccPeriod;
import entities.Cdc_general;
import entities.Snapshot_stock_value;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
@ManagedBean
@SessionScoped
public class Snapshot_stock_valueBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Snapshot_stock_value> Snapshot_stock_valueList;
    private Snapshot_stock_value Snapshot_stock_valueObj;

    public void setSnapshot_stock_valueFromResultset(Snapshot_stock_value aSnapshot_stock_value, ResultSet aResultSet) {
        try {
            try {
                aSnapshot_stock_value.setSnapshot_stock_value_id(aResultSet.getInt("snapshot_stock_value_id"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setSnapshot_stock_value_id(0);
            }
            try {
                aSnapshot_stock_value.setSnapshot_no(aResultSet.getInt("snapshot_no"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setSnapshot_no(0);
            }
            try {
                aSnapshot_stock_value.setSnapshot_date(new Date(aResultSet.getTimestamp("snapshot_date").getTime()));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setSnapshot_date(null);
            }
            try {
                aSnapshot_stock_value.setAcc_period_id(aResultSet.getInt("acc_period_id"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setAcc_period_id(0);
            }
            try {
                aSnapshot_stock_value.setItem_id(aResultSet.getInt("item_id"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setItem_id(0);
            }
            try {
                aSnapshot_stock_value.setBatchno(aResultSet.getString("batchno"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setBatchno("");
            }
            try {
                aSnapshot_stock_value.setCode_specific(aResultSet.getString("code_specific"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setCode_specific("");
            }
            try {
                aSnapshot_stock_value.setDesc_specific(aResultSet.getString("desc_specific"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setDesc_specific("");
            }
            try {
                aSnapshot_stock_value.setCurrency_code(aResultSet.getString("currency_code"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setCurrency_code("");
            }
            try {
                aSnapshot_stock_value.setCurrentqty(aResultSet.getDouble("currentqty"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setCurrentqty(0);
            }
            try {
                aSnapshot_stock_value.setUnit_cost_price(aResultSet.getDouble("unit_cost_price"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setUnit_cost_price(0);
            }
            try {
                aSnapshot_stock_value.setCp_value(aResultSet.getDouble("cp_value"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setCp_value(0);
            }
            try {
                aSnapshot_stock_value.setWp_value(aResultSet.getDouble("wp_value"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setWp_value(0);
            }
            try {
                aSnapshot_stock_value.setRp_value(aResultSet.getDouble("rp_value"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setRp_value(0);
            }
            try {
                aSnapshot_stock_value.setSpecific_size(aResultSet.getDouble("specific_size"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setSpecific_size(0);
            }
            try {
                aSnapshot_stock_value.setQty_damage(aResultSet.getDouble("qty_damage"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setQty_damage(0);
            }
            try {
                aSnapshot_stock_value.setCdc_id(aResultSet.getString("cdc_id"));
            } catch (NullPointerException npe) {
                aSnapshot_stock_value.setCdc_id("");
            }
        } catch (SQLException se) {
            System.err.println("setSnapshot_stock_valueFromResultset:" + se.getMessage());
        }
    }

    public Snapshot_stock_value getSnapshot_stock_value(Long aSnapshot_stock_value_id) {
        String sql = "SLECT * FROM snapshot_stock_value WHERE snapshot_stock_value_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aSnapshot_stock_value_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Snapshot_stock_value ssv = new Snapshot_stock_value();
                this.setSnapshot_stock_valueFromResultset(ssv, rs);
                return ssv;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println("getSnapshot_stock_value:" + se.getMessage());
            return null;
        }
    }

    public long getRecordsByCdc_id(String aCdc_id) {
        String sql = "SLECT count(*) as cdc_id FROM snapshot_stock_value WHERE cdc_id=?";
        ResultSet rs = null;
        long n = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCdc_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    n = rs.getLong("cdc_id");
                } catch (NullPointerException npe) {

                }
            }
        } catch (Exception e) {
            System.err.println("getRecordsByCdc_id:" + e.getMessage());
        }
        return n;
    }

    public Snapshot_stock_value getSnapshot_stock_value(int aStoreId, long aItemId, String aBatchNo, String aCodeSpecific, String aDescSpecific) {
        String sql = "{call sp_search_stock_bms(?,?,?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aStoreId);
            ps.setLong(2, aItemId);
            ps.setString(3, aBatchNo);
            ps.setString(4, aCodeSpecific);
            ps.setString(5, aDescSpecific);

            rs = ps.executeQuery();
            if (rs.next()) {
                Snapshot_stock_value ssv = new Snapshot_stock_value();
                this.setSnapshot_stock_valueFromResultset(ssv, rs);
                return ssv;
            } else {
                return null;
            }
        } catch (SQLException | NullPointerException se) {
            System.err.println("getSnapshot_stock_value:" + se.getMessage());
            return null;
        }
    }

    public int insertSnapshot_stock_value(Cdc_general aCdc_general) {
        int saved = 0;
        String sql = "{call sp_insert_snapshot_stock_value(?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (null != aCdc_general) {
                cs.setInt("in_acc_period_id", aCdc_general.getAcc_period_id());
                cs.setLong("in_snapshot_no", aCdc_general.getSnapshot_no());
                cs.setTimestamp("in_snapshot_date", new java.sql.Timestamp(aCdc_general.getCdc_date().getTime()));
                cs.setString("in_cdc_id", aCdc_general.getCdc_id());
                cs.executeUpdate();
                saved = 1;
            }
        } catch (Exception e) {
            System.err.println("insertSnapshot_stock_value:" + e.getMessage());
        }
        return saved;
    }

    public void clearSnapshot_stock_value(Snapshot_stock_value aSnapshot_stock_value) {
        aSnapshot_stock_value.setSnapshot_stock_value_id(0);
        aSnapshot_stock_value.setSnapshot_no(0);
        aSnapshot_stock_value.setSnapshot_date(null);
        aSnapshot_stock_value.setAcc_period_id(0);
        aSnapshot_stock_value.setItem_id(0);
        aSnapshot_stock_value.setBatchno("");
        aSnapshot_stock_value.setCode_specific("");
        aSnapshot_stock_value.setDesc_specific("");
        aSnapshot_stock_value.setCurrency_code("");
        aSnapshot_stock_value.setCurrentqty(0);
        aSnapshot_stock_value.setUnit_cost_price(0);
        aSnapshot_stock_value.setCp_value(0);
        aSnapshot_stock_value.setWp_value(0);
        aSnapshot_stock_value.setRp_value(0);
        aSnapshot_stock_value.setSpecific_size(0);
        aSnapshot_stock_value.setQty_damage(0);
        aSnapshot_stock_value.setCdc_id("");
    }

    /**
     * @return the Snapshot_stock_valueList
     */
    public List<Snapshot_stock_value> getSnapshot_stock_valueList() {
        return Snapshot_stock_valueList;
    }

    /**
     * @param Snapshot_stock_valueList the Snapshot_stock_valueList to set
     */
    public void setSnapshot_stock_valueList(List<Snapshot_stock_value> Snapshot_stock_valueList) {
        this.Snapshot_stock_valueList = Snapshot_stock_valueList;
    }

    /**
     * @return the Snapshot_stock_valueObj
     */
    public Snapshot_stock_value getSnapshot_stock_valueObj() {
        return Snapshot_stock_valueObj;
    }

    /**
     * @param Snapshot_stock_valueObj the Snapshot_stock_valueObj to set
     */
    public void setSnapshot_stock_valueObj(Snapshot_stock_value Snapshot_stock_valueObj) {
        this.Snapshot_stock_valueObj = Snapshot_stock_valueObj;
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param ActionMessage the ActionMessage to set
     */
    public void setActionMessage(String ActionMessage) {
        this.ActionMessage = ActionMessage;
    }
}
