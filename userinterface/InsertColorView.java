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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class InsertColorView extends View {

    private TextField descriptionField;
    private TextField barcodePrefixField;
    private TextField alphaCodeField;
    private TextField statusField;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;

    public InsertColorView(IModel article) {
        super(article, "InsertArticle");

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
        myModel.subscribe("LoginError", this);
    }

    private Node createTitle() {

        Text titleText = new Text("       Brockport Clothes Closet          ");
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

        Label descriptionLabel = new Label("Description: ");
        grid.add(descriptionLabel, 0, 0);
        descriptionField = new TextField();
        grid.add(descriptionField, 1, 0);
        Label barcodePrefixLabel = new Label("Barcode Prefix: ");
        grid.add(barcodePrefixLabel, 0, 1);
        barcodePrefixField = new TextField();
        grid.add(barcodePrefixField, 1, 1);
        Label alphaCodeLabel = new Label("Alpha Code: ");
        grid.add(alphaCodeLabel, 0, 2);
        alphaCodeField = new TextField();
        grid.add(alphaCodeField, 1, 2);
        Label statusLabel = new Label("Status: ");
        grid.add(statusLabel, 0, 3);
        statusField = new TextField();
        grid.add(statusField, 1, 3);

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
        // DEBUG: System.out.println("InsertArticleView.actionPerformed()");
        System.out.println("Logic TBA");

    }

    @Override
    public void updateState(String key, Object value) {

    }

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
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
