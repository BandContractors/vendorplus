package converters;

import beans.BusinessCategoryBean;
import entities.Business_category;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("BusinessCategoryConverter")
public class BusinessCategoryConverter implements Converter {

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
        return new BusinessCategoryBean().getBusinessCategory(id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof Business_category)) {
            throw new ConverterException("The value is not a valid BusinessCategory: " + value);
        }

        Integer gID = ((Business_category) value).getBusiness_category_id();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
