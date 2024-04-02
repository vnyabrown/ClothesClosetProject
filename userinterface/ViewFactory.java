package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
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
