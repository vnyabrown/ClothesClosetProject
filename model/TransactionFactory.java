// specify the package
package model;

// system imports
//import userinterface.InsertBookView;

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory
{
    //----------------------------------------------------------
    public static Transaction createTransaction(String transType)
            throws Exception
    {

        switch (transType) {
            case "InsertArticle":
                return new InsertArticleTransaction();
            case "ModifyArticle":
                //return new ModifyArticleTransaction();
            case "DeleteArticle":
                //return new DeleteArticleTransaction();

            case "InsertColor":
                //return new InsertColorTransaction();
            case "ModifyColor":
                return new ModifyColorTransaction();
            case "DeleteColor":
                return new DeleteColorTransaction();

            case "InsertClothing":
                //return new InsertClothingTransaction();
            case "ModifyClothing":
                //return new ModifyClothingTransaction();
            case "DeleteClothing":
                //return new DeleteClothingTransaction();

            case "Checkout":
                //return new CheckoutTransaction();
            case "ListInventory":
                //return new ListInventoryTransaction();
            case "ListCheckout":
                //return new ListCheckoutTransaction();
            default:
                return null;
        }
    }
}
