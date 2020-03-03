package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.Category;
import entities.DiscountPackage;
import entities.GroupRight;
import entities.UserDetail;
import entities.DiscountPackageItem;
import entities.Item;
import entities.SubCategory;
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
    private List<DiscountPackageItem> DiscountPackageItemsList;

    public String getCategoryIdsStrFromList(DiscountPackageItem aDiscountPackageItem) {
        String CategoryIdsSrt = "";
        try {
            if (null == aDiscountPackageItem || null == aDiscountPackageItem.getSelectedCategories()) {
                CategoryIdsSrt = "";
            } else {
                for (int i = 0; i < aDiscountPackageItem.getSelectedCategories().size(); i++) {
                    int id = aDiscountPackageItem.getSelectedCategories().get(i).getCategoryId();
                    if (id > 0) {
                        if (CategoryIdsSrt.length() == 0) {
                            CategoryIdsSrt = Integer.toString(id);
                        } else {
                            CategoryIdsSrt = CategoryIdsSrt + "," + Integer.toString(id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getCategoryIdsStrFromList:" + e.getMessage());
        }
        return CategoryIdsSrt;
    }

    public String getSubCategoryIdsStrFromList(DiscountPackageItem aDiscountPackageItem) {
        String SubCategoryIdsSrt = "";
        try {
            if (null == aDiscountPackageItem || null == aDiscountPackageItem.getSelectedSubCategories()) {
                SubCategoryIdsSrt = "";
            } else {
                for (int i = 0; i < aDiscountPackageItem.getSelectedSubCategories().size(); i++) {
                    int id = aDiscountPackageItem.getSelectedSubCategories().get(i).getSubCategoryId();
                    if (id > 0) {
                        if (SubCategoryIdsSrt.length() == 0) {
                            SubCategoryIdsSrt = Integer.toString(id);
                        } else {
                            SubCategoryIdsSrt = SubCategoryIdsSrt + "," + Integer.toString(id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getSubCategoryIdsStrFromList:" + e.getMessage());
        }
        return SubCategoryIdsSrt;
    }

    public String getItemIdsStrFromList(DiscountPackageItem aDiscountPackageItem) {
        String ItemIdsSrt = "";
        try {
            if (null == aDiscountPackageItem || null == aDiscountPackageItem.getSelectedItems()) {
                ItemIdsSrt = "";
            } else {
                for (int i = 0; i < aDiscountPackageItem.getSelectedItems().size(); i++) {
                    long id = aDiscountPackageItem.getSelectedItems().get(i).getItemId();
                    if (id > 0) {
                        if (ItemIdsSrt.length() == 0) {
                            ItemIdsSrt = Long.toString(id);
                        } else {
                            ItemIdsSrt = ItemIdsSrt + "," + Long.toString(id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getItemIdsStrFromList:" + e.getMessage());
        }
        return ItemIdsSrt;
    }

    public List<Category> getCategoryListFromIdsStr(DiscountPackageItem aDiscountPackageItem) {
        List<Category> lst = new ArrayList<>();
        String[] array = null;
        try {
            if (null == aDiscountPackageItem) {
            } else {
                if (aDiscountPackageItem.getCategory_scope().length() > 0) {
                    array = aDiscountPackageItem.getCategory_scope().split(",");
                    for (int i = 0; i < array.length; i++) {
                        lst.add(new CategoryBean().getCategory(Integer.parseInt(array[i])));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getCategoryListFromIdsStr:" + e.getMessage());
        }
        return lst;
    }

    public List<SubCategory> getSubCategoryListFromIdsStr(DiscountPackageItem aDiscountPackageItem) {
        List<SubCategory> lst = new ArrayList<>();
        String[] array = null;
        try {
            if (null == aDiscountPackageItem) {
            } else {
                if (aDiscountPackageItem.getSub_category_scope().length() > 0) {
                    array = aDiscountPackageItem.getSub_category_scope().split(",");
                    for (int i = 0; i < array.length; i++) {
                        lst.add(new SubCategoryBean().getSubCategory(Integer.parseInt(array[i])));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getSubCategoryListFromIdsStr:" + e.getMessage());
        }
        return lst;
    }

    public List<Item> getItemListFromIdsStr(DiscountPackageItem aDiscountPackageItem) {
        List<Item> lst = new ArrayList<>();
        String[] array = null;
        try {
            if (null == aDiscountPackageItem) {
            } else {
                if (aDiscountPackageItem.getItem_scope().length() > 0) {
                    array = aDiscountPackageItem.getItem_scope().split(",");
                    for (int i = 0; i < array.length; i++) {
                        lst.add(new ItemBean().getItem(Long.parseLong(array[i])));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getItemListFromIdsStr:" + e.getMessage());
        }
        return lst;
    }

    public void saveDiscountPackageItem(DiscountPackageItem aDiscountPackageItem, DiscountPackage aDiscountPackage) {
        String sql = null;
        String msg = "";
        //update lookups
        try {
            aDiscountPackageItem.setDiscountPackageId(aDiscountPackage.getDiscountPackageId());
            aDiscountPackageItem.setCategory_scope(this.getCategoryIdsStrFromList(aDiscountPackageItem));
            aDiscountPackageItem.setSub_category_scope(this.getSubCategoryIdsStrFromList(aDiscountPackageItem));
            aDiscountPackageItem.setItem_scope(this.getItemIdsStrFromList(aDiscountPackageItem));
        } catch (Exception e) {
            //
        }
        if (aDiscountPackageItem.getDiscountPackageId() == 0) {
            msg = "Select package...";
            this.setActionMessage(msg);
        } else if (aDiscountPackageItem.getCategory_scope().length() == 0 && aDiscountPackageItem.getSub_category_scope().length() == 0 && aDiscountPackageItem.getItem_scope().length() == 0) {
            msg = "Select package items (category and/or subcategory and/or items)...";
            this.setActionMessage(msg);
        } else if (aDiscountPackageItem.getItemQty() <= 0) {
            msg = "Specify Quanity please...";
            this.setActionMessage(msg);
        } else if (aDiscountPackageItem.getWholesaleDiscountAmt() <= 0 && aDiscountPackageItem.getRetailsaleDiscountAmt() <= 0 && aDiscountPackageItem.getHire_price_discount_amt() <= 0) {
            msg = "Specify percentage discount...";
            this.setActionMessage(msg);
        } else if (aDiscountPackageItem.getRetailsaleDiscountAmt() > 100 || aDiscountPackageItem.getWholesaleDiscountAmt() > 100 || aDiscountPackageItem.getHire_price_discount_amt() > 100) {
            msg = "Discount percent cannot exceed 100%...";
            this.setActionMessage(msg);
        } else {
            if (aDiscountPackageItem.getDiscountPackageItemId() == 0) {
                sql = "{call sp_insert_discount_package_item(?,?,?,?,?,?,?,?,?,?)}";
            } else if (aDiscountPackageItem.getDiscountPackageItemId() > 0) {
                sql = "{call sp_update_discount_package_item(?,?,?,?,?,?,?,?,?,?,?)}";
            }
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (aDiscountPackageItem.getDiscountPackageItemId() == 0) {
                    cs.setInt("in_discount_package_id", aDiscountPackageItem.getDiscountPackageId());
                    cs.setInt("in_store_id", aDiscountPackageItem.getStoreId());
                    cs.setLong("in_item_id", aDiscountPackageItem.getItemId());
                    cs.setDouble("in_item_qty", aDiscountPackageItem.getItemQty());
                    cs.setDouble("in_wholesale_discount_amt", aDiscountPackageItem.getWholesaleDiscountAmt());
                    cs.setDouble("in_retailsale_discount_amt", aDiscountPackageItem.getRetailsaleDiscountAmt());
                    cs.setDouble("in_hire_price_discount_amt", aDiscountPackageItem.getHire_price_discount_amt());
                    cs.setString("in_category_scope", aDiscountPackageItem.getCategory_scope());
                    cs.setString("in_sub_category_scope", aDiscountPackageItem.getSub_category_scope());
                    cs.setString("in_item_scope", aDiscountPackageItem.getItem_scope());
                    cs.executeUpdate();
                    this.refreshDiscountPackageItems(aDiscountPackageItem.getDiscountPackageId());
                    this.clearDiscountPackageItem(aDiscountPackageItem);
                    this.setActionMessage("Saved Successfully");
                } else if (aDiscountPackageItem.getDiscountPackageItemId() > 0) {
                    cs.setLong("in_discount_package_item_id", aDiscountPackageItem.getDiscountPackageItemId());
                    cs.setInt("in_discount_package_id", aDiscountPackageItem.getDiscountPackageId());
                    cs.setInt("in_store_id", aDiscountPackageItem.getStoreId());
                    cs.setLong("in_item_id", aDiscountPackageItem.getItemId());
                    cs.setDouble("in_item_qty", aDiscountPackageItem.getItemQty());
                    cs.setDouble("in_wholesale_discount_amt", aDiscountPackageItem.getWholesaleDiscountAmt());
                    cs.setDouble("in_retailsale_discount_amt", aDiscountPackageItem.getRetailsaleDiscountAmt());
                    cs.setDouble("in_hire_price_discount_amt", aDiscountPackageItem.getHire_price_discount_amt());
                    cs.setString("in_category_scope", aDiscountPackageItem.getCategory_scope());
                    cs.setString("in_sub_category_scope", aDiscountPackageItem.getSub_category_scope());
                    cs.setString("in_item_scope", aDiscountPackageItem.getItem_scope());
                    cs.executeUpdate();
                    this.refreshDiscountPackageItems(aDiscountPackageItem.getDiscountPackageId());
                    this.clearDiscountPackageItem(aDiscountPackageItem);
                    this.setActionMessage("Saved Successfully");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                this.setActionMessage("DiscountPackageItem NOT saved");
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
            try {
                aDiscountPackageItem.setCategory_scope(aResultSet.getString("category_scope"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setCategory_scope("");
            }
            try {
                aDiscountPackageItem.setSub_category_scope(aResultSet.getString("sub_category_scope"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setSub_category_scope("");
            }
            try {
                aDiscountPackageItem.setItem_scope(aResultSet.getString("item_scope"));
            } catch (NullPointerException npe) {
                aDiscountPackageItem.setItem_scope("");
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
        if (null == discountPackageItem || discountPackageItem.getDiscountPackageItemId() == 0) {
            //
        } else {
            String sql = "DELETE FROM discount_package_item WHERE discount_package_item_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setLong(1, discountPackageItem.getDiscountPackageItemId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.refreshDiscountPackageItems(discountPackageItem.getDiscountPackageId());
                this.clearDiscountPackageItem(discountPackageItem);
            } catch (Exception e) {
                System.err.println("deleteDiscountPackageItem:" + e.getMessage());
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
        DiscountPackageItemTo.setStoreId(0);
        DiscountPackageItemTo.setItemId(0);
        DiscountPackageItemTo.setItemQty(DiscountPackageItemFrom.getItemQty());
        DiscountPackageItemTo.setWholesaleDiscountAmt(DiscountPackageItemFrom.getWholesaleDiscountAmt());
        DiscountPackageItemTo.setRetailsaleDiscountAmt(DiscountPackageItemFrom.getRetailsaleDiscountAmt());
        DiscountPackageItemTo.setHire_price_discount_amt(DiscountPackageItemFrom.getHire_price_discount_amt());
        DiscountPackageItemTo.setCategory_scope(DiscountPackageItemFrom.getCategory_scope());
        DiscountPackageItemTo.setSub_category_scope(DiscountPackageItemFrom.getSub_category_scope());
        DiscountPackageItemTo.setItem_scope(DiscountPackageItemFrom.getItem_scope());
        DiscountPackageItemTo.setSelectedCategories(this.getCategoryListFromIdsStr(DiscountPackageItemFrom));
        DiscountPackageItemTo.setSelectedSubCategories(this.getSubCategoryListFromIdsStr(DiscountPackageItemFrom));
        DiscountPackageItemTo.setSelectedItems(this.getItemListFromIdsStr(DiscountPackageItemFrom));
    }

    public void clearDiscountPackageItem(DiscountPackageItem aDiscountPackageItem) {
        if (null != aDiscountPackageItem) {
            aDiscountPackageItem.setDiscountPackageItemId(0);
            aDiscountPackageItem.setDiscountPackageId(0);
            aDiscountPackageItem.setItemId(0);
            aDiscountPackageItem.setStoreId(0);
            aDiscountPackageItem.setWholesaleDiscountAmt(0);
            aDiscountPackageItem.setRetailsaleDiscountAmt(0);
            aDiscountPackageItem.setHire_price_discount_amt(0);
            aDiscountPackageItem.setCategory_scope("");
            aDiscountPackageItem.setSub_category_scope("");
            aDiscountPackageItem.setItem_scope("");
            try {
                aDiscountPackageItem.getSelectedCategories().clear();
            } catch (Exception e) {
                //
            }
            try {
                aDiscountPackageItem.getSelectedSubCategories().clear();
            } catch (Exception e) {
                //
            }
            try {
                aDiscountPackageItem.getSelectedItems().clear();
            } catch (Exception e) {
                //
            }
        }
    }

    public void initClearDiscountPackageItem(DiscountPackageItem aDiscountPackageItem) {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            this.clearDiscountPackageItem(aDiscountPackageItem);
        }
    }

    public void refreshDiscountPackageItems(int aPackageId) {
        String sql = "SELECT * FROM discount_package_item WHERE discount_package_id=" + aPackageId;
        ResultSet rs = null;
        this.DiscountPackageItemsList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                DiscountPackageItem discountPackageItem = new DiscountPackageItem();
                this.setDiscountPackageItemFromResultset(discountPackageItem, rs);
                this.DiscountPackageItemsList.add(discountPackageItem);
            }
        } catch (Exception e) {
            System.err.println("refreshDiscountPackageItems:" + e.getMessage());
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

    /**
     * @return the DiscountPackageItemsList
     */
    public List<DiscountPackageItem> getDiscountPackageItemsList() {
        return DiscountPackageItemsList;
    }

    /**
     * @param DiscountPackageItemsList the DiscountPackageItemsList to set
     */
    public void setDiscountPackageItemsList(List<DiscountPackageItem> DiscountPackageItemsList) {
        this.DiscountPackageItemsList = DiscountPackageItemsList;
    }
}
