package beans;

import connections.DBConnection;
import entities.Id_type;
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
@ManagedBean(name = "id_typeBean")
@SessionScoped
public class Id_typeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Id_type> Id_typeList;

    public void setId_typeFromResultset(Id_type aId_type, ResultSet aResultSet) {
        try {
            try {
                aId_type.setId_type_id(aResultSet.getInt("id_type_id"));
            } catch (NullPointerException npe) {
                aId_type.setId_type_id(0);
            }
            try {
                aId_type.setId_type_code(aResultSet.getString("id_type_code"));
            } catch (NullPointerException npe) {
                aId_type.setId_type_code("");
            }
            try {
                aId_type.setId_type_name(aResultSet.getString("id_type_name"));
            } catch (NullPointerException npe) {
                aId_type.setId_type_name("");
            }
            try {
                aId_type.setApplies_to_individual(aResultSet.getInt("applies_to_individual"));
            } catch (NullPointerException npe) {
                aId_type.setApplies_to_individual(0);
            }
            try {
                aId_type.setApplies_to_company(aResultSet.getInt("applies_to_company"));
            } catch (NullPointerException npe) {
                aId_type.setApplies_to_company(0);
            }
            try {
                aId_type.setIs_active(aResultSet.getInt("is_active"));
            } catch (NullPointerException npe) {
                aId_type.setIs_active(0);
            }
        } catch (Exception e) {
            System.err.println("setId_typeFromResultset:" + e.getMessage());
        }
    }

    public Id_type getId_type(int aId_type_id) {
        String sql = "SELECT * FROM id_type WHERE id_type_id=?";
        ResultSet rs = null;
        Id_type idt = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aId_type_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                idt = new Id_type();
                this.setId_typeFromResultset(idt, rs);
            }
        } catch (Exception e) {
            System.err.println("getId_type:" + e.getMessage());
        }
        return idt;
    }

    public Id_type getId_typeByCode(String aId_type_code) {
        String sql = "SELECT * FROM id_type WHERE id_type_code=?";
        ResultSet rs = null;
        Id_type idt = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aId_type_code);
            rs = ps.executeQuery();
            if (rs.next()) {
                idt = new Id_type();
                this.setId_typeFromResultset(idt, rs);
            }
        } catch (Exception e) {
            System.err.println("getId_typeByCode:" + e.getMessage());
        }
        return idt;
    }

    public List<Id_type> getId_types(int aActive, int aIndividual, int aCompany) {
        ResultSet rs = null;
        List<Id_type> idts = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM id_type WHERE 1=1";
        if (aActive == 0 || aActive == 1) {
            sql = sql + " AND is_active=" + aActive;
        }
        if (aIndividual == 0 || aIndividual == 1) {
            sql = sql + " AND applies_to_individual=" + aIndividual;
        }
        if (aCompany == 0 || aCompany == 1) {
            sql = sql + " AND applies_to_company=" + aCompany;
        }
        sql = sql + " ORDER BY id_type_name ASC";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Id_type id_type = new Id_type();
                this.setId_typeFromResultset(id_type, rs);
                idts.add(id_type);
            }
        } catch (Exception e) {
            System.err.println("getId_types:" + e.getMessage());
        }
        return idts;
    }

    public void refreshId_typesList(int aActive, int aIndividual, int aCompany) {
        ResultSet rs = null;
        if (null == this.Id_typeList) {
            this.Id_typeList = new ArrayList<>();
        } else {
            this.Id_typeList.clear();
        }
        String sql;
        sql = "SELECT * FROM id_type WHERE 1=1";
        if (aActive == 0 || aActive == 1) {
            sql = sql + " AND is_active=" + aActive;
        }
        if (aIndividual == 0 || aIndividual == 1) {
            sql = sql + " AND applies_to_individual=" + aIndividual;
        }
        if (aCompany == 0 || aCompany == 1) {
            sql = sql + " AND applies_to_company=" + aCompany;
        }
        sql = sql + " ORDER BY id_type_name ASC";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Id_type id_type = new Id_type();
                this.setId_typeFromResultset(id_type, rs);
                this.Id_typeList.add(id_type);
            }
        } catch (Exception e) {
            System.err.println("refreshId_typesList:" + e.getMessage());
        }
    }

    /**
     * @return the Id_typeList
     */
    public List<Id_type> getId_typeList() {
        return Id_typeList;
    }

    /**
     * @param Id_typeList the Id_typeList to set
     */
    public void setId_typeList(List<Id_type> Id_typeList) {
        this.Id_typeList = Id_typeList;
    }

}
