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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;

// Note for what to do later
// Add view to viewFactory
// Add view change to stateChangeRequest in Teller and test
// Implement processAction with Teller and ArticleType Class

public class ArticleTypeView extends View {
    // GUI stuff
    // Description, Barcode Prefix and Alpha code
    private TextField atDescription;
    private TextField atBarcode;
    private TextField atAlpha;

    private Button submitButton;
    private Button doneButton;
    private ComboBox<String> statusBox;

    // For showing error message
    private MessageView statusLog;

    public ArticleTypeView(IModel model)
    {
        super(model, "ArticleTypeView");

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

        // populateFields();
    }

    // Create the label (Text) for the title of the screen
    // -------------------------------------------------------------
    private Node createTitle()
    {
        Text titleText = new Text(("       Add Article Type          "););
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKBLUE);
        return titleText;
    } // end of createTitle

    // Create the main form contents
    // -------------------------------------------------------------
    private GridPane createFormContents()
    {
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

        // Data Entry Fields
        // Article Type Description
        Label articleTypeDesc = new Label("Description");
        grid.add(articleTypeDesc, 0, 1);

        atDescription = new TextField();
        grid.add(atDescription, 1, 1);

        // Article Type Barcode Prefix
        Label articleTypeBarcode = new Label("Barcode Prefix");
        grid.add(articleTypeBarcode, 0, 2);

        atBarcode = new TextField();
        grid.add(atBarcode, 1, 2);

        // Article Type Alpha Code
        Label articleTypeAlpha = new Label("Alpha Code");
        grid.add(articleTypeAlpha, 0, 3);

        atAlpha = new TextField();
        grid.add(atAlpha, 1, 3);

        // Submit Button
        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //processAction(e);
            }
        });

        // Done Button
        doneButton = new Button("Done");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // processAction(e);
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });

        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(doneButton);
        grid.add(btnContainer, 1, 5);

        return grid;
    } // end of createFormContents

    // This method processes events generated from our GUI components.
	// Make the ActionListeners delegate to this method
	//-------------------------------------------------------------
    public void processAction(Event evt)
    {
        clearErrorMessage();

        // Get fields info
        // Check all fields should be populated
        // Add fields to a property object for inserting
        // Call stateChangeRequest to add Article Type
    } // end of processAction

    // Create the status log field
    // -------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage) {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    } // end of messageView

    /**
	 * Display success message
	 */
	//----------------------------------------------------------
    public void displaySuccessMessage(String message)
    {
        statusLog.displayMessage(message);
    } // end of displaySuccessMessage

    /**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	} // end of displayErrorMessage

    /**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	} // end of clearErrorMessage

    @Override
    public void updateState(String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateState'");
    } 
}
