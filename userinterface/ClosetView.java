package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

import java.util.Properties;

public class ClosetView extends View {

    private Button addArticleButton;
    private Button modArticleButton;
    private Button delArticleButton;
    private Button addColorButton;
    private Button modColorButton;
    private Button delColorButton;
    private Button addClothingButton;
    private Button modClothingButton;
    private Button delClothingButton;
    private Button checkoutButton;
    private Button listInventoryButton;
    private Button listCheckedOutButton;

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

        addArticleButton = new Button("Add Article");
        addArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        modArticleButton = new Button("Modify Article");
        modArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        delArticleButton = new Button("Delete Article");
        delArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        HBox articleContainer = new HBox(10);
        articleContainer.setAlignment(Pos.CENTER);
        articleContainer.getChildren().add(addArticleButton);
        articleContainer.getChildren().add(modArticleButton);
        articleContainer.getChildren().add(delArticleButton);
        grid.add(articleContainer, 0, 0);


        addColorButton = new Button("Add Color");
        addColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        modColorButton = new Button("Modify Color");
        modColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        delColorButton = new Button("Delete Color");
        delColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        HBox colorContainer = new HBox(10);
        colorContainer.setAlignment(Pos.CENTER);
        colorContainer.getChildren().add(addColorButton);
        colorContainer.getChildren().add(modColorButton);
        colorContainer.getChildren().add(delColorButton);
        grid.add(colorContainer, 0, 1);


        addClothingButton = new Button("Add Clothing Item");
        addClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        modClothingButton = new Button("Modify Clothing Item");
        modClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        delClothingButton = new Button("Delete Clothing Item");
        delClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        HBox clothingContainer = new HBox(10);
        clothingContainer.setAlignment(Pos.CENTER);
        clothingContainer.getChildren().add(addClothingButton);
        clothingContainer.getChildren().add(modClothingButton);
        clothingContainer.getChildren().add(delClothingButton);
        grid.add(clothingContainer, 0, 2);


        checkoutButton = new Button("Checkout Item(s)");
        checkoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        listInventoryButton = new Button("List Available Items");
        listInventoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        listCheckedOutButton = new Button("List Checked Out Items");
        listCheckedOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        HBox miscContainer = new HBox(10);
        miscContainer.setAlignment(Pos.CENTER);
        miscContainer.getChildren().add(checkoutButton);
        miscContainer.getChildren().add(listInventoryButton);
        miscContainer.getChildren().add(listCheckedOutButton);
        grid.add(miscContainer, 0, 3);

        return grid;
    }

    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void processAction(Event evt)
    {
        // DEBUG: System.out.println("ClosetView.actionPerformed()");
        System.out.println("Yay buttons!");

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
