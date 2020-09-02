package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Transaction_tax_map implements Serializable {

    private static final long serialVersionUID = 1L;
    private long transaction_tax_map_id;
    private long transaction_id;
    private int transaction_type_id;
    private int transaction_reason_id;
    private String transaction_number;
    private String transaction_number_tax;
    private Date add_date;
    private int is_updated;
    private int update_synced;
    private String qr_code_tax;
    private String verification_code_tax;
    private String update_type;
    private String transaction_number_tax_update;
    private String qr_code_tax_update;
    private String verification_code_tax_update;
    private int is_updated_more_than_once;
    private int more_than_once_update_reconsiled;

    /**
     * @return the transaction_tax_map_id
     */
    public long getTransaction_tax_map_id() {
        return transaction_tax_map_id;
    }

    /**
     * @param transaction_tax_map_id the transaction_tax_map_id to set
     */
    public void setTransaction_tax_map_id(long transaction_tax_map_id) {
        this.transaction_tax_map_id = transaction_tax_map_id;
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
     * @return the transaction_number_tax
     */
    public String getTransaction_number_tax() {
        return transaction_number_tax;
    }

    /**
     * @param transaction_number_tax the transaction_number_tax to set
     */
    public void setTransaction_number_tax(String transaction_number_tax) {
        this.transaction_number_tax = transaction_number_tax;
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
     * @return the is_updated
     */
    public int getIs_updated() {
        return is_updated;
    }

    /**
     * @param is_updated the is_updated to set
     */
    public void setIs_updated(int is_updated) {
        this.is_updated = is_updated;
    }

    /**
     * @return the update_synced
     */
    public int getUpdate_synced() {
        return update_synced;
    }

    /**
     * @param update_synced the update_synced to set
     */
    public void setUpdate_synced(int update_synced) {
        this.update_synced = update_synced;
    }

    /**
     * @return the qr_code_tax
     */
    public String getQr_code_tax() {
        return qr_code_tax;
    }

    /**
     * @param qr_code_tax the qr_code_tax to set
     */
    public void setQr_code_tax(String qr_code_tax) {
        this.qr_code_tax = qr_code_tax;
    }

    /**
     * @return the verification_code_tax
     */
    public String getVerification_code_tax() {
        return verification_code_tax;
    }

    /**
     * @param verification_code_tax the verification_code_tax to set
     */
    public void setVerification_code_tax(String verification_code_tax) {
        this.verification_code_tax = verification_code_tax;
    }

    /**
     * @return the update_type
     */
    public String getUpdate_type() {
        return update_type;
    }

    /**
     * @param update_type the update_type to set
     */
    public void setUpdate_type(String update_type) {
        this.update_type = update_type;
    }

    /**
     * @return the transaction_number_tax_update
     */
    public String getTransaction_number_tax_update() {
        return transaction_number_tax_update;
    }

    /**
     * @param transaction_number_tax_update the transaction_number_tax_update to
     * set
     */
    public void setTransaction_number_tax_update(String transaction_number_tax_update) {
        this.transaction_number_tax_update = transaction_number_tax_update;
    }

    /**
     * @return the qr_code_tax_update
     */
    public String getQr_code_tax_update() {
        return qr_code_tax_update;
    }

    /**
     * @param qr_code_tax_update the qr_code_tax_update to set
     */
    public void setQr_code_tax_update(String qr_code_tax_update) {
        this.qr_code_tax_update = qr_code_tax_update;
    }

    /**
     * @return the verification_code_tax_update
     */
    public String getVerification_code_tax_update() {
        return verification_code_tax_update;
    }

    /**
     * @param verification_code_tax_update the verification_code_tax_update to
     * set
     */
    public void setVerification_code_tax_update(String verification_code_tax_update) {
        this.verification_code_tax_update = verification_code_tax_update;
    }

    /**
     * @return the is_updated_more_than_once
     */
    public int getIs_updated_more_than_once() {
        return is_updated_more_than_once;
    }

    /**
     * @param is_updated_more_than_once the is_updated_more_than_once to set
     */
    public void setIs_updated_more_than_once(int is_updated_more_than_once) {
        this.is_updated_more_than_once = is_updated_more_than_once;
    }

    /**
     * @return the more_than_once_update_reconsiled
     */
    public int getMore_than_once_update_reconsiled() {
        return more_than_once_update_reconsiled;
    }

    /**
     * @param more_than_once_update_reconsiled the
     * more_than_once_update_reconsiled to set
     */
    public void setMore_than_once_update_reconsiled(int more_than_once_update_reconsiled) {
        this.more_than_once_update_reconsiled = more_than_once_update_reconsiled;
    }
}
