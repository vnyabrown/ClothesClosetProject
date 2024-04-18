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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Color;
import model.ColorCollection;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

//==============================================================================
public class ClothingCollectionView extends View
{

    javafx.scene.paint.Color color;
    protected TableView<ColorTableModel> tableOfColors;
    protected Button cancelButton;
    protected Button deleteButton;
    protected MessageView statusLog;

    //CHANGE TO CLOTHING ONCE IMPLEMENTED
    private ColorCollection atc;



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
        ObservableList<ColorTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            atc = (ColorCollection) myModel.getState("ColorCollection");
//            bookCollection.display();

            Vector entryList = (Vector)atc.getState("getVector");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Color nextAT = (Color) entries.nextElement();
                Vector<String> view = nextAT.getFields();
                System.out.println("---------");
                for(String str : view) {
                    System.out.println(str);
                }
                System.out.println("---------");

                // add this list entry to the list
                ColorTableModel nextTableRowData = new ColorTableModel(view);
                tableData.add(nextTableRowData);
            }

            tableOfColors.setItems(tableData);
            System.out.println("ok getting Color.");
        }
        catch (Exception e) {//SQLException e) {
            System.err.println("Error getting Color from db.");
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

        tableOfColors = new TableView<ColorTableModel>();
        tableOfColors.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn colorNumberColumn = new TableColumn("Id") ;
        colorNumberColumn.setMinWidth(100);
        colorNumberColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("Id"));

        TableColumn colorDescColumn = new TableColumn("Description") ;
        colorDescColumn.setMinWidth(100);
        colorDescColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("Description"));

        TableColumn  colorBfxColumn = new TableColumn("BarcodePrefix") ;
        colorBfxColumn.setMinWidth(100);
        colorBfxColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("BarcodePrefix"));

        TableColumn colorAlphaColumn = new TableColumn("AlphaCode") ;
        colorAlphaColumn.setMinWidth(100);
        colorAlphaColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("AlphaCode"));

        TableColumn colorStatusColumn = new TableColumn("Status") ;
        colorStatusColumn.setMinWidth(100);
        colorStatusColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("Status"));

        tableOfColors.getColumns().addAll(colorNumberColumn, colorDescColumn,
                colorBfxColumn, colorAlphaColumn, colorStatusColumn);

        tableOfColors.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processColorSelected("");
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfColors);

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("SearchForColor", null);
            }
        });

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processColorSelected("delete");
            }
        });

        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processColorSelected("modify");
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(cancelButton);
        btnContainer.getChildren().add(deleteButton);
        btnContainer.getChildren().add(modifyButton);

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
    protected void processColorSelected(String str)
    {
        ColorTableModel selectedItem = tableOfColors.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedColorNum = selectedItem.getId();
            System.out.println("selected : " + selectedColorNum);
            Properties prop = new Properties();
            prop.setProperty("Id", selectedItem.getId());
            prop.setProperty("Description", selectedItem.getDescription());
            prop.setProperty("BarcodePrefix", selectedItem.getBarcodePrefix());
            prop.setProperty("AlphaCode", selectedItem.getAlphaCode());
            prop.setProperty("Status", selectedItem.getStatus());

            if(str.equals("delete")) {
                myModel.stateChangeRequest("ColorSelectedForDeletion", prop);
                displayMessage("Color Deleted.");
            }
            else if(str.equals("modify")) {
                System.out.println("in modify in view");
                myModel.stateChangeRequest("ColorToBeModified", prop);
            }
            else {
                System.out.println("str in processColorSelected is not right");
            }
        }
        else {
            System.out.println("selecteditem is null in atc view");
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
	}
   */

}