package converters;

import beans.UnitBean;
import entities.Unit_tax_list;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("Unit_tax_listConverter")
public class Unit_tax_listConverter implements Converter {

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
        return new UnitBean().findUnit_tax_list(id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof Unit_tax_list)) {
            throw new ConverterException("The value is not a valid Unit_tax_list: " + value);
        }

        Integer gID = ((Unit_tax_list) value).getUnit_tax_list_id();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
