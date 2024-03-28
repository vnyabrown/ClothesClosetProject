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
    private static final String myTableName = "articletype";
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
        persistentState.setProperty("Status", "Active");
        System.out.println("article created");
    }


    public static int compare(ArticleType a, ArticleType b) {
        String aStr = (String)a.getState("alphaCode");
        String bStr = (String)b.getState("alphaCode");

        return aStr.compareTo(bStr);
    }


    public void updateStateInDatabase() {
        try {
            // Upate Article Type
            if(persistentState.getProperty("Id") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("Id", 
                    persistentState.getProperty("Id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Article type info for id: " +
                    persistentState.getProperty("Id") +
                    " was updated in database.";
                System.out.println("article was updated.");
            }
            // Insert new Article Type
            else {
                try {
                    System.out.println("11111");
                    Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                    System.out.println("22222");
                    persistentState.setProperty("Id", Integer.toString(id));
                    updateStatusMessage = "Article Type for id: " +  
                    persistentState.getProperty("Id") + 
                    " installed successfully in database!";
                }
                catch(Exception e) {
                    System.out.println(e);
                    System.out.println("Possible duplicate value for Barcode Prefix.");
                }
            }
        } catch (Exception e) {
			updateStatusMessage = "Error in installing Article Type data in database!";
        }
    }
    

    public String toString() {
        return "New Article ------------------------------------\n" +
            "Article id: " + persistentState.getProperty("Id") + "\n" +
            "Description: " + persistentState.getProperty("Description") + "\n" +
            "Barcode: " + persistentState.getProperty("BarcodePrefix") + "\n" +
            "Alpha Code: " + persistentState.getProperty("AlphaCode") + "\n" + 
            "Status: " + persistentState.getProperty("Status");
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
        if(key == "markInactive") {
            markInactive();
        }
		myRegistry.updateSubscribers(key, this);
	}


    private void markInactive() {
        persistentState.setProperty("Status", "Inactive");
        updateStateInDatabase();
    }


	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
}
