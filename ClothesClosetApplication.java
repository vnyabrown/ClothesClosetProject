import event.Event;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Closet;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

public class ClothesClosetApplication extends Application {
    private Closet myCloset;
    private Stage mainStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Clothes Closet Version 1.00");
//        System.out.println("Copyright 2004/2015 Sandeep Mitra and T M Rao");

        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "Brockport Clothes Closet");
        mainStage = MainStageContainer.getInstance();
        mainStage.setResizable(true);

        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });

        try
        {
            myCloset = new Closet();
        }
        catch(Exception exc)
        {
            System.err.println("ClothesClosetProject.ClothesClosetApplication - could not create Closet!");
            new Event(Event.getLeafLevelClassName(this), "ClothesClosetApplication.<init>",
                    "Unable to create Closet object", Event.ERROR);
            exc.printStackTrace();
        }

        WindowPosition.placeCenter(mainStage);
//        mainStage.setHeight(1000);
//        mainStage.setWidth(1000);

        mainStage.show();
    }
}
