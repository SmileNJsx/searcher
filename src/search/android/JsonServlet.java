package search.android;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search.search.SearchDb;

@SuppressWarnings("serial")
public class JsonServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String search;
		
		int i =0;
		
		String result="{"
				+ "\"list\":[";
		
		SearchDb searchDb =new SearchDb();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "application/x-java-serialized-object");
		
		search= request.getParameter("content"); 
		System.out.println(search);
		
		try {
			searchDb.search(search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(;i<searchDb.Title.size()-1;i++)
		{
			result += "{"
					+ "\"title\":"+"\""+searchDb.Title.get(i)+"\""+","
					+ "\"url\":"+"\""+searchDb.Url.get(i)+"\""
					+ "}"+","
					+ "\n";
		}
		
		result += "{"
				+ "\"title\":"+"\""+searchDb.Title.get(i)+"\""+","
				+ "\"url\":"+"\""+searchDb.Url.get(i)+"\""
				+ "}"
				+ "\n";
		
		result = result+"]}";
		
		response.getWriter().println(result);
	}
}

