package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ColorTableModel
{
    private final SimpleStringProperty Id;
    private final SimpleStringProperty Description;
    private final SimpleStringProperty BarcodePrefix;
    private final SimpleStringProperty AlphaCode;
    private final SimpleStringProperty Status;

    //----------------------------------------------------------------------------
    public ColorTableModel(Vector<String> bookData)
    {
        Id =  new SimpleStringProperty(bookData.elementAt(0));
        Description =  new SimpleStringProperty(bookData.elementAt(1));
        BarcodePrefix =  new SimpleStringProperty(bookData.elementAt(2));
        AlphaCode =  new SimpleStringProperty(bookData.elementAt(3));
        Status =  new SimpleStringProperty(bookData.elementAt(4));
    }

    //----------------------------------------------------------------------------
    public String getId() {
        return Id.get();
    }

    //----------------------------------------------------------------------------
    public void setBookNumber(String number) {
        Id.set(number);
    }

    //----------------------------------------------------------------------------
    public String getDescription() {
        return Description.get();
    }

    //----------------------------------------------------------------------------
    public void setDescription(String aType) {
        Description.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getBarcodePrefix() {
        return BarcodePrefix.get();
    }

    //----------------------------------------------------------------------------
    public void setBarcodePrefix(String bal) {
        BarcodePrefix.set(bal);
    }

    //----------------------------------------------------------------------------
    public String getAlphaCode() {
        return AlphaCode.get();
    }

    //----------------------------------------------------------------------------
    public void setAlphaCode(String charge)
    {
        AlphaCode.set(charge);
    }

    public String getStatus() {
        return Status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String charge)
    {
        Status.set(charge);
    }
}
