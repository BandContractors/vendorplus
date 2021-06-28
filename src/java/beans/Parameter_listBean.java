package beans;

import connections.DBConnection;
import entities.Parameter_list;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import utilities.Security;
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
@ManagedBean(name = "parameter_listBean")
@SessionScoped
public class Parameter_listBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(Parameter_listBean.class.getName());

    private String ActionMessage = null;
    private List<Parameter_list> Parameter_lists = new ArrayList<>();
    private Parameter_list Parameter_listObject = new Parameter_list();
    private static List<Parameter_list> SavedParameterLists = new ArrayList<>();
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void setParameter_listFromResultset(Parameter_list aParameter_list, ResultSet aResultSet) {
        try {
            try {
                aParameter_list.setParameter_list_id(aResultSet.getInt("parameter_list_id"));
            } catch (NullPointerException npe) {
                aParameter_list.setParameter_list_id(0);
            }
            try {
                aParameter_list.setContext(aResultSet.getString("context"));
            } catch (NullPointerException npe) {
                aParameter_list.setContext("");
            }
            try {
                aParameter_list.setParameter_name(aResultSet.getString("parameter_name"));
            } catch (NullPointerException npe) {
                aParameter_list.setParameter_name("");
            }
            try {
                aParameter_list.setParameter_value(aResultSet.getString("parameter_value"));
            } catch (NullPointerException npe) {
                aParameter_list.setParameter_value("");
            }
            try {
                aParameter_list.setDescription(aResultSet.getString("description"));
            } catch (NullPointerException npe) {
                aParameter_list.setDescription("");
            }
            try {
                aParameter_list.setStore_id(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aParameter_list.setStore_id(0);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public long saveParameter_list(Parameter_list aParameter_list) {
        UtilityBean ub = new UtilityBean();
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        String sql = null;
        long status = 0;
        if (aParameter_list.getParameter_list_id() == 0) {
            sql = "{call sp_insert_parameter_list(?,?,?,?,?)}";
        } else if (aParameter_list.getParameter_list_id() > 0) {
            sql = "{call sp_update_parameter_list(?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (aParameter_list.getParameter_list_id() == 0) {
                cs.setString("in_context", aParameter_list.getContext());
                cs.setString("in_parameter_name", aParameter_list.getParameter_name());
                cs.setString("in_parameter_value", aParameter_list.getParameter_value());
                try {
                    cs.setString("in_description", aParameter_list.getDescription());
                } catch (Exception e) {
                    cs.setString("in_description", "");
                }
                try {
                    cs.setInt("in_store_id", aParameter_list.getStore_id());
                } catch (Exception e) {
                    cs.setInt("in_store_id", 0);
                }
                cs.executeUpdate();
                status = 1;
                this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                this.clearParameter_list(aParameter_list);
            } else if (aParameter_list.getParameter_list_id() > 0) {
                cs.setLong("in_parameter_list_id", aParameter_list.getParameter_list_id());
                cs.setString("in_context", aParameter_list.getContext());
                cs.setString("in_parameter_name", aParameter_list.getParameter_name());
                cs.setString("in_parameter_value", aParameter_list.getParameter_value());
                try {
                    cs.setString("in_description", aParameter_list.getDescription());
                } catch (Exception e) {
                    cs.setString("in_description", "");
                }
                try {
                    cs.setInt("in_store_id", aParameter_list.getStore_id());
                } catch (Exception e) {
                    cs.setInt("in_store_id", 0);
                }
                cs.executeUpdate();
                status = 1;
                this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                this.clearParameter_list(aParameter_list);
            }
        } catch (Exception e) {
            status = 0;
            this.setActionMessage(ub.translateWordsInText(BaseName, "Not Saved"));
            LOGGER.log(Level.ERROR, e);
        }
        return status;
    }

    public Parameter_list getParameter_listById(int aParameter_list_id) {
        String sql;
        sql = "SELECT * FROM parameter_list WHERE parameter_list_id=" + aParameter_list_id;
        ResultSet rs = null;
        Parameter_list pl = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                pl = new Parameter_list();
                this.setParameter_listFromResultset(pl, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return pl;
    }

    public Parameter_list getParameter_listByIdStatic(int aParameter_list_id) {
        int ListItemIndex = 0;
        Parameter_list pl = null;
        try {
            int ListItemNo = SavedParameterLists.size();
            while (ListItemIndex < ListItemNo) {
                if (aParameter_list_id == SavedParameterLists.get(ListItemIndex).getParameter_list_id()) {
                    pl = new Parameter_list();
                    pl = SavedParameterLists.get(ListItemIndex);
                    break;
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception e) {

        }
        return pl;
    }

    public Parameter_list getParameter_listByContextName(String aContext, String aParameter_name) {
        String sql;
        sql = "SELECT * FROM parameter_list WHERE context='" + aContext + "' AND parameter_name='" + aParameter_name + "'";
        ResultSet rs = null;
        //Parameter_list pl = null;
        Parameter_list pl = new Parameter_list();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                //pl = new Parameter_list();
                this.setParameter_listFromResultset(pl, rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return pl;
    }

    public Parameter_list getParameter_listByContextNameMemory(String aContext, String aParameter_name) {
        int ListItemIndex = 0;
        //Parameter_list pl = null;
        Parameter_list pl = new Parameter_list();
        try {
            int ListItemNo = SavedParameterLists.size();
            while (ListItemIndex < ListItemNo) {
                if (aContext.equals(SavedParameterLists.get(ListItemIndex).getContext()) && aParameter_name.equals(SavedParameterLists.get(ListItemIndex).getParameter_name())) {
                    //pl = new Parameter_list();
                    pl = SavedParameterLists.get(ListItemIndex);
                    break;
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return pl;
    }

    public void saveParameter_listByIdMemory(int aParameter_list_id, String aParameter_value) {
        int ListItemIndex = 0;
        try {
            int ListItemNo = SavedParameterLists.size();
            while (ListItemIndex < ListItemNo) {
                if (aParameter_list_id == SavedParameterLists.get(ListItemIndex).getParameter_list_id()) {
                    SavedParameterLists.get(ListItemIndex).setParameter_value(aParameter_value);
                    break;
                }
                ListItemIndex = ListItemIndex + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Parameter_list> getParameter_listsByContext(String aContext) {
        String sql;
        sql = "SELECT * FROM parameter_list WHERE context='" + aContext + "'";
        ResultSet rs = null;
        List<Parameter_list> pls = new ArrayList<Parameter_list>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Parameter_list pl = new Parameter_list();
                this.setParameter_listFromResultset(pl, rs);
                pls.add(pl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return pls;
    }

    public void refreshSavedParameterLists() {
        String sql;
        sql = "SELECT * FROM parameter_list";
        ResultSet rs = null;
        SavedParameterLists = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Parameter_list pl = new Parameter_list();
                this.setParameter_listFromResultset(pl, rs);
                SavedParameterLists.add(pl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Parameter_list> getParameterListAll() {
        String sql;
        sql = "SELECT * FROM parameter_list ORDER BY context ASC,parameter_name ASC";
        ResultSet rs = null;
        List<Parameter_list> pls = new ArrayList<Parameter_list>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Parameter_list aParameter_list = new Parameter_list();
                this.setParameter_listFromResultset(aParameter_list, rs);
                pls.add(aParameter_list);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return pls;
    }

    public void displayParameter_list(Parameter_list Parameter_listFrom, Parameter_list Parameter_listTo) {
        try {
            Parameter_listTo.setParameter_list_id(Parameter_listFrom.getParameter_list_id());
            Parameter_listTo.setContext(Parameter_listFrom.getContext());
            Parameter_listTo.setParameter_name(Parameter_listFrom.getParameter_name());
            Parameter_listTo.setParameter_value(Parameter_listFrom.getParameter_value());
            Parameter_listTo.setDescription(Parameter_listFrom.getDescription());
            Parameter_listTo.setStore_id(Parameter_listFrom.getStore_id());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void initClearParameter_list(Parameter_list aParameter_list) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else if (aParameter_list != null) {
            this.clearParameter_list(aParameter_list);
        }
    }

    public void clearParameter_list(Parameter_list aParameter_list) {
        if (null != aParameter_list) {
            aParameter_list.setContext("");
            aParameter_list.setParameter_list_id(0);
            aParameter_list.setParameter_name("");
            aParameter_list.setParameter_value("");
            aParameter_list.setDescription("");
            aParameter_list.setStore_id(0);
        }
    }

    public void copyParameter_list(Parameter_list aFrom, Parameter_list aTo) {

    }

    public String encryptParameterValue(String aParameterValue) {
        UtilityBean ub = new UtilityBean();
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        String encryptedValue = "";
        try {
            if (aParameterValue.length() == 0) {
                this.setActionMessage(ub.translateWordsInText(BaseName, "Enter Value"));
            } else {
                encryptedValue = Security.Encrypt(aParameterValue);
            }
        } catch (Exception e) {
        }
        return encryptedValue;
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
     * @return the Parameter_lists
     */
    public List<Parameter_list> getParameter_lists() {
        return Parameter_lists;
    }

    /**
     * @param Parameter_lists the Parameter_lists to set
     */
    public void setParameter_lists(List<Parameter_list> Parameter_lists) {
        this.Parameter_lists = Parameter_lists;
    }

    /**
     * @return the Parameter_listObject
     */
    public Parameter_list getParameter_listObject() {
        return Parameter_listObject;
    }

    /**
     * @param Parameter_listObject the Parameter_listObject to set
     */
    public void setParameter_listObject(Parameter_list Parameter_listObject) {
        this.Parameter_listObject = Parameter_listObject;
    }

    /**
     * @return the SavedParameterLists
     */
    public static List<Parameter_list> getSavedParameterLists() {
        return SavedParameterLists;
    }

    /**
     * @param aSavedParameterLists the SavedParameterLists to set
     */
    public static void setSavedParameterLists(List<Parameter_list> aSavedParameterLists) {
        SavedParameterLists = aSavedParameterLists;
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
