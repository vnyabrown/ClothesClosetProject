// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import impresario.IView;

public class ArticleType extends EntityBase {
    private static final String myTableName = "articletype";
    protected Properties dependencies;
    // GUI Components
    private String updateStatusMessage = "empty from ArticleType";

    // Constructor
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
    } // Consturctor by given Properties

    public ArticleType(String articleID) throws InvalidPrimaryKeyException
    {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (Id = " + articleID + ")";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one item at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one item, more than that is an error
            if (size!=1)
            {
                throw new InvalidPrimaryKeyException("Multiple ArticleTypes matching Article Id: " + articleID + " found.");
            }

            else
            {
                // Copy all retrieved data into persistent state
                Properties retrievedColorData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedColorData.propertyNames();
                while(allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedColorData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    } // end if
                } // end while
            } // end if else
        } // end if
    } // end of Constructor by ID

    public ArticleType(int BPFX) throws InvalidPrimaryKeyException
    {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (BarcodePrefix = " + Integer.toString(BPFX) + ")";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one item at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one item, more than that is an error
            if (size!=1)
            {
                throw new InvalidPrimaryKeyException("Multiple ArticleTypes matching BarcodePrefix: " + Integer.toString(BPFX) + " found.");
            }

            else
            {
                // Copy all retrieved data into persistent state
                Properties retrievedColorData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedColorData.propertyNames();
                while(allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedColorData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    } // end if
                } // end while
            } // end if else
        } // end if
    } // end of Constructor by ID

    public ArticleType()
    {
        super(myTableName);
        //setDependencies();
        persistentState = new Properties();
    }

    public void processNewArticle(Properties props)
    {
        //setDependencies();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
            // Set enum for status automatically to "Active"
            persistentState.setProperty("Status", "Active");
            System.out.println("article created");
        }
    }

    public static int compare(ArticleType a, ArticleType b) {
        String aStr = (String)a.getState("Id");
        String bStr = (String)b.getState("Id");

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
                System.out.println("Article Type was updated into database.");
            }
            else if (checkBarcodePrefixExists(persistentState.getProperty("BarcodePrefix")))
            {
                System.out.println();
                throw new Exception("Error! Color with BarcodePrefix " + persistentState.getProperty("BarcodePrefix") + " exists in database!");
            }
            // Insert new Article Type
            else {
                try {
                    Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                    persistentState.setProperty("Id", Integer.toString(id));
                    updateStatusMessage = "Article Type for id: " +  
                    persistentState.getProperty("Id") + 
                    " installed successfully in database!";
                }
                catch(Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                    throw new Exception ("Possible duplicate value for Barcode Prefix.");
                }
            }
        } catch(SQLException e) {
            System.err.println("ERROR sql: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("ERROR general: " + e.getMessage());
			updateStatusMessage = "Error in installing Article Type data in database!";
        }
    }


    public Vector<String> getFields() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("Id"));
        v.addElement(persistentState.getProperty("Description"));
        v.addElement(persistentState.getProperty("BarcodePrefix"));
        v.addElement(persistentState.getProperty("AlphaCode"));
        v.addElement(persistentState.getProperty("Status"));

        return v;
    }

    public boolean checkBarcodePrefixExists(String bfx)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE BarcodePrefix = '%" + bfx + "%';";
        if (!getSelectQueryResult(query).isEmpty())
        {
            return true; // it does already exist in table
        }
        return false; // it doesn't already exist in table
    }


    public void modifyDescription(String str) {
        persistentState.setProperty("Description", str);
        System.out.println("Description modified.");
    }


    public void modifyAlphaCode(String str) {
        persistentState.setProperty("AlphaCode", str);
        System.out.println("Alpha Code modified.");
    }


    public void modifyBarcodePrefix(String str) {
        persistentState.setProperty("BarcodePrefix", str);
        System.out.println("Barcode Prefix modified.");
    }
    

    public String toString() {
        return "Article ------------------------------------\n" +
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


    public void markInactive() {
        persistentState.setProperty("Status", "Inactive");
        updateStateInDatabase();
    }


	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
}
