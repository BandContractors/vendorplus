package converters;

import beans.AccCoaBean;
import entities.AccCoa;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("AccCoaConverter")
public class AccCoaConverter implements Converter {

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
        return new AccCoaBean().getAccCoaByCodeOrId("", id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof AccCoa)) {
            throw new ConverterException("The value is not a valid AccCoa: " + value);
        }

        Integer gID = ((AccCoa) value).getAccCoaId();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
