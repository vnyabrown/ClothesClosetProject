import model.*;
import java.util.Properties;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        ArticleTypeCollection atc = new ArticleTypeCollection();

        try {
            atc.findArticleTypeWithDescription("ie");
            Vector<ArticleType> atcVector = (Vector<ArticleType>)atc.getState("getVector");
            ArticleType at = atcVector.get(0);
            System.out.println(at.toString());

            System.out.println();
            Vector<String> v = at.getFields(); 
            for(String str : v) {
                System.out.println(str);
            }

            String str = "this is now a 3-piece suit.";
            String str2 = "QQ";
            String str3 = "010";

            at.modifyDescription(str);
            at.modifyAlphaCode(str2);
            at.updateStateInDatabase();

            at.modifyBarcodePrefix(str3);
            at.updateStateInDatabase();

            at.markInactive();
            at.updateStateInDatabase();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        //atc.display();
    }
}
