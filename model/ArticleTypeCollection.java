// specify the package
package model;
import model.*;

// system imports
import java.util.Properties;
import java.util.Vector;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;
import impresario.IView;

public class ArticleTypeCollection extends EntityBase implements IView{
    private static final String myTableName = "articletype";
    private Vector<ArticleType> articleTypes;
    private String updateStatusMessage;

    // Constructor
    public ArticleTypeCollection() {
        super(myTableName);
        articleTypes = new Vector<>();
    }

    
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage")) {
            return updateStatusMessage;
        }
        else if (key.equals("getVector")) {
            return this.articleTypes;
        }

        return persistentState.getProperty(key);
    }


//----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }


    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }


    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }


    public void display(){
        if (articleTypes.isEmpty())
        {
            System.out.println("No Article types in vector.");
            return;
        }
        for (int cnt = 0;cnt <articleTypes.size(); cnt++) {
            System.out.println(articleTypes.elementAt(cnt).toString());

        }
    }


    private void addArticleType(ArticleType at) {
        int index = findIndexTOAdd(at);
        articleTypes.insertElementAt(at, index);

    }


    private int findIndexTOAdd(ArticleType at) {
        int low = 0;
        int high = articleTypes.size() - 1;
        int middle;

        while (low <= high) {
            middle = (low + high)/2;
            ArticleType midSession = articleTypes.elementAt(middle);
            int result = ArticleType.compare(at, midSession);
            if(result == 0) {
                return middle;
            }
            else if (result < 0) {
                high = middle - 1;
            }
            else {
                low = middle + 1;
            }
        }
        return low;
    }

    public String getArticleDescriptionFromPFX(String PFX){
        String query = "SELECT Description FROM "+ myTableName + " WHERE BarcodePrefix LIKE '" + PFX + "'";
        Vector<Properties> result = getSelectQueryResult(query);
        String realResult = result.elementAt(0).getProperty("Description");

        return realResult;
    }


    public void findArticleTypeWithAlphaCode(String alphaCode) throws InvalidPrimaryKeyException{
        String query = "SELECT * FROM " + myTableName + " WHERE AlphaCode LIKE '%" + alphaCode + "%'";
        populateArticleTypesWithQuery(query);
    }


    public void findArticleTypeWithDescription(String text) throws InvalidPrimaryKeyException{
        String query = "SELECT * FROM " + myTableName + " WHERE Description LIKE '%" + text + "%'";
        populateArticleTypesWithQuery(query);
    }


    private void populateArticleTypesWithQuery(String query) throws InvalidPrimaryKeyException {
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            System.out.println("alldataretrieved count: " + allDataRetrieved.size());
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                System.out.println(allDataRetrieved.elementAt(cnt).getProperty("Id"));
                Properties articleTypeData = allDataRetrieved.elementAt(cnt);
                ArticleType articleType = new ArticleType(articleTypeData);
                if (articleType != null) {
                    addArticleType(articleType);
                }
            }
        }
        else {
            System.out.println("alldataretrieved is null");
            throw new InvalidPrimaryKeyException("Articles not found");
        }
    }



    public Vector<ArticleType> getAllValidArticleTypes() {
        String query = "SELECT DISTINCT * FROM " + myTableName;
        try {
            populateArticleTypesWithQuery(query);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to retrieve all valid colors!");
        }

        return this.articleTypes;
    }
}
