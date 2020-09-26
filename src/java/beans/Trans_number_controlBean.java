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
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.GeneralUserSetting;

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
                aTrans_number_control.setTrans_number_control_id(aResultSet.getLong("trans_number_control_id"));
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
                aTrans_number_control.setDay_count(aResultSet.getLong("day_count"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setDay_count(0);
            }
            try {
                aTrans_number_control.setMonth_count(aResultSet.getLong("month_count"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setMonth_count(0);
            }
            try {
                aTrans_number_control.setYear_count(aResultSet.getLong("year_count"));
            } catch (NullPointerException npe) {
                aTrans_number_control.setYear_count(0);
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
        String sql = "";
        //X/W/Z
        if (aTransType.getTrans_number_format().contains("X")) {//counter on day
            sql = "UPDATE trans_number_control SET day_count=day_count+1 WHERE trans_number_control_id>0 AND trans_type_id=" + aTransType.getTransactionTypeId() + " AND year_num=" + y + " AND month_num=" + m + " AND day_num=" + d;
        } else if (aTransType.getTrans_number_format().contains("W")) {//counter on month
            sql = "UPDATE trans_number_control SET month_count=month_count+1 WHERE trans_number_control_id>0 AND trans_type_id=" + aTransType.getTransactionTypeId() + " AND year_num=" + y + " AND month_num=" + m;
        } else if (aTransType.getTrans_number_format().contains("Z")) {//counter on year
            sql = "UPDATE trans_number_control SET year_count=year_count+1 WHERE trans_number_control_id>0 AND trans_type_id=" + aTransType.getTransactionTypeId() + " AND year_num=" + y;
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setInt(1, aTransType.getTransactionTypeId());
            //ps.setInt(2, y);
            //ps.setInt(3, m);
            //ps.setInt(4, d);
            ps.executeUpdate();
        } catch (SQLException se) {
            System.err.println("updateTrans_number_control:" + se.getMessage());
        }
    }

    public Trans_number_control getTrans_number_controlUnique(int aTransTypeId, int aYear, int aMonth, int aDay) {
        String sql = "";
        ResultSet rs = null;
        Trans_number_control obj = null;
        TransactionType TransType = new TransactionTypeBean().getTransactionType(aTransTypeId);
        if (null == TransType) {
            //do nothing
        } else {
            //X/W/Z
            if (TransType.getTrans_number_format().contains("X")) {//counter on day
                sql = "SELECT * FROM trans_number_control WHERE trans_type_id=" + TransType.getTransactionTypeId() + " AND year_num=" + aYear + " AND month_num=" + aMonth + " AND day_num=" + aDay;
            } else if (TransType.getTrans_number_format().contains("W")) {//counter on month
                sql = "SELECT * FROM trans_number_control WHERE trans_type_id=" + TransType.getTransactionTypeId() + " AND year_num=" + aYear + " AND month_num=" + aMonth;
            } else if (TransType.getTrans_number_format().contains("Z")) {//counter on year
                sql = "SELECT * FROM trans_number_control WHERE trans_type_id=" + TransType.getTransactionTypeId() + " AND year_num=" + aYear;
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                //ps.setInt(1, aTransTypeId);
                // ps.setInt(2, aYear);
                //ps.setInt(3, aMonth);
                //ps.setInt(4, aDay);
                rs = ps.executeQuery();
                if (rs.next()) {
                    obj = new Trans_number_control();
                    this.setTrans_number_controlFromResultset(obj, rs);
                }
            } catch (Exception e) {
                System.err.println("getTrans_number_controlUnique:" + e.getMessage());
            }
        }
        return obj;
    }

    public String getNewTransNumber(TransactionType aTransType) {
        String transno = "";
        long CurNo = 0;
        long NewNo = 0;

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
        this.initDayTransNumber(aTransType, y, m, d);

        //get current trans day number
        try {
            //X/W/Z
            if (aTransType.getTrans_number_format().contains("X")) {//counter on day
                CurNo = this.getTrans_number_controlUnique(aTransType.getTransactionTypeId(), y, m, d).getDay_count();
            } else if (aTransType.getTrans_number_format().contains("W")) {//counter on month
                CurNo = this.getTrans_number_controlUnique(aTransType.getTransactionTypeId(), y, m, d).getMonth_count();
            } else if (aTransType.getTrans_number_format().contains("Z")) {//counter on year
                CurNo = this.getTrans_number_controlUnique(aTransType.getTransactionTypeId(), y, m, d).getYear_count();
            }
        } catch (NullPointerException npe) {
        }
        NewNo = CurNo + 1;
        String X = "";
        if (NewNo <= 99) {
            X = String.format("%02d", NewNo);
        } else if (NewNo <= 999) {
            X = String.format("%03d", NewNo);
        } else if (NewNo <= 9999) {
            X = String.format("%04d", NewNo);
        } else if (NewNo <= 99999) {
            X = String.format("%05d", NewNo);
        } else if (NewNo <= 999999) {
            X = String.format("%06d", NewNo);
        } else {
            X = String.format("%07d", NewNo);
        }

        String C = aTransType.getTransaction_type_code();
        if (null == C) {
            C = "";
        }
        String S = new GeneralUserSetting().getCurrentStore().getStore_code();
        if (null == S || S.length() == 0) {
            S = "";
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
            } else if (TransNumberFormat.charAt(i) == 'X' || TransNumberFormat.charAt(i) == 'W' || TransNumberFormat.charAt(i) == 'Z') {
                transno = transno + X;
            } else if (TransNumberFormat.charAt(i) == 'S') {
                transno = transno + S;
            }
        }
        return transno;
    }

    public long countTrans_number_control(TransactionType aTransType, int aYear, int aMonth, int aDay) {
        String sql;
        sql = "SELECT count(*) as total_records FROM trans_number_control WHERE trans_type_id=? AND year_num=? AND month_num=? AND day_num=?";
        //X/W/Z
        if (aTransType.getTrans_number_format().contains("X")) {//counter on day
            sql = "SELECT count(*) as total_records FROM trans_number_control WHERE trans_type_id=" + aTransType.getTransactionTypeId() + " AND year_num=" + aYear + " AND month_num=" + aMonth + " AND day_num=" + aDay;
        } else if (aTransType.getTrans_number_format().contains("W")) {//counter on month
            sql = "SELECT count(*) as total_records FROM trans_number_control WHERE trans_type_id=" + aTransType.getTransactionTypeId() + " AND year_num=" + aYear + " AND month_num=" + aMonth;
        } else if (aTransType.getTrans_number_format().contains("Z")) {//counter on year
            sql = "SELECT count(*) as total_records FROM trans_number_control WHERE trans_type_id=" + aTransType.getTransactionTypeId() + " AND year_num=" + aYear;
        }
        ResultSet rs = null;
        int TotalRecords = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setInt(1, aTransTypeId);
            //ps.setInt(2, aYear);
            //ps.setInt(3, aMonth);
            //ps.setInt(4, aDay);
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    TotalRecords = rs.getInt("total_records");
                } catch (NullPointerException npe) {
                    TotalRecords = 0;
                }
            }
        } catch (Exception e) {
            System.err.println("countTrans_number_control:" + e.getMessage());
        }
        return TotalRecords;
    }

    public void initDayTransNumber(TransactionType aTransType, int aYear, int aMonth, int aDay) {
        String sql = "";
        if (this.countTrans_number_control(aTransType, aYear, aMonth, aDay) > 0) {
            //do nothing
        } else {
            sql = "INSERT INTO trans_number_control(trans_type_id,year_num,month_num,day_num,day_count,month_count,year_count) VALUES(" + aTransType.getTransactionTypeId() + "," + aYear + "," + aMonth + "," + aDay + ",0,0,0)";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                //parameters for insert
                //ps.setInt(1, aTransTypeId);
                //ps.setInt(2, aYear);
                //ps.setInt(3, aMonth);
                //ps.setInt(4, aDay);
                //ps.setInt(5, 0);
                ps.executeUpdate();
            } catch (Exception e) {
                System.err.println("initDayTransNumber:" + e.getMessage());
            }
        }
    }

    public int getIsTrans_number_used(int aTransTypeId, String aTransNumber) {
        int TransNoUsed = 0;
        String sql = "";
        ResultSet rs = null;
        TransactionType TransType = new TransactionTypeBean().getTransactionType(aTransTypeId);
        if (null == TransType) {
            //do nothing
        } else {
            if (aTransTypeId == 70) {//production
                sql = "SELECT * FROM trans_production WHERE transaction_number='" + aTransNumber + "'";
            } else {
                sql = "SELECT * FROM transaction WHERE transaction_number='" + aTransNumber + "'";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                if (rs.next()) {
                    TransNoUsed = 1;
                } else {
                    TransNoUsed = 0;
                }
            } catch (Exception e) {
                System.err.println("getIsTrans_number_used:" + e.getMessage());
            }
        }
        return TransNoUsed;
    }
}
