// specify the package
package model;
import model.*;

// system imports
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;
import impresario.IView;

public class InventoryCollection extends EntityBase implements IView{
    private static final String myTableName = "inventory";
    private Vector<Inventory> inventory;
    private String updateStatusMessage;

    // Constructor
    public InventoryCollection() {
        super(myTableName);
        inventory = new Vector<>();
    }


    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage")) {
            return updateStatusMessage;
        }
        else if (key.equals("getVector")) {
            return this.inventory;
        }

        return persistentState.getProperty(key);
    }


    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }


    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }


    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }


    public void display(){
        if (inventory.isEmpty())
        {
            System.out.println("No inventory items in vector.");
            return;
        }
        for (int cnt = 0;cnt <inventory.size(); cnt++) {
            System.out.println(inventory.elementAt(cnt).toString());

        }
    }


    private void addInventoryItem(Inventory item) {
        int index = findIndexTOAdd(item);
        inventory.insertElementAt(item, index);

    }


    private int findIndexTOAdd(Inventory item) {
        int low = 0;
        int high = inventory.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high)/2;
            Inventory midSession = inventory.elementAt(middle);
            int result = Inventory.compare(item, midSession);
            if(result == 0) {
                return middle;
            }
            else if (result < 0) {
                high = middle - 1;
            }
            else {
                low = middle + 1;
            }
        }
        return low;
    }


    public void findAvailableInventory() throws InvalidPrimaryKeyException{
        String query = "SELECT * FROM " + myTableName + " WHERE Status LIKE '%Donated%'";
        populateInventorysWithQuery(query);
    }


    public void findCheckedOutInventory() throws InvalidPrimaryKeyException{
        String query = "SELECT * FROM " + myTableName + " WHERE Status LIKE '%Received%'";
        populateInventorysWithQuery(query);
    }


    private void populateInventorysWithQuery(String query) throws InvalidPrimaryKeyException {
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            System.out.println("alldataretrieved count: " + allDataRetrieved.size());
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                System.out.println(allDataRetrieved.elementAt(cnt).getProperty("Barcode"));
                Properties itemData = allDataRetrieved.elementAt(cnt);
                Inventory item = new Inventory(itemData);
                if (item != null) {
                    addInventoryItem(item);
                }
            }
        }
        else {
            System.out.println("alldataretrieved is null");
            throw new InvalidPrimaryKeyException("Articles not found");
        }
    }
}