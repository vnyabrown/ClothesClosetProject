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

public class InsertArticleView extends View {

    Properties props;
    private TextField descriptionField;
    private TextField barcodePrefixField;
    private TextField alphaCodeField;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;

    public InsertArticleView(IModel article) {
        super(article, "InsertArticleView");

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

//        populateFields();

        myModel.subscribe("successfulModify", this);
        myModel.subscribe("unsuccessfulModify", this);
    } // end of Constructor

    private Node createTitle() {

        Text titleText = new Text("       Insert Article Type          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);


        return titleText;
    }

    private GridPane createFormContents() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("ARTICLE TYPE INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Label descriptionLabel = new Label("Description: ");
        grid.add(descriptionLabel, 0, 1);
        descriptionField = new TextField();
        grid.add(descriptionField, 1, 1);

        Label barcodePrefixLabel = new Label("Barcode Prefix: ");
        grid.add(barcodePrefixLabel, 0, 2);
        barcodePrefixField = new TextField();
        grid.add(barcodePrefixField, 1, 2);

        Label alphaCodeLabel = new Label("Alpha Code: ");
        grid.add(alphaCodeLabel, 0, 3);
        alphaCodeField = new TextField();
        grid.add(alphaCodeField, 1, 3);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        grid.add(submitButton, 0, 4);
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Article", "");
            }
        });
        grid.add(cancelButton, 1, 4);
        return grid;
    }

    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void processAction(Event evt)
    {
        clearErrorMessage();
        // DEBUG: System.out.println("InsertArticleView.actionPerformed()");
        //System.out.println("Logic TBA");

        // PROCESS FIELDS SUBMITTED
        String descriptionEntered = descriptionField.getText();
        String barcodePrefixEntered = barcodePrefixField.getText();
        String alphaCodeEntered = alphaCodeField.getText();

        // Check all fields should not be empty
        if (descriptionEntered == null || descriptionEntered.isEmpty()) // Description field should not be empty
        {
            displayErrorMessage("Please enter a description for Article Type!");
            descriptionField.requestFocus();
        }
        else if (barcodePrefixEntered == null || barcodePrefixEntered.isEmpty())
        {
            displayErrorMessage("Please enter a Barcode Prefix for Article Type!");
            barcodePrefixField.requestFocus();
        }
        else if (alphaCodeEntered == null || alphaCodeEntered.isEmpty())
        {
            displayErrorMessage("Please enter an Alpha Code for Article Type!");
            alphaCodeField.requestFocus();
        }
        else if (!checkBarcodePrefix(barcodePrefixEntered)) {
            barcodePrefixField.requestFocus();
        }
        else
        {
            // Add fields to a property object for inserting
            barcodePrefixEntered = formatBarcodePrefix(barcodePrefixEntered);
            props = new Properties();
            props.setProperty("Description", descriptionEntered);
            props.setProperty("BarcodePrefix", barcodePrefixEntered);
            props.setProperty("AlphaCode", alphaCodeEntered);
            //props.setProperty("Status", null);

            try
            {
                myModel.stateChangeRequest("InsertArticle", props); // Call stateChangeRequest to insert an article
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

    private String formatBarcodePrefix(String pfx) {
        if(pfx.length() == 1) {
            return "0" + pfx;
        }
        return pfx;
    }

    private boolean checkBarcodePrefix(String pfx) {
        if(pfx.length() > 2) {
            displayErrorMessage("Barcode prefix can only be at max two digits.");
            return false;
        } else if(pfx.matches("[0-9]+")) {
            return true;
        }
        displayErrorMessage("Barcode prefix can only contain digits.");
        return false;
    }

    @Override
    public void updateState(String key, Object value) {
        if(key.equals("successfulModify")) {
            displaySuccessMessage("New Article Type Successfully Inserted");
        } else if(key.equals("unsuccessfulModify")) {
            displayErrorMessage("Error Inserting New Article Type");
        } else {
            System.out.println("else updateState in insertArticleView");
        }

    }

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    public void displaySuccessMessage(String message)
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