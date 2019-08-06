package utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.*;
import sessions.GeneralUserSetting;

@ManagedBean
@SessionScoped
public class ListOptionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<ListOption> getItemSaleOptions() {
        List<ListOption> IPs = new ArrayList<>();
        String ItemPurpose = "";
        ItemPurpose = new GeneralUserSetting().getCurrentItemPurpose();
        ListOption IP = null;
        if (ItemPurpose.equals("Stock")) {
            IP = new ListOption();
            IP.setIntOption1(1);
            IP.setStrOption2("Yes");
            IPs.add(IP);
        }else{
            IP = new ListOption();
            IP.setIntOption1(0);
            IP.setStrOption2("No");
            IPs.add(IP);
        }
        return IPs;
    }
    
    public List<ListOption> getItemAssetOptions() {
        List<ListOption> IPs = new ArrayList<>();
        String ItemPurpose = "";
        ItemPurpose = new GeneralUserSetting().getCurrentItemPurpose();
        ListOption IP = null;
        if (ItemPurpose.equals("Asset")) {
            IP = new ListOption();
            IP.setIntOption1(1);
            IP.setStrOption2("Yes");
            IPs.add(IP);
        }else{
            IP = new ListOption();
            IP.setIntOption1(0);
            IP.setStrOption2("No");
            IPs.add(IP);
        }
        return IPs;
    }
    
    public List<ListOption> getItemHireOptions() {
        List<ListOption> IPs = new ArrayList<>();
        String ItemPurpose = "";
        ItemPurpose = new GeneralUserSetting().getCurrentItemPurpose();
        ListOption IP = null;
        if (ItemPurpose.equals("Asset") || ItemPurpose.equals("Stock")) {
            IP = new ListOption();
            IP.setIntOption1(1);
            IP.setStrOption2("Yes");
            IPs.add(IP);
        }else{
            IP = new ListOption();
            IP.setIntOption1(0);
            IP.setStrOption2("No");
            IPs.add(IP);
        }
        return IPs;
    }
    
    public String getColorByItemsNo(int aSize) {
        if(aSize<=1){
            return "lightslategrey";
        }else{
            return "white";
        }
    }
    
    public String getColorByBoolean(boolean aBool) {
        if(aBool){
            return "white";
        }else{
            return "lightslategrey";
        }
    }

}
