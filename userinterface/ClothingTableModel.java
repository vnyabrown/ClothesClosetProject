package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

//==============================================================================
public class ClothingTableModel {
    private final SimpleStringProperty Barcode;
    private final SimpleStringProperty Gender;
    private final SimpleStringProperty Size;
    private final SimpleStringProperty ArticleType;
    private final SimpleStringProperty Color1;
    private final SimpleStringProperty Color2;
    private final SimpleStringProperty Brand;
    private final SimpleStringProperty Notes;
    private final SimpleStringProperty Status;
    private final SimpleStringProperty DonorLastname;
    private final SimpleStringProperty DonorFirstname;
    private final SimpleStringProperty DonorPhone;
    private final SimpleStringProperty DonorEmail;
    private final SimpleStringProperty ReceiverNetid;
    private final SimpleStringProperty ReceiverLastname;
    private final SimpleStringProperty ReceiverFirstname;
    private final SimpleStringProperty DateDonated;
    private final SimpleStringProperty DateTaken;

    public ClothingTableModel(Vector<String> rowData) {
        Barcode = new SimpleStringProperty(rowData.elementAt(0));
        Gender = new SimpleStringProperty(rowData.elementAt(1));
        Size = new SimpleStringProperty(rowData.elementAt(2));
        ArticleType = new SimpleStringProperty(rowData.elementAt(3));
        Color1 = new SimpleStringProperty(rowData.elementAt(4));
        Color2 = new SimpleStringProperty(rowData.elementAt(5));
        Brand = new SimpleStringProperty(rowData.elementAt(6));
        Notes = new SimpleStringProperty(rowData.elementAt(7));
        Status = new SimpleStringProperty(rowData.elementAt(8));
        DonorLastname = new SimpleStringProperty(rowData.elementAt(9));
        DonorFirstname = new SimpleStringProperty(rowData.elementAt(10));
        DonorPhone = new SimpleStringProperty(rowData.elementAt(11));
        DonorEmail = new SimpleStringProperty(rowData.elementAt(12));
        ReceiverNetid = new SimpleStringProperty(rowData.elementAt(13));
        ReceiverLastname = new SimpleStringProperty(rowData.elementAt(14));
        ReceiverFirstname = new SimpleStringProperty(rowData.elementAt(15));
        DateDonated = new SimpleStringProperty(rowData.elementAt(16));
        DateTaken = new SimpleStringProperty(rowData.elementAt(17));
    }

    public String getBarcode() {
        return Barcode.get();
    }

    public void setBarcode(String barcode) {
        Barcode.set(barcode);
    }

    public String getGender() {
        return Gender.get();
    }

    public void setGender(String gender) {
        Gender.set(gender);
    }

    public String getSize() {
        return Size.get();
    }

    public void setSize(String size) {
        Size.set(size);
    }

    public String getArticleType() {
        return ArticleType.get();
    }

    public void setArticleType(String articleType) {
        ArticleType.set(articleType);
    }

    public String getColor1() {
        return Color1.get();
    }

    public void setColor1(String color1) {
        Color1.set(color1);
    }

    public String getColor2() {
        return Color2.get();
    }

    public void setColor2(String color2) {
        Color2.set(color2);
    }

    public String getBrand() {
        return Brand.get();
    }

    public void setBrand(String brand) {
        Brand.set(brand);
    }

    public String getNotes() {
        return Notes.get();
    }

    public void setNotes(String notes) {
        Notes.set(notes);
    }

    public String getStatus() {
        return Status.get();
    }

    public void setStatus(String status) {
        Status.set(status);
    }

    public String getDonorLastname() {
        return DonorLastname.get();
    }

    public void setDonorLastname(String donorLastname) {
        DonorLastname.set(donorLastname);
    }

    public String getDonorFirstname() {
        return DonorFirstname.get();
    }

    public void setDonorFirstname(String donorFirstname) {
        DonorFirstname.set(donorFirstname);
    }

    public String getDonorPhone() {
        return DonorPhone.get();
    }

    public void setDonorPhone(String donorPhone) {
        DonorPhone.set(donorPhone);
    }

    public String getDonorEmail() {
        return DonorEmail.get();
    }

    public void setDonorEmail(String donorEmail) {
        DonorEmail.set(donorEmail);
    }

    public String getReceiverNetid() {
        return ReceiverNetid.get();
    }

    public void setReceiverNetid(String receiverNetid) {
        ReceiverNetid.set(receiverNetid);
    }

    public String getReceiverLastname() {
        return ReceiverLastname.get();
    }

    public void setReceiverLastname(String receiverLastname) {
        ReceiverLastname.set(receiverLastname);
    }

    public String getReceiverFirstname() {
        return ReceiverFirstname.get();
    }

    public void setReceiverFirstname(String receiverFirstname) {
        ReceiverFirstname.set(receiverFirstname);
    }

    public String getDateDonated() {
        return DateDonated.get();
    }

    public void setDateDonated(String dateDonated) {
        DateDonated.set(dateDonated);
    }

    public String getDateTaken() {
        return DateTaken.get();
    }

    public void setDateTaken(String dateTaken) {
        DateTaken.set(dateTaken);
    }
}