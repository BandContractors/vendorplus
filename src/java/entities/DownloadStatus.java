package entities;


import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class DownloadStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    private int download_status_id;
    private String download_name;
    private int download_status;
    private String download_status_msg;
    private double total_amount;
    private double total_downloaded;
    private Date add_date;

    /**
     * @return the download_status_id
     */
    public int getDownload_status_id() {
        return download_status_id;
    }

    /**
     * @param download_status_id the download_status_id to set
     */
    public void setDownload_status_id(int download_status_id) {
        this.download_status_id = download_status_id;
    }

    /**
     * @return the download_name
     */
    public String getDownload_name() {
        return download_name;
    }

    /**
     * @param download_name the download_name to set
     */
    public void setDownload_name(String download_name) {
        this.download_name = download_name;
    }

    /**
     * @return the download_status
     */
    public int getDownload_status() {
        return download_status;
    }

    /**
     * @param download_status the download_status to set
     */
    public void setDownload_status(int download_status) {
        this.download_status = download_status;
    }

    /**
     * @return the download_status_msg
     */
    public String getDownload_status_msg() {
        return download_status_msg;
    }

    /**
     * @param download_status_msg the download_status_msg to set
     */
    public void setDownload_status_msg(String download_status_msg) {
        this.download_status_msg = download_status_msg;
    }

    /**
     * @return the total_amount
     */
    public double getTotal_amount() {
        return total_amount;
    }

    /**
     * @param total_amount the total_amount to set
     */
    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    /**
     * @return the total_downloaded
     */
    public double getTotal_downloaded() {
        return total_downloaded;
    }

    /**
     * @param total_downloaded the total_downloaded to set
     */
    public void setTotal_downloaded(double total_downloaded) {
        this.total_downloaded = total_downloaded;
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
}
