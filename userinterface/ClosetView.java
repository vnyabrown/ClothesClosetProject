package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class
ClosetView extends View {

    private Button articleButton;
    private Button colorButton;
    private Button inventoryButton;
    private Button quitButton;

    // For showing error message
    private MessageView statusLog;

    public ClosetView(IModel closet) {
        super(closet, "ClosetView");

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

    private Node createTitle()
    {

        Text titleText = new Text("       Brockport Clothes Closet          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);


        return titleText;
    }

    private GridPane createFormContents()
    {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        articleButton = new Button("Article");
        articleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Article", "");
            }
        });

        colorButton = new Button("Color");
        colorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Color", "");
            }
        });

        inventoryButton = new Button("Inventory");
        inventoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Inventory", "");
            }
        });

        quitButton = new Button("Quit");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {System.exit(0);}
        });

        grid.add(articleButton, 0, 0);
        grid.add(colorButton, 1, 0);
        grid.add(inventoryButton, 0, 1);
        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
        btnContainer.getChildren().add(quitButton);
        grid.add(btnContainer, 0, 2);

        return grid;
    }

    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
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
