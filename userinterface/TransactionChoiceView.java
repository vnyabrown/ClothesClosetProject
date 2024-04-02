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

public class TransactionChoiceView extends View {

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

    public TransactionChoiceView(IModel closet) {
        super(closet, "TransactionChoiceView");

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

    private GridPane createFormContents()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        addArticleButton = new Button("Insert Article");
        addArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("InsertArticle", null);
            }
        });
        modArticleButton = new Button("Modify Article");
        modArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("ModifyArticle", null);
            }
        });
        delArticleButton = new Button("Delete Article");
        delArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("DeleteArticle", null);
            }
        });

        HBox articleContainer = new HBox(10);
        articleContainer.setAlignment(Pos.CENTER);
        articleContainer.getChildren().add(addArticleButton);
        articleContainer.getChildren().add(modArticleButton);
        articleContainer.getChildren().add(delArticleButton);
        grid.add(articleContainer, 0, 0);


        addColorButton = new Button("Insert Color");
        addColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("InsertColor", null);
            }
        });
        modColorButton = new Button("Modify Color");
        modColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("ModifyColor", null);
            }
        });
        delColorButton = new Button("Delete Color");
        delColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("DeleteColor", null);
            }
        });
        HBox colorContainer = new HBox(10);
        colorContainer.setAlignment(Pos.CENTER);
        colorContainer.getChildren().add(addColorButton);
        colorContainer.getChildren().add(modColorButton);
        colorContainer.getChildren().add(delColorButton);
        grid.add(colorContainer, 0, 2);


        addClothingButton = new Button("Insert Clothing Item");
        addClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("InsertClothing", null);
            }
        });
        modClothingButton = new Button("Modify Clothing Item");
        modClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("ModifyClothing", null);
            }
        });
        delClothingButton = new Button("Delete Clothing Item");
        delClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("DeleteClothing", null);
            }
        });

        HBox clothingContainer = new HBox(10);
        clothingContainer.setAlignment(Pos.CENTER);
        clothingContainer.getChildren().add(addClothingButton);
        clothingContainer.getChildren().add(modClothingButton);
        clothingContainer.getChildren().add(delClothingButton);
        grid.add(clothingContainer, 0, 4);


        checkoutButton = new Button("Checkout Item(s)");
        checkoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("Checkout", null);
            }
        });
        listInventoryButton = new Button("List Available Items");
        listInventoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("ListStock", null);
            }
        });
        listCheckedOutButton = new Button("List Checked Out Items");
        listCheckedOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Unimplemented");
                myModel.stateChangeRequest("ListCheckout", null);
            }
        });
        HBox miscContainer = new HBox(10);
        miscContainer.setAlignment(Pos.CENTER);
        miscContainer.getChildren().add(checkoutButton);
        miscContainer.getChildren().add(listInventoryButton);
        miscContainer.getChildren().add(listCheckedOutButton);
        grid.add(miscContainer, 0, 6);

        return grid;
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
