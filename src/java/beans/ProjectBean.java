/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.GroupRight;
import entities.Project;
import entities.UserDetail;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sessions.GeneralUserSetting;
import utilities.UtilityBean;

/**
 *
 * @author HP
 */
@ManagedBean
@RequestScoped
public class ProjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(ProjectBean.class.getName());

    private String SearchProjectName = "";

    @ManagedProperty("#{menuItemBean}")
    private MenuItemBean menuItemBean;

//    public void test() {
//        List<Project> maList = this.getProjectAll();
//        System.out.println("Found:" + maList.size());
//    }
    public void setProjectFromResultset(Project aProject, ResultSet aResultSet) {
        try {
            try {
                aProject.setProject_id(aResultSet.getInt("project_id"));
            } catch (Exception e) {
                aProject.setProject_id(0);
            }
            try {
                String project_name = aResultSet.getString("project_name");
                if (null == project_name) {
                    aProject.setProject_name("");
                } else {
                    aProject.setProject_name(project_name);
                }
            } catch (Exception e) {
                aProject.setProject_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void saveProject(Project aProject) {
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        String msg;
        String sql = null;
        try {
            try {
                BaseName = getMenuItemBean().getMenuItemObj().getLANG_BASE_NAME_SYS();
            } catch (Exception e) {
            }
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            String sql1 = "SELECT count(*) as n FROM project WHERE project_name='" + aProject.getProject_name() + "'";

            if (aProject.getProject_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Add") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aProject.getProject_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "8", "Edit") == 0) {
                msg = "Not Allowed to Access this Function";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aProject.getProject_name().length() <= 0) {
                msg = "Project Cannot be Empty";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else if (aProject.getProject_id() == 0 && ub.getN(sql1) > 0) {
                msg = "Project Already Exists ##: " + aProject.getProject_name();
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            } else {
                int saved = 0;
                if (aProject.getProject_id() == 0) {
                    saved = this.insertProject(aProject);
                } else if (aProject.getProject_id() > 0) {
                    saved = this.updateProject(aProject);
                }
                if (saved > 0) {
                    msg = "Project Saved Successfully";
                    this.clearProject(aProject);
                } else {
                    msg = "Activity Status NOT Saved";
                }
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void displayProject(Project ProjectFrom, Project ProjectTo) {
        try {
            this.clearProject(ProjectTo);
            ProjectTo.setProject_id(ProjectFrom.getProject_id());
            ProjectTo.setProject_name(ProjectFrom.getProject_name());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public int insertProject(Project aProject) {
        int InsertedId = 0;
        String sql = "INSERT INTO project(project_name) VALUES(?)";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, aProject.getProject_name());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                InsertedId = rs.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return InsertedId;
    }

    public int updateProject(Project aProject) {
        int IsUpdated = 0;
        String sql = "UPDATE project SET project_name=? WHERE project_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aProject.getProject_name());
            ps.setInt(2, aProject.getProject_id());
            ps.executeUpdate();
            IsUpdated = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return IsUpdated;
    }

    public int deleteProject(Project aProject) {
        int IsDeleted = 0;
        UtilityBean ub = new UtilityBean();
        String BaseName = "language_en";
        try {
            BaseName = menuItemBean.getMenuItemObj().getLANG_BASE_NAME_SYS();
        } catch (Exception e) {
        }
        String msg = "";
        String sql = "DELETE FROM project WHERE project_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aProject.getProject_id());
            ps.executeUpdate();
            IsDeleted = 1;
            this.clearProject(aProject);
            msg = "Project Deleted Successfully";
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            msg = "Project NOT Deleted";
        }
        FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(ub.translateWordsInText(BaseName, msg)));
        return IsDeleted;
    }

    public Project getProject(int aProject) {
        String sql = "SELECT * FROM project WHERE project_id=?";
        ResultSet rs;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aProject);
            rs = ps.executeQuery();
            if (rs.next()) {
                Project obj = new Project();
                this.setProjectFromResultset(obj, rs);
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            return null;
        }
    }

    public List<Project> getProjectAll() {
        String sql = "SELECT * FROM project";
        ResultSet rs;
        List<Project> list = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Project obj = new Project();
                this.setProjectFromResultset(obj, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return list;
    }

    public void clearProject(Project aProject) {
        try {
            if (null != aProject) {
                aProject.setProject_id(0);
                aProject.setProject_name("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public List<Project> getProjectByProjectName(String aProjectName) {
        String sql;
        sql = "SELECT * FROM project WHERE project_name LIKE CONCAT('%',?,'%')";
        ResultSet rs;
        List<Project> ProjectList = new ArrayList<>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, aProjectName);

            rs = ps.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                this.setProjectFromResultset(project, rs);
                ProjectList.add(project);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
        return ProjectList;
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

    /**
     * @return the SearchProjectName
     */
    public String getSearchProjectName() {
        return SearchProjectName;
    }

    /**
     * @param SearchProjectName the SearchProjectName to set
     */
    public void setSearchProjectName(String SearchProjectName) {
        this.SearchProjectName = SearchProjectName;
    }
}
