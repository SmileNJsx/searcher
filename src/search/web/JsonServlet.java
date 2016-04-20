package search.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search.search.SearchDb;

@SuppressWarnings("serial")
public class JsonServlet extends HttpServlet{
	public static String search;
	
	public static int i =0;
	
	public static String result="{"
			+ "\"list\":[";
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		
		search= new String(request.getParameter("s_form").getBytes("iso-8859-1"), "utf-8"); 
		System.out.println(search);
		
		try {
			SearchDb.search(search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(;i<SearchDb.title.size()-1;i++)
		{
			result += "{"
					+ "\"title\":"+"\""+SearchDb.title.get(i)+"\""+","
					+ "\"url\":"+"\""+SearchDb.url.get(i)+"\""
					+ "}"+","
					+ "\n";
		}
		
		result += "{"
				+ "\"title\":"+"\""+SearchDb.title.get(i)+"\""+","
				+ "\"url\":"+"\""+SearchDb.url.get(i)+"\""
				+ "}"
				+ "\n";
		
		result = result+"]}";
		
		response.getWriter().println(result);
	}
}
