package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class DiscountPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    private int DiscountPackageId;
    private String PackageName;
    private Date StartDate;
    private Date EndDate;
    private String store_scope;
    private String transactor_scope;
    private String[] SelectedStores;
    private List<Transactor> SelectedTransactors;
    private String StatusColor;
    private String segment_scope;
    private String[] SelectedSegments;
    private String day_scope;
    private String[] SelectedDays;
    private String time_scope_from;
    private String time_scope_to;
    private Date FromTime;
    private Date ToTime;

    /**
     * @return the DiscountPackageId
     */
    public int getDiscountPackageId() {
        return DiscountPackageId;
    }

    /**
     * @param DiscountPackageId the DiscountPackageId to set
     */
    public void setDiscountPackageId(int DiscountPackageId) {
        this.DiscountPackageId = DiscountPackageId;
    }

    /**
     * @return the PackageName
     */
    public String getPackageName() {
        return PackageName;
    }

    /**
     * @param PackageName the PackageName to set
     */
    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    /**
     * @return the StartDate
     */
    public Date getStartDate() {
        return StartDate;
    }

    /**
     * @param StartDate the StartDate to set
     */
    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    /**
     * @return the EndDate
     */
    public Date getEndDate() {
        return EndDate;
    }

    /**
     * @param EndDate the EndDate to set
     */
    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    /**
     * @return the store_scope
     */
    public String getStore_scope() {
        return store_scope;
    }

    /**
     * @param store_scope the store_scope to set
     */
    public void setStore_scope(String store_scope) {
        this.store_scope = store_scope;
    }

    /**
     * @return the transactor_scope
     */
    public String getTransactor_scope() {
        return transactor_scope;
    }

    /**
     * @param transactor_scope the transactor_scope to set
     */
    public void setTransactor_scope(String transactor_scope) {
        this.transactor_scope = transactor_scope;
    }

    /**
     * @return the SelectedTransactors
     */
    public List<Transactor> getSelectedTransactors() {
        return SelectedTransactors;
    }

    /**
     * @param SelectedTransactors the SelectedTransactors to set
     */
    public void setSelectedTransactors(List<Transactor> SelectedTransactors) {
        this.SelectedTransactors = SelectedTransactors;
    }

    /**
     * @return the SelectedStores
     */
    public String[] getSelectedStores() {
        return SelectedStores;
    }

    /**
     * @param SelectedStores the SelectedStores to set
     */
    public void setSelectedStores(String[] SelectedStores) {
        this.SelectedStores = SelectedStores;
    }

    /**
     * @return the StatusColor
     */
    public String getStatusColor() {
        return StatusColor;
    }

    /**
     * @param StatusColor the StatusColor to set
     */
    public void setStatusColor(String StatusColor) {
        this.StatusColor = StatusColor;
    }

    /**
     * @return the segment_scope
     */
    public String getSegment_scope() {
        return segment_scope;
    }

    /**
     * @param segment_scope the segment_scope to set
     */
    public void setSegment_scope(String segment_scope) {
        this.segment_scope = segment_scope;
    }

    /**
     * @return the SelectedSegments
     */
    public String[] getSelectedSegments() {
        return SelectedSegments;
    }

    /**
     * @param SelectedSegments the SelectedSegments to set
     */
    public void setSelectedSegments(String[] SelectedSegments) {
        this.SelectedSegments = SelectedSegments;
    }

    /**
     * @return the day_scope
     */
    public String getDay_scope() {
        return day_scope;
    }

    /**
     * @param day_scope the day_scope to set
     */
    public void setDay_scope(String day_scope) {
        this.day_scope = day_scope;
    }

    /**
     * @return the SelectedDays
     */
    public String[] getSelectedDays() {
        return SelectedDays;
    }

    /**
     * @param SelectedDays the SelectedDays to set
     */
    public void setSelectedDays(String[] SelectedDays) {
        this.SelectedDays = SelectedDays;
    }

    /**
     * @return the time_scope_from
     */
    public String getTime_scope_from() {
        return time_scope_from;
    }

    /**
     * @param time_scope_from the time_scope_from to set
     */
    public void setTime_scope_from(String time_scope_from) {
        this.time_scope_from = time_scope_from;
    }

    /**
     * @return the time_scope_to
     */
    public String getTime_scope_to() {
        return time_scope_to;
    }

    /**
     * @param time_scope_to the time_scope_to to set
     */
    public void setTime_scope_to(String time_scope_to) {
        this.time_scope_to = time_scope_to;
    }

    /**
     * @return the FromTime
     */
    public Date getFromTime() {
        return FromTime;
    }

    /**
     * @param FromTime the FromTime to set
     */
    public void setFromTime(Date FromTime) {
        this.FromTime = FromTime;
    }

    /**
     * @return the ToTime
     */
    public Date getToTime() {
        return ToTime;
    }

    /**
     * @param ToTime the ToTime to set
     */
    public void setToTime(Date ToTime) {
        this.ToTime = ToTime;
    }
    
}
