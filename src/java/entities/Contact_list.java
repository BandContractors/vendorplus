package entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author btwesigye
 */
@ManagedBean
@SessionScoped
public class Contact_list implements Serializable {

    private static final long serialVersionUID = 1L;
    private int Contact_list_id;
    private String Category;
    private String Subcategory;
    private String Email1;
    private String Email2;
    private String Company_name;
    private String Title;
    private String First_name;
    private String Second_name;
    private String Phone1;
    private String Phone2;
    private String Source;
    private String Type;
    private String Loc_level1;
    private String Loc_level2;
    private String Loc_level3;
    private String Loc_level4;
    private String Address;

    /**
     * @return the Category
     */
    public String getCategory() {
        return Category;
    }

    /**
     * @param Category the Category to set
     */
    public void setCategory(String Category) {
        this.Category = Category;
    }

    /**
     * @return the Subcategory
     */
    public String getSubcategory() {
        return Subcategory;
    }

    /**
     * @param Subcategory the Subcategory to set
     */
    public void setSubcategory(String Subcategory) {
        this.Subcategory = Subcategory;
    }

    /**
     * @return the Email1
     */
    public String getEmail1() {
        return Email1;
    }

    /**
     * @param Email1 the Email1 to set
     */
    public void setEmail1(String Email1) {
        this.Email1 = Email1;
    }

    /**
     * @return the Email2
     */
    public String getEmail2() {
        return Email2;
    }

    /**
     * @param Email2 the Email2 to set
     */
    public void setEmail2(String Email2) {
        this.Email2 = Email2;
    }

    /**
     * @return the Contact_list_id
     */
    public int getContact_list_id() {
        return Contact_list_id;
    }

    /**
     * @param Contact_list_id the Contact_list_id to set
     */
    public void setContact_list_id(int Contact_list_id) {
        this.Contact_list_id = Contact_list_id;
    }

    /**
     * @return the Company_name
     */
    public String getCompany_name() {
        return Company_name;
    }

    /**
     * @param Company_name the Company_name to set
     */
    public void setCompany_name(String Company_name) {
        this.Company_name = Company_name;
    }

    /**
     * @return the Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title the Title to set
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return the First_name
     */
    public String getFirst_name() {
        return First_name;
    }

    /**
     * @param First_name the First_name to set
     */
    public void setFirst_name(String First_name) {
        this.First_name = First_name;
    }

    /**
     * @return the Second_name
     */
    public String getSecond_name() {
        return Second_name;
    }

    /**
     * @param Second_name the Second_name to set
     */
    public void setSecond_name(String Second_name) {
        this.Second_name = Second_name;
    }

    /**
     * @return the Phone1
     */
    public String getPhone1() {
        return Phone1;
    }

    /**
     * @param Phone1 the Phone1 to set
     */
    public void setPhone1(String Phone1) {
        this.Phone1 = Phone1;
    }

    /**
     * @return the Phone2
     */
    public String getPhone2() {
        return Phone2;
    }

    /**
     * @param Phone2 the Phone2 to set
     */
    public void setPhone2(String Phone2) {
        this.Phone2 = Phone2;
    }

    /**
     * @return the Source
     */
    public String getSource() {
        return Source;
    }

    /**
     * @param Source the Source to set
     */
    public void setSource(String Source) {
        this.Source = Source;
    }

    /**
     * @return the Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type the Type to set
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return the Loc_level1
     */
    public String getLoc_level1() {
        return Loc_level1;
    }

    /**
     * @param Loc_level1 the Loc_level1 to set
     */
    public void setLoc_level1(String Loc_level1) {
        this.Loc_level1 = Loc_level1;
    }

    /**
     * @return the Loc_level2
     */
    public String getLoc_level2() {
        return Loc_level2;
    }

    /**
     * @param Loc_level2 the Loc_level2 to set
     */
    public void setLoc_level2(String Loc_level2) {
        this.Loc_level2 = Loc_level2;
    }

    /**
     * @return the Loc_level3
     */
    public String getLoc_level3() {
        return Loc_level3;
    }

    /**
     * @param Loc_level3 the Loc_level3 to set
     */
    public void setLoc_level3(String Loc_level3) {
        this.Loc_level3 = Loc_level3;
    }

    /**
     * @return the Loc_level4
     */
    public String getLoc_level4() {
        return Loc_level4;
    }

    /**
     * @param Loc_level4 the Loc_level4 to set
     */
    public void setLoc_level4(String Loc_level4) {
        this.Loc_level4 = Loc_level4;
    }

    /**
     * @return the Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param Address the Address to set
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

}
