// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
//import impresario.IView;

public class ArticleType extends EntityBase {
    private static final String myTableName = "ArticleType";
    protected Properties dependencies;
    // GUI Components
    private String updateStatusMessage = "empty from ArticleType";

    // Cronstructor
    public ArticleType(Properties articleTypeProperties) {
        super(myTableName);
        // Need to figure the purpose of this out
        // setDependencies();
        persistentState = new Properties();
        articleTypeProperties.forEach((key, value) -> {
            if(value != null) {
                //System.out.println(value); // Debugging 
                persistentState.setProperty((String)key, (String)value);
            }
        });                                                                                                                                                                                         
        // Set enum for status automatically to "Active"
        persistentState.setProperty("status", "Active");
    }


    public void updateStateInDatabase() {
        try {
            // Upate Article Type
            if(persistentState.getProperty("id") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("id", 
                    persistentState.getProperty("id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Article type info for id: " +
                    persistentState.getProperty("id") +
                    " was updated in database.";
            }
            else {
                Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("id", Integer.toString(id));
                updateStatusMessage = "Article Type for id: " +  
                    persistentState.getProperty("id") + 
                    " installed successfully in database!";
            }
        } catch (Exception e) {
			updateStatusMessage = "Error in installing Article Type data in database!";
        }
    }
    

    public String toString() {
        return "Article id: " + persistentState.getProperty("id") + "\n" +
            "Description: " + persistentState.getProperty("description") + "\n" +
            "Barcode: " + persistentState.getProperty("barcodePrefix") + "\n" +
            "Alpha Code: " + persistentState.getProperty("alphaCode") + "\n" + 
            "Status: " + persistentState.getProperty("status");
    }


    protected void initializeSchema(String tableName) {
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}


    public Object getState(String key) {
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}


    public void stateChangeRequest(String key, Object value) {

		myRegistry.updateSubscribers(key, this);
	}


	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
}
