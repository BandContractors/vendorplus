/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import connections.DBConnection;
import entities.Project;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author HP
 */
@ManagedBean
@RequestScoped
public class ProjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(ProjectBean.class.getName());

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
        String sql = "DELETE FROM project WHERE project_id=?";
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, aProject.getProject_id());
            ps.executeUpdate();
            IsDeleted = 1;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
        }
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
}
