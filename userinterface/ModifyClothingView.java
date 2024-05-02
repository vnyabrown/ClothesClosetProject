package userinterface;

import impresario.IModel;
import javafx.beans.value.ChangeListener;
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

import javax.swing.event.ChangeEvent;
import java.util.Properties;

import static model.Closet.clothMod;

public class ModifyClothingView extends View {

    Properties props = clothMod;
    private TextField genderField;
    private TextField sizeField;
    private TextField articleTypeField;
    private TextField color1Field;
    private TextField color2Field;
    private TextField brandField;
    private TextField notesField;
    private TextField donorLastnameField;
    private TextField donorFirstnameField;
    private TextField donorPhoneField;
    private TextField donorEmailField;
    private TextField dateDonatedField;

    private Button submitButton;
    private Button cancelButton;

    // For showing error message
    private MessageView statusLog;

    private boolean newView = true;


    public ModifyClothingView(IModel color)
    {
        super(color, "ModifyClothingView");

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

        newView = false;

        System.out.println("the clothing to mod is: " + clothMod);

        myModel.subscribe("successfulModify", this);
        myModel.subscribe("unsuccessfulModify", this);
        myModel.subscribe("updateText", this);

    }

    private Node createTitle() {

        Text titleText = new Text("       Modify Clothing          ");
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

        Text prompt = new Text("COLOR INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Label genderLabel = new Label("Gender: ");
        grid.add(genderLabel, 0, 1);
        genderField = new TextField();
        grid.add(genderField, 1, 1);


        Label sizeLabel = new Label("Size: ");
        grid.add(sizeLabel, 0, 2);
        sizeField = new TextField();
        grid.add(sizeField, 1, 2);


        Label articleTypeLabel = new Label("Article Type: ");
        grid.add(articleTypeLabel, 0, 3);
        articleTypeField = new TextField();
        grid.add(articleTypeField, 1, 3);


        Label color1Label = new Label("Color 1: ");
        grid.add(color1Label, 0, 4);
        color1Field = new TextField();
        grid.add(color1Field, 1, 4);


        Label color2Label = new Label("Color 2: ");
        grid.add(color2Label, 0, 5);
        color2Field = new TextField();
        grid.add(color2Field, 1, 5);


        Label brandLabel = new Label("Brand: ");
        grid.add(brandLabel, 0, 6);
        brandField = new TextField();
        grid.add(brandField, 1, 6);


        Label notesLabel = new Label("Notes: ");
        grid.add(notesLabel, 0, 7);
        notesField = new TextField();
        grid.add(notesField, 1, 7);


        Label donorLastnameLabel = new Label("Donor Last Name: ");
        grid.add(donorLastnameLabel, 0, 8);
        donorLastnameField = new TextField();
        grid.add(donorLastnameField, 1, 8);


        Label donorFirstnameLabel = new Label("Donor First Name: ");
        grid.add(donorFirstnameLabel, 0, 9);
        donorFirstnameField = new TextField();
        grid.add(donorFirstnameField, 1, 9);


        Label donorPhoneLabel = new Label("Donor Phone: ");
        grid.add(donorPhoneLabel, 0, 10);
        donorPhoneField = new TextField();
        grid.add(donorPhoneField, 1, 10);


        Label donorEmailLabel = new Label("Donor Email: ");
        grid.add(donorEmailLabel, 0, 11);
        donorEmailField = new TextField();
        grid.add(donorEmailField, 1, 11);


        Label dateDonatedLabel = new Label("Date Donated: ");
        grid.add(dateDonatedLabel, 0, 12);
        dateDonatedField = new TextField();
        grid.add(dateDonatedField, 1, 12);



        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        grid.add(submitButton, 0, 13);
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                resetTextFields();
                clearErrorMessage();
                //System.out.println("clothing Mod: " + clothMod);
                myModel.stateChangeRequest("Inventory", "");
            }
        });
        grid.add(cancelButton, 1, 13);

        fillTextFields();

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
        String[] values = new String[]{genderField.getText(), sizeField.getText(), articleTypeField.getText(),
                color1Field.getText(), color2Field.getText(), brandField.getText(), notesField.getText(),
                donorLastnameField.getText(), donorFirstnameField.getText(), donorPhoneField.getText(),
                donorEmailField.getText(), dateDonatedField.getText()};


        myModel.stateChangeRequest("ModifyClothing", values);

    }

    @Override
    public void updateState(String key, Object value) {
        clearErrorMessage();
        if(key.equals("successfulModify")){
            displaySuccessMessage("update successfully in db");
        } else if(key.equals("unsuccessfulModify")){
            displayErrorMessage("unsuccessfull update");
        } else if(key.equals("updateText")){
            System.out.println("Updating Text");
            props = clothMod;
            fillTextFields();
        } else {
            displaySuccessMessage("something went wrong, find else in update state");
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
    //---------------------------------------------------------- .toString()
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    public void fillTextFields(){
        genderField.setText(props.getProperty("Gender"));
        sizeField.setText(props.getProperty("Size"));
        articleTypeField.setText(props.getProperty("ArticleType"));
        color1Field.setText(props.getProperty("Color1"));
        color2Field.setText(props.getProperty("Color2"));
        brandField.setText(props.getProperty("Brand"));
        notesField.setText(props.getProperty("Notes"));
        donorLastnameField.setText(props.getProperty("DonorLastname"));
        donorFirstnameField.setText(props.getProperty("DonorFirstname"));
        donorPhoneField.setText(props.getProperty("DonorPhone"));
        donorEmailField.setText(props.getProperty("DonorEmail"));
        dateDonatedField.setText(props.getProperty("DateDonated"));

    }

    private void resetTextFields(){
        genderField.clear();
        sizeField.clear();
        articleTypeField.clear();
        color1Field.clear();
        color2Field.clear();
        brandField.clear();
        notesField.clear();
        donorLastnameField.clear();
        donorFirstnameField.clear();
        donorPhoneField.clear();
        donorEmailField.clear();
        dateDonatedField.clear();
    }
}
