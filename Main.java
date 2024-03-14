import model.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties atp = new Properties();

        atp.setProperty("description", "Somthing new");
        atp.setProperty("barcodePrefix", "ab");
        atp.setProperty("alphaCode", "cd");

        ArticleType at = new ArticleType(atp);
        at.updateStateInDatabase();
        System.out.println(at.toString());
    }
}
