package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.Unit;
import entities.GroupRight;
import entities.Unit_tax_list;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
@ManagedBean
@SessionScoped
public class UnitBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(UnitBean.class.getName());
    private List<Unit> Units;
    private String ActionMessage = null;
    private Unit SelectedUnit = null;
    private int SelectedUnitId;
    private String SearchUnitName = "";
    private List<Unit_tax_list> Unit_tax_lists;
    private Unit_tax_list Unit_tax_listObj = new Unit_tax_list();
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setUnitFromResultset(Unit aUnit, ResultSet aResultSet) {
        try {
            try {
                aUnit.setUnitId(aResultSet.getInt("unit_id"));
            } catch (Exception e) {
                aUnit.setUnitId(0);
            }
            try {
                aUnit.setUnitName(aResultSet.getString("unit_name"));
            } catch (Exception e) {
                aUnit.setUnitName("");
            }
            try {
                aUnit.setUnitSymbol(aResultSet.getString("unit_symbol"));
            } catch (Exception e) {
                aUnit.setUnitSymbol("");
            }
            try {
                aUnit.setUnit_symbol_tax(aResultSet.getString("unit_symbol_tax"));
            } catch (Exception e) {
                aUnit.setUnit_symbol_tax("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void setUnit_tax_listFromResultset(Unit_tax_list aUnit_tax_list, ResultSet aResultSet) {
        try {
            try {
                aUnit_tax_list.setUnit_tax_list_id(aResultSet.getInt("unit_tax_list_id"));
            } catch (Exception e) {
                aUnit_tax_list.setUnit_tax_list_id(0);
            }
            try {
                aUnit_tax_list.setUnit_symbol_tax(aResultSet.getString("unit_symbol_tax"));
            } catch (Exception e) {
                aUnit_tax_list.setUnit_symbol_tax("");
            }
            try {
                aUnit_tax_list.setUnit_name_tax(aResultSet.getString("unit_name_tax"));
            } catch (Exception e) {
                aUnit_tax_list.setUnit_name_tax("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int saveUnit_tax_list(Unit_tax_list aUnit_tax_list) {
        int saved = 0;
        try {
            //save Unit_tax_list
            saved = this.insertUnit_tax_list(aUnit_tax_list);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int saveUnit_tax_list(List<Unit_tax_list> aUnit_tax_list) {
        int saved = 0;
        try {
            int Unit_tax_listSaved = 0;
            //save Unit_tax_list
            for (int i = 0, size = aUnit_tax_list.size(); i < size; i++) {
                Unit_tax_listSaved = Unit_tax_listSaved + this.insertUnit_tax_list(aUnit_tax_list.get(i));
            }

            if (Unit_tax_listSaved == aUnit_tax_list.size()) {
                saved = 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public int insertUnit_tax_list(Unit_tax_list aUnit_tax_list) {
        int saved = 0;
        String sql = "INSERT INTO unit_tax_list"
                + "(unit_symbol_tax, unit_name_tax)"
                + "VALUES"
                + "(?,?);";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //unit_symbol_tax, unit_name_tax
            if (aUnit_tax_list.getUnit_symbol_tax()!= null) {
                ps.setString(1, aUnit_tax_list.getUnit_symbol_tax());
            } else {
                ps.setString(1, "");
            }
            if (aUnit_tax_list.getUnit_name_tax()!= null) {
                ps.setString(2, aUnit_tax_list.getUnit_name_tax());
            } else {
                ps.setString(2, "");
            }
            
            ps.executeUpdate();
            saved = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return saved;
    }

    public void saveUnit(Unit unit, Unit_tax_list aUnit_tax_listObj) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = null;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (unit.getUnitId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (unit.getUnitId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (unit.getUnitName().length() <= 0 || unit.getUnitSymbol().length() <= 0) {
            msg = "Unit Name and Symbol Cannot be Empty";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (null != aUnit_tax_listObj) {
                unit.setUnit_symbol_tax(aUnit_tax_listObj.getUnit_symbol_tax());
            } else {
                unit.setUnit_symbol_tax("");
            }
            if (unit.getUnitId() == 0) {
                sql = "{call sp_insert_unit(?,?,?)}";
            } else if (unit.getUnitId() > 0) {
                sql = "{call sp_update_unit(?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (unit.getUnitId() == 0) {
                    cs.setString(1, unit.getUnitName());
                    cs.setString(2, unit.getUnitSymbol());
                    cs.setString(3, unit.getUnit_symbol_tax());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearUnit(unit, aUnit_tax_listObj);
                } else if (unit.getUnitId() > 0) {
                    cs.setInt(1, unit.getUnitId());
                    cs.setString(2, unit.getUnitName());
                    cs.setString(3, unit.getUnitSymbol());
                    cs.setString(4, unit.getUnit_symbol_tax());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                    this.clearUnit(unit, aUnit_tax_listObj);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Unit NOT saved"));
            }
        }

    }

    public Unit getUnit(int aUnitId) {
        String sql = "{call sp_search_unit_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aUnitId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Unit unit = new Unit();
                this.setUnitFromResultset(unit, rs);
                return unit;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deleteUnit() {
        this.deleteUnitById(this.SelectedUnitId);
    }

    public void deleteUnitByObject(Unit unit) {
        this.deleteUnitById(unit.getUnitId());
    }

    public void deleteUnitById(int UnitId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (null != new ItemBean().getItemObjectListByUnit(UnitId)) {
            msg = "Unit is in use by an Item";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "DELETE FROM unit WHERE unit_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, UnitId);
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Unit Not Deleted"));
            }
        }
    }

    public void displayUnit(Unit UnitFrom, Unit UnitTo, Unit_tax_list aUnit_tax_listObj) {
        try {
            this.clearUnit(UnitTo, aUnit_tax_listObj);
            UnitTo.setUnitId(UnitFrom.getUnitId());
            UnitTo.setUnitName(UnitFrom.getUnitName());
            UnitTo.setUnitSymbol(UnitFrom.getUnitSymbol());
            UnitTo.setUnit_symbol_tax(UnitFrom.getUnit_symbol_tax());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }

        try {
            if (UnitTo.getUnit_symbol_tax().length() > 0) {
                this.setUnit_tax_listObj(this.findUnit_tax_list(UnitTo.getUnit_symbol_tax()));
                //this.Unit_tax_listObj.setUnit_tax_list_id(utl.getUnit_tax_list_id());
                //this.Unit_tax_listObj.setUnit_symbol_tax(utl.getUnit_symbol_tax());
                //this.Unit_tax_listObj.setUnit_name_tax(utl.getUnit_name_tax());
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void clearUnit(Unit unit, Unit_tax_list aUnit_tax_listObj) {
        unit.setUnitId(0);
        unit.setUnitName("");
        unit.setUnitSymbol("");
        unit.setUnit_symbol_tax("");
        if (null != aUnit_tax_listObj) {
            this.clearUnit_tax_list(aUnit_tax_listObj);
        }
    }

    public void clearUnit_tax_list(Unit_tax_list aUnit_tax_listObj) {
        if (null != aUnit_tax_listObj) {
            aUnit_tax_listObj.setUnit_tax_list_id(0);
            aUnit_tax_listObj.setUnit_symbol_tax("");
            aUnit_tax_listObj.setUnit_name_tax("");
        }
    }

    public List<Unit> getUnits() {
        String sql;
        sql = "{call sp_search_unit_by_none()}";
        ResultSet rs = null;
        Units = new ArrayList<Unit>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit unit = new Unit();
                this.setUnitFromResultset(unit, rs);
                Units.add(unit);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Units;
    }

    /**
     * @param aUnitName
     * @return the Units
     */
    public List<Unit> getUnitsByUnitName(String aUnitName) {
        String sql;
        sql = "{call sp_search_unit_by_name(?)}";
        ResultSet rs = null;
        Units = new ArrayList<Unit>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aUnitName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit unit = new Unit();
                this.setUnitFromResultset(unit, rs);
                Units.add(unit);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Units;
    }

    public List<Unit> getUnitsByUnitCodeTax(String aUnitCodeTax) {
        String sql;
        sql = "SELECT * FROM unit WHERE unit_symbol_tax=?";
        ResultSet rs = null;
        Unit u = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aUnitCodeTax);
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit unit = new Unit();
                this.setUnitFromResultset(unit, rs);
                Units.add(unit);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Units;
    }

    public List<Unit_tax_list> getUnit_tax_lists() {
        String sql;
        sql = "SELECT * FROM unit_tax_list ORDER BY unit_name_tax ASC";
        ResultSet rs = null;
        List<Unit_tax_list> lst = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit_tax_list obj = new Unit_tax_list();
                this.setUnit_tax_listFromResultset(obj, rs);
                lst.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return lst;
    }

    public Unit_tax_list findUnit_tax_list(int aUnit_tax_list_id) {
        String sql = "SELECT * FROM unit_tax_list WHERE unit_tax_list_id=" + aUnit_tax_list_id;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                Unit_tax_list obj = new Unit_tax_list();
                this.setUnit_tax_listFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public Unit_tax_list findUnit_tax_list(String aUnit_code) {
        String sql = "SELECT * FROM unit_tax_list WHERE unit_symbol_tax='" + aUnit_code + "'";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                Unit_tax_list obj = new Unit_tax_list();
                this.setUnit_tax_listFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Unit_tax_list> searchUnit_tax_lists(String Query) {
        String sql = "SELECT * FROM unit_tax_list WHERE unit_name_tax LIKE '%" + Query + "%' OR unit_symbol_tax LIKE '%" + Query + "%' LIMIT 10";
        ResultSet rs = null;
        List<Unit_tax_list> lst = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit_tax_list obj = new Unit_tax_list();
                this.setUnit_tax_listFromResultset(obj, rs);
                lst.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return lst;
    }

    public int deleteUnit_tax_list_All() {
        int IsDeleted = 0;
        String sql = "DELETE FROM unit_tax_list WHERE unit_tax_list_id > ?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, 0);
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsDeleted;
    }

    public Unit getUnitByItemCodeTax(long aItemId, String aUnitCodeTax) {
        String sql;
        sql = "select u.* from item_unit_other iuo inner join unit u on iuo.item_id=? and iuo.other_unit_id=u.unit_id where u.unit_symbol_tax=?";
        ResultSet rs = null;
        Unit u = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aItemId);
            ps.setString(2, aUnitCodeTax);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = new Unit();
                this.setUnitFromResultset(u, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return u;
    }

    public void refreshUnit_tax_lists() {
        this.setUnit_tax_lists(this.getUnit_tax_lists());
    }

    /**
     * @param Units the Units to set
     */
    public void setUnits(List<Unit> Units) {
        this.Units = Units;
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
     * @return the SelectedUnit
     */
    public Unit getSelectedUnit() {
        return SelectedUnit;
    }

    /**
     * @param SelectedUnit the SelectedUnit to set
     */
    public void setSelectedUnit(Unit SelectedUnit) {
        this.SelectedUnit = SelectedUnit;
    }

    /**
     * @return the SelectedUnitId
     */
    public int getSelectedUnitId() {
        return SelectedUnitId;
    }

    /**
     * @param SelectedUnitId the SelectedUnitId to set
     */
    public void setSelectedUnitId(int SelectedUnitId) {
        this.SelectedUnitId = SelectedUnitId;
    }

    /**
     * @return the SearchUnitName
     */
    public String getSearchUnitName() {
        return SearchUnitName;
    }

    /**
     * @param SearchUnitName the SearchUnitName to set
     */
    public void setSearchUnitName(String SearchUnitName) {
        this.SearchUnitName = SearchUnitName;
    }

    /**
     * @param Unit_tax_lists the Unit_tax_lists to set
     */
    public void setUnit_tax_lists(List<Unit_tax_list> Unit_tax_lists) {
        this.Unit_tax_lists = Unit_tax_lists;
    }

    /**
     * @return the Unit_tax_listObj
     */
    public Unit_tax_list getUnit_tax_listObj() {
        return Unit_tax_listObj;
    }

    /**
     * @param Unit_tax_listObj the Unit_tax_listObj to set
     */
    public void setUnit_tax_listObj(Unit_tax_list Unit_tax_listObj) {
        this.Unit_tax_listObj = Unit_tax_listObj;
    }

    /**
     * @return the menuItemBean
     */
    public MenuItemBean getMenuItemBean() {
        return menuItemBean;
    }

    /**
     * @param menuItemBean the menuItemBean to set
     */
    public void setMenuItemBean(MenuItemBean menuItemBean) {
        this.menuItemBean = menuItemBean;
    }

}
