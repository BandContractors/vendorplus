package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.UserDetail;
import entities.DiscountPackage;
import entities.GeneralSetting;
import entities.Transactor;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import utilities.CustomValidator;

@ManagedBean
@SessionScoped
public class DiscountPackageBean implements Serializable {

    private List<DiscountPackage> DiscountPackages;
    private String ActionMessage = null;
    private DiscountPackage SelectedDiscountPackage = null;
    private long SelectedDiscountPackageId;
    private String SearchPackageName = "";

    public void setDiscountPackageFromResultset(DiscountPackage aDiscountPackage, ResultSet aResultSet) {
        try {
            try {
                aDiscountPackage.setDiscountPackageId(aResultSet.getInt("discount_package_id"));
            } catch (NullPointerException npe) {
                aDiscountPackage.setDiscountPackageId(0);
            }
            try {
                aDiscountPackage.setPackageName(aResultSet.getString("package_name"));
            } catch (NullPointerException npe) {
                aDiscountPackage.setPackageName("");
            }
            try {
                aDiscountPackage.setStartDate(new Date(aResultSet.getTimestamp("start_date").getTime()));
            } catch (NullPointerException npe) {
                aDiscountPackage.setStartDate(null);
            }
            try {
                aDiscountPackage.setEndDate(new Date(aResultSet.getTimestamp("end_date").getTime()));
            } catch (NullPointerException npe) {
                aDiscountPackage.setEndDate(null);
            }
            try {
                aDiscountPackage.setStore_scope(aResultSet.getString("store_scope"));
            } catch (NullPointerException npe) {
                aDiscountPackage.setStore_scope("");
            }
            try {
                aDiscountPackage.setTransactor_scope(aResultSet.getString("transactor_scope"));
            } catch (NullPointerException npe) {
                aDiscountPackage.setTransactor_scope("");
            }
        } catch (SQLException se) {
            System.err.println("setDiscountPackageFromResultset:" + se.getMessage());
        }
    }

    public String getStoreIdsStrFromList(DiscountPackage aDiscountPackage) {
        String StoreIdsSrt = "";
        try {
            if (null == aDiscountPackage) {
                StoreIdsSrt = "";
            } else {
                for (int i = 0; i < aDiscountPackage.getSelectedStores().length; i++) {
                    if (StoreIdsSrt.length() == 0) {
                        StoreIdsSrt = aDiscountPackage.getSelectedStores()[i];
                    } else {
                        StoreIdsSrt = StoreIdsSrt + "," + aDiscountPackage.getSelectedStores()[i];
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getStoreIdsStrFromList:" + e.getMessage());
        }
        return StoreIdsSrt;
    }

    public String[] getStoreListFromIdsStr(DiscountPackage aDiscountPackage) {
        String[] StoreList = null;
        try {
            if (null == aDiscountPackage) {
                //StoreList
            } else {
                if (aDiscountPackage.getStore_scope().length() > 0) {
                    StoreList = aDiscountPackage.getStore_scope().split(",");
                }
            }
        } catch (Exception e) {
            System.out.println("getStoreListFromIdsStr:" + e.getMessage());
        }
        return StoreList;
    }

    public String getTransactorIdsStrFromList(DiscountPackage aDiscountPackage) {
        String TransactorIdsSrt = "";
        try {
            if (null == aDiscountPackage) {
                TransactorIdsSrt = "";
            } else {
                for (int i = 0; i < aDiscountPackage.getSelectedTransactors().size(); i++) {
                    if (TransactorIdsSrt.length() == 0) {
                        TransactorIdsSrt = Long.toString(aDiscountPackage.getSelectedTransactors().get(i).getTransactorId());
                    } else {
                        TransactorIdsSrt = TransactorIdsSrt + "," + Long.toString(aDiscountPackage.getSelectedTransactors().get(i).getTransactorId());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getTransactorIdsStrFromList:" + e.getMessage());
        }
        return TransactorIdsSrt;
    }

    public List<Transactor> getTransactorListFromIdsStr(DiscountPackage aDiscountPackage) {
        List<Transactor> lst = new ArrayList<>();
        String[] array = null;
        try {
            if (null == aDiscountPackage) {
            } else {
                if (aDiscountPackage.getTransactor_scope().length() > 0) {
                    array = aDiscountPackage.getTransactor_scope().split(",");
                    for (int i = 0; i < array.length; i++) {
                        lst.add(new TransactorBean().getTransactor(Long.parseLong(array[i])));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getTransactorListFromIdsStr:" + e.getMessage());
        }
        return lst;
    }

    public void saveDiscountPackage(DiscountPackage discountPackage) {
        String sql = null;
        String msg = "";
        String sql2 = null;
        sql2 = "SELECT * FROM discount_package WHERE package_name='" + discountPackage.getPackageName() + "'";

        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (discountPackage.getDiscountPackageId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackage.getDiscountPackageId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().TextSize(discountPackage.getPackageName(), 1, 50).equals("FAIL")) {
            msg = "Enter Discount Package Name!";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if ((new CustomValidator().CheckRecords(sql2) > 0 && discountPackage.getDiscountPackageId() == 0) || (new CustomValidator().CheckRecords(sql2) > 0 && new CustomValidator().CheckRecords(sql2) != 1 && discountPackage.getDiscountPackageId() > 0)) {
            msg = "Discount Package Name already exists!";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (discountPackage.getStartDate() == null || discountPackage.getEndDate() == null) {
            msg = "PleasesSelect  valid discount start and end dates...!";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {

            if (discountPackage.getDiscountPackageId() == 0) {
                sql = "{call sp_insert_discount_package(?,?,?,?,?)}";
            } else if (discountPackage.getDiscountPackageId() > 0) {
                sql = "{call sp_update_discount_package(?,?,?,?,?,?)}";
            }
            discountPackage.setStore_scope(this.getStoreIdsStrFromList(discountPackage));
            discountPackage.setTransactor_scope(this.getTransactorIdsStrFromList(discountPackage));
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (discountPackage.getDiscountPackageId() == 0) {
                    cs.setString("in_package_name", discountPackage.getPackageName());
                    cs.setTimestamp("in_start_date", new java.sql.Timestamp(discountPackage.getStartDate().getTime()));
                    cs.setTimestamp("in_end_date", new java.sql.Timestamp(discountPackage.getEndDate().getTime()));
                    cs.setString("in_store_scope", discountPackage.getStore_scope());
                    cs.setString("in_transactor_scope", discountPackage.getTransactor_scope());
                    cs.executeUpdate();
                    this.setActionMessage("Saved Successfully");
                    this.clearDiscountPackage(discountPackage);
                } else if (discountPackage.getDiscountPackageId() > 0) {
                    cs.setLong("in_discount_package_id", discountPackage.getDiscountPackageId());
                    cs.setString("in_package_name", discountPackage.getPackageName());
                    cs.setTimestamp("in_start_date", new java.sql.Timestamp(discountPackage.getStartDate().getTime()));
                    cs.setTimestamp("in_end_date", new java.sql.Timestamp(discountPackage.getEndDate().getTime()));
                    cs.setString("in_store_scope", discountPackage.getStore_scope());
                    cs.setString("in_transactor_scope", discountPackage.getTransactor_scope());
                    cs.executeUpdate();
                    this.setActionMessage("Updated Successfully");
                    this.clearDiscountPackage(discountPackage);
                }
                this.refreshList(this.SearchPackageName);
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("DiscountPackage NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("DiscountPackage NOT saved!"));
            }
        }
    }

    public DiscountPackage getDiscountPackage(int DiscountPackageId) {
        String sql = "{call sp_search_discount_package_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, DiscountPackageId);
            rs = ps.executeQuery();
            if (rs.next()) {
                DiscountPackage discountPackage = new DiscountPackage();
                this.setDiscountPackageFromResultset(discountPackage, rs);
                return discountPackage;
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

    public void deleteDiscountPackage(DiscountPackage discountPackage) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();
        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            new DiscountPackageItemBean().deleteDiscountPackageItems(discountPackage.getDiscountPackageId());
            String sql = "DELETE FROM discount_package WHERE discount_package_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, discountPackage.getDiscountPackageId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.clearDiscountPackage(discountPackage);
                this.refreshList(this.SearchPackageName);
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("DiscountPackage NOT deleted");
            }
        }
    }

    public void displayDiscountPackage(DiscountPackage DiscountPackageFrom, DiscountPackage DiscountPackageTo) {
        DiscountPackageTo.setDiscountPackageId(DiscountPackageFrom.getDiscountPackageId());
        DiscountPackageTo.setPackageName(DiscountPackageFrom.getPackageName());
        DiscountPackageTo.setStartDate(DiscountPackageFrom.getStartDate());
        DiscountPackageTo.setEndDate(DiscountPackageFrom.getEndDate());
        DiscountPackageTo.setStore_scope(DiscountPackageFrom.getStore_scope());
        DiscountPackageTo.setTransactor_scope(DiscountPackageFrom.getTransactor_scope());
        DiscountPackageTo.setSelectedStores(this.getStoreListFromIdsStr(DiscountPackageFrom));
        DiscountPackageTo.setSelectedTransactors(this.getTransactorListFromIdsStr(DiscountPackageFrom));
    }

    public void clearDiscountPackage(DiscountPackage discountPackage) {
        if (discountPackage != null) {
            discountPackage.setDiscountPackageId(0);
            discountPackage.setPackageName("");
            discountPackage.setStartDate(null);
            discountPackage.setEndDate(null);
            discountPackage.setStore_scope("");
            discountPackage.setTransactor_scope("");
            discountPackage.setSelectedStores(null);
            discountPackage.setSelectedTransactors(null);
        }
    }

    public void clearSelectedDiscountPackage() {
        this.clearDiscountPackage(this.getSelectedDiscountPackage());
    }

    public void refreshList(String aText) {
        String sql = "SELECT * FROM discount_package ORDER BY package_name ASC";
        if (aText.length() > 0) {
            sql = "SELECT * FROM discount_package WHERE package_name LIKE '%" + aText + "%' ORDER BY package_name ASC";
        }
        ResultSet rs = null;
        this.setDiscountPackages(new ArrayList<>());
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                DiscountPackage discountPackage = new DiscountPackage();
                this.setDiscountPackageFromResultset(discountPackage, rs);
                discountPackage.setStatusColor(new GeneralSetting().getStyleColorByDaysFromNow("DISCOUNT-EXPIRY-DATE",discountPackage.getEndDate()));
                this.getDiscountPackages().add(discountPackage);
            }
        } catch (Exception e) {
            System.err.println("refreshList:" + e.getMessage());
        }
    }

    /**
     * @param DiscountPackages the DiscountPackages to set
     */
    public void setDiscountPackages(List<DiscountPackage> DiscountPackages) {
        this.DiscountPackages = DiscountPackages;
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
     * @return the SelectedDiscountPackageId
     */
    public long getSelectedDiscountPackageId() {
        return SelectedDiscountPackageId;
    }

    /**
     * @param SelectedDiscountPackageId the SelectedDiscountPackageId to set
     */
    public void setSelectedDiscountPackageId(long SelectedDiscountPackageId) {
        this.SelectedDiscountPackageId = SelectedDiscountPackageId;
    }

    /**
     * @return the SearchPackageName
     */
    public String getSearchPackageName() {
        return SearchPackageName;
    }

    /**
     * @param SearchPackageName the SearchPackageName to set
     */
    public void setSearchPackageName(String SearchPackageName) {
        this.SearchPackageName = SearchPackageName;
    }

    /**
     * @return the SelectedDiscountPackage
     */
    public DiscountPackage getSelectedDiscountPackage() {
        return SelectedDiscountPackage;
    }

    /**
     * @param SelectedDiscountPackage the SelectedDiscountPackage to set
     */
    public void setSelectedDiscountPackage(DiscountPackage SelectedDiscountPackage) {
        this.SelectedDiscountPackage = SelectedDiscountPackage;
    }

    /**
     * @return the DiscountPackages
     */
    public List<DiscountPackage> getDiscountPackages() {
        return DiscountPackages;
    }
}
