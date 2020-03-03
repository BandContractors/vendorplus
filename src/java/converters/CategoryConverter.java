package converters;

import beans.CategoryBean;
import entities.Category;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("CategoryConverter")
public class CategoryConverter implements Converter {

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
        return new CategoryBean().getCategory(id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof Category)) {
            throw new ConverterException("The value is not a valid Category: " + value);
        }

        Integer gID = ((Category) value).getCategoryId();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
