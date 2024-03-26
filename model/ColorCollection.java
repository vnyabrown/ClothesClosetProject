package model;

import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import java.util.Properties;
import model.Color;

public class ColorCollection extends EntityBase implements IView {
    private static final String myTableName = "Color";

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









    @Override
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    @Override
    public Object getState(String key) {
        if (key.equals("Color"))
            return colorList;

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
