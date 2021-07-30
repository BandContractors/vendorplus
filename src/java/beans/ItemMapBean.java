package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.Item;
import entities.UserDetail;
import entities.ItemMap;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.Types.VARCHAR;
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
public class ItemMapBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(ItemMapBean.class.getName());
    private List<ItemMap> ItemMaps;
    private ItemMap SelectedItemMap = null;
    private int SelectedItemMapId;
    private String SearchItemMap = "";
    private long SelectedMapGroupId;
    long NewId = 0;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void saveItemMap(ItemMap aItemMap, Item aBigItem, Item aSmallItem) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = null;
        String sql2 = null;
        if (aItemMap != null) {
            if ((null != aBigItem && aBigItem.getItemType().equals("SERVICE")) || (null != aSmallItem && aSmallItem.equals("SERVICE"))) {
                msg = "Service Item Cannot be Mapped";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
                List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
                GroupRightBean grb = new GroupRightBean();

                if (aItemMap.getItemMapId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
                    msg = "Not Allowed to Access this Function";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if (aItemMap.getItemMapId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
                    msg = "Not Allowed to Access this Function";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if (aItemMap.getBigItemId() == 0 || aItemMap.getSmallItemId() == 0) {
                    msg = "Select Big Item and Small Item";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if (aItemMap.getBigItemId() == aItemMap.getSmallItemId()) {
                    msg = "Big Item and Small Item Cannot be the Same";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if (aItemMap.getFractionQty() == 0) {
                    msg = "Specify Qty of Small Item in Big Item";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if (aItemMap.getPosition() == 0) {
                    msg = "Specify Mapping Position in the Group";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if ((this.itemCountInMap("BIG", aItemMap.getBigItemId()) > 0 && aItemMap.getItemMapId() == 0) || (this.itemCountInMap("BIG", aItemMap.getBigItemId()) > 0 && this.itemCountInMap("BIG", aItemMap.getBigItemId()) != 1 && aItemMap.getItemMapId() > 0)) {
                    msg = "Selected Big Item is already Mapped";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if ((this.itemCountInMap("SMALL", aItemMap.getSmallItemId()) > 0 && aItemMap.getItemMapId() == 0) || (this.itemCountInMap("SMALL", aItemMap.getSmallItemId()) > 0 && this.itemCountInMap("SMALL", aItemMap.getSmallItemId()) != 1 && aItemMap.getItemMapId() > 0)) {
                    msg = "Selected Small Item is already Mapped";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else if ((this.groupCountInMap(this.SelectedMapGroupId) >= 2 && aItemMap.getItemMapId() == 0) || (this.groupCountInMap(this.SelectedMapGroupId) >= 2 && this.groupCountInMap(this.SelectedMapGroupId) != 2 && aItemMap.getItemMapId() > 0)) {
                    msg = "You Cannot Map Beyond 3 Levels";
                    FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                } else {

                    if (aItemMap.getItemMapId() == 0) {
                        sql = "{call sp_insert_item_map(?,?,?,?,?)}";
                    } else if (aItemMap.getItemMapId() > 0) {
                        sql = "{call sp_update_item_map(?,?,?,?,?,?)}";
                    }
                    if (this.SelectedMapGroupId == 0) {
                        this.SelectedMapGroupId = this.getNewMapGroupId();
                    }
                    try (
                            Connection conn = DBConnection.getMySQLConnection();
                            CallableStatement cs = conn.prepareCall(sql);) {
                        if (aItemMap.getItemMapId() == 0) {
                            cs.setLong("in_big_item_id", aItemMap.getBigItemId());
                            cs.setLong("in_small_item_id", aItemMap.getSmallItemId());
                            cs.setDouble("in_fraction_qty", aItemMap.getFractionQty());
                            cs.setInt("in_position", aItemMap.getPosition());
                            cs.setLong("in_map_group_id", this.SelectedMapGroupId);
                            cs.executeUpdate();
                            //this.clearItemMap(aItemMap, aBigItem, aSmallItem);
                            msg = "Saved Successfully";
                            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                        } else if (aItemMap.getItemMapId() > 0) {
                            cs.setLong("in_item_map_id", aItemMap.getItemMapId());
                            cs.setLong("in_big_item_id", aItemMap.getBigItemId());
                            cs.setLong("in_small_item_id", aItemMap.getSmallItemId());
                            cs.setDouble("in_fraction_qty", aItemMap.getFractionQty());
                            cs.setInt("in_position", aItemMap.getPosition());
                            cs.setLong("in_map_group_id", this.SelectedMapGroupId);
                            cs.executeUpdate();
                            //this.clearItemMap(aItemMap, aBigItem, aSmallItem);
                            msg = "Saved Successfully";
                            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                        }
                    } catch (Exception e) {
                        LOGGER.log(Level.ERROR, e);
                        msg = "Item Map Not Saved";
                        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
                    }
                }
            }
        }
    }

    public ItemMap getItemMap(long ItemMapId) {
        //revise this fully
        String sql = "{call sp_search_item_map_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, ItemMapId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ItemMap itemmap = new ItemMap();
                itemmap.setItemMapId(rs.getLong("item_map_id"));
                itemmap.setBigItemId(rs.getLong("big_item_id"));
                itemmap.setSmallItemId(rs.getLong("small_item_id"));
                itemmap.setFractionQty(rs.getDouble("fraction_qty"));
                itemmap.setPosition(rs.getInt("position"));
                itemmap.setMapGroupId(rs.getLong("map_group_id"));
                return itemmap;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }

    }

    public List<ItemMap> getItemMapsByBigItemId(long BigItemId) {
        String sql = "{call sp_search_item_map_by_big_item_id(?)}";
        ResultSet rs = null;
        List<ItemMap> ItemMaps2 = new ArrayList<ItemMap>();
        ItemMaps2.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, BigItemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemMap itemmap = new ItemMap();
                itemmap.setItemMapId(rs.getLong("item_map_id"));
                itemmap.setBigItemId(rs.getLong("big_item_id"));
                itemmap.setSmallItemId(rs.getLong("small_item_id"));
                itemmap.setFractionQty(rs.getDouble("fraction_qty"));
                itemmap.setPosition(rs.getInt("position"));
                itemmap.setMapGroupId(rs.getLong("map_group_id"));
                ItemMaps2.add(itemmap);
            }
            return ItemMaps2;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public ItemMap getItemMapByBigItemId(long BigItemId) {
        String sql = "{call sp_search_item_map_by_big_item_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, BigItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ItemMap itemmap = new ItemMap();
                itemmap.setItemMapId(rs.getLong("item_map_id"));
                itemmap.setBigItemId(rs.getLong("big_item_id"));
                itemmap.setSmallItemId(rs.getLong("small_item_id"));
                itemmap.setFractionQty(rs.getDouble("fraction_qty"));
                itemmap.setPosition(rs.getInt("position"));
                itemmap.setMapGroupId(rs.getLong("map_group_id"));
                return itemmap;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public ItemMap getItemMapBySmallItemId(long SmallItemId) {
        String sql = "{call sp_search_item_map_by_small_item_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, SmallItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ItemMap itemmap = new ItemMap();
                itemmap.setItemMapId(rs.getLong("item_map_id"));
                itemmap.setBigItemId(rs.getLong("big_item_id"));
                itemmap.setSmallItemId(rs.getLong("small_item_id"));
                itemmap.setFractionQty(rs.getDouble("fraction_qty"));
                itemmap.setPosition(rs.getInt("position"));
                itemmap.setMapGroupId(rs.getLong("map_group_id"));
                return itemmap;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public void deleteItemMap(ItemMap itemmap) {
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
        } else {
            String sql = "DELETE FROM item_map WHERE item_map_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, itemmap.getItemMapId());
                ps.executeUpdate();
                msg = "Deleted Successfully";
                FacesContext.getCurrentInstance().addMessage("Delete", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public void displayItemMap(ItemMap ItemMapFrom, ItemMap ItemMapTo) {
        ItemMapTo.setItemMapId(ItemMapFrom.getItemMapId());
        ItemMapTo.setBigItemId(ItemMapFrom.getBigItemId());
        ItemMapTo.setSmallItemId(ItemMapFrom.getSmallItemId());
        ItemMapTo.setFractionQty(ItemMapFrom.getFractionQty());
        ItemMapTo.setPosition(ItemMapFrom.getPosition());
        ItemMapTo.setMapGroupId(ItemMapFrom.getMapGroupId());
    }

    /**
     * @param BigItmId
     * @param SmallItmId
     * @return the ItemMaps
     */
    public List<ItemMap> getItemMaps(long BigItmId, long SmallItmId) {
        String sql = "{call sp_search_item_map_by_map_group_id(?)}";
        ResultSet rs = null;
        ItemMaps = new ArrayList<ItemMap>();
        this.SelectedMapGroupId = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, this.getItemMapGroupId(BigItmId, SmallItmId));
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemMap itemmap = new ItemMap();
                itemmap.setItemMapId(rs.getLong("item_map_id"));
                itemmap.setBigItemId(rs.getLong("big_item_id"));
                itemmap.setSmallItemId(rs.getLong("small_item_id"));
                itemmap.setFractionQty(rs.getDouble("fraction_qty"));
                itemmap.setPosition(rs.getInt("position"));
                itemmap.setMapGroupId(rs.getLong("map_group_id"));
                this.SelectedMapGroupId = rs.getLong("map_group_id");
                ItemMaps.add(itemmap);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ItemMaps;
    }

    /**
     * @param BigItmId
     * @param SmallItmId
     * @return the ItemMaps
     */
    public long getItemMapGroupId(long BigItmId, long SmallItmId) {
        String sql = "{call sp_search_item_map_by_big_small_item_id(?,?)}";
        ResultSet rs = null;
        ItemMaps = new ArrayList<ItemMap>();
        this.SelectedMapGroupId = 0;

        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, BigItmId);
            ps.setLong(2, SmallItmId);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.SelectedMapGroupId = rs.getLong("map_group_id");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return this.SelectedMapGroupId;
    }

    public List<ItemMap> getItemMaps() {
        String sql = "{call sp_search_item_map_by_map_group_id(?)}";
        ResultSet rs = null;
        ItemMaps = new ArrayList<ItemMap>();
        this.SelectedMapGroupId = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, this.SelectedMapGroupId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemMap itemmap = new ItemMap();
                itemmap.setItemMapId(rs.getLong("item_map_id"));
                itemmap.setBigItemId(rs.getLong("big_item_id"));
                itemmap.setSmallItemId(rs.getLong("small_item_id"));
                itemmap.setFractionQty(rs.getDouble("fraction_qty"));
                itemmap.setPosition(rs.getInt("position"));
                itemmap.setMapGroupId(rs.getLong("map_group_id"));
                this.SelectedMapGroupId = rs.getLong("map_group_id");
                ItemMaps.add(itemmap);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ItemMaps;
    }

    public void clearItemMap(ItemMap im, Item aBigItem, Item aSmallItem) {
        if (im != null) {
            im.setItemMapId(0);
            im.setBigItemId(0);
            im.setSmallItemId(0);
            im.setFractionQty(0);
            im.setPosition(0);
        }
        new ItemBean().clearItem(aBigItem);
        new ItemBean().clearItem(aSmallItem);
    }

    public long getNewMapGroupId() {
        String sql = "{call sp_get_new_id(?,?,?)}";
        NewId = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, "item_map");
            cs.setString(2, "map_group_id");
            cs.registerOutParameter("out_new_id", VARCHAR);
            cs.executeUpdate();
            NewId = cs.getLong(3);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            NewId = 0;
        }
        return NewId;
    }

    public int itemCountInMap(String BigSmall, long ItmId) {
        String sql = "";
        if ("BIG".equals(BigSmall)) {
            sql = "{call sp_search_item_map_by_big_item_id(?)}";
        } else if ("SMALL".equals(BigSmall)) {
            sql = "{call sp_search_item_map_by_small_item_id(?)}";
        }
        ResultSet rs = null;
        int records = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, ItmId);
            rs = ps.executeQuery();
            while (rs.next()) {
                records = records + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return records;
    }

    public int groupCountInMap(long GrpId) {
        String sql = "{call sp_search_item_map_get_count_map_group_id(?)}";
        ResultSet rs = null;
        int records = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, GrpId);
            rs = ps.executeQuery();
            while (rs.next()) {
                records = records + 1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return records;
    }

    /**
     * @param ItemMaps the ItemMaps to set
     */
    public void setItemMaps(List<ItemMap> ItemMaps) {
        this.ItemMaps = ItemMaps;
    }

    /**
     * @return the SelectedItemMap
     */
    public ItemMap getSelectedItemMap() {
        return SelectedItemMap;
    }

    /**
     * @param SelectedItemMap the SelectedItemMap to set
     */
    public void setSelectedItemMap(ItemMap SelectedItemMap) {
        this.SelectedItemMap = SelectedItemMap;
    }

    /**
     * @return the SelectedItemMapId
     */
    public int getSelectedItemMapId() {
        return SelectedItemMapId;
    }

    /**
     * @param SelectedItemMapId the SelectedItemMapId to set
     */
    public void setSelectedItemMapId(int SelectedItemMapId) {
        this.SelectedItemMapId = SelectedItemMapId;
    }

    /**
     * @return the SearchItemMap
     */
    public String getSearchItemMap() {
        return SearchItemMap;
    }

    /**
     * @param SearchItemMap the SearchItemMap to set
     */
    public void setSearchItemMap(String SearchItemMap) {
        this.SearchItemMap = SearchItemMap;
    }

    /**
     * @return the SelectedMapGroupId
     */
    public long getSelectedMapGroupId() {
        return SelectedMapGroupId;
    }

    /**
     * @param SelectedMapGroupId the SelectedMapGroupId to set
     */
    public void setSelectedMapGroupId(long SelectedMapGroupId) {
        this.SelectedMapGroupId = SelectedMapGroupId;
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
