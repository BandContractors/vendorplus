package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Subscription_log implements Serializable {

    private static final long serialVersionUID = 1L;
    private long subscription_log_id;
    private Date add_date;
    private String added_by;
    private int subscription_id;
    private String action;

    /**
     * @return the subscription_log_id
     */
    public long getSubscription_log_id() {
        return subscription_log_id;
    }

    /**
     * @param subscription_log_id the subscription_log_id to set
     */
    public void setSubscription_log_id(long subscription_log_id) {
        this.subscription_log_id = subscription_log_id;
    }

    /**
     * @return the add_date
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * @param add_date the add_date to set
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * @return the added_by
     */
    public String getAdded_by() {
        return added_by;
    }

    /**
     * @param added_by the added_by to set
     */
    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    /**
     * @return the subscription_id
     */
    public int getSubscription_id() {
        return subscription_id;
    }

    /**
     * @param subscription_id the subscription_id to set
     */
    public void setSubscription_id(int subscription_id) {
        this.subscription_id = subscription_id;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }
}
