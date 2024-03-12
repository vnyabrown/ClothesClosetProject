package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		/*
		 * if *viewName.equals("VIWNAMEHERE") == true)
		 * {
		 * 		return new VIEWNAMEHERE(model);
		 * }
		 */
		if(viewName.equals("VIEWNAMEHERE") == true)
		{
			return null;
			// this is just an example
		}
		else
			return null;
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
