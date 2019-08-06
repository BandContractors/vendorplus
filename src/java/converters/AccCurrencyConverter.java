package converters;

import beans.AccCurrencyBean;
import entities.AccCurrency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("AccCurrencyConverter")
public class AccCurrencyConverter implements Converter {

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
        return new AccCurrencyBean().getCurrency(id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof AccCurrency)) {
            throw new ConverterException("The value is not a valid AccCurrency: " + value);
        }

        Integer gID = ((AccCurrency) value).getAccCurrencyId();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
