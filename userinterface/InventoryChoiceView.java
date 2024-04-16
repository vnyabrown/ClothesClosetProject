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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class InventoryChoiceView extends View {

    private Button addClothingButton;
    private Button modClothingButton;
    private Button delClothingButton;
    private Button checkoutButton;
    private Button listInventoryButton;
    private Button listCheckoutButton;
    private Button quitButton;

    // For showing error message
    private MessageView statusLog;

    public InventoryChoiceView(IModel closet) {
        super(closet, "InventoryChoiceView");

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

        Text titleText = new Text("       Select an Inventory Option          ");
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

        addClothingButton = new Button("Insert Clothing");
        addClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("InsertClothingView", null);
            }
        });
        modClothingButton = new Button("Modify Clothing");
        modClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchForClothing", null);
            }
        });
        delClothingButton = new Button("Delete Clothing");
        delClothingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchForClothing", null);
            }
        });
        quitButton = new Button("Back");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {myModel.stateChangeRequest("CancelClothingTransaction", null);}
        });
        checkoutButton = new Button("Checkout");
        checkoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CheckoutView", null);
            }
        });
        listInventoryButton = new Button("List Inventory");
        listInventoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("ListInventoryView", null);
            }
        });
        listCheckoutButton = new Button("List Checkout");
        listCheckoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("ListInventoryView", null);
            }
        });
        quitButton = new Button("Back");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {myModel.stateChangeRequest("CancelInventoryTransaction", null);}
        });

        grid.add(addClothingButton, 0, 0);
        grid.add(modClothingButton, 0, 1);
        grid.add(delClothingButton, 0, 2);
        grid.add(checkoutButton, 1, 0);
        grid.add(listInventoryButton, 1, 1);
        grid.add(listCheckoutButton, 1, 2);
        grid.add(quitButton, 0, 3);

        return grid;
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
