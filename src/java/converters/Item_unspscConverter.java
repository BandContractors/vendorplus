package converters;

import beans.ItemBean;
import entities.Item_unspsc;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("Item_unspscConverter")
public class Item_unspscConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }
        Long id;
        try {
            id = Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return new ItemBean().findItem_unspsc(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof Item_unspsc)) {
            throw new ConverterException("The value is not a valid Item_unspsc: " + value);
        }

        Long gID = ((Item_unspsc) value).getItem_unspsc_id();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
