package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Iso_country_code implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int iso_country_code_id;
    private String country_short_name;
    private String code_2;
    private String code_3;
    private String numeric_code;

    /**
     * @return the iso_country_code_id
     */
    public int getIso_country_code_id() {
        return iso_country_code_id;
    }

    /**
     * @param iso_country_code_id the iso_country_code_id to set
     */
    public void setIso_country_code_id(int iso_country_code_id) {
        this.iso_country_code_id = iso_country_code_id;
    }

    /**
     * @return the country_short_name
     */
    public String getCountry_short_name() {
        return country_short_name;
    }

    /**
     * @param country_short_name the country_short_name to set
     */
    public void setCountry_short_name(String country_short_name) {
        this.country_short_name = country_short_name;
    }

    /**
     * @return the code_2
     */
    public String getCode_2() {
        return code_2;
    }

    /**
     * @param code_2 the code_2 to set
     */
    public void setCode_2(String code_2) {
        this.code_2 = code_2;
    }

    /**
     * @return the code_3
     */
    public String getCode_3() {
        return code_3;
    }

    /**
     * @param code_3 the code_3 to set
     */
    public void setCode_3(String code_3) {
        this.code_3 = code_3;
    }

    /**
     * @return the numeric_code
     */
    public String getNumeric_code() {
        return numeric_code;
    }

    /**
     * @param numeric_code the numeric_code to set
     */
    public void setNumeric_code(String numeric_code) {
        this.numeric_code = numeric_code;
    }
}
