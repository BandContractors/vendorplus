package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Subscription_log implements Serializable {

    private static final long serialVersionUID = 1L;
    private long subscription_log_id;
    private int subscription_id;
    private long transactor_id;
    private int subscription_category_id;
    private int business_category_id;
    private long item_id;
    private String description;
    private double amount;
    private String is_recurring;
    private String current_status;
    private String frequency;
    private double unit_price;
    private double qty;
    private String agent;
    private String account_manager;
    private Date subscription_date;
    private Date renewal_date;
    private Date expiry_date;
    private int free_at_reg;
    private double commission_amount;
    private String action;
    private Date add_date;
    private String added_by;

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

    /**
     * @return the transactor_id
     */
    public long getTransactor_id() {
        return transactor_id;
    }

    /**
     * @param transactor_id the transactor_id to set
     */
    public void setTransactor_id(long transactor_id) {
        this.transactor_id = transactor_id;
    }

    /**
     * @return the subscription_category_id
     */
    public int getSubscription_category_id() {
        return subscription_category_id;
    }

    /**
     * @param subscription_category_id the subscription_category_id to set
     */
    public void setSubscription_category_id(int subscription_category_id) {
        this.subscription_category_id = subscription_category_id;
    }

    /**
     * @return the business_category_id
     */
    public int getBusiness_category_id() {
        return business_category_id;
    }

    /**
     * @param business_category_id the business_category_id to set
     */
    public void setBusiness_category_id(int business_category_id) {
        this.business_category_id = business_category_id;
    }

    /**
     * @return the item_id
     */
    public long getItem_id() {
        return item_id;
    }

    /**
     * @param item_id the item_id to set
     */
    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the is_recurring
     */
    public String getIs_recurring() {
        return is_recurring;
    }

    /**
     * @param is_recurring the is_recurring to set
     */
    public void setIs_recurring(String is_recurring) {
        this.is_recurring = is_recurring;
    }

    /**
     * @return the current_status
     */
    public String getCurrent_status() {
        return current_status;
    }

    /**
     * @param current_status the current_status to set
     */
    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    /**
     * @return the frequency
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * @return the unit_price
     */
    public double getUnit_price() {
        return unit_price;
    }

    /**
     * @param unit_price the unit_price to set
     */
    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    /**
     * @return the agent
     */
    public String getAgent() {
        return agent;
    }

    /**
     * @param agent the agent to set
     */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    /**
     * @return the account_manager
     */
    public String getAccount_manager() {
        return account_manager;
    }

    /**
     * @param account_manager the account_manager to set
     */
    public void setAccount_manager(String account_manager) {
        this.account_manager = account_manager;
    }

    /**
     * @return the subscription_date
     */
    public Date getSubscription_date() {
        return subscription_date;
    }

    /**
     * @param subscription_date the subscription_date to set
     */
    public void setSubscription_date(Date subscription_date) {
        this.subscription_date = subscription_date;
    }

    /**
     * @return the renewal_date
     */
    public Date getRenewal_date() {
        return renewal_date;
    }

    /**
     * @param renewal_date the renewal_date to set
     */
    public void setRenewal_date(Date renewal_date) {
        this.renewal_date = renewal_date;
    }

    /**
     * @return the expiry_date
     */
    public Date getExpiry_date() {
        return expiry_date;
    }

    /**
     * @param expiry_date the expiry_date to set
     */
    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    /**
     * @return the free_at_reg
     */
    public int getFree_at_reg() {
        return free_at_reg;
    }

    /**
     * @param free_at_reg the free_at_reg to set
     */
    public void setFree_at_reg(int free_at_reg) {
        this.free_at_reg = free_at_reg;
    }

    /**
     * @return the commission_amount
     */
    public double getCommission_amount() {
        return commission_amount;
    }

    /**
     * @param commission_amount the commission_amount to set
     */
    public void setCommission_amount(double commission_amount) {
        this.commission_amount = commission_amount;
    }
}
