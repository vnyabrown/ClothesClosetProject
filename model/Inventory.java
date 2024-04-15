// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;

public class Inventory extends EntityBase {
    private static final String myTableName = "inventory";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // Constructor
    public Inventory(String Barcode) throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE Barcode = " + Barcode;

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);


        // You must get one item at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one item. More than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple Items matching barcode : "
                        + Barcode + " found.");
            } else {
                // copy all the retrieved data into persistent state
                Properties retrievedInventoryData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedInventoryData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedInventoryData.getProperty(nextKey);
                    // BarNumber = Integer.parseInt(retrievedInventoryData.getProperty("Barcode"));

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no item found for this barcode, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No item matching barcode : "
                    + Barcode + " found.");
        }
    }

    // Constructor to initialize empty Inventory object
    public Inventory()
    {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }

    // Constructor to initialize Inventory object with given properties
    public Inventory(Properties props) {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    // DO we need the above constructor if we have this function? This is code repetition...
    public void processNewInventory(Properties props)
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
    }

    public void modifyGender(String Gender) {
        persistentState.setProperty("Gender", Gender);
    }

    public void modifySize(String Size) {
        persistentState.setProperty("Size", Size);
    }

    public void modifyArticleType(String ArticleType) {
        persistentState.setProperty("ArticleType", ArticleType);
    }

    public void modifyColor1(String Color1) {
        persistentState.setProperty("Color1", Color1);
    }

    public void modifyColor2(String Color2) {
        persistentState.setProperty("Color2", Color2);
    }

    public void modifyBrand(String Brand) {
        persistentState.setProperty("Brand", Brand);
    }

    public void modifyNotes(String Notes) {
        persistentState.setProperty("Notes", Notes);
    }

    public void modifyStatus(String Status) {
        persistentState.setProperty("Status", Status);
    }

    public void modifyDonorLastname(String DonorLastname) {
        persistentState.setProperty("DonorLastname", DonorLastname);
    }

    public void modifyDonorFirstname(String DonorFirstname) {
        persistentState.setProperty("DonorFirstname", DonorFirstname);
    }

    public void modifyDonorPhone(String DonorPhone) {
        persistentState.setProperty("DonorPhone", DonorPhone);
    }

    public void modifyDonorEmail(String DonorEmail) {
        persistentState.setProperty("DonorEmail", DonorEmail);
    }

    public void modifyReceiverNetid(String ReceiverNetid) {
        persistentState.setProperty("ReceiverNetid", ReceiverNetid);
    }

    public void modifyReceiverLastname(String ReceiverLastname) {
        persistentState.setProperty("ReceiverLastname", ReceiverLastname);
    }

    public void modifyReceiverFirstname(String ReceiverFirstname) {
        persistentState.setProperty("ReceiverFirstname", ReceiverFirstname);
    }

    public void modifyDateDonated(String DateDonated) {
        persistentState.setProperty("DateDonated", DateDonated);
    }

    public void modifyDateTaken(String DateTaken) {
        persistentState.setProperty("DateTaken", DateTaken);
    }

    /**
     * This method is needed solely to enable the Book information to be
     * displayable in a table NOTE: order is important, this gets the data from each table row
     *
     */
    // --------------------------------------------------------------------------
    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("Barcode"));
        v.addElement(persistentState.getProperty("Gender"));
        v.addElement(persistentState.getProperty("Size"));
        v.addElement(persistentState.getProperty("ArticleType"));
        v.addElement(persistentState.getProperty("Color1"));
        v.addElement(persistentState.getProperty("Color2"));
        v.addElement(persistentState.getProperty("Brand"));
        v.addElement(persistentState.getProperty("Notes"));
        v.addElement(persistentState.getProperty("Status"));
        v.addElement(persistentState.getProperty("DonorLastname"));

        v.addElement(persistentState.getProperty("DonorFirstname"));
        v.addElement(persistentState.getProperty("DonorPhone"));
        v.addElement(persistentState.getProperty("DonorEmail"));
        v.addElement(persistentState.getProperty("ReceiverNetid"));
        v.addElement(persistentState.getProperty("ReceiverLastname"));
        v.addElement(persistentState.getProperty("ReceiverFirstname"));
        v.addElement(persistentState.getProperty("DateDonated"));
        v.addElement(persistentState.getProperty("DateTaken"));

        return v;
    }

    public void save() {
        updateStateInDatabase();
    }

    public void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("Barcode") != null) {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("Barcode",
                        persistentState.getProperty("Barcode"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Inventory data for Barcode number : " + persistentState.getProperty("Barcode")
                        + " updated successfully in database!";
            } else {
                // insert
                Integer barNumber = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("Barcode", "" + barNumber.intValue());
                updateStatusMessage = "Inventory data for new Item : " + persistentState.getProperty("Barcode")
                        + "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error in installing Inventory data in database!";
        }
        System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    /* Return Book information as a string */
    // -----------------------------------------------------------------------------------
    public String toString()
    {
        return "Barcode: " + persistentState.getProperty("Barcode") + " Gender: " + persistentState.getProperty("Gender") +
                " Size: " + persistentState.getProperty("Size") + " ArticleType: " + persistentState.getProperty("ArticleType") +
                " Color1: " + persistentState.getProperty("Color1") + " Color2: " + persistentState.getProperty("Color2") +
                " Brand: " + persistentState.getProperty("Brand") + " Notes: " + persistentState.getProperty("Notes") +
                " Status: " + persistentState.getProperty("Status") + " DonorLastname: " + persistentState.getProperty("DonorLastname") +
                " DonorFirstname: " + persistentState.getProperty("DonorFirstname") + " DonorPhone: " + persistentState.getProperty("DonorPhone") +
                " DonorEmail: " + persistentState.getProperty("DonorEmail") + " ReceiverNetid: " + persistentState.getProperty("ReceiverNetid") +
                " ReceiverLastname: " + persistentState.getProperty("ReceiverLastname") + " ReceiverFirstname: " + persistentState.getProperty("ReceiverFirstname") +
                " DateDonated: " + persistentState.getProperty("DateDonated") + " DateTaken: " + persistentState.getProperty("DateTaken");
    }

    /* Display Book information to user  */
    // -----------------------------------------------------------------------------------
    public void display()
    {
        System.out.println(toString());
    }

    // -----------------------------------------------------------------------------------
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

    @Override
    public void stateChangeRequest(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stateChangeRequest'");
    }
}