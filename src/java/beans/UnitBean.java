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

    public void saveUnit(Unit unit, Unit_tax_list aUnit_tax_listObj) {
        UtilityBean ub = new UtilityBean();
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
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
                unit.setUnitId(rs.getInt("unit_id"));
                unit.setUnitName(rs.getString("unit_name"));
                unit.setUnitSymbol(rs.getString("unit_symbol"));
                unit.setUnit_symbol_tax(rs.getString("unit_symbol_tax"));
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
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
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
            System.out.println("displayUnit-a:" + e.getMessage());
        }

        try {
            if (UnitTo.getUnit_symbol_tax().length() > 0) {
                this.setUnit_tax_listObj(this.findUnit_tax_list(UnitTo.getUnit_symbol_tax()));
                //this.Unit_tax_listObj.setUnit_tax_list_id(utl.getUnit_tax_list_id());
                //this.Unit_tax_listObj.setUnit_symbol_tax(utl.getUnit_symbol_tax());
                //this.Unit_tax_listObj.setUnit_name_tax(utl.getUnit_name_tax());
            }
        } catch (Exception e) {
            System.out.println("displayUnit-b:" + e.getMessage());
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
                unit.setUnitId(rs.getInt("unit_id"));
                unit.setUnitName(rs.getString("unit_name"));
                unit.setUnitSymbol(rs.getString("unit_symbol"));
                unit.setUnit_symbol_tax(rs.getString("unit_symbol_tax"));
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
                unit.setUnitId(rs.getInt("unit_id"));
                unit.setUnitName(rs.getString("unit_name"));
                unit.setUnitSymbol(rs.getString("unit_symbol"));
                unit.setUnit_symbol_tax(rs.getString("unit_symbol_tax"));
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
                obj.setUnit_tax_list_id(rs.getInt("unit_tax_list_id"));
                obj.setUnit_symbol_tax(rs.getString("unit_symbol_tax"));
                obj.setUnit_name_tax(rs.getString("unit_name_tax"));
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
                obj.setUnit_tax_list_id(rs.getInt("unit_tax_list_id"));
                obj.setUnit_symbol_tax(rs.getString("unit_symbol_tax"));
                obj.setUnit_name_tax(rs.getString("unit_name_tax"));
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
                obj.setUnit_tax_list_id(rs.getInt("unit_tax_list_id"));
                obj.setUnit_symbol_tax(rs.getString("unit_symbol_tax"));
                obj.setUnit_name_tax(rs.getString("unit_name_tax"));
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
                obj.setUnit_tax_list_id(rs.getInt("unit_tax_list_id"));
                obj.setUnit_symbol_tax(rs.getString("unit_symbol_tax"));
                obj.setUnit_name_tax(rs.getString("unit_name_tax"));
                lst.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return lst;
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
