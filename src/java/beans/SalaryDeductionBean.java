package beans;

import connections.DBConnection;
import entities.SalaryDeduction;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class SalaryDeductionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<SalaryDeduction> SalaryDeductions;
    private String ActionMessage;
    private SalaryDeduction SelectedSalaryDeduction = null;

    public void saveSalaryDeductions(Long aTransactorId, List<SalaryDeduction> aSalaryDeductions) {
        List<SalaryDeduction> sd = aSalaryDeductions;
        int ListItemIndex = 0;
        int ListItemNo = aSalaryDeductions.size();
        while (ListItemIndex < ListItemNo) {
            sd.get(ListItemIndex).setTransactorId(aTransactorId);
            this.saveSalaryDeduction(sd.get(ListItemIndex));
            ListItemIndex = ListItemIndex + 1;
        }
    }

    public void saveSalaryDeduction(SalaryDeduction aSalaryDeduction) {
        String sql = null;
        String msg = "";
        if (aSalaryDeduction.getSalaryDeductionId() == 0) {
            sql = "{call sp_insert_salary_deduction(?,?,?,?,?)}";
        } else if (aSalaryDeduction.getSalaryDeductionId() > 0) {
            sql = "{call sp_update_salary_deduction(?,?,?,?,?,?)}";
        }
        if (aSalaryDeduction.getTransactorId() > 0) {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (aSalaryDeduction.getSalaryDeductionId() > 0) {
                    cs.setInt("in_salary_deduction_id", aSalaryDeduction.getSalaryDeductionId());
                }
                cs.setLong("in_transactor_id", aSalaryDeduction.getTransactorId());
                cs.setString("in_account_code", aSalaryDeduction.getAccountCode());
                cs.setDouble("in_perc", aSalaryDeduction.getPerc());
                cs.setDouble("in_amount", aSalaryDeduction.getAmount());
                cs.setString("in_deduction_name", aSalaryDeduction.getDeductionName());
                cs.executeUpdate();
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("SalaryDeduction NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("SalaryDeduction NOT saved!"));
            }
        }
    }

    public void setSalaryDeductionFromResultset(SalaryDeduction aSalaryDeduction, ResultSet aResultSet) {
        try {
            try {
                aSalaryDeduction.setSalaryDeductionId(aResultSet.getInt("salary_deduction_id"));
            } catch (NullPointerException npe) {
                aSalaryDeduction.setSalaryDeductionId(0);
            }
            try {
                aSalaryDeduction.setTransactorId(aResultSet.getLong("transactor_id"));
            } catch (NullPointerException npe) {
                aSalaryDeduction.setTransactorId(0);
            }
            try {
                aSalaryDeduction.setAccountCode(aResultSet.getString("account_code"));
            } catch (NullPointerException npe) {
                aSalaryDeduction.setAccountCode("");
            }
            try {
                aSalaryDeduction.setPerc(aResultSet.getDouble("perc"));
            } catch (NullPointerException npe) {
                aSalaryDeduction.setPerc(0);
            }
            try {
                aSalaryDeduction.setAmount(aResultSet.getDouble("amount"));
            } catch (NullPointerException npe) {
                aSalaryDeduction.setAmount(0);
            }
            try {
                aSalaryDeduction.setDeductionName(aResultSet.getString("deduction_name"));
            } catch (NullPointerException npe) {
                aSalaryDeduction.setDeductionName("");
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
    }

    public List<SalaryDeduction> getSalaryDeductions(long aTransactorId) {
        String sql;
        sql = "SELECT * FROM salary_deduction WHERE transactor_id=" + aTransactorId;
        ResultSet rs = null;
        List<SalaryDeduction> sds = new ArrayList<SalaryDeduction>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                SalaryDeduction sd = new SalaryDeduction();
                this.setSalaryDeductionFromResultset(sd, rs);
                sds.add(sd);
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
        return sds;
    }

    public void setSalaryDeductions(long aTransactorId, List<SalaryDeduction> aSalaryDeductions) {
        String sql;
        sql = "SELECT * FROM salary_deduction WHERE transactor_id=" + aTransactorId;
        ResultSet rs = null;
        aSalaryDeductions = new ArrayList<SalaryDeduction>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                SalaryDeduction sd = new SalaryDeduction();
                this.setSalaryDeductionFromResultset(sd, rs);
                aSalaryDeductions.add(sd);
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
    }

    public SalaryDeduction getSalaryDeductionById(int aSalaryDeductionId) {
        String sql = "";
        if (aSalaryDeductionId > 0) {
            sql = "SELECT * FROM salary_deduction WHERE salary_deduction_id=" + aSalaryDeductionId;
        } else {
            return null;
        }
        ResultSet rs = null;
        SalaryDeduction sd = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                sd = new SalaryDeduction();
                this.setSalaryDeductionFromResultset(sd, rs);
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
        return sd;
    }

    /**
     * @return the SelectedSalaryDeduction
     */
    public SalaryDeduction getSelectedSalaryDeduction() {
        return SelectedSalaryDeduction;
    }

    /**
     * @param SelectedSalaryDeduction the SelectedSalaryDeduction to set
     */
    public void setSelectedSalaryDeduction(SalaryDeduction SelectedSalaryDeduction) {
        this.SelectedSalaryDeduction = SelectedSalaryDeduction;
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

    /**
     * @return the SalaryDeductions
     */
    public List<SalaryDeduction> getSalaryDeductions() {
        return SalaryDeductions;
    }

    /**
     * @param SalaryDeductions the SalaryDeductions to set
     */
    public void setSalaryDeductions(List<SalaryDeduction> SalaryDeductions) {
        this.SalaryDeductions = SalaryDeductions;
    }

}
