package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.Item;
import entities.Item_combination;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import utilities.CustomValidator;

@ManagedBean
@SessionScoped
public class Item_combinationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ActionMessage = "";
    private List<Item_combination> Item_combinationsList = new ArrayList<>();
    private List<Item_combination> Item_combinationsListEdit = new ArrayList<>();
    private Item_combination Item_combinationObj;
    private List<Item_combination> Item_combinationsParentList = new ArrayList<>();
    private List<Item_combination> Item_combinationsChildList = new ArrayList<>();

    public void initItem_combination(Item_combination aItem_combinationObj, List<Item_combination> aItem_combinationsListEdit, List<Item_combination> aItem_combinationsList, Item aParentItem, Item aChildItem) {
        this.clearItem_combination(aItem_combinationObj);
        new ItemBean().clearItem(aParentItem);
        new ItemBean().clearItem(aChildItem);
        aItem_combinationsListEdit.clear();
        aItem_combinationsList.clear();
        this.refreshChildList(aItem_combinationObj.getParent_item_id(), aItem_combinationsList);
    }

    public void addItem(Item_combination aItem_combinationObj, List<Item_combination> aItem_combinationsListEdit, Item aChilItem) {
        String msg = "";
        if (null == aItem_combinationObj) {
            msg = "Select Item to add";
        } else if (aItem_combinationObj.getParent_item_id() == 0 || aItem_combinationObj.getChild_item_id() == 0 || aItem_combinationObj.getChild_qty() <= 0) {
            msg = "Check Parent, Child Item and Qty";
        } else if (this.combinationExists(aItem_combinationObj.getParent_item_id(), aItem_combinationObj.getChild_item_id())) {
            msg = "Child item already exists...";
        } else {
            Item_combination ic = new Item_combination();
            ic.setItem_combination_id(aItem_combinationObj.getItem_combination_id());
            ic.setParent_item_id(aItem_combinationObj.getParent_item_id());
            ic.setChild_item_id(aItem_combinationObj.getChild_item_id());
            ic.setChild_qty(aItem_combinationObj.getChild_qty());
            this.updateLookUpsUI(ic);
            this.Item_combinationsListEdit.add(ic);
            this.clearItem_combinationChild(aItem_combinationObj);
            new ItemBean().clearItem(aChilItem);
        }
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Add", new FacesMessage(msg));
        }
    }

    public boolean combinationExists(long aParentItemId, long aChildItemId) {
        boolean found = false;
        String sql = "SELECT * FROM item_combination WHERE parent_item_id=" + aParentItemId + " AND child_item_id=" + aChildItemId;
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

    public void save(Item_combination aItem_combination, List<Item_combination> aChildItems, List<Item_combination> aItems) {
        String msg = "";
        msg = this.validateItem_combination(aItem_combination, aChildItems);
        if (msg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            try {
                int savedok = 0;
                for (int i = 0; i < aChildItems.size(); i++) {
                    Item_combination ic = aChildItems.get(i);
                    ic.setItem_combination_id(aItem_combination.getItem_combination_id());
                    ic.setParent_item_id(aItem_combination.getParent_item_id());
                    savedok = this.saveValidatedItem(aChildItems.get(i));
                    if (savedok == 0) {
                        break;
                    }
                }
                if (savedok == 1) {
                    msg = "Saved Successfully";
                    this.clearItem_combinationChild(aItem_combination);
                    aChildItems.clear();
                    //refresh the list
                    this.refreshChildList(aItem_combination.getParent_item_id(), aItems);
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

    public String validateItem_combination(Item_combination aItem_combination, List<Item_combination> aChildItems) {
        String msg = "";
        String sql2 = "SELECT * FROM item_combination WHERE child_item_id=" + aItem_combination.getParent_item_id();
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (aItem_combination.getItem_combination_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (aItem_combination.getItem_combination_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (aItem_combination.getParent_item_id() == 0) {
            msg = "Select a Parent Item please";
        } else if (new CustomValidator().CheckRecords(sql2) > 0) {
            msg = "A Child item cannot be mapped as a Parent item!";
        } else if (aChildItems.size() <= 0) {
            msg = "Please add Child Items!";
        } else {
            msg = "";
        }
        return msg;
    }

    public int saveValidatedItem(Item_combination aItem_combination) {
        int save_status = 0;
        String sql = null;
        if (aItem_combination.getItem_combination_id() == 0) {
            sql = "INSERT INTO item_combination(parent_item_id,child_item_id,child_qty) VALUES(" + aItem_combination.getParent_item_id() + "," + aItem_combination.getChild_item_id() + "," + aItem_combination.getChild_qty() + ")";
        } else if (aItem_combination.getItem_combination_id() > 0) {
            sql = "UPDATE item_combination SET parent_item_id=" + aItem_combination.getParent_item_id() + ",child_item_id=" + aItem_combination.getChild_item_id() + ",child_qty=" + aItem_combination.getChild_qty() + " WHERE item_combination_id=" + aItem_combination.getItem_combination_id();
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

    public void setItem_combinationFromResultset(Item_combination aItem_combination, ResultSet aResultSet) {
        try {
            try {
                aItem_combination.setItem_combination_id(aResultSet.getInt("item_combination_id"));
            } catch (NullPointerException npe) {
                aItem_combination.setItem_combination_id(0);
            }
            try {
                aItem_combination.setParent_item_id(aResultSet.getLong("parent_item_id"));
            } catch (NullPointerException npe) {
                aItem_combination.setParent_item_id(0);
            }
            try {
                aItem_combination.setChild_item_id(aResultSet.getLong("child_item_id"));
            } catch (NullPointerException npe) {
                aItem_combination.setChild_item_id(0);
            }
            try {
                aItem_combination.setChild_qty(aResultSet.getDouble("child_qty"));
            } catch (NullPointerException npe) {
                aItem_combination.setChild_qty(0);
            }
        } catch (SQLException se) {
            System.err.println("setItem_combinationFromResultset:" + se.getMessage());
        }
    }

    public void deleteItem(Item_combination aItem_combDel, Item_combination aParentItemComb, List<Item_combination> aList) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            String sql = "DELETE FROM item_combination WHERE item_combination_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, aItem_combDel.getItem_combination_id());
                ps.executeUpdate();
                this.refreshChildList(aParentItemComb.getParent_item_id(), aList);
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Deleted Successfully!"));
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Item_combination NOT deleted"));
            }
        }
    }

    public void copyItem_combination(Item_combination Item_combinationFrom, Item_combination Item_combinationTo) {
        try {
            Item_combinationTo.setItem_combination_id(Item_combinationFrom.getItem_combination_id());
            Item_combinationTo.setParent_item_id(Item_combinationFrom.getParent_item_id());
            Item_combinationTo.setChild_item_id(Item_combinationFrom.getChild_item_id());
            Item_combinationTo.setChild_qty(Item_combinationFrom.getChild_qty());
        } catch (Exception e) {
            System.out.println("copyItem_combination:" + e.getMessage());
        }
    }

    public void clearItem_combination(Item_combination aItem_combination) {
        try {
            if (aItem_combination != null) {
                aItem_combination.setItem_combination_id(0);
                aItem_combination.setParent_item_id(0);
                aItem_combination.setChild_item_id(0);
                aItem_combination.setChild_qty(0);
            }
        } catch (Exception e) {
            System.out.println("clearItem_combination:" + e.getMessage());
        }
    }

    public void clearItem_combinationChild(Item_combination aItem_combination) {
        try {
            if (aItem_combination != null) {
                //aItem_combination.setItem_combination_id(0);
                //aItem_combination.setParent_item_id(0);
                aItem_combination.setChild_item_id(0);
                aItem_combination.setChild_qty(0);
            }
        } catch (Exception e) {
            System.out.println("clearItem_combination:" + e.getMessage());
        }
    }

    public void clearItem_combination(Item_combination aItem_combination, List<Item_combination> aList) {
        try {
            if (aItem_combination != null) {
                aItem_combination.setItem_combination_id(0);
                aItem_combination.setParent_item_id(0);
                aItem_combination.setChild_item_id(0);
                aItem_combination.setChild_qty(0);
            }
            aList.clear();
        } catch (Exception e) {
            System.out.println("clearItem_combination:" + e.getMessage());
        }
    }

    public void initClearItem_combination(Item_combination aItem_combination, List<Item_combination> aItem_combinationList) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            try {
                if (aItem_combination != null) {
                    this.clearItem_combination(aItem_combination);
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (aItem_combinationList != null) {
                    aItem_combinationList.clear();
                }
            } catch (NullPointerException npe) {
            }
        }
    }

    /**
     * @return the Item_combinationsParentList
     */
    public List<Item_combination> getItem_combinationsParentList() {
        return Item_combinationsParentList;
    }

    /**
     * @param Item_combinationsParentList the Item_combinationsParentList to set
     */
    public void setItem_combinationsParentList(List<Item_combination> Item_combinationsParentList) {
        this.Item_combinationsParentList = Item_combinationsParentList;
    }

    /**
     * @return the Item_combinationsChildList
     */
    public List<Item_combination> getItem_combinationsChildList() {
        return Item_combinationsChildList;
    }

    /**
     * @param Item_combinationsChildList the Item_combinationsChildList to set
     */
    public void setItem_combinationsChildList(List<Item_combination> Item_combinationsChildList) {
        this.Item_combinationsChildList = Item_combinationsChildList;
    }

    public void refreshParentList(long aParent_item_id, List<Item_combination> aList) {
        aList.clear();
        //aList = this.getItem_combinationParentList(aParent_item_id);
    }

    public void refreshChildList(long aParent_item_id, List<Item_combination> aList) {
        aList.clear();
        List<Item_combination> ics;
        if (aParent_item_id == 0) {
            ics = this.getItem_combinationChildList();
        } else {
            ics = this.getItem_combinationChildList(aParent_item_id);
        }
        ics.stream().forEach((ic) -> {
            aList.add(ic);
        });
    }

    public List<Item_combination> getItem_combinationChildList() {
        String sql = "SELECT * FROM item_combination ORDER BY parent_item_id";
        ResultSet rs = null;
        List<Item_combination> aList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Item_combination aObject = new Item_combination();
                this.setItem_combinationFromResultset(aObject, rs);
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

    public List<Item_combination> getItem_combinationChildList(long aParent_item_id) {
        String sql = "SELECT * FROM item_combination WHERE parent_item_id=" + aParent_item_id + " ORDER BY parent_item_id";
        ResultSet rs = null;
        List<Item_combination> aList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Item_combination aObject = new Item_combination();
                this.setItem_combinationFromResultset(aObject, rs);
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

    public List<Item_combination> getItem_combinationParentList(long aChild_item_id) {
        String sql = "SELECT * FROM item_combination WHERE child_item_id=" + aChild_item_id;
        ResultSet rs = null;
        List<Item_combination> aList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Item_combination aObject = new Item_combination();
                this.setItem_combinationFromResultset(aObject, rs);
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

    public void updateLookUpsUI(Item_combination aItem_combination) {
        try {
            if (null == aItem_combination) {
                //do nothing
            } else {
                //Parent
                try {
                    Item ParentItem = new ItemBean().getItem(aItem_combination.getParent_item_id());
                    aItem_combination.setParent_item_name(ParentItem.getDescription());
                    aItem_combination.setParent_item_unit(new UnitBean().getUnit(ParentItem.getUnitId()).getUnitSymbol());
                } catch (NullPointerException npe) {
                    aItem_combination.setParent_item_name("");
                    aItem_combination.setParent_item_unit("");
                }
                //Child
                try {
                    Item ChildItem = new ItemBean().getItem(aItem_combination.getChild_item_id());
                    aItem_combination.setChild_item_name(ChildItem.getDescription());
                    aItem_combination.setChild_item_unit(new UnitBean().getUnit(ChildItem.getUnitId()).getUnitSymbol());
                } catch (NullPointerException npe) {
                    aItem_combination.setChild_item_name("");
                    aItem_combination.setChild_item_unit("");
                }
            }
        } catch (Exception e) {
            System.out.println("updateLookUpsUI:" + e.getMessage());
        }
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
     * @return the Item_combinationsList
     */
    public List<Item_combination> getItem_combinationsList() {
        return Item_combinationsList;
    }

    /**
     * @param Item_combinationsList the Item_combinationsList to set
     */
    public void setItem_combinationsList(List<Item_combination> Item_combinationsList) {
        this.Item_combinationsList = Item_combinationsList;
    }

    /**
     * @return the Item_combinationObj
     */
    public Item_combination getItem_combinationObj() {
        return Item_combinationObj;
    }

    /**
     * @param Item_combinationObj the Item_combinationObj to set
     */
    public void setItem_combinationObj(Item_combination Item_combinationObj) {
        this.Item_combinationObj = Item_combinationObj;
    }

    /**
     * @return the Item_combinationsListEdit
     */
    public List<Item_combination> getItem_combinationsListEdit() {
        return Item_combinationsListEdit;
    }

    /**
     * @param Item_combinationsListEdit the Item_combinationsListEdit to set
     */
    public void setItem_combinationsListEdit(List<Item_combination> Item_combinationsListEdit) {
        this.Item_combinationsListEdit = Item_combinationsListEdit;
    }
}
