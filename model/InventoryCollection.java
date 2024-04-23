package model;

import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import java.util.Properties;
import model.Inventory;

public class InventoryCollection extends EntityBase implements IView{
    private static final String myTableName = "inventory";
    private Vector<Inventory> invenList;

    // Constructor
    public InventoryCollection() {
        super(myTableName);
        invenList = new Vector<>();
    }


    public void updateInventoryListFromSQL(String query) throws Exception {
        // Reset bookList
        this.invenList = new Vector<Inventory>();

        // Pull the data
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // Loop through data received and make fill bookList with Book objects
        for (int i = 0; i < allDataRetrieved.size(); i++) {
            this.invenList.add(new Inventory(allDataRetrieved.elementAt(i)));
        }
    }


    public Vector<Inventory> findInventoryBarcode(String pfx)  {

        // The query to get all the colors
        String query = "SELECT * FROM " + myTableName + " WHERE Barcode LIKE '%" + pfx + "%'";
        try {
            updateInventoryListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid Barcode. '" + pfx + "' is not valid!");
        }

        return this.invenList;
    }

    public void display(){
        if (invenList.isEmpty())
        {
            System.out.println("No inventory items in vector.");
            return;
        }
        for (int cnt = 0; cnt < invenList.size(); cnt++) {
            System.out.println(invenList.elementAt(cnt).toString());

        }
    }

    @Override
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    @Override
    public Object getState(String key) {
        if (key.equals("Inventory"))
            return invenList;
        else if (key.equals("getVector")) {
            return this.invenList;
        }
        return null;
    }



    @Override
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }


    @Override
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }


    }

}