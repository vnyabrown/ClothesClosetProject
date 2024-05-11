package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.ArticleTypeCollection;
import model.ColorCollection;

import java.util.Properties;
import java.util.function.UnaryOperator;
import java.util.Vector;

import static model.Closet.clothMod;

public class ModifyClothingView extends View {

    Properties props = clothMod;
    private TextField genderField;
    private TextField sizeField;
    private TextField articleTypeField;
    private ComboBox color1Field;
    private ComboBox color2Field;
    private TextField brandField;
    private TextField notesField;
    private TextField donorLastnameField;
    private TextField donorFirstnameField;
    private TextField donorPhoneField;
    private TextField donorEmailField;
    private TextField dateDonatedField;

    private Vector<model.Color> colorObj = null;
    ColorCollection colorCollection = new ColorCollection();
    ArticleTypeCollection articleTypeCollection = new ArticleTypeCollection();

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

        colorObj = colorCollection.getAllValidColors();
        populateComboBoxes();

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
        genderField.clear();
        genderField.setText(props.getProperty("Gender"));

        Label sizeLabel = new Label("Size: ");
        grid.add(sizeLabel, 0, 2);
        sizeField = new TextField();
        grid.add(sizeField, 1, 2);
        sizeField.clear();
        sizeField.setText(props.getProperty("Size"));


        Label articleTypeLabel = new Label("Article Type: ");
        grid.add(articleTypeLabel, 0, 3);
        articleTypeField = new TextField();
        grid.add(articleTypeField, 1, 3);
        articleTypeField.clear();
        articleTypeField.setText(props.getProperty("ArticleType"));


        Label color1Label = new Label("Color 1: ");
        grid.add(color1Label, 0, 4);
        color1Field = new ComboBox<String>();
        color1Field.setMinSize(100, 20);
        grid.add(color1Field, 1, 4);


        Label color2Label = new Label("Color 2: ");
        grid.add(color2Label, 0, 5);
        color2Field = new ComboBox<String>();
        color2Field.setMinSize(100, 20);
        grid.add(color2Field, 1, 5);


        Label brandLabel = new Label("Brand: ");
        grid.add(brandLabel, 0, 6);
        brandField = new TextField();
        grid.add(brandField, 1, 6);
        brandField.clear();
        brandField.setText(props.getProperty("Brand"));


        Label notesLabel = new Label("Notes: ");
        grid.add(notesLabel, 0, 7);
        notesField = new TextField();
        grid.add(notesField, 1, 7);
        notesField.clear();
        notesField.setText(props.getProperty("Notes"));

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
        addPhoneFormatter(donorPhoneField);


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
        articleTypeField.setDisable(true);

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

        //Convert comboBox descriptions back to barcode prefixes
        String color1 = colorCollection.getColorPFXFromDescription((String)color1Field.getValue());
        String color2 = colorCollection.getColorPFXFromDescription((String)color2Field.getValue());

        // PROCESS FIELDS SUBMITTED
        String[] values = new String[]{genderField.getText(), sizeField.getText(),
                color1, color2, brandField.getText(), notesField.getText(),
                donorLastnameField.getText(), donorFirstnameField.getText(), donorPhoneField.getText(),
                donorEmailField.getText(), dateDonatedField.getText()};


            myModel.stateChangeRequest("ModifyClothing", values);
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

        //pulling barcodePFX from props and converting to descriptions
        articleTypeField.setText(articleTypeCollection.getArticleDescriptionFromPFX(props.getProperty("ArticleType")));
        color1Field.getSelectionModel().select(colorCollection.getColorDescriptionFromPFX(props.getProperty("Color1")));
        color2Field.getSelectionModel().select(colorCollection.getColorDescriptionFromPFX(props.getProperty("Color2")));

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
        brandField.clear();
        notesField.clear();
        donorLastnameField.clear();
        donorFirstnameField.clear();
        donorPhoneField.clear();
        donorEmailField.clear();
        dateDonatedField.clear();
    }

    public void populateComboBoxes()
    {
        for (int num = 0; num < colorObj.size(); num++) {
            Vector<String> filler = colorObj.elementAt(num).getFields();
            //System.out.println(filler);
            color1Field.getItems().add(filler.get(1));
            color2Field.getItems().add(filler.get(1));

        }

    }
}
