package converters;

import beans.AccChildAccountBean;
import entities.AccChildAccount;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("AccChildAccountConverter")
public class AccChildAccountConverter implements Converter {

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
        return new AccChildAccountBean().getAccChildAccById(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof AccChildAccount)) {
            throw new ConverterException("The value is not a valid AccChildAccount: " + value);
        }

        Integer gID = ((AccChildAccount) value).getAccChildAccountId();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
