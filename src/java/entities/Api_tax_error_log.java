package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Api_tax_error_log implements Serializable {

    private static final long serialVersionUID = 1L;
    private long api_tax_error_log_id;
    private long id_table;
    private String name_table;
    private int transaction_type_id;
    private int transaction_reason_id;
    private String error_desc;
    private Date error_date;

    /**
     * @return the api_tax_error_log_id
     */
    public long getApi_tax_error_log_id() {
        return api_tax_error_log_id;
    }

    /**
     * @param api_tax_error_log_id the api_tax_error_log_id to set
     */
    public void setApi_tax_error_log_id(long api_tax_error_log_id) {
        this.api_tax_error_log_id = api_tax_error_log_id;
    }

    /**
     * @return the id_table
     */
    public long getId_table() {
        return id_table;
    }

    /**
     * @param id_table the id_table to set
     */
    public void setId_table(long id_table) {
        this.id_table = id_table;
    }

    /**
     * @return the name_table
     */
    public String getName_table() {
        return name_table;
    }

    /**
     * @param name_table the name_table to set
     */
    public void setName_table(String name_table) {
        this.name_table = name_table;
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
     * @return the error_desc
     */
    public String getError_desc() {
        return error_desc;
    }

    /**
     * @param error_desc the error_desc to set
     */
    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }

    /**
     * @return the error_date
     */
    public Date getError_date() {
        return error_date;
    }

    /**
     * @param error_date the error_date to set
     */
    public void setError_date(Date error_date) {
        this.error_date = error_date;
    }
}
