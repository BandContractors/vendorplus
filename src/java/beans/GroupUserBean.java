package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupUser;
import entities.GroupRight;
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
import utilities.CustomValidator;
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
public class GroupUserBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(GroupUserBean.class.getName());
    private List<GroupUser> GroupUsers;
    private String ActionMessage = null;
    private GroupUser SelectedGroupUser = null;
    private int SelectedGroupUserId;
    private String SearchGroupUserName = "";
    private int SelectedGroupDetailId;
    private int SelectedUserDetailId;
    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

    public void saveGroupUser(GroupUser groupuser) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = null;
        String sql2 = null;

        sql2 = "SELECT * FROM group_user WHERE group_detail_id=" + groupuser.getGroupDetailId() + " AND user_detail_id=" + groupuser.getUserDetailId();
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (groupuser.getGroupUserId() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (groupuser.getGroupUserId() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else if (groupuser.getGroupDetailId() == 0 || groupuser.getUserDetailId() == 0) {
            this.setActionMessage(ub.translateWordsInText(BaseName, "Select Group and User"));
        } else if (new CustomValidator().CheckRecords(sql2) > 0) {
            this.setActionMessage(ub.translateWordsInText(BaseName, "User Already Exists in the Group"));
        } else if (groupuser.getGroupUserId() != 0) {
            //do nothing
        } else {
            sql = "{call sp_insert_group_user(?,?)}";
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    CallableStatement cs = conn.prepareCall(sql);) {
                cs.setInt(1, groupuser.getGroupDetailId());
                cs.setInt(2, groupuser.getUserDetailId());
                cs.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Saved Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);//(se.getMessage());
                this.setActionMessage(ub.translateWordsInText(BaseName, "Group User Not Saved"));
            }
        }

    }

    public GroupUser getGroupUser(int GrpUserId) {
        String sql = "SELECT * FROM group_user WHERE group_user_id=?";
        ResultSet rs = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, GrpUserId);
            rs = ps.executeQuery();
            if (rs.next()) {
                GroupUser groupuser = new GroupUser();
                groupuser.setGroupUserId(rs.getInt("group_user_id"));
                groupuser.setGroupDetailId(rs.getInt("group_detail_id"));
                groupuser.setUserDetailId(rs.getInt("user_detail_id"));
                return groupuser;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
            return null;
        }
    }

    public void deleteGroupUser() {
        this.deleteGroupUserById(this.SelectedGroupUserId);
    }

    public void deleteGroupUserByObject(GroupUser GrpUsr) {
        this.deleteGroupUserById(GrpUsr.getGroupUserId());
    }

    public void deleteGroupUserById(int GrpUserId) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = "DELETE FROM group_user WHERE group_user_id=?";
        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
            msg = "Not Allowed to Access this Function";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        } else {
            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                ps.setInt(1, GrpUserId);
                ps.executeUpdate();
                this.setActionMessage(ub.translateWordsInText(BaseName, "Deleted Successfully"));
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, e);//(se.getMessage());
                this.setActionMessage(ub.translateWordsInText(BaseName, "Not Deleted"));
            }
        }
    }

    public void displayGroupUser(GroupUser GrpUsrFrom, GroupUser GrpUsrTo) {
        GrpUsrTo.setGroupUserId(GrpUsrFrom.getGroupUserId());
        GrpUsrTo.setGroupDetailId(GrpUsrFrom.getGroupDetailId());
        GrpUsrTo.setUserDetailId(GrpUsrFrom.getUserDetailId());
    }

    public void clearGroupUser(GroupUser GrpUsr) {
        GrpUsr.setGroupUserId(0);
        //GrpUsr.setGroupDetailId(0);
        GrpUsr.setUserDetailId(0);
        this.SelectedUserDetailId = 0;
    }

    public List<GroupUser> getGroupUsers() {
        String sql;
        if (this.SelectedGroupDetailId != 0 && this.SelectedUserDetailId != 0) {
            sql = "SELECT * FROM group_user gu WHERE gu.group_detail_id=" + this.SelectedGroupDetailId + " AND gu.user_detail_id=" + this.SelectedUserDetailId + "";
        } else if (this.SelectedGroupDetailId != 0 && this.SelectedUserDetailId == 0) {
            sql = "SELECT * FROM group_user gu WHERE gu.group_detail_id=" + this.SelectedGroupDetailId + "";
        } else if (this.SelectedGroupDetailId == 0 && this.SelectedUserDetailId != 0) {
            sql = "SELECT * FROM group_user gu WHERE gu.user_detail_id=" + this.SelectedUserDetailId + "";
        } else {
            sql = "SELECT * FROM group_user gu ORDER BY group_detail_id ASC";
        }
        ResultSet rs = null;
        GroupUsers = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                GroupUser groupuser = new GroupUser();
                groupuser.setGroupUserId(rs.getInt("group_user_id"));
                groupuser.setGroupDetailId(rs.getInt("group_detail_id"));
                groupuser.setUserDetailId(rs.getInt("user_detail_id"));
                GroupUsers.add(groupuser);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);//(se.getMessage());
        }
        return GroupUsers;
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
     * @return the GroupUsers
     */
    /**
     * @param GroupUsers the GroupUsers to set
     */
    public void setGroupUsers(List<GroupUser> GroupUsers) {
        this.GroupUsers = GroupUsers;
    }

    /**
     * @return the SelectedGroupUser
     */
    public GroupUser getSelectedGroupUser() {
        return SelectedGroupUser;
    }

    /**
     * @param SelectedGroupUser the SelectedGroupUser to set
     */
    public void setSelectedGroupUser(GroupUser SelectedGroupUser) {
        this.SelectedGroupUser = SelectedGroupUser;
    }

    /**
     * @return the SelectedGroupUserId
     */
    public int getSelectedGroupUserId() {
        return SelectedGroupUserId;
    }

    /**
     * @param SelectedGroupUserId the SelectedGroupUserId to set
     */
    public void setSelectedGroupUserId(int SelectedGroupUserId) {
        this.SelectedGroupUserId = SelectedGroupUserId;
    }

    /**
     * @return the SearchGroupUserName
     */
    public String getSearchGroupUserName() {
        return SearchGroupUserName;
    }

    /**
     * @param SearchGroupUserName the SearchGroupUserName to set
     */
    public void setSearchGroupUserName(String SearchGroupUserName) {
        this.SearchGroupUserName = SearchGroupUserName;
    }

    /**
     * @return the SelectedGroupDetailId
     */
    public int getSelectedGroupDetailId() {
        return SelectedGroupDetailId;
    }

    /**
     * @param SelectedGroupDetailId the SelectedGroupDetailId to set
     */
    public void setSelectedGroupDetailId(int SelectedGroupDetailId) {
        this.SelectedGroupDetailId = SelectedGroupDetailId;
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
