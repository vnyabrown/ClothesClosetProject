package model;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.*;

import java.util.Hashtable;
import java.util.Properties;

public class Closet implements IView, IModel {
    // State variables
    private ArticleTypeCollection atc;
    private ArticleType at;
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";

    public Closet() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Closet");

        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowChoiceView("ClosetView");
//        System.out.println("LibrarianView Shown");
    }
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Login", "LoginError");
        dependencies.setProperty("InsertArticle", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public Object getState(String key) {
        if(key.equals("ArticleTypeCollection")) {
            return atc;
        }
        else {
            return "nothing from getState in Closet";
        }
    }

    @Override
    public void subscribe(String key, IView subscriber) {

    }

    @Override
    public void unSubscribe(String key, IView subscriber) {

    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        switch (key) {
            case "Article":
                if (value != null) {
                    loginErrorMessage = "";
                    createAndShowChoiceView("ArticleChoiceView");
                }
                break;

            case "Color":
                if (value != null) {
                    loginErrorMessage = "";
                    createAndShowChoiceView("ColorChoiceView");
                }
                break;

            case "Clothing":
                if (value != null) {
                    loginErrorMessage = "";
                    createAndShowChoiceView("ClothingChoiceView");
                }
                break;

            case "Stock":
                if (value != null) {
                    loginErrorMessage = "";
                    createAndShowChoiceView("StockChoiceView");
                }
                break;

            case "ArticleChoiceView":
                if (value != null) {
                    loginErrorMessage = "";
                    createAndShowChoiceView("ArticleChoiceView");
                }
                break;

            case "SearchForArticleType":
                createAndShowChoiceView("SearchArticleTypeView");
                break;
            // value in this case is not a string but an array
            // first value is search text
            // second is whether to search by alphaCode or description
            case"SearchArticleTypeCollection":
                searchArticleTypeCollection((String[]) value);
                break;
            case "ArticleTypeSelectedForDeletion":
                at = new ArticleType((Properties) value);
                at.markInactive();
                break;
            case "ArticleTypeToBeModified":
                at = new ArticleType((Properties) value);
                System.out.println("here");
                createAndShowChoiceView("ModifyArticleTypeView");
                break;
            case "ModifyArticleType":
                String[] array = (String[]) value;
                if(array[1].equals("Description")) {
                    at.modifyDescription(array[0]);
                    at.updateStateInDatabase();
                }
                else if(array[1].equals("Barcode Prefix")) {
                    at.modifyBarcodePrefix(array[0]);
                    at.updateStateInDatabase();
                }
                else if(array[1].equals("Alpha Code")) {
                    at.modifyAlphaCode(array[0]);
                    at.updateStateInDatabase();
                }
                else {
                    System.out.println("not an option in statechangerequest in modifyarticletype");
                }



            case "CancelArticleTransaction":
            case "CancelColorTransaction":
            case "CancelClothingTransaction":
            case "CancelStockTransaction":
                createAndShowChoiceView("ClosetView");
                break;

            case "InsertArticle":
            //case "DeleteArticle":
                String transType = key;
                doTransaction(transType);
                break;


            case "Logout":
                myViews.remove("CancelArticleTransaction");
                myViews.remove("CancelColorTransaction");
                myViews.remove("CancelClothingTransaction");
                myViews.remove("CancelStockTransaction");
                createAndShowChoiceView("ClosetView");
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }


    // argument is array
    // first element is search string
    // second is whether to search by alphaCode or description
    private void searchArticleTypeCollection(String[] values) {
        System.out.println(values[0] + " " + values[1]);
        atc = new ArticleTypeCollection();
        String target = values[0];
        try {
            if(values[1].equals("Alpha Code")) {
                atc.findArticleTypeWithAlphaCode(target);
            }
            else if(values[1].equals("Description")) {
                atc.findArticleTypeWithDescription(target);
            }
            else {
                System.err.println("string in combo box doesn't match one of correct conditions.");
            }
            atc.display();
            createAndShowArticleTypeCollectionView();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    // Create view to hold table of article types
    private void createAndShowArticleTypeCollectionView() {
        // create our initial view
        View newView = ViewFactory.createView("ArticleTypeCollectionView", this); // USE VIEW FACTORY
        Scene currentScene = new Scene(newView);

        swapToView(currentScene);
    }

    private void createAndShowChoiceView(String view)
    {
        Scene currentScene = (Scene)myViews.get(view);

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView(view, this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put(view, currentScene);
        }


        // make the view visible by installing it into the frame
        swapToView(currentScene);

    }

    /**
     * Create a Transaction depending on the Transaction type (deposit,
     * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
     * create.
     */
    //----------------------------------------------------------
    public void doTransaction(String transactionType)
    {
        try
        {

            Transaction trans = TransactionFactory.createTransaction(transactionType);

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("DoYourJob", "");
        }
        catch (Exception ex)
        {
            transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
            new Event(Event.getLeafLevelClassName(this), "createTransaction",
                    "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
                    Event.ERROR);
        }
    }

    @Override
    public void updateState(String key, Object value) {

    }

    public void swapToView(Scene newScene)
    {


        if (newScene == null)
        {
            System.out.println("Librarian.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }
}
