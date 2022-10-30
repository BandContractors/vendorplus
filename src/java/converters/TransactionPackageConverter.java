package converters;

import beans.ItemBean;
import beans.TransactionPackageBean;
import entities.Item;
import entities.TransactionPackage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("TransactionPackageConverter")
public class TransactionPackageConverter implements Converter {  
    
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
        return new TransactionPackageBean().findTransactionPackage(id);
        
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof TransactionPackage)) {
            throw new ConverterException("The value is not a valid Item: " + value);
        }
        
        Long gID = ((TransactionPackage) value).getTransactionPackageId();
        return (gID != null) ? String.valueOf(gID) : null;
    }
    
}
