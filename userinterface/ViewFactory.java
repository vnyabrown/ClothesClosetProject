package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		switch (viewName) {
			case "ClosetView":
				return new ClosetView(model);
			case "ArticleChoiceView":
				return new ArticleChoiceView(model);
			case "ColorChoiceView":
				return new ColorChoiceView(model);
			case "InventoryChoiceView":
				return new InventoryChoiceView(model);
			case "InsertArticleView":
				return new InsertArticleView(model);
			case "InsertColorView":
				return new InsertColorView(model);
			case "DeleteArticleView":
				return new DeleteArticleView(model);
			case "SearchArticleTypeView":
				return new SearchArticleTypeView(model);
			case "ArticleTypeCollectionView":
				return new ArticleTypeCollectionView(model);
			case "ModifyArticleTypeView":
				return new ModifyArticleTypeView(model);
			case "DeleteColorView":
				return new DeleteColorView(model);
			case "SearchColorView":
				return new SearchColorView(model);
			case "ColorCollectionView":
				return new ColorCollectionView(model);
			case "ModifyColorView":
				return new ModifyColorView(model);
			case "InsertClothingView":
				return new InsertClothingView(model);
			case "SearchForClothing":
				return new SearchForClothingView(model);
			default:
				System.out.println("No view found matching " + viewName + " in view factory.");
				return null;
		}
	}

	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
