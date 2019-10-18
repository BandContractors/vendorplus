package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.CompanySetting;
import entities.GroupRight;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import utilities.CustomValidator;
import utilities.Security;

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
public class UserDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<UserDetail> UserDetails;
    private List<UserDetail> UserDetailObjectList;
    private String ActionMessage = null;
    private UserDetail SelectedUserDetail = null;
    private int SelectedUserDetailId;
    private String SearchUserName = "";

    public void setUserDetailFromResultset(UserDetail aUserDetail, ResultSet aResultSet) {
        try {
            aUserDetail.setUserDetailId(aResultSet.getInt("user_detail_id"));
            aUserDetail.setUserName(aResultSet.getString("user_name"));
            aUserDetail.setUserPassword(Security.Decrypt(aResultSet.getString("user_password")));
            aUserDetail.setUserPasswordConfirm(Security.Decrypt(aResultSet.getString("user_password")));
            aUserDetail.setFirstName(aResultSet.getString("first_name"));
            aUserDetail.setSecondName(aResultSet.getString("second_name"));
            aUserDetail.setThirdName(aResultSet.getString("third_name"));
            aUserDetail.setIsUserLocked(aResultSet.getString("is_user_locked"));
            aUserDetail.setIsUserGenAdmin(aResultSet.getString("is_user_gen_admin"));
            aUserDetail.setUserCategoryId(aResultSet.getInt("user_category_id"));
            aUserDetail.setUserImgUrl(aResultSet.getString("user_img_url"));
            aUserDetail.setEmail_address(aResultSet.getString("email_address"));
            aUserDetail.setPhone_no(aResultSet.getString("phone_no"));
            aUserDetail.setTrans_code(Security.Decrypt(aResultSet.getString("trans_code")));
        } catch (SQLException se) {
            System.err.println("setUserDetailFromResultset:" + se.getMessage());
        }
    }

    public UserDetail findUserDetail(int aUserDetailId) {
        String sql = "{call sp_search_user_detail_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aUserDetailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                return userdetail;
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

    public boolean IsTransCodeValid(UserDetail aUserdetail) {
        boolean res = false;
        if (aUserdetail.getUserDetailId() == 0) {//new user
            //check if code exists
            UserDetail found_userdetail = this.getUserDetailWithTransCode(aUserdetail.getTrans_code());
            if (null == found_userdetail) {//not found
                res = true;
            } else {//found
                res = false;
            }
        } else {//existing user for update
            //check if code exists
            UserDetail found_userdetail = this.getUserDetailWithTransCode(aUserdetail.getTrans_code());
            if (null == found_userdetail) {//not found
                res = true;
            } else {//found
                if (found_userdetail.getUserName().equals(aUserdetail.getUserName())) {
                    res = true;
                } else {
                    res = false;
                }
            }
        }
        return res;
    }

    public void saveUserDetail(UserDetail userdetail) {
        String sql = null;
        String msg = "";
        String sql2 = null;
        sql2 = "SELECT * FROM user_detail WHERE user_name='" + userdetail.getUserName() + "'";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (userdetail.getUserDetailId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (userdetail.getUserDetailId() > 0 && new GeneralUserSetting().getChangePasswordAllowed() != 1 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (userdetail.getUserCategoryId() == 0) {
            msg = "Please select the User Category";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().CheckPassword(userdetail.getUserPassword(), userdetail.getUserPasswordConfirm()).equals("FAIL")) {
            msg = "Ensure that user passwords match and are between 5-to-20 characters";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().TextSize(userdetail.getUserName(), 5, 20).equals("FAIL")) {
            msg = "User Name MUST be between 5-to-20 characters";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().TextSize(userdetail.getFirstName(), 3, 100).equals("FAIL")) {
            msg = "First Name MUST be between 3-to-100 characters";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().TextSize(userdetail.getSecondName(), 0, 100).equals("FAIL")) {
            msg = "Second Name cannot exceed 100 characters";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().TextSize(userdetail.getThirdName(), 0, 100).equals("FAIL")) {
            msg = "Third Name cannot exceed 100 characters";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().TextSize(userdetail.getIsUserLocked(), 2, 3).equals("FAIL")) {
            msg = "Please select an option for Lock User? !";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (new CustomValidator().TextSize(userdetail.getIsUserGenAdmin(), 2, 3).equals("FAIL")) {
            msg = "Please select an option for General Admin !";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if ((new CustomValidator().CheckRecords(sql2) > 0 && userdetail.getUserDetailId() == 0) || (new CustomValidator().CheckRecords(sql2) > 0 && new CustomValidator().CheckRecords(sql2) != 1 && userdetail.getUserDetailId() > 0)) {
            msg = "Username already exists, please enter a different username !";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (!this.IsTransCodeValid(userdetail)) {
            msg = "Please enter a different User Code...!";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (userdetail.getTrans_code().length() < 4) {
            msg = "User Code must be at least 4 characters...!";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (this.isLicensePackageViolated(userdetail, CompanySetting.getPackageUsers(CompanySetting.getPACKAGE_NAME()))) {
            msg = "YOUR CURRENT LICENSE PACKAGE LIMITS ADDITION OF MORE USERS, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {

            if (userdetail.getUserDetailId() == 0) {
                sql = "{call sp_insert_user_detail(?,?,?,?,?,?,?,?,?,?,?,?)}";
            } else if (userdetail.getUserDetailId() > 0) {
                sql = "{call sp_update_user_detail(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                if (userdetail.getUserDetailId() == 0) {
                    cs.setString("in_user_name", userdetail.getUserName());
                    cs.setString("in_user_password", Security.Encrypt(userdetail.getUserPassword()));
                    cs.setString("in_first_name", userdetail.getFirstName());
                    cs.setString("in_second_name", userdetail.getSecondName());
                    cs.setString("in_third_name", userdetail.getThirdName());
                    cs.setString("in_is_user_locked", userdetail.getIsUserLocked());
                    cs.setString("in_is_user_gen_admin", userdetail.getIsUserGenAdmin());
                    cs.setInt("in_user_category_id", userdetail.getUserCategoryId());
                    cs.setString("in_user_img_url", userdetail.getUserImgUrl());
                    cs.setString("in_email_address", userdetail.getEmail_address());
                    cs.setString("in_phone_no", userdetail.getPhone_no());
                    cs.setString("in_trans_code", Security.Encrypt(userdetail.getTrans_code()));
                    cs.executeUpdate();
                    this.setActionMessage("Saved Successfully");
                    this.clearUserDetail(userdetail);

                } else if (userdetail.getUserDetailId() > 0) {
                    cs.setInt("in_user_detail_id", userdetail.getUserDetailId());
                    cs.setString("in_user_name", userdetail.getUserName());
                    cs.setString("in_user_password", Security.Encrypt(userdetail.getUserPassword()));
                    cs.setString("in_first_name", userdetail.getFirstName());
                    cs.setString("in_second_name", userdetail.getSecondName());
                    cs.setString("in_third_name", userdetail.getThirdName());
                    cs.setString("in_is_user_locked", userdetail.getIsUserLocked());
                    cs.setString("in_is_user_gen_admin", userdetail.getIsUserGenAdmin());
                    cs.setInt("in_user_category_id", userdetail.getUserCategoryId());
                    cs.setString("in_user_img_url", userdetail.getUserImgUrl());
                    cs.setString("in_email_address", userdetail.getEmail_address());
                    cs.setString("in_phone_no", userdetail.getPhone_no());
                    cs.setString("in_trans_code", Security.Encrypt(userdetail.getTrans_code()));
                    cs.executeUpdate();
                    this.setActionMessage("Saved Successfully");
                    this.clearUserDetail(userdetail);
                }
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("UserDetail NOT saved");
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage("UserDetail NOT saved!"));
            }
        }
    }

    public UserDetail getUserDetail(int aUserDetailId) {
        String sql = "{call sp_search_user_detail_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aUserDetailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                return userdetail;
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

    public UserDetail getUserDetailWithTransCode(String aTransCode) {
        String sql = "SELECT * FROM user_detail";
        ResultSet rs = null;
        UserDetail userdetail = null;
        if (null == aTransCode) {
            //do nothing
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (aTransCode.equals(Security.Decrypt(rs.getString("trans_code")))) {
                        userdetail = new UserDetail();
                        this.setUserDetailFromResultset(userdetail, rs);
                        break;
                    }
                }
            } catch (SQLException se) {
                userdetail = null;
            }
        }
        return userdetail;
    }

    public void setUserDetail(UserDetail userdetail, int aUserDetailId) {
        String sql = "{call sp_search_user_detail_by_id(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aUserDetailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.setUserDetailFromResultset(userdetail, rs);
            } else {
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
    }

    public UserDetail getUserDetailByUserName(String UserName) {
        String sql = "{call sp_search_user_detail_by_username(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, UserName);
            rs = ps.executeQuery();
            if (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                return userdetail;
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

    public static int getSystemUserDetailId() {
        String sql = "{call sp_search_user_detail_by_username(?)}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, "system");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_detail_id");
            } else {
                return 0;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return 0;
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

    public long getCountTotalActiveUserDetail() {
        String sql = "{call sp_search_user_detail_all_active()}";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("total_user_count");
            } else {
                return 0;
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return 1000000;
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

    public boolean isLicensePackageViolated(UserDetail aUserDetail, long PackageUsers) {
        long TotalActiveUsers = this.getCountTotalActiveUserDetail();
        if (aUserDetail.getUserDetailId() == 0) {//for new addition
            if (aUserDetail.getIsUserLocked().equals("Yes") && TotalActiveUsers > PackageUsers) {
                return true;
            } else if (aUserDetail.getIsUserLocked().equals("No") && (TotalActiveUsers + 1) > PackageUsers) {
                return true;
            } else {
                return false;
            }
        } else if (aUserDetail.getUserDetailId() > 0) {//for update
            if (aUserDetail.getIsUserLocked().equals("No")) {
                UserDetail UserDetailOld = new UserDetail();
                UserDetailOld = this.getUserDetail(aUserDetail.getUserDetailId());
                if (UserDetailOld.getIsUserLocked().equals("Yes") && (TotalActiveUsers + 1) > PackageUsers) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void deleteUserDetail(UserDetail userdetail) {
        String msg;
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            String sql = "DELETE FROM user_detail WHERE user_detail_id=?";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, userdetail.getUserDetailId());
                ps.executeUpdate();
                this.setActionMessage("Deleted Successfully!");
                this.clearUserDetail(userdetail);
            } catch (SQLException se) {
                System.err.println(se.getMessage());
                this.setActionMessage("UserDetail NOT deleted");
            }
        }
    }

    public void displayUserDetail(UserDetail UserDetailFrom, UserDetail UserDetailTo) {
        try {
            UserDetailTo.setUserDetailId(UserDetailFrom.getUserDetailId());
            UserDetailTo.setUserName(UserDetailFrom.getUserName());
            UserDetailTo.setUserPassword(UserDetailFrom.getUserPassword());
            UserDetailTo.setUserPasswordConfirm(UserDetailFrom.getUserPasswordConfirm());
            UserDetailTo.setFirstName(UserDetailFrom.getFirstName());
            UserDetailTo.setSecondName(UserDetailFrom.getSecondName());
            UserDetailTo.setThirdName(UserDetailFrom.getThirdName());
            UserDetailTo.setIsUserLocked(UserDetailFrom.getIsUserLocked());
            UserDetailTo.setIsUserGenAdmin(UserDetailFrom.getIsUserGenAdmin());
            UserDetailTo.setUserCategoryId(UserDetailFrom.getUserCategoryId());
            UserDetailTo.setUserImgUrl(UserDetailFrom.getUserImgUrl());
            UserDetailTo.setEmail_address(UserDetailFrom.getEmail_address());
            UserDetailTo.setPhone_no(UserDetailFrom.getPhone_no());
            UserDetailTo.setTrans_code(UserDetailFrom.getTrans_code());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearUserDetail(UserDetail userdetail) {
        if (userdetail != null) {
            userdetail.setUserDetailId(0);
            userdetail.setUserName("");
            userdetail.setUserPassword("");
            userdetail.setUserPasswordConfirm("");
            userdetail.setFirstName("");
            userdetail.setSecondName("");
            userdetail.setThirdName("");
            userdetail.setIsUserLocked("");
            userdetail.setIsUserGenAdmin("");
            userdetail.setUserCategoryId(0);
            userdetail.setUserImgUrl("");
            userdetail.setEmail_address("");
            userdetail.setPhone_no("");
            userdetail.setTrans_code("");
            userdetail.setNew_trans_code("");
        }
    }

    public void clearUserDetailChange(UserDetail userdetail) {
        userdetail.setOldUserPassword("");
        userdetail.setNewUserPassword("");
        userdetail.setNewUserPasswordConfirm("");
    }

    /**
     * @param aSearchName
     * @return the UserDetails
     */
    public List<UserDetail> getUserDetailsByNames(String aSearchName) {
        String sql;
        sql = "{call sp_search_user_detail_by_names(?)}";
        ResultSet rs = null;
        UserDetails = new ArrayList<UserDetail>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aSearchName);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                UserDetails.add(userdetail);
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
        return UserDetails;
    }

    public List<UserDetail> getUserDetails() {
        String sql;
        sql = "{call sp_search_user_detail_by_none()}";
        ResultSet rs = null;
        UserDetails = new ArrayList<UserDetail>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                UserDetails.add(userdetail);
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
        return UserDetails;
    }

    public List<UserDetail> getUserDetailsNotLocked() {
        String sql;
        sql = "{call sp_search_user_detail_not_locked()}";
        ResultSet rs = null;
        UserDetails = new ArrayList<UserDetail>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                UserDetails.add(userdetail);
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
        return UserDetails;
    }

    public void changePassword(UserDetail aUserDetail) {
        String msg;
        UserDetail OldUD = new GeneralUserSetting().getCurrentUser();

        UserDetail NewUD = new UserDetail();
        NewUD = aUserDetail;

        try {
            if (!NewUD.getOldUserPassword().equals(OldUD.getUserPassword())) {
                msg = "Old password is not correct...";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else if (!NewUD.getNewUserPassword().equals(NewUD.getNewUserPasswordConfirm())) {
                msg = "New passwords do not match...";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else {
                OldUD.setUserPassword(NewUD.getNewUserPassword());
                OldUD.setUserPasswordConfirm(NewUD.getNewUserPasswordConfirm());
                //update seesion
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpSession httpSession = request.getSession(false);

                httpSession.setAttribute("CHANGE_PASSWORD_ALLOWED", 1);
                this.saveUserDetail(OldUD);
                httpSession.setAttribute("CHANGE_PASSWORD_ALLOWED", 0);
                this.clearUserDetailChange(NewUD);
            }

        } catch (NullPointerException npe) {
            msg = "Invalid user...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        }
    }

    public void changeTransCode(UserDetail aUserDetail) {
        String msg;
        try {
            if (aUserDetail.getNew_trans_code().length() > 0) {
                UserDetail ud = new GeneralUserSetting().getCurrentUser();
                ud.setTrans_code(aUserDetail.getNew_trans_code());
                this.saveUserDetail(ud);
            }
        } catch (NullPointerException npe) {
            msg = "user code not saved...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        }
    }

    public String getUserEmailAddressesForFunction(String aFunctionName, String aRole) {
        String EmailAddresses = "";
        String sql;
        sql = "select ud.* from user_detail ud where ud.is_user_locked='No' and ud.user_detail_id IN"
                + "("
                + " select distinct gu.user_detail_id from group_user gu where gu.group_detail_id IN "
                + "  ("
                + "	select distinct gr.group_detail_id from group_right gr where gr.function_name='" + aFunctionName + "' and gr." + aRole + "='Yes'"
                + "  )"
                + ")";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                if (userdetail.getEmail_address().length() > 0) {
                    if (EmailAddresses.length() == 0) {
                        EmailAddresses = userdetail.getEmail_address();
                    } else {
                        EmailAddresses = EmailAddresses + "," + userdetail.getEmail_address();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("getUserEmailAddressesForFunction:" + e.getMessage());
        }
        return EmailAddresses;
    }

    public String getUserDetailIDsForFunction(String aFunctionName, String aRole) {
        String UserIDs = "";
        String sql;
        sql = "select ud.* from user_detail ud where ud.is_user_locked='No' and ud.user_detail_id IN"
                + "("
                + " select distinct gu.user_detail_id from group_user gu where gu.group_detail_id IN "
                + "  ("
                + "	select distinct gr.group_detail_id from group_right gr where gr.function_name='" + aFunctionName + "' and gr." + aRole + "='Yes'"
                + "  )"
                + ")";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                if (userdetail.getEmail_address().length() > 0) {
                    if (UserIDs.length() == 0) {
                        UserIDs = "" + userdetail.getUserDetailId() + "";
                    } else {
                        UserIDs = UserIDs + "," + userdetail.getUserDetailId();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("getUserDetailIDsForFunction:" + e.getMessage());
        }
        return UserIDs;
    }

    public String getUserEmailAddressesForIDs(String aUserIDs) {
        String EmailAddresses = "";
        String sql;
        sql = "select distinct email_address from user_detail where user_detail_id IN(" + aUserIDs + ") and ifnull(email_address,'')!=''";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                String emailaddress = rs.getString("email_address");
                if (emailaddress.length() > 0) {
                    if (EmailAddresses.length() == 0) {
                        EmailAddresses = emailaddress;
                    } else {
                        EmailAddresses = EmailAddresses + "," + emailaddress;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("getUserEmailAddressesForIDs:" + e.getMessage());
        }
        return EmailAddresses;
    }

    /**
     * @param UserDetails the UserDetails to set
     */
    public void setUserDetails(List<UserDetail> UserDetails) {
        this.UserDetails = UserDetails;
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
     * @return the SelectedUserDetail
     */
    public UserDetail getSelectedUserDetail() {
        return SelectedUserDetail;
    }

    /**
     * @param SelectedUserDetail the SelectedUserDetail to set
     */
    public void setSelectedUserDetail(UserDetail SelectedUserDetail) {
        this.SelectedUserDetail = SelectedUserDetail;
    }

    /**
     * @return the SelectedUserDetailId
     */
    public int getSelectedUserDetailId() {
        return SelectedUserDetailId;
    }

    /**
     * @param SelectedUserDetailId the SelectedUserDetailId to set
     */
    public void setSelectedUserDetailId(int SelectedUserDetailId) {
        this.SelectedUserDetailId = SelectedUserDetailId;
    }

    /**
     * @return the SearchUserName
     */
    public String getSearchUserName() {
        return SearchUserName;
    }

    /**
     * @param SearchUserName the SearchUserName to set
     */
    public void setSearchUserName(String SearchUserName) {
        this.SearchUserName = SearchUserName;
    }

    /**
     * @return the UserDetailObjectList
     */
    public List<UserDetail> getUserDetailObjectList(String Query) {
        String sql;
        sql = "{call sp_search_user_detail_by_names(?)}";
        ResultSet rs = null;
        UserDetailObjectList = new ArrayList<UserDetail>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, Query.trim());
            rs = ps.executeQuery();
            while (rs.next()) {
                UserDetail userdetail = new UserDetail();
                this.setUserDetailFromResultset(userdetail, rs);
                UserDetailObjectList.add(userdetail);
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
        return UserDetailObjectList;
    }

    /**
     * @param UserDetailObjectList the UserDetailObjectList to set
     */
    public void setUserDetailObjectList(List<UserDetail> UserDetailObjectList) {
        this.UserDetailObjectList = UserDetailObjectList;
    }

}
