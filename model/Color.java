// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;

public class Color extends EntityBase {
    private static final String myTableName = "color";
    
    protected Properties dependencies;

    // Gui components
    private String updateStatusMessage = "";

    //Constructor
    public Color(String colorId) throws InvalidPrimaryKeyException
    {
        super(myTableName);
        
        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (Id = " + colorId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one item at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one item, more than that is an error
            if (size!=1)
            {
                throw new InvalidPrimaryKeyException("Multiple items matching Color Id: " + colorId + " found.");
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
    } // end of constructor



    // Constructor to initialize empty Color object
    public Color()
    {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    } // end of empty constructor

    // Constructor to initialize Color object from given properties
    public Color(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();

        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue!=null)
            {
                persistentState.setProperty(nextKey, nextValue);
            } // end if
        } // end while
    } // end of Properties constructor

    // DO we need the above constructor if we have this function? This is code repetition?
    public void processNewColor(Properties props)
    {
        setDependencies();

        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    } // end of processNewColor

    /**
     * This method is needed solely to enable the Color information to be
     * displayable in a table NOTE: order is important, this gets the data from each table row
     *
     */

    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();
        v.addElement(persistentState.getProperty("Id"));
        v.addElement(persistentState.getProperty("Description"));
        v.addElement(persistentState.getProperty("BarcodePrefix"));
        v.addElement(persistentState.getProperty("AlphaCode"));
        v.addElement(persistentState.getProperty("Status"));

        return v;
    } // end of getEntryListView

    public void save() {
        updateStateInDatabase();
    } // end of save

    private void updateStateInDatabase()
    {
        try {
            if (persistentState.getProperty("Id") != null) {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("Id",
                        persistentState.getProperty("Id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Color data for Id number : " + persistentState.getProperty("Id")
                        + " updated successfully in database!";
            } else {
                // insert
                Integer colorId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("Id", "" + colorId.intValue());
                updateStatusMessage = "Color data for new Item : " + persistentState.getProperty("Id")
                        + "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error in installing Color data in database!";
        }
        System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    /* Return Color information as a string */
    // -----------------------------------------------------------------------------------
    public String toString()
    {
        return "Description: " + persistentState.getProperty("Description") + " BarcodePrefix: " + persistentState.getProperty("BarcodePrefix") +
        " AlphaCode: " + persistentState.getProperty("AlphaCode") + " Status: " + persistentState.getProperty("Status"); 
    } // end of toString

    /* Display Color information to user  */
    public void display()
    {
        System.out.println(toString());
    } // end of display


    private void setDependencies() {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    // -----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

    //@Override
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);    }

    @Override
    public String getState(String key) {
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
}
