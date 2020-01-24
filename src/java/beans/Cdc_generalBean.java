package beans;

import connections.DBConnection;
import entities.Cdc_general;
import entities.CompanySetting;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import utilities.UtilityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "cdc_generalBean")
@SessionScoped
public class Cdc_generalBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = null;
    private List<Cdc_general> Cdc_generalObjectList;
    private Cdc_general Cdc_generalObj;

    public void setCdc_generalFromResultset(Cdc_general aCdc_general, ResultSet aResultSet) {
        try {
            try {
                aCdc_general.setCdc_general_id(aResultSet.getLong("cdc_general_id"));
            } catch (NullPointerException npe) {
                aCdc_general.setCdc_general_id(0);
            }
            try {
                aCdc_general.setCdc_function(aResultSet.getString("cdc_function"));
            } catch (NullPointerException npe) {
                aCdc_general.setCdc_function("");
            }
            try {
                aCdc_general.setCdc_id(aResultSet.getString("cdc_id"));
            } catch (NullPointerException npe) {
                aCdc_general.setCdc_id("");
            }
            try {
                aCdc_general.setCdc_date(new Date(aResultSet.getTimestamp("cdc_date").getTime()));
            } catch (NullPointerException npe) {
                aCdc_general.setCdc_date(null);
            }
            try {
                aCdc_general.setCdc_start_time(new Date(aResultSet.getTimestamp("cdc_start_time").getTime()));
            } catch (NullPointerException npe) {
                aCdc_general.setCdc_start_time(null);
            }
            try {
                aCdc_general.setCdc_end_time(new Date(aResultSet.getTimestamp("cdc_end_time").getTime()));
            } catch (NullPointerException npe) {
                aCdc_general.setCdc_end_time(null);
            }
            try {
                aCdc_general.setIs_passed(aResultSet.getInt("is_passed"));
            } catch (NullPointerException npe) {
                aCdc_general.setIs_passed(0);
            }
            try {
                aCdc_general.setRecords_affected(aResultSet.getDouble("records_affected"));
            } catch (NullPointerException npe) {
                aCdc_general.setRecords_affected(0);
            }
            try {
                aCdc_general.setAdd_date(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (NullPointerException npe) {
                aCdc_general.setAdd_date(null);
            }
            try {
                aCdc_general.setLast_update_date(new Date(aResultSet.getTimestamp("last_update_date").getTime()));
            } catch (NullPointerException npe) {
                aCdc_general.setLast_update_date(null);
            }
            try {
                aCdc_general.setAdd_by(aResultSet.getInt("add_by"));
            } catch (NullPointerException npe) {
                aCdc_general.setAdd_by(0);
            }
            try {
                aCdc_general.setLast_update_by(aResultSet.getInt("last_update_by"));
            } catch (NullPointerException npe) {
                aCdc_general.setLast_update_by(0);
            }
            try {
                aCdc_general.setSnapshot_no(aResultSet.getLong("snapshot_no"));
            } catch (NullPointerException npe) {
                aCdc_general.setSnapshot_no(0);
            }
            try {
                aCdc_general.setAcc_period_id(aResultSet.getInt("acc_period_id"));
            } catch (NullPointerException npe) {
                aCdc_general.setAcc_period_id(0);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public String getNewCdc_id() {
        String cdcid = "";

        java.util.Calendar calendar = new GregorianCalendar();
        Date aDate = new CompanySetting().getCURRENT_SERVER_DATE();
        calendar.setTime(aDate);
        int d = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        String D = String.format("%02d", d);
        int m = calendar.get(java.util.Calendar.MONTH) + 1;
        String M = String.format("%02d", m);
        int y = calendar.get(java.util.Calendar.YEAR);
        Format formatter = new SimpleDateFormat("YY");
        String Y = formatter.format(aDate);
        int h = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        String H = String.format("%02d", h);
        int mn = calendar.get(java.util.Calendar.MINUTE);
        String MN = String.format("%02d", mn);
        int s = calendar.get(java.util.Calendar.SECOND);
        String S = String.format("%02d", s);
        cdcid = cdcid + Y + M + D + H + MN + S;
        return cdcid;
    }

    public Date getStartTime(int aHourOfDay, int aMinute) {
        try {
            Date CurrentTime = new CompanySetting().getCURRENT_SERVER_DATE();
            Calendar cal = Calendar.getInstance();
            cal.setTime(CurrentTime);
            cal.set(Calendar.HOUR_OF_DAY, aHourOfDay);
            cal.set(Calendar.MINUTE, aMinute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date StartTime = cal.getTime();
            if (CurrentTime.compareTo(StartTime) > 0) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                StartTime = cal.getTime();
            }
            return StartTime;
        } catch (Exception e) {
            return null;
        }
    }

//    public static void main(String[] args){
//        try {
//            DBConnection.readConnectionConfigurations("configurations.ConfigFile");
//            new Parameter_listBean().refreshSavedParameterLists();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Cdc_generalBean gb=new Cdc_generalBean();
//        gb.getStartTime(11,59);
//    }
    public long getNewSnapshot_no() {
        String sql;
        sql = "SELECT max(snapshot_no) as snapshot_no FROM cdc_general";
        ResultSet rs = null;
        long NewNo = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    NewNo = rs.getLong("snapshot_no");
                } catch (NullPointerException npe) {
                    NewNo = 0;
                }
            }
            NewNo = NewNo + 1;
        } catch (Exception e) {
            System.err.println("getNewSnapshot_no:" + e.getMessage());
        }
        return NewNo;
    }

    public Cdc_general getCdc_general(long aCdc_general_id) {
        String sql = "SELECT * FROM cdc_general WHERE cdc_general_id=" + aCdc_general_id;
        ResultSet rs = null;
        Cdc_general coa = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                coa = new Cdc_general();
                this.setCdc_generalFromResultset(coa, rs);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return coa;
    }

    public Cdc_general getCdc_generalByJobId(String aCdc_id) {
        String sql = "SELECT * FROM cdc_general WHERE cdc_id='" + aCdc_id + "'";
        ResultSet rs = null;
        Cdc_general cg = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                cg = new Cdc_general();
                this.setCdc_generalFromResultset(cg, rs);
            }
        } catch (Exception e) {
            System.err.println("getCdc_generalByJobId:" + e.getMessage());
        }
        return cg;
    }

    public Cdc_general getLatestCdc_general() {
        String sql = "SELECT c1.* FROM cdc_general c1 WHERE c1.cdc_general_id=(select max(c2.cdc_general_id) from cdc_general c2)";
        ResultSet rs = null;
        Cdc_general cg = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                cg = new Cdc_general();
                this.setCdc_generalFromResultset(cg, rs);
            }
        } catch (Exception e) {
            System.err.println("getLatestCdc_general:" + e.getMessage());
        }
        return cg;
    }

    public boolean isTodaySnapshotFound() {
        boolean res = false;
        Date today = new CompanySetting().getCURRENT_SERVER_DATE();
        Cdc_general cdcg = this.getLatestCdc_general();
        if (null == cdcg) {
            res = false;
        } else {
            if (new UtilityBean().isDatesEqual(today, cdcg.getCdc_date()) == 1) {
                if (cdcg.getIs_passed() == 1) {
                    res = true;
                } else {
                    res = false;
                }
            } else {
                res = false;
            }
        }
        return res;
    }

    public void takeNewSnapshot_stock() {
        try {
            //1. insert cdc_record
            long snapshotno = this.getNewSnapshot_no();
            String cdcid = this.getNewCdc_id();
            Cdc_general cdcgenInsert = new Cdc_general();
            cdcgenInsert.setCdc_general_id(0);
            cdcgenInsert.setSnapshot_no(snapshotno);
            cdcgenInsert.setCdc_id(cdcid);
            cdcgenInsert.setCdc_date(new CompanySetting().getCURRENT_SERVER_DATE());
            cdcgenInsert.setCdc_function("STOCK");
            cdcgenInsert.setCdc_start_time(new CompanySetting().getCURRENT_SERVER_DATE());
            cdcgenInsert.setAdd_date(new CompanySetting().getCURRENT_SERVER_DATE());
            cdcgenInsert.setAdd_by(UserDetailBean.getSystemUserDetailId());
            //cdcgenInsert.setAcc_period_id(new AccPeriodBean().getAccPeriodCurrent().getAccPeriodId());
            cdcgenInsert.setAcc_period_id(new AccPeriodBean().getAccPeriod(cdcgenInsert.getCdc_date()).getAccPeriodId());
            this.saveCdc_general(cdcgenInsert);
            //2. insert stock snapshop
            Cdc_general cdcgenSaved = this.getCdc_generalByJobId(cdcid);
            if (null != cdcgenSaved) {
                int saved = new Snapshot_stock_valueBean().insertSnapshot_stock_value(cdcgenSaved);
                long recordsInserted = new Snapshot_stock_valueBean().getRecordsByCdc_id(cdcid);
                Cdc_general cdcgenUpdate = new Cdc_general();
                cdcgenUpdate.setCdc_general_id(cdcgenSaved.getCdc_general_id());
                cdcgenUpdate.setRecords_affected(recordsInserted);
                if (recordsInserted > 0) {
                    cdcgenUpdate.setIs_passed(1);
                } else {
                    cdcgenUpdate.setIs_passed(0);
                }
                cdcgenUpdate.setCdc_end_time(new CompanySetting().getCURRENT_SERVER_DATE());
                cdcgenUpdate.setLast_update_date(new CompanySetting().getCURRENT_SERVER_DATE());
                cdcgenUpdate.setLast_update_by(UserDetailBean.getSystemUserDetailId());
                this.saveCdc_general(cdcgenUpdate);
            }
        } catch (Exception e) {
            System.out.println("takeNewSnapshot_stock:" + e.getMessage());
        }
    }

    public void takeNewSnapshot_stockAtLogin() {
        try {
            String DailySnapshotTime = new Parameter_listBean().getParameter_listByContextNameMemory("SNAPSHOT", "DAILY_SNAPSHOT_TIME").getParameter_value();
            if (DailySnapshotTime.equals("1")) {//first login
                //check if it hasnt been taken
                if (new Cdc_generalBean().isTodaySnapshotFound()) {
                    //ignore
                } else {
                    this.takeNewSnapshot_stock();
                }
            }
        } catch (Exception e) {
            System.out.println("takeNewSnapshot_stockAtLogin:" + e.getMessage());
        }
    }

    public void saveCdc_general(Cdc_general aCdc_general) {
        String sql = null;
        if (aCdc_general.getCdc_general_id() == 0) {
            sql = "INSERT INTO cdc_general(cdc_function,cdc_id,cdc_date,cdc_start_time,add_date,add_by,acc_period_id,snapshot_no) VALUES (?,?,?,?,?,?,?,?)";
        } else if (aCdc_general.getCdc_general_id() > 0) {
            sql = "UPDATE cdc_general SET cdc_end_time=?,is_passed=?,records_affected=?,last_update_date=?,last_update_by=? WHERE cdc_general_id=?";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //Insert
            if (aCdc_general.getCdc_general_id() == 0) {
                ps.setString(1, aCdc_general.getCdc_function());
                ps.setString(2, aCdc_general.getCdc_id());
                try {
                    ps.setTimestamp(3, new java.sql.Timestamp(aCdc_general.getCdc_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(3, null);
                }
                try {
                    ps.setTimestamp(4, new java.sql.Timestamp(aCdc_general.getCdc_start_time().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(4, null);
                }
                try {
                    ps.setTimestamp(5, new java.sql.Timestamp(aCdc_general.getAdd_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(5, null);
                }
                ps.setInt(6, aCdc_general.getAdd_by());
                ps.setInt(7, aCdc_general.getAcc_period_id());
                ps.setLong(8, aCdc_general.getSnapshot_no());
            }
            //update
            if (aCdc_general.getCdc_general_id() > 0) {
                try {
                    ps.setTimestamp(1, new java.sql.Timestamp(aCdc_general.getCdc_end_time().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(1, null);
                }
                ps.setInt(2, aCdc_general.getIs_passed());
                ps.setDouble(3, aCdc_general.getRecords_affected());
                try {
                    ps.setTimestamp(4, new java.sql.Timestamp(aCdc_general.getLast_update_date().getTime()));
                } catch (NullPointerException npe) {
                    ps.setTimestamp(4, null);
                }
                ps.setInt(5, aCdc_general.getLast_update_by());
                ps.setLong(6, aCdc_general.getCdc_general_id());
            }
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("saveCdc_general:" + e.getMessage());
        }
    }

    public void clearCdc_general(Cdc_general aCdc_general) {
        if (null != aCdc_general) {
            aCdc_general.setCdc_general_id(0);
            aCdc_general.setCdc_function("");
            aCdc_general.setCdc_id("");
            aCdc_general.setCdc_date(null);
            aCdc_general.setCdc_start_time(null);
            aCdc_general.setCdc_end_time(null);
            aCdc_general.setIs_passed(0);
            aCdc_general.setRecords_affected(0);
            aCdc_general.setAdd_date(null);
            aCdc_general.setLast_update_date(null);
            aCdc_general.setAdd_by(0);
            aCdc_general.setLast_update_by(0);
            aCdc_general.setSnapshot_no(0);
            aCdc_general.setAcc_period_id(0);
        }
    }

    /**
     * @return the Cdc_generalObjectList
     */
    public List<Cdc_general> getCdc_generalObjectList() {
        return Cdc_generalObjectList;
    }

    /**
     * @param Cdc_generalObjectList the Cdc_generalObjectList to set
     */
    public void setCdc_generalObjectList(List<Cdc_general> Cdc_generalObjectList) {
        this.Cdc_generalObjectList = Cdc_generalObjectList;
    }

    /**
     * @return the Cdc_generalObj
     */
    public Cdc_general getCdc_generalObj() {
        return Cdc_generalObj;
    }

    /**
     * @param Cdc_generalObj the Cdc_generalObj to set
     */
    public void setCdc_generalObj(Cdc_general Cdc_generalObj) {
        this.Cdc_generalObj = Cdc_generalObj;
    }
}
