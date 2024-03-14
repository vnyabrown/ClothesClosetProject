import model.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties atp = new Properties();

        atp.setProperty("description", "t-shirt");
        atp.setProperty("barcodePrefix", "ts");
        atp.setProperty("alphaCode", "bb");

        ArticleType at = new ArticleType(atp);
        at.updateStateInDatabase();
        System.out.println(at.toString());
    }
}
