package model;

import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import java.util.Properties;
import model.Inventory;

public class InventoryCollection extends EntityBase implements IView{

    private static final String myTableName = "inventory";
    private Vector<Inventory> inventoryList;

    //Constructor
    public InventoryCollection()
    {
        super(myTableName);
        inventoryList = new Vector<Inventory>();
    } // end of constructor

    public void findInventoryWithBarcode(String barcode) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE Barcode LIKE '%" + barcode + "%'";
        executeQueryAndPopulate(query);
    }

        private void executeQueryAndPopulate(String query)
    {
        Vector allDataRetrieved = getSelectQueryResult(query);

        // Check against query condition to retrieve matching Inventory Items
        for (int i = 0; i < allDataRetrieved.size(); i++) {
            Properties nextInventory = (Properties) allDataRetrieved.elementAt(i);

            // Add to our inventory list
            Inventory inv_item = new Inventory(nextInventory);
            inventoryList.add(inv_item);
        }
    } // end of executeQuereAndPopulate

    // Will need queries below-- what condition do we find inventory items by?

    /* Display each Inventory information from Inventory in Collection to user */
    // ==============================================================
    public void displayCollection()
    {
        // Cycle through each Patron in the Patron collection
        for (int count = 0; count < inventoryList.size(); count++)
        {
            System.out.println(inventoryList.elementAt(count).toString()); // Convert each Book information to a string and display it
        }
    }

    @Override
    public void updateState(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateState'");
    } // end of updateState

    @Override
    public Object getState(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    } // end of getState

    @Override
    public void stateChangeRequest(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stateChangeRequest'");
    } // end of stateChangeRequest

    @Override
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    } // end of initializeSchema

}