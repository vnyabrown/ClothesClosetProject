import model.*;
import java.util.Properties;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        ArticleTypeCollection atc = new ArticleTypeCollection();

        try {
            
            //atc.findArticleTypeWithDescription("so");
            //ArticleType at = new ArticleType(atc.getState("getVector").get(0));
            Properties p = new Properties();
            p.setProperty("id", "15");
            p.setProperty("description", "blank");
            p.setProperty("barcodePrefix", "zz");
            p.setProperty("alphaCode", "zz");
            ArticleType at = new ArticleType(p);
            //at.updateStateInDatabase();
            at.stateChangeRequest("markInactive", "");

        }
        catch(Exception e) {
            System.out.println(e);
        }

        atc.display();
    }
}
