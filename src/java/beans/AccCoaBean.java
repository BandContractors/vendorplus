package beans;

import connections.DBConnection;
import entities.AccCoa;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utilities.UtilityBean;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "accCoaBean")
@SessionScoped
public class AccCoaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(AccCoaBean.class.getName());
    private String ActionMessage = null;
    private List<AccCoa> AccCoaObjectList;
    private AccCoa AccCoaObject;

    public void setAccCoaFromResultset(AccCoa acccoa, ResultSet aResultSet) {
        try {
            try {
                acccoa.setAccCoaId(aResultSet.getInt("acc_coa_id"));
            } catch (Exception e) {
                acccoa.setAccCoaId(0);
            }
            try {
                acccoa.setAccountCode(aResultSet.getString("account_code"));
            } catch (Exception e) {
                acccoa.setAccountCode("");
            }
            try {
                acccoa.setAccountName(aResultSet.getString("account_name"));
            } catch (Exception e) {
                acccoa.setAccountName("");
            }
            try {
                acccoa.setAccountDesc(aResultSet.getString("account_desc"));
            } catch (Exception e) {
                acccoa.setAccountDesc("");
            }
            try {
                acccoa.setAccClassId(aResultSet.getInt("acc_class_id"));
            } catch (Exception e) {
                acccoa.setAccClassId(0);
            }
            try {
                acccoa.setAccTypeId(aResultSet.getInt("acc_type_id"));
            } catch (Exception e) {
                acccoa.setAccTypeId(0);
            }
            try {
                acccoa.setAccGroupId(aResultSet.getInt("acc_group_id"));
            } catch (Exception e) {
                acccoa.setAccGroupId(0);
            }
            try {
                acccoa.setAccCategoryId(aResultSet.getInt("acc_category_id"));
            } catch (Exception e) {
                acccoa.setAccCategoryId(0);
            }
            try {
                acccoa.setOrderCoa(aResultSet.getInt("order_coa"));
            } catch (Exception e) {
                acccoa.setOrderCoa(0);
            }
            try {
                acccoa.setIsActive(aResultSet.getInt("is_active"));
            } catch (Exception e) {
                acccoa.setIsActive(0);
            }
            try {
                acccoa.setIsDeleted(aResultSet.getInt("is_deleted"));
            } catch (Exception e) {
                acccoa.setIsDeleted(0);
            }
            try {
                acccoa.setIsChild(aResultSet.getInt("is_child"));
            } catch (Exception e) {
                acccoa.setIsChild(0);
            }
            try {
                acccoa.setIsTransactorMandatory(aResultSet.getInt("is_transactor_mandatory"));
            } catch (Exception e) {
                acccoa.setIsTransactorMandatory(0);
            }
            try {
                acccoa.setIsSystemAccount(aResultSet.getInt("is_system_account"));
            } catch (Exception e) {
                acccoa.setIsSystemAccount(0);
            }
            try {
                acccoa.setAddBy(aResultSet.getInt("add_by"));
            } catch (Exception e) {
                acccoa.setAddBy(0);
            }
            try {
                acccoa.setLastEditBy(aResultSet.getInt("last_edit_by"));
            } catch (Exception e) {
                acccoa.setLastEditBy(0);
            }
            try {
                acccoa.setAddDate(new Date(aResultSet.getTimestamp("add_date").getTime()));
            } catch (Exception e) {
                acccoa.setAddDate(null);
            }
            try {
                acccoa.setLastEditDate(new Date(aResultSet.getTimestamp("last_edit_date").getTime()));
            } catch (Exception e) {
                acccoa.setLastEditDate(null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<AccCoa> getAccCoaObjectListAllActive(String Query) {
        String sql;
        sql = "{call sp_search_coa_all_active(?)}";
        ResultSet rs = null;
        this.AccCoaObjectList = new ArrayList<AccCoa>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa accCoa = new AccCoa();
                this.setAccCoaFromResultset(accCoa, rs);
                this.AccCoaObjectList.add(accCoa);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return AccCoaObjectList;
    }

    public List<AccCoa> getAccCoaObjectListAll(String Query) {
        String sql;
        sql = "{call sp_search_coa_all(?)}";
        ResultSet rs = null;
        this.AccCoaObjectList = new ArrayList<AccCoa>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa accCoa = new AccCoa();
                this.setAccCoaFromResultset(accCoa, rs);
                this.AccCoaObjectList.add(accCoa);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return AccCoaObjectList;
    }

    public AccCoa getAccCoaByCodeOrId(String aAccountCode, int aAccCoaId) {
        String sql = "";
        if (aAccountCode.length() > 0) {
            sql = "SELECT * FROM acc_coa WHERE account_code='" + aAccountCode + "' ";
        } else if (aAccCoaId > 0) {
            sql = "SELECT * FROM acc_coa WHERE acc_coa_id=" + aAccCoaId;
        } else {
            return null;
        }
        ResultSet rs = null;
        AccCoa coa = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                coa = new AccCoa();
                this.setAccCoaFromResultset(coa, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return coa;
    }

    public void setAccCoaByCodeOrId(AccCoa coa, String aAccountCode, int aAccCoaId) {
        String sql = "";
        if (aAccountCode.length() > 0) {
            sql = "SELECT * FROM acc_coa WHERE account_code='" + aAccountCode + "' ";
        } else if (aAccCoaId > 0) {
            sql = "SELECT * FROM acc_coa WHERE acc_coa_id=" + aAccCoaId;
        } else {
            //coa=null;
        }
        ResultSet rs = null;
        //AccCoa coa = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                //coa = new AccCoa();
                this.setAccCoaFromResultset(coa, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<AccCoa> getAccCoaObjectListForExpense(String Query) {
        String sql;
        sql = "{call sp_search_coa_for_expense(?)}";
        ResultSet rs = null;
        this.AccCoaObjectList = new ArrayList<AccCoa>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa accCoa = new AccCoa();
                this.setAccCoaFromResultset(accCoa, rs);
                this.AccCoaObjectList.add(accCoa);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return AccCoaObjectList;
    }

    public List<AccCoa> getAccCoaObjectListBeginWith1or2(String aExpenseType, String aBeginWith1, String aBeginWith2) {
        if (aExpenseType.equals("RAW MATERIAL")) {
            return this.getAccCoaObjectListBeginWith1(aBeginWith1);
        } else if (aExpenseType.equals("CONSUMPTION")) {
            return this.getAccCoaObjectListBeginWith2(aBeginWith1, aBeginWith2);
        } else {
            return null;
        }
    }

    public List<AccCoa> getAccCoaObjectListBeginWith1(String aBeginWith1) {
        String sql;
        sql = "{call sp_search_coa_begin_with1(?)}";
        ResultSet rs = null;
        this.AccCoaObjectList = new ArrayList<AccCoa>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aBeginWith1);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa accCoa = new AccCoa();
                this.setAccCoaFromResultset(accCoa, rs);
                this.AccCoaObjectList.add(accCoa);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return AccCoaObjectList;
    }

    public List<AccCoa> getAccCoaObjectListByInventoryType(String aItemPurpose, String aInventoryType) {
        String sql;
        String aWhere = "";
        String aOrder = " ORDER BY account_name ASC";
        sql = "SELECT * FROM acc_coa WHERE is_active=1 AND is_deleted=0";
        ResultSet rs = null;
        this.AccCoaObjectList = new ArrayList<>();
        if (aItemPurpose.equals("Stock")) {
            if (aInventoryType.equals("Merchandise")) {
                aWhere = " AND account_code='1-00-020-010'";
            } else if (aInventoryType.equals("Finished Goods")) {
                aWhere = " AND account_code='1-00-020-040'";
            } else if (aInventoryType.equals("Services")) {
                aWhere = " AND (account_code='5-10-000-010' OR account_code='5-10-000-020')";
            }
        } else if (aItemPurpose.equals("Expense")) {
            if (aInventoryType.equals("Raw Material")) {
                aWhere = " AND account_code='1-00-020-020'";
            } else if (aInventoryType.equals("Consumption")) {
                aWhere = " AND account_code='1-00-020-070'";
            } else if (aInventoryType.equals("Services")) {
                aWhere = " AND account_code LIKE '5-20%'";
            }
        }
        sql = sql + aWhere + aOrder;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa accCoa = new AccCoa();
                this.setAccCoaFromResultset(accCoa, rs);
                this.AccCoaObjectList.add(accCoa);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return AccCoaObjectList;
    }

    public List<AccCoa> getAccCoaObjectListBeginWith2(String aBeginWith1, String aBeginWith2) {
        String sql;
        sql = "{call sp_search_coa_begin_with2(?,?)}";
        ResultSet rs = null;
        this.AccCoaObjectList = new ArrayList<AccCoa>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aBeginWith1);
            ps.setString(2, aBeginWith2);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa accCoa = new AccCoa();
                this.setAccCoaFromResultset(accCoa, rs);
                this.AccCoaObjectList.add(accCoa);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return AccCoaObjectList;
    }

    public List<AccCoa> getAccsByCategory(int aAccCategoryId) {
        String sql;
        sql = "select * from acc_coa where acc_category_id=? order by order_coa asc";
        ResultSet rs = null;
        List<AccCoa> acs = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aAccCategoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa ac = new AccCoa();
                this.setAccCoaFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public List<AccCoa> getAccsBySearch(String Query) {
        String sql;
        sql = "SELECT * FROM acc_coa WHERE account_name LIKE '%" + Query + "%' OR account_code LIKE '%" + Query + "%' ORDER BY account_name ASC LIMIT 10";
        ResultSet rs = null;
        List<AccCoa> acs = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa ac = new AccCoa();
                this.setAccCoaFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public void reportChartOfAccountsDetail(AccCoa aAccCoa, AccCoaBean aAccCoaBean) {
        aAccCoaBean.setActionMessage("");
        ResultSet rs = null;
        this.AccCoaObjectList = new ArrayList<>();
        String sql = "SELECT * FROM acc_coa WHERE acc_coa_id>0";
        String wheresql = "";
        String ordersql = "";
        if (aAccCoa.getAccTypeId() > 0) {
            wheresql = wheresql + " AND acc_type_id=" + aAccCoa.getAccTypeId();
        }
        if (aAccCoa.getAccGroupId() > 0) {
            wheresql = wheresql + " AND acc_group_id=" + aAccCoa.getAccGroupId();
        }
        if (aAccCoa.getAccCategoryId() > 0) {
            wheresql = wheresql + " AND acc_category_id=" + aAccCoa.getAccCategoryId();
        }
        if (aAccCoa.getAccCoaId() > 0) {
            wheresql = wheresql + " AND acc_coa_id=" + aAccCoa.getAccCoaId();
        }
        try {
            if (aAccCoaBean.getAccCoaObject().getAccCoaId() > 0) {
                wheresql = wheresql + " AND acc_coa_id=" + aAccCoaBean.getAccCoaObject().getAccCoaId();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        ordersql = " ORDER BY account_code ASC";
        sql = sql + wheresql + ordersql;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            AccCoa acccoa = null;
            while (rs.next()) {
                acccoa = new AccCoa();
                this.setAccCoaFromResultset(acccoa, rs);
                this.AccCoaObjectList.add(acccoa);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<AccCoa> getNonChildAccounts() {
        String sql;
        sql = "select * from acc_coa where is_child=0 order by account_code asc";
        ResultSet rs = null;
        List<AccCoa> acs = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                AccCoa ac = new AccCoa();
                this.setAccCoaFromResultset(ac, rs);
                acs.add(ac);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return acs;
    }

    public void initResetCoADetail(AccCoa aAccCoa, AccCoaBean aAccCoaBean) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.resetCoADetail(aAccCoa, aAccCoaBean);
        }
    }

    public void resetCoADetail(AccCoa aAccCoa, AccCoaBean aAccCoaBean) {
        aAccCoaBean.setActionMessage("");
        try {
            new AccCoaBean().clearAccCoa(aAccCoa);
        } catch (Exception e) {
        }
        try {
            aAccCoaBean.setAccCoaObject(null);
            aAccCoaBean.AccCoaObjectList.clear();
        } catch (Exception e) {
        }
    }

    public void clearAccCoa(AccCoa aAccCoa) {
        if (null != aAccCoa) {
            aAccCoa.setAccCoaId(0);
            aAccCoa.setAccountCode("");
            aAccCoa.setAccountName("");
            aAccCoa.setAccountDesc("");
            aAccCoa.setAccClassId(0);
            aAccCoa.setAccTypeId(0);
            aAccCoa.setAccGroupId(0);
            aAccCoa.setAccCategoryId(0);
            aAccCoa.setOrderCoa(0);
            aAccCoa.setIsActive(0);
            aAccCoa.setIsDeleted(0);
            aAccCoa.setIsChild(0);
            aAccCoa.setIsTransactorMandatory(0);
            aAccCoa.setIsSystemAccount(0);
            aAccCoa.setAddBy(0);
            aAccCoa.setLastEditBy(0);
            aAccCoa.setAddDate(null);
            aAccCoa.setLastEditDate(null);
        }
    }

    /**
     * @return the AccCoaObjectList
     */
    public List<AccCoa> getAccCoaObjectList() {
        return AccCoaObjectList;
    }

    /**
     * @param AccCoaObjectList the AccCoaObjectList to set
     */
    public void setAccCoaObjectList(List<AccCoa> AccCoaObjectList) {
        this.AccCoaObjectList = AccCoaObjectList;
    }

    /**
     * @return the AccCoaObject
     */
    public AccCoa getAccCoaObject() {
        return AccCoaObject;
    }

    /**
     * @param AccCoaObject the AccCoaObject to set
     */
    public void setAccCoaObject(AccCoa AccCoaObject) {
        this.AccCoaObject = AccCoaObject;
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
