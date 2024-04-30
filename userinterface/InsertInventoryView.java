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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Properties;

import model.ArticleType;
import model.Color;

public class InsertInventoryView extends View {
   
    javafx.scene.paint.Color color;

    Properties props;
    Properties articleProps;
    Properties colorProps;

    ArticleType newAT;
    Color newCo;

    private TextField barcodeField;
    private TextField genderField;
    private TextField sizeField;
    private TextField articleTypeField;
    private TextField color1Field;
    private TextField color2Field;
    private TextField brandField;
    private TextField notesField;
    private TextField donorLastNameField;
    private TextField donorFirstNameField;
    private TextField donorPhoneField;
    private TextField donorEmailField;

    private Button submitButton;
    private Button cancelButton;

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

        getChildren().add(container);
    } // end of Constructor

    private Node createTitle() {

        Text titleText = new Text("       Insert an Inventory Item          ");
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

        Text prompt = new Text("INVENTORY INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Label barcodeLabel = new Label("Inventory Item Barcode: ");
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
        grid.add(donorPhoneField, 1, 4);

        Label donorEmailLabel = new Label("Donor Email: ");
        grid.add(donorEmailLabel, 0, 5);
        donorEmailField = new TextField();
        grid.add(donorEmailField, 1, 5);

        Label articleTypeLabel = new Label("Article Type: ");
        grid.add(articleTypeLabel, 0, 6);
        articleTypeField = new TextField();
        grid.add(articleTypeField, 1, 6);

        Label color1Label = new Label("Primary Color: ");
        grid.add(color1Label, 0, 7);
        color1Field = new TextField();
        grid.add(color1Field, 1, 7);

        Label color2Label = new Label("Secondary Color: ");
        grid.add(color2Label, 0, 8);
        color2Field = new TextField();
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
        sizeField = new TextField();
        grid.add(sizeField, 1, 11);

        Label notesLabel = new Label("Notes: ");
        grid.add(notesLabel, 0, 12);
        notesField = new TextField();
        grid.add(notesField, 1, 12);

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
                myModel.stateChangeRequest("Inventory", "");
            }
        });
        grid.add(cancelButton, 1, 13);
        return grid;
    } // end of createFormContents

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
        String barcodeEntered = barcodeField.getText();

        //These fields will be autopopulated by barcode
        String genderEntered = new String();
        String articleTypeEntered = new String();
        String color1Entered = new String();
        
        String sizeEntered = sizeField.getText();
        String color2Entered = color2Field.getText();
        String brandEntered = brandField.getText();
        String notesEntered = notesField.getText();
        String donorFirstNameEntered = donorFirstNameField.getText();
        String donorLastNameEntered = donorLastNameField.getText();
        String donorPhoneEntered = donorPhoneField.getText();
        String donorEmailEntered = donorEmailField.getText();
        
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

                    // Verify Article Type Barcode Prefix in database
                    try { 
                        newAT = new ArticleType(getArticleBPFX); //Use constructor to instantiate ArticleType from barcode prefix
                        System.out.println(newAT.toString());
                        articleTypeField.setText(currentDig);
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
                    }

                } // end get Article Type
                if ((parseBC == 3) || (parseBC == 4)) // Get Color from 4th & fifth digits in barcode
                {
                    System.out.println("Getting Color...");
                    currentDig = Character.toString(barcodeEntered.charAt(parseBC)) + Character.toString(barcodeEntered.charAt(parseBC + 1));
                    int getColorBPFX = Integer.parseInt(currentDig);

                    try { 
                        newCo = new Color(getColorBPFX); // Use constructor to instantiate Color from barcode prefix
                        color1Field.setText(currentDig);
                        System.out.println(newCo.toString());
                        System.out.println("Successfully verified Color!");
                        parseBC = parseBC + 2; // move to next digits in barcode
                        // Testing, print Color
                        System.out.println("\nColor: " + color1Field.getText());
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
            articleTypeEntered = articleTypeField.getText();
            color1Entered = color1Field.getText();
            barcodeField.setEditable(false);
        } // end getBarcode
        if (barcodeEntered == null || barcodeEntered.isEmpty())
        {
            displayErrorMessage("Please provide a barcode to get initial Inventory Item information!");
            barcodeField.requestFocus();
        } // end getBarcode Error

        // Check all fields should not be empty
        else if (genderEntered == null || genderEntered.isEmpty()) // Description field should not be empty
        {
            System.out.println("Please provide a Barcode to get Gender for Inventory Item!");
            displayErrorMessage("Please provide a Barcode to get Gender for Inventory Item!");
            genderField.requestFocus();
        }
        else if (sizeEntered == null || sizeEntered.isEmpty())
        {
            System.out.println("Please enter a Size for Inventory Item!");
            displayErrorMessage("Please enter a Size for Inventory Item!");
            sizeField.requestFocus();
        }
        else if (articleTypeEntered == null || articleTypeEntered.isEmpty())
        {  
            System.out.println("Please provide a Barcode to get an Article Type for Inventory Item!");
            displayErrorMessage("Please provide a Barcode to get an Article Type for Inventory Item!");
            articleTypeField.requestFocus();
        }
        else if (color1Entered == null || color1Entered.isEmpty())
        {
            System.out.println("Please provide a Barconde to get a Primary Color for Inventory Item!");
            displayErrorMessage("Please provide a Barconde to get a Primary Color for Inventory Item!");
            color1Field.requestFocus();
        }
        else if (color2Entered == null || color2Entered.isEmpty())
        {
            System.out.println();
            System.out.println("Please enter a Secondary Color for Inventory Item!");
            displayErrorMessage("Please enter a Secondary Color for Inventory Item!");
            color2Field.requestFocus();
        }
        else if (brandEntered == null || brandEntered.isEmpty())
        {
            System.out.println("Please enter a Brand for Clothing!");
            displayErrorMessage("Please enter a Brand for Clothing!");
            brandField.requestFocus();
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
                myModel.stateChangeRequest("InsertInventory", props); // Call stateChangeRequest to insert an inventoryItem
                displaySuccessMessage("Successfully inserted a new Inventory item!");
            }
            catch(Exception ex)
            {
                displayErrorMessage("Failed to insert an Inventory item!");
                ex.printStackTrace();
            }
        }
    } // end of processAction

    @Override
    public void updateState(String key, Object value) {}

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

} // end of InsertInventoryView