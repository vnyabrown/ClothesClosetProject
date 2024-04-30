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

public class CheckoutClothingView extends View {

    Properties props;
    private TextField netId;
    private TextField firstName;
    private TextField lastName;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;

    public CheckoutClothingView(IModel article) {
        super(article, "CheckoutClothingView");

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

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("successfulModify", this);
        myModel.subscribe("unsuccessfulModify", this);
    }

    private Node createTitle() {

        Text titleText = new Text("       Checkout Clothing Item    ");
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

        Text prompt = new Text("RECEIVER INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Label netIdLabel = new Label("Student ID: ");
        grid.add(netIdLabel, 0, 1);
        netId = new TextField();
        grid.add(netId, 1, 1);

        Label firstNameLabel = new Label("First Name: ");
        grid.add(firstNameLabel, 0, 2);
        firstName = new TextField();
        grid.add(firstName, 1, 2);

        Label lastNameLabel = new Label("Last Name: ");
        grid.add(lastNameLabel, 0, 3);
        lastName = new TextField();
        grid.add(lastName, 1, 3);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        grid.add(submitButton, 1, 4);
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchForClothing", "");
            }
        });
        grid.add(cancelButton, 0, 4);
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
        String netIdEntered = netId.getText();
        String firstNameEntered = firstName.getText();
        String lastNameEntered = lastName.getText();

        // Check all fields should not be empty
        if (netIdEntered == null || netIdEntered.isEmpty()) // netId field should not be empty
        {
            displayErrorMessage("Please enter a netId for receiver!");
            netId.requestFocus();
        }
        else if (firstNameEntered == null || firstNameEntered.isEmpty())
        {
            displayErrorMessage("Please enter a first name for receiver!");
            firstName.requestFocus();
        }
        else if (lastNameEntered == null || lastNameEntered.isEmpty())
        {
            displayErrorMessage("Please enter an last name for receiver!");
            lastName.requestFocus();
        }
        else
        {
            System.out.println("no error");
            // Add fields to a property object for inserting
            props = new Properties();
            props.setProperty("ReceiverNetid", netIdEntered);
            props.setProperty("ReceiverFirstname", firstNameEntered);
            props.setProperty("ReceiverLastname", lastNameEntered);
            //props.setProperty("Status", null);

            try
            {
                myModel.stateChangeRequest("checkoutClothing", props); // Call stateChangeRequest to insert an article
            }
            catch(Exception ex)
            {
                displayErrorMessage("Failed to insert an Article Type!");
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void updateState(String key, Object value) {
        switch (key) {
            case "successfulModify":
                displaySuccessMessage("Clothing item checked out.");
                break;
            case "unsuccessfulModify":
                displayErrorMessage("Error checking out clothing item.");
                break;
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