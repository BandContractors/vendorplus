package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Contact_list;
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
import sessions.GeneralUserSetting;
import utilities.CustomValidator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean(name = "contact_listBean")
@SessionScoped
public class Contact_listBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ActionMessage = null;
    private List<Contact_list> Contact_listList = new ArrayList<>();
    private Contact_list Contact_listObj = new Contact_list();
    private int CountList;
    private String SearchContactNames = "";
    private List<Contact_list> Contact_lists;

    public void setContact_listFromResultset(Contact_list aContact_list, ResultSet aResultSet) {
        try {
            try {
                aContact_list.setContact_list_id(aResultSet.getInt("contact_list_id"));
            } catch (NullPointerException npe) {
                aContact_list.setContact_list_id(0);
            }
            try {
                aContact_list.setCategory(aResultSet.getString("category"));
            } catch (NullPointerException npe) {
                aContact_list.setCategory("");
            }
            try {
                aContact_list.setSubcategory(aResultSet.getString("subcategory"));
            } catch (NullPointerException npe) {
                aContact_list.setSubcategory("");
            }
            try {
                aContact_list.setEmail1(aResultSet.getString("email1"));
            } catch (NullPointerException npe) {
                aContact_list.setEmail1("");
            }
            try {
                aContact_list.setEmail2(aResultSet.getString("email2"));
            } catch (NullPointerException npe) {
                aContact_list.setEmail2("");
            }
            try {
                aContact_list.setCompany_name(aResultSet.getString("company_name"));
            } catch (NullPointerException npe) {
                aContact_list.setCompany_name("");
            }
            try {
                aContact_list.setTitle(aResultSet.getString("title"));
            } catch (NullPointerException npe) {
                aContact_list.setTitle("");
            }
            try {
                aContact_list.setFirst_name(aResultSet.getString("first_name"));
            } catch (NullPointerException npe) {
                aContact_list.setFirst_name("");
            }
            try {
                aContact_list.setSecond_name(aResultSet.getString("second_name"));
            } catch (NullPointerException npe) {
                aContact_list.setSecond_name("");
            }
            try {
                aContact_list.setPhone1(aResultSet.getString("phone1"));
            } catch (NullPointerException npe) {
                aContact_list.setPhone1("");
            }
            try {
                aContact_list.setPhone2(aResultSet.getString("phone2"));
            } catch (NullPointerException npe) {
                aContact_list.setPhone2("");
            }
            try {
                aContact_list.setSource(aResultSet.getString("source"));
            } catch (NullPointerException npe) {
                aContact_list.setSource("");
            }
            try {
                aContact_list.setType(aResultSet.getString("type"));
            } catch (NullPointerException npe) {
                aContact_list.setType("");
            }
            try {
                aContact_list.setLoc_level1(aResultSet.getString("loc_level_1"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level1("");
            }
            try {
                aContact_list.setLoc_level2(aResultSet.getString("loc_level_2"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level2("");
            }
            try {
                aContact_list.setLoc_level3(aResultSet.getString("loc_level_3"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level3("");
            }
            try {
                aContact_list.setLoc_level4(aResultSet.getString("loc_level_4"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level4("");
            }
            try {
                aContact_list.setAddress(aResultSet.getString("address"));
            } catch (NullPointerException npe) {
                aContact_list.setAddress("");
            }
        } catch (SQLException se) {
            System.err.println("setContact_listFromResultset:" + se.getMessage());
        }
    }

    public Contact_list getContact_listFromResultSet(ResultSet aResultSet) {
        try {
            Contact_list aContact_list = new Contact_list();
            try {
                aContact_list.setContact_list_id(aResultSet.getInt("contact_list_id"));
            } catch (NullPointerException npe) {
                aContact_list.setContact_list_id(0);
            }
            try {
                aContact_list.setCategory(aResultSet.getString("category"));
            } catch (NullPointerException npe) {
                aContact_list.setCategory("");
            }
            try {
                aContact_list.setSubcategory(aResultSet.getString("subcategory"));
            } catch (NullPointerException npe) {
                aContact_list.setSubcategory("");
            }
            try {
                aContact_list.setEmail1(aResultSet.getString("email1"));
            } catch (NullPointerException npe) {
                aContact_list.setEmail1("");
            }
            try {
                aContact_list.setEmail2(aResultSet.getString("email2"));
            } catch (NullPointerException npe) {
                aContact_list.setEmail2("");
            }
            try {
                aContact_list.setCompany_name(aResultSet.getString("company_name"));
            } catch (NullPointerException npe) {
                aContact_list.setCompany_name("");
            }
            try {
                aContact_list.setTitle(aResultSet.getString("title"));
            } catch (NullPointerException npe) {
                aContact_list.setTitle("");
            }
            try {
                aContact_list.setFirst_name(aResultSet.getString("first_name"));
            } catch (NullPointerException npe) {
                aContact_list.setFirst_name("");
            }
            try {
                aContact_list.setSecond_name(aResultSet.getString("second_name"));
            } catch (NullPointerException npe) {
                aContact_list.setSecond_name("");
            }
            try {
                aContact_list.setPhone1(aResultSet.getString("phone1"));
            } catch (NullPointerException npe) {
                aContact_list.setPhone1("");
            }
            try {
                aContact_list.setPhone2(aResultSet.getString("phone2"));
            } catch (NullPointerException npe) {
                aContact_list.setPhone2("");
            }
            try {
                aContact_list.setSource(aResultSet.getString("source"));
            } catch (NullPointerException npe) {
                aContact_list.setSource("");
            }
            try {
                aContact_list.setType(aResultSet.getString("type"));
            } catch (NullPointerException npe) {
                aContact_list.setType("");
            }
            try {
                aContact_list.setLoc_level1(aResultSet.getString("loc_level_1"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level1("");
            }
            try {
                aContact_list.setLoc_level2(aResultSet.getString("loc_level_2"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level2("");
            }
            try {
                aContact_list.setLoc_level3(aResultSet.getString("loc_level_3"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level3("");
            }
            try {
                aContact_list.setLoc_level4(aResultSet.getString("loc_leve1_4"));
            } catch (NullPointerException npe) {
                aContact_list.setLoc_level4("");
            }
            try {
                aContact_list.setAddress(aResultSet.getString("address"));
            } catch (NullPointerException npe) {
                aContact_list.setAddress("");
            }
            return aContact_list;
        } catch (SQLException se) {
            System.out.println("getContact_listFromResultSet:" + se.getMessage());
            return null;
        }
    }

    public Contact_list findContact_list(int Contact_listId) {
        String sql = "{call sp_search_ontact_list_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, Contact_listId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return this.getContact_listFromResultSet(rs);
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

    public void initContact_listObj() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else if (null == this.Contact_listObj) {
            this.Contact_listObj = new Contact_list();
        }
    }

    public String validateContact_list(Contact_list aContact_list) {
        String sql = null;
        String msg = "";
        String sql2 = null;
        String sql3 = null;
        sql2 = "SELECT * FROM contact_list WHERE company_name='" + aContact_list.getCompany_name() + "'";
        sql3 = "SELECT * FROM contact_list WHERE company_name='" + aContact_list.getCompany_name() + "' AND contact_list_id!=" + aContact_list.getContact_list_id();
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (aContact_list.getContact_list_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "CUSTOMER", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (aContact_list.getContact_list_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "CUSTOMER", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
        } else if (new CustomValidator().TextSize(aContact_list.getCompany_name(), 1, 20).equals("FAIL")) {
            msg = "Contact_list Type MUST be specified and cannot exceed 20 characters";
        } else if (new CustomValidator().TextSize(aContact_list.getCategory(), 1, 20).equals("FAIL")) {
            msg = "Contact_list Category MUST be specified and cannot exceed 20 characters";
        } else if (new CustomValidator().TextSize(aContact_list.getType(), 1, 20).equals("FAIL")) {
            msg = "Contact Type MUST be between be specified and cannot exceed 20 characters";
        } else if (new CustomValidator().TextSize(aContact_list.getEmail1(), 1, 20).equals("FAIL")) {
            msg = "Email field MUST be specified and cannot exceed 20 characters";
        } else if ((new CustomValidator().CheckRecords(sql2) > 0 && aContact_list.getContact_list_id() == 0) || (new CustomValidator().CheckRecords(sql3) > 0 && aContact_list.getContact_list_id() > 0)) {
            msg = "Company Name already exists, please enter different name(s) !";
        }
        return msg;
    }

    public void saveContact_list(Contact_list aContact_list) {
        String sql = null;
        String msg = "";
        GroupRightBean grb = new GroupRightBean();
        String ValidationMsg = this.validateContact_list(aContact_list);

        if (ValidationMsg.length() > 0) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ValidationMsg));
        } else {
            try {
                long status = 0;
                try {
                    status = this.insertUpdateContact_list(aContact_list);
                } catch (NullPointerException npe) {
                    status = 0;
                }
                if (status > 0) {
                    this.setActionMessage("Saved Successfully");
                    this.clearContact_list(aContact_list);
                } else {
                    this.setActionMessage("Contact NOT saved");
                }
            } catch (Exception se) {
                System.err.println(se.getMessage());
                se.printStackTrace();
                this.setActionMessage("Contact NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Contact NOT saved!"));
            }
        }
    }

    public long insertUpdateContact_list(Contact_list aContact_list) {
        String sql = null;
        long status = 0;
        if (aContact_list.getContact_list_id() == 0) {
            sql = "{call sp_insert_contact_list(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        } else if (aContact_list.getContact_list_id() > 0) {
            sql = "{call sp_update_contact_list(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        }
        try (
                Connection conn = DBConnection.getMySQLConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (aContact_list.getContact_list_id() == 0) {
                cs.setString("in_category", aContact_list.getCategory());
                cs.setString("in_subcategory", aContact_list.getSubcategory());
                cs.setString("in_email1", aContact_list.getEmail1());
                cs.setString("in_email2", aContact_list.getEmail2());
                cs.setString("in_company_name", aContact_list.getCompany_name());
                cs.setString("in_title", aContact_list.getTitle());
                cs.setString("in_first_name", aContact_list.getFirst_name());
                cs.setString("in_second_name", aContact_list.getSecond_name());
                cs.setString("in_phone1", aContact_list.getPhone1());
                cs.setString("in_phone2", aContact_list.getPhone2());
                cs.setString("in_source", aContact_list.getSource());
                cs.setString("in_type", aContact_list.getType());
                cs.setString("in_loc_level_1", aContact_list.getLoc_level1());
                cs.setString("in_loc_level_2", aContact_list.getLoc_level2());
                cs.setString("in_loc_level_3", aContact_list.getLoc_level3());
                cs.setString("in_loc_level_4", aContact_list.getLoc_level4());
                cs.setString("in_address", aContact_list.getAddress());
                cs.executeUpdate();
                status = 1;
            } else if (aContact_list.getContact_list_id() > 0) {
                cs.setLong("in_contact_list_id", aContact_list.getContact_list_id());
                cs.setString("in_category", aContact_list.getCategory());
                cs.setString("in_subcategory", aContact_list.getSubcategory());
                cs.setString("in_email1", aContact_list.getEmail1());
                cs.setString("in_email2", aContact_list.getEmail2());
                cs.setString("in_company_name", aContact_list.getCompany_name());
                cs.setString("in_title", aContact_list.getTitle());
                cs.setString("in_first_name", aContact_list.getFirst_name());
                cs.setString("in_second_name", aContact_list.getSecond_name());
                cs.setString("in_phone1", aContact_list.getPhone1());
                cs.setString("in_phone2", aContact_list.getPhone2());
                cs.setString("in_source", aContact_list.getSource());
                cs.setString("in_type", aContact_list.getType());
                cs.setString("in_loc_level_1", aContact_list.getLoc_level1());
                cs.setString("in_loc_level_2", aContact_list.getLoc_level2());
                cs.setString("in_loc_level_3", aContact_list.getLoc_level3());
                cs.setString("in_loc_level_4", aContact_list.getLoc_level4());
                cs.setString("in_address", aContact_list.getAddress());
                cs.executeUpdate();
                status = 1;
            }
        } catch (SQLException se) {
            status = 0;
            System.err.println(se.getMessage());
        }
        return status;
    }

    public void clearContact_list(Contact_list aContact_list) {
        if (aContact_list != null) {
            aContact_list.setContact_list_id(0);
            aContact_list.setCategory("");
            aContact_list.setSubcategory("");
            aContact_list.setEmail1("");
            aContact_list.setEmail2("");
            aContact_list.setCompany_name("");
            aContact_list.setTitle("");
            aContact_list.setFirst_name("");
            aContact_list.setSecond_name("");
            aContact_list.setPhone1("");
            aContact_list.setPhone2("");
            aContact_list.setSource("");
            aContact_list.setType("");
            aContact_list.setLoc_level1("");
            aContact_list.setLoc_level2("");
            aContact_list.setLoc_level3("");
            aContact_list.setLoc_level4("");
            aContact_list.setAddress("");

        }
    }

    public Contact_list getContact_listById(int aContact_listId) {
        String sql = "";
        if (aContact_listId > 0) {
            sql = "SELECT * FROM contact_list WHERE contact_list_id=" + aContact_listId;
        } else {
            return null;
        }
        ResultSet rs = null;
        Contact_list contactlist = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                contactlist = new Contact_list();
                this.setContact_listFromResultset(contactlist, rs);
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
        return contactlist;
    }

    public List<Contact_list> getContact_listAll() {
        String sql;
        sql = "select * from contact_list";
        ResultSet rs = null;
        List<Contact_list> cl = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Contact_list contactlist = new Contact_list();
                this.setContact_listFromResultset(contactlist, rs);
                cl.add(contactlist);
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
        return cl;
    }

    public List<Contact_list> getContact_listByCat(String aCategory, int aAppendControl) {
        String sql;
        if (aAppendControl == 1) {
            sql = "select * from contact_list where category=?"
                    + " UNION "
                    + "select * from contact_list where type='Control'";
        } else {
            sql = "select * from contact_list where category=?";
        }
        ResultSet rs = null;
        List<Contact_list> cl = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contact_list contactlist = new Contact_list();
                this.setContact_listFromResultset(contactlist, rs);
                cl.add(contactlist);
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
        return cl;
    }

    public List<Contact_list> getContact_listByCatSubcat(String aCategory, String aSubcategory, int aAppendControl) {
        String sql;
        if (aAppendControl == 1) {
            sql = "select * from contact_list where category=? and subcategory=?"
                    + " UNION "
                    + "select * from contact_list where type='Control'";
        } else {
            sql = "select * from contact_list where category=? and subcategory=?";
        }
        ResultSet rs = null;
        List<Contact_list> cl = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCategory);
            ps.setString(2, aSubcategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contact_list contactlist = new Contact_list();
                this.setContact_listFromResultset(contactlist, rs);
                cl.add(contactlist);
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
        return cl;
    }

    public List<Contact_list> getDistinctSubcategory(String aCategory) {
        String sql;
        sql = "select distinct subcategory from contact_list where category=?";
        ResultSet rs = null;
        List<Contact_list> cl = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contact_list contactlist = new Contact_list();
                try {
                    contactlist.setSubcategory(rs.getString("subcategory"));
                } catch (NullPointerException npe) {
                    contactlist.setSubcategory("");
                }
                cl.add(contactlist);
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
        return cl;
    }

    public List<Contact_list> getDistinctCategory() {
        String sql;
        sql = "select distinct category from contact_list";
        ResultSet rs = null;
        this.Contact_listObj.setSubcategory("");
        List<Contact_list> cl = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            //ps.setString(1, aCategory);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contact_list contactlist = new Contact_list();
                try {
                    contactlist.setCategory(rs.getString("category"));
                } catch (NullPointerException npe) {
                    contactlist.setCategory("");
                }
                cl.add(contactlist);
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
        return cl;
    }

    public void refreshCountList(Contact_list aContact_list) {
        this.CountList = new EmailEntityBean().countList(aContact_list);
    }

    public void displayContact_list(Contact_list Contact_listFrom, Contact_list Contact_listTo) {
        Contact_listTo.setContact_list_id(Contact_listFrom.getContact_list_id());
        Contact_listTo.setType(Contact_listFrom.getType());
        Contact_listTo.setTitle(Contact_listFrom.getTitle());
        Contact_listTo.setCategory(Contact_listFrom.getCategory());
        Contact_listTo.setSubcategory(Contact_listFrom.getSubcategory());
        Contact_listTo.setEmail1(Contact_listFrom.getEmail1());
        Contact_listTo.setEmail2(Contact_listFrom.getEmail2());
        Contact_listTo.setCompany_name(Contact_listFrom.getCompany_name());
        Contact_listTo.setPhone1(Contact_listFrom.getPhone1());
        Contact_listTo.setPhone2(Contact_listFrom.getPhone2());
        Contact_listTo.setFirst_name(Contact_listFrom.getFirst_name());
        Contact_listTo.setSecond_name(Contact_listFrom.getSecond_name());
        Contact_listTo.setLoc_level1(Contact_listFrom.getLoc_level1());
        Contact_listTo.setLoc_level2(Contact_listFrom.getLoc_level2());
        Contact_listTo.setLoc_level2(Contact_listFrom.getLoc_level2());
        Contact_listTo.setLoc_level3(Contact_listFrom.getLoc_level3());
        Contact_listTo.setLoc_level4(Contact_listFrom.getLoc_level4());
        Contact_listTo.setAddress(Contact_listFrom.getAddress());
        Contact_listTo.setSource(Contact_listFrom.getSource());
    }

    public List<Contact_list> getContact_lists() {
        String sql;
        sql = "{call sp_search_contact_list_by_company_name(?)}";
        ResultSet rs = null;
        Contact_lists = new ArrayList<Contact_list>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, this.getSearchContactNames());
            rs = ps.executeQuery();
            while (rs.next()) {
                Contact_lists.add(this.getContact_listFromResultSet(rs));
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
        return Contact_lists;
    }

    public List<Contact_list> getContact_listsByCompanyEmailFirstName(String aSearchName) {
        String sql;
        sql = "{call sp_search_contact_list_by_company_email_firstname(?)}";
        ResultSet rs = null;
        Contact_lists = new ArrayList<Contact_list>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSearchName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contact_list aContact_list = new Contact_list();
                this.setContact_listFromResultset(aContact_list, rs);
                Contact_lists.add(aContact_list);
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
        return Contact_lists;
    }

    public void deleteContactList(Contact_list aContact_list) {
        String sql = "";
        sql = "delete from contact_list where contact_list_id=" + aContact_list.getContact_list_id();
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.executeUpdate(sql);
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Deleted Successfully"));
            this.setActionMessage("Deleted successdfully");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("Contact List NOT deleted"));
            this.setActionMessage("Contact List NOT deleted");
            System.err.println(e.getMessage());
        }
    }

    /**
     * @return the Contact_listList
     */
    public List<Contact_list> getContact_listList() {
        return Contact_listList;
    }

    /**
     * @param Contact_listList the Contact_listList to set
     */
    public void setContact_listList(List<Contact_list> Contact_listList) {
        this.Contact_listList = Contact_listList;
    }

    /**
     * @return the Contact_listObj
     */
    public Contact_list getContact_listObj() {
        return Contact_listObj;
    }

    /**
     * @param Contact_listObj the Contact_listObj to set
     */
    public void setContact_listObj(Contact_list Contact_listObj) {
        this.Contact_listObj = Contact_listObj;
    }

    /**
     * @return the CountList
     */
    public int getCountList() {
        return CountList;
    }

    /**
     * @param CountList the CountList to set
     */
    public void setCountList(int CountList) {
        this.CountList = CountList;
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
     * @return the SearchContactNames
     */
    public String getSearchContactNames() {
        return SearchContactNames;
    }

    /**
     * @param SearchContactNames the SearchContactNames to set
     */
    public void setSearchContactNames(String SearchContactNames) {
        this.SearchContactNames = SearchContactNames;
    }

    /**
     * @param Contact_lists the Contact_lists to set
     */
    public void setContact_lists(List<Contact_list> Contact_lists) {
        this.Contact_lists = Contact_lists;
    }
}
