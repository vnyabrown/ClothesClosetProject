package userinterface;

// system imports

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Inventory;
import model.InventoryCollection;

import java.util.Enumeration;
import java.util.Optional;
import java.util.Properties;
import java.util.Vector;

import static userinterface.InventoryChoiceView.modDelCheckFlag;

//==============================================================================
public class ClothingCollectionView extends View
{

    javafx.scene.paint.Color color;
    protected TableView<ClothingTableModel> tableOfClothing;
    protected Button cancelButton;
    protected Button deleteButton;
    protected Button modifyButton;
    protected Button checkButton;

    protected MessageView statusLog;

    private InventoryCollection atc;



    //--------------------------------------------------------------------------
    public ClothingCollectionView(IModel wsc)
    {
        super(wsc, "ClothingCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {
        ObservableList<ClothingTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            atc = (InventoryCollection) myModel.getState("InventoryCollection");
//            bookCollection.display();

            Vector entryList = (Vector)atc.getState("getVector");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Inventory nextAT = (Inventory) entries.nextElement();
                Vector<String> view = nextAT.getFields();

                /* this print is slowing things down
                System.out.println("---------");
                for(String str : view) {
                    System.out.println(str);
                }
                System.out.println("---------");
                */


                // add this list entry to the list
                ClothingTableModel nextTableRowData = new ClothingTableModel(view);
                tableData.add(nextTableRowData);
            }

            tableOfClothing.setItems(tableData);
            System.out.println("ok getting Inventory.");
        }
        catch (Exception e) {//SQLException e) {
            System.err.println("Error getting Inventory from db.");
            e.printStackTrace();
            // Need to handle this exception
        }
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text("       Brockport Clothes Closet          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF Clothing");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfClothing = new TableView<ClothingTableModel>();
        tableOfClothing.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn barcodeColumn = new TableColumn("Barcode");
        barcodeColumn.setMinWidth(100);
        barcodeColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Barcode"));

        TableColumn genderColumn = new TableColumn("Gender");
        genderColumn.setMinWidth(100);
        genderColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Gender"));

        TableColumn sizeColumn = new TableColumn("Size");
        sizeColumn.setMinWidth(100);
        sizeColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Size"));

        TableColumn articleTypeColumn = new TableColumn("ArticleType");
        articleTypeColumn.setMinWidth(100);
        articleTypeColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("ArticleType"));

        TableColumn color1Column = new TableColumn("Color1");
        color1Column.setMinWidth(100);
        color1Column.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Color1"));

        TableColumn color2Column = new TableColumn("Color2");
        color2Column.setMinWidth(100);
        color2Column.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Color2"));

        TableColumn brandColumn = new TableColumn("Brand");
        brandColumn.setMinWidth(100);
        brandColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Brand"));

        TableColumn notesColumn = new TableColumn("Notes");
        notesColumn.setMinWidth(100);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Notes"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("Status"));

        TableColumn donorLastnameColumn = new TableColumn("DonorLastname");
        donorLastnameColumn.setMinWidth(100);
        donorLastnameColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("DonorLastname"));

        TableColumn donorFirstnameColumn = new TableColumn("DonorFirstname");
        donorFirstnameColumn.setMinWidth(100);
        donorFirstnameColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("DonorFirstname"));

        TableColumn donorPhoneColumn = new TableColumn("DonorPhone");
        donorPhoneColumn.setMinWidth(100);
        donorPhoneColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("DonorPhone"));

        TableColumn donorEmailColumn = new TableColumn("DonorEmail");
        donorEmailColumn.setMinWidth(100);
        donorEmailColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("DonorEmail"));

        TableColumn receiverNetidColumn = new TableColumn("ReceiverNetid");
        receiverNetidColumn.setMinWidth(100);
        receiverNetidColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("ReceiverNetid"));

        TableColumn receiverLastnameColumn = new TableColumn("ReceiverLastname");
        receiverLastnameColumn.setMinWidth(100);
        receiverLastnameColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("ReceiverLastname"));

        TableColumn receiverFirstnameColumn = new TableColumn("ReceiverFirstname");
        receiverFirstnameColumn.setMinWidth(100);
        receiverFirstnameColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("ReceiverFirstname"));

        TableColumn dateDonatedColumn = new TableColumn("DateDonated");
        dateDonatedColumn.setMinWidth(100);
        dateDonatedColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("DateDonated"));

        TableColumn dateTakenColumn = new TableColumn("DateTaken");
        dateTakenColumn.setMinWidth(100);
        dateTakenColumn.setCellValueFactory(
                new PropertyValueFactory<ClothingTableModel, String>("DateTaken"));


        tableOfClothing.getColumns().addAll(barcodeColumn, genderColumn, sizeColumn, articleTypeColumn,
                color1Column, color2Column, brandColumn, notesColumn, statusColumn, donorLastnameColumn,
                donorFirstnameColumn, donorPhoneColumn, donorEmailColumn, receiverNetidColumn,
                receiverLastnameColumn, receiverFirstnameColumn, dateDonatedColumn, dateTakenColumn);

        tableOfClothing.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processClothingSelected("");
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfClothing);

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("SearchForClothing", null);
            }
        });

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Request Confirmation before deletion
                Alert confirmDel = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this Clothing Item?", ButtonType.OK, ButtonType.CANCEL);
                confirmDel.setTitle("Confirm deleting Clothing Item");
                ((Button) confirmDel.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
                ((Button) confirmDel.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");
                Optional<ButtonType> result = confirmDel.showAndWait();
                if (result.get() == ButtonType.OK)
                {
                    clearErrorMessage();
                    processClothingSelected("delete");
                } 
            }
        });

        modifyButton = new Button("Modify");
        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processClothingSelected("modify");
            }
        });

        checkButton = new Button("Checkout");
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processClothingSelected("Checkout");
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);

        btnContainer.getChildren().add(cancelButton);
        if( modDelCheckFlag == "del") {
            btnContainer.getChildren().add(deleteButton);
        }else if (modDelCheckFlag == "mod") {
            btnContainer.getChildren().add(modifyButton);
        }else if (modDelCheckFlag == "check"){
            btnContainer.getChildren().add(checkButton);
        }
        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }



    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
    }

    //--------------------------------------------------------------------------
    protected void processClothingSelected(String str)
    {
        ClothingTableModel selectedItem = tableOfClothing.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            clearErrorMessage();
            String selectedBarcode = selectedItem.getBarcode();
            System.out.println("Selected Barcode: " + selectedBarcode);
            Properties prop = new Properties();
            prop.setProperty("Barcode", selectedItem.getBarcode());
            prop.setProperty("Gender", selectedItem.getGender());
            prop.setProperty("Size", selectedItem.getSize());
            prop.setProperty("ArticleType", selectedItem.getArticleType());
            prop.setProperty("Color1", selectedItem.getColor1());
            prop.setProperty("Color2", selectedItem.getColor2());
            prop.setProperty("Brand", selectedItem.getBrand());

            //Notes can be null
            try {
                prop.setProperty("Notes", selectedItem.getNotes());
            } catch (NullPointerException exception){
                prop.setProperty("Notes", "NULL");
            }
            prop.setProperty("Status", selectedItem.getStatus());

            //Donor name can be null
            try{
                prop.setProperty("DonorLastname", selectedItem.getDonorLastname());
            } catch (NullPointerException exception){
                prop.setProperty("DonorLastname", "NULL");
            }
            try {
                prop.setProperty("DonorFirstname", selectedItem.getDonorFirstname());
            } catch (NullPointerException exception){
                prop.setProperty("DonorFirstname", "NULL");
            }

            prop.setProperty("DonorPhone", selectedItem.getDonorPhone());
            prop.setProperty("DonorEmail", selectedItem.getDonorEmail());

            //receiver can be null
            try {
                prop.setProperty("ReceiverNetid", selectedItem.getReceiverNetid());
            } catch (NullPointerException exception){
                prop.setProperty("ReceiverNetid", "NULL");
            }
            try {
                prop.setProperty("ReceiverLastname", selectedItem.getReceiverLastname());
            } catch (NullPointerException exception){
                prop.setProperty("ReceiverLastname", "NULL");
            }
            try {
                prop.setProperty("ReceiverFirstname", selectedItem.getReceiverFirstname());
            } catch (NullPointerException exception){
                prop.setProperty("ReceiverFirstname", "NULL");
            }

            prop.setProperty("DateDonated", selectedItem.getDateDonated());

            //date taken can be null
            try {
                prop.setProperty("DateTaken", selectedItem.getDateTaken());
            } catch (NullPointerException exception){
                prop.setProperty("DateTaken", "NULL");
            }

            System.out.println(str);
            if(str.equals("delete")) {
                myModel.stateChangeRequest("ClothingSelectedForDeletion", prop);
                displayMessage("Clothing Deleted.");
            }
            else if(str.equals("modify")) {
                System.out.println("clothing in modify in view");
                myModel.stateChangeRequest("ClothingToBeModified", prop);
            }
            else {
                System.out.println("str in processClothingSelected is not right");
            }
        }
        else {
            System.out.println("selecteditem is null in clothing collection view");
            displayErrorMessage("Select an Item");
        }
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processArticleTypeselected();
		}
	} in modify in view
   */

}
