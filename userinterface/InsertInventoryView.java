package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Properties;
import java.util.Vector;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static model.Closet.barcode;
import static model.Closet.clothMod;

import model.ArticleType;
import model.ArticleTypeCollection;
import model.Color;
import model.ColorCollection;

public class InsertInventoryView extends View {
   
    javafx.scene.paint.Color color;

    Properties props;
    Properties articleProps;
    Properties colorProps;

    ArticleType newAT;
    Color newCo;

    private String barcodeEntered;

    //These fields will be autopopulated by barcode
    String genderEntered = new String();
    String articleTypeEntered = new String();
    String color1Entered = new String();

    private TextField barcodeField;
    private TextField genderField;
    private ComboBox sizeField;
    private TextField articleTypeField;
    private ComboBox color1Field;
    private ComboBox color2Field;
    private TextField brandField;
    private TextField notesField;
    private TextField donorLastNameField;
    private TextField donorFirstNameField;
    private TextField donorPhoneField;
    private TextField donorEmailField;

    //Article and Color to get Descriptions & BarcodePrefixes
    private Vector<model.Color> colorObj = null;
    ColorCollection colorCollection = new ColorCollection();
    ArticleTypeCollection articleTypeCollection = new ArticleTypeCollection();

    private Button submitButton;
    private Button cancelButton;

    private boolean validBarcode = false;

    // For showing error message
    private MessageView statusLog;

    public InsertInventoryView(IModel inventory)
    {
        super(inventory, "InsertInventoryView");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        barcodeEntered = barcode;
        barcodeField.setText(barcode);
        parseBarcode();

        myModel.subscribe("updateText", this);

        getChildren().add(container);

        colorObj = colorCollection.getAllValidColors();
        populateComboBoxes();

        myModel.subscribe("successfulModify", this);
        myModel.subscribe("unsuccessfulModify", this);

    } // end of Constructor

    private Node createTitle() {

        Text titleText = new Text("       Insert a Clothing Item          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(color.DARKGREEN);

        return titleText;
    } // end of createTitle

    private GridPane createFormContents()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("CLOTHING INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Label barcodeLabel = new Label("Barcode: ");
        grid.add(barcodeLabel, 0, 1);
        barcodeField = new TextField();
        grid.add(barcodeField, 1, 1);

        Label donorFirstNameLabel = new Label("Donor First Name: ");
        grid.add(donorFirstNameLabel, 0, 2);
        donorFirstNameField = new TextField();
        grid.add(donorFirstNameField, 1, 2);

        Label donorLastNameLabel = new Label("Donor Last Name: ");
        grid.add(donorLastNameLabel, 0, 3);
        donorLastNameField = new TextField();
        grid.add(donorLastNameField, 1, 3);

        Label donorPhoneLabel = new Label("Donor Phone: ");
        grid.add(donorPhoneLabel, 0, 4);
        donorPhoneField = new TextField();
        addPhoneFormatter(donorPhoneField);
        grid.add(donorPhoneField, 1, 4);


        Label donorEmailLabel = new Label("Donor Email: ");
        grid.add(donorEmailLabel, 0, 5);
        donorEmailField = new TextField();
        grid.add(donorEmailField, 1, 5);

        Label articleTypeLabel = new Label("Article Type: ");
        grid.add(articleTypeLabel, 0, 6);
        articleTypeField = new TextField();
        grid.add(articleTypeField, 1, 6);

        // Change to dropdown
        Label color1Label = new Label("Primary Color: ");
        grid.add(color1Label, 0, 7);
        color1Field = new ComboBox<String>();
        color1Field.setMinSize(100, 20);
        grid.add(color1Field, 1, 7);

        // Change to dropdown
        Label color2Label = new Label("Secondary Color: ");
        grid.add(color2Label, 0, 8);
        color2Field = new ComboBox<String>();
        color2Field.setMinSize(100, 20);
        grid.add(color2Field, 1, 8);

        Label brandLabel = new Label("Brand: ");
        grid.add(brandLabel, 0, 9);
        brandField = new TextField();
        grid.add(brandField, 1, 9);

        Label genderLabel = new Label("Gender: ");
        grid.add(genderLabel, 0, 10);
        genderField = new TextField();
        grid.add(genderField, 1, 10);

        Label sizeLabel = new Label("Size: ");
        grid.add(sizeLabel, 0, 11);
        sizeField = new ComboBox<String>();
        sizeField.setMinSize(100, 20);
        grid.add(sizeField, 1, 11);

        //add values to size comboBox
        sizeField.getItems().add("XS");
        sizeField.getItems().add("S");
        sizeField.getItems().add("M");
        sizeField.getItems().add("L");
        sizeField.getItems().add("XL");

        Label notesLabel = new Label("Notes: ");
        grid.add(notesLabel, 0, 12);
        notesField = new TextField();
        grid.add(notesField, 1, 12);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (validBarcode == true)
                    processAction(e);
                else if (validBarcode == false)
                    {
                        submitButton.setDisable(true);
                        displayErrorMessage("Invalid barcode! Cancel and enter new Barcode!");
                    }
            }
        });

        grid.add(submitButton, 0, 13);
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Unlock fields
                myModel.stateChangeRequest("SearchForClothing", "");
            }
        });
        grid.add(cancelButton, 1, 13);
        return grid;
    } // end of createFormContents

    // add formatter to phone field
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

    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    } // end of createStatusLog

    public void processAction(Event evt)
    {
        clearErrorMessage();
        // DEBUG: System.out.println("InsertArticleView.actionPerformed()");
        //System.out.println("Logic TBA");

        // PROCESS FIELDS SUBMITTED        
        String sizeEntered = (String) sizeField.getValue();
        String color2Entered = colorCollection.getColorPFXFromDescription((String)color2Field.getValue());
        String brandEntered = brandField.getText();
        String notesEntered = notesField.getText();
        String donorFirstNameEntered = donorFirstNameField.getText();
        String donorLastNameEntered = donorLastNameField.getText();
        String donorPhoneEntered = donorPhoneField.getText();
        String donorEmailEntered = donorEmailField.getText();
        
        if (barcodeEntered == null || barcodeEntered.isEmpty())
        {
            displayErrorMessage("Please provide a valid barcode to get initial Clothing Item information!");
            barcodeField.requestFocus();
        } // end getBarcode Error

        // Check all fields should not be empty
        else if (genderEntered == null || genderEntered.isEmpty()) // Description field should not be empty
        {
            System.out.println("Please provide a Barcode to get Gender for Clothing Item!");
            displayErrorMessage("Please provide a Barcode to get Gender for Clothing Item!");
            genderField.requestFocus();
        }
        else if (articleTypeEntered == null || articleTypeEntered.isEmpty())
        {  
            System.out.println("Please provide a Barcode to get an Article Type for Clothing Item!");
            displayErrorMessage("Please provide a Barcode to get an Article Type for Clothing Item!");
            articleTypeField.requestFocus();
        }
        else if (color1Entered == null || color1Entered.isEmpty())
        {
            System.out.println("Please provide a Barconde to get a Primary Color for Clothing Item!");
            displayErrorMessage("Please provide a Barconde to get a Primary Color for Clothing Item!");
            color1Field.requestFocus();
        }
        else if (donorFirstNameEntered == null || donorFirstNameEntered.isEmpty())
        {
            System.out.println("Please enter a Donor First Name for Clothing");
            displayErrorMessage("Please enter a Donor First Name for Clothing!");
            donorFirstNameField.requestFocus();
        }
        else if (donorLastNameEntered == null || donorLastNameEntered.isEmpty())
        {
            System.out.println("Please enter a Donor Last Name for Clothing!");
            displayErrorMessage("Please enter a Donor Last Name for Clothing!");
            donorLastNameField.requestFocus();
        }
        else if (donorPhoneEntered == null || donorPhoneEntered.isEmpty())
        {
            System.out.println("Please enter a Donor Phone for Clothing!");
            displayErrorMessage("Please enter a Donor Phone for Clothing!");
            donorPhoneField.requestFocus();
        }
        else if (donorEmailEntered == null || donorEmailEntered.isEmpty())
        {
            System.out.println("Please enter a Donor Email for Clothing!");
            displayErrorMessage("Please enter a Donor Email for Clothing!");
            donorEmailField.requestFocus();
        }
        else if (color2Entered == null || color2Entered.isEmpty())
        {
            System.out.println();
            System.out.println("Please choose a Secondary Color for Clothing Item!");
            displayErrorMessage("Please choose a Secondary Color for Clothing Item!");
            color2Field.requestFocus();
        }
        else if (brandEntered == null || brandEntered.isEmpty())
        {
            System.out.println("Please enter a Brand for Clothing!");
            displayErrorMessage("Please enter a Brand for Clothing!");
            brandField.requestFocus();
        }
        else if (sizeEntered == null || sizeEntered.isEmpty())
        {
            System.out.println("Please choose a Size for Clothing Item!");
            displayErrorMessage("Please choose a Size for Clothing Item!");
            sizeField.requestFocus();
        }
        else if(!checkPhone(donorPhoneEntered)) {
            donorPhoneField.requestFocus();
        }
        else if(!checkEmail(donorEmailEntered)) {
            donorEmailField.requestFocus();
        }
        else
        {
            props = new Properties();
            props.setProperty("Barcode", barcodeEntered);
            props.setProperty("Gender", genderEntered);
            props.setProperty("Size", sizeEntered);
            props.setProperty("ArticleType", articleTypeEntered);
            props.setProperty("Color1", color1Entered);
            props.setProperty("Color2", color2Entered);
            props.setProperty("Brand", brandEntered);
            props.setProperty("Notes", notesEntered);
            props.setProperty("DonorLastname", donorLastNameEntered);
            props.setProperty("DonorFirstname", donorFirstNameEntered);
            props.setProperty("DonorPhone", donorPhoneEntered);
            props.setProperty("DonorEmail", donorEmailEntered);

            //Set empty fields
            props.setProperty("ReceiverNetid", "");
            props.setProperty("ReceiverLastname", "");
            props.setProperty("ReceiverFirstname", "");
            props.setProperty("DateDonated", "");
            props.setProperty("DateTaken", "");
            props.setProperty("Status", "Donated");


            try
            {
                // TODO: need to finalize this state request within Closet
                createReceipt(barcodeEntered, genderEntered, sizeEntered,
                        articleTypeCollection.getArticleDescriptionFromPFX(articleTypeEntered),
                        colorCollection.getColorDescriptionFromPFX(color1Entered),
                        colorCollection.getColorDescriptionFromPFX(color2Entered),
                        brandEntered, notesEntered, donorLastNameEntered, donorFirstNameEntered,
                        donorPhoneEntered, donorEmailEntered);
                myModel.stateChangeRequest("InsertInventory", props); // Call stateChangeRequest to insert an inventoryItem
                //displaySuccessMessage("Successfully inserted a new Inventory item!");
            }
            catch(Exception ex)
            {
                //displayErrorMessage("Failed to insert an Inventory item!");
                ex.printStackTrace();
            }
        }
    } // end of processAction

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

    public void populateComboBoxes()
    {
        for (int num = 0; num < colorObj.size(); num++) {
            Vector<String> filler = colorObj.elementAt(num).getFields();
            //System.out.println(filler);
            color1Field.getItems().add(filler.get(1));
            color2Field.getItems().add(filler.get(1));

        }
    } // end of setColorDropdowns

    public void parseBarcode()
    {
        // Parse barcode to get inventory before any other action
        if (barcodeEntered != null)
        {
            int parseBC = 0; // set a count to keep track of digits of Barcode 
            while (parseBC < 5)
            {
                String currentDig = Character.toString(barcodeEntered.charAt(parseBC));
                System.out.println("On Barcode digit: " + Integer.parseInt(barcodeEntered));

                if (parseBC == 0) // Get Gender from 1st digit in Barcode
                {
                    switch(Integer.parseInt(currentDig))
                    {
                        // Populate Gender field according to data found from Barcode
                        case 0:
                            System.out.println("Set Gender to Men!");
                            genderField.setText("M"); 
                            parseBC = parseBC + 1; // move to next digits in barcode
                            System.out.println(barcodeEntered.charAt(parseBC) + genderField.getText()); // Testing, print Gender
                            break;
                        case 1:
                            System.out.println("Set Gender to Women!");
                            genderField.setText("W");
                            parseBC = parseBC + 1; // move to next digits in barcode
                            break;
                        default:
                            System.out.println("Error parsing Barcode for Gender!");
                            displayErrorMessage("Error parsing Barcode for Gender!");
                            parseBC = 6;
                            barcodeField.requestFocus();
                            System.out.println(barcodeEntered.charAt(parseBC) + genderField.getText()); // Testing, print Gender
                            break;
                    } // end switch
                } // end getGender
                if ((parseBC == 1) || (parseBC == 2)) // Get Article Type from 2nd & 3rd digits in barcode 
                {
                    System.out.println("Getting Article Type...");
                    currentDig = Character.toString(barcodeEntered.charAt(parseBC)) + Character.toString(barcodeEntered.charAt(parseBC + 1));
                    int getArticleBPFX = Integer.parseInt(currentDig);
                    System.out.println("integerrrrr: " + getArticleBPFX);

                    // Verify Article Type Barcode Prefix in database
                    try {
                        System.out.println("getATBPFX" + getArticleBPFX);
                        newAT = new ArticleType(getArticleBPFX); //Use constructor to instantiate ArticleType from barcode prefix
                        //articleTypeField.setText(articleTypeCollection.getArticleDescriptionFromPFX(currentDig));
                        articleTypeField.setText((String) newAT.getState("Description"));
                        System.out.println("fail here");
                        System.out.println("Successfully verified Article Type!");
                        parseBC = parseBC + 2; // move to next digits in barcode
                        // Testing, print Article Type
                        System.out.println("\nArticle: " + articleTypeField.getText());
                    }
                    catch (Exception ex) {
                        System.out.println("Error parsing Barcode for Article Type!");
                        parseBC = 6;
                        displayErrorMessage("Error parsing Barcode for Article Type!");
                        articleTypeField.requestFocus();
                        ex.printStackTrace();
                    }

                } // end get Article Type
                if ((parseBC == 3) || (parseBC == 4)) // Get Color from 4th & fifth digits in barcode
                {
                    System.out.println("Getting Color...");
                    currentDig = Character.toString(barcodeEntered.charAt(parseBC)) + Character.toString(barcodeEntered.charAt(parseBC + 1));
                    int getColorBPFX = Integer.parseInt(currentDig);

                    try { 
                        newCo = new Color(getColorBPFX); // Use constructor to instantiate Color from barcode prefix
                        //color1Field.setValue((String)colorCollection.getColorDescriptionFromPFX(currentDig));
                        color1Field.setValue(newCo.getState("Description"));
                        System.out.println(newCo.toString());
                        System.out.println("Successfully verified Color!");
                        parseBC = parseBC + 2; // move to next digits in barcode
                        // Testing, print Color
                        System.out.println("\nColor: " + color1Field.getValue());
                    }
                    catch (Exception ex) {
                        System.out.println("Error parsing Barcode for Color!");
                        parseBC = 6;
                        displayErrorMessage("Error parsing Barcode for Color!");
                        color1Field.requestFocus();
                    }
                } // end get Color
            } // end while parsing Barcode
            genderEntered = genderField.getText();
            articleTypeEntered = Character.toString(barcodeEntered.charAt(1)) + Character.toString(barcodeEntered.charAt(2));
            color1Entered = colorCollection.getColorPFXFromDescription((String)color1Field.getValue());

            // Lock auto-set fields
            barcodeField.setDisable(true);
            articleTypeField.setDisable(true);
            color1Field.setDisable(true);
            genderField.setDisable(true);
            validBarcode = true;
        } // end getBarcode
        else {
            System.out.println("Barcode is null THERE IS AN ERROR HERE");
        }
    } // end of parseBarcode

    @Override
            public void updateState(String key, Object value) {
        if(key.equals("updateText")){
            clearTextFields();
            System.out.println("Updating Text");
            barcodeEntered = barcode;
            barcodeField.setText(barcode);
            parseBarcode();
        }
        else if(key.equals("successfulModify")) {
            displaySuccessMessage("New Clothing Item Successfully Inserted");
        } else if(key.equals("unsuccessfulModify")) {
            displayErrorMessage("Error Inserting New Clothing Item");
        } else {
            System.out.println("Error: else updateState in insertInventoryView");
        }
    }

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    } // end of errorMessage

    public void displaySuccessMessage(String message)
    {
        statusLog.displayMessage(message);
    } // end of successMessage

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    } // end of clearMessage

    private void clearTextFields(){
        sizeField.getSelectionModel().select("");
        brandField.clear();
        notesField.clear();
        donorFirstNameField.clear();
        donorLastNameField.clear();
        donorPhoneField.clear();
        donorEmailField.clear();

    }

    public void createReceipt(String barcode, String gender, String size, String articleType,
                              String color1, String color2, String brand, String notes,
                              String donorLastName, String donorFirstName, String donorPhone,
                              String donorEmail){
        //receipt creation
        Alert receipt = new Alert(Alert.AlertType.NONE, "Insertion Receipt", ButtonType.OK);
        receipt.setTitle("Insertion Receipt");
        Label text = new Label("Barcode: " + barcode + "\n" +
                "Gender: " + gender + "\n" +
                "Size: " + size + "\n" +
                "Article Type: " + articleType + "\n" +
                "Color 1: " + color1 + "\n" +
                "Color 2: " + color2 + "\n" +
                "Brand: " + brand + "\n" +
                "Notes: " + notes + "\n" +
                "Donor Last Name: " + donorLastName + "\n" +
                "Donor First Name: " + donorFirstName + "\n" +
                "Donor Phone: " + donorPhone + "\n" +
                "Donor Email: " + donorEmail);
        receipt.getDialogPane().setContent(text);

        receipt.showAndWait();
        clearErrorMessage();
    }

} // end of InsertInventoryView