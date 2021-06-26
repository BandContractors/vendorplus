package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.Location;
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
public class LocationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(LocationBean.class.getName());
    private List<Location> Locations;
    private String ActionMessage = null;
    private Location SelectedLocation = null;
    private int SelectedLocationId;
    private String SearchLocationName = "";
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void saveLocation(Location loc) {
        UtilityBean ub = new UtilityBean();
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        String sql = null;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (loc.getLocationId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (loc.getLocationId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (loc.getStoreId() == 0) {
            msg = "Select Location Store";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if ("".equals(loc.getLocationName()) || loc.getLocationName().length() > 20) {
            msg = "Location Name Must be between 1 and 20 Characters";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            if (loc.getLocationId() == 0) {
                sql = "{call sp_insert_location(?,?)}";
            } else if (loc.getLocationId() > 0) {
                sql = "{call sp_update_location(?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (loc.getLocationId() == 0) {
                    cs.setInt("in_store_id", loc.getStoreId());
                    cs.setString("in_location_name", loc.getLocationName());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                } else if (loc.getLocationId() > 0) {
                    cs.setLong("in_location_id", loc.getLocationId());
                    cs.setInt("in_store_id", loc.getStoreId());
                    cs.setString("in_location_name", loc.getLocationName());
                    cs.executeUpdate();
                    this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Location Not Saved"));
            }
        }

    }

    public Location getLocation(long LocId) {
        String sql = "SELECT * FROM location WHERE location_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, LocId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Location loc = new Location();
                loc.setLocationId(rs.getLong("location_id"));
                loc.setStoreId(rs.getInt("store_id"));
                loc.setLocationName(rs.getString("location_name"));
                return loc;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deleteLocation() {
        this.deleteLocationById(this.SelectedLocationId);
    }

    public void deleteLocationByObject(Location Loc) {
        this.deleteLocationById(Loc.getLocationId());
    }

    public void deleteLocationById(long LocId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        String msg = "";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            String sql = "DELETE FROM location WHERE location_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, LocId);
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
                this.setActionMessage(ub.translateWordsInText(BaseName, "Not Deleted"));
            }
        }
    }

    public void displayLocation(Location LocFrom, Location LocTo) {
        LocTo.setLocationId(LocFrom.getLocationId());
        LocTo.setStoreId(LocFrom.getStoreId());
        LocTo.setLocationName(LocFrom.getLocationName());
    }

    public void clearLocation(Location Loc) {
        Loc.setLocationId(0);
        Loc.setStoreId(0);
        Loc.setLocationName("");
    }

    public List<Location> getLocations() {
        String sql;

        sql = "{call sp_search_location(?,?,?)}";
        ResultSet rs = null;
        Locations = new ArrayList<Location>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, 0);
            ps.setString(2, this.SearchLocationName);
            ps.setInt(3, 0);

            rs = ps.executeQuery();
            while (rs.next()) {
                Location loc = new Location();
                loc.setLocationId(rs.getInt("location_id"));
                loc.setStoreId(rs.getInt("store_id"));
                loc.setLocationName(rs.getString("location_name"));
                Locations.add(loc);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return Locations;
    }

    public void searchLocations() {
        String sql;

        sql = "{call sp_search_location(?,?,?)}";
        ResultSet rs = null;
        Locations = new ArrayList<Location>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, 0);
            ps.setString(2, this.SearchLocationName);
            ps.setInt(3, 0);

            rs = ps.executeQuery();
            while (rs.next()) {
                Location loc = new Location();
                loc.setLocationId(rs.getInt("location_id"));
                loc.setStoreId(rs.getInt("store_id"));
                loc.setLocationName(rs.getString("location_name"));
                Locations.add(loc);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Location> getLocations(int aStoreId) {
        String sql;

        sql = "{call sp_search_location(?,?,?)}";
        ResultSet rs = null;
        Locations = new ArrayList<>();
        Locations.clear();
        if (aStoreId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aStoreId);
                ps.setString(2, "");
                ps.setInt(3, 0);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Location loc = new Location();
                    loc.setLocationId(rs.getInt("location_id"));
                    loc.setStoreId(rs.getInt("store_id"));
                    loc.setLocationName(rs.getString("location_name"));
                    Locations.add(loc);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        return Locations;
    }

    public String getLocationsString(List<Location> aLocations) {
        String the_string = "";
        int n = 0;
        try {
            n = aLocations.size();
        } catch (NullPointerException npe) {
            n = 0;
        }
        for (int i = 0; i < n; i++) {
            if (the_string.length() == 0) {
                the_string = aLocations.get(i).getLocationName();
            } else {
                the_string = the_string + "," + aLocations.get(i).getLocationName();
            }
        }
        return the_string;
    }

    public List<Location> getLocationsByStoreItem(int aStoreId, long aItemId) {
        String sql;

        sql = "{call sp_search_location_by_store_item(?,?)}";
        ResultSet rs = null;
        Locations = new ArrayList<Location>();
        Locations.clear();
        if (aItemId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, aStoreId);
                ps.setLong(2, aItemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Location loc = new Location();
                    loc.setLocationId(rs.getInt("location_id"));
                    loc.setStoreId(rs.getInt("store_id"));
                    loc.setLocationName(rs.getString("location_name"));
                    Locations.add(loc);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        return Locations;
    }

    public List<Location> getLocationsByItem(long aItemId) {
        String sql;

        sql = "{call sp_search_location_by_item_id(?)}";
        ResultSet rs = null;
        Locations = new ArrayList<Location>();
        Locations.clear();
        if (aItemId == 0) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, aItemId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Location loc = new Location();
                    loc.setLocationId(rs.getInt("location_id"));
                    loc.setStoreId(rs.getInt("store_id"));
                    loc.setLocationName(rs.getString("location_name"));
                    Locations.add(loc);
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        return Locations;
    }

    /**
     * @return the ActionMessage
     */
    public String getActionMessage() {
        return ActionMessage;
    }

    /**
     * @param aActionMessage the ActionMessage to set
     */
    public void setActionMessage(String aActionMessage) {
        this.ActionMessage = aActionMessage;
    }

    /**
     * @return the Locations
     */
    /**
     * @param Locations the Locations to set
     */
    public void setLocations(List<Location> Locations) {
        this.Locations = Locations;
    }

    /**
     * @return the SelectedLocation
     */
    public Location getSelectedLocation() {
        return SelectedLocation;
    }

    /**
     * @param SelectedLocation the SelectedLocation to set
     */
    public void setSelectedLocation(Location SelectedLocation) {
        this.SelectedLocation = SelectedLocation;
    }

    /**
     * @return the SelectedLocationId
     */
    public int getSelectedLocationId() {
        return SelectedLocationId;
    }

    /**
     * @param SelectedLocationId the SelectedLocationId to set
     */
    public void setSelectedLocationId(int SelectedLocationId) {
        this.SelectedLocationId = SelectedLocationId;
    }

    /**
     * @return the SearchLocationName
     */
    public String getSearchLocationName() {
        return SearchLocationName;
    }

    /**
     * @param SearchLocationName the SearchLocationName to set
     */
    public void setSearchLocationName(String SearchLocationName) {
        this.SearchLocationName = SearchLocationName;
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
