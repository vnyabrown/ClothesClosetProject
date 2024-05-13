package userinterface;

// system imports
import impresario.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.ArticleType;
import model.ArticleTypeCollection;
import static userinterface.ArticleChoiceView.modDelCheckFlag;

//==============================================================================
public class ArticleTypeCollectionView extends View
{
    protected TableView<ArticleTypeTableModel> tableOfArticleTypes;
    protected Button cancelButton;
    protected Button deleteButton;
    protected MessageView statusLog;
    private ArticleTypeCollection atc;



    //--------------------------------------------------------------------------
    public ArticleTypeCollectionView(IModel wsc)
    {
        super(wsc, "ArticleTypeCollectionView");

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
        ObservableList<ArticleTypeTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            atc = (ArticleTypeCollection) myModel.getState("ArticleTypeCollection");
//            bookCollection.display();

            Vector entryList = (Vector)atc.getState("getVector");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                ArticleType nextAT = (ArticleType) entries.nextElement();
                Vector<String> view = nextAT.getFields();
                System.out.println("---------");
                for(String str : view) {
                    System.out.println(str);
                }
                System.out.println("---------");

                // add this list entry to the list
                ArticleTypeTableModel nextTableRowData = new ArticleTypeTableModel(view);
                tableData.add(nextTableRowData);
            }

            tableOfArticleTypes.setItems(tableData);
            System.out.println("ok getting ArticleTypes.");
        }
        catch (Exception e) {//SQLException e) {
            System.err.println("Error getting ArticleTypes from db.");
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
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        vbox.setMinWidth(500);
        vbox.setMinHeight(500);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF ArticleTypes");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfArticleTypes = new TableView<ArticleTypeTableModel>();
        tableOfArticleTypes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Label placeholder = new Label("No Results");
        placeholder.setAlignment(Pos.TOP_LEFT);
        tableOfArticleTypes.setPlaceholder(placeholder);

        TableColumn accountNumberColumn = new TableColumn("Id") ;
        accountNumberColumn.setMinWidth(100);
        accountNumberColumn.setCellValueFactory(
                new PropertyValueFactory<ArticleTypeTableModel, String>("Id"));

        TableColumn accountTypeColumn = new TableColumn("Description") ;
        accountTypeColumn.setMinWidth(100);
        accountTypeColumn.setCellValueFactory(
                new PropertyValueFactory<ArticleTypeTableModel, String>("Description"));

        TableColumn balanceColumn = new TableColumn("BarcodePrefix") ;
        balanceColumn.setMinWidth(100);
        balanceColumn.setCellValueFactory(
                new PropertyValueFactory<ArticleTypeTableModel, String>("BarcodePrefix"));

        TableColumn serviceChargeColumn = new TableColumn("AlphaCode") ;
        serviceChargeColumn.setMinWidth(100);
        serviceChargeColumn.setCellValueFactory(
                new PropertyValueFactory<ArticleTypeTableModel, String>("AlphaCode"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<ArticleTypeTableModel, String>("Status"));

        tableOfArticleTypes.getColumns().addAll(accountNumberColumn, accountTypeColumn,
                balanceColumn, serviceChargeColumn, statusColumn);

        tableOfArticleTypes.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processArticleTypeSelected(modDelCheckFlag);
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfArticleTypes);

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("SearchForArticleType", "");
            }
        });

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Request Confirmation before deletion
                Alert confirmDel = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this Clothing Item?", ButtonType.OK, ButtonType.CANCEL);
                confirmDel.setTitle("Confirm deleting Article Type");
                ((Button) confirmDel.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
                ((Button) confirmDel.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");
                Optional<ButtonType> result = confirmDel.showAndWait();
                if (result.get() == ButtonType.OK)
                {
                    clearErrorMessage();
                    processArticleTypeSelected("delete");
                }
            }
        });

        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processArticleTypeSelected("modify");
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        // Add appropriate buttons
        btnContainer.getChildren().add(cancelButton);
        if(modDelCheckFlag.equals("mod")) {
            btnContainer.getChildren().add(modifyButton);
        } else if(modDelCheckFlag.equals("del")) {
            btnContainer.getChildren().add(deleteButton);
        } else {
            System.out.println("else in articleTypeCollectionView");
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
    protected void processArticleTypeSelected(String str)
    {
        ArticleTypeTableModel selectedItem = tableOfArticleTypes.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedAcctNumber = selectedItem.getId();
            System.out.println("selected : " + selectedAcctNumber);
            Properties prop = new Properties();
            prop.setProperty("Id", selectedItem.getId());
            prop.setProperty("Description", selectedItem.getDescription());
            prop.setProperty("BarcodePrefix", selectedItem.getBarcodePrefix());
            prop.setProperty("AlphaCode", selectedItem.getAlphaCode());
            prop.setProperty("Status", selectedItem.getStatus());

            if(str.equals("delete")) {
                myModel.stateChangeRequest("ArticleTypeSelectedForDeletion", prop);
                displayMessage("Article type with ID " + prop.getProperty("Id") + " was successfully removed");
            }
            else if(str.equals("modify")) {
                System.out.println("in modify in view");
                myModel.stateChangeRequest("ArticleTypeToBeModified", prop);
            }
            else {
                System.out.println("str in processArticleTypeSelected is not right");
            }
        }
        else {
            System.out.println("selecteditem is null in atc view");
            displayErrorMessage("Please select an Article Type!");
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
	}
   */

}
