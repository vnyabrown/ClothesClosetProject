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

            String str = "this is now a 2-piece suit.";
            at.modifyDescription(str);
            at.updateStateInDatabase();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        //atc.display();
    }
}
