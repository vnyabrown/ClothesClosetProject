package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	/* public static View createView(String viewName, IModel model)
	{
		return switch (viewName) {
			case "ClosetView" -> new ClosetView(model);
			case "ArticleChoiceView" -> new ArticleChoiceView(model);
			case "ColorChoiceView" -> new ColorChoiceView(model);
			case "InsertArticleView" -> new InsertArticleView(model);
			case "ModifyArticleView" -> new ModifyArticleView(model);
			case "DeleteArticleView" -> new DeleteArticleView(model);
			default -> null;
		};
	} */

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("ClosetView") == true)
		{
			return new ClosetView(model);
		}
		else if(viewName.equals("ArticleChoiceView") == true)
		{
			return new ArticleChoiceView(model);
		}
		else if (viewName.equals("ColorChoiceView") == true)
		{
			return new ColorChoiceView(model);
		}
		else if (viewName.equals("InsertArticleView") == true)
		{
			return new InsertArticleView(model);
		}
		else if (viewName.equals("InsertColorView") == true)
		{
			return new InsertColorView(model);
		}
		else if (viewName.equals("DeleteArticleView") == true)
		{
			return new DeleteArticleView(model);
		}
		else if(viewName.equals("SearchArticleTypeView")) {
			return new SearchArticleTypeView(model);
		}
		else if(viewName.equals("ArticleTypeCollectionView")) {
			return new ArticleTypeCollectionView(model);
		}
		else if (viewName.equals("ModifyArticleTypeView") == true)
		{
			return new ModifyArticleTypeView(model);
		}
		else if (viewName.equals("DeleteColorView") == true)
		{
			return new DeleteColorView(model);
		}
		else if(viewName.equals("SearchColorView")) {
			return new SearchColorView(model);
		}
		else if(viewName.equals("ColorCollectionView")) {
			return new ColorCollectionView(model);
		}
		else if(viewName.equals("ModifyColorView")) {
			return new ModifyColorView(model);
		}
		else {
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
