import java.util.HashMap;
import java.util.Map;

public class Shoppinglist {

    private Map shoppinglist = new HashMap<Shoe,Integer>();


    public Shoppinglist(Map shoppinglist) {
    }

    public Map getShoppinglist() {
        return shoppinglist;
    }

    public void setShoppinglist(Map shoppinglist){
        this.shoppinglist = shoppinglist;
    }

}
