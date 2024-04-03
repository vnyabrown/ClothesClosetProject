package model;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;

public class Closet implements IView, IModel {
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
        return null;
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

            case "ModifyArticle":
            case "DeleteArticle":
                createAndShowChoiceView("SearchArticleTypeView");
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
