package beans;

import sessions.GeneralUserSetting;
import connections.DBConnection;
import entities.GroupRight;
import entities.UserDetail;
import entities.Transactor_segment;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import utilities.CustomValidator;

@ManagedBean
@SessionScoped
public class Transactor_segmentBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Transactor_segment> Transactor_segments;
    private String ActionMessage;
    private Transactor_segment SelectedTransactor_segment = null;

    public void saveTransactor_segment(Transactor_segment aTransactor_segment) {
        String sql = null;
        String msg = null;

        UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
        List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
        GroupRightBean grb = new GroupRightBean();

        if (aTransactor_segment.getTransactor_segment_id() == 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Add") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (aTransactor_segment.getTransactor_segment_id() > 0 && grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Edit") == 0) {
            msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else if (aTransactor_segment.getSegment_name().length() <= 0) {
            msg = "Segment Name Needed...";
            FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
        } else {
            if (aTransactor_segment.getTransactor_segment_id() == 0) {
                sql = "INSERT INTO transactor_segment(segment_name) VALUES(?)";
            } else if (aTransactor_segment.getTransactor_segment_id() > 0) {
                sql = "UPDATE transactor_segment SET segment_name=? WHERE transactor_segment_id=?";
            }

            try (
                    Connection conn = DBConnection.getMySQLConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);) {
                if (aTransactor_segment.getTransactor_segment_id() == 0) {
                    ps.setString(1, aTransactor_segment.getSegment_name());
                    ps.executeUpdate();
                    this.setActionMessage("Saved Successfully");
                    this.clearTransactor_segment(aTransactor_segment);
                } else if (aTransactor_segment.getTransactor_segment_id() > 0) {
                    ps.setString(1, aTransactor_segment.getSegment_name());
                    ps.setInt(2, aTransactor_segment.getTransactor_segment_id());
                    ps.executeUpdate();
                    this.setActionMessage("Saved Successfully");
                    this.clearTransactor_segment(aTransactor_segment);
                }
            } catch (Exception e) {
                System.err.println("saveTransactor_segment:" + e.getMessage());
                this.setActionMessage("Transactor_segment NOT saved");
            }
        }
    }

    public void setTransactor_segmentFromResultset(Transactor_segment aTransactor_segment, ResultSet aResultSet) {
        try {
            try {
                aTransactor_segment.setTransactor_segment_id(aResultSet.getInt("transactor_segment_id"));
            } catch (NullPointerException npe) {
                aTransactor_segment.setTransactor_segment_id(0);
            }
            try {
                aTransactor_segment.setSegment_name(aResultSet.getString("segment_name"));
            } catch (NullPointerException npe) {
                aTransactor_segment.setSegment_name("");
            }
        } catch (SQLException se) {
            System.err.println("setTransactor_segmentFromResultset:" + se.getMessage());
        }
    }

    public Transactor_segment getTransactor_segment(int aTransactor_segment_id) {
        String sql = "SELECT * FROM transactor_segment WHERE transactor_segment_id=" + aTransactor_segment_id;
        ResultSet rs = null;
        Transactor_segment seg = null;
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            if (rs.next()) {
                seg = new Transactor_segment();
                this.setTransactor_segmentFromResultset(seg, rs);
            }
        } catch (Exception e) {
            System.err.println("getTransactor_segment:" + e.getMessage());
        }
        return seg;
    }

    public void deleteTransactor_segment(Transactor_segment aTransactor_segment) {
        if (null == aTransactor_segment) {
            //do nothing
        } else {
            String msg;
            UserDetail aCurrentUserDetail = new GeneralUserSetting().getCurrentUser();
            List<GroupRight> aCurrentGroupRights = new GeneralUserSetting().getCurrentGroupRights();
            GroupRightBean grb = new GroupRightBean();

            String sql1 = "SELECT * FROM transactor WHERE transactor_segment_id=" + aTransactor_segment.getTransactor_segment_id();
            long found1 = new CustomValidator().CheckRecords(sql1);
            if (grb.IsUserGroupsFunctionAccessAllowed(aCurrentUserDetail, aCurrentGroupRights, "88", "Delete") == 0) {
                msg = "YOU ARE NOT ALLOWED TO USE THIS FUNCTION, CONTACT SYSTEM ADMINISTRATOR...";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else if (found1 > 0) {
                msg = "Segment cannot be deleted because it is already applied to " + found1 + " partners";
                FacesContext.getCurrentInstance().addMessage("Save", new FacesMessage(msg));
            } else {
                String sql = "DELETE FROM transactor_segment WHERE transactor_segment_id=" + aTransactor_segment.getTransactor_segment_id();
                try (
                        Connection conn = DBConnection.getMySQLConnection();
                        PreparedStatement ps = conn.prepareStatement(sql);) {
                    ps.executeUpdate();
                    this.setActionMessage("Deleted Successfully!");
                } catch (Exception e) {
                    System.err.println("deleteTransactor_segmentById:" + e.getMessage());
                    this.setActionMessage("Transactor_segment NOT deleted");
                }
            }
        }
    }

    public void displayTransactor_segment(Transactor_segment aFrom, Transactor_segment aTo) {
        aTo.setTransactor_segment_id(aFrom.getTransactor_segment_id());
        aTo.setSegment_name(aFrom.getSegment_name());
    }

    public void clearTransactor_segment(Transactor_segment aTransactor_segment) {
        aTransactor_segment.setTransactor_segment_id(0);
        aTransactor_segment.setSegment_name("");
    }

    public List<Transactor_segment> getTransactor_segments() {
        String sql;
        sql = "SELECT * FROM transactor_segment ORDER BY segment_name ASC";
        ResultSet rs = null;
        Transactor_segments = new ArrayList<Transactor_segment>();
        try (
                Connection conn = DBConnection.getMySQLConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Transactor_segment seg = new Transactor_segment();
                this.setTransactor_segmentFromResultset(seg, rs);
                Transactor_segments.add(seg);
            }
        } catch (Exception e) {
            System.err.println("getTransactor_segments:" + e.getMessage());
        }
        return Transactor_segments;
    }

    /**
     * @param Transactor_segments the Transactor_segments to set
     */
    public void setTransactor_segments(List<Transactor_segment> Transactor_segments) {
        this.Transactor_segments = Transactor_segments;
    }

    /**
     * @return the SelectedTransactor_segment
     */
    public Transactor_segment getSelectedTransactor_segment() {
        return SelectedTransactor_segment;
    }

    /**
     * @param SelectedTransactor_segment the SelectedTransactor_segment to set
     */
    public void setSelectedTransactor_segment(Transactor_segment SelectedTransactor_segment) {
        this.SelectedTransactor_segment = SelectedTransactor_segment;
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
