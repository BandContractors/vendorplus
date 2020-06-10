package beans;

import connections.DBConnection;
import entities.AccDepSchedule;
import entities.AccPeriod;
import entities.CompanySetting;
import entities.Stock;
import entities.Trans;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "accDepScheduleBean")
@SessionScoped
public class AccDepScheduleBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<AccDepSchedule> AccDepScheduleList;
    private String ItemDescription;

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
            try {
                aAccDepSchedule.setPost_status(aResultSet.getInt("post_status"));
            } catch (NullPointerException npe) {
                aAccDepSchedule.setPost_status(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public int insertAccDepSchedule(AccDepSchedule aAccDepSchedule) {
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

    public int updateAccDepSchedule(AccDepSchedule aAccDepSchedule) {
        int status = 0;
        String sql = "{call sp_update_acc_dep_schedule(?,?,?,?,?,?,?,?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_acc_dep_schedule_id", aAccDepSchedule.getAccDepScheduleId());
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
            cs.setInt("in_post_status", aAccDepSchedule.getPost_status());
            cs.executeUpdate();
            status = 1;
        } catch (SQLException se) {
            status = 0;
            System.err.println(se.getMessage());
        }
        return status;
    }

    public void insertAccDepSchedules(List<AccDepSchedule> aAccDepSchedules) {
        List<AccDepSchedule> ati = aAccDepSchedules;
        int ListItemIndex = 0;
        int ListItemNo = ati.size();
        while (ListItemIndex < ListItemNo) {
            this.insertAccDepSchedule(ati.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void postAccDepSchedules(AccPeriod aAccPeriod, long aPostJobId) {
        String sql = "select stock_id,min(year_number) as year_number from acc_dep_schedule where post_status=0 group by stock_id";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            int curyearno = 0;
            long curstockid = 0;
            int prvyearno = 0;
            //int curaccprd = 0;
            AccPeriod prvaccprd = null;
            AccDepSchedule prevDepSched = null;
            AccDepSchedule curDepSched = null;
            //set trans
            Trans trans = new Trans();
            trans.setTransactionId(0);
            trans.setTransactionTypeId(26);//ACCOUNT PERIOD
            trans.setTransactionReasonId(51);//ACCOUNT PERIOD OPEN
            trans.setTransactionDate(new CompanySetting().getCURRENT_SERVER_DATE());
            //below are all un posted depreciation shcedules
            while (rs.next()) {
                curyearno = 0;
                prvyearno = 0;
                prevDepSched = null;
                //---
                //1. get prev acc period
                curyearno = rs.getInt("year_number");
                prvyearno = curyearno - 1;
                if (prvyearno > 0) {
                    prevDepSched = this.getAccDepScheduleByYear(rs.getLong("stock_id"), prvyearno);
                    prvaccprd = new AccPeriodBean().getAccPeriodById(prevDepSched.getDepForAccPeriodId());
                }
                //2. confirm if this is next acc period and commit
                if (null != prvaccprd) {
                    if (aAccPeriod.getOrderNo() == (prvaccprd.getOrderNo() + 1)) {
                        //depreciate for this year
                        curstockid = rs.getInt("stock_id");
                        curDepSched = this.getAccDepScheduleByYear(curstockid, curyearno);
                        new AccJournalBean().postJournalDepreciateAsset(trans, new StockBean().getStock(curstockid), curDepSched, aAccPeriod.getAccPeriodId(), aPostJobId);
                        curDepSched.setDepForAccPeriodId(aAccPeriod.getAccPeriodId());
                        curDepSched.setDepFromDate(aAccPeriod.getStartDate());
                        curDepSched.setDepToDate(aAccPeriod.getEndDate());
                        curDepSched.setPost_status(1);
                        new AccDepScheduleBean().updateAccDepSchedule(curDepSched);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("postAccDepSchedules:" + e.getMessage());
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

    public void deleteAccDepScheduleUnposted(long aStockId) {
        String sql = "{call sp_delete_acc_dep_schedule_unposted(?)}";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setLong("in_stock_id", aStockId);
            cs.executeUpdate();
        } catch (Exception e) {
            System.err.println("deleteAccDepScheduleUnposted:" + e.getMessage());
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

    public AccDepSchedule getAccDepScheduleByYearPosted(Long aStockId, int aYearNumber) {
        String sql = "{call sp_search_acc_dep_schedule_by_year_posted(?,?)}";
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
        } catch (Exception e) {
            System.err.println("getAccDepScheduleByYearPosted:" + e.getMessage());
            return null;
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
        setAccDepScheduleList(new ArrayList<>());
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
                    getAccDepScheduleList().add(depsch);
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
                    getAccDepScheduleList().add(depsch);
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
                    getAccDepScheduleList().add(depsch);
                    udy = udy - 1;
                }
            } else {//NONE

            }
        }
        return getAccDepScheduleList();
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

    public void refreshAccDepSchedules(Stock aStock) {
        String sql = "SELECT * FROM acc_dep_schedule WHERE stock_id=" + aStock.getStockId() + " ORDER BY year_number ASC";
        this.setAccDepScheduleList(new ArrayList<>());
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccDepSchedule ds = null;
            try {
                this.ItemDescription = new ItemBean().getItem(aStock.getItemId()).getDescription() + " " + aStock.getDescSpecific() + " " + aStock.getCodeSpecific();
            } catch (Exception e) {
                this.ItemDescription = "";
            }
            while (rs.next()) {
                ds = new AccDepSchedule();
                this.setAccDepScheduleFromResultset(ds, rs);
                try {
                    ds.setAcc_period_name(new AccPeriodBean().getAccPeriodById(rs.getInt("dep_for_acc_period_id")).getAccPeriodName());
                } catch (Exception e) {
                    ds.setAcc_period_name("");
                }
                this.getAccDepScheduleList().add(ds);
            }
        } catch (Exception e) {
            System.err.println("refreshAccDepSchedules:" + e.getMessage());
        }
    }

    public void cancelAccDepSchedule(AccDepSchedule aAccDepSchedule) {
        String msg = "";
        String sql = "";
        int x = 0;
        AccPeriod ap = null;
        Trans trans = null;
        Stock stock = null;
        try {
            ap = new AccPeriodBean().getAccPeriodById(aAccDepSchedule.getDepForAccPeriodId());
        } catch (Exception e) {
            ap = null;
        }
        if (null == ap) {
            msg = "You cannot cancel posted depreciation for this accounting period...";
            FacesContext.getCurrentInstance().addMessage("Cancel", new FacesMessage(msg));
        } else if (ap.getIsClosed() == 1) {
            msg = "You cannot cancel posted depreciation for a closed accounting period...";
            FacesContext.getCurrentInstance().addMessage("Cancel", new FacesMessage(msg));
        } else if (aAccDepSchedule.getDepAmount() <= 0) {
            msg = "You cannot cancel depreciation for 0 amount...";
            FacesContext.getCurrentInstance().addMessage("Cancel", new FacesMessage(msg));
        } else if (aAccDepSchedule.getDepForAccPeriodId() <= 0) {
            msg = "You cannot cancel this depreciation...";
            FacesContext.getCurrentInstance().addMessage("Cancel", new FacesMessage(msg));
        } else {
            trans = new Trans();
            trans.setTransactionId(0);
            trans.setTransactionTypeId(16);//JOURNAL ENTRY
            trans.setTransactionReasonId(35);//JOURNAL ENTRY
            trans.setTransactionDate(new CompanySetting().getCURRENT_SERVER_DATE());
            stock = new StockBean().getStock(aAccDepSchedule.getStockId());
            new AccJournalBean().postJournalDepreciateAssetREVERSE(trans, stock, aAccDepSchedule, aAccDepSchedule.getDepForAccPeriodId(), 0);
            x = 1;
            aAccDepSchedule.setPost_status(0);
            new AccDepScheduleBean().updateAccDepSchedule(aAccDepSchedule);
        }
        if (x == 1) {
            this.refreshAccDepSchedules(stock);
            msg = "Depreciation record cancelled successfully...";
            FacesContext.getCurrentInstance().addMessage("Cancel", new FacesMessage(msg));
        }
    }

    public void postAccDepSchedule(AccDepSchedule aAccDepSchedule) {
        String msg = "";
        String sql = "";
        int x = 0;
        AccPeriod ap = null;
        Trans trans = null;
        Stock stock = null;
        try {
            ap = new AccPeriodBean().getAccPeriodById(aAccDepSchedule.getDepForAccPeriodId());
        } catch (Exception e) {
            ap = null;
        }
        if (null == ap) {
            msg = "You cannot post depreciation to this accounting period...";
            FacesContext.getCurrentInstance().addMessage("Post", new FacesMessage(msg));
        } else if (ap.getIsClosed() == 1) {
            msg = "You cannot post depreciation to a closed accounting period...";
            FacesContext.getCurrentInstance().addMessage("Post", new FacesMessage(msg));
        } else if (null != ap && ap.getIsOpen() == 0) {
            msg = "You cannot post depreciation to an accounting period not open...";
            FacesContext.getCurrentInstance().addMessage("Post", new FacesMessage(msg));
        } else if (aAccDepSchedule.getDepAmount() <= 0) {
            msg = "You cannot post depreciation for 0 amount...";
            FacesContext.getCurrentInstance().addMessage("Post", new FacesMessage(msg));
        } else if (aAccDepSchedule.getDepForAccPeriodId() <= 0) {
            msg = "Select a valid accounting period before posting a depreciation...";
            FacesContext.getCurrentInstance().addMessage("Post", new FacesMessage(msg));
        } else {
            trans = new Trans();
            trans.setTransactionId(0);
            trans.setTransactionTypeId(16);//JOURNAL ENTRY
            trans.setTransactionReasonId(35);//JOURNAL ENTRY
            trans.setTransactionDate(new CompanySetting().getCURRENT_SERVER_DATE());
            stock = new StockBean().getStock(aAccDepSchedule.getStockId());
            new AccJournalBean().postJournalDepreciateAsset(trans, stock, aAccDepSchedule, aAccDepSchedule.getDepForAccPeriodId(), 0);
            x = 1;
            aAccDepSchedule.setDepForAccPeriodId(ap.getAccPeriodId());
            aAccDepSchedule.setDepFromDate(ap.getStartDate());
            aAccDepSchedule.setDepToDate(ap.getEndDate());
            aAccDepSchedule.setPost_status(1);
            new AccDepScheduleBean().updateAccDepSchedule(aAccDepSchedule);
        }
        if (x == 1) {
            this.refreshAccDepSchedules(stock);
            msg = "Depreciation schedule posted successfully...";
            FacesContext.getCurrentInstance().addMessage("Post", new FacesMessage(msg));
        }
    }

    /**
     * @return the ItemDescription
     */
    public String getItemDescription() {
        return ItemDescription;
    }

    /**
     * @param ItemDescription the ItemDescription to set
     */
    public void setItemDescription(String ItemDescription) {
        this.ItemDescription = ItemDescription;
    }

    /**
     * @return the AccDepScheduleList
     */
    public List<AccDepSchedule> getAccDepScheduleList() {
        return AccDepScheduleList;
    }

    /**
     * @param AccDepScheduleList the AccDepScheduleList to set
     */
    public void setAccDepScheduleList(List<AccDepSchedule> AccDepScheduleList) {
        this.AccDepScheduleList = AccDepScheduleList;
    }

}
