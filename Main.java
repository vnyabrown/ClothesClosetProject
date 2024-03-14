import model.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        ArticleTypeCollection atc = new ArticleTypeCollection();

        try {
        atc.findArticleTypeWithAlphaCode("bb");
        }
        catch(Exception e) {
            System.out.println(e);
        }

        atc.display();
    }
}
