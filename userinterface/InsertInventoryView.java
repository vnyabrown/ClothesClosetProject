package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Properties;

public class InsertInventoryView extends View {
   
    Properties props;
    private TextField barcodeField;
    private TextField genderField;
    private TextField sizeField;
    private TextField articleTypeField;
    private TextField color1Field;
    private TextField color2Field;
    private TextField brandField;
    private TextField notesField;
    private TextField donorLastNameField;
    private TextField donorFirstNameField;
    private TextField donorPhoneField;
    private TextField donorEmailField;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;

    public InsertInventoryView(IModel inventory)
    {
        super(inventory, "InsertInventoryView");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);
    } // end of Constructor

    private Node createTitle() {

        Text titleText = new Text("       Insert an Inventory Item          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    } // end of createTitle

    private GridPane createFormContents()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("INVENTORY INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Label barcodeLabel = new Label("Inveotry Item Barcode: ");
        grid.add(barcodeLabel, 0, 0);
        barcodeField = new TextField();
        grid.add(barcodeField, 1, 0);

        Label donorFirstNameLabel = new Label("Donor First Name: ");
        grid.add(donorFirstNameLabel, 0, 1);
        donorFirstNameField = new TextField();
        grid.add(donorFirstNameField, 1, 1);

        Label donorLastNameLabel = new Label("Donor Last Name: ");
        grid.add(donorLastNameLabel, 0, 2);
        donorLastNameField = new TextField();
        grid.add(donorLastNameField, 1, 2);

        Label donorPhoneLabel = new Label("Donor Phone: ");
        grid.add(donorPhoneLabel, 0, 3);
        donorPhoneField = new TextField();
        grid.add(donorPhoneField, 1, 3);

        Label donorEmailLabel = new Label("Donor Email: ");
        grid.add(donorEmailLabel, 0, 4);
        donorEmailField = new TextField();
        grid.add(donorEmailField, 1, 4);

        Label articleTypeLabel = new Label("Article Type: ");
        grid.add(articleTypeLabel, 0, 5);
        articleTypeField = new TextField();
        grid.add(articleTypeField, 1, 5);

        Label color1Label = new Label("Primary Color: ");
        grid.add(color1Label, 0, 6);
        color1Field = new TextField();
        grid.add(color1Field, 1, 6);

        Label color2Label = new Label("Secondary Color: ");
        grid.add(color2Label, 0, 7);
        color2Field = new TextField();
        grid.add(color2Field, 1, 7);

        Label brandLabel = new Label("Brand: ");
        grid.add(brandLabel, 0, 8);
        brandField = new TextField();
        grid.add(brandField, 1, 8);

        Label genderLabel = new Label("Gender: ");
        grid.add(genderLabel, 0, 9);
        genderField = new TextField();
        grid.add(genderField, 1, 9);

        Label sizeLabel = new Label("Size: ");
        grid.add(sizeLabel, 0, 10);
        sizeField = new TextField();
        grid.add(sizeField, 1, 10);

        Label notesLabel = new Label("Notes: ");
        grid.add(notesLabel, 0, 11);
        notesField = new TextField();
        grid.add(notesField, 1, 11);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        grid.add(submitButton, 0, 12);
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Color", "");
            }
        });
        grid.add(cancelButton, 1, 12);
        return grid;
    } // end of createFormContents

    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    } // end of createStatusLog

    public void processAction(Event evt)
    {
        clearErrorMessage();
        // DEBUG: System.out.println("InsertArticleView.actionPerformed()");
        //System.out.println("Logic TBA");

        // PROCESS FIELDS SUBMITTED
        String genderEntered = genderField.getText();
        String sizeEntered = sizeField.getText();
        String articleTypeEntered = articleTypeField.getText();
        String color1Entered = color1Field.getText();
        String color2Entered = color2Field.getText();
        String brandEntered = brandField.getText();
        String notesEntered = notesField.getText();
        String donorFirstNameEntered = donorFirstNameField.getText();
        String donorLastNameEntered = donorLastNameField.getText();
        String donorPhoneEntered = donorPhoneField.getText();
        String donorEmailEntered = donorEmailField.getText();
        // Check all fields should not be empty
        if (genderEntered == null) // Description field should not be empty
        {
            displayErrorMessage("Please enter a Gender for Clothing!");
            genderField.requestFocus();
        }
        else if (sizeEntered == null)
        {
            displayErrorMessage("Please enter a Size for Clothing!");
            sizeField.requestFocus();
        }
        else if (articleTypeEntered == null)
        {
            displayErrorMessage("Please enter an Article Type for Clothing!");
            articleTypeField.requestFocus();
        }
        else if (color1Entered == null)
        {
            displayErrorMessage("Please enter a Primary Color for Clothing!");
            color1Field.requestFocus();
        }
        else if (color2Entered == null)
        {
            displayErrorMessage("Please enter a Secondary Color for Clothing!");
            color2Field.requestFocus();
        }
        else if (brandEntered == null)
        {
            displayErrorMessage("Please enter a Brand for Clothing!");
            brandField.requestFocus();
        }
        else if (donorFirstNameEntered == null)
        {
            displayErrorMessage("Please enter a Donor First Name for Clothing!");
            donorFirstNameField.requestFocus();
        }
        else if (donorLastNameEntered == null)
        {
            displayErrorMessage("Please enter a Donor Last Name for Clothing!");
            donorLastNameField.requestFocus();
        }
        else if (donorPhoneEntered == null)
        {
            displayErrorMessage("Please enter a Donor Phone for Clothing!");
            donorPhoneField.requestFocus();
        }
        else if (donorEmailEntered == null)
        {
            displayErrorMessage("Please enter a Donor Email for Clothing!");
            donorEmailField.requestFocus();
        }
        else
        {
            props = new Properties();
            props.setProperty("Gender", genderEntered);
            props.setProperty("Size", sizeEntered);
            props.setProperty("ArticleType", articleTypeEntered);
            props.setProperty("Color1", color1Entered);
            props.setProperty("Color2", color2Entered);
            props.setProperty("Brand", brandEntered);
            props.setProperty("Notes", notesEntered);
            props.setProperty("DonorLastName", donorLastNameEntered);
            props.setProperty("DonorFirstName", donorFirstNameEntered);
            props.setProperty("DonorPhone", donorPhoneEntered);
            props.setProperty("DonorEmail", donorEmailEntered);


            try
            {
                // TODO: need to finalize this state request within Closet
                myModel.stateChangeRequest("InsertInventory", props); // Call stateChangeRequest to insert an inventoryItem
                displaySuccessMessage("Successfully inserted a new Inventory item!");
            }
            catch(Exception ex)
            {
                displayErrorMessage("Failed to insert a Inventory item!");
                ex.printStackTrace();
            }
        }
    } // end of processAction

    @Override
    public void updateState(String key, Object value) {}

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    } // end of errorMessage

    public void displaySuccessMessage(String message)
    {
        statusLog.displayMessage(message);
    } // end of successMessage

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    } // end of clearMessage

} // end of InsertInventoryView