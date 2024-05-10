// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static userinterface.InventoryChoiceView.modDelCheckFlag;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class SearchForClothingView extends View
{

    // GUI components
    protected TextField bookTitle;
    protected TextField text;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SearchForClothingView(IModel bookCollection)
    {
        super(bookCollection, "SearchForCLothingView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        // These need to be made specific for Book
        myModel.subscribe("ModifyButton", this);
        myModel.subscribe("duplicateBarcode", this);
        myModel.subscribe("noBarcodeFound", this);
        //myModel.subscribe("UpdateStatusMessage", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text("       Brockport Clothes Closet          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("Enter Barcode for search.");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Text textLabel = new Text("Search Text : ");
        textLabel.setFont(myFont);
        textLabel.setWrappingWidth(150);
        textLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(textLabel, 0, 1);

        text = new TextField();
        text.setEditable(true);
        grid.add(text, 1, 1);


        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("Inventory", "");
            }
        });
        doneCont.getChildren().add(cancelButton);

        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("submit clicked");
                processAction(actionEvent);
            }
        });
        doneCont.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    // Process user input
    public void processAction(Event event) {

        if(text.getText() == null || text.getText().isEmpty()) {
            displayErrorMessage("Please enter a Barcode.");
        } else if (text.getText().length() != 8) {
            displayErrorMessage("Barcode must be 8 digits long");
        } else if(!checkGender(text.getText())) {
            displayErrorMessage("First letter of the barcode can be 0 (male) or 1 (female)");
        }
        else {
            clearErrorMessage();
            String[] values = new String[2];
            values[0] = (String) text.getText();
            values[1] = (String) "Barcode";
            if(modDelCheckFlag == "ins") {
                    myModel.stateChangeRequest("InsertInventoryView", values[0]);
            } else {
                myModel.stateChangeRequest("SearchClothingCollection", values);
            //myModel.stateChangeRequest("SearchColorCollection", null);
            }
        }
    }

    // Checks first digit of barcode to make sure gender can be parsed correctly when adding
    private boolean checkGender(String barcode) {
        Character firstDigit = barcode.charAt(0);
        return firstDigit.equals('0') || firstDigit.equals('1');
    }

    private boolean pubYearHasLetter(String str) {
        for(int i = 0; i < str.length(); i++) {
            if(Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        //searchMethod.getItems().add("Alpha Code");
        //searchMethod.getItems().add("Description");
//        status.getItems().add("Active");
//        status.getItems().add("Inactive");

        //bookId.setText((String)myModel.getState("bookId"));
//		bookTitle.setText((String)myModel.getState("bookTtitle"));
//		author.setText((String)myModel.getState("author"));
//	 	pubYear.setText((String)myModel.getState("pubYear"));
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        switch (key) {
            case "duplicateBarcode":
                displayErrorMessage("Duplicate Barcode");
                break;
            case "noBarcodeFound":
                displayErrorMessage("No Barcode Found");
                break;
        }

        //if (key.equals("ServiceCharge") == true)
        //{
        //	String val = (String)value;
        //	pubYear.setText(val);
        //	displayMessage("Service Charge Imposed: $ " + val);
        //}
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
        text.requestFocus();
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

}

//---------------------------------------------------------------
//	Revision History:
//


