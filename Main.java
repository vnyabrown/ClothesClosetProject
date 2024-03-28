import model.*;
import java.util.Properties;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        ArticleTypeCollection atc = new ArticleTypeCollection();

        try {
            
            // //atc.findArticleTypeWithDescription("so");
            // //ArticleType at = new ArticleType(atc.getState("getVector").get(0));
            Properties p = new Properties();
            p.setProperty("Description", "blank");
            p.setProperty("Id", "20");
            p.setProperty("BarcodePrefix", "zz");
            p.setProperty("AlphaCode", "zz");
            ArticleType at = new ArticleType(p);
            System.out.println(at.toString());
            // System.out.println("here in main");
            //at.updateStateInDatabase();
            at.stateChangeRequest("markInactive", "");

        }
        catch(Exception e) {
            System.out.println(e);
        }

        atc.display();
    }
}
