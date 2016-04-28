package search.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import search.db.DbConnector;

public class SearchDb {
	
	private static Connection conn = DbConnector.getconnection();
	
	public static String search;
	
	public ArrayList<String> title = new ArrayList<String>();
	public ArrayList<String> url = new ArrayList<String>();
	
	public void search(String search) throws SQLException
	{
		
		SearchDb.search = search;
		
		String sql_1 = "select title from t_url where title like '%"+search+"%' limit 10";
		String sql_2 = "select url from t_url where title like '%"+search+"%' limit 10";
    	
		System.out.println(sql_1);
        Statement statement = conn.createStatement();
        
        ResultSet resultSet_1 = statement.executeQuery(sql_1);
        
        while(resultSet_1.next())
        {
		    String tranfer = resultSet_1.getString("title");
            
		    title.add(tranfer);
        }
        
        ResultSet resultSet_2 = statement.executeQuery(sql_2);
        
        while(resultSet_2.next())
        {
		    String tranfer = resultSet_2.getString("url");
            
		    url.add(tranfer);
        }
	}
}
