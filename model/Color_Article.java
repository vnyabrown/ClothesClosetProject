package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;

public class Color_Article extends EntityBase {

    // Variable to set table names
    // Todo: for the contructor. Need to set this somehow
    // private static final String myTableNameColor = "color";
    // private static final String myTableNameArticle = "articletype";
    private static String myTableName = "NotSet";

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "Empty from Table";

    // Assign the value of my table
    public Color_Article(int tableInt) {
        super(myTableName);
        // If the int == 0, then it is color, else it is article
        if (tableInt == 0) {
            myTableName = "color";
        } else {
            myTableName = "articletype";
        }
    }

    // For Both
    public Color_Article(Properties color_article_Properties) {
        super(myTableName);
        // Need to figure the purpose of this out
        // setDependencies();
        persistentState = new Properties();
        color_article_Properties.forEach((key, value) -> {
            if (value != null) {

                persistentState.setProperty((String) key, (String) value);

            }
        });
        // Set enum for status automatically to "Active"
        persistentState.setProperty("Status", "Active");
        // Todo: The print should be based on article or color
        // System.out.println("article created");
    }

    // For Both
    public Color_Article() {

        // Todo: I wonder if the set dependencies need to be commented out
        // Or do you always have to set it. Color and Article is different
        super(myTableName);
        // setDependencies();
        persistentState = new Properties();

    }

    // For Both
    public void processNewColorArticle(Properties props) {
        // setDependencies();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
            // Set enum for status automatically to "Active"
            persistentState.setProperty("Status", "Active");
            // Todo If statement to check if color or article for print
            // System.out.println("article created");
        }
    }

    // Unique Color constructor
    // Constructor
    // This is unique to color
    public Color_Article(String colorId) throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (Id = " + colorId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one item at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one item, more than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple Colors matching Color Id: " + colorId + " found.");
            }

            else {
                // Copy all retrieved data into persistent state
                Properties retrievedColorData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedColorData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedColorData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    } // end if
                } // end while
            } // end if else
        } // end if
    } // end of constructor

    /**
     * This method is needed solely to enable the Color information to be
     * displayable in a table NOTE: order is important, this gets the data from each
     * table row
     *
     */

    public Vector<String> getEntryListView() {
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

    // For article
    public static int compare(ArticleType a, ArticleType b) {
        String aStr = (String) a.getState("Id");
        String bStr = (String) b.getState("Id");

        return aStr.compareTo(bStr);
    }

    @SuppressWarnings("unused")
    public void updateStateInDatabase() {
        if (myTableName == "color") {
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
        } else {

            try {
                // Upate Article Type

                if (persistentState.getProperty("Id") != null) {
                    Properties whereClause = new Properties();
                    whereClause.setProperty("Id",
                            persistentState.getProperty("Id"));
                    updatePersistentState(mySchema, persistentState, whereClause);
                    updateStatusMessage = "Article type info for id: " +
                            persistentState.getProperty("Id") +
                            " was updated in database.";
                    System.out.println("Article Type was updated into database.");
                } else if (checkBarcodePrefixExists(persistentState.getProperty("BarcodePrefix"))) {
                    System.out.println();
                    throw new Exception("Error! Color with BarcodePrefix "
                            + persistentState.getProperty("BarcodePrefix") + " exists in database!");
                }
                // Insert new Article Type
                else {
                    try {
                        Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                        persistentState.setProperty("Id", Integer.toString(id));
                        updateStatusMessage = "Article Type for id: " +
                                persistentState.getProperty("Id") +
                                " installed successfully in database!";
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e);
                        throw new Exception("Possible duplicate value for Barcode Prefix.");
                    }
                }
            } catch (SQLException e) {
                System.err.println("ERROR sql: " + e.getMessage());

            } catch (Exception e) {
                System.err.println("ERROR general: " + e.getMessage());
                updateStatusMessage = "Error in installing Article Type data in database!";
            }

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

    public boolean checkBarcodePrefixExists(String bfx) {
        String query = "SELECT * FROM " + myTableName + " WHERE BarcodePrefix = '%" + bfx + "%';";
        if (!getSelectQueryResult(query).isEmpty()) {
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
        return "Article id: " + persistentState.getProperty("Id") + "\n" +
                "Description: " + persistentState.getProperty("Description") + "\n" +
                "Barcode: " + persistentState.getProperty("BarcodePrefix") + "\n" +
                "Alpha Code: " + persistentState.getProperty("AlphaCode") + "\n" +
                "Status: " + persistentState.getProperty("Status");
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value) {
        if (key == "markInactive") {
            markInactive();
        }
        myRegistry.updateSubscribers(key, this);
    }

    public void markInactive() {
        persistentState.setProperty("Status", "Inactive");
        updateStateInDatabase();
    }

    /** Called via the IView relationship */
    // ----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    private void setDependencies() {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }
}
