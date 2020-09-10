package beans;

import connections.DBConnection;
import entities.Iso_country_code;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@ManagedBean(name = "iso_country_codeBean")
@SessionScoped
public class Iso_country_codeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Iso_country_code> Iso_country_codeList;

    public void setIso_country_codeFromResultset(Iso_country_code aIso_country_code, ResultSet aResultSet) {
        try {
            try {
                aIso_country_code.setIso_country_code_id(aResultSet.getInt("iso_country_code_id"));
            } catch (NullPointerException npe) {
                aIso_country_code.setIso_country_code_id(0);
            }
            try {
                aIso_country_code.setCountry_short_name(aResultSet.getString("country_short_name"));
            } catch (NullPointerException npe) {
                aIso_country_code.setCountry_short_name("");
            }
            try {
                aIso_country_code.setCode_2(aResultSet.getString("code_2"));
            } catch (NullPointerException npe) {
                aIso_country_code.setCode_2("");
            }
            try {
                aIso_country_code.setCode_3(aResultSet.getString("code_3"));
            } catch (NullPointerException npe) {
                aIso_country_code.setCode_3("");
            }
            try {
                aIso_country_code.setNumeric_code(aResultSet.getString("numeric_code"));
            } catch (NullPointerException npe) {
                aIso_country_code.setNumeric_code("");
            }
        } catch (Exception e) {
            System.err.println("setIso_country_codeFromResultset:" + e.getMessage());
        }
    }

    public Iso_country_code getIso_country_code(int aIso_country_code_id) {
        String sql = "SELECT * FROM iso_country_code WHERE iso_country_code_id=?";
        ResultSet rs = null;
        Iso_country_code icc = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aIso_country_code_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                icc = new Iso_country_code();
                this.setIso_country_codeFromResultset(icc, rs);
            }
        } catch (Exception e) {
            System.err.println("getIso_country_code:" + e.getMessage());
        }
        return icc;
    }

    public Iso_country_code getIso_country_codeByCode(String aCode_3) {
        String sql = "SELECT * FROM iso_country_code WHERE code_3=?";
        ResultSet rs = null;
        Iso_country_code idt = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCode_3);
            rs = ps.executeQuery();
            if (rs.next()) {
                idt = new Iso_country_code();
                this.setIso_country_codeFromResultset(idt, rs);
            }
        } catch (Exception e) {
            System.err.println("getIso_country_codeByCode:" + e.getMessage());
        }
        return idt;
    }

    public List<Iso_country_code> getIso_country_codes() {
        ResultSet rs = null;
        List<Iso_country_code> idts = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM iso_country_code WHERE 1=1";
        sql = sql + " ORDER BY country_short_name ASC";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Iso_country_code iso_country_code = new Iso_country_code();
                this.setIso_country_codeFromResultset(iso_country_code, rs);
                idts.add(iso_country_code);
            }
        } catch (Exception e) {
            System.err.println("getIso_country_codes:" + e.getMessage());
        }
        return idts;
    }

    public void refreshIso_country_codesList() {
        ResultSet rs = null;
        if (null == this.Iso_country_codeList) {
            this.Iso_country_codeList = new ArrayList<>();
        } else {
            this.Iso_country_codeList.clear();
        }
        String sql;
        sql = "SELECT * FROM iso_country_code WHERE 1=1";
        sql = sql + " ORDER BY country_short_name ASC";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Iso_country_code iso_country_code = new Iso_country_code();
                this.setIso_country_codeFromResultset(iso_country_code, rs);
                this.Iso_country_codeList.add(iso_country_code);
            }
        } catch (Exception e) {
            System.err.println("refreshIso_country_codesList:" + e.getMessage());
        }
    }

    /**
     * @return the Iso_country_codeList
     */
    public List<Iso_country_code> getIso_country_codeList() {
        return Iso_country_codeList;
    }

    /**
     * @param Iso_country_codeList the Iso_country_codeList to set
     */
    public void setIso_country_codeList(List<Iso_country_code> Iso_country_codeList) {
        this.Iso_country_codeList = Iso_country_codeList;
    }

}
