package model;

import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import java.util.Properties;

public class ColorCollection extends EntityBase implements IView {
    private static final String myTableName = "color";

    private Vector<Color> colorList;

    public ColorCollection() {
        super(myTableName);
        colorList = new Vector<Color>();
    }
    private void executeQueryAndPopulate(String query)
    {
        Vector allDataRetrieved = getSelectQueryResult(query);

        // Check if each book has a date older than given date
        for (int i = 0; i < allDataRetrieved.size(); i++) {
            Properties nextColor = (Properties) allDataRetrieved.elementAt(i);

            // Remeber in the color class you change the type of the contructor from String
            // to Properties
            Color color = new Color(nextColor);
            colorList.add(color);

        }
    }

    public void updateColorListFromSQL(String query) throws Exception {
        // Reset bookList
        this.colorList = new Vector<Color>();

        // Pull the data
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // Loop through data received and make fill bookList with Book objects
        for (int i = 0; i < allDataRetrieved.size(); i++) {
            this.colorList.add(new Color(allDataRetrieved.elementAt(i)));
        }
    }

    public String getColorDescriptionFromPFX(String PFX){
        String query = "SELECT Description FROM "+ myTableName + " WHERE BarcodePrefix LIKE '" + PFX + "'";
        Vector<Properties> result = getSelectQueryResult(query);
        String realResult = result.elementAt(0).getProperty("Description");

        return realResult;
    }

    public String getColorPFXFromDescription(String desc){
        System.out.println("desc: " + desc);
        String query = "SELECT BarcodePrefix FROM "+ myTableName + " WHERE Description ='" + desc + "'";
        System.out.println("qqq: " + query);
        Vector<Properties> result = getSelectQueryResult(query);
        System.out.println("result size" + result.size());
        String realResult = result.elementAt(0).getProperty("BarcodePrefix");

        return realResult;
    }

    /* Display each color information from Book in Collection to user */
    // ==============================================================
    public void displayCollection()
    {
        // Cycle through each color in the color collection
        for (int count = 0; count < colorList.size(); count++)
        {
            System.out.println(colorList.elementAt(count).toString()); // Convert each color information to a string and display it
        }
    }


    public Vector<Color> findColorBarcodePfx(String pfx)  {

        // The query to get all the colors
        String query = "SELECT * FROM " + myTableName + " WHERE (BarcodePrefix = " + pfx + ")";
        try {
            updateColorListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid BarcodePrefix. '" + pfx + "' is not valid!");
        }

        return this.colorList;
    }

    public Vector<Color> findColorAlphaCode(String alpha) throws InvalidPrimaryKeyException{
        String query = "SELECT * FROM " + myTableName + " WHERE AlphaCode LIKE '%" + alpha + "%'";
        try {
            updateColorListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid BarcodePrefix. '" + alpha + "' is not valid!");
        }

        return this.colorList;
    }


    public Vector<Color> findColorDescription(String descr) throws InvalidPrimaryKeyException{
        String query = "SELECT * FROM " + myTableName + " WHERE Description LIKE '%" + descr + "%'";
        try {
            updateColorListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid BarcodePrefix. '" + descr + "' is not valid!");
        }

        return this.colorList;
    }

    public Vector<Color> getAllValidColors() {
        String query = "SELECT DISTINCT * FROM " + myTableName;
        try {
            updateColorListFromSQL(query);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to retrieve all valid colors!");
        }

        return this.colorList;
    }


    public void display(){
        if (colorList.isEmpty())
        {
            System.out.println("No Colors in vector.");
            return;
        }
        for (int cnt = 0;cnt <colorList.size(); cnt++) {
            System.out.println(colorList.elementAt(cnt).toString());

        }
    }



    @Override
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    @Override
    public Object getState(String key) {
        if (key.equals("Color"))
            return colorList;
        else if (key.equals("getVector")) {
            return this.colorList;
        }
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stateChangeRequest'");
    }

    @Override
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }


    }

}
