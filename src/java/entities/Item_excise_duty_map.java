package entities;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Item_excise_duty_map implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    private long item_excise_duty_map_id;
    private long item_id;
    private String excise_duty_code;

    /**
     * @return the item_excise_duty_map_id
     */
    public long getItem_excise_duty_map_id() {
        return item_excise_duty_map_id;
    }

    /**
     * @param item_excise_duty_map_id the item_excise_duty_map_id to set
     */
    public void setItem_excise_duty_map_id(long item_excise_duty_map_id) {
        this.item_excise_duty_map_id = item_excise_duty_map_id;
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
     * @return the excise_duty_code
     */
    public String getExcise_duty_code() {
        return excise_duty_code;
    }

    /**
     * @param excise_duty_code the excise_duty_code to set
     */
    public void setExcise_duty_code(String excise_duty_code) {
        this.excise_duty_code = excise_duty_code;
    }
}
