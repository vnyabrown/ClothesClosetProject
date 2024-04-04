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

public class ArticleChoiceView extends View {

    private Button addArticleButton;
    private Button modArticleButton;
    private Button delArticleButton;
    private Button quitButton;

    // For showing error message
    private MessageView statusLog;

    public ArticleChoiceView(IModel closet) {
        super(closet, "ArticleChoiceView");

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

        Text titleText = new Text("       Select an Article Option          ");
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
                myModel.stateChangeRequest("InsertArticleView", null);
            }
        });
        modArticleButton = new Button("Modify Article");
        modArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchForArticleType", null);
            }
        });
        delArticleButton = new Button("Delete Article");
        delArticleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SearchForArticleType", null);
            }
        });
        quitButton = new Button("Back");
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {myModel.stateChangeRequest("CancelArticleTransaction", null);}
        });

        grid.add(addArticleButton, 0, 0);
        grid.add(modArticleButton, 0, 1);
        grid.add(delArticleButton, 0, 2);
        grid.add(quitButton, 0, 3);

        return grid;
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
