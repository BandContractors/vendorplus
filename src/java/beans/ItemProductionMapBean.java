package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.Item;
import entities.UserDetail;
import entities.ItemProductionMap;
import entities.TransItem;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.VARCHAR;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import utilities.CustomValidator;


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
public class ItemProductionMapBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ItemProductionMap> ItemProductionMaps;
    private String ActionMessage = null;
    private ItemProductionMap SelectedItemProductionMap = new ItemProductionMap();
    private int SelectedItemProductionMapId;
    private String SearchItemProductionMap = "";
    private String ItemAddedStatus = "Item Added";
    private String ItemNotAddedStatus = "Item Not Added";
    private int ShowItemAddedStatus = 0;
    private int ShowItemNotAddedStatus = 0;
    private long SelectedMapGroupId;
    long NewId = 0;
    private ArrayList<Item> ItemObjectList;
    private List<ItemProductionMap> ItemProductionMapsListEdit = new ArrayList<>();
    private List<ItemProductionMap> ItemProductionMapsList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();

    public void saveItemProductionMap(ItemProductionMap itemproductionmap) {

        String sql = null;
        String sql2 = null;
        String msg = "";
//        Item itemObj=null;

        if (itemproductionmap != null) {

            if (itemproductionmap.getItemProductionMapId() == 0) {
                sql = "{call sp_insert_item_production_map(?,?,?)}";
            } else if (itemproductionmap.getItemProductionMapId() > 0) {
                sql = "{call sp_update_item_production_map(?,?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (itemproductionmap.getItemProductionMapId() == 0) {
                    cs.setLong("in_output_item_id", itemproductionmap.getOutputItemId());
                    cs.setLong("in_input_item_id", itemproductionmap.getInputItemId());
                    cs.setDouble("in_input_qty", itemproductionmap.getInputQty());
                    cs.executeUpdate();
                } else if (itemproductionmap.getItemProductionMapId() > 0) {
                    cs.setLong("in_output_item_id", itemproductionmap.getOutputItemId());
                    cs.setLong("in_input_item_id", itemproductionmap.getInputItemId());
                    cs.setDouble("in_input_qty", itemproductionmap.getInputQty());
                    cs.executeUpdate();
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("ItemProductionMap NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("ItemProductionMap NOT saved!"));
            }
        } else {
            this.setActionMessage("Please select atleast one raw material");
        }

        this.clearItemProductionMap(itemproductionmap);
        this.setActionMessage("Saved Successfully");
    }

    public void saveItemProductionMap(ItemProductionMap aItemProductionMap, List<ItemProductionMap> aInputItems, List<ItemProductionMap> aItems) {
        String msg = "";
        msg = this.validateItemProductionMap(aItemProductionMap, aInputItems);
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            try {
                int savedok = 0;
                for (int i = 0; i < aInputItems.size(); i++) {
                    ItemProductionMap ic = aInputItems.get(i);
                    ic.setItemProductionMapId(aItemProductionMap.getItemProductionMapId());
                    ic.setOutputItemId(aItemProductionMap.getOutputItemId());
                    savedok = this.saveValidatedItem(aInputItems.get(i));
                    if (savedok == 0) {
                        break;
                    }
                }
                if (savedok == 1) {
                    msg = "Saved Successfully";
                    this.clearItemProductionMap(aItemProductionMap);
                    aInputItems.clear();
                    //refresh the list
                    this.refreshChildList(aItemProductionMap.getOutputItemId(), aItems);
                } else {
                    msg = "NOT saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } catch (Exception e) {
                System.err.println("saveItem_combination:" + e.getMessage());
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("NOT saved!"));
            }
        }
    }

    public String validateItemProductionMap(ItemProductionMap aItemProductionMap, List<ItemProductionMap> aInputItems) {
        String msg = "";
        String sql2 = "SELECT * FROM item_production_map WHERE input_item_id=" + aItemProductionMap.getOutputItemId();
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (aItemProductionMap.getItemProductionMapId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (aItemProductionMap.getItemProductionMapId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (aItemProductionMap.getOutputItemId() == 0) {
            msg = "Select a Parent Item please";
        } else if (new CustomValidator().CheckRecords(sql2) > 0) {
            msg = "A Child item cannot be mapped as a Parent item!";
        } else if (aInputItems.size() <= 0) {
            msg = "Please add Child Items!";
        } else {
            msg = "";
        }
        return msg;
    }

    public void refreshChildList(long aParent_item_id, List<ItemProductionMap> aList) {
        aList.clear();
        List<ItemProductionMap> ics;
        if (aParent_item_id == 0) {
            //ics = this.getItemProductionMapChildList();
            ics = new ArrayList<>();
        } else {
            ics = this.getItemProductionMapChildList(aParent_item_id);
        }
        ics.stream().forEach((ic) -> {
            aList.add(ic);
        });
    }

    public List<ItemProductionMap> getItemProductionMapChildList() {
        String sql = "SELECT * FROM item_production_map ORDER BY output_item_id";
        ResultSet rs = null;
        List<ItemProductionMap> aList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemProductionMap aObject = new ItemProductionMap();
                this.setItemProductionMapFromResultset(aObject, rs);
                this.updateLookUpsUI(aObject);
                aList.add(aObject);
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
        return aList;
    }

    public List<ItemProductionMap> getItemProductionMapChildList(long aParent_item_id) {
        String sql = "SELECT * FROM item_production_map WHERE output_item_id=" + aParent_item_id + " ORDER BY output_item_id";
        ResultSet rs = null;
        List<ItemProductionMap> aList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemProductionMap aObject = new ItemProductionMap();
                this.setItemProductionMapFromResultset(aObject, rs);
                this.updateLookUpsUI(aObject);
                aList.add(aObject);
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
        return aList;
    }

    public int saveValidatedItem(ItemProductionMap aItemProductionMap) {
        int save_status = 0;
        String sql = null;
        if (aItemProductionMap.getItemProductionMapId() == 0) {
            sql = "INSERT INTO item_production_map(output_item_id,input_item_id,input_qty) VALUES(" + aItemProductionMap.getOutputItemId() + "," + aItemProductionMap.getInputItemId() + "," + aItemProductionMap.getInputQty() + ")";
        } else if (aItemProductionMap.getItemProductionMapId() > 0) {
            sql = "UPDATE item_production_map SET output_item_id=" + aItemProductionMap.getOutputItemId() + ",input_item_id=" + aItemProductionMap.getInputItemId() + ",input_qty=" + aItemProductionMap.getInputQty() + " WHERE item_production_map_id=" + aItemProductionMap.getItemProductionMapId();
        }
        //System.out.println(sql);
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.executeUpdate();
            save_status = 1;
        } catch (SQLException se) {
            save_status = 0;
            System.err.println("saveValidatedItem:" + se.getMessage());
        }
        return save_status;
    }

    public void addItem(ItemProductionMap aItemProductionMap, List<ItemProductionMap> aItemProductionMapList, Item aInputItem) {
        String msg = "";
        if (null == aItemProductionMap) {
            msg = "Select Item to add";
        } else if (aItemProductionMap.getOutputItemId() == 0 || aItemProductionMap.getInputItemId() == 0 || aItemProductionMap.getInputQty() < 0) {//aItemProductionMap.getInputQty() <= 0
            msg = "Check Parent, Child Item and Qty";
        } else if (this.combinationExists(aItemProductionMap.getOutputItemId(), aItemProductionMap.getInputItemId())) {
            msg = "Input item already exists...";
        } else if (this.differentCurrencyExists(aItemProductionMap.getOutputItemId(), aItemProductionMap.getInputItemId())) {
            msg = "Both Input and Output items MUST be of the same Currency...";
        } else {
            ItemProductionMap ic = new ItemProductionMap();
            ic.setItemProductionMapId(aItemProductionMap.getItemProductionMapId());
            ic.setOutputItemId(aItemProductionMap.getOutputItemId());
            ic.setInputItemId(aItemProductionMap.getInputItemId());
            ic.setInputQty(aItemProductionMap.getInputQty());
            this.updateLookUpsUI(ic);
            this.ItemProductionMapsListEdit.add(ic);
            this.clearItemProductionChild(aItemProductionMap);
            new ItemBean().clearItem(aInputItem);
        }
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Add", new FacesMessage(msg));
        }
    }

    public boolean combinationExists(long aOutputItemId, long aInputItemId) {
        boolean found = false;
        String sql = "SELECT * FROM item_production_map WHERE output_item_id=" + aOutputItemId + " AND input_item_id=" + aInputItemId;
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                found = true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return found;
    }

    public boolean differentCurrencyExists(long aOutputItemId, long aInputItemId) {
        boolean found = false;
        String sql = "select count(distinct currency_code) as n from item where item_id in(" + aOutputItemId + "," + aInputItemId + ")";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("n") > 1) {
                    found = true;
                } else {
                    found = false;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return found;
    }

    public void updateLookUpsUI(ItemProductionMap aItemProductionMap) {
        try {
            if (null == aItemProductionMap) {
                //do nothing
            } else {
                //Parent
                try {
                    Item ParentItem = new ItemBean().getItem(aItemProductionMap.getOutputItemId());
                    aItemProductionMap.setOutputItemName(ParentItem.getDescription());
                    aItemProductionMap.setOutputItemUnit(new UnitBean().getUnit(ParentItem.getUnitId()).getUnitSymbol());
                } catch (NullPointerException npe) {
                    aItemProductionMap.setOutputItemName("");
                    aItemProductionMap.setOutputItemUnit("");
                }
                //Child
                try {
                    Item InputItem = new ItemBean().getItem(aItemProductionMap.getInputItemId());
                    aItemProductionMap.setInputItemName(InputItem.getDescription());
                    aItemProductionMap.setInputItemUnit(new UnitBean().getUnit(InputItem.getUnitId()).getUnitSymbol());
                } catch (NullPointerException npe) {
                    aItemProductionMap.setInputItemName("");
                    aItemProductionMap.setInputItemUnit("");
                }
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUI:" + e.getMessage());
        }
    }

    public void clearItemProductionChild(ItemProductionMap aItemProductionMap) {
        try {
            if (aItemProductionMap != null) {
                //aItem_combination.setItem_combination_id(0);
                //aItem_combination.setParent_item_id(0);
                aItemProductionMap.setInputItemId(0);
                aItemProductionMap.setInputQty(0);
            }
        } catch (Exception e) {
            System.out.println("clearItemProduction:" + e.getMessage());
        }
    }

    public ItemProductionMap getItemProductionMap(long ItemProductionMapId) {
        //revise this fully
        String sql = "{call sp_search_item_production_map_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, ItemProductionMapId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ItemProductionMap itemproductionmap = new ItemProductionMap();
                itemproductionmap.setItemProductionMapId(rs.getLong("item_production_map_id"));
                itemproductionmap.setOutputItemId(rs.getLong("output_item_id"));
                itemproductionmap.setInputItemId(rs.getLong("input_item_id"));
                itemproductionmap.setInputQty(rs.getDouble("input_qty"));
                return itemproductionmap;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public List<ItemProductionMap> getItemProductionMapsByOutputItemId(long OutputItemId) {
        String sql = "{call sp_search_item_production_map_by_output_item_id(?)}";
        ResultSet rs = null;
        List<ItemProductionMap> ItemProductionMaps2 = new ArrayList<ItemProductionMap>();
        ItemProductionMaps2.clear();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, OutputItemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemProductionMap itemproductionmap = new ItemProductionMap();
                itemproductionmap.setItemProductionMapId(rs.getLong("item_production_map_id"));
                itemproductionmap.setOutputItemId(rs.getLong("output_item_id"));
                itemproductionmap.setInputItemId(rs.getLong("input_item_id"));
                itemproductionmap.setInputQty(rs.getDouble("input_qty"));
                ItemProductionMaps2.add(itemproductionmap);
            }
            return ItemProductionMaps2;
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public ItemProductionMap getItemProductionMapByOutputItemId(long OutputItemId) {
        String sql = "{call sp_search_item_production_map_by_output_item_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, OutputItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ItemProductionMap itemproductionmap = new ItemProductionMap();
                itemproductionmap.setItemProductionMapId(rs.getLong("item_production_map_id"));
                itemproductionmap.setOutputItemId(rs.getLong("output_item_id"));
                itemproductionmap.setInputItemId(rs.getLong("input_item_id"));
                itemproductionmap.setInputQty(rs.getDouble("input_qty"));
                return itemproductionmap;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public ItemProductionMap getItemProductionMapByInputItemId(long InputItemId) {
        String sql = "{call sp_search_item_production_map_by_input_item_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, InputItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ItemProductionMap itemproductionmap = new ItemProductionMap();
                itemproductionmap.setItemProductionMapId(rs.getLong("item_production_map_id"));
                itemproductionmap.setOutputItemId(rs.getLong("output_item_id"));
                itemproductionmap.setInputItemId(rs.getLong("input_item_id"));
                itemproductionmap.setInputQty(rs.getDouble("input_qty"));
                return itemproductionmap;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return null;
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

    public void deleteItemProductionMap(ItemProductionMap aItemProdForDel, ItemProductionMap aOutput, List<ItemProductionMap> aList) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            String sql = "DELETE FROM item_production_map WHERE item_production_map_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, aItemProdForDel.getItemProductionMapId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.refreshChildList(aOutput.getOutputItemId(), aList);
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("ItemProductionMap NOT deleted");
            }
        }
    }

    public void displayItemProductionMap(ItemProductionMap ItemProductionMapFrom, ItemProductionMap ItemProductionMapTo) {
        ItemProductionMapTo.setItemProductionMapId(ItemProductionMapFrom.getItemProductionMapId());
        ItemProductionMapTo.setOutputItemId(ItemProductionMapFrom.getOutputItemId());
        ItemProductionMapTo.setInputItemId(ItemProductionMapFrom.getInputItemId());
        ItemProductionMapTo.setInputQty(ItemProductionMapFrom.getInputQty());
    }

    /**
     * @param BigItmId
     * @param SmallItmId
     * @return the ItemProductionMaps
     */
    public List<ItemProductionMap> getItemProductionMaps(long BigItmId, long SmallItmId) {
        String sql = "{call sp_search_item_production_map_by_map_group_id(?)}";
        ResultSet rs = null;
        ItemProductionMaps = new ArrayList<ItemProductionMap>();
        this.SelectedMapGroupId = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, this.getItemProductionMapGroupId(BigItmId, SmallItmId));
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemProductionMap itemproductionmap = new ItemProductionMap();
                itemproductionmap.setItemProductionMapId(rs.getLong("item_production_map_id"));
                itemproductionmap.setOutputItemId(rs.getLong("output_item_id"));
                itemproductionmap.setInputItemId(rs.getLong("input_item_id"));
                itemproductionmap.setInputQty(rs.getDouble("input_qty"));
                ItemProductionMaps.add(itemproductionmap);
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
        return ItemProductionMaps;
    }

    /**
     * @param BigItmId
     * @param SmallItmId
     * @return the ItemProductionMaps
     */
    public long getItemProductionMapGroupId(long BigItmId, long SmallItmId) {
        String sql = "{call sp_search_item_production_map_by_big_input_item_id(?,?)}";
        ResultSet rs = null;
        ItemProductionMaps = new ArrayList<ItemProductionMap>();
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
        return this.SelectedMapGroupId;
    }

    public List<ItemProductionMap> getItemProductionMaps() {
        String sql = "{call sp_search_item_production_map_by_map_group_id(?)}";
        ResultSet rs = null;
        ItemProductionMaps = new ArrayList<ItemProductionMap>();
        this.SelectedMapGroupId = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, this.SelectedMapGroupId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemProductionMap itemproductionmap = new ItemProductionMap();
                itemproductionmap.setItemProductionMapId(rs.getLong("item_production_map_id"));
                itemproductionmap.setOutputItemId(rs.getLong("output_item_id"));
                itemproductionmap.setInputItemId(rs.getLong("input_item_id"));
                itemproductionmap.setInputQty(rs.getDouble("input_qty"));
                ItemProductionMaps.add(itemproductionmap);
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
        return ItemProductionMaps;
    }

    public void clearItemProductionMap(ItemProductionMap im) {
        if (im != null) {
            im.setItemProductionMapId(0);
            im.setOutputItemId(0);
            im.setInputItemId(0);
            im.setInputQty(0);
            im.setInputQtyTotal(0);
            im.setInputQtyCurrent(0);
            im.setInputQtyBalance(0);
            im.setBatchno("");
            im.setCodeSpecific("");
            im.setDescSpecific("");
            im.setInputItemUnit("");
            im.setInputItemName("");
            im.setBatchno("");
        }
    }

    public long getNewMapGroupId() {
        String sql = "{call sp_get_new_id(?,?,?)}";
        NewId = 0;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, "item_production_map");
            cs.setString(2, "map_group_id");
            cs.registerOutParameter("out_new_id", VARCHAR);
            cs.executeUpdate();
            NewId = cs.getLong(3);
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            NewId = 0;
        }
        return NewId;
    }

    public int itemCountInMap(String BigSmall, long ItmId) {
        String sql = "";
        if ("BIG".equals(BigSmall)) {
            sql = "{call sp_search_item_production_map_by_output_item_id(?)}";
        } else if ("SMALL".equals(BigSmall)) {
            sql = "{call sp_search_item_production_map_by_input_item_id(?)}";
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
        return records;
    }

    public int groupCountInMap(long GrpId) {
        String sql = "{call sp_search_item_production_map_get_count_map_group_id(?)}";
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
        return records;
    }

    public List<Item> getParentItemObjectListForCombination() {
//        this.setTypedItemCode(Query);
        String sql;
        sql = "SELECT * FROM view_item WHERE is_sale=1 AND is_buy=0 AND is_asset=0 AND is_suspended='No' ORDER BY description ASC";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
//            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                new ItemBean().setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public List<Item> getInputItemObjectListForCombination() {
//        this.setTypedItemCode(Query);
        String sql;
        sql = "SELECT * FROM view_item WHERE is_sale=0 AND is_buy=1 AND is_asset=0 AND is_track=1 AND is_suspended='No' ORDER BY description ASC";
        ResultSet rs = null;
        this.ItemObjectList = new ArrayList<Item>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
//            ps.setString(1, Query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                new ItemBean().setItemFromResultset(item, rs);
                this.ItemObjectList.add(item);
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
        return ItemObjectList;
    }

    public void setItemProductionMapFromResultset(ItemProductionMap aItemProductionMap, ResultSet aResultSet) {
        try {
            try {
                aItemProductionMap.setItemProductionMapId(aResultSet.getLong("item_production_map_id"));
            } catch (NullPointerException npe) {
                aItemProductionMap.setItemProductionMapId(0);
            }
            try {
                aItemProductionMap.setOutputItemId(aResultSet.getLong("output_item_id"));
            } catch (NullPointerException npe) {
                aItemProductionMap.setOutputItemId(0);
            }
            try {
                aItemProductionMap.setInputItemId(aResultSet.getLong("input_item_id"));
            } catch (NullPointerException npe) {
                aItemProductionMap.setInputItemId(0);
            }
            try {
                aItemProductionMap.setInputQty(aResultSet.getDouble("input_qty"));
            } catch (NullPointerException npe) {
                aItemProductionMap.setInputQty(0);
            }

        } catch (SQLException se) {
            System.err.println("setItemFromResultset:" + se.getMessage());
        }
    }

    public void updateLookUpsUIInput(ItemProductionMap aItemProductionMap) {
        try {
            if (null == aItemProductionMap) {
                //do nothing
            } else {
                //Child
                try {
                    Item InputItem = new ItemBean().getItem(aItemProductionMap.getInputItemId());
                    aItemProductionMap.setInputItemName(InputItem.getDescription());
                    aItemProductionMap.setInputItemUnit(new UnitBean().getUnit(InputItem.getUnitId()).getUnitSymbol());
                } catch (NullPointerException npe) {
                    aItemProductionMap.setInputItemName("");
                    aItemProductionMap.setInputItemUnit("");
                }
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUIInput:" + e.getMessage());
        }
    }

    public void updateModelInput(ItemProductionMap aItemProductionMap, Item aItem) {
        try {
            if (null == aItem) {
                //do nothing
            } else {
                try {
                    aItemProductionMap.setInputItemId(aItem.getItemId());
                    aItemProductionMap.setInputItemName(aItem.getDescription());
                    aItemProductionMap.setInputItemUnit(new UnitBean().getUnit(aItem.getUnitId()).getUnitSymbol());
                    aItemProductionMap.setBatchno("");
                    aItemProductionMap.setCodeSpecific("");
                    aItemProductionMap.setDescSpecific("");
                    aItemProductionMap.setInputQty(1);
                } catch (NullPointerException npe) {
                    aItemProductionMap.setInputItemId(0);
                    aItemProductionMap.setInputItemName("");
                    aItemProductionMap.setInputItemUnit("");
                    aItemProductionMap.setBatchno("");
                    aItemProductionMap.setCodeSpecific("");
                    aItemProductionMap.setDescSpecific("");
                    aItemProductionMap.setInputQty(0);
                }
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUIInput:" + e.getMessage());
        }
    }

    public void updateModelInput(ItemProductionMap aItemProductionMap, Item aItem, TransItem aTransItem) {
        try {
            if (null == aItem) {
                //do nothing
            } else {
                try {
                    aItemProductionMap.setInputItemId(aItem.getItemId());
                    aItemProductionMap.setInputItemName(aItem.getDescription());
                    aItemProductionMap.setInputItemUnit(new UnitBean().getUnit(aItem.getUnitId()).getUnitSymbol());
                    aItemProductionMap.setBatchno("");
                    aItemProductionMap.setCodeSpecific("");
                    aItemProductionMap.setDescSpecific("");
                    aItemProductionMap.setInputQty(1);
                    aItemProductionMap.setInputQtyTotal(aItemProductionMap.getInputQty() * aTransItem.getItemQty());
                } catch (NullPointerException npe) {
                    aItemProductionMap.setInputItemId(0);
                    aItemProductionMap.setInputItemName("");
                    aItemProductionMap.setInputItemUnit("");
                    aItemProductionMap.setBatchno("");
                    aItemProductionMap.setCodeSpecific("");
                    aItemProductionMap.setDescSpecific("");
                    aItemProductionMap.setInputQty(0);
                    aItemProductionMap.setInputQtyTotal(0);
                }
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUIInput:" + e.getMessage());
        }
    }

    /**
     * @param ItemProductionMaps the ItemProductionMaps to set
     */
    public void setItemProductionMaps(List<ItemProductionMap> ItemProductionMaps) {
        this.ItemProductionMaps = ItemProductionMaps;
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
     * @return the SelectedItemProductionMapId
     */
    public int getSelectedItemProductionMapId() {
        return SelectedItemProductionMapId;
    }

    /**
     * @param SelectedItemProductionMapId the SelectedItemProductionMapId to set
     */
    public void setSelectedItemProductionMapId(int SelectedItemProductionMapId) {
        this.SelectedItemProductionMapId = SelectedItemProductionMapId;
    }

    /**
     * @return the SearchItemProductionMap
     */
    public String getSearchItemProductionMap() {
        return SearchItemProductionMap;
    }

    /**
     * @param SearchItemProductionMap the SearchItemProductionMap to set
     */
    public void setSearchItemProductionMap(String SearchItemProductionMap) {
        this.SearchItemProductionMap = SearchItemProductionMap;
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
     * @return the ItemAddedStatus
     */
    public String getItemAddedStatus() {
        return ItemAddedStatus;
    }

    /**
     * @param ItemAddedStatus the ItemAddedStatus to set
     */
    public void setItemAddedStatus(String ItemAddedStatus) {
        this.ItemAddedStatus = ItemAddedStatus;
    }

    /**
     * @return the ItemNotAddedStatus
     */
    public String getItemNotAddedStatus() {
        return ItemNotAddedStatus;
    }

    /**
     * @param ItemNotAddedStatus the ItemNotAddedStatus to set
     */
    public void setItemNotAddedStatus(String ItemNotAddedStatus) {
        this.ItemNotAddedStatus = ItemNotAddedStatus;
    }

    /**
     * @return the ShowItemAddedStatus
     */
    public int getShowItemAddedStatus() {
        return ShowItemAddedStatus;
    }

    /**
     * @param ShowItemAddedStatus the ShowItemAddedStatus to set
     */
    public void setShowItemAddedStatus(int ShowItemAddedStatus) {
        this.ShowItemAddedStatus = ShowItemAddedStatus;
    }

    /**
     * @return the ShowItemNotAddedStatus
     */
    public int getShowItemNotAddedStatus() {
        return ShowItemNotAddedStatus;
    }

    /**
     * @param ShowItemNotAddedStatus the ShowItemNotAddedStatus to set
     */
    public void setShowItemNotAddedStatus(int ShowItemNotAddedStatus) {
        this.ShowItemNotAddedStatus = ShowItemNotAddedStatus;
    }

    /**
     * @return the itemList
     */
    public List<Item> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * @return the SelectedItemProductionMap
     */
    public ItemProductionMap getSelectedItemProductionMap() {
        return SelectedItemProductionMap;
    }

    /**
     * @param SelectedItemProductionMap the SelectedItemProductionMap to set
     */
    public void setSelectedItemProductionMap(ItemProductionMap SelectedItemProductionMap) {
        this.SelectedItemProductionMap = SelectedItemProductionMap;
    }

    /**
     * @return the ItemProductionMapsListEdit
     */
    public List<ItemProductionMap> getItemProductionMapsListEdit() {
        return ItemProductionMapsListEdit;
    }

    /**
     * @param ItemProductionMapsListEdit the ItemProductionMapsListEdit to set
     */
    public void setItemProductionMapsListEdit(List<ItemProductionMap> ItemProductionMapsListEdit) {
        this.ItemProductionMapsListEdit = ItemProductionMapsListEdit;
    }

    /**
     * @return the ItemProductionMapsList
     */
    public List<ItemProductionMap> getItemProductionMapsList() {
        return ItemProductionMapsList;
    }

    /**
     * @param ItemProductionMapsList the ItemProductionMapsList to set
     */
    public void setItemProductionMapsList(List<ItemProductionMap> ItemProductionMapsList) {
        this.ItemProductionMapsList = ItemProductionMapsList;
    }

}
