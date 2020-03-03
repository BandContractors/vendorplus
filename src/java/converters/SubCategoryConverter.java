package converters;

import beans.SubCategoryBean;
import entities.SubCategory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("SubCategoryConverter")
public class SubCategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }
        int id;
        try {
            id = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return new SubCategoryBean().getSubCategory(id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof SubCategory)) {
            throw new ConverterException("The value is not a valid SubCategory: " + value);
        }

        Integer gID = ((SubCategory) value).getSubCategoryId();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
