package beans;

import connections.DBConnection;
import entities.AccDepSchedule;
import entities.Stock;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
@ManagedBean(name = "aAccDepScheduleBean")
@SessionScoped
public class AccDepScheduleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AccDepSchedule> AccDepScheduleList;

    public void setAccDepScheduleFromResultset(AccDepSchedule aAccDepSchedule, ResultSet aResultSet) {
        try {
            try {
                aAccDepSchedule.setAccDepScheduleId(aResultSet.getInt("acc_dep_schedule_id"));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setAccDepScheduleId(0);
            }
            try {
                aAccDepSchedule.setStockId(aResultSet.getLong("stock_id"));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setStockId(0);
            }
            try {
                aAccDepSchedule.setDepForAccPeriodId(aResultSet.getInt("dep_for_acc_period_id"));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setDepForAccPeriodId(0);
            }
            try {
                aAccDepSchedule.setDepFromDate(new Date(aResultSet.getDate("dep_from_date").getTime()));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setDepFromDate(null);
            }
            try {
                aAccDepSchedule.setDepToDate(new Date(aResultSet.getDate("dep_to_date").getTime()));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setDepToDate(null);
            }
            try {
                aAccDepSchedule.setYearNumber(aResultSet.getInt("year_number"));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setYearNumber(0);
            }
            try {
                aAccDepSchedule.setDepAmount(aResultSet.getDouble("dep_amount"));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setDepAmount(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public int saveAccDepSchedule(AccDepSchedule aAccDepSchedule) {
        int status = 0;
        String sql = "{call sp_insert_acc_dep_schedule(?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_stock_id", aAccDepSchedule.getStockId());
            cs.setInt("in_dep_for_acc_period_id", aAccDepSchedule.getDepForAccPeriodId());
            try {
                cs.setDate("in_dep_from_date", new java.sql.Date(aAccDepSchedule.getDepFromDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_dep_from_date", null);
            }
            try {
                cs.setDate("in_dep_to_date", new java.sql.Date(aAccDepSchedule.getDepToDate().getTime()));
            } catch (NullPointerException npe) {
                cs.setDate("in_dep_to_date", null);
            }
            try {
                cs.setInt("in_year_number", aAccDepSchedule.getYearNumber());
            } catch (NullPointerException npe) {
                cs.setInt("in_year_number", 0);
            }
            try {
                cs.setDouble("in_dep_amount", aAccDepSchedule.getDepAmount());
            } catch (NullPointerException npe) {
                cs.setDouble("in_dep_amount", 0);
            }
            cs.executeUpdate();
            status = 1;
        } catch (SQLException se) {
            status = 0;
            System.err.println(se.getMessage());
        }
        return status;
    }

    public void saveAccDepSchedules(List<AccDepSchedule> aAccDepSchedules) {
        List<AccDepSchedule> ati = aAccDepSchedules;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            this.saveAccDepSchedule(ati.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void deleteAccDepSchedule(long aStockId) {
        String sql = "{call sp_delete_acc_dep_schedule(?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_stock_id", aStockId);
            cs.executeUpdate();
        } catch (SQLException se) {
            System.err.println("deleteAccDepSchedule:" + se.getMessage());
        }
    }

    public AccDepSchedule getAccDepSchedule(int aAccDepScheduleId) {
        String sql = "{call sp_search_acc_dep_schedule_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aAccDepScheduleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                AccDepSchedule ads = new AccDepSchedule();
                this.setAccDepScheduleFromResultset(ads, rs);
                return ads;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

    }

    public AccDepSchedule getAccDepScheduleByYear(Long aStockId, int aYearNumber) {
        String sql = "{call sp_search_acc_dep_schedule_by_year(?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aStockId);
            ps.setLong(2, aYearNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                AccDepSchedule ads = new AccDepSchedule();
                this.setAccDepScheduleFromResultset(ads, rs);
                return ads;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public List<AccDepSchedule> getAccDepSchedulesByStock(Long aStockId) {
        String sql;
        sql = "{call sp_search_acc_dep_schedule_by_stock(?)}";
        ResultSet rs = null;
        List<AccDepSchedule> accDepSchedules = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aStockId);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccDepSchedule accDepSchedule = new AccDepSchedule();
                this.setAccDepScheduleFromResultset(accDepSchedule, rs);
                accDepSchedules.add(accDepSchedule);
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
        return accDepSchedules;
    }

    public List<AccDepSchedule> calcAccDepSchedules(Stock aStock) {
        AccDepSchedule depsch;
        AccDepScheduleList = new ArrayList<>();
        if (null != aStock && aStock.getEffectiveLife() > 0) {
            int depmethodid = aStock.getDepMethodId();
            if (depmethodid == 1) {//SLD
                double rate = 0;
                double nbv = 0;
                int n = 0;
                n = aStock.getEffectiveLife();
                nbv = aStock.getUnitCost() - aStock.getResidualValue();
                for (int i = 1; i <= n; i++) {
                    depsch = new AccDepSchedule();
                    depsch.setStockId(aStock.getStockId());
                    depsch.setYearNumber(i);
                    depsch.setDepAmount(nbv / n);
                    AccDepScheduleList.add(depsch);
                }
            } else if (depmethodid == 2) {//RBD
                double rate = 0;
                double nbv = 0;
                int n = 0;
                n = aStock.getEffectiveLife();
                rate = aStock.getDepRate();
                nbv = aStock.getUnitCost() - aStock.getResidualValue();
                for (int i = 1; i <= n; i++) {
                    depsch = new AccDepSchedule();
                    depsch.setStockId(aStock.getStockId());
                    depsch.setYearNumber(i);
                    if (i != n) {
                        depsch.setDepAmount(nbv * rate / 100);
                    } else {
                        depsch.setDepAmount(nbv);
                    }
                    AccDepScheduleList.add(depsch);
                    nbv = nbv - depsch.getDepAmount();
                }
            } else if (depmethodid == 3) {//SYD
                double nbv = 0;
                int n = 0;
                int syd = 0;
                int udy = 0;
                n = aStock.getEffectiveLife();
                nbv = aStock.getUnitCost() - aStock.getResidualValue();
                udy = n;
                syd = n * (n + 1) / 2;
                for (int i = 1; i <= n; i++) {
                    depsch = new AccDepSchedule();
                    depsch.setStockId(aStock.getStockId());
                    depsch.setYearNumber(i);
                    depsch.setDepAmount(nbv * udy / syd);
                    AccDepScheduleList.add(depsch);
                    udy = udy - 1;
                }
            } else {//NONE

            }
        }
        return AccDepScheduleList;
    }

    public double calculateDepRate(Stock aStock) {
        double deprate = 0;
        if (null != aStock && aStock.getEffectiveLife() > 0) {
            //1. first get the dep method
            int depmethodid = aStock.getDepMethodId();
            if (depmethodid == 1) {//SLD
                deprate = (aStock.getUnitCost() - aStock.getResidualValue()) / aStock.getEffectiveLife();
            } else if (depmethodid == 2) {//RBD

            } else if (depmethodid == 3) {//SYD

            } else {//NONE

            }
        }
        return deprate;
    }

}
