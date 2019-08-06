package beans;

import connections.DBConnection;
import entities.CompanySetting;
import entities.Trans_number_control;
import entities.TransactionType;
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
@ManagedBean(name = "trans_number_controlBean")
@SessionScoped
public class Trans_number_controlBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setTrans_number_controlFromResultset(Trans_number_control aTrans_number_control, ResultSet aResultSet) {
        try {
            try {
                aTrans_number_control.setTrans_number_control_id(aResultSet.getInt("trans_number_control_id"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setTrans_number_control_id(0);
            }
            try {
                aTrans_number_control.setTrans_type_id(aResultSet.getInt("trans_type_id"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setTrans_type_id(0);
            }
            try {
                aTrans_number_control.setYear_num(aResultSet.getInt("year_num"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setYear_num(0);
            }
            try {
                aTrans_number_control.setMonth_num(aResultSet.getInt("month_num"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setMonth_num(0);
            }
            try {
                aTrans_number_control.setDay_num(aResultSet.getInt("day_num"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setDay_num(0);
            }
            try {
                aTrans_number_control.setDay_count(aResultSet.getInt("day_count"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setDay_count(0);
            }
        } catch (SQLException se) {
            System.err.println("setTrans_number_controlFromResultset:" + se.getMessage());
        }
    }

    public void updateTrans_number_control(TransactionType aTransType) {
        java.util.Calendar calendar = new GregorianCalendar();
        Date aDate = new CompanySetting().getCURRENT_SERVER_DATE();
        calendar.setTime(aDate);
        int d = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        int m = calendar.get(java.util.Calendar.MONTH) + 1;
        int y = calendar.get(java.util.Calendar.YEAR);
        String sql = "UPDATE trans_number_control SET day_count=day_count+1 WHERE trans_number_control_id>0 AND trans_type_id=? AND year_num=? AND month_num=? AND day_num=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aTransType.getTransactionTypeId());
            ps.setInt(2, y);
            ps.setInt(3, m);
            ps.setInt(4, d);
            ps.executeUpdate();
        } catch (SQLException se) {
            System.err.println("updateTrans_number_control:" + se.getMessage());
        }
    }

    public Trans_number_control getTrans_number_controlUnique(int aTransTypeId, int aYear, int aMonth, int aDay) {
        String sql;
        sql = "SELECT * FROM trans_number_control WHERE trans_type_id=? AND year_num=? AND month_num=? AND day_num=?";
        ResultSet rs = null;
        Trans_number_control obj = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aTransTypeId);
            ps.setInt(2, aYear);
            ps.setInt(3, aMonth);
            ps.setInt(4, aDay);
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Trans_number_control();
                this.setTrans_number_controlFromResultset(obj, rs);
            }
        } catch (SQLException se) {
            System.err.println("getTrans_number_controlUnique:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getTrans_number_controlUnique:" + ex.getMessage());
                }
            }
        }
        return obj;
    }

    public String getNewTransNumber(TransactionType aTransType) {
        String transno = "";
        int CurDayNo = 0;
        int NewDayNo = 0;

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
        //first init
        this.initDayTransNumber(aTransType.getTransactionTypeId(), y, m, d);

        //get current trans day number
        try {
            CurDayNo = this.getTrans_number_controlUnique(aTransType.getTransactionTypeId(), y, m, d).getDay_count();
        } catch (NullPointerException npe) {
        }
        NewDayNo = CurDayNo + 1;
        String X = String.format("%04d", NewDayNo);

        String C = aTransType.getTransaction_type_code();
        if (null == C) {
            C = "";
        }
        String TransNumberFormat = aTransType.getTrans_number_format();
        for (int i = 0; i < TransNumberFormat.length(); i++) {
            if (TransNumberFormat.charAt(i) == 'C') {
                transno = transno + C;
            } else if (TransNumberFormat.charAt(i) == 'Y') {
                transno = transno + Y;
            } else if (TransNumberFormat.charAt(i) == 'M') {
                transno = transno + M;
            } else if (TransNumberFormat.charAt(i) == 'D') {
                transno = transno + D;
            } else if (TransNumberFormat.charAt(i) == 'X') {
                transno = transno + X;
            }
        }
        return transno;
    }

    public int countTrans_number_control(int aTransTypeId, int aYear, int aMonth, int aDay) {
        String sql;
        sql = "SELECT count(*) as total_records FROM trans_number_control WHERE trans_type_id=? AND year_num=? AND month_num=? AND day_num=?";
        ResultSet rs = null;
        int TotalRecords = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aTransTypeId);
            ps.setInt(2, aYear);
            ps.setInt(3, aMonth);
            ps.setInt(4, aDay);
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    TotalRecords = rs.getInt("total_records");
                } catch (NullPointerException npe) {
                    TotalRecords = 0;
                }
            }
        } catch (SQLException se) {
            System.err.println("countTrans_number_control:" + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("countTrans_number_control:" + ex.getMessage());
                }
            }
        }
        return TotalRecords;
    }

    public void initDayTransNumber(int aTransTypeId, int aYear, int aMonth, int aDay) {
        String sql = "";
        if (this.countTrans_number_control(aTransTypeId, aYear, aMonth, aDay) > 0) {
            //do nothing
        } else {
            sql = "INSERT INTO trans_number_control(trans_type_id,year_num,month_num,day_num,day_count) VALUES(?,?,?,?,?)";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                //parameters for insert
                ps.setInt(1, aTransTypeId);
                ps.setInt(2, aYear);
                ps.setInt(3, aMonth);
                ps.setInt(4, aDay);
                ps.setInt(5, 0);
                ps.executeUpdate();
            } catch (SQLException se) {
                System.err.println("initDayTransNumber:" + se.getMessage());
            }
        }
    }
}
