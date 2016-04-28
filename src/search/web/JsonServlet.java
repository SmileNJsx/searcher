package search.web;

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
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		
		search= new String(request.getParameter("s_form").getBytes("iso-8859-1"), "utf-8"); 
		System.out.println(search);
		
		try {
			searchDb.search(search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(;i<searchDb.title.size()-1;i++)
		{
			result += "{"
					+ "\"title\":"+"\""+searchDb.title.get(i)+"\""+","
					+ "\"url\":"+"\""+searchDb.url.get(i)+"\""
					+ "}"+","
					+ "\n";
		}
		
		result += "{"
				+ "\"title\":"+"\""+searchDb.title.get(i)+"\""+","
				+ "\"url\":"+"\""+searchDb.url.get(i)+"\""
				+ "}"
				+ "\n";
		
		result = result+"]}";
		
		response.getWriter().println(result);
	}
}
