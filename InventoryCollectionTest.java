import exception.InvalidPrimaryKeyException;
import model.Inventory;
import model.InventoryCollection;
import java.util.Properties;
import java.util.Vector;

public class InventoryCollectionTest {
    public static void main(String[]args) throws InvalidPrimaryKeyException {
        InventoryCollection ic = new InventoryCollection();
        ic.findInventoryWithBarcode("123");
        ic.displayCollection();
        Inventory i = new Inventory("123");
        i.modifyDateDonated("2024-1-2");
        i.removeInventoryItem();
        i.updateStateInDatabase();

    }
}
