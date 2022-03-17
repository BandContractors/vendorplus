package entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Shift implements Serializable {

    private static final long serialVersionUID = 1L;
    private int shift_id;
    private String shift_name;
    private String description;
    private LocalTime start_time;
    private LocalTime end_time;

    /**
     * @return the shift_id
     */
    public int getShift_id() {
        return shift_id;
    }

    /**
     * @param shift_id the shift_id to set
     */
    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    /**
     * @return the shift_name
     */
    public String getShift_name() {
        return shift_name;
    }

    /**
     * @param shift_name the shift_name to set
     */
    public void setShift_name(String shift_name) {
        this.shift_name = shift_name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the Description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the start_time
     */
    public LocalTime getStart_time() {
        return start_time;
    }

    /**
     * @param start_time the start_time to set
     */
    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    /**
     * @return the end_time
     */
    public LocalTime getEnd_time() {
        return end_time;
    }

    /**
     * @param end_time the end_time to set
     */
    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }
}
