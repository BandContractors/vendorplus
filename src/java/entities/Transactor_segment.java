package entities;


import java.io.Serializable;
import javax.faces.bean.*;

@ManagedBean
@SessionScoped
public class Transactor_segment implements Serializable {

    private static final long serialVersionUID = 1L;
    private int transactor_segment_id;
    private String segment_name;

    /**
     * @return the transactor_segment_id
     */
    public int getTransactor_segment_id() {
        return transactor_segment_id;
    }

    /**
     * @param transactor_segment_id the transactor_segment_id to set
     */
    public void setTransactor_segment_id(int transactor_segment_id) {
        this.transactor_segment_id = transactor_segment_id;
    }

    /**
     * @return the segment_name
     */
    public String getSegment_name() {
        return segment_name;
    }

    /**
     * @param segment_name the segment_name to set
     */
    public void setSegment_name(String segment_name) {
        this.segment_name = segment_name;
    }

}
