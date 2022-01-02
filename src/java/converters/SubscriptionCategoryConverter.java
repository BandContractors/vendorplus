package converters;

import beans.SubCategoryBean;
import beans.SubscriptionCategoryBean;
import entities.SubCategory;
import entities.Subscription_category;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("SubscriptionCategoryConverter")
public class SubscriptionCategoryConverter implements Converter {

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
        return new SubscriptionCategoryBean().getSubscriptionCategory(id);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "") {
            return null;
        }
        if (!(value instanceof Subscription_category)) {
            throw new ConverterException("The value is not a valid SubscriptionCategory: " + value);
        }

        Integer gID = ((Subscription_category) value).getSubscription_category_id();
        return (gID != null) ? String.valueOf(gID) : null;
    }

}
