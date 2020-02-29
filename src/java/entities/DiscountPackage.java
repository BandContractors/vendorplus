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
    
}
