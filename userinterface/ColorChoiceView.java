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

public class ColorChoiceView extends View {

    private Button addColorButton;
    private Button modColorButton;
    private Button delColorButton;
    private Button quitButton;
    public static String modDelCheckFlag = "";

    // For showing error message
    private MessageView statusLog;

    public ColorChoiceView(IModel closet) {
        super(closet, "ColorChoiceView");

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
        //myModel.subscribe("LoginError", this);
    }

    private Node createTitle() {

        Text titleText = new Text("       Select a Color Option          ");
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

        addColorButton = new Button("Insert Color");
        addColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("InsertColorView", null);
            }
        });
        modColorButton = new Button("Modify Color");
        modColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                modDelCheckFlag = "mod";
                myModel.stateChangeRequest("SearchForColor", null);
            }
        });
        delColorButton = new Button("Delete Color");
        delColorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                modDelCheckFlag = "del";
                myModel.stateChangeRequest("SearchForColor", null);
            }
        });
        quitButton = new Button("Cancel");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {myModel.stateChangeRequest("CancelColorTransaction", null);}
        });

        grid.add(addColorButton, 0, 0);
        grid.add(modColorButton, 0, 1);
        grid.add(delColorButton, 0, 2);
        grid.add(quitButton, 0, 3);

        return grid;
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
