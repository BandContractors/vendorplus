package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class test implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String label_name;
    private Integer row_no;
    private Integer col_no;
    private Integer col_span;
    private Integer row_span;
    private String text_color;
    private String cell_color;
    private boolean read_only;
    private Integer de_id;

    /**
     * @return the label_name
     */
    public String getLabel_name() {
        return label_name;
    }

    /**
     * @param label_name the label_name to set
     */
    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    /**
     * @return the row_no
     */
    public Integer getRow_no() {
        return row_no;
    }

    /**
     * @param row_no the row_no to set
     */
    public void setRow_no(Integer row_no) {
        this.row_no = row_no;
    }

    /**
     * @return the col_no
     */
    public Integer getCol_no() {
        return col_no;
    }

    /**
     * @param col_no the col_no to set
     */
    public void setCol_no(Integer col_no) {
        this.col_no = col_no;
    }

    /**
     * @return the col_span
     */
    public Integer getCol_span() {
        return col_span;
    }

    /**
     * @param col_span the col_span to set
     */
    public void setCol_span(Integer col_span) {
        this.col_span = col_span;
    }

    /**
     * @return the row_span
     */
    public Integer getRow_span() {
        return row_span;
    }

    /**
     * @param row_span the row_span to set
     */
    public void setRow_span(Integer row_span) {
        this.row_span = row_span;
    }

    /**
     * @return the text_color
     */
    public String getText_color() {
        return text_color;
    }

    /**
     * @param text_color the text_color to set
     */
    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    /**
     * @return the cell_color
     */
    public String getCell_color() {
        return cell_color;
    }

    /**
     * @param cell_color the cell_color to set
     */
    public void setCell_color(String cell_color) {
        this.cell_color = cell_color;
    }

    /**
     * @return the read_only
     */
    public boolean isRead_only() {
        return read_only;
    }

    /**
     * @param read_only the read_only to set
     */
    public void setRead_only(boolean read_only) {
        this.read_only = read_only;
    }

    /**
     * @return the de_id
     */
    public Integer getDe_id() {
        return de_id;
    }

    /**
     * @param de_id the de_id to set
     */
    public void setDe_id(Integer de_id) {
        this.de_id = de_id;
    }
    
}
