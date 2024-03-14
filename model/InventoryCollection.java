package model;

import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import java.util.Properties;
import model.Inventory;

public class InventoryCollection extends EntityBase implements IView{

    private static final String myTableName = "Inventory";
    private Vector<Inventory> inventoryList;

    //Constructor
    public InventoryCollection()
    {
        super(myTableName);
        inventoryList = new Vector<Inventory>();
    } // end of constructor

    private void executeQueryAndPopulate(String query)
    {
        Vector allDataRetrieved = getSelectQueryResult(query);
        
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
