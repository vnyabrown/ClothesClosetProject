import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import model.Inventory;
import model.InventoryCollection;
import java.util.Properties;

public class TestInventoryCollection {
    public static void main(String[] args) throws InvalidPrimaryKeyException {
        InventoryCollection ic = new InventoryCollection();
//        ic.findAvailableInventory();
//        ic.display();
        ic.findCheckedOutInventory();
        ic.display();
    }
}
