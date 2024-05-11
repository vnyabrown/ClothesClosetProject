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
import javafx.scene.control.TextFormatter;
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
import java.util.function.UnaryOperator;

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
    private TextField receiverNetidField;
    private TextField receiverLastnameField;
    private TextField receiverFirstnameField;
    private TextField dateDonatedField;
    private TextField dateTakenField;

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
        grid.add(donorLastnameLabel, 0, 9);
        donorLastnameField = new TextField();
        grid.add(donorLastnameField, 1, 9);


        Label donorFirstnameLabel = new Label("Donor First Name: ");
        grid.add(donorFirstnameLabel, 0, 10);
        donorFirstnameField = new TextField();
        grid.add(donorFirstnameField, 1, 10);


        Label donorPhoneLabel = new Label("Donor Phone: ");
        grid.add(donorPhoneLabel, 0, 11);
        donorPhoneField = new TextField();
        grid.add(donorPhoneField, 1, 11);
        addPhoneFormatter(donorPhoneField);


        Label donorEmailLabel = new Label("Donor Email: ");
        grid.add(donorEmailLabel, 0, 12);
        donorEmailField = new TextField();
        grid.add(donorEmailField, 1, 12);


        Label receiverNetidLabel = new Label("Receiver Netid: ");
        grid.add(receiverNetidLabel, 0, 13);
        receiverNetidField = new TextField();
        grid.add(receiverNetidField, 1, 13);


        Label receiverLastnameLabel = new Label("Receiver Last Name: ");
        grid.add(receiverLastnameLabel, 0, 14);
        receiverLastnameField = new TextField();
        grid.add(receiverLastnameField, 1, 14);


        Label receiverFirstnameLabel = new Label("Receiver First Name: ");
        grid.add(receiverFirstnameLabel, 0, 15);
        receiverFirstnameField = new TextField();
        grid.add(receiverFirstnameField, 1, 15);


        Label dateDonatedLabel = new Label("Date Donated: ");
        grid.add(dateDonatedLabel, 0, 16);
        dateDonatedField = new TextField();
        grid.add(dateDonatedField, 1, 16);


        Label dateTakenLabel = new Label("Date Taken: ");
        grid.add(dateTakenLabel, 0, 17);
        dateTakenField = new TextField();
        grid.add(dateTakenField, 1, 17);



        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        grid.add(submitButton, 0, 18);
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
        grid.add(cancelButton, 1, 18);

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

        if(!checkPhone(donorPhoneField.getText())) {
            donorPhoneField.requestFocus();
        } else if(!checkEmail(donorEmailField.getText())) {
            donorEmailField.requestFocus();
        } else {
            // PROCESS FIELDS SUBMITTED
            String[] values = new String[]{genderField.getText(), sizeField.getText(), articleTypeField.getText(),
                    color1Field.getText(), color2Field.getText(), brandField.getText(), notesField.getText(),
                    donorLastnameField.getText(), donorFirstnameField.getText(), donorPhoneField.getText(),
                    donorEmailField.getText(), receiverNetidField.getText(), receiverLastnameField.getText(),
                    receiverFirstnameField.getText(), dateDonatedField.getText(), dateTakenField.getText()};


            myModel.stateChangeRequest("ModifyClothing", values);
        }
    }

    // add phone formatter to text field
    private void addPhoneFormatter(TextField donorPhoneField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            System.out.println("newtext: " + newText);

            if(newText.length() > 14) {
                return null;
            }

            if(newText.matches("\\(*[0-9]*\\)? ?[0-9]*-*[0-9]*")) {//|| newText.isEmpty()){
                System.out.println("is digit: " + newText);
                System.out.println("getText:" + change.getText());
                if(newText.length() == 1 && !change.getText().isEmpty()) {
                    String str = "(" + change.getText();
                    change.setText(str);
                    change.setCaretPosition(newText.length() + 1);
                    change.setAnchor(newText.length() + 1);
                } else if(newText.length() == 4 && !change.getText().isEmpty()) {
                    String str = change.getText() + ") ";
                    change.setText(str);
                    change.setCaretPosition(newText.length() + 2);
                    change.setAnchor(newText.length() + 2);
                } else if(newText.length() == 9 && !change.getText().isEmpty()) {
                    String str = change.getText() + "-";
                    System.out.println("new new text: " + newText);
                    System.out.println("str: '" + str + "'");
                    change.setText(str);
                    change.setCaretPosition(newText.length() + 1);
                    change.setAnchor(newText.length() + 1);
                }
                return change;
            }
            System.out.println("no match: " + newText);
            return null;
        };


        TextFormatter<String> phoneFormater = new TextFormatter<>(filter);
        donorPhoneField.setTextFormatter(phoneFormater);
        ///------------------------------------//------------------------------------/-------------------------------------
    }

    // Check email
    private boolean checkEmail(String email) {
        if(!email.matches("[\\w]+@brockport.edu")) {
            displayErrorMessage("Email format: tamer10@brockport.edu");
            return false;
        }
        return true;
    }

    // Check phone number
    private boolean checkPhone(String phoneNum) {
        if(!phoneNum.matches("\\(([0-9]){3}\\) ([0-9]){3}-([0-9]){4}")) {
            displayErrorMessage("Phone number format: (111) 222-3333");
            return false;
        }
        return true;
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
        receiverNetidField.setText(props.getProperty("ReceiverNetid"));
        receiverLastnameField.setText(props.getProperty("ReceiverLastname"));
        receiverFirstnameField.setText(props.getProperty("ReceiverFirstname"));
        dateDonatedField.setText(props.getProperty("DateDonated"));
        dateTakenField.setText(props.getProperty("DateTaken"));

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
        receiverNetidField.clear();
        receiverLastnameField.clear();
        receiverFirstnameField.clear();
        dateDonatedField.clear();
        dateTakenField.clear();
    }
}
