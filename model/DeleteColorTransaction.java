package model;

import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class DeleteColorTransaction extends Transaction {
    /**
     * Constructor for this class.
     * <p>
     * Transaction remembers all the account IDs for this customer.
     * It uses AccountCatalog to create this list of account IDs.
     */
    protected DeleteColorTransaction() throws Exception {
        super();
    }

    @Override
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelDelete", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");

        myRegistry.setDependencies(dependencies);

    }

    @Override
    protected Scene createView() {
        Scene currentScene = myViews.get("DeleteColorView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("DeleteColorView", this);
            currentScene = new Scene(newView);
            myViews.put("DeleteColorView", currentScene);
        }
        return currentScene;
    }

    @Override
    public Object getState(String key) {
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("DoYourJob"))
        {
            doYourJob();
        }
        else
        if (key.equals("InsertBook"))
        {
            processTransaction((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    public void processTransaction(Properties props) {
        System.out.println("TBA - merge branches and I don't want to mess that up");
    }
}
