package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Transaction_smbi_map implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_smbi_map_id;
    private long transaction_id;
    private int transaction_type_id;
    private int transaction_reason_id;
    private String transaction_number;
    private Date add_date;
    private int status_sync;
    private Date status_date;
    private String status_desc;

    /**
     * @return the transaction_smbi_map_id
     */
    public long getTransaction_smbi_map_id() {
        return transaction_smbi_map_id;
    }

    /**
     * @param transaction_smbi_map_id the transaction_smbi_map_id to set
     */
    public void setTransaction_smbi_map_id(long transaction_smbi_map_id) {
        this.transaction_smbi_map_id = transaction_smbi_map_id;
    }

    /**
     * @return the transaction_id
     */
    public long getTransaction_id() {
        return transaction_id;
    }

    /**
     * @param transaction_id the transaction_id to set
     */
    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    /**
     * @return the transaction_type_id
     */
    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    /**
     * @param transaction_type_id the transaction_type_id to set
     */
    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    /**
     * @return the transaction_reason_id
     */
    public int getTransaction_reason_id() {
        return transaction_reason_id;
    }

    /**
     * @param transaction_reason_id the transaction_reason_id to set
     */
    public void setTransaction_reason_id(int transaction_reason_id) {
        this.transaction_reason_id = transaction_reason_id;
    }

    /**
     * @return the transaction_number
     */
    public String getTransaction_number() {
        return transaction_number;
    }

    /**
     * @param transaction_number the transaction_number to set
     */
    public void setTransaction_number(String transaction_number) {
        this.transaction_number = transaction_number;
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
     * @return the status_sync
     */
    public int getStatus_sync() {
        return status_sync;
    }

    /**
     * @param status_sync the status_sync to set
     */
    public void setStatus_sync(int status_sync) {
        this.status_sync = status_sync;
    }

    /**
     * @return the status_date
     */
    public Date getStatus_date() {
        return status_date;
    }

    /**
     * @param status_date the status_date to set
     */
    public void setStatus_date(Date status_date) {
        this.status_date = status_date;
    }

    /**
     * @return the status_desc
     */
    public String getStatus_desc() {
        return status_desc;
    }

    /**
     * @param status_desc the status_desc to set
     */
    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
}
