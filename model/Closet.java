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
import java.util.Vector;

public class Closet implements IView, IModel {
    // State variables


    private Inventory cloth;
    private InventoryCollection clothColl;

    public static Properties clothMod;


    private ArticleTypeCollection atc;
    private ArticleType at;
    private ColorCollection colorColl;
    private Color color;
    String[] array;
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private ArticleType newArticle = new ArticleType();
    private Color newColor = new Color();
    private Inventory newInv = new Inventory();
    public static String barcode = "";
    //private Inventory newInventory;

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
            new Event(Event.getLeafLevelClassName(this), "Closet",
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
        dependencies.setProperty("ModifyButton", "ModifyButton");
        dependencies.setProperty("successfulModify", "successfulModify");
        dependencies.setProperty("unsuccessfulModify", "unsuccessfulModify");
        dependencies.setProperty("updateText", "update");
        dependencies.setProperty("duplicateBarcode", "duplicateBarcode");
        dependencies.setProperty("noBarcodeFound", "noBarcodeFound");


        myRegistry.setDependencies(dependencies);
    }

    @Override
    public Object getState(String key) {
        if(key.equals("ColorCollection")) {
            return colorColl;
        }
        else if (key.equals("ArticleTypeCollection"))
        {
            return atc;
        }
        else if (key.equals("InventoryCollection"))
        {
            return clothColl;
        }
        else {
            return "nothing from getState in Closet";
        }
    }

    @Override
    public void subscribe(String key, IView subscriber) {
        myRegistry.subscribe(key, subscriber);
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

            case "Inventory":
                if (value != null) {
                    loginErrorMessage = "";
                    createAndShowChoiceView("InventoryChoiceView");
                }
                break;
            case "SearchForClothing":
                createAndShowChoiceView("SearchForClothing");
                break;
            case"SearchClothingCollection":
                searchClothingCollection((String[]) value);
                break;
            case "ClothingSelectedForDeletion":
                cloth = new Inventory((Properties) value);
                cloth.markRemoved();
                break;
            case "ClothingToBeModified":
                clothMod = (Properties) value;
                cloth = new Inventory((Properties) value);
                createAndShowChoiceView("ModifyClothingView");
                stateChangeRequest("updateText", "update");
                //ModifyClothingView.fillTextFields();
                break;
            case "ModifyClothing":
                array = (String[]) value;
                cloth.modifyInventory(array[0], array[1], array[2], array[3], array[4], array[5],
                        array[6], array[7], array[8], array[9], array[10]);
                cloth.updateStateInDatabase();
                String str = (String)cloth.getState("UpdateStatusMessage");
                if(str.equals("ok")){
                    stateChangeRequest("successfulModify", "yahoo");
                } else {
                    stateChangeRequest("unsuccessfulModify", "yahoo");
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
                array = (String[]) value;
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
                break;
            case "SearchForColor":
                createAndShowChoiceView("SearchColorView");
                break;
            // value in this case is not a string but an array
            // first value is search text
            // second is whether to search by alphaCode or description
            case"SearchColorCollection":
                searchColorCollection((String[]) value);
                break;
            case "ColorSelectedForDeletion":
                color = new Color((Properties) value);
                color.markInactive();
                break;
            case "ColorToBeModified":
                color = new Color((Properties) value);
                System.out.println("here");
                createAndShowChoiceView("ModifyColorView");
                break;
            case "ModifyColor":
                array = (String[]) value;
                if(array[1].equals("Description")) {
                    color.modifyDescription(array[0]);
                    color.updateStateInDatabase();
                }
                else if(array[1].equals("Barcode Prefix")) {
                    color.modifyBarcodePrefix(array[0]);
                    color.updateStateInDatabase();
                }
                else if(array[1].equals("Alpha Code")) {
                    color.modifyAlphaCode(array[0]);
                    color.updateStateInDatabase();
                }
                else {
                    System.out.println("not an option in statechangerequest in modifycolor");
                }
                break;
                
            case "CancelArticleTransaction":
            case "CancelColorTransaction":
            case "CancelClothingTransaction":
            case "CancelInventoryTransaction":
                createAndShowChoiceView("ClosetView");
                break;

            case "InsertArticleView":
                createAndShowChoiceView("InsertArticleView");
                break;
            case "InsertColorView":
                createAndShowChoiceView("InsertColorView");
                break;
            case "InsertInventoryView":
                barcode = (String)value;
                try {
                    Inventory inv = new Inventory(barcode);
                    String result = inv.getState("UpdateStatusMessage");
                    if(result.equals("duplicateBarcode")) {
                        System.out.println("hereeeeeeeeeee");
                        stateChangeRequest("duplicateBarcode", "nada");
                    }
                } catch(Exception e) {
                    createAndShowChoiceView("InsertInventoryView");
                    stateChangeRequest("updateText", "update");
                    e.printStackTrace();
                }
                break;
            case "InsertArticle":
                newArticle.processNewArticle((Properties)value);
                newArticle.updateStateInDatabase();
                break;
            case "InsertColor":
                newColor.processNewColor((Properties)value);
                newColor.updateStateInDatabase();
                break;
            case "InsertInventory":
                try {
                    System.out.println(value.toString());
                    newInv.processNewInventory((Properties)value); // populate Inventory with new inventory data
                    newInv.updateStateInDatabase(); // Save Inventory record to databse
                } catch (Exception e) {
                    System.out.println("Error in Closet.insertInventory: Could not create inventory object");
                    e.printStackTrace();
                }
                break;
            case "DeleteArticle":
                String transType = key;
                doTransaction(transType);
                break;
            case "Logout":
                myViews.remove("CancelArticleTransaction");
                myViews.remove("CancelColorTransaction");
                myViews.remove("CancelClothingTransaction");
                myViews.remove("CancelInventoryTransaction");
                createAndShowChoiceView("ClosetView");
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }


    // argument is array
    // first element is search string
    // second is whether to search by alphaCode or description
    private void searchColorCollection(String[] values) {
        System.out.println(values[0] + " " + values[1]);
        colorColl = new ColorCollection();
        String target = values[0];
        try {
            if(values[1].equals("Alpha Code")) {
                colorColl.findColorAlphaCode(target);
            }
            else if(values[1].equals("Description")) {
                colorColl.findColorDescription(target);
            }
            else {
                System.err.println("string in combo box doesn't match one of correct conditions.");
            }
            colorColl.display();
            createAndShowColorCollectionView();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void searchClothingCollection(String[] values) {
        System.out.println(values[0] + " " + values[1]);
        clothColl = new InventoryCollection();
        String target = values[0];
        try {
            if(values[1].equals("Barcode")) {
                clothColl.findInventoryBarcodeDonated(target);
                Vector<Inventory> invVec = (Vector<Inventory>) clothColl.getState("getVector");
                if(invVec.isEmpty()) {
                    stateChangeRequest("noBarcodeFound", "nnnnn");
                } else {
                    createAndShowClothingCollectionView();
                }
            }
            else {
                System.err.println("Somethings wrong reciving: " + values[1] + " instead of 'Barcode'");
            }
            //clothColl.display();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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

    // Create view to hold table of colors
    private void createAndShowColorCollectionView() {
        // create our initial view
        View newView = ViewFactory.createView("ColorCollectionView", this); // USE VIEW FACTORY
        Scene currentScene = new Scene(newView);

        swapToView(currentScene);
    }

    private void createAndShowClothingCollectionView() {
        // create our initial view
        View newView = ViewFactory.createView("ClothingCollectionView", this); // USE VIEW FACTORY
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
            System.out.println("Closet.swapToView(): Missing view for display");
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
