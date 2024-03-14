import model.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties atp = new Properties();

        atp.setProperty("description", "socks");
        atp.setProperty("barcodePrefix", "ss");
        atp.setProperty("alphaCode", "sk");

        ArticleType at = new ArticleType(atp);
        at.updateStateInDatabase();
        System.out.println(at.toString());
    }
}
