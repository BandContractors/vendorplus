package beans;

import entities.test;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class testBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Integer> rws;
    private List<Integer> cls;
    Integer r = 0;
    Integer c = 0;
    private List<Integer> rws2;
    private List<Integer> cls2;
    private Integer r2 = 0;
    private Integer c2 = 0;
    private test[][] cellsArray;
    private test[][] cellsArray2;

    public void initTest() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            //for rows and cols
            Integer rowscount = 4;
            Integer colscount = 6;
            rws = new ArrayList<>();
            for (Integer i = 1; i <= rowscount; i++) {
                rws.add(i);
            }
            cls = new ArrayList<>();
            for (Integer i = 1; i <= colscount; i++) {
                cls.add(i);
            }
            this.cellsArray = new test[rowscount+1][colscount+1];
            test t;
            String rcs;
            //r = 0;
            for (r = 0; r <= rowscount; r++) {
                if (r == 0) {
                    continue;
                }
                if(r==1){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("");
                    this.cellsArray[r][1] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Male<18");
                    this.cellsArray[r][2] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Male>=18");
                    this.cellsArray[r][3] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Female<18");
                    this.cellsArray[r][4] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Male>=18");
                    this.cellsArray[r][5] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Total");
                    this.cellsArray[r][6] = t;
                }
                if(r==2){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Malaria");
                    this.cellsArray[r][1] = t;
                    t = new test();
                    t.setDe_id(22);
                    t.setLabel_name("");
                    this.cellsArray[r][2] = t;
                    t = new test();
                    t.setDe_id(23);
                    t.setLabel_name("");
                    this.cellsArray[r][3] = t;
                    t = new test();
                    t.setDe_id(24);
                    t.setLabel_name("");
                    this.cellsArray[r][4] = t;
                    t = new test();
                    t.setDe_id(25);
                    t.setLabel_name("");
                    this.cellsArray[r][5] = t;
                    t = new test();
                    t.setDe_id(26);
                    t.setLabel_name("");
                    this.cellsArray[r][6] = t;
                }
                if(r==3){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Dysentry");
                    this.cellsArray[r][1] = t;
                    t = new test();
                    t.setDe_id(32);
                    t.setLabel_name("");
                    this.cellsArray[r][2] = t;
                    t = new test();
                    t.setDe_id(33);
                    t.setLabel_name("");
                    this.cellsArray[r][3] = t;
                    t = new test();
                    t.setDe_id(34);
                    t.setLabel_name("");
                    this.cellsArray[r][4] = t;
                    t = new test();
                    t.setDe_id(35);
                    t.setLabel_name("");
                    this.cellsArray[r][5] = t;
                    t = new test();
                    t.setDe_id(36);
                    t.setLabel_name("");
                    this.cellsArray[r][6] = t;
                }
                if(r==4){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("BCG");
                    this.cellsArray[r][1] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("");
                    this.cellsArray[r][2] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("");
                    this.cellsArray[r][3] = t;
                    t = new test();
                    t.setDe_id(44);
                    t.setLabel_name("");
                    this.cellsArray[r][4] = t;
                    t = new test();
                    t.setDe_id(45);
                    t.setLabel_name("");
                    this.cellsArray[r][5] = t;
                    t = new test();
                    t.setDe_id(46);
                    t.setLabel_name("");
                    this.cellsArray[r][6] = t;
                }
            }
        }
    }
    
    public void initTest2() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            // Skip ajax requests.
        } else {
            //for rows and cols
            Integer rowscount2 = 5;
            Integer colscount2 = 6;
            rws2 = new ArrayList<>();
            for (Integer i = 1; i <= rowscount2; i++) {
                rws2.add(i);
            }
            cls2 = new ArrayList<>();
            for (Integer i = 1; i <= colscount2; i++) {
                cls2.add(i);
            }
            this.cellsArray2 = new test[rowscount2+1][colscount2+1];
            test t;
            String rcs;
            //r = 0;
            for (r2 = 0; r2 <= rowscount2; r2++) {
                if (r2 == 0) {
                    continue;
                }
                if(r2==1){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    t.setRow_span(2);
                    this.cellsArray2[r2][1] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("MALE");
                    t.setCol_span(2);
                    this.cellsArray2[r2][2] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("FEMALE");
                    t.setCol_span(2);
                    this.cellsArray2[r2][3] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("TOTAL");
                    t.setCol_span(1);
                    this.cellsArray2[r2][4] = t;
                }
                if(r2==2){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][1] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("<18");
                    t.setCol_span(1);
                    this.cellsArray2[r2][2] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name(">=18");
                    t.setCol_span(1);
                    this.cellsArray2[r2][3] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("<18");
                    t.setCol_span(1);
                    this.cellsArray2[r2][4] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name(">=18");
                    t.setCol_span(1);
                    this.cellsArray2[r2][5] = t;
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][6] = t;
                }
                if(r2==3){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Malaria");
                    t.setCol_span(1);
                    this.cellsArray2[r2][1] = t;
                    t = new test();
                    t.setDe_id(32);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][2] = t;
                    t = new test();
                    t.setDe_id(33);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][3] = t;
                    t = new test();
                    t.setDe_id(34);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][4] = t;
                    t = new test();
                    t.setDe_id(35);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][5] = t;
                    t = new test();
                    t.setDe_id(36);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][6] = t;
                }
                if(r2==4){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("Dysentry");
                    t.setCol_span(1);
                    this.cellsArray2[r2][1] = t;
                    t = new test();
                    t.setDe_id(42);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][2] = t;
                    t = new test();
                    t.setDe_id(43);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][3] = t;
                    t = new test();
                    t.setDe_id(44);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][4] = t;
                    t = new test();
                    t.setDe_id(45);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][5] = t;
                    t = new test();
                    t.setDe_id(46);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][6] = t;
                }
                if(r2==5){
                    t = new test();
                    t.setDe_id(0);
                    t.setLabel_name("BCG");
                    t.setCol_span(1);
                    this.cellsArray2[r2][1] = t;
                    t = new test();
                    t.setDe_id(52);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][2] = t;
                    t = new test();
                    t.setDe_id(53);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][3] = t;
                    t = new test();
                    t.setDe_id(54);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][4] = t;
                    t = new test();
                    t.setDe_id(55);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][5] = t;
                    t = new test();
                    t.setDe_id(56);
                    t.setLabel_name("");
                    t.setCol_span(1);
                    this.cellsArray2[r2][6] = t;
                }
            }
        }
    }

    public test getCell(Integer rn, Integer cn) {
        test t = new test();
        t = this.cellsArray[rn][cn];
        return t;
    }
    
    public test getCell2(Integer rn, Integer cn) {
        test t = new test();
        t = this.cellsArray2[rn][cn];
        return t;
    }

    /**
     * @return the rws
     */
    public List<Integer> getRws() {
        return rws;
    }

    /**
     * @param rws the rws to set
     */
    public void setRws(List<Integer> rws) {
        this.rws = rws;
    }

    /**
     * @return the cls
     */
    public List<Integer> getCls() {
        return cls;
    }

    /**
     * @param cls the cls to set
     */
    public void setCls(List<Integer> cls) {
        this.cls = cls;
    }

    /**
     * @return the cellsArray
     */
    public test[][] getCellsArray() {
        return cellsArray;
    }

    /**
     * @param cellsArray the cellsArray to set
     */
    public void setCellsArray(test[][] cellsArray) {
        this.cellsArray = cellsArray;
    }

    /**
     * @return the cellsArray2
     */
    public test[][] getCellsArray2() {
        return cellsArray2;
    }

    /**
     * @param cellsArray2 the cellsArray2 to set
     */
    public void setCellsArray2(test[][] cellsArray2) {
        this.cellsArray2 = cellsArray2;
    }

    /**
     * @return the rws2
     */
    public List<Integer> getRws2() {
        return rws2;
    }

    /**
     * @param rws2 the rws2 to set
     */
    public void setRws2(List<Integer> rws2) {
        this.rws2 = rws2;
    }

    /**
     * @return the cls2
     */
    public List<Integer> getCls2() {
        return cls2;
    }

    /**
     * @param cls2 the cls2 to set
     */
    public void setCls2(List<Integer> cls2) {
        this.cls2 = cls2;
    }

    /**
     * @return the r2
     */
    public Integer getR2() {
        return r2;
    }

    /**
     * @param r2 the r2 to set
     */
    public void setR2(Integer r2) {
        this.r2 = r2;
    }

    /**
     * @return the c2
     */
    public Integer getC2() {
        return c2;
    }

    /**
     * @param c2 the c2 to set
     */
    public void setC2(Integer c2) {
        this.c2 = c2;
    }
}
