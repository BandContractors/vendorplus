package api_sm_bi;

import beans.Parameter_listBean;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import javax.faces.bean.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class CheckApiBean implements Serializable {

    private static final long serialVersionUID = 1L;
    static Logger LOGGER = Logger.getLogger(CheckApiBean.class.getName());

    public boolean IsSmBiAvailable() {
        boolean isavailbale = false;
        try {
            com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
            WebResource webResource = client.resource(new Parameter_listBean().getParameter_listByContextName("API", "API_SMBI_URL").getParameter_value());
            String json = "{\"transactionType\":\"CHECK API\"}";
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);
            int statuscode = response.getStatus();
            if (statuscode == 200) {
                isavailbale = true;
            }
        } catch (Exception e) {
            isavailbale = false;
        }
        return isavailbale;
    }
}
