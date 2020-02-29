package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.UserDetail;
import entities.DiscountPackageItem;
import entities.Item;
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
public class DiscountPackageItemBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DiscountPackageItem> DiscountPackageItems;
    private String ActionMessage = null;
    DiscountPackageItem SelectedDiscountPackageItem = null;

    public void saveDiscountPackageItem(DiscountPackageItem discountPackageItem) {
        String sql = null;
        String sql2 = null;
        String msg = "";

        sql2 = "SELECT * FROM discount_package_item WHERE store_id=" + discountPackageItem.getStoreId() + " AND item_id=" + discountPackageItem.getItemId() + " AND item_qty=" + discountPackageItem.getItemQty();

        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (discountPackageItem.getDiscountPackageItemId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getDiscountPackageItemId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getDiscountPackageId() == 0) {
            msg = "Select discount package...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getStoreId() == 0) {
            msg = "Select store...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getItemId() == 0) {
            msg = "Select item to add to the package...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getItemQty() <= 0) {
            msg = "Specify Quanity please...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getWholesaleDiscountAmt() <= 0 && discountPackageItem.getRetailsaleDiscountAmt() <= 0) {
            msg = "EITHER Retailsale-Discount-Amount OR Wholesale-Discount-Amount is not selected...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if ((new CustomValidator().CheckRecords(sql2) > 0 && discountPackageItem.getDiscountPackageItemId() == 0) || (new CustomValidator().CheckRecords(sql2) > 0 && new CustomValidator().CheckRecords(sql2) != 1 && discountPackageItem.getDiscountPackageItemId() > 0)) {
            msg = "Quantity for this item already exists in the store's discount package...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getRetailsaleDiscountAmt() > new ItemBean().findItem(discountPackageItem.getItemId()).getUnitRetailsalePrice() || discountPackageItem.getWholesaleDiscountAmt() > new ItemBean().findItem(discountPackageItem.getItemId()).getUnitWholesalePrice()) {
            msg = "Discount amount cannot exceed the set retail/wholesale unit price for the item...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            if (discountPackageItem.getDiscountPackageItemId() == 0) {
                sql = "{call sp_insert_discount_package_item(?,?,?,?,?,?,?)}";
            } else if (discountPackageItem.getDiscountPackageItemId() > 0) {
                sql = "{call sp_update_discount_package_item(?,?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (discountPackageItem.getDiscountPackageItemId() == 0) {
                    cs.setInt("in_discount_package_id", discountPackageItem.getDiscountPackageId());
                    cs.setInt("in_store_id", discountPackageItem.getStoreId());
                    cs.setLong("in_item_id", discountPackageItem.getItemId());
                    cs.setDouble("in_item_qty", discountPackageItem.getItemQty());
                    cs.setDouble("in_wholesale_discount_amt", discountPackageItem.getWholesaleDiscountAmt());
                    cs.setDouble("in_retailsale_discount_amt", discountPackageItem.getRetailsaleDiscountAmt());
                    cs.setDouble("in_hire_price_discount_amt", discountPackageItem.getHire_price_discount_amt());
                    cs.executeUpdate();
                    this.clearDiscountPackageItem(discountPackageItem);
                    this.setActionMessage("Saved Successfully");

                } else if (discountPackageItem.getDiscountPackageItemId() > 0) {
                    cs.setLong("in_discount_package_item_id", discountPackageItem.getDiscountPackageItemId());
                    cs.setInt("in_discount_package_id", discountPackageItem.getDiscountPackageId());
                    cs.setInt("in_store_id", discountPackageItem.getStoreId());
                    cs.setLong("in_item_id", discountPackageItem.getItemId());
                    cs.setDouble("in_item_qty", discountPackageItem.getItemQty());
                    cs.setDouble("in_wholesale_discount_amt", discountPackageItem.getWholesaleDiscountAmt());
                    cs.setDouble("in_retailsale_discount_amt", discountPackageItem.getRetailsaleDiscountAmt());
                    cs.setDouble("in_hire_price_discount_amt", discountPackageItem.getHire_price_discount_amt());
                    cs.executeUpdate();
                    this.setActionMessage("Saved Successfully");
                    this.clearDiscountPackageItem(discountPackageItem);
                }
            } catch (SQLException se) {
                System.err.println("saveDiscountPackageItem:" + se.getMessage());
                this.setActionMessage("DiscountPackageItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("DiscountPackageItem NOT saved!"));
            }
        }
    }

    public void saveDiscountPackageItem(DiscountPackageItem discountPackageItem, Item aItem) {
        String sql = null;
        String sql2 = null;
        String msg = "";

        sql2 = "SELECT * FROM discount_package_item WHERE store_id=" + discountPackageItem.getStoreId() + " AND item_id=" + discountPackageItem.getItemId() + " AND item_qty=" + discountPackageItem.getItemQty();

        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (discountPackageItem.getDiscountPackageItemId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getDiscountPackageItemId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getDiscountPackageId() == 0) {
            msg = "Select discount package...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getStoreId() == 0) {
            msg = "Select store...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getItemId() == 0) {
            msg = "Select item to add to the package...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getItemQty() <= 0) {
            msg = "Specify Quanity please...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getWholesaleDiscountAmt() <= 0 && discountPackageItem.getRetailsaleDiscountAmt() <= 0) {
            msg = "EITHER Retailsale-Discount-Percent OR Wholesale-Discount-Percent is not selected...";
            this.setActionMessage("DiscountPackageItem NOT saved");
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if ((new CustomValidator().CheckRecords(sql2) > 0 && discountPackageItem.getDiscountPackageItemId() == 0) || (new CustomValidator().CheckRecords(sql2) > 0 && new CustomValidator().CheckRecords(sql2) != 1 && discountPackageItem.getDiscountPackageItemId() > 0)) {
            msg = "Quantity for this item already exists in the store's discount package...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackageItem.getRetailsaleDiscountAmt() > 100 || discountPackageItem.getWholesaleDiscountAmt() > 100) {
            msg = "Discount percent cannot exceed 100%...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            if (discountPackageItem.getDiscountPackageItemId() == 0) {
                sql = "{call sp_insert_discount_package_item(?,?,?,?,?,?,?)}";
            } else if (discountPackageItem.getDiscountPackageItemId() > 0) {
                sql = "{call sp_update_discount_package_item(?,?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (discountPackageItem.getDiscountPackageItemId() == 0) {
                    cs.setInt("in_discount_package_id", discountPackageItem.getDiscountPackageId());
                    cs.setInt("in_store_id", discountPackageItem.getStoreId());
                    cs.setLong("in_item_id", discountPackageItem.getItemId());
                    cs.setDouble("in_item_qty", discountPackageItem.getItemQty());
                    cs.setDouble("in_wholesale_discount_amt", discountPackageItem.getWholesaleDiscountAmt());
                    cs.setDouble("in_retailsale_discount_amt", discountPackageItem.getRetailsaleDiscountAmt());
                    cs.setDouble("in_hire_price_discount_amt", discountPackageItem.getHire_price_discount_amt());
                    cs.executeUpdate();
                    this.clearDiscountPackageItem(discountPackageItem);
                    this.setActionMessage("Saved Successfully");
                    this.clearDiscountPackageItem(discountPackageItem, aItem);
                } else if (discountPackageItem.getDiscountPackageItemId() > 0) {
                    cs.setLong("in_discount_package_item_id", discountPackageItem.getDiscountPackageItemId());
                    cs.setInt("in_discount_package_id", discountPackageItem.getDiscountPackageId());
                    cs.setInt("in_store_id", discountPackageItem.getStoreId());
                    cs.setLong("in_item_id", discountPackageItem.getItemId());
                    cs.setDouble("in_item_qty", discountPackageItem.getItemQty());
                    cs.setDouble("in_wholesale_discount_amt", discountPackageItem.getWholesaleDiscountAmt());
                    cs.setDouble("in_retailsale_discount_amt", discountPackageItem.getRetailsaleDiscountAmt());
                    cs.setDouble("in_hire_price_discount_amt", discountPackageItem.getHire_price_discount_amt());
                    cs.executeUpdate();
                    this.setActionMessage("Saved Successfully");
                    this.clearDiscountPackageItem(discountPackageItem, aItem);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("DiscountPackageItem NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("DiscountPackageItem NOT saved!"));
            }
        }
    }

    public void setDiscountPackageItemFromResultset(DiscountPackageItem aDiscountPackageItem, ResultSet aResultSet) {
        try {
            try {
                aDiscountPackageItem.setDiscountPackageItemId(aResultSet.getLong("discount_package_item_id"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setDiscountPackageItemId(0);
            }
            try {
                aDiscountPackageItem.setDiscountPackageId(aResultSet.getInt("discount_package_id"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setDiscountPackageId(0);
            }
            try {
                aDiscountPackageItem.setStoreId(aResultSet.getInt("store_id"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setStoreId(0);
            }
            try {
                aDiscountPackageItem.setItemId(aResultSet.getLong("item_id"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setItemId(0);
            }
            try {
                aDiscountPackageItem.setItemQty(aResultSet.getDouble("item_qty"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setItemQty(0);
            }
            try {
                aDiscountPackageItem.setWholesaleDiscountAmt(aResultSet.getDouble("wholesale_discount_amt"));
            } catch (NullPointerException npe) {

            }
            try {
                aDiscountPackageItem.setRetailsaleDiscountAmt(aResultSet.getDouble("retailsale_discount_amt"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setRetailsaleDiscountAmt(0);
            }
            try {
                aDiscountPackageItem.setHire_price_discount_amt(aResultSet.getDouble("hire_price_discount_amt"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setHire_price_discount_amt(0);
            }
        } catch (SQLException se) {
            System.err.println("setDiscountPackageItemFromResultset:" + se.getMessage());
        }
    }

    public DiscountPackageItem getDiscountPackageItem(long DiscPackItemId) {
        String sql = "{call sp_search_discount_package_item_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, DiscPackItemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                DiscountPackageItem discountPackageItem = new DiscountPackageItem();
                this.setDiscountPackageItemFromResultset(discountPackageItem, rs);
                return discountPackageItem;
            } else {
                return null;
            }
        } catch (SQLException se) {
            System.err.println("getDiscountPackageItem:" + se.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("getDiscountPackageItem:" + ex.getMessage());
                }
            }
        }

    }

    public void deleteDiscountPackageItem(DiscountPackageItem discountPackageItem) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            String sql = "DELETE FROM discount_package_item WHERE discount_package_item_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, discountPackageItem.getDiscountPackageItemId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.clearDiscountPackageItem(discountPackageItem);
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("DiscountPackageItem NOT deleted");
            }
        }
    }

    public void deleteDiscountPackageItems(long aDiscountPackageItemId) {
        String sql = "DELETE FROM discount_package_item WHERE discount_package_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setLong(1, aDiscountPackageItemId);
            ps.executeUpdate();
        } catch (SQLException se) {
            System.err.println("deleteDiscountPackageItems:" + se.getMessage());
        }
    }

    public void displayDiscountPackageItem(DiscountPackageItem DiscountPackageItemFrom, DiscountPackageItem DiscountPackageItemTo) {
        DiscountPackageItemTo.setDiscountPackageItemId(DiscountPackageItemFrom.getDiscountPackageItemId());
        DiscountPackageItemTo.setDiscountPackageId(DiscountPackageItemFrom.getDiscountPackageId());
        DiscountPackageItemTo.setStoreId(DiscountPackageItemFrom.getStoreId());
        DiscountPackageItemTo.setItemId(DiscountPackageItemFrom.getItemId());
        DiscountPackageItemTo.setItemQty(DiscountPackageItemFrom.getItemQty());
        DiscountPackageItemTo.setWholesaleDiscountAmt(DiscountPackageItemFrom.getWholesaleDiscountAmt());
        DiscountPackageItemTo.setRetailsaleDiscountAmt(DiscountPackageItemFrom.getRetailsaleDiscountAmt());
        DiscountPackageItemTo.setHire_price_discount_amt(DiscountPackageItemFrom.getHire_price_discount_amt());
    }

    public void clearDiscountPackageItem(DiscountPackageItem discountPackageItem) {
        //discountPackageItem.setDiscountPackageItemId(0);
        //discountPackageItem.setDiscountPackageId(0);
        //discountPackageItem.setStoreId(0);
        discountPackageItem.setItemId(0);
        //discountPackageItem.setItemQty(1);
        discountPackageItem.setWholesaleDiscountAmt(0);
        discountPackageItem.setRetailsaleDiscountAmt(0);
        discountPackageItem.setHire_price_discount_amt(0);
    }

    public void clearDiscountPackageItem(DiscountPackageItem aDiscountPackageItem, Item aItem) {
        new ItemBean().clearItem(aItem);
        aDiscountPackageItem.setItemId(0);
        aDiscountPackageItem.setWholesaleDiscountAmt(0);
        aDiscountPackageItem.setRetailsaleDiscountAmt(0);
        aDiscountPackageItem.setHire_price_discount_amt(0);
    }

    public void initClearDiscountPackageItem(DiscountPackageItem aDiscountPackageItem, Item aItem) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.clearDiscountPackageItem(aDiscountPackageItem, aItem);
        }
    }
    
    public List<DiscountPackageItem> getDiscountPackageItems(int PacId, int StrId, long ItmId) {//1.ByPackage, 2.ByPackageStore,3. ByStoreItem
        String sql = "";
        if (PacId != 0 && StrId == 0 && ItmId == 0) {//1.ByPackage
            sql = "{call sp_search_discount_package_item_by_package_id(?)}";
        } else if (PacId != 0 && StrId != 0 && ItmId == 0) {//2.ByPackageStore
            sql = "{call sp_search_discount_package_item_by_package_store(?,?)}";
        } else if (StrId != 0 && ItmId != 0) {//3. ByStoreItem
            sql = "{call sp_search_discount_package_item_by_store_item(?,?)}";
        } else if (PacId == 0 && StrId != 0 && ItmId == 0) {//2.ByPackageStore
            sql = "{call sp_search_discount_package_item_by_store_id(?)}";
        } else {
            //sql = "{call sp_search_discount_package_item_by_null()}";
        }
        ResultSet rs = null;
        DiscountPackageItems = new ArrayList<DiscountPackageItem>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            if (PacId != 0 && StrId == 0 && ItmId == 0) {//1.ByPackage
                ps.setInt(1, PacId);
            } else if (PacId != 0 && StrId != 0 && ItmId == 0) {//2.ByPackageStore
                ps.setInt(1, PacId);
                ps.setInt(2, StrId);
            } else if (StrId != 0 && ItmId != 0) {//3. ByStoreItem
                ps.setInt(1, StrId);
                ps.setLong(2, ItmId);
            } else if (PacId == 0 && StrId != 0 && ItmId == 0) {//2.ByStore
                ps.setInt(1, StrId);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                DiscountPackageItem discountPackageItem = new DiscountPackageItem();
                this.setDiscountPackageItemFromResultset(discountPackageItem, rs);
                DiscountPackageItems.add(discountPackageItem);
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
        return DiscountPackageItems;
    }

    public DiscountPackageItem getActiveDiscountPackageItem(int StoreI, long ItemI, double Qty) {
        String sql = "{call sp_search_discount_package_item_by_store_item_qty_active(?,?,?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, StoreI);
            ps.setLong(2, ItemI);
            ps.setDouble(3, Qty);
            rs = ps.executeQuery();
            if (rs.next()) {
                DiscountPackageItem discountPackageItem = new DiscountPackageItem();
                this.setDiscountPackageItemFromResultset(discountPackageItem, rs);
                return discountPackageItem;
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

    public double getActiveWholesaleDiscount(int StoreI, long ItemI, double Qty) {
        double DiscountAmount = 0;
        SelectedDiscountPackageItem = this.getActiveDiscountPackageItem(StoreI, ItemI, Qty);
        if (SelectedDiscountPackageItem == null) {
            DiscountAmount = 0;
            return 0;
        } else {
            DiscountAmount = SelectedDiscountPackageItem.getWholesaleDiscountAmt();
            SelectedDiscountPackageItem = null;
            return DiscountAmount;
        }
    }

    /**
     * @param DiscountPackageItems the DiscountPackageItems to set
     */
    public void setDiscountPackageItems(List<DiscountPackageItem> DiscountPackageItems) {
        this.DiscountPackageItems = DiscountPackageItems;
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
